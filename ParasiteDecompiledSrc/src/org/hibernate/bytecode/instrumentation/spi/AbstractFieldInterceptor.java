/*   1:    */ package org.hibernate.bytecode.instrumentation.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Set;
/*   5:    */ import org.hibernate.LazyInitializationException;
/*   6:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   7:    */ import org.hibernate.engine.spi.SessionImplementor;
/*   8:    */ 
/*   9:    */ public abstract class AbstractFieldInterceptor
/*  10:    */   implements FieldInterceptor, Serializable
/*  11:    */ {
/*  12:    */   private transient SessionImplementor session;
/*  13:    */   private Set uninitializedFields;
/*  14:    */   private final String entityName;
/*  15:    */   private transient boolean initializing;
/*  16:    */   private boolean dirty;
/*  17:    */   
/*  18:    */   protected AbstractFieldInterceptor(SessionImplementor session, Set uninitializedFields, String entityName)
/*  19:    */   {
/*  20: 44 */     this.session = session;
/*  21: 45 */     this.uninitializedFields = uninitializedFields;
/*  22: 46 */     this.entityName = entityName;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public final void setSession(SessionImplementor session)
/*  26:    */   {
/*  27: 53 */     this.session = session;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public final boolean isInitialized()
/*  31:    */   {
/*  32: 57 */     return (this.uninitializedFields == null) || (this.uninitializedFields.size() == 0);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final boolean isInitialized(String field)
/*  36:    */   {
/*  37: 61 */     return (this.uninitializedFields == null) || (!this.uninitializedFields.contains(field));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final void dirty()
/*  41:    */   {
/*  42: 65 */     this.dirty = true;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public final boolean isDirty()
/*  46:    */   {
/*  47: 69 */     return this.dirty;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public final void clearDirty()
/*  51:    */   {
/*  52: 73 */     this.dirty = false;
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected final Object intercept(Object target, String fieldName, Object value)
/*  56:    */   {
/*  57: 80 */     if (this.initializing) {
/*  58: 81 */       return value;
/*  59:    */     }
/*  60: 84 */     if ((this.uninitializedFields != null) && (this.uninitializedFields.contains(fieldName)))
/*  61:    */     {
/*  62: 85 */       if (this.session == null) {
/*  63: 86 */         throw new LazyInitializationException("entity with lazy properties is not associated with a session");
/*  64:    */       }
/*  65: 88 */       if ((!this.session.isOpen()) || (!this.session.isConnected())) {
/*  66: 89 */         throw new LazyInitializationException("session is not connected");
/*  67:    */       }
/*  68: 93 */       this.initializing = true;
/*  69:    */       Object result;
/*  70:    */       try
/*  71:    */       {
/*  72: 95 */         result = ((LazyPropertyInitializer)this.session.getFactory().getEntityPersister(this.entityName)).initializeLazyProperty(fieldName, target, this.session);
/*  73:    */       }
/*  74:    */       finally
/*  75:    */       {
/*  76:100 */         this.initializing = false;
/*  77:    */       }
/*  78:102 */       this.uninitializedFields = null;
/*  79:103 */       return result;
/*  80:    */     }
/*  81:106 */     return value;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public final SessionImplementor getSession()
/*  85:    */   {
/*  86:111 */     return this.session;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public final Set getUninitializedFields()
/*  90:    */   {
/*  91:115 */     return this.uninitializedFields;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public final String getEntityName()
/*  95:    */   {
/*  96:119 */     return this.entityName;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public final boolean isInitializing()
/* 100:    */   {
/* 101:123 */     return this.initializing;
/* 102:    */   }
/* 103:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.instrumentation.spi.AbstractFieldInterceptor
 * JD-Core Version:    0.7.0.1
 */