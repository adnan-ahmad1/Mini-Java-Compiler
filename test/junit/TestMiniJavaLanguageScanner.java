import Scanner.*;
import java.io.*;

import Parser.sym;
import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.charset.Charset;

import static org.junit.Assert.*;
import org.junit.Test;

/*
    This class shows one way to use JUnit for testing your compiler.

    NOTE: The single provided test case here is not designed for MiniJava!
    Use this as a starting point, but you will want to create tests
    that fit the MiniJava grammar and match whatever output format you choose
    (e.g. your chosen token names, parse table formats, etc).
    In later phases of the project, you may find it helpful to write test
    cases for Minijava.java itself rather than the underlying modules as is
    shown here.
*/
public class TestMiniJavaLanguageScanner {

    public static final String TEST_FILES_LOCATION = "test/resources/MiniJavaLanguageScanner/";
    public static final String TEST_FILES_INPUT_EXTENSION = ".java";
    public static final String TEST_FILES_EXPECTED_EXTENSION = ".expected";

    /*
        You may be able to reuse this private helper method for your own
        testing of the MiniJava scanner.
    */
    private void runScannerTestCase(String testCaseName) {
        try {
            FileInputStream input = new FileInputStream(TEST_FILES_LOCATION + testCaseName + TEST_FILES_INPUT_EXTENSION);
            String[] expected = new String(Files.readAllBytes(Paths.get(TEST_FILES_LOCATION, testCaseName + TEST_FILES_EXPECTED_EXTENSION)),
                    Charset.defaultCharset()).split(" ");

            ComplexSymbolFactory sf = new ComplexSymbolFactory();
            Reader in = new BufferedReader(new InputStreamReader(input));
            scanner s = new scanner(in, sf);
            Symbol t = s.next_token();
            int i = 0;
            while (t.sym != sym.EOF){
                // verify each token that we scan
                assertEquals(expected[i], s.symbolToString(t));
                t = s.next_token();
                i++;
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /*
        A single test case for simple arithmetic, showing how to use the
        helper function above (and the given folder organization).
    */
    @Test
    public void testEmpty() {
        runScannerTestCase("Empty");
    }

    @Test
    public void testAllOperatorOperations() {
        runScannerTestCase("AllOperatorOperations");
    }

    @Test
    public void testIfAndWhile() {
        runScannerTestCase("IfAndWhile");
    }

    @Test
    public void testComments() {
        runScannerTestCase("Comments");
    }

    @Test
    public void testObject() {
        runScannerTestCase("TestObject");
    }
}
