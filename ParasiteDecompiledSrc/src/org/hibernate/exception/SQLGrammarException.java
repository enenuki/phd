/*  1:   */ package org.hibernate.exception;
/*  2:   */ 
/*  3:   */ import java.sql.SQLException;
/*  4:   */ import org.hibernate.JDBCException;
/*  5:   */ 
/*  6:   */ public class SQLGrammarException
/*  7:   */   extends JDBCException
/*  8:   */ {
/*  9:   */   public SQLGrammarException(String message, SQLException root)
/* 10:   */   {
/* 11:43 */     super(message, root);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public SQLGrammarException(String message, SQLException root, String sql)
/* 15:   */   {
/* 16:53 */     super(message, root, sql);
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.exception.SQLGrammarException
 * JD-Core Version:    0.7.0.1
 */