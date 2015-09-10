/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import javax.xml.transform.TransformerException;
/*  5:   */ import org.apache.xalan.templates.StylesheetRoot;
/*  6:   */ import org.apache.xalan.transformer.TransformerImpl;
/*  7:   */ import org.apache.xml.utils.PrefixResolver;
/*  8:   */ import org.apache.xml.utils.QName;
/*  9:   */ import org.apache.xpath.Expression;
/* 10:   */ import org.apache.xpath.ExtensionsProvider;
/* 11:   */ import org.apache.xpath.XPathContext;
/* 12:   */ import org.apache.xpath.objects.XBoolean;
/* 13:   */ import org.apache.xpath.objects.XObject;
/* 14:   */ 
/* 15:   */ public class FuncExtElementAvailable
/* 16:   */   extends FunctionOneArg
/* 17:   */ {
/* 18:   */   static final long serialVersionUID = -472533699257968546L;
/* 19:   */   
/* 20:   */   public XObject execute(XPathContext xctxt)
/* 21:   */     throws TransformerException
/* 22:   */   {
/* 23:54 */     String fullName = this.m_arg0.execute(xctxt).str();
/* 24:55 */     int indexOfNSSep = fullName.indexOf(':');
/* 25:   */     String prefix;
/* 26:   */     String namespace;
/* 27:   */     String methName;
/* 28:57 */     if (indexOfNSSep < 0)
/* 29:   */     {
/* 30:59 */       prefix = "";
/* 31:60 */       namespace = "http://www.w3.org/1999/XSL/Transform";
/* 32:61 */       methName = fullName;
/* 33:   */     }
/* 34:   */     else
/* 35:   */     {
/* 36:65 */       prefix = fullName.substring(0, indexOfNSSep);
/* 37:66 */       namespace = xctxt.getNamespaceContext().getNamespaceForPrefix(prefix);
/* 38:67 */       if (null == namespace) {
/* 39:68 */         return XBoolean.S_FALSE;
/* 40:   */       }
/* 41:69 */       methName = fullName.substring(indexOfNSSep + 1);
/* 42:   */     }
/* 43:72 */     if ((namespace.equals("http://www.w3.org/1999/XSL/Transform")) || (namespace.equals("http://xml.apache.org/xalan"))) {
/* 44:   */       try
/* 45:   */       {
/* 46:77 */         TransformerImpl transformer = (TransformerImpl)xctxt.getOwnerObject();
/* 47:78 */         return transformer.getStylesheet().getAvailableElements().containsKey(new QName(namespace, methName)) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
/* 48:   */       }
/* 49:   */       catch (Exception e)
/* 50:   */       {
/* 51:84 */         return XBoolean.S_FALSE;
/* 52:   */       }
/* 53:   */     }
/* 54:90 */     ExtensionsProvider extProvider = (ExtensionsProvider)xctxt.getOwnerObject();
/* 55:91 */     return extProvider.elementAvailable(namespace, methName) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncExtElementAvailable
 * JD-Core Version:    0.7.0.1
 */