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

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import yg0r2.dm.liferay.Parameter;

/**
 * @author Yg0R2
 */
public class ParameterTest {

	@Test
	public void constructorClassTest() throws ClassNotFoundException {
		assertEquals(false, (new Parameter(null, Boolean.class)).getValue());
		assertEquals(true, (new Parameter(null, Boolean.class, true)).getValue());
		assertEquals(true, (new Parameter(null, Boolean.class, "true")).getValue());
		assertNotEquals(false, (new Parameter(null, Boolean.class, true)).getValue());

		assertEquals(0d, (new Parameter(null, Double.class)).getValue());
		assertEquals(1d, (new Parameter(null, Double.class, 1d)).getValue());
		assertEquals(1d, (new Parameter(null, Double.class, "1")).getValue());
		assertNotEquals(0d, (new Parameter(null, Double.class, 1d)).getValue());

		assertEquals(0, (new Parameter(null, Integer.class)).getValue());
		assertEquals(1, (new Parameter(null, Integer.class, 1)).getValue());
		assertEquals(1, (new Parameter(null, Integer.class, "1")).getValue());
		assertNotEquals(0, (new Parameter(null, Integer.class, 1)).getValue());

		assertEquals(0L, (new Parameter(null, Long.class)).getValue());
		assertEquals(1L, (new Parameter(null, Long.class, 1L)).getValue());
		assertEquals(1L, (new Parameter(null, Long.class, "1")).getValue());
		assertNotEquals(0L, (new Parameter(null, Long.class, 1L)).getValue());
	}

	@Test(expected = ClassNotFoundException.class)
	public void constructorInvalidClassTest() throws ClassNotFoundException {
		new Parameter(null, "java.lang.NonExistingClass");
	}

	@Test
	public void constructorPrimitiveTest() throws ClassNotFoundException {
		assertEquals(false, (new Parameter(null, Boolean.TYPE)).getValue());
		assertEquals(true, (new Parameter(null, Boolean.TYPE, true)).getValue());
		assertEquals(true, (new Parameter(null, Boolean.TYPE, "true")).getValue());
		assertNotEquals(false, (new Parameter(null, Boolean.TYPE, true)).getValue());

		assertEquals(0d, (new Parameter(null, Double.TYPE)).getValue());
		assertEquals(1d, (new Parameter(null, Double.TYPE, 1d)).getValue());
		assertEquals(1d, (new Parameter(null, Double.TYPE, "1")).getValue());
		assertNotEquals(0d, (new Parameter(null, Double.TYPE, 1d)).getValue());

		assertEquals(0, (new Parameter(null, Integer.TYPE)).getValue());
		assertEquals(1, (new Parameter(null, Integer.TYPE, 1)).getValue());
		assertEquals(1, (new Parameter(null, Integer.TYPE, "1")).getValue());
		assertNotEquals(0, (new Parameter(null, Integer.TYPE, 1)).getValue());

		assertEquals(0L, (new Parameter(null, Long.TYPE)).getValue());
		assertEquals(1L, (new Parameter(null, Long.TYPE, 1L)).getValue());
		assertEquals(1L, (new Parameter(null, Long.TYPE, "1")).getValue());
		assertNotEquals(0L, (new Parameter(null, Long.TYPE, 1L)).getValue());
	}

	@Test
	public void constructorPrimitiveNameTest() throws ClassNotFoundException {
		assertEquals(false, (new Parameter(null, Boolean.TYPE.toString())).getValue());
		assertEquals(true, (new Parameter(null, Boolean.TYPE.toString(), true)).getValue());
		assertEquals(true, (new Parameter(null, Boolean.TYPE.toString(), "true")).getValue());
		assertNotEquals(false, (new Parameter(null, Boolean.TYPE.toString(), true)).getValue());

		assertEquals(0d, (new Parameter(null, Double.TYPE.toString())).getValue());
		assertEquals(1d, (new Parameter(null, Double.TYPE.toString(), 1d)).getValue());
		assertEquals(1d, (new Parameter(null, Double.TYPE.toString(), "1")).getValue());
		assertNotEquals(0d, (new Parameter(null, Double.TYPE, 1d)).getValue());

		assertEquals(0, (new Parameter(null, Integer.TYPE.toString())).getValue());
		assertEquals(1, (new Parameter(null, Integer.TYPE.toString(), 1)).getValue());
		assertEquals(1, (new Parameter(null, Integer.TYPE.toString(), "1")).getValue());
		assertNotEquals(0, (new Parameter(null, Integer.TYPE.toString(), 1)).getValue());

		assertEquals(0L, (new Parameter(null, Long.TYPE.toString())).getValue());
		assertEquals(1L, (new Parameter(null, Long.TYPE.toString(), 1L)).getValue());
		assertEquals(1L, (new Parameter(null, Long.TYPE.toString(), "1")).getValue());
		assertNotEquals(0L, (new Parameter(null, Long.TYPE.toString(), 1L)).getValue());
	}

	@Test
	public void constructorValueTest() throws ClassNotFoundException {
		Object[] objectArray = new Object[] {true, "false", 1d, 1L, 1, "1"};
		List<Object> objectList = Arrays.asList(objectArray);

		assertEquals(objectArray, (new Parameter(null, Object[].class, objectArray)).getValue());
		assertEquals(objectList, (new Parameter(null, List.class, objectList)).getValue());

		String[] stringArray = new String[] {"true", "false", "1"};
		List<String> stringList = Arrays.asList(stringArray);

		assertEquals(stringArray, (new Parameter(null, String[].class, stringArray)).getValue());
		assertEquals(stringList, (new Parameter(null, List.class, stringList)).getValue());
	}

}
