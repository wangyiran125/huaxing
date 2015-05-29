package org.chinalbs.logistics.common.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Base class from which every concrete entity requiring version for optimistic
 * concurrency control inherits.
 * 
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class VersionedEntity<T> extends BaseEntity<T> {

    /**
     * Field used by JPA's optimistic concurrency control
     */
    @Version
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    // TODO better to be protected
    public void setVersion(Integer version) {
        this.version = version;
    }
}