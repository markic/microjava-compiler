package rs.etf.markic;

import java.io.*;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.etf.markic.util.Log4JUtils;

public class Scanner {

	public static class Errors
	{
		public static final String fileNotFound = "Error: Source file not found!";
		public static final String noArg = "Error: Source file name not specified!";
	}
	
	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args){
		
		Reader reader = null;
		PrintWriter writer = null;
		
		String inputFile = null;
		String outputFile = null;
		
		Logger log = Logger.getLogger(Scanner.class);
		
		boolean writeToFile = false;
		
		try {
			
			if(args.length == 0) throw new Exception(Errors.noArg); 
			else if(args.length > 1) writeToFile = true;
		
			inputFile = "test/" + args[0] + ".mj";
			
			if(writeToFile) outputFile = args[1];
			
			File sourceCode = new File(inputFile);
			if(!sourceCode.exists()) throw new Exception(Errors.fileNotFound); 
			
			log.info("Lexer: Source file: " + sourceCode.getAbsolutePath());
			
			reader = new BufferedReader(new FileReader(sourceCode));
			
			if(writeToFile) writer = new PrintWriter(new File(outputFile));
			
			Yylex lexer = new Yylex(reader);
			
			Symbol currToken = null;
			while ((currToken = lexer.next_token()).sym != sym.EOF) {
				if (currToken != null && currToken.value != null){
					String s = currToken.toString() + " " + currToken.value.toString();
					log.info(s);
					
					if(writeToFile) writer.println(s);
					else System.out.println(s);
				}
			}
		}
		catch(Exception e){	System.err.println(e.getMessage()); }
		finally {
			if (reader != null) try { reader.close(); } catch (IOException e1) {}
			if (writer != null) writer.close();
		}
	}
	
}
