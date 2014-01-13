package Board;

import java.io.Serializable;
import java.util.*;

public class BoardEntry implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String title;
	private String content;
	private BoardEntry parent;
	private List<BoardEntry> child;
	
	public BoardEntry(int userId, String title, String content) {
		setContent(content);
		setTitle(title);
		setUserId(userId);
		setChild(new ArrayList<BoardEntry>());
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
	
	public List<BoardEntry> getChildren() {
		return child;
	}
	
	public BoardEntry getChild(int pos) {
		return child.get(pos);
	}

	public void addChild(BoardEntry child) {
		child.setParent(this);
		this.child.add(child);
	}
	
	public void setChild(List<BoardEntry> child) {
		this.child = child;
	}

}
