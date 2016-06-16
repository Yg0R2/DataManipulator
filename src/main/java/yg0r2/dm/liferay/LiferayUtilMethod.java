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

import com.google.common.annotations.VisibleForTesting;

/**
 * @author Yg0R2
 */
public class LiferayUtilMethod {

	private String _utilClassName;
	private String _methodName;
	private List<Parameter> _parameters;

	@SuppressWarnings("unchecked")
	public LiferayUtilMethod(String utilClassName, String methodName, Map<String, String>... parameters) {

		_parameters = new ArrayList<>();

		for (Map<String, String> parameter : parameters) {
			if (parameter.isEmpty()) {
				continue;
			}

			_parameters.add(new Parameter(parameter.get("name"), parameter.get("type"), parameter.get("value")));
		}

		_utilClassName = utilClassName;

		_methodName = methodName;
	}

	public String[] getParameterNames() {
		List<String> names = new ArrayList<>(_parameters.size());

		_parameters.forEach(p -> {
			names.add(p.getName());
		});

		return names.toArray(new String[names.size()]);
	}

	public Class<?>[] getParameterTypes() {
		List<Class<?>> types = new ArrayList<>(_parameters.size());

		_parameters.forEach(p -> {
			types.add(p.getType());
		});

		return types.toArray(new Class<?>[types.size()]);
	}

	public Object[] getParameterValues(Map<String, Object> presetParameters) {
		List<Object> values = new ArrayList<>(_parameters.size());

		for (Parameter parameter : _parameters) {
			if (presetParameters.containsKey(parameter.getName())) {
				values.add(presetParameters.get(parameter.getName()));
			}
			else {
				values.add(parameter.getValue());
			}
		}

		return values.toArray(new Object[values.size()]);
	}

	public Object invoke(Map<String, Object> presetParameters) throws Exception {
		Class<?> utilClass = Class.forName(_utilClassName);

		Method method = utilClass.getMethod(_methodName, getParameterTypes());

		return method.invoke(null, getParameterValues(presetParameters));
	}

	@VisibleForTesting
	protected List<Parameter> getParameters() {
		return _parameters;
	}

}
