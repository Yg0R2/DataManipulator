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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yg0R2
 */
public class ReflectUtil {

	private static Logger _logger = LoggerFactory.getLogger(ReflectUtil.class);

	/**
	 * If the given <i>value</i> is:
	 * <ul>
	 * <li>null, will return with the <i>defaultValue</i>.</li>
	 * <li><code>instanceOf String<code>, get the <code>String.valueOf(value)</code> and invoke <code>valueOf</code>
	 * method.</li>
	 * <li>not <code>instanceOf String<code>, simply use <code>Class.forName(className).cast(value)</code></li>
	 * </ul>
	 *
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T castValue(Class<T> clazz, Object value, Object defaultValue) {
		if ((value == null) && (defaultValue == null)) {
			return (T) null;
		}

		if (value == null) {
			// If value is null, we have to do the same steps with defaultValue.

			return castValue(clazz, defaultValue, null);
		}

		try {
			return (T) jodd.util.ReflectUtil.invoke(clazz, "valueOf", new Object[] { String.valueOf(value) });
		}
		catch (Exception e) {
			// Ignore
		}

		// If the required class is Integer, try get 'intValue()'.
		if (clazz.equals(Integer.class)) {
			try {
				return (T) jodd.util.ReflectUtil.invoke(value, "intValue", new Object[0]);
			}
			catch (Exception e) {
				// Ignore
			}
		}

		// If the required class is Long, try get 'longValue()'.
		if (clazz.equals(Long.class)) {
			try {
				return (T) jodd.util.ReflectUtil.invoke(value, "longValue", new Object[0]);
			}
			catch (Exception e) {
				// Ignore
			}
		}

		// If the required class is String, try get 'valueOf()'.
		if (clazz.equals(String.class)) {
			try {
				return (T) String.valueOf(value);
			}
			catch (Exception e) {
				// Ignore
			}
		}

		return (T) getType(clazz.getName()).cast(value);
	}

	/**
	 * Return a class of the given <i>className</i> parameter.
	 * 
	 * @param className
	 * @return
	 */
	public static Class<?> getType(String className) {
		if (className.equals(Boolean.TYPE.toString())) {
			return Boolean.TYPE;
		}
		else if (className.equals(Boolean.class.getName())) {
			return Boolean.class;
		}
		else if (className.equals(Double.TYPE.toString())) {
			return Double.TYPE;
		}
		else if (className.equals(Double.class.getName())) {
			return Double.class;
		}
		else if (className.equals(Integer.TYPE.toString())) {
			return Integer.TYPE;
		}
		else if (className.equals(Integer.class.getName())) {
			return Integer.class;
		}
		else if (className.equals(Long.TYPE.toString())) {
			return Long.TYPE;
		}
		else if (className.equals(Long.class.getName())) {
			return Long.class;
		}

		try {
			return Class.forName(className);
		}
		catch (ClassNotFoundException e) {
			_logger.error("Class not found, returns with Object.class.", e);
		}

		return Object.class;
	}

}
