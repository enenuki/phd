/*   1:    */ package org.apache.bcel.verifier.structurals;
/*   2:    */ 
/*   3:    */ public class Frame
/*   4:    */ {
/*   5:    */   protected static UninitializedObjectType _this;
/*   6:    */   private LocalVariables locals;
/*   7:    */   private OperandStack stack;
/*   8:    */   
/*   9:    */   public Frame(int maxLocals, int maxStack)
/*  10:    */   {
/*  11: 93 */     this.locals = new LocalVariables(maxLocals);
/*  12: 94 */     this.stack = new OperandStack(maxStack);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public Frame(LocalVariables locals, OperandStack stack)
/*  16:    */   {
/*  17:101 */     this.locals = locals;
/*  18:102 */     this.stack = stack;
/*  19:    */   }
/*  20:    */   
/*  21:    */   protected Object clone()
/*  22:    */   {
/*  23:109 */     Frame f = new Frame(this.locals.getClone(), this.stack.getClone());
/*  24:110 */     return f;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Frame getClone()
/*  28:    */   {
/*  29:117 */     return (Frame)clone();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public LocalVariables getLocals()
/*  33:    */   {
/*  34:124 */     return this.locals;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public OperandStack getStack()
/*  38:    */   {
/*  39:131 */     return this.stack;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean equals(Object o)
/*  43:    */   {
/*  44:138 */     if (!(o instanceof Frame)) {
/*  45:138 */       return false;
/*  46:    */     }
/*  47:139 */     Frame f = (Frame)o;
/*  48:140 */     return (this.stack.equals(f.stack)) && (this.locals.equals(f.locals));
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString()
/*  52:    */   {
/*  53:147 */     String s = "Local Variables:\n";
/*  54:148 */     s = s + this.locals;
/*  55:149 */     s = s + "OperandStack:\n";
/*  56:150 */     s = s + this.stack;
/*  57:151 */     return s;
/*  58:    */   }
/*  59:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.structurals.Frame
 * JD-Core Version:    0.7.0.1
 */