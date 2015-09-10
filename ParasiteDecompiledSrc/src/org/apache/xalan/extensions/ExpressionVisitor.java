/*  1:   */ package org.apache.xalan.extensions;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.templates.ElemTemplateElement;
/*  4:   */ import org.apache.xalan.templates.StylesheetRoot;
/*  5:   */ import org.apache.xpath.ExpressionOwner;
/*  6:   */ import org.apache.xpath.XPathVisitor;
/*  7:   */ import org.apache.xpath.functions.FuncExtFunction;
/*  8:   */ import org.apache.xpath.functions.FuncExtFunctionAvailable;
/*  9:   */ import org.apache.xpath.functions.Function;
/* 10:   */ import org.apache.xpath.functions.FunctionOneArg;
/* 11:   */ 
/* 12:   */ public class ExpressionVisitor
/* 13:   */   extends XPathVisitor
/* 14:   */ {
/* 15:   */   private StylesheetRoot m_sroot;
/* 16:   */   
/* 17:   */   public ExpressionVisitor(StylesheetRoot sroot)
/* 18:   */   {
/* 19:51 */     this.m_sroot = sroot;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean visitFunction(ExpressionOwner owner, Function func)
/* 23:   */   {
/* 24:64 */     if ((func instanceof FuncExtFunction))
/* 25:   */     {
/* 26:66 */       String namespace = ((FuncExtFunction)func).getNamespace();
/* 27:67 */       this.m_sroot.getExtensionNamespacesManager().registerExtension(namespace);
/* 28:   */     }
/* 29:69 */     else if ((func instanceof FuncExtFunctionAvailable))
/* 30:   */     {
/* 31:71 */       String arg = ((FuncExtFunctionAvailable)func).getArg0().toString();
/* 32:72 */       if (arg.indexOf(":") > 0)
/* 33:   */       {
/* 34:74 */         String prefix = arg.substring(0, arg.indexOf(":"));
/* 35:75 */         String namespace = this.m_sroot.getNamespaceForPrefix(prefix);
/* 36:76 */         this.m_sroot.getExtensionNamespacesManager().registerExtension(namespace);
/* 37:   */       }
/* 38:   */     }
/* 39:79 */     return true;
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.ExpressionVisitor
 * JD-Core Version:    0.7.0.1
 */