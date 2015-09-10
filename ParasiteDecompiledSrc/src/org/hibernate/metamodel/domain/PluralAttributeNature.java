/*  1:   */ package org.hibernate.metamodel.domain;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ import java.util.Set;
/*  7:   */ 
/*  8:   */ public enum PluralAttributeNature
/*  9:   */ {
/* 10:37 */   BAG("bag", Collection.class),  IDBAG("idbag", Collection.class),  SET("set", Set.class),  LIST("list", List.class),  MAP("map", Map.class);
/* 11:   */   
/* 12:   */   private final String name;
/* 13:   */   private final Class javaContract;
/* 14:   */   private final boolean indexed;
/* 15:   */   
/* 16:   */   private PluralAttributeNature(String name, Class javaContract)
/* 17:   */   {
/* 18:48 */     this.name = name;
/* 19:49 */     this.javaContract = javaContract;
/* 20:50 */     this.indexed = ((Map.class.isAssignableFrom(javaContract)) || (List.class.isAssignableFrom(javaContract)));
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getName()
/* 24:   */   {
/* 25:54 */     return this.name;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Class getJavaContract()
/* 29:   */   {
/* 30:58 */     return this.javaContract;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean isIndexed()
/* 34:   */   {
/* 35:62 */     return this.indexed;
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.domain.PluralAttributeNature
 * JD-Core Version:    0.7.0.1
 */