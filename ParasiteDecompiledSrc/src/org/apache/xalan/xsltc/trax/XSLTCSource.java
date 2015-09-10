/*   1:    */ package org.apache.xalan.xsltc.trax;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.Source;
/*   4:    */ import javax.xml.transform.stream.StreamSource;
/*   5:    */ import org.apache.xalan.xsltc.DOM;
/*   6:    */ import org.apache.xalan.xsltc.StripFilter;
/*   7:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*   8:    */ import org.apache.xalan.xsltc.dom.DOMWSFilter;
/*   9:    */ import org.apache.xalan.xsltc.dom.SAXImpl;
/*  10:    */ import org.apache.xalan.xsltc.dom.XSLTCDTMManager;
/*  11:    */ import org.apache.xalan.xsltc.runtime.AbstractTranslet;
/*  12:    */ import org.xml.sax.SAXException;
/*  13:    */ 
/*  14:    */ public final class XSLTCSource
/*  15:    */   implements Source
/*  16:    */ {
/*  17: 43 */   private String _systemId = null;
/*  18: 44 */   private Source _source = null;
/*  19: 45 */   private ThreadLocal _dom = new ThreadLocal();
/*  20:    */   
/*  21:    */   public XSLTCSource(String systemId)
/*  22:    */   {
/*  23: 52 */     this._systemId = systemId;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public XSLTCSource(Source source)
/*  27:    */   {
/*  28: 60 */     this._source = source;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setSystemId(String systemId)
/*  32:    */   {
/*  33: 72 */     this._systemId = systemId;
/*  34: 73 */     if (this._source != null) {
/*  35: 74 */       this._source.setSystemId(systemId);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getSystemId()
/*  40:    */   {
/*  41: 85 */     if (this._source != null) {
/*  42: 86 */       return this._source.getSystemId();
/*  43:    */     }
/*  44: 89 */     return this._systemId;
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected DOM getDOM(XSLTCDTMManager dtmManager, AbstractTranslet translet)
/*  48:    */     throws SAXException
/*  49:    */   {
/*  50: 99 */     SAXImpl idom = (SAXImpl)this._dom.get();
/*  51:101 */     if (idom != null)
/*  52:    */     {
/*  53:102 */       if (dtmManager != null) {
/*  54:103 */         idom.migrateTo(dtmManager);
/*  55:    */       }
/*  56:    */     }
/*  57:    */     else
/*  58:    */     {
/*  59:107 */       Source source = this._source;
/*  60:108 */       if (source == null) {
/*  61:109 */         if ((this._systemId != null) && (this._systemId.length() > 0))
/*  62:    */         {
/*  63:110 */           source = new StreamSource(this._systemId);
/*  64:    */         }
/*  65:    */         else
/*  66:    */         {
/*  67:113 */           ErrorMsg err = new ErrorMsg("XSLTC_SOURCE_ERR");
/*  68:114 */           throw new SAXException(err.toString());
/*  69:    */         }
/*  70:    */       }
/*  71:118 */       DOMWSFilter wsfilter = null;
/*  72:119 */       if ((translet != null) && ((translet instanceof StripFilter))) {
/*  73:120 */         wsfilter = new DOMWSFilter(translet);
/*  74:    */       }
/*  75:123 */       boolean hasIdCall = translet != null ? translet.hasIdCall() : false;
/*  76:125 */       if (dtmManager == null) {
/*  77:126 */         dtmManager = XSLTCDTMManager.newInstance();
/*  78:    */       }
/*  79:129 */       idom = (SAXImpl)dtmManager.getDTM(source, true, wsfilter, false, false, hasIdCall);
/*  80:    */       
/*  81:131 */       String systemId = getSystemId();
/*  82:132 */       if (systemId != null) {
/*  83:133 */         idom.setDocumentURI(systemId);
/*  84:    */       }
/*  85:135 */       this._dom.set(idom);
/*  86:    */     }
/*  87:137 */     return idom;
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.trax.XSLTCSource
 * JD-Core Version:    0.7.0.1
 */