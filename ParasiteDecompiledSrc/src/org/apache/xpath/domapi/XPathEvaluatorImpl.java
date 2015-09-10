/*   1:    */ package org.apache.xpath.domapi;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.utils.PrefixResolver;
/*   5:    */ import org.apache.xpath.XPath;
/*   6:    */ import org.apache.xpath.res.XPATHMessages;
/*   7:    */ import org.w3c.dom.DOMException;
/*   8:    */ import org.w3c.dom.Document;
/*   9:    */ import org.w3c.dom.Node;
/*  10:    */ import org.w3c.dom.xpath.XPathEvaluator;
/*  11:    */ import org.w3c.dom.xpath.XPathException;
/*  12:    */ import org.w3c.dom.xpath.XPathExpression;
/*  13:    */ import org.w3c.dom.xpath.XPathNSResolver;
/*  14:    */ 
/*  15:    */ public final class XPathEvaluatorImpl
/*  16:    */   implements XPathEvaluator
/*  17:    */ {
/*  18:    */   private final Document m_doc;
/*  19:    */   
/*  20:    */   private class DummyPrefixResolver
/*  21:    */     implements PrefixResolver
/*  22:    */   {
/*  23:    */     DummyPrefixResolver() {}
/*  24:    */     
/*  25:    */     public String getNamespaceForPrefix(String prefix, Node context)
/*  26:    */     {
/*  27: 81 */       String fmsg = XPATHMessages.createXPATHMessage("ER_NULL_RESOLVER", null);
/*  28: 82 */       throw new DOMException((short)14, fmsg);
/*  29:    */     }
/*  30:    */     
/*  31:    */     public String getNamespaceForPrefix(String prefix)
/*  32:    */     {
/*  33: 92 */       return getNamespaceForPrefix(prefix, null);
/*  34:    */     }
/*  35:    */     
/*  36:    */     public boolean handlesNullPrefixes()
/*  37:    */     {
/*  38: 99 */       return false;
/*  39:    */     }
/*  40:    */     
/*  41:    */     public String getBaseIdentifier()
/*  42:    */     {
/*  43:106 */       return null;
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public XPathEvaluatorImpl(Document doc)
/*  48:    */   {
/*  49:124 */     this.m_doc = doc;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public XPathEvaluatorImpl()
/*  53:    */   {
/*  54:133 */     this.m_doc = null;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public XPathExpression createExpression(String expression, XPathNSResolver resolver)
/*  58:    */     throws XPathException, DOMException
/*  59:    */   {
/*  60:    */     try
/*  61:    */     {
/*  62:168 */       XPath xpath = new XPath(expression, null, null == resolver ? new DummyPrefixResolver() : (PrefixResolver)resolver, 0);
/*  63:    */       
/*  64:    */ 
/*  65:    */ 
/*  66:172 */       return new XPathExpressionImpl(xpath, this.m_doc);
/*  67:    */     }
/*  68:    */     catch (TransformerException e)
/*  69:    */     {
/*  70:177 */       if ((e instanceof XPathStylesheetDOM3Exception)) {
/*  71:178 */         throw new DOMException((short)14, e.getMessageAndLocation());
/*  72:    */       }
/*  73:180 */       throw new XPathException((short)51, e.getMessageAndLocation());
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public XPathNSResolver createNSResolver(Node nodeResolver)
/*  78:    */   {
/*  79:203 */     return new XPathNSResolverImpl(nodeResolver.getNodeType() == 9 ? ((Document)nodeResolver).getDocumentElement() : nodeResolver);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Object evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result)
/*  83:    */     throws XPathException, DOMException
/*  84:    */   {
/*  85:266 */     XPathExpression xpathExpression = createExpression(expression, resolver);
/*  86:    */     
/*  87:268 */     return xpathExpression.evaluate(contextNode, type, result);
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.domapi.XPathEvaluatorImpl
 * JD-Core Version:    0.7.0.1
 */