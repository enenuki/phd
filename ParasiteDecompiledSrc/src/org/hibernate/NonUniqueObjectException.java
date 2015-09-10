/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.pretty.MessageHelper;
/*  5:   */ 
/*  6:   */ public class NonUniqueObjectException
/*  7:   */   extends HibernateException
/*  8:   */ {
/*  9:   */   private final Serializable identifier;
/* 10:   */   private final String entityName;
/* 11:   */   
/* 12:   */   public NonUniqueObjectException(String message, Serializable id, String clazz)
/* 13:   */   {
/* 14:44 */     super(message);
/* 15:45 */     this.entityName = clazz;
/* 16:46 */     this.identifier = id;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public NonUniqueObjectException(Serializable id, String clazz)
/* 20:   */   {
/* 21:50 */     this("a different object with the same identifier value was already associated with the session", id, clazz);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Serializable getIdentifier()
/* 25:   */   {
/* 26:54 */     return this.identifier;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getMessage()
/* 30:   */   {
/* 31:58 */     return super.getMessage() + ": " + MessageHelper.infoString(this.entityName, this.identifier);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getEntityName()
/* 35:   */   {
/* 36:63 */     return this.entityName;
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.NonUniqueObjectException
 * JD-Core Version:    0.7.0.1
 */