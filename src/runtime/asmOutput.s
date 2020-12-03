    .text
    .globl  _asm_main
    
_asm_main:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    movq $32,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Run$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Run$run:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $16,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Two$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,8(%rax)
    movq $16,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Two$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,16(%rax)
    movq $16,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Three$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,24(%rax)
    movq -8(%rbp),%rax
    movq 8(%rax),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq -8(%rbp),%rax
    movq 16(%rax),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq -8(%rbp),%rax
    movq 16(%rax),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *16(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq -8(%rbp),%rax
    movq 24(%rax),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq -8(%rbp),%rax
    movq 24(%rax),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *16(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq -8(%rbp),%rax
    movq 24(%rax),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *24(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq $999,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
One$m1:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $11,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Two$m1:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $12,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Two$m2:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $22,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Three$getN:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $3,%rax 		 # Integer Literal
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,8(%rax)
    movq -8(%rbp),%rax
    movq 8(%rax),%rax
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
    .data
    One$$:
    .quad 0
    .quad One$m1
    Run$$:
    .quad 0
    .quad Run$run
    Two$$:
    .quad One$$
    .quad Two$m1
    .quad Two$m2
    Three$$:
    .quad Two$$
    .quad Two$m1
    .quad Two$m2
    .quad Three$getN
