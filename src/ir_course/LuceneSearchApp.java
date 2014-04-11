/*
 * Skeleton class for the Lucene search program implementation
 * Created on 2011-12-21
 * Jouni Tuominen <jouni.tuominen@aalto.fi>
 */

/*
 * precision on relevantit / ei relevantit suhde
 * recall on relevantit / kaikki relevantit suhde  
 */
package ir_course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class LuceneSearchApp {
	
	private Directory directory;
	private Analyzer analyzer;
	private int relevantDocCount = 0;
	
	private final int TASK_NUMBER = 19;
	private final String[] queries = {
			"power sensor saving energy",
			"fuel consumption reduction sensor",
			"reducing energy consumption sensor",
			"through sensors and energy consumption or save energy"
	};
	/*
	private final String[] queries = {
			"power sensor saving energy",
			"\"fuel consumption\" reduction sensor",
			"\"reducing energy consumption\" sensor",
			"(\"through sensors\") and (\"energy consumption\" or \"save energy\")"
	};
	*/
	
	public LuceneSearchApp() {
		directory = new RAMDirectory();
		analyzer = new StandardAnalyzer(Version.LUCENE_42);
	}
	
	public void index(List<DocumentInCollection> docs) throws IOException {
		
		// Store the index in memory:
		try{
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42, analyzer);
		    IndexWriter writer = new IndexWriter(directory, config);
		    
			// loop the list, add all fields to document
			for (final DocumentInCollection entry : docs) {
				Document doc = new Document();
			    doc.add(new Field("title", entry.getTitle(), TextField.TYPE_STORED));
			    doc.add(new Field("abstract", entry.getAbstractText(), TextField.TYPE_STORED));
			    doc.add(new Field("query", entry.getQuery(), TextField.TYPE_STORED));
			    doc.add(new IntField("tasknumber", entry.getSearchTaskNumber(), IntField.TYPE_STORED));
			    if (entry.isRelevant() /*&& entry.getSearchTaskNumber() == TASK_NUMBER */){
			    	doc.add(new IntField("relevance", 1 , IntField.TYPE_STORED));
			    	this.relevantDocCount += 1;
			    }
			    else {
			    	doc.add(new IntField("relevance", 0 , IntField.TYPE_STORED));
			    }
			    writer.addDocument(doc);
			}
			writer.close();    

		} catch (Exception e) {
            System.out.println("Couldn't create the index: " +e);
        }
	}
	
	/**
	 * Search the index
	 * @return list of results
	 * @throws IOException
	 */
	public void Print11PointTable(SearchResultList srl){
		
		
		for (Document d : srl){
			
			
		}
		
	}
	public List<String> search() throws IOException{
		
		List<String> results = new LinkedList<String>();		
	    DirectoryReader ireader;
	    
		try {
			ireader = DirectoryReader.open(directory);

			Searcher vsm = new Searcher(ireader, new DefaultSimilarity());
			Searcher bm25 = new Searcher(ireader, new BM25Similarity());
			
			List<SearchResultList> allResults = new ArrayList<SearchResultList>();
			
			
			for (String query : queries) {
				
				SearchResultList vsmResults = vsm.search(query, false);
				SearchResultList bm25Results = bm25.search(query, false);
				SearchResultList vsmResultsWithStemmer = vsm.search(query, true);
				SearchResultList bm25ResultsWithStemmer = bm25.search(query, true);
				
				System.out.println("Query: " + query + "\n");
				
				Print11PointTable(vsmResults);
				Print11PointTable(bm25Results);
				Print11PointTable(vsmResultsWithStemmer);
				Print11PointTable(bm25ResultsWithStemmer);
				
				
				
				
				/*
				System.out.println("Query: " + query);
				
				System.out.println(vsmResults.get(0).get("title"));
				System.out.println(vsmResults2.get(0));
				System.out.println(bm25results.get(0));
				System.out.println(bm25results2.get(0));
				*/
			}
			
		    ireader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			LuceneSearchApp engine = new LuceneSearchApp();		
			DocumentCollectionParser docParser = new DocumentCollectionParser();
			docParser.parse(args[0]);
			List<DocumentInCollection> docs = docParser.getDocuments();
			engine.index(docs);
			engine.search();
			
		}
		else
			System.out.println("ERROR: the path of a RSS Feed file has to be passed as a command line argument.");
	}
}












