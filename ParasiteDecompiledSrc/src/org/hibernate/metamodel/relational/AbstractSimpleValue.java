/*  1:   */ package org.hibernate.metamodel.relational;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.CoreMessageLogger;
/*  4:   */ import org.hibernate.metamodel.ValidationException;
/*  5:   */ import org.jboss.logging.Logger;
/*  6:   */ 
/*  7:   */ public abstract class AbstractSimpleValue
/*  8:   */   implements SimpleValue
/*  9:   */ {
/* 10:38 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractSimpleValue.class.getName());
/* 11:   */   private final TableSpecification table;
/* 12:   */   private final int position;
/* 13:   */   private Datatype datatype;
/* 14:   */   
/* 15:   */   protected AbstractSimpleValue(TableSpecification table, int position)
/* 16:   */   {
/* 17:45 */     this.table = table;
/* 18:46 */     this.position = position;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public TableSpecification getTable()
/* 22:   */   {
/* 23:51 */     return this.table;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getPosition()
/* 27:   */   {
/* 28:55 */     return this.position;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Datatype getDatatype()
/* 32:   */   {
/* 33:60 */     return this.datatype;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setDatatype(Datatype datatype)
/* 37:   */   {
/* 38:65 */     LOG.debugf("setting datatype for column %s : %s", toLoggableString(), datatype);
/* 39:66 */     if ((this.datatype != null) && (!this.datatype.equals(datatype))) {
/* 40:67 */       LOG.debugf("overriding previous datatype : %s", this.datatype);
/* 41:   */     }
/* 42:69 */     this.datatype = datatype;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void validateJdbcTypes(Value.JdbcCodes typeCodes)
/* 46:   */   {
/* 47:75 */     if (this.datatype.getTypeCode() != typeCodes.nextJdbcCde()) {
/* 48:76 */       throw new ValidationException("Mismatched types");
/* 49:   */     }
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.AbstractSimpleValue
 * JD-Core Version:    0.7.0.1
 */