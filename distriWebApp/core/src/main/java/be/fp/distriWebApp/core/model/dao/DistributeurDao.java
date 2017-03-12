package be.fp.distriWebApp.core.model.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import be.fp.distriWebApp.core.model.eo.Distributeur;

public interface DistributeurDao extends GenericDao<Distributeur, Integer> {
	public List<Distributeur> getAllDistributeurByEtat(String domCodeEtat);
}
	