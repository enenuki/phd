/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ public class FCONST
/*   4:    */   extends Instruction
/*   5:    */   implements ConstantPushInstruction, TypedInstruction
/*   6:    */ {
/*   7:    */   private float value;
/*   8:    */   
/*   9:    */   FCONST() {}
/*  10:    */   
/*  11:    */   public FCONST(float f)
/*  12:    */   {
/*  13: 76 */     super((short)11, (short)1);
/*  14: 78 */     if (f == 0.0D) {
/*  15: 79 */       this.opcode = 11;
/*  16: 80 */     } else if (f == 1.0D) {
/*  17: 81 */       this.opcode = 12;
/*  18: 82 */     } else if (f == 2.0D) {
/*  19: 83 */       this.opcode = 13;
/*  20:    */     } else {
/*  21: 85 */       throw new ClassGenException("FCONST can be used only for 0.0, 1.0 and 2.0: " + f);
/*  22:    */     }
/*  23: 87 */     this.value = f;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Number getValue()
/*  27:    */   {
/*  28: 90 */     return new Float(this.value);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Type getType(ConstantPoolGen cp)
/*  32:    */   {
/*  33: 95 */     return Type.FLOAT;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void accept(Visitor v)
/*  37:    */   {
/*  38:108 */     v.visitPushInstruction(this);
/*  39:109 */     v.visitStackProducer(this);
/*  40:110 */     v.visitTypedInstruction(this);
/*  41:111 */     v.visitConstantPushInstruction(this);
/*  42:112 */     v.visitFCONST(this);
/*  43:    */   }
/*  44:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.FCONST
 * JD-Core Version:    0.7.0.1
 */