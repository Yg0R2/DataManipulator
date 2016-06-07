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

import java.util.List;

import yg0r2.dm.liferay.LiferayUtilMethod;
import yg0r2.dm.mvc.displayfield.DisplayField;

/**
 * @author Yg0R2
 */
public class DataManipulatorEntry {

	private LiferayUtilMethod _addEntryMethod;
	private List<DisplayField> _dDisplayFields;
	private LiferayUtilMethod _updateEntryMethod;

	public DataManipulatorEntry(List<DisplayField> displayFields, LiferayUtilMethod addMethod,
		LiferayUtilMethod updateMethod) {

		_dDisplayFields = displayFields;

		_addEntryMethod = addMethod;
		_updateEntryMethod = updateMethod;
	}

	public List<DisplayField> getDisplayFields() {
		return _dDisplayFields;
	}

}
