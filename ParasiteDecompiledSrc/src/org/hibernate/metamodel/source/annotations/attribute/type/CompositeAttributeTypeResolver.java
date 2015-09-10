/*  1:   */ package org.hibernate.metamodel.source.annotations.attribute.type;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ import org.hibernate.AssertionFailure;
/*  7:   */ import org.hibernate.internal.util.StringHelper;
/*  8:   */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  9:   */ 
/* 10:   */ public class CompositeAttributeTypeResolver
/* 11:   */   implements AttributeTypeResolver
/* 12:   */ {
/* 13:39 */   private List<AttributeTypeResolver> resolvers = new ArrayList();
/* 14:   */   private final AttributeTypeResolverImpl explicitHibernateTypeResolver;
/* 15:   */   
/* 16:   */   public CompositeAttributeTypeResolver(AttributeTypeResolverImpl explicitHibernateTypeResolver)
/* 17:   */   {
/* 18:43 */     if (explicitHibernateTypeResolver == null) {
/* 19:44 */       throw new AssertionFailure("The Given AttributeTypeResolver is null.");
/* 20:   */     }
/* 21:46 */     this.explicitHibernateTypeResolver = explicitHibernateTypeResolver;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void addHibernateTypeResolver(AttributeTypeResolver resolver)
/* 25:   */   {
/* 26:50 */     if (resolver == null) {
/* 27:51 */       throw new AssertionFailure("The Given AttributeTypeResolver is null.");
/* 28:   */     }
/* 29:53 */     this.resolvers.add(resolver);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String getExplicitHibernateTypeName()
/* 33:   */   {
/* 34:58 */     String type = this.explicitHibernateTypeResolver.getExplicitHibernateTypeName();
/* 35:59 */     if (StringHelper.isEmpty(type)) {
/* 36:60 */       for (AttributeTypeResolver resolver : this.resolvers)
/* 37:   */       {
/* 38:61 */         type = resolver.getExplicitHibernateTypeName();
/* 39:62 */         if (StringHelper.isNotEmpty(type)) {
/* 40:   */           break;
/* 41:   */         }
/* 42:   */       }
/* 43:   */     }
/* 44:67 */     return type;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Map<String, String> getExplicitHibernateTypeParameters()
/* 48:   */   {
/* 49:72 */     Map<String, String> parameters = this.explicitHibernateTypeResolver.getExplicitHibernateTypeParameters();
/* 50:73 */     if (CollectionHelper.isEmpty(parameters)) {
/* 51:74 */       for (AttributeTypeResolver resolver : this.resolvers)
/* 52:   */       {
/* 53:75 */         parameters = resolver.getExplicitHibernateTypeParameters();
/* 54:76 */         if (CollectionHelper.isNotEmpty(parameters)) {
/* 55:   */           break;
/* 56:   */         }
/* 57:   */       }
/* 58:   */     }
/* 59:81 */     return parameters;
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.type.CompositeAttributeTypeResolver
 * JD-Core Version:    0.7.0.1
 */