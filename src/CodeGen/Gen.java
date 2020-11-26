package CodeGen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Gen {

    private File asmFile;
    private String pathname;
    private FileWriter fileWriter;

    public Gen(String pathname) throws IOException {
        this.pathname = pathname;
        asmFile = new File(pathname);
        asmFile.createNewFile();
        fileWriter = new FileWriter(asmFile);
        fileWriter.write("    .text\n");
        fileWriter.write("    .globl  _asm_main\n");
        gen("");
    }

    public void gen(String s) throws IOException {
        fileWriter.write(s + "\n");
    }

    public void genbin(String op, String src, String dst) throws IOException {
        fileWriter.write(op + " " + src + "," + dst + "\n");
    }

    public void genLabel(String L) throws IOException {
        fileWriter.write(L + ":\n");
    }

    public void finish() throws IOException {
        fileWriter.flush();
        fileWriter.close();
    }

    public void prologue() throws IOException {
        gen("    pushq %rbp \t\t # Prologue");
        genbin("    movq", "%rsp", "%rbp");
        gen("");
    }

    public void epilogue() throws IOException {
        genbin("    movq", "%rbp", "%rsp \t\t # Epilogue");
        gen("    popq %rbp");
        gen("    ret");
        gen("");
    }

    public void pushDummy() throws IOException{
        gen("    pushq %rax \t\t # Push Dummy");
        gen("");
    }

    public void popDummy() throws IOException {
        gen("    addq 8,%rsp \t\t # Pop Dummy");
        gen("");
    }
}
