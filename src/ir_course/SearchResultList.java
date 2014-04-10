package ir_course;

import java.util.ArrayList;

/**
 * Extended list to store the search results that includes relevant result count
 */
public class SearchResultList extends ArrayList<String> {
	private static final long serialVersionUID = 1L;
	private int resultCount;
	
	public SearchResultList() {
		this.resultCount = 0;
	}
	
	public boolean add(String s) {
		this.resultCount++;
		return super.add(s);
	}
	
	/**
	 * Get number of relevant results
	 */
	public int getResultCount() {
		return resultCount;
	}
}