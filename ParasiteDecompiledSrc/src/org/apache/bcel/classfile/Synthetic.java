/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ 
/*   8:    */ public final class Synthetic
/*   9:    */   extends Attribute
/*  10:    */ {
/*  11:    */   private byte[] bytes;
/*  12:    */   
/*  13:    */   public Synthetic(Synthetic c)
/*  14:    */   {
/*  15: 77 */     this(c.getNameIndex(), c.getLength(), c.getBytes(), c.getConstantPool());
/*  16:    */   }
/*  17:    */   
/*  18:    */   public Synthetic(int name_index, int length, byte[] bytes, ConstantPool constant_pool)
/*  19:    */   {
/*  20: 90 */     super((byte)7, name_index, length, constant_pool);
/*  21: 91 */     this.bytes = bytes;
/*  22:    */   }
/*  23:    */   
/*  24:    */   Synthetic(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27:105 */     this(name_index, length, (byte[])null, constant_pool);
/*  28:107 */     if (length > 0)
/*  29:    */     {
/*  30:108 */       this.bytes = new byte[length];
/*  31:109 */       file.readFully(this.bytes);
/*  32:110 */       System.err.println("Synthetic attribute with length > 0");
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void accept(Visitor v)
/*  37:    */   {
/*  38:121 */     v.visitSynthetic(this);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public final void dump(DataOutputStream file)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44:131 */     super.dump(file);
/*  45:132 */     if (this.length > 0) {
/*  46:133 */       file.write(this.bytes, 0, this.length);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public final byte[] getBytes()
/*  51:    */   {
/*  52:138 */     return this.bytes;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final void setBytes(byte[] bytes)
/*  56:    */   {
/*  57:144 */     this.bytes = bytes;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final String toString()
/*  61:    */   {
/*  62:151 */     StringBuffer buf = new StringBuffer("Synthetic");
/*  63:153 */     if (this.length > 0) {
/*  64:154 */       buf.append(" " + Utility.toHexString(this.bytes));
/*  65:    */     }
/*  66:156 */     return buf.toString();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Attribute copy(ConstantPool constant_pool)
/*  70:    */   {
/*  71:163 */     Synthetic c = (Synthetic)clone();
/*  72:165 */     if (this.bytes != null) {
/*  73:166 */       c.bytes = ((byte[])this.bytes.clone());
/*  74:    */     }
/*  75:168 */     c.constant_pool = constant_pool;
/*  76:169 */     return c;
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.Synthetic
 * JD-Core Version:    0.7.0.1
 */