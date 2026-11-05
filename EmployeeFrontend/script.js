document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();  // Prevent default form submission

    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;

    // Send a POST request to your login API
    fetch('http://localhost:8080/api/admin/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username: username, password: password })  // Ensure this matches the credentials
    })
    .then(response => {
        if (response.ok) {
            window.location.href = "/employee.html";  // Redirect to employee page on success
        } else {
            document.getElementById('error').textContent = 'Invalid username or password';  // Show error message on failure
        }
    })
    .catch(error => {
        console.error('Login failed:', error);  // Log error in case of a network issue
    });
});
