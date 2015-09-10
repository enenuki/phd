/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class Code
/*   8:    */   extends Attribute
/*   9:    */ {
/*  10:    */   private int max_stack;
/*  11:    */   private int max_locals;
/*  12:    */   private int code_length;
/*  13:    */   private byte[] code;
/*  14:    */   private int exception_table_length;
/*  15:    */   private CodeException[] exception_table;
/*  16:    */   private int attributes_count;
/*  17:    */   private Attribute[] attributes;
/*  18:    */   
/*  19:    */   public Code(Code c)
/*  20:    */   {
/*  21: 95 */     this(c.getNameIndex(), c.getLength(), c.getMaxStack(), c.getMaxLocals(), c.getCode(), c.getExceptionTable(), c.getAttributes(), c.getConstantPool());
/*  22:    */   }
/*  23:    */   
/*  24:    */   Code(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27:110 */     this(name_index, length, file.readUnsignedShort(), file.readUnsignedShort(), (byte[])null, (CodeException[])null, (Attribute[])null, constant_pool);
/*  28:    */     
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:115 */     this.code_length = file.readInt();
/*  33:116 */     this.code = new byte[this.code_length];
/*  34:117 */     file.readFully(this.code);
/*  35:    */     
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:122 */     this.exception_table_length = file.readUnsignedShort();
/*  40:123 */     this.exception_table = new CodeException[this.exception_table_length];
/*  41:125 */     for (int i = 0; i < this.exception_table_length; i++) {
/*  42:126 */       this.exception_table[i] = new CodeException(file);
/*  43:    */     }
/*  44:131 */     this.attributes_count = file.readUnsignedShort();
/*  45:132 */     this.attributes = new Attribute[this.attributes_count];
/*  46:133 */     for (int i = 0; i < this.attributes_count; i++) {
/*  47:134 */       this.attributes[i] = Attribute.readAttribute(file, constant_pool);
/*  48:    */     }
/*  49:140 */     this.length = length;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Code(int name_index, int length, int max_stack, int max_locals, byte[] code, CodeException[] exception_table, Attribute[] attributes, ConstantPool constant_pool)
/*  53:    */   {
/*  54:160 */     super((byte)2, name_index, length, constant_pool);
/*  55:    */     
/*  56:162 */     this.max_stack = max_stack;
/*  57:163 */     this.max_locals = max_locals;
/*  58:    */     
/*  59:165 */     setCode(code);
/*  60:166 */     setExceptionTable(exception_table);
/*  61:167 */     setAttributes(attributes);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void accept(Visitor v)
/*  65:    */   {
/*  66:178 */     v.visitCode(this);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public final void dump(DataOutputStream file)
/*  70:    */     throws IOException
/*  71:    */   {
/*  72:189 */     super.dump(file);
/*  73:    */     
/*  74:191 */     file.writeShort(this.max_stack);
/*  75:192 */     file.writeShort(this.max_locals);
/*  76:193 */     file.writeInt(this.code_length);
/*  77:194 */     file.write(this.code, 0, this.code_length);
/*  78:    */     
/*  79:196 */     file.writeShort(this.exception_table_length);
/*  80:197 */     for (int i = 0; i < this.exception_table_length; i++) {
/*  81:198 */       this.exception_table[i].dump(file);
/*  82:    */     }
/*  83:200 */     file.writeShort(this.attributes_count);
/*  84:201 */     for (int i = 0; i < this.attributes_count; i++) {
/*  85:202 */       this.attributes[i].dump(file);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public final Attribute[] getAttributes()
/*  90:    */   {
/*  91:209 */     return this.attributes;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public LineNumberTable getLineNumberTable()
/*  95:    */   {
/*  96:215 */     for (int i = 0; i < this.attributes_count; i++) {
/*  97:216 */       if ((this.attributes[i] instanceof LineNumberTable)) {
/*  98:217 */         return (LineNumberTable)this.attributes[i];
/*  99:    */       }
/* 100:    */     }
/* 101:219 */     return null;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public LocalVariableTable getLocalVariableTable()
/* 105:    */   {
/* 106:226 */     for (int i = 0; i < this.attributes_count; i++) {
/* 107:227 */       if ((this.attributes[i] instanceof LocalVariableTable)) {
/* 108:228 */         return (LocalVariableTable)this.attributes[i];
/* 109:    */       }
/* 110:    */     }
/* 111:230 */     return null;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public final byte[] getCode()
/* 115:    */   {
/* 116:236 */     return this.code;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public final CodeException[] getExceptionTable()
/* 120:    */   {
/* 121:242 */     return this.exception_table;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public final int getMaxLocals()
/* 125:    */   {
/* 126:247 */     return this.max_locals;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public final int getMaxStack()
/* 130:    */   {
/* 131:253 */     return this.max_stack;
/* 132:    */   }
/* 133:    */   
/* 134:    */   private final int getInternalLength()
/* 135:    */   {
/* 136:260 */     return 8 + this.code_length + 2 + 8 * this.exception_table_length + 2;
/* 137:    */   }
/* 138:    */   
/* 139:    */   private final int calculateLength()
/* 140:    */   {
/* 141:272 */     int len = 0;
/* 142:274 */     for (int i = 0; i < this.attributes_count; i++) {
/* 143:275 */       len += this.attributes[i].length + 6;
/* 144:    */     }
/* 145:277 */     return len + getInternalLength();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public final void setAttributes(Attribute[] attributes)
/* 149:    */   {
/* 150:284 */     this.attributes = attributes;
/* 151:285 */     this.attributes_count = (attributes == null ? 0 : attributes.length);
/* 152:286 */     this.length = calculateLength();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public final void setCode(byte[] code)
/* 156:    */   {
/* 157:293 */     this.code = code;
/* 158:294 */     this.code_length = (code == null ? 0 : code.length);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public final void setExceptionTable(CodeException[] exception_table)
/* 162:    */   {
/* 163:301 */     this.exception_table = exception_table;
/* 164:302 */     this.exception_table_length = (exception_table == null ? 0 : exception_table.length);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public final void setMaxLocals(int max_locals)
/* 168:    */   {
/* 169:310 */     this.max_locals = max_locals;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public final void setMaxStack(int max_stack)
/* 173:    */   {
/* 174:317 */     this.max_stack = max_stack;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public final String toString(boolean verbose)
/* 178:    */   {
/* 179:326 */     StringBuffer buf = new StringBuffer("Code(max_stack = " + this.max_stack + ", max_locals = " + this.max_locals + ", code_length = " + this.code_length + ")\n" + Utility.codeToString(this.code, this.constant_pool, 0, -1, verbose));
/* 180:331 */     if (this.exception_table_length > 0)
/* 181:    */     {
/* 182:332 */       buf.append("\nException handler(s) = \nFrom\tTo\tHandler\tType\n");
/* 183:334 */       for (int i = 0; i < this.exception_table_length; i++) {
/* 184:335 */         buf.append(this.exception_table[i].toString(this.constant_pool, verbose) + "\n");
/* 185:    */       }
/* 186:    */     }
/* 187:338 */     if (this.attributes_count > 0)
/* 188:    */     {
/* 189:339 */       buf.append("\nAttribute(s) = \n");
/* 190:341 */       for (int i = 0; i < this.attributes_count; i++) {
/* 191:342 */         buf.append(this.attributes[i].toString() + "\n");
/* 192:    */       }
/* 193:    */     }
/* 194:345 */     return buf.toString();
/* 195:    */   }
/* 196:    */   
/* 197:    */   public final String toString()
/* 198:    */   {
/* 199:352 */     return toString(true);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Attribute copy(ConstantPool constant_pool)
/* 203:    */   {
/* 204:359 */     Code c = (Code)clone();
/* 205:360 */     c.code = ((byte[])this.code.clone());
/* 206:361 */     c.constant_pool = constant_pool;
/* 207:    */     
/* 208:363 */     c.exception_table = new CodeException[this.exception_table_length];
/* 209:364 */     for (int i = 0; i < this.exception_table_length; i++) {
/* 210:365 */       c.exception_table[i] = this.exception_table[i].copy();
/* 211:    */     }
/* 212:367 */     c.attributes = new Attribute[this.attributes_count];
/* 213:368 */     for (int i = 0; i < this.attributes_count; i++) {
/* 214:369 */       c.attributes[i] = this.attributes[i].copy(constant_pool);
/* 215:    */     }
/* 216:371 */     return c;
/* 217:    */   }
/* 218:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.Code
 * JD-Core Version:    0.7.0.1
 */