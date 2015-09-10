/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.serialize.SerializerUtils;
/*   5:    */ import org.apache.xml.dtm.DTM;
/*   6:    */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   7:    */ import org.apache.xml.serializer.SerializationHandler;
/*   8:    */ import org.apache.xml.utils.XMLString;
/*   9:    */ import org.xml.sax.ContentHandler;
/*  10:    */ import org.xml.sax.SAXException;
/*  11:    */ import org.xml.sax.ext.LexicalHandler;
/*  12:    */ 
/*  13:    */ public class ClonerToResultTree
/*  14:    */ {
/*  15:    */   public static void cloneToResultTree(int node, int nodeType, DTM dtm, SerializationHandler rth, boolean shouldCloneAttributes)
/*  16:    */     throws TransformerException
/*  17:    */   {
/*  18:    */     try
/*  19:    */     {
/*  20:140 */       switch (nodeType)
/*  21:    */       {
/*  22:    */       case 3: 
/*  23:143 */         dtm.dispatchCharactersEvents(node, rth, false);
/*  24:144 */         break;
/*  25:    */       case 9: 
/*  26:    */       case 11: 
/*  27:    */         break;
/*  28:    */       case 1: 
/*  29:154 */         String ns = dtm.getNamespaceURI(node);
/*  30:155 */         if (ns == null) {
/*  31:155 */           ns = "";
/*  32:    */         }
/*  33:156 */         String localName = dtm.getLocalName(node);
/*  34:    */         
/*  35:    */ 
/*  36:    */ 
/*  37:160 */         rth.startElement(ns, localName, dtm.getNodeNameX(node));
/*  38:165 */         if (shouldCloneAttributes)
/*  39:    */         {
/*  40:167 */           SerializerUtils.addAttributes(rth, node);
/*  41:168 */           SerializerUtils.processNSDecls(rth, node, nodeType, dtm);
/*  42:    */         }
/*  43:171 */         break;
/*  44:    */       case 4: 
/*  45:173 */         rth.startCDATA();
/*  46:174 */         dtm.dispatchCharactersEvents(node, rth, false);
/*  47:175 */         rth.endCDATA();
/*  48:176 */         break;
/*  49:    */       case 2: 
/*  50:178 */         SerializerUtils.addAttribute(rth, node);
/*  51:179 */         break;
/*  52:    */       case 13: 
/*  53:185 */         SerializerUtils.processNSDecls(rth, node, 13, dtm);
/*  54:186 */         break;
/*  55:    */       case 8: 
/*  56:188 */         XMLString xstr = dtm.getStringValue(node);
/*  57:189 */         xstr.dispatchAsComment(rth);
/*  58:190 */         break;
/*  59:    */       case 5: 
/*  60:192 */         rth.entityReference(dtm.getNodeNameX(node));
/*  61:193 */         break;
/*  62:    */       case 7: 
/*  63:197 */         rth.processingInstruction(dtm.getNodeNameX(node), dtm.getNodeValue(node));
/*  64:    */         
/*  65:    */ 
/*  66:200 */         break;
/*  67:    */       case 6: 
/*  68:    */       case 10: 
/*  69:    */       case 12: 
/*  70:    */       default: 
/*  71:203 */         throw new TransformerException("Can't clone node: " + dtm.getNodeName(node));
/*  72:    */       }
/*  73:    */     }
/*  74:    */     catch (SAXException se)
/*  75:    */     {
/*  76:209 */       throw new TransformerException(se);
/*  77:    */     }
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.ClonerToResultTree
 * JD-Core Version:    0.7.0.1
 */