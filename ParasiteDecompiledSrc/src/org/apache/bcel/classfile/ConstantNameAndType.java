/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class ConstantNameAndType
/*   8:    */   extends Constant
/*   9:    */ {
/*  10:    */   private int name_index;
/*  11:    */   private int signature_index;
/*  12:    */   
/*  13:    */   public ConstantNameAndType(ConstantNameAndType c)
/*  14:    */   {
/*  15: 78 */     this(c.getNameIndex(), c.getSignatureIndex());
/*  16:    */   }
/*  17:    */   
/*  18:    */   ConstantNameAndType(DataInputStream file)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 89 */     this(file.readUnsignedShort(), file.readUnsignedShort());
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ConstantNameAndType(int name_index, int signature_index)
/*  25:    */   {
/*  26: 99 */     super((byte)12);
/*  27:100 */     this.name_index = name_index;
/*  28:101 */     this.signature_index = signature_index;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void accept(Visitor v)
/*  32:    */   {
/*  33:112 */     v.visitConstantNameAndType(this);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public final void dump(DataOutputStream file)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39:123 */     file.writeByte(this.tag);
/*  40:124 */     file.writeShort(this.name_index);
/*  41:125 */     file.writeShort(this.signature_index);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public final int getNameIndex()
/*  45:    */   {
/*  46:131 */     return this.name_index;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public final String getName(ConstantPool cp)
/*  50:    */   {
/*  51:136 */     return cp.constantToString(getNameIndex(), (byte)1);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final int getSignatureIndex()
/*  55:    */   {
/*  56:142 */     return this.signature_index;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final String getSignature(ConstantPool cp)
/*  60:    */   {
/*  61:147 */     return cp.constantToString(getSignatureIndex(), (byte)1);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final void setNameIndex(int name_index)
/*  65:    */   {
/*  66:154 */     this.name_index = name_index;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public final void setSignatureIndex(int signature_index)
/*  70:    */   {
/*  71:161 */     this.signature_index = signature_index;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public final String toString()
/*  75:    */   {
/*  76:168 */     return super.toString() + "(name_index = " + this.name_index + ", signature_index = " + this.signature_index + ")";
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ConstantNameAndType
 * JD-Core Version:    0.7.0.1
 */