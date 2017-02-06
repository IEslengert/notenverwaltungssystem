-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 29. Sep 2016 um 19:16
-- Server-Version: 10.1.13-MariaDB
-- PHP-Version: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `notenverwaltungssystem`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `benutzer`
--

CREATE TABLE `benutzer` (
  `benutzer_id` bigint(20) NOT NULL,
  `passwort` varchar(250) COLLATE utf8_bin NOT NULL,
  `login_name` varchar(250) COLLATE utf8_bin NOT NULL,
  `religion` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `erster_login` tinyint(1) NOT NULL DEFAULT '1',
  `profil_bild` longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `benutzer`
--

INSERT INTO `benutzer` (`benutzer_id`, `passwort`, `login_name`, `religion`, `erster_login`, `profil_bild`) VALUES
(1, '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin', '', 0, NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `fach`
--

CREATE TABLE `fach` (
  `fach_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(250) COLLATE utf8_bin NOT NULL,
  `unterrichtssprache` varchar(50) COLLATE utf8_bin DEFAULT 'Deutsch',
  `lehrer_id` bigint(20) DEFAULT NULL,
  `klasse_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `klasse`
--

CREATE TABLE `klasse` (
  `klasse_id` bigint(20) UNSIGNED NOT NULL,
  `jahr` int(11) NOT NULL,
  `jahrgangsstufe` varchar(100) COLLATE utf8_bin NOT NULL,
  `max_klassen_groesse` int(11) NOT NULL DEFAULT '30',
  `klassenraum` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ist_aktiv` tinyint(1) NOT NULL DEFAULT '1',
  `lehrer_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `klasse`
--

INSERT INTO `klasse` (`klasse_id`, `jahr`, `jahrgangsstufe`, `max_klassen_groesse`, `klassenraum`, `ist_aktiv`, `lehrer_id`) VALUES
(1, 2016, 'tetet', 30, '', 1, NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `klausur`
--

CREATE TABLE `klausur` (
  `klausur_id` bigint(20) UNSIGNED NOT NULL,
  `max_punkte` double DEFAULT NULL,
  `art_leistung` varchar(150) COLLATE utf8_bin DEFAULT NULL,
  `bemerkung` text COLLATE utf8_bin,
  `punkte` double DEFAULT NULL,
  `note` double NOT NULL,
  `gewichtung` double NOT NULL DEFAULT '1',
  `fach_id` bigint(20) NOT NULL,
  `schueler_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `konferenz`
--

CREATE TABLE `konferenz` (
  `konferenz_id` bigint(20) NOT NULL,
  `datum` date NOT NULL,
  `raum` varchar(50) COLLATE utf8_bin NOT NULL,
  `von_uhrzeit` varchar(20) COLLATE utf8_bin NOT NULL,
  `bis_uhrzeit` varchar(20) COLLATE utf8_bin NOT NULL,
  `nachricht` text COLLATE utf8_bin NOT NULL,
  `klasse_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `lehrer`
--

CREATE TABLE `lehrer` (
  `lehrer_id` bigint(20) NOT NULL,
  `personal_nr` varchar(250) COLLATE utf8_bin NOT NULL,
  `titel` varchar(50) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `lehrer`
--

INSERT INTO `lehrer` (`lehrer_id`, `personal_nr`, `titel`) VALUES
(1, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `person`
--

CREATE TABLE `person` (
  `person_id` bigint(20) UNSIGNED NOT NULL,
  `vorname` varchar(250) COLLATE utf8_bin NOT NULL,
  `nachname` varchar(250) COLLATE utf8_bin NOT NULL,
  `geburtstag` date NOT NULL,
  `anrede` varchar(50) COLLATE utf8_bin NOT NULL,
  `telefonnummer` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `strasse` varchar(250) COLLATE utf8_bin NOT NULL,
  `hausnummer` varchar(50) COLLATE utf8_bin NOT NULL,
  `ort` varchar(250) COLLATE utf8_bin NOT NULL,
  `plz` varchar(150) COLLATE utf8_bin NOT NULL,
  `email` varchar(250) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `person`
--

INSERT INTO `person` (`person_id`, `vorname`, `nachname`, `geburtstag`, `anrede`, `telefonnummer`, `strasse`, `hausnummer`, `ort`, `plz`, `email`) VALUES
(1, 'admin', 'admin', '2016-08-29', 'Herr', '', 'AdminStrasse', '10', 'AdminOrt', 'AdminPlz', 'AdminAdminAdmin@AdminAdminAdmin.AdminAdminAdmin');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `rolle`
--

CREATE TABLE `rolle` (
  `benutzer_id` bigint(20) NOT NULL,
  `rollen_name` varchar(100) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Daten für Tabelle `rolle`
--

INSERT INTO `rolle` (`benutzer_id`, `rollen_name`) VALUES
(1, 'Lehrer'),
(1, 'Schulleiter');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `schueler`
--

CREATE TABLE `schueler` (
  `schueler_id` bigint(20) NOT NULL,
  `besonderheit` text COLLATE utf8_bin,
  `letzte_einschreibung` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `schueler_fach`
--

CREATE TABLE `schueler_fach` (
  `schueler_id` bigint(20) NOT NULL,
  `fach_id` bigint(20) NOT NULL,
  `fach_notendurchschnitt` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `schueler_klasse`
--

CREATE TABLE `schueler_klasse` (
  `schueler_id` bigint(20) NOT NULL,
  `klasse_id` bigint(20) NOT NULL,
  `status` varchar(100) COLLATE utf8_bin NOT NULL,
  `bemerkung` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `ist_versetzungsgefaehrdet` tinyint(1) NOT NULL DEFAULT '0',
  `notendurchschnitt` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sorgeberechtigter`
--

CREATE TABLE `sorgeberechtigter` (
  `schueler_id` bigint(20) NOT NULL,
  `person_id` bigint(20) NOT NULL,
  `verwandtschaftsgrad` varchar(50) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `benutzer`
--
ALTER TABLE `benutzer`
  ADD PRIMARY KEY (`benutzer_id`),
  ADD UNIQUE KEY `login_name` (`login_name`);

--
-- Indizes für die Tabelle `fach`
--
ALTER TABLE `fach`
  ADD PRIMARY KEY (`fach_id`),
  ADD UNIQUE KEY `fach_id` (`fach_id`),
  ADD UNIQUE KEY `name und klassen_id` (`klasse_id`,`name`) USING BTREE;

--
-- Indizes für die Tabelle `klasse`
--
ALTER TABLE `klasse`
  ADD PRIMARY KEY (`klasse_id`),
  ADD UNIQUE KEY `klassen_id` (`klasse_id`),
  ADD UNIQUE KEY `jahr_jahrgangsstufe` (`jahr`,`jahrgangsstufe`) USING BTREE,
  ADD UNIQUE KEY `jahr_lehrer` (`jahr`,`lehrer_id`);

--
-- Indizes für die Tabelle `klausur`
--
ALTER TABLE `klausur`
  ADD PRIMARY KEY (`klausur_id`),
  ADD UNIQUE KEY `klausur_id` (`klausur_id`);

--
-- Indizes für die Tabelle `konferenz`
--
ALTER TABLE `konferenz`
  ADD PRIMARY KEY (`konferenz_id`);

--
-- Indizes für die Tabelle `lehrer`
--
ALTER TABLE `lehrer`
  ADD PRIMARY KEY (`lehrer_id`),
  ADD UNIQUE KEY `personal_nr` (`personal_nr`);

--
-- Indizes für die Tabelle `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`person_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indizes für die Tabelle `rolle`
--
ALTER TABLE `rolle`
  ADD PRIMARY KEY (`benutzer_id`,`rollen_name`);

--
-- Indizes für die Tabelle `schueler`
--
ALTER TABLE `schueler`
  ADD PRIMARY KEY (`schueler_id`);

--
-- Indizes für die Tabelle `schueler_fach`
--
ALTER TABLE `schueler_fach`
  ADD PRIMARY KEY (`schueler_id`,`fach_id`);

--
-- Indizes für die Tabelle `schueler_klasse`
--
ALTER TABLE `schueler_klasse`
  ADD PRIMARY KEY (`schueler_id`,`klasse_id`);

--
-- Indizes für die Tabelle `sorgeberechtigter`
--
ALTER TABLE `sorgeberechtigter`
  ADD PRIMARY KEY (`person_id`) USING BTREE,
  ADD UNIQUE KEY `schueler_id` (`schueler_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
