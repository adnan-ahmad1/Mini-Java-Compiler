    .text
    .globl  _asm_main

_runtime_error_exit:
    call _runtime_error

_asm_main:
    pushq %rbp           # Prologue
    movq %rsp,%rbp

    movq $3,%rax                 # Integer Literal
    pushq %rax           # Evaluate args and push on stack
    pushq %rax           # Push Dummy

    movq $4,%rax                 # Integer Literal
    addq $8,%rsp                 # Pop Dummy

    pushq %rax           # Evaluate args and push on stack
    movq $5,%rax                 # Integer Literal
    pushq %rax           # Evaluate args and push on stack
    pushq %rax           # Push Dummy

    movq $8,%rdi                 # New object declaration
    call _mjcalloc               # Allocate space and return pointer in %rax
    leaq Two$$(%rip),%rdx                # Load class vtable into %rdx
    movq %rdx,0(%rax)            # Load vtable at the beginning of %rax

    addq $8,%rsp                 # Pop Dummy

    popq %rcx            # Pop from stack into arg registers
    popq %rdx            # Pop from stack into arg registers
    popq %rsi            # Pop from stack into arg registers
    movq %rax,%rdi               # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax)                # Call variable's method

    movq %rax,%rdi               # Print
    call _put

    movq %rbp,%rsp               # Epilogue
    popq %rbp
    ret

Two$first:
    pushq %rbp           # Prologue
    movq %rsp,%rbp

    subq $8,%rsp
    subq $40,%rsp                # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp)           # Move variable onto stack
    movq %rsi,-16(%rbp)                  # Move variable onto stack
    movq %rdx,-24(%rbp)                  # Move variable onto stack
    movq %rcx,-32(%rbp)                  # Move variable onto stack
    movq -16(%rbp),%rax
    movq %rax,%rdi               # Print
    call _put

    movq -24(%rbp),%rax
    movq %rax,%rdi               # Print
    call _put

    movq -32(%rbp),%rax
    movq %rax,%rdi               # Print
    call _put

    pushq %rax           # Push Dummy

    movq -32(%rbp),%rax
    addq $8,%rsp                 # Pop Dummy

    pushq %rax           # Evaluate args and push on stack
    movq $8,%rdi                 # New object declaration
    call _mjcalloc               # Allocate space and return pointer in %rax
    leaq Three$$(%rip),%rdx              # Load class vtable into %rdx
    movq %rdx,0(%rax)            # Load vtable at the beginning of %rax

    popq %rsi            # Pop from stack into arg registers
    movq %rax,%rdi               # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax)                # Call variable's method

    movq %rax,-40(%rbp)                  # Assign to local var
    movq -40(%rbp),%rax
    movq %rax,%rdi               # Print
    call _put

    movq $1,%rax                 # Integer Literal

    movq %rbp,%rsp               # Epilogue
    popq %rbp
    ret

Three$m1:
    pushq %rbp           # Prologue
    movq %rsp,%rbp

    subq $8,%rsp
    subq $24,%rsp                # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp)           # Move variable onto stack
    movq %rsi,-16(%rbp)                  # Move variable onto stack
    movq -16(%rbp),%rax
    pushq %rax           # Evaluate args and push on stack
    pushq %rax           # Push Dummy

    movq $2,%rax                 # Integer Literal
    addq $8,%rsp                 # Pop Dummy

    pushq %rax           # Evaluate args and push on stack
    movq $8,%rdi                 # New object declaration
    call _mjcalloc               # Allocate space and return pointer in %rax
    leaq Four$$(%rip),%rdx               # Load class vtable into %rdx
    movq %rdx,0(%rax)            # Load vtable at the beginning of %rax

    popq %rdx            # Pop from stack into arg registers
    popq %rsi            # Pop from stack into arg registers
    movq %rax,%rdi               # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax)                # Call variable's method

    cmpq $0,%rax
    jl _runtime_error_exit
    addq $1,%rax
    imulq $8,%rax
    movq %rax,%rdi               # New array declaration
    call _mjcalloc               # Allocate space and return pointer in %rax
    pushq %rax
    pushq %rax           # Push Dummy

    movq -16(%rbp),%rax
    addq $8,%rsp                 # Pop Dummy

    pushq %rax           # Evaluate args and push on stack
    movq $2,%rax                 # Integer Literal
    pushq %rax           # Evaluate args and push on stack
    pushq %rax           # Push Dummy

    movq $8,%rdi                 # New object declaration
    call _mjcalloc               # Allocate space and return pointer in %rax
    leaq Four$$(%rip),%rdx               # Load class vtable into %rdx
    movq %rdx,0(%rax)            # Load vtable at the beginning of %rax

    addq $8,%rsp                 # Pop Dummy

    popq %rdx            # Pop from stack into arg registers
    popq %rsi            # Pop from stack into arg registers
    movq %rax,%rdi               # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax)                # Call variable's method

    popq %rdx
    movq %rax,0(%rdx)
    movq %rdx,%rax

    movq %rax,-24(%rbp)                  # Assign to local var
    movq $0,%rax                 # Integer Literal
    pushq %rax
    movq $0,%rax                 # Integer Literal
    popq %rdx
    movq -24(%rbp),%rcx                  # Array Assign local var
    cmpq (%rcx),%rdx
    jge _runtime_error_exit
    cmpq $0,%rdx
    jl _runtime_error_exit
    imulq $8,%rdx
    addq %rdx,%rcx
    addq $8,%rcx
    movq %rax,(%rcx)
    movq $1,%rax                 # Integer Literal
    pushq %rax
    movq $1,%rax                 # Integer Literal
    popq %rdx
    movq -24(%rbp),%rcx                  # Array Assign local var
    cmpq (%rcx),%rdx
    jge _runtime_error_exit
    cmpq $0,%rdx
    jl _runtime_error_exit
    imulq $8,%rdx
    addq %rdx,%rcx
    addq $8,%rcx
    movq %rax,(%rcx)
    movq $2,%rax                 # Integer Literal
    pushq %rax
    movq $2,%rax                 # Integer Literal
    popq %rdx
    movq -24(%rbp),%rcx                  # Array Assign local var
    cmpq (%rcx),%rdx
    jge _runtime_error_exit
    cmpq $0,%rdx
    jl _runtime_error_exit
    imulq $8,%rdx
    addq %rdx,%rcx
    addq $8,%rcx
    movq %rax,(%rcx)
    movq -24(%rbp),%rax
    pushq %rax
    movq $0,%rax                 # Integer Literal
    popq %rdx
    cmpq (%rdx),%rax
    jge _runtime_error_exit
    cmpq $0,%rax
    jl _runtime_error_exit
    imulq $8,%rax                # Array Lookup
    addq %rax,%rdx
    addq $8,%rdx
    movq (%rdx),%rax

    movq %rax,%rdi               # Print
    call _put

    movq -24(%rbp),%rax
    pushq %rax
    movq $1,%rax                 # Integer Literal
    popq %rdx
    cmpq (%rdx),%rax
    jge _runtime_error_exit
    cmpq $0,%rax
    jl _runtime_error_exit
    imulq $8,%rax                # Array Lookup
    addq %rax,%rdx
    addq $8,%rdx
    movq (%rdx),%rax

    movq %rax,%rdi               # Print
    call _put

    movq -24(%rbp),%rax
    pushq %rax
    movq $2,%rax                 # Integer Literal
    popq %rdx
    cmpq (%rdx),%rax
    jge _runtime_error_exit
    cmpq $0,%rax
    jl _runtime_error_exit
    imulq $8,%rax                # Array Lookup
    addq %rax,%rdx
    addq $8,%rdx
    movq (%rdx),%rax

    movq %rax,%rdi               # Print
    call _put

    movq $0,%rax                 # Integer Literal
    pushq %rax           # Minus
    movq $10,%rax                # Integer Literal
    popq %rdx
    subq %rax,%rdx
    movq %rdx,%rax


    movq %rbp,%rsp               # Epilogue
    popq %rbp
    ret

Four$m:
    pushq %rbp           # Prologue
    movq %rsp,%rbp

    subq $8,%rsp
    subq $24,%rsp                # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp)           # Move variable onto stack
    movq %rsi,-16(%rbp)                  # Move variable onto stack
    movq %rdx,-24(%rbp)                  # Move variable onto stack
    movq -16(%rbp),%rax

    movq %rbp,%rsp               # Epilogue
    popq %rbp
    ret

    .data
    Four$$:
    .quad 0
    .quad Four$m
    Two$$:
    .quad 0
    .quad Two$first
    Three$$:
    .quad 0
    .quad Three$m1
