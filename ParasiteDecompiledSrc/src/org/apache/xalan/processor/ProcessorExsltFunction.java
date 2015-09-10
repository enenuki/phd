/*   1:    */ package org.apache.xalan.processor;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.templates.ElemApplyImport;
/*   4:    */ import org.apache.xalan.templates.ElemApplyTemplates;
/*   5:    */ import org.apache.xalan.templates.ElemAttribute;
/*   6:    */ import org.apache.xalan.templates.ElemCallTemplate;
/*   7:    */ import org.apache.xalan.templates.ElemComment;
/*   8:    */ import org.apache.xalan.templates.ElemCopy;
/*   9:    */ import org.apache.xalan.templates.ElemCopyOf;
/*  10:    */ import org.apache.xalan.templates.ElemElement;
/*  11:    */ import org.apache.xalan.templates.ElemExsltFuncResult;
/*  12:    */ import org.apache.xalan.templates.ElemExsltFunction;
/*  13:    */ import org.apache.xalan.templates.ElemFallback;
/*  14:    */ import org.apache.xalan.templates.ElemLiteralResult;
/*  15:    */ import org.apache.xalan.templates.ElemMessage;
/*  16:    */ import org.apache.xalan.templates.ElemNumber;
/*  17:    */ import org.apache.xalan.templates.ElemPI;
/*  18:    */ import org.apache.xalan.templates.ElemParam;
/*  19:    */ import org.apache.xalan.templates.ElemTemplate;
/*  20:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*  21:    */ import org.apache.xalan.templates.ElemText;
/*  22:    */ import org.apache.xalan.templates.ElemTextLiteral;
/*  23:    */ import org.apache.xalan.templates.ElemValueOf;
/*  24:    */ import org.apache.xalan.templates.ElemVariable;
/*  25:    */ import org.apache.xalan.templates.Stylesheet;
/*  26:    */ import org.xml.sax.Attributes;
/*  27:    */ import org.xml.sax.SAXException;
/*  28:    */ 
/*  29:    */ public class ProcessorExsltFunction
/*  30:    */   extends ProcessorTemplateElem
/*  31:    */ {
/*  32:    */   static final long serialVersionUID = 2411427965578315332L;
/*  33:    */   
/*  34:    */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/*  35:    */     throws SAXException
/*  36:    */   {
/*  37: 66 */     String msg = "";
/*  38: 67 */     if (!(handler.getElemTemplateElement() instanceof Stylesheet))
/*  39:    */     {
/*  40: 69 */       msg = "func:function element must be top level.";
/*  41: 70 */       handler.error(msg, new SAXException(msg));
/*  42:    */     }
/*  43: 72 */     super.startElement(handler, uri, localName, rawName, attributes);
/*  44:    */     
/*  45: 74 */     String val = attributes.getValue("name");
/*  46: 75 */     int indexOfColon = val.indexOf(":");
/*  47: 76 */     if (indexOfColon <= 0)
/*  48:    */     {
/*  49: 86 */       msg = "func:function name must have namespace";
/*  50: 87 */       handler.error(msg, new SAXException(msg));
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected void appendAndPush(StylesheetHandler handler, ElemTemplateElement elem)
/*  55:    */     throws SAXException
/*  56:    */   {
/*  57: 99 */     super.appendAndPush(handler, elem);
/*  58:    */     
/*  59:101 */     elem.setDOMBackPointer(handler.getOriginatingNode());
/*  60:102 */     handler.getStylesheet().setTemplate((ElemTemplate)elem);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void endElement(StylesheetHandler handler, String uri, String localName, String rawName)
/*  64:    */     throws SAXException
/*  65:    */   {
/*  66:112 */     ElemTemplateElement function = handler.getElemTemplateElement();
/*  67:113 */     validate(function, handler);
/*  68:114 */     super.endElement(handler, uri, localName, rawName);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void validate(ElemTemplateElement elem, StylesheetHandler handler)
/*  72:    */     throws SAXException
/*  73:    */   {
/*  74:125 */     String msg = "";
/*  75:126 */     while (elem != null)
/*  76:    */     {
/*  77:129 */       if (((elem instanceof ElemExsltFuncResult)) && (elem.getNextSiblingElem() != null) && (!(elem.getNextSiblingElem() instanceof ElemFallback)))
/*  78:    */       {
/*  79:133 */         msg = "func:result has an illegal following sibling (only xsl:fallback allowed)";
/*  80:134 */         handler.error(msg, new SAXException(msg));
/*  81:    */       }
/*  82:137 */       if ((((elem instanceof ElemApplyImport)) || ((elem instanceof ElemApplyTemplates)) || ((elem instanceof ElemAttribute)) || ((elem instanceof ElemCallTemplate)) || ((elem instanceof ElemComment)) || ((elem instanceof ElemCopy)) || ((elem instanceof ElemCopyOf)) || ((elem instanceof ElemElement)) || ((elem instanceof ElemLiteralResult)) || ((elem instanceof ElemNumber)) || ((elem instanceof ElemPI)) || ((elem instanceof ElemText)) || ((elem instanceof ElemTextLiteral)) || ((elem instanceof ElemValueOf))) && (!ancestorIsOk(elem)))
/*  83:    */       {
/*  84:153 */         msg = "misplaced literal result in a func:function container.";
/*  85:154 */         handler.error(msg, new SAXException(msg));
/*  86:    */       }
/*  87:156 */       ElemTemplateElement nextElem = elem.getFirstChildElem();
/*  88:157 */       while (nextElem == null)
/*  89:    */       {
/*  90:159 */         nextElem = elem.getNextSiblingElem();
/*  91:160 */         if (nextElem == null) {
/*  92:161 */           elem = elem.getParentElem();
/*  93:    */         }
/*  94:162 */         if ((elem == null) || ((elem instanceof ElemExsltFunction))) {
/*  95:163 */           return;
/*  96:    */         }
/*  97:    */       }
/*  98:165 */       elem = nextElem;
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   boolean ancestorIsOk(ElemTemplateElement child)
/* 103:    */   {
/* 104:176 */     while ((child.getParentElem() != null) && (!(child.getParentElem() instanceof ElemExsltFunction)))
/* 105:    */     {
/* 106:178 */       ElemTemplateElement parent = child.getParentElem();
/* 107:179 */       if (((parent instanceof ElemExsltFuncResult)) || ((parent instanceof ElemVariable)) || ((parent instanceof ElemParam)) || ((parent instanceof ElemMessage))) {
/* 108:183 */         return true;
/* 109:    */       }
/* 110:184 */       child = parent;
/* 111:    */     }
/* 112:186 */     return false;
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorExsltFunction
 * JD-Core Version:    0.7.0.1
 */