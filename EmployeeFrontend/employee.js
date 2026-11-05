// Show the corresponding form based on button clicked
document.getElementById('addEmployeeBtn').addEventListener('click', function () {
    toggleForms('addEmployeeForm');
});
document.getElementById('updateEmployeeBtn').addEventListener('click', function () {
    toggleForms('updateEmployeeForm');
});
document.getElementById('deleteEmployeeBtn').addEventListener('click', function () {
    toggleForms('deleteEmployeeForm');
});
document.getElementById('findEmployeeBtn').addEventListener('click', function () {
    toggleForms('findEmployeeForm');
});
document.getElementById('fetchAllEmployeesBtn').addEventListener('click', function () {
    toggleForms('fetchAllEmployeesForm');
    fetchAllEmployees();
});

function toggleForms(formId) {
    const forms = document.querySelectorAll('.form');
    forms.forEach(form => form.style.display = 'none');
    document.getElementById(formId).style.display = 'block';
}

// Add Employee
function addEmployee() {
    const name = document.getElementById('addEmployeeName').value;
    const email = document.getElementById('addEmployeeEmail').value;
    const phone = document.getElementById('addEmployeePhone').value;
    const address = document.getElementById('addEmployeeAddress').value;
    const department = document.getElementById('addEmployeeDepartment').value;
    const joinDate = document.getElementById('addEmployeeJoinDate').value;
    
    //alert("Button clicked!"); 
    if (!name || !email || !phone || !department || !joinDate) {
        alert("Please fill in all required fields!");
        return;
    }

    fetch('http://localhost:8080/api/employees/add', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, phone, address, department, joinDate })
    })
        .then(response => {
            if (response.ok) {
                alert("Employee added!");
                // Clear all fields after successful addition
                document.getElementById('addEmployeeName').value = "";
                document.getElementById('addEmployeeEmail').value = "";
                document.getElementById('addEmployeePhone').value = "";
                document.getElementById('addEmployeeAddress').value = "";
                document.getElementById('addEmployeeDepartment').value = "";
                document.getElementById('addEmployeeJoinDate').value = "";
            } else {
                alert("Add failed.");
            }
        })
        .catch(err => console.error("Error:", err));
        //console.log("add method called")
}


// Update Employee
function updateEmployee() {
    const id = document.getElementById('updateEmployeeId').value;
    const name = document.getElementById('updateEmployeeName').value;
    const email = document.getElementById('updateEmployeeEmail').value;
    const phone = document.getElementById('updateEmployeePhone').value;
    const address = document.getElementById('updateEmployeeAddress').value;
    const department = document.getElementById('updateEmployeeDepartment').value;
    const joinDate = document.getElementById('updateEmployeeJoinDate').value;

    if (!id || !name || !email || !phone || !department || !joinDate) {
        alert("Please fill in all required fields!");
        return;
    }

    fetch(`http://localhost:8080/api/employees/update/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, phone, address, department, joinDate })
    })
        .then(response =>{  
            if (response.ok) {
            alert("Employee Updated!");
            // Clear all fields after successful addition
            document.getElementById('addEmployeeName').value = "";
            document.getElementById('addEmployeeEmail').value = "";
            document.getElementById('addEmployeePhone').value = "";
            document.getElementById('addEmployeeAddress').value = "";
            document.getElementById('addEmployeeDepartment').value = "";
            document.getElementById('addEmployeeJoinDate').value = "";
        } else {
            alert("Add failed.");
        }
    })
        .catch(err => console.error("Error:", err));
}

// Delete Employee
function deleteEmployee() {
    const id = document.getElementById('deleteEmployeeId').value;

    fetch(`http://localhost:8080/api/employees/delete/${id}`, {
        method: 'DELETE'
    })
        .then(response => response.ok ? alert("Employee deleted!") : alert("Delete failed."))
        .catch(err => console.error("Error:", err));
}

// Find by ID
function findEmployeeById() {
    const id = document.getElementById('findEmployeeId').value;

    fetch(`http://localhost:8080/api/employees/find/byId/${id}`)
        .then(response => {
            if (!response.ok) throw new Error("Network response was not ok");
            return response.json();
        })
        .then(data => {
            if (data && data.data) {
                displayEmployeeDetails(data.data);
            } else {
                document.getElementById('employeeDetails').innerHTML = "Employee not found.";
            }
        })
        .catch(err => {
            document.getElementById('employeeDetails').innerHTML = "Employee not found.";
            console.error("Error fetching employee:", err);
        });
}

function displayEmployeeDetails(emp) {
    const container = document.getElementById('employeeDetails');
    container.innerHTML = `
        <p><strong>ID:</strong> ${emp.eid}</p>
        <p><strong>Name:</strong> ${emp.name}</p>
        <p><strong>Email:</strong> ${emp.email}</p>
        <p><strong>Phone:</strong> ${emp.phone}</p>
        <p><strong>Address:</strong> ${emp.address}</p>
        <p><strong>Department:</strong> ${emp.department}</p>
        <p><strong>Join Date:</strong> ${emp.joinDate}</p>
    `;
}


// Find by Name
function findEmployeeByName() {
    const name = document.getElementById('findEmployeeName').value;

    fetch(`http://localhost:8080/api/employees/find/byName/search?name=${encodeURIComponent(name)}`)
        .then(response => {
            if (!response.ok) throw new Error("Network response was not ok");
            return response.json();
        })
        .then(data => {
            if (data && Array.isArray(data.data) && data.data.length > 0) {
                displayEmployeeList(data.data);
            } else {
                document.getElementById('employeeDetails').innerHTML = "No employees found.";
            }
        })
        .catch(err => {
            document.getElementById('employeeDetails').innerHTML = "Error fetching employees.";
            console.error("Error:", err);
        });
}

// Display list of employees
function displayEmployeeList(employees) {
    const container = document.getElementById('employeeDetails');
    container.innerHTML = ""; // Clear previous content

    employees.forEach(emp => {
        const empDiv = document.createElement('div');
        empDiv.classList.add('employee-card');
        empDiv.innerHTML = `
            <hr>
            <p><strong>ID:</strong> ${emp.eid}</p>
            <p><strong>Name:</strong> ${emp.name}</p>
            <p><strong>Email:</strong> ${emp.email}</p>
            <p><strong>Phone:</strong> ${emp.phone}</p>
            <p><strong>Address:</strong> ${emp.address}</p>
            <p><strong>Department:</strong> ${emp.department}</p>
            <p><strong>Join Date:</strong> ${emp.joinDate}</p>
        `;
        container.appendChild(empDiv);
    });
}


// Find by Department
function findEmployeeByDepartment() {
    const department = document.getElementById('findEmployeeDepartment').value;

    fetch(`http://localhost:8080/api/employees/find/byDepartment/search?department=${encodeURIComponent(department)}`)
        .then(response => response.json())
        .then(data => {
            if (data && Array.isArray(data.data) && data.data.length > 0) {
                displayEmployeeList(data.data);
            } else {
                document.getElementById('employeeDetails').innerHTML = "No employees found.";
            }
        })
        .catch(err => {
            document.getElementById('employeeDetails').innerHTML = "Error fetching employees.";
            console.error("Error:", err);
        });
}

// Fetch All Employees
function fetchAllEmployees() {
    fetch('http://localhost:8080/api/employees/find/all')
        .then(response => {
            if (!response.ok) throw new Error("Network error");
            return response.json();
        })
        .then(data => {
            if (data && Array.isArray(data.data) && data.data.length > 0) {
                displayEmployeeListAll(data.data);
            } else {
                document.getElementById('allEmployeesList').innerHTML = "No employees found.";
            }
        })
        .catch(err => {
            document.getElementById('allEmployeesList').innerHTML = "Error loading employees.";
            console.error("Error:", err);
        });
}

function displayEmployeeListAll(employees) {
    const container = document.getElementById('allEmployeesList');
    
    let tableHTML = `
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Department</th>
                    <th>Join Date</th>
                </tr>
            </thead>
            <tbody>
    `;

    employees.forEach(emp => {
        tableHTML += `
            <tr>
                <td>${emp.eid}</td>
                <td>${emp.name}</td>
                <td>${emp.email}</td>
                <td>${emp.phone}</td>
                <td>${emp.address}</td>
                <td>${emp.department}</td>
                <td>${emp.joinDate}</td>
            </tr>
        `;
    });

    tableHTML += `
            </tbody>
        </table>
    `;

    container.innerHTML = tableHTML;
}



