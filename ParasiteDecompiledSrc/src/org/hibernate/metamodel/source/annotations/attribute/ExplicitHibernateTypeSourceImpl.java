/*  1:   */ package org.hibernate.metamodel.source.annotations.attribute;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.metamodel.source.annotations.attribute.type.AttributeTypeResolver;
/*  5:   */ import org.hibernate.metamodel.source.binder.ExplicitHibernateTypeSource;
/*  6:   */ 
/*  7:   */ public class ExplicitHibernateTypeSourceImpl
/*  8:   */   implements ExplicitHibernateTypeSource
/*  9:   */ {
/* 10:   */   private final AttributeTypeResolver typeResolver;
/* 11:   */   
/* 12:   */   public ExplicitHibernateTypeSourceImpl(AttributeTypeResolver typeResolver)
/* 13:   */   {
/* 14:39 */     this.typeResolver = typeResolver;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getName()
/* 18:   */   {
/* 19:44 */     return this.typeResolver.getExplicitHibernateTypeName();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Map<String, String> getParameters()
/* 23:   */   {
/* 24:49 */     return this.typeResolver.getExplicitHibernateTypeParameters();
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.ExplicitHibernateTypeSourceImpl
 * JD-Core Version:    0.7.0.1
 */