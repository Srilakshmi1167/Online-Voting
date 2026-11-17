```javascript id="signupjssep"
document.getElementById("signupForm").addEventListener("submit", function(e) {
    e.preventDefault();

    let formData = {
        firstName: this.firstName.value,
        lastName: this.lastName.value,
        email: this.email.value,
        password: this.password.value,
        phone: this.phone.value
    };

    fetch("http://localhost:8080/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(formData)
    })
    .then(response => response.text())
    .then(data => {
        document.getElementById("message").innerText = data;

        if (data.includes("Successfully")) {
            window.location.href = "login.html";
        }
    })
    .catch(error => {
        document.getElementById("message").innerText = "Server Error";
    });
});
```
