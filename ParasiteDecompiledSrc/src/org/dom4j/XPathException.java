/*  1:   */ package org.dom4j;
/*  2:   */ 
/*  3:   */ public class XPathException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   private String xpath;
/*  7:   */   
/*  8:   */   public XPathException(String xpath)
/*  9:   */   {
/* 10:24 */     super("Exception occurred evaluting XPath: " + xpath);
/* 11:25 */     this.xpath = xpath;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public XPathException(String xpath, String reason)
/* 15:   */   {
/* 16:29 */     super("Exception occurred evaluting XPath: " + xpath + " " + reason);
/* 17:30 */     this.xpath = xpath;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public XPathException(String xpath, Exception e)
/* 21:   */   {
/* 22:34 */     super("Exception occurred evaluting XPath: " + xpath + ". Exception: " + e.getMessage());
/* 23:   */     
/* 24:36 */     this.xpath = xpath;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getXPath()
/* 28:   */   {
/* 29:45 */     return this.xpath;
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.XPathException
 * JD-Core Version:    0.7.0.1
 */