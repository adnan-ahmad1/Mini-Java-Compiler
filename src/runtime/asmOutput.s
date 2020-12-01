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
    
    subq $8,%rsp
    subq $24,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $8,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq A$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    movq %rax,-16(%rbp)
    movq $8,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq B$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    movq %rax,-24(%rbp)
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    cmpq $0,%rax
    je t_else_1
    movq $1,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    jmp t_done_1
t_else_1:
    movq $2,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
t_done_1:
    movq -24(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    cmpq $0,%rax
    je t_else_2
    movq $3,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    jmp t_done_2
t_else_2:
    movq $4,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
t_done_2:
    movq -24(%rbp),%rax
    movq %rax,-16(%rbp)
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    cmpq $0,%rax
    je t_else_3
    movq $5,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    jmp t_done_3
t_else_3:
    movq $6,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
t_done_3:
    movq $0,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
A$m:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $8,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Animal$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
B$m:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $8,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Dog$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Animal$bark:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $0,%rax 		 # Boolean false
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Dog$bark:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $1,%rax 		 # Boolean true
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
    .data
    A$$:
    .quad 0
    .quad A$m
    B$$:
    .quad A$$
    .quad B$m
    T$$:
    .quad 0
    .quad T$t
    Animal$$:
    .quad 0
    .quad Animal$bark
    Dog$$:
    .quad Animal$$
    .quad Dog$bark
