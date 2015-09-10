/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ 
/*  5:   */ public enum InheritanceType
/*  6:   */ {
/*  7:36 */   JOINED,  SINGLE_TABLE,  TABLE_PER_CLASS,  NO_INHERITANCE;
/*  8:   */   
/*  9:   */   private InheritanceType() {}
/* 10:   */   
/* 11:   */   public static InheritanceType get(javax.persistence.InheritanceType jpaType)
/* 12:   */   {
/* 13:47 */     switch (1.$SwitchMap$javax$persistence$InheritanceType[jpaType.ordinal()])
/* 14:   */     {
/* 15:   */     case 1: 
/* 16:49 */       return SINGLE_TABLE;
/* 17:   */     case 2: 
/* 18:52 */       return JOINED;
/* 19:   */     case 3: 
/* 20:55 */       return TABLE_PER_CLASS;
/* 21:   */     }
/* 22:58 */     throw new MappingException("Unknown jpa inheritance type:" + jpaType.name());
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.InheritanceType
 * JD-Core Version:    0.7.0.1
 */