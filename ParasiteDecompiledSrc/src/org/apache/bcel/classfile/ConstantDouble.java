/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class ConstantDouble
/*   8:    */   extends Constant
/*   9:    */   implements ConstantObject
/*  10:    */ {
/*  11:    */   private double bytes;
/*  12:    */   
/*  13:    */   public ConstantDouble(double bytes)
/*  14:    */   {
/*  15: 76 */     super((byte)6);
/*  16: 77 */     this.bytes = bytes;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public ConstantDouble(ConstantDouble c)
/*  20:    */   {
/*  21: 84 */     this(c.getBytes());
/*  22:    */   }
/*  23:    */   
/*  24:    */   ConstantDouble(DataInputStream file)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 95 */     this(file.readDouble());
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void accept(Visitor v)
/*  31:    */   {
/*  32:106 */     v.visitConstantDouble(this);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final void dump(DataOutputStream file)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38:116 */     file.writeByte(this.tag);
/*  39:117 */     file.writeDouble(this.bytes);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public final double getBytes()
/*  43:    */   {
/*  44:122 */     return this.bytes;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public final void setBytes(double bytes)
/*  48:    */   {
/*  49:127 */     this.bytes = bytes;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final String toString()
/*  53:    */   {
/*  54:134 */     return super.toString() + "(bytes = " + this.bytes + ")";
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object getConstantValue(ConstantPool cp)
/*  58:    */   {
/*  59:140 */     return new Double(this.bytes);
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ConstantDouble
 * JD-Core Version:    0.7.0.1
 */