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

import org.springframework.beans.factory.BeanNameAware;

import yg0r2.dm.liferay.LiferayUtilMethod;
import yg0r2.dm.mvc.displayfield.DisplayField;
import yg0r2.dm.util.ReflectUtil;

/**
 * @author Yg0R2
 */
public final class DataManipulatorEntry implements BeanNameAware {

	private LiferayUtilMethod _addMethod;
	private String _beanName;
	private List<DisplayField> _displayFields;
	private Map<String, String> _entrySpecificArgs = new HashMap<>(2);
	private List<DataManipulatorEntry> _subDataManipulatorEntries = new ArrayList<>(0);
	private LiferayUtilMethod _updateMethod;

	/**
	 * Create a new instance of <b>DataManipulatorEntry</b>.
	 *
	 * @param displayFields
	 * @param entrySpecificArgs
	 * @param addMethod
	 * @param updateMethod
	 */
	public DataManipulatorEntry(List<DisplayField> displayFields, Map<String, String> entrySpecificArgs,
		LiferayUtilMethod addMethod, LiferayUtilMethod updateMethod) {

		_displayFields = displayFields;

		entrySpecificArgs.forEach((key, value) -> {
			try {
				int period = key.lastIndexOf(".");

				Class<?> clazz = ReflectUtil.getType(key.substring(0, period));
				String fieldName = key.substring(period + 1);

				key = (String) clazz.getDeclaredField(fieldName).get(null);
			}
			catch (Exception e) {
				// Ignore, key is not a class.field
			}

			_entrySpecificArgs.put(key, value);
		});

		_addMethod = addMethod;
		_updateMethod = updateMethod;
	}

	/**
	 * @return the Liferay Util add method.
	 */
	public LiferayUtilMethod getAddMethod() {
		return _addMethod;
	}

	public String getBeanName() {
		return _beanName;
	}

	/**
	 * @return a list of DisplayFields
	 */
	public List<DisplayField> getDisplayFields() {
		return _displayFields;
	}

	/**
	 * @return the Liferay Entry specific arguments.
	 */
	public Map<String, String> getEntrySpecificArgs() {
		return _entrySpecificArgs;
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
	 * Set sub DataManupilatorEntries.
	 *
	 * @param dataManipulatorEntries
	 */
	public void setSubDataManipulatorEntries(DataManipulatorEntry... dataManipulatorEntries) {
		for (DataManipulatorEntry dme : dataManipulatorEntries) {
			_subDataManipulatorEntries.add(dme);
		}
	}

	@Override
	public void setBeanName(String name) {
		_beanName = name;
	}

}
