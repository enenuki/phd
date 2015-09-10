/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.security.AccessController;
/*   4:    */ import java.security.PrivilegedAction;
/*   5:    */ import java.security.ProtectionDomain;
/*   6:    */ 
/*   7:    */ public class SecurityUtilities
/*   8:    */ {
/*   9:    */   public static String getSystemProperty(String name)
/*  10:    */   {
/*  11: 59 */     (String)AccessController.doPrivileged(new PrivilegedAction()
/*  12:    */     {
/*  13:    */       public String run()
/*  14:    */       {
/*  15: 64 */         return System.getProperty(this.val$name);
/*  16:    */       }
/*  17:    */     });
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static ProtectionDomain getProtectionDomain(Class<?> clazz)
/*  21:    */   {
/*  22: 71 */     (ProtectionDomain)AccessController.doPrivileged(new PrivilegedAction()
/*  23:    */     {
/*  24:    */       public ProtectionDomain run()
/*  25:    */       {
/*  26: 76 */         return this.val$clazz.getProtectionDomain();
/*  27:    */       }
/*  28:    */     });
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static ProtectionDomain getScriptProtectionDomain()
/*  32:    */   {
/*  33: 89 */     SecurityManager securityManager = System.getSecurityManager();
/*  34: 90 */     if ((securityManager instanceof RhinoSecurityManager)) {
/*  35: 91 */       (ProtectionDomain)AccessController.doPrivileged(new PrivilegedAction()
/*  36:    */       {
/*  37:    */         public ProtectionDomain run()
/*  38:    */         {
/*  39: 94 */           Class c = ((RhinoSecurityManager)this.val$securityManager).getCurrentScriptClass();
/*  40:    */           
/*  41: 96 */           return c == null ? null : c.getProtectionDomain();
/*  42:    */         }
/*  43:    */       });
/*  44:    */     }
/*  45:101 */     return null;
/*  46:    */   }
/*  47:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.SecurityUtilities
 * JD-Core Version:    0.7.0.1
 */