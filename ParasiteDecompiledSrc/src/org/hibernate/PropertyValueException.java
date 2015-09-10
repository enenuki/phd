/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.util.StringHelper;
/*  4:   */ 
/*  5:   */ public class PropertyValueException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   private final String entityName;
/*  9:   */   private final String propertyName;
/* 10:   */   
/* 11:   */   public PropertyValueException(String s, String entityName, String propertyName)
/* 12:   */   {
/* 13:44 */     super(s);
/* 14:45 */     this.entityName = entityName;
/* 15:46 */     this.propertyName = propertyName;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getEntityName()
/* 19:   */   {
/* 20:50 */     return this.entityName;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getPropertyName()
/* 24:   */   {
/* 25:54 */     return this.propertyName;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getMessage()
/* 29:   */   {
/* 30:59 */     return super.getMessage() + ": " + StringHelper.qualify(this.entityName, this.propertyName);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static String buildPropertyPath(String parent, String child)
/* 34:   */   {
/* 35:72 */     return parent + '.' + child;
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.PropertyValueException
 * JD-Core Version:    0.7.0.1
 */