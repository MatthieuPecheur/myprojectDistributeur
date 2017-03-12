package be.fp.distriWebApp.core.model.eo;
// Generated by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "districoktailtodo", catalog = "distriwebapp")

public class Districoktailtodo implements java.io.Serializable {

	public static final String ID = "id";
	public static final String COCKTAIL = "cocktail";
	public static final String DISTRIBUTEUR = "distributeur";
	public static final String PRIORITE = "priorite";

	@Id

	@Column(name = "id", unique = true, nullable = false)

	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_id_cocktail")

	private Cocktail cocktail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_id_distributeur")

	private Distributeur distributeur;

	@Column(name = "priorite")

	private Integer priorite;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cocktail getCocktail() {
		return cocktail;
	}

	public void setCocktail(Cocktail cocktail) {
		this.cocktail = cocktail;
	}

	public Distributeur getDistributeur() {
		return distributeur;
	}

	public void setDistributeur(Distributeur distributeur) {
		this.distributeur = distributeur;
	}

	public Integer getPriorite() {
		return priorite;
	}

	public void setPriorite(Integer priorite) {
		this.priorite = priorite;
	}

	/**
	* toString
	* @return String
	*/
	public String toString() {
		return new ToStringBuilder(this).append("\n").append("id", id).append("\n").toString();
	}

	public boolean equals(Object obj) {
		if (obj instanceof Districoktailtodo == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Districoktailtodo rhs = (Districoktailtodo) obj;
		return new EqualsBuilder().append("id", rhs.id).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(id).toHashCode();
	}

}
