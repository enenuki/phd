/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class ConstantValue
/*   8:    */   extends Attribute
/*   9:    */ {
/*  10:    */   private int constantvalue_index;
/*  11:    */   
/*  12:    */   public ConstantValue(ConstantValue c)
/*  13:    */   {
/*  14: 77 */     this(c.getNameIndex(), c.getLength(), c.getConstantValueIndex(), c.getConstantPool());
/*  15:    */   }
/*  16:    */   
/*  17:    */   ConstantValue(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
/*  18:    */     throws IOException
/*  19:    */   {
/*  20: 92 */     this(name_index, length, file.readUnsignedShort(), constant_pool);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public ConstantValue(int name_index, int length, int constantvalue_index, ConstantPool constant_pool)
/*  24:    */   {
/*  25:105 */     super((byte)1, name_index, length, constant_pool);
/*  26:106 */     this.constantvalue_index = constantvalue_index;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void accept(Visitor v)
/*  30:    */   {
/*  31:117 */     v.visitConstantValue(this);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public final void dump(DataOutputStream file)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37:127 */     super.dump(file);
/*  38:128 */     file.writeShort(this.constantvalue_index);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public final int getConstantValueIndex()
/*  42:    */   {
/*  43:133 */     return this.constantvalue_index;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public final void setConstantValueIndex(int constantvalue_index)
/*  47:    */   {
/*  48:139 */     this.constantvalue_index = constantvalue_index;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public final String toString()
/*  52:    */     throws InternalError
/*  53:    */   {
/*  54:147 */     Constant c = this.constant_pool.getConstant(this.constantvalue_index);
/*  55:    */     String buf;
/*  56:153 */     switch (c.getTag())
/*  57:    */     {
/*  58:    */     case 5: 
/*  59:154 */       buf = "" + ((ConstantLong)c).getBytes(); break;
/*  60:    */     case 4: 
/*  61:155 */       buf = "" + ((ConstantFloat)c).getBytes(); break;
/*  62:    */     case 6: 
/*  63:156 */       buf = "" + ((ConstantDouble)c).getBytes(); break;
/*  64:    */     case 3: 
/*  65:157 */       buf = "" + ((ConstantInteger)c).getBytes(); break;
/*  66:    */     case 8: 
/*  67:159 */       int i = ((ConstantString)c).getStringIndex();
/*  68:160 */       c = this.constant_pool.getConstant(i, (byte)1);
/*  69:161 */       buf = "\"" + convertString(((ConstantUtf8)c).getBytes()) + "\"";
/*  70:162 */       break;
/*  71:    */     case 7: 
/*  72:    */     default: 
/*  73:163 */       throw new InternalError("Type of ConstValue invalid: " + c);
/*  74:    */     }
/*  75:166 */     return buf;
/*  76:    */   }
/*  77:    */   
/*  78:    */   private static final String convertString(String label)
/*  79:    */   {
/*  80:173 */     char[] ch = label.toCharArray();
/*  81:174 */     StringBuffer buf = new StringBuffer();
/*  82:176 */     for (int i = 0; i < ch.length; i++) {
/*  83:177 */       switch (ch[i])
/*  84:    */       {
/*  85:    */       case '\n': 
/*  86:179 */         buf.append("\\n"); break;
/*  87:    */       case '\r': 
/*  88:181 */         buf.append("\\r"); break;
/*  89:    */       case '"': 
/*  90:183 */         buf.append("\\\""); break;
/*  91:    */       case '\'': 
/*  92:185 */         buf.append("\\'"); break;
/*  93:    */       case '\\': 
/*  94:187 */         buf.append("\\\\"); break;
/*  95:    */       default: 
/*  96:189 */         buf.append(ch[i]);
/*  97:    */       }
/*  98:    */     }
/*  99:193 */     return buf.toString();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Attribute copy(ConstantPool constant_pool)
/* 103:    */   {
/* 104:200 */     ConstantValue c = (ConstantValue)clone();
/* 105:201 */     c.constant_pool = constant_pool;
/* 106:202 */     return c;
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ConstantValue
 * JD-Core Version:    0.7.0.1
 */