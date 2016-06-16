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
package yg0r2.dm.util.resolver;

import java.util.Map;

import jodd.util.StringTemplateParser.MacroResolver;

/**
 * @author Yg0R2
 */
public class MacroMapResolver implements MacroResolver {

	private Map<String, String> _fieldMap;

	public MacroMapResolver(Map<String, String> fieldMap) {
		_fieldMap = fieldMap;
	}

	@Override
	public String resolve(String macroName) {
		return _fieldMap.containsKey(macroName) ? _fieldMap.get(macroName) : "";
	}

}
