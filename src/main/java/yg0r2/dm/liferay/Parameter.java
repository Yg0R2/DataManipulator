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

import com.google.common.annotations.VisibleForTesting;

import yg0r2.dm.util.ReflectUtil;
import yg0r2.dm.util.ReflectUtil.Cast;

/**
 * @author Yg0R2
 */
public final class Parameter {

	private String _name;
	private Class<?> _type;
	private Object _value;

	/**
	 * Create an instance of Parameter with the given parameters.
	 *
	 * @param name
	 * @param clazz
	 */
	public Parameter(String name, Class<?> clazz) {
		this(name, clazz.getName(), null);
	}

	/**
	 * Create an instance of Parameter with the given parameters.
	 *
	 * @param name
	 * @param className
	 */
	public Parameter(String name, String className) {
		this(name, className, null);
	}

	/**
	 * Create an instance of Parameter with the given parameters.
	 *
	 * @param name
	 * @param clazz
	 * @param value
	 */
	public Parameter(String name, Class<?> clazz, Object value) {
		this(name, clazz.getName(), value);
	}

	/**
	 * Create an instance of Parameter with the given parameters.
	 *
	 * @param name
	 * @param className
	 * @param value
	 */
	public Parameter(String name, String className, Object value) {
		_name = name;

		_type = ReflectUtil.getType(className);

		_value = _getValue(className, value);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @return the type
	 */
	public Class<?> getType() {
		return _type;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return _value;
	}

	/**
	 * For testing purpose only.<br />
	 * Check the given instance of Parameter is equals with the current one.
	 *
	 * @param parameter
	 * @return
	 */
	@VisibleForTesting
	protected boolean equals(Parameter parameter) {
		return this.getName().equals(parameter.getName()) && this.getType().equals(parameter.getType())
			&& (((this.getValue() == null) && (parameter.getValue() == null))
				|| this.getValue().equals(parameter.getValue()));
	}

	/**
	 * Based on the given <i>className</i> attribute it returns with the casted value of <i>value</i> parameter, or the
	 * default value of the class.
	 *
	 * @param className
	 * @param value
	 * @return
	 */
	private Object _getValue(String className, Object value) {
		if (className.equals(Boolean.TYPE.toString()) || className.equals(Boolean.class.getName())) {
			return (new Cast<>(Boolean.class)).castValue(value, false);
		}
		else if (className.equals(Double.TYPE.toString()) || className.equals(Double.class.getName())) {
			return (new Cast<>(Double.class)).castValue(value, 0d);
		}
		else if (className.equals(Integer.TYPE.toString()) || className.equals(Integer.class.getName())) {
			return (new Cast<>(Integer.class)).castValue(value, 0);
		}
		else if (className.equals(Long.TYPE.toString()) || className.equals(Long.class.getName())) {
			return (new Cast<>(Long.class)).castValue(value, 0L);
		}

		return ReflectUtil.getType(className).cast(value);
	}

}
