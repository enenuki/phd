/*  1:   */ package org.hibernate.metamodel.source.hbm;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.internal.jaxb.JaxbRoot;
/*  6:   */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping;
/*  7:   */ import org.hibernate.metamodel.MetadataSources;
/*  8:   */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  9:   */ import org.hibernate.metamodel.source.MetadataSourceProcessor;
/* 10:   */ import org.hibernate.metamodel.source.binder.Binder;
/* 11:   */ 
/* 12:   */ public class HbmMetadataSourceProcessorImpl
/* 13:   */   implements MetadataSourceProcessor
/* 14:   */ {
/* 15:   */   private final MetadataImplementor metadata;
/* 16:44 */   private List<HibernateMappingProcessor> processors = new ArrayList();
/* 17:   */   private List<EntityHierarchyImpl> entityHierarchies;
/* 18:   */   
/* 19:   */   public HbmMetadataSourceProcessorImpl(MetadataImplementor metadata)
/* 20:   */   {
/* 21:48 */     this.metadata = metadata;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void prepare(MetadataSources sources)
/* 25:   */   {
/* 26:54 */     HierarchyBuilder hierarchyBuilder = new HierarchyBuilder();
/* 27:56 */     for (JaxbRoot jaxbRoot : sources.getJaxbRootList()) {
/* 28:57 */       if (JaxbHibernateMapping.class.isInstance(jaxbRoot.getRoot()))
/* 29:   */       {
/* 30:61 */         MappingDocument mappingDocument = new MappingDocument(jaxbRoot, this.metadata);
/* 31:62 */         this.processors.add(new HibernateMappingProcessor(this.metadata, mappingDocument));
/* 32:   */         
/* 33:64 */         hierarchyBuilder.processMappingDocument(mappingDocument);
/* 34:   */       }
/* 35:   */     }
/* 36:67 */     this.entityHierarchies = hierarchyBuilder.groupEntityHierarchies();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void processIndependentMetadata(MetadataSources sources)
/* 40:   */   {
/* 41:72 */     for (HibernateMappingProcessor processor : this.processors) {
/* 42:73 */       processor.processIndependentMetadata();
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void processTypeDependentMetadata(MetadataSources sources)
/* 47:   */   {
/* 48:79 */     for (HibernateMappingProcessor processor : this.processors) {
/* 49:80 */       processor.processTypeDependentMetadata();
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void processMappingMetadata(MetadataSources sources, List<String> processedEntityNames)
/* 54:   */   {
/* 55:86 */     Binder binder = new Binder(this.metadata, processedEntityNames);
/* 56:87 */     for (EntityHierarchyImpl entityHierarchy : this.entityHierarchies) {
/* 57:88 */       binder.processEntityHierarchy(entityHierarchy);
/* 58:   */     }
/* 59:   */   }
/* 60:   */   
/* 61:   */   public void processMappingDependentMetadata(MetadataSources sources)
/* 62:   */   {
/* 63:94 */     for (HibernateMappingProcessor processor : this.processors) {
/* 64:95 */       processor.processMappingDependentMetadata();
/* 65:   */     }
/* 66:   */   }
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.HbmMetadataSourceProcessorImpl
 * JD-Core Version:    0.7.0.1
 */