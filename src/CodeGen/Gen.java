package CodeGen;
import java.io.IOException;

public class Gen {

    public Gen() {
        // direct to standard output
        System.out.println("    .text");
        System.out.println("    .globl  _asm_main");
        System.out.println();

        System.out.println("_runtime_error_exit:");
        System.out.println("    call _runtime_error");
        System.out.println();
    }

    public void gen(String s) {
        // direct to standard output
        System.out.println("    " + s);
    }

    public void genbin(String op, String src, String dst) {
        // direct to standard output
        System.out.println("    " + op + " " + src + "," + dst);
    }

    public void genLabel(String L) {
        // direct to standard output
        System.out.println(L + ":");
    }

    public void prologue() {
        gen("pushq %rbp \t\t # Prologue");
        genbin("movq", "%rsp", "%rbp");
        gen("");
    }

    public void epilogue() {
        genbin("movq", "%rbp", "%rsp \t\t # Epilogue");
        gen("popq %rbp");
        gen("ret");
        gen("");
    }

    public void pushDummy() {
        gen("pushq %rax \t\t # Push Dummy");
        gen("");
    }

    public void popDummy() {
        gen("addq $8,%rsp \t\t # Pop Dummy");
        gen("");
    }
}
