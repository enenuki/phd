/*   1:    */ package javassist.util.proxy;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.AccessibleObject;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.Field;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.security.AccessController;
/*   8:    */ import java.security.PrivilegedAction;
/*   9:    */ import java.security.PrivilegedActionException;
/*  10:    */ import java.security.PrivilegedExceptionAction;
/*  11:    */ 
/*  12:    */ class SecurityActions
/*  13:    */ {
/*  14:    */   static Method[] getDeclaredMethods(Class clazz)
/*  15:    */   {
/*  16: 28 */     if (System.getSecurityManager() == null) {
/*  17: 29 */       return clazz.getDeclaredMethods();
/*  18:    */     }
/*  19: 31 */     (Method[])AccessController.doPrivileged(new PrivilegedAction()
/*  20:    */     {
/*  21:    */       private final Class val$clazz;
/*  22:    */       
/*  23:    */       public Object run()
/*  24:    */       {
/*  25: 34 */         return this.val$clazz.getDeclaredMethods();
/*  26:    */       }
/*  27:    */     });
/*  28:    */   }
/*  29:    */   
/*  30:    */   static Constructor[] getDeclaredConstructors(Class clazz)
/*  31:    */   {
/*  32: 41 */     if (System.getSecurityManager() == null) {
/*  33: 42 */       return clazz.getDeclaredConstructors();
/*  34:    */     }
/*  35: 44 */     (Constructor[])AccessController.doPrivileged(new PrivilegedAction()
/*  36:    */     {
/*  37:    */       private final Class val$clazz;
/*  38:    */       
/*  39:    */       public Object run()
/*  40:    */       {
/*  41: 47 */         return this.val$clazz.getDeclaredConstructors();
/*  42:    */       }
/*  43:    */     });
/*  44:    */   }
/*  45:    */   
/*  46:    */   static Method getDeclaredMethod(Class clazz, final String name, final Class[] types)
/*  47:    */     throws NoSuchMethodException
/*  48:    */   {
/*  49: 55 */     if (System.getSecurityManager() == null) {
/*  50: 56 */       return clazz.getDeclaredMethod(name, types);
/*  51:    */     }
/*  52:    */     try
/*  53:    */     {
/*  54: 59 */       (Method)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*  55:    */       {
/*  56:    */         private final Class val$clazz;
/*  57:    */         private final String val$name;
/*  58:    */         private final Class[] val$types;
/*  59:    */         
/*  60:    */         public Object run()
/*  61:    */           throws Exception
/*  62:    */         {
/*  63: 62 */           return this.val$clazz.getDeclaredMethod(name, types);
/*  64:    */         }
/*  65:    */       });
/*  66:    */     }
/*  67:    */     catch (PrivilegedActionException e)
/*  68:    */     {
/*  69: 67 */       if ((e.getCause() instanceof NoSuchMethodException)) {
/*  70: 68 */         throw ((NoSuchMethodException)e.getCause());
/*  71:    */       }
/*  72: 70 */       throw new RuntimeException(e.getCause());
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   static Constructor getDeclaredConstructor(Class clazz, final Class[] types)
/*  77:    */     throws NoSuchMethodException
/*  78:    */   {
/*  79: 79 */     if (System.getSecurityManager() == null) {
/*  80: 80 */       return clazz.getDeclaredConstructor(types);
/*  81:    */     }
/*  82:    */     try
/*  83:    */     {
/*  84: 83 */       (Constructor)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*  85:    */       {
/*  86:    */         private final Class val$clazz;
/*  87:    */         private final Class[] val$types;
/*  88:    */         
/*  89:    */         public Object run()
/*  90:    */           throws Exception
/*  91:    */         {
/*  92: 86 */           return this.val$clazz.getDeclaredConstructor(types);
/*  93:    */         }
/*  94:    */       });
/*  95:    */     }
/*  96:    */     catch (PrivilegedActionException e)
/*  97:    */     {
/*  98: 91 */       if ((e.getCause() instanceof NoSuchMethodException)) {
/*  99: 92 */         throw ((NoSuchMethodException)e.getCause());
/* 100:    */       }
/* 101: 94 */       throw new RuntimeException(e.getCause());
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   static void setAccessible(AccessibleObject ao, final boolean accessible)
/* 106:    */   {
/* 107:101 */     if (System.getSecurityManager() == null) {
/* 108:102 */       ao.setAccessible(accessible);
/* 109:    */     } else {
/* 110:104 */       AccessController.doPrivileged(new PrivilegedAction()
/* 111:    */       {
/* 112:    */         private final AccessibleObject val$ao;
/* 113:    */         private final boolean val$accessible;
/* 114:    */         
/* 115:    */         public Object run()
/* 116:    */         {
/* 117:106 */           this.val$ao.setAccessible(accessible);
/* 118:107 */           return null;
/* 119:    */         }
/* 120:    */       });
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   static void set(Field fld, final Object target, final Object value)
/* 125:    */     throws IllegalAccessException
/* 126:    */   {
/* 127:116 */     if (System.getSecurityManager() == null) {
/* 128:117 */       fld.set(target, value);
/* 129:    */     } else {
/* 130:    */       try
/* 131:    */       {
/* 132:120 */         AccessController.doPrivileged(new PrivilegedExceptionAction()
/* 133:    */         {
/* 134:    */           private final Field val$fld;
/* 135:    */           private final Object val$target;
/* 136:    */           private final Object val$value;
/* 137:    */           
/* 138:    */           public Object run()
/* 139:    */             throws Exception
/* 140:    */           {
/* 141:122 */             this.val$fld.set(target, value);
/* 142:123 */             return null;
/* 143:    */           }
/* 144:    */         });
/* 145:    */       }
/* 146:    */       catch (PrivilegedActionException e)
/* 147:    */       {
/* 148:128 */         if ((e.getCause() instanceof NoSuchMethodException)) {
/* 149:129 */           throw ((IllegalAccessException)e.getCause());
/* 150:    */         }
/* 151:131 */         throw new RuntimeException(e.getCause());
/* 152:    */       }
/* 153:    */     }
/* 154:    */   }
/* 155:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.util.proxy.SecurityActions
 * JD-Core Version:    0.7.0.1
 */