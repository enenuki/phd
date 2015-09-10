/*   1:    */ package org.hibernate.event.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.engine.spi.EntityEntry;
/*   5:    */ 
/*   6:    */ public class SaveOrUpdateEvent
/*   7:    */   extends AbstractEvent
/*   8:    */ {
/*   9:    */   private Object object;
/*  10:    */   private Serializable requestedId;
/*  11:    */   private String entityName;
/*  12:    */   private Object entity;
/*  13:    */   private EntityEntry entry;
/*  14:    */   private Serializable resultId;
/*  15:    */   
/*  16:    */   public SaveOrUpdateEvent(String entityName, Object original, EventSource source)
/*  17:    */   {
/*  18: 45 */     this(original, source);
/*  19: 46 */     this.entityName = entityName;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public SaveOrUpdateEvent(String entityName, Object original, Serializable id, EventSource source)
/*  23:    */   {
/*  24: 50 */     this(entityName, original, source);
/*  25: 51 */     this.requestedId = id;
/*  26: 52 */     if (this.requestedId == null) {
/*  27: 53 */       throw new IllegalArgumentException("attempt to create saveOrUpdate event with null identifier");
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   public SaveOrUpdateEvent(Object object, EventSource source)
/*  32:    */   {
/*  33: 60 */     super(source);
/*  34: 61 */     if (object == null) {
/*  35: 62 */       throw new IllegalArgumentException("attempt to create saveOrUpdate event with null entity");
/*  36:    */     }
/*  37: 66 */     this.object = object;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Object getObject()
/*  41:    */   {
/*  42: 70 */     return this.object;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setObject(Object object)
/*  46:    */   {
/*  47: 74 */     this.object = object;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Serializable getRequestedId()
/*  51:    */   {
/*  52: 78 */     return this.requestedId;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setRequestedId(Serializable requestedId)
/*  56:    */   {
/*  57: 82 */     this.requestedId = requestedId;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getEntityName()
/*  61:    */   {
/*  62: 86 */     return this.entityName;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setEntityName(String entityName)
/*  66:    */   {
/*  67: 90 */     this.entityName = entityName;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Object getEntity()
/*  71:    */   {
/*  72: 94 */     return this.entity;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setEntity(Object entity)
/*  76:    */   {
/*  77: 98 */     this.entity = entity;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public EntityEntry getEntry()
/*  81:    */   {
/*  82:102 */     return this.entry;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setEntry(EntityEntry entry)
/*  86:    */   {
/*  87:106 */     this.entry = entry;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Serializable getResultId()
/*  91:    */   {
/*  92:110 */     return this.resultId;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setResultId(Serializable resultId)
/*  96:    */   {
/*  97:114 */     this.resultId = resultId;
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.SaveOrUpdateEvent
 * JD-Core Version:    0.7.0.1
 */