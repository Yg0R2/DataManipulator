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
package yg0r2.dm.mvc;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import yg0r2.dm.entry.DataManipulatorEntry;
import yg0r2.dm.liferay.LiferayEntryKey;
import yg0r2.dm.util.ReflectUtil;

/**
 * @author Yg0R2
 */
public class TmpMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String beanId = "blog-entry";

		AbstractApplicationContext context = new ClassPathXmlApplicationContext("liferay-6.0.12-bean.xml");
		DataManipulatorEntry dme = context.getBean(beanId, DataManipulatorEntry.class);
		context.close();

		System.out.println(dme);
		System.out.println(dme.getAddMethod().getParameterNames());

		System.out.println(dme.getEntrySpecificArgs().get(LiferayEntryKey.ENTRY_ID_KEY));
	}

}
