DROP DATABASE IF EXISTS progetto;
CREATE DATABASE progetto;  

DROP USER IF EXISTS 'raffaella'@'localhost';
CREATE USER 'raffaella'@'localhost' IDENTIFIED BY 'raffaella';
GRANT ALL ON progetto.* TO 'raffaella'@'localhost';

USE progetto;

CREATE TABLE Cliente (
    PIVA CHAR(11) PRIMARY KEY,
    NomeSocietà VARCHAR(30) NOT NULL UNIQUE,
    Email VARCHAR(30) NOT NULL UNIQUE,
    Via VARCHAR(30) NOT NULL,
    NCivico SMALLINT NOT NULL,
    CAP MEDIUMINT(5) ZEROFILL NOT NULL,
    NumeroAssistenzeRichieste TINYINT UNSIGNED NULL DEFAULT 0,
    CHECK (CAP >= 10 AND Cap <= 97100)
)  ENGINE=INNODB;

       

CREATE TABLE TelefonoCliente (
    NumeroTelefono CHAR(10) PRIMARY KEY,
    idCliente CHAR(11) DEFAULT NULL,
    FOREIGN KEY (idCliente)
        REFERENCES Cliente (PIVA)
        ON DELETE CASCADE ON UPDATE CASCADE
)  ENGINE=INNODB;

 

CREATE TABLE Fornitore (
    PIVA CHAR(11) PRIMARY KEY,
    NomeFornitore CHAR(30) NOT NULL UNIQUE,
    Via VARCHAR(15),
    NCivico SMALLINT,
    CAP MEDIUMINT(5) ZEROFILL,
    CHECK (CAP >= 10 AND Cap <= 97100)
)  ENGINE=INNODB;

 

CREATE TABLE TelefonoFornitore (
    NumeroTelefono CHAR(10) PRIMARY KEY,
    idFornitore CHAR(11) NOT NULL,
    FOREIGN KEY (idFornitore)
        REFERENCES Fornitore (PIVA)
        ON DELETE CASCADE ON UPDATE CASCADE
)  ENGINE=INNODB;

 

CREATE TABLE Assistenza (
    NumeroChiamata SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    Descrizione TINYTEXT,
    Data DATE NOT NULL,
    Tipo ENUM('Sostituzione DGFE', 'Sostituzione Merce', 'Riparazione Stampante'),
    idCliente CHAR(11),
    FOREIGN KEY (idCliente)
        REFERENCES Cliente (PIVA)
        ON DELETE SET NULL ON UPDATE CASCADE
)  ENGINE=INNODB;


CREATE TABLE OrdineCliente (
    Codice SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    Data DATE NOT NULL,
    Tipo ENUM('acquisto', 'noleggio', 'rinnovo noleggio') NOT NULL,
    Stato ENUM('in lavorazione', 'chiuso', 'annullato', 'aperto') NOT NULL,
    idCliente CHAR(11) NOT NULL,
    FOREIGN KEY (idCliente)
        REFERENCES Cliente (PIVA)
        ON DELETE CASCADE ON UPDATE CASCADE
)  ENGINE=INNODB;

CREATE TABLE Pagamento (
    ID SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    Data DATE NOT NULL,
    Modalità ENUM('carta di credito', 'bonifico', 'contanti') NOT NULL,
    Importo FLOAT(10 , 2 ) NOT NULL,
    CHECK (Importo > 0)
)  ENGINE=INNODB;

CREATE TABLE OrdineFornitore (
    Codice SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    Data DATE NOT NULL,
    idFornitore CHAR(11) NOT NULL,
    FOREIGN KEY (idFornitore)
        REFERENCES Fornitore (PIVA)
)  ENGINE=INNODB;

CREATE TABLE ProdottoContabile (
    Modello VARCHAR(15) PRIMARY KEY,
    Prezzo FLOAT(6 , 2 ) NOT NULL,
    NumeroPezzi SMALLINT UNSIGNED NULL DEFAULT 1,
    AziendaProduttrice CHAR(20),
    AnnoDiProduzione YEAR,
    Tipologia ENUM('registratore di cassa', 'scanner codice a barre', 'cassetto contanti', 'display di cortesia'),
    CHECK (Prezzo > 0)
)  ENGINE=INNODB;

CREATE TABLE Specifica (
    idProdottoContabile CHAR(10),
    idOrdineCliente SMALLINT UNSIGNED,
    Quantità SMALLINT UNSIGNED DEFAULT 1,
    FOREIGN KEY (idProdottoContabile)
        REFERENCES ProdottoContabile (Modello)
        ON DELETE SET NULL ON UPDATE SET NULL,
    FOREIGN KEY (idOrdineCliente)
        REFERENCES OrdineCliente (Codice)
        ON DELETE SET NULL ON UPDATE SET NULL,
    CHECK (Quantità > 0)
)  ENGINE=INNODB;

         
CREATE TABLE StampanteDigitale (
    Modello VARCHAR(25) NOT NULL PRIMARY KEY,
    CanoneFisso FLOAT(6 , 2 ) NOT NULL,
    Disponibile BOOLEAN NOT NULL DEFAULT TRUE,
    NumeroPezzi SMALLINT NOT NULL,
    FormatoDiStampaMax CHAR(3) NOT NULL DEFAULT 'A3',
    AnnoDiProduzione YEAR,
    AziendaProduttrice VARCHAR(25),
    CHECK (CanoneFisso > 0)
)  ENGINE=INNODB;


CREATE TABLE FatturazioneCliente (
    idOrdineCliente SMALLINT UNSIGNED,
    idPagamento SMALLINT UNSIGNED,
    FOREIGN KEY (idOrdineCliente)
        REFERENCES OrdineCliente (Codice)
        ON DELETE SET NULL ON UPDATE SET NULL,
    FOREIGN KEY (idPagamento)
        REFERENCES Pagamento (ID)
        ON DELETE SET NULL ON UPDATE SET NULL
)  ENGINE=INNODB;



CREATE TABLE FatturazioneFornitore (
    idOrdineFornitore SMALLINT UNSIGNED,
    idPagamento SMALLINT UNSIGNED,
    FOREIGN KEY (idOrdineFornitore)
        REFERENCES OrdineFornitore (Codice)
        ON DELETE SET NULL ON UPDATE SET NULL,
    FOREIGN KEY (idPagamento)
        REFERENCES Pagamento (ID)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB;


CREATE TABLE FatturazioneAssistenza (
    idAssistenza SMALLINT UNSIGNED,
    idPagamento SMALLINT UNSIGNED,
    FOREIGN KEY (idAssistenza)
        REFERENCES Assistenza (NumeroChiamata)
        ON DELETE SET NULL ON UPDATE SET NULL,
    FOREIGN KEY (idPagamento)
        REFERENCES Pagamento (ID)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB;


CREATE TABLE Forma (
    idStampanteDigitale VARCHAR(25),
    idOrdineCliente SMALLINT UNSIGNED,
    Quantità SMALLINT UNSIGNED DEFAULT 1,
    FOREIGN KEY (idstampanteDigitale)
        REFERENCES stampantedigitale (modello)
        ON DELETE SET NULL ON UPDATE SET NULL,
    FOREIGN KEY (idOrdineCliente)
        REFERENCES OrdineCliente (Codice)
        ON DELETE SET NULL ON UPDATE SET NULL,
    CHECK (Quantità > 0)
)  ENGINE=INNODB;

       
CREATE TABLE Dettaglia (
    idStampanteDigitale VARCHAR(25),
    idOrdineFornitore SMALLINT UNSIGNED,
    Quantità SMALLINT UNSIGNED DEFAULT 1,
    FOREIGN KEY (idstampanteDigitale)
        REFERENCES stampantedigitale (modello)
         ON DELETE SET NULL ON UPDATE SET NULL,
    FOREIGN KEY (idOrdineFornitore)
        REFERENCES OrdineFornitore (Codice)
        ON DELETE SET NULL ON UPDATE SET NULL,
    CHECK (Quantità > 0)
)  ENGINE=INNODB;

CREATE TABLE Compone (
    idProdottoContabile CHAR(10),
    idOrdineFornitore SMALLINT UNSIGNED,
    Quantità SMALLINT UNSIGNED DEFAULT 1,
    FOREIGN KEY (idProdottoContabile)
        REFERENCES ProdottoContabile (Modello)
        ON DELETE SET NULL ON UPDATE SET NULL,
    FOREIGN KEY (idOrdineFornitore)
        REFERENCES OrdineFornitore (Codice)
        ON DELETE SET NULL ON UPDATE SET NULL,
    CHECK (Quantità > 0)
)  ENGINE=INNODB; 