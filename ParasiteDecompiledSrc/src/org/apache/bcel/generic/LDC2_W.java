/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.classfile.Constant;
/*   4:    */ import org.apache.bcel.classfile.ConstantDouble;
/*   5:    */ import org.apache.bcel.classfile.ConstantLong;
/*   6:    */ import org.apache.bcel.classfile.ConstantPool;
/*   7:    */ 
/*   8:    */ public class LDC2_W
/*   9:    */   extends CPInstruction
/*  10:    */   implements PushInstruction, TypedInstruction
/*  11:    */ {
/*  12:    */   LDC2_W() {}
/*  13:    */   
/*  14:    */   public LDC2_W(int index)
/*  15:    */   {
/*  16: 74 */     super((short)20, index);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Type getType(ConstantPoolGen cpg)
/*  20:    */   {
/*  21: 78 */     switch (cpg.getConstantPool().getConstant(this.index).getTag())
/*  22:    */     {
/*  23:    */     case 5: 
/*  24: 79 */       return Type.LONG;
/*  25:    */     case 6: 
/*  26: 80 */       return Type.DOUBLE;
/*  27:    */     }
/*  28: 82 */     throw new RuntimeException("Unknown constant type " + this.opcode);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Number getValue(ConstantPoolGen cpg)
/*  32:    */   {
/*  33: 87 */     Constant c = cpg.getConstantPool().getConstant(this.index);
/*  34: 89 */     switch (c.getTag())
/*  35:    */     {
/*  36:    */     case 5: 
/*  37: 91 */       return new Long(((ConstantLong)c).getBytes());
/*  38:    */     case 6: 
/*  39: 94 */       return new Double(((ConstantDouble)c).getBytes());
/*  40:    */     }
/*  41: 97 */     throw new RuntimeException("Unknown or invalid constant type at " + this.index);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void accept(Visitor v)
/*  45:    */   {
/*  46:110 */     v.visitStackProducer(this);
/*  47:111 */     v.visitPushInstruction(this);
/*  48:112 */     v.visitTypedInstruction(this);
/*  49:113 */     v.visitCPInstruction(this);
/*  50:114 */     v.visitLDC2_W(this);
/*  51:    */   }
/*  52:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LDC2_W
 * JD-Core Version:    0.7.0.1
 */