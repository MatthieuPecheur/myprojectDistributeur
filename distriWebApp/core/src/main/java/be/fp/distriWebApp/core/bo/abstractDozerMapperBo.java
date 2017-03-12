package be.fp.distriWebApp.core.bo;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class abstractDozerMapperBo {
	@Autowired
	protected Mapper mapper;
}
