/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.classfile.ConstantPool;
/*   4:    */ 
/*   5:    */ public abstract class FieldInstruction
/*   6:    */   extends FieldOrMethod
/*   7:    */   implements TypedInstruction
/*   8:    */ {
/*   9:    */   FieldInstruction() {}
/*  10:    */   
/*  11:    */   protected FieldInstruction(short opcode, int index)
/*  12:    */   {
/*  13: 81 */     super(opcode, index);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public String toString(ConstantPool cp)
/*  17:    */   {
/*  18: 88 */     return org.apache.bcel.Constants.OPCODE_NAMES[this.opcode] + " " + cp.constantToString(this.index, (byte)9);
/*  19:    */   }
/*  20:    */   
/*  21:    */   protected int getFieldSize(ConstantPoolGen cpg)
/*  22:    */   {
/*  23: 95 */     return getType(cpg).getSize();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Type getType(ConstantPoolGen cpg)
/*  27:    */   {
/*  28:101 */     return getFieldType(cpg);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Type getFieldType(ConstantPoolGen cpg)
/*  32:    */   {
/*  33:107 */     return Type.getType(getSignature(cpg));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getFieldName(ConstantPoolGen cpg)
/*  37:    */   {
/*  38:113 */     return getName(cpg);
/*  39:    */   }
/*  40:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.FieldInstruction
 * JD-Core Version:    0.7.0.1
 */