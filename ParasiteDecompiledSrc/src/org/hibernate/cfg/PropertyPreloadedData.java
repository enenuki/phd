/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.annotations.common.reflection.XClass;
/*  5:   */ import org.hibernate.annotations.common.reflection.XProperty;
/*  6:   */ 
/*  7:   */ public class PropertyPreloadedData
/*  8:   */   implements PropertyData
/*  9:   */ {
/* 10:   */   private final AccessType defaultAccess;
/* 11:   */   private final String propertyName;
/* 12:   */   private final XClass returnedClass;
/* 13:   */   
/* 14:   */   public PropertyPreloadedData(AccessType defaultAccess, String propertyName, XClass returnedClass)
/* 15:   */   {
/* 16:37 */     this.defaultAccess = defaultAccess;
/* 17:38 */     this.propertyName = propertyName;
/* 18:39 */     this.returnedClass = returnedClass;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public AccessType getDefaultAccess()
/* 22:   */     throws MappingException
/* 23:   */   {
/* 24:43 */     return this.defaultAccess;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getPropertyName()
/* 28:   */     throws MappingException
/* 29:   */   {
/* 30:47 */     return this.propertyName;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public XClass getClassOrElement()
/* 34:   */     throws MappingException
/* 35:   */   {
/* 36:51 */     return getPropertyClass();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public XClass getPropertyClass()
/* 40:   */     throws MappingException
/* 41:   */   {
/* 42:55 */     return this.returnedClass;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String getClassOrElementName()
/* 46:   */     throws MappingException
/* 47:   */   {
/* 48:59 */     return getTypeName();
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String getTypeName()
/* 52:   */     throws MappingException
/* 53:   */   {
/* 54:63 */     return this.returnedClass == null ? null : this.returnedClass.getName();
/* 55:   */   }
/* 56:   */   
/* 57:   */   public XProperty getProperty()
/* 58:   */   {
/* 59:67 */     return null;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public XClass getDeclaringClass()
/* 63:   */   {
/* 64:73 */     return null;
/* 65:   */   }
/* 66:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.PropertyPreloadedData
 * JD-Core Version:    0.7.0.1
 */