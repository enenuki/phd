/*   1:    */ package org.hibernate.annotations.common.reflection.java;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.lang.reflect.Modifier;
/*   6:    */ import java.util.LinkedList;
/*   7:    */ import java.util.List;
/*   8:    */ import org.hibernate.annotations.common.reflection.Filter;
/*   9:    */ import org.hibernate.annotations.common.reflection.ReflectionUtil;
/*  10:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  11:    */ import org.hibernate.annotations.common.reflection.XMethod;
/*  12:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  13:    */ import org.hibernate.annotations.common.reflection.java.generics.CompoundTypeEnvironment;
/*  14:    */ import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;
/*  15:    */ 
/*  16:    */ class JavaXClass
/*  17:    */   extends JavaXAnnotatedElement
/*  18:    */   implements XClass
/*  19:    */ {
/*  20:    */   private final TypeEnvironment context;
/*  21:    */   private final Class clazz;
/*  22:    */   
/*  23:    */   public JavaXClass(Class clazz, TypeEnvironment env, JavaReflectionManager factory)
/*  24:    */   {
/*  25: 50 */     super(clazz, factory);
/*  26: 51 */     this.clazz = clazz;
/*  27: 52 */     this.context = env;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getName()
/*  31:    */   {
/*  32: 56 */     return toClass().getName();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public XClass getSuperclass()
/*  36:    */   {
/*  37: 60 */     return getFactory().toXClass(toClass().getSuperclass(), CompoundTypeEnvironment.create(getTypeEnvironment(), getFactory().getTypeEnvironment(toClass())));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public XClass[] getInterfaces()
/*  41:    */   {
/*  42: 69 */     Class[] classes = toClass().getInterfaces();
/*  43: 70 */     int length = classes.length;
/*  44: 71 */     XClass[] xClasses = new XClass[length];
/*  45: 72 */     if (length != 0)
/*  46:    */     {
/*  47: 73 */       TypeEnvironment environment = CompoundTypeEnvironment.create(getTypeEnvironment(), getFactory().getTypeEnvironment(toClass()));
/*  48: 77 */       for (int index = 0; index < length; index++) {
/*  49: 78 */         xClasses[index] = getFactory().toXClass(classes[index], environment);
/*  50:    */       }
/*  51:    */     }
/*  52: 81 */     return xClasses;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean isInterface()
/*  56:    */   {
/*  57: 85 */     return toClass().isInterface();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isAbstract()
/*  61:    */   {
/*  62: 89 */     return Modifier.isAbstract(toClass().getModifiers());
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isPrimitive()
/*  66:    */   {
/*  67: 93 */     return toClass().isPrimitive();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean isEnum()
/*  71:    */   {
/*  72: 97 */     return toClass().isEnum();
/*  73:    */   }
/*  74:    */   
/*  75:    */   private List<XProperty> getDeclaredFieldProperties(Filter filter)
/*  76:    */   {
/*  77:101 */     List<XProperty> result = new LinkedList();
/*  78:102 */     for (Field f : toClass().getDeclaredFields()) {
/*  79:103 */       if (ReflectionUtil.isProperty(f, getTypeEnvironment().bind(f.getGenericType()), filter)) {
/*  80:104 */         result.add(getFactory().getXProperty(f, getTypeEnvironment()));
/*  81:    */       }
/*  82:    */     }
/*  83:107 */     return result;
/*  84:    */   }
/*  85:    */   
/*  86:    */   private List<XProperty> getDeclaredMethodProperties(Filter filter)
/*  87:    */   {
/*  88:111 */     List<XProperty> result = new LinkedList();
/*  89:112 */     for (Method m : toClass().getDeclaredMethods()) {
/*  90:113 */       if (ReflectionUtil.isProperty(m, getTypeEnvironment().bind(m.getGenericReturnType()), filter)) {
/*  91:114 */         result.add(getFactory().getXProperty(m, getTypeEnvironment()));
/*  92:    */       }
/*  93:    */     }
/*  94:117 */     return result;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public List<XProperty> getDeclaredProperties(String accessType)
/*  98:    */   {
/*  99:121 */     return getDeclaredProperties(accessType, XClass.DEFAULT_FILTER);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public List<XProperty> getDeclaredProperties(String accessType, Filter filter)
/* 103:    */   {
/* 104:125 */     if (accessType.equals("field")) {
/* 105:126 */       return getDeclaredFieldProperties(filter);
/* 106:    */     }
/* 107:128 */     if (accessType.equals("property")) {
/* 108:129 */       return getDeclaredMethodProperties(filter);
/* 109:    */     }
/* 110:131 */     throw new IllegalArgumentException("Unknown access type " + accessType);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<XMethod> getDeclaredMethods()
/* 114:    */   {
/* 115:135 */     List<XMethod> result = new LinkedList();
/* 116:136 */     for (Method m : toClass().getDeclaredMethods()) {
/* 117:137 */       result.add(getFactory().getXMethod(m, getTypeEnvironment()));
/* 118:    */     }
/* 119:139 */     return result;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Class<?> toClass()
/* 123:    */   {
/* 124:143 */     return this.clazz;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isAssignableFrom(XClass c)
/* 128:    */   {
/* 129:147 */     return toClass().isAssignableFrom(((JavaXClass)c).toClass());
/* 130:    */   }
/* 131:    */   
/* 132:    */   boolean isArray()
/* 133:    */   {
/* 134:151 */     return toClass().isArray();
/* 135:    */   }
/* 136:    */   
/* 137:    */   TypeEnvironment getTypeEnvironment()
/* 138:    */   {
/* 139:155 */     return this.context;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String toString()
/* 143:    */   {
/* 144:160 */     return getName();
/* 145:    */   }
/* 146:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.JavaXClass
 * JD-Core Version:    0.7.0.1
 */