package rs.etf.markic;

import java.io.*;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.etf.markic.Scanner.Errors;
import rs.etf.markic.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;

/**
*
* @author Marin Markic
* 
*/

public class Compiler {
	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(Compiler.class);
		
		String sourceFile = null;
		Reader reader = null;

		try {
			if(args.length == 0) throw new Exception(Errors.noArg); 
			
			sourceFile = "test/" + args[0] + ".mj";
			
			File sourceCode = new File(sourceFile);
			if(!sourceCode.exists()) throw new Exception(Errors.fileNotFound); 

			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			reader = new BufferedReader(new FileReader(sourceCode));
			
			Yylex lexer = new Yylex(reader);
			
			MJParser p = new MJParser(lexer);
			p.parse();
	        
			
			if(!p.errorDetected)
			{
		        log.info("-----------SYNTAX ANALYSIS----------");
		        log.info(p.classDefCount + " class definitions");
		        log.info(p.classFieldCount + " class fields");
		        log.info(p.classMethodCount + " class methods");
		        log.info(p.classExtendedCount + " extended class");
		        log.info(p.newObjectCount + " new object instantiations");
		        log.info(p.statementBlockCount + " blocks of statement");
		        log.info(p.globalArrayCount + " global arrays");
		        log.info(p.globalVarCount + " global variables");
		        log.info(p.globalFunctionCount + " global functions");
		        log.info(p.fcallInMainCount + " functions calls in main");
		        

		        
		        // generate code
		        File objFile = new File("test/program.obj");
		       
		        if (objFile.exists()) objFile.delete();	
		        
		        Code.write( new FileOutputStream(objFile) );
	        	
		        log.info("Parsing successful! No errors detected.");  
	        }
			else log.info("Parsing unsuccessful! Errors detected!!!");
			
	        ModernSymbolTableVisitor m = new ModernSymbolTableVisitor();
	        MJParser.dump(m);


		}
		catch(Exception e)
		{	
			System.err.println(e.getMessage()); 
			e.printStackTrace();
		}
		finally {
			if (reader != null) try { reader.close(); } catch (IOException e1) {}
		}
	}
}
