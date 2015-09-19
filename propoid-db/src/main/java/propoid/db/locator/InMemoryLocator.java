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
package propoid.db.locator;

import propoid.db.Locator;
import android.database.sqlite.SQLiteDatabase;

/**
 * Locator of an in-memory database.
 */
public class InMemoryLocator implements Locator {

	private SQLiteDatabase database;

	public InMemoryLocator() {
	}

	public SQLiteDatabase open() {
		if (database != null) {
			throw new IllegalStateException("already open");
		}
		database = SQLiteDatabase.create(null);

		return database;
	}

	@Override
	public void close() {
		database.close();
		database = null;
	}
}