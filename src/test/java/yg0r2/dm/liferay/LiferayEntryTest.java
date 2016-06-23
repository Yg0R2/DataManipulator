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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import jodd.bean.BeanException;

/**
 * @author Yg0R2
 */
public class LiferayEntryTest {

	private Map<String, String> _args;

	@Before
	public void beforeTest() {
		_args = new HashMap<>();

		_args.put(LiferayEntry.ENTRY_ID_KEY, "entryIdValue");
		_args.put(LiferayEntry.PARENT_ENTRY_ID_KEY, "parentEntryIdValue");
	}

	@Test
	public void constructorTest() {
		LiferayEntry liferayEntry = new LiferayEntry(null);

		MockObject mockObject = new MockObject();

		liferayEntry.setEntryId(mockObject);

		assertEquals("entryIdValue", liferayEntry.getEntryId());
		assertNull(liferayEntry.getParentEntryId());

		liferayEntry.setParentEntryId(mockObject);

		assertEquals("parentEntryIdValue", liferayEntry.getParentEntryId());

		liferayEntry = new LiferayEntry(_args);

		assertEquals("entryIdValue", liferayEntry.getEntryId());
		assertEquals("parentEntryIdValue", liferayEntry.getParentEntryId());
	}

	@Test
	public void getterNullTest() {
		LiferayEntry liferayEntry = new LiferayEntry(null);

		assertNull(liferayEntry.getEntryId());
		assertNull(liferayEntry.getParentEntryId());
	}

	@Test
	public void getterSetterTest() {
		LiferayEntry liferayEntry = new LiferayEntry(_args);

		liferayEntry.set("key", 123);

		assertEquals(123, liferayEntry.get("key"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void setEntryIdNullObjectTest() {
		LiferayEntry liferayEntry = new LiferayEntry(null);

		liferayEntry.setEntryId(null);
	}

	@Test(expected = BeanException.class)
	public void setEntryIdNullTest() {
		LiferayEntry liferayEntry = new LiferayEntry(_args);

		liferayEntry.setEntryId(new Object());

		assertNull(liferayEntry.getEntryId());
	}

	@Test
	public void setParentEntryIdNullObjectTest() {
		LiferayEntry liferayEntry = new LiferayEntry(null);

		liferayEntry.setParentEntryId(null);

		assertNull(liferayEntry.getParentEntryId());
	}

	@Test(expected = BeanException.class)
	public void setParentEntryIdNullTest() {
		LiferayEntry liferayEntry = new LiferayEntry(_args);

		liferayEntry.setParentEntryId(new Object());

		assertNull(liferayEntry.getParentEntryId());
	}

}

class MockObject {

	private String _entryIdKey = "entryIdValue";
	private String _parentEntryIdKey = "parentEntryIdValue";

	public String getEntryIdKey() {
		return _entryIdKey;
	}

	public String getParentEntryIdKey() {
		return _parentEntryIdKey;
	}

}