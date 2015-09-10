/*  1:   */ package org.hibernate.metamodel.domain;
/*  2:   */ 
/*  3:   */ public enum TypeNature
/*  4:   */ {
/*  5:32 */   BASIC("basic"),  COMPONENT("component"),  ENTITY("entity"),  SUPERCLASS("superclass"),  NON_ENTITY("non-entity");
/*  6:   */   
/*  7:   */   private final String name;
/*  8:   */   
/*  9:   */   private TypeNature(String name)
/* 10:   */   {
/* 11:41 */     this.name = name;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getName()
/* 15:   */   {
/* 16:45 */     return this.name;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String toString()
/* 20:   */   {
/* 21:49 */     return super.toString() + "[" + getName() + "]";
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.domain.TypeNature
 * JD-Core Version:    0.7.0.1
 */