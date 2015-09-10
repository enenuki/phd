/*   1:    */ package org.hibernate.persister.internal;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import org.hibernate.mapping.Collection;
/*   5:    */ import org.hibernate.mapping.JoinedSubclass;
/*   6:    */ import org.hibernate.mapping.PersistentClass;
/*   7:    */ import org.hibernate.mapping.RootClass;
/*   8:    */ import org.hibernate.mapping.SingleTableSubclass;
/*   9:    */ import org.hibernate.mapping.UnionSubclass;
/*  10:    */ import org.hibernate.metamodel.binding.AbstractCollectionElement;
/*  11:    */ import org.hibernate.metamodel.binding.CollectionElementNature;
/*  12:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  13:    */ import org.hibernate.metamodel.binding.PluralAttributeBinding;
/*  14:    */ import org.hibernate.metamodel.domain.Entity;
/*  15:    */ import org.hibernate.persister.collection.BasicCollectionPersister;
/*  16:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  17:    */ import org.hibernate.persister.collection.OneToManyPersister;
/*  18:    */ import org.hibernate.persister.entity.EntityPersister;
/*  19:    */ import org.hibernate.persister.entity.JoinedSubclassEntityPersister;
/*  20:    */ import org.hibernate.persister.entity.SingleTableEntityPersister;
/*  21:    */ import org.hibernate.persister.entity.UnionSubclassEntityPersister;
/*  22:    */ import org.hibernate.persister.spi.PersisterClassResolver;
/*  23:    */ import org.hibernate.persister.spi.UnknownPersisterException;
/*  24:    */ 
/*  25:    */ public class StandardPersisterClassResolver
/*  26:    */   implements PersisterClassResolver
/*  27:    */ {
/*  28:    */   public Class<? extends EntityPersister> getEntityPersisterClass(EntityBinding metadata)
/*  29:    */   {
/*  30: 53 */     if (metadata.isRoot())
/*  31:    */     {
/*  32: 54 */       Iterator<EntityBinding> subEntityBindingIterator = metadata.getDirectSubEntityBindings().iterator();
/*  33: 55 */       if (subEntityBindingIterator.hasNext()) {
/*  34: 57 */         metadata = (EntityBinding)subEntityBindingIterator.next();
/*  35:    */       } else {
/*  36: 60 */         return singleTableEntityPersister();
/*  37:    */       }
/*  38:    */     }
/*  39: 63 */     switch (1.$SwitchMap$org$hibernate$metamodel$binding$InheritanceType[metadata.getHierarchyDetails().getInheritanceType().ordinal()])
/*  40:    */     {
/*  41:    */     case 1: 
/*  42: 65 */       return joinedSubclassEntityPersister();
/*  43:    */     case 2: 
/*  44: 68 */       return singleTableEntityPersister();
/*  45:    */     case 3: 
/*  46: 71 */       return unionSubclassEntityPersister();
/*  47:    */     }
/*  48: 74 */     throw new UnknownPersisterException("Could not determine persister implementation for entity [" + metadata.getEntity().getName() + "]");
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Class<? extends EntityPersister> getEntityPersisterClass(PersistentClass metadata)
/*  52:    */   {
/*  53: 85 */     if (RootClass.class.isInstance(metadata)) {
/*  54: 86 */       if (metadata.hasSubclasses()) {
/*  55: 88 */         metadata = (PersistentClass)metadata.getDirectSubclasses().next();
/*  56:    */       } else {
/*  57: 91 */         return singleTableEntityPersister();
/*  58:    */       }
/*  59:    */     }
/*  60: 94 */     if (JoinedSubclass.class.isInstance(metadata)) {
/*  61: 95 */       return joinedSubclassEntityPersister();
/*  62:    */     }
/*  63: 97 */     if (UnionSubclass.class.isInstance(metadata)) {
/*  64: 98 */       return unionSubclassEntityPersister();
/*  65:    */     }
/*  66:100 */     if (SingleTableSubclass.class.isInstance(metadata)) {
/*  67:101 */       return singleTableEntityPersister();
/*  68:    */     }
/*  69:104 */     throw new UnknownPersisterException("Could not determine persister implementation for entity [" + metadata.getEntityName() + "]");
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Class<? extends EntityPersister> singleTableEntityPersister()
/*  73:    */   {
/*  74:111 */     return SingleTableEntityPersister.class;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Class<? extends EntityPersister> joinedSubclassEntityPersister()
/*  78:    */   {
/*  79:115 */     return JoinedSubclassEntityPersister.class;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Class<? extends EntityPersister> unionSubclassEntityPersister()
/*  83:    */   {
/*  84:119 */     return UnionSubclassEntityPersister.class;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Class<? extends CollectionPersister> getCollectionPersisterClass(Collection metadata)
/*  88:    */   {
/*  89:124 */     return metadata.isOneToMany() ? oneToManyPersister() : basicCollectionPersister();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Class<? extends CollectionPersister> getCollectionPersisterClass(PluralAttributeBinding metadata)
/*  93:    */   {
/*  94:129 */     return metadata.getCollectionElement().getCollectionElementNature() == CollectionElementNature.ONE_TO_MANY ? oneToManyPersister() : basicCollectionPersister();
/*  95:    */   }
/*  96:    */   
/*  97:    */   private Class<OneToManyPersister> oneToManyPersister()
/*  98:    */   {
/*  99:135 */     return OneToManyPersister.class;
/* 100:    */   }
/* 101:    */   
/* 102:    */   private Class<BasicCollectionPersister> basicCollectionPersister()
/* 103:    */   {
/* 104:139 */     return BasicCollectionPersister.class;
/* 105:    */   }
/* 106:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.internal.StandardPersisterClassResolver
 * JD-Core Version:    0.7.0.1
 */