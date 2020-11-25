    .text
    .globl  _asm_main

_asm_main:
    pushq %rbp
    movq %rsp,%rbp

    movq $3,%rax
pushq %rax
popq %rsi
    movq $8,%rdi
    call _mjcalloc
    leaq Two$$(%rip),%rdx
    movq %rdx,0(%rax)

    movq %rax,%rdi
    movq 0(%rdi),%rax
    call *8(%rax)

    movq %rax,%rdi
    call _put

    movq %rbp,%rsp
    popq %rbp
    ret

Two$first:
    pushq %rbp
    movq %rsp,%rbp

    subq $8,%rsp
    subq $8,%rsp
    movq %rax,-8(%rbp)
    movq %rax,%rdi
    call _put


    movq $1,%rax
addq $16,%rsp
    movq %rbp,%rsp
    popq %rbp
    ret

.data
Two$$:
    .quad 0
    .quad Two$first
