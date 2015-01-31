package rs.etf.markic;

import java_cup.runtime.Symbol;

%%

%{

	StringBuffer string = new StringBuffer();

	// create token from type
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// inserting information about token positon
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT
%xstate STRING

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROGRAM, yytext()); }
"const" 	{ return new_symbol(sym.CONST, yytext()); }
"class" 	{ return new_symbol(sym.CLASS, yytext()); }
"extends" 	{ return new_symbol(sym.EXTENDS, yytext()); }
"if" 		{ return new_symbol(sym.IF, yytext()); }
"else" 		{ return new_symbol(sym.ELSE, yytext()); }
"while" 	{ return new_symbol(sym.WHILE, yytext()); }
"read" 		{ return new_symbol(sym.READ, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"break" 	{ return new_symbol(sym.BREAK, yytext()); }
"return"	{ return new_symbol(sym.RETURN, yytext()); }
"new"		{ return new_symbol(sym.NEW, yytext()); }

"."   	{ return new_symbol(sym.DOT, yytext()); }
"," 	{ return new_symbol(sym.COMMA, yytext()); }
";" 	{ return new_symbol(sym.SEMI, yytext()); }
"(" 	{ return new_symbol(sym.LPAREN, yytext()); }
")" 	{ return new_symbol(sym.RPAREN, yytext()); }
"{" 	{ return new_symbol(sym.LBRACE, yytext()); }
"}" 	{ return new_symbol(sym.RBRACE, yytext()); }
"[" 	{ return new_symbol(sym.LBRACKET, yytext()); }
"]" 	{ return new_symbol(sym.RBRACKET, yytext()); }

"||"   	{ return new_symbol(sym.OR, yytext()); }
"&&"   	{ return new_symbol(sym.AND, yytext()); }
"=="   	{ return new_symbol(sym.EQUAL, yytext()); }
"!="   	{ return new_symbol(sym.NOTEQUAL, yytext()); }
">"   	{ return new_symbol(sym.GREATER, yytext()); }
">="   	{ return new_symbol(sym.GREATEREQUAL, yytext()); }
"<"   	{ return new_symbol(sym.LESS, yytext()); }
"<="   	{ return new_symbol(sym.LESSEQUAL, yytext()); }

"+"   	{ return new_symbol(sym.PLUS, yytext()); }
"-"   	{ return new_symbol(sym.MINUS, yytext()); }
"++"   	{ return new_symbol(sym.INC, yytext()); }
"--"   	{ return new_symbol(sym.DEC, yytext()); }
"*"   	{ return new_symbol(sym.MUL, yytext()); }
"/"   	{ return new_symbol(sym.DIV, yytext()); }
"%"   	{ return new_symbol(sym.MOD, yytext()); }
"="   	{ return new_symbol(sym.ASSIGN, yytext()); }

"void"   { return new_symbol(sym.VOID, yytext()); }
"true"	 { return new_symbol(sym.BOOLCONST, yytext()); }
"false"	 { return new_symbol(sym.BOOLCONST, yytext()); }


\"						{ string.setLength(0); yybegin(STRING); }
<STRING> "\r\n" 		{ System.err.println("Lexical Error: Quotes are not closed, line: "+(yyline+1)+"."); yybegin(YYINITIAL); }
<STRING> [^\n\r\"\\]+ 	{ string.append( yytext() ); }              
<STRING>  \\t           { string.append('\t'); }
<STRING>  \\n           { string.append('\n'); }
<STRING>  \\r           { string.append('\r'); }
<STRING>  \\\"          { string.append('\"'); }
<STRING>  \\            { string.append('\\'); }
<STRING>  \"            { yybegin(YYINITIAL);  return new_symbol(sym.STRCONST, string.toString()); }


[\'][\b-~][\'] { return new_symbol(sym.CHARCONST, yytext().charAt(1)); }

[0-9]+[a-z|A-Z|_]+	{ System.err.println("Lexical Error: '"+(yytext())+"' is not a valid identifier, line: "+(yyline+1)+", column: "+(yycolumn+1)+"."); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{ return new_symbol (sym.IDENT, yytext()); }
[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }


"//" 		     { yybegin(COMMENT); }
<COMMENT> .      { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

. { System.err.println("Lexical Error: Characters sequence: '"+(yytext())+"' is not recognized on line: "+(yyline+1)+", column: "+(yycolumn+1)+"."); }
