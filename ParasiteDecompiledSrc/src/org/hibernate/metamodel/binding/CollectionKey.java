/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import org.hibernate.AssertionFailure;
/*  4:   */ import org.hibernate.metamodel.relational.ForeignKey;
/*  5:   */ import org.hibernate.metamodel.relational.TableSpecification;
/*  6:   */ 
/*  7:   */ public class CollectionKey
/*  8:   */ {
/*  9:   */   private final AbstractPluralAttributeBinding pluralAttributeBinding;
/* 10:   */   private ForeignKey foreignKey;
/* 11:   */   private boolean inverse;
/* 12:   */   private HibernateTypeDescriptor hibernateTypeDescriptor;
/* 13:   */   
/* 14:   */   public CollectionKey(AbstractPluralAttributeBinding pluralAttributeBinding)
/* 15:   */   {
/* 16:46 */     this.pluralAttributeBinding = pluralAttributeBinding;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public AbstractPluralAttributeBinding getPluralAttributeBinding()
/* 20:   */   {
/* 21:50 */     return this.pluralAttributeBinding;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void prepareForeignKey(String foreignKeyName, String targetTableName)
/* 25:   */   {
/* 26:54 */     if (this.foreignKey != null) {
/* 27:55 */       throw new AssertionFailure("Foreign key already initialized");
/* 28:   */     }
/* 29:57 */     TableSpecification collectionTable = this.pluralAttributeBinding.getCollectionTable();
/* 30:58 */     if (collectionTable == null) {
/* 31:59 */       throw new AssertionFailure("Collection table not yet bound");
/* 32:   */     }
/* 33:62 */     TableSpecification targetTable = this.pluralAttributeBinding.getContainer().seekEntityBinding().locateTable(targetTableName);
/* 34:   */     
/* 35:   */ 
/* 36:   */ 
/* 37:   */ 
/* 38:   */ 
/* 39:68 */     this.foreignKey = collectionTable.createForeignKey(targetTable, foreignKeyName);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public ForeignKey getForeignKey()
/* 43:   */   {
/* 44:72 */     return this.foreignKey;
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.CollectionKey
 * JD-Core Version:    0.7.0.1
 */