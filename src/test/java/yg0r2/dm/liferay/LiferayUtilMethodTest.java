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
package yg0r2.dm.liferay;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import yg0r2.dm.liferay.LiferayUtilMethod;
import yg0r2.dm.liferay.Parameter;

/**
 * @author Yg0R2
 */
public class LiferayUtilMethodTest {

	private List<Parameter> _defaultList;
	private Map<String, String>[] _defaultParameters;

	@Before
	@SuppressWarnings("unchecked")
	public void init() {
		_defaultList = new ArrayList<>();
		_defaultList.add(new Parameter("title", "java.lang.String"));
		_defaultList.add(new Parameter("title2", "java.lang.String", "TitleValue"));
		_defaultList.add(new Parameter("id", "long"));
		_defaultList.add(new Parameter("id2", "long", "1234"));

		List<Map<String, String>> defaultParameters = new ArrayList<>();
		defaultParameters.add(_getMap("title", "java.lang.String", null));
		defaultParameters.add(_getMap("title2", "java.lang.String", "TitleValue"));
		defaultParameters.add(_getMap("id", "long", null));
		defaultParameters.add(_getMap("id2", "long", "1234"));

		_defaultParameters = defaultParameters.toArray((Map<String, String>[]) new Map[defaultParameters.size()]);
	}

	@Test
	public void constructorTest() {
		LiferayUtilMethod liferayUtilMethod = new LiferayUtilMethod(null, null, _defaultParameters);

		assertTrue(_isEqual(_defaultList, liferayUtilMethod.getParameters()));
	}

	@Test
	public void getParameterNamesTest() {
		String[] names = new String[] { "title", "title2", "id", "id2" };

		LiferayUtilMethod liferayUtilMethod = new LiferayUtilMethod(null, null, _defaultParameters);

		assertArrayEquals(names, liferayUtilMethod.getParameterNames());
	}

	@Test
	public void getParameterTypesTest() {
		LiferayUtilMethod liferayUtilMethod = new LiferayUtilMethod(null, null, _defaultParameters);

		assertArrayEquals(new Class<?>[] { String.class, String.class, Long.TYPE, Long.TYPE },
			liferayUtilMethod.getParameterTypes());
	}

	@Test
	public void getParameterValuesTest() {
		LiferayUtilMethod liferayUtilMethod = new LiferayUtilMethod(null, null, _defaultParameters);

		assertArrayEquals(new Object[] { null, "TitleValue", 0L, 1234L },
			liferayUtilMethod.getParameterValues(new HashMap<>(0)));
	}

	@Test
	public void getParameterValuesWithPresetParametersTest() {
		Map<String, Object> presetParameters = new HashMap<>(2);
		presetParameters.put("title2", "EditedTitleValue");
		presetParameters.put("id2", 5678L);

		LiferayUtilMethod liferayUtilMethod = new LiferayUtilMethod(null, null, _defaultParameters);

		assertNotEquals(new Object[] { null, "TitleValue", 0L, 1234L },
			liferayUtilMethod.getParameterValues(presetParameters));

		assertArrayEquals(new Object[] { null, "EditedTitleValue", 0L, 5678L },
			liferayUtilMethod.getParameterValues(presetParameters));
	}

	@Test
	@SuppressWarnings({ "unchecked", "static-access" })
	public void invokeTest() throws Exception {
		TestMockUtilClass testMockUtilClass = mock(TestMockUtilClass.class);

		LiferayUtilMethod liferayUtilMethod = new LiferayUtilMethod(TestMockUtilClass.class.getName(), "addEntry",
			new HashMap<>());

		liferayUtilMethod.invoke(null);

		verify(testMockUtilClass, times(1)).addEntry();
	}

	private static Map<String, String> _getMap(String name, String type, String value) {
		Map<String, String> map = new HashMap<>(3);

		map.put("name", name);
		map.put("type", type);
		map.put("value", value);

		return map;
	}

	private static boolean _isEqual(List<Parameter> list1, List<Parameter> list2) {
		if (list1.size() != list2.size()) {
			return false;
		}

		for (Parameter p1 : list1) {
			boolean found = false;

			for (Parameter p2 : list2) {
				if (p1.equals(p2)) {
					found = true;

					break;
				}
			}

			if (!found) {
				return false;
			}
		}

		return true;
	}

}

class TestMockUtilClass {

	public static void addEntry() {
		// Mocked method
	}

}