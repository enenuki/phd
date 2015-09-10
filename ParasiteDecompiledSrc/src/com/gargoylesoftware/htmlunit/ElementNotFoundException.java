/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ public class ElementNotFoundException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   private final String elementName_;
/*  7:   */   private final String attributeName_;
/*  8:   */   private final String attributeValue_;
/*  9:   */   
/* 10:   */   public ElementNotFoundException(String elementName, String attributeName, String attributeValue)
/* 11:   */   {
/* 12:38 */     super("elementName=[" + elementName + "] attributeName=[" + attributeName + "] attributeValue=[" + attributeValue + "]");
/* 13:   */     
/* 14:   */ 
/* 15:   */ 
/* 16:42 */     this.elementName_ = elementName;
/* 17:43 */     this.attributeName_ = attributeName;
/* 18:44 */     this.attributeValue_ = attributeValue;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getElementName()
/* 22:   */   {
/* 23:53 */     return this.elementName_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getAttributeName()
/* 27:   */   {
/* 28:62 */     return this.attributeName_;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getAttributeValue()
/* 32:   */   {
/* 33:71 */     return this.attributeValue_;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.ElementNotFoundException
 * JD-Core Version:    0.7.0.1
 */