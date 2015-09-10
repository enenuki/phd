/*   1:    */ package org.apache.xalan.processor;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   5:    */ import org.xml.sax.Attributes;
/*   6:    */ import org.xml.sax.SAXException;
/*   7:    */ 
/*   8:    */ public class ProcessorTemplateElem
/*   9:    */   extends XSLTElementProcessor
/*  10:    */ {
/*  11:    */   static final long serialVersionUID = 8344994001943407235L;
/*  12:    */   
/*  13:    */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/*  14:    */     throws SAXException
/*  15:    */   {
/*  16: 53 */     super.startElement(handler, uri, localName, rawName, attributes);
/*  17:    */     try
/*  18:    */     {
/*  19: 57 */       XSLTElementDef def = getElemDef();
/*  20: 58 */       Class classObject = def.getClassObject();
/*  21: 59 */       ElemTemplateElement elem = null;
/*  22:    */       try
/*  23:    */       {
/*  24: 63 */         elem = (ElemTemplateElement)classObject.newInstance();
/*  25:    */         
/*  26: 65 */         elem.setDOMBackPointer(handler.getOriginatingNode());
/*  27: 66 */         elem.setLocaterInfo(handler.getLocator());
/*  28: 67 */         elem.setPrefixes(handler.getNamespaceSupport());
/*  29:    */       }
/*  30:    */       catch (InstantiationException ie)
/*  31:    */       {
/*  32: 71 */         handler.error("ER_FAILED_CREATING_ELEMTMPL", null, ie);
/*  33:    */       }
/*  34:    */       catch (IllegalAccessException iae)
/*  35:    */       {
/*  36: 75 */         handler.error("ER_FAILED_CREATING_ELEMTMPL", null, iae);
/*  37:    */       }
/*  38: 78 */       setPropertiesFromAttributes(handler, rawName, attributes, elem);
/*  39: 79 */       appendAndPush(handler, elem);
/*  40:    */     }
/*  41:    */     catch (TransformerException te)
/*  42:    */     {
/*  43: 83 */       throw new SAXException(te);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected void appendAndPush(StylesheetHandler handler, ElemTemplateElement elem)
/*  48:    */     throws SAXException
/*  49:    */   {
/*  50:103 */     ElemTemplateElement parent = handler.getElemTemplateElement();
/*  51:104 */     if (null != parent)
/*  52:    */     {
/*  53:106 */       parent.appendChild(elem);
/*  54:107 */       handler.pushElemTemplateElement(elem);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void endElement(StylesheetHandler handler, String uri, String localName, String rawName)
/*  59:    */     throws SAXException
/*  60:    */   {
/*  61:123 */     super.endElement(handler, uri, localName, rawName);
/*  62:124 */     handler.popElemTemplateElement().setEndLocaterInfo(handler.getLocator());
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorTemplateElem
 * JD-Core Version:    0.7.0.1
 */