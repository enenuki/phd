/*   1:    */ package org.apache.xalan.processor;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.templates.ElemAttributeSet;
/*   5:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   6:    */ import org.apache.xalan.templates.Stylesheet;
/*   7:    */ import org.xml.sax.Attributes;
/*   8:    */ import org.xml.sax.SAXException;
/*   9:    */ 
/*  10:    */ class ProcessorAttributeSet
/*  11:    */   extends XSLTElementProcessor
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = -6473739251316787552L;
/*  14:    */   
/*  15:    */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/*  16:    */     throws SAXException
/*  17:    */   {
/*  18: 65 */     ElemAttributeSet eat = new ElemAttributeSet();
/*  19:    */     
/*  20: 67 */     eat.setLocaterInfo(handler.getLocator());
/*  21:    */     try
/*  22:    */     {
/*  23: 70 */       eat.setPrefixes(handler.getNamespaceSupport());
/*  24:    */     }
/*  25:    */     catch (TransformerException te)
/*  26:    */     {
/*  27: 74 */       throw new SAXException(te);
/*  28:    */     }
/*  29: 77 */     eat.setDOMBackPointer(handler.getOriginatingNode());
/*  30: 78 */     setPropertiesFromAttributes(handler, rawName, attributes, eat);
/*  31: 79 */     handler.getStylesheet().setAttributeSet(eat);
/*  32:    */     
/*  33:    */ 
/*  34: 82 */     ElemTemplateElement parent = handler.getElemTemplateElement();
/*  35:    */     
/*  36: 84 */     parent.appendChild(eat);
/*  37: 85 */     handler.pushElemTemplateElement(eat);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void endElement(StylesheetHandler handler, String uri, String localName, String rawName)
/*  41:    */     throws SAXException
/*  42:    */   {
/*  43:103 */     handler.popElemTemplateElement();
/*  44:    */   }
/*  45:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorAttributeSet
 * JD-Core Version:    0.7.0.1
 */