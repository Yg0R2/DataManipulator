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
package yg0r2.dm;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import jodd.bean.BeanUtil;
import jodd.bean.BeanUtilBean;
import yg0r2.dm.entry.DataManipulatorEntry;
import yg0r2.dm.liferay.LiferayEntryKey;
import yg0r2.dm.mvc.displayfield.DisplayField;
import yg0r2.dm.util.RandomUtil;
import yg0r2.dm.util.RequestUtil;

/**
 * @author Yg0R2
 */
public class DataManipulatorRunnable implements Runnable {

	private static Logger _logger = LoggerFactory.getLogger(DataManipulatorRunnable.class);
	private static volatile HttpServletRequest _request;

	private DataManipulatorEntry _dmEntry;

	/**
	 * Constructor
	 *
	 * @param dataManipulatorEntry
	 * @param request
	 */
	public DataManipulatorRunnable(DataManipulatorEntry dataManipulatorEntry, final HttpServletRequest request) {
		this(dataManipulatorEntry);

		_request = request;
	}

	private DataManipulatorRunnable(DataManipulatorEntry dataManipulatorEntry) {
		_dmEntry = dataManipulatorEntry;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		int count = 0;
		int depth = 0;
		int subCount = 0;
		int updateCount = 0;

		Map<String, Object> argsMap = new HashMap<>(_dmEntry.getEntrySpecificArgs());

		for (DisplayField displayField : _dmEntry.getDisplayFields()) {
			String fieldId = displayField.getId();

			if (fieldId.endsWith("-count")) {
				count = RequestUtil.getIntParameter(_request, fieldId);
			}
			else if (fieldId.endsWith("-depth")) {
				depth = RequestUtil.getIntParameter(_request, fieldId);
			}
			else if (fieldId.endsWith("-subCount")) {
				subCount = RequestUtil.getIntParameter(_request, fieldId);
			}
			else if (fieldId.endsWith("-updateCount")) {
				updateCount = RequestUtil.getIntParameter(_request, fieldId);
			}

			// TODO add field values to argsMap
		}

		try {
			if (count == 0) {
				_runSubDataManipulatorEntry(argsMap);

				return;
			}

			for (int i = 0; i < count; i++) {
				// Add a new Entry

				Map<String, Object> addArgsMap = new HashMap<>(argsMap);

				addArgsMap.put("counter", String.valueOf(i + 1));

				Object entry = addLiferayEntry(null, addArgsMap);

				// Update the entry

				for (int j = 0; j < updateCount; j++) {
					Map<String, Object> updateArgsMap = new HashMap<>(addArgsMap);

					updateArgsMap.put("updatePrefix", String.valueOf(j + 1) + ". update on the ");

					entry = updateLiferayEntry(entry, updateArgsMap);
				}

				// Add sub type Entries

				_runSubDataManipulatorEntry(new HashMap<>(argsMap));

				// Add sub DataManipulatorEntries

				addSubLiferayEntries(entry, new HashMap<>(argsMap), depth, subCount);
			}
		}
		catch (Exception e) {
			_logger.error("The following exeption appeared during the executiof of this thread.", e);
		}
	}

	/**
	 * Add a Liferay entry and returns with it
	 *
	 * @param parentEntry
	 * @param argsMap
	 * @return
	 * @throws Exception
	 */
	protected Object addLiferayEntry(Object parentEntry, Map<String, Object> argsMap) throws Exception {
		if (parentEntry != null) {
			// Get the 'parentEntryIdKey' from the argsMap. This value came from the bean from 'entrySpecificArgs'.
			String parentEntryIdKey = (String) argsMap.get(LiferayEntryKey.PARENT_ENTRY_ID_KEY);

			BeanUtil beanUtil = new BeanUtilBean();

			// Put the parent entry's Id into the argsMap with 'parentEntryIdKey' key.
			argsMap.put(parentEntryIdKey, beanUtil.getProperty(parentEntry, parentEntryIdKey));
		}

		while (true) {
			argsMap.put("rndString", RandomUtil.nextString());

			try {
				return _dmEntry.getAddMethod().invoke(argsMap);
			}
			catch (Exception e) {
				_checkLiferayException(e);
			}
		}
	}

	/**
	 * Add child Liferay entries.
	 *
	 * @param parentEntry
	 * @param argsMap
	 * @param depth
	 * @param count
	 * @throws Exception
	 */
	protected void addSubLiferayEntries(Object parentEntry, Map<String, Object> argsMap, int depth, int count)
		throws Exception {

		if (depth <= 0) {
			return;
		}

		for (int i = 0; i < count; i++) {
			argsMap.put("counter", String.valueOf(i + 1));

			Object entry = addLiferayEntry(parentEntry, argsMap);

			addSubLiferayEntries(entry, argsMap, depth - 1, count);
		}
	}

	/**
	 * Update an existing Liferay entry and returns with it.
	 *
	 * @param entry
	 * @param argsMap
	 * @return
	 * @throws Exception
	 */
	protected Object updateLiferayEntry(Object entry, Map<String, Object> argsMap) throws Exception {
		Assert.notNull(entry);

		// Get the 'ntryIdKey' from the argsMap. This value came from the bean from 'entrySpecificArgs'.
		String entryIdKey = (String) argsMap.get(LiferayEntryKey.ENTRY_ID_KEY);

		BeanUtil beanUtil = new BeanUtilBean();

		// Put the entry's Id into the argsMap with 'entryIdKey' key.
		argsMap.put(entryIdKey, beanUtil.getProperty(entry, entryIdKey));

		while (true) {
			try {
				return _dmEntry.getUpdateMethod().invoke(argsMap);
			}
			catch (Exception e) {
				_checkLiferayException(e);

				// There was a 'duplicate entry' exception, so try to add an entry with a new random string.
				argsMap.put("rndString", RandomUtil.nextString());
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
	private void _checkLiferayException(Exception e) throws Exception {
		String stackTrace = ExceptionUtils.getStackTrace(e);

		if (stackTrace.contains("Duplicate entry")) {
			_logger.info("Handled ;)");
		}
		else {
			throw e;
		}
	}

	/**
	 * Add sub DataManipulatorEntries.
	 *
	 * @param argsMap
	 * @throws Exception
	 */
	private final void _runSubDataManipulatorEntry(Map<String, Object> argsMap) throws Exception {
		for (DataManipulatorEntry dmEntry : _dmEntry.getSubDataManipulatorEntries()) {
			new DataManipulatorRunnable(dmEntry).run();
		}
	}

}
