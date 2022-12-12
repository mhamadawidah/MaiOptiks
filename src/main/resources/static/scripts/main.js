// Seiten mit Buttons öffnen
function openLink(link) {
    window.location.href = link;
}

// Datenbank Abfrage
function doRequest(method, endpoint, key, json_data, func) {
    let url = 'http://localhost:8080' + endpoint;

    if (key !== undefined && key !== '') {
        url += key;
    }

    //json_data = JSON.stringify(json_data);
    if (typeof (json_data) === 'object') {
        json_data = JSON.stringify(json_data);
    }

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: json_data,
    })
        .then(async (response) => {
            if (response.status >= 200 && response.status < 300) {
                switch (method) {
                    case 'POST':
                    case 'PUT':
                        return response.text();
                        break;
                    default:
                        return response.json();
                }
            } else if (response.status >= 500) {
                alert('\nServerfehler!\n\nBitte den Systemadministrator kontaktieren.');
            } else {
                alert('\nError: ' + response.status + '\n\n' + (JSON.parse(await response.text())).exception);

                return response.text()
            }
        })
        .then(async (data) => {
            if (func !== undefined && data !== undefined) {
                if (typeof func === 'function') {
                    func(data);
                }
            }
        })
        .catch(error => console.error(error));
}

function doGetRequest(endpoint, key, func) {
    doRequest('GET', endpoint, key, undefined, func)
}

function doPutRequest(endpoint, key, data, func) {
    doRequest('PUT', endpoint, key, data, func)
}

function doPostRequest(endpoint, data, func) {
    doRequest('POST', endpoint, undefined, data, func)
}

function doDeleteRequest(endpoint, key, func) {
    doRequest('DELETE', endpoint, key, undefined, func)
}