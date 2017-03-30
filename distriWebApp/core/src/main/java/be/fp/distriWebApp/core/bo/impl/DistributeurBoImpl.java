package be.fp.distriWebApp.core.bo.impl;

import be.fp.distriWebApp.core.bo.DistributeurBo;
import be.fp.distriWebApp.core.bo.abstractDozerMapperBo;
import be.fp.distriWebApp.core.enums.RefLovsEnum;
import be.fp.distriWebApp.core.model.dao.CocktailDao;
import be.fp.distriWebApp.core.model.dao.DistributeurDao;
import be.fp.distriWebApp.core.model.dto.CocktailDto;
import be.fp.distriWebApp.core.model.dto.DistributeurDto;
import be.fp.distriWebApp.core.model.eo.Distributeur;
import be.fp.distriWebApp.core.model.eo.Districoktaildispo;
import be.fp.distriWebApp.core.model.eo.Districoktailtodo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
@WebService
public class DistributeurBoImpl extends abstractDozerMapperBo implements DistributeurBo,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4598531461205985723L;
	@Autowired
	private DistributeurDao distributeurDao;
	@Autowired
	private CocktailDao cocktailDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<CocktailDto> getListCocktailDispo(DistributeurDto distributeurDto) {
		
		List<CocktailDto> cocktailDispo = new ArrayList<CocktailDto>();
		Distributeur distributeur = distributeurDao.get(distributeurDto.getId());
		if(distributeur != null && distributeur.getDistricoktaildispos() != null && distributeur.getDistricoktaildispos().size() > 0){
			for(Districoktaildispo currDistriCockDispo : distributeur.getDistricoktaildispos()){
				cocktailDispo.add(mapper.map(currDistriCockDispo.getCocktail(), CocktailDto.class));
			}
		}
		return cocktailDispo;
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<CocktailDto> getListCocktailToDo(DistributeurDto distributeurDto) {
		List<CocktailDto> cocktailToDo = new ArrayList<CocktailDto>();
		Distributeur distributeur = distributeurDao.get(distributeurDto.getId());
		if(distributeur != null && distributeur.getDistricoktailtodos() != null && distributeur.getDistricoktailtodos().size() > 0){
			for(Districoktailtodo currDistriCockDispo : distributeur.getDistricoktailtodos()){
				cocktailToDo.add(mapper.map(currDistriCockDispo.getCocktail(), CocktailDto.class));
			}
		}
		return cocktailToDo;
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<DistributeurDto> getAllDistributeur() {
		List<DistributeurDto> distriListDto = new ArrayList<DistributeurDto>();
		List<Distributeur> distriList = distributeurDao.getAll();
	
		if(distriList != null && distriList.size() > 0){
			for(Distributeur currDistr : distriList){
				distriListDto.add(mapper.map(currDistr, DistributeurDto.class));				
			}
			return distriListDto;
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<DistributeurDto> getAllDistributeurByEtat(RefLovsEnum etatEnum) {
		List<DistributeurDto> distriListDto = new ArrayList<DistributeurDto>();
		List<Distributeur> distriList = distributeurDao.getAllDistributeurByEtat(etatEnum.getName());
		if(distriList != null && distriList.size() > 0){
			for(Distributeur currDistr : distriList){
				distriListDto.add(mapper.map(currDistr, DistributeurDto.class));				
			}
			return distriListDto;
		}
		return null;
	}


	@Transactional(readOnly=false)
	@Override
	public void deleteDistributeur(DistributeurDto distributeurDto){
		if(distributeurDto != null && distributeurDto.getId() > -1){
			distributeurDao.remove(distributeurDto.getId());
		}
	}

	@Transactional(readOnly=false)
	@Override
	public void saveDistributeur(DistributeurDto distributeurDto){
		if(distributeurDto != null){
			distributeurDao.save(mapper.map(distributeurDto,Distributeur.class));
		}
	}

	@WebMethod public void addDistributeur(DistributeurDto distributeurDto){
		if(distributeurDto != null){
			distributeurDao.save(mapper.map(distributeurDto,Distributeur.class));
		}
	}
}
