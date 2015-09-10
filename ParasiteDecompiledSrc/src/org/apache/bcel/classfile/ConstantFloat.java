/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class ConstantFloat
/*   8:    */   extends Constant
/*   9:    */   implements ConstantObject
/*  10:    */ {
/*  11:    */   private float bytes;
/*  12:    */   
/*  13:    */   public ConstantFloat(float bytes)
/*  14:    */   {
/*  15: 77 */     super((byte)4);
/*  16: 78 */     this.bytes = bytes;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public ConstantFloat(ConstantFloat c)
/*  20:    */   {
/*  21: 85 */     this(c.getBytes());
/*  22:    */   }
/*  23:    */   
/*  24:    */   ConstantFloat(DataInputStream file)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 95 */     this(file.readFloat());
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void accept(Visitor v)
/*  31:    */   {
/*  32:105 */     v.visitConstantFloat(this);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final void dump(DataOutputStream file)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38:115 */     file.writeByte(this.tag);
/*  39:116 */     file.writeFloat(this.bytes);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public final float getBytes()
/*  43:    */   {
/*  44:121 */     return this.bytes;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public final void setBytes(float bytes)
/*  48:    */   {
/*  49:126 */     this.bytes = bytes;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final String toString()
/*  53:    */   {
/*  54:133 */     return super.toString() + "(bytes = " + this.bytes + ")";
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object getConstantValue(ConstantPool cp)
/*  58:    */   {
/*  59:139 */     return new Float(this.bytes);
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ConstantFloat
 * JD-Core Version:    0.7.0.1
 */