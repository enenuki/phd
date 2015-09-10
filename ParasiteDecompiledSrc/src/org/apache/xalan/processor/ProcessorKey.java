/*   1:    */ package org.apache.xalan.processor;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.xalan.res.XSLMessages;
/*   6:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   7:    */ import org.apache.xalan.templates.KeyDeclaration;
/*   8:    */ import org.apache.xalan.templates.Stylesheet;
/*   9:    */ import org.xml.sax.Attributes;
/*  10:    */ import org.xml.sax.SAXException;
/*  11:    */ 
/*  12:    */ class ProcessorKey
/*  13:    */   extends XSLTElementProcessor
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = 4285205417566822979L;
/*  16:    */   
/*  17:    */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/*  18:    */     throws SAXException
/*  19:    */   {
/*  20: 69 */     KeyDeclaration kd = new KeyDeclaration(handler.getStylesheet(), handler.nextUid());
/*  21:    */     
/*  22: 71 */     kd.setDOMBackPointer(handler.getOriginatingNode());
/*  23: 72 */     kd.setLocaterInfo(handler.getLocator());
/*  24: 73 */     setPropertiesFromAttributes(handler, rawName, attributes, kd);
/*  25: 74 */     handler.getStylesheet().setKey(kd);
/*  26:    */   }
/*  27:    */   
/*  28:    */   void setPropertiesFromAttributes(StylesheetHandler handler, String rawName, Attributes attributes, ElemTemplateElement target)
/*  29:    */     throws SAXException
/*  30:    */   {
/*  31: 92 */     XSLTElementDef def = getElemDef();
/*  32:    */     
/*  33:    */ 
/*  34:    */ 
/*  35: 96 */     List processedDefs = new ArrayList();
/*  36: 97 */     int nAttrs = attributes.getLength();
/*  37: 99 */     for (int i = 0; i < nAttrs; i++)
/*  38:    */     {
/*  39:101 */       String attrUri = attributes.getURI(i);
/*  40:102 */       String attrLocalName = attributes.getLocalName(i);
/*  41:103 */       XSLTAttributeDef attrDef = def.getAttributeDef(attrUri, attrLocalName);
/*  42:105 */       if (null == attrDef)
/*  43:    */       {
/*  44:109 */         handler.error(attributes.getQName(i) + "attribute is not allowed on the " + rawName + " element!", null);
/*  45:    */       }
/*  46:    */       else
/*  47:    */       {
/*  48:115 */         String valueString = attributes.getValue(i);
/*  49:117 */         if (valueString.indexOf("key(") >= 0) {
/*  50:119 */           handler.error(XSLMessages.createMessage("ER_INVALID_KEY_CALL", null), null);
/*  51:    */         }
/*  52:123 */         processedDefs.add(attrDef);
/*  53:124 */         attrDef.setAttrValue(handler, attrUri, attrLocalName, attributes.getQName(i), attributes.getValue(i), target);
/*  54:    */       }
/*  55:    */     }
/*  56:130 */     XSLTAttributeDef[] attrDefs = def.getAttributes();
/*  57:131 */     int nAttrDefs = attrDefs.length;
/*  58:133 */     for (int i = 0; i < nAttrDefs; i++)
/*  59:    */     {
/*  60:135 */       XSLTAttributeDef attrDef = attrDefs[i];
/*  61:136 */       String defVal = attrDef.getDefault();
/*  62:138 */       if (null != defVal) {
/*  63:140 */         if (!processedDefs.contains(attrDef)) {
/*  64:142 */           attrDef.setDefAttrValue(handler, target);
/*  65:    */         }
/*  66:    */       }
/*  67:146 */       if (attrDef.getRequired()) {
/*  68:148 */         if (!processedDefs.contains(attrDef)) {
/*  69:149 */           handler.error(XSLMessages.createMessage("ER_REQUIRES_ATTRIB", new Object[] { rawName, attrDef.getName() }), null);
/*  70:    */         }
/*  71:    */       }
/*  72:    */     }
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorKey
 * JD-Core Version:    0.7.0.1
 */