    .text
    .globl  _asm_main

_asm_main:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp

    movq $3,%rax 		 # Integer Literal
    pushq %rax 		 # Evaluate args and push on stack
    popq %rsi 		 # Pop from stack into arg registers
    movq $8,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Two$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax

    movq %rax,%rdi 		 # Load variable's vtable
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method

    movq %rax,%rdi 		 # Print
    call _put

    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret

Two$first:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp

    subq 8,%rsp
    subq 8,%rsp 		 # Subtract space for variables to push on stack
    movq %rax,-8(%rbp)
    movq %rax,%rdi 		 # Print
    call _put

    movq $1,%rax 		 # Integer Literal
    addq 16,%rsp 		 # Remove space from top of stack frame

    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret

.data
Two$$:
    .quad 0
    .quad Two$first
