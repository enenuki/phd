/*   1:    */ package org.hibernate.metamodel.source.annotations.entity;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.EntityMode;
/*   6:    */ import org.hibernate.cfg.NotYetImplementedException;
/*   7:    */ import org.hibernate.engine.OptimisticLockStyle;
/*   8:    */ import org.hibernate.metamodel.binding.Caching;
/*   9:    */ import org.hibernate.metamodel.source.annotations.attribute.BasicAttribute;
/*  10:    */ import org.hibernate.metamodel.source.annotations.attribute.DiscriminatorSourceImpl;
/*  11:    */ import org.hibernate.metamodel.source.annotations.attribute.SimpleIdentifierSourceImpl;
/*  12:    */ import org.hibernate.metamodel.source.annotations.attribute.SingularAttributeSourceImpl;
/*  13:    */ import org.hibernate.metamodel.source.binder.DiscriminatorSource;
/*  14:    */ import org.hibernate.metamodel.source.binder.IdentifierSource;
/*  15:    */ import org.hibernate.metamodel.source.binder.RootEntitySource;
/*  16:    */ import org.hibernate.metamodel.source.binder.SingularAttributeSource;
/*  17:    */ 
/*  18:    */ public class RootEntitySourceImpl
/*  19:    */   extends EntitySourceImpl
/*  20:    */   implements RootEntitySource
/*  21:    */ {
/*  22:    */   public RootEntitySourceImpl(EntityClass entityClass)
/*  23:    */   {
/*  24: 45 */     super(entityClass);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public IdentifierSource getIdentifierSource()
/*  28:    */   {
/*  29: 50 */     IdType idType = getEntityClass().getIdType();
/*  30: 51 */     switch (1.$SwitchMap$org$hibernate$metamodel$source$annotations$entity$IdType[idType.ordinal()])
/*  31:    */     {
/*  32:    */     case 1: 
/*  33: 53 */       BasicAttribute attribute = (BasicAttribute)getEntityClass().getIdAttributes().iterator().next();
/*  34: 54 */       return new SimpleIdentifierSourceImpl(attribute, getEntityClass().getAttributeOverrideMap());
/*  35:    */     case 2: 
/*  36: 57 */       throw new NotYetImplementedException("Composed ids must still be implemented.");
/*  37:    */     case 3: 
/*  38: 60 */       throw new NotYetImplementedException("Embedded ids must still be implemented.");
/*  39:    */     }
/*  40: 63 */     throw new AssertionFailure("The root entity needs to specify an identifier");
/*  41:    */   }
/*  42:    */   
/*  43:    */   public SingularAttributeSource getVersioningAttributeSource()
/*  44:    */   {
/*  45: 70 */     SingularAttributeSource attributeSource = null;
/*  46: 71 */     EntityClass entityClass = getEntityClass();
/*  47: 72 */     if (entityClass.getVersionAttribute() != null) {
/*  48: 73 */       attributeSource = new SingularAttributeSourceImpl(entityClass.getVersionAttribute());
/*  49:    */     }
/*  50: 75 */     return attributeSource;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public DiscriminatorSource getDiscriminatorSource()
/*  54:    */   {
/*  55: 80 */     DiscriminatorSource discriminatorSource = null;
/*  56: 81 */     if (getEntityClass().getDiscriminatorColumnValues() != null) {
/*  57: 82 */       discriminatorSource = new DiscriminatorSourceImpl(getEntityClass());
/*  58:    */     }
/*  59: 84 */     return discriminatorSource;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public EntityMode getEntityMode()
/*  63:    */   {
/*  64: 89 */     return EntityMode.POJO;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean isMutable()
/*  68:    */   {
/*  69: 94 */     return getEntityClass().isMutable();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean isExplicitPolymorphism()
/*  73:    */   {
/*  74: 99 */     return getEntityClass().isExplicitPolymorphism();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getWhere()
/*  78:    */   {
/*  79:104 */     return getEntityClass().getWhereClause();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getRowId()
/*  83:    */   {
/*  84:109 */     return getEntityClass().getRowId();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public OptimisticLockStyle getOptimisticLockStyle()
/*  88:    */   {
/*  89:114 */     return getEntityClass().getOptimisticLockStyle();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Caching getCaching()
/*  93:    */   {
/*  94:119 */     return getEntityClass().getCaching();
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.entity.RootEntitySourceImpl
 * JD-Core Version:    0.7.0.1
 */