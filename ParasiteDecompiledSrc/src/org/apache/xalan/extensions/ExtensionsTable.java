/*   1:    */ package org.apache.xalan.extensions;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.apache.xalan.res.XSLMessages;
/*   7:    */ import org.apache.xalan.templates.StylesheetRoot;
/*   8:    */ import org.apache.xpath.XPathProcessorException;
/*   9:    */ import org.apache.xpath.functions.FuncExtFunction;
/*  10:    */ 
/*  11:    */ public class ExtensionsTable
/*  12:    */ {
/*  13: 44 */   public Hashtable m_extensionFunctionNamespaces = new Hashtable();
/*  14:    */   private StylesheetRoot m_sroot;
/*  15:    */   
/*  16:    */   public ExtensionsTable(StylesheetRoot sroot)
/*  17:    */     throws TransformerException
/*  18:    */   {
/*  19: 60 */     this.m_sroot = sroot;
/*  20: 61 */     Vector extensions = this.m_sroot.getExtensions();
/*  21: 62 */     for (int i = 0; i < extensions.size(); i++)
/*  22:    */     {
/*  23: 64 */       ExtensionNamespaceSupport extNamespaceSpt = (ExtensionNamespaceSupport)extensions.get(i);
/*  24:    */       
/*  25: 66 */       ExtensionHandler extHandler = extNamespaceSpt.launch();
/*  26: 67 */       if (extHandler != null) {
/*  27: 68 */         addExtensionNamespace(extNamespaceSpt.getNamespace(), extHandler);
/*  28:    */       }
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public ExtensionHandler get(String extns)
/*  33:    */   {
/*  34: 82 */     return (ExtensionHandler)this.m_extensionFunctionNamespaces.get(extns);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void addExtensionNamespace(String uri, ExtensionHandler extNS)
/*  38:    */   {
/*  39: 96 */     this.m_extensionFunctionNamespaces.put(uri, extNS);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean functionAvailable(String ns, String funcName)
/*  43:    */     throws TransformerException
/*  44:    */   {
/*  45:111 */     boolean isAvailable = false;
/*  46:113 */     if (null != ns)
/*  47:    */     {
/*  48:115 */       ExtensionHandler extNS = (ExtensionHandler)this.m_extensionFunctionNamespaces.get(ns);
/*  49:117 */       if (extNS != null) {
/*  50:118 */         isAvailable = extNS.isFunctionAvailable(funcName);
/*  51:    */       }
/*  52:    */     }
/*  53:120 */     return isAvailable;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean elementAvailable(String ns, String elemName)
/*  57:    */     throws TransformerException
/*  58:    */   {
/*  59:135 */     boolean isAvailable = false;
/*  60:136 */     if (null != ns)
/*  61:    */     {
/*  62:138 */       ExtensionHandler extNS = (ExtensionHandler)this.m_extensionFunctionNamespaces.get(ns);
/*  63:140 */       if (extNS != null) {
/*  64:141 */         isAvailable = extNS.isElementAvailable(elemName);
/*  65:    */       }
/*  66:    */     }
/*  67:143 */     return isAvailable;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Object extFunction(String ns, String funcName, Vector argVec, Object methodKey, ExpressionContext exprContext)
/*  71:    */     throws TransformerException
/*  72:    */   {
/*  73:166 */     Object result = null;
/*  74:167 */     if (null != ns)
/*  75:    */     {
/*  76:169 */       ExtensionHandler extNS = (ExtensionHandler)this.m_extensionFunctionNamespaces.get(ns);
/*  77:171 */       if (null != extNS) {
/*  78:    */         try
/*  79:    */         {
/*  80:175 */           result = extNS.callFunction(funcName, argVec, methodKey, exprContext);
/*  81:    */         }
/*  82:    */         catch (TransformerException e)
/*  83:    */         {
/*  84:180 */           throw e;
/*  85:    */         }
/*  86:    */         catch (Exception e)
/*  87:    */         {
/*  88:184 */           throw new TransformerException(e);
/*  89:    */         }
/*  90:    */       } else {
/*  91:189 */         throw new XPathProcessorException(XSLMessages.createMessage("ER_EXTENSION_FUNC_UNKNOWN", new Object[] { ns, funcName }));
/*  92:    */       }
/*  93:    */     }
/*  94:193 */     return result;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Object extFunction(FuncExtFunction extFunction, Vector argVec, ExpressionContext exprContext)
/*  98:    */     throws TransformerException
/*  99:    */   {
/* 100:212 */     Object result = null;
/* 101:213 */     String ns = extFunction.getNamespace();
/* 102:214 */     if (null != ns)
/* 103:    */     {
/* 104:216 */       ExtensionHandler extNS = (ExtensionHandler)this.m_extensionFunctionNamespaces.get(ns);
/* 105:218 */       if (null != extNS) {
/* 106:    */         try
/* 107:    */         {
/* 108:222 */           result = extNS.callFunction(extFunction, argVec, exprContext);
/* 109:    */         }
/* 110:    */         catch (TransformerException e)
/* 111:    */         {
/* 112:226 */           throw e;
/* 113:    */         }
/* 114:    */         catch (Exception e)
/* 115:    */         {
/* 116:230 */           throw new TransformerException(e);
/* 117:    */         }
/* 118:    */       } else {
/* 119:235 */         throw new XPathProcessorException(XSLMessages.createMessage("ER_EXTENSION_FUNC_UNKNOWN", new Object[] { ns, extFunction.getFunctionName() }));
/* 120:    */       }
/* 121:    */     }
/* 122:239 */     return result;
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.ExtensionsTable
 * JD-Core Version:    0.7.0.1
 */