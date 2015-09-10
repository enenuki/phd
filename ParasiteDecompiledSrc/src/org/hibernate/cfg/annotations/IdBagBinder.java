/*   1:    */ package org.hibernate.cfg.annotations;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.AnnotationException;
/*   6:    */ import org.hibernate.annotations.CollectionId;
/*   7:    */ import org.hibernate.annotations.Type;
/*   8:    */ import org.hibernate.annotations.common.reflection.XClass;
/*   9:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  10:    */ import org.hibernate.cfg.BinderHelper;
/*  11:    */ import org.hibernate.cfg.Ejb3Column;
/*  12:    */ import org.hibernate.cfg.Ejb3JoinColumn;
/*  13:    */ import org.hibernate.cfg.Mappings;
/*  14:    */ import org.hibernate.cfg.PropertyData;
/*  15:    */ import org.hibernate.cfg.PropertyHolder;
/*  16:    */ import org.hibernate.cfg.PropertyInferredData;
/*  17:    */ import org.hibernate.cfg.WrappedInferredData;
/*  18:    */ import org.hibernate.internal.util.StringHelper;
/*  19:    */ import org.hibernate.mapping.Collection;
/*  20:    */ import org.hibernate.mapping.IdentifierBag;
/*  21:    */ import org.hibernate.mapping.IdentifierCollection;
/*  22:    */ import org.hibernate.mapping.PersistentClass;
/*  23:    */ import org.hibernate.mapping.SimpleValue;
/*  24:    */ import org.hibernate.mapping.Table;
/*  25:    */ 
/*  26:    */ public class IdBagBinder
/*  27:    */   extends BagBinder
/*  28:    */ {
/*  29:    */   protected Collection createCollection(PersistentClass persistentClass)
/*  30:    */   {
/*  31: 52 */     return new IdentifierBag(getMappings(), persistentClass);
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected boolean bindStarToManySecondPass(Map persistentClasses, XClass collType, Ejb3JoinColumn[] fkJoinColumns, Ejb3JoinColumn[] keyColumns, Ejb3JoinColumn[] inverseColumns, Ejb3Column[] elementColumns, boolean isEmbedded, XProperty property, boolean unique, TableBinder associationTableBinder, boolean ignoreNotFound, Mappings mappings)
/*  35:    */   {
/*  36: 69 */     boolean result = super.bindStarToManySecondPass(persistentClasses, collType, fkJoinColumns, keyColumns, inverseColumns, elementColumns, isEmbedded, property, unique, associationTableBinder, ignoreNotFound, mappings);
/*  37:    */     
/*  38:    */ 
/*  39:    */ 
/*  40: 73 */     CollectionId collectionIdAnn = (CollectionId)property.getAnnotation(CollectionId.class);
/*  41: 74 */     if (collectionIdAnn != null)
/*  42:    */     {
/*  43: 75 */       SimpleValueBinder simpleValue = new SimpleValueBinder();
/*  44:    */       
/*  45: 77 */       PropertyData propertyData = new WrappedInferredData(new PropertyInferredData(null, property, null, mappings.getReflectionManager()), "id");
/*  46:    */       
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54: 86 */       Ejb3Column[] idColumns = Ejb3Column.buildColumnFromAnnotation(collectionIdAnn.columns(), null, Nullability.FORCED_NOT_NULL, this.propertyHolder, propertyData, Collections.EMPTY_MAP, mappings);
/*  55: 96 */       for (Ejb3Column idColumn : idColumns) {
/*  56: 97 */         idColumn.setNullable(false);
/*  57:    */       }
/*  58: 99 */       Table table = this.collection.getCollectionTable();
/*  59:100 */       simpleValue.setTable(table);
/*  60:101 */       simpleValue.setColumns(idColumns);
/*  61:102 */       Type typeAnn = collectionIdAnn.type();
/*  62:103 */       if ((typeAnn != null) && (!BinderHelper.isEmptyAnnotationValue(typeAnn.type()))) {
/*  63:104 */         simpleValue.setExplicitType(typeAnn);
/*  64:    */       } else {
/*  65:107 */         throw new AnnotationException("@CollectionId is missing type: " + StringHelper.qualify(this.propertyHolder.getPath(), this.propertyName));
/*  66:    */       }
/*  67:110 */       simpleValue.setMappings(mappings);
/*  68:111 */       SimpleValue id = simpleValue.make();
/*  69:112 */       ((IdentifierCollection)this.collection).setIdentifier(id);
/*  70:113 */       String generator = collectionIdAnn.generator();
/*  71:    */       String generatorType;
/*  72:115 */       if (("identity".equals(generator)) || ("assigned".equals(generator)) || ("sequence".equals(generator)) || ("native".equals(generator)))
/*  73:    */       {
/*  74:117 */         String generatorType = generator;
/*  75:118 */         generator = "";
/*  76:    */       }
/*  77:    */       else
/*  78:    */       {
/*  79:121 */         generatorType = null;
/*  80:    */       }
/*  81:123 */       BinderHelper.makeIdGenerator(id, generatorType, generator, mappings, this.localGenerators);
/*  82:    */     }
/*  83:125 */     return result;
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.IdBagBinder
 * JD-Core Version:    0.7.0.1
 */