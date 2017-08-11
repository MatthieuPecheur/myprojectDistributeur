package be.fp.distriWebApp.core.model.dto;
import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * An entity that is identified with an unique ID.<br />
 * <br />
 * This basic class re-implements <code>equals()</code> and <code>hashCode()</code> to reflect the below statement.
 *
 * @author edo
 */
@MappedSuperclass
public abstract class AbstractIdentifiedObject<K extends Serializable> {

	public abstract K getId();

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (object == null || getClass() != object.getClass()) {
			return false;
		}

		if (getId() == null) {
			return false;
		}

		@SuppressWarnings("unchecked")
		AbstractIdentifiedObject<K> other = (AbstractIdentifiedObject<K>) object;

		return getId().equals(other.getId());
	}

	@Override
	public int hashCode() {
		if (getId() == null) {
			return 0;
		}

		return getId().hashCode();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '[' + getId() + ']';
	}
}
