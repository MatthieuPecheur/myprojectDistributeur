package be.fp.distriWebApp.webapp.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;
import org.springframework.stereotype.Component;

import be.fp.distriWebApp.core.model.dto.AbstractIdentifiedObject;

/**
 * Faces Converter for <code>&lt;p:pickList&gt;</code> used with <code>DualListModel&lt;? extends AbstractIdentifiedObject&gt;</code><br />
 * <br />
 * You can refer to this converter using the following EL-expression: <code>#{identifiedObjectPickListConverter}</code>
 *
 * @author edo
 */
@Component
@FacesConverter(value="identifiedObjectPickListConverter")
public class IdentifiedObjectPickListConverter extends AbstractIdentifiedObjectConverter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		@SuppressWarnings("unchecked")
		DualListModel<? extends AbstractIdentifiedObject<? extends Serializable>> identifiedObjects = (DualListModel<? extends AbstractIdentifiedObject<? extends Serializable>>) ((PickList) component).getValue();

		for (AbstractIdentifiedObject<? extends Serializable> object : identifiedObjects.getSource()) {
			Serializable id = object.getId();

			if (id != null && id.toString().equals(value)) {
				return object;
			}
		}

		for (AbstractIdentifiedObject<? extends Serializable> object : identifiedObjects.getTarget()) {
			Serializable id = object.getId();

			if (id != null && id.toString().equals(value)) {
				return object;
			}
		}

		return null;
	}
}
