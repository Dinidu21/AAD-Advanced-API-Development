// Configuration
const API_BASE_URL = 'http://localhost:8080/api/v1/job';
const ITEMS_PER_PAGE = 5;

// State management
let currentPage = 0;
let totalJobs = 0;
let jobs = [];

$(document).ready(function() {
    loadJobs(currentPage);

    // Search functionality
    $('#searchInput').on('input', function() {
        const searchTerm = $(this).val().trim();
        if (searchTerm.length >= 3 || searchTerm.length === 0) {
            loadJobs(0, searchTerm);
        }
    });

    // Save job handler
    $('#saveJobBtn').click(saveJob);

    // Update job handler
    $('#updateJobBtn').click(updateJob);
});

function loadJobs(page, searchTerm = '') {
    showLoading(true);

    const url = searchTerm ?
        `${API_BASE_URL}/search/${encodeURIComponent(searchTerm)}` :
        `${API_BASE_URL}/alljobs?page=${page}&size=${ITEMS_PER_PAGE}`;

    $.ajax({
        url: url,
        type: 'GET',
        success: function(response) {
            // Handle both paginated and non-paginated responses
            if (searchTerm) {
                jobs = response;
                totalJobs = jobs.length;
                currentPage = 0;
                renderJobs(jobs.slice(0, ITEMS_PER_PAGE));
            } else {
                jobs = response.content;
                totalJobs = response.totalElements;
                renderJobs(jobs);
            }
            renderPagination();
            showLoading(false);
        },
        error: function(xhr) {
            console.error('Error loading jobs:', xhr.responseText);
            showError('Failed to load jobs. Please try again.');
            showLoading(false);
        }
    });
}

function renderJobs(jobs) {
    const tbody = $('#jobsTableBody');
    tbody.empty();

    if (jobs.length === 0) {
        tbody.append(`
            <tr>
                <td colspan="7" class="text-center py-5">
                    <i class="fas fa-inbox fa-3x text-muted mb-3"></i>
                    <h5 class="text-muted">No jobs found</h5>
                </td>
            </tr>
        `);
        return;
    }

    jobs.forEach((job, index) => {
        const row = `
            <tr>
                <td>${job.id}</td>
                <td>${job.jobTitle}</td>
                <td>${job.company}</td>
                <td>${job.location}</td>
                <td>${job.type}</td>
                <td>
                    <span class="badge badge-status ${job.status === 'active' ? 'bg-success' : 'bg-secondary'}">
                        ${job.status === 'active' ? 'Active' : 'Inactive'}
                    </span>
                </td>
                <td class="text-end">
                    <button class="btn btn-sm btn-outline-primary btn-action edit-job" 
                            data-id="${job.id}" data-bs-toggle="modal" data-bs-target="#editJobModal">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm ${job.status === 'active' ? 'btn-outline-warning' : 'btn-outline-success'} btn-action toggle-status" 
                            data-id="${job.id}">
                        ${job.status === 'active' ? '<i class="fas fa-ban"></i>' : '<i class="fas fa-check"></i>'}
                    </button>
                </td>
            </tr>
        `;
        tbody.append(row);
    });

    // Attach event handlers
    $('.edit-job').click(function() {
        const jobId = $(this).data('id');
        const job = jobs.find(j => j.id === jobId);
        if (job) {
            $('#editJobId').val(job.id);
            $('#editJobTitle').val(job.jobTitle);
            $('#editCompanyName').val(job.company);
            $('#editJobLocation').val(job.location);
            $('#editJobType').val(job.type);
            $('#editJobDescription').val(job.jobDescription);
        }
    });

    $('.toggle-status').click(function() {
        const jobId = $(this).data('id');
        toggleJobStatus(jobId);
    });
}

function renderPagination() {
    const totalPages = Math.ceil(totalJobs / ITEMS_PER_PAGE);
    const pagination = $('#paginationContainer');
    pagination.empty();

    // Pagination info
    const start = Math.min(currentPage * ITEMS_PER_PAGE + 1, totalJobs);
    const end = Math.min((currentPage + 1) * ITEMS_PER_PAGE, totalJobs);
    $('#paginationInfo').text(`Showing ${start} to ${end} of ${totalJobs} jobs`);

    // Previous button
    const prevDisabled = currentPage === 0 ? 'disabled' : '';
    pagination.append(`
        <li class="page-item ${prevDisabled}">
            <a class="page-link" href="#" aria-label="Previous" data-page="${currentPage - 1}">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
    `);

    // Page numbers
    for (let i = 0; i < totalPages; i++) {
        const active = i === currentPage ? 'active' : '';
        pagination.append(`
            <li class="page-item ${active}">
                <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
            </li>
        `);
    }

    // Next button
    const nextDisabled = currentPage >= totalPages - 1 ? 'disabled' : '';
    pagination.append(`
        <li class="page-item ${nextDisabled}">
            <a class="page-link" href="#" aria-label="Next" data-page="${currentPage + 1}">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    `);

    // Attach page click handlers
    $('.page-link').click(function(e) {
        e.preventDefault();
        const page = $(this).data('page');
        if (page !== undefined) {
            currentPage = page;
            loadJobs(currentPage, $('#searchInput').val().trim());
        }
    });
}

function saveJob() {
    const jobData = {
        jobTitle: $('#jobTitle').val(),
        company: $('#companyName').val(),
        location: $('#jobLocation').val(),
        type: $('#jobType').val(),
        jobDescription: $('#jobDescription').val(),
        status: 'active' // Default status
    };

    $.ajax({
        url: `${API_BASE_URL}/create`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(jobData),
        success: function() {
            showSuccess('Job created successfully!');
            $('#addJobModal').modal('hide');
            $('#addJobForm')[0].reset();
            loadJobs(currentPage);
        },
        error: function(xhr) {
            showError('Failed to create job. Please try again.');
        }
    });
}

function updateJob() {
    const jobData = {
        id: $('#editJobId').val(),
        jobTitle: $('#editJobTitle').val(),
        company: $('#editCompanyName').val(),
        location: $('#editJobLocation').val(),
        type: $('#editJobType').val(),
        jobDescription: $('#editJobDescription').val(),
        status: 'active' // Status not changed in edit
    };

    $.ajax({
        url: `${API_BASE_URL}/edit`,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(jobData),
        success: function() {
            showSuccess('Job updated successfully!');
            $('#editJobModal').modal('hide');
            loadJobs(currentPage);
        },
        error: function(xhr) {
            showError('Failed to update job. Please try again.');
        }
    });
}

function toggleJobStatus(jobId) {
    $.ajax({
        url: `${API_BASE_URL}/status/${jobId}`,
        type: 'PATCH',
        success: function() {
            showSuccess('Job status updated!');
            loadJobs(currentPage);
        },
        error: function(xhr) {
            showError('Failed to update job status. Please try again.');
        }
    });
}

// UI Helpers
function showLoading(show) {
    if (show) {
        $('#jobsTableBody').html(`
            <tr>
                <td colspan="7" class="text-center py-5">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Loading...</span>
                    </div>
                </td>
            </tr>
        `);
    }
}

function showSuccess(message) {
    const alert = $(`
        <div class="alert alert-success alert-dismissible fade show position-fixed top-0 end-0 m-4" role="alert">
            <i class="fas fa-check-circle me-2"></i> ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    `);
    $('body').append(alert);
    setTimeout(() => alert.alert('close'), 3000);
}

function showError(message) {
    const alert = $(`
        <div class="alert alert-danger alert-dismissible fade show position-fixed top-0 end-0 m-4" role="alert">
            <i class="fas fa-exclamation-circle me-2"></i> ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    `);
    $('body').append(alert);
    setTimeout(() => alert.alert('close'), 5000);
}