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
import android.database.sqlite.SQLiteDatabase;

/**
 */
public class FileBasedLocator implements Locator {

	private File file;

	public FileBasedLocator(File file) {
		this.file = file;
	}

	public SQLiteDatabase open() {
		return SQLiteDatabase.openOrCreateDatabase(file, null);
	}
}