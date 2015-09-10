/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ public class ICONST
/*   4:    */   extends Instruction
/*   5:    */   implements ConstantPushInstruction, TypedInstruction
/*   6:    */ {
/*   7:    */   private int value;
/*   8:    */   
/*   9:    */   ICONST() {}
/*  10:    */   
/*  11:    */   public ICONST(int i)
/*  12:    */   {
/*  13: 76 */     super((short)3, (short)1);
/*  14: 78 */     if ((i >= -1) && (i <= 5)) {
/*  15: 79 */       this.opcode = ((short)(3 + i));
/*  16:    */     } else {
/*  17: 81 */       throw new ClassGenException("ICONST can be used only for value between -1 and 5: " + i);
/*  18:    */     }
/*  19: 83 */     this.value = i;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Number getValue()
/*  23:    */   {
/*  24: 86 */     return new Integer(this.value);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Type getType(ConstantPoolGen cp)
/*  28:    */   {
/*  29: 91 */     return Type.INT;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void accept(Visitor v)
/*  33:    */   {
/*  34:104 */     v.visitPushInstruction(this);
/*  35:105 */     v.visitStackProducer(this);
/*  36:106 */     v.visitTypedInstruction(this);
/*  37:107 */     v.visitConstantPushInstruction(this);
/*  38:108 */     v.visitICONST(this);
/*  39:    */   }
/*  40:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ICONST
 * JD-Core Version:    0.7.0.1
 */