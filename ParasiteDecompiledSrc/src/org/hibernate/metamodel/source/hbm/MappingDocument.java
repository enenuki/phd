/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.hibernate.cfg.NamingStrategy;
/*   5:    */ import org.hibernate.internal.jaxb.JaxbRoot;
/*   6:    */ import org.hibernate.internal.jaxb.Origin;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.hbm.EntityElement;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbFetchProfileElement;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping;
/*  10:    */ import org.hibernate.internal.util.Value;
/*  11:    */ import org.hibernate.metamodel.domain.Type;
/*  12:    */ import org.hibernate.metamodel.source.MappingDefaults;
/*  13:    */ import org.hibernate.metamodel.source.MetaAttributeContext;
/*  14:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  15:    */ import org.hibernate.metamodel.source.internal.OverriddenMappingDefaults;
/*  16:    */ import org.hibernate.service.ServiceRegistry;
/*  17:    */ 
/*  18:    */ public class MappingDocument
/*  19:    */ {
/*  20:    */   private final JaxbRoot<JaxbHibernateMapping> hbmJaxbRoot;
/*  21:    */   private final LocalBindingContextImpl mappingLocalBindingContext;
/*  22:    */   
/*  23:    */   public MappingDocument(JaxbRoot<JaxbHibernateMapping> hbmJaxbRoot, MetadataImplementor metadata)
/*  24:    */   {
/*  25: 52 */     this.hbmJaxbRoot = hbmJaxbRoot;
/*  26: 53 */     this.mappingLocalBindingContext = new LocalBindingContextImpl(metadata, null);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public JaxbHibernateMapping getMappingRoot()
/*  30:    */   {
/*  31: 58 */     return (JaxbHibernateMapping)this.hbmJaxbRoot.getRoot();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Origin getOrigin()
/*  35:    */   {
/*  36: 62 */     return this.hbmJaxbRoot.getOrigin();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public JaxbRoot<JaxbHibernateMapping> getJaxbRoot()
/*  40:    */   {
/*  41: 66 */     return this.hbmJaxbRoot;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public HbmBindingContext getMappingLocalBindingContext()
/*  45:    */   {
/*  46: 70 */     return this.mappingLocalBindingContext;
/*  47:    */   }
/*  48:    */   
/*  49:    */   private class LocalBindingContextImpl
/*  50:    */     implements HbmBindingContext
/*  51:    */   {
/*  52:    */     private final MetadataImplementor metadata;
/*  53:    */     private final MappingDefaults localMappingDefaults;
/*  54:    */     private final MetaAttributeContext metaAttributeContext;
/*  55:    */     
/*  56:    */     private LocalBindingContextImpl(MetadataImplementor metadata)
/*  57:    */     {
/*  58: 79 */       this.metadata = metadata;
/*  59: 80 */       this.localMappingDefaults = new OverriddenMappingDefaults(metadata.getMappingDefaults(), ((JaxbHibernateMapping)MappingDocument.this.hbmJaxbRoot.getRoot()).getPackage(), ((JaxbHibernateMapping)MappingDocument.this.hbmJaxbRoot.getRoot()).getSchema(), ((JaxbHibernateMapping)MappingDocument.this.hbmJaxbRoot.getRoot()).getCatalog(), null, null, ((JaxbHibernateMapping)MappingDocument.this.hbmJaxbRoot.getRoot()).getDefaultCascade(), ((JaxbHibernateMapping)MappingDocument.this.hbmJaxbRoot.getRoot()).getDefaultAccess(), Boolean.valueOf(((JaxbHibernateMapping)MappingDocument.this.hbmJaxbRoot.getRoot()).isDefaultLazy()));
/*  60: 91 */       if ((((JaxbHibernateMapping)MappingDocument.this.hbmJaxbRoot.getRoot()).getMeta() == null) || (((JaxbHibernateMapping)MappingDocument.this.hbmJaxbRoot.getRoot()).getMeta().isEmpty())) {
/*  61: 92 */         this.metaAttributeContext = new MetaAttributeContext(metadata.getGlobalMetaAttributeContext());
/*  62:    */       } else {
/*  63: 95 */         this.metaAttributeContext = Helper.extractMetaAttributeContext(((JaxbHibernateMapping)MappingDocument.this.hbmJaxbRoot.getRoot()).getMeta(), true, metadata.getGlobalMetaAttributeContext());
/*  64:    */       }
/*  65:    */     }
/*  66:    */     
/*  67:    */     public ServiceRegistry getServiceRegistry()
/*  68:    */     {
/*  69:105 */       return this.metadata.getServiceRegistry();
/*  70:    */     }
/*  71:    */     
/*  72:    */     public NamingStrategy getNamingStrategy()
/*  73:    */     {
/*  74:110 */       return this.metadata.getNamingStrategy();
/*  75:    */     }
/*  76:    */     
/*  77:    */     public MappingDefaults getMappingDefaults()
/*  78:    */     {
/*  79:115 */       return this.localMappingDefaults;
/*  80:    */     }
/*  81:    */     
/*  82:    */     public MetadataImplementor getMetadataImplementor()
/*  83:    */     {
/*  84:120 */       return this.metadata;
/*  85:    */     }
/*  86:    */     
/*  87:    */     public <T> Class<T> locateClassByName(String name)
/*  88:    */     {
/*  89:125 */       return this.metadata.locateClassByName(name);
/*  90:    */     }
/*  91:    */     
/*  92:    */     public Type makeJavaType(String className)
/*  93:    */     {
/*  94:130 */       return this.metadata.makeJavaType(className);
/*  95:    */     }
/*  96:    */     
/*  97:    */     public Value<Class<?>> makeClassReference(String className)
/*  98:    */     {
/*  99:135 */       return this.metadata.makeClassReference(className);
/* 100:    */     }
/* 101:    */     
/* 102:    */     public boolean isAutoImport()
/* 103:    */     {
/* 104:140 */       return ((JaxbHibernateMapping)MappingDocument.this.hbmJaxbRoot.getRoot()).isAutoImport();
/* 105:    */     }
/* 106:    */     
/* 107:    */     public MetaAttributeContext getMetaAttributeContext()
/* 108:    */     {
/* 109:145 */       return this.metaAttributeContext;
/* 110:    */     }
/* 111:    */     
/* 112:    */     public Origin getOrigin()
/* 113:    */     {
/* 114:150 */       return MappingDocument.this.hbmJaxbRoot.getOrigin();
/* 115:    */     }
/* 116:    */     
/* 117:    */     public String qualifyClassName(String unqualifiedName)
/* 118:    */     {
/* 119:155 */       return Helper.qualifyIfNeeded(unqualifiedName, getMappingDefaults().getPackageName());
/* 120:    */     }
/* 121:    */     
/* 122:    */     public String determineEntityName(EntityElement entityElement)
/* 123:    */     {
/* 124:160 */       return Helper.determineEntityName(entityElement, getMappingDefaults().getPackageName());
/* 125:    */     }
/* 126:    */     
/* 127:    */     public boolean isGloballyQuotedIdentifiers()
/* 128:    */     {
/* 129:165 */       return this.metadata.isGloballyQuotedIdentifiers();
/* 130:    */     }
/* 131:    */     
/* 132:    */     public void processFetchProfiles(List<JaxbFetchProfileElement> fetchProfiles, String containingEntityName) {}
/* 133:    */   }
/* 134:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.MappingDocument
 * JD-Core Version:    0.7.0.1
 */