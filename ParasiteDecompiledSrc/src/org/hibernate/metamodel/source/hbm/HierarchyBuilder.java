/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.hibernate.MappingException;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.hbm.EntityElement;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbClass;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbJoinedSubclassElement;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbSubclassElement;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbUnionSubclassElement;
/*  15:    */ import org.hibernate.internal.jaxb.mapping.hbm.SubEntityElement;
/*  16:    */ import org.hibernate.metamodel.source.binder.SubclassEntityContainer;
/*  17:    */ import org.hibernate.metamodel.source.binder.SubclassEntitySource;
/*  18:    */ 
/*  19:    */ public class HierarchyBuilder
/*  20:    */ {
/*  21:    */   private final List<EntityHierarchyImpl> entityHierarchies;
/*  22:    */   private final Map<String, SubclassEntityContainer> subEntityContainerMap;
/*  23:    */   private final List<ExtendsQueueEntry> extendsQueue;
/*  24:    */   private MappingDocument currentMappingDocument;
/*  25:    */   
/*  26:    */   public HierarchyBuilder()
/*  27:    */   {
/*  28: 46 */     this.entityHierarchies = new ArrayList();
/*  29:    */     
/*  30:    */ 
/*  31: 49 */     this.subEntityContainerMap = new HashMap();
/*  32: 50 */     this.extendsQueue = new ArrayList();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void processMappingDocument(MappingDocument mappingDocument)
/*  36:    */   {
/*  37: 56 */     this.currentMappingDocument = mappingDocument;
/*  38:    */     try
/*  39:    */     {
/*  40: 58 */       processCurrentMappingDocument();
/*  41:    */     }
/*  42:    */     finally
/*  43:    */     {
/*  44: 61 */       this.currentMappingDocument = null;
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   private void processCurrentMappingDocument()
/*  49:    */   {
/*  50: 66 */     for (Object entityElementO : this.currentMappingDocument.getMappingRoot().getClazzOrSubclassOrJoinedSubclass())
/*  51:    */     {
/*  52: 67 */       EntityElement entityElement = (EntityElement)entityElementO;
/*  53: 68 */       if (JaxbHibernateMapping.JaxbClass.class.isInstance(entityElement))
/*  54:    */       {
/*  55: 70 */         JaxbHibernateMapping.JaxbClass jaxbClass = (JaxbHibernateMapping.JaxbClass)entityElement;
/*  56: 71 */         RootEntitySourceImpl rootEntitySource = new RootEntitySourceImpl(this.currentMappingDocument, jaxbClass);
/*  57:    */         
/*  58:    */ 
/*  59: 74 */         EntityHierarchyImpl hierarchy = new EntityHierarchyImpl(rootEntitySource);
/*  60:    */         
/*  61: 76 */         this.entityHierarchies.add(hierarchy);
/*  62: 77 */         this.subEntityContainerMap.put(rootEntitySource.getEntityName(), rootEntitySource);
/*  63:    */         
/*  64: 79 */         processSubElements(entityElement, rootEntitySource);
/*  65:    */       }
/*  66:    */       else
/*  67:    */       {
/*  68: 84 */         SubclassEntitySourceImpl subClassEntitySource = new SubclassEntitySourceImpl(this.currentMappingDocument, entityElement);
/*  69: 85 */         String entityName = subClassEntitySource.getEntityName();
/*  70: 86 */         this.subEntityContainerMap.put(entityName, subClassEntitySource);
/*  71: 87 */         String entityItExtends = this.currentMappingDocument.getMappingLocalBindingContext().qualifyClassName(((SubEntityElement)entityElement).getExtends());
/*  72:    */         
/*  73:    */ 
/*  74: 90 */         processSubElements(entityElement, subClassEntitySource);
/*  75: 91 */         SubclassEntityContainer container = (SubclassEntityContainer)this.subEntityContainerMap.get(entityItExtends);
/*  76: 92 */         if (container != null) {
/*  77: 94 */           container.add(subClassEntitySource);
/*  78:    */         } else {
/*  79: 98 */           this.extendsQueue.add(new ExtendsQueueEntry(subClassEntitySource, entityItExtends, null));
/*  80:    */         }
/*  81:    */       }
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<EntityHierarchyImpl> groupEntityHierarchies()
/*  86:    */   {
/*  87:105 */     while (!this.extendsQueue.isEmpty())
/*  88:    */     {
/*  89:107 */       int numberOfMappingsProcessed = 0;
/*  90:108 */       Iterator<ExtendsQueueEntry> iterator = this.extendsQueue.iterator();
/*  91:109 */       while (iterator.hasNext())
/*  92:    */       {
/*  93:110 */         ExtendsQueueEntry entry = (ExtendsQueueEntry)iterator.next();
/*  94:111 */         SubclassEntityContainer container = (SubclassEntityContainer)this.subEntityContainerMap.get(entry.entityItExtends);
/*  95:112 */         if (container != null)
/*  96:    */         {
/*  97:114 */           container.add(entry.subClassEntitySource);
/*  98:115 */           iterator.remove();
/*  99:116 */           numberOfMappingsProcessed++;
/* 100:    */         }
/* 101:    */       }
/* 102:120 */       if (numberOfMappingsProcessed == 0) {
/* 103:122 */         throw new MappingException("Unable to process extends dependencies in hbm files");
/* 104:    */       }
/* 105:    */     }
/* 106:126 */     return this.entityHierarchies;
/* 107:    */   }
/* 108:    */   
/* 109:    */   private void processSubElements(EntityElement entityElement, SubclassEntityContainer container)
/* 110:    */   {
/* 111:130 */     if (JaxbHibernateMapping.JaxbClass.class.isInstance(entityElement))
/* 112:    */     {
/* 113:131 */       JaxbHibernateMapping.JaxbClass jaxbClass = (JaxbHibernateMapping.JaxbClass)entityElement;
/* 114:132 */       processElements(jaxbClass.getJoinedSubclass(), container);
/* 115:133 */       processElements(jaxbClass.getSubclass(), container);
/* 116:134 */       processElements(jaxbClass.getUnionSubclass(), container);
/* 117:    */     }
/* 118:136 */     else if (JaxbSubclassElement.class.isInstance(entityElement))
/* 119:    */     {
/* 120:137 */       JaxbSubclassElement jaxbSubclass = (JaxbSubclassElement)entityElement;
/* 121:138 */       processElements(jaxbSubclass.getSubclass(), container);
/* 122:    */     }
/* 123:140 */     else if (JaxbJoinedSubclassElement.class.isInstance(entityElement))
/* 124:    */     {
/* 125:141 */       JaxbJoinedSubclassElement jaxbJoinedSubclass = (JaxbJoinedSubclassElement)entityElement;
/* 126:142 */       processElements(jaxbJoinedSubclass.getJoinedSubclass(), container);
/* 127:    */     }
/* 128:144 */     else if (JaxbUnionSubclassElement.class.isInstance(entityElement))
/* 129:    */     {
/* 130:145 */       JaxbUnionSubclassElement jaxbUnionSubclass = (JaxbUnionSubclassElement)entityElement;
/* 131:146 */       processElements(jaxbUnionSubclass.getUnionSubclass(), container);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   private void processElements(List subElements, SubclassEntityContainer container)
/* 136:    */   {
/* 137:151 */     for (Object subElementO : subElements)
/* 138:    */     {
/* 139:152 */       SubEntityElement subElement = (SubEntityElement)subElementO;
/* 140:153 */       SubclassEntitySourceImpl subclassEntitySource = new SubclassEntitySourceImpl(this.currentMappingDocument, subElement);
/* 141:154 */       container.add(subclassEntitySource);
/* 142:155 */       String subEntityName = subclassEntitySource.getEntityName();
/* 143:156 */       this.subEntityContainerMap.put(subEntityName, subclassEntitySource);
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   private static class ExtendsQueueEntry
/* 148:    */   {
/* 149:    */     private final SubclassEntitySource subClassEntitySource;
/* 150:    */     private final String entityItExtends;
/* 151:    */     
/* 152:    */     private ExtendsQueueEntry(SubclassEntitySource subClassEntitySource, String entityItExtends)
/* 153:    */     {
/* 154:165 */       this.subClassEntitySource = subClassEntitySource;
/* 155:166 */       this.entityItExtends = entityItExtends;
/* 156:    */     }
/* 157:    */   }
/* 158:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.HierarchyBuilder
 * JD-Core Version:    0.7.0.1
 */