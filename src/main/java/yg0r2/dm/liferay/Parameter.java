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

import java.util.Objects;

import com.google.common.annotations.VisibleForTesting;

import yg0r2.dm.util.ReflectUtil;

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

		_value = ReflectUtil.getValue(className, value);
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
	 * @param that
	 * @return
	 */
	@VisibleForTesting
	protected boolean equals(Parameter that) {
		if (!Objects.equals(this.getName(), that.getName())) {
			return false;
		}

		if (!Objects.equals(this.getType(), that.getType())) {
			return false;
		}

		return Objects.equals(this.getValue(), that.getValue());
	}

}
