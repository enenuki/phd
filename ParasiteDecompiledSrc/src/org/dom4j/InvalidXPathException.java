/*  1:   */ package org.dom4j;
/*  2:   */ 
/*  3:   */ public class InvalidXPathException
/*  4:   */   extends IllegalArgumentException
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 3257009869058881592L;
/*  7:   */   
/*  8:   */   public InvalidXPathException(String xpath)
/*  9:   */   {
/* 10:23 */     super("Invalid XPath expression: " + xpath);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public InvalidXPathException(String xpath, String reason)
/* 14:   */   {
/* 15:27 */     super("Invalid XPath expression: " + xpath + " " + reason);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public InvalidXPathException(String xpath, Throwable t)
/* 19:   */   {
/* 20:31 */     super("Invalid XPath expression: '" + xpath + "'. Caused by: " + t.getMessage());
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.InvalidXPathException
 * JD-Core Version:    0.7.0.1
 */