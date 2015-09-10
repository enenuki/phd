/*   1:    */ package org.hibernate.jdbc;
/*   2:    */ 
/*   3:    */ import java.sql.CallableStatement;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.StaleStateException;
/*   8:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*   9:    */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*  10:    */ import org.hibernate.exception.GenericJDBCException;
/*  11:    */ import org.hibernate.internal.CoreMessageLogger;
/*  12:    */ import org.jboss.logging.Logger;
/*  13:    */ 
/*  14:    */ public class Expectations
/*  15:    */ {
/*  16: 48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, Expectations.class.getName());
/*  17: 49 */   private static SqlExceptionHelper sqlExceptionHelper = new SqlExceptionHelper();
/*  18:    */   public static final int USUAL_EXPECTED_COUNT = 1;
/*  19:    */   public static final int USUAL_PARAM_POSITION = 1;
/*  20:    */   
/*  21:    */   public static class BasicExpectation
/*  22:    */     implements Expectation
/*  23:    */   {
/*  24:    */     private final int expectedRowCount;
/*  25:    */     
/*  26:    */     protected BasicExpectation(int expectedRowCount)
/*  27:    */     {
/*  28: 61 */       this.expectedRowCount = expectedRowCount;
/*  29: 62 */       if (expectedRowCount < 0) {
/*  30: 63 */         throw new IllegalArgumentException("Expected row count must be greater than zero");
/*  31:    */       }
/*  32:    */     }
/*  33:    */     
/*  34:    */     public final void verifyOutcome(int rowCount, PreparedStatement statement, int batchPosition)
/*  35:    */     {
/*  36: 68 */       rowCount = determineRowCount(rowCount, statement);
/*  37: 69 */       if (batchPosition < 0) {
/*  38: 70 */         checkNonBatched(rowCount);
/*  39:    */       } else {
/*  40: 73 */         checkBatched(rowCount, batchPosition);
/*  41:    */       }
/*  42:    */     }
/*  43:    */     
/*  44:    */     private void checkBatched(int rowCount, int batchPosition)
/*  45:    */     {
/*  46: 78 */       if (rowCount == -2)
/*  47:    */       {
/*  48: 78 */         Expectations.LOG.debugf("Success of batch update unknown: %s", Integer.valueOf(batchPosition));
/*  49:    */       }
/*  50:    */       else
/*  51:    */       {
/*  52: 79 */         if (rowCount == -3) {
/*  53: 79 */           throw new BatchFailedException("Batch update failed: " + batchPosition);
/*  54:    */         }
/*  55: 81 */         if (this.expectedRowCount > rowCount) {
/*  56: 81 */           throw new StaleStateException("Batch update returned unexpected row count from update [" + batchPosition + "]; actual row count: " + rowCount + "; expected: " + this.expectedRowCount);
/*  57:    */         }
/*  58: 85 */         if (this.expectedRowCount < rowCount)
/*  59:    */         {
/*  60: 86 */           String msg = "Batch update returned unexpected row count from update [" + batchPosition + "]; actual row count: " + rowCount + "; expected: " + this.expectedRowCount;
/*  61:    */           
/*  62:    */ 
/*  63: 89 */           throw new BatchedTooManyRowsAffectedException(msg, this.expectedRowCount, rowCount, batchPosition);
/*  64:    */         }
/*  65:    */       }
/*  66:    */     }
/*  67:    */     
/*  68:    */     private void checkNonBatched(int rowCount)
/*  69:    */     {
/*  70: 95 */       if (this.expectedRowCount > rowCount) {
/*  71: 96 */         throw new StaleStateException("Unexpected row count: " + rowCount + "; expected: " + this.expectedRowCount);
/*  72:    */       }
/*  73:100 */       if (this.expectedRowCount < rowCount)
/*  74:    */       {
/*  75:101 */         String msg = "Unexpected row count: " + rowCount + "; expected: " + this.expectedRowCount;
/*  76:102 */         throw new TooManyRowsAffectedException(msg, this.expectedRowCount, rowCount);
/*  77:    */       }
/*  78:    */     }
/*  79:    */     
/*  80:    */     public int prepare(PreparedStatement statement)
/*  81:    */       throws SQLException, HibernateException
/*  82:    */     {
/*  83:107 */       return 0;
/*  84:    */     }
/*  85:    */     
/*  86:    */     public boolean canBeBatched()
/*  87:    */     {
/*  88:111 */       return true;
/*  89:    */     }
/*  90:    */     
/*  91:    */     protected int determineRowCount(int reportedRowCount, PreparedStatement statement)
/*  92:    */     {
/*  93:115 */       return reportedRowCount;
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static class BasicParamExpectation
/*  98:    */     extends Expectations.BasicExpectation
/*  99:    */   {
/* 100:    */     private final int parameterPosition;
/* 101:    */     
/* 102:    */     protected BasicParamExpectation(int expectedRowCount, int parameterPosition)
/* 103:    */     {
/* 104:122 */       super();
/* 105:123 */       this.parameterPosition = parameterPosition;
/* 106:    */     }
/* 107:    */     
/* 108:    */     public int prepare(PreparedStatement statement)
/* 109:    */       throws SQLException, HibernateException
/* 110:    */     {
/* 111:128 */       toCallableStatement(statement).registerOutParameter(this.parameterPosition, 2);
/* 112:129 */       return 1;
/* 113:    */     }
/* 114:    */     
/* 115:    */     public boolean canBeBatched()
/* 116:    */     {
/* 117:134 */       return false;
/* 118:    */     }
/* 119:    */     
/* 120:    */     protected int determineRowCount(int reportedRowCount, PreparedStatement statement)
/* 121:    */     {
/* 122:    */       try
/* 123:    */       {
/* 124:140 */         return toCallableStatement(statement).getInt(this.parameterPosition);
/* 125:    */       }
/* 126:    */       catch (SQLException sqle)
/* 127:    */       {
/* 128:143 */         Expectations.sqlExceptionHelper.logExceptions(sqle, "could not extract row counts from CallableStatement");
/* 129:144 */         throw new GenericJDBCException("could not extract row counts from CallableStatement", sqle);
/* 130:    */       }
/* 131:    */     }
/* 132:    */     
/* 133:    */     private CallableStatement toCallableStatement(PreparedStatement statement)
/* 134:    */     {
/* 135:149 */       if (!CallableStatement.class.isInstance(statement)) {
/* 136:150 */         throw new HibernateException("BasicParamExpectation operates exclusively on CallableStatements : " + statement.getClass());
/* 137:    */       }
/* 138:152 */       return (CallableStatement)statement;
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:159 */   public static final Expectation NONE = new Expectation()
/* 143:    */   {
/* 144:    */     public void verifyOutcome(int rowCount, PreparedStatement statement, int batchPosition) {}
/* 145:    */     
/* 146:    */     public int prepare(PreparedStatement statement)
/* 147:    */     {
/* 148:165 */       return 0;
/* 149:    */     }
/* 150:    */     
/* 151:    */     public boolean canBeBatched()
/* 152:    */     {
/* 153:169 */       return true;
/* 154:    */     }
/* 155:    */   };
/* 156:173 */   public static final Expectation BASIC = new BasicExpectation(1);
/* 157:175 */   public static final Expectation PARAM = new BasicParamExpectation(1, 1);
/* 158:    */   
/* 159:    */   public static Expectation appropriateExpectation(ExecuteUpdateResultCheckStyle style)
/* 160:    */   {
/* 161:179 */     if (style == ExecuteUpdateResultCheckStyle.NONE) {
/* 162:180 */       return NONE;
/* 163:    */     }
/* 164:182 */     if (style == ExecuteUpdateResultCheckStyle.COUNT) {
/* 165:183 */       return BASIC;
/* 166:    */     }
/* 167:185 */     if (style == ExecuteUpdateResultCheckStyle.PARAM) {
/* 168:186 */       return PARAM;
/* 169:    */     }
/* 170:189 */     throw new HibernateException("unknown check style : " + style);
/* 171:    */   }
/* 172:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jdbc.Expectations
 * JD-Core Version:    0.7.0.1
 */