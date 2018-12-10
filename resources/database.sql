-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  sam. 08 déc. 2018 à 08:59
-- Version du serveur :  5.7.23
-- Version de PHP :  7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `salle`
--
drop database if exists salle;
CREATE DATABASE IF NOT EXISTS `salle` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;

drop user if exists 'UtilisateurSalle'@'%';
CREATE USER 'UtilisateurSalle'@'%' IDENTIFIED by 'password';
GRANT USAGE ON *.* TO 'UtilisateurSalle'@'%';

USE `salle`;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `id_salle` int(11) NOT NULL,
  `mail_user` varchar(255) NOT NULL,
  `date_debut` datetime NOT NULL,
  `date_fin` datetime NOT NULL,
  PRIMARY KEY (`id_salle`,`date_debut`,`date_fin`),
  KEY `ForeignUser` (`mail_user`)
);

-- --------------------------------------------------------

--
-- Structure de la table `salle`
--

DROP TABLE IF EXISTS `salle`;
CREATE TABLE IF NOT EXISTS `salle` (
  `id_salle` int(11) NOT NULL AUTO_INCREMENT,
  `nom_salle` varchar(255) NOT NULL,
  PRIMARY KEY (`id_salle`),
  UNIQUE KEY `salle_nom_salle_uindex` (`nom_salle`)
);

--
-- Déchargement des données de la table `salle`
--

INSERT INTO `salle` (`id_salle`, `nom_salle`) VALUES
(1, 'Turing'),
(2, 'Pascale'),
(3, 'Boole'),
(4, 'Lovelace'),
(5, 'Shannon'),
(6, 'Von Neumann'),
(7, 'Chaine de prod'),
(8, 'S 228 229'),
(9, 'TP Systeme'),
(10, 'Unix A'),
(11, 'Unix B'),
(12, 'windows A'),
(13, 'windows B');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `mail_user` varchar(255) NOT NULL,
  `nom_user` varchar(255) DEFAULT NULL,
  `prenom_user` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mail_user`),
  UNIQUE KEY `USER_mail_user_uindex` (`mail_user`)
);

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`mail_user`, `nom_user`, `prenom_user`, `password`) VALUES
('kilian.paquier@hotmail.fr', 'Paquier', 'Kilian', 'c532800644dae46ad38ed2711c96676851e9b8d4b8b3dbc7bb89829d42f09313');
COMMIT;

GRANT select on salle.salle to 'UtilisateurSalle'@'%';
grant select on salle.user to 'UtilisateurSalle'@'%';
grant select, insert, update, delete on salle.reservation to 'UtilisateurSalle'@'%';
commit;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;