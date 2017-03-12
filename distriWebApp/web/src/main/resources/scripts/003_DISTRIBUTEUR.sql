CREATE TABLE IF NOT EXISTS `distributeur` (
  `id` int(11) not null,
  `numero` int(11) not null,
  `intitule` varchar(255) default null,
  `description` varchar(255) default null,
  `ipAdresse` varchar(50),
  `etat_lovs` varchar(300),
  `portAtmega` int(11) not null,
  PRIMARY KEY (`id`),
  KEY `FK_ETAT_LOVS_REF_LOVS` (`etat_lovs`),
  CONSTRAINT `FK_ETAT_LOVS_REF_LOVS` FOREIGN KEY (`etat_lovs`) REFERENCES `ref_lovs` (`dom_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


ALTER TABLE `pompe`
	ADD `type_tompe_lovs` varchar(300),
	ADD `fk_id_distributeur` int(11),
	ADD KEY `FK_TP_LOVS_REF_LOVS` (`type_tompe_lovs`),
    ADD CONSTRAINT `FK_TP_LOVS_REF_LOVS` FOREIGN KEY (`type_tompe_lovs`) REFERENCES `ref_lovs` (`dom_code`),
    ADD KEY `FK_ID_DIS_DISTRI` (`fk_id_distributeur`),
    ADD CONSTRAINT `FK_ID_DIS_DISTRI` FOREIGN KEY (`fk_id_distributeur`) REFERENCES `distributeur` (`id`);
 
    
CREATE TABLE IF NOT EXISTS `distriCoktailToDo` (
  `id` int(11) not null,
  `fk_id_distributeur` int(11),
  `fk_id_cocktail` int(11),
  `priorite` int(11),
  PRIMARY KEY (`id`),
  KEY `FK_DCTD_DIS_DISTRI` (`fk_id_distributeur`),
  CONSTRAINT `FK_DCTD_DIS_DISTRI` FOREIGN KEY (`fk_id_distributeur`) REFERENCES `distributeur` (`id`),
  KEY `FK_DCTD_DIS_COCKT` (`fk_id_cocktail`),
  CONSTRAINT `FK_DCTD_DIS_COCKT` FOREIGN KEY (`fk_id_cocktail`) REFERENCES `cocktail` (`id`)

  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;  
  
  CREATE TABLE IF NOT EXISTS `distriCoktailDispo` (
  `id` int(11) not null,
  `fk_id_distributeur` int(11),
  `fk_id_cocktail` int(11),
  `quantite` float,
  `actif` bit(1),
  PRIMARY KEY (`id`),
  KEY `FK_DCDIS_DIS_DISTRI` (`fk_id_distributeur`),
  CONSTRAINT `FK_DCDIS_DIS_DISTRI` FOREIGN KEY (`fk_id_distributeur`) REFERENCES `distributeur` (`id`),
  KEY `FK_DCDIS_DIS_COCKT` (`fk_id_cocktail`),
  CONSTRAINT `FK_DCDIS_DIS_COCKT` FOREIGN KEY (`fk_id_cocktail`) REFERENCES `cocktail` (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;  
  
  
  ALTER TABLE `distributeur`
	ADD `fk_id_cocktail_en_cours` int(11),
	ADD  KEY `FK_DCEC_DIS_COCKT` (`fk_id_cocktail_en_cours`),
 	ADD CONSTRAINT `FK_DCEC_DIS_COCKT` FOREIGN KEY (`fk_id_cocktail_en_cours`) REFERENCES `cocktail` (`id`);