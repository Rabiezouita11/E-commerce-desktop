-- --------------------------------------------------------
-- Hôte:                         127.0.0.1
-- Version du serveur:           5.7.33 - MySQL Community Server (GPL)
-- SE du serveur:                Win64
-- HeidiSQL Version:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Listage de la structure de la base pour elif
DROP DATABASE IF EXISTS `elif`;
CREATE DATABASE IF NOT EXISTS `elif` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `elif`;

-- Listage de la structure de la table elif. adresse
DROP TABLE IF EXISTS `adresse`;
CREATE TABLE IF NOT EXISTS `adresse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code_postale` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `rue` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `numero_boite_lettre` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ville` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `id_user` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.adresse : ~0 rows (environ)
/*!40000 ALTER TABLE `adresse` DISABLE KEYS */;
INSERT INTO `adresse` (`id`, `code_postale`, `rue`, `numero_boite_lettre`, `ville`, `id_user`) VALUES
	(1, '7130', 'sanwber n 14', '20', 'kef', 32);
/*!40000 ALTER TABLE `adresse` ENABLE KEYS */;

-- Listage de la structure de la table elif. agresseurs
DROP TABLE IF EXISTS `agresseurs`;
CREATE TABLE IF NOT EXISTS `agresseurs` (
  `id` bigint(20) unsigned NOT NULL,
  `fiche_id` bigint(20) unsigned NOT NULL,
  `first_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `age` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nationality` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `profession` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `level_instruction` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `relation` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` text COLLATE utf8mb4_unicode_ci,
  `state` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Listage des données de la table elif.agresseurs : ~0 rows (environ)
/*!40000 ALTER TABLE `agresseurs` DISABLE KEYS */;
/*!40000 ALTER TABLE `agresseurs` ENABLE KEYS */;

-- Listage de la structure de la table elif. avis
DROP TABLE IF EXISTS `avis`;
CREATE TABLE IF NOT EXISTS `avis` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) DEFAULT NULL,
  `message` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.avis : ~0 rows (environ)
/*!40000 ALTER TABLE `avis` DISABLE KEYS */;
INSERT INTO `avis` (`id`, `id_user`, `message`) VALUES
	(1, 32, 'application excellente');
/*!40000 ALTER TABLE `avis` ENABLE KEYS */;

-- Listage de la structure de la table elif. besoin_victimes
DROP TABLE IF EXISTS `besoin_victimes`;
CREATE TABLE IF NOT EXISTS `besoin_victimes` (
  `id` bigint(20) unsigned NOT NULL,
  `fiche_id` bigint(20) unsigned NOT NULL,
  `medical_follow` tinyint(1) DEFAULT NULL,
  `social_follow` tinyint(1) DEFAULT NULL,
  `psychological_follow` tinyint(1) DEFAULT NULL,
  `legal_follow` tinyint(1) DEFAULT NULL,
  `other_needs` longtext COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Listage des données de la table elif.besoin_victimes : ~0 rows (environ)
/*!40000 ALTER TABLE `besoin_victimes` DISABLE KEYS */;
/*!40000 ALTER TABLE `besoin_victimes` ENABLE KEYS */;

-- Listage de la structure de la table elif. categories
DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.categories : ~6 rows (environ)
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` (`id`, `name`) VALUES
	(29, 'Smartphones'),
	(30, 'Tablettes'),
	(31, 'ACCESSOIRES TÉLÉPHONIE'),
	(32, 'SMARTPHONES PREMIUM'),
	(33, 'TOP MARQUE'),
	(34, 'Stockage de données');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;

-- Listage de la structure de la table elif. commandes
DROP TABLE IF EXISTS `commandes`;
CREATE TABLE IF NOT EXISTS `commandes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Produit` varchar(10000) COLLATE utf8_bin DEFAULT NULL,
  `total_prix` int(255) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `Ville` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `etat_commande` varchar(50) COLLATE utf8_bin DEFAULT 'En cours',
  `id_livreur` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `date_livraison` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.commandes : ~0 rows (environ)
/*!40000 ALTER TABLE `commandes` DISABLE KEYS */;
INSERT INTO `commandes` (`id`, `Produit`, `total_prix`, `id_user`, `Ville`, `created`, `etat_commande`, `id_livreur`, `date_livraison`) VALUES
	(1, '[Samsung Galaxy A22*2]', 1800, 32, 'kef', '2022-09-21 05:30:31', 'valider', NULL, NULL);
/*!40000 ALTER TABLE `commandes` ENABLE KEYS */;

-- Listage de la structure de la table elif. contacts
DROP TABLE IF EXISTS `contacts`;
CREATE TABLE IF NOT EXISTS `contacts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `message` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.contacts : ~0 rows (environ)
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` (`id`, `nom`, `email`, `message`) VALUES
	(1, 'ronaldo', 'rabiezouita82@gmail.com', 'help me ');
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;

-- Listage de la structure de la table elif. coupon
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE IF NOT EXISTS `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date_expiration` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `prix` int(11) NOT NULL DEFAULT '0',
  `id_user` int(11) DEFAULT NULL,
  `etat` varchar(50) COLLATE utf8_bin DEFAULT 'en cours',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.coupon : ~2 rows (environ)
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` (`id`, `date_expiration`, `prix`, `id_user`, `etat`) VALUES
	(2, '2022-09-28', 449, 32, 'en cours'),
	(3, '2022-09-28', 207, 33, 'en cours');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;

-- Listage de la structure de la table elif. historiques
DROP TABLE IF EXISTS `historiques`;
CREATE TABLE IF NOT EXISTS `historiques` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.historiques : ~0 rows (environ)
/*!40000 ALTER TABLE `historiques` DISABLE KEYS */;
/*!40000 ALTER TABLE `historiques` ENABLE KEYS */;

-- Listage de la structure de la table elif. livreurs
DROP TABLE IF EXISTS `livreurs`;
CREATE TABLE IF NOT EXISTS `livreurs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.livreurs : ~0 rows (environ)
/*!40000 ALTER TABLE `livreurs` DISABLE KEYS */;
/*!40000 ALTER TABLE `livreurs` ENABLE KEYS */;

-- Listage de la structure de la table elif. panier
DROP TABLE IF EXISTS `panier`;
CREATE TABLE IF NOT EXISTS `panier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom_produit` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `image` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `prix` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `description` longtext COLLATE utf8_bin,
  `categorie` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `quantite` int(11) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `id_produit` int(11) DEFAULT NULL,
  `newprix` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.panier : ~0 rows (environ)
/*!40000 ALTER TABLE `panier` DISABLE KEYS */;
INSERT INTO `panier` (`id`, `nom_produit`, `image`, `prix`, `description`, `categorie`, `quantite`, `id_user`, `id_produit`, `newprix`) VALUES
	(1, 'Evertek M20 ', 'uploids\\produit\\SAJERPSLVM.jpg', '219', 'Le Evertek M20 se décline avec un écran 5.45', 'Smartphones', 1, 32, 1, 219);
/*!40000 ALTER TABLE `panier` ENABLE KEYS */;

-- Listage de la structure de la table elif. produits
DROP TABLE IF EXISTS `produits`;
CREATE TABLE IF NOT EXISTS `produits` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `image` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `prix` int(11) DEFAULT NULL,
  `Description` longtext COLLATE utf8_bin,
  `categorie` int(11) DEFAULT NULL,
  `quantite` int(11) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `promotion` varchar(50) COLLATE utf8_bin DEFAULT 'false',
  `numberpromotion` int(11) DEFAULT NULL,
  `date_exp` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `prixold` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `Index 2` (`categorie`),
  CONSTRAINT `FK_produits_categories` FOREIGN KEY (`categorie`) REFERENCES `categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.produits : ~10 rows (environ)
/*!40000 ALTER TABLE `produits` DISABLE KEYS */;
INSERT INTO `produits` (`id`, `nom`, `image`, `prix`, `Description`, `categorie`, `quantite`, `created`, `promotion`, `numberpromotion`, `date_exp`, `prixold`) VALUES
	(1, 'Evertek M20 ', 'uploids\\produit\\SAJERPSLVM.jpg', 219, 'Le Evertek M20 se décline avec un écran 5.45', 29, 49, '2022-09-21 04:40:05', 'true', 5, '2022-09-22', 230),
	(2, 'XIAOMI Redmi 10C', 'uploids\\produit\\GXFBMJYLOL.jpg', 700, 'Le Nouveau Smartphone Xiaomi Redmi 10C Disponible Chez JUMIA Au Meilleur Prix Smartphone Xiaomi Tunisie Avec Un Design Époustouflant Pour Mise En Main Confortable. Redmi 10C Possède Un Écran Clair Et Lumineux De 6.71 Pouces Avec Une Résolutio', 29, 80, '2022-09-21 04:41:02', 'false', NULL, NULL, NULL),
	(3, 'Samsung Galaxy A13', 'uploids\\produit\\VECKOFNZUF.jpg', 350, 'Avec un appareil photo puissant, une durabilité Corning® Gorilla® Glass 5, une longue durée de vie de la batterie et un grand écran de 6,6 pouces, le Galaxy A13 offre toutes les fonctionnalités Galaxy ', 29, 50, '2022-09-21 04:42:01', 'true', 30, '2022-09-23', 500),
	(4, 'Samsung Galaxy A22', 'uploids\\produit\\BQSKEQHAMD.jpg', 900, 'Ecran : 6,4" ; Résolution :  720 x 1600 pixelsMémoire RAM :  6 Go', 29, 78, '2022-09-21 04:43:11', 'false', NULL, NULL, NULL),
	(5, 'Nokia Tablette ', 'uploids\\produit\\GOXJDLSRGX.jpg', 500, 'Ecran 10.4\'\' IPS LCD 2K - Résolution: 1200 x 2000 pixels ', 30, 100, '2022-09-21 04:49:28', 'false', NULL, NULL, NULL),
	(6, 'Vega Tablette', 'uploids\\produit\\ZDZDMINTUV.jpg', 200, 'tab A1 4G est Doté d’un écran 7’’ avec une résolution 1024x600', 30, 5, '2022-09-21 04:50:46', 'false', NULL, NULL, NULL),
	(7, 'Infinix Earphones', 'uploids\\produit\\MSYGNSLOJD.jpg', 100, 'Avec le XE20, jouer à des jeux ou regarder des films est maintenant plus agréable que jamais', 31, 50, '2022-09-21 04:52:44', 'false', NULL, NULL, NULL),
	(8, 'Adata Flash disque', 'uploids\\produit\\VWGUFLKHKO.jpg', 40, 'USB Adata UV250 - Capacité 64 Go - Interface USB 2.0 - Dimensions: 42.4 x 14.95 x 5.35 mm - Poids: 5.6 g - Couleur Silver', 34, 50, '2022-09-21 04:55:43', 'false', NULL, NULL, NULL),
	(9, 'Silicon Power ', 'uploids\\produit\\JTWPLEFWNY.jpg', 50, 'Flash Disque 128Go Capacité de Stockage : 128Go', 34, 10, '2022-09-21 04:57:16', 'false', NULL, NULL, NULL),
	(10, 'Samsung Galaxy S21', 'uploids\\produit\\KIVOIPCYMC.jpg', 2700000, 'Ecran : 6.4" , Dynamic AMOLED 2X ; Résolution 1080 x 2400 pixelsSystème : Android 12, One UI 4', 33, 5, '2022-09-21 04:59:28', 'true', 10, '2022-09-24', 3000000);
/*!40000 ALTER TABLE `produits` ENABLE KEYS */;

-- Listage de la structure de la table elif. quiz
DROP TABLE IF EXISTS `quiz`;
CREATE TABLE IF NOT EXISTS `quiz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titre_quiz` longtext COLLATE utf8_bin,
  `option1` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `option2` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `option3` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `bonne_reponse` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.quiz : ~4 rows (environ)
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
INSERT INTO `quiz` (`id`, `titre_quiz`, `option1`, `option2`, `option3`, `bonne_reponse`) VALUES
	(1, 'combien de ram  telphone a32 samsung', '10', '5', '6', '6'),
	(2, 'combien de Batterie  telphoneSamsung S22 Ultra ', '3,000mAh', '2,000mAh', '5,000mAh', '5,000mAh'),
	(3, 'combien stockage telphone Samsung A32', '128GO', '50GO', '130GO', '128GO'),
	(4, 'Est-ce que la facture est obligatoire en vente en distance?', ' Non, jamais', '  Je ne sais pas', 'Oui, toujours', 'Oui, toujours');
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;

-- Listage de la structure de la table elif. rate
DROP TABLE IF EXISTS `rate`;
CREATE TABLE IF NOT EXISTS `rate` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL DEFAULT '0',
  `id_produit` int(11) NOT NULL DEFAULT '0',
  `noter` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.rate : ~0 rows (environ)
/*!40000 ALTER TABLE `rate` DISABLE KEYS */;
INSERT INTO `rate` (`id`, `id_user`, `id_produit`, `noter`) VALUES
	(1, 32, 4, 3);
/*!40000 ALTER TABLE `rate` ENABLE KEYS */;

-- Listage de la structure de la table elif. reponse
DROP TABLE IF EXISTS `reponse`;
CREATE TABLE IF NOT EXISTS `reponse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reponse` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `id_quiz` int(11) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.reponse : ~3 rows (environ)
/*!40000 ALTER TABLE `reponse` DISABLE KEYS */;
INSERT INTO `reponse` (`id`, `reponse`, `id_quiz`, `id_user`) VALUES
	(1, '6', 1, 32),
	(2, 'Oui, toujours', 4, 32),
	(3, '6', 1, 33);
/*!40000 ALTER TABLE `reponse` ENABLE KEYS */;

-- Listage de la structure de la table elif. users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `prenom` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `mot_de_passe` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `cin` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `role` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `image` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `banier` varchar(50) COLLATE utf8_bin DEFAULT 'false',
  `date_supprimer_compte` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- Listage des données de la table elif.users : ~3 rows (environ)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `nom`, `prenom`, `mot_de_passe`, `cin`, `role`, `image`, `email`, `banier`, `date_supprimer_compte`, `code`) VALUES
	(29, 'rabie', 'zouita', '7c4a8d09ca3762af61e59520943dc26494f8941b', '13633840', 'Admin', 'uploids\\profile\\NCDQWPFDVK.jpg', 'rabie.zouita@esprit.tn', 'false', NULL, NULL),
	(32, 'ronaldo', 'zouita', '7c4a8d09ca3762af61e59520943dc26494f8941b', '14785236', 'Client', 'uploids\\profile\\VDCFKWYHVE.jpg', 'rabiezouita82@gmail.com', 'false', '2022-10-06', ''),
	(33, 'wissem', 'samtouni', '7c4a8d09ca3762af61e59520943dc26494f8941b', '12547896', 'Client', 'uploids\\profile\\XVFWPCMMJG.jpg', 'wissem.samtouni@esprit.tn', 'false', NULL, '86875');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
