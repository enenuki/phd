/*   1:    */ package org.hibernate.exception.internal;
/*   2:    */ 
/*   3:    */ import java.sql.SQLException;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Set;
/*   6:    */ import org.hibernate.JDBCException;
/*   7:    */ import org.hibernate.exception.ConstraintViolationException;
/*   8:    */ import org.hibernate.exception.DataException;
/*   9:    */ import org.hibernate.exception.GenericJDBCException;
/*  10:    */ import org.hibernate.exception.JDBCConnectionException;
/*  11:    */ import org.hibernate.exception.SQLGrammarException;
/*  12:    */ import org.hibernate.exception.spi.SQLExceptionConverter;
/*  13:    */ import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
/*  14:    */ import org.hibernate.internal.util.JdbcExceptionHelper;
/*  15:    */ 
/*  16:    */ public class CacheSQLStateConverter
/*  17:    */   implements SQLExceptionConverter
/*  18:    */ {
/*  19:    */   private ViolatedConstraintNameExtracter extracter;
/*  20: 50 */   private static final Set<String> SQL_GRAMMAR_CATEGORIES = new HashSet();
/*  21: 51 */   private static final Set<String> DATA_CATEGORIES = new HashSet();
/*  22: 52 */   private static final Set<Integer> INTEGRITY_VIOLATION_CATEGORIES = new HashSet();
/*  23: 53 */   private static final Set<String> CONNECTION_CATEGORIES = new HashSet();
/*  24:    */   
/*  25:    */   static
/*  26:    */   {
/*  27: 56 */     SQL_GRAMMAR_CATEGORIES.add("07");
/*  28: 57 */     SQL_GRAMMAR_CATEGORIES.add("37");
/*  29: 58 */     SQL_GRAMMAR_CATEGORIES.add("42");
/*  30: 59 */     SQL_GRAMMAR_CATEGORIES.add("65");
/*  31: 60 */     SQL_GRAMMAR_CATEGORIES.add("S0");
/*  32: 61 */     SQL_GRAMMAR_CATEGORIES.add("20");
/*  33:    */     
/*  34: 63 */     DATA_CATEGORIES.add("22");
/*  35: 64 */     DATA_CATEGORIES.add("21");
/*  36: 65 */     DATA_CATEGORIES.add("02");
/*  37:    */     
/*  38: 67 */     INTEGRITY_VIOLATION_CATEGORIES.add(Integer.valueOf(119));
/*  39: 68 */     INTEGRITY_VIOLATION_CATEGORIES.add(Integer.valueOf(120));
/*  40: 69 */     INTEGRITY_VIOLATION_CATEGORIES.add(Integer.valueOf(121));
/*  41: 70 */     INTEGRITY_VIOLATION_CATEGORIES.add(Integer.valueOf(122));
/*  42: 71 */     INTEGRITY_VIOLATION_CATEGORIES.add(Integer.valueOf(123));
/*  43: 72 */     INTEGRITY_VIOLATION_CATEGORIES.add(Integer.valueOf(124));
/*  44: 73 */     INTEGRITY_VIOLATION_CATEGORIES.add(Integer.valueOf(125));
/*  45: 74 */     INTEGRITY_VIOLATION_CATEGORIES.add(Integer.valueOf(127));
/*  46:    */     
/*  47: 76 */     CONNECTION_CATEGORIES.add("08");
/*  48:    */   }
/*  49:    */   
/*  50:    */   public CacheSQLStateConverter(ViolatedConstraintNameExtracter extracter)
/*  51:    */   {
/*  52: 80 */     this.extracter = extracter;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public JDBCException convert(SQLException sqlException, String message, String sql)
/*  56:    */   {
/*  57: 92 */     String sqlStateClassCode = JdbcExceptionHelper.extractSqlStateClassCode(sqlException);
/*  58: 93 */     Integer errorCode = Integer.valueOf(JdbcExceptionHelper.extractErrorCode(sqlException));
/*  59: 94 */     if (sqlStateClassCode != null)
/*  60:    */     {
/*  61: 95 */       if (SQL_GRAMMAR_CATEGORIES.contains(sqlStateClassCode)) {
/*  62: 96 */         return new SQLGrammarException(message, sqlException, sql);
/*  63:    */       }
/*  64: 98 */       if (INTEGRITY_VIOLATION_CATEGORIES.contains(errorCode))
/*  65:    */       {
/*  66: 99 */         String constraintName = this.extracter.extractConstraintName(sqlException);
/*  67:100 */         return new ConstraintViolationException(message, sqlException, sql, constraintName);
/*  68:    */       }
/*  69:102 */       if (CONNECTION_CATEGORIES.contains(sqlStateClassCode)) {
/*  70:103 */         return new JDBCConnectionException(message, sqlException, sql);
/*  71:    */       }
/*  72:105 */       if (DATA_CATEGORIES.contains(sqlStateClassCode)) {
/*  73:106 */         return new DataException(message, sqlException, sql);
/*  74:    */       }
/*  75:    */     }
/*  76:109 */     return handledNonSpecificException(sqlException, message, sql);
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected JDBCException handledNonSpecificException(SQLException sqlException, String message, String sql)
/*  80:    */   {
/*  81:121 */     return new GenericJDBCException(message, sqlException, sql);
/*  82:    */   }
/*  83:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.exception.internal.CacheSQLStateConverter
 * JD-Core Version:    0.7.0.1
 */