/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.ErrorListener;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.extensions.ExtensionHandler;
/*   6:    */ import org.apache.xalan.extensions.ExtensionNamespacesManager;
/*   7:    */ import org.apache.xalan.extensions.ExtensionsTable;
/*   8:    */ import org.apache.xalan.res.XSLMessages;
/*   9:    */ import org.apache.xalan.trace.TraceManager;
/*  10:    */ import org.apache.xalan.transformer.TransformerImpl;
/*  11:    */ import org.apache.xml.serializer.SerializationHandler;
/*  12:    */ import org.apache.xpath.XPathContext;
/*  13:    */ import org.w3c.dom.Node;
/*  14:    */ import org.xml.sax.SAXException;
/*  15:    */ 
/*  16:    */ public class ElemExtensionCall
/*  17:    */   extends ElemLiteralResult
/*  18:    */ {
/*  19:    */   static final long serialVersionUID = 3171339708500216920L;
/*  20:    */   String m_extns;
/*  21:    */   String m_lang;
/*  22:    */   String m_srcURL;
/*  23:    */   String m_scriptSrc;
/*  24: 60 */   ElemExtensionDecl m_decl = null;
/*  25:    */   
/*  26:    */   public int getXSLToken()
/*  27:    */   {
/*  28: 70 */     return 79;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void compose(StylesheetRoot sroot)
/*  32:    */     throws TransformerException
/*  33:    */   {
/*  34: 93 */     super.compose(sroot);
/*  35: 94 */     this.m_extns = getNamespace();
/*  36: 95 */     this.m_decl = getElemExtensionDecl(sroot, this.m_extns);
/*  37: 98 */     if (this.m_decl == null) {
/*  38: 99 */       sroot.getExtensionNamespacesManager().registerExtension(this.m_extns);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   private ElemExtensionDecl getElemExtensionDecl(StylesheetRoot stylesheet, String namespace)
/*  43:    */   {
/*  44:115 */     ElemExtensionDecl decl = null;
/*  45:116 */     int n = stylesheet.getGlobalImportCount();
/*  46:118 */     for (int i = 0; i < n; i++)
/*  47:    */     {
/*  48:120 */       Stylesheet imported = stylesheet.getGlobalImport(i);
/*  49:122 */       for (ElemTemplateElement child = imported.getFirstChildElem(); child != null; child = child.getNextSiblingElem()) {
/*  50:125 */         if (85 == child.getXSLToken())
/*  51:    */         {
/*  52:127 */           decl = (ElemExtensionDecl)child;
/*  53:    */           
/*  54:129 */           String prefix = decl.getPrefix();
/*  55:130 */           String declNamespace = child.getNamespaceForPrefix(prefix);
/*  56:132 */           if (namespace.equals(declNamespace)) {
/*  57:134 */             return decl;
/*  58:    */           }
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62:140 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   private void executeFallbacks(TransformerImpl transformer)
/*  66:    */     throws TransformerException
/*  67:    */   {
/*  68:154 */     for (ElemTemplateElement child = this.m_firstChild; child != null; child = child.m_nextSibling) {
/*  69:157 */       if (child.getXSLToken() == 57) {
/*  70:    */         try
/*  71:    */         {
/*  72:161 */           transformer.pushElemTemplateElement(child);
/*  73:162 */           ((ElemFallback)child).executeFallback(transformer);
/*  74:    */         }
/*  75:    */         finally
/*  76:    */         {
/*  77:166 */           transformer.popElemTemplateElement();
/*  78:    */         }
/*  79:    */       }
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   private boolean hasFallbackChildren()
/*  84:    */   {
/*  85:180 */     for (ElemTemplateElement child = this.m_firstChild; child != null; child = child.m_nextSibling) {
/*  86:183 */       if (child.getXSLToken() == 57) {
/*  87:184 */         return true;
/*  88:    */       }
/*  89:    */     }
/*  90:187 */     return false;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void execute(TransformerImpl transformer)
/*  94:    */     throws TransformerException
/*  95:    */   {
/*  96:201 */     if (transformer.getStylesheet().isSecureProcessing()) {
/*  97:202 */       throw new TransformerException(XSLMessages.createMessage("ER_EXTENSION_ELEMENT_NOT_ALLOWED_IN_SECURE_PROCESSING", new Object[] { getRawName() }));
/*  98:    */     }
/*  99:207 */     if (transformer.getDebug()) {
/* 100:208 */       transformer.getTraceManager().fireTraceEvent(this);
/* 101:    */     }
/* 102:    */     try
/* 103:    */     {
/* 104:211 */       transformer.getResultTreeHandler().flushPending();
/* 105:    */       
/* 106:213 */       ExtensionsTable etable = transformer.getExtensionsTable();
/* 107:214 */       ExtensionHandler nsh = etable.get(this.m_extns);
/* 108:216 */       if (null == nsh)
/* 109:    */       {
/* 110:218 */         if (hasFallbackChildren())
/* 111:    */         {
/* 112:220 */           executeFallbacks(transformer);
/* 113:    */         }
/* 114:    */         else
/* 115:    */         {
/* 116:224 */           TransformerException te = new TransformerException(XSLMessages.createMessage("ER_CALL_TO_EXT_FAILED", new Object[] { getNodeName() }));
/* 117:    */           
/* 118:226 */           transformer.getErrorListener().fatalError(te);
/* 119:    */         }
/* 120:229 */         return;
/* 121:    */       }
/* 122:    */       try
/* 123:    */       {
/* 124:234 */         nsh.processElement(getLocalName(), this, transformer, getStylesheet(), this);
/* 125:    */       }
/* 126:    */       catch (Exception e)
/* 127:    */       {
/* 128:240 */         if (hasFallbackChildren())
/* 129:    */         {
/* 130:241 */           executeFallbacks(transformer);
/* 131:    */         }
/* 132:244 */         else if ((e instanceof TransformerException))
/* 133:    */         {
/* 134:246 */           TransformerException te = (TransformerException)e;
/* 135:247 */           if (null == te.getLocator()) {
/* 136:248 */             te.setLocator(this);
/* 137:    */           }
/* 138:250 */           transformer.getErrorListener().fatalError(te);
/* 139:    */         }
/* 140:252 */         else if ((e instanceof RuntimeException))
/* 141:    */         {
/* 142:254 */           transformer.getErrorListener().fatalError(new TransformerException(e));
/* 143:    */         }
/* 144:    */         else
/* 145:    */         {
/* 146:258 */           transformer.getErrorListener().warning(new TransformerException(e));
/* 147:    */         }
/* 148:    */       }
/* 149:    */     }
/* 150:    */     catch (TransformerException e)
/* 151:    */     {
/* 152:265 */       transformer.getErrorListener().fatalError(e);
/* 153:    */     }
/* 154:    */     catch (SAXException se)
/* 155:    */     {
/* 156:268 */       throw new TransformerException(se);
/* 157:    */     }
/* 158:270 */     if (transformer.getDebug()) {
/* 159:271 */       transformer.getTraceManager().fireTraceEndEvent(this);
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String getAttribute(String rawName, Node sourceNode, TransformerImpl transformer)
/* 164:    */     throws TransformerException
/* 165:    */   {
/* 166:292 */     AVT avt = getLiteralResultAttribute(rawName);
/* 167:294 */     if ((null != avt) && (avt.getRawName().equals(rawName)))
/* 168:    */     {
/* 169:296 */       XPathContext xctxt = transformer.getXPathContext();
/* 170:    */       
/* 171:298 */       return avt.evaluate(xctxt, xctxt.getDTMHandleFromNode(sourceNode), this);
/* 172:    */     }
/* 173:303 */     return null;
/* 174:    */   }
/* 175:    */   
/* 176:    */   protected boolean accept(XSLTVisitor visitor)
/* 177:    */   {
/* 178:315 */     return visitor.visitExtensionElement(this);
/* 179:    */   }
/* 180:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemExtensionCall
 * JD-Core Version:    0.7.0.1
 */