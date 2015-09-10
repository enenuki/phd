/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.regexp;
/*  2:   */ 
/*  3:   */ public class SubString
/*  4:   */ {
/*  5:   */   public SubString() {}
/*  6:   */   
/*  7:   */   public SubString(String str)
/*  8:   */   {
/*  9:51 */     this.str = str;
/* 10:52 */     this.index = 0;
/* 11:53 */     this.length = str.length();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public SubString(String source, int start, int len)
/* 15:   */   {
/* 16:58 */     this.str = source;
/* 17:59 */     this.index = start;
/* 18:60 */     this.length = len;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String toString()
/* 22:   */   {
/* 23:65 */     return this.str == null ? "" : this.str.substring(this.index, this.index + this.length);
/* 24:   */   }
/* 25:   */   
/* 26:70 */   public static final SubString emptySubString = new SubString();
/* 27:   */   String str;
/* 28:   */   int index;
/* 29:   */   int length;
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.regexp.SubString
 * JD-Core Version:    0.7.0.1
 */