/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.pretty.MessageHelper;
/*  5:   */ 
/*  6:   */ public class UnresolvableObjectException
/*  7:   */   extends HibernateException
/*  8:   */ {
/*  9:   */   private final Serializable identifier;
/* 10:   */   private final String entityName;
/* 11:   */   
/* 12:   */   public UnresolvableObjectException(Serializable identifier, String clazz)
/* 13:   */   {
/* 14:42 */     this("No row with the given identifier exists", identifier, clazz);
/* 15:   */   }
/* 16:   */   
/* 17:   */   UnresolvableObjectException(String message, Serializable identifier, String clazz)
/* 18:   */   {
/* 19:45 */     super(message);
/* 20:46 */     this.identifier = identifier;
/* 21:47 */     this.entityName = clazz;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Serializable getIdentifier()
/* 25:   */   {
/* 26:50 */     return this.identifier;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getMessage()
/* 30:   */   {
/* 31:54 */     return super.getMessage() + ": " + MessageHelper.infoString(this.entityName, this.identifier);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getEntityName()
/* 35:   */   {
/* 36:59 */     return this.entityName;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void throwIfNull(Object o, Serializable id, String clazz)
/* 40:   */     throws UnresolvableObjectException
/* 41:   */   {
/* 42:64 */     if (o == null) {
/* 43:64 */       throw new UnresolvableObjectException(id, clazz);
/* 44:   */     }
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.UnresolvableObjectException
 * JD-Core Version:    0.7.0.1
 */