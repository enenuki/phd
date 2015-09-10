/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.ListIterator;
/*  10:    */ import java.util.Map;
/*  11:    */ 
/*  12:    */ public class AttributeInfo
/*  13:    */ {
/*  14:    */   protected ConstPool constPool;
/*  15:    */   int name;
/*  16:    */   byte[] info;
/*  17:    */   
/*  18:    */   protected AttributeInfo(ConstPool cp, int attrname, byte[] attrinfo)
/*  19:    */   {
/*  20: 39 */     this.constPool = cp;
/*  21: 40 */     this.name = attrname;
/*  22: 41 */     this.info = attrinfo;
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected AttributeInfo(ConstPool cp, String attrname)
/*  26:    */   {
/*  27: 45 */     this(cp, attrname, (byte[])null);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public AttributeInfo(ConstPool cp, String attrname, byte[] attrinfo)
/*  31:    */   {
/*  32: 57 */     this(cp, cp.addUtf8Info(attrname), attrinfo);
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected AttributeInfo(ConstPool cp, int n, DataInputStream in)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38: 63 */     this.constPool = cp;
/*  39: 64 */     this.name = n;
/*  40: 65 */     int len = in.readInt();
/*  41: 66 */     this.info = new byte[len];
/*  42: 67 */     if (len > 0) {
/*  43: 68 */       in.readFully(this.info);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   static AttributeInfo read(ConstPool cp, DataInputStream in)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50: 74 */     int name = in.readUnsignedShort();
/*  51: 75 */     String nameStr = cp.getUtf8Info(name);
/*  52: 76 */     if (nameStr.charAt(0) < 'L')
/*  53:    */     {
/*  54: 77 */       if (nameStr.equals("AnnotationDefault")) {
/*  55: 78 */         return new AnnotationDefaultAttribute(cp, name, in);
/*  56:    */       }
/*  57: 79 */       if (nameStr.equals("Code")) {
/*  58: 80 */         return new CodeAttribute(cp, name, in);
/*  59:    */       }
/*  60: 81 */       if (nameStr.equals("ConstantValue")) {
/*  61: 82 */         return new ConstantAttribute(cp, name, in);
/*  62:    */       }
/*  63: 83 */       if (nameStr.equals("Deprecated")) {
/*  64: 84 */         return new DeprecatedAttribute(cp, name, in);
/*  65:    */       }
/*  66: 85 */       if (nameStr.equals("EnclosingMethod")) {
/*  67: 86 */         return new EnclosingMethodAttribute(cp, name, in);
/*  68:    */       }
/*  69: 87 */       if (nameStr.equals("Exceptions")) {
/*  70: 88 */         return new ExceptionsAttribute(cp, name, in);
/*  71:    */       }
/*  72: 89 */       if (nameStr.equals("InnerClasses")) {
/*  73: 90 */         return new InnerClassesAttribute(cp, name, in);
/*  74:    */       }
/*  75:    */     }
/*  76:    */     else
/*  77:    */     {
/*  78: 95 */       if (nameStr.equals("LineNumberTable")) {
/*  79: 96 */         return new LineNumberAttribute(cp, name, in);
/*  80:    */       }
/*  81: 97 */       if (nameStr.equals("LocalVariableTable")) {
/*  82: 98 */         return new LocalVariableAttribute(cp, name, in);
/*  83:    */       }
/*  84: 99 */       if (nameStr.equals("LocalVariableTypeTable")) {
/*  85:100 */         return new LocalVariableTypeAttribute(cp, name, in);
/*  86:    */       }
/*  87:101 */       if ((nameStr.equals("RuntimeVisibleAnnotations")) || (nameStr.equals("RuntimeInvisibleAnnotations"))) {
/*  88:104 */         return new AnnotationsAttribute(cp, name, in);
/*  89:    */       }
/*  90:106 */       if ((nameStr.equals("RuntimeVisibleParameterAnnotations")) || (nameStr.equals("RuntimeInvisibleParameterAnnotations"))) {
/*  91:108 */         return new ParameterAnnotationsAttribute(cp, name, in);
/*  92:    */       }
/*  93:109 */       if (nameStr.equals("Signature")) {
/*  94:110 */         return new SignatureAttribute(cp, name, in);
/*  95:    */       }
/*  96:111 */       if (nameStr.equals("SourceFile")) {
/*  97:112 */         return new SourceFileAttribute(cp, name, in);
/*  98:    */       }
/*  99:113 */       if (nameStr.equals("Synthetic")) {
/* 100:114 */         return new SyntheticAttribute(cp, name, in);
/* 101:    */       }
/* 102:115 */       if (nameStr.equals("StackMap")) {
/* 103:116 */         return new StackMap(cp, name, in);
/* 104:    */       }
/* 105:117 */       if (nameStr.equals("StackMapTable")) {
/* 106:118 */         return new StackMapTable(cp, name, in);
/* 107:    */       }
/* 108:    */     }
/* 109:121 */     return new AttributeInfo(cp, name, in);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getName()
/* 113:    */   {
/* 114:128 */     return this.constPool.getUtf8Info(this.name);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public ConstPool getConstPool()
/* 118:    */   {
/* 119:134 */     return this.constPool;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int length()
/* 123:    */   {
/* 124:142 */     return this.info.length + 6;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public byte[] get()
/* 128:    */   {
/* 129:152 */     return this.info;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void set(byte[] newinfo)
/* 133:    */   {
/* 134:161 */     this.info = newinfo;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/* 138:    */   {
/* 139:172 */     int s = this.info.length;
/* 140:173 */     byte[] srcInfo = this.info;
/* 141:174 */     byte[] newInfo = new byte[s];
/* 142:175 */     for (int i = 0; i < s; i++) {
/* 143:176 */       newInfo[i] = srcInfo[i];
/* 144:    */     }
/* 145:178 */     return new AttributeInfo(newCp, getName(), newInfo);
/* 146:    */   }
/* 147:    */   
/* 148:    */   void write(DataOutputStream out)
/* 149:    */     throws IOException
/* 150:    */   {
/* 151:182 */     out.writeShort(this.name);
/* 152:183 */     out.writeInt(this.info.length);
/* 153:184 */     if (this.info.length > 0) {
/* 154:185 */       out.write(this.info);
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   static int getLength(ArrayList list)
/* 159:    */   {
/* 160:189 */     int size = 0;
/* 161:190 */     int n = list.size();
/* 162:191 */     for (int i = 0; i < n; i++)
/* 163:    */     {
/* 164:192 */       AttributeInfo attr = (AttributeInfo)list.get(i);
/* 165:193 */       size += attr.length();
/* 166:    */     }
/* 167:196 */     return size;
/* 168:    */   }
/* 169:    */   
/* 170:    */   static AttributeInfo lookup(ArrayList list, String name)
/* 171:    */   {
/* 172:200 */     if (list == null) {
/* 173:201 */       return null;
/* 174:    */     }
/* 175:203 */     ListIterator iterator = list.listIterator();
/* 176:204 */     while (iterator.hasNext())
/* 177:    */     {
/* 178:205 */       AttributeInfo ai = (AttributeInfo)iterator.next();
/* 179:206 */       if (ai.getName().equals(name)) {
/* 180:207 */         return ai;
/* 181:    */       }
/* 182:    */     }
/* 183:210 */     return null;
/* 184:    */   }
/* 185:    */   
/* 186:    */   static synchronized void remove(ArrayList list, String name)
/* 187:    */   {
/* 188:214 */     if (list == null) {
/* 189:215 */       return;
/* 190:    */     }
/* 191:217 */     ListIterator iterator = list.listIterator();
/* 192:218 */     while (iterator.hasNext())
/* 193:    */     {
/* 194:219 */       AttributeInfo ai = (AttributeInfo)iterator.next();
/* 195:220 */       if (ai.getName().equals(name)) {
/* 196:221 */         iterator.remove();
/* 197:    */       }
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   static void writeAll(ArrayList list, DataOutputStream out)
/* 202:    */     throws IOException
/* 203:    */   {
/* 204:228 */     if (list == null) {
/* 205:229 */       return;
/* 206:    */     }
/* 207:231 */     int n = list.size();
/* 208:232 */     for (int i = 0; i < n; i++)
/* 209:    */     {
/* 210:233 */       AttributeInfo attr = (AttributeInfo)list.get(i);
/* 211:234 */       attr.write(out);
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   static ArrayList copyAll(ArrayList list, ConstPool cp)
/* 216:    */   {
/* 217:239 */     if (list == null) {
/* 218:240 */       return null;
/* 219:    */     }
/* 220:242 */     ArrayList newList = new ArrayList();
/* 221:243 */     int n = list.size();
/* 222:244 */     for (int i = 0; i < n; i++)
/* 223:    */     {
/* 224:245 */       AttributeInfo attr = (AttributeInfo)list.get(i);
/* 225:246 */       newList.add(attr.copy(cp, null));
/* 226:    */     }
/* 227:249 */     return newList;
/* 228:    */   }
/* 229:    */   
/* 230:    */   void renameClass(String oldname, String newname) {}
/* 231:    */   
/* 232:    */   void renameClass(Map classnames) {}
/* 233:    */   
/* 234:    */   static void renameClass(List attributes, String oldname, String newname)
/* 235:    */   {
/* 236:261 */     Iterator iterator = attributes.iterator();
/* 237:262 */     while (iterator.hasNext())
/* 238:    */     {
/* 239:263 */       AttributeInfo ai = (AttributeInfo)iterator.next();
/* 240:264 */       ai.renameClass(oldname, newname);
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   static void renameClass(List attributes, Map classnames)
/* 245:    */   {
/* 246:269 */     Iterator iterator = attributes.iterator();
/* 247:270 */     while (iterator.hasNext())
/* 248:    */     {
/* 249:271 */       AttributeInfo ai = (AttributeInfo)iterator.next();
/* 250:272 */       ai.renameClass(classnames);
/* 251:    */     }
/* 252:    */   }
/* 253:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.AttributeInfo
 * JD-Core Version:    0.7.0.1
 */