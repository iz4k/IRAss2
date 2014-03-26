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
			    doc.add(new IntField("relevance", (entry.isRelevant() ? 1 : 0), IntField.TYPE_STORED));
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
	public List<String> search() throws IOException{
		
		List<String> results = new LinkedList<String>();

		//printQuery(inTitle, notInTitle, inDescription, notInDescription, startDate, endDate);
		
		// implement the Lucene search here
		/*
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
		DirectoryReader ireader = DirectoryReader.open(FSDirectory.open(new File("index")));
		IndexSearcher searcher = new IndexSearcher(ireader);
		
		BooleanQuery bq = new BooleanQuery();
		QueryParser qparser = new QueryParser(Version.LUCENE_42, "title", analyzer);
		if (inTitle != null){
			for (final String title : inTitle){
				Query query = null;
				try {
					query = qparser.parse(title);
					bq.add(query, Occur.MUST);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
		}
		if (notInTitle != null){
			for (final String title : notInTitle){
				Query query = null;
				try {
					query = qparser.parse(title);
					bq.add(query, Occur.MUST_NOT);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
		}
		qparser = new QueryParser(Version.LUCENE_42, "description", analyzer);
		if (inDescription != null){
			for (final String desc : inDescription){
				Query query = null;
				try {
					query = qparser.parse(desc);
					bq.add(query, Occur.MUST);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
		}
		if (notInDescription != null){
			for (final String desc : notInDescription){
				Query query = null;
				try {
					query = qparser.parse(desc);
					bq.add(query, Occur.MUST_NOT);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
		}
		if (startDate!=null || endDate!=null){
			long min=0;
			long max=0;
			if (startDate == null){
				min = 0;
			}else{
				startDate += " 00:00";
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					Date d = f.parse(startDate);
					min = d.getTime();
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
			}
			if (endDate == null){
				max = 0;
			}else{
				endDate += " 23:59";
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					Date d = f.parse(endDate);
					max = d.getTime();
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Query query=null;
			if (min!=0 && max != 0){query = NumericRangeQuery.newLongRange("pubdate", min, max, true, true);}
			else if (min == 0 && max != 0){query = NumericRangeQuery.newLongRange("pubdate", null, max, true, true);}
			else if (min != 0 && max == 0){query = NumericRangeQuery.newLongRange("pubdate", min, null, true, true);}
			bq.add(query, Occur.MUST);
		}
		ScoreDoc[] hits = searcher.search(bq, null, 1000).scoreDocs;
		for (int i = 0; i < hits.length; i++){
			Document hitdoc = searcher.doc(hits[i].doc);
			results.add(hitdoc.get("title"));
		}
		*/
		return results;
	}
	
	public void printQuery() {
		/*System.out.print("Search (");
		if (inTitle != null) {
			System.out.print("in title: "+inTitle);
			if (notInTitle != null || inDescription != null || notInDescription != null || startDate != null || endDate != null)
				System.out.print("; ");
		}
		if (notInTitle != null) {
			System.out.print("not in title: "+notInTitle);
			if (inDescription != null || notInDescription != null || startDate != null || endDate != null)
				System.out.print("; ");
		}
		if (inDescription != null) {
			System.out.print("in description: "+inDescription);
			if (notInDescription != null || startDate != null || endDate != null)
				System.out.print("; ");
		}
		if (notInDescription != null) {
			System.out.print("not in description: "+notInDescription);
			if (startDate != null || endDate != null)
				System.out.print("; ");
		}
		if (startDate != null) {
			System.out.print("startDate: "+startDate);
			if (endDate != null)
				System.out.print("; ");
		}
		if (endDate != null)
			System.out.print("endDate: "+endDate);
		System.out.println("):");
	}
	
	public void printResults(List<String> results) {
		if (results.size() > 0) {
			Collections.sort(results);
			for (int i=0; i<results.size(); i++)
				System.out.println(" " + (i+1) + ". " + results.get(i));
		}
		else
			System.out.println(" no results");*/
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			LuceneSearchApp engine = new LuceneSearchApp();
			
			DocumentCollectionParser parser = new DocumentCollectionParser();
			parser.parse(args[0]);
			List<DocumentInCollection> docs = parser.getDocuments();
			engine.index(docs);

			// TODO Create searchers
			
			engine.search();
		}
		else
			System.out.println("ERROR: the path of a RSS Feed file has to be passed as a command line argument.");
	}
}
