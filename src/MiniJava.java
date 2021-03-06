import AST.Program;
import AST.Visitor.*;
import Parser.parser;
import Parser.sym;
import Scanner.scanner;
import Semantics.SemanticTable;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class MiniJava {
    public static void main(String[] args) {
        String filename;
        if (args.length == 1) {
            filename = args[0];
        } else {
            filename = args[1];
        }

        boolean crashed = false;

        try {
            // create a scanner on the input file
            ComplexSymbolFactory sf = new ComplexSymbolFactory();
            Reader in = new BufferedReader(new FileReader(filename));
            scanner s = new scanner(in, sf);
            parser p = new parser(s, sf);
            if (args[0].equals("-S")) {
                Symbol t = s.next_token();
                while (t.sym != sym.EOF) {

                    if (t.sym == sym.error) {
                        crashed = true;
                    }
                    // print each token that we scan
                    System.out.print(s.symbolToString(t) + " ");
                    t = s.next_token();
                }
            } else if (args[0].equals("-P")) {
                Symbol root;
                root = p.parse();
                Program program = (Program)root.value;
                program.accept(new PrettyPrintVisitor());

            } else if (args[0].equals("-A")) {
                Symbol root;
                root = p.parse();
                Program program = (Program)root.value;
                program.accept(new AbstractTreeVisitor());
            } else if (args[0].equals("-T")) {
                Symbol root;
                root = p.parse();
                Program program = (Program)root.value;
                FillGlobalTablesVisitor gVisitor = new FillGlobalTablesVisitor(new SemanticTable());
                program.accept(gVisitor);

                // first pass
                SemanticTable st = gVisitor.getSemanticTable();

                // second pass
                TypeCheckVisitor tVisitor = new TypeCheckVisitor(st);
                program.accept(tVisitor);

                // print table
                st = tVisitor.getSemanticTable();
                st.printTable();
                System.out.println();

                if (st.hasError()) {
                    System.out.println("DID NOT PASS SEMANTIC CHECKING");
                    System.exit(1);
                }

            } else {

                Symbol root;
                root = p.parse();
                Program program = (Program)root.value;
                FillGlobalTablesVisitor gVisitor = new FillGlobalTablesVisitor(new SemanticTable());
                program.accept(gVisitor);

                if (gVisitor.getSemanticTable().hasError()) {
                    // error handle
                    System.err.println("DID NOT PASS SEMANTIC CHECKING");
                    System.exit(1);
                }

                TypeCheckVisitor tc = new TypeCheckVisitor(gVisitor.getSemanticTable());
                program.accept(tc);

                if (tc.getSemanticTable().hasError()) {
                    // error handle
                    System.err.println("DID NOT PASS SEMANTIC CHECKING");
                    System.exit(1);
                }

                CodeGenVisitor visitor = new CodeGenVisitor(tc.getSemanticTable());
                program.accept(visitor);

            }
        } catch (Exception e) {
            // yuck: some kind of error in the compiler implementation
            // that we're not expecting (a bug!)
            System.err.println("Unexpected internal compiler error: " +
                    e.toString());
            // print out a stack dump
            e.printStackTrace();
            System.exit(1);
        }

        // if error was encountered, exit with 1
        if (crashed) {
            System.err.println("PROGRAM CRASHED");
            System.exit(1);
        }

        System.exit(0);
    }
}
