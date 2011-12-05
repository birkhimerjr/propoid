/*
 * Copyright 2011 Sven Meier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package propoid.db.operation;

import propoid.core.Property;
import propoid.core.Propoid;
import propoid.db.Repository;
import propoid.db.RepositoryException;
import propoid.db.SQL;
import propoid.db.aspect.Relation;
import propoid.db.aspect.Row;

/**
 * Delete a single {@link Propoid}.
 */
public class Delete extends Operation {

	public Delete(Repository repository) {
		super(repository);
	}

	@SuppressWarnings("unchecked")
	public void now(Propoid propoid) {
		long id = Row.getID(propoid);
		if (id == Row.TRANSIENT) {
			throw new RepositoryException("cannot delete transient propoid");
		}

		for (Property<?> property : propoid.properties()) {
			if (property.type() instanceof Class
					&& Propoid.class.isAssignableFrom((Class<?>) property
							.type())) {
				cascade((Property<Propoid>) property);
			}
		}

		SQL sql = new SQL();
		sql.raw("DELETE FROM ");
		sql.escaped(repository.naming.toTable(repository, propoid.getClass()));
		sql.raw(" WHERE _id = ?");

		repository.getDatabase().execSQL(sql.toString(), new Object[] { id });
	}

	@Override
	protected void onCascade(Relation relation) {
		repository.cascading.onDelete(repository, relation);
	}
}