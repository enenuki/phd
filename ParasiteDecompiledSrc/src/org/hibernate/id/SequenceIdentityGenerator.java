/*   1:    */ package org.hibernate.id;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Properties;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.MappingException;
/*   9:    */ import org.hibernate.dialect.Dialect;
/*  10:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  11:    */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*  12:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  13:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  14:    */ import org.hibernate.id.insert.AbstractReturningDelegate;
/*  15:    */ import org.hibernate.id.insert.IdentifierGeneratingInsert;
/*  16:    */ import org.hibernate.id.insert.InsertGeneratedIdentifierDelegate;
/*  17:    */ import org.hibernate.internal.CoreMessageLogger;
/*  18:    */ import org.hibernate.sql.Insert;
/*  19:    */ import org.hibernate.type.Type;
/*  20:    */ import org.jboss.logging.Logger;
/*  21:    */ 
/*  22:    */ public class SequenceIdentityGenerator
/*  23:    */   extends SequenceGenerator
/*  24:    */   implements PostInsertIdentifierGenerator
/*  25:    */ {
/*  26: 61 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SequenceIdentityGenerator.class.getName());
/*  27:    */   
/*  28:    */   public Serializable generate(SessionImplementor s, Object obj)
/*  29:    */   {
/*  30: 66 */     return IdentifierGeneratorHelper.POST_INSERT_INDICATOR;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public InsertGeneratedIdentifierDelegate getInsertGeneratedIdentifierDelegate(PostInsertIdentityPersister persister, Dialect dialect, boolean isGetGeneratedKeysEnabled)
/*  34:    */     throws HibernateException
/*  35:    */   {
/*  36: 73 */     return new Delegate(persister, dialect, getSequenceName());
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void configure(Type type, Properties params, Dialect dialect)
/*  40:    */     throws MappingException
/*  41:    */   {
/*  42: 78 */     super.configure(type, params, dialect);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static class Delegate
/*  46:    */     extends AbstractReturningDelegate
/*  47:    */   {
/*  48:    */     private final Dialect dialect;
/*  49:    */     private final String sequenceNextValFragment;
/*  50:    */     private final String[] keyColumns;
/*  51:    */     
/*  52:    */     public Delegate(PostInsertIdentityPersister persister, Dialect dialect, String sequenceName)
/*  53:    */     {
/*  54: 87 */       super();
/*  55: 88 */       this.dialect = dialect;
/*  56: 89 */       this.sequenceNextValFragment = dialect.getSelectSequenceNextValString(sequenceName);
/*  57: 90 */       this.keyColumns = getPersister().getRootTableKeyColumnNames();
/*  58: 91 */       if (this.keyColumns.length > 1) {
/*  59: 92 */         throw new HibernateException("sequence-identity generator cannot be used with with multi-column keys");
/*  60:    */       }
/*  61:    */     }
/*  62:    */     
/*  63:    */     public IdentifierGeneratingInsert prepareIdentifierGeneratingInsert()
/*  64:    */     {
/*  65: 97 */       SequenceIdentityGenerator.NoCommentsInsert insert = new SequenceIdentityGenerator.NoCommentsInsert(this.dialect);
/*  66: 98 */       insert.addColumn(getPersister().getRootTableKeyColumnNames()[0], this.sequenceNextValFragment);
/*  67: 99 */       return insert;
/*  68:    */     }
/*  69:    */     
/*  70:    */     protected PreparedStatement prepare(String insertSQL, SessionImplementor session)
/*  71:    */       throws SQLException
/*  72:    */     {
/*  73:104 */       return session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(insertSQL, this.keyColumns);
/*  74:    */     }
/*  75:    */     
/*  76:    */     protected Serializable executeAndExtract(PreparedStatement insert)
/*  77:    */       throws SQLException
/*  78:    */     {
/*  79:109 */       insert.executeUpdate();
/*  80:110 */       return IdentifierGeneratorHelper.getGeneratedIdentity(insert.getGeneratedKeys(), getPersister().getIdentifierType());
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static class NoCommentsInsert
/*  85:    */     extends IdentifierGeneratingInsert
/*  86:    */   {
/*  87:    */     public NoCommentsInsert(Dialect dialect)
/*  88:    */     {
/*  89:119 */       super();
/*  90:    */     }
/*  91:    */     
/*  92:    */     public Insert setComment(String comment)
/*  93:    */     {
/*  94:126 */       SequenceIdentityGenerator.LOG.disallowingInsertStatementComment();
/*  95:127 */       return this;
/*  96:    */     }
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.SequenceIdentityGenerator
 * JD-Core Version:    0.7.0.1
 */