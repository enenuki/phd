/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*  4:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  5:   */ import com.gargoylesoftware.htmlunit.xml.XmlUtil;
/*  6:   */ 
/*  7:   */ public class XPathNSResolver
/*  8:   */   extends SimpleScriptable
/*  9:   */ {
/* 10:   */   private Object element_;
/* 11:   */   
/* 12:   */   public void setElement(Node element)
/* 13:   */   {
/* 14:37 */     this.element_ = element;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String jsxFunction_lookupNamespaceURI(String prefix)
/* 18:   */   {
/* 19:46 */     return XmlUtil.lookupNamespaceURI((DomElement)((SimpleScriptable)this.element_).getDomNodeOrDie(), prefix);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.XPathNSResolver
 * JD-Core Version:    0.7.0.1
 */