/*  1:   */ package org.hibernate.tool.hbm2ddl;
/*  2:   */ 
/*  3:   */ import java.io.Reader;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.hql.internal.antlr.SqlStatementLexer;
/*  6:   */ import org.hibernate.hql.internal.antlr.SqlStatementParser;
/*  7:   */ 
/*  8:   */ public class MultipleLinesSqlCommandExtractor
/*  9:   */   implements ImportSqlCommandExtractor
/* 10:   */ {
/* 11:   */   public String[] extractCommands(Reader reader)
/* 12:   */   {
/* 13:18 */     SqlStatementLexer lexer = new SqlStatementLexer(reader);
/* 14:19 */     SqlStatementParser parser = new SqlStatementParser(lexer);
/* 15:   */     try
/* 16:   */     {
/* 17:21 */       parser.script();
/* 18:22 */       parser.throwExceptionIfErrorOccurred();
/* 19:   */     }
/* 20:   */     catch (Exception e)
/* 21:   */     {
/* 22:25 */       throw new ImportScriptException("Error during import script parsing.", e);
/* 23:   */     }
/* 24:27 */     List<String> statementList = parser.getStatementList();
/* 25:28 */     return (String[])statementList.toArray(new String[statementList.size()]);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor
 * JD-Core Version:    0.7.0.1
 */