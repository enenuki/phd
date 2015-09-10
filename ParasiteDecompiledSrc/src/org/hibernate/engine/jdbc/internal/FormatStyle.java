/*  1:   */ package org.hibernate.engine.jdbc.internal;
/*  2:   */ 
/*  3:   */ public enum FormatStyle
/*  4:   */ {
/*  5:33 */   BASIC("basic", new BasicFormatterImpl()),  DDL("ddl", new DDLFormatterImpl()),  NONE("none", new NoFormatImpl(null));
/*  6:   */   
/*  7:   */   private final String name;
/*  8:   */   private final Formatter formatter;
/*  9:   */   
/* 10:   */   private FormatStyle(String name, Formatter formatter)
/* 11:   */   {
/* 12:41 */     this.name = name;
/* 13:42 */     this.formatter = formatter;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:46 */     return this.name;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Formatter getFormatter()
/* 22:   */   {
/* 23:50 */     return this.formatter;
/* 24:   */   }
/* 25:   */   
/* 26:   */   private static class NoFormatImpl
/* 27:   */     implements Formatter
/* 28:   */   {
/* 29:   */     public String format(String source)
/* 30:   */     {
/* 31:55 */       return source;
/* 32:   */     }
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.FormatStyle
 * JD-Core Version:    0.7.0.1
 */