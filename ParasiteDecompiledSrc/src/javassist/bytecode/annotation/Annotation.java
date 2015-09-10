/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.LinkedHashMap;
/*   7:    */ import java.util.Set;
/*   8:    */ import javassist.ClassPool;
/*   9:    */ import javassist.CtClass;
/*  10:    */ import javassist.CtMethod;
/*  11:    */ import javassist.NotFoundException;
/*  12:    */ import javassist.bytecode.ConstPool;
/*  13:    */ import javassist.bytecode.Descriptor;
/*  14:    */ 
/*  15:    */ public class Annotation
/*  16:    */ {
/*  17:    */   ConstPool pool;
/*  18:    */   int typeIndex;
/*  19:    */   LinkedHashMap members;
/*  20:    */   
/*  21:    */   public Annotation(int type, ConstPool cp)
/*  22:    */   {
/*  23: 71 */     this.pool = cp;
/*  24: 72 */     this.typeIndex = type;
/*  25: 73 */     this.members = null;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Annotation(String typeName, ConstPool cp)
/*  29:    */   {
/*  30: 86 */     this(cp.addUtf8Info(Descriptor.of(typeName)), cp);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Annotation(ConstPool cp, CtClass clazz)
/*  34:    */     throws NotFoundException
/*  35:    */   {
/*  36:102 */     this(cp.addUtf8Info(Descriptor.of(clazz.getName())), cp);
/*  37:104 */     if (!clazz.isInterface()) {
/*  38:105 */       throw new RuntimeException("Only interfaces are allowed for Annotation creation.");
/*  39:    */     }
/*  40:108 */     CtMethod[] methods = clazz.getDeclaredMethods();
/*  41:109 */     if (methods.length > 0) {
/*  42:110 */       this.members = new LinkedHashMap();
/*  43:    */     }
/*  44:113 */     for (int i = 0; i < methods.length; i++)
/*  45:    */     {
/*  46:114 */       CtClass returnType = methods[i].getReturnType();
/*  47:115 */       addMemberValue(methods[i].getName(), createMemberValue(cp, returnType));
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static MemberValue createMemberValue(ConstPool cp, CtClass type)
/*  52:    */     throws NotFoundException
/*  53:    */   {
/*  54:132 */     if (type == CtClass.booleanType) {
/*  55:133 */       return new BooleanMemberValue(cp);
/*  56:    */     }
/*  57:134 */     if (type == CtClass.byteType) {
/*  58:135 */       return new ByteMemberValue(cp);
/*  59:    */     }
/*  60:136 */     if (type == CtClass.charType) {
/*  61:137 */       return new CharMemberValue(cp);
/*  62:    */     }
/*  63:138 */     if (type == CtClass.shortType) {
/*  64:139 */       return new ShortMemberValue(cp);
/*  65:    */     }
/*  66:140 */     if (type == CtClass.intType) {
/*  67:141 */       return new IntegerMemberValue(cp);
/*  68:    */     }
/*  69:142 */     if (type == CtClass.longType) {
/*  70:143 */       return new LongMemberValue(cp);
/*  71:    */     }
/*  72:144 */     if (type == CtClass.floatType) {
/*  73:145 */       return new FloatMemberValue(cp);
/*  74:    */     }
/*  75:146 */     if (type == CtClass.doubleType) {
/*  76:147 */       return new DoubleMemberValue(cp);
/*  77:    */     }
/*  78:148 */     if (type.getName().equals("java.lang.Class")) {
/*  79:149 */       return new ClassMemberValue(cp);
/*  80:    */     }
/*  81:150 */     if (type.getName().equals("java.lang.String")) {
/*  82:151 */       return new StringMemberValue(cp);
/*  83:    */     }
/*  84:152 */     if (type.isArray())
/*  85:    */     {
/*  86:153 */       CtClass arrayType = type.getComponentType();
/*  87:154 */       MemberValue member = createMemberValue(cp, arrayType);
/*  88:155 */       return new ArrayMemberValue(member, cp);
/*  89:    */     }
/*  90:157 */     if (type.isInterface())
/*  91:    */     {
/*  92:158 */       Annotation info = new Annotation(cp, type);
/*  93:159 */       return new AnnotationMemberValue(info, cp);
/*  94:    */     }
/*  95:165 */     EnumMemberValue emv = new EnumMemberValue(cp);
/*  96:166 */     emv.setType(type.getName());
/*  97:167 */     return emv;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void addMemberValue(int nameIndex, MemberValue value)
/* 101:    */   {
/* 102:181 */     Pair p = new Pair();
/* 103:182 */     p.name = nameIndex;
/* 104:183 */     p.value = value;
/* 105:184 */     addMemberValue(p);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void addMemberValue(String name, MemberValue value)
/* 109:    */   {
/* 110:194 */     Pair p = new Pair();
/* 111:195 */     p.name = this.pool.addUtf8Info(name);
/* 112:196 */     p.value = value;
/* 113:197 */     if (this.members == null) {
/* 114:198 */       this.members = new LinkedHashMap();
/* 115:    */     }
/* 116:200 */     this.members.put(name, p);
/* 117:    */   }
/* 118:    */   
/* 119:    */   private void addMemberValue(Pair pair)
/* 120:    */   {
/* 121:204 */     String name = this.pool.getUtf8Info(pair.name);
/* 122:205 */     if (this.members == null) {
/* 123:206 */       this.members = new LinkedHashMap();
/* 124:    */     }
/* 125:208 */     this.members.put(name, pair);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String toString()
/* 129:    */   {
/* 130:215 */     StringBuffer buf = new StringBuffer("@");
/* 131:216 */     buf.append(getTypeName());
/* 132:217 */     if (this.members != null)
/* 133:    */     {
/* 134:218 */       buf.append("(");
/* 135:219 */       Iterator mit = this.members.keySet().iterator();
/* 136:220 */       while (mit.hasNext())
/* 137:    */       {
/* 138:221 */         String name = (String)mit.next();
/* 139:222 */         buf.append(name).append("=").append(getMemberValue(name));
/* 140:223 */         if (mit.hasNext()) {
/* 141:224 */           buf.append(", ");
/* 142:    */         }
/* 143:    */       }
/* 144:226 */       buf.append(")");
/* 145:    */     }
/* 146:229 */     return buf.toString();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String getTypeName()
/* 150:    */   {
/* 151:238 */     return Descriptor.toClassName(this.pool.getUtf8Info(this.typeIndex));
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Set getMemberNames()
/* 155:    */   {
/* 156:247 */     if (this.members == null) {
/* 157:248 */       return null;
/* 158:    */     }
/* 159:250 */     return this.members.keySet();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public MemberValue getMemberValue(String name)
/* 163:    */   {
/* 164:269 */     if (this.members == null) {
/* 165:270 */       return null;
/* 166:    */     }
/* 167:272 */     Pair p = (Pair)this.members.get(name);
/* 168:273 */     if (p == null) {
/* 169:274 */       return null;
/* 170:    */     }
/* 171:276 */     return p.value;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Object toAnnotationType(ClassLoader cl, ClassPool cp)
/* 175:    */     throws ClassNotFoundException, NoSuchClassError
/* 176:    */   {
/* 177:294 */     return AnnotationImpl.make(cl, MemberValue.loadClass(cl, getTypeName()), cp, this);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void write(AnnotationsWriter writer)
/* 181:    */     throws IOException
/* 182:    */   {
/* 183:306 */     String typeName = this.pool.getUtf8Info(this.typeIndex);
/* 184:307 */     if (this.members == null)
/* 185:    */     {
/* 186:308 */       writer.annotation(typeName, 0);
/* 187:309 */       return;
/* 188:    */     }
/* 189:312 */     writer.annotation(typeName, this.members.size());
/* 190:313 */     Iterator it = this.members.values().iterator();
/* 191:314 */     while (it.hasNext())
/* 192:    */     {
/* 193:315 */       Pair pair = (Pair)it.next();
/* 194:316 */       writer.memberValuePair(pair.name);
/* 195:317 */       pair.value.write(writer);
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean equals(Object obj)
/* 200:    */   {
/* 201:326 */     if (obj == this) {
/* 202:327 */       return true;
/* 203:    */     }
/* 204:328 */     if ((obj == null) || (!(obj instanceof Annotation))) {
/* 205:329 */       return false;
/* 206:    */     }
/* 207:331 */     Annotation other = (Annotation)obj;
/* 208:333 */     if (!getTypeName().equals(other.getTypeName())) {
/* 209:334 */       return false;
/* 210:    */     }
/* 211:336 */     LinkedHashMap otherMembers = other.members;
/* 212:337 */     if (this.members == otherMembers) {
/* 213:338 */       return true;
/* 214:    */     }
/* 215:339 */     if (this.members == null) {
/* 216:340 */       return otherMembers == null;
/* 217:    */     }
/* 218:342 */     if (otherMembers == null) {
/* 219:343 */       return false;
/* 220:    */     }
/* 221:345 */     return this.members.equals(otherMembers);
/* 222:    */   }
/* 223:    */   
/* 224:    */   static class Pair
/* 225:    */   {
/* 226:    */     int name;
/* 227:    */     MemberValue value;
/* 228:    */   }
/* 229:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.Annotation
 * JD-Core Version:    0.7.0.1
 */