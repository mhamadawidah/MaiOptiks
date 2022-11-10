CREATE TABLE IF NOT EXISTS Land (LandID varchar(254) NOT NULL, Name varchar(254), PRIMARY KEY (LandID));

CREATE TABLE IF NOT EXISTS Stadt (PLZ int NOT NULL, Ort varchar(254), Land varchar(254), PRIMARY KEY (PLZ), FOREIGN KEY (Land) REFERENCES Land(LandID));

CREATE TABLE IF NOT EXISTS Krankenkasse (KrankenkassenNr varchar(254) NOT NULL, Name varchar(254), Strasse varchar(254), PLZ int, TelefonNr varchar(254), Email varchar(254), PRIMARY KEY (KrankenkassenNr), FOREIGN KEY (PLZ) REFERENCES Stadt(PLZ));

CREATE TABLE IF NOT EXISTS Kunde (KundenNr int NOT NULL, Anrede varchar(254), Name varchar(254), Vorname varchar(254), Strasse varchar(254), HausNr varchar(254), PLZ Int, Geburtsdatum date, TelefonNr varchar(254), Handy varchar(254), EMail varchar(254), KrankenkassenNr varchar(254), VersicherungsNr varchar(254), Gueltigkeit date, Bemerkung varchar(254),PRIMARY KEY (KundenNr), FOREIGN KEY (PLZ) REFERENCES Stadt(PLZ), FOREIGN KEY (KrankenkassenNr) REFERENCES Krankenkasse(KrankenkassenNr));

CREATE TABLE IF NOT EXISTS Firmenstamm (AugenoptikerIKNr varchar(254) NOT NULL, Steuernummer varchar(254), Geschaeftsname varchar(254), Bankverbindung varchar(254), Strasse varchar(254), HausNr varchar(254), PLZ int, TelefonNr varchar(254), Inhabername varchar(254), Inhabervorname varchar(254), PRIMARY KEY (AugenoptikerIKNr), FOREIGN KEY (PLZ) REFERENCES Stadt(PLZ));

CREATE TABLE IF NOT EXISTS Mail (EMail varchar(254) NOT NULL, Benutzername varchar(254), Passwort varchar(254), SMTPServer varchar(254), SMTPPort int, SMTPAuthentifizierung bit, PRIMARY KEY (Email));

CREATE TABLE IF NOT EXISTS Abrechnungsart (ID int NOT NULL, Art varchar(254), PRIMARY KEY (ID));

CREATE TABLE IF NOT EXISTS Mitarbeiter (MitarbeiterNr int NOT NULL, Name varchar(254), Vorname varchar(254), Strasse varchar(254), HausNr varchar(254), PLZ int, TelefonNr varchar(254), Handy varchar(254), Email varchar(254), Geburtsdatum date, PRIMARY KEY (MitarbeiterNr), FOREIGN KEY (PLZ) REFERENCES Stadt(PLZ));

CREATE TABLE IF NOT EXISTS Arzt (ArztNr int NOT NULL, Name varchar(254), Vorname varchar(254), Strasse varchar(254), HausNr varchar(254), PLZ int, TelefonNr varchar(254), Handy varchar(254), Email varchar(254), PRIMARY KEY (ArztNr), FOREIGN KEY (PLZ) REFERENCES Stadt(PLZ));

CREATE TABLE IF NOT EXISTS Refraktion_durchgefuert (RefraktionsNr int NOT NULL, MitarbeiterNr int, ArztNr int, PRIMARY KEY (RefraktionsNr), FOREIGN KEY (MitarbeiterNr) REFERENCES Mitarbeiter (MitarbeiterNr), FOREIGN KEY (ArztNr) REFERENCES Arzt(ArztNr));

CREATE TABLE IF NOT EXISTS Auftrag (Auftragsnummer int NOT NULL, AbrechnungsID varchar(254), Rezepturvorhanden bit, Womit varchar(254), Wann varchar(254), Fertig bit, Abgeholt bit, Bezahlt bit, Auftragsbestaetigung varchar(254), Rechnung varchar(254), ErsteMahnung bit, ZweiteMahnung bit, DritteMahnung bit, KundenNr int, Datum date, Werkstatt int, Berater int, Refraktion int, PRIMARY KEY (Auftragsnummer), FOREIGN KEY (KundenNr) REFERENCES Kunde(KundenNr), FOREIGN KEY (Berater) REFERENCES Mitarbeiter(MitarbeiterNr), FOREIGN KEY (Werkstatt) REFERENCES Mitarbeiter(MitarbeiterNr), FOREIGN KEY (Refraktion) REFERENCES Refraktion_durchgefuert(RefraktionsNr),FOREIGN KEY (AbrechnungsID) REFERENCES Abrechnungsart(ID));

CREATE TABLE IF NOT EXISTS Farbe (FarbeID int NOT NULL, Bezeichnung varchar(254), Info varchar(254), PRIMARY KEY (FarbeID));

CREATE TABLE IF NOT EXISTS Lieferant (LieferantID int NOT NULL, Name varchar(254), Strasse varchar(254), PLZ int, TelefonNr varchar(254),  PRIMARY KEY (LieferantID), FOREIGN KEY (PLZ) REFERENCES Stadt(PLZ));

CREATE TABLE IF NOT EXISTS Material (MaterialID int NOT NULL, Bezeichung varchar(254), Info varchar(254), PRIMARY KEY (MaterialID));

CREATE TABLE IF NOT EXISTS Refraktion (RefraktionID int NOT NULL, Sph float, Cyl float, Ach int, Adds float, Pris float, Bas int, Visus float, PRIMARY KEY (RefraktionID));

CREATE TABLE IF NOT EXISTS Hornhaut (HornhautID int NOT NULL, RefraktionID int NOT NULL, HSIR float, A0R int, H2SHR float, A90R int, massalR float, tempR float, supR float, intR float, HSIL float, A0L int, HSHL float, A90L int, massalL float, tempL float , supL float, intL float, PRIMARY KEY(HornhautID), FOREIGN KEY (RefraktionID) REFERENCES Refraktion(RefraktionID));

CREATE TABLE IF NOT EXISTS Artikelart (ArtID int NOT NULL, Bezeichnung varchar(254), PRIMARY KEY (ArtID));

CREATE TABLE IF NOT EXISTS Artikel (ArtikelNr int NOT NULL, Bezeichnung varchar(254), Artikelart varchar(254), Material int, Farbe int, Einkaufspreis float, Verkaufspreis float, Lieferant int, PRIMARY KEY (ArtikelNr), FOREIGN KEY (Material) REFERENCES Material(MaterialID), FOREIGN KEY (Farbe) REFERENCES Farbe(FarbeID), FOREIGN KEY (Lieferant) REFERENCES Lieferant(LieferantID));

CREATE TABLE IF NOT EXISTS Glaeser (ArtikelNr int NOT NULL, Art int NOT NULL, Werte varchar(254), Material int, Farbe int, Einkaufspreis float, Verkaufspreis float, Lieferant int, PRIMARY KEY (ArtikelNr,Art), FOREIGN KEY (Art) REFERENCES Artikelart(ArtID),  FOREIGN KEY (Werte) REFERENCES Refraktion(RefraktionID), FOREIGN KEY (Material) REFERENCES Material(MaterialID), FOREIGN KEY (Farbe) REFERENCES Farbe(FarbeID), FOREIGN KEY (Lieferant) REFERENCES Lieferant(LieferantID));

CREATE TABLE IF NOT EXISTS Fassungen (ArtikelNr int NOT NULL, Art int NOT NULL, Material int, Farbe int, Einkaufspreis float, Verkaufspreis float, Lieferant int, PRIMARY KEY (ArtikelNr,Art), FOREIGN KEY (Art) REFERENCES Artikelart (ArtID), FOREIGN KEY (Material) REFERENCES Material(MaterialID), FOREIGN KEY (Farbe) REFERENCES Farbe(FarbeID), FOREIGN KEY (Lieferant) REFERENCES Lieferant(LieferantID));

CREATE TABLE IF NOT EXISTS Kontaktlinsen (ArtikelNr int NOT NULL, Art varchar(254) NOT NULL, Material int, Farbe int, Einkaufspreis float, Verkaufspreis float, Lieferant int, PRIMARY KEY (ArtikelNr,Art), FOREIGN KEY (Art) REFERENCES Artikelart(ArtID), FOREIGN KEY (Material) REFERENCES Material(MaterialID), FOREIGN KEY (Farbe) REFERENCES Farbe(FarbeID), FOREIGN KEY (Lieferant) REFERENCES Lieferant(LieferantID));

CREATE TABLE IF NOT EXISTS Brille (BrillenID int NOT NULL, GlasArtikelIDLinks int, GlasArtikelIDRechts int, FassungsArtikelID int, PRIMARY KEY (BrillenID), FOREIGN KEY (GlasArtikelIDLinks) REFERENCES Artikel (ArtikelNr), FOREIGN KEY (GlasArtikelIDRechts) REFERENCES Artikel(ArtikelNr), FOREIGN KEY (FassungsArtikelID) REFERENCES Artikel(ArtikelNr));

CREATE TABLE IF NOT EXISTS Auftragsartikel (AuftragsArtikelID int NOT NULL,AuftragsNr int, SehhilfeID int, SehhilfenArt int, PRIMARY KEY (AuftragsArtikelID), FOREIGN KEY (AuftragsNr) REFERENCES Auftrag(Auftragsnummer),FOREIGN KEY (SehhilfeID) REFERENCES Brille(BrillenID));
