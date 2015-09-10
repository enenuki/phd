/*  1:   */ package org.hibernate.metamodel.source.annotations.attribute;
/*  2:   */ 
/*  3:   */ import java.util.Set;
/*  4:   */ import org.hibernate.FetchMode;
/*  5:   */ import org.hibernate.engine.FetchStyle;
/*  6:   */ import org.hibernate.engine.FetchTiming;
/*  7:   */ import org.hibernate.engine.spi.CascadeStyle;
/*  8:   */ import org.hibernate.metamodel.source.annotations.EnumConversionHelper;
/*  9:   */ import org.hibernate.metamodel.source.binder.SingularAttributeNature;
/* 10:   */ import org.hibernate.metamodel.source.binder.ToOneAttributeSource;
/* 11:   */ 
/* 12:   */ public class ToOneAttributeSourceImpl
/* 13:   */   extends SingularAttributeSourceImpl
/* 14:   */   implements ToOneAttributeSource
/* 15:   */ {
/* 16:   */   private final AssociationAttribute associationAttribute;
/* 17:   */   private final Set<CascadeStyle> cascadeStyles;
/* 18:   */   
/* 19:   */   public ToOneAttributeSourceImpl(AssociationAttribute associationAttribute)
/* 20:   */   {
/* 21:44 */     super(associationAttribute);
/* 22:45 */     this.associationAttribute = associationAttribute;
/* 23:46 */     this.cascadeStyles = EnumConversionHelper.cascadeTypeToCascadeStyleSet(associationAttribute.getCascadeTypes());
/* 24:   */   }
/* 25:   */   
/* 26:   */   public SingularAttributeNature getNature()
/* 27:   */   {
/* 28:51 */     return SingularAttributeNature.MANY_TO_ONE;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getReferencedEntityName()
/* 32:   */   {
/* 33:56 */     return this.associationAttribute.getReferencedEntityType();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getReferencedEntityAttributeName()
/* 37:   */   {
/* 38:61 */     return this.associationAttribute.getMappedBy();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Iterable<CascadeStyle> getCascadeStyles()
/* 42:   */   {
/* 43:66 */     return this.cascadeStyles;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public FetchMode getFetchMode()
/* 47:   */   {
/* 48:71 */     return this.associationAttribute.getFetchMode();
/* 49:   */   }
/* 50:   */   
/* 51:   */   public FetchTiming getFetchTiming()
/* 52:   */   {
/* 53:77 */     return FetchTiming.IMMEDIATE;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public FetchStyle getFetchStyle()
/* 57:   */   {
/* 58:83 */     return FetchStyle.JOIN;
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.ToOneAttributeSourceImpl
 * JD-Core Version:    0.7.0.1
 */