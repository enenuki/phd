/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.security.MessageDigest;
/*   7:    */ import java.security.NoSuchAlgorithmException;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import java.util.Comparator;
/*  10:    */ import javassist.bytecode.ClassFile;
/*  11:    */ import javassist.bytecode.Descriptor;
/*  12:    */ import javassist.bytecode.FieldInfo;
/*  13:    */ import javassist.bytecode.MethodInfo;
/*  14:    */ 
/*  15:    */ public class SerialVersionUID
/*  16:    */ {
/*  17:    */   public static void setSerialVersionUID(CtClass clazz)
/*  18:    */     throws CannotCompileException, NotFoundException
/*  19:    */   {
/*  20:    */     try
/*  21:    */     {
/*  22: 42 */       clazz.getDeclaredField("serialVersionUID");
/*  23: 43 */       return;
/*  24:    */     }
/*  25:    */     catch (NotFoundException e)
/*  26:    */     {
/*  27: 48 */       if (!isSerializable(clazz)) {
/*  28: 49 */         return;
/*  29:    */       }
/*  30: 52 */       CtField field = new CtField(CtClass.longType, "serialVersionUID", clazz);
/*  31:    */       
/*  32: 54 */       field.setModifiers(26);
/*  33:    */       
/*  34: 56 */       clazz.addField(field, calculateDefault(clazz) + "L");
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   private static boolean isSerializable(CtClass clazz)
/*  39:    */     throws NotFoundException
/*  40:    */   {
/*  41: 65 */     ClassPool pool = clazz.getClassPool();
/*  42: 66 */     return clazz.subtypeOf(pool.get("java.io.Serializable"));
/*  43:    */   }
/*  44:    */   
/*  45:    */   static long calculateDefault(CtClass clazz)
/*  46:    */     throws CannotCompileException
/*  47:    */   {
/*  48:    */     try
/*  49:    */     {
/*  50: 77 */       ByteArrayOutputStream bout = new ByteArrayOutputStream();
/*  51: 78 */       DataOutputStream out = new DataOutputStream(bout);
/*  52: 79 */       ClassFile classFile = clazz.getClassFile();
/*  53:    */       
/*  54:    */ 
/*  55: 82 */       String javaName = javaName(clazz);
/*  56: 83 */       out.writeUTF(javaName);
/*  57:    */       
/*  58: 85 */       CtMethod[] methods = clazz.getDeclaredMethods();
/*  59:    */       
/*  60:    */ 
/*  61: 88 */       int classMods = clazz.getModifiers();
/*  62: 89 */       if ((classMods & 0x200) != 0) {
/*  63: 90 */         if (methods.length > 0) {
/*  64: 91 */           classMods |= 0x400;
/*  65:    */         } else {
/*  66: 93 */           classMods &= 0xFFFFFBFF;
/*  67:    */         }
/*  68:    */       }
/*  69: 95 */       out.writeInt(classMods);
/*  70:    */       
/*  71:    */ 
/*  72: 98 */       String[] interfaces = classFile.getInterfaces();
/*  73: 99 */       for (int i = 0; i < interfaces.length; i++) {
/*  74:100 */         interfaces[i] = javaName(interfaces[i]);
/*  75:    */       }
/*  76:102 */       Arrays.sort(interfaces);
/*  77:103 */       for (int i = 0; i < interfaces.length; i++) {
/*  78:104 */         out.writeUTF(interfaces[i]);
/*  79:    */       }
/*  80:107 */       CtField[] fields = clazz.getDeclaredFields();
/*  81:108 */       Arrays.sort(fields, new Comparator()
/*  82:    */       {
/*  83:    */         public int compare(Object o1, Object o2)
/*  84:    */         {
/*  85:110 */           CtField field1 = (CtField)o1;
/*  86:111 */           CtField field2 = (CtField)o2;
/*  87:112 */           return field1.getName().compareTo(field2.getName());
/*  88:    */         }
/*  89:    */       });
/*  90:116 */       for (int i = 0; i < fields.length; i++)
/*  91:    */       {
/*  92:117 */         CtField field = fields[i];
/*  93:118 */         int mods = field.getModifiers();
/*  94:119 */         if (((mods & 0x2) == 0) || ((mods & 0x88) == 0))
/*  95:    */         {
/*  96:121 */           out.writeUTF(field.getName());
/*  97:122 */           out.writeInt(mods);
/*  98:123 */           out.writeUTF(field.getFieldInfo2().getDescriptor());
/*  99:    */         }
/* 100:    */       }
/* 101:128 */       if (classFile.getStaticInitializer() != null)
/* 102:    */       {
/* 103:129 */         out.writeUTF("<clinit>");
/* 104:130 */         out.writeInt(8);
/* 105:131 */         out.writeUTF("()V");
/* 106:    */       }
/* 107:135 */       CtConstructor[] constructors = clazz.getDeclaredConstructors();
/* 108:136 */       Arrays.sort(constructors, new Comparator()
/* 109:    */       {
/* 110:    */         public int compare(Object o1, Object o2)
/* 111:    */         {
/* 112:138 */           CtConstructor c1 = (CtConstructor)o1;
/* 113:139 */           CtConstructor c2 = (CtConstructor)o2;
/* 114:140 */           return c1.getMethodInfo2().getDescriptor().compareTo(c2.getMethodInfo2().getDescriptor());
/* 115:    */         }
/* 116:    */       });
/* 117:145 */       for (int i = 0; i < constructors.length; i++)
/* 118:    */       {
/* 119:146 */         CtConstructor constructor = constructors[i];
/* 120:147 */         int mods = constructor.getModifiers();
/* 121:148 */         if ((mods & 0x2) == 0)
/* 122:    */         {
/* 123:149 */           out.writeUTF("<init>");
/* 124:150 */           out.writeInt(mods);
/* 125:151 */           out.writeUTF(constructor.getMethodInfo2().getDescriptor().replace('/', '.'));
/* 126:    */         }
/* 127:    */       }
/* 128:157 */       Arrays.sort(methods, new Comparator()
/* 129:    */       {
/* 130:    */         public int compare(Object o1, Object o2)
/* 131:    */         {
/* 132:159 */           CtMethod m1 = (CtMethod)o1;
/* 133:160 */           CtMethod m2 = (CtMethod)o2;
/* 134:161 */           int value = m1.getName().compareTo(m2.getName());
/* 135:162 */           if (value == 0) {
/* 136:163 */             value = m1.getMethodInfo2().getDescriptor().compareTo(m2.getMethodInfo2().getDescriptor());
/* 137:    */           }
/* 138:166 */           return value;
/* 139:    */         }
/* 140:    */       });
/* 141:170 */       for (int i = 0; i < methods.length; i++)
/* 142:    */       {
/* 143:171 */         CtMethod method = methods[i];
/* 144:172 */         int mods = method.getModifiers() & 0xD3F;
/* 145:177 */         if ((mods & 0x2) == 0)
/* 146:    */         {
/* 147:178 */           out.writeUTF(method.getName());
/* 148:179 */           out.writeInt(mods);
/* 149:180 */           out.writeUTF(method.getMethodInfo2().getDescriptor().replace('/', '.'));
/* 150:    */         }
/* 151:    */       }
/* 152:186 */       out.flush();
/* 153:187 */       MessageDigest digest = MessageDigest.getInstance("SHA");
/* 154:188 */       byte[] digested = digest.digest(bout.toByteArray());
/* 155:189 */       long hash = 0L;
/* 156:190 */       for (int i = Math.min(digested.length, 8) - 1; i >= 0; i--) {
/* 157:191 */         hash = hash << 8 | digested[i] & 0xFF;
/* 158:    */       }
/* 159:193 */       return hash;
/* 160:    */     }
/* 161:    */     catch (IOException e)
/* 162:    */     {
/* 163:196 */       throw new CannotCompileException(e);
/* 164:    */     }
/* 165:    */     catch (NoSuchAlgorithmException e)
/* 166:    */     {
/* 167:199 */       throw new CannotCompileException(e);
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   private static String javaName(CtClass clazz)
/* 172:    */   {
/* 173:204 */     return Descriptor.toJavaName(Descriptor.toJvmName(clazz));
/* 174:    */   }
/* 175:    */   
/* 176:    */   private static String javaName(String name)
/* 177:    */   {
/* 178:208 */     return Descriptor.toJavaName(Descriptor.toJvmName(name));
/* 179:    */   }
/* 180:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.SerialVersionUID
 * JD-Core Version:    0.7.0.1
 */