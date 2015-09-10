/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript;
/*  2:   */ 
/*  3:   */ public class RhinoSecurityManager
/*  4:   */   extends SecurityManager
/*  5:   */ {
/*  6:   */   protected Class getCurrentScriptClass()
/*  7:   */   {
/*  8:19 */     Class[] context = getClassContext();
/*  9:20 */     for (Class c : context) {
/* 10:21 */       if (((c != InterpretedFunction.class) && (NativeFunction.class.isAssignableFrom(c))) || (PolicySecurityController.SecureCaller.class.isAssignableFrom(c))) {
/* 11:23 */         return c;
/* 12:   */       }
/* 13:   */     }
/* 14:26 */     return null;
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.RhinoSecurityManager
 * JD-Core Version:    0.7.0.1
 */