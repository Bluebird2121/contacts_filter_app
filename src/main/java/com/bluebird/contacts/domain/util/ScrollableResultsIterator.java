/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package com.bluebird.contacts.domain.util;

import org.hibernate.ScrollableResults;

import java.util.Iterator;


public class ScrollableResultsIterator<T> implements Iterator<T> {

	private final ScrollableResults scrollableResults;

	public ScrollableResultsIterator(ScrollableResults scrollableResults) {
		this.scrollableResults = scrollableResults;
	}

	@Override
	public boolean hasNext() {
		return scrollableResults.next();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T next() {
		return (T) scrollableResults.get(0);
	}
}
