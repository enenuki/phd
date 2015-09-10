/*   1:    */ package org.apache.xalan.extensions;
/*   2:    */ 
/*   3:    */ import javax.xml.namespace.QName;
/*   4:    */ import javax.xml.xpath.XPathFunction;
/*   5:    */ import javax.xml.xpath.XPathFunctionResolver;
/*   6:    */ import org.apache.xalan.res.XSLMessages;
/*   7:    */ 
/*   8:    */ public class XPathFunctionResolverImpl
/*   9:    */   implements XPathFunctionResolver
/*  10:    */ {
/*  11:    */   public XPathFunction resolveFunction(QName qname, int arity)
/*  12:    */   {
/*  13: 38 */     if (qname == null) {
/*  14: 39 */       throw new NullPointerException(XSLMessages.createMessage("ER_XPATH_RESOLVER_NULL_QNAME", null));
/*  15:    */     }
/*  16: 43 */     if (arity < 0) {
/*  17: 44 */       throw new IllegalArgumentException(XSLMessages.createMessage("ER_XPATH_RESOLVER_NEGATIVE_ARITY", null));
/*  18:    */     }
/*  19: 48 */     String uri = qname.getNamespaceURI();
/*  20: 49 */     if ((uri == null) || (uri.length() == 0)) {
/*  21: 50 */       return null;
/*  22:    */     }
/*  23: 52 */     String className = null;
/*  24: 53 */     String methodName = null;
/*  25: 54 */     if (uri.startsWith("http://exslt.org"))
/*  26:    */     {
/*  27: 56 */       className = getEXSLTClassName(uri);
/*  28: 57 */       methodName = qname.getLocalPart();
/*  29:    */     }
/*  30: 59 */     else if (!uri.equals("http://xml.apache.org/xalan/java"))
/*  31:    */     {
/*  32: 61 */       int lastSlash = className.lastIndexOf("/");
/*  33: 62 */       if (-1 != lastSlash) {
/*  34: 63 */         className = className.substring(lastSlash + 1);
/*  35:    */       }
/*  36:    */     }
/*  37: 66 */     String localPart = qname.getLocalPart();
/*  38: 67 */     int lastDotIndex = localPart.lastIndexOf('.');
/*  39: 68 */     if (lastDotIndex > 0)
/*  40:    */     {
/*  41: 70 */       if (className != null) {
/*  42: 71 */         className = className + "." + localPart.substring(0, lastDotIndex);
/*  43:    */       } else {
/*  44: 73 */         className = localPart.substring(0, lastDotIndex);
/*  45:    */       }
/*  46: 75 */       methodName = localPart.substring(lastDotIndex + 1);
/*  47:    */     }
/*  48:    */     else
/*  49:    */     {
/*  50: 78 */       methodName = localPart;
/*  51:    */     }
/*  52: 80 */     if ((null == className) || (className.trim().length() == 0) || (null == methodName) || (methodName.trim().length() == 0)) {
/*  53: 82 */       return null;
/*  54:    */     }
/*  55: 84 */     ExtensionHandler handler = null;
/*  56:    */     try
/*  57:    */     {
/*  58: 87 */       ExtensionHandler.getClassForName(className);
/*  59: 88 */       handler = new ExtensionHandlerJavaClass(uri, "javaclass", className);
/*  60:    */     }
/*  61:    */     catch (ClassNotFoundException e)
/*  62:    */     {
/*  63: 92 */       return null;
/*  64:    */     }
/*  65: 94 */     return new XPathFunctionImpl(handler, methodName);
/*  66:    */   }
/*  67:    */   
/*  68:    */   private String getEXSLTClassName(String uri)
/*  69:    */   {
/*  70:103 */     if (uri.equals("http://exslt.org/math")) {
/*  71:104 */       return "org.apache.xalan.lib.ExsltMath";
/*  72:    */     }
/*  73:105 */     if (uri.equals("http://exslt.org/sets")) {
/*  74:106 */       return "org.apache.xalan.lib.ExsltSets";
/*  75:    */     }
/*  76:107 */     if (uri.equals("http://exslt.org/strings")) {
/*  77:108 */       return "org.apache.xalan.lib.ExsltStrings";
/*  78:    */     }
/*  79:109 */     if (uri.equals("http://exslt.org/dates-and-times")) {
/*  80:110 */       return "org.apache.xalan.lib.ExsltDatetime";
/*  81:    */     }
/*  82:111 */     if (uri.equals("http://exslt.org/dynamic")) {
/*  83:112 */       return "org.apache.xalan.lib.ExsltDynamic";
/*  84:    */     }
/*  85:113 */     if (uri.equals("http://exslt.org/common")) {
/*  86:114 */       return "org.apache.xalan.lib.ExsltCommon";
/*  87:    */     }
/*  88:116 */     return null;
/*  89:    */   }
/*  90:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.XPathFunctionResolverImpl
 * JD-Core Version:    0.7.0.1
 */