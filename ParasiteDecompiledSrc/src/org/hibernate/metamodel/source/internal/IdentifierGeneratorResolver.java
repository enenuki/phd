/*  1:   */ package org.hibernate.metamodel.source.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Properties;
/*  5:   */ import org.hibernate.cfg.NamingStrategy;
/*  6:   */ import org.hibernate.cfg.ObjectNameNormalizer;
/*  7:   */ import org.hibernate.metamodel.binding.EntityBinding;
/*  8:   */ import org.hibernate.metamodel.binding.EntityIdentifier;
/*  9:   */ import org.hibernate.metamodel.binding.HierarchyDetails;
/* 10:   */ import org.hibernate.metamodel.source.MetadataImplementor;
/* 11:   */ import org.hibernate.service.ServiceRegistry;
/* 12:   */ import org.hibernate.service.config.spi.ConfigurationService;
/* 13:   */ 
/* 14:   */ public class IdentifierGeneratorResolver
/* 15:   */ {
/* 16:   */   private final MetadataImplementor metadata;
/* 17:   */   
/* 18:   */   IdentifierGeneratorResolver(MetadataImplementor metadata)
/* 19:   */   {
/* 20:45 */     this.metadata = metadata;
/* 21:   */   }
/* 22:   */   
/* 23:   */   void resolve()
/* 24:   */   {
/* 25:52 */     for (EntityBinding entityBinding : this.metadata.getEntityBindings()) {
/* 26:53 */       if (entityBinding.isRoot())
/* 27:   */       {
/* 28:54 */         Properties properties = new Properties();
/* 29:55 */         properties.putAll(((ConfigurationService)this.metadata.getServiceRegistry().getService(ConfigurationService.class)).getSettings());
/* 30:61 */         if (!properties.contains("hibernate.id.optimizer.pooled.prefer_lo")) {
/* 31:62 */           properties.put("hibernate.id.optimizer.pooled.prefer_lo", "false");
/* 32:   */         }
/* 33:64 */         if (!properties.contains("identifier_normalizer")) {
/* 34:65 */           properties.put("identifier_normalizer", new ObjectNameNormalizerImpl(this.metadata, null));
/* 35:   */         }
/* 36:70 */         entityBinding.getHierarchyDetails().getEntityIdentifier().createIdentifierGenerator(this.metadata.getIdentifierGeneratorFactory(), properties);
/* 37:   */       }
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   private static class ObjectNameNormalizerImpl
/* 42:   */     extends ObjectNameNormalizer
/* 43:   */     implements Serializable
/* 44:   */   {
/* 45:   */     private final boolean useQuotedIdentifiersGlobally;
/* 46:   */     private final NamingStrategy namingStrategy;
/* 47:   */     
/* 48:   */     private ObjectNameNormalizerImpl(MetadataImplementor metadata)
/* 49:   */     {
/* 50:83 */       this.useQuotedIdentifiersGlobally = metadata.isGloballyQuotedIdentifiers();
/* 51:84 */       this.namingStrategy = metadata.getNamingStrategy();
/* 52:   */     }
/* 53:   */     
/* 54:   */     protected boolean isUseQuotedIdentifiersGlobally()
/* 55:   */     {
/* 56:89 */       return this.useQuotedIdentifiersGlobally;
/* 57:   */     }
/* 58:   */     
/* 59:   */     protected NamingStrategy getNamingStrategy()
/* 60:   */     {
/* 61:94 */       return this.namingStrategy;
/* 62:   */     }
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.internal.IdentifierGeneratorResolver
 * JD-Core Version:    0.7.0.1
 */