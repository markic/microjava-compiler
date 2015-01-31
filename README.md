# microjava-compiler
This was a project for the course ”Compilers” on my faculty. Compiler for Micro Java programing language. Compiler performs lexical, syntax and semantic analysis and after that generates code that executes on Micro Java virtual machine. Java cup parser generator and jFlex scanner are used. Developed in Java using Eclipse IDE.


Department of Computer Engineering and Information Theory.


School of Electrical Engineering, University of Belgrade, Serbia.


Developed by Marin Markić. No licence. October - December 2013.
- marinmarkic@mail.com


### Micro Java language
Language and this compiler supports following: constants, primitive types, strings, static variables, local variables, arrays, functions, classes with fields and methods, objects, class inheritance and polymorphism, dynamic binding, operators and special keywords such as len, print, read etc. Full language specification can be found in docs folder. Program source file extension is mj. Open test folder for micro java program examples.


### Usage example:
Write micro java program and place file inside test folder, for instance test/program.mj. Run compiler and pass filename 'program' as first argument. If program.mj does not contain syntax and semantical errors, file program.obj will be created and executed on micro java virtual machine. You can also run Scanner program for only lexical program analysis.