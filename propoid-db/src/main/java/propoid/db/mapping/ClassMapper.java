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
package propoid.db.mapping;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import propoid.core.Property;
import propoid.db.Repository;
import propoid.db.RepositoryException;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

/**
 * A mapper for {@link Class} properties.
 */
@SuppressWarnings("rawtypes")
public class ClassMapper implements Mapper<Class> {

	@Override
	public boolean maps(Property<?> property) {
		Type type = property.type();
		return ParameterizedType.class.isInstance(type);
	}

	public String type(Property<Class> property, Repository repository) {
		return "TEXT";
	}

	@Override
	public void bind(Property<Class> property, Repository repository,
			SQLiteStatement statement, int index) {
		Class value = property.meta().get(property);
		if (value == null) {
			statement.bindNull(index);
		} else {
			statement.bindString(index, value.getName());
		}
	}

	@Override
	public void retrieve(Property<Class> property, Repository repository,
			Cursor cursor, int index) {
		if (cursor.isNull(index)) {
			property.meta().set(property, null);
		} else {
			try {
				property.meta().set(property,
						Class.forName(cursor.getString(index)));
			} catch (ClassNotFoundException ex) {
				throw new RepositoryException(ex);
			}
		}
	}

	@Override
	public String argument(Property<Class> newParam, Repository repository,
			Class value) {
		return value.getName();
	}
}