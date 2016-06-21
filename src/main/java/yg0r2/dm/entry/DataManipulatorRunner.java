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
package yg0r2.dm.entry;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yg0R2
 */
public class DataManipulatorRunner implements Runnable {

	private static Logger _logger = LoggerFactory.getLogger(DataManipulatorRunner.class);

	private DataManipulatorEntry _dmEntry;
	private Map<String, Object> _parentArgsMap;

	public DataManipulatorRunner(DataManipulatorEntry dmEntry, Map<String, Object> parentArgsMap) {
		_dmEntry = dmEntry;

		_parentArgsMap = parentArgsMap;
	}

	@Override
	public void run() {
		int count = _dmEntry.getEntryCount();
		int depth = _dmEntry.getEntryDepth();
		int updateCount = _dmEntry.getEntryUpdateCount();

		Map<String, Object> argsMap = new HashMap<>();
		argsMap.putAll(_parentArgsMap);

		try {
			if (count == 0) {
				runSubDataManipulatorEntry(argsMap);

				return;
			}

			for (int i = 0; i < count; i++) {
				argsMap.put("counter", String.valueOf(i));

				Object entry = _dmEntry.addLiferayEntry(null, argsMap);

				for (int j = 0; j < updateCount; j++) {
					Map<String, Object> updateArgsMap = new HashMap<>(argsMap);

					updateArgsMap.put("updatePrefix", String.valueOf(j) + ". update on the ");

					entry = _dmEntry.updateLiferayEntry(entry, updateArgsMap);
				}

				runSubDataManipulatorEntry(new HashMap<>(argsMap));

				_dmEntry.addSubLiferayEntries(entry, depth, new HashMap<>(argsMap));
			}
		}
		catch (Exception e) {
			_logger.error("The following exeption appeared during the executiof of this thread.", e);
		}
	}

	protected final void runSubDataManipulatorEntry(Map<String, Object> argsMap) throws Exception {
		for (DataManipulatorEntry dmEntry : _dmEntry.getSubDataManipulatorEntries()) {
			new DataManipulatorRunner(dmEntry, argsMap).run();
		}
	}

}
