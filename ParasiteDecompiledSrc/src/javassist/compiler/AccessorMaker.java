/*   1:    */ package javassist.compiler;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import javassist.CannotCompileException;
/*   5:    */ import javassist.ClassPool;
/*   6:    */ import javassist.CtClass;
/*   7:    */ import javassist.NotFoundException;
/*   8:    */ import javassist.bytecode.Bytecode;
/*   9:    */ import javassist.bytecode.ClassFile;
/*  10:    */ import javassist.bytecode.ConstPool;
/*  11:    */ import javassist.bytecode.Descriptor;
/*  12:    */ import javassist.bytecode.ExceptionsAttribute;
/*  13:    */ import javassist.bytecode.FieldInfo;
/*  14:    */ import javassist.bytecode.MethodInfo;
/*  15:    */ import javassist.bytecode.SyntheticAttribute;
/*  16:    */ 
/*  17:    */ public class AccessorMaker
/*  18:    */ {
/*  19:    */   private CtClass clazz;
/*  20:    */   private int uniqueNumber;
/*  21:    */   private HashMap accessors;
/*  22:    */   static final String lastParamType = "javassist.runtime.Inner";
/*  23:    */   
/*  24:    */   public AccessorMaker(CtClass c)
/*  25:    */   {
/*  26: 34 */     this.clazz = c;
/*  27: 35 */     this.uniqueNumber = 1;
/*  28: 36 */     this.accessors = new HashMap();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getConstructor(CtClass c, String desc, MethodInfo orig)
/*  32:    */     throws CompileError
/*  33:    */   {
/*  34: 42 */     String key = "<init>:" + desc;
/*  35: 43 */     String consDesc = (String)this.accessors.get(key);
/*  36: 44 */     if (consDesc != null) {
/*  37: 45 */       return consDesc;
/*  38:    */     }
/*  39: 47 */     consDesc = Descriptor.appendParameter("javassist.runtime.Inner", desc);
/*  40: 48 */     ClassFile cf = this.clazz.getClassFile();
/*  41:    */     try
/*  42:    */     {
/*  43: 50 */       ConstPool cp = cf.getConstPool();
/*  44: 51 */       ClassPool pool = this.clazz.getClassPool();
/*  45: 52 */       MethodInfo minfo = new MethodInfo(cp, "<init>", consDesc);
/*  46:    */       
/*  47: 54 */       minfo.setAccessFlags(0);
/*  48: 55 */       minfo.addAttribute(new SyntheticAttribute(cp));
/*  49: 56 */       ExceptionsAttribute ea = orig.getExceptionsAttribute();
/*  50: 57 */       if (ea != null) {
/*  51: 58 */         minfo.addAttribute(ea.copy(cp, null));
/*  52:    */       }
/*  53: 60 */       CtClass[] params = Descriptor.getParameterTypes(desc, pool);
/*  54: 61 */       Bytecode code = new Bytecode(cp);
/*  55: 62 */       code.addAload(0);
/*  56: 63 */       int regno = 1;
/*  57: 64 */       for (int i = 0; i < params.length; i++) {
/*  58: 65 */         regno += code.addLoad(regno, params[i]);
/*  59:    */       }
/*  60: 66 */       code.setMaxLocals(regno + 1);
/*  61: 67 */       code.addInvokespecial(this.clazz, "<init>", desc);
/*  62:    */       
/*  63: 69 */       code.addReturn(null);
/*  64: 70 */       minfo.setCodeAttribute(code.toCodeAttribute());
/*  65: 71 */       cf.addMethod(minfo);
/*  66:    */     }
/*  67:    */     catch (CannotCompileException e)
/*  68:    */     {
/*  69: 74 */       throw new CompileError(e);
/*  70:    */     }
/*  71:    */     catch (NotFoundException e)
/*  72:    */     {
/*  73: 77 */       throw new CompileError(e);
/*  74:    */     }
/*  75: 80 */     this.accessors.put(key, consDesc);
/*  76: 81 */     return consDesc;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getMethodAccessor(String name, String desc, String accDesc, MethodInfo orig)
/*  80:    */     throws CompileError
/*  81:    */   {
/*  82:101 */     String key = name + ":" + desc;
/*  83:102 */     String accName = (String)this.accessors.get(key);
/*  84:103 */     if (accName != null) {
/*  85:104 */       return accName;
/*  86:    */     }
/*  87:106 */     ClassFile cf = this.clazz.getClassFile();
/*  88:107 */     accName = findAccessorName(cf);
/*  89:    */     try
/*  90:    */     {
/*  91:109 */       ConstPool cp = cf.getConstPool();
/*  92:110 */       ClassPool pool = this.clazz.getClassPool();
/*  93:111 */       MethodInfo minfo = new MethodInfo(cp, accName, accDesc);
/*  94:    */       
/*  95:113 */       minfo.setAccessFlags(8);
/*  96:114 */       minfo.addAttribute(new SyntheticAttribute(cp));
/*  97:115 */       ExceptionsAttribute ea = orig.getExceptionsAttribute();
/*  98:116 */       if (ea != null) {
/*  99:117 */         minfo.addAttribute(ea.copy(cp, null));
/* 100:    */       }
/* 101:119 */       CtClass[] params = Descriptor.getParameterTypes(accDesc, pool);
/* 102:120 */       int regno = 0;
/* 103:121 */       Bytecode code = new Bytecode(cp);
/* 104:122 */       for (int i = 0; i < params.length; i++) {
/* 105:123 */         regno += code.addLoad(regno, params[i]);
/* 106:    */       }
/* 107:125 */       code.setMaxLocals(regno);
/* 108:126 */       if (desc == accDesc) {
/* 109:127 */         code.addInvokestatic(this.clazz, name, desc);
/* 110:    */       } else {
/* 111:129 */         code.addInvokevirtual(this.clazz, name, desc);
/* 112:    */       }
/* 113:131 */       code.addReturn(Descriptor.getReturnType(desc, pool));
/* 114:132 */       minfo.setCodeAttribute(code.toCodeAttribute());
/* 115:133 */       cf.addMethod(minfo);
/* 116:    */     }
/* 117:    */     catch (CannotCompileException e)
/* 118:    */     {
/* 119:136 */       throw new CompileError(e);
/* 120:    */     }
/* 121:    */     catch (NotFoundException e)
/* 122:    */     {
/* 123:139 */       throw new CompileError(e);
/* 124:    */     }
/* 125:142 */     this.accessors.put(key, accName);
/* 126:143 */     return accName;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public MethodInfo getFieldGetter(FieldInfo finfo, boolean is_static)
/* 130:    */     throws CompileError
/* 131:    */   {
/* 132:152 */     String fieldName = finfo.getName();
/* 133:153 */     String key = fieldName + ":getter";
/* 134:154 */     Object res = this.accessors.get(key);
/* 135:155 */     if (res != null) {
/* 136:156 */       return (MethodInfo)res;
/* 137:    */     }
/* 138:158 */     ClassFile cf = this.clazz.getClassFile();
/* 139:159 */     String accName = findAccessorName(cf);
/* 140:    */     try
/* 141:    */     {
/* 142:161 */       ConstPool cp = cf.getConstPool();
/* 143:162 */       ClassPool pool = this.clazz.getClassPool();
/* 144:163 */       String fieldType = finfo.getDescriptor();
/* 145:    */       String accDesc;
/* 146:    */       String accDesc;
/* 147:165 */       if (is_static) {
/* 148:166 */         accDesc = "()" + fieldType;
/* 149:    */       } else {
/* 150:168 */         accDesc = "(" + Descriptor.of(this.clazz) + ")" + fieldType;
/* 151:    */       }
/* 152:170 */       MethodInfo minfo = new MethodInfo(cp, accName, accDesc);
/* 153:171 */       minfo.setAccessFlags(8);
/* 154:172 */       minfo.addAttribute(new SyntheticAttribute(cp));
/* 155:173 */       Bytecode code = new Bytecode(cp);
/* 156:174 */       if (is_static)
/* 157:    */       {
/* 158:175 */         code.addGetstatic(Bytecode.THIS, fieldName, fieldType);
/* 159:    */       }
/* 160:    */       else
/* 161:    */       {
/* 162:178 */         code.addAload(0);
/* 163:179 */         code.addGetfield(Bytecode.THIS, fieldName, fieldType);
/* 164:180 */         code.setMaxLocals(1);
/* 165:    */       }
/* 166:183 */       code.addReturn(Descriptor.toCtClass(fieldType, pool));
/* 167:184 */       minfo.setCodeAttribute(code.toCodeAttribute());
/* 168:185 */       cf.addMethod(minfo);
/* 169:186 */       this.accessors.put(key, minfo);
/* 170:187 */       return minfo;
/* 171:    */     }
/* 172:    */     catch (CannotCompileException e)
/* 173:    */     {
/* 174:190 */       throw new CompileError(e);
/* 175:    */     }
/* 176:    */     catch (NotFoundException e)
/* 177:    */     {
/* 178:193 */       throw new CompileError(e);
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public MethodInfo getFieldSetter(FieldInfo finfo, boolean is_static)
/* 183:    */     throws CompileError
/* 184:    */   {
/* 185:203 */     String fieldName = finfo.getName();
/* 186:204 */     String key = fieldName + ":setter";
/* 187:205 */     Object res = this.accessors.get(key);
/* 188:206 */     if (res != null) {
/* 189:207 */       return (MethodInfo)res;
/* 190:    */     }
/* 191:209 */     ClassFile cf = this.clazz.getClassFile();
/* 192:210 */     String accName = findAccessorName(cf);
/* 193:    */     try
/* 194:    */     {
/* 195:212 */       ConstPool cp = cf.getConstPool();
/* 196:213 */       ClassPool pool = this.clazz.getClassPool();
/* 197:214 */       String fieldType = finfo.getDescriptor();
/* 198:    */       String accDesc;
/* 199:    */       String accDesc;
/* 200:216 */       if (is_static) {
/* 201:217 */         accDesc = "(" + fieldType + ")V";
/* 202:    */       } else {
/* 203:219 */         accDesc = "(" + Descriptor.of(this.clazz) + fieldType + ")V";
/* 204:    */       }
/* 205:221 */       MethodInfo minfo = new MethodInfo(cp, accName, accDesc);
/* 206:222 */       minfo.setAccessFlags(8);
/* 207:223 */       minfo.addAttribute(new SyntheticAttribute(cp));
/* 208:224 */       Bytecode code = new Bytecode(cp);
/* 209:    */       int reg;
/* 210:226 */       if (is_static)
/* 211:    */       {
/* 212:227 */         int reg = code.addLoad(0, Descriptor.toCtClass(fieldType, pool));
/* 213:228 */         code.addPutstatic(Bytecode.THIS, fieldName, fieldType);
/* 214:    */       }
/* 215:    */       else
/* 216:    */       {
/* 217:231 */         code.addAload(0);
/* 218:232 */         reg = code.addLoad(1, Descriptor.toCtClass(fieldType, pool)) + 1;
/* 219:    */         
/* 220:234 */         code.addPutfield(Bytecode.THIS, fieldName, fieldType);
/* 221:    */       }
/* 222:237 */       code.addReturn(null);
/* 223:238 */       code.setMaxLocals(reg);
/* 224:239 */       minfo.setCodeAttribute(code.toCodeAttribute());
/* 225:240 */       cf.addMethod(minfo);
/* 226:241 */       this.accessors.put(key, minfo);
/* 227:242 */       return minfo;
/* 228:    */     }
/* 229:    */     catch (CannotCompileException e)
/* 230:    */     {
/* 231:245 */       throw new CompileError(e);
/* 232:    */     }
/* 233:    */     catch (NotFoundException e)
/* 234:    */     {
/* 235:248 */       throw new CompileError(e);
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   private String findAccessorName(ClassFile cf)
/* 240:    */   {
/* 241:    */     String accName;
/* 242:    */     do
/* 243:    */     {
/* 244:255 */       accName = "access$" + this.uniqueNumber++;
/* 245:256 */     } while (cf.getMethod(accName) != null);
/* 246:257 */     return accName;
/* 247:    */   }
/* 248:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.AccessorMaker
 * JD-Core Version:    0.7.0.1
 */