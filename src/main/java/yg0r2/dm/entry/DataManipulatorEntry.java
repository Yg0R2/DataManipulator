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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yg0r2.dm.liferay.LiferayEntry;
import yg0r2.dm.liferay.LiferayUtilMethod;
import yg0r2.dm.mvc.displayfield.DisplayField;
import yg0r2.dm.util.RandomUtil;

/**
 * @author Yg0R2
 */
public class DataManipulatorEntry {

	private static Logger _logger = LoggerFactory.getLogger(DataManipulatorEntry.class);

	private LiferayUtilMethod _addMethod;
	private List<DisplayField> _displayFields;
	private int _entryCount;
	private int _entryDepth;
	private int _entrySubCount;
	private int _entryUpdateCount;
	private LiferayEntry _liferayEntry;
	private List<DataManipulatorEntry> _subDataManipulatorEntries = new ArrayList<>(0);
	private LiferayUtilMethod _updateMethod;

	public DataManipulatorEntry(List<DisplayField> displayFields, LiferayEntry liferayEntry,
		LiferayUtilMethod addMethod, LiferayUtilMethod updateMethod) {

		_displayFields = displayFields;

		_liferayEntry = liferayEntry;

		_addMethod = addMethod;
		_updateMethod = updateMethod;
	}

	/**
	 * Add a Liferay entry and returns with
	 *
	 * @param parentEntry if there is parent entry
	 * @param argsMap additional parameters like: counter, updatePrefix
	 * @return the created Liferay Entry
	 * @throws Exception
	 */
	public Object addLiferayEntry(Object parentEntry, Map<String, Object> argsMap) throws Exception {
		if (parentEntry != null) {
			argsMap.put(_liferayEntry.getParentEntryIdKey(), _liferayEntry.getParentEntryId(parentEntry));
		}

		while (true) {
			argsMap.put("rndString", RandomUtil.nextString());

			try {
				return _addMethod.invoke(argsMap);
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
	public void addSubLiferayEntries(Object parentEntry, int depth, Map<String, Object> argsMap) throws Exception {
		if (depth <= 0) {
			return;
		}

		for (int i = 0; i < getEntrySubCount(); i++) {
			argsMap.put("counter", String.valueOf(i));

			Object entry = addLiferayEntry(parentEntry, new HashMap<>(argsMap));

			addSubLiferayEntries(entry, depth - 1, argsMap);
		}
	}

	/**
	 * @return a list of DisplayFields
	 */
	public List<DisplayField> getDisplayFields() {
		return _displayFields;
	}

	/**
	 * @return the entryCount
	 */
	public int getEntryCount() {
		return _entryCount;
	}

	/**
	 * @return the entryDepth
	 */
	public int getEntryDepth() {
		return _entryDepth;
	}

	/**
	 * @return the entrySubCount
	 */
	public int getEntrySubCount() {
		return _entrySubCount;
	}

	/**
	 * @return the entryUpdateCount
	 */
	public int getEntryUpdateCount() {
		return _entryUpdateCount;
	}

	/**
	 * @return the subDataManipulatorEntries
	 */
	public List<DataManipulatorEntry> getSubDataManipulatorEntries() {
		return _subDataManipulatorEntries;
	}

	/**
	 * @return the Liferay Util update method.
	 */
	public LiferayUtilMethod getUpdateMethod() {
		return _updateMethod;
	}

	/**
	 * @param entryCount the entryCount to set
	 */
	public void setEntryCount(int entryCount) {
		_entryCount = entryCount;
	}

	/**
	 * @param entryDepth the entryDepth to set
	 */
	public void setEntryDepth(int entryDepth) {
		_entryDepth = entryDepth;
	}

	/**
	 * @param entrySubCount the entrySubCount to set
	 */
	public void setEntrySubCount(int entrySubCount) {
		_entrySubCount = entrySubCount;
	}

	/**
	 * @param entryUpdateCount the entryUpdateCount to set
	 */
	public void setEntryUpdateCount(int entryUpdateCount) {
		_entryUpdateCount = entryUpdateCount;
	}

	/**
	 * Set sub DataManupilatorEntries.
	 *
	 * @param dataManipulatorEntries
	 */
	public void setSubDataManipulatorEntries(DataManipulatorEntry... dataManipulatorEntries) {
		for (DataManipulatorEntry dme : dataManipulatorEntries) {
			_subDataManipulatorEntries.add(dme);
		}
	}

	/**
	 * 
	 * @param entry this entry will be updated
	 * @param argsMap additional parameters like: counter, updatePrefix
	 * @return the updated Liferay Entry
	 * @throws Exception
	 */
	public Object updateLiferayEntry(Object entry, Map<String, Object> argsMap) throws Exception {
		argsMap.put(_liferayEntry.getEntryIdKey(), _liferayEntry.getEntryId(entry));

		while (true) {
			try {
				return _updateMethod.invoke(argsMap);
			}
			catch (Exception e) {
				_checkLiferayException(e);

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

}
