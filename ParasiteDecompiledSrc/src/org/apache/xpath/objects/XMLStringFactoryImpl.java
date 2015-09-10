/*  1:   */ package org.apache.xpath.objects;
/*  2:   */ 
/*  3:   */ import org.apache.xml.utils.FastStringBuffer;
/*  4:   */ import org.apache.xml.utils.XMLString;
/*  5:   */ import org.apache.xml.utils.XMLStringFactory;
/*  6:   */ 
/*  7:   */ public class XMLStringFactoryImpl
/*  8:   */   extends XMLStringFactory
/*  9:   */ {
/* 10:34 */   private static XMLStringFactory m_xstringfactory = new XMLStringFactoryImpl();
/* 11:   */   
/* 12:   */   public static XMLStringFactory getFactory()
/* 13:   */   {
/* 14:45 */     return m_xstringfactory;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public XMLString newstr(String string)
/* 18:   */   {
/* 19:58 */     return new XString(string);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public XMLString newstr(FastStringBuffer fsb, int start, int length)
/* 23:   */   {
/* 24:73 */     return new XStringForFSB(fsb, start, length);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public XMLString newstr(char[] string, int start, int length)
/* 28:   */   {
/* 29:88 */     return new XStringForChars(string, start, length);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public XMLString emptystr()
/* 33:   */   {
/* 34:98 */     return XString.EMPTYSTRING;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XMLStringFactoryImpl
 * JD-Core Version:    0.7.0.1
 */