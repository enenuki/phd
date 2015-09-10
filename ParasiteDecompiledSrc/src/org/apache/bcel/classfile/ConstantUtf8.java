/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class ConstantUtf8
/*   8:    */   extends Constant
/*   9:    */ {
/*  10:    */   private String bytes;
/*  11:    */   
/*  12:    */   public ConstantUtf8(ConstantUtf8 c)
/*  13:    */   {
/*  14: 76 */     this(c.getBytes());
/*  15:    */   }
/*  16:    */   
/*  17:    */   ConstantUtf8(DataInputStream file)
/*  18:    */     throws IOException
/*  19:    */   {
/*  20: 87 */     super((byte)1);
/*  21:    */     
/*  22: 89 */     this.bytes = file.readUTF();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public ConstantUtf8(String bytes)
/*  26:    */   {
/*  27: 97 */     super((byte)1);
/*  28: 98 */     this.bytes = bytes;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void accept(Visitor v)
/*  32:    */   {
/*  33:109 */     v.visitConstantUtf8(this);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public final void dump(DataOutputStream file)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39:120 */     file.writeByte(this.tag);
/*  40:121 */     file.writeUTF(this.bytes);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final String getBytes()
/*  44:    */   {
/*  45:127 */     return this.bytes;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final void setBytes(String bytes)
/*  49:    */   {
/*  50:133 */     this.bytes = bytes;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final String toString()
/*  54:    */   {
/*  55:141 */     return super.toString() + "(\"" + Utility.replace(this.bytes, "\n", "\\n") + "\")";
/*  56:    */   }
/*  57:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ConstantUtf8
 * JD-Core Version:    0.7.0.1
 */