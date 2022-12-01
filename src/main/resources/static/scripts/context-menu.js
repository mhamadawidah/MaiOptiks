function startContextMenu() {
    const contextMenuLinkClassName = 'contextMenuLink';
    const menu = document.querySelector('#contextMenu');
    const contextMenuActive = 'contextMenu--active';
    const taskItemClassName = 'dataCell';
    let menuState = 0;
    let clickCoords;
    let clickCoordsX;
    let clickCoordsY;
    let menuWidth;
    let menuHeight;
    let windowWidth;
    let windowHeight;
    let taskItemInContext;
    var changeLink;
    var orderLink;

    function init() {
        clickListener();
        keyListener();
        resizeListener();
        scrollListener();
        debugger;
        //contextMenuListener();
    }

// Rechtsklickevent & Standardmenü verhindern
   // function contextMenuListener() {
        document.addEventListener('contextmenu', (evt) => {
            taskItemInContext = clickInsideElement(evt, taskItemClassName);

            if (taskItemInContext) {
                evt.preventDefault();
                toggleMenuOn();
                positionMenu(evt);
                 return fillData(taskItemInContext.parentNode);
            } else {
                taskItemInContext = null;
                toggleMenuOff();
            }
        });
    //}

// Menü anzeigen
    function toggleMenuOn() {
        if (menuState !== 1) {
            menuState = 1;
            menu.classList.add(contextMenuActive);
        }
    }

// Menü ausblenden
    function toggleMenuOff() {
        if (menuState !== 0) {
            menuState = 0;
            menu.classList.remove(contextMenuActive);
        }
    }

// Klick außerhalb der Tabelle?
    function clickInsideElement(elm, className) {
        elm = elm.target;

        if (elm.classList.contains(className)) {
            return elm;
        } else {
            while (elm === elm.parentNode) {
                if (elm.classList && elm.classList.contains(className)) {
                    return elm;
                }
            }
        }

        return false;
    }

// Kontext Menu an Position des Cursors verschieben
    function positionMenu(evt) {
        clickCoords = getPosition(evt);
        clickCoordsX = clickCoords.x + 'px';
        clickCoordsY = clickCoords.y + 'px';

        menu.style.left = clickCoordsX;
        menu.style.top = clickCoordsY;

        menuWidth = menu.offsetWidth;
        menuHeight = menu.offsetHeight;

        windowWidth = window.innerWidth;
        windowHeight = window.innerHeight;

        if ((windowWidth - clickCoordsX) < menuWidth) {
            menu.style.left = windowWidth - menuWidth + 'px';
        } else {
            menu.style.left = clickCoordsX + 'px';
        }

        if ((windowHeight - clickCoordsY) < menuHeight) {
            menu.style.top = windowHeight - menuHeight + 'px';
        } else {
            menu.style.top = clickCoordsY + 'px';
        }
    }

// Position des Cursors beim Klick ermitteln
    function getPosition(evt) {
        let posX = 0;
        let posY = 0;

        if (!evt) evt = window.event;

        if (evt.pageX || evt.pageY) {
            posX = evt.pageX;
            posY = evt.pageY;
        } else if (evt.clientX || evt.clientY) {
            posX = evt.clientX + document.body.scrollLeft + document.documentElement.scrollLeft;
            posY = evt.clientY + document.body.scrollTop + document.documentElement.scrollTop;
        }

        return {x: posX, y: posY};
    }

    function menuItemListener(link) {
        debugger;
        console.log('TaskId: ' + taskItemInContext.getAttribute('id')
            + 'Task action: ' + link.getAttribute('data-action'));
        toggleMenuOff();
    }

// Passende Daten/Aufträge im Link übergeben
    function fillData(node) {
        let kundenNr,
            anrede,
            name,
            vorname,
            strasse,
            hausNr,
            geburtsdatum,
            telefonNr,
            handy,
            email,
            versicherungsNr,
            gueltigkeit,
            bemerkung,
            plz,
            krankenkassenNr;

        for (let i = 0; i < node.children.length; i++) {
            console.log(node.children[i].innerHTML);
            switch (i) {
                case 0:
                    kundenNr = node.children[i].innerHTML;
                    break;
                case 1:
                    anrede = node.children[i].innerHTML;
                    break;
                case 2:
                    name = node.children[i].innerHTML;
                    break;
                case 3:
                    vorname = node.children[i].innerHTML;
                    break;
                case 4:
                    strasse = node.children[i].innerHTML;
                    break;
                case 5:
                    hausNr = node.children[i].innerHTML;
                    break;
                case 6:
                    geburtsdatum = node.children[i].innerHTML;
                    break;
                case 7:
                    telefonNr = node.children[i].innerHTML;
                    break;
                case 8:
                    handy = node.children[i].innerHTML;
                    break;
                case 9:
                    email = node.children[i].innerHTML;
                    break;
                case 10:
                    versicherungsNr = node.children[i].innerHTML;
                    break;
                case 11:
                    gueltigkeit = node.children[i].innerHTML;
                    break;
                case 12:
                    bemerkung = node.children[i].innerHTML;
                    break;
                case 13:
                    plz = node.children[i].innerHTML;
                    break;
                case 14:
                    krankenkassenNr = node.children[i].innerHTML;
            }
        }

        // Link für Bearbeiten
        changeLink = `/neuer-kunde?neu=bearbeiten
					&kunnr=${kundenNr}
					&anrede=${anrede}
					&name=${name}
					&vorname=${vorname}
					&geburtsdatum=${geburtsdatum}
					&plz=${plz}
					&strasse=${strasse}
					&hausnr=${hausNr}
					&mail=${email}
					&tel=${telefonNr}
					&handy=${handy}
					&kknr=${krankenkassenNr}
					&vsnr=${versicherungsNr}
					&gueltigkeit=${gueltigkeit}
					&bemerkung=${bemerkung}`;

        // Link für Aufträge
        orderLink = `/auftragsdatenverwaltung?v=s&kid=${kundenNr}&auto=1`;
    }

// Linksklick zum Deaktivieren
    function clickListener() {
        document.addEventListener('click', (evt) => {
            let clickElemIsLink = clickInsideElement(evt, contextMenuLinkClassName);

            if (clickElemIsLink) {
                evt.preventDefault();
                menuItemListener(clickElemIsLink);
            } else {
                let button = evt.button;

                if (button === 0) {
                    toggleMenuOff();
                }
            }

        });
    }

// Escape zum Deaktivieren
    function keyListener() {
        window.onkeyup = (evt) => {
            if (evt.key === 'Escape') {
                toggleMenuOff();
            }
        };
    }

// Fenstergröße verändert?
    function resizeListener() {
        window.onresize = () => {
            toggleMenuOff();
        }
    }

// Mausrad bedient?
    function scrollListener() {
        document.getElementsByClassName('table-container')[0].addEventListener('scroll', () => {
            toggleMenuOff();
        });
    }

    init();

    return {
        change: changeLink,
        order: orderLink
    };
}