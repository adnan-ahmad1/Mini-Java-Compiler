    .text
    .globl  _asm_main
    
_asm_main:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    movq $8,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq T$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
T$t:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $16,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $24,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Dog$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    movq %rax,-16(%rbp)
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *32(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq $50,%rax 		 # Integer Literal
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *16(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *24(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *32(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq $1,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Animal$bark:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $0,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Animal$getNum:
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
    
Animal$setNum:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $16,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq -16(%rbp),%rax
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,-8(%rax)
    movq $100,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Dog$bark:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $10,%rax 		 # Integer Literal
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,-16(%rax)
    movq $1,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Dog$getShadowNum:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq -8(%rbp),%rax
    movq -16(%rax),%rax
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
    .data
    T$$:
    .quad 0
    .quad T$t
    Animal$$:
    .quad 0
    .quad Animal$bark
    .quad Animal$setNum
    .quad Animal$getNum
    Dog$$:
    .quad Animal$$
    .quad Dog$bark
    .quad Animal$setNum
    .quad Animal$getNum
    .quad Dog$getShadowNum
