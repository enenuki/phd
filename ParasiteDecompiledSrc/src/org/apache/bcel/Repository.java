/*   1:    */ package org.apache.bcel;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import org.apache.bcel.classfile.AccessFlags;
/*   8:    */ import org.apache.bcel.classfile.ClassParser;
/*   9:    */ import org.apache.bcel.classfile.JavaClass;
/*  10:    */ import org.apache.bcel.util.ClassPath;
/*  11:    */ import org.apache.bcel.util.ClassPath.ClassFile;
/*  12:    */ import org.apache.bcel.util.ClassQueue;
/*  13:    */ import org.apache.bcel.util.ClassVector;
/*  14:    */ 
/*  15:    */ public abstract class Repository
/*  16:    */ {
/*  17: 75 */   private static ClassPath class_path = new ClassPath();
/*  18:    */   private static HashMap classes;
/*  19:    */   private static JavaClass OBJECT;
/*  20:    */   
/*  21:    */   static
/*  22:    */   {
/*  23: 79 */     clearCache();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static JavaClass lookupClass(String class_name)
/*  27:    */   {
/*  28: 84 */     if ((class_name == null) || (class_name.equals(""))) {
/*  29: 85 */       throw new RuntimeException("Invalid class name");
/*  30:    */     }
/*  31: 87 */     class_name = class_name.replace('/', '.');
/*  32:    */     
/*  33: 89 */     JavaClass clazz = (JavaClass)classes.get(class_name);
/*  34: 91 */     if (clazz == null)
/*  35:    */     {
/*  36:    */       try
/*  37:    */       {
/*  38: 93 */         InputStream is = class_path.getInputStream(class_name);
/*  39: 94 */         clazz = new ClassParser(is, class_name).parse();
/*  40: 95 */         class_name = clazz.getClassName();
/*  41:    */       }
/*  42:    */       catch (IOException e)
/*  43:    */       {
/*  44: 96 */         return null;
/*  45:    */       }
/*  46: 98 */       classes.put(class_name, clazz);
/*  47:    */     }
/*  48:101 */     return clazz;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static ClassPath.ClassFile lookupClassFile(String class_name)
/*  52:    */   {
/*  53:    */     try
/*  54:    */     {
/*  55:108 */       return class_path.getClassFile(class_name);
/*  56:    */     }
/*  57:    */     catch (IOException e) {}
/*  58:109 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static void clearCache()
/*  62:    */   {
/*  63:115 */     classes = new HashMap();
/*  64:116 */     OBJECT = lookupClass("java.lang.Object");
/*  65:118 */     if (OBJECT == null) {
/*  66:119 */       System.err.println("Warning: java.lang.Object not found on CLASSPATH!");
/*  67:    */     } else {
/*  68:121 */       classes.put("java.lang.Object", OBJECT);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static JavaClass addClass(JavaClass clazz)
/*  73:    */   {
/*  74:130 */     String name = clazz.getClassName();
/*  75:131 */     JavaClass cl = (JavaClass)classes.get(name);
/*  76:133 */     if (cl == null)
/*  77:    */     {
/*  78:134 */       cl = clazz;classes.put(name, clazz);
/*  79:    */     }
/*  80:136 */     return cl;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static void removeClass(String clazz)
/*  84:    */   {
/*  85:143 */     classes.remove(clazz);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static void removeClass(JavaClass clazz)
/*  89:    */   {
/*  90:150 */     removeClass(clazz.getClassName());
/*  91:    */   }
/*  92:    */   
/*  93:    */   private static final JavaClass getSuperClass(JavaClass clazz)
/*  94:    */   {
/*  95:155 */     if (clazz == OBJECT) {
/*  96:156 */       return null;
/*  97:    */     }
/*  98:158 */     return lookupClass(clazz.getSuperclassName());
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static JavaClass[] getSuperClasses(JavaClass clazz)
/* 102:    */   {
/* 103:166 */     ClassVector vec = new ClassVector();
/* 104:168 */     for (clazz = getSuperClass(clazz); clazz != null; clazz = getSuperClass(clazz)) {
/* 105:169 */       vec.addElement(clazz);
/* 106:    */     }
/* 107:171 */     return vec.toArray();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static JavaClass[] getSuperClasses(String class_name)
/* 111:    */   {
/* 112:179 */     JavaClass jc = lookupClass(class_name);
/* 113:180 */     return jc == null ? null : getSuperClasses(jc);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static JavaClass[] getInterfaces(JavaClass clazz)
/* 117:    */   {
/* 118:188 */     ClassVector vec = new ClassVector();
/* 119:189 */     ClassQueue queue = new ClassQueue();
/* 120:    */     
/* 121:191 */     queue.enqueue(clazz);
/* 122:    */     String[] interfaces;
/* 123:    */     int i;
/* 124:193 */     for (; !queue.empty(); i < interfaces.length)
/* 125:    */     {
/* 126:194 */       clazz = queue.dequeue();
/* 127:    */       
/* 128:196 */       String s = clazz.getSuperclassName();
/* 129:197 */       interfaces = clazz.getInterfaceNames();
/* 130:199 */       if (clazz.isInterface()) {
/* 131:200 */         vec.addElement(clazz);
/* 132:201 */       } else if (!s.equals("java.lang.Object")) {
/* 133:202 */         queue.enqueue(lookupClass(s));
/* 134:    */       }
/* 135:204 */       i = 0; continue;
/* 136:205 */       queue.enqueue(lookupClass(interfaces[i]));i++;
/* 137:    */     }
/* 138:208 */     return vec.toArray();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static JavaClass[] getInterfaces(String class_name)
/* 142:    */   {
/* 143:216 */     return getInterfaces(lookupClass(class_name));
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static boolean instanceOf(JavaClass clazz, JavaClass super_class)
/* 147:    */   {
/* 148:223 */     if (clazz == super_class) {
/* 149:224 */       return true;
/* 150:    */     }
/* 151:226 */     JavaClass[] super_classes = getSuperClasses(clazz);
/* 152:228 */     for (int i = 0; i < super_classes.length; i++) {
/* 153:229 */       if (super_classes[i] == super_class) {
/* 154:230 */         return true;
/* 155:    */       }
/* 156:    */     }
/* 157:232 */     if (super_class.isInterface()) {
/* 158:233 */       return implementationOf(clazz, super_class);
/* 159:    */     }
/* 160:235 */     return false;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static boolean instanceOf(String clazz, String super_class)
/* 164:    */   {
/* 165:242 */     return instanceOf(lookupClass(clazz), lookupClass(super_class));
/* 166:    */   }
/* 167:    */   
/* 168:    */   public static boolean instanceOf(JavaClass clazz, String super_class)
/* 169:    */   {
/* 170:249 */     return instanceOf(clazz, lookupClass(super_class));
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static boolean instanceOf(String clazz, JavaClass super_class)
/* 174:    */   {
/* 175:256 */     return instanceOf(lookupClass(clazz), super_class);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public static boolean implementationOf(JavaClass clazz, JavaClass inter)
/* 179:    */   {
/* 180:263 */     if (clazz == inter) {
/* 181:264 */       return true;
/* 182:    */     }
/* 183:266 */     JavaClass[] super_interfaces = getInterfaces(clazz);
/* 184:268 */     for (int i = 0; i < super_interfaces.length; i++) {
/* 185:269 */       if (super_interfaces[i] == inter) {
/* 186:270 */         return true;
/* 187:    */       }
/* 188:    */     }
/* 189:272 */     return false;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static boolean implementationOf(String clazz, String inter)
/* 193:    */   {
/* 194:279 */     return implementationOf(lookupClass(clazz), lookupClass(inter));
/* 195:    */   }
/* 196:    */   
/* 197:    */   public static boolean implementationOf(JavaClass clazz, String inter)
/* 198:    */   {
/* 199:286 */     return implementationOf(clazz, lookupClass(inter));
/* 200:    */   }
/* 201:    */   
/* 202:    */   public static boolean implementationOf(String clazz, JavaClass inter)
/* 203:    */   {
/* 204:293 */     return implementationOf(lookupClass(clazz), inter);
/* 205:    */   }
/* 206:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.Repository
 * JD-Core Version:    0.7.0.1
 */