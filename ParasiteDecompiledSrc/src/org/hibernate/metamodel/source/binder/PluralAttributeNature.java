/*  1:   */ package org.hibernate.metamodel.source.binder;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ import java.util.Set;
/*  7:   */ 
/*  8:   */ public enum PluralAttributeNature
/*  9:   */ {
/* 10:37 */   BAG(Collection.class),  ID_BAG(Collection.class),  SET(Set.class),  LIST(List.class),  MAP(Map.class);
/* 11:   */   
/* 12:   */   private final Class<?> reportedJavaType;
/* 13:   */   
/* 14:   */   private PluralAttributeNature(Class<?> reportedJavaType)
/* 15:   */   {
/* 16:46 */     this.reportedJavaType = reportedJavaType;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Class<?> reportedJavaType()
/* 20:   */   {
/* 21:50 */     return this.reportedJavaType;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.PluralAttributeNature
 * JD-Core Version:    0.7.0.1
 */