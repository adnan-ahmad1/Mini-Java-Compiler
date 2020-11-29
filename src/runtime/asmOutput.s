    .text
    .globl  _asm_main
    
_asm_main:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    movq $100,%rax 		 # Integer Literal
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq $32,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Two$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
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
    
    subq $16,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq $1,%rax 		 # Integer Literal
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,-8(%rax)
    movq $2,%rax 		 # Integer Literal
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,-16(%rax)
    movq -8(%rbp),%rax
    movq -16(%rax),%rax
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,-24(%rax)
    movq -8(%rbp),%rax
    movq -8(%rax),%rax
    movq %rax,%rdi 		 # Print
    call _put
    
    movq -8(%rbp),%rax
    movq -16(%rax),%rax
    movq %rax,%rdi 		 # Print
    call _put
    
    movq -8(%rbp),%rax
    movq -24(%rax),%rax
    movq %rax,%rdi 		 # Print
    call _put
    
    movq -16(%rbp),%rax
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,-8(%rax)
    movq -8(%rbp),%rax
    movq -8(%rax),%rax
    movq %rax,%rdi 		 # Print
    call _put
    
    movq $5,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
    .data
    Two$$:
    .quad 0
    .quad Two$first
