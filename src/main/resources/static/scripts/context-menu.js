/*
Um das Kontextmenü zu nutzen, muss es auf eine bestimmte Weise aufgebaut werden.
Dies passiert im jeweiligen HTML Skript.
Dieses Skript muss wie gewohnt importiert werden:

<script th:src="@{/scripts/context-menu.js}" type="text/javascript"></script>

Aufbau:

<nav class="contextMenu" id="contextMenu">
    <ul class="contextMenuItems">
        <li class="contextMenuItem" id="1">
            <a class="contextMenuLink"
               data-action="change"
               href="#"><Menüpunkt1></a>
        </li>
        <hr/>
        <li class="contextMenuItem" id="2">
            <a class="contextMenuLink"
               data-action="orders"
               href="#"><Menüpunkt2></a>
        </li>
    </ul>
</nav>

Es können beliebig viele Menüpunkte ergänzt werden.
Die hinterlegten Links, welche aufgerufen werden müssen ggf. definiert werden (s. Funktion "fillData").
Hierbei ist der übergebene Parameter "node" die Zeile des Elements welches angeklickt wurde.
*/

function startContextMenu() {
    //// Helfer-Funktionen ////

    /**
     * Klick außerhalb der Tabelle?
     *
     * @param elm
     * @param className
     * @returns {{classList}|*|boolean}
     */
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

    /**
     * Position des Cursors beim Klick ermitteln
     *
     * @param evt
     * @returns {{x: number, y: number}}
     */
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

    //// Kern-Funktionen ////

    const contextMenuLinkClassName = 'contextMenuLink';
    const menu = document.querySelector('#contextMenu');
    const contextMenuActive = 'contextMenu--active';
    const taskItemClassName = 'dataCell';
    const menuItems = document.getElementsByClassName('contextMenuItem');
    const page = document.getElementsByClassName('heading')[0].innerHTML;
    let menuState = 0;
    let clickCoords;
    let clickCoordsX;
    let clickCoordsY;
    let menuWidth;
    let menuHeight;
    let windowWidth;
    let windowHeight;
    let taskItemInContext;
    let changeLink;
    let orderLink;

    /**
     * Start aller Kern-Funktionen
     */
    function init() {
        clickListener();
        keyListener();
        resizeListener();
        scrollListener();
        contextMenuListener();
    }

    /**
     * Rechtsklick-Event & Standardmenü verhindern
     */
    function contextMenuListener() {
        document.addEventListener('contextmenu', (evt) => {
            taskItemInContext = clickInsideElement(evt, taskItemClassName);

            if (taskItemInContext) {
                evt.preventDefault();
                toggleMenuOn();
                positionMenu(evt);
                fillData(taskItemInContext.parentNode);
            } else {
                taskItemInContext = null;
                toggleMenuOff();
            }
        });
    }

    /**
     * Linksklick zum Deaktivieren
     */
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

    /**
     * Escape zum Deaktivieren
     */
    function keyListener() {
        window.onkeyup = (evt) => {
            if (evt.key === 'Escape') {
                toggleMenuOff();
            }
        };
    }

    /**
     * Fenstergröße zum Deaktivieren
     */
    function resizeListener() {
        window.onresize = () => {
            toggleMenuOff();
        }
    }

    /**
     * Scrollen zum Deaktivieren
     */
    function scrollListener() {
        document.getElementsByClassName('table-container')[0].addEventListener('scroll', () => {
            toggleMenuOff();
        });
    }

    /**
     * Menü anzeigen
     */
    function toggleMenuOn() {
        if (menuState !== 1) {
            menuState = 1;
            menu.classList.add(contextMenuActive);
        }
    }

    /**
     * Menü ausblenden
     */
    function toggleMenuOff() {
        if (menuState !== 0) {
            menuState = 0;
            menu.classList.remove(contextMenuActive);
        }
    }

    /**
     * Kontext Menu an Position des Cursors verschieben
     *
     * @param evt
     */
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

    /**
     * Daten der jeweiligen Zeile erhalten
     *
     * @param link
     */
    function menuItemListener(link) {
       // console.log('TaskId: ' + taskItemInContext.getAttribute('id') // Helfer für Daten in Konsole
        //    + 'Task action: ' + link.getAttribute('data-action'));
        toggleMenuOff();
    }

    /**
     * Passende Daten im Link übergeben
     *
     * Elseif erstellen, falls neue Links ergänzt werden!
     *
     * @param node
     */
    function fillData(node) {
        if (page === 'Kunde') {
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

            // Link für Kunde bearbeiten
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

            // Link für Aufträge des Kunden
            orderLink = `/auftragsdatenverwaltung?v=s&kid=${kundenNr}&auto=1`;
        } /* elseif (page === '') {

             } */
    }

    /**
     * Anwendung starten
     */
    init();

    // Hier müssen die neu erstellten Links ergänzt werden
    if (page === 'Kunde') {
        menuItems[0].onclick = function() { window.location.href = changeLink; }; // Erster Menüpunkt
        menuItems[1].onclick = function() { window.location.href = orderLink; }; // Zweiter Menüpunkt
        // usw...
    }
}