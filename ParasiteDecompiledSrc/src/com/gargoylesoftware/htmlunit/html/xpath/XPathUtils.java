/*   1:    */ package com.gargoylesoftware.htmlunit.html.xpath;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.xml.transform.TransformerException;
/*   8:    */ import org.apache.xml.utils.PrefixResolver;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.apache.xpath.objects.XBoolean;
/*  11:    */ import org.apache.xpath.objects.XNodeSet;
/*  12:    */ import org.apache.xpath.objects.XNumber;
/*  13:    */ import org.apache.xpath.objects.XObject;
/*  14:    */ import org.apache.xpath.objects.XString;
/*  15:    */ import org.w3c.dom.Document;
/*  16:    */ import org.w3c.dom.Node;
/*  17:    */ import org.w3c.dom.NodeList;
/*  18:    */ 
/*  19:    */ public final class XPathUtils
/*  20:    */ {
/*  21: 43 */   private static ThreadLocal<Boolean> PROCESS_XPATH_ = new ThreadLocal()
/*  22:    */   {
/*  23:    */     protected synchronized Boolean initialValue()
/*  24:    */     {
/*  25: 46 */       return Boolean.FALSE;
/*  26:    */     }
/*  27:    */   };
/*  28:    */   
/*  29:    */   public static List<Object> getByXPath(DomNode node, String xpathExpr)
/*  30:    */   {
/*  31: 65 */     if (xpathExpr == null) {
/*  32: 66 */       throw new NullPointerException("Null is not a valid XPath expression");
/*  33:    */     }
/*  34: 69 */     PROCESS_XPATH_.set(Boolean.TRUE);
/*  35: 70 */     List<Object> list = new ArrayList();
/*  36:    */     try
/*  37:    */     {
/*  38: 72 */       XObject result = evaluateXPath(node, xpathExpr);
/*  39: 74 */       if ((result instanceof XNodeSet))
/*  40:    */       {
/*  41: 75 */         NodeList nodelist = ((XNodeSet)result).nodelist();
/*  42: 76 */         for (int i = 0; i < nodelist.getLength(); i++) {
/*  43: 77 */           list.add(nodelist.item(i));
/*  44:    */         }
/*  45:    */       }
/*  46: 80 */       else if ((result instanceof XNumber))
/*  47:    */       {
/*  48: 81 */         list.add(Double.valueOf(result.num()));
/*  49:    */       }
/*  50: 83 */       else if ((result instanceof XBoolean))
/*  51:    */       {
/*  52: 84 */         list.add(Boolean.valueOf(result.bool()));
/*  53:    */       }
/*  54: 86 */       else if ((result instanceof XString))
/*  55:    */       {
/*  56: 87 */         list.add(result.str());
/*  57:    */       }
/*  58:    */       else
/*  59:    */       {
/*  60: 90 */         throw new RuntimeException("Unproccessed " + result.getClass().getName());
/*  61:    */       }
/*  62:    */     }
/*  63:    */     catch (Exception e)
/*  64:    */     {
/*  65: 94 */       throw new RuntimeException("Could not retrieve XPath >" + xpathExpr + "< on " + node, e);
/*  66:    */     }
/*  67:    */     finally
/*  68:    */     {
/*  69: 97 */       PROCESS_XPATH_.set(Boolean.FALSE);
/*  70:    */     }
/*  71: 99 */     return list;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static boolean isProcessingXPath()
/*  75:    */   {
/*  76:107 */     return ((Boolean)PROCESS_XPATH_.get()).booleanValue();
/*  77:    */   }
/*  78:    */   
/*  79:    */   private static XObject evaluateXPath(DomNode contextNode, String str)
/*  80:    */     throws TransformerException
/*  81:    */   {
/*  82:118 */     XPathContext xpathSupport = new XPathContext();
/*  83:    */     Node xpathExpressionContext;
/*  84:    */     Node xpathExpressionContext;
/*  85:120 */     if (contextNode.getNodeType() == 9) {
/*  86:121 */       xpathExpressionContext = ((Document)contextNode).getDocumentElement();
/*  87:    */     } else {
/*  88:124 */       xpathExpressionContext = contextNode;
/*  89:    */     }
/*  90:126 */     PrefixResolver prefixResolver = new HtmlUnitPrefixResolver(xpathExpressionContext);
/*  91:127 */     boolean caseSensitive = contextNode.getPage().hasCaseSensitiveTagNames();
/*  92:128 */     XPathAdapter xpath = new XPathAdapter(str, null, prefixResolver, null, caseSensitive);
/*  93:129 */     int ctxtNode = xpathSupport.getDTMHandleFromNode(contextNode);
/*  94:130 */     return xpath.execute(xpathSupport, ctxtNode, prefixResolver);
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.xpath.XPathUtils
 * JD-Core Version:    0.7.0.1
 */