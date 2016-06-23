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

/**
 * @author Yg0R2
 */
public final class ReflectUtil {

	/**
	 * If the given <i>value</i> is:
	 * <ul>
	 * <li>null and the <i>defaultValue</i> is null, will return a casted null.</li>
	 * <li>null and the <i>defaultValue</i> is not null, the method will call itself as <code>value=defaultValue</code>
	 * </li>
	 * <li>not null, ties to invoke <code>valueOf</code> on it</li>
	 * <li>not null and <T> is <i>Integer.class</i>, ties to invoke <code>intValue</code> on it</li>
	 * <li>not null and <T> is <i>Long.class</i>, ties to invoke <code>longValue</code> on it</li>
	 * <li>not null and <T> is <i>String.class</i>, ties to invoke <code>String.valueOf</code> on it</li>
	 * <li>not null, simply use <code>Class.forName(className).cast(value)</code></li>
	 * </ul>
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T castValue(Class<T> clazz, Object value, Object defaultValue) throws ClassNotFoundException {
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
	 * @throws ClassNotFoundException
	 */
	public static Class<?> getType(String className) throws ClassNotFoundException {
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

		return Class.forName(className);
	}

	/**
	 * Based on the given <i>className</i> attribute it returns with the casted value of <i>value</i> parameter, or the
	 * default value of the class.
	 *
	 * @param className
	 * @param value
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Object getValue(String className, Object value) throws ClassNotFoundException {
		if (className.equals(Boolean.TYPE.toString()) || className.equals(Boolean.class.getName())) {
			return castValue(Boolean.class, value, false);
		}
		else if (className.equals(Double.TYPE.toString()) || className.equals(Double.class.getName())) {
			return castValue(Double.class, value, 0d);
		}
		else if (className.equals(Integer.TYPE.toString()) || className.equals(Integer.class.getName())) {
			return castValue(Integer.class, value, 0);
		}
		else if (className.equals(Long.TYPE.toString()) || className.equals(Long.class.getName())) {
			return castValue(Long.class, value, 0L);
		}

		return getType(className).cast(value);
	}

}
