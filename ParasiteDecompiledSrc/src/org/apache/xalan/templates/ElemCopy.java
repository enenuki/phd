/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.serialize.SerializerUtils;
/*   5:    */ import org.apache.xalan.trace.TraceManager;
/*   6:    */ import org.apache.xalan.transformer.ClonerToResultTree;
/*   7:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   8:    */ import org.apache.xml.dtm.DTM;
/*   9:    */ import org.apache.xml.serializer.SerializationHandler;
/*  10:    */ import org.apache.xpath.XPathContext;
/*  11:    */ import org.xml.sax.ContentHandler;
/*  12:    */ import org.xml.sax.SAXException;
/*  13:    */ 
/*  14:    */ public class ElemCopy
/*  15:    */   extends ElemUse
/*  16:    */ {
/*  17:    */   static final long serialVersionUID = 5478580783896941384L;
/*  18:    */   
/*  19:    */   public int getXSLToken()
/*  20:    */   {
/*  21: 56 */     return 9;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String getNodeName()
/*  25:    */   {
/*  26: 66 */     return "copy";
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void execute(TransformerImpl transformer)
/*  30:    */     throws TransformerException
/*  31:    */   {
/*  32: 92 */     XPathContext xctxt = transformer.getXPathContext();
/*  33:    */     try
/*  34:    */     {
/*  35: 96 */       int sourceNode = xctxt.getCurrentNode();
/*  36: 97 */       xctxt.pushCurrentNode(sourceNode);
/*  37: 98 */       DTM dtm = xctxt.getDTM(sourceNode);
/*  38: 99 */       short nodeType = dtm.getNodeType(sourceNode);
/*  39:101 */       if ((9 != nodeType) && (11 != nodeType))
/*  40:    */       {
/*  41:103 */         SerializationHandler rthandler = transformer.getSerializationHandler();
/*  42:105 */         if (transformer.getDebug()) {
/*  43:106 */           transformer.getTraceManager().fireTraceEvent(this);
/*  44:    */         }
/*  45:109 */         ClonerToResultTree.cloneToResultTree(sourceNode, nodeType, dtm, rthandler, false);
/*  46:112 */         if (1 == nodeType)
/*  47:    */         {
/*  48:114 */           super.execute(transformer);
/*  49:115 */           SerializerUtils.processNSDecls(rthandler, sourceNode, nodeType, dtm);
/*  50:116 */           transformer.executeChildTemplates(this, true);
/*  51:    */           
/*  52:118 */           String ns = dtm.getNamespaceURI(sourceNode);
/*  53:119 */           String localName = dtm.getLocalName(sourceNode);
/*  54:120 */           transformer.getResultTreeHandler().endElement(ns, localName, dtm.getNodeName(sourceNode));
/*  55:    */         }
/*  56:123 */         if (transformer.getDebug()) {
/*  57:124 */           transformer.getTraceManager().fireTraceEndEvent(this);
/*  58:    */         }
/*  59:    */       }
/*  60:    */       else
/*  61:    */       {
/*  62:128 */         if (transformer.getDebug()) {
/*  63:129 */           transformer.getTraceManager().fireTraceEvent(this);
/*  64:    */         }
/*  65:131 */         super.execute(transformer);
/*  66:132 */         transformer.executeChildTemplates(this, true);
/*  67:134 */         if (transformer.getDebug()) {
/*  68:135 */           transformer.getTraceManager().fireTraceEndEvent(this);
/*  69:    */         }
/*  70:    */       }
/*  71:    */     }
/*  72:    */     catch (SAXException se)
/*  73:    */     {
/*  74:140 */       throw new TransformerException(se);
/*  75:    */     }
/*  76:    */     finally
/*  77:    */     {
/*  78:144 */       xctxt.popCurrentNode();
/*  79:    */     }
/*  80:    */   }
/*  81:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemCopy
 * JD-Core Version:    0.7.0.1
 */