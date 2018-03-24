SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `wypozyczalniaksiazek`
--
CREATE DATABASE wypozyczalniaksiazek;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `osoba`
--

CREATE TABLE `osoba` (
  `id_osoba` int(255),  
  `imie` varchar(50) NOT NULL,
  `nazwisko` varchar(50) NOT NULL,
  `pesel` varchar(50) NOT NULL UNIQUE,
  `login` varchar(50) NOT NULL UNIQUE,
  `haslo` varchar(500) NOT NULL
  
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `osoba` (`id_osoba`,`imie`,`nazwisko`,`pesel`,`login`,`haslo`) VALUES(1,'Adam','Nowak',020102030409,'adam','ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb');
INSERT INTO `osoba` (`id_osoba`,`imie`,`nazwisko`,`pesel`,`login`,`haslo`) VALUES(2,'Bartek','Kowalski',222222222222,'bartek','8254c329a92850f6d539dd376f4816ee2764517da5e0235514af433164480d7a');

--
-- Struktura tabeli dla tabeli `stanowisko`
--

CREATE TABLE `stanowisko` (
  `id_stanowisko` int(255),  
  `rodzaj` int(3) NOT NULL DEFAULT '2',
  `id_osoba` int(255) NOT NULL
   
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `stanowisko` (`id_stanowisko`,`rodzaj`,`id_osoba`) VALUES(1,1,1);
INSERT INTO `stanowisko` (`id_stanowisko`,`rodzaj`,`id_osoba`) VALUES(2,2,2);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `ksiazki`
--

CREATE TABLE `ksiazki` (
  `id_ksiazka` int(255) NOT NULL,
  `tytul` varchar(50) NOT NULL,
  `pisarz` varchar(50) NOT NULL,
  `ilosc_stron` int(255),
  `Cena` double(8,2) NOT NULL,
  `Stan_w_Magazynie` int(255) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into ksiazki values(1,'Pan Tadeusz','Adam Mickiewicz',200,20,5);
insert into ksiazki values(2,'Potop','Henryk Sienkiewicz',200,20,5);
insert into ksiazki values(3,'Władca Pierścieni','John Ronald Reuel Tolkien',200,20,5);
insert into ksiazki values(4,'Harry Potter','J.K. Rowling',200,20,5);


--
-- Struktura tabeli dla tabeli `wypozyczenia`
--


CREATE TABLE `wypozyczenia` (
  `id_wypozyczenie` int(255) NOT NULL,  
  `datawypozyczenia` date NOT NULL,      
  `zrealizowano` varchar(3) NOT NULL DEFAULT 'NIE',  
  `id_osoba` int(255) NOT NULL,
  `id_ksiazka` int(255) NOT NULL
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;





ALTER TABLE `osoba`
  ADD PRIMARY KEY (`id_osoba`);
  

  
ALTER TABLE `stanowisko`
  ADD PRIMARY KEY (`id_stanowisko`),
  ADD KEY `id_osoba` (`id_osoba`);
  

ALTER TABLE `ksiazki`
  ADD PRIMARY KEY (`id_ksiazka`);
  


ALTER TABLE `wypozyczenia`
  ADD PRIMARY KEY (`id_wypozyczenie`),  
  ADD KEY `id_osoba` (`id_osoba`),
   ADD KEY `id_ksiazka` (`id_ksiazka`);
  
    


ALTER TABLE `osoba`
  MODIFY `id_osoba` int(255) NOT NULL AUTO_INCREMENT;

ALTER TABLE `stanowisko`
  MODIFY `id_stanowisko` int(255) NOT NULL AUTO_INCREMENT;

ALTER TABLE `ksiazki`
  MODIFY `id_ksiazka` int(255) NOT NULL AUTO_INCREMENT;
  
ALTER TABLE `wypozyczenia`
  MODIFY `id_wypozyczenie` int(255) NOT NULL AUTO_INCREMENT;
  
  


  ALTER TABLE `stanowisko`
  ADD CONSTRAINT `osoba_ibfk` FOREIGN KEY (`id_osoba`) REFERENCES `osoba` (`id_osoba`);
  
  ALTER TABLE `wypozyczenia`
  ADD CONSTRAINT `wypozyczenia_ibfk` FOREIGN KEY (`id_osoba`) REFERENCES `osoba` (`id_osoba`);
  
  ALTER TABLE `wypozyczenia`
  ADD CONSTRAINT `wypozyczenia_ibfk_1` FOREIGN KEY (`id_ksiazka`) REFERENCES `ksiazki` (`id_ksiazka`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
