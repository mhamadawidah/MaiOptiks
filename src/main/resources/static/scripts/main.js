// Seiten mit Buttons öffnen
function openLink(link) {
    window.location.href = link;
}

// Datenbank Abfrage
function doRequest(method, url, url_extend, data, func) {
    debugger;
    let request = new XMLHttpRequest();

    url = 'http://localhost:8080' + url;

    if (url_extend !== undefined && url_extend !== "") {
        url += url_extend;
    }

    request.open(method, url, true);

    request.setRequestHeader("Accept", "application/json");
    request.setRequestHeader("Content-Type", "application/json");

    data !== undefined && data !== "" ? request.send(data) : request.send();

    request.onload = () => {
        if (request.status !== 200 || request.status !== 201 || request.status !== 202) {
            alert('\nError: ' + request.status + '\n\n' + JSON.parse(request.response).exception);
        }

        func(request.response);
    };

    request.onerror = () => {
        alert('\nNetzwerkfehler!\n\nBitte den Systemadministrator kontaktieren.');
    };
}

function doGetRequest(url, key, func){
    doRequest('GET', url, key, undefined, func)
}
function doPutRequest(url, data, func){
    doRequest('PUT', url, undefined, data, func)
}
function doPostRequest(url, key, data, func){
    doRequest('POST', url, key, data, func)
}
function doDeleteRequest(url, key, func){
    doRequest('DELETE', url, key, undefined, func)
}