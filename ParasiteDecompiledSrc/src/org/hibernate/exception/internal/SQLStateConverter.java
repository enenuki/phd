/*   1:    */ package org.hibernate.exception.internal;
/*   2:    */ 
/*   3:    */ import java.sql.SQLException;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Set;
/*   6:    */ import org.hibernate.JDBCException;
/*   7:    */ import org.hibernate.PessimisticLockException;
/*   8:    */ import org.hibernate.QueryTimeoutException;
/*   9:    */ import org.hibernate.exception.ConstraintViolationException;
/*  10:    */ import org.hibernate.exception.DataException;
/*  11:    */ import org.hibernate.exception.GenericJDBCException;
/*  12:    */ import org.hibernate.exception.JDBCConnectionException;
/*  13:    */ import org.hibernate.exception.LockAcquisitionException;
/*  14:    */ import org.hibernate.exception.SQLGrammarException;
/*  15:    */ import org.hibernate.exception.spi.SQLExceptionConverter;
/*  16:    */ import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
/*  17:    */ import org.hibernate.internal.util.JdbcExceptionHelper;
/*  18:    */ 
/*  19:    */ public class SQLStateConverter
/*  20:    */   implements SQLExceptionConverter
/*  21:    */ {
/*  22:    */   private ViolatedConstraintNameExtracter extracter;
/*  23: 56 */   private static final Set SQL_GRAMMAR_CATEGORIES = new HashSet();
/*  24: 57 */   private static final Set DATA_CATEGORIES = new HashSet();
/*  25: 58 */   private static final Set INTEGRITY_VIOLATION_CATEGORIES = new HashSet();
/*  26: 59 */   private static final Set CONNECTION_CATEGORIES = new HashSet();
/*  27:    */   
/*  28:    */   static
/*  29:    */   {
/*  30: 62 */     SQL_GRAMMAR_CATEGORIES.add("07");
/*  31: 63 */     SQL_GRAMMAR_CATEGORIES.add("37");
/*  32: 64 */     SQL_GRAMMAR_CATEGORIES.add("42");
/*  33: 65 */     SQL_GRAMMAR_CATEGORIES.add("65");
/*  34: 66 */     SQL_GRAMMAR_CATEGORIES.add("S0");
/*  35: 67 */     SQL_GRAMMAR_CATEGORIES.add("20");
/*  36:    */     
/*  37: 69 */     DATA_CATEGORIES.add("22");
/*  38: 70 */     DATA_CATEGORIES.add("21");
/*  39: 71 */     DATA_CATEGORIES.add("02");
/*  40:    */     
/*  41: 73 */     INTEGRITY_VIOLATION_CATEGORIES.add("23");
/*  42: 74 */     INTEGRITY_VIOLATION_CATEGORIES.add("27");
/*  43: 75 */     INTEGRITY_VIOLATION_CATEGORIES.add("44");
/*  44:    */     
/*  45: 77 */     CONNECTION_CATEGORIES.add("08");
/*  46:    */   }
/*  47:    */   
/*  48:    */   public SQLStateConverter(ViolatedConstraintNameExtracter extracter)
/*  49:    */   {
/*  50: 81 */     this.extracter = extracter;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public JDBCException convert(SQLException sqlException, String message, String sql)
/*  54:    */   {
/*  55: 93 */     String sqlState = JdbcExceptionHelper.extractSqlState(sqlException);
/*  56: 95 */     if (sqlState != null)
/*  57:    */     {
/*  58: 96 */       String sqlStateClassCode = JdbcExceptionHelper.determineSqlStateClassCode(sqlState);
/*  59: 98 */       if (sqlStateClassCode != null)
/*  60:    */       {
/*  61: 99 */         if (SQL_GRAMMAR_CATEGORIES.contains(sqlStateClassCode)) {
/*  62:100 */           return new SQLGrammarException(message, sqlException, sql);
/*  63:    */         }
/*  64:102 */         if (INTEGRITY_VIOLATION_CATEGORIES.contains(sqlStateClassCode))
/*  65:    */         {
/*  66:103 */           String constraintName = this.extracter.extractConstraintName(sqlException);
/*  67:104 */           return new ConstraintViolationException(message, sqlException, sql, constraintName);
/*  68:    */         }
/*  69:106 */         if (CONNECTION_CATEGORIES.contains(sqlStateClassCode)) {
/*  70:107 */           return new JDBCConnectionException(message, sqlException, sql);
/*  71:    */         }
/*  72:109 */         if (DATA_CATEGORIES.contains(sqlStateClassCode)) {
/*  73:110 */           return new DataException(message, sqlException, sql);
/*  74:    */         }
/*  75:    */       }
/*  76:114 */       if ("40001".equals(sqlState)) {
/*  77:115 */         return new LockAcquisitionException(message, sqlException, sql);
/*  78:    */       }
/*  79:118 */       if ("61000".equals(sqlState)) {
/*  80:120 */         return new LockAcquisitionException(message, sqlException, sql);
/*  81:    */       }
/*  82:123 */       if (("40XL1".equals(sqlState)) || ("40XL2".equals(sqlState))) {
/*  83:125 */         return new PessimisticLockException(message, sqlException, sql);
/*  84:    */       }
/*  85:129 */       if (("70100".equals(sqlState)) || ("72000".equals(sqlState))) {
/*  86:132 */         throw new QueryTimeoutException(message, sqlException, sql);
/*  87:    */       }
/*  88:    */     }
/*  89:136 */     return handledNonSpecificException(sqlException, message, sql);
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected JDBCException handledNonSpecificException(SQLException sqlException, String message, String sql)
/*  93:    */   {
/*  94:148 */     return new GenericJDBCException(message, sqlException, sql);
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.exception.internal.SQLStateConverter
 * JD-Core Version:    0.7.0.1
 */