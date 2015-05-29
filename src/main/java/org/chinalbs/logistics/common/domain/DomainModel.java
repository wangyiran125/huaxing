package org.chinalbs.logistics.common.domain;

import java.io.Serializable;

/**
 * All the domain models should be serializable so that they can be safely
 * stored in HTTP Session and so on, but it doesn't always make sense to
 * directly pass them in remote calls.
 * 
 */
@SuppressWarnings("serial")
public abstract class DomainModel implements Serializable {
}
