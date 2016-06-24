package yg0r2.liferay.mock;

/**
 * @author Yg0R2
 */
public class MockLiferayEntry {

	private long _entryId;
	private String _title;
	private String _content;

	public MockLiferayEntry(String title, String content) {
		_entryId = 1;
		_title = title;
		_content = content;
	}

	public long getEntryId() {
		return _entryId;
	}

	public String getTitle() {
		return _title;
	}

	public String getContent() {
		return _content;
	}

	public long getParentEntryId() {
		return 0;
	}

}
