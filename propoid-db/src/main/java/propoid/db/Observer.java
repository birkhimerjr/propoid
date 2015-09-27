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
package propoid.db;

import propoid.core.Property;
import propoid.core.Propoid;
import propoid.db.mapping.Mapper;
import propoid.db.operation.Operation;

/**
 * Observer to changes on {@link Propoid}s.
 */
public interface Observer extends Setting {

	public void onInsert(Propoid propoid);

	public void onDelete(Propoid propoid);

	public void onUpdate(Propoid propoid);
}