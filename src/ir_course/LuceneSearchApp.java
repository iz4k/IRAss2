/*
 * Skeleton class for the Lucene search program implementation
 * Created on 2011-12-21
 * Jouni Tuominen <jouni.tuominen@aalto.fi>
 */
package ir_course;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class LuceneSearchApp {
	
	private Directory directory;
	private Analyzer analyzer;
	private int relevantDocCount = 0;
	
	private final int TASK_NUMBER = 19;
	private final String[] queries = {
			"power sensor saving energy",
			"\"fuel consumption\" reduction sensor",
			"\"reducing energy consumption\" sensor",
			"(\"through sensors\") and (\"energy consumption\" or \"save energy\")"
	};
	
	public LuceneSearchApp() {
		directory = new RAMDirectory();
		analyzer = new StandardAnalyzer(Version.LUCENE_42);
	}
	
	public void index(List<DocumentInCollection> docs) throws IOException {
		
		// Store the index in memory:
		try{
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_CURRENT, analyzer);
		    IndexWriter writer = new IndexWriter(directory, config);
		    
			// loop the list, add all fields to document
			for (final DocumentInCollection entry : docs) {
				Document doc = new Document();
			    doc.add(new Field("title", entry.getTitle(), TextField.TYPE_STORED));
			    doc.add(new Field("abstract", entry.getAbstractText(), TextField.TYPE_STORED));
			    doc.add(new Field("query", entry.getQuery(), TextField.TYPE_STORED));
			    doc.add(new IntField("tasknumber", entry.getSearchTaskNumber(), IntField.TYPE_STORED));
			    if (entry.isRelevant() && entry.getSearchTaskNumber() == TASK_NUMBER ){
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
	

	
	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			LuceneSearchApp engine = new LuceneSearchApp();
			
			DocumentCollectionParser parser = new DocumentCollectionParser();
			parser.parse(args[0]);
			List<DocumentInCollection> docs = parser.getDocuments();
			engine.index(docs);

		}
		else
			System.out.println("ERROR: the path of a RSS Feed file has to be passed as a command line argument.");
	}
}
