/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ public class DeleteEvent
/*  4:   */   extends AbstractEvent
/*  5:   */ {
/*  6:   */   private Object object;
/*  7:   */   private String entityName;
/*  8:   */   private boolean cascadeDeleteEnabled;
/*  9:   */   
/* 10:   */   public DeleteEvent(Object object, EventSource source)
/* 11:   */   {
/* 12:43 */     super(source);
/* 13:44 */     if (object == null) {
/* 14:45 */       throw new IllegalArgumentException("attempt to create delete event with null entity");
/* 15:   */     }
/* 16:49 */     this.object = object;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public DeleteEvent(String entityName, Object object, EventSource source)
/* 20:   */   {
/* 21:53 */     this(object, source);
/* 22:54 */     this.entityName = entityName;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public DeleteEvent(String entityName, Object object, boolean isCascadeDeleteEnabled, EventSource source)
/* 26:   */   {
/* 27:58 */     this(object, source);
/* 28:59 */     this.entityName = entityName;
/* 29:60 */     this.cascadeDeleteEnabled = isCascadeDeleteEnabled;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Object getObject()
/* 33:   */   {
/* 34:69 */     return this.object;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String getEntityName()
/* 38:   */   {
/* 39:73 */     return this.entityName;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean isCascadeDeleteEnabled()
/* 43:   */   {
/* 44:77 */     return this.cascadeDeleteEnabled;
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.DeleteEvent
 * JD-Core Version:    0.7.0.1
 */