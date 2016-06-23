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
package yg0r2.dm.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import yg0r2.dm.entry.DataManipulatorEntry;

/**
 * @author Yg0R2
 */
@Controller
@RequestMapping("/")
public class DataManipulatorController {

	@RequestMapping(value = "/1", method = RequestMethod.GET)
	public ModelAndView entryCount(@RequestParam(value = "beanId", required = true) String beanId) throws Exception {
		DataManipulatorEntry dme = _getDME(beanId);

		ModelAndView modelAndView = new ModelAndView("form");

		modelAndView.addObject("command", "");
		modelAndView.addObject("fields", dme.getDisplayFields());
		modelAndView.addObject("beanId", beanId);

		return modelAndView;
	}

	@RequestMapping(value = "/2", method = RequestMethod.POST)
	public ModelAndView main2(HttpServletRequest request) {
		String beanId = (String) request.getParameter("beanId");

		DataManipulatorEntry dme = _getDME(beanId);

		ModelAndView modelAndView = new ModelAndView("index");

		modelAndView.addObject("command", dme.getDisplayFields());

		return modelAndView;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView selectBean() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("liferay-6.0.12-bean.xml");

		String[] beanIds = context.getBeanNamesForType(DataManipulatorEntry.class);

		context.close();

		ModelAndView modelAndView = new ModelAndView("navigation");

		modelAndView.addObject("beanIds", beanIds);
		modelAndView.addObject("command", "");

		return modelAndView;
	}

	private DataManipulatorEntry _getDME(String beanId) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("liferay-6.0.12-bean.xml");
		DataManipulatorEntry dme = context.getBean(beanId, DataManipulatorEntry.class);
		context.close();

		return dme;
	}

}
