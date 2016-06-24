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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.google.common.annotations.VisibleForTesting;

import jodd.util.StringTemplateParser;
import yg0r2.dm.util.resolver.MacroMapResolver;

/**
 * This class represents a Liferay *[Local]ServiceUtil. With this, DataManipulator can call a method from a Liferay util
 * class.
 *
 * @author Yg0R2
 */
public class LiferayUtilMethod {

	private String _methodName;
	private List<Parameter> _parameters;
	private String _utilClassName;

	/**
	 * Create a new LiferayUtilMethod instance
	 *
	 * @param utilClassName
	 * @param methodName
	 * @param parameters
	 */
	public LiferayUtilMethod(String utilClassName, String methodName, List<Parameter> parameters) {
		_parameters = parameters;

		_utilClassName = utilClassName;

		_methodName = methodName;
	}

	/**
	 * Invoke the Lifera util method.
	 * 
	 * @param presetParameters the preset values, what you would like to use instead of the default ones.
	 * @return with the object, what the Lifera util method creates.
	 * @throws Exception If something goes wrong, exception can be thrown.
	 */
	public Object invoke(Map<String, Object> presetParameters) throws Exception {
		Assert.notNull(presetParameters);

		Class<?> utilClass = Class.forName(_utilClassName);

		Method method = utilClass.getMethod(_methodName, getParameterTypes());

		return method.invoke(null, getParameterValues(presetParameters));
	}

	/**
	 * @return the parameter names of the Liferay util method.
	 */
	protected String[] getParameterNames() {
		List<String> names = new ArrayList<>(_parameters.size());

		_parameters.forEach(p -> {
			names.add(p.getName());
		});

		return names.toArray(new String[names.size()]);
	}

	/**
	 * <b>ONLY FOR TESTING PURPOSE!</b><br />
	 * Never use this method in other places.
	 *
	 * @return
	 */
	@VisibleForTesting
	protected List<Parameter> getParameters() {
		return _parameters;
	}

	/**
	 * @return the parameter types of the Liferay util method.
	 */
	protected Class<?>[] getParameterTypes() {
		List<Class<?>> types = new ArrayList<>(_parameters.size());

		_parameters.forEach(p -> {
			types.add(p.getType());
		});

		return types.toArray(new Class<?>[types.size()]);
	}

	/**
	 * @param presetParameters the preset values, what you would like to use instead of the default ones.
	 * @return an array of the parameters, which are required for the Liferay util method.
	 */
	protected Object[] getParameterValues(Map<String, Object> presetParameters) {
		List<Object> values = new ArrayList<>(_parameters.size());

		StringTemplateParser templateParser = new StringTemplateParser();

		for (Parameter parameter : _parameters) {
			Object value = null;

			if (presetParameters.containsKey(parameter.getName())) {
				value = presetParameters.get(parameter.getName());
			}
			else {
				value = parameter.getValue();
			}

			if (value instanceof String) {
				String stringValue = String.valueOf(value);

				if (stringValue.contains("${") && stringValue.contains("}")) {
					value = templateParser.parse(stringValue, new MacroMapResolver(presetParameters));
				}
			}

			values.add(value);
		}

		return values.toArray(new Object[values.size()]);
	}

}
