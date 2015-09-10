/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ public class PersistEvent
/*  4:   */   extends AbstractEvent
/*  5:   */ {
/*  6:   */   private Object object;
/*  7:   */   private String entityName;
/*  8:   */   
/*  9:   */   public PersistEvent(String entityName, Object original, EventSource source)
/* 10:   */   {
/* 11:37 */     this(original, source);
/* 12:38 */     this.entityName = entityName;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public PersistEvent(Object object, EventSource source)
/* 16:   */   {
/* 17:42 */     super(source);
/* 18:43 */     if (object == null) {
/* 19:44 */       throw new IllegalArgumentException("attempt to create create event with null entity");
/* 20:   */     }
/* 21:48 */     this.object = object;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Object getObject()
/* 25:   */   {
/* 26:52 */     return this.object;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setObject(Object object)
/* 30:   */   {
/* 31:56 */     this.object = object;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getEntityName()
/* 35:   */   {
/* 36:60 */     return this.entityName;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setEntityName(String entityName)
/* 40:   */   {
/* 41:64 */     this.entityName = entityName;
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.PersistEvent
 * JD-Core Version:    0.7.0.1
 */