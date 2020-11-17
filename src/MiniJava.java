import AST.Program;
import AST.Visitor.AbstractTreeVisitor;
import AST.Visitor.FillGlobalTablesVisitor;
import AST.Visitor.TypeCheckVisitor;
import AST.Visitor.PrettyPrintVisitor;
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
        //System.out.println(Arrays.toString(args));
        String filename = args[1];

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
                //st.printTable();

                // second pass
                TypeCheckVisitor tVisitor = new TypeCheckVisitor(st);
                program.accept(tVisitor);

                st.printTable();
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
            System.exit(1);
        }

        System.exit(0);
    }
}
