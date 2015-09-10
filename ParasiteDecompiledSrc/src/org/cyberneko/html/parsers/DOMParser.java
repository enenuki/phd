/*   1:    */ package org.cyberneko.html.parsers;
/*   2:    */ 
/*   3:    */ import org.apache.xerces.xni.Augmentations;
/*   4:    */ import org.apache.xerces.xni.XNIException;
/*   5:    */ import org.cyberneko.html.HTMLConfiguration;
/*   6:    */ import org.cyberneko.html.xercesbridge.XercesBridge;
/*   7:    */ import org.xml.sax.SAXNotRecognizedException;
/*   8:    */ import org.xml.sax.SAXNotSupportedException;
/*   9:    */ 
/*  10:    */ public class DOMParser
/*  11:    */   extends org.apache.xerces.parsers.DOMParser
/*  12:    */ {
/*  13:    */   public DOMParser()
/*  14:    */   {
/*  15: 49 */     super(new HTMLConfiguration());
/*  16:    */     try
/*  17:    */     {
/*  18: 52 */       setProperty("http://apache.org/xml/properties/dom/document-class-name", "org.apache.html.dom.HTMLDocumentImpl");
/*  19:    */     }
/*  20:    */     catch (SAXNotRecognizedException e)
/*  21:    */     {
/*  22: 56 */       throw new RuntimeException("http://apache.org/xml/properties/dom/document-class-name property not recognized");
/*  23:    */     }
/*  24:    */     catch (SAXNotSupportedException e)
/*  25:    */     {
/*  26: 59 */       throw new RuntimeException("http://apache.org/xml/properties/dom/document-class-name property not supported");
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void doctypeDecl(String root, String pubid, String sysid, Augmentations augs)
/*  31:    */     throws XNIException
/*  32:    */   {
/*  33: 81 */     String VERSION = XercesBridge.getInstance().getVersion();
/*  34: 82 */     boolean okay = true;
/*  35: 83 */     if (VERSION.startsWith("Xerces-J 2.")) {
/*  36: 84 */       okay = getParserSubVersion() > 5;
/*  37: 89 */     } else if (VERSION.startsWith("XML4J")) {
/*  38: 90 */       okay = false;
/*  39:    */     }
/*  40: 94 */     if (okay) {
/*  41: 95 */       super.doctypeDecl(root, pubid, sysid, augs);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   private static int getParserSubVersion()
/*  46:    */   {
/*  47:    */     try
/*  48:    */     {
/*  49:107 */       String VERSION = XercesBridge.getInstance().getVersion();
/*  50:108 */       int index1 = VERSION.indexOf('.') + 1;
/*  51:109 */       int index2 = VERSION.indexOf('.', index1);
/*  52:110 */       if (index2 == -1) {
/*  53:110 */         index2 = VERSION.length();
/*  54:    */       }
/*  55:111 */       return Integer.parseInt(VERSION.substring(index1, index2));
/*  56:    */     }
/*  57:    */     catch (Exception e) {}
/*  58:114 */     return -1;
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.parsers.DOMParser
 * JD-Core Version:    0.7.0.1
 */