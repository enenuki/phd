/*   1:    */ package java_cup.runtime;
/*   2:    */ 
/*   3:    */ import java.util.Stack;
/*   4:    */ import java.util.Vector;
/*   5:    */ 
/*   6:    */ public class virtual_parse_stack
/*   7:    */ {
/*   8:    */   protected Stack real_stack;
/*   9:    */   protected int real_next;
/*  10:    */   protected Stack vstack;
/*  11:    */   
/*  12:    */   public virtual_parse_stack(Stack paramStack)
/*  13:    */     throws Exception
/*  14:    */   {
/*  15: 31 */     if (paramStack == null) {
/*  16: 32 */       throw new Exception(
/*  17: 33 */         "Internal parser error: attempt to create null virtual stack");
/*  18:    */     }
/*  19: 36 */     this.real_stack = paramStack;
/*  20: 37 */     this.vstack = new Stack();
/*  21: 38 */     this.real_next = 0;
/*  22:    */     
/*  23:    */ 
/*  24: 41 */     get_from_real();
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected void get_from_real()
/*  28:    */   {
/*  29: 84 */     if (this.real_next >= this.real_stack.size()) {
/*  30: 84 */       return;
/*  31:    */     }
/*  32: 87 */     Symbol localSymbol = (Symbol)this.real_stack.elementAt(this.real_stack.size() - 1 - this.real_next);
/*  33:    */     
/*  34:    */ 
/*  35: 90 */     this.real_next += 1;
/*  36:    */     
/*  37:    */ 
/*  38: 93 */     this.vstack.push(new Integer(localSymbol.parse_state));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean empty()
/*  42:    */   {
/*  43:103 */     return this.vstack.empty();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int top()
/*  47:    */     throws Exception
/*  48:    */   {
/*  49:111 */     if (this.vstack.empty()) {
/*  50:112 */       throw new Exception(
/*  51:113 */         "Internal parser error: top() called on empty virtual stack");
/*  52:    */     }
/*  53:115 */     return ((Integer)this.vstack.peek()).intValue();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void pop()
/*  57:    */     throws Exception
/*  58:    */   {
/*  59:123 */     if (this.vstack.empty()) {
/*  60:124 */       throw new Exception(
/*  61:125 */         "Internal parser error: pop from empty virtual stack");
/*  62:    */     }
/*  63:128 */     this.vstack.pop();
/*  64:131 */     if (this.vstack.empty()) {
/*  65:132 */       get_from_real();
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void push(int paramInt)
/*  70:    */   {
/*  71:140 */     this.vstack.push(new Integer(paramInt));
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     java_cup.runtime.virtual_parse_stack
 * JD-Core Version:    0.7.0.1
 */