package org.chinalbs.logistics.common.domain;

/**
 * Simple JavaBean domain object adds a name property to <code>BaseEntity</code>
 * . Used as a base class for objects needing these properties.
 */
public class NamedEntity<T> extends BaseEntity<T> {

    private static final long serialVersionUID = -2224537465383108519L;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
