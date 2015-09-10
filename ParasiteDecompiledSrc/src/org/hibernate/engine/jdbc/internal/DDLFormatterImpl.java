/*   1:    */ package org.hibernate.engine.jdbc.internal;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import org.hibernate.internal.util.StringHelper;
/*   5:    */ 
/*   6:    */ public class DDLFormatterImpl
/*   7:    */   implements Formatter
/*   8:    */ {
/*   9:    */   public String format(String sql)
/*  10:    */   {
/*  11: 48 */     if (StringHelper.isEmpty(sql)) {
/*  12: 48 */       return sql;
/*  13:    */     }
/*  14: 49 */     if (sql.toLowerCase().startsWith("create table")) {
/*  15: 50 */       return formatCreateTable(sql);
/*  16:    */     }
/*  17: 52 */     if (sql.toLowerCase().startsWith("alter table")) {
/*  18: 53 */       return formatAlterTable(sql);
/*  19:    */     }
/*  20: 55 */     if (sql.toLowerCase().startsWith("comment on")) {
/*  21: 56 */       return formatCommentOn(sql);
/*  22:    */     }
/*  23: 59 */     return "\n    " + sql;
/*  24:    */   }
/*  25:    */   
/*  26:    */   private String formatCommentOn(String sql)
/*  27:    */   {
/*  28: 64 */     StringBuffer result = new StringBuffer(60).append("\n    ");
/*  29: 65 */     StringTokenizer tokens = new StringTokenizer(sql, " '[]\"", true);
/*  30:    */     
/*  31: 67 */     boolean quoted = false;
/*  32: 68 */     while (tokens.hasMoreTokens())
/*  33:    */     {
/*  34: 69 */       String token = tokens.nextToken();
/*  35: 70 */       result.append(token);
/*  36: 71 */       if (isQuote(token)) {
/*  37: 72 */         quoted = !quoted;
/*  38: 74 */       } else if ((!quoted) && 
/*  39: 75 */         ("is".equals(token))) {
/*  40: 76 */         result.append("\n       ");
/*  41:    */       }
/*  42:    */     }
/*  43: 81 */     return result.toString();
/*  44:    */   }
/*  45:    */   
/*  46:    */   private String formatAlterTable(String sql)
/*  47:    */   {
/*  48: 85 */     StringBuffer result = new StringBuffer(60).append("\n    ");
/*  49: 86 */     StringTokenizer tokens = new StringTokenizer(sql, " (,)'[]\"", true);
/*  50:    */     
/*  51: 88 */     boolean quoted = false;
/*  52: 89 */     while (tokens.hasMoreTokens())
/*  53:    */     {
/*  54: 90 */       String token = tokens.nextToken();
/*  55: 91 */       if (isQuote(token)) {
/*  56: 92 */         quoted = !quoted;
/*  57: 94 */       } else if ((!quoted) && 
/*  58: 95 */         (isBreak(token))) {
/*  59: 96 */         result.append("\n        ");
/*  60:    */       }
/*  61: 99 */       result.append(token);
/*  62:    */     }
/*  63:102 */     return result.toString();
/*  64:    */   }
/*  65:    */   
/*  66:    */   private String formatCreateTable(String sql)
/*  67:    */   {
/*  68:106 */     StringBuffer result = new StringBuffer(60).append("\n    ");
/*  69:107 */     StringTokenizer tokens = new StringTokenizer(sql, "(,)'[]\"", true);
/*  70:    */     
/*  71:109 */     int depth = 0;
/*  72:110 */     boolean quoted = false;
/*  73:111 */     while (tokens.hasMoreTokens())
/*  74:    */     {
/*  75:112 */       String token = tokens.nextToken();
/*  76:113 */       if (isQuote(token))
/*  77:    */       {
/*  78:114 */         quoted = !quoted;
/*  79:115 */         result.append(token);
/*  80:    */       }
/*  81:117 */       else if (quoted)
/*  82:    */       {
/*  83:118 */         result.append(token);
/*  84:    */       }
/*  85:    */       else
/*  86:    */       {
/*  87:121 */         if (")".equals(token))
/*  88:    */         {
/*  89:122 */           depth--;
/*  90:123 */           if (depth == 0) {
/*  91:124 */             result.append("\n    ");
/*  92:    */           }
/*  93:    */         }
/*  94:127 */         result.append(token);
/*  95:128 */         if ((",".equals(token)) && (depth == 1)) {
/*  96:129 */           result.append("\n       ");
/*  97:    */         }
/*  98:131 */         if ("(".equals(token))
/*  99:    */         {
/* 100:132 */           depth++;
/* 101:133 */           if (depth == 1) {
/* 102:134 */             result.append("\n        ");
/* 103:    */           }
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107:140 */     return result.toString();
/* 108:    */   }
/* 109:    */   
/* 110:    */   private static boolean isBreak(String token)
/* 111:    */   {
/* 112:144 */     return ("drop".equals(token)) || ("add".equals(token)) || ("references".equals(token)) || ("foreign".equals(token)) || ("on".equals(token));
/* 113:    */   }
/* 114:    */   
/* 115:    */   private static boolean isQuote(String tok)
/* 116:    */   {
/* 117:152 */     return ("\"".equals(tok)) || ("`".equals(tok)) || ("]".equals(tok)) || ("[".equals(tok)) || ("'".equals(tok));
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.DDLFormatterImpl
 * JD-Core Version:    0.7.0.1
 */