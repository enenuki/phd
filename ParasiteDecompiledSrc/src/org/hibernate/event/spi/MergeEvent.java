/*   1:    */ package org.hibernate.event.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class MergeEvent
/*   6:    */   extends AbstractEvent
/*   7:    */ {
/*   8:    */   private Object original;
/*   9:    */   private Serializable requestedId;
/*  10:    */   private String entityName;
/*  11:    */   private Object entity;
/*  12:    */   private Object result;
/*  13:    */   
/*  14:    */   public MergeEvent(String entityName, Object original, EventSource source)
/*  15:    */   {
/*  16: 42 */     this(original, source);
/*  17: 43 */     this.entityName = entityName;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public MergeEvent(String entityName, Object original, Serializable id, EventSource source)
/*  21:    */   {
/*  22: 47 */     this(entityName, original, source);
/*  23: 48 */     this.requestedId = id;
/*  24: 49 */     if (this.requestedId == null) {
/*  25: 50 */       throw new IllegalArgumentException("attempt to create merge event with null identifier");
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public MergeEvent(Object object, EventSource source)
/*  30:    */   {
/*  31: 57 */     super(source);
/*  32: 58 */     if (object == null) {
/*  33: 59 */       throw new IllegalArgumentException("attempt to create merge event with null entity");
/*  34:    */     }
/*  35: 63 */     this.original = object;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Object getOriginal()
/*  39:    */   {
/*  40: 67 */     return this.original;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setOriginal(Object object)
/*  44:    */   {
/*  45: 71 */     this.original = object;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Serializable getRequestedId()
/*  49:    */   {
/*  50: 75 */     return this.requestedId;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setRequestedId(Serializable requestedId)
/*  54:    */   {
/*  55: 79 */     this.requestedId = requestedId;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getEntityName()
/*  59:    */   {
/*  60: 83 */     return this.entityName;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setEntityName(String entityName)
/*  64:    */   {
/*  65: 87 */     this.entityName = entityName;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Object getEntity()
/*  69:    */   {
/*  70: 91 */     return this.entity;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setEntity(Object entity)
/*  74:    */   {
/*  75: 94 */     this.entity = entity;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Object getResult()
/*  79:    */   {
/*  80: 98 */     return this.result;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setResult(Object result)
/*  84:    */   {
/*  85:102 */     this.result = result;
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.MergeEvent
 * JD-Core Version:    0.7.0.1
 */