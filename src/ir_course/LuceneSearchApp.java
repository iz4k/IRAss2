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
import org.apache.lucene.document.LongField;
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
import org.apache.lucene.util.Version;

public class LuceneSearchApp {
	
	public LuceneSearchApp() {

	}
	
	public void index(List<DocumentInCollection> docs) throws IOException {

		// implement the Lucene indexing here
		//Creating the index file
		IndexWriter writer = null;
		try{
			
            Directory dir = FSDirectory.open(new File("index"));

            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
            
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_42, analyzer);
            
            //make sure the old file is overwritten
            iwc.setOpenMode(OpenMode.CREATE);
            
            
            writer = new IndexWriter(dir, iwc);
            
		}catch (Exception e) {
            System.out.println("Couldnt open the index.. "+e);
        }
		
		//index the rssfeed list
		//loop the list of rssfeeddocs, add all fields to document
		for (final DocumentInCollection entry : docs){
			/*Document doc = new Document();
		    doc.add(new Field("title", entry.getTitle(), TextField.TYPE_STORED));
		    doc.add(new Field("description", entry.getDescription(), TextField.TYPE_STORED));
		    doc.add(new LongField("pubdate", entry.getPubDate().getTime(), Field.Store.YES));
		    writer.addDocument(doc);
		    */
		}
		writer.close();
	}
	
	public List<String> search() throws IOException{
		
		

		List<String> results = new LinkedList<String>();

		/*printQuery(inTitle, notInTitle, inDescription, notInDescription, startDate, endDate);
		
		// implement the Lucene search here
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
		}*/
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

			List<String> inTitle;
			List<String> notInTitle;
			List<String> inDescription;
			List<String> notInDescription;
			List<String> results;
			
			// 1) search documents with words "kim" and "korea" in the title
			inTitle = new LinkedList<String>();
			inTitle.add("kim");
			inTitle.add("korea");
			//results = engine.search(inTitle, null, null, null, null, null);
			//engine.printResults(results);
			
			// 2) search documents with word "kim" in the title and no word "korea" in the description
			inTitle = new LinkedList<String>();
			notInDescription = new LinkedList<String>();
			inTitle.add("kim");
			notInDescription.add("korea");
			//results = engine.search(inTitle, null, null, notInDescription, null, null);
			//engine.printResults(results);

			// 3) search documents with word "us" in the title, no word "dawn" in the title and word "" and "" in the description
			inTitle = new LinkedList<String>();
			inTitle.add("us");
			notInTitle = new LinkedList<String>();
			notInTitle.add("dawn");
			inDescription = new LinkedList<String>();
			inDescription.add("american");
			inDescription.add("confession");
			//results = engine.search(inTitle, notInTitle, inDescription, null, null, null);
			//engine.printResults(results);
			
			// 4) search documents whose publication date is 2011-12-18
			//results = engine.search(null, null, null, null, "2011-12-18", "2011-12-18");
			//engine.printResults(results);
			
			// 5) search documents with word "video" in the title whose publication date is 2000-01-01 or later
			inTitle = new LinkedList<String>();
			inTitle.add("video");
			//results = engine.search(inTitle, null, null, null, "2000-01-01", null);
			//engine.printResults(results);
			
			// 6) search documents with no word "canada" or "iraq" or "israel" in the description whose publication date is 2011-12-18 or earlier
			notInDescription = new LinkedList<String>();
			notInDescription.add("canada");
			notInDescription.add("iraq");
			notInDescription.add("israel");
			//results = engine.search(null, null, null, notInDescription, null, "2011-12-18");
			//engine.printResults(results);
		}
		else
			System.out.println("ERROR: the path of a RSS Feed file has to be passed as a command line argument.");
	}
}
