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
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
    Test$test:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $1,%rax 		 # Boolean true
    pushq %rax 		 # Plus
    movq $1,%rax 		 # Boolean true
    popq %rdx
    and %rdx,%rax
    
    cmpq $0,%rax
    je test_else_1
    movq $1,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    jmp test_done_1
test_else_1:
    movq $0,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
test_done_1:
    movq $1,%rax 		 # Boolean true
    pushq %rax 		 # Plus
    movq $0,%rax 		 # Boolean false
    popq %rdx
    and %rdx,%rax
    
    cmpq $0,%rax
    je test_else_2
    movq $2,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    jmp test_done_2
test_else_2:
    movq $3,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
test_done_2:
    movq $0,%rax 		 # Boolean false
    pushq %rax 		 # Plus
    movq $0,%rax 		 # Boolean false
    popq %rdx
    and %rdx,%rax
    
    cmpq $0,%rax
    je test_else_3
    movq $4,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    jmp test_done_3
test_else_3:
    movq $5,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
test_done_3:
    movq $1,%rax 		 # Boolean true
    cmpq $0,%rax
    je test_else_4
    movq $6,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    jmp test_done_4
test_else_4:
    movq $7,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
test_done_4:
    movq $0,%rax 		 # Boolean false
    cmpq $0,%rax
    je test_else_5
    movq $8,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    jmp test_done_5
test_else_5:
    movq $9,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
test_done_5:
    movq $100,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
    .data
    Test$$:
    .quad 0
    .quad Test$test
