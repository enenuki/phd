/*   1:    */ package org.apache.xalan.serialize;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   5:    */ import org.apache.xml.dtm.DTM;
/*   6:    */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   7:    */ import org.apache.xml.serializer.NamespaceMappings;
/*   8:    */ import org.apache.xml.serializer.SerializationHandler;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.apache.xpath.objects.XObject;
/*  11:    */ import org.xml.sax.ContentHandler;
/*  12:    */ import org.xml.sax.SAXException;
/*  13:    */ 
/*  14:    */ public class SerializerUtils
/*  15:    */ {
/*  16:    */   public static void addAttribute(SerializationHandler handler, int attr)
/*  17:    */     throws TransformerException
/*  18:    */   {
/*  19: 58 */     TransformerImpl transformer = (TransformerImpl)handler.getTransformer();
/*  20:    */     
/*  21: 60 */     DTM dtm = transformer.getXPathContext().getDTM(attr);
/*  22: 62 */     if (isDefinedNSDecl(handler, attr, dtm)) {
/*  23: 63 */       return;
/*  24:    */     }
/*  25: 65 */     String ns = dtm.getNamespaceURI(attr);
/*  26: 67 */     if (ns == null) {
/*  27: 68 */       ns = "";
/*  28:    */     }
/*  29:    */     try
/*  30:    */     {
/*  31: 73 */       handler.addAttribute(ns, dtm.getLocalName(attr), dtm.getNodeName(attr), "CDATA", dtm.getNodeValue(attr), false);
/*  32:    */     }
/*  33:    */     catch (SAXException e) {}
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static void addAttributes(SerializationHandler handler, int src)
/*  37:    */     throws TransformerException
/*  38:    */   {
/*  39: 97 */     TransformerImpl transformer = (TransformerImpl)handler.getTransformer();
/*  40:    */     
/*  41: 99 */     DTM dtm = transformer.getXPathContext().getDTM(src);
/*  42:101 */     for (int node = dtm.getFirstAttribute(src); -1 != node; node = dtm.getNextAttribute(node)) {
/*  43:105 */       addAttribute(handler, node);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static void outputResultTreeFragment(SerializationHandler handler, XObject obj, XPathContext support)
/*  48:    */     throws SAXException
/*  49:    */   {
/*  50:125 */     int doc = obj.rtf();
/*  51:126 */     DTM dtm = support.getDTM(doc);
/*  52:128 */     if (null != dtm) {
/*  53:130 */       for (int n = dtm.getFirstChild(doc); -1 != n; n = dtm.getNextSibling(n))
/*  54:    */       {
/*  55:134 */         handler.flushPending();
/*  56:138 */         if ((dtm.getNodeType(n) == 1) && (dtm.getNamespaceURI(n) == null)) {
/*  57:140 */           handler.startPrefixMapping("", "");
/*  58:    */         }
/*  59:141 */         dtm.dispatchToEvents(n, handler);
/*  60:    */       }
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static void processNSDecls(SerializationHandler handler, int src, int type, DTM dtm)
/*  65:    */     throws TransformerException
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69:168 */       if (type == 1)
/*  70:    */       {
/*  71:170 */         for (int namespace = dtm.getFirstNamespaceNode(src, true); -1 != namespace; namespace = dtm.getNextNamespaceNode(src, namespace, true))
/*  72:    */         {
/*  73:176 */           String prefix = dtm.getNodeNameX(namespace);
/*  74:177 */           String desturi = handler.getNamespaceURIFromPrefix(prefix);
/*  75:    */           
/*  76:179 */           String srcURI = dtm.getNodeValue(namespace);
/*  77:181 */           if (!srcURI.equalsIgnoreCase(desturi)) {
/*  78:183 */             handler.startPrefixMapping(prefix, srcURI, false);
/*  79:    */           }
/*  80:    */         }
/*  81:    */       }
/*  82:187 */       else if (type == 13)
/*  83:    */       {
/*  84:189 */         String prefix = dtm.getNodeNameX(src);
/*  85:    */         
/*  86:191 */         String desturi = handler.getNamespaceURIFromPrefix(prefix);
/*  87:192 */         String srcURI = dtm.getNodeValue(src);
/*  88:194 */         if (!srcURI.equalsIgnoreCase(desturi)) {
/*  89:196 */           handler.startPrefixMapping(prefix, srcURI, false);
/*  90:    */         }
/*  91:    */       }
/*  92:    */     }
/*  93:    */     catch (SAXException se)
/*  94:    */     {
/*  95:202 */       throw new TransformerException(se);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static boolean isDefinedNSDecl(SerializationHandler serializer, int attr, DTM dtm)
/* 100:    */   {
/* 101:222 */     if (13 == dtm.getNodeType(attr))
/* 102:    */     {
/* 103:226 */       String prefix = dtm.getNodeNameX(attr);
/* 104:227 */       String uri = serializer.getNamespaceURIFromPrefix(prefix);
/* 105:230 */       if ((null != uri) && (uri.equals(dtm.getStringValue(attr)))) {
/* 106:231 */         return true;
/* 107:    */       }
/* 108:    */     }
/* 109:234 */     return false;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static void ensureNamespaceDeclDeclared(SerializationHandler handler, DTM dtm, int namespace)
/* 113:    */     throws SAXException
/* 114:    */   {
/* 115:255 */     String uri = dtm.getNodeValue(namespace);
/* 116:256 */     String prefix = dtm.getNodeNameX(namespace);
/* 117:258 */     if ((uri != null) && (uri.length() > 0) && (null != prefix))
/* 118:    */     {
/* 119:261 */       NamespaceMappings ns = handler.getNamespaceMappings();
/* 120:262 */       if (ns != null)
/* 121:    */       {
/* 122:265 */         String foundURI = ns.lookupNamespace(prefix);
/* 123:266 */         if ((null == foundURI) || (!foundURI.equals(uri))) {
/* 124:268 */           handler.startPrefixMapping(prefix, uri, false);
/* 125:    */         }
/* 126:    */       }
/* 127:    */     }
/* 128:    */   }
/* 129:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.serialize.SerializerUtils
 * JD-Core Version:    0.7.0.1
 */