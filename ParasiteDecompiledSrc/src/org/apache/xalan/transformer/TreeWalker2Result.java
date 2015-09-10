/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.serialize.SerializerUtils;
/*   5:    */ import org.apache.xml.dtm.DTM;
/*   6:    */ import org.apache.xml.dtm.ref.DTMTreeWalker;
/*   7:    */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   8:    */ import org.apache.xml.serializer.SerializationHandler;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.xml.sax.SAXException;
/*  11:    */ 
/*  12:    */ public class TreeWalker2Result
/*  13:    */   extends DTMTreeWalker
/*  14:    */ {
/*  15:    */   TransformerImpl m_transformer;
/*  16:    */   SerializationHandler m_handler;
/*  17:    */   int m_startNode;
/*  18:    */   
/*  19:    */   public TreeWalker2Result(TransformerImpl transformer, SerializationHandler handler)
/*  20:    */   {
/*  21: 56 */     super(handler, null);
/*  22:    */     
/*  23: 58 */     this.m_transformer = transformer;
/*  24: 59 */     this.m_handler = handler;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void traverse(int pos)
/*  28:    */     throws SAXException
/*  29:    */   {
/*  30: 71 */     this.m_dtm = this.m_transformer.getXPathContext().getDTM(pos);
/*  31: 72 */     this.m_startNode = pos;
/*  32:    */     
/*  33: 74 */     super.traverse(pos);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected void endNode(int node)
/*  37:    */     throws SAXException
/*  38:    */   {
/*  39: 87 */     super.endNode(node);
/*  40: 88 */     if (1 == this.m_dtm.getNodeType(node)) {
/*  41: 90 */       this.m_transformer.getXPathContext().popCurrentNode();
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected void startNode(int node)
/*  46:    */     throws SAXException
/*  47:    */   {
/*  48:105 */     XPathContext xcntxt = this.m_transformer.getXPathContext();
/*  49:    */     try
/*  50:    */     {
/*  51:109 */       if (1 == this.m_dtm.getNodeType(node))
/*  52:    */       {
/*  53:111 */         xcntxt.pushCurrentNode(node);
/*  54:113 */         if (this.m_startNode != node)
/*  55:    */         {
/*  56:115 */           super.startNode(node);
/*  57:    */         }
/*  58:    */         else
/*  59:    */         {
/*  60:119 */           String elemName = this.m_dtm.getNodeName(node);
/*  61:120 */           String localName = this.m_dtm.getLocalName(node);
/*  62:121 */           String namespace = this.m_dtm.getNamespaceURI(node);
/*  63:    */           
/*  64:    */ 
/*  65:    */ 
/*  66:125 */           this.m_handler.startElement(namespace, localName, elemName);
/*  67:126 */           boolean hasNSDecls = false;
/*  68:127 */           DTM dtm = this.m_dtm;
/*  69:128 */           for (int ns = dtm.getFirstNamespaceNode(node, true); -1 != ns; ns = dtm.getNextNamespaceNode(node, ns, true)) {
/*  70:131 */             SerializerUtils.ensureNamespaceDeclDeclared(this.m_handler, dtm, ns);
/*  71:    */           }
/*  72:135 */           for (int attr = dtm.getFirstAttribute(node); -1 != attr; attr = dtm.getNextAttribute(attr)) {
/*  73:138 */             SerializerUtils.addAttribute(this.m_handler, attr);
/*  74:    */           }
/*  75:    */         }
/*  76:    */       }
/*  77:    */       else
/*  78:    */       {
/*  79:145 */         xcntxt.pushCurrentNode(node);
/*  80:146 */         super.startNode(node);
/*  81:147 */         xcntxt.popCurrentNode();
/*  82:    */       }
/*  83:    */     }
/*  84:    */     catch (TransformerException te)
/*  85:    */     {
/*  86:152 */       throw new SAXException(te);
/*  87:    */     }
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.TreeWalker2Result
 * JD-Core Version:    0.7.0.1
 */