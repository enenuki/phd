/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbElementElement;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbTypeElement;
/*   8:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*   9:    */ import org.hibernate.metamodel.source.binder.BasicPluralAttributeElementSource;
/*  10:    */ import org.hibernate.metamodel.source.binder.ExplicitHibernateTypeSource;
/*  11:    */ import org.hibernate.metamodel.source.binder.PluralAttributeElementNature;
/*  12:    */ import org.hibernate.metamodel.source.binder.RelationalValueSource;
/*  13:    */ 
/*  14:    */ public class BasicPluralAttributeElementSourceImpl
/*  15:    */   implements BasicPluralAttributeElementSource
/*  16:    */ {
/*  17:    */   private final List<RelationalValueSource> valueSources;
/*  18:    */   private final ExplicitHibernateTypeSource typeSource;
/*  19:    */   
/*  20:    */   public BasicPluralAttributeElementSourceImpl(final JaxbElementElement elementElement, LocalBindingContext bindingContext)
/*  21:    */   {
/*  22: 46 */     this.valueSources = Helper.buildValueSources(new Helper.ValueSourcesAdapter()
/*  23:    */     {
/*  24:    */       public String getContainingTableName()
/*  25:    */       {
/*  26: 50 */         return null;
/*  27:    */       }
/*  28:    */       
/*  29:    */       public boolean isIncludedInInsertByDefault()
/*  30:    */       {
/*  31: 55 */         return true;
/*  32:    */       }
/*  33:    */       
/*  34:    */       public boolean isIncludedInUpdateByDefault()
/*  35:    */       {
/*  36: 60 */         return true;
/*  37:    */       }
/*  38:    */       
/*  39:    */       public String getColumnAttribute()
/*  40:    */       {
/*  41: 65 */         return elementElement.getColumn();
/*  42:    */       }
/*  43:    */       
/*  44:    */       public String getFormulaAttribute()
/*  45:    */       {
/*  46: 70 */         return elementElement.getFormula();
/*  47:    */       }
/*  48:    */       
/*  49:    */       public List getColumnOrFormulaElements()
/*  50:    */       {
/*  51: 75 */         return elementElement.getColumnOrFormula();
/*  52:    */       }
/*  53: 75 */     }, bindingContext);
/*  54:    */     
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59: 81 */     this.typeSource = new ExplicitHibernateTypeSource()
/*  60:    */     {
/*  61:    */       public String getName()
/*  62:    */       {
/*  63: 84 */         if (elementElement.getTypeAttribute() != null) {
/*  64: 85 */           return elementElement.getTypeAttribute();
/*  65:    */         }
/*  66: 87 */         if (elementElement.getType() != null) {
/*  67: 88 */           return elementElement.getType().getName();
/*  68:    */         }
/*  69: 91 */         return null;
/*  70:    */       }
/*  71:    */       
/*  72:    */       public Map<String, String> getParameters()
/*  73:    */       {
/*  74: 97 */         return elementElement.getType() != null ? Helper.extractParameters(elementElement.getType().getParam()) : Collections.emptyMap();
/*  75:    */       }
/*  76:    */     };
/*  77:    */   }
/*  78:    */   
/*  79:    */   public PluralAttributeElementNature getNature()
/*  80:    */   {
/*  81:106 */     return PluralAttributeElementNature.BASIC;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<RelationalValueSource> getValueSources()
/*  85:    */   {
/*  86:111 */     return this.valueSources;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public ExplicitHibernateTypeSource getExplicitHibernateTypeSource()
/*  90:    */   {
/*  91:116 */     return this.typeSource;
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.BasicPluralAttributeElementSourceImpl
 * JD-Core Version:    0.7.0.1
 */