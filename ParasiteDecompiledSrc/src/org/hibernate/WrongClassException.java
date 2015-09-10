/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class WrongClassException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   private final Serializable identifier;
/*  9:   */   private final String entityName;
/* 10:   */   
/* 11:   */   public WrongClassException(String msg, Serializable identifier, String clazz)
/* 12:   */   {
/* 13:42 */     super(msg);
/* 14:43 */     this.identifier = identifier;
/* 15:44 */     this.entityName = clazz;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Serializable getIdentifier()
/* 19:   */   {
/* 20:47 */     return this.identifier;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getMessage()
/* 24:   */   {
/* 25:51 */     return "Object with id: " + this.identifier + " was not of the specified subclass: " + this.entityName + " (" + super.getMessage() + ")";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getEntityName()
/* 29:   */   {
/* 30:59 */     return this.entityName;
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.WrongClassException
 * JD-Core Version:    0.7.0.1
 */