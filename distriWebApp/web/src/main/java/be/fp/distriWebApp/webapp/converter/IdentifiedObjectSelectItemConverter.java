package be.fp.distriWebApp.webapp.converter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import be.fp.distriWebApp.core.model.dto.AbstractIdentifiedObject;

/**
 * Faces Converter for <code>&lt;f:selectItems /&gt;</code>-parent used with <code>List&lt;? extends AbstractIdentifiedObject&gt;</code><br />
 * <br />
 * This is the default converter for components bound to <code>List&lt;? extends AbstractIdentifiedObject&gt;</code>
 *
 * @author edo
 */
@FacesConverter(forClass = AbstractIdentifiedObject.class)
public class IdentifiedObjectSelectItemConverter extends AbstractIdentifiedObjectConverter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		for (UIComponent child : component.getChildren()) {
			if (child instanceof UISelectItems) {
				List<Object> selectItems = new ArrayList<Object>();
				if(((UISelectItems) child).getValue() instanceof List)
				{
					selectItems = (List<Object>) (((UISelectItems) child).getValue());
				}
				else
				{
					selectItems = Arrays.asList(((Object[])((UISelectItems) child).getValue()));
				}
			
				for (Object oneItem : selectItems) {
					@SuppressWarnings("unchecked")
					AbstractIdentifiedObject<? extends Serializable> selectItem = (AbstractIdentifiedObject<? extends Serializable>) oneItem;
					Serializable id = selectItem.getId();

					if (id != null && id.toString().equals(value)) {
						return selectItem;
					}
				}
			}
		}

		return null;
	}
}