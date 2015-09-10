/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class ConstantInteger
/*   8:    */   extends Constant
/*   9:    */   implements ConstantObject
/*  10:    */ {
/*  11:    */   private int bytes;
/*  12:    */   
/*  13:    */   public ConstantInteger(int bytes)
/*  14:    */   {
/*  15: 79 */     super((byte)3);
/*  16: 80 */     this.bytes = bytes;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public ConstantInteger(ConstantInteger c)
/*  20:    */   {
/*  21: 87 */     this(c.getBytes());
/*  22:    */   }
/*  23:    */   
/*  24:    */   ConstantInteger(DataInputStream file)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 98 */     this(file.readInt());
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void accept(Visitor v)
/*  31:    */   {
/*  32:109 */     v.visitConstantInteger(this);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final void dump(DataOutputStream file)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38:120 */     file.writeByte(this.tag);
/*  39:121 */     file.writeInt(this.bytes);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public final int getBytes()
/*  43:    */   {
/*  44:127 */     return this.bytes;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public final void setBytes(int bytes)
/*  48:    */   {
/*  49:133 */     this.bytes = bytes;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final String toString()
/*  53:    */   {
/*  54:140 */     return super.toString() + "(bytes = " + this.bytes + ")";
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object getConstantValue(ConstantPool cp)
/*  58:    */   {
/*  59:146 */     return new Integer(this.bytes);
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ConstantInteger
 * JD-Core Version:    0.7.0.1
 */