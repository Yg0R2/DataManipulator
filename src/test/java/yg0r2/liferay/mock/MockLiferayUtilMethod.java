/**
 * Copyright 2016 Yg0R2
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
package yg0r2.liferay.mock;

import com.google.common.annotations.VisibleForTesting;

/**
 * This class is for testing purpose only.<br />
 * It represents a Liferay <b>*[Local]ServiceUtil</b> class.
 *
 * @author Yg0R2
 */
@VisibleForTesting
public class MockLiferayUtilMethod {

	/**
	 * Like an addEntry method of a Liferay <b>*[Local]ServiceUtil</b> class.
	 *
	 * @return
	 */
	public static Object addEntry() {
		return null;
	}

	public static MockLiferayEntry addEntry(String title, String content) {
		return new MockLiferayEntry(title, content);
	}

	/**
	 * Like an updateEntry method of a Liferay <b>*[Local]ServiceUtil</b> class.
	 *
	 * @param entry
	 * @return
	 */
	public static Object updateEntry(Object entry) {
		return entry;
	}

	public static MockLiferayEntry updateEntry(long entryId, String editedTitle, String editedContent) {
		return new MockLiferayEntry(editedTitle, editedContent);
	}

}
