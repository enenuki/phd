/*   1:    */ package org.hibernate.id;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Properties;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.Session;
/*   7:    */ import org.hibernate.TransientObjectException;
/*   8:    */ import org.hibernate.dialect.Dialect;
/*   9:    */ import org.hibernate.engine.internal.ForeignKeys;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.persister.entity.EntityPersister;
/*  13:    */ import org.hibernate.type.EntityType;
/*  14:    */ import org.hibernate.type.Type;
/*  15:    */ 
/*  16:    */ public class ForeignGenerator
/*  17:    */   implements IdentifierGenerator, Configurable
/*  18:    */ {
/*  19:    */   private String entityName;
/*  20:    */   private String propertyName;
/*  21:    */   
/*  22:    */   public String getEntityName()
/*  23:    */   {
/*  24: 59 */     return this.entityName;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getPropertyName()
/*  28:    */   {
/*  29: 68 */     return this.propertyName;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getRole()
/*  33:    */   {
/*  34: 78 */     return getEntityName() + '.' + getPropertyName();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void configure(Type type, Properties params, Dialect d)
/*  38:    */   {
/*  39: 85 */     this.propertyName = params.getProperty("property");
/*  40: 86 */     this.entityName = params.getProperty("entity_name");
/*  41: 87 */     if (this.propertyName == null) {
/*  42: 88 */       throw new MappingException("param named \"property\" is required for foreign id generation strategy");
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Serializable generate(SessionImplementor sessionImplementor, Object object)
/*  47:    */   {
/*  48: 96 */     Session session = (Session)sessionImplementor;
/*  49:    */     
/*  50: 98 */     EntityPersister persister = sessionImplementor.getFactory().getEntityPersister(this.entityName);
/*  51: 99 */     Object associatedObject = persister.getPropertyValue(object, this.propertyName);
/*  52:100 */     if (associatedObject == null) {
/*  53:101 */       throw new IdentifierGenerationException("attempted to assign id from null one-to-one property [" + getRole() + "]");
/*  54:    */     }
/*  55:107 */     Type propertyType = persister.getPropertyType(this.propertyName);
/*  56:    */     EntityType foreignValueSourceType;
/*  57:    */     EntityType foreignValueSourceType;
/*  58:108 */     if (propertyType.isEntityType()) {
/*  59:110 */       foreignValueSourceType = (EntityType)propertyType;
/*  60:    */     } else {
/*  61:114 */       foreignValueSourceType = (EntityType)persister.getPropertyType("_identifierMapper." + this.propertyName);
/*  62:    */     }
/*  63:    */     Serializable id;
/*  64:    */     try
/*  65:    */     {
/*  66:119 */       id = ForeignKeys.getEntityIdentifierIfNotUnsaved(foreignValueSourceType.getAssociatedEntityName(), associatedObject, sessionImplementor);
/*  67:    */     }
/*  68:    */     catch (TransientObjectException toe)
/*  69:    */     {
/*  70:126 */       id = session.save(foreignValueSourceType.getAssociatedEntityName(), associatedObject);
/*  71:    */     }
/*  72:129 */     if (session.contains(object)) {
/*  73:131 */       return IdentifierGeneratorHelper.SHORT_CIRCUIT_INDICATOR;
/*  74:    */     }
/*  75:134 */     return id;
/*  76:    */   }
/*  77:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.ForeignGenerator
 * JD-Core Version:    0.7.0.1
 */