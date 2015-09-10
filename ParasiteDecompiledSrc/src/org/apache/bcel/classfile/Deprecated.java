/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ 
/*   8:    */ public final class Deprecated
/*   9:    */   extends Attribute
/*  10:    */ {
/*  11:    */   private byte[] bytes;
/*  12:    */   
/*  13:    */   public Deprecated(Deprecated c)
/*  14:    */   {
/*  15: 77 */     this(c.getNameIndex(), c.getLength(), c.getBytes(), c.getConstantPool());
/*  16:    */   }
/*  17:    */   
/*  18:    */   public Deprecated(int name_index, int length, byte[] bytes, ConstantPool constant_pool)
/*  19:    */   {
/*  20: 90 */     super((byte)8, name_index, length, constant_pool);
/*  21: 91 */     this.bytes = bytes;
/*  22:    */   }
/*  23:    */   
/*  24:    */   Deprecated(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27:105 */     this(name_index, length, (byte[])null, constant_pool);
/*  28:107 */     if (length > 0)
/*  29:    */     {
/*  30:108 */       this.bytes = new byte[length];
/*  31:109 */       file.readFully(this.bytes);
/*  32:110 */       System.err.println("Deprecated attribute with length > 0");
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void accept(Visitor v)
/*  37:    */   {
/*  38:122 */     v.visitDeprecated(this);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public final void dump(DataOutputStream file)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44:133 */     super.dump(file);
/*  45:135 */     if (this.length > 0) {
/*  46:136 */       file.write(this.bytes, 0, this.length);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public final byte[] getBytes()
/*  51:    */   {
/*  52:142 */     return this.bytes;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final void setBytes(byte[] bytes)
/*  56:    */   {
/*  57:148 */     this.bytes = bytes;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final String toString()
/*  61:    */   {
/*  62:155 */     return org.apache.bcel.Constants.ATTRIBUTE_NAMES[8];
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Attribute copy(ConstantPool constant_pool)
/*  66:    */   {
/*  67:162 */     Deprecated c = (Deprecated)clone();
/*  68:164 */     if (this.bytes != null) {
/*  69:165 */       c.bytes = ((byte[])this.bytes.clone());
/*  70:    */     }
/*  71:167 */     c.constant_pool = constant_pool;
/*  72:168 */     return c;
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.Deprecated
 * JD-Core Version:    0.7.0.1
 */