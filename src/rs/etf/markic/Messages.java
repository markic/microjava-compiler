package rs.etf.markic;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

/**
 *
 * @author Marin Markic
 * 
 */

public final class Messages {

	    // Syntax error messages ----------------------------------------------------------------------------------------------
	    public static class SyntaxErrors
	    {
	        public static final String globalErr = "Global variables syntax error.";
	        public static final String localErr = "Local variables syntax error.";
	        public static final String extendsErr = "Class extending syntax error.";
	        public static final String fieldsErr = "Class fields syntax error.";
	        public static final String formParsErr = "Formal parameters syntax error.";
	        public static final String assignErr = "Assign expresion syntax error.";
	        public static final String funcErr = "Function call syntax error.";
	        public static final String actualParsErr = "Function call parameters syntax error.";
	        public static final String condErr = "Condition syntax error.";
	        public static final String arrayIndexErr = "Array index syntax error.";
	    }

	    // Semantic error messages ----------------------------------------------------------------------------------------------
	    public static class SemanticErrors
	    {
	        private static String semErr = "Semantic error on line ";
	        public static String noTyperErr(String type, int line) { return semErr + line + ": Type '" + type + "' has not been found in symbol table."; }
	        public static String typerErr(String type, int line) { return semErr + line + ": Name '" + type + "' is not a valid type."; }
	        public static String nameNotDeclaredErr(String type, int line) { return semErr + line + ": Name '" + type + "' has not been declared."; }
	        public static String alreadyDeclared(String name, int line) { return semErr + line + ": Name '" + name + "' has already been declared."; }
	        public static String wrongType(String type, int line) { return semErr + line + ": Data type mismatch. Use valid value for '" + type + "'."; }
	        public static String selfInheritance(int line) { return semErr + line  + ": Class cannot extend itself." ; }
	        public static String cannotCompare(int line) { return semErr + line  + ": Cannot compare different data types."; }
	        public static String onlyInteger(int line) { return semErr + line  + ": Operand must be integer."; }
	        public static String onlyBoolean(int line) { return semErr + line  + ": Operand must be boolean."; }
	        public static String breakOutOfLoop(int line) { return semErr + line  + ": Keyword break can only be used in loop."; }
	        public static String wrongRetValue(String fname, int line) { return semErr + line  + ": Return type mismatch in function '" + fname + "'."; }
	        public static String noReturn(String fname, int line) { return semErr + line  + ": No return statement found in function '" + fname + "'."; }
	        public static String notObject(String fname, int line) { return semErr + line  + ": Cannot use '.' on non object type '" + fname + "'."; }
	        public static String memberNotFound(String fname, int line) { return semErr + line  + ": Class member '" + fname + "' not found."; }
	        public static String notFunction(String fname, int line) { return semErr + line  + ": '" + fname + "' is not function or method."; }
	        public static String wrongParams(String fname, int line) { return semErr + line  + ": Function '" + fname + "' call arguments do not match declaration."; }
	        public static String mainFunctionErr(int line) { return semErr + line  + ": Main function must return void and have no arguments."; }
	        public static String assigningToConst(String name, int line) { return semErr + line  + ": Cannot change value of constant '" + name + "'."; }
	        public static String voidAssign(int line) { return semErr + line  + ": Cannot assign void value."; }
	        public static String noMainDetected = "Semantic error: void main() not found.";

	    }

	    // Semantic info messages ----------------------------------------------------------------------------------------------
	    public static class SemanticMessages
	    {
	        public static String constVarDecl(Obj obj, int line) { return "Constant variable '" + obj.getName() + "' declared on line " + line + "." + objNodeToString(obj); }
	        public static String globalVarDecl(Obj obj, int line) { return "Global variable '" + obj.getName() + "' declared on line " + line + "." + objNodeToString(obj); }
	        public static String fieldDecl(Obj obj, int line) { return "Class field '" + obj.getName()  + "' declared on line " + line + "." + objNodeToString(obj); }
	        public static String localVarDecl(Obj obj, int line) { return "Local variable '" + obj.getName()  + "' declared on line " + line + "." + objNodeToString(obj); }
	        public static String classDecl(Obj obj, int line) { return "Class '" + obj.getName()  + "' declared on line " + line + "." + objNodeToString(obj); }
	        public static String functionCalled(Obj obj, int line) { return "Function '" + obj.getName()  + "' called on line " + line + "." + objNodeToString(obj); }
	        public static String methodCalled(Obj obj, int line) { return "Method '" + obj.getName()  + "' called on line " + line + "." + objNodeToString(obj); }
	        public static String symbolFound(Obj obj, int line) { return "Symbol '" + obj.getName()  + "' used on line " + line + "." + objNodeToString(obj); }

	    }
	    
	    
	 // Print obj node ----------------------------------------------------------------------------------------------
    private static String objNodeToString(Obj obj)
    {
        if (obj == null) return null;

        StringBuilder sb = new StringBuilder(" Obj node: ");

        // kind
        switch (obj.getKind()) 
        {
            case Obj.Con:  sb.append("Con "); break;
            case Obj.Var:  sb.append("Var "); break;
            case Obj.Type: sb.append("Type "); break;
            case Obj.Meth: sb.append("Meth "); break;
            case Obj.Fld:  sb.append("Fld "); break;
            case Obj.Prog: sb.append("Prog "); break;

        }
        
        sb.append(obj.getName());
        sb.append(": ");

        // type 
        switch (obj.getType().getKind()) 
        {
            case Struct.None:   sb.append("notype"); break;
            case Struct.Int:    sb.append("int"); break;
            case Struct.Char:   sb.append("char"); break;
            case Struct.Class:  sb.append("class"); break;
            case 5:             sb.append("bool"); break;
            case Struct.Array:  sb.append("Arr of ");
            
                switch (obj.getType().getElemType().getKind()) 
                {
                                case Struct.None:   sb.append("notype"); break;
                                case Struct.Int:    sb.append("int"); break;
                                case Struct.Char:   sb.append("char"); break;
                                case Struct.Class:  sb.append("class"); break;
                                case 5:             sb.append("bool"); break;
                                default: break;
                }
            
            default: break;
        }

        sb.append(", ");
        sb.append(obj.getAdr());
        sb.append(", ");
        sb.append(obj.getLevel());

        return sb.toString();
    }
}
