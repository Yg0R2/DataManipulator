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

import jodd.bean.BeanUtil;
import jodd.bean.BeanUtilBean;

/**
 * @author Yg0R2
 */
public class LiferayEntry {

	private String _entryIdKey;
	private String _parentEntryIdKey;

	public LiferayEntry(String entryIdKey, String parentEntryIdKey) {
		_entryIdKey = entryIdKey;
		_parentEntryIdKey = parentEntryIdKey;
	}

	public String getEntryId(Object entry) {
		BeanUtil beanUtil = new BeanUtilBean();

		return beanUtil.getProperty(entry, _entryIdKey);
	}

	public String getEntryIdKey() {
		return _entryIdKey;
	}

	public String getParentEntryId(Object entry) {
		BeanUtil beanUtil = new BeanUtilBean();

		return beanUtil.getProperty(entry, _parentEntryIdKey);
	}

	public String getParentEntryIdKey() {
		return _parentEntryIdKey;
	}

}
