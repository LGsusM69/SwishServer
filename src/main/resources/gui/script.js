let isServerRunning = false;

// Function to start the server
function startServer() {
const serverPort = document.getElementById("portNumber").value;
    console.log("JavaScript: start server function called");
    let connected = ""
    if (window.app) {
        connected = window.app.startServer(serverPort);
        if(connected.localeCompare("success") == 0) {
            updateServerStatus(true);
        } else {
        const statusText = document.getElementById("server-status");
        statusText.textContent = connected;
        statusText.style.color = "red";
        startButton.disabled = false;
        stopButton.disabled = true;
        }
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
function addClient(nickName, ipAddress) {
    const tableBody = document.getElementById("clients-table").querySelector("tbody");
    const row = document.createElement("tr");

    row.innerHTML = `
        <td>${nickName}</td>
        <td>${ipAddress}</td>
    `;

    tableBody.appendChild(row);
}

// Remove a client from the table
function removeClient(nickName) {
    console.log("platanito");
    const tableBody = document.getElementById("clients-table").querySelector("tbody");
    for (let row of tableBody.rows) {
        if (row.cells[0].textContent === nickName) {
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
