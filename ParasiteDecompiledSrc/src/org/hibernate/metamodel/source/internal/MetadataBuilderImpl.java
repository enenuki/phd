/*   1:    */ package org.hibernate.metamodel.source.internal;
/*   2:    */ 
/*   3:    */ import javax.persistence.SharedCacheMode;
/*   4:    */ import org.hibernate.cache.spi.access.AccessType;
/*   5:    */ import org.hibernate.cfg.EJB3NamingStrategy;
/*   6:    */ import org.hibernate.cfg.NamingStrategy;
/*   7:    */ import org.hibernate.metamodel.Metadata;
/*   8:    */ import org.hibernate.metamodel.Metadata.Options;
/*   9:    */ import org.hibernate.metamodel.MetadataBuilder;
/*  10:    */ import org.hibernate.metamodel.MetadataSourceProcessingOrder;
/*  11:    */ import org.hibernate.metamodel.MetadataSources;
/*  12:    */ import org.hibernate.service.ServiceRegistry;
/*  13:    */ import org.hibernate.service.config.spi.ConfigurationService;
/*  14:    */ import org.hibernate.service.config.spi.ConfigurationService.Converter;
/*  15:    */ 
/*  16:    */ public class MetadataBuilderImpl
/*  17:    */   implements MetadataBuilder
/*  18:    */ {
/*  19:    */   private final MetadataSources sources;
/*  20:    */   private final OptionsImpl options;
/*  21:    */   
/*  22:    */   public MetadataBuilderImpl(MetadataSources sources)
/*  23:    */   {
/*  24: 47 */     this.sources = sources;
/*  25: 48 */     this.options = new OptionsImpl(sources.getServiceRegistry());
/*  26:    */   }
/*  27:    */   
/*  28:    */   public MetadataBuilder with(NamingStrategy namingStrategy)
/*  29:    */   {
/*  30: 53 */     this.options.namingStrategy = namingStrategy;
/*  31: 54 */     return this;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public MetadataBuilder with(MetadataSourceProcessingOrder metadataSourceProcessingOrder)
/*  35:    */   {
/*  36: 59 */     this.options.metadataSourceProcessingOrder = metadataSourceProcessingOrder;
/*  37: 60 */     return this;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public MetadataBuilder with(SharedCacheMode sharedCacheMode)
/*  41:    */   {
/*  42: 65 */     this.options.sharedCacheMode = sharedCacheMode;
/*  43: 66 */     return this;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public MetadataBuilder with(AccessType accessType)
/*  47:    */   {
/*  48: 71 */     this.options.defaultCacheAccessType = accessType;
/*  49: 72 */     return this;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public MetadataBuilder withNewIdentifierGeneratorsEnabled(boolean enabled)
/*  53:    */   {
/*  54: 77 */     this.options.useNewIdentifierGenerators = enabled;
/*  55: 78 */     return this;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Metadata buildMetadata()
/*  59:    */   {
/*  60: 83 */     return new MetadataImpl(this.sources, this.options);
/*  61:    */   }
/*  62:    */   
/*  63:    */   private static class OptionsImpl
/*  64:    */     implements Metadata.Options
/*  65:    */   {
/*  66: 87 */     private MetadataSourceProcessingOrder metadataSourceProcessingOrder = MetadataSourceProcessingOrder.HBM_FIRST;
/*  67: 88 */     private NamingStrategy namingStrategy = EJB3NamingStrategy.INSTANCE;
/*  68: 89 */     private SharedCacheMode sharedCacheMode = SharedCacheMode.ENABLE_SELECTIVE;
/*  69:    */     private AccessType defaultCacheAccessType;
/*  70:    */     private boolean useNewIdentifierGenerators;
/*  71:    */     private boolean globallyQuotedIdentifiers;
/*  72:    */     private String defaultSchemaName;
/*  73:    */     private String defaultCatalogName;
/*  74:    */     
/*  75:    */     public OptionsImpl(ServiceRegistry serviceRegistry)
/*  76:    */     {
/*  77: 97 */       ConfigurationService configService = (ConfigurationService)serviceRegistry.getService(ConfigurationService.class);
/*  78:    */       
/*  79:    */ 
/*  80:100 */       this.defaultCacheAccessType = ((AccessType)configService.getSetting("hibernate.cache.default_cache_concurrency_strategy", new ConfigurationService.Converter()
/*  81:    */       {
/*  82:    */         public AccessType convert(Object value)
/*  83:    */         {
/*  84:105 */           return AccessType.fromExternalName(value.toString());
/*  85:    */         }
/*  86:109 */       }));
/*  87:110 */       this.useNewIdentifierGenerators = ((Boolean)configService.getSetting("hibernate.id.new_generator_mappings", new ConfigurationService.Converter()
/*  88:    */       {
/*  89:    */         public Boolean convert(Object value)
/*  90:    */         {
/*  91:115 */           return Boolean.valueOf(Boolean.parseBoolean(value.toString()));
/*  92:    */         }
/*  93:115 */       }, Boolean.valueOf(false))).booleanValue();
/*  94:    */       
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:121 */       this.defaultSchemaName = ((String)configService.getSetting("hibernate.default_schema", new ConfigurationService.Converter()
/* 100:    */       {
/* 101:    */         public String convert(Object value)
/* 102:    */         {
/* 103:126 */           return value.toString();
/* 104:    */         }
/* 105:126 */       }, null));
/* 106:    */       
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:132 */       this.defaultCatalogName = ((String)configService.getSetting("hibernate.default_catalog", new ConfigurationService.Converter()
/* 112:    */       {
/* 113:    */         public String convert(Object value)
/* 114:    */         {
/* 115:137 */           return value.toString();
/* 116:    */         }
/* 117:137 */       }, null));
/* 118:    */       
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:143 */       this.globallyQuotedIdentifiers = ((Boolean)configService.getSetting("hibernate.globally_quoted_identifiers", new ConfigurationService.Converter()
/* 124:    */       {
/* 125:    */         public Boolean convert(Object value)
/* 126:    */         {
/* 127:148 */           return Boolean.valueOf(Boolean.parseBoolean(value.toString()));
/* 128:    */         }
/* 129:148 */       }, Boolean.valueOf(false))).booleanValue();
/* 130:    */     }
/* 131:    */     
/* 132:    */     public MetadataSourceProcessingOrder getMetadataSourceProcessingOrder()
/* 133:    */     {
/* 134:158 */       return this.metadataSourceProcessingOrder;
/* 135:    */     }
/* 136:    */     
/* 137:    */     public NamingStrategy getNamingStrategy()
/* 138:    */     {
/* 139:163 */       return this.namingStrategy;
/* 140:    */     }
/* 141:    */     
/* 142:    */     public AccessType getDefaultAccessType()
/* 143:    */     {
/* 144:168 */       return this.defaultCacheAccessType;
/* 145:    */     }
/* 146:    */     
/* 147:    */     public SharedCacheMode getSharedCacheMode()
/* 148:    */     {
/* 149:173 */       return this.sharedCacheMode;
/* 150:    */     }
/* 151:    */     
/* 152:    */     public boolean useNewIdentifierGenerators()
/* 153:    */     {
/* 154:178 */       return this.useNewIdentifierGenerators;
/* 155:    */     }
/* 156:    */     
/* 157:    */     public boolean isGloballyQuotedIdentifiers()
/* 158:    */     {
/* 159:183 */       return this.globallyQuotedIdentifiers;
/* 160:    */     }
/* 161:    */     
/* 162:    */     public String getDefaultSchemaName()
/* 163:    */     {
/* 164:188 */       return this.defaultSchemaName;
/* 165:    */     }
/* 166:    */     
/* 167:    */     public String getDefaultCatalogName()
/* 168:    */     {
/* 169:193 */       return this.defaultCatalogName;
/* 170:    */     }
/* 171:    */   }
/* 172:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.internal.MetadataBuilderImpl
 * JD-Core Version:    0.7.0.1
 */