
CREATE TABLE IF NOT EXISTS `ref_lovs_domains` (
  `domain` varchar(255) not null,
  `lab_fr` varchar(500) default null,
  `creation_date` date not null,
  `creation_user` varchar(300) not null,
  `modification_date` date default null,
  `modification_user` varchar(300) default null,
  PRIMARY KEY (`domain`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS `ref_lovs` (
  `domain` varchar(255) not null,
  `code` varchar(50) not null,
  `dom_code` varchar(300) not null,
  `lab_fr` varchar(255) default null,
  `long_lab_fr` text default null,
  `sequence_nr` int(5) default 1,
  `creation_date` date not null,
  `creation_user` varchar(300) not null,
  `modification_date` date default null,
  `modification_user` varchar(300) default null,
  PRIMARY KEY (`dom_code`),
  KEY `FK_DOMAIN_REF_LOVS_DOM` (`domain`),
  CONSTRAINT `FK_DOMAIN_REF_LOVS_DOM` FOREIGN KEY (`domain`) REFERENCES `ref_lovs_domains` (`domain`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

