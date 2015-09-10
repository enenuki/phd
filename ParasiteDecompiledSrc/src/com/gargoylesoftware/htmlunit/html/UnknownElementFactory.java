/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.xml.sax.Attributes;
/*  6:   */ 
/*  7:   */ public final class UnknownElementFactory
/*  8:   */   implements IElementFactory
/*  9:   */ {
/* 10:35 */   public static final UnknownElementFactory instance = new UnknownElementFactory();
/* 11:   */   
/* 12:   */   public HtmlElement createElement(SgmlPage page, String tagName, Attributes attributes)
/* 13:   */   {
/* 14:45 */     String namespace = null;
/* 15:46 */     if (((page instanceof HtmlPage)) && (tagName.indexOf(':') != -1))
/* 16:   */     {
/* 17:47 */       HtmlPage htmlPage = (HtmlPage)page;
/* 18:48 */       String prefix = tagName.substring(0, tagName.indexOf(':'));
/* 19:49 */       Map<String, String> namespaces = htmlPage.getNamespaces();
/* 20:50 */       if (namespaces.containsKey(prefix)) {
/* 21:51 */         namespace = (String)namespaces.get(prefix);
/* 22:   */       }
/* 23:   */     }
/* 24:54 */     return createElementNS(page, namespace, tagName, attributes);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public HtmlElement createElementNS(SgmlPage page, String namespaceURI, String qualifiedName, Attributes attributes)
/* 28:   */   {
/* 29:62 */     Map<String, DomAttr> attributeMap = DefaultElementFactory.setAttributes(page, attributes);
/* 30:63 */     return new HtmlUnknownElement(page, namespaceURI, qualifiedName, attributeMap);
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.UnknownElementFactory
 * JD-Core Version:    0.7.0.1
 */