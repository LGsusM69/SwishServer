let isServerRunning = false;

// Function to start the server
function startServer() {
    console.log("JavaScript: start server function called");
    if (window.app) {
        window.app.startServer();
        updateServerStatus(true);
    } else {
        console.log("App not available!");
    }
}

// Function to stop the server
function stopServer() {
    if (window.app) {
        window.app.stopServer();
        updateServerStatus(false);
    } else {
        console.log("App not available!");
    }
}

// Update server status in the GUI
function updateServerStatus(isRunning) {
    const statusText = document.getElementById("server-status");
    const startButton = document.getElementById("start-server");
    const stopButton = document.getElementById("stop-server");

    if (isRunning) {
        statusText.textContent = "Server is running";
        statusText.style.color = "green";
        startButton.disabled = true;
        stopButton.disabled = false;
    } else {
        statusText.textContent = "Server is not running";
        statusText.style.color = "red";
        startButton.disabled = false;
        stopButton.disabled = true;
    }
}

// Add a client to the table
function addClient(clientId, username, ipAddress) {
    const tableBody = document.getElementById("clients-table").querySelector("tbody");
    const row = document.createElement("tr");

    row.innerHTML = `
        <td>${clientId}</td>
        <td>${username}</td>
        <td>${ipAddress}</td>
    `;

    tableBody.appendChild(row);
}

// Remove a client from the table
function removeClient(clientId) {
    const tableBody = document.getElementById("clients-table").querySelector("tbody");
    for (let row of tableBody.rows) {
        if (row.cells[0].textContent === clientId) {
            tableBody.removeChild(row);
            break;
        }
    }
}

// Append a log message
function appendLog(message) {
    const logs = document.getElementById("server-logs");
    logs.value += message + "\n";
    logs.scrollTop = logs.scrollHeight;
}
