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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import yg0r2.dm.liferay.LiferayUtilMethod;

/**
 * @author Yg0R2
 */
public class DataManipulatorRunnerTest {

	private DataManipulatorEntry _dme = mock(DataManipulatorEntry.class);

	@Before
	@SuppressWarnings("unchecked")
	public void beforeTest() throws Exception {
		when(_dme.getAddMethod()).thenReturn(mock(LiferayUtilMethod.class));
		when(_dme.getUpdateMethod()).thenReturn(mock(LiferayUtilMethod.class));

		when(_dme.getAddMethod().invoke(anyMap())).thenReturn(12);
		when(_dme.getUpdateMethod().invoke(anyMap())).thenReturn(15);
	}

	@Test
	public void addSubEntryTest() throws Exception {
		DataManipulatorRunner runner = new DataManipulatorRunner(_dme);

		assertEquals(12, runner.addEntry(new HashMap<>()));
	}

}