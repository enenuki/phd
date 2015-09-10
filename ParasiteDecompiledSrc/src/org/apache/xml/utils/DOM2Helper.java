/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import javax.xml.parsers.DocumentBuilder;
/*   5:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*   6:    */ import javax.xml.parsers.ParserConfigurationException;
/*   7:    */ import javax.xml.transform.TransformerException;
/*   8:    */ import org.w3c.dom.Attr;
/*   9:    */ import org.w3c.dom.Document;
/*  10:    */ import org.w3c.dom.Element;
/*  11:    */ import org.w3c.dom.Node;
/*  12:    */ import org.xml.sax.InputSource;
/*  13:    */ import org.xml.sax.SAXException;
/*  14:    */ 
/*  15:    */ /**
/*  16:    */  * @deprecated
/*  17:    */  */
/*  18:    */ public class DOM2Helper
/*  19:    */   extends DOMHelper
/*  20:    */ {
/*  21:    */   private Document m_doc;
/*  22:    */   
/*  23:    */   public void checkNode(Node node)
/*  24:    */     throws TransformerException
/*  25:    */   {}
/*  26:    */   
/*  27:    */   public boolean supportsSAX()
/*  28:    */   {
/*  29: 79 */     return true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setDocument(Document doc)
/*  33:    */   {
/*  34: 97 */     this.m_doc = doc;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Document getDocument()
/*  38:    */   {
/*  39:108 */     return this.m_doc;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void parse(InputSource source)
/*  43:    */     throws TransformerException
/*  44:    */   {
/*  45:    */     try
/*  46:    */     {
/*  47:142 */       DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
/*  48:    */       
/*  49:    */ 
/*  50:145 */       builderFactory.setNamespaceAware(true);
/*  51:146 */       builderFactory.setValidating(true);
/*  52:    */       
/*  53:148 */       DocumentBuilder parser = builderFactory.newDocumentBuilder();
/*  54:    */       
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:165 */       parser.setErrorHandler(new DefaultErrorHandler());
/*  71:    */       
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:173 */       setDocument(parser.parse(source));
/*  79:    */     }
/*  80:    */     catch (SAXException se)
/*  81:    */     {
/*  82:177 */       throw new TransformerException(se);
/*  83:    */     }
/*  84:    */     catch (ParserConfigurationException pce)
/*  85:    */     {
/*  86:181 */       throw new TransformerException(pce);
/*  87:    */     }
/*  88:    */     catch (IOException ioe)
/*  89:    */     {
/*  90:185 */       throw new TransformerException(ioe);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Element getElementByID(String id, Document doc)
/*  95:    */   {
/*  96:208 */     return doc.getElementById(id);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static boolean isNodeAfter(Node node1, Node node2)
/* 100:    */   {
/* 101:234 */     if (((node1 instanceof DOMOrder)) && ((node2 instanceof DOMOrder)))
/* 102:    */     {
/* 103:236 */       int index1 = ((DOMOrder)node1).getUid();
/* 104:237 */       int index2 = ((DOMOrder)node2).getUid();
/* 105:    */       
/* 106:239 */       return index1 <= index2;
/* 107:    */     }
/* 108:246 */     return DOMHelper.isNodeAfter(node1, node2);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static Node getParentOfNode(Node node)
/* 112:    */   {
/* 113:264 */     Node parent = node.getParentNode();
/* 114:265 */     if ((parent == null) && (2 == node.getNodeType())) {
/* 115:266 */       parent = ((Attr)node).getOwnerElement();
/* 116:    */     }
/* 117:267 */     return parent;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getLocalNameOfNode(Node n)
/* 121:    */   {
/* 122:284 */     String name = n.getLocalName();
/* 123:    */     
/* 124:286 */     return null == name ? super.getLocalNameOfNode(n) : name;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getNamespaceOfNode(Node n)
/* 128:    */   {
/* 129:306 */     return n.getNamespaceURI();
/* 130:    */   }
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.DOM2Helper
 * JD-Core Version:    0.7.0.1
 */