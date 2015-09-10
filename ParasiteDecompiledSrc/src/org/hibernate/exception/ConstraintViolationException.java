/*  1:   */ package org.hibernate.exception;
/*  2:   */ 
/*  3:   */ import java.sql.SQLException;
/*  4:   */ import org.hibernate.JDBCException;
/*  5:   */ 
/*  6:   */ public class ConstraintViolationException
/*  7:   */   extends JDBCException
/*  8:   */ {
/*  9:   */   private String constraintName;
/* 10:   */   
/* 11:   */   public ConstraintViolationException(String message, SQLException root, String constraintName)
/* 12:   */   {
/* 13:41 */     super(message, root);
/* 14:42 */     this.constraintName = constraintName;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public ConstraintViolationException(String message, SQLException root, String sql, String constraintName)
/* 18:   */   {
/* 19:46 */     super(message, root, sql);
/* 20:47 */     this.constraintName = constraintName;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getConstraintName()
/* 24:   */   {
/* 25:56 */     return this.constraintName;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.exception.ConstraintViolationException
 * JD-Core Version:    0.7.0.1
 */