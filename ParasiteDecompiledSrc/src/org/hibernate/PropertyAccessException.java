/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.util.StringHelper;
/*  4:   */ 
/*  5:   */ public class PropertyAccessException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   private final Class persistentClass;
/*  9:   */   private final String propertyName;
/* 10:   */   private final boolean wasSetter;
/* 11:   */   
/* 12:   */   public PropertyAccessException(Throwable root, String s, boolean wasSetter, Class persistentClass, String propertyName)
/* 13:   */   {
/* 14:48 */     super(s, root);
/* 15:49 */     this.persistentClass = persistentClass;
/* 16:50 */     this.wasSetter = wasSetter;
/* 17:51 */     this.propertyName = propertyName;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Class getPersistentClass()
/* 21:   */   {
/* 22:55 */     return this.persistentClass;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getPropertyName()
/* 26:   */   {
/* 27:59 */     return this.propertyName;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String getMessage()
/* 31:   */   {
/* 32:64 */     return super.getMessage() + (this.wasSetter ? " setter of " : " getter of ") + StringHelper.qualify(this.persistentClass.getName(), this.propertyName);
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.PropertyAccessException
 * JD-Core Version:    0.7.0.1
 */