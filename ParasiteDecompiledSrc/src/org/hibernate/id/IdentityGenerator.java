/*   1:    */ package org.hibernate.id;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import org.hibernate.AssertionFailure;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.dialect.Dialect;
/*  10:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  11:    */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*  12:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  13:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  14:    */ import org.hibernate.id.insert.AbstractReturningDelegate;
/*  15:    */ import org.hibernate.id.insert.AbstractSelectingDelegate;
/*  16:    */ import org.hibernate.id.insert.IdentifierGeneratingInsert;
/*  17:    */ import org.hibernate.id.insert.InsertGeneratedIdentifierDelegate;
/*  18:    */ import org.hibernate.id.insert.InsertSelectIdentityInsert;
/*  19:    */ 
/*  20:    */ public class IdentityGenerator
/*  21:    */   extends AbstractPostInsertGenerator
/*  22:    */ {
/*  23:    */   public InsertGeneratedIdentifierDelegate getInsertGeneratedIdentifierDelegate(PostInsertIdentityPersister persister, Dialect dialect, boolean isGetGeneratedKeysEnabled)
/*  24:    */     throws HibernateException
/*  25:    */   {
/*  26: 56 */     if (isGetGeneratedKeysEnabled) {
/*  27: 57 */       return new GetGeneratedKeysDelegate(persister, dialect);
/*  28:    */     }
/*  29: 59 */     if (dialect.supportsInsertSelectIdentity()) {
/*  30: 60 */       return new InsertSelectDelegate(persister, dialect);
/*  31:    */     }
/*  32: 63 */     return new BasicDelegate(persister, dialect);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static class GetGeneratedKeysDelegate
/*  36:    */     extends AbstractReturningDelegate
/*  37:    */     implements InsertGeneratedIdentifierDelegate
/*  38:    */   {
/*  39:    */     private final PostInsertIdentityPersister persister;
/*  40:    */     private final Dialect dialect;
/*  41:    */     
/*  42:    */     public GetGeneratedKeysDelegate(PostInsertIdentityPersister persister, Dialect dialect)
/*  43:    */     {
/*  44: 77 */       super();
/*  45: 78 */       this.persister = persister;
/*  46: 79 */       this.dialect = dialect;
/*  47:    */     }
/*  48:    */     
/*  49:    */     public IdentifierGeneratingInsert prepareIdentifierGeneratingInsert()
/*  50:    */     {
/*  51: 83 */       IdentifierGeneratingInsert insert = new IdentifierGeneratingInsert(this.dialect);
/*  52: 84 */       insert.addIdentityColumn(this.persister.getRootTableKeyColumnNames()[0]);
/*  53: 85 */       return insert;
/*  54:    */     }
/*  55:    */     
/*  56:    */     protected PreparedStatement prepare(String insertSQL, SessionImplementor session)
/*  57:    */       throws SQLException
/*  58:    */     {
/*  59: 89 */       return session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(insertSQL, 1);
/*  60:    */     }
/*  61:    */     
/*  62:    */     public Serializable executeAndExtract(PreparedStatement insert)
/*  63:    */       throws SQLException
/*  64:    */     {
/*  65: 96 */       insert.executeUpdate();
/*  66: 97 */       ResultSet rs = null;
/*  67:    */       try
/*  68:    */       {
/*  69: 99 */         rs = insert.getGeneratedKeys();
/*  70:100 */         return IdentifierGeneratorHelper.getGeneratedIdentity(rs, this.persister.getIdentifierType());
/*  71:    */       }
/*  72:    */       finally
/*  73:    */       {
/*  74:106 */         if (rs != null) {
/*  75:107 */           rs.close();
/*  76:    */         }
/*  77:    */       }
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static class InsertSelectDelegate
/*  82:    */     extends AbstractReturningDelegate
/*  83:    */     implements InsertGeneratedIdentifierDelegate
/*  84:    */   {
/*  85:    */     private final PostInsertIdentityPersister persister;
/*  86:    */     private final Dialect dialect;
/*  87:    */     
/*  88:    */     public InsertSelectDelegate(PostInsertIdentityPersister persister, Dialect dialect)
/*  89:    */     {
/*  90:124 */       super();
/*  91:125 */       this.persister = persister;
/*  92:126 */       this.dialect = dialect;
/*  93:    */     }
/*  94:    */     
/*  95:    */     public IdentifierGeneratingInsert prepareIdentifierGeneratingInsert()
/*  96:    */     {
/*  97:130 */       InsertSelectIdentityInsert insert = new InsertSelectIdentityInsert(this.dialect);
/*  98:131 */       insert.addIdentityColumn(this.persister.getRootTableKeyColumnNames()[0]);
/*  99:132 */       return insert;
/* 100:    */     }
/* 101:    */     
/* 102:    */     protected PreparedStatement prepare(String insertSQL, SessionImplementor session)
/* 103:    */       throws SQLException
/* 104:    */     {
/* 105:136 */       return session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(insertSQL, 2);
/* 106:    */     }
/* 107:    */     
/* 108:    */     public Serializable executeAndExtract(PreparedStatement insert)
/* 109:    */       throws SQLException
/* 110:    */     {
/* 111:143 */       while ((!insert.execute()) && 
/* 112:144 */         (!insert.getMoreResults()) && (insert.getUpdateCount() != -1)) {}
/* 113:148 */       ResultSet rs = insert.getResultSet();
/* 114:    */       try
/* 115:    */       {
/* 116:150 */         return IdentifierGeneratorHelper.getGeneratedIdentity(rs, this.persister.getIdentifierType());
/* 117:    */       }
/* 118:    */       finally
/* 119:    */       {
/* 120:153 */         rs.close();
/* 121:    */       }
/* 122:    */     }
/* 123:    */     
/* 124:    */     public Serializable determineGeneratedIdentifier(SessionImplementor session, Object entity)
/* 125:    */     {
/* 126:158 */       throw new AssertionFailure("insert statement returns generated value");
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static class BasicDelegate
/* 131:    */     extends AbstractSelectingDelegate
/* 132:    */     implements InsertGeneratedIdentifierDelegate
/* 133:    */   {
/* 134:    */     private final PostInsertIdentityPersister persister;
/* 135:    */     private final Dialect dialect;
/* 136:    */     
/* 137:    */     public BasicDelegate(PostInsertIdentityPersister persister, Dialect dialect)
/* 138:    */     {
/* 139:173 */       super();
/* 140:174 */       this.persister = persister;
/* 141:175 */       this.dialect = dialect;
/* 142:    */     }
/* 143:    */     
/* 144:    */     public IdentifierGeneratingInsert prepareIdentifierGeneratingInsert()
/* 145:    */     {
/* 146:179 */       IdentifierGeneratingInsert insert = new IdentifierGeneratingInsert(this.dialect);
/* 147:180 */       insert.addIdentityColumn(this.persister.getRootTableKeyColumnNames()[0]);
/* 148:181 */       return insert;
/* 149:    */     }
/* 150:    */     
/* 151:    */     protected String getSelectSQL()
/* 152:    */     {
/* 153:185 */       return this.persister.getIdentitySelectString();
/* 154:    */     }
/* 155:    */     
/* 156:    */     protected Serializable getResult(SessionImplementor session, ResultSet rs, Object object)
/* 157:    */       throws SQLException
/* 158:    */     {
/* 159:192 */       return IdentifierGeneratorHelper.getGeneratedIdentity(rs, this.persister.getIdentifierType());
/* 160:    */     }
/* 161:    */   }
/* 162:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.IdentityGenerator
 * JD-Core Version:    0.7.0.1
 */