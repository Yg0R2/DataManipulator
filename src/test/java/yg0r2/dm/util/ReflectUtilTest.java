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
package yg0r2.dm.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * @author Yg0R2
 */
public class ReflectUtilTest {

	@Test
	public void castArrayTest() {
		Object[] objectArray = new Object[] { true, "false", 1, 1D, 1L };

		assertArrayEquals(objectArray, ReflectUtil.castValue(Object[].class, objectArray, null));

		String[] stringArray = new String[] { "true", "1", "1D", "1L" };

		assertArrayEquals(stringArray, ReflectUtil.castValue(String[].class, stringArray, null));
	}

	@Test
	public void castBooleanTest() {
		assertEquals(false, ReflectUtil.castValue(Boolean.class, "false", null));

		assertEquals(false, ReflectUtil.castValue(Boolean.class, 1, null));
		assertEquals(false, ReflectUtil.castValue(Boolean.class, 1D, null));
		assertEquals(false, ReflectUtil.castValue(Boolean.class, 1L, null));

		assertEquals(false, ReflectUtil.castValue(Boolean.class, false, null));

		assertEquals(false, ReflectUtil.castValue(Boolean.class, false, true));

		assertEquals(true, ReflectUtil.castValue(Boolean.class, null, true));

		assertEquals((Boolean) null, ReflectUtil.castValue(Boolean.class, null, null));
	}

	@Test
	public void castDoubleTest() {
		assertEquals(Double.valueOf(123), ReflectUtil.castValue(Double.class, "123", null));
		assertEquals(Double.valueOf(123), ReflectUtil.castValue(Double.class, "123D", null));
		// assertEquals(Double.valueOf(123), ReflectUtil.castValue(Double.class, "123L", null));

		assertEquals(Double.valueOf(123), ReflectUtil.castValue(Double.class, 123, null));
		assertEquals(Double.valueOf(123), ReflectUtil.castValue(Double.class, 123D, null));
		assertEquals(Double.valueOf(123), ReflectUtil.castValue(Double.class, 123L, null));

		assertEquals(Double.valueOf(123), ReflectUtil.castValue(Double.class, 123, 0));
		assertEquals(Double.valueOf(123), ReflectUtil.castValue(Double.class, 123D, 0D));
		assertEquals(Double.valueOf(123), ReflectUtil.castValue(Double.class, 123L, 0L));

		assertEquals(Double.valueOf(123), ReflectUtil.castValue(Double.class, null, 123));
		assertEquals(Double.valueOf(123), ReflectUtil.castValue(Double.class, null, 123D));
		assertEquals(Double.valueOf(123), ReflectUtil.castValue(Double.class, null, 123L));

		assertEquals((Double) null, ReflectUtil.castValue(Double.class, null, null));
	}

	@Test
	public void castIntegerTest() {
		assertEquals(123, ReflectUtil.castValue(Integer.class, "123", null));

		assertEquals(123, ReflectUtil.castValue(Integer.class, 123, null));
		assertEquals(123, ReflectUtil.castValue(Integer.class, 123D, null));
		assertEquals(123, ReflectUtil.castValue(Integer.class, 123L, null));

		assertEquals(123, ReflectUtil.castValue(Integer.class, 123, 0));
		assertEquals(123, ReflectUtil.castValue(Integer.class, 123D, 0D));
		assertEquals(123, ReflectUtil.castValue(Integer.class, 123L, 0L));

		assertEquals(123, ReflectUtil.castValue(Integer.class, null, 123));
		assertEquals(123, ReflectUtil.castValue(Integer.class, null, 123D));
		assertEquals(123, ReflectUtil.castValue(Integer.class, null, 123L));

		assertEquals((Integer) null, ReflectUtil.castValue(Integer.class, null, null));
	}

	@Test
	public void castListTest() {
		List<Object> objectList = Arrays.asList(true, "false", 1, 1D, 1L);

		assertEquals(objectList, ReflectUtil.castValue(List.class, objectList, null));

		List<String> stringList = Arrays.asList("true", "1", "1D", "1L");

		assertEquals(stringList, ReflectUtil.castValue(List.class, stringList, null));
	}

	@Test
	public void castLongTest() {
		assertEquals(Long.valueOf(123), ReflectUtil.castValue(Long.class, "123", null));

		assertEquals(Long.valueOf(123), ReflectUtil.castValue(Long.class, 123, null));
		assertEquals(Long.valueOf(123), ReflectUtil.castValue(Long.class, 123D, null));
		assertEquals(Long.valueOf(123), ReflectUtil.castValue(Long.class, 123L, null));

		assertEquals(Long.valueOf(123), ReflectUtil.castValue(Long.class, 123, 0));
		assertEquals(Long.valueOf(123), ReflectUtil.castValue(Long.class, 123D, 0D));
		assertEquals(Long.valueOf(123), ReflectUtil.castValue(Long.class, 123L, 0L));

		assertEquals(Long.valueOf(123), ReflectUtil.castValue(Long.class, null, 123));
		assertEquals(Long.valueOf(123), ReflectUtil.castValue(Long.class, null, 123D));
		assertEquals(Long.valueOf(123), ReflectUtil.castValue(Long.class, null, 123L));

		assertEquals((Long) null, ReflectUtil.castValue(Long.class, null, null));
	}

	@Test
	public void castStingTest() {
		assertEquals("123", ReflectUtil.castValue(String.class, "123", null));

		assertEquals("123", ReflectUtil.castValue(String.class, 123, null));
		assertEquals("123.0", ReflectUtil.castValue(String.class, 123D, null));
		assertEquals("123", ReflectUtil.castValue(String.class, 123L, null));

		assertEquals("123", ReflectUtil.castValue(String.class, 123, 0));
		assertEquals("123.0", ReflectUtil.castValue(String.class, 123D, 0D));
		assertEquals("123", ReflectUtil.castValue(String.class, 123L, 0L));

		assertEquals("123", ReflectUtil.castValue(String.class, null, 123));
		assertEquals("123.0", ReflectUtil.castValue(String.class, null, 123D));
		assertEquals("123", ReflectUtil.castValue(String.class, null, 123L));

		assertEquals((String) null, ReflectUtil.castValue(String.class, null, null));
	}

	@Test
	public void getTypeTest() {
		assertEquals(Boolean.class, ReflectUtil.getType(Boolean.class.getName()));
		assertEquals(boolean.class, ReflectUtil.getType("boolean"));

		assertEquals(Double.class, ReflectUtil.getType(Double.class.getName()));
		assertEquals(double.class, ReflectUtil.getType("double"));

		assertEquals(Integer.class, ReflectUtil.getType(Integer.class.getName()));
		assertEquals(int.class, ReflectUtil.getType("int"));

		assertEquals(List.class, ReflectUtil.getType(List.class.getName()));
		// assertEquals(List.class, ReflectUtil.getType(ArrayList.class.getName()));

		assertEquals(Long.class, ReflectUtil.getType(Long.class.getName()));
		assertEquals(long.class, ReflectUtil.getType("long"));

		assertEquals(String.class, ReflectUtil.getType(String.class.getName()));

		assertEquals(Character.class, ReflectUtil.getType(Character.class.getName()));
		// assertEquals(char.class, ReflectUtil.getType("char"));

	}

}
