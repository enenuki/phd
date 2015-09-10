/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public abstract class SecurityController
/*   4:    */ {
/*   5:    */   private static SecurityController global;
/*   6:    */   
/*   7:    */   static SecurityController global()
/*   8:    */   {
/*   9: 75 */     return global;
/*  10:    */   }
/*  11:    */   
/*  12:    */   public static boolean hasGlobal()
/*  13:    */   {
/*  14: 84 */     return global != null;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public static void initGlobal(SecurityController controller)
/*  18:    */   {
/*  19:101 */     if (controller == null) {
/*  20:101 */       throw new IllegalArgumentException();
/*  21:    */     }
/*  22:102 */     if (global != null) {
/*  23:103 */       throw new SecurityException("Cannot overwrite already installed global SecurityController");
/*  24:    */     }
/*  25:105 */     global = controller;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public abstract GeneratedClassLoader createClassLoader(ClassLoader paramClassLoader, Object paramObject);
/*  29:    */   
/*  30:    */   public static GeneratedClassLoader createLoader(ClassLoader parent, Object staticDomain)
/*  31:    */   {
/*  32:136 */     Context cx = Context.getContext();
/*  33:137 */     if (parent == null) {
/*  34:138 */       parent = cx.getApplicationClassLoader();
/*  35:    */     }
/*  36:140 */     SecurityController sc = cx.getSecurityController();
/*  37:    */     GeneratedClassLoader loader;
/*  38:    */     GeneratedClassLoader loader;
/*  39:142 */     if (sc == null)
/*  40:    */     {
/*  41:143 */       loader = cx.createClassLoader(parent);
/*  42:    */     }
/*  43:    */     else
/*  44:    */     {
/*  45:145 */       Object dynamicDomain = sc.getDynamicSecurityDomain(staticDomain);
/*  46:146 */       loader = sc.createClassLoader(parent, dynamicDomain);
/*  47:    */     }
/*  48:148 */     return loader;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static Class<?> getStaticSecurityDomainClass()
/*  52:    */   {
/*  53:152 */     SecurityController sc = Context.getContext().getSecurityController();
/*  54:153 */     return sc == null ? null : sc.getStaticSecurityDomainClassInternal();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Class<?> getStaticSecurityDomainClassInternal()
/*  58:    */   {
/*  59:158 */     return null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public abstract Object getDynamicSecurityDomain(Object paramObject);
/*  63:    */   
/*  64:    */   public Object callWithDomain(Object securityDomain, Context cx, final Callable callable, Scriptable scope, final Scriptable thisObj, final Object[] args)
/*  65:    */   {
/*  66:189 */     execWithDomain(cx, scope, new Script()
/*  67:    */     {
/*  68:    */       public Object exec(Context cx, Scriptable scope)
/*  69:    */       {
/*  70:193 */         return callable.call(cx, scope, thisObj, args);
/*  71:    */       }
/*  72:193 */     }, securityDomain);
/*  73:    */   }
/*  74:    */   
/*  75:    */   /**
/*  76:    */    * @deprecated
/*  77:    */    */
/*  78:    */   public Object execWithDomain(Context cx, Scriptable scope, Script script, Object securityDomain)
/*  79:    */   {
/*  80:207 */     throw new IllegalStateException("callWithDomain should be overridden");
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.SecurityController
 * JD-Core Version:    0.7.0.1
 */