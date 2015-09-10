/*  1:   */ package org.apache.xpath.domapi;
/*  2:   */ 
/*  3:   */ import org.apache.xml.utils.PrefixResolverDefault;
/*  4:   */ import org.w3c.dom.Node;
/*  5:   */ import org.w3c.dom.xpath.XPathNSResolver;
/*  6:   */ 
/*  7:   */ class XPathNSResolverImpl
/*  8:   */   extends PrefixResolverDefault
/*  9:   */   implements XPathNSResolver
/* 10:   */ {
/* 11:   */   public XPathNSResolverImpl(Node xpathExpressionContext)
/* 12:   */   {
/* 13:51 */     super(xpathExpressionContext);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String lookupNamespaceURI(String prefix)
/* 17:   */   {
/* 18:58 */     return super.getNamespaceForPrefix(prefix);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.domapi.XPathNSResolverImpl
 * JD-Core Version:    0.7.0.1
 */