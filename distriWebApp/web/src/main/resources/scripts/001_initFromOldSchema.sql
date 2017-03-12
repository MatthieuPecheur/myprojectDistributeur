

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE IF NOT EXISTS `client` (
  `id` int(11) not null,
  `nom` varchar(255) not null,
  `prenom` varchar(255) not null,
  `age` int(11) not null,
  `code` int(11) not null,
  `credit` double not null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



-- --------------------------------------------------------

--
-- Structure de la table `cocktail`
--

CREATE TABLE IF NOT EXISTS `cocktail` (
  `id` int(11) not null,
  `intitule` varchar(255) default null,
  `prix` float default null,
  `color` int(100) default null,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `cocktail`
--

INSERT INTO `cocktail` (`id`, `intitule`, `prix`, `color`) VALUES
(0, '', 0, 00000),
(1, 'Tequila Sunrise', 3, 65280),
(2, 'Rinçage', 3, 16711680),
(3, 'After Sex', 3, 16758044),
(4, 'Canaria', 3, 16731932),
(5, 'Planteur', 3, 16775708),
(6, 'Cascade', 3, 1316095),
(7, 'Blue lagoon', 3, 1887487),
(8, 'Vodka-Redbul', 3, 15555680),
(9, 'Margot''sTripToTheUSA(sors avec !)', 3, 16718586);

-- --------------------------------------------------------

--
-- Structure de la table `pompe`
--

CREATE TABLE IF NOT EXISTS `pompe` (
  `id` int(16) NOT NULL,
  `debitmlperseconde` float NOT NULL,
  `marque` varchar(512) DEFAULT NULL,
  `typepompe` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `pompe`
--

INSERT INTO `pompe` (`id`, `debitmlperseconde`, `marque`, `typepompe`) VALUES
(1, 13, '', ''),
(2, 13, '', ''),
(3, 13, 'fghg', 'fgdhf'),
(4, 13, 'dfsgdf', 'dfgfd'),
(5, 11, 'fdhgfhf', 'gfhdhf'),
(6, 10, 'fhfg', 'gfhfd'),
(7, 8, 'rose', 'blooby'),
(8, 11, 'rose', 'blooby');


-- --------------------------------------------------------

--
-- Structure de la table `ingredient`
--

CREATE TABLE IF NOT EXISTS `ingredient` (
  `id` int(255) not null,
  `intitule` varchar(500) not null,
  `alcoolise` tinyint(1) not null,
  `degre_alcool` int(255) not null,
  `marque` varchar(500) not null,
  `quantite_pleine` int(255) default null,
  `quantite_restante` int(255) default null,
  `fk_pompe_id` int(11) default null,
  primary key (`id`),
  KEY `FK_INGREDIENT_POMPE` (`fk_pompe_id`),
  CONSTRAINT `FK_INGREDIENT_POMPE` FOREIGN KEY (`fk_pompe_id`) REFERENCES `pompe` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `ingredient`
--

INSERT INTO `ingredient` (`id`, `intitule`, `alcoolise`, `degre_alcool`, `marque`, `quantite_pleine`, `quantite_restante`, `fk_pompe_id`) VALUES
(0, '', 0, 0, '', 1000, 1000, 1),
(1, 'Vodka', 1, 40, 'Smirnoff', 1000, 1000, 1),
(2, 'Téquila', 1, 35, 'Bourdoff', 1000, 1000, 2),
(3, 'Rhum', 1, 30, 'Almeyda', 1000, 1000, 3),
(4, 'Curaçao', 1, 25, 'Bengladesh', 1000, 1000, 4),
(5, 'Jus d''orange', 0, 0, 'Minute Made', 1000, 1000, 5),
(6, 'Jus d''ananas', 0, 0, 'Minutes Maid', 1000, 1000, 6),
(7, 'Grenadine', 0, 0, 'Tessert', 1000, 1000, 7),
(8, 'RedBull', 0, 0, 'GoldenPower', 1000, 1000, 8);

-- --------------------------------------------------------

--
-- Structure de la table `ingredientcocktail`
--

CREATE TABLE IF NOT EXISTS `ingredientcocktail` (
  `id` int(11) not null,
  `fk_id_cocktail` int(11) not null,
  `fk_id_ingredient` int(11) not null,
  `num_ordre` int(11) not null,
  `quantite_cl` float not null,
  PRIMARY KEY (`id`),
  KEY `FK_ING_COCK_COCK` (`fk_id_cocktail`),
  CONSTRAINT `FK_ING_COCK_COCK` FOREIGN KEY (`fk_id_cocktail`) REFERENCES `cocktail` (`id`),
  KEY `FK_ING_COCK_ING` (`fk_id_ingredient`),
  CONSTRAINT `FK_ING_COCK_ING` FOREIGN KEY (`fk_id_ingredient`) REFERENCES `ingredient` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `ingredientcocktail`
--

INSERT INTO `ingredientcocktail` (`id`, `fk_id_cocktail`, `fk_id_ingredient`, `num_ordre`, `quantite_cl`) VALUES
(0, 0, 0, 0, 0),
(1, 1, 2, 1, 4),
(2, 1, 5, 2, 10),
(3, 1, 7, 3, 3),
(4, 2, 1, 2, 50),
(5, 2, 2, 1, 50),
(6, 2, 3, 3, 50),
(7, 2, 4, 4, 50),
(8, 2, 5, 5, 50),
(9, 2, 6, 6, 50),
(10, 2, 7, 7, 50),
(12, 3, 1, 1, 3),
(13, 3, 5, 2, 11),
(14, 3, 7, 3, 1),
(15, 4, 3, 1, 3),
(16, 4, 6, 2, 5),
(17, 4, 5, 3, 5),
(18, 4, 7, 4, 1),
(19, 5, 3, 1, 5),
(20, 5, 5, 2, 5),
(21, 5, 6, 3, 3),
(22, 5, 7, 4, 3),
(23, 6, 6, 1, 3),
(24, 6, 5, 2, 7),
(25, 6, 3, 3, 3),
(26, 6, 4, 4, 3),
(27, 7, 1, 1, 4),
(28, 7, 4, 2, 3),
(29, 8, 8, 1, 12),
(30, 8, 1, 2, 5),
(31, 9, 3, 1, 4),
(32, 9, 4, 2, 4),
(33, 9, 7, 3, 2),
(34, 9, 5, 4, 4),
(35, 7, 5, 3, 4),
(36, 2, 8, 8, 50);



