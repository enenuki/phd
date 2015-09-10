/*   1:    */ package org.apache.xalan.processor;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerConfigurationException;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   6:    */ import org.apache.xalan.templates.Stylesheet;
/*   7:    */ import org.apache.xalan.templates.StylesheetComposed;
/*   8:    */ import org.apache.xalan.templates.StylesheetRoot;
/*   9:    */ import org.xml.sax.Attributes;
/*  10:    */ import org.xml.sax.SAXException;
/*  11:    */ 
/*  12:    */ public class ProcessorStylesheetElement
/*  13:    */   extends XSLTElementProcessor
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = -877798927447840792L;
/*  16:    */   
/*  17:    */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/*  18:    */     throws SAXException
/*  19:    */   {
/*  20: 64 */     super.startElement(handler, uri, localName, rawName, attributes);
/*  21:    */     try
/*  22:    */     {
/*  23: 67 */       int stylesheetType = handler.getStylesheetType();
/*  24:    */       Stylesheet stylesheet;
/*  25: 70 */       if (stylesheetType == 1)
/*  26:    */       {
/*  27:    */         try
/*  28:    */         {
/*  29: 74 */           stylesheet = getStylesheetRoot(handler);
/*  30:    */         }
/*  31:    */         catch (TransformerConfigurationException tfe)
/*  32:    */         {
/*  33: 78 */           throw new TransformerException(tfe);
/*  34:    */         }
/*  35:    */       }
/*  36:    */       else
/*  37:    */       {
/*  38: 83 */         Stylesheet parent = handler.getStylesheet();
/*  39: 85 */         if (stylesheetType == 3)
/*  40:    */         {
/*  41: 87 */           StylesheetComposed sc = new StylesheetComposed(parent);
/*  42:    */           
/*  43: 89 */           parent.setImport(sc);
/*  44:    */           
/*  45: 91 */           stylesheet = sc;
/*  46:    */         }
/*  47:    */         else
/*  48:    */         {
/*  49: 95 */           stylesheet = new Stylesheet(parent);
/*  50:    */           
/*  51: 97 */           parent.setInclude(stylesheet);
/*  52:    */         }
/*  53:    */       }
/*  54:101 */       stylesheet.setDOMBackPointer(handler.getOriginatingNode());
/*  55:102 */       stylesheet.setLocaterInfo(handler.getLocator());
/*  56:    */       
/*  57:104 */       stylesheet.setPrefixes(handler.getNamespaceSupport());
/*  58:105 */       handler.pushStylesheet(stylesheet);
/*  59:106 */       setPropertiesFromAttributes(handler, rawName, attributes, handler.getStylesheet());
/*  60:    */       
/*  61:108 */       handler.pushElemTemplateElement(handler.getStylesheet());
/*  62:    */     }
/*  63:    */     catch (TransformerException te)
/*  64:    */     {
/*  65:112 */       throw new SAXException(te);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected Stylesheet getStylesheetRoot(StylesheetHandler handler)
/*  70:    */     throws TransformerConfigurationException
/*  71:    */   {
/*  72:123 */     StylesheetRoot stylesheet = new StylesheetRoot(handler.getSchema(), handler.getStylesheetProcessor().getErrorListener());
/*  73:125 */     if (handler.getStylesheetProcessor().isSecureProcessing()) {
/*  74:126 */       stylesheet.setSecureProcessing(true);
/*  75:    */     }
/*  76:128 */     return stylesheet;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void endElement(StylesheetHandler handler, String uri, String localName, String rawName)
/*  80:    */     throws SAXException
/*  81:    */   {
/*  82:143 */     super.endElement(handler, uri, localName, rawName);
/*  83:144 */     handler.popElemTemplateElement();
/*  84:145 */     handler.popStylesheet();
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorStylesheetElement
 * JD-Core Version:    0.7.0.1
 */