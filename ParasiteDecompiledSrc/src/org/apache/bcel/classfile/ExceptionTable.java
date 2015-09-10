/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class ExceptionTable
/*   8:    */   extends Attribute
/*   9:    */ {
/*  10:    */   private int number_of_exceptions;
/*  11:    */   private int[] exception_index_table;
/*  12:    */   
/*  13:    */   public ExceptionTable(ExceptionTable c)
/*  14:    */   {
/*  15: 81 */     this(c.getNameIndex(), c.getLength(), c.getExceptionIndexTable(), c.getConstantPool());
/*  16:    */   }
/*  17:    */   
/*  18:    */   public ExceptionTable(int name_index, int length, int[] exception_index_table, ConstantPool constant_pool)
/*  19:    */   {
/*  20: 95 */     super((byte)3, name_index, length, constant_pool);
/*  21: 96 */     setExceptionIndexTable(exception_index_table);
/*  22:    */   }
/*  23:    */   
/*  24:    */   ExceptionTable(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27:110 */     this(name_index, length, (int[])null, constant_pool);
/*  28:    */     
/*  29:112 */     this.number_of_exceptions = file.readUnsignedShort();
/*  30:113 */     this.exception_index_table = new int[this.number_of_exceptions];
/*  31:115 */     for (int i = 0; i < this.number_of_exceptions; i++) {
/*  32:116 */       this.exception_index_table[i] = file.readUnsignedShort();
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void accept(Visitor v)
/*  37:    */   {
/*  38:127 */     v.visitExceptionTable(this);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public final void dump(DataOutputStream file)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44:138 */     super.dump(file);
/*  45:139 */     file.writeShort(this.number_of_exceptions);
/*  46:140 */     for (int i = 0; i < this.number_of_exceptions; i++) {
/*  47:141 */       file.writeShort(this.exception_index_table[i]);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public final int[] getExceptionIndexTable()
/*  52:    */   {
/*  53:147 */     return this.exception_index_table;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public final int getNumberOfExceptions()
/*  57:    */   {
/*  58:151 */     return this.number_of_exceptions;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public final String[] getExceptionNames()
/*  62:    */   {
/*  63:157 */     String[] names = new String[this.number_of_exceptions];
/*  64:158 */     for (int i = 0; i < this.number_of_exceptions; i++) {
/*  65:159 */       names[i] = this.constant_pool.getConstantString(this.exception_index_table[i], 7).replace('/', '.');
/*  66:    */     }
/*  67:162 */     return names;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final void setExceptionIndexTable(int[] exception_index_table)
/*  71:    */   {
/*  72:170 */     this.exception_index_table = exception_index_table;
/*  73:171 */     this.number_of_exceptions = (exception_index_table == null ? 0 : exception_index_table.length);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public final String toString()
/*  77:    */   {
/*  78:178 */     StringBuffer buf = new StringBuffer("");
/*  79:181 */     for (int i = 0; i < this.number_of_exceptions; i++)
/*  80:    */     {
/*  81:182 */       String str = this.constant_pool.getConstantString(this.exception_index_table[i], (byte)7);
/*  82:    */       
/*  83:184 */       buf.append(Utility.compactClassName(str, false));
/*  84:186 */       if (i < this.number_of_exceptions - 1) {
/*  85:187 */         buf.append(", ");
/*  86:    */       }
/*  87:    */     }
/*  88:190 */     return buf.toString();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Attribute copy(ConstantPool constant_pool)
/*  92:    */   {
/*  93:197 */     ExceptionTable c = (ExceptionTable)clone();
/*  94:198 */     c.exception_index_table = ((int[])this.exception_index_table.clone());
/*  95:199 */     c.constant_pool = constant_pool;
/*  96:200 */     return c;
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ExceptionTable
 * JD-Core Version:    0.7.0.1
 */