/*   1:    */ package org.hibernate.engine.query.spi;
/*   2:    */ 
/*   3:    */ import org.hibernate.QueryException;
/*   4:    */ import org.hibernate.internal.util.StringHelper;
/*   5:    */ 
/*   6:    */ public class ParameterParser
/*   7:    */ {
/*   8:    */   public static void parse(String sqlString, Recognizer recognizer)
/*   9:    */     throws QueryException
/*  10:    */   {
/*  11: 67 */     boolean hasMainOutputParameter = startsWithEscapeCallTemplate(sqlString);
/*  12: 68 */     boolean foundMainOutputParam = false;
/*  13:    */     
/*  14: 70 */     int stringLength = sqlString.length();
/*  15: 71 */     boolean inQuote = false;
/*  16: 72 */     for (int indx = 0; indx < stringLength; indx++)
/*  17:    */     {
/*  18: 73 */       char c = sqlString.charAt(indx);
/*  19: 74 */       if (inQuote)
/*  20:    */       {
/*  21: 75 */         if ('\'' == c) {
/*  22: 76 */           inQuote = false;
/*  23:    */         }
/*  24: 78 */         recognizer.other(c);
/*  25:    */       }
/*  26: 80 */       else if ('\'' == c)
/*  27:    */       {
/*  28: 81 */         inQuote = true;
/*  29: 82 */         recognizer.other(c);
/*  30:    */       }
/*  31: 85 */       else if (c == ':')
/*  32:    */       {
/*  33: 87 */         int right = StringHelper.firstIndexOfChar(sqlString, " \n\r\f\t,()=<>&|+-=/*'^![]#~\\", indx + 1);
/*  34: 88 */         int chopLocation = right < 0 ? sqlString.length() : right;
/*  35: 89 */         String param = sqlString.substring(indx + 1, chopLocation);
/*  36: 90 */         if (StringHelper.isEmpty(param)) {
/*  37: 91 */           throw new QueryException("Space is not allowed after parameter prefix ':' [" + sqlString + "]");
/*  38:    */         }
/*  39: 95 */         recognizer.namedParameter(param, indx);
/*  40: 96 */         indx = chopLocation - 1;
/*  41:    */       }
/*  42: 98 */       else if (c == '?')
/*  43:    */       {
/*  44:100 */         if ((indx < stringLength - 1) && (Character.isDigit(sqlString.charAt(indx + 1))))
/*  45:    */         {
/*  46:102 */           int right = StringHelper.firstIndexOfChar(sqlString, " \n\r\f\t,()=<>&|+-=/*'^![]#~\\", indx + 1);
/*  47:103 */           int chopLocation = right < 0 ? sqlString.length() : right;
/*  48:104 */           String param = sqlString.substring(indx + 1, chopLocation);
/*  49:    */           try
/*  50:    */           {
/*  51:107 */             Integer.valueOf(param);
/*  52:    */           }
/*  53:    */           catch (NumberFormatException e)
/*  54:    */           {
/*  55:110 */             throw new QueryException("JPA-style positional param was not an integral ordinal");
/*  56:    */           }
/*  57:112 */           recognizer.jpaPositionalParameter(param, indx);
/*  58:113 */           indx = chopLocation - 1;
/*  59:    */         }
/*  60:116 */         else if ((hasMainOutputParameter) && (!foundMainOutputParam))
/*  61:    */         {
/*  62:117 */           foundMainOutputParam = true;
/*  63:118 */           recognizer.outParameter(indx);
/*  64:    */         }
/*  65:    */         else
/*  66:    */         {
/*  67:121 */           recognizer.ordinalParameter(indx);
/*  68:    */         }
/*  69:    */       }
/*  70:    */       else
/*  71:    */       {
/*  72:126 */         recognizer.other(c);
/*  73:    */       }
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static boolean startsWithEscapeCallTemplate(String sqlString)
/*  78:    */   {
/*  79:133 */     if ((!sqlString.startsWith("{")) || (!sqlString.endsWith("}"))) {
/*  80:134 */       return false;
/*  81:    */     }
/*  82:137 */     int chopLocation = sqlString.indexOf("call");
/*  83:138 */     if (chopLocation <= 0) {
/*  84:139 */       return false;
/*  85:    */     }
/*  86:142 */     String checkString = sqlString.substring(1, chopLocation + 4);
/*  87:143 */     String fixture = "?=call";
/*  88:144 */     int fixturePosition = 0;
/*  89:145 */     boolean matches = true;
/*  90:146 */     int i = 0;
/*  91:146 */     for (int max = checkString.length(); i < max; i++)
/*  92:    */     {
/*  93:147 */       char c = Character.toLowerCase(checkString.charAt(i));
/*  94:148 */       if (!Character.isWhitespace(c)) {
/*  95:151 */         if (c == "?=call".charAt(fixturePosition))
/*  96:    */         {
/*  97:152 */           fixturePosition++;
/*  98:    */         }
/*  99:    */         else
/* 100:    */         {
/* 101:155 */           matches = false;
/* 102:156 */           break;
/* 103:    */         }
/* 104:    */       }
/* 105:    */     }
/* 106:159 */     return matches;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static abstract interface Recognizer
/* 110:    */   {
/* 111:    */     public abstract void outParameter(int paramInt);
/* 112:    */     
/* 113:    */     public abstract void ordinalParameter(int paramInt);
/* 114:    */     
/* 115:    */     public abstract void namedParameter(String paramString, int paramInt);
/* 116:    */     
/* 117:    */     public abstract void jpaPositionalParameter(String paramString, int paramInt);
/* 118:    */     
/* 119:    */     public abstract void other(char paramChar);
/* 120:    */   }
/* 121:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.ParameterParser
 * JD-Core Version:    0.7.0.1
 */