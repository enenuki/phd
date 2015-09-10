/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class LineNumberTable
/*   8:    */   extends Attribute
/*   9:    */ {
/*  10:    */   private int line_number_table_length;
/*  11:    */   private LineNumber[] line_number_table;
/*  12:    */   
/*  13:    */   public LineNumberTable(LineNumberTable c)
/*  14:    */   {
/*  15: 79 */     this(c.getNameIndex(), c.getLength(), c.getLineNumberTable(), c.getConstantPool());
/*  16:    */   }
/*  17:    */   
/*  18:    */   public LineNumberTable(int name_index, int length, LineNumber[] line_number_table, ConstantPool constant_pool)
/*  19:    */   {
/*  20: 93 */     super((byte)4, name_index, length, constant_pool);
/*  21: 94 */     setLineNumberTable(line_number_table);
/*  22:    */   }
/*  23:    */   
/*  24:    */   LineNumberTable(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27:108 */     this(name_index, length, (LineNumber[])null, constant_pool);
/*  28:109 */     this.line_number_table_length = file.readUnsignedShort();
/*  29:110 */     this.line_number_table = new LineNumber[this.line_number_table_length];
/*  30:112 */     for (int i = 0; i < this.line_number_table_length; i++) {
/*  31:113 */       this.line_number_table[i] = new LineNumber(file);
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void accept(Visitor v)
/*  36:    */   {
/*  37:123 */     v.visitLineNumberTable(this);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final void dump(DataOutputStream file)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43:133 */     super.dump(file);
/*  44:134 */     file.writeShort(this.line_number_table_length);
/*  45:135 */     for (int i = 0; i < this.line_number_table_length; i++) {
/*  46:136 */       this.line_number_table[i].dump(file);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public final LineNumber[] getLineNumberTable()
/*  51:    */   {
/*  52:142 */     return this.line_number_table;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final void setLineNumberTable(LineNumber[] line_number_table)
/*  56:    */   {
/*  57:148 */     this.line_number_table = line_number_table;
/*  58:    */     
/*  59:150 */     this.line_number_table_length = (line_number_table == null ? 0 : line_number_table.length);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final String toString()
/*  63:    */   {
/*  64:158 */     StringBuffer buf = new StringBuffer();
/*  65:159 */     StringBuffer line = new StringBuffer();
/*  66:161 */     for (int i = 0; i < this.line_number_table_length; i++)
/*  67:    */     {
/*  68:162 */       line.append(this.line_number_table[i].toString());
/*  69:164 */       if (i < this.line_number_table_length - 1) {
/*  70:165 */         line.append(", ");
/*  71:    */       }
/*  72:167 */       if (line.length() > 72)
/*  73:    */       {
/*  74:168 */         line.append('\n');
/*  75:169 */         buf.append(line);
/*  76:170 */         line.setLength(0);
/*  77:    */       }
/*  78:    */     }
/*  79:174 */     buf.append(line);
/*  80:    */     
/*  81:176 */     return buf.toString();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getSourceLine(int pos)
/*  85:    */   {
/*  86:186 */     int l = 0;int r = this.line_number_table_length - 1;
/*  87:188 */     if (r < 0) {
/*  88:189 */       return -1;
/*  89:    */     }
/*  90:191 */     int min_index = -1;int min = -1;
/*  91:    */     do
/*  92:    */     {
/*  93:196 */       int i = (l + r) / 2;
/*  94:197 */       int j = this.line_number_table[i].getStartPC();
/*  95:199 */       if (j == pos) {
/*  96:200 */         return this.line_number_table[i].getLineNumber();
/*  97:    */       }
/*  98:201 */       if (pos < j) {
/*  99:202 */         r = i - 1;
/* 100:    */       } else {
/* 101:204 */         l = i + 1;
/* 102:    */       }
/* 103:210 */       if ((j < pos) && (j > min))
/* 104:    */       {
/* 105:211 */         min = j;
/* 106:212 */         min_index = i;
/* 107:    */       }
/* 108:214 */     } while (l <= r);
/* 109:216 */     return this.line_number_table[min_index].getLineNumber();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Attribute copy(ConstantPool constant_pool)
/* 113:    */   {
/* 114:223 */     LineNumberTable c = (LineNumberTable)clone();
/* 115:    */     
/* 116:225 */     c.line_number_table = new LineNumber[this.line_number_table_length];
/* 117:226 */     for (int i = 0; i < this.line_number_table_length; i++) {
/* 118:227 */       c.line_number_table[i] = this.line_number_table[i].copy();
/* 119:    */     }
/* 120:229 */     c.constant_pool = constant_pool;
/* 121:230 */     return c;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public final int getTableLength()
/* 125:    */   {
/* 126:233 */     return this.line_number_table_length;
/* 127:    */   }
/* 128:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.LineNumberTable
 * JD-Core Version:    0.7.0.1
 */