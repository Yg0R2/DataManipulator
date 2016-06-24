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
package yg0r2.dm;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import yg0r2.dm.DataManipulatorRunnable;
import yg0r2.dm.entry.DataManipulatorEntry;
import yg0r2.dm.util.RandomUtil;
import yg0r2.liferay.mock.MockLiferayEntry;

/**
 * @author Yg0R2
 */
@PrepareForTest(RandomUtil.class)
@RunWith(PowerMockRunner.class)
public class DataManipulatorRunnableTest {

	private HttpServletRequest _request = mock(HttpServletRequest.class);

	@Before
	public void beforeTest() {
		when(_request.getParameter(anyString())).thenReturn("");

		when(_request.getParameter("parent-entry-count")).thenReturn("1");
		when(_request.getParameter("parent-entry-updateCount")).thenReturn("2");
		when(_request.getParameter("parent-entry-depth")).thenReturn("3");
		when(_request.getParameter("parent-entry-subCount")).thenReturn("4");

		when(_request.getParameter("child-entry-count")).thenReturn("1");
		when(_request.getParameter("child-entry-updateCount")).thenReturn("2");
	}

	@Test
	public void addLiferayEntry() throws Exception {
		PowerMockito.mockStatic(RandomUtil.class, new Class<?>[0]);
		when(RandomUtil.nextString()).thenReturn("randomString");

		DataManipulatorEntry dmEntry = _getBean("child-entry");

		Map<String, Object> argsMap = new HashMap<>();
		argsMap.putAll(dmEntry.getEntrySpecificArgs());
		argsMap.put("counter", "1");

		DataManipulatorRunnable runnable = new DataManipulatorRunnable(dmEntry, _request);

		MockLiferayEntry generatedEntrey = (MockLiferayEntry) runnable.addLiferayEntry(null, argsMap);

		MockLiferayEntry entry = new MockLiferayEntry("1. Child Entry Title - randomString",
			"1. Child Entry Content - randomString");

		assertEquals(entry.getTitle(), generatedEntrey.getTitle());
		assertEquals(entry.getContent(), generatedEntrey.getContent());
	}

	@Test
	public void addSubLiferayEntries() throws Exception {
		PowerMockito.mockStatic(RandomUtil.class, new Class<?>[0]);
		when(RandomUtil.nextString()).thenReturn("randomString");

		DataManipulatorEntry dmEntry = _getBean("parent-entry");

		Map<String, Object> argsMap = new HashMap<>();
		argsMap.putAll(dmEntry.getEntrySpecificArgs());
		argsMap.put("counter", "1");

		DataManipulatorRunnable runnable = new DataManipulatorRunnable(dmEntry, _request);

		MockLiferayEntry parentEntry = (MockLiferayEntry) runnable.addLiferayEntry(null, argsMap);

		runnable.addSubLiferayEntries(parentEntry, argsMap, 2, 3);
	}

	@Test
	public void runChildEntryTest() throws Exception {
		DataManipulatorEntry dme = _getBean("child-entry");

		DataManipulatorRunnable runnable = new DataManipulatorRunnable(dme, _request);

		runnable.run();
	}

	@Test
	public void runParentEntryTest() throws Exception {
		DataManipulatorEntry dme = _getBean("parent-entry");

		DataManipulatorRunnable runnable = new DataManipulatorRunnable(dme, _request);

		runnable.run();
	}

	@Test
	public void updateLiferayEntry() throws Exception {
		PowerMockito.mockStatic(RandomUtil.class, new Class<?>[0]);
		when(RandomUtil.nextString()).thenReturn("randomString");

		DataManipulatorEntry dmEntry = _getBean("child-entry");

		Map<String, Object> argsMap = new HashMap<>();
		argsMap.putAll(dmEntry.getEntrySpecificArgs());
		argsMap.put("counter", "1");

		DataManipulatorRunnable runnable = new DataManipulatorRunnable(dmEntry, _request);

		MockLiferayEntry generatedEntrey = (MockLiferayEntry) runnable.addLiferayEntry(null, argsMap);

		argsMap.put("updatePrefix", "1. update on the ");

		MockLiferayEntry updatedEntry = (MockLiferayEntry) runnable.updateLiferayEntry(generatedEntrey, argsMap);

		MockLiferayEntry entry = new MockLiferayEntry("1. Child Entry Title - randomString",
			"1. Child Entry Content - randomString");

		assertNotEquals(entry.getTitle(), updatedEntry.getTitle());
		assertNotEquals(entry.getContent(), updatedEntry.getContent());

		MockLiferayEntry updateEntry = new MockLiferayEntry("1. update on the 1. Child Entry Title - randomString",
			"1. update on the 1. Child Entry Content - randomString");

		assertEquals(updateEntry.getTitle(), updatedEntry.getTitle());
		assertEquals(updateEntry.getContent(), updatedEntry.getContent());
	}

	private DataManipulatorEntry _getBean(String beanId) {
		try (AbstractApplicationContext context = new ClassPathXmlApplicationContext("test-bean.xml")) {
			return context.getBean(beanId, DataManipulatorEntry.class);
		}
	}

}
