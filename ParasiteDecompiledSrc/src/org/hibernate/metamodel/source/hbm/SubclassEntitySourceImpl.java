/*  1:   */ package org.hibernate.metamodel.source.hbm;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.jaxb.mapping.hbm.EntityElement;
/*  4:   */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbJoinedSubclassElement;
/*  5:   */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbSubclassElement;
/*  6:   */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbUnionSubclassElement;
/*  7:   */ import org.hibernate.metamodel.source.binder.SubclassEntitySource;
/*  8:   */ import org.hibernate.metamodel.source.binder.TableSource;
/*  9:   */ 
/* 10:   */ public class SubclassEntitySourceImpl
/* 11:   */   extends AbstractEntitySourceImpl
/* 12:   */   implements SubclassEntitySource
/* 13:   */ {
/* 14:   */   protected SubclassEntitySourceImpl(MappingDocument sourceMappingDocument, EntityElement entityElement)
/* 15:   */   {
/* 16:38 */     super(sourceMappingDocument, entityElement);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public TableSource getPrimaryTable()
/* 20:   */   {
/* 21:43 */     if (JaxbJoinedSubclassElement.class.isInstance(entityElement())) {
/* 22:44 */       new TableSource()
/* 23:   */       {
/* 24:   */         public String getExplicitSchemaName()
/* 25:   */         {
/* 26:47 */           return ((JaxbJoinedSubclassElement)SubclassEntitySourceImpl.this.entityElement()).getSchema();
/* 27:   */         }
/* 28:   */         
/* 29:   */         public String getExplicitCatalogName()
/* 30:   */         {
/* 31:52 */           return ((JaxbJoinedSubclassElement)SubclassEntitySourceImpl.this.entityElement()).getCatalog();
/* 32:   */         }
/* 33:   */         
/* 34:   */         public String getExplicitTableName()
/* 35:   */         {
/* 36:57 */           return ((JaxbJoinedSubclassElement)SubclassEntitySourceImpl.this.entityElement()).getTable();
/* 37:   */         }
/* 38:   */         
/* 39:   */         public String getLogicalName()
/* 40:   */         {
/* 41:63 */           return null;
/* 42:   */         }
/* 43:   */       };
/* 44:   */     }
/* 45:67 */     if (JaxbUnionSubclassElement.class.isInstance(entityElement())) {
/* 46:68 */       new TableSource()
/* 47:   */       {
/* 48:   */         public String getExplicitSchemaName()
/* 49:   */         {
/* 50:71 */           return ((JaxbUnionSubclassElement)SubclassEntitySourceImpl.this.entityElement()).getSchema();
/* 51:   */         }
/* 52:   */         
/* 53:   */         public String getExplicitCatalogName()
/* 54:   */         {
/* 55:76 */           return ((JaxbUnionSubclassElement)SubclassEntitySourceImpl.this.entityElement()).getCatalog();
/* 56:   */         }
/* 57:   */         
/* 58:   */         public String getExplicitTableName()
/* 59:   */         {
/* 60:81 */           return ((JaxbUnionSubclassElement)SubclassEntitySourceImpl.this.entityElement()).getTable();
/* 61:   */         }
/* 62:   */         
/* 63:   */         public String getLogicalName()
/* 64:   */         {
/* 65:87 */           return null;
/* 66:   */         }
/* 67:   */       };
/* 68:   */     }
/* 69:91 */     return null;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public String getDiscriminatorMatchValue()
/* 73:   */   {
/* 74:96 */     return JaxbSubclassElement.class.isInstance(entityElement()) ? ((JaxbSubclassElement)entityElement()).getDiscriminatorValue() : null;
/* 75:   */   }
/* 76:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.SubclassEntitySourceImpl
 * JD-Core Version:    0.7.0.1
 */