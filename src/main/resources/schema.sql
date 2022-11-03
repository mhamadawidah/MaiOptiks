CREATE TABLE IF NOT EXISTS Land (LandID varchar(254) NOT NULL, Name varchar(254), PRIMARY KEY (LandID));

CREATE TABLE IF NOT EXISTS Stadt (PLZ int NOT NULL, Ort varchar(254), Land varchar(254), PRIMARY KEY (PLZ), FOREIGN KEY (Land) REFERENCES Land(LandID));

CREATE TABLE IF NOT EXISTS Krankenkasse (KrankenkassenID varchar(254) NOT NULL, Name varchar(254), Strasse varchar(254), PLZ int, TelefonID varchar(254), Email varchar(254), PRIMARY KEY (KrankenkassenNr), FOREIGN KEY (PLZ) REFERENCES Stadt(PLZ));

CREATE TABLE IF NOT EXISTS Kunde (KundenID int NOT NULL, Anrede varchar(254), Name varchar(254), Vorname varchar(254), Strasse varchar(254), HausID varchar(254), PLZ Int, Geburtsdatum date, TelefonID varchar(254), Handy varchar(254), EMail varchar(254), KrankenkassenID varchar(254), VersicherungsID varchar(254), Gueltigkeit date, Bemerkung varchar(254),PRIMARY KEY (KundenNr), FOREIGN KEY (PLZ) REFERENCES Stadt(PLZ), FOREIGN KEY (KrankenkassenNr) REFERENCES Krankenkasse(KrankenkassenNr));

CREATE TABLE IF NOT EXISTS Firmenstamm (AugenoptikerIKID varchar(254) NOT NULL, Steuernummer varchar(254), Geschaeftsname varchar(254), Bankverbindung varchar(254), Strasse varchar(254), HausID varchar(254), PLZ int, TelefonID varchar(254), Inhabername varchar(254), Inhabervorname varchar(254), PRIMARY KEY (AugenoptikerIKNr), FOREIGN KEY (PLZ) REFERENCES Stadt(PLZ));

CREATE TABLE IF NOT EXISTS Mail (EMail varchar(254) NOT NULL, Benutzername varchar(254), Passwort varchar(254), SMTPServer varchar(254), SMTPPort int, SMTPAuthentifizierung bit, PRIMARY KEY (Email));

CREATE TABLE IF NOT EXISTS Abrechnungsart (AbrechnungsartID int NOT NULL, Art varchar(254), PRIMARY KEY (AbrechnungsartID));

CREATE TABLE IF NOT EXISTS Gattung (GattungID int NOT NULL, Bezeichnung varchar(254) NOT NULL, PRIMARY KEY (GattungID));

CREATE TABLE IF NOT EXISTS Refraktion (RefraktionID int NOT NULL, MitarbeiterID int, ArztID int, Sph float, Cyl float, Ach float, Adds float, Pris float, Bas float, Visus float, PRIMARY KEY (RefraktionID), FOREIGN KEY (MitarbeiterNr) REFERENCES Mitarbeiter(MitarbeiterNr), FOREIGN KEY (ArztNr) REFERENCES Arzt(ArztNr));

CREATE TABLE IF NOT EXISTS Mitarbeiter (MitarbeiterID int NOT NULL, Name varchar(254), Vorname varchar(254), Starsse varchar(254), HausID varchar(254), PLZ int, TelefonID varchar(254), Handy varchar(254), Email varchar(254), Geburtsdatum date, PRIMARY KEY (MitarbeiterNr), FOREIGN KEY (PLZ) REFERENCES Stadt(PLZ));

CREATE TABLE IF NOT EXISTS Arzt (ArztID int NOT NULL, Name varchar(254), Vorname varchar(254), Strasse varchar(254), HausID varchar(254), PLZ int, TelefonID varchar(254), Handy varchar(254), Email varchar(254), PRIMARY KEY (ArztNr), FOREIGN KEY (PLZ) REFERENCES Stadt(PLZ));

CREATE TABLE IF NOT EXISTS Auftrag (Auftragsnummer int NOT NULL, AbrechnungsartID varchar(254), Rezepturvorhanden bit, Womit varchar(254), Wann varchar(254), Fertig bit, Abgeholt bit, Bezahlt bit, Auftragsbestaetigung varchar(254), Rechnung varchar(254), Mahnung int, KundenID int, Datum date, Werkstatt int, Berater int, Refraktion int, PRIMARY KEY (Auftragsnummer), FOREIGN KEY (KundenNr) REFERENCES Kunde(KundenNr), FOREIGN KEY (Berater) REFERENCES Mitarbeiter(MitarbeiterNr), FOREIGN KEY (Werkstatt) REFERENCES Mitarbeiter(MitarbeiterNr), FOREIGN KEY (Refraktion) REFERENCES Refraktion(RefraktionsNr),FOREIGN KEY (AbrechnungsartID) REFERENCES Abrechnungsart(AbrechnungsartID));

CREATE TABLE IF NOT EXISTS Farbe (FarbeID int NOT NULL, Bezeichnung varchar(254), Info varchar(254), PRIMARY KEY (FarbeID));

CREATE TABLE IF NOT EXISTS Lieferant (LieferantID int NOT NULL, Name varchar(254), Strasse varchar(254), PLZ int, TelefonID varchar(254),  PRIMARY KEY (LieferantID), FOREIGN KEY (PLZ) REFERENCES Stadt(PLZ));

CREATE TABLE IF NOT EXISTS Material (MaterialID int NOT NULL, Bezeichung varchar(254), Info varchar(254), PRIMARY KEY (MaterialID));

CREATE TABLE IF NOT EXISTS Hornhaut (HornhautID int NOT NULL, RefraktionID int NOT NULL, HSIR float, A0R int, H2SHR float, A90R int, massalR float, tempR float, supR float, intR float, HSIL float, A0L int, HSHL float, A90L int, massalL float, tempL float , supL float, intL float, PRIMARY KEY(HornhautID), FOREIGN KEY (RefraktionID) REFERENCES Refraktion(RefraktionID));

CREATE TABLE IF NOT EXISTS Artikelart (ArtID int NOT NULL, Bezeichnung varchar(254), PRIMARY KEY (ArtID));

CREATE TABLE IF NOT EXISTS Artikel (ArtikelID int NOT NULL, Bezeichnung varchar(254), Artikelart varchar(254), Material int, Farbe int, Einkaufspreis float, Verkaufspreis float, Lieferant int, PRIMARY KEY (ArtikelNr), FOREIGN KEY (Material) REFERENCES Material(MaterialID), FOREIGN KEY (Farbe) REFERENCES Farbe(FarbeID), FOREIGN KEY (Lieferant) REFERENCES Lieferant(LieferantID));

CREATE TABLE IF NOT EXISTS Fassungen (ArtikelID int NOT NULL, GattungID int, Modell varchar(254), Groesse varchar(254), Buegel int, Faktor double, PRIMARY KEY (ArtikelNr), FOREIGN KEY (ArtikelID) REFERENCES Artikel(ArtikelID), FOREIGN KEY (GattungID) REFERENCES Gattung(GattungID));

CREATE TABLE IF NOT EXISTS Glaeser (ArtikelID int NOT NULL, Werte int, PRIMARY KEY (ArtikelNr), FOREIGN KEY (ArtikelID) REFERENCES Artikel(ArtikelID), FOREIGN KEY (Werte) REFERENCES Refraktion(RefraktionID));

CREATE TABLE IF NOT EXISTS Kontaktlinsen (ArtikelID int NOT NULL, Werte int, PRIMARY KEY (ArtikelNr), FOREIGN KEY (ArtikelID) REFERENCES Artikel(ArtikelID), FOREIGN KEY (Werte) REFERENCES Refraktion(RefraktionID));

CREATE TABLE IF NOT EXISTS Brille (BrillenID int NOT NULL, GlasArtikelIDLinks int, GlasArtikelIDRechts int, FassungsArtikelID int, PRIMARY KEY (BrillenID), FOREIGN KEY (GlasArtikelIDLinks) REFERENCES Artikel (ArtikelNr), FOREIGN KEY (GlasArtikelIDRechts) REFERENCES Artikel(ArtikelNr), FOREIGN KEY (FassungsArtikelID) REFERENCES Artikel(ArtikelNr));

CREATE TABLE IF NOT EXISTS Auftragsartikel (AuftragsArtikelID int NOT NULL,AuftragsID int, SehhilfeID int, SehhilfenArt int, PRIMARY KEY (AuftragsArtikelID), FOREIGN KEY (AuftragsNr) REFERENCES Auftrag(Auftragsnummer),FOREIGN KEY (SehhilfeID) REFERENCES Brille(BrillenID));
