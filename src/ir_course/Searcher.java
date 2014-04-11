package ir_course;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.Version;
import org.tartarus.snowball.ext.EnglishStemmer;

/**
 * Extended IndexSearcher to search our documents with the given queries and SimilarityProviders
 */
public class Searcher extends IndexSearcher {

	private EnglishStemmer ps;
	
	public Searcher(IndexReader r, Similarity similarity) {
		super(r);
		this.setSimilarity(similarity);
		this.ps = new EnglishStemmer();
	}
	
	/**
	 * Search the index for with the given query and limit
	 * 
	 * @param query Query string
	 * @param limit Number of results to limit to
	 * @return List of results
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public SearchResultList search(String query, boolean useStemmer) throws IOException, ParseException {
		
		SearchResultList results = new SearchResultList();
		BooleanQuery bq = new BooleanQuery();
		String[] words = query.split(" ");
		
		//QueryParser qp = new QueryParser(Version.LUCENE_42, "abstract", new StandardAnalyzer(Version.LUCENE_42));
		//Query q  = qp.parse(query);
		
		
		for (String word : words) {
			if (useStemmer) {
				ps.setCurrent(word);
				ps.stem();
				System.out.println(word + " -> " + ps.getCurrent());
		        word = ps.getCurrent();
			}
			bq.add(new TermQuery(new Term("abstract", word)), Occur.SHOULD);
		}
		
		bq.add(new MatchAllDocsQuery(), Occur.SHOULD);
		
	    ScoreDoc[] hits;    
		hits = this.search(bq, 5 /*Integer.MAX_VALUE*/).scoreDocs;
		
		// Iterate through the results
	    for (ScoreDoc hit : hits) {
		      Document hitDoc = this.doc(hit.doc);
		      results.add(hitDoc, Integer.parseInt(hitDoc.get("relevance")) == 1);
		      //System.out.println(hitDoc.get("relevance") + " - " + (Integer.parseInt(hitDoc.get("relevance")) == 1));
		}
	    return results;
	}
}
