.text
.globl  _asm_main
_asm_main:
pushq %rbp
movq %rsp,%rbp
movq $1,%rax
pushq %rax
movq $1,%rax
popq %rdx
subq %rax,%rdx
movq %rdx,%rax
pushq %rax
movq $9,%rax
popq %rdx
imulq %rdx,%rax
movq %rax,%rdi
call _put
movq %rbp,%rsp
popq %rbp
ret
