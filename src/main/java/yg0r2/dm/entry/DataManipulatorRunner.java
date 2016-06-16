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
public class DataManipulatorRunner extends Thread {

	private static Logger _logger = LoggerFactory.getLogger(DataManipulatorRunner.class);

	

	public final Object addEntry() throws Exception {
		Object entry = null;

		Map<String, Object> argsMap = new HashMap<>();

		// TODO - have to add/parse values

		while (true) {
			try {
				entry = _addEntryMethod.invoke(argsMap);

				break;
			}
			catch (Exception e) {
				_checkException(e);
			}
		}

		for (int i = 0; i < )

		return entry;
	}

	public void generate() throws Exception {
		// TMP
		int count = 1;
		int editCount = 1;
		int depth = 1;
		int subCount = 1;

		if (count != 0) {
			for (int i = 0; i < count; i++) {
				Object entry = addEntry();
			}
		}
	}

	@Override
	public void run() {
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
