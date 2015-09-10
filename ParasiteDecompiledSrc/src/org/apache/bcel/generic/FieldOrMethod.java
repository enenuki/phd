/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.classfile.ConstantCP;
/*   4:    */ import org.apache.bcel.classfile.ConstantNameAndType;
/*   5:    */ import org.apache.bcel.classfile.ConstantPool;
/*   6:    */ import org.apache.bcel.classfile.ConstantUtf8;
/*   7:    */ 
/*   8:    */ public abstract class FieldOrMethod
/*   9:    */   extends CPInstruction
/*  10:    */   implements LoadClass
/*  11:    */ {
/*  12:    */   FieldOrMethod() {}
/*  13:    */   
/*  14:    */   protected FieldOrMethod(short opcode, int index)
/*  15:    */   {
/*  16: 76 */     super(opcode, index);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public String getSignature(ConstantPoolGen cpg)
/*  20:    */   {
/*  21: 82 */     ConstantPool cp = cpg.getConstantPool();
/*  22: 83 */     ConstantCP cmr = (ConstantCP)cp.getConstant(this.index);
/*  23: 84 */     ConstantNameAndType cnat = (ConstantNameAndType)cp.getConstant(cmr.getNameAndTypeIndex());
/*  24:    */     
/*  25: 86 */     return ((ConstantUtf8)cp.getConstant(cnat.getSignatureIndex())).getBytes();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getName(ConstantPoolGen cpg)
/*  29:    */   {
/*  30: 92 */     ConstantPool cp = cpg.getConstantPool();
/*  31: 93 */     ConstantCP cmr = (ConstantCP)cp.getConstant(this.index);
/*  32: 94 */     ConstantNameAndType cnat = (ConstantNameAndType)cp.getConstant(cmr.getNameAndTypeIndex());
/*  33: 95 */     return ((ConstantUtf8)cp.getConstant(cnat.getNameIndex())).getBytes();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getClassName(ConstantPoolGen cpg)
/*  37:    */   {
/*  38:101 */     ConstantPool cp = cpg.getConstantPool();
/*  39:102 */     ConstantCP cmr = (ConstantCP)cp.getConstant(this.index);
/*  40:103 */     return cp.getConstantString(cmr.getClassIndex(), (byte)7).replace('/', '.');
/*  41:    */   }
/*  42:    */   
/*  43:    */   public ObjectType getClassType(ConstantPoolGen cpg)
/*  44:    */   {
/*  45:109 */     return new ObjectType(getClassName(cpg));
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ObjectType getLoadClassType(ConstantPoolGen cpg)
/*  49:    */   {
/*  50:115 */     return getClassType(cpg);
/*  51:    */   }
/*  52:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.FieldOrMethod
 * JD-Core Version:    0.7.0.1
 */