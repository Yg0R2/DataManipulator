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
package yg0r2.dm.util;

import java.lang.reflect.Array;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import yg0r2.dm.DataManipulatorRunnable;
import yg0r2.dm.entry.DataManipulatorEntry;

/**
 * @author Yg0R2
 */
public class Tmp {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(Array.newInstance(String.class, 0).getClass().getName());

		String beanId = "blog-entry";

		AbstractApplicationContext context = new ClassPathXmlApplicationContext("liferay-6.0.12-bean.xml");
		DataManipulatorEntry dme = context.getBean(beanId, DataManipulatorEntry.class);
		context.close();

		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter(anyString())).thenReturn("");
		when(request.getParameter("blog-entry-count")).thenReturn("1");
		when(request.getParameter("blog-entry-updateCount")).thenReturn("2");
		when(request.getParameter("blog-entry-depth")).thenReturn("3");
		when(request.getParameter("blog-entry-subCount")).thenReturn("4");

		when(request.getParameter("blog-entry1-count")).thenReturn("1");
		when(request.getParameter("blog-entry1-updateCount")).thenReturn("2");

		DataManipulatorRunnable runnabele = new DataManipulatorRunnable(dme, request);

		runnabele.run();
	}

}
