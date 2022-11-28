function createTable(data, containerId, target) {
	// Bereits eine Tabelle vorhanden?
	if (document.getElementById(containerId).firstElementChild !== null) {
		const table = document.getElementsByTagName('TABLE')[0];
		
		table.remove();
		createTable(data, containerId, target);
		
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
		// Für jeden Eintrag neue Zeile erstellen
		for (let i = 0; i < dataCount; i++) {
			const dataRow = document.createElement('TR');
			const values = Object.values(data[i]);
   
			values
				.map((item) => {
					const td = document.createElement('TD');
					td.innerHTML = item.valueOf();
                    td.setAttribute('class', 'dataCell');
					dataRow.appendChild(td);
				});
			debugger;
			dataRow.setAttribute('id', `${i}`);
			
			tableHead.appendChild(headerRow);
			tableBody.appendChild(dataRow);
		}
		
		tableContainer.appendChild(table);
	}
	
	if (target !== undefined) {
		let allRows = document.getElementsByTagName('TR');
		
		for (let i = 0; i < allRows.length; i++) {
			allRows[i].addEventListener('click', () => {
				if (target === "neuer-kunde") {
					window.location.href = `/neuer-kunde?neu=einsehen
					&kunnr=${data[i - 1].kundenNr}
					&anrede=${data[i - 1].anrede}
					&name=${data[i - 1].name}
					&vorname=${data[i - 1].vorname}
					&geburtsdatum=${data[i - 1].geburtsdatum}
					&plz=${data[i - 1].plz}
					&strasse=${data[i - 1].strasse}
					&hausnr=${data[i - 1].hausNr}
					&mail=${data[i - 1].email}
					&tel=${data[i - 1].telefonNr}
					&handy=${data[i - 1].handy}
					&kknr=${data[i - 1].krankenkassenNr}
					&vsnr=${data[i - 1].versicherungsNr}
					&gueltigkeit=${data[i - 1].gueltigkeit}
					&bemerkung=${data[i - 1].bemerkung}`;
				}
				
				if (target === "neuer-auftrag") {
					window.location.href = "/"; // Weiterleiten zu neuer-auftrag
				}
			});
		}
	}
}