function createTable(data, containerId, selectedCallback, minColumns) {
    // Bereits eine Tabelle vorhanden?
    let tableContainer;
    if (document.getElementById(containerId).firstElementChild !== null) {
        const table = document.getElementsByTagName('TABLE')[0];

        table.remove();
        createTable(data, containerId, selectedCallback);

        return;
    } else {
        tableContainer = document.getElementById(containerId);
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

        if (minColumns === undefined)
            minColumns = 0;

        let emptyData;
        if (data.length > 0) {
            emptyData = Object.values(data[0]);
            for (let i = 0; i < emptyData.length; i++) {
                emptyData[i] = '';
            }
        }

        // Für jeden Eintrag neue Zeile erstellen
        for (let i = 0; i < dataCount || i < minColumns; i++) {

            const dataRow = document.createElement('TR');
            let values = Object.values(emptyData);
            if (i < dataCount)
                values = Object.values(data[i]);

            values
                .map((item) => {
                    const td = document.createElement('TD');
                    if (item !== undefined && item !== null)
                        td.innerHTML = item.valueOf();
                    else td.innerHTML = '';
                    td.setAttribute('class', 'dataCell');
                    dataRow.appendChild(td);
                });

            dataRow.setAttribute('id', `${i}`);

            tableHead.appendChild(headerRow);
            tableBody.appendChild(dataRow);
        }

        tableContainer.appendChild(table);
    }

    if (selectedCallback !== undefined) {

        let allRows = tableContainer.getElementsByTagName('TR');

        for (let i = 1 /* 0 === header*/; i < allRows.length; i++) {
            allRows[i].addEventListener('click', () => {
                selectedCallback(data[i - 1]);
            });
        }
    }
}
