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

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import yg0r2.dm.liferay.LiferayEntryKey;
import yg0r2.dm.liferay.LiferayUtilMethod;
import yg0r2.dm.liferay.Parameter;
import yg0r2.dm.mvc.displayfield.DisplayField;
import yg0r2.dm.mvc.displayfield.FieldType;
import yg0r2.liferay.mock.MockLiferayUtilMethod;

/**
 * @author Yg0R2
 */
public class DataManipulatorEntryTest {

	@Test
	public void constructorTest() throws ClassNotFoundException {
		LiferayUtilMethod addMethod = new LiferayUtilMethod(MockLiferayUtilMethod.class.getName(), "addEntry",
			new ArrayList<>());

		List<DisplayField> displayField = new ArrayList<>();
		displayField.add(new DisplayField("fieldId", FieldType.INPUT, "defaultValue"));

		List<Parameter> updateParameters = new ArrayList<>();
		updateParameters.add(new Parameter("entry", Object.class, null));
		updateParameters.add(new Parameter(LiferayEntryKey.ENTRY_ID_KEY, long.class));

		LiferayUtilMethod updateMethod = new LiferayUtilMethod(MockLiferayUtilMethod.class.getName(), "updateEntry",
			updateParameters);

		DataManipulatorEntry dataManipulatorEntry = new DataManipulatorEntry(displayField, new HashMap<>(), addMethod,
			updateMethod);

		assertEquals(addMethod, dataManipulatorEntry.getAddMethod());
		assertEquals(displayField, dataManipulatorEntry.getDisplayFields());
		assertEquals(new HashMap<>(), dataManipulatorEntry.getEntrySpecificArgs());
		assertEquals(updateMethod, dataManipulatorEntry.getUpdateMethod());

		assertEquals(new ArrayList<>(), dataManipulatorEntry.getSubDataManipulatorEntries());
	}

	@Test
	public void getEntrySpecificArgsTest() {
		Map<String, String> args = new HashMap<>();
		args.put("userId", "12345");
		args.put(LiferayEntryKey.class.getName() + ".ENTRY_ID_KEY", "123");
		args.put(LiferayEntryKey.PARENT_ENTRY_ID_KEY, "2345");

		DataManipulatorEntry dmEntry = new DataManipulatorEntry(null, args, null, null);

		assertEquals("12345", dmEntry.getEntrySpecificArgs().get("userId"));
		assertEquals("123", dmEntry.getEntrySpecificArgs().get(LiferayEntryKey.ENTRY_ID_KEY));
		assertEquals("2345", dmEntry.getEntrySpecificArgs().get(LiferayEntryKey.PARENT_ENTRY_ID_KEY));
	}

}
