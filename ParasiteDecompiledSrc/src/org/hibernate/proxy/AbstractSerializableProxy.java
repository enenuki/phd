/*  1:   */ package org.hibernate.proxy;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public abstract class AbstractSerializableProxy
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private String entityName;
/*  9:   */   private Serializable id;
/* 10:   */   private Boolean readOnly;
/* 11:   */   
/* 12:   */   protected AbstractSerializableProxy() {}
/* 13:   */   
/* 14:   */   protected AbstractSerializableProxy(String entityName, Serializable id, Boolean readOnly)
/* 15:   */   {
/* 16:45 */     this.entityName = entityName;
/* 17:46 */     this.id = id;
/* 18:47 */     this.readOnly = readOnly;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected String getEntityName()
/* 22:   */   {
/* 23:51 */     return this.entityName;
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected Serializable getId()
/* 27:   */   {
/* 28:55 */     return this.id;
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected void setReadOnlyBeforeAttachedToSession(AbstractLazyInitializer li)
/* 32:   */   {
/* 33:69 */     li.setReadOnlyBeforeAttachedToSession(this.readOnly);
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.AbstractSerializableProxy
 * JD-Core Version:    0.7.0.1
 */