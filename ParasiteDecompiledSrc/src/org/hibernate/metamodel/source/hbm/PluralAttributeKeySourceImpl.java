/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbKeyElement;
/*   5:    */ import org.hibernate.metamodel.relational.ForeignKey.ReferentialAction;
/*   6:    */ import org.hibernate.metamodel.source.binder.AttributeSourceContainer;
/*   7:    */ import org.hibernate.metamodel.source.binder.PluralAttributeKeySource;
/*   8:    */ import org.hibernate.metamodel.source.binder.RelationalValueSource;
/*   9:    */ 
/*  10:    */ public class PluralAttributeKeySourceImpl
/*  11:    */   implements PluralAttributeKeySource
/*  12:    */ {
/*  13:    */   private final JaxbKeyElement keyElement;
/*  14:    */   private final List<RelationalValueSource> valueSources;
/*  15:    */   
/*  16:    */   public PluralAttributeKeySourceImpl(final JaxbKeyElement keyElement, AttributeSourceContainer container)
/*  17:    */   {
/*  18: 45 */     this.keyElement = keyElement;
/*  19:    */     
/*  20: 47 */     this.valueSources = Helper.buildValueSources(new Helper.ValueSourcesAdapter()
/*  21:    */     {
/*  22:    */       public String getContainingTableName()
/*  23:    */       {
/*  24: 51 */         return null;
/*  25:    */       }
/*  26:    */       
/*  27:    */       public boolean isIncludedInInsertByDefault()
/*  28:    */       {
/*  29: 56 */         return true;
/*  30:    */       }
/*  31:    */       
/*  32:    */       public boolean isIncludedInUpdateByDefault()
/*  33:    */       {
/*  34: 61 */         return Helper.getBooleanValue(keyElement.isUpdate(), true);
/*  35:    */       }
/*  36:    */       
/*  37:    */       public String getColumnAttribute()
/*  38:    */       {
/*  39: 66 */         return keyElement.getColumnAttribute();
/*  40:    */       }
/*  41:    */       
/*  42:    */       public String getFormulaAttribute()
/*  43:    */       {
/*  44: 71 */         return null;
/*  45:    */       }
/*  46:    */       
/*  47:    */       public List getColumnOrFormulaElements()
/*  48:    */       {
/*  49: 76 */         return keyElement.getColumn();
/*  50:    */       }
/*  51: 76 */     }, container.getLocalBindingContext());
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<RelationalValueSource> getValueSources()
/*  55:    */   {
/*  56: 85 */     return this.valueSources;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getExplicitForeignKeyName()
/*  60:    */   {
/*  61: 90 */     return this.keyElement.getForeignKey();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public ForeignKey.ReferentialAction getOnDeleteAction()
/*  65:    */   {
/*  66: 95 */     return "cascade".equals(this.keyElement.getOnDelete()) ? ForeignKey.ReferentialAction.CASCADE : ForeignKey.ReferentialAction.NO_ACTION;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getReferencedEntityAttributeName()
/*  70:    */   {
/*  71:102 */     return this.keyElement.getPropertyRef();
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.PluralAttributeKeySourceImpl
 * JD-Core Version:    0.7.0.1
 */