/*   1:    */ package org.hibernate.annotations.common.reflection.java;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.AnnotatedElement;
/*   4:    */ import java.lang.reflect.Member;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.lang.reflect.ParameterizedType;
/*   7:    */ import java.lang.reflect.Type;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.hibernate.annotations.common.Version;
/*  11:    */ import org.hibernate.annotations.common.reflection.AnnotationReader;
/*  12:    */ import org.hibernate.annotations.common.reflection.MetadataProvider;
/*  13:    */ import org.hibernate.annotations.common.reflection.MetadataProviderInjector;
/*  14:    */ import org.hibernate.annotations.common.reflection.ReflectionManager;
/*  15:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  16:    */ import org.hibernate.annotations.common.reflection.XMethod;
/*  17:    */ import org.hibernate.annotations.common.reflection.XPackage;
/*  18:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  19:    */ import org.hibernate.annotations.common.reflection.java.generics.IdentityTypeEnvironment;
/*  20:    */ import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;
/*  21:    */ import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironmentFactory;
/*  22:    */ import org.hibernate.annotations.common.reflection.java.generics.TypeSwitch;
/*  23:    */ import org.hibernate.annotations.common.reflection.java.generics.TypeUtils;
/*  24:    */ import org.hibernate.annotations.common.util.ReflectHelper;
/*  25:    */ 
/*  26:    */ public class JavaReflectionManager
/*  27:    */   implements ReflectionManager, MetadataProviderInjector
/*  28:    */ {
/*  29:    */   private MetadataProvider metadataProvider;
/*  30:    */   
/*  31:    */   public MetadataProvider getMetadataProvider()
/*  32:    */   {
/*  33: 62 */     if (this.metadataProvider == null) {
/*  34: 63 */       setMetadataProvider(new JavaMetadataProvider());
/*  35:    */     }
/*  36: 65 */     return this.metadataProvider;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setMetadataProvider(MetadataProvider metadataProvider)
/*  40:    */   {
/*  41: 69 */     this.metadataProvider = metadataProvider;
/*  42:    */   }
/*  43:    */   
/*  44:    */   private static class TypeKey
/*  45:    */     extends Pair<Type, TypeEnvironment>
/*  46:    */   {
/*  47:    */     TypeKey(Type t, TypeEnvironment context)
/*  48:    */     {
/*  49: 78 */       super(context);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   private static class MemberKey
/*  54:    */     extends Pair<Member, TypeEnvironment>
/*  55:    */   {
/*  56:    */     MemberKey(Member member, TypeEnvironment context)
/*  57:    */     {
/*  58: 84 */       super(context);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62: 88 */   private final Map<TypeKey, JavaXClass> xClasses = new HashMap();
/*  63: 90 */   private final Map<Package, JavaXPackage> packagesToXPackages = new HashMap();
/*  64: 92 */   private final Map<MemberKey, JavaXProperty> xProperties = new HashMap();
/*  65: 94 */   private final Map<MemberKey, JavaXMethod> xMethods = new HashMap();
/*  66: 96 */   private final TypeEnvironmentFactory typeEnvs = new TypeEnvironmentFactory();
/*  67:    */   
/*  68:    */   public XClass toXClass(Class clazz)
/*  69:    */   {
/*  70: 99 */     return toXClass(clazz, IdentityTypeEnvironment.INSTANCE);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Class toClass(XClass xClazz)
/*  74:    */   {
/*  75:103 */     if (!(xClazz instanceof JavaXClass)) {
/*  76:104 */       throw new IllegalArgumentException("XClass not coming from this ReflectionManager implementation");
/*  77:    */     }
/*  78:106 */     return (Class)((JavaXClass)xClazz).toAnnotatedElement();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Method toMethod(XMethod xMethod)
/*  82:    */   {
/*  83:110 */     if (!(xMethod instanceof JavaXMethod)) {
/*  84:111 */       throw new IllegalArgumentException("XMethod not coming from this ReflectionManager implementation");
/*  85:    */     }
/*  86:113 */     return (Method)((JavaXAnnotatedElement)xMethod).toAnnotatedElement();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public XClass classForName(String name, Class caller)
/*  90:    */     throws ClassNotFoundException
/*  91:    */   {
/*  92:117 */     return toXClass(ReflectHelper.classForName(name, caller));
/*  93:    */   }
/*  94:    */   
/*  95:    */   public XPackage packageForName(String packageName)
/*  96:    */     throws ClassNotFoundException
/*  97:    */   {
/*  98:121 */     return getXAnnotatedElement(ReflectHelper.classForName(packageName + ".package-info").getPackage());
/*  99:    */   }
/* 100:    */   
/* 101:    */   XClass toXClass(Type t, final TypeEnvironment context)
/* 102:    */   {
/* 103:125 */     (XClass)new TypeSwitch()
/* 104:    */     {
/* 105:    */       public XClass caseClass(Class classType)
/* 106:    */       {
/* 107:128 */         JavaReflectionManager.TypeKey key = new JavaReflectionManager.TypeKey(classType, context);
/* 108:129 */         JavaXClass result = (JavaXClass)JavaReflectionManager.this.xClasses.get(key);
/* 109:130 */         if (result == null)
/* 110:    */         {
/* 111:131 */           result = new JavaXClass(classType, context, JavaReflectionManager.this);
/* 112:132 */           JavaReflectionManager.this.xClasses.put(key, result);
/* 113:    */         }
/* 114:134 */         return result;
/* 115:    */       }
/* 116:    */       
/* 117:    */       public XClass caseParameterizedType(ParameterizedType parameterizedType)
/* 118:    */       {
/* 119:139 */         return JavaReflectionManager.this.toXClass(parameterizedType.getRawType(), JavaReflectionManager.this.typeEnvs.getEnvironment(parameterizedType, context));
/* 120:    */       }
/* 121:139 */     }.doSwitch(context.bind(t));
/* 122:    */   }
/* 123:    */   
/* 124:    */   XPackage getXAnnotatedElement(Package pkg)
/* 125:    */   {
/* 126:147 */     JavaXPackage xPackage = (JavaXPackage)this.packagesToXPackages.get(pkg);
/* 127:148 */     if (xPackage == null)
/* 128:    */     {
/* 129:149 */       xPackage = new JavaXPackage(pkg, this);
/* 130:150 */       this.packagesToXPackages.put(pkg, xPackage);
/* 131:    */     }
/* 132:152 */     return xPackage;
/* 133:    */   }
/* 134:    */   
/* 135:    */   XProperty getXProperty(Member member, TypeEnvironment context)
/* 136:    */   {
/* 137:156 */     MemberKey key = new MemberKey(member, context);
/* 138:    */     
/* 139:158 */     JavaXProperty xProperty = (JavaXProperty)this.xProperties.get(key);
/* 140:159 */     if (xProperty == null)
/* 141:    */     {
/* 142:160 */       xProperty = JavaXProperty.create(member, context, this);
/* 143:161 */       this.xProperties.put(key, xProperty);
/* 144:    */     }
/* 145:163 */     return xProperty;
/* 146:    */   }
/* 147:    */   
/* 148:    */   XMethod getXMethod(Member member, TypeEnvironment context)
/* 149:    */   {
/* 150:167 */     MemberKey key = new MemberKey(member, context);
/* 151:    */     
/* 152:169 */     JavaXMethod xMethod = (JavaXMethod)this.xMethods.get(key);
/* 153:170 */     if (xMethod == null)
/* 154:    */     {
/* 155:171 */       xMethod = JavaXMethod.create(member, context, this);
/* 156:172 */       this.xMethods.put(key, xMethod);
/* 157:    */     }
/* 158:174 */     return xMethod;
/* 159:    */   }
/* 160:    */   
/* 161:    */   TypeEnvironment getTypeEnvironment(Type t)
/* 162:    */   {
/* 163:178 */     (TypeEnvironment)new TypeSwitch()
/* 164:    */     {
/* 165:    */       public TypeEnvironment caseClass(Class classType)
/* 166:    */       {
/* 167:181 */         return JavaReflectionManager.this.typeEnvs.getEnvironment(classType);
/* 168:    */       }
/* 169:    */       
/* 170:    */       public TypeEnvironment caseParameterizedType(ParameterizedType parameterizedType)
/* 171:    */       {
/* 172:186 */         return JavaReflectionManager.this.typeEnvs.getEnvironment(parameterizedType);
/* 173:    */       }
/* 174:    */       
/* 175:    */       public TypeEnvironment defaultCase(Type type)
/* 176:    */       {
/* 177:191 */         return IdentityTypeEnvironment.INSTANCE;
/* 178:    */       }
/* 179:191 */     }.doSwitch(t);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public JavaXType toXType(TypeEnvironment context, Type propType)
/* 183:    */   {
/* 184:197 */     Type boundType = toApproximatingEnvironment(context).bind(propType);
/* 185:198 */     if (TypeUtils.isArray(boundType)) {
/* 186:199 */       return new JavaXArrayType(propType, context, this);
/* 187:    */     }
/* 188:201 */     if (TypeUtils.isCollection(boundType)) {
/* 189:202 */       return new JavaXCollectionType(propType, context, this);
/* 190:    */     }
/* 191:204 */     if (TypeUtils.isSimple(boundType)) {
/* 192:205 */       return new JavaXSimpleType(propType, context, this);
/* 193:    */     }
/* 194:207 */     throw new IllegalArgumentException("No PropertyTypeExtractor available for type void ");
/* 195:    */   }
/* 196:    */   
/* 197:    */   public boolean equals(XClass class1, Class class2)
/* 198:    */   {
/* 199:211 */     if (class1 == null) {
/* 200:212 */       return class2 == null;
/* 201:    */     }
/* 202:214 */     return ((JavaXClass)class1).toClass().equals(class2);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public TypeEnvironment toApproximatingEnvironment(TypeEnvironment context)
/* 206:    */   {
/* 207:218 */     return this.typeEnvs.toApproximatingEnvironment(context);
/* 208:    */   }
/* 209:    */   
/* 210:    */   public AnnotationReader buildAnnotationReader(AnnotatedElement annotatedElement)
/* 211:    */   {
/* 212:222 */     return getMetadataProvider().getAnnotationReader(annotatedElement);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Map getDefaults()
/* 216:    */   {
/* 217:226 */     return getMetadataProvider().getDefaults();
/* 218:    */   }
/* 219:    */   
/* 220:    */   static {}
/* 221:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.JavaReflectionManager
 * JD-Core Version:    0.7.0.1
 */