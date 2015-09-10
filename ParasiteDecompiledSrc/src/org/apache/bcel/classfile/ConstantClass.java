/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class ConstantClass
/*   8:    */   extends Constant
/*   9:    */   implements ConstantObject
/*  10:    */ {
/*  11:    */   private int name_index;
/*  12:    */   
/*  13:    */   public ConstantClass(ConstantClass c)
/*  14:    */   {
/*  15: 76 */     this(c.getNameIndex());
/*  16:    */   }
/*  17:    */   
/*  18:    */   ConstantClass(DataInputStream file)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 87 */     this(file.readUnsignedShort());
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ConstantClass(int name_index)
/*  25:    */   {
/*  26: 94 */     super((byte)7);
/*  27: 95 */     this.name_index = name_index;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void accept(Visitor v)
/*  31:    */   {
/*  32:106 */     v.visitConstantClass(this);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final void dump(DataOutputStream file)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38:117 */     file.writeByte(this.tag);
/*  39:118 */     file.writeShort(this.name_index);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public final int getNameIndex()
/*  43:    */   {
/*  44:124 */     return this.name_index;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public final void setNameIndex(int name_index)
/*  48:    */   {
/*  49:130 */     this.name_index = name_index;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Object getConstantValue(ConstantPool cp)
/*  53:    */   {
/*  54:137 */     Constant c = cp.getConstant(this.name_index, (byte)1);
/*  55:138 */     return ((ConstantUtf8)c).getBytes();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getBytes(ConstantPool cp)
/*  59:    */   {
/*  60:144 */     return (String)getConstantValue(cp);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public final String toString()
/*  64:    */   {
/*  65:151 */     return super.toString() + "(name_index = " + this.name_index + ")";
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ConstantClass
 * JD-Core Version:    0.7.0.1
 */