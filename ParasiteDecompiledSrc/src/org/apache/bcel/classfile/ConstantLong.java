/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class ConstantLong
/*   8:    */   extends Constant
/*   9:    */   implements ConstantObject
/*  10:    */ {
/*  11:    */   private long bytes;
/*  12:    */   
/*  13:    */   public ConstantLong(long bytes)
/*  14:    */   {
/*  15: 77 */     super((byte)5);
/*  16: 78 */     this.bytes = bytes;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public ConstantLong(ConstantLong c)
/*  20:    */   {
/*  21: 84 */     this(c.getBytes());
/*  22:    */   }
/*  23:    */   
/*  24:    */   ConstantLong(DataInputStream file)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 94 */     this(file.readLong());
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void accept(Visitor v)
/*  31:    */   {
/*  32:104 */     v.visitConstantLong(this);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final void dump(DataOutputStream file)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38:114 */     file.writeByte(this.tag);
/*  39:115 */     file.writeLong(this.bytes);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public final long getBytes()
/*  43:    */   {
/*  44:120 */     return this.bytes;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public final void setBytes(long bytes)
/*  48:    */   {
/*  49:125 */     this.bytes = bytes;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final String toString()
/*  53:    */   {
/*  54:131 */     return super.toString() + "(bytes = " + this.bytes + ")";
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object getConstantValue(ConstantPool cp)
/*  58:    */   {
/*  59:137 */     return new Long(this.bytes);
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ConstantLong
 * JD-Core Version:    0.7.0.1
 */