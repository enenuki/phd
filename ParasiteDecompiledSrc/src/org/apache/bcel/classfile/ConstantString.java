/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class ConstantString
/*   8:    */   extends Constant
/*   9:    */   implements ConstantObject
/*  10:    */ {
/*  11:    */   private int string_index;
/*  12:    */   
/*  13:    */   public ConstantString(ConstantString c)
/*  14:    */   {
/*  15: 76 */     this(c.getStringIndex());
/*  16:    */   }
/*  17:    */   
/*  18:    */   ConstantString(DataInputStream file)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 86 */     this(file.readUnsignedShort());
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ConstantString(int string_index)
/*  25:    */   {
/*  26: 93 */     super((byte)8);
/*  27: 94 */     this.string_index = string_index;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void accept(Visitor v)
/*  31:    */   {
/*  32:104 */     v.visitConstantString(this);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final void dump(DataOutputStream file)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38:114 */     file.writeByte(this.tag);
/*  39:115 */     file.writeShort(this.string_index);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public final int getStringIndex()
/*  43:    */   {
/*  44:120 */     return this.string_index;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public final void setStringIndex(int string_index)
/*  48:    */   {
/*  49:125 */     this.string_index = string_index;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final String toString()
/*  53:    */   {
/*  54:132 */     return super.toString() + "(string_index = " + this.string_index + ")";
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object getConstantValue(ConstantPool cp)
/*  58:    */   {
/*  59:138 */     Constant c = cp.getConstant(this.string_index, (byte)1);
/*  60:139 */     return ((ConstantUtf8)c).getBytes();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getBytes(ConstantPool cp)
/*  64:    */   {
/*  65:145 */     return (String)getConstantValue(cp);
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ConstantString
 * JD-Core Version:    0.7.0.1
 */