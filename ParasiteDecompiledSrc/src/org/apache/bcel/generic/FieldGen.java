/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.util.AbstractCollection;
/*   4:    */ import java.util.AbstractList;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import org.apache.bcel.classfile.AccessFlags;
/*   8:    */ import org.apache.bcel.classfile.Attribute;
/*   9:    */ import org.apache.bcel.classfile.Constant;
/*  10:    */ import org.apache.bcel.classfile.ConstantObject;
/*  11:    */ import org.apache.bcel.classfile.ConstantPool;
/*  12:    */ import org.apache.bcel.classfile.ConstantValue;
/*  13:    */ import org.apache.bcel.classfile.Field;
/*  14:    */ import org.apache.bcel.classfile.FieldOrMethod;
/*  15:    */ import org.apache.bcel.classfile.Utility;
/*  16:    */ 
/*  17:    */ public class FieldGen
/*  18:    */   extends FieldGenOrMethodGen
/*  19:    */ {
/*  20: 72 */   private Object value = null;
/*  21:    */   private ArrayList observers;
/*  22:    */   
/*  23:    */   public FieldGen(int access_flags, Type type, String name, ConstantPoolGen cp)
/*  24:    */   {
/*  25: 85 */     setAccessFlags(access_flags);
/*  26: 86 */     setType(type);
/*  27: 87 */     setName(name);
/*  28: 88 */     setConstantPool(cp);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public FieldGen(Field field, ConstantPoolGen cp)
/*  32:    */   {
/*  33: 98 */     this(field.getAccessFlags(), Type.getType(field.getSignature()), field.getName(), cp);
/*  34:    */     
/*  35:100 */     Attribute[] attrs = field.getAttributes();
/*  36:102 */     for (int i = 0; i < attrs.length; i++) {
/*  37:103 */       if ((attrs[i] instanceof ConstantValue)) {
/*  38:104 */         setValue(((ConstantValue)attrs[i]).getConstantValueIndex());
/*  39:    */       } else {
/*  40:106 */         addAttribute(attrs[i]);
/*  41:    */       }
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   private void setValue(int index)
/*  46:    */   {
/*  47:111 */     ConstantPool cp = this.cp.getConstantPool();
/*  48:112 */     Constant c = cp.getConstant(index);
/*  49:113 */     this.value = ((ConstantObject)c).getConstantValue(cp);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setInitValue(String str)
/*  53:    */   {
/*  54:121 */     checkType(new ObjectType("java.lang.String"));
/*  55:123 */     if (str != null) {
/*  56:124 */       this.value = str;
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setInitValue(long l)
/*  61:    */   {
/*  62:128 */     checkType(Type.LONG);
/*  63:130 */     if (l != 0L) {
/*  64:131 */       this.value = new Long(l);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setInitValue(int i)
/*  69:    */   {
/*  70:135 */     checkType(Type.INT);
/*  71:137 */     if (i != 0) {
/*  72:138 */       this.value = new Integer(i);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setInitValue(short s)
/*  77:    */   {
/*  78:142 */     checkType(Type.SHORT);
/*  79:144 */     if (s != 0) {
/*  80:145 */       this.value = new Integer(s);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setInitValue(char c)
/*  85:    */   {
/*  86:149 */     checkType(Type.CHAR);
/*  87:151 */     if (c != 0) {
/*  88:152 */       this.value = new Integer(c);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setInitValue(byte b)
/*  93:    */   {
/*  94:156 */     checkType(Type.BYTE);
/*  95:158 */     if (b != 0) {
/*  96:159 */       this.value = new Integer(b);
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setInitValue(boolean b)
/* 101:    */   {
/* 102:163 */     checkType(Type.BOOLEAN);
/* 103:165 */     if (b) {
/* 104:166 */       this.value = new Integer(1);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setInitValue(float f)
/* 109:    */   {
/* 110:170 */     checkType(Type.FLOAT);
/* 111:172 */     if (f != 0.0D) {
/* 112:173 */       this.value = new Float(f);
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setInitValue(double d)
/* 117:    */   {
/* 118:177 */     checkType(Type.DOUBLE);
/* 119:179 */     if (d != 0.0D) {
/* 120:180 */       this.value = new Double(d);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void cancelInitValue()
/* 125:    */   {
/* 126:186 */     this.value = null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   private void checkType(Type atype)
/* 130:    */   {
/* 131:190 */     if (this.type == null) {
/* 132:191 */       throw new ClassGenException("You haven't defined the type of the field yet");
/* 133:    */     }
/* 134:193 */     if (!isFinal()) {
/* 135:194 */       throw new ClassGenException("Only final fields may have an initial value!");
/* 136:    */     }
/* 137:196 */     if (!this.type.equals(atype)) {
/* 138:197 */       throw new ClassGenException("Types are not compatible: " + this.type + " vs. " + atype);
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Field getField()
/* 143:    */   {
/* 144:204 */     String signature = getSignature();
/* 145:205 */     int name_index = this.cp.addUtf8(this.name);
/* 146:206 */     int signature_index = this.cp.addUtf8(signature);
/* 147:208 */     if (this.value != null)
/* 148:    */     {
/* 149:209 */       checkType(this.type);
/* 150:210 */       int index = addConstant();
/* 151:211 */       addAttribute(new ConstantValue(this.cp.addUtf8("ConstantValue"), 2, index, this.cp.getConstantPool()));
/* 152:    */     }
/* 153:215 */     return new Field(this.access_flags, name_index, signature_index, getAttributes(), this.cp.getConstantPool());
/* 154:    */   }
/* 155:    */   
/* 156:    */   private int addConstant()
/* 157:    */   {
/* 158:220 */     switch (this.type.getType())
/* 159:    */     {
/* 160:    */     case 4: 
/* 161:    */     case 5: 
/* 162:    */     case 8: 
/* 163:    */     case 9: 
/* 164:    */     case 10: 
/* 165:223 */       return this.cp.addInteger(((Integer)this.value).intValue());
/* 166:    */     case 6: 
/* 167:226 */       return this.cp.addFloat(((Float)this.value).floatValue());
/* 168:    */     case 7: 
/* 169:229 */       return this.cp.addDouble(((Double)this.value).doubleValue());
/* 170:    */     case 11: 
/* 171:232 */       return this.cp.addLong(((Long)this.value).longValue());
/* 172:    */     case 14: 
/* 173:235 */       return this.cp.addString((String)this.value);
/* 174:    */     }
/* 175:238 */     throw new RuntimeException("Oops: Unhandled : " + this.type.getType());
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String getSignature()
/* 179:    */   {
/* 180:242 */     return this.type.getSignature();
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void addObserver(FieldObserver o)
/* 184:    */   {
/* 185:249 */     if (this.observers == null) {
/* 186:250 */       this.observers = new ArrayList();
/* 187:    */     }
/* 188:252 */     this.observers.add(o);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void removeObserver(FieldObserver o)
/* 192:    */   {
/* 193:258 */     if (this.observers != null) {
/* 194:259 */       this.observers.remove(o);
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void update()
/* 199:    */   {
/* 200:267 */     if (this.observers != null) {
/* 201:268 */       for (Iterator e = this.observers.iterator(); e.hasNext();) {
/* 202:269 */         ((FieldObserver)e.next()).notify(this);
/* 203:    */       }
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String getInitValue()
/* 208:    */   {
/* 209:273 */     if (this.value != null) {
/* 210:274 */       return this.value.toString();
/* 211:    */     }
/* 212:276 */     return null;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public final String toString()
/* 216:    */   {
/* 217:288 */     String access = Utility.accessToString(this.access_flags);
/* 218:289 */     access = access + " ";
/* 219:290 */     String signature = this.type.toString();
/* 220:291 */     String name = getName();
/* 221:    */     
/* 222:293 */     StringBuffer buf = new StringBuffer(access + signature + " " + name);
/* 223:294 */     String value = getInitValue();
/* 224:296 */     if (value != null) {
/* 225:297 */       buf.append(" = " + value);
/* 226:    */     }
/* 227:299 */     return buf.toString();
/* 228:    */   }
/* 229:    */   
/* 230:    */   public FieldGen copy(ConstantPoolGen cp)
/* 231:    */   {
/* 232:305 */     FieldGen fg = (FieldGen)clone();
/* 233:    */     
/* 234:307 */     fg.setConstantPool(cp);
/* 235:308 */     return fg;
/* 236:    */   }
/* 237:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.FieldGen
 * JD-Core Version:    0.7.0.1
 */