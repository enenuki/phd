/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.EntityMode;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbCompositeElementElement;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbParentElement;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbTuplizerElement;
/*  10:    */ import org.hibernate.internal.util.StringHelper;
/*  11:    */ import org.hibernate.internal.util.Value;
/*  12:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*  13:    */ import org.hibernate.metamodel.source.binder.AttributeSource;
/*  14:    */ import org.hibernate.metamodel.source.binder.CompositePluralAttributeElementSource;
/*  15:    */ import org.hibernate.metamodel.source.binder.PluralAttributeElementNature;
/*  16:    */ 
/*  17:    */ public class CompositePluralAttributeElementSourceImpl
/*  18:    */   implements CompositePluralAttributeElementSource
/*  19:    */ {
/*  20:    */   private final JaxbCompositeElementElement compositeElement;
/*  21:    */   private final LocalBindingContext bindingContext;
/*  22:    */   
/*  23:    */   public CompositePluralAttributeElementSourceImpl(JaxbCompositeElementElement compositeElement, LocalBindingContext bindingContext)
/*  24:    */   {
/*  25: 49 */     this.compositeElement = compositeElement;
/*  26: 50 */     this.bindingContext = bindingContext;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public PluralAttributeElementNature getNature()
/*  30:    */   {
/*  31: 55 */     return PluralAttributeElementNature.COMPONENT;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getClassName()
/*  35:    */   {
/*  36: 60 */     return this.bindingContext.qualifyClassName(this.compositeElement.getClazz());
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Value<Class<?>> getClassReference()
/*  40:    */   {
/*  41: 65 */     return this.bindingContext.makeClassReference(getClassName());
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getParentReferenceAttributeName()
/*  45:    */   {
/*  46: 70 */     return this.compositeElement.getParent() != null ? this.compositeElement.getParent().getName() : null;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getExplicitTuplizerClassName()
/*  50:    */   {
/*  51: 77 */     if (this.compositeElement.getTuplizer() == null) {
/*  52: 78 */       return null;
/*  53:    */     }
/*  54: 80 */     EntityMode entityMode = StringHelper.isEmpty(this.compositeElement.getClazz()) ? EntityMode.MAP : EntityMode.POJO;
/*  55: 81 */     for (JaxbTuplizerElement tuplizerElement : this.compositeElement.getTuplizer()) {
/*  56: 82 */       if (entityMode == EntityMode.parse(tuplizerElement.getEntityMode())) {
/*  57: 83 */         return tuplizerElement.getClazz();
/*  58:    */       }
/*  59:    */     }
/*  60: 86 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getPath()
/*  64:    */   {
/*  65: 92 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Iterable<AttributeSource> attributeSources()
/*  69:    */   {
/*  70: 97 */     List<AttributeSource> attributeSources = new ArrayList();
/*  71:    */     Object attribute;
/*  72: 98 */     for (Iterator i$ = this.compositeElement.getPropertyOrManyToOneOrAny().iterator(); i$.hasNext(); attribute = i$.next()) {}
/*  73:101 */     return attributeSources;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public LocalBindingContext getLocalBindingContext()
/*  77:    */   {
/*  78:106 */     return this.bindingContext;
/*  79:    */   }
/*  80:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.CompositePluralAttributeElementSourceImpl
 * JD-Core Version:    0.7.0.1
 */