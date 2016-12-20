import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 
 * @author lxw
 *
 */
public class SplitWord {
	public StringBuilder text = new StringBuilder();
	public StringBuilder result = new StringBuilder();
	public String resPath;
	/**
	 * 读文件。
	 */
	public void readTextFromFile() {
		try {
			resPath = new File("").getCanonicalPath().toString() + "\\res\\";
			BufferedReader reader = new BufferedReader(new FileReader(new File(resPath + "text.txt")));
			String line;
			while ((line=reader.readLine()) != null) {
				text.append(line);
			}
			
			reader.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 切词处理。
	 */
	public void splitWord() {
		readTextFromFile();
		Analyzer analyzer = new IKAnalyzer(false);
		StringReader reader = new StringReader(text.toString());
		TokenStream ts = analyzer.tokenStream("", reader);  
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class); 
        try {
			while(ts.incrementToken()){  
				result.append(term.toString()+" ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        analyzer.close();
        reader.close();  
        writeResultToFile();
	}
	
	
	/**
	 * 写结果。
	 */
	public void writeResultToFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(resPath + "result.txt")));
			writer.write(result.toString().trim());
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws Exception {
		new SplitWord().splitWord();
	}

}