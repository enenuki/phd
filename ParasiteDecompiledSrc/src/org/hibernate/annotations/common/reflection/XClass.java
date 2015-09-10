/*  1:   */ package org.hibernate.annotations.common.reflection;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ 
/*  5:   */ public abstract interface XClass
/*  6:   */   extends XAnnotatedElement
/*  7:   */ {
/*  8:   */   public static final String ACCESS_PROPERTY = "property";
/*  9:   */   public static final String ACCESS_FIELD = "field";
/* 10:37 */   public static final Filter DEFAULT_FILTER = new Filter()
/* 11:   */   {
/* 12:   */     public boolean returnStatic()
/* 13:   */     {
/* 14:40 */       return false;
/* 15:   */     }
/* 16:   */     
/* 17:   */     public boolean returnTransient()
/* 18:   */     {
/* 19:44 */       return false;
/* 20:   */     }
/* 21:   */   };
/* 22:   */   
/* 23:   */   public abstract String getName();
/* 24:   */   
/* 25:   */   public abstract XClass getSuperclass();
/* 26:   */   
/* 27:   */   public abstract XClass[] getInterfaces();
/* 28:   */   
/* 29:   */   public abstract boolean isInterface();
/* 30:   */   
/* 31:   */   public abstract boolean isAbstract();
/* 32:   */   
/* 33:   */   public abstract boolean isPrimitive();
/* 34:   */   
/* 35:   */   public abstract boolean isEnum();
/* 36:   */   
/* 37:   */   public abstract boolean isAssignableFrom(XClass paramXClass);
/* 38:   */   
/* 39:   */   public abstract List<XProperty> getDeclaredProperties(String paramString);
/* 40:   */   
/* 41:   */   public abstract List<XProperty> getDeclaredProperties(String paramString, Filter paramFilter);
/* 42:   */   
/* 43:   */   public abstract List<XMethod> getDeclaredMethods();
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.XClass
 * JD-Core Version:    0.7.0.1
 */