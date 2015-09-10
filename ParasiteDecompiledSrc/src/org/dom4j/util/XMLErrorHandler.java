/*   1:    */ package org.dom4j.util;
/*   2:    */ 
/*   3:    */ import org.dom4j.DocumentHelper;
/*   4:    */ import org.dom4j.Element;
/*   5:    */ import org.dom4j.QName;
/*   6:    */ import org.xml.sax.ErrorHandler;
/*   7:    */ import org.xml.sax.SAXParseException;
/*   8:    */ 
/*   9:    */ public class XMLErrorHandler
/*  10:    */   implements ErrorHandler
/*  11:    */ {
/*  12: 26 */   protected static final QName ERROR_QNAME = QName.get("error");
/*  13: 28 */   protected static final QName FATALERROR_QNAME = QName.get("fatalError");
/*  14: 30 */   protected static final QName WARNING_QNAME = QName.get("warning");
/*  15:    */   private Element errors;
/*  16: 36 */   private QName errorQName = ERROR_QNAME;
/*  17: 39 */   private QName fatalErrorQName = FATALERROR_QNAME;
/*  18: 42 */   private QName warningQName = WARNING_QNAME;
/*  19:    */   
/*  20:    */   public XMLErrorHandler()
/*  21:    */   {
/*  22: 45 */     this.errors = DocumentHelper.createElement("errors");
/*  23:    */   }
/*  24:    */   
/*  25:    */   public XMLErrorHandler(Element errors)
/*  26:    */   {
/*  27: 49 */     this.errors = errors;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void error(SAXParseException e)
/*  31:    */   {
/*  32: 53 */     Element element = this.errors.addElement(this.errorQName);
/*  33: 54 */     addException(element, e);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void fatalError(SAXParseException e)
/*  37:    */   {
/*  38: 58 */     Element element = this.errors.addElement(this.fatalErrorQName);
/*  39: 59 */     addException(element, e);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void warning(SAXParseException e)
/*  43:    */   {
/*  44: 63 */     Element element = this.errors.addElement(this.warningQName);
/*  45: 64 */     addException(element, e);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Element getErrors()
/*  49:    */   {
/*  50: 70 */     return this.errors;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setErrors(Element errors)
/*  54:    */   {
/*  55: 74 */     this.errors = errors;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public QName getErrorQName()
/*  59:    */   {
/*  60: 79 */     return this.errorQName;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setErrorQName(QName errorQName)
/*  64:    */   {
/*  65: 83 */     this.errorQName = errorQName;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public QName getFatalErrorQName()
/*  69:    */   {
/*  70: 87 */     return this.fatalErrorQName;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setFatalErrorQName(QName fatalErrorQName)
/*  74:    */   {
/*  75: 91 */     this.fatalErrorQName = fatalErrorQName;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public QName getWarningQName()
/*  79:    */   {
/*  80: 95 */     return this.warningQName;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setWarningQName(QName warningQName)
/*  84:    */   {
/*  85: 99 */     this.warningQName = warningQName;
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected void addException(Element element, SAXParseException e)
/*  89:    */   {
/*  90:114 */     element.addAttribute("column", Integer.toString(e.getColumnNumber()));
/*  91:115 */     element.addAttribute("line", Integer.toString(e.getLineNumber()));
/*  92:    */     
/*  93:117 */     String publicID = e.getPublicId();
/*  94:119 */     if ((publicID != null) && (publicID.length() > 0)) {
/*  95:120 */       element.addAttribute("publicID", publicID);
/*  96:    */     }
/*  97:123 */     String systemID = e.getSystemId();
/*  98:125 */     if ((systemID != null) && (systemID.length() > 0)) {
/*  99:126 */       element.addAttribute("systemID", systemID);
/* 100:    */     }
/* 101:129 */     element.addText(e.getMessage());
/* 102:    */   }
/* 103:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.util.XMLErrorHandler
 * JD-Core Version:    0.7.0.1
 */