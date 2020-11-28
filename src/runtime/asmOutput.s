    .text
    .globl  _asm_main

_asm_main:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp

    movq $10,%rax 		 # Integer Literal
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy

    movq $20,%rax 		 # Integer Literal
    addq $8,%rsp 		 # Pop Dummy

    pushq %rax 		 # Evaluate args and push on stack
    movq $30,%rax 		 # Integer Literal
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy

    movq $40,%rax 		 # Integer Literal
    addq $8,%rsp 		 # Pop Dummy

    pushq %rax 		 # Evaluate args and push on stack
    movq $8,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Two$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax

    popq %r8 		 # Pop from stack into arg registers
    popq %rcx 		 # Pop from stack into arg registers
    popq %rdx 		 # Pop from stack into arg registers
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

    subq $8,%rsp
    subq $88,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq %rdx,-24(%rbp) 		 # Move variable onto stack
    movq %rcx,-32(%rbp) 		 # Move variable onto stack
    movq %r8,-40(%rbp) 		 # Move variable onto stack
    movq $16,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Three$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax

movq %rax,-72(%rbp)
    movq -16(%rbp),%rax
movq %rax,-48(%rbp)
    movq -24(%rbp),%rax
movq %rax,-56(%rbp)
    movq -32(%rbp),%rax
movq %rax,-64(%rbp)
    movq -48(%rbp),%rax
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy

    movq -56(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy

    pushq %rax 		 # Evaluate args and push on stack
    movq -64(%rbp),%rax
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy

    movq -40(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy

    pushq %rax 		 # Evaluate args and push on stack
    movq -72(%rbp),%rax
    popq %r8 		 # Pop from stack into arg registers
    popq %rcx 		 # Pop from stack into arg registers
    popq %rdx 		 # Pop from stack into arg registers
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method

    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy

    movq $20,%rax 		 # Integer Literal
    addq $8,%rsp 		 # Pop Dummy

    pushq %rax 		 # Evaluate args and push on stack
    movq $8,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Five$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax

    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method

Three$t:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp

    subq $8,%rsp
    subq $88,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq %rdx,-24(%rbp) 		 # Move variable onto stack
    movq %rcx,-32(%rbp) 		 # Move variable onto stack
    movq %r8,-40(%rbp) 		 # Move variable onto stack
    movq -16(%rbp),%rax
    pushq %rax 		 # Minus
    movq -24(%rbp),%rax
    popq %rdx
    subq %rax,%rdx
    movq %rdx,%rax

    pushq %rax 		 # Minus
    movq -32(%rbp),%rax
    popq %rdx
    subq %rax,%rdx
    movq %rdx,%rax

    pushq %rax 		 # Minus
    movq -40(%rbp),%rax
    popq %rdx
    subq %rax,%rdx
    movq %rdx,%rax

    movq %rax,%rdi 		 # Print
    call _put

    movq -16(%rbp),%rax
    pushq %rax 		 # Minus
    movq -24(%rbp),%rax
    popq %rdx
    subq %rax,%rdx
    movq %rdx,%rax

    pushq %rax 		 # Minus
    movq -32(%rbp),%rax
    popq %rdx
    subq %rax,%rdx
    movq %rdx,%rax

    pushq %rax 		 # Minus
    movq -40(%rbp),%rax
    popq %rdx
    subq %rax,%rdx
    movq %rdx,%rax


    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret

Four$mix:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp

    subq $32,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq %rdx,-24(%rbp) 		 # Move variable onto stack
    movq %rcx,-32(%rbp) 		 # Move variable onto stack
    movq -16(%rbp),%rax
    pushq %rax 		 # Plus
    movq -24(%rbp),%rax
    popq %rdx
    addq %rdx,%rax

    pushq %rax 		 # Times
    movq -32(%rbp),%rax
    popq %rdx
    imulq %rdx,%rax

    movq %rax,%rdi 		 # Print
    call _put

    movq $0,%rax 		 # Integer Literal

    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret

Four$getNum:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp

    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $10,%rax 		 # Integer Literal

    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret

Five$getFour:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp

    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $32,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Four$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax


    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret

.data
Five$$:
    .quad 0
    .quad Five$getFour
Four$$:
    .quad 0
    .quad Four$getNum
    .quad Four$mix
Two$$:
    .quad 0
    .quad Two$first
Three$$:
    .quad 0
    .quad Three$t
