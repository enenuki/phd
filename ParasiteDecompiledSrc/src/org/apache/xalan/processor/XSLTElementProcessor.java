/*   1:    */ package org.apache.xalan.processor;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.xalan.res.XSLMessages;
/*   6:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   7:    */ import org.apache.xalan.templates.Stylesheet;
/*   8:    */ import org.apache.xml.utils.IntStack;
/*   9:    */ import org.xml.sax.Attributes;
/*  10:    */ import org.xml.sax.InputSource;
/*  11:    */ import org.xml.sax.SAXException;
/*  12:    */ import org.xml.sax.helpers.AttributesImpl;
/*  13:    */ 
/*  14:    */ public class XSLTElementProcessor
/*  15:    */   extends ElemTemplateElement
/*  16:    */ {
/*  17:    */   static final long serialVersionUID = 5597421564955304421L;
/*  18:    */   private IntStack m_savedLastOrder;
/*  19:    */   private XSLTElementDef m_elemDef;
/*  20:    */   
/*  21:    */   XSLTElementDef getElemDef()
/*  22:    */   {
/*  23: 66 */     return this.m_elemDef;
/*  24:    */   }
/*  25:    */   
/*  26:    */   void setElemDef(XSLTElementDef def)
/*  27:    */   {
/*  28: 76 */     this.m_elemDef = def;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public InputSource resolveEntity(StylesheetHandler handler, String publicId, String systemId)
/*  32:    */     throws SAXException
/*  33:    */   {
/*  34: 95 */     return null;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void notationDecl(StylesheetHandler handler, String name, String publicId, String systemId) {}
/*  38:    */   
/*  39:    */   public void unparsedEntityDecl(StylesheetHandler handler, String name, String publicId, String systemId, String notationName) {}
/*  40:    */   
/*  41:    */   public void startNonText(StylesheetHandler handler)
/*  42:    */     throws SAXException
/*  43:    */   {}
/*  44:    */   
/*  45:    */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/*  46:    */     throws SAXException
/*  47:    */   {
/*  48:162 */     if (this.m_savedLastOrder == null) {
/*  49:163 */       this.m_savedLastOrder = new IntStack();
/*  50:    */     }
/*  51:164 */     this.m_savedLastOrder.push(getElemDef().getLastOrder());
/*  52:165 */     getElemDef().setLastOrder(-1);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void endElement(StylesheetHandler handler, String uri, String localName, String rawName)
/*  56:    */     throws SAXException
/*  57:    */   {
/*  58:180 */     if ((this.m_savedLastOrder != null) && (!this.m_savedLastOrder.empty())) {
/*  59:181 */       getElemDef().setLastOrder(this.m_savedLastOrder.pop());
/*  60:    */     }
/*  61:183 */     if (!getElemDef().getRequiredFound()) {
/*  62:184 */       handler.error("ER_REQUIRED_ELEM_NOT_FOUND", new Object[] { getElemDef().getRequiredElem() }, null);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void characters(StylesheetHandler handler, char[] ch, int start, int length)
/*  67:    */     throws SAXException
/*  68:    */   {
/*  69:201 */     handler.error("ER_CHARS_NOT_ALLOWED", null, null);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void ignorableWhitespace(StylesheetHandler handler, char[] ch, int start, int length)
/*  73:    */     throws SAXException
/*  74:    */   {}
/*  75:    */   
/*  76:    */   public void processingInstruction(StylesheetHandler handler, String target, String data)
/*  77:    */     throws SAXException
/*  78:    */   {}
/*  79:    */   
/*  80:    */   public void skippedEntity(StylesheetHandler handler, String name)
/*  81:    */     throws SAXException
/*  82:    */   {}
/*  83:    */   
/*  84:    */   void setPropertiesFromAttributes(StylesheetHandler handler, String rawName, Attributes attributes, ElemTemplateElement target)
/*  85:    */     throws SAXException
/*  86:    */   {
/*  87:268 */     setPropertiesFromAttributes(handler, rawName, attributes, target, true);
/*  88:    */   }
/*  89:    */   
/*  90:    */   Attributes setPropertiesFromAttributes(StylesheetHandler handler, String rawName, Attributes attributes, ElemTemplateElement target, boolean throwError)
/*  91:    */     throws SAXException
/*  92:    */   {
/*  93:291 */     XSLTElementDef def = getElemDef();
/*  94:292 */     AttributesImpl undefines = null;
/*  95:293 */     boolean isCompatibleMode = ((null != handler.getStylesheet()) && (handler.getStylesheet().getCompatibleMode())) || (!throwError);
/*  96:296 */     if (isCompatibleMode) {
/*  97:297 */       undefines = new AttributesImpl();
/*  98:    */     }
/*  99:302 */     List processedDefs = new ArrayList();
/* 100:    */     
/* 101:    */ 
/* 102:305 */     List errorDefs = new ArrayList();
/* 103:306 */     int nAttrs = attributes.getLength();
/* 104:308 */     for (int i = 0; i < nAttrs; i++)
/* 105:    */     {
/* 106:310 */       String attrUri = attributes.getURI(i);
/* 107:312 */       if ((null != attrUri) && (attrUri.length() == 0) && ((attributes.getQName(i).startsWith("xmlns:")) || (attributes.getQName(i).equals("xmlns")))) {
/* 108:316 */         attrUri = "http://www.w3.org/XML/1998/namespace";
/* 109:    */       }
/* 110:318 */       String attrLocalName = attributes.getLocalName(i);
/* 111:319 */       XSLTAttributeDef attrDef = def.getAttributeDef(attrUri, attrLocalName);
/* 112:321 */       if (null == attrDef)
/* 113:    */       {
/* 114:323 */         if (!isCompatibleMode) {
/* 115:327 */           handler.error("ER_ATTR_NOT_ALLOWED", new Object[] { attributes.getQName(i), rawName }, null);
/* 116:    */         } else {
/* 117:333 */           undefines.addAttribute(attrUri, attrLocalName, attributes.getQName(i), attributes.getType(i), attributes.getValue(i));
/* 118:    */         }
/* 119:    */       }
/* 120:    */       else
/* 121:    */       {
/* 122:343 */         boolean success = attrDef.setAttrValue(handler, attrUri, attrLocalName, attributes.getQName(i), attributes.getValue(i), target);
/* 123:348 */         if (success) {
/* 124:349 */           processedDefs.add(attrDef);
/* 125:    */         } else {
/* 126:351 */           errorDefs.add(attrDef);
/* 127:    */         }
/* 128:    */       }
/* 129:    */     }
/* 130:355 */     XSLTAttributeDef[] attrDefs = def.getAttributes();
/* 131:356 */     int nAttrDefs = attrDefs.length;
/* 132:358 */     for (int i = 0; i < nAttrDefs; i++)
/* 133:    */     {
/* 134:360 */       XSLTAttributeDef attrDef = attrDefs[i];
/* 135:361 */       String defVal = attrDef.getDefault();
/* 136:363 */       if (null != defVal) {
/* 137:365 */         if (!processedDefs.contains(attrDef)) {
/* 138:367 */           attrDef.setDefAttrValue(handler, target);
/* 139:    */         }
/* 140:    */       }
/* 141:371 */       if (attrDef.getRequired()) {
/* 142:373 */         if ((!processedDefs.contains(attrDef)) && (!errorDefs.contains(attrDef))) {
/* 143:374 */           handler.error(XSLMessages.createMessage("ER_REQUIRES_ATTRIB", new Object[] { rawName, attrDef.getName() }), null);
/* 144:    */         }
/* 145:    */       }
/* 146:    */     }
/* 147:381 */     return undefines;
/* 148:    */   }
/* 149:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.XSLTElementProcessor
 * JD-Core Version:    0.7.0.1
 */