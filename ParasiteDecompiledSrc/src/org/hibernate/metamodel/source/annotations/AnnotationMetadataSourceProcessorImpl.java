/*   1:    */ package org.hibernate.metamodel.source.annotations;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.hibernate.AssertionFailure;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.internal.jaxb.JaxbRoot;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntityMappings;
/*  12:    */ import org.hibernate.metamodel.MetadataSources;
/*  13:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  14:    */ import org.hibernate.metamodel.source.MetadataSourceProcessor;
/*  15:    */ import org.hibernate.metamodel.source.annotations.global.FetchProfileBinder;
/*  16:    */ import org.hibernate.metamodel.source.annotations.global.FilterDefBinder;
/*  17:    */ import org.hibernate.metamodel.source.annotations.global.IdGeneratorBinder;
/*  18:    */ import org.hibernate.metamodel.source.annotations.global.QueryBinder;
/*  19:    */ import org.hibernate.metamodel.source.annotations.global.TableBinder;
/*  20:    */ import org.hibernate.metamodel.source.annotations.global.TypeDefBinder;
/*  21:    */ import org.hibernate.metamodel.source.annotations.xml.PseudoJpaDotNames;
/*  22:    */ import org.hibernate.metamodel.source.annotations.xml.mocker.EntityMappingsMocker;
/*  23:    */ import org.hibernate.metamodel.source.binder.Binder;
/*  24:    */ import org.hibernate.metamodel.source.binder.EntityHierarchy;
/*  25:    */ import org.hibernate.metamodel.source.internal.MetadataImpl;
/*  26:    */ import org.hibernate.service.ServiceRegistry;
/*  27:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  28:    */ import org.jboss.jandex.Index;
/*  29:    */ import org.jboss.jandex.Indexer;
/*  30:    */ import org.jboss.logging.Logger;
/*  31:    */ 
/*  32:    */ public class AnnotationMetadataSourceProcessorImpl
/*  33:    */   implements MetadataSourceProcessor
/*  34:    */ {
/*  35: 65 */   private static final Logger LOG = Logger.getLogger(AnnotationMetadataSourceProcessorImpl.class);
/*  36:    */   private final MetadataImplementor metadata;
/*  37:    */   private AnnotationBindingContext bindingContext;
/*  38:    */   
/*  39:    */   public AnnotationMetadataSourceProcessorImpl(MetadataImpl metadata)
/*  40:    */   {
/*  41: 71 */     this.metadata = metadata;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void prepare(MetadataSources sources)
/*  45:    */   {
/*  46: 78 */     Indexer indexer = new Indexer();
/*  47: 79 */     for (Class<?> clazz : sources.getAnnotatedClasses()) {
/*  48: 80 */       indexClass(indexer, clazz.getName().replace('.', '/') + ".class");
/*  49:    */     }
/*  50: 84 */     for (String packageName : sources.getAnnotatedPackages()) {
/*  51: 85 */       indexClass(indexer, packageName.replace('.', '/') + "/package-info.class");
/*  52:    */     }
/*  53: 88 */     Index index = indexer.complete();
/*  54:    */     
/*  55: 90 */     List<JaxbRoot<JaxbEntityMappings>> mappings = new ArrayList();
/*  56: 91 */     for (JaxbRoot<?> root : sources.getJaxbRootList()) {
/*  57: 92 */       if ((root.getRoot() instanceof JaxbEntityMappings)) {
/*  58: 93 */         mappings.add(root);
/*  59:    */       }
/*  60:    */     }
/*  61: 96 */     if (!mappings.isEmpty()) {
/*  62: 97 */       index = parseAndUpdateIndex(mappings, index);
/*  63:    */     }
/*  64:100 */     if (index.getAnnotations(PseudoJpaDotNames.DEFAULT_DELIMITED_IDENTIFIERS) != null) {
/*  65:103 */       this.metadata.setGloballyQuotedIdentifiers(true);
/*  66:    */     }
/*  67:105 */     this.bindingContext = new AnnotationBindingContextImpl(this.metadata, index);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void processIndependentMetadata(MetadataSources sources)
/*  71:    */   {
/*  72:110 */     assertBindingContextExists();
/*  73:111 */     TypeDefBinder.bind(this.bindingContext);
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void assertBindingContextExists()
/*  77:    */   {
/*  78:115 */     if (this.bindingContext == null) {
/*  79:116 */       throw new AssertionFailure("The binding context should exist. Has prepare been called!?");
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void processTypeDependentMetadata(MetadataSources sources)
/*  84:    */   {
/*  85:122 */     assertBindingContextExists();
/*  86:123 */     IdGeneratorBinder.bind(this.bindingContext);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void processMappingMetadata(MetadataSources sources, List<String> processedEntityNames)
/*  90:    */   {
/*  91:128 */     assertBindingContextExists();
/*  92:    */     
/*  93:130 */     Set<EntityHierarchy> hierarchies = EntityHierarchyBuilder.createEntityHierarchies(this.bindingContext);
/*  94:    */     
/*  95:132 */     Binder binder = new Binder(this.bindingContext.getMetadataImplementor(), new ArrayList());
/*  96:133 */     for (EntityHierarchy hierarchy : hierarchies) {
/*  97:134 */       binder.processEntityHierarchy(hierarchy);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void processMappingDependentMetadata(MetadataSources sources)
/* 102:    */   {
/* 103:140 */     TableBinder.bind(this.bindingContext);
/* 104:141 */     FetchProfileBinder.bind(this.bindingContext);
/* 105:142 */     QueryBinder.bind(this.bindingContext);
/* 106:143 */     FilterDefBinder.bind(this.bindingContext);
/* 107:    */   }
/* 108:    */   
/* 109:    */   private Index parseAndUpdateIndex(List<JaxbRoot<JaxbEntityMappings>> mappings, Index annotationIndex)
/* 110:    */   {
/* 111:147 */     List<JaxbEntityMappings> list = new ArrayList(mappings.size());
/* 112:148 */     for (JaxbRoot<JaxbEntityMappings> jaxbRoot : mappings) {
/* 113:149 */       list.add(jaxbRoot.getRoot());
/* 114:    */     }
/* 115:151 */     return new EntityMappingsMocker(list, annotationIndex, this.metadata.getServiceRegistry()).mockNewIndex();
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void indexClass(Indexer indexer, String className)
/* 119:    */   {
/* 120:155 */     InputStream stream = ((ClassLoaderService)this.metadata.getServiceRegistry().getService(ClassLoaderService.class)).locateResourceStream(className);
/* 121:    */     try
/* 122:    */     {
/* 123:159 */       indexer.index(stream);
/* 124:    */     }
/* 125:    */     catch (IOException e)
/* 126:    */     {
/* 127:162 */       throw new HibernateException("Unable to open input stream for class " + className, e);
/* 128:    */     }
/* 129:    */   }
/* 130:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.AnnotationMetadataSourceProcessorImpl
 * JD-Core Version:    0.7.0.1
 */