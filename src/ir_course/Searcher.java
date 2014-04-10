package ir_course;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.similarities.Similarity;

/**
 * Extended IndexSearcher to search our documents with the given queries and SimilarityProviders
 */
public class Searcher extends IndexSearcher {

	public Searcher(IndexReader r, Similarity similarity) {
		super(r);
		this.setSimilarity(similarity);
	}
	
	/**
	 * Search the index for with the given query and limit
	 * 
	 * @param query Query string
	 * @param limit Number of results to limit to
	 * @return List of results
	 * @throws IOException 
	 */
	public SearchResultList search(String query, int limit) throws IOException {
		
		SearchResultList results = new SearchResultList();
		BooleanQuery bq = new BooleanQuery();
		String[] words = query.split(" ");
		
		for (String word : words) {
			bq.add(new TermQuery(new Term("abstract", word)), Occur.SHOULD);
		}
		bq.add(new MatchAllDocsQuery(), Occur.SHOULD);
		
	    ScoreDoc[] hits;    
		hits = this.search(bq, limit).scoreDocs;
		
		// Iterate through the results
	    for (ScoreDoc hit : hits) {
		      Document hitDoc = this.doc(hit.doc);
		      results.add(hitDoc.get("title"));
		}
	    return results;
	}
}
