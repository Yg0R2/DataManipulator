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
import java.util.List;

import jodd.util.ReflectUtil;
import yg0r2.dm.liferay.LiferayEntry;
import yg0r2.dm.liferay.LiferayUtilMethod;
import yg0r2.dm.mvc.displayfield.DisplayField;

/**
 * @author Yg0R2
 */
public class DataManipulatorEntry {

	private LiferayUtilMethod _addMethod;
	private List<DisplayField> _displayFields;
	private int _entryCount;
	private int _entryDepth;
	private int _entrySubCount;
	private int _entryUpdateCount;
	private LiferayEntry _liferayEntry;
	private List<DataManipulatorEntry> _subDataManipulatorEntries = new ArrayList<>(0);
	private LiferayUtilMethod _updateMethod;

	public DataManipulatorEntry(List<DisplayField> displayFields, LiferayUtilMethod addMethod,
		LiferayUtilMethod updateMethod, LiferayEntry liferayEntry) {

		_displayFields = displayFields;

		_liferayEntry = liferayEntry;

		_addMethod = addMethod;
		_updateMethod = updateMethod;
	}

	/**
	 * @return the Liferay Util add method.
	 */
	public LiferayUtilMethod getAddMethod() {
		return _addMethod;
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
	 * @param entry Which is a Liferay entry
	 * @return the id of the given entry
	 */
	public String getEntryId(Object entry) {
		return _liferayEntry.getEntryId(entry);
	}

	/**
	 * @return the entryIdKey
	 */
	public String getEntryIdKey() {
		return _liferayEntry.getEntryIdKey();
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
	 * @param entry Which is a Liferay entry
	 * @return the given entry's parent id
	 */
	public String getParentEntryId(Object entry) {
		return _liferayEntry.getParentEntryId(entry);
	}

	/**
	 * @return the parentEntryIdKey
	 */
	public String getParentEntryIdKey() {
		return _liferayEntry.getParentEntryIdKey();
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

}
