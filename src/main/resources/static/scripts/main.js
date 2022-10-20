function openLink(link) {
    window.location.href = link;
}

function doRequest(method, url, data) {
    let request = new XMLHttpRequest();
    let baseUrl = 'http://localhost:8080';

    url = baseUrl + url;

    if (data !== undefined && data !== null) {
        url += JSON.stringify(data);
    }
    request.open(method, url, true)
    request.send();

    request.onload = () => {
        console.log(JSON.parse(request.response));
    }
}