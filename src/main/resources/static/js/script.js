document.getElementById("loginButton").addEventListener("click", () => {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const data = {
        username: username,
        password: password
    };

    // Disable login button while request is in progress
    document.getElementById("loginButton").disabled = true;

    fetch("/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Network response was not ok');
        }
    })
    .then(data => {
        console.log('Login successful:', data);
        // Handle successful login, e.g., redirect to the dashboard
        window.location.href = "/dashboard";
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred during login. Please check your credentials and try again.');
    })
    .finally(() => {
        // Re-enable login button after request completion
        document.getElementById("loginButton").disabled = false;
    });
});