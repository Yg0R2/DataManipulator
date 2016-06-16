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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringTemplateParser;
import yg0r2.dm.mvc.displayfield.DisplayField;
import yg0r2.dm.mvc.displayfield.FieldType;

/**
 * @author Yg0R2
 */
public class JspUtil {

	protected static final String FIELD_TEMPLATE = "<${fieldType} ${idAttribute} ${requiredAttribute} ${typeAttribute} ${extraAttribute} ${valueAttribute}>${innerHTML}</${fieldType}>";

	public static String getFileds(List<DisplayField> displayFields) {
		StringBuilder sb = new StringBuilder();

		for (DisplayField displayField : displayFields) {

			Map<String, String> fieldMap = new HashMap<>();

			// Set 'fieldType'

			FieldType fieldType = displayField.getFieldType();

			if (fieldType == FieldType.MULTI_SELECT) {
				fieldMap.put("fieldType", "select");
				fieldMap.put("extraAttribute", "multiple='true'");
			}
			else {
				fieldMap.put("fieldType", "" + fieldType);
			}

			// Set fix values

			fieldMap.put("idAttribute", "id='" + displayField.getId() + "'");
			fieldMap.put("requiredAttribute", displayField.isRequired() ? "required='true'" : "");
			fieldMap.put("typeAttribute", displayField.isVisible() ? "" : "type='hidden'");

			// Set value(s) / innerHTML

			if ((fieldType == FieldType.MULTI_SELECT) || (fieldType == FieldType.SELECT)) {
				fieldMap.put("innerHTML", getSelectOptions(displayField.getValues()));
			}
			else {
				fieldMap.put("valueAttribute", "value='" + displayField.getValue() + "'");
			}

			// Pars the template

			sb.append(_getWrappedField(displayField.getId(), fieldMap));
		}

		return sb.toString();
	}

	public static String getSelectOptions(String[] keyValueArray) {
		StringBuilder sb = new StringBuilder(keyValueArray.length);

		for (String keyValue : keyValueArray) {
			sb.append(getSelectOption(keyValue, keyValue));
		}

		return sb.toString();
	}

	public static String getSelectOptions(List<Pair<String, String>> keyValuePairs) {
		StringBuilder sb = new StringBuilder(keyValuePairs.size());

		for (Pair<String, String> keyValuePair : keyValuePairs) {
			sb.append(getSelectOption(keyValuePair.getKey(), keyValuePair.getValue()));
		}

		return sb.toString();
	}

	public static StringBuilder getSelectOption(String label, String value) {
		StringBuilder sb = new StringBuilder(5);

		sb.append("<option value='");
		sb.append(value);
		sb.append("'>");
		sb.append(label);
		sb.append("</option>");

		return sb;
	}

	private static StringBuilder _getWrappedField(String label, Map<String, String> fieldMap) {
		StringBuilder sb = new StringBuilder();

		sb.append("<div class='field-wrapper'>");

		sb.append("<legend>");
		sb.append(label);
		sb.append("</legend>");

		StringTemplateParser templateParser = new StringTemplateParser();

		sb.append(templateParser.parse(FIELD_TEMPLATE, new MacroResolver(fieldMap)));

		sb.append("</div>");

		return sb;
	}
}

class MacroResolver implements jodd.util.StringTemplateParser.MacroResolver {

	private Map<String, String> _fieldMap;

	public MacroResolver(Map<String, String> fieldMap) {
		super();

		_fieldMap = fieldMap;
	}

	@Override
	public String resolve(String macroName) {
		return _fieldMap.containsKey(macroName) ? _fieldMap.get(macroName) : "";
	}

}