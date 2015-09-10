/*  1:   */ package org.apache.xml.utils;
/*  2:   */ 
/*  3:   */ public class XMLStringFactoryDefault
/*  4:   */   extends XMLStringFactory
/*  5:   */ {
/*  6:30 */   private static final XMLStringDefault EMPTY_STR = new XMLStringDefault("");
/*  7:   */   
/*  8:   */   public XMLString newstr(String string)
/*  9:   */   {
/* 10:42 */     return new XMLStringDefault(string);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public XMLString newstr(FastStringBuffer fsb, int start, int length)
/* 14:   */   {
/* 15:57 */     return new XMLStringDefault(fsb.getString(start, length));
/* 16:   */   }
/* 17:   */   
/* 18:   */   public XMLString newstr(char[] string, int start, int length)
/* 19:   */   {
/* 20:72 */     return new XMLStringDefault(new String(string, start, length));
/* 21:   */   }
/* 22:   */   
/* 23:   */   public XMLString emptystr()
/* 24:   */   {
/* 25:82 */     return EMPTY_STR;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.XMLStringFactoryDefault
 * JD-Core Version:    0.7.0.1
 */