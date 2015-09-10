/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ public class DCONST
/*   4:    */   extends Instruction
/*   5:    */   implements ConstantPushInstruction, TypedInstruction
/*   6:    */ {
/*   7:    */   private double value;
/*   8:    */   
/*   9:    */   DCONST() {}
/*  10:    */   
/*  11:    */   public DCONST(double f)
/*  12:    */   {
/*  13: 76 */     super((short)14, (short)1);
/*  14: 78 */     if (f == 0.0D) {
/*  15: 79 */       this.opcode = 14;
/*  16: 80 */     } else if (f == 1.0D) {
/*  17: 81 */       this.opcode = 15;
/*  18:    */     } else {
/*  19: 83 */       throw new ClassGenException("DCONST can be used only for 0.0 and 1.0: " + f);
/*  20:    */     }
/*  21: 85 */     this.value = f;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Number getValue()
/*  25:    */   {
/*  26: 88 */     return new Double(this.value);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Type getType(ConstantPoolGen cp)
/*  30:    */   {
/*  31: 93 */     return Type.DOUBLE;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void accept(Visitor v)
/*  35:    */   {
/*  36:106 */     v.visitPushInstruction(this);
/*  37:107 */     v.visitStackProducer(this);
/*  38:108 */     v.visitTypedInstruction(this);
/*  39:109 */     v.visitConstantPushInstruction(this);
/*  40:110 */     v.visitDCONST(this);
/*  41:    */   }
/*  42:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.DCONST
 * JD-Core Version:    0.7.0.1
 */