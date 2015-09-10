/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.pretty.MessageHelper;
/*  5:   */ 
/*  6:   */ public class StaleObjectStateException
/*  7:   */   extends StaleStateException
/*  8:   */ {
/*  9:   */   private final String entityName;
/* 10:   */   private final Serializable identifier;
/* 11:   */   
/* 12:   */   public StaleObjectStateException(String persistentClass, Serializable identifier)
/* 13:   */   {
/* 14:42 */     super("Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect)");
/* 15:43 */     this.entityName = persistentClass;
/* 16:44 */     this.identifier = identifier;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getEntityName()
/* 20:   */   {
/* 21:48 */     return this.entityName;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Serializable getIdentifier()
/* 25:   */   {
/* 26:52 */     return this.identifier;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getMessage()
/* 30:   */   {
/* 31:56 */     return super.getMessage() + ": " + MessageHelper.infoString(this.entityName, this.identifier);
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.StaleObjectStateException
 * JD-Core Version:    0.7.0.1
 */