/*   1:    */ package org.hibernate.cfg.annotations;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.hibernate.AnnotationException;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.annotations.OrderBy;
/*   7:    */ import org.hibernate.annotations.Sort;
/*   8:    */ import org.hibernate.annotations.common.reflection.XClass;
/*   9:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  10:    */ import org.hibernate.cfg.CollectionSecondPass;
/*  11:    */ import org.hibernate.cfg.Ejb3Column;
/*  12:    */ import org.hibernate.cfg.Ejb3JoinColumn;
/*  13:    */ import org.hibernate.cfg.IndexColumn;
/*  14:    */ import org.hibernate.cfg.Mappings;
/*  15:    */ import org.hibernate.cfg.PropertyHolder;
/*  16:    */ import org.hibernate.cfg.PropertyHolderBuilder;
/*  17:    */ import org.hibernate.cfg.SecondPass;
/*  18:    */ import org.hibernate.internal.CoreMessageLogger;
/*  19:    */ import org.hibernate.internal.util.StringHelper;
/*  20:    */ import org.hibernate.mapping.Collection;
/*  21:    */ import org.hibernate.mapping.IndexBackref;
/*  22:    */ import org.hibernate.mapping.KeyValue;
/*  23:    */ import org.hibernate.mapping.List;
/*  24:    */ import org.hibernate.mapping.OneToMany;
/*  25:    */ import org.hibernate.mapping.PersistentClass;
/*  26:    */ import org.hibernate.mapping.SimpleValue;
/*  27:    */ import org.jboss.logging.Logger;
/*  28:    */ 
/*  29:    */ public class ListBinder
/*  30:    */   extends CollectionBinder
/*  31:    */ {
/*  32: 60 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ListBinder.class.getName());
/*  33:    */   
/*  34:    */   protected Collection createCollection(PersistentClass persistentClass)
/*  35:    */   {
/*  36: 67 */     return new List(getMappings(), persistentClass);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setSqlOrderBy(OrderBy orderByAnn)
/*  40:    */   {
/*  41: 72 */     if (orderByAnn != null) {
/*  42: 73 */       LOG.orderByAnnotationIndexedCollection();
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setSort(Sort sortAnn)
/*  47:    */   {
/*  48: 78 */     if (sortAnn != null) {
/*  49: 79 */       LOG.sortAnnotationIndexedCollection();
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public SecondPass getSecondPass(final Ejb3JoinColumn[] fkJoinColumns, final Ejb3JoinColumn[] keyColumns, final Ejb3JoinColumn[] inverseColumns, final Ejb3Column[] elementColumns, Ejb3Column[] mapKeyColumns, Ejb3JoinColumn[] mapKeyManyToManyColumns, final boolean isEmbedded, final XProperty property, final XClass collType, final boolean ignoreNotFound, final boolean unique, final TableBinder assocTableBinder, final Mappings mappings)
/*  54:    */   {
/*  55: 97 */     new CollectionSecondPass(mappings, this.collection)
/*  56:    */     {
/*  57:    */       public void secondPass(Map persistentClasses, Map inheritedMetas)
/*  58:    */         throws MappingException
/*  59:    */       {
/*  60:101 */         ListBinder.this.bindStarToManySecondPass(persistentClasses, collType, fkJoinColumns, keyColumns, inverseColumns, elementColumns, isEmbedded, property, unique, assocTableBinder, ignoreNotFound, mappings);
/*  61:    */         
/*  62:    */ 
/*  63:    */ 
/*  64:105 */         ListBinder.this.bindIndex(mappings);
/*  65:    */       }
/*  66:    */     };
/*  67:    */   }
/*  68:    */   
/*  69:    */   private void bindIndex(Mappings mappings)
/*  70:    */   {
/*  71:111 */     if (!this.indexColumn.isImplicit())
/*  72:    */     {
/*  73:112 */       PropertyHolder valueHolder = PropertyHolderBuilder.buildPropertyHolder(this.collection, StringHelper.qualify(this.collection.getRole(), "key"), (XClass)null, (XProperty)null, this.propertyHolder, mappings);
/*  74:    */       
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:118 */       List list = (List)this.collection;
/*  80:119 */       if (!list.isOneToMany()) {
/*  81:119 */         this.indexColumn.forceNotNull();
/*  82:    */       }
/*  83:120 */       this.indexColumn.setPropertyHolder(valueHolder);
/*  84:121 */       SimpleValueBinder value = new SimpleValueBinder();
/*  85:122 */       value.setColumns(new Ejb3Column[] { this.indexColumn });
/*  86:123 */       value.setExplicitType("integer");
/*  87:124 */       value.setMappings(mappings);
/*  88:125 */       SimpleValue indexValue = value.make();
/*  89:126 */       this.indexColumn.linkWithValue(indexValue);
/*  90:127 */       list.setIndex(indexValue);
/*  91:128 */       list.setBaseIndex(this.indexColumn.getBase());
/*  92:129 */       if ((list.isOneToMany()) && (!list.getKey().isNullable()) && (!list.isInverse()))
/*  93:    */       {
/*  94:130 */         String entityName = ((OneToMany)list.getElement()).getReferencedEntityName();
/*  95:131 */         PersistentClass referenced = mappings.getClass(entityName);
/*  96:132 */         IndexBackref ib = new IndexBackref();
/*  97:133 */         ib.setName('_' + this.propertyName + "IndexBackref");
/*  98:134 */         ib.setUpdateable(false);
/*  99:135 */         ib.setSelectable(false);
/* 100:136 */         ib.setCollectionRole(list.getRole());
/* 101:137 */         ib.setEntityName(list.getOwner().getEntityName());
/* 102:138 */         ib.setValue(list.getIndex());
/* 103:139 */         referenced.addProperty(ib);
/* 104:    */       }
/* 105:    */     }
/* 106:    */     else
/* 107:    */     {
/* 108:143 */       Collection coll = this.collection;
/* 109:144 */       throw new AnnotationException("List/array has to be annotated with an @OrderColumn (or @IndexColumn): " + coll.getRole());
/* 110:    */     }
/* 111:    */   }
/* 112:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.ListBinder
 * JD-Core Version:    0.7.0.1
 */