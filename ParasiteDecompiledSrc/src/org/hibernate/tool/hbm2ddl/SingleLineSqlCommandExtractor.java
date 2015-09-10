/*  1:   */ package org.hibernate.tool.hbm2ddl;
/*  2:   */ 
/*  3:   */ import java.io.BufferedReader;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.Reader;
/*  6:   */ import java.util.LinkedList;
/*  7:   */ import java.util.List;
/*  8:   */ import org.hibernate.internal.util.StringHelper;
/*  9:   */ 
/* 10:   */ public class SingleLineSqlCommandExtractor
/* 11:   */   implements ImportSqlCommandExtractor
/* 12:   */ {
/* 13:   */   public String[] extractCommands(Reader reader)
/* 14:   */   {
/* 15:20 */     BufferedReader bufferedReader = new BufferedReader(reader);
/* 16:21 */     List<String> statementList = new LinkedList();
/* 17:   */     try
/* 18:   */     {
/* 19:23 */       for (String sql = bufferedReader.readLine(); sql != null; sql = bufferedReader.readLine())
/* 20:   */       {
/* 21:24 */         String trimmedSql = sql.trim();
/* 22:25 */         if ((!StringHelper.isEmpty(trimmedSql)) && (!isComment(trimmedSql)))
/* 23:   */         {
/* 24:28 */           if (trimmedSql.endsWith(";")) {
/* 25:29 */             trimmedSql = trimmedSql.substring(0, trimmedSql.length() - 1);
/* 26:   */           }
/* 27:31 */           statementList.add(trimmedSql);
/* 28:   */         }
/* 29:   */       }
/* 30:33 */       return (String[])statementList.toArray(new String[statementList.size()]);
/* 31:   */     }
/* 32:   */     catch (IOException e)
/* 33:   */     {
/* 34:36 */       throw new ImportScriptException("Error during import script parsing.", e);
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   private boolean isComment(String line)
/* 39:   */   {
/* 40:41 */     return (line.startsWith("--")) || (line.startsWith("//")) || (line.startsWith("/*"));
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.SingleLineSqlCommandExtractor
 * JD-Core Version:    0.7.0.1
 */