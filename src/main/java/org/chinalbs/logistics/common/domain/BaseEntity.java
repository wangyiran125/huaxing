package org.chinalbs.logistics.common.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for
 * objects needing this property.
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity<T> extends DomainModel {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected T id;

    public void setId(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    @JsonIgnore
    public boolean isNew() {
        return (this.id == null);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity<T> other = (BaseEntity<T>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
