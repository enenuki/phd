/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public class LocalVariableTable
/*   8:    */   extends Attribute
/*   9:    */ {
/*  10:    */   private int local_variable_table_length;
/*  11:    */   private LocalVariable[] local_variable_table;
/*  12:    */   
/*  13:    */   public LocalVariableTable(LocalVariableTable c)
/*  14:    */   {
/*  15: 78 */     this(c.getNameIndex(), c.getLength(), c.getLocalVariableTable(), c.getConstantPool());
/*  16:    */   }
/*  17:    */   
/*  18:    */   public LocalVariableTable(int name_index, int length, LocalVariable[] local_variable_table, ConstantPool constant_pool)
/*  19:    */   {
/*  20: 92 */     super((byte)5, name_index, length, constant_pool);
/*  21: 93 */     setLocalVariableTable(local_variable_table);
/*  22:    */   }
/*  23:    */   
/*  24:    */   LocalVariableTable(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27:107 */     this(name_index, length, (LocalVariable[])null, constant_pool);
/*  28:    */     
/*  29:109 */     this.local_variable_table_length = file.readUnsignedShort();
/*  30:110 */     this.local_variable_table = new LocalVariable[this.local_variable_table_length];
/*  31:112 */     for (int i = 0; i < this.local_variable_table_length; i++) {
/*  32:113 */       this.local_variable_table[i] = new LocalVariable(file, constant_pool);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void accept(Visitor v)
/*  37:    */   {
/*  38:124 */     v.visitLocalVariableTable(this);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public final void dump(DataOutputStream file)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44:135 */     super.dump(file);
/*  45:136 */     file.writeShort(this.local_variable_table_length);
/*  46:137 */     for (int i = 0; i < this.local_variable_table_length; i++) {
/*  47:138 */       this.local_variable_table[i].dump(file);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public final LocalVariable[] getLocalVariableTable()
/*  52:    */   {
/*  53:145 */     return this.local_variable_table;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public final LocalVariable getLocalVariable(int index)
/*  57:    */   {
/*  58:151 */     for (int i = 0; i < this.local_variable_table_length; i++) {
/*  59:152 */       if (this.local_variable_table[i].getIndex() == index) {
/*  60:153 */         return this.local_variable_table[i];
/*  61:    */       }
/*  62:    */     }
/*  63:155 */     return null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public final void setLocalVariableTable(LocalVariable[] local_variable_table)
/*  67:    */   {
/*  68:160 */     this.local_variable_table = local_variable_table;
/*  69:161 */     this.local_variable_table_length = (local_variable_table == null ? 0 : local_variable_table.length);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public final String toString()
/*  73:    */   {
/*  74:169 */     StringBuffer buf = new StringBuffer("");
/*  75:171 */     for (int i = 0; i < this.local_variable_table_length; i++)
/*  76:    */     {
/*  77:172 */       buf.append(this.local_variable_table[i].toString());
/*  78:174 */       if (i < this.local_variable_table_length - 1) {
/*  79:175 */         buf.append('\n');
/*  80:    */       }
/*  81:    */     }
/*  82:178 */     return buf.toString();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Attribute copy(ConstantPool constant_pool)
/*  86:    */   {
/*  87:185 */     LocalVariableTable c = (LocalVariableTable)clone();
/*  88:    */     
/*  89:187 */     c.local_variable_table = new LocalVariable[this.local_variable_table_length];
/*  90:188 */     for (int i = 0; i < this.local_variable_table_length; i++) {
/*  91:189 */       c.local_variable_table[i] = this.local_variable_table[i].copy();
/*  92:    */     }
/*  93:191 */     c.constant_pool = constant_pool;
/*  94:192 */     return c;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final int getTableLength()
/*  98:    */   {
/*  99:195 */     return this.local_variable_table_length;
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.LocalVariableTable
 * JD-Core Version:    0.7.0.1
 */