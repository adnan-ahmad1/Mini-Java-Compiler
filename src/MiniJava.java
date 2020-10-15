import Parser.sym;
import Scanner.scanner;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;

public class MiniJava {
    public static void main(String[] args) {
        //System.out.println(Arrays.toString(args));
        String filename = "";
        if (args[0].equals("-S")) {
            filename = args[1];
        }

        boolean crashed = false;

        try {
            // create a scanner on the input file
            ComplexSymbolFactory sf = new ComplexSymbolFactory();
            Reader in = new BufferedReader(new FileReader(filename));
            scanner s = new scanner(in, sf);
            Symbol t = s.next_token();
            while (t.sym != sym.EOF) {

                if (t.sym == sym.error) {
                    crashed = true;
                }
                // print each token that we scan
                System.out.print(s.symbolToString(t) + " ");
                t = s.next_token();
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
