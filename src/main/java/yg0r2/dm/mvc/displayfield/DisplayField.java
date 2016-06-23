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
package yg0r2.dm.mvc.displayfield;

import java.util.List;

import yg0r2.dm.util.Pair;

/**
 * @author Yg0R2
 */
public final class DisplayField {

	private FieldType _fieldType;
	private String _id;
	private boolean _required;
	private Object _value;
	private boolean _visible;

	/**
	 * Constructor with the following parameters:</br>
	 * (note: the field is an <i>input</> field, with <i>empty</i> String value, and it is <i>required</i> and
	 * <i>visible</i>)
	 *
	 * @param id
	 * @throws IllegalDisplayFieldException
	 */
	public DisplayField(String id) {
		this(id, FieldType.INPUT, null, true, true);
	}

	/**
	 * Constructor with the following parameters:</br>
	 * (note: the field value is an <i>empty</i> String, and <i>required</i> and <i>visible</i>)
	 * 
	 * @param id
	 * @param fieldType
	 * @throws IllegalDisplayFieldException
	 */
	public DisplayField(String id, FieldType fieldType) {
		this(id, fieldType, null, true, true);
	}

	/**
	 * Constructor with the following parameters:</br>
	 * (note: the field value is an <i>empty</i> String, and <i>visible</i>)
	 * 
	 * @param id
	 * @param fieldType
	 * @param required
	 * @throws IllegalDisplayFieldException
	 */
	public DisplayField(String id, FieldType fieldType, boolean required) {
		this(id, fieldType, null, required, true);
	}

	/**
	 * Constructor with the following parameters:</br>
	 * (note: the field is <i>required</i> and <i>visible</i>)
	 * 
	 * @param id
	 * @param fieldType
	 * @param value
	 * @throws IllegalDisplayFieldException
	 */
	public DisplayField(String id, FieldType fieldType, Object value) {
		this(id, fieldType, value, true, true);
	}

	/**
	 * Constructor with the following parameters:</br>
	 * (note: the field is <i>visible</i>)
	 * 
	 * @param id
	 * @param fieldType
	 * @param value
	 * @param required
	 * @throws IllegalDisplayFieldException
	 */
	public DisplayField(String id, FieldType fieldType, Object value, boolean required) {
		this(id, fieldType, value, required, true);
	}

	/**
	 * Constructor with the following parameters:
	 *
	 * @param id
	 * @param fieldType
	 * @param value
	 * @param required
	 * @param visible
	 * @throws IllegalDisplayFieldException
	 */
	public DisplayField(String id, FieldType fieldType, Object value, boolean required, boolean visible) {
		_id = id;

		_required = required;

		_fieldType = fieldType;

		if (value == null) {
			_value = "";
		}
		else {
			_value = value;
		}

		_visible = true;
	}

	/**
	 * @return the fieldType
	 */
	public FieldType getFieldType() {
		return _fieldType;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return _id;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return String.valueOf(_value);
	}

	/**
	 * @return the values list
	 */
	@SuppressWarnings("unchecked")
	public List<Pair<String, String>> getValues() {
		return (List<Pair<String, String>>) _value;
	}

	/**
	 * @return is required
	 */
	public boolean isRequired() {
		return _required;
	}

	/**
	 * @return is visible
	 */
	public boolean isVisible() {
		return _visible;
	}

}
