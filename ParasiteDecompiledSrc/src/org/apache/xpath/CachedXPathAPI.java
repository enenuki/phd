/*   1:    */ package org.apache.xpath;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.utils.PrefixResolver;
/*   5:    */ import org.apache.xml.utils.PrefixResolverDefault;
/*   6:    */ import org.apache.xpath.objects.XObject;
/*   7:    */ import org.w3c.dom.Document;
/*   8:    */ import org.w3c.dom.Node;
/*   9:    */ import org.w3c.dom.NodeList;
/*  10:    */ import org.w3c.dom.traversal.NodeIterator;
/*  11:    */ 
/*  12:    */ public class CachedXPathAPI
/*  13:    */ {
/*  14:    */   protected XPathContext xpathSupport;
/*  15:    */   
/*  16:    */   public CachedXPathAPI()
/*  17:    */   {
/*  18: 77 */     this.xpathSupport = new XPathContext(false);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public CachedXPathAPI(CachedXPathAPI priorXPathAPI)
/*  22:    */   {
/*  23: 95 */     this.xpathSupport = priorXPathAPI.xpathSupport;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public XPathContext getXPathContext()
/*  27:    */   {
/*  28:106 */     return this.xpathSupport;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Node selectSingleNode(Node contextNode, String str)
/*  32:    */     throws TransformerException
/*  33:    */   {
/*  34:124 */     return selectSingleNode(contextNode, str, contextNode);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Node selectSingleNode(Node contextNode, String str, Node namespaceNode)
/*  38:    */     throws TransformerException
/*  39:    */   {
/*  40:144 */     NodeIterator nl = selectNodeIterator(contextNode, str, namespaceNode);
/*  41:    */     
/*  42:    */ 
/*  43:147 */     return nl.nextNode();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public NodeIterator selectNodeIterator(Node contextNode, String str)
/*  47:    */     throws TransformerException
/*  48:    */   {
/*  49:163 */     return selectNodeIterator(contextNode, str, contextNode);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public NodeIterator selectNodeIterator(Node contextNode, String str, Node namespaceNode)
/*  53:    */     throws TransformerException
/*  54:    */   {
/*  55:183 */     XObject list = eval(contextNode, str, namespaceNode);
/*  56:    */     
/*  57:    */ 
/*  58:186 */     return list.nodeset();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public NodeList selectNodeList(Node contextNode, String str)
/*  62:    */     throws TransformerException
/*  63:    */   {
/*  64:202 */     return selectNodeList(contextNode, str, contextNode);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public NodeList selectNodeList(Node contextNode, String str, Node namespaceNode)
/*  68:    */     throws TransformerException
/*  69:    */   {
/*  70:222 */     XObject list = eval(contextNode, str, namespaceNode);
/*  71:    */     
/*  72:    */ 
/*  73:225 */     return list.nodelist();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public XObject eval(Node contextNode, String str)
/*  77:    */     throws TransformerException
/*  78:    */   {
/*  79:246 */     return eval(contextNode, str, contextNode);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public XObject eval(Node contextNode, String str, Node namespaceNode)
/*  83:    */     throws TransformerException
/*  84:    */   {
/*  85:283 */     PrefixResolverDefault prefixResolver = new PrefixResolverDefault(namespaceNode.getNodeType() == 9 ? ((Document)namespaceNode).getDocumentElement() : namespaceNode);
/*  86:    */     
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:288 */     XPath xpath = new XPath(str, null, prefixResolver, 0, null);
/*  91:    */     
/*  92:    */ 
/*  93:    */ 
/*  94:292 */     int ctxtNode = this.xpathSupport.getDTMHandleFromNode(contextNode);
/*  95:    */     
/*  96:294 */     return xpath.execute(this.xpathSupport, ctxtNode, prefixResolver);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public XObject eval(Node contextNode, String str, PrefixResolver prefixResolver)
/* 100:    */     throws TransformerException
/* 101:    */   {
/* 102:329 */     XPath xpath = new XPath(str, null, prefixResolver, 0, null);
/* 103:    */     
/* 104:    */ 
/* 105:    */ 
/* 106:333 */     XPathContext xpathSupport = new XPathContext(false);
/* 107:    */     
/* 108:    */ 
/* 109:336 */     int ctxtNode = xpathSupport.getDTMHandleFromNode(contextNode);
/* 110:    */     
/* 111:338 */     return xpath.execute(xpathSupport, ctxtNode, prefixResolver);
/* 112:    */   }
/* 113:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.CachedXPathAPI
 * JD-Core Version:    0.7.0.1
 */