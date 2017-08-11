package be.fp.distriWebApp.webapp.converter;
import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import be.fp.distriWebApp.core.model.dto.AbstractIdentifiedObject;

public abstract class AbstractIdentifiedObjectConverter implements Converter {

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		@SuppressWarnings("unchecked")
		Serializable id = ((AbstractIdentifiedObject<? extends Serializable>) value).getId();

		if (id != null) {
			return id.toString();
		}

		return null;
	}
}
