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
package yg0r2.dm.mvc;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;

import yg0r2.dm.entry.DataManipulatorEntry;
import yg0r2.dm.liferay.LiferayEntryUtil;
import yg0r2.dm.util.RequestUtil;

/**
 * @author Yg0R2
 */
public class DataManipulatorRunner implements Runnable {

	private static Logger _logger = LoggerFactory.getLogger(DataManipulatorRunner.class);

	private int _count;
	private int _depth;
	private DataManipulatorEntry _dmEntry;
	private HttpServletRequest _request;
	private int _subCount;
	private int _updateCount;

	public DataManipulatorRunner(DataManipulatorEntry dmEntry, final HttpServletRequest request) {
		_dmEntry = dmEntry;

		_request = request;

		init();
	}

	@Override
	public void run() {
		try {
			if (_count == 0) {
				runSubDataManipulatorEntry();

				return;
			}

			for (int i = 0; i < _count; i++) {
				_dmEntry.getLiferayEntry().set("counter", String.valueOf(i));

				Object entry = LiferayEntryUtil.addLiferayEntry(_dmEntry, null);

				for (int j = 0; j < _updateCount; j++) {
					_dmEntry.getLiferayEntry().set("updatePrefix", String.valueOf(j) + ". update on the ");

					entry = LiferayEntryUtil.updateLiferayEntry(_dmEntry, entry);

					_dmEntry.getLiferayEntry().set("updatePrefix", null);
				}

				runSubDataManipulatorEntry();

				LiferayEntryUtil.addSubLiferayEntries(_dmEntry, entry, _depth, _subCount);
			}
		}
		catch (Exception e) {
			_logger.error("The following exeption appeared during the executiof of this thread.", e);
		}
	}

	protected void init() {
		String beanId = _request.getParameter("beanId");

		_count = RequestUtil.getIntParameter(_request, beanId + "-count");
		_depth = RequestUtil.getIntParameter(_request, beanId + "-depth");
		_subCount = RequestUtil.getIntParameter(_request, beanId + "-sub-count");
		_updateCount = RequestUtil.getIntParameter(_request, beanId + "-update-count");
	}

	protected final void runSubDataManipulatorEntry() throws Exception {
		for (DataManipulatorEntry dmEntry : _dmEntry.getSubDataManipulatorEntries()) {
			new DataManipulatorRunner(dmEntry, _request).run();
		}
	}

}
