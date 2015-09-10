/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public class ConstantPool
/*   8:    */   implements Cloneable, Node
/*   9:    */ {
/*  10:    */   private int constant_pool_count;
/*  11:    */   private Constant[] constant_pool;
/*  12:    */   
/*  13:    */   public ConstantPool(Constant[] constant_pool)
/*  14:    */   {
/*  15: 78 */     setConstantPool(constant_pool);
/*  16:    */   }
/*  17:    */   
/*  18:    */   ConstantPool(DataInputStream file)
/*  19:    */     throws IOException, ClassFormatError
/*  20:    */   {
/*  21: 92 */     this.constant_pool_count = file.readUnsignedShort();
/*  22: 93 */     this.constant_pool = new Constant[this.constant_pool_count];
/*  23: 98 */     for (int i = 1; i < this.constant_pool_count; i++)
/*  24:    */     {
/*  25: 99 */       this.constant_pool[i] = Constant.readConstant(file);
/*  26:    */       
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:108 */       byte tag = this.constant_pool[i].getTag();
/*  35:109 */       if ((tag == 6) || (tag == 5)) {
/*  36:110 */         i++;
/*  37:    */       }
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void accept(Visitor v)
/*  42:    */   {
/*  43:122 */     v.visitConstantPool(this);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String constantToString(Constant c)
/*  47:    */     throws ClassFormatError
/*  48:    */   {
/*  49:136 */     byte tag = c.getTag();
/*  50:    */     int i;
/*  51:    */     String str;
/*  52:138 */     switch (tag)
/*  53:    */     {
/*  54:    */     case 7: 
/*  55:140 */       i = ((ConstantClass)c).getNameIndex();
/*  56:141 */       c = getConstant(i, (byte)1);
/*  57:142 */       str = Utility.compactClassName(((ConstantUtf8)c).getBytes(), false);
/*  58:143 */       break;
/*  59:    */     case 8: 
/*  60:146 */       i = ((ConstantString)c).getStringIndex();
/*  61:147 */       c = getConstant(i, (byte)1);
/*  62:148 */       str = "\"" + escape(((ConstantUtf8)c).getBytes()) + "\"";
/*  63:149 */       break;
/*  64:    */     case 1: 
/*  65:151 */       str = ((ConstantUtf8)c).getBytes(); break;
/*  66:    */     case 6: 
/*  67:152 */       str = "" + ((ConstantDouble)c).getBytes(); break;
/*  68:    */     case 4: 
/*  69:153 */       str = "" + ((ConstantFloat)c).getBytes(); break;
/*  70:    */     case 5: 
/*  71:154 */       str = "" + ((ConstantLong)c).getBytes(); break;
/*  72:    */     case 3: 
/*  73:155 */       str = "" + ((ConstantInteger)c).getBytes(); break;
/*  74:    */     case 12: 
/*  75:158 */       str = constantToString(((ConstantNameAndType)c).getNameIndex(), (byte)1) + " " + constantToString(((ConstantNameAndType)c).getSignatureIndex(), (byte)1);
/*  76:    */       
/*  77:    */ 
/*  78:    */ 
/*  79:162 */       break;
/*  80:    */     case 9: 
/*  81:    */     case 10: 
/*  82:    */     case 11: 
/*  83:166 */       str = constantToString(((ConstantCP)c).getClassIndex(), (byte)7) + "." + constantToString(((ConstantCP)c).getNameAndTypeIndex(), (byte)12);
/*  84:    */       
/*  85:    */ 
/*  86:    */ 
/*  87:170 */       break;
/*  88:    */     case 2: 
/*  89:    */     default: 
/*  90:173 */       throw new RuntimeException("Unknown constant type " + tag);
/*  91:    */     }
/*  92:176 */     return str;
/*  93:    */   }
/*  94:    */   
/*  95:    */   private static final String escape(String str)
/*  96:    */   {
/*  97:180 */     int len = str.length();
/*  98:181 */     StringBuffer buf = new StringBuffer(len + 5);
/*  99:182 */     char[] ch = str.toCharArray();
/* 100:184 */     for (int i = 0; i < len; i++) {
/* 101:185 */       switch (ch[i])
/* 102:    */       {
/* 103:    */       case '\n': 
/* 104:186 */         buf.append("\\n"); break;
/* 105:    */       case '\r': 
/* 106:187 */         buf.append("\\r"); break;
/* 107:    */       case '\t': 
/* 108:188 */         buf.append("\\t"); break;
/* 109:    */       case '\b': 
/* 110:189 */         buf.append("\\b"); break;
/* 111:    */       case '"': 
/* 112:190 */         buf.append("\\\""); break;
/* 113:    */       default: 
/* 114:191 */         buf.append(ch[i]);
/* 115:    */       }
/* 116:    */     }
/* 117:195 */     return buf.toString();
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String constantToString(int index, byte tag)
/* 121:    */     throws ClassFormatError
/* 122:    */   {
/* 123:210 */     Constant c = getConstant(index, tag);
/* 124:211 */     return constantToString(c);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void dump(DataOutputStream file)
/* 128:    */     throws IOException
/* 129:    */   {
/* 130:222 */     file.writeShort(this.constant_pool_count);
/* 131:224 */     for (int i = 1; i < this.constant_pool_count; i++) {
/* 132:225 */       if (this.constant_pool[i] != null) {
/* 133:226 */         this.constant_pool[i].dump(file);
/* 134:    */       }
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Constant getConstant(int index)
/* 139:    */   {
/* 140:237 */     if ((index >= this.constant_pool.length) || (index < 0)) {
/* 141:238 */       throw new ClassFormatError("Invalid constant pool reference: " + index + ". Constant pool size is: " + this.constant_pool.length);
/* 142:    */     }
/* 143:241 */     return this.constant_pool[index];
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Constant getConstant(int index, byte tag)
/* 147:    */     throws ClassFormatError
/* 148:    */   {
/* 149:259 */     Constant c = getConstant(index);
/* 150:261 */     if (c == null) {
/* 151:262 */       throw new ClassFormatError("Constant pool at index " + index + " is null.");
/* 152:    */     }
/* 153:264 */     if (c.getTag() == tag) {
/* 154:265 */       return c;
/* 155:    */     }
/* 156:267 */     throw new ClassFormatError("Expected class `" + org.apache.bcel.Constants.CONSTANT_NAMES[tag] + "' at index " + index + " and got " + c);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Constant[] getConstantPool()
/* 160:    */   {
/* 161:275 */     return this.constant_pool;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String getConstantString(int index, byte tag)
/* 165:    */     throws ClassFormatError
/* 166:    */   {
/* 167:296 */     Constant c = getConstant(index, tag);
/* 168:    */     int i;
/* 169:305 */     switch (tag)
/* 170:    */     {
/* 171:    */     case 7: 
/* 172:306 */       i = ((ConstantClass)c).getNameIndex(); break;
/* 173:    */     case 8: 
/* 174:307 */       i = ((ConstantString)c).getStringIndex(); break;
/* 175:    */     default: 
/* 176:309 */       throw new RuntimeException("getConstantString called with illegal tag " + tag);
/* 177:    */     }
/* 178:313 */     c = getConstant(i, (byte)1);
/* 179:314 */     return ((ConstantUtf8)c).getBytes();
/* 180:    */   }
/* 181:    */   
/* 182:    */   public int getLength()
/* 183:    */   {
/* 184:321 */     return this.constant_pool_count;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setConstant(int index, Constant constant)
/* 188:    */   {
/* 189:328 */     this.constant_pool[index] = constant;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setConstantPool(Constant[] constant_pool)
/* 193:    */   {
/* 194:335 */     this.constant_pool = constant_pool;
/* 195:336 */     this.constant_pool_count = (constant_pool == null ? 0 : constant_pool.length);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String toString()
/* 199:    */   {
/* 200:342 */     StringBuffer buf = new StringBuffer();
/* 201:344 */     for (int i = 1; i < this.constant_pool_count; i++) {
/* 202:345 */       buf.append(i + ")" + this.constant_pool[i] + "\n");
/* 203:    */     }
/* 204:347 */     return buf.toString();
/* 205:    */   }
/* 206:    */   
/* 207:    */   public ConstantPool copy()
/* 208:    */   {
/* 209:354 */     ConstantPool c = null;
/* 210:    */     try
/* 211:    */     {
/* 212:357 */       c = (ConstantPool)clone();
/* 213:    */     }
/* 214:    */     catch (CloneNotSupportedException e) {}
/* 215:360 */     c.constant_pool = new Constant[this.constant_pool_count];
/* 216:362 */     for (int i = 1; i < this.constant_pool_count; i++) {
/* 217:363 */       if (this.constant_pool[i] != null) {
/* 218:364 */         c.constant_pool[i] = this.constant_pool[i].copy();
/* 219:    */       }
/* 220:    */     }
/* 221:367 */     return c;
/* 222:    */   }
/* 223:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.ConstantPool
 * JD-Core Version:    0.7.0.1
 */