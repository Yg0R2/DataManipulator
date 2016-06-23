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

import static org.mockito.Mockito.mock;
import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import yg0r2.dm.liferay.LiferayEntry;
import yg0r2.dm.liferay.LiferayUtilMethod;
import yg0r2.dm.mvc.displayfield.DisplayField;

/**
 * @author Yg0R2
 */
public class DataManipulatorEntryTest {

	private DataManipulatorEntry _dataManipulatorEntry;
	private LiferayUtilMethod _mockAddMethod = mock(LiferayUtilMethod.class);
	private List<DisplayField> _mockDisplayField = new ArrayList<>();
	private LiferayEntry _mockLiferayEntry = mock(LiferayEntry.class);
	private LiferayUtilMethod _mockUpdateMethod = mock(LiferayUtilMethod.class);

	@Before
	public void beforeTest() {
		_dataManipulatorEntry = new DataManipulatorEntry(_mockDisplayField, _mockLiferayEntry, _mockAddMethod,
			_mockUpdateMethod);
	}

	@Test
	public void constructorTest() {
		assertEquals(_mockAddMethod, _dataManipulatorEntry.getAddMethod());
		assertEquals(_mockDisplayField, _dataManipulatorEntry.getDisplayFields());
		assertEquals(_mockLiferayEntry, _dataManipulatorEntry.getLiferayEntry());
		assertEquals(_mockUpdateMethod, _dataManipulatorEntry.getUpdateMethod());

		assertEquals(new ArrayList<>(), _dataManipulatorEntry.getSubDataManipulatorEntries());
	}

}
