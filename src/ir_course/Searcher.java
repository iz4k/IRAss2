package ir_course;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.similarities.Similarity;
import org.tartarus.snowball.ext.EnglishStemmer;

/**
 * Extended IndexSearcher to search our documents with the given queries and settings
 */
public class Searcher extends IndexSearcher {

	private EnglishStemmer ps;
	
	public Searcher(IndexReader r, Similarity similarity) {
		super(r);
		this.setSimilarity(similarity);
		this.ps = new EnglishStemmer();
	}
	
	/**
	 * Search the index for with the given query and settings
	 * 
	 * @param query Query string
	 * @param useStemmer use EnglishStemmer or not
	 * @param useStopWords use stop words or not
	 * @return List of results
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public ArrayList<Document> search(String query, boolean useStemmer, boolean useStopWords) throws IOException, ParseException {
		
		ArrayList<Document> results = new ArrayList<Document>();
		BooleanQuery bq = new BooleanQuery();
		String[] words = query.split(" ");
		
		CharArraySet stopWords = StopAnalyzer.ENGLISH_STOP_WORDS_SET; // Using Lucene default stopword list
		
		// loop the words in the query
		for (String word : words) {
			
			// check if the word is in stopword list
			if (useStopWords && stopWords.contains(word)) {
				continue;
			}
			
			// stem the word
			if (useStemmer) {
				ps.setCurrent(word);
				ps.stem();
		        word = ps.getCurrent();
			}
			
			// add the word to our query
			bq.add(new TermQuery(new Term("abstract", word)), Occur.SHOULD);
		}
		
		bq.add(new MatchAllDocsQuery(), Occur.SHOULD);
		
	    ScoreDoc[] hits;    
		hits = this.search(bq, Integer.MAX_VALUE).scoreDocs;
		
		// Iterate through the results and return a list of the contained documents
	    for (ScoreDoc hit : hits) {
	    	results.add(this.doc(hit.doc));
		}
	    return results;
	}
}
