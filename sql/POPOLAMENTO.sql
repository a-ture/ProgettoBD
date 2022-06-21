USE progetto;

INSERT INTO Cliente (Piva, NomeSocietà, Email, Via, NCivico, CAP)
	VALUES ('02346494673', 'Tutto Scuola', 'tuttoscuola@gmail.com', 'Via Marti di Ungheria', 80, 84018);
INSERT INTO Cliente (Piva, NomeSocietà, Email, Via, NCivico, CAP)
	VALUES ('05643873928', 'AZ Ufficio', 'azufficio@libro.it', 'Via Sacra', 123, 80045);
INSERT INTO Cliente (Piva, NomeSocietà, Email, Via, NCivico, CAP)
	VALUES ('04837294873', 'Zio Savino', 'ziosavino@hotmail.com', 'Via Sacra', 205, 80045);
INSERT INTO Cliente (Piva, NomeSocietà, Email, Via, NCivico, CAP)
	VALUES ('03582957482', 'La Girandola', 'lagirandola@gmail.com', 'Via Traversa', 68, 83013);
INSERT INTO Cliente (Piva, NomeSocietà, Email, Via, NCivico, CAP)
	VALUES ('09385928475', 'Punto Contabile', 'puntocantabile@hotmail.com', 'Piazza Garibaldi', 5, 83100);
INSERT INTO Cliente (Piva, NomeSocietà, Email, Via, NCivico, CAP)
	VALUES ('01847395847', 'Pizzeria da Carminuccio', 'dacarminuccio@gmail.com', 'Via Pietro Conte', 8, 84018);
INSERT INTO Cliente (Piva, NomeSocietà, Email, Via, NCivico, CAP)
	VALUES ('03958574837', 'Lavanda e Vaniglia', 'lavandaevaniglia@mail.com', 'Via Traversa', 51, 83013);
INSERT INTO Cliente (Piva, NomeSocietà, Email, Via, NCivico, CAP)
	VALUES ('03859285748', 'Erboristeria', 'erboristeria@gmail.com', 'Via De Conciliis', 10, 83100);
INSERT INTO Cliente (Piva, NomeSocietà, Email, Via, NCivico, CAP)
	VALUES ('01254788689', 'Sole 365', 'sole365@libero.it', 'Via Roma', 88, 80100);
INSERT INTO Cliente (Piva, NomeSocietà, Email, Via, NCivico, CAP)
	VALUES ('07486456738', 'Pulcinella Da Ciro', 'pulcinella@daciro.com', 'Via Nicola Litto', 169, 83022);


INSERT INTO TelefonoCliente VALUES ('3478976743', '02346494673');
INSERT INTO TelefonoCliente VALUES ('3278507664', '05643873928');
INSERT INTO TelefonoCliente VALUES ('3485356377', '04837294873');
INSERT INTO TelefonoCliente VALUES ('3509747527', '03582957482');
INSERT INTO TelefonoCliente VALUES ('3337485560', '09385928475');
INSERT INTO TelefonoCliente VALUES ('3696372655', '01847395847');
INSERT INTO TelefonoCliente VALUES ('3398684676', '03958574837');
INSERT INTO TelefonoCliente VALUES ('3339856674', '03859285748');
INSERT INTO TelefonoCliente VALUES ('3556473685', '01254788689');
INSERT INTO TelefonoCliente VALUES ('3887586469', '07486456738');
INSERT INTO TelefonoCliente VALUES ('027469578', '09385928475');
INSERT INTO TelefonoCliente VALUES ('025476996', '01254788689');
INSERT INTO TelefonoCliente VALUES ('0816748668', '03582957482');
INSERT INTO TelefonoCliente VALUES ('082532132', '04837294873');
INSERT INTO TelefonoCliente VALUES ('0017584957', '01254788689');


INSERT INTO Fornitore VALUES ('03948759496', 'RCH', 'Via Garibaldi', 8, 84068);
INSERT INTO Fornitore VALUES ('03589656265', 'Emotiq', 'Piazza Duomo', 30, 20122);
INSERT INTO Fornitore VALUES ('04596896765', 'Olivetti', 'Via Dante', 9, 18462);
INSERT INTO Fornitore VALUES ('02654283764', 'Develop', 'Via Colombo', 11, 82537);
INSERT INTO Fornitore VALUES ('01385637342', 'Konica Minolta', 'Via Garibaldi', 8, 84068);
INSERT INTO Fornitore VALUES ('01293756482', 'Canon', 'Via Margherita', 1, 84068);


INSERT INTO TelefonoFornitore VALUES ('3346476489', '03948759496');
INSERT INTO TelefonoFornitore VALUES ('3336465645', '03589656265');
INSERT INTO TelefonoFornitore VALUES ('0275454867', '04596896765');
INSERT INTO TelefonoFornitore VALUES ('0825776576', '02654283764');
INSERT INTO TelefonoFornitore VALUES ('0267467775', '01385637342');
INSERT INTO TelefonoFornitore VALUES ('3278657796', '01293756482');
INSERT INTO TelefonoFornitore VALUES ('3484567667', '04596896765');
INSERT INTO TelefonoFornitore VALUES ('3645775567', '01385637342');
INSERT INTO TelefonoFornitore VALUES ('3336457788', '03948759496');
INSERT INTO TelefonoFornitore VALUES ('0286638565', '01293756482');


INSERT INTO Assistenza (Descrizione, Data, Tipo, idCliente)
	VALUES ('Inceppamento carta' , '2021-05-21', 'Riparazione Stampante', '02346494673');
INSERT INTO Assistenza (Descrizione, Data, Tipo, idCliente)
	VALUES ('Sostituzione tamburo' , '2021-04-25', 'Riparazione Stampante' , '02346494673');
INSERT INTO Assistenza (Descrizione, Data, Tipo, idCliente)
	VALUES ('Sostituzione stampante danneggiata' , '2021-06-03', 'Sostituzione Merce' , '02346494673');
INSERT INTO Assistenza (Descrizione, Data , Tipo, idCliente)
	VALUES ('Sostituzione memoria', '2021-01-10', 'Sostituzione DGFE', '07486456738');
INSERT INTO Assistenza (Descrizione, Data , Tipo, idCliente)
	VALUES ('Sostituzione memoria', '2021-02-15', 'Sostituzione DGFE', '01847395847');
INSERT INTO Assistenza (Descrizione, Data , Tipo, idCliente)
	VALUES ('Sostituzione memoria', '2021-03-15', 'Sostituzione DGFE', '01847395847');
INSERT INTO Assistenza (Descrizione, Data , Tipo, idCliente)
	VALUES ('Gruppo trasporto carta', '2021-02-18', 'Riparazione Stampante', '03582957482');
INSERT INTO Assistenza (Descrizione, Data , Tipo, idCliente)
	VALUES ('Sostituzione stampante danneggiata', '2021-03-20', 'Sostituzione merce', '03582957482');


INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2020-12-12', 'noleggio' , 'chiuso', '02346494673');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2020-11-15', 'noleggio' , 'chiuso', '05643873928');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2020-10-26', 'acquisto' , 'chiuso', '04837294873');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2020-12-01', 'noleggio' , 'chiuso', '03582957482');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2020-12-05', 'noleggio' , 'chiuso', '09385928475');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2021-01-15', 'acquisto' , 'in lavorazione', '01847395847');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2020-09-18', 'acquisto' , 'annullato', '03958574837');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2021-01-10', 'acquisto' , 'chiuso', '03859285748');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2020-09-01', 'acquisto' , 'annullato', '01254788689');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2020-12-10', 'acquisto' , 'chiuso', '07486456738');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2021-01-12', 'rinnovo noleggio' , 'in lavorazione', '02346494673');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2020-12-15', 'rinnovo noleggio' , 'chiuso', '05643873928');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2021-01-01', 'rinnovo noleggio' , 'in lavorazione', '03582957482');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2021-01-05', 'rinnovo noleggio' , 'in lavorazione', '09385928475');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2021-01-15', 'rinnovo noleggio' , 'in lavorazione', '05643873928');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2021-01-19', 'acquisto' , 'in lavorazione', '02346494673');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2021-01-18', 'acquisto' , 'in lavorazione', '05643873928');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2021-01-03', 'acquisto' , 'chiuso', '03582957482');
INSERT INTO OrdineCliente (Data, Tipo, Stato, idCliente)
	VALUES ('2021-01-19', 'acquisto' , 'in lavorazione', '09385928475');
 
 
INSERT INTO OrdineFornitore (Data, idFornitore)
	VALUES ('2020-08-19', '03948759496');
INSERT INTO OrdineFornitore (Data, idFornitore)
	VALUES ('2020-08-13', '03589656265');
INSERT INTO OrdineFornitore (Data, idFornitore)
	VALUES ('2020-08-17', '04596896765');
INSERT INTO OrdineFornitore (Data, idFornitore)
	VALUES ('2020-08-25', '02654283764');
INSERT INTO OrdineFornitore (Data, idFornitore)
	VALUES ('2020-08-30', '01385637342');
INSERT INTO OrdineFornitore (Data, idFornitore)
	VALUES ('2020-08-18', '01293756482');


INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2020-12-15', 'bonifico', 200);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2020-11-20', 'carta di credito', 100);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2020-12-14', 'bonifico', 200);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2020-12-20', 'carta di credito', 100);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2020-12-22', 'bonifico', 200);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2020-10-26', 'carta di credito', 517.30);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2021-01-14', 'bonifico', 320);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2020-12-10', 'carta di credito', 320);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2021-01-6', 'bonifico', 320);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2020-08-19', 'carta di credito', 2520);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2020-08-16', 'bonifico', 5600);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2020-08-19', 'bonifico', 17246.7);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2020-08-27', 'bonifico', 13500);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2020-09-01', 'bonifico', 12480);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2020-08-25', 'carta di credito', 7200);
INSERT INTO Pagamento (Data, Modalità, Importo)
	VALUES ('2021-01-10', 'contanti', 70);


INSERT INTO ProdottoContabile VALUES ('Form 200', 517.50, 30, 'Olivetti', 2020, 'registratore di cassa');
INSERT INTO ProdottoContabile VALUES('Nota RT', 320, 10, 'Emotiq', 2019, 'registratore di cassa');
INSERT INTO ProdottoContabile VALUES('XP60', 126, 20, 'RCH', 2019, 'display di cortesia');
INSERT INTO ProdottoContabile VALUES('MAV', 80, 30, 'Emotiq', 2019, 'scanner codice a barre');
INSERT INTO ProdottoContabile VALUES('LD', 57.39, 30, 'Olivetti', 2019, 'cassetto contanti');


INSERT INTO StampanteDigitale (Modello, CanoneFisso, NumeroPezzi, AnnoDiProduzione , AziendaProduttrice)
   VALUES('BizHub Pro C5501', 3120, 4, 2019, 'Konica Minolta');
INSERT INTO StampanteDigitale (Modello, CanoneFisso, NumeroPezzi, AnnoDiProduzione , AziendaProduttrice)
   VALUES('Ineo+', 1350, 10, 2019, 'Develop');
INSERT INTO StampanteDigitale (Modello, CanoneFisso, NumeroPezzi, AnnoDiProduzione , AziendaProduttrice)
   VALUES('Printop', 800, 9, 2019, 'Canon');
   
 
INSERT INTO FatturazioneCliente (idOrdineCliente) 
	VALUES (1); 
INSERT INTO FatturazioneCliente (idOrdineCliente) 
	VALUES (2);
INSERT INTO FatturazioneCliente VALUES (3, 1);
INSERT INTO FatturazioneCliente VALUES (4, 2);
INSERT INTO FatturazioneCliente VALUES (5, 3);
INSERT INTO FatturazioneCliente VALUES (6, 4);
INSERT INTO FatturazioneCliente VALUES (7, 5);
INSERT INTO FatturazioneCliente VALUES (8, 6);
INSERT INTO FatturazioneCliente VALUES (9, 7);
INSERT INTO FatturazioneCliente (idOrdineCliente) 
	VALUES (10);
INSERT INTO FatturazioneCliente VALUES (11, 8);
INSERT INTO FatturazioneCliente (idOrdineCliente) 
	VALUES (12);
INSERT INTO FatturazioneCliente VALUES (13, 9);
INSERT INTO FatturazioneCliente (idOrdineCliente) 
	VALUES (14);
INSERT INTO FatturazioneCliente (idOrdineCliente) 
	VALUES (15);
INSERT INTO FatturazioneCliente (idOrdineCliente) 
	VALUES (16);
INSERT INTO FatturazioneCliente (idOrdineCliente) 
	VALUES (17);
INSERT INTO FatturazioneCliente (idOrdineCliente) 
	VALUES (18);
INSERT INTO FatturazioneCliente (idOrdineCliente) 
	VALUES (19);


INSERT INTO FatturazioneFornitore VALUES (1, 10);
INSERT INTO FatturazioneFornitore VALUES (2, 11);
INSERT INTO FatturazioneFornitore VALUES (3, 12);
INSERT INTO FatturazioneFornitore VALUES (4, 13);
INSERT INTO FatturazioneFornitore VALUES (5, 14);
INSERT INTO FatturazioneFornitore VALUES (6, 15);

INSERT INTO FatturazioneAssistenza VALUES (1, 16);
INSERT INTO FatturazioneAssistenza (idAssistenza)
	VALUES (2);
INSERT INTO FatturazioneAssistenza (idAssistenza)
	VALUES (3);
INSERT INTO FatturazioneAssistenza (idAssistenza)
	VALUES (4);
INSERT INTO FatturazioneAssistenza (idAssistenza)
	VALUES (5);
INSERT INTO FatturazioneAssistenza (idAssistenza)
	VALUES (6);
INSERT INTO FatturazioneAssistenza (idAssistenza)
	VALUES (7);
INSERT INTO FatturazioneAssistenza (idAssistenza)
	VALUES (8);

INSERT INTO Forma (idStampanteDigitale, idOrdineCliente)
	VALUES ('BizHub Pro C5501', 4);
INSERT INTO Forma VALUES ('Printop', 5,2);
INSERT INTO Forma (idStampanteDigitale, idOrdineCliente)
	VALUES ('BizHub Pro C5501', 6);
INSERT INTO Forma VALUES ('Printop', 8, 2);
INSERT INTO Forma VALUES ('Ineo+', 9, 2);
INSERT INTO Forma (idStampanteDigitale, idOrdineCliente)
	VALUES ('Ineo+', 10);
INSERT INTO Forma (idStampanteDigitale, idOrdineCliente)
	VALUES ('BizHub Pro C5501', 12);
INSERT INTO Forma VALUES ('Printop', 13, 2);
INSERT INTO Forma VALUES ('Printop', 14, 2);

INSERT INTO Dettaglia VALUES ('Ineo+', 5, 10);
INSERT INTO Dettaglia VALUES ('BizHub Pro C5501', 6, 4);
INSERT INTO Dettaglia VALUES ('Printop', 3, 9);

INSERT INTO Compone VALUES ('Form 200', 2, 30);
INSERT INTO Compone VALUES ('Nota RT', 1, 10);
INSERT INTO Compone VALUES ('XP60', 4, 20);
INSERT INTO Compone VALUES ('MAV', 1, 30);
INSERT INTO Compone VALUES ('LD', 2, 30);

INSERT INTO Specifica (idProdottoContabile, idOrdineCliente)
	VALUES ('Form 200', 3);
INSERT INTO Specifica VALUES ('Form 200', 15, 2);
INSERT INTO Specifica VALUES ('MAV', 15, 2);
INSERT INTO Specifica (idProdottoContabile, idOrdineCliente)
	VALUES ('Nota RT', 2);
INSERT INTO Specifica (idProdottoContabile, idOrdineCliente)
	VALUES ('Nota RT', 13);
INSERT INTO Specifica VALUES ('Form 200', 1, 2);
INSERT INTO Specifica VALUES ('LD', 1, 2);
INSERT INTO Specifica (idProdottoContabile, idOrdineCliente)
	VALUES ('Nota RT', 7);
INSERT INTO Specifica (idProdottoContabile, idOrdineCliente)
	VALUES ('Nota RT', 18);
INSERT INTO Specifica (idProdottoContabile, idOrdineCliente)
	VALUES ('XP60', 18);
INSERT INTO Specifica (idProdottoContabile, idOrdineCliente)
	VALUES ('Form 200', 17);
INSERT INTO Specifica (idProdottoContabile, idOrdineCliente)
	VALUES ('Nota RT', 11);
INSERT INTO Specifica (idProdottoContabile, idOrdineCliente)
	VALUES ('Nota RT', 19);