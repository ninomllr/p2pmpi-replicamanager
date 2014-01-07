package Board;

import java.io.Serializable;

public class BoardEntry implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String title;
	private String content;
	private BoardEntry parent;
	private BoardEntry child;
	
	public BoardEntry(int userId, String title, String content) {
		setContent(content);
		setTitle(title);
		setUserId(userId);
		setChild(null);
		setParent(null);
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public BoardEntry getParent() {
		return parent;
	}

	public void setParent(BoardEntry parent) {
		this.parent = parent;
	}
	
	public BoardEntry getChild() {
		return child;
	}

	public void setChild(BoardEntry child) {
		this.child = child;
	}

}
