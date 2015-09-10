/*   1:    */ package org.apache.xpath.domapi;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xpath.XPath;
/*   5:    */ import org.apache.xpath.XPathContext;
/*   6:    */ import org.apache.xpath.objects.XObject;
/*   7:    */ import org.apache.xpath.res.XPATHMessages;
/*   8:    */ import org.w3c.dom.DOMException;
/*   9:    */ import org.w3c.dom.Document;
/*  10:    */ import org.w3c.dom.Node;
/*  11:    */ import org.w3c.dom.xpath.XPathException;
/*  12:    */ import org.w3c.dom.xpath.XPathExpression;
/*  13:    */ 
/*  14:    */ class XPathExpressionImpl
/*  15:    */   implements XPathExpression
/*  16:    */ {
/*  17:    */   private final XPath m_xpath;
/*  18:    */   private final Document m_doc;
/*  19:    */   
/*  20:    */   XPathExpressionImpl(XPath xpath, Document doc)
/*  21:    */   {
/*  22: 74 */     this.m_xpath = xpath;
/*  23: 75 */     this.m_doc = doc;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Object evaluate(Node contextNode, short type, Object result)
/*  27:    */     throws XPathException, DOMException
/*  28:    */   {
/*  29:129 */     if (this.m_doc != null)
/*  30:    */     {
/*  31:132 */       if ((contextNode != this.m_doc) && (!contextNode.getOwnerDocument().equals(this.m_doc)))
/*  32:    */       {
/*  33:133 */         String fmsg = XPATHMessages.createXPATHMessage("ER_WRONG_DOCUMENT", null);
/*  34:134 */         throw new DOMException((short)4, fmsg);
/*  35:    */       }
/*  36:138 */       short nodeType = contextNode.getNodeType();
/*  37:139 */       if ((nodeType != 9) && (nodeType != 1) && (nodeType != 2) && (nodeType != 3) && (nodeType != 4) && (nodeType != 8) && (nodeType != 7) && (nodeType != 13))
/*  38:    */       {
/*  39:147 */         String fmsg = XPATHMessages.createXPATHMessage("ER_WRONG_NODETYPE", null);
/*  40:148 */         throw new DOMException((short)9, fmsg);
/*  41:    */       }
/*  42:    */     }
/*  43:155 */     if (!XPathResultImpl.isValidType(type))
/*  44:    */     {
/*  45:156 */       String fmsg = XPATHMessages.createXPATHMessage("ER_INVALID_XPATH_TYPE", new Object[] { new Integer(type) });
/*  46:157 */       throw new XPathException((short)52, fmsg);
/*  47:    */     }
/*  48:164 */     XPathContext xpathSupport = new XPathContext(false);
/*  49:167 */     if (null != this.m_doc) {
/*  50:168 */       xpathSupport.getDTMHandleFromNode(this.m_doc);
/*  51:    */     }
/*  52:171 */     XObject xobj = null;
/*  53:    */     try
/*  54:    */     {
/*  55:173 */       xobj = this.m_xpath.execute(xpathSupport, contextNode, null);
/*  56:    */     }
/*  57:    */     catch (TransformerException te)
/*  58:    */     {
/*  59:176 */       throw new XPathException((short)51, te.getMessageAndLocation());
/*  60:    */     }
/*  61:183 */     return new XPathResultImpl(type, xobj, contextNode, this.m_xpath);
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.domapi.XPathExpressionImpl
 * JD-Core Version:    0.7.0.1
 */