/*  1:   */ package org.hibernate.persister.entity;
/*  2:   */ 
/*  3:   */ import org.hibernate.QueryException;
/*  4:   */ import org.hibernate.type.Type;
/*  5:   */ 
/*  6:   */ public class BasicEntityPropertyMapping
/*  7:   */   extends AbstractPropertyMapping
/*  8:   */ {
/*  9:   */   private final AbstractEntityPersister persister;
/* 10:   */   
/* 11:   */   public BasicEntityPropertyMapping(AbstractEntityPersister persister)
/* 12:   */   {
/* 13:37 */     this.persister = persister;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String[] getIdentifierColumnNames()
/* 17:   */   {
/* 18:41 */     return this.persister.getIdentifierColumnNames();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String[] getIdentifierColumnReaders()
/* 22:   */   {
/* 23:45 */     return this.persister.getIdentifierColumnReaders();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String[] getIdentifierColumnReaderTemplates()
/* 27:   */   {
/* 28:49 */     return this.persister.getIdentifierColumnReaderTemplates();
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected String getEntityName()
/* 32:   */   {
/* 33:53 */     return this.persister.getEntityName();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Type getType()
/* 37:   */   {
/* 38:57 */     return this.persister.getType();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String[] toColumns(String alias, String propertyName)
/* 42:   */     throws QueryException
/* 43:   */   {
/* 44:61 */     return super.toColumns(this.persister.generateTableAlias(alias, this.persister.getSubclassPropertyTableNumber(propertyName)), propertyName);
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.BasicEntityPropertyMapping
 * JD-Core Version:    0.7.0.1
 */