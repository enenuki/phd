/*  1:   */ package org.apache.xalan.extensions;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Vector;
/*  5:   */ import javax.xml.transform.TransformerException;
/*  6:   */ import javax.xml.xpath.XPathFunction;
/*  7:   */ import javax.xml.xpath.XPathFunctionException;
/*  8:   */ 
/*  9:   */ public class XPathFunctionImpl
/* 10:   */   implements XPathFunction
/* 11:   */ {
/* 12:   */   private ExtensionHandler m_handler;
/* 13:   */   private String m_funcName;
/* 14:   */   
/* 15:   */   public XPathFunctionImpl(ExtensionHandler handler, String funcName)
/* 16:   */   {
/* 17:43 */     this.m_handler = handler;
/* 18:44 */     this.m_funcName = funcName;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Object evaluate(List args)
/* 22:   */     throws XPathFunctionException
/* 23:   */   {
/* 24:53 */     Vector argsVec = listToVector(args);
/* 25:   */     try
/* 26:   */     {
/* 27:57 */       return this.m_handler.callFunction(this.m_funcName, argsVec, null, null);
/* 28:   */     }
/* 29:   */     catch (TransformerException e)
/* 30:   */     {
/* 31:61 */       throw new XPathFunctionException(e);
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   private static Vector listToVector(List args)
/* 36:   */   {
/* 37:71 */     if (args == null) {
/* 38:72 */       return null;
/* 39:   */     }
/* 40:73 */     if ((args instanceof Vector)) {
/* 41:74 */       return (Vector)args;
/* 42:   */     }
/* 43:77 */     Vector result = new Vector();
/* 44:78 */     result.addAll(args);
/* 45:79 */     return result;
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.XPathFunctionImpl
 * JD-Core Version:    0.7.0.1
 */