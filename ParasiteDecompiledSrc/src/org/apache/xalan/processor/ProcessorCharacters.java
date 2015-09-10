/*   1:    */ package org.apache.xalan.processor;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   5:    */ import org.apache.xalan.templates.ElemText;
/*   6:    */ import org.apache.xalan.templates.ElemTextLiteral;
/*   7:    */ import org.apache.xml.utils.XMLCharacterRecognizer;
/*   8:    */ import org.w3c.dom.Node;
/*   9:    */ import org.xml.sax.SAXException;
/*  10:    */ 
/*  11:    */ public class ProcessorCharacters
/*  12:    */   extends XSLTElementProcessor
/*  13:    */ {
/*  14:    */   static final long serialVersionUID = 8632900007814162650L;
/*  15:    */   
/*  16:    */   public void startNonText(StylesheetHandler handler)
/*  17:    */     throws SAXException
/*  18:    */   {
/*  19: 49 */     if (this == handler.getCurrentProcessor()) {
/*  20: 51 */       handler.popProcessor();
/*  21:    */     }
/*  22: 54 */     int nChars = this.m_accumulator.length();
/*  23: 56 */     if (((nChars > 0) && ((null != this.m_xslTextElement) || (!XMLCharacterRecognizer.isWhiteSpace(this.m_accumulator)))) || (handler.isSpacePreserve()))
/*  24:    */     {
/*  25: 61 */       ElemTextLiteral elem = new ElemTextLiteral();
/*  26:    */       
/*  27: 63 */       elem.setDOMBackPointer(this.m_firstBackPointer);
/*  28: 64 */       elem.setLocaterInfo(handler.getLocator());
/*  29:    */       try
/*  30:    */       {
/*  31: 67 */         elem.setPrefixes(handler.getNamespaceSupport());
/*  32:    */       }
/*  33:    */       catch (TransformerException te)
/*  34:    */       {
/*  35: 71 */         throw new SAXException(te);
/*  36:    */       }
/*  37: 74 */       boolean doe = null != this.m_xslTextElement ? this.m_xslTextElement.getDisableOutputEscaping() : false;
/*  38:    */       
/*  39:    */ 
/*  40: 77 */       elem.setDisableOutputEscaping(doe);
/*  41: 78 */       elem.setPreserveSpace(true);
/*  42:    */       
/*  43: 80 */       char[] chars = new char[nChars];
/*  44:    */       
/*  45: 82 */       this.m_accumulator.getChars(0, nChars, chars, 0);
/*  46: 83 */       elem.setChars(chars);
/*  47:    */       
/*  48: 85 */       ElemTemplateElement parent = handler.getElemTemplateElement();
/*  49:    */       
/*  50: 87 */       parent.appendChild(elem);
/*  51:    */     }
/*  52: 90 */     this.m_accumulator.setLength(0);
/*  53: 91 */     this.m_firstBackPointer = null;
/*  54:    */   }
/*  55:    */   
/*  56: 94 */   protected Node m_firstBackPointer = null;
/*  57:    */   
/*  58:    */   public void characters(StylesheetHandler handler, char[] ch, int start, int length)
/*  59:    */     throws SAXException
/*  60:    */   {
/*  61:114 */     this.m_accumulator.append(ch, start, length);
/*  62:116 */     if (null == this.m_firstBackPointer) {
/*  63:117 */       this.m_firstBackPointer = handler.getOriginatingNode();
/*  64:    */     }
/*  65:120 */     if (this != handler.getCurrentProcessor()) {
/*  66:121 */       handler.pushProcessor(this);
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void endElement(StylesheetHandler handler, String uri, String localName, String rawName)
/*  71:    */     throws SAXException
/*  72:    */   {
/*  73:150 */     startNonText(handler);
/*  74:151 */     handler.getCurrentProcessor().endElement(handler, uri, localName, rawName);
/*  75:    */     
/*  76:153 */     handler.popProcessor();
/*  77:    */   }
/*  78:    */   
/*  79:160 */   private StringBuffer m_accumulator = new StringBuffer();
/*  80:    */   private ElemText m_xslTextElement;
/*  81:    */   
/*  82:    */   void setXslTextElement(ElemText xslTextElement)
/*  83:    */   {
/*  84:177 */     this.m_xslTextElement = xslTextElement;
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorCharacters
 * JD-Core Version:    0.7.0.1
 */