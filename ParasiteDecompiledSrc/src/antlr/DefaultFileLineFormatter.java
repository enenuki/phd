/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class DefaultFileLineFormatter
/*  4:   */   extends FileLineFormatter
/*  5:   */ {
/*  6:   */   public String getFormatString(String paramString, int paramInt1, int paramInt2)
/*  7:   */   {
/*  8:12 */     StringBuffer localStringBuffer = new StringBuffer();
/*  9:14 */     if (paramString != null) {
/* 10:15 */       localStringBuffer.append(paramString + ":");
/* 11:   */     }
/* 12:17 */     if (paramInt1 != -1)
/* 13:   */     {
/* 14:18 */       if (paramString == null) {
/* 15:19 */         localStringBuffer.append("line ");
/* 16:   */       }
/* 17:21 */       localStringBuffer.append(paramInt1);
/* 18:23 */       if (paramInt2 != -1) {
/* 19:24 */         localStringBuffer.append(":" + paramInt2);
/* 20:   */       }
/* 21:26 */       localStringBuffer.append(":");
/* 22:   */     }
/* 23:29 */     localStringBuffer.append(" ");
/* 24:   */     
/* 25:31 */     return localStringBuffer.toString();
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.DefaultFileLineFormatter
 * JD-Core Version:    0.7.0.1
 */