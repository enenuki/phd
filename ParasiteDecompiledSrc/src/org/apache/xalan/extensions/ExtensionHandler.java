/*  1:   */ package org.apache.xalan.extensions;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.Vector;
/*  5:   */ import javax.xml.transform.TransformerException;
/*  6:   */ import org.apache.xalan.templates.ElemTemplateElement;
/*  7:   */ import org.apache.xalan.templates.Stylesheet;
/*  8:   */ import org.apache.xalan.transformer.TransformerImpl;
/*  9:   */ import org.apache.xpath.functions.FuncExtFunction;
/* 10:   */ 
/* 11:   */ public abstract class ExtensionHandler
/* 12:   */ {
/* 13:   */   protected String m_namespaceUri;
/* 14:   */   protected String m_scriptLang;
/* 15:   */   
/* 16:   */   static Class getClassForName(String className)
/* 17:   */     throws ClassNotFoundException
/* 18:   */   {
/* 19:61 */     if (className.equals("org.apache.xalan.xslt.extensions.Redirect")) {
/* 20:62 */       className = "org.apache.xalan.lib.Redirect";
/* 21:   */     }
/* 22:65 */     return ObjectFactory.findProviderClass(className, ObjectFactory.findClassLoader(), true);
/* 23:   */   }
/* 24:   */   
/* 25:   */   protected ExtensionHandler(String namespaceUri, String scriptLang)
/* 26:   */   {
/* 27:78 */     this.m_namespaceUri = namespaceUri;
/* 28:79 */     this.m_scriptLang = scriptLang;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public abstract boolean isFunctionAvailable(String paramString);
/* 32:   */   
/* 33:   */   public abstract boolean isElementAvailable(String paramString);
/* 34:   */   
/* 35:   */   public abstract Object callFunction(String paramString, Vector paramVector, Object paramObject, ExpressionContext paramExpressionContext)
/* 36:   */     throws TransformerException;
/* 37:   */   
/* 38:   */   public abstract Object callFunction(FuncExtFunction paramFuncExtFunction, Vector paramVector, ExpressionContext paramExpressionContext)
/* 39:   */     throws TransformerException;
/* 40:   */   
/* 41:   */   public abstract void processElement(String paramString, ElemTemplateElement paramElemTemplateElement, TransformerImpl paramTransformerImpl, Stylesheet paramStylesheet, Object paramObject)
/* 42:   */     throws TransformerException, IOException;
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.ExtensionHandler
 * JD-Core Version:    0.7.0.1
 */