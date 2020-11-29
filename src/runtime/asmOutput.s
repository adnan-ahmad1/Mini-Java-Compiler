    .text
    .globl  _asm_main
    
_asm_main:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    movq $8,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Test$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *16(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
    Test$test:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $24,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $16,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq A$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    movq %rax,-24(%rbp)
    movq -24(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *16(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq -8(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,-16(%rbp)
    movq $1000,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
    Test$print:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $500,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    movq $1,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
    A$getA:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $2,%rax 		 # Integer Literal
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,-8(%rax)
    movq -8(%rbp),%rax
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
    A$getNum:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq -8(%rbp),%rax
    movq -8(%rax),%rax
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
    .data
    A$$:
    .quad 0
    .quad A$getNum
    .quad A$getA
    Test$$:
    .quad 0
    .quad Test$print
    .quad Test$test
