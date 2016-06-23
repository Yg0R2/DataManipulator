/**
 * Copyright 2016 Yg0R2
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package yg0r2.dm.liferay;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yg0r2.dm.entry.DataManipulatorEntry;
import yg0r2.dm.util.RandomUtil;

/**
 * @author Yg0R2
 */
public class LiferayEntryUtil {

	private static Logger _logger = LoggerFactory.getLogger(LiferayEntryUtil.class);

	/**
	 * Add a Liferay entry and returns with
	 *
	 * @param parentEntry if there is parent entry
	 * @param argsMap additional parameters like: counter, updatePrefix
	 * @return the created Liferay Entry
	 * @throws Exception
	 */
	public static Object addLiferayEntry(DataManipulatorEntry dmEntry, Object parentEntry) throws Exception {
		LiferayEntry liferayEntry = dmEntry.getLiferayEntry();

		if (parentEntry != null) {
			liferayEntry.setParentEntryId(parentEntry);
		}

		while (true) {
			liferayEntry.set("rndString", RandomUtil.nextString());

			try {
				return dmEntry.getAddMethod().invoke(liferayEntry.getAll());
			}
			catch (Exception e) {
				_checkLiferayException(e);
			}
		}
	}

	/**
	 * Add child Liferay entries
	 *
	 * @param parentEntry parent Entry
	 * @param depth depth of the Entries
	 * @param argsMap additional parameters like: counter, updatePrefix
	 * @throws Exception
	 */
	public static void addSubLiferayEntries(DataManipulatorEntry dmEntry, Object parentEntry, int depth, int count)
		throws Exception {

		if (depth <= 0) {
			return;
		}

		for (int i = 0; i < count; i++) {
			dmEntry.getLiferayEntry().set("counter", String.valueOf(i));

			Object entry = addLiferayEntry(dmEntry, parentEntry);

			addSubLiferayEntries(dmEntry, entry, depth - 1, count);
		}
	}

	/**
	 * 
	 * @param entry this entry will be updated
	 * @param argsMap additional parameters like: counter, updatePrefix
	 * @return the updated Liferay Entry
	 * @throws Exception
	 */
	public static Object updateLiferayEntry(DataManipulatorEntry dmEntry, Object entry) throws Exception {
		LiferayEntry liferayEntry = dmEntry.getLiferayEntry();

		liferayEntry.setEntryId(entry);

		while (true) {
			try {
				return dmEntry.getUpdateMethod().invoke(liferayEntry.getAll());
			}
			catch (Exception e) {
				_checkLiferayException(e);

				liferayEntry.set("rndString", RandomUtil.nextString());
			}
		}
	}

	/**
	 * Liferay throws exceptions, and if the exception caused by a 'Duplicate entry' exception, just ignore it, and
	 * generate a new random string fot the suffix.
	 *
	 * @param e
	 * @throws Exception if the given exception is not a 'Duplicate entry' exception
	 */
	private static void _checkLiferayException(Exception e) throws Exception {
		String stackTrace = ExceptionUtils.getStackTrace(e);

		if (stackTrace.contains("Duplicate entry")) {
			_logger.info("Handled ;)");
		}
		else {
			throw e;
		}
	}

}
