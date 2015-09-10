/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public abstract class FileLineFormatter
/*  4:   */ {
/*  5:12 */   private static FileLineFormatter formatter = new DefaultFileLineFormatter();
/*  6:   */   
/*  7:   */   public static FileLineFormatter getFormatter()
/*  8:   */   {
/*  9:15 */     return formatter;
/* 10:   */   }
/* 11:   */   
/* 12:   */   public static void setFormatter(FileLineFormatter paramFileLineFormatter)
/* 13:   */   {
/* 14:19 */     formatter = paramFileLineFormatter;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public abstract String getFormatString(String paramString, int paramInt1, int paramInt2);
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.FileLineFormatter
 * JD-Core Version:    0.7.0.1
 */