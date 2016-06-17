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
package yg0r2.dm.entry;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yg0R2
 */
public class DataManipulatorRunner implements Runnable {

	private static Logger _logger = LoggerFactory.getLogger(DataManipulatorRunner.class);

	private DataManipulatorEntry _dmEntry;

	public DataManipulatorRunner(DataManipulatorEntry dmEntry) {
		_dmEntry = dmEntry;
	}

	@Override
	public void run() {
		try {
			int count = _dmEntry.getEntryCount();

			if (count != 0) {
				for (int i = 0; i < count; i++) {
					Object entry = addEntry();
				}
			}
			else {
				// TODO need a parent entry/entryId
				addSubEntry(null);
			}
		}
		catch (Exception e) {
			_logger.error("The following exeption appeared during the executiof of this thread.", e);
		}
	}

	protected final Object addEntry() throws Exception {
		Object entry = null;

		Map<String, Object> argsMap = new HashMap<>();

		// TODO - have to add/parse values

		while (true) {
			try {
				entry = _dmEntry.getAddMethod().invoke(argsMap);

				break;
			}
			catch (Exception e) {
				_checkException(e);
			}
		}

		for (int i = 0; i < _dmEntry.getEntryUpdateCount(); i++) {
			
		}

		return entry;
	}

	protected final Object addSubEntry(Object parentEntry) throws Exception {
		Object entry = null;

		return entry;
	}

	protected final Object updateEntry(Object entry) {
		return entry;
	}

	private void _checkException(Exception e) throws Exception {
		String stackTrace = ExceptionUtils.getStackTrace(e);

		if (stackTrace.contains("Duplicate entry")) {
			_logger.info("Handled ;)");
		}
		else {
			throw e;
		}
	}

}
