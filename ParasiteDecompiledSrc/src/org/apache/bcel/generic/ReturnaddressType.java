/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ public class ReturnaddressType
/*   4:    */   extends Type
/*   5:    */ {
/*   6: 68 */   public static final ReturnaddressType NO_TARGET = new ReturnaddressType();
/*   7:    */   private InstructionHandle returnTarget;
/*   8:    */   
/*   9:    */   private ReturnaddressType()
/*  10:    */   {
/*  11: 75 */     super((byte)16, "<return address>");
/*  12:    */   }
/*  13:    */   
/*  14:    */   public ReturnaddressType(InstructionHandle returnTarget)
/*  15:    */   {
/*  16: 82 */     super((byte)16, "<return address targeting " + returnTarget + ">");
/*  17: 83 */     this.returnTarget = returnTarget;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public boolean equals(Object rat)
/*  21:    */   {
/*  22: 90 */     if (!(rat instanceof ReturnaddressType)) {
/*  23: 91 */       return false;
/*  24:    */     }
/*  25: 93 */     return ((ReturnaddressType)rat).returnTarget.equals(this.returnTarget);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public InstructionHandle getTarget()
/*  29:    */   {
/*  30:100 */     return this.returnTarget;
/*  31:    */   }
/*  32:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ReturnaddressType
 * JD-Core Version:    0.7.0.1
 */