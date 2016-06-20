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

import yg0r2.dm.util.RandomUtil;

/**
 * @author Yg0R2

import yg0r2.dm.util.RandomUtil;
 */
public class DataManipulatorRunner implements Runnable {

	private static Logger _logger = LoggerFactory.getLogger(DataManipulatorRunner.class);

	private DataManipulatorEntry _dmEntry;

	public DataManipulatorRunner(DataManipulatorEntry dmEntry) {
		_dmEntry = dmEntry;
	}

	@Override
	public void run() {
		int count = _dmEntry.getEntryCount();

		Map<String, Object> argsMap = new HashMap<>();

		try {
			if (count != 0) {
				for (int i = 0; i < count; i++) {
					argsMap.put("counter", String.valueOf(i));

					Object entry = addEntry(argsMap);
				}
			}
			else {
				// TODO need a parent entry/entryId
				addSubEntry(null, argsMap);
			}
		}
		catch (Exception e) {
			_logger.error("The following exeption appeared during the executiof of this thread.", e);
		}
	}

	protected final Object addEntry(Map<String, Object> argsMap) throws Exception {
		Object entry = null;

		while (true) {
			argsMap.put("rndString", RandomUtil.nextString());

			try {
				entry = _dmEntry.getAddMethod().invoke(argsMap);

				break;
			}
			catch (Exception e) {
				_checkException(e);
			}
		}

		for (int i = 0; i < _dmEntry.getEntryUpdateCount(); i++) {
			Map<String, Object> updateArgsMap = new HashMap<>(argsMap);

			updateArgsMap.put("updatePrefix", String.valueOf(i) + ". update on the ");

			entry = updateEntry(entry, updateArgsMap);
		}

		if (_dmEntry.getEntrySubCount() > 0) {
			Map<String, Object> subArgsMap = new HashMap<>(argsMap);

			for (int i = 0; i < _dmEntry.getEntrySubCount(); i++) {
				subArgsMap.put("counter", String.valueOf(i));

				addSubEntry(entry, new HashMap<>(subArgsMap));
			}
		}

		return entry;
	}

	protected final Object addSubEntry(Object parentEntry, Map<String, Object> argsMap) throws Exception {
		argsMap.put(_dmEntry.getParentEntryIdKey(), _dmEntry.getParentEntryId(parentEntry));

		Object entry = null;

		while (true) {
			argsMap.put("rndString", RandomUtil.nextString());

			try {
				entry = _dmEntry.getAddMethod().invoke(argsMap);

				break;
			}
			catch (Exception e) {
				_checkException(e);
			}
		}

		return entry;
	}

	protected final Object updateEntry(Object entry, Map<String, Object> argsMap) throws Exception {
		argsMap.put(_dmEntry.getEntryIdKey(), _dmEntry.getEntryId(entry));

		while (true) {
			try {
				return _dmEntry.getUpdateMethod().invoke(argsMap);
			}
			catch (Exception e) {
				_checkException(e);

				argsMap.put("rndString", RandomUtil.nextString());
			}
		}
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
