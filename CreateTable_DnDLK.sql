
CREATE DATABASE IF NOT EXISTS DungeonsAndDragonsLoreKeeper DEFAULT CHARACTER SET utf8;
USE DungeonsAndDragonsLoreKeeper ;


CREATE TABLE IF NOT EXISTS Utente (
  idUtente INT NOT NULL,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(50) NOT NULL UNIQUE,
  userType TINYINT NOT NULL,
  password VARCHAR(64) NOT NULL,
  PRIMARY KEY (idUtente)
  ) ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS Campagna (
  idCampagna INT NOT NULL,
  nomeCampagna VARCHAR(50) NOT NULL UNIQUE,
  descrizione VARCHAR(200),
  numMaxGiocatori INT NOT NULL,
  contenutoDiario LONGTEXT,
  PRIMARY KEY (idCampagna),
  CHECK (numMaxGiocatori >= 2 AND numMaxGiocatori <= 10)
  ) ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS Partecipa (
  idCampagna INT NOT NULL,
  idUtente INT NOT NULL,
  PRIMARY KEY (idCampagna, idUtente),
  FOREIGN KEY (idCampagna) REFERENCES Campagna (idCampagna) ON DELETE NO ACTION ON UPDATE CASCADE,
  FOREIGN KEY (idUtente) REFERENCES Utente (idUtente) ON DELETE CASCADE ON UPDATE CASCADE
  ) ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS SchedaPersonaggio (
  idSchedaPersonaggio INT NOT NULL,
  nomeScheda VARCHAR(50) NOT NULL,
  contenutoScheda LONGTEXT NOT NULL,
  idCampagna INT NOT NULL,
  idUtente INT NOT NULL,
  PRIMARY KEY (idSchedaPersonaggio),
  FOREIGN KEY (idCampagna) REFERENCES Campagna (idCampagna) ON DELETE NO ACTION ON UPDATE CASCADE,
  FOREIGN KEY (idUtente) REFERENCES Utente (idUtente) ON DELETE CASCADE ON UPDATE CASCADE
  ) ENGINE = InnoDB;

CREATE USER 'utente' IDENTIFIED BY 'password';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE DungeonsAndDragonsLoreKeeper.* TO 'utente';

CREATE USER 'simoneowner' IDENTIFIED BY 'simoneowner';
GRANT ALL ON DungeonsAndDragonsLoreKeeper.* TO 'simoneowner';