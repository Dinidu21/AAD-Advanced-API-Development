$('#saveJobBtn').click(() => {
    const job = {
        jobTitle: $('#jobTitle').val(),
        company: $('#companyName').val(),
        location: $('#jobLocation').val(),
        type: $('#jobType').val(),
        jobDescription: $('#jobDescription').val(),
        status: 'ACTIVE'
    };

    fetch('http://localhost:8080/api/v1/jobs', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(job)
    })
        .then(res => res.json())
        .then(data => {
            if (data.status === 409) {
                alert("Job already exists!");
            } else {
                alert(data.message);
                loadJobs();
            }
        })
        .catch(err => {
            console.error(err);
            alert("Error occurred while saving the job.");
        });
});

function loadJobs(page = 0, size = 5) {
    fetch(`http://localhost:8080/api/v1/jobs?page=${page}&size=${size}`)
        .then(res => res.json())
        .then(data => {
            const jobs = data.data.content;
            let html = '';
            jobs.forEach((job, index) => {
                html += `
                    <tr>
                        <td>${index + 1}</td>
                        <td>${job.jobTitle}</td>
                        <td>${job.company}</td>
                        <td>${job.location}</td>
                        <td>${job.type}</td>
                        <td>${job.status}</td>
                        <td class="text-end">
                            <button class="btn btn-sm btn-warning me-2"
                                    onclick="editJob('${encodeURIComponent(JSON.stringify(job))}')">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="btn btn-sm btn-danger" onclick="deleteJob(${job.id})"><i class="fas fa-trash"></i></button>
                        </td>
                    </tr>
                `;
            });
            $('#jobsTableBody').html(html);
        })
        .catch(err => console.error(err));
}

$(document).ready(() => {
    loadJobs();
});

function editJob(jobData) {
    const job = typeof jobData === 'string' ? JSON.parse(decodeURIComponent(jobData)) : jobData;

    $('#editJobId').val(job.id);
    $('#editJobTitle').val(job.jobTitle);
    $('#editCompanyName').val(job.company);
    $('#editJobLocation').val(job.location);
    $('#editJobType').val(job.type);
    $('#editJobDescription').val(job.jobDescription || '');

    const editModal = new bootstrap.Modal(document.getElementById('editJobModal'));
    editModal.show();
}

$('#updateJobBtn').click(() => {
    const job = {
        id: $('#editJobId').val(),
        jobTitle: $('#editJobTitle').val(),
        company: $('#editCompanyName').val(),
        location: $('#editJobLocation').val(),
        type: $('#editJobType').val(),
        jobDescription: $('#editJobDescription').val(),
        status: 'ACTIVE' // or get from original if you store it
    };

    fetch('http://localhost:8080/api/v1/jobs', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(job)
    })
        .then(res => res.json())
        .then(data => {
            alert(data.message);
            loadJobs();
        })
        .catch(err => console.error(err));
});

function toggleStatus(id) {
    fetch(`http://localhost:8080/api/v1/jobs/${id}/status`, {
        method: 'PATCH'
    })
        .then(res => res.json())
        .then(data => {
            alert(data.message);
            loadJobs();
        })
        .catch(err => console.error(err));
}

$('#searchInput').on('input', function () {
    const keyword = $(this).val().trim();
    if (keyword.length === 0) {
        loadJobs(); // load all
        return;
    }

    fetch(`http://localhost:8080/api/v1/jobs/search/${keyword}`)
        .then(res => res.json())
        .then(data => {
            const jobs = data.data;
            let html = '';
            jobs.forEach((job, index) => {
                html += `
                    <tr>
                        <td>${index + 1}</td>
                        <td>${job.jobTitle}</td>
                        <td>${job.company}</td>
                        <td>${job.location}</td>
                        <td>${job.type}</td>
                        <td>${job.status}</td>
                        <td class="text-end">
                            <button class="btn btn-sm btn-warning me-2" onclick="editJob(${encodeURIComponent(JSON.stringify(job))})"><i class="fas fa-edit"></i></button>
                            <button class="btn btn-sm btn-secondary" onclick="toggleStatus(${job.id})"><i class="fas fa-sync-alt"></i></button>
                        </td>
                    </tr>
                `;
            });
            $('#jobsTableBody').html(html);
        })
        .catch(err => console.error(err));
});

function deleteJob(id) {
    if (!confirm("Are you sure you want to delete this job?")) return;

    fetch(`http://localhost:8080/api/v1/jobs/${id}`, {
        method: 'DELETE'
    })
        .then(res => res.json())
        .then(data => {
            alert(data.message);
            loadJobs();
        })
        .catch(err => console.error(err));
}
