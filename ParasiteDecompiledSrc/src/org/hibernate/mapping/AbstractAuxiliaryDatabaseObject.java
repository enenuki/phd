/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import java.util.HashSet;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ 
/*  6:   */ public abstract class AbstractAuxiliaryDatabaseObject
/*  7:   */   implements AuxiliaryDatabaseObject
/*  8:   */ {
/*  9:   */   private final HashSet dialectScopes;
/* 10:   */   
/* 11:   */   protected AbstractAuxiliaryDatabaseObject()
/* 12:   */   {
/* 13:43 */     this.dialectScopes = new HashSet();
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected AbstractAuxiliaryDatabaseObject(HashSet dialectScopes)
/* 17:   */   {
/* 18:47 */     this.dialectScopes = dialectScopes;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void addDialectScope(String dialectName)
/* 22:   */   {
/* 23:51 */     this.dialectScopes.add(dialectName);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public HashSet getDialectScopes()
/* 27:   */   {
/* 28:55 */     return this.dialectScopes;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean appliesToDialect(Dialect dialect)
/* 32:   */   {
/* 33:60 */     return (this.dialectScopes.isEmpty()) || (this.dialectScopes.contains(dialect.getClass().getName()));
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.AbstractAuxiliaryDatabaseObject
 * JD-Core Version:    0.7.0.1
 */