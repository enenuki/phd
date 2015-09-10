/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class SourceFile
/*   8:    */   extends Attribute
/*   9:    */ {
/*  10:    */   private int sourcefile_index;
/*  11:    */   
/*  12:    */   public SourceFile(SourceFile c)
/*  13:    */   {
/*  14: 77 */     this(c.getNameIndex(), c.getLength(), c.getSourceFileIndex(), c.getConstantPool());
/*  15:    */   }
/*  16:    */   
/*  17:    */   SourceFile(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
/*  18:    */     throws IOException
/*  19:    */   {
/*  20: 92 */     this(name_index, length, file.readUnsignedShort(), constant_pool);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public SourceFile(int name_index, int length, int sourcefile_index, ConstantPool constant_pool)
/*  24:    */   {
/*  25:104 */     super((byte)0, name_index, length, constant_pool);
/*  26:105 */     this.sourcefile_index = sourcefile_index;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void accept(Visitor v)
/*  30:    */   {
/*  31:116 */     v.visitSourceFile(this);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public final void dump(DataOutputStream file)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37:127 */     super.dump(file);
/*  38:128 */     file.writeShort(this.sourcefile_index);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public final int getSourceFileIndex()
/*  42:    */   {
/*  43:134 */     return this.sourcefile_index;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public final void setSourceFileIndex(int sourcefile_index)
/*  47:    */   {
/*  48:140 */     this.sourcefile_index = sourcefile_index;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public final String getSourceFileName()
/*  52:    */   {
/*  53:147 */     ConstantUtf8 c = (ConstantUtf8)this.constant_pool.getConstant(this.sourcefile_index, (byte)1);
/*  54:    */     
/*  55:149 */     return c.getBytes();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public final String toString()
/*  59:    */   {
/*  60:156 */     return "SourceFile(" + getSourceFileName() + ")";
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Attribute copy(ConstantPool constant_pool)
/*  64:    */   {
/*  65:163 */     return (SourceFile)clone();
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.SourceFile
 * JD-Core Version:    0.7.0.1
 */