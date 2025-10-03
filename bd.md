CREATE DATABASE gerenciamento_jogadores;

USE gerenciamento_jogadores;

CREATE TABLE nacionalidades (
  iso2 char(2) NOT NULL,
  pais varchar(100) NOT NULL,
  gentilico_masc varchar(60) NOT NULL,
  gentilico_fem varchar(60) NOT NULL,
  PRIMARY KEY (iso2),
  UNIQUE KEY uq_pais (pais)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

INSERT INTO nacionalidades (iso2, pais, gentilico_masc, gentilico_fem) VALUES
('BR','Brasil','Brasileiro','Brasileira'),
('GB','Reino Unido','Britânico','Britânica'),
('EN','Inglaterra','Inglês','Inglesa'),
('SC','Escócia','Escocês','Escocesa'),
('ES','Espanha','Espanhol','Espanhola'),
('FR','França','Francês','Francesa'),
('NL','Países Baixos','Holandês','Holandesa'),
('NO','Noruega','Norueguês','Norueguesa'),
('PL','Polônia','Polonês','Polonesa'),
('JP','Japão','Japonês','Japonesa'),
('UA','Ucrânia','Ucraniano','Ucraniana'),
('GH','Gana','Ganês','Ganesa'),
('PT','Portugal','Português','Portuguesa'),
('IT','Itália','Italiano','Italiana'),
('DE','Alemanha','Alemão','Alemã'),
('AR','Argentina','Argentino','Argentina'),
('UY','Uruguai','Uruguaio','Uruguaia'),
('CL','Chile','Chileno','Chilena'),
('CO','Colômbia','Colombiano','Colombiana'),
('MX','México','Mexicano','Mexicana'),
('US','Estados Unidos','Americano','Americana'),
('CA','Canadá','Canadense','Canadense'),
('SE','Suécia','Sueco','Sueca'),
('DK','Dinamarca','Dinamarquês','Dinamarquesa'),
('FI','Finlândia','Finlandês','Finlandesa'),
('CH','Suíça','Suíço','Suíça'),
('AT','Áustria','Austríaco','Austríaca'),
('BE','Bélgica','Belga','Belga'),
('IE','Irlanda','Irlandês','Irlandesa'),
('CZ','Tchéquia','Tcheco','Tcheca'),
('HR','Croácia','Croata','Croata'),
('RS','Sérvia','Sérvio','Sérvia'),
('TR','Turquia','Turco','Turca'),
('MA','Marrocos','Marroquino','Marroquina'),
('CM','Camarões','Camaronês','Camaronensa'),
('NG','Nigéria','Nigeriano','Nigeriana'),
('SN','Senegal','Senegalês','Senegalesa'),
('CD','RD do Congo','Congolês','Congolesa'),
('EG','Egito','Egípcio','Egípcia'),
('KR','Coreia do Sul','Sul-coreano','Sul-coreana'),
('CN','China','Chinês','Chinesa'),
('RU','Rússia','Russo','Russa')
;

CREATE TABLE jogadores (
  id bigint NOT NULL AUTO_INCREMENT,
  nome varchar(255) NOT NULL,
  data_nascimento date NOT NULL,
  posicao varchar(255) NOT NULL,
  numero int NOT NULL,
  salario double NOT NULL,
  valor_mercado double NOT NULL,
  valor_contrato double NOT NULL,
  tempo_contrato int NOT NULL,
  categoria varchar(255) NOT NULL,
  nacionalidade_iso2 char(2) NOT NULL,
  PRIMARY KEY (id),
  KEY fk_jogadores_nacionalidade (nacionalidade_iso2),
  CONSTRAINT fk_jogadores_nacionalidade FOREIGN KEY (nacionalidade_iso2) REFERENCES nacionalidades (iso2)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

USE gerenciamento_jogadores;

INSERT INTO jogadores
  (nome, data_nascimento, posicao, numero, salario, valor_mercado, valor_contrato, tempo_contrato, categoria, nacionalidade_iso2)
VALUES
('Bukayo Saka',         '2001-09-05', 'Atacante',          7,  500000.00, 120000000.00, 50000000.00, 4, 'Principal', 'EN'),
('Martin Ødegaard',     '1998-12-17', 'Meia',              8,  600000.00, 100000000.00, 48000000.00, 4, 'Principal', 'NO'),
('Gabriel Jesus',       '1997-04-03', 'Atacante',          9,  550000.00,  65000000.00, 40000000.00, 3, 'Principal', 'BR'),
('Ethan Nwaneri',       '2007-03-21', 'Meia',             42,   20000.00,   5000000.00,  2000000.00, 3, 'Sub-23',   'EN'),
('Reuell Walters',      '2004-12-16', 'Defensor',         45,   30000.00,   4000000.00,  1500000.00, 2, 'Sub-23',   'EN'),
('Vivianne Miedema',    '1996-07-15', 'Atacante',         11,  100000.00,   2500000.00,  1200000.00, 2, 'Feminino', 'NL'),
('Kim Little',          '1990-06-29', 'Meia',             10,   90000.00,   1500000.00,  1000000.00, 1, 'Feminino', 'SC'),
('David Raya',          '1995-09-15', 'Goleiro',          22,  200000.00,  40000000.00, 20000000.00, 4, 'Principal', 'ES'),
('Aaron Ramsdale',      '1998-05-14', 'Goleiro',           1,  150000.00,  16000000.00,  8000000.00, 3, 'Principal', 'EN'),
('William Saliba',      '2001-03-24', 'Zagueiro Central',  2,  250000.00,  80000000.00, 40000000.00, 4, 'Principal', 'FR'),
('Gabriel Magalhães',   '1997-12-19', 'Zagueiro Central',  6,  300000.00,  75000000.00, 37500000.00, 4, 'Principal', 'BR'),
('Jakub Kiwior',        '2000-02-15', 'Zagueiro Central', 15,  100000.00,  28000000.00, 14000000.00, 4, 'Principal', 'PL'),
('Takehiro Tomiyasu',   '1998-11-05', 'Zagueiro Central', 18,  120000.00,  18000000.00,  9000000.00, 3, 'Principal', 'JP'),
('Ben White',           '1997-10-08', 'Lateral Direito',   4,  200000.00,  50000000.00, 25000000.00, 4, 'Principal', 'EN'),
('Oleksandr Zinchenko', '1996-12-15', 'Lateral Esquerdo', 35,  150000.00,  25000000.00, 12500000.00, 3, 'Principal', 'UA'),
('Kieran Tierney',      '1997-06-05', 'Lateral Esquerdo',  3,  100000.00,  20000000.00, 10000000.00, 3, 'Principal', 'SC'),
('Thomas Partey',       '1993-06-13', 'Volante',           5,  300000.00,  30000000.00, 15000000.00, 3, 'Principal', 'GH');

