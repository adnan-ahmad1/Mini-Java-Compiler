    .text
    .globl  _asm_main

_asm_main:
    pushq %rbp
    movq %rsp,%rbp

    movq $8,%rdi
    call _mjcalloc
    leaq Two$$(%rip),%rdx
    movq %rdx,0(%rax)

    movq %rax,%rdi
    movq 0(%rdi),%rax
    call *16(%rax)

    movq %rax,%rdi
    call _put

    movq %rbp,%rsp
    popq %rbp
    ret

Two$add:
    pushq %rbp
    movq %rsp,%rbp

    movq $1,%rax
    pushq %rax
    movq $1,%rax
    popq %rdx
    addq %rdx,%rax

    movq %rbp,%rsp
    popq %rbp
    ret

Two$seventeen:
    pushq %rbp
    movq %rsp,%rbp

    movq $1,%rax
    movq %rax,%rdi
    call _put


    movq $3,%rax
    pushq %rax
    movq $8,%rdi
    call _mjcalloc
    leaq Two$$(%rip),%rdx
    movq %rdx,0(%rax)

    movq %rax,%rdi
    movq 0(%rdi),%rax
    call *8(%rax)

    popq %rdx
    imulq %rdx,%rax

    movq %rax,%rdi
    call _put

    movq $9,%rax
    pushq %rax
    movq $3,%rax
    pushq %rax
    movq $1,%rax
    popq %rdx
    subq %rax,%rdx
    movq %rdx,%rax

    popq %rdx
    imulq %rdx,%rax

    pushq %rax
    movq $5,%rax
    popq %rdx
    addq %rdx,%rax

    movq %rax,%rdi
    call _put


    movq $17,%rax
    movq %rbp,%rsp
    popq %rbp
    ret

.data
One$$:
    .quad 0
Two$$:
    .quad 0
    .quad Two$add
    .quad Two$seventeen
