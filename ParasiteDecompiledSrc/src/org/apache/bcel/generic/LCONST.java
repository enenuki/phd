/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ public class LCONST
/*   4:    */   extends Instruction
/*   5:    */   implements ConstantPushInstruction, TypedInstruction
/*   6:    */ {
/*   7:    */   private long value;
/*   8:    */   
/*   9:    */   LCONST() {}
/*  10:    */   
/*  11:    */   public LCONST(long l)
/*  12:    */   {
/*  13: 76 */     super((short)9, (short)1);
/*  14: 78 */     if (l == 0L) {
/*  15: 79 */       this.opcode = 9;
/*  16: 80 */     } else if (l == 1L) {
/*  17: 81 */       this.opcode = 10;
/*  18:    */     } else {
/*  19: 83 */       throw new ClassGenException("LCONST can be used only for 0 and 1: " + l);
/*  20:    */     }
/*  21: 85 */     this.value = l;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Number getValue()
/*  25:    */   {
/*  26: 88 */     return new Long(this.value);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Type getType(ConstantPoolGen cp)
/*  30:    */   {
/*  31: 93 */     return Type.LONG;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void accept(Visitor v)
/*  35:    */   {
/*  36:106 */     v.visitPushInstruction(this);
/*  37:107 */     v.visitStackProducer(this);
/*  38:108 */     v.visitTypedInstruction(this);
/*  39:109 */     v.visitConstantPushInstruction(this);
/*  40:110 */     v.visitLCONST(this);
/*  41:    */   }
/*  42:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LCONST
 * JD-Core Version:    0.7.0.1
 */