    .text
    .globl  _asm_main

_runtime_error_exit:
    call _runtime_error

_asm_main:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    movq $8,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq TV$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
TV$Start:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $40,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq $56,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Tree$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    movq %rax,-16(%rbp) 		 # Assign to local var
    movq $16,%rax 		 # Integer Literal
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *104(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *96(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    movq $100000000,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    pushq %rax 		 # Push Dummy
    
    movq $8,%rax 		 # Integer Literal
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -16(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *80(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    movq $24,%rax 		 # Integer Literal
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *80(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq $4,%rax 		 # Integer Literal
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -16(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *80(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    movq $12,%rax 		 # Integer Literal
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *80(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq $20,%rax 		 # Integer Literal
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -16(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *80(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    movq $28,%rax 		 # Integer Literal
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *80(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq $14,%rax 		 # Integer Literal
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -16(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *80(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *96(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    movq $100000000,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    movq $24,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq MyVisitor$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    movq %rax,-40(%rbp) 		 # Assign to local var
    movq $50000000,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    movq -40(%rbp),%rax
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *88(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq $100000000,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    pushq %rax 		 # Push Dummy
    
    movq $24,%rax 		 # Integer Literal
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -16(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *136(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq $12,%rax 		 # Integer Literal
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *136(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    pushq %rax 		 # Push Dummy
    
    movq $16,%rax 		 # Integer Literal
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -16(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *136(%rax) 		 # Call variable's method
    
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
    call *136(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    pushq %rax 		 # Push Dummy
    
    movq $12,%rax 		 # Integer Literal
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -16(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *136(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq $12,%rax 		 # Integer Literal
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *96(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq $12,%rax 		 # Integer Literal
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -16(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *136(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq $0,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$Init:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $16,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq -16(%rbp),%rax
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,48(%rax) 		 # Assign to field
    movq $0,%rax 		 # Boolean false
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,40(%rax) 		 # Assign to field
    movq $0,%rax 		 # Boolean false
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,16(%rax) 		 # Assign to field
    movq $1,%rax 		 # Boolean true
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$SetRight:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $16,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq -16(%rbp),%rax
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,32(%rax) 		 # Assign to field
    movq $1,%rax 		 # Boolean true
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$SetLeft:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $16,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq -16(%rbp),%rax
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,8(%rax) 		 # Assign to field
    movq $1,%rax 		 # Boolean true
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$GetRight:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq -8(%rbp),%rax
    movq 32(%rax),%rax
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$GetLeft:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq -8(%rbp),%rax
    movq 8(%rax),%rax
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$GetKey:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq -8(%rbp),%rax
    movq 48(%rax),%rax
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$SetKey:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $16,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq -16(%rbp),%rax
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,48(%rax) 		 # Assign to field
    movq $1,%rax 		 # Boolean true
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$GetHas_Right:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq -8(%rbp),%rax
    movq 16(%rax),%rax
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$GetHas_Left:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $8,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq -8(%rbp),%rax
    movq 40(%rax),%rax
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$SetHas_Left:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $16,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq -16(%rbp),%rax
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,40(%rax) 		 # Assign to field
    movq $1,%rax 		 # Boolean true
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$SetHas_Right:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $16,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq -16(%rbp),%rax
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,16(%rax) 		 # Assign to field
    movq $1,%rax 		 # Boolean true
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$Compare:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $40,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq %rdx,-24(%rbp) 		 # Move variable onto stack
    movq $0,%rax 		 # Boolean false
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq -24(%rbp),%rax
    pushq %rax 		 # Plus
    movq $1,%rax 		 # Integer Literal
    popq %rdx
    addq %rdx,%rax
    
    movq %rax,-40(%rbp) 		 # Assign to local var
    movq -16(%rbp),%rax
    pushq %rax
    movq -24(%rbp),%rax
    popq %rdx
    cmpq %rdx, %rax 		 # Less Than
    setg %al
    movzbl %al,%eax
    cmpq $0,%rax 		 # If statement
    je Compare_else_1
    movq $0,%rax 		 # Boolean false
    movq %rax,-32(%rbp) 		 # Assign to local var
    jmp Compare_done_1
Compare_else_1:
    movq -16(%rbp),%rax
    pushq %rax
    movq -40(%rbp),%rax
    popq %rdx
    cmpq %rdx, %rax 		 # Less Than
    setg %al
    movzbl %al,%eax
    xor $1,%rax 		 # Not
    
    cmpq $0,%rax 		 # If statement
    je Compare_else_2
    movq $0,%rax 		 # Boolean false
    movq %rax,-32(%rbp) 		 # Assign to local var
    jmp Compare_done_2
Compare_else_2:
    movq $1,%rax 		 # Boolean true
    movq %rax,-32(%rbp) 		 # Assign to local var
Compare_done_2:
Compare_done_1:
    movq -32(%rbp),%rax
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$Insert:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $56,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq $56,%rdi 		 # New object declaration
    call _mjcalloc 		 # Allocate space and return pointer in %rax
    leaq Tree$$(%rip),%rdx 		 # Load class vtable into %rdx
    movq %rdx,0(%rax) 		 # Load vtable at the beginning of %rax
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    movq -16(%rbp),%rax
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *104(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq -8(%rbp),%rax 		 # This
    movq %rax,-40(%rbp) 		 # Assign to local var
    movq $1,%rax 		 # Boolean true
    movq %rax,-48(%rbp) 		 # Assign to local var
    jmp Insert_while_3 		 # While statement
Insert_while_3:
    movq -48(%rbp),%rax
    cmpq $0,%rax
    je Insert_while_done_3
    pushq %rax 		 # Push Dummy
    
    movq -40(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *32(%rax) 		 # Call variable's method
    
    movq %rax,-56(%rbp) 		 # Assign to local var
    movq -16(%rbp),%rax
    pushq %rax
    movq -56(%rbp),%rax
    popq %rdx
    cmpq %rdx, %rax 		 # Less Than
    setg %al
    movzbl %al,%eax
    cmpq $0,%rax 		 # If statement
    je Insert_else_4
    pushq %rax 		 # Push Dummy
    
    movq -40(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *120(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je Insert_else_5
    pushq %rax 		 # Push Dummy
    
    movq -40(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *48(%rax) 		 # Call variable's method
    
    movq %rax,-40(%rbp) 		 # Assign to local var
    jmp Insert_done_5
Insert_else_5:
    movq $0,%rax 		 # Boolean false
    movq %rax,-48(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq $1,%rax 		 # Boolean true
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -40(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *16(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq -24(%rbp),%rax
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -40(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *72(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
Insert_done_5:
    jmp Insert_done_4
Insert_else_4:
    pushq %rax 		 # Push Dummy
    
    movq -40(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *112(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je Insert_else_6
    pushq %rax 		 # Push Dummy
    
    movq -40(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *56(%rax) 		 # Call variable's method
    
    movq %rax,-40(%rbp) 		 # Assign to local var
    jmp Insert_done_6
Insert_else_6:
    movq $0,%rax 		 # Boolean false
    movq %rax,-48(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq $1,%rax 		 # Boolean true
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -40(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *160(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq -24(%rbp),%rax
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -40(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *40(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
Insert_done_6:
Insert_done_4:
    jmp Insert_while_3
Insert_while_done_3:
    movq $1,%rax 		 # Boolean true
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$Delete:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $72,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq -8(%rbp),%rax 		 # This
    movq %rax,-24(%rbp) 		 # Assign to local var
    movq -8(%rbp),%rax 		 # This
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq $1,%rax 		 # Boolean true
    movq %rax,-40(%rbp) 		 # Assign to local var
    movq $0,%rax 		 # Boolean false
    movq %rax,-48(%rbp) 		 # Assign to local var
    movq $1,%rax 		 # Boolean true
    movq %rax,-64(%rbp) 		 # Assign to local var
    jmp Delete_while_7 		 # While statement
Delete_while_7:
    movq -40(%rbp),%rax
    cmpq $0,%rax
    je Delete_while_done_7
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *32(%rax) 		 # Call variable's method
    
    movq %rax,-72(%rbp) 		 # Assign to local var
    movq -16(%rbp),%rax
    pushq %rax
    movq -72(%rbp),%rax
    popq %rdx
    cmpq %rdx, %rax 		 # Less Than
    setg %al
    movzbl %al,%eax
    cmpq $0,%rax 		 # If statement
    je Delete_else_8
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *120(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je Delete_else_9
    movq -24(%rbp),%rax
    movq %rax,-32(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *48(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    jmp Delete_done_9
Delete_else_9:
    movq $0,%rax 		 # Boolean false
    movq %rax,-40(%rbp) 		 # Assign to local var
Delete_done_9:
    jmp Delete_done_8
Delete_else_8:
    movq -72(%rbp),%rax
    pushq %rax
    movq -16(%rbp),%rax
    popq %rdx
    cmpq %rdx, %rax 		 # Less Than
    setg %al
    movzbl %al,%eax
    cmpq $0,%rax 		 # If statement
    je Delete_else_10
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *112(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je Delete_else_11
    movq -24(%rbp),%rax
    movq %rax,-32(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *56(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    jmp Delete_done_11
Delete_else_11:
    movq $0,%rax 		 # Boolean false
    movq %rax,-40(%rbp) 		 # Assign to local var
Delete_done_11:
    jmp Delete_done_10
Delete_else_10:
    movq -64(%rbp),%rax
    cmpq $0,%rax 		 # If statement
    je Delete_else_12
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *112(%rax) 		 # Call variable's method
    
    xor $1,%rax 		 # Not
    
    pushq %rax 		 # Plus
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *120(%rax) 		 # Call variable's method
    
    xor $1,%rax 		 # Not
    
    popq %rdx
    and %rdx,%rax
    
    cmpq $0,%rax 		 # If statement
    je Delete_else_13
    movq $1,%rax 		 # Boolean true
    movq %rax,-56(%rbp) 		 # Assign to local var
    jmp Delete_done_13
Delete_else_13:
    pushq %rax 		 # Push Dummy
    
    movq -32(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -24(%rbp),%rax
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -8(%rbp),%rax 		 # This
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rdx 		 # Pop from stack into arg registers
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *64(%rax) 		 # Call variable's method
    
    movq %rax,-56(%rbp) 		 # Assign to local var
Delete_done_13:
    jmp Delete_done_12
Delete_else_12:
    pushq %rax 		 # Push Dummy
    
    movq -32(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -24(%rbp),%rax
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -8(%rbp),%rax 		 # This
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rdx 		 # Pop from stack into arg registers
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *64(%rax) 		 # Call variable's method
    
    movq %rax,-56(%rbp) 		 # Assign to local var
Delete_done_12:
    movq $1,%rax 		 # Boolean true
    movq %rax,-48(%rbp) 		 # Assign to local var
    movq $0,%rax 		 # Boolean false
    movq %rax,-40(%rbp) 		 # Assign to local var
Delete_done_10:
Delete_done_8:
    movq $0,%rax 		 # Boolean false
    movq %rax,-64(%rbp) 		 # Assign to local var
    jmp Delete_while_7
Delete_while_done_7:
    movq -48(%rbp),%rax
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$Remove:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $48,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq %rdx,-24(%rbp) 		 # Move variable onto stack
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *120(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je Remove_else_14
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -24(%rbp),%rax
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -8(%rbp),%rax 		 # This
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rdx 		 # Pop from stack into arg registers
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *24(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    jmp Remove_done_14
Remove_else_14:
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *112(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je Remove_else_15
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -24(%rbp),%rax
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -8(%rbp),%rax 		 # This
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rdx 		 # Pop from stack into arg registers
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *128(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    jmp Remove_done_15
Remove_else_15:
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *32(%rax) 		 # Call variable's method
    
    movq %rax,-40(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *48(%rax) 		 # Call variable's method
    
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *32(%rax) 		 # Call variable's method
    
    movq %rax,-48(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq -40(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -48(%rbp),%rax
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -8(%rbp),%rax 		 # This
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rdx 		 # Pop from stack into arg registers
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *152(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je Remove_else_16
    pushq %rax 		 # Push Dummy
    
    movq -8(%rbp),%rax
    movq 24(%rax),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -16(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *72(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq $0,%rax 		 # Boolean false
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *16(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    jmp Remove_done_16
Remove_else_16:
    pushq %rax 		 # Push Dummy
    
    movq -8(%rbp),%rax
    movq 24(%rax),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -16(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *40(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq $0,%rax 		 # Boolean false
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *160(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
Remove_done_16:
Remove_done_15:
Remove_done_14:
    movq $1,%rax 		 # Boolean true
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$RemoveRight:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $32,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq %rdx,-24(%rbp) 		 # Move variable onto stack
    jmp RemoveRight_while_17 		 # While statement
RemoveRight_while_17:
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *112(%rax) 		 # Call variable's method
    
    cmpq $0,%rax
    je RemoveRight_while_done_17
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *56(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *32(%rax) 		 # Call variable's method
    
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -24(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *144(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq -24(%rbp),%rax
    movq %rax,-16(%rbp) 		 # Assign to local var
    movq -24(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *56(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    jmp RemoveRight_while_17
RemoveRight_while_done_17:
    movq -8(%rbp),%rax
    movq 24(%rax),%rax
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *40(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq $0,%rax 		 # Boolean false
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -16(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *160(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq $1,%rax 		 # Boolean true
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$RemoveLeft:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $32,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq %rdx,-24(%rbp) 		 # Move variable onto stack
    jmp RemoveLeft_while_18 		 # While statement
RemoveLeft_while_18:
    movq -24(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *120(%rax) 		 # Call variable's method
    
    cmpq $0,%rax
    je RemoveLeft_while_done_18
    movq -24(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *48(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *32(%rax) 		 # Call variable's method
    
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *144(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq -24(%rbp),%rax
    movq %rax,-16(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *48(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    jmp RemoveLeft_while_18
RemoveLeft_while_done_18:
    pushq %rax 		 # Push Dummy
    
    movq -8(%rbp),%rax
    movq 24(%rax),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -16(%rbp),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *72(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq $0,%rax 		 # Boolean false
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *16(%rax) 		 # Call variable's method
    
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq $1,%rax 		 # Boolean true
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$Search:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $48,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq -8(%rbp),%rax 		 # This
    movq %rax,-24(%rbp) 		 # Assign to local var
    movq $1,%rax 		 # Boolean true
    movq %rax,-40(%rbp) 		 # Assign to local var
    movq $0,%rax 		 # Integer Literal
    movq %rax,-32(%rbp) 		 # Assign to local var
    jmp Search_while_19 		 # While statement
Search_while_19:
    movq -40(%rbp),%rax
    cmpq $0,%rax
    je Search_while_done_19
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *32(%rax) 		 # Call variable's method
    
    movq %rax,-48(%rbp) 		 # Assign to local var
    movq -16(%rbp),%rax
    pushq %rax
    movq -48(%rbp),%rax
    popq %rdx
    cmpq %rdx, %rax 		 # Less Than
    setg %al
    movzbl %al,%eax
    cmpq $0,%rax 		 # If statement
    je Search_else_20
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *120(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je Search_else_21
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *48(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    jmp Search_done_21
Search_else_21:
    movq $0,%rax 		 # Boolean false
    movq %rax,-40(%rbp) 		 # Assign to local var
Search_done_21:
    jmp Search_done_20
Search_else_20:
    movq -48(%rbp),%rax
    pushq %rax
    movq -16(%rbp),%rax
    popq %rdx
    cmpq %rdx, %rax 		 # Less Than
    setg %al
    movzbl %al,%eax
    cmpq $0,%rax 		 # If statement
    je Search_else_22
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *112(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je Search_else_23
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *56(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    jmp Search_done_23
Search_else_23:
    movq $0,%rax 		 # Boolean false
    movq %rax,-40(%rbp) 		 # Assign to local var
Search_done_23:
    jmp Search_done_22
Search_else_22:
    movq $1,%rax 		 # Integer Literal
    movq %rax,-32(%rbp) 		 # Assign to local var
    movq $0,%rax 		 # Boolean false
    movq %rax,-40(%rbp) 		 # Assign to local var
Search_done_22:
Search_done_20:
    jmp Search_while_19
Search_while_done_19:
    movq -32(%rbp),%rax
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$Print:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $24,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq -8(%rbp),%rax 		 # This
    movq %rax,-24(%rbp) 		 # Assign to local var
    pushq %rax 		 # Push Dummy
    
    movq -24(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -8(%rbp),%rax 		 # This
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *168(%rax) 		 # Call variable's method
    
    movq %rax,-16(%rbp) 		 # Assign to local var
    movq $1,%rax 		 # Boolean true
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$RecPrint:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $24,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *120(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je RecPrint_else_24
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *48(%rax) 		 # Call variable's method
    
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -8(%rbp),%rax 		 # This
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *168(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    jmp RecPrint_done_24
RecPrint_else_24:
    movq $1,%rax 		 # Boolean true
    movq %rax,-24(%rbp) 		 # Assign to local var
RecPrint_done_24:
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *32(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *112(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je RecPrint_else_25
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *56(%rax) 		 # Call variable's method
    
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -8(%rbp),%rax 		 # This
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *168(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    jmp RecPrint_done_25
RecPrint_else_25:
    movq $1,%rax 		 # Boolean true
    movq %rax,-24(%rbp) 		 # Assign to local var
RecPrint_done_25:
    movq $1,%rax 		 # Boolean true
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Tree$accept:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $24,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    movq $333,%rax 		 # Integer Literal
    movq %rax,%rdi 		 # Print
    call _put
    
    movq -8(%rbp),%rax 		 # This
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *8(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    movq $0,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
Visitor$visit:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $24,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *112(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je visit_else_26
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *56(%rax) 		 # Call variable's method
    
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,8(%rax) 		 # Assign to field
    pushq %rax 		 # Push Dummy
    
    movq -8(%rbp),%rax 		 # This
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -8(%rbp),%rax
    movq 8(%rax),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *88(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    jmp visit_done_26
visit_else_26:
    movq $0,%rax 		 # Integer Literal
    movq %rax,-24(%rbp) 		 # Assign to local var
visit_done_26:
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *120(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je visit_else_27
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *48(%rax) 		 # Call variable's method
    
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,16(%rax) 		 # Assign to field
    movq -8(%rbp),%rax 		 # This
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -8(%rbp),%rax
    movq 16(%rax),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *88(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    jmp visit_done_27
visit_else_27:
    movq $0,%rax 		 # Integer Literal
    movq %rax,-24(%rbp) 		 # Assign to local var
visit_done_27:
    movq $0,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
MyVisitor$visit:
    pushq %rbp 		 # Prologue
    movq %rsp,%rbp
    
    subq $8,%rsp
    subq $24,%rsp 		 # Subtract space for variables to push on stack
    movq %rdi,-8(%rbp) 		 # Move variable onto stack
    movq %rsi,-16(%rbp) 		 # Move variable onto stack
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *112(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je visit_else_28
    pushq %rax 		 # Push Dummy
    
    movq -16(%rbp),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *56(%rax) 		 # Call variable's method
    
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,8(%rax) 		 # Assign to field
    pushq %rax 		 # Push Dummy
    
    movq -8(%rbp),%rax 		 # This
    addq $8,%rsp 		 # Pop Dummy
    
    pushq %rax 		 # Evaluate args and push on stack
    movq -8(%rbp),%rax
    movq 8(%rax),%rax
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *88(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    jmp visit_done_28
visit_else_28:
    movq $0,%rax 		 # Integer Literal
    movq %rax,-24(%rbp) 		 # Assign to local var
visit_done_28:
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *32(%rax) 		 # Call variable's method
    
    movq %rax,%rdi 		 # Print
    call _put
    
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *120(%rax) 		 # Call variable's method
    
    cmpq $0,%rax 		 # If statement
    je visit_else_29
    movq -16(%rbp),%rax
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *48(%rax) 		 # Call variable's method
    
    pushq %rax
    movq -8(%rbp),%rax
    popq %rdx
    movq %rdx,16(%rax) 		 # Assign to field
    movq -8(%rbp),%rax 		 # This
    pushq %rax 		 # Evaluate args and push on stack
    pushq %rax 		 # Push Dummy
    
    movq -8(%rbp),%rax
    movq 16(%rax),%rax
    addq $8,%rsp 		 # Pop Dummy
    
    popq %rsi 		 # Pop from stack into arg registers
    movq %rax,%rdi 		 # Load pointer of object making call in first arg register
    movq 0(%rdi),%rax
    call *88(%rax) 		 # Call variable's method
    
    movq %rax,-24(%rbp) 		 # Assign to local var
    jmp visit_done_29
visit_else_29:
    movq $0,%rax 		 # Integer Literal
    movq %rax,-24(%rbp) 		 # Assign to local var
visit_done_29:
    movq $0,%rax 		 # Integer Literal
    
    movq %rbp,%rsp 		 # Epilogue
    popq %rbp
    ret
    
    .data
    TV$$:
    .quad 0
    .quad TV$Start
    Visitor$$:
    .quad 0
    .quad Visitor$visit
    Tree$$:
    .quad 0
    .quad Tree$Delete
    .quad Tree$SetHas_Left
    .quad Tree$RemoveLeft
    .quad Tree$GetKey
    .quad Tree$SetRight
    .quad Tree$GetLeft
    .quad Tree$GetRight
    .quad Tree$Remove
    .quad Tree$SetLeft
    .quad Tree$Insert
    .quad Tree$accept
    .quad Tree$Print
    .quad Tree$Init
    .quad Tree$GetHas_Right
    .quad Tree$GetHas_Left
    .quad Tree$RemoveRight
    .quad Tree$Search
    .quad Tree$SetKey
    .quad Tree$Compare
    .quad Tree$SetHas_Right
    .quad Tree$RecPrint
    MyVisitor$$:
    .quad Visitor$$
    .quad MyVisitor$visit
