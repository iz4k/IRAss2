package ir_course;

import java.util.ArrayList;

import org.apache.lucene.document.Document;

/**
 * Extended list to store the search results that includes relevant result count
 */
public class SearchResultList extends ArrayList<Document> {
	private static final long serialVersionUID = 1L;
	private int resultCount;
	
	public SearchResultList() {
		this.resultCount = 0;
	}
	
	public boolean add(Document d, boolean isRelevant) {
		if (isRelevant)
			this.resultCount++;
		return super.add(d);
	}
	
	/**
	 * Get number of relevant results
	 */
	public int getResultCount() {
		return resultCount;
	}
	
	public double getRelevantResultPercentage() {
		return this.resultCount / (double)this.size();
	}
}