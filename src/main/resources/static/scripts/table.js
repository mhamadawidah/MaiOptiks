function createTable(data, containerId, link) {
    // Bereits eine Tabelle vorhanden?
    if (document.getElementById(containerId).firstElementChild !== null) {
        const table = document.getElementsByTagName('TABLE')[0];

        table.remove();
        createTable(data, containerId, link);

        return;
    } else {
        const tableContainer = document.getElementById(containerId);
        const table = document.createElement('TABLE');
        const tableHead = document.createElement('THEAD');
        const tableBody = document.createElement('TBODY');
        const dataCount = data.length;

        table.appendChild(tableHead);
        table.appendChild(tableBody);

        const headerRow = document.createElement('TR');
        const keys = Object.keys(data[0]);

        keys
            .map((item) => {
                const th = document.createElement('TH');
                let caps = item.charAt(0).toUpperCase() + item.slice(1);
                th.innerHTML = caps.valueOf();
                headerRow.appendChild(th);
            });
        // Für jeden Kunden neue Zeile erstellen
        for (let i = 0; i < dataCount; i++) {
            const dataRow = document.createElement('TR');
            const values = Object.values(data[i]);

            values
                .map((item) => {
                    const td = document.createElement('TD');
                    td.innerHTML = item.valueOf();
                    dataRow.appendChild(td);
                });

            tableHead.appendChild(headerRow);
            tableBody.appendChild(dataRow)
        }

        tableContainer.appendChild(table);
    }

    if (link !== undefined) {
        let allRows = document.getElementsByTagName('TR');

        for (let i = 0; i < allRows.length; i++) {
            allRows[i].addEventListener('click', () => {
                window.location.href = link;
            });
        }
    }
}
