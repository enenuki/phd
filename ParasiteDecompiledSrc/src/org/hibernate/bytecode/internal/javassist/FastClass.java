/*   1:    */ package org.hibernate.bytecode.internal.javassist;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.lang.reflect.Modifier;
/*   8:    */ 
/*   9:    */ public class FastClass
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12: 37 */   private static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
/*  13:    */   private Class type;
/*  14:    */   
/*  15:    */   private FastClass() {}
/*  16:    */   
/*  17:    */   private FastClass(Class type)
/*  18:    */   {
/*  19: 45 */     this.type = type;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Object invoke(String name, Class[] parameterTypes, Object obj, Object[] args)
/*  23:    */     throws InvocationTargetException
/*  24:    */   {
/*  25: 53 */     return invoke(getIndex(name, parameterTypes), obj, args);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Object invoke(int index, Object obj, Object[] args)
/*  29:    */     throws InvocationTargetException
/*  30:    */   {
/*  31: 60 */     Method[] methods = this.type.getMethods();
/*  32:    */     try
/*  33:    */     {
/*  34: 62 */       return methods[index].invoke(obj, args);
/*  35:    */     }
/*  36:    */     catch (ArrayIndexOutOfBoundsException e)
/*  37:    */     {
/*  38: 65 */       throw new IllegalArgumentException("Cannot find matching method/constructor");
/*  39:    */     }
/*  40:    */     catch (IllegalAccessException e)
/*  41:    */     {
/*  42: 70 */       throw new InvocationTargetException(e);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Object newInstance()
/*  47:    */     throws InvocationTargetException
/*  48:    */   {
/*  49: 75 */     return newInstance(getIndex(EMPTY_CLASS_ARRAY), null);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Object newInstance(Class[] parameterTypes, Object[] args)
/*  53:    */     throws InvocationTargetException
/*  54:    */   {
/*  55: 81 */     return newInstance(getIndex(parameterTypes), args);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Object newInstance(int index, Object[] args)
/*  59:    */     throws InvocationTargetException
/*  60:    */   {
/*  61: 87 */     Constructor[] conss = this.type.getConstructors();
/*  62:    */     try
/*  63:    */     {
/*  64: 89 */       return conss[index].newInstance(args);
/*  65:    */     }
/*  66:    */     catch (ArrayIndexOutOfBoundsException e)
/*  67:    */     {
/*  68: 92 */       throw new IllegalArgumentException("Cannot find matching method/constructor");
/*  69:    */     }
/*  70:    */     catch (InstantiationException e)
/*  71:    */     {
/*  72: 95 */       throw new InvocationTargetException(e);
/*  73:    */     }
/*  74:    */     catch (IllegalAccessException e)
/*  75:    */     {
/*  76: 98 */       throw new InvocationTargetException(e);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIndex(String name, Class[] parameterTypes)
/*  81:    */   {
/*  82:103 */     Method[] methods = this.type.getMethods();
/*  83:104 */     boolean eq = true;
/*  84:105 */     for (int i = 0; i < methods.length; i++) {
/*  85:106 */       if (Modifier.isPublic(methods[i].getModifiers())) {
/*  86:109 */         if (methods[i].getName().equals(name))
/*  87:    */         {
/*  88:112 */           Class[] params = methods[i].getParameterTypes();
/*  89:113 */           if (params.length == parameterTypes.length)
/*  90:    */           {
/*  91:116 */             eq = true;
/*  92:117 */             for (int j = 0; j < params.length; j++) {
/*  93:118 */               if (!params[j].equals(parameterTypes[j]))
/*  94:    */               {
/*  95:119 */                 eq = false;
/*  96:120 */                 break;
/*  97:    */               }
/*  98:    */             }
/*  99:123 */             if (eq) {
/* 100:124 */               return i;
/* 101:    */             }
/* 102:    */           }
/* 103:    */         }
/* 104:    */       }
/* 105:    */     }
/* 106:127 */     return -1;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getIndex(Class[] parameterTypes)
/* 110:    */   {
/* 111:131 */     Constructor[] conss = this.type.getConstructors();
/* 112:132 */     boolean eq = true;
/* 113:133 */     for (int i = 0; i < conss.length; i++) {
/* 114:134 */       if (Modifier.isPublic(conss[i].getModifiers()))
/* 115:    */       {
/* 116:137 */         Class[] params = conss[i].getParameterTypes();
/* 117:138 */         if (params.length == parameterTypes.length)
/* 118:    */         {
/* 119:141 */           eq = true;
/* 120:142 */           for (int j = 0; j < params.length; j++) {
/* 121:143 */             if (!params[j].equals(parameterTypes[j]))
/* 122:    */             {
/* 123:144 */               eq = false;
/* 124:145 */               break;
/* 125:    */             }
/* 126:    */           }
/* 127:148 */           if (eq) {
/* 128:149 */             return i;
/* 129:    */           }
/* 130:    */         }
/* 131:    */       }
/* 132:    */     }
/* 133:152 */     return -1;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getMaxIndex()
/* 137:    */   {
/* 138:156 */     Method[] methods = this.type.getMethods();
/* 139:157 */     int count = 0;
/* 140:158 */     for (int i = 0; i < methods.length; i++) {
/* 141:159 */       if (Modifier.isPublic(methods[i].getModifiers())) {
/* 142:160 */         count++;
/* 143:    */       }
/* 144:    */     }
/* 145:163 */     return count;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getName()
/* 149:    */   {
/* 150:167 */     return this.type.getName();
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Class getJavaClass()
/* 154:    */   {
/* 155:171 */     return this.type;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String toString()
/* 159:    */   {
/* 160:175 */     return this.type.toString();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public int hashCode()
/* 164:    */   {
/* 165:179 */     return this.type.hashCode();
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean equals(Object o)
/* 169:    */   {
/* 170:183 */     if (!(o instanceof FastClass)) {
/* 171:184 */       return false;
/* 172:    */     }
/* 173:186 */     return this.type.equals(((FastClass)o).type);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public static FastClass create(Class type)
/* 177:    */   {
/* 178:190 */     FastClass fc = new FastClass(type);
/* 179:191 */     return fc;
/* 180:    */   }
/* 181:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.internal.javassist.FastClass
 * JD-Core Version:    0.7.0.1
 */