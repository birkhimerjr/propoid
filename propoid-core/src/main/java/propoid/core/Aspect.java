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

/**
 * An aspect of a {@link Propoid}.
 */
public interface Aspect extends Serializable {

	/**
	 * Notification of a {@link Property} being set.
	 * 
	 * @param property
	 *            property about to be set
	 * @param value
	 *            value
	 * @return new value
	 */
	public <T> T onSet(Property<T> property, T value);

	/**
	 * Notification of a {@link Property} being get.
	 * 
	 * @param property
	 *            property about to be get
	 * @param value
	 *            value
	 * @return new value
	 */
	public <T> T onGet(Property<T> property, T value);
}