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
package propoid.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * A property of a {@link Propoid}.
 * 
 * @see Propoid#property()
 */
public final class Property<T> implements Serializable {

	private static Map<Field, Meta> metas = new HashMap<Field, Meta>();

	/**
	 * The owning {@link Propoid}.
	 */
	public final Propoid propoid;

	final Property<?> next;

	final int index;

	T value;

	Property(Propoid propoid, Property<?> next) {
		this.propoid = propoid;
		this.next = next;

		index = (next == null) ? 0 : next.index + 1;
	}

	public Property.Meta meta() {
		return Propoid.getMeta(propoid).get(this);
	}

	/**
	 * The name of this property.
	 */
	public String name() {
		return meta().name;
	}

	/**
	 * The type of this property.
	 */
	public Type type() {
		return meta().type;
	}

	/**
	 * Get the value.
	 */
	public T get() {
		value = propoid.aspect.onGet(this, value);

		return value;
	}

	/**
	 * Set the value.
	 */
	public void set(T value) {
		this.value = propoid.aspect.onSet(this, value);
	}

	/**
	 * String representation.
	 */
	@Override
	public String toString() {
		return String.format("%s = %s", name(), value);
	}

	@Override
	public int hashCode() {
		return meta().hashCode();
	}

	public boolean equals(Object other) {
		if (other instanceof Property) {
			Property<?> property = (Property<?>) other;

			return meta() == property.meta();
		}

		return false;
	}

	static Meta getMeta(Field field) {
		Meta meta = metas.get(field);
		if (meta == null) {
			meta = new Meta(field);

			metas.put(field, meta);
		}
		return meta;
	}

	public static final class Meta {
		public final String name;
		public final Type type;

		Meta(Field field) {
			name = field.getName();

			if (!Modifier.isFinal(field.getModifiers())) {
				throw new IllegalStateException("property '" + name
						+ "' must be declared final");
			}

			type = ((ParameterizedType) field.getGenericType())
					.getActualTypeArguments()[0];
		}

		public <T> T get(Property<T> property) {
			return property.value;
		}

		public <T> void set(Property<T> property, T value) {
			property.value = value;
		}
	}
}