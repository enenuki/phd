/*  1:   */ package org.hibernate.hql.internal.ast;
/*  2:   */ 
/*  3:   */ import antlr.Token;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.io.Reader;
/*  6:   */ import org.hibernate.QueryException;
/*  7:   */ import org.hibernate.hql.internal.antlr.HqlBaseLexer;
/*  8:   */ 
/*  9:   */ class HqlLexer
/* 10:   */   extends HqlBaseLexer
/* 11:   */ {
/* 12:42 */   private boolean possibleID = false;
/* 13:   */   
/* 14:   */   public HqlLexer(InputStream in)
/* 15:   */   {
/* 16:45 */     super(in);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public HqlLexer(Reader in)
/* 20:   */   {
/* 21:49 */     super(in);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setTokenObjectClass(String cl)
/* 25:   */   {
/* 26:53 */     Thread thread = null;
/* 27:54 */     ClassLoader contextClassLoader = null;
/* 28:   */     try
/* 29:   */     {
/* 30:57 */       thread = Thread.currentThread();
/* 31:58 */       contextClassLoader = thread.getContextClassLoader();
/* 32:59 */       thread.setContextClassLoader(HqlToken.class.getClassLoader());
/* 33:   */       
/* 34:   */ 
/* 35:62 */       super.setTokenObjectClass(HqlToken.class.getName());
/* 36:   */     }
/* 37:   */     finally
/* 38:   */     {
/* 39:65 */       thread.setContextClassLoader(contextClassLoader);
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   protected void setPossibleID(boolean possibleID)
/* 44:   */   {
/* 45:70 */     this.possibleID = possibleID;
/* 46:   */   }
/* 47:   */   
/* 48:   */   protected Token makeToken(int i)
/* 49:   */   {
/* 50:74 */     HqlToken token = (HqlToken)super.makeToken(i);
/* 51:75 */     token.setPossibleID(this.possibleID);
/* 52:76 */     this.possibleID = false;
/* 53:77 */     return token;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public int testLiteralsTable(int i)
/* 57:   */   {
/* 58:81 */     int ttype = super.testLiteralsTable(i);
/* 59:82 */     return ttype;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void panic()
/* 63:   */   {
/* 64:87 */     panic("CharScanner: panic");
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void panic(String s)
/* 68:   */   {
/* 69:92 */     throw new QueryException(s);
/* 70:   */   }
/* 71:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.HqlLexer
 * JD-Core Version:    0.7.0.1
 */