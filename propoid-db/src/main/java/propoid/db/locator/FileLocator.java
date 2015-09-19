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

import java.io.File;

import propoid.db.Locator;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

/**
 * Locator of a file database.
 */
public class FileLocator implements Locator {

	private File file;
	private SQLiteDatabase database;

	/**
	 * Locate the database from the given file.
	 */
	public FileLocator(File file) {
		this.file = file;
	}

	/**
	 * Locate the database from the given context.
	 * 
	 * @param context
	 *            context
	 * @param name
	 *            name of database
	 */
	public FileLocator(Context context, String name) {
		this.file = context.getDatabasePath(name);
	}

	public SQLiteDatabase open() {
		if (database != null) {
			throw new IllegalStateException("already open");
		}

		if (!file.exists()) {
			file.getParentFile().mkdirs();

			initial(file);
		}

		database = SQLiteDatabase.openOrCreateDatabase(file, null);

		return database;
	}

	/**
	 * Hook method to provide an initial content in case the database doesn't
	 * exits.
	 * <p>
	 * Default implementation does nothing.
	 * 
	 * @see Resources#openRawResource(int)
	 * @see AssetManager#open(String)
	 */
	protected void initial(File file) {
	}

	@Override
	public void close() {
		database.close();
		database = null;
	}
}