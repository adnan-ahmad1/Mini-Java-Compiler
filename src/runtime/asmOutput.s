    .text
    .globl  asm_main

runtime_error_exit:
    call runtime_error

asm_main:
    pushq %rbp           # Prologue
    movq %rsp,%rbp

    movq $16,%rdi                # New object declaration
    call mjcalloc                # Allocate space and return pointer in %rax
    leaq Two$$(%rip),%rdx                # Load class vtable into %rdx
    movq %rdx,0(%rax)            # Load vtable at the beginning of %rax

    movq %rax,%rdi               # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax)                # Call variable's method

    movq %rax,%rdi               # Print
    call put

    movq %rbp,%rsp               # Epilogue
    popq %rbp
    ret

Two$first:
    pushq %rbp           # Prologue
    movq %rsp,%rbp

    subq $8,%rsp
    subq $8,%rsp                 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp)           # Move variable onto stack
    movq $5,%rax                 # Integer Literal
    cmpq $0,%rax
    jl runtime_error_exit
    addq $1,%rax
    imulq $8,%rax
    movq %rax,%rdi               # New array declaration
    call mjcalloc                # Allocate space and return pointer in %rax
    pushq %rax
    movq $5,%rax                 # Integer Literal
    popq %rdx
    movq %rax,0(%rdx)
    movq %rdx,%rax

    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,8(%rax)            # Assign to field
    movq $0,%rax                 # Integer Literal
    pushq %rax
    movq $1,%rax                 # Integer Literal
    pushq %rax
    movq -8(%rbp),%rax
    movq 8(%rax),%rax
    popq %rdx
    popq %rcx
    cmpq (%rax),%rcx
    jge runtime_error_exit
    cmpq $0,%rcx
    jl runtime_error_exit
    imulq $8,%rcx
    addq %rcx,%rax
    addq $8,%rax
    movq %rdx,(%rax)
    movq $1,%rax                 # Integer Literal
    pushq %rax
    movq $2,%rax                 # Integer Literal
    pushq %rax
    movq -8(%rbp),%rax
    movq 8(%rax),%rax
    popq %rdx
    popq %rcx
    cmpq (%rax),%rcx
    jge runtime_error_exit
    cmpq $0,%rcx
    jl runtime_error_exit
    imulq $8,%rcx
    addq %rcx,%rax
    addq $8,%rax
    movq %rdx,(%rax)
    movq $3,%rax                 # Integer Literal
    pushq %rax
    movq $4,%rax                 # Integer Literal
    pushq %rax
    movq -8(%rbp),%rax
    movq 8(%rax),%rax
    popq %rdx
    popq %rcx
    cmpq (%rax),%rcx
    jge runtime_error_exit
    cmpq $0,%rcx
    jl runtime_error_exit
    imulq $8,%rcx
    addq %rcx,%rax
    addq $8,%rax
    movq %rdx,(%rax)
    movq -8(%rbp),%rax
    movq 8(%rax),%rax
    pushq %rax
    movq $0,%rax                 # Integer Literal
    popq %rdx
    cmpq (%rdx),%rax
    jge runtime_error_exit
    cmpq $0,%rax
    jl runtime_error_exit
    imulq $8,%rax                # Array Lookup
    addq %rax,%rdx
    addq $8,%rdx
    movq (%rdx),%rax

    movq %rax,%rdi               # Print
    call put

    movq -8(%rbp),%rax
    movq 8(%rax),%rax
    pushq %rax
    movq $1,%rax                 # Integer Literal
    popq %rdx
    cmpq (%rdx),%rax
    jge runtime_error_exit
    cmpq $0,%rax
    jl runtime_error_exit
    imulq $8,%rax                # Array Lookup
    addq %rax,%rdx
    addq $8,%rdx
    movq (%rdx),%rax

    pushq %rax           # Plus
    movq -8(%rbp),%rax
    movq 8(%rax),%rax
    pushq %rax
    movq $3,%rax                 # Integer Literal
    popq %rdx
    cmpq (%rdx),%rax
    jge runtime_error_exit
    cmpq $0,%rax
    jl runtime_error_exit
    imulq $8,%rax                # Array Lookup
    addq %rax,%rdx
    addq $8,%rdx
    movq (%rdx),%rax

    popq %rdx
    addq %rdx,%rax

    movq %rax,%rdi               # Print
    call put

    movq -8(%rbp),%rax
    movq 8(%rax),%rax
    movq 0(%rax),%rax            # Array Length


    movq %rbp,%rsp               # Epilogue
    popq %rbp
    ret

    .data
    Two$$:
    .quad 0
    .quad Two$first
