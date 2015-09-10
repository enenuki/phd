/*  1:   */ package org.hibernate.metamodel.relational;
/*  2:   */ 
/*  3:   */ import java.util.HashSet;
/*  4:   */ import java.util.Set;
/*  5:   */ import java.util.UUID;
/*  6:   */ import java.util.concurrent.atomic.AtomicInteger;
/*  7:   */ import org.hibernate.dialect.Dialect;
/*  8:   */ 
/*  9:   */ public abstract class AbstractAuxiliaryDatabaseObject
/* 10:   */   implements AuxiliaryDatabaseObject
/* 11:   */ {
/* 12:45 */   private static final String EXPORT_IDENTIFIER_PREFIX = "auxiliary-object-" + UUID.randomUUID();
/* 13:46 */   private static final AtomicInteger counter = new AtomicInteger(0);
/* 14:   */   private final String exportIdentifier;
/* 15:   */   private final Set<String> dialectScopes;
/* 16:   */   
/* 17:   */   protected AbstractAuxiliaryDatabaseObject(Set<String> dialectScopes)
/* 18:   */   {
/* 19:51 */     this.dialectScopes = (dialectScopes == null ? new HashSet() : dialectScopes);
/* 20:52 */     this.exportIdentifier = (EXPORT_IDENTIFIER_PREFIX + '.' + counter.getAndIncrement());
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void addDialectScope(String dialectName)
/* 24:   */   {
/* 25:60 */     this.dialectScopes.add(dialectName);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Iterable<String> getDialectScopes()
/* 29:   */   {
/* 30:64 */     return this.dialectScopes;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean appliesToDialect(Dialect dialect)
/* 34:   */   {
/* 35:69 */     return (this.dialectScopes.isEmpty()) || (this.dialectScopes.contains(dialect.getClass().getName()));
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getExportIdentifier()
/* 39:   */   {
/* 40:74 */     return this.exportIdentifier;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.AbstractAuxiliaryDatabaseObject
 * JD-Core Version:    0.7.0.1
 */