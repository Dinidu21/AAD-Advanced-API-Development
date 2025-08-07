// Function to format date to a readable string
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
}

// Function to populate the User Management table
async function populateUserTable() {
    try {
        const response = await fetch('/api/users');
        const users = await response.json();
        const tableBody = document.querySelector('.user-management-table tbody');
        tableBody.innerHTML = ''; // Clear loading message

        users.forEach(user => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${formatDate(user.joined)}</td>
                <td>${user.status || 'Active'}</td>
                <td>
                    <button onclick="editUser(${user.id})">Edit</button>
                    <button onclick="deleteUser(${user.id})">Delete</button>
                </td>
            `;
            tableBody.appendChild(row);
        });
    } catch (error) {
        console.error('Error fetching users:', error);
        const tableBody = document.querySelector('.user-management-table tbody');
        tableBody.innerHTML = '<tr><td colspan="5">Error loading users</td></tr>';
    }
}

// Function to populate the Your Profile section
async function populateProfile() {
    try {
        const response = await fetch('/api/profile');
        const user = await response.json();
        const welcomeMessage = document.querySelector('.welcome-message');
        const profileDetails = document.querySelector('.profile-details');

        // Update welcome message
        welcomeMessage.textContent = `Welcome back, ${user.username}`;

        // Update profile details
        profileDetails.innerHTML = `
            <div class="flex items-center space-x-4">
                <img src="/images/avatar-placeholder.png" alt="User Avatar" class="w-16 h-16 rounded-full">
                <div>
                    <h3 class="text-lg font-semibold">${user.username}</h3>
                    <p class="text-gray-600">@${user.username}</p>
                    <p class="text-gray-600">${user.role}</p>
                </div>
            </div>
            <div class="grid grid-cols-2 gap-4 mt-4">
                <div>
                    <p class="text-sm text-gray-500">User ID</p>
                    <p class="font-medium">#${user.id}</p>
                </div>
                <div>
                    <p class="text-sm text-gray-500">Last updated</p>
                    <p class="font-medium">${formatDate(user.lastUpdated)}</p>
                </div>
                <div>
                    <p class="text-sm text-gray-500">Member since</p>
                    <p class="font-medium">${formatDate(user.joined)}</p>
                </div>
                <div>
                    <p class="text-sm text-gray-500">Email</p>
                    <p class="font-medium">${user.email}</p>
                </div>
            </div>
        `;
    } catch (error) {
        console.error('Error fetching profile:', error);
        const profileDetails = document.querySelector('.profile-details');
        profileDetails.innerHTML = '<p>Error loading profile</p>';
    }
}

// Placeholder functions for edit and delete actions
function editUser(id) {
    alert(`Edit user with ID: ${id}`);
    // Implement edit functionality (e.g., open modal with user details)
}

function deleteUser(id) {
    if (confirm(`Are you sure you want to delete user with ID: ${id}?`)) {
        // Implement delete functionality (e.g., send DELETE request to /api/users/:id)
        alert(`User with ID: ${id} deleted`);
    }
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', () => {
    populateUserTable();
    populateProfile();
});