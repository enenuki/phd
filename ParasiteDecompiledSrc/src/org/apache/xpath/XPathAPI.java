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
/*  12:    */ public class XPathAPI
/*  13:    */ {
/*  14:    */   public static Node selectSingleNode(Node contextNode, String str)
/*  15:    */     throws TransformerException
/*  16:    */   {
/*  17: 69 */     return selectSingleNode(contextNode, str, contextNode);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static Node selectSingleNode(Node contextNode, String str, Node namespaceNode)
/*  21:    */     throws TransformerException
/*  22:    */   {
/*  23: 89 */     NodeIterator nl = selectNodeIterator(contextNode, str, namespaceNode);
/*  24:    */     
/*  25:    */ 
/*  26: 92 */     return nl.nextNode();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static NodeIterator selectNodeIterator(Node contextNode, String str)
/*  30:    */     throws TransformerException
/*  31:    */   {
/*  32:108 */     return selectNodeIterator(contextNode, str, contextNode);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static NodeIterator selectNodeIterator(Node contextNode, String str, Node namespaceNode)
/*  36:    */     throws TransformerException
/*  37:    */   {
/*  38:128 */     XObject list = eval(contextNode, str, namespaceNode);
/*  39:    */     
/*  40:    */ 
/*  41:131 */     return list.nodeset();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static NodeList selectNodeList(Node contextNode, String str)
/*  45:    */     throws TransformerException
/*  46:    */   {
/*  47:147 */     return selectNodeList(contextNode, str, contextNode);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static NodeList selectNodeList(Node contextNode, String str, Node namespaceNode)
/*  51:    */     throws TransformerException
/*  52:    */   {
/*  53:167 */     XObject list = eval(contextNode, str, namespaceNode);
/*  54:    */     
/*  55:    */ 
/*  56:170 */     return list.nodelist();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static XObject eval(Node contextNode, String str)
/*  60:    */     throws TransformerException
/*  61:    */   {
/*  62:191 */     return eval(contextNode, str, contextNode);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static XObject eval(Node contextNode, String str, Node namespaceNode)
/*  66:    */     throws TransformerException
/*  67:    */   {
/*  68:225 */     XPathContext xpathSupport = new XPathContext(false);
/*  69:    */     
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:231 */     PrefixResolverDefault prefixResolver = new PrefixResolverDefault(namespaceNode.getNodeType() == 9 ? ((Document)namespaceNode).getDocumentElement() : namespaceNode);
/*  75:    */     
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:236 */     XPath xpath = new XPath(str, null, prefixResolver, 0, null);
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:240 */     int ctxtNode = xpathSupport.getDTMHandleFromNode(contextNode);
/*  84:    */     
/*  85:242 */     return xpath.execute(xpathSupport, ctxtNode, prefixResolver);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static XObject eval(Node contextNode, String str, PrefixResolver prefixResolver)
/*  89:    */     throws TransformerException
/*  90:    */   {
/*  91:277 */     XPath xpath = new XPath(str, null, prefixResolver, 0, null);
/*  92:    */     
/*  93:    */ 
/*  94:    */ 
/*  95:281 */     XPathContext xpathSupport = new XPathContext(false);
/*  96:    */     
/*  97:    */ 
/*  98:284 */     int ctxtNode = xpathSupport.getDTMHandleFromNode(contextNode);
/*  99:    */     
/* 100:286 */     return xpath.execute(xpathSupport, ctxtNode, prefixResolver);
/* 101:    */   }
/* 102:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.XPathAPI
 * JD-Core Version:    0.7.0.1
 */