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
package yg0r2.dm.liferay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import jodd.bean.BeanUtil;
import jodd.bean.BeanUtilBean;

/**
 * @author Yg0R2
 */
public class LiferayEntry {

	public static final String ENTRY_ID_KEY = "entryIdKey";
	public static final String PARENT_ENTRY_ID_KEY = "parentEntryIdKey";

	private Map<String, Object> _argsMap;

	public LiferayEntry(Map<String, String> argsMap) {
		if (argsMap != null) {
			_argsMap = new HashMap<>(argsMap);
		}
		else {
			_argsMap = new HashMap<>();
		}
	}

	public Object get(String argKey) {
		return _argsMap.get(argKey);
	}

	public Map<String, Object> getAll() {
		return _argsMap;
	}

	public Object getEntryId() {
		return _argsMap.get(ENTRY_ID_KEY);
	}

	public Object getParentEntryId() {
		return _argsMap.get(PARENT_ENTRY_ID_KEY);
	}

	public void setEntryId(Object entry) {
		Assert.notNull(entry);

		BeanUtil beanUtil = new BeanUtilBean();

		_argsMap.put(ENTRY_ID_KEY, beanUtil.getProperty(entry, ENTRY_ID_KEY));
	}

	public void setParentEntryId(Object entry) {
		if (entry == null) {
			return;
		}

		BeanUtil beanUtil = new BeanUtilBean();

		_argsMap.put(PARENT_ENTRY_ID_KEY, beanUtil.getProperty(entry, PARENT_ENTRY_ID_KEY));
	}

	public void set(String argKey, Object argValue) {
		_argsMap.put(argKey, argValue);
	}

}
