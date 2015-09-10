/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import org.hibernate.engine.spi.EntityEntry;
/*  4:   */ 
/*  5:   */ public class FlushEntityEvent
/*  6:   */   extends AbstractEvent
/*  7:   */ {
/*  8:   */   private Object entity;
/*  9:   */   private Object[] propertyValues;
/* 10:   */   private Object[] databaseSnapshot;
/* 11:   */   private int[] dirtyProperties;
/* 12:   */   private boolean hasDirtyCollection;
/* 13:   */   private boolean dirtyCheckPossible;
/* 14:   */   private boolean dirtyCheckHandledByInterceptor;
/* 15:   */   private EntityEntry entityEntry;
/* 16:   */   
/* 17:   */   public FlushEntityEvent(EventSource source, Object entity, EntityEntry entry)
/* 18:   */   {
/* 19:42 */     super(source);
/* 20:43 */     this.entity = entity;
/* 21:44 */     this.entityEntry = entry;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public EntityEntry getEntityEntry()
/* 25:   */   {
/* 26:48 */     return this.entityEntry;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Object[] getDatabaseSnapshot()
/* 30:   */   {
/* 31:51 */     return this.databaseSnapshot;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setDatabaseSnapshot(Object[] databaseSnapshot)
/* 35:   */   {
/* 36:54 */     this.databaseSnapshot = databaseSnapshot;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean hasDatabaseSnapshot()
/* 40:   */   {
/* 41:57 */     return this.databaseSnapshot != null;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public boolean isDirtyCheckHandledByInterceptor()
/* 45:   */   {
/* 46:60 */     return this.dirtyCheckHandledByInterceptor;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void setDirtyCheckHandledByInterceptor(boolean dirtyCheckHandledByInterceptor)
/* 50:   */   {
/* 51:63 */     this.dirtyCheckHandledByInterceptor = dirtyCheckHandledByInterceptor;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public boolean isDirtyCheckPossible()
/* 55:   */   {
/* 56:66 */     return this.dirtyCheckPossible;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void setDirtyCheckPossible(boolean dirtyCheckPossible)
/* 60:   */   {
/* 61:69 */     this.dirtyCheckPossible = dirtyCheckPossible;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public int[] getDirtyProperties()
/* 65:   */   {
/* 66:72 */     return this.dirtyProperties;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public void setDirtyProperties(int[] dirtyProperties)
/* 70:   */   {
/* 71:75 */     this.dirtyProperties = dirtyProperties;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public boolean hasDirtyCollection()
/* 75:   */   {
/* 76:78 */     return this.hasDirtyCollection;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public void setHasDirtyCollection(boolean hasDirtyCollection)
/* 80:   */   {
/* 81:81 */     this.hasDirtyCollection = hasDirtyCollection;
/* 82:   */   }
/* 83:   */   
/* 84:   */   public Object[] getPropertyValues()
/* 85:   */   {
/* 86:84 */     return this.propertyValues;
/* 87:   */   }
/* 88:   */   
/* 89:   */   public void setPropertyValues(Object[] propertyValues)
/* 90:   */   {
/* 91:87 */     this.propertyValues = propertyValues;
/* 92:   */   }
/* 93:   */   
/* 94:   */   public Object getEntity()
/* 95:   */   {
/* 96:90 */     return this.entity;
/* 97:   */   }
/* 98:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.FlushEntityEvent
 * JD-Core Version:    0.7.0.1
 */