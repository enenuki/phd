/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Member;
/*   4:    */ import java.util.Iterator;
/*   5:    */ 
/*   6:    */ public abstract class VMBridge
/*   7:    */ {
/*   8: 49 */   static final VMBridge instance = ;
/*   9:    */   
/*  10:    */   private static VMBridge makeInstance()
/*  11:    */   {
/*  12: 53 */     String[] classNames = { "net.sourceforge.htmlunit.corejs.javascript.VMBridge_custom", "net.sourceforge.htmlunit.corejs.javascript.jdk15.VMBridge_jdk15", "net.sourceforge.htmlunit.corejs.javascript.jdk13.VMBridge_jdk13", "net.sourceforge.htmlunit.corejs.javascript.jdk11.VMBridge_jdk11" };
/*  13: 59 */     for (int i = 0; i != classNames.length; i++)
/*  14:    */     {
/*  15: 60 */       String className = classNames[i];
/*  16: 61 */       Class<?> cl = Kit.classOrNull(className);
/*  17: 62 */       if (cl != null)
/*  18:    */       {
/*  19: 63 */         VMBridge bridge = (VMBridge)Kit.newInstanceOrNull(cl);
/*  20: 64 */         if (bridge != null) {
/*  21: 65 */           return bridge;
/*  22:    */         }
/*  23:    */       }
/*  24:    */     }
/*  25: 69 */     throw new IllegalStateException("Failed to create VMBridge instance");
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected abstract Object getThreadContextHelper();
/*  29:    */   
/*  30:    */   protected abstract Context getContext(Object paramObject);
/*  31:    */   
/*  32:    */   protected abstract void setContext(Object paramObject, Context paramContext);
/*  33:    */   
/*  34:    */   protected abstract ClassLoader getCurrentThreadClassLoader();
/*  35:    */   
/*  36:    */   protected abstract boolean tryToMakeAccessible(Object paramObject);
/*  37:    */   
/*  38:    */   protected Object getInterfaceProxyHelper(ContextFactory cf, Class<?>[] interfaces)
/*  39:    */   {
/*  40:133 */     throw Context.reportRuntimeError("VMBridge.getInterfaceProxyHelper is not supported");
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected Object newInterfaceProxy(Object proxyHelper, ContextFactory cf, InterfaceAdapter adapter, Object target, Scriptable topScope)
/*  44:    */   {
/*  45:156 */     throw Context.reportRuntimeError("VMBridge.newInterfaceProxy is not supported");
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected abstract boolean isVarArgs(Member paramMember);
/*  49:    */   
/*  50:    */   public Iterator<?> getJavaIterator(Context cx, Scriptable scope, Object obj)
/*  51:    */   {
/*  52:174 */     if ((obj instanceof Wrapper))
/*  53:    */     {
/*  54:175 */       Object unwrapped = ((Wrapper)obj).unwrap();
/*  55:176 */       Iterator<?> iterator = null;
/*  56:177 */       if ((unwrapped instanceof Iterator)) {
/*  57:178 */         iterator = (Iterator)unwrapped;
/*  58:    */       }
/*  59:179 */       return iterator;
/*  60:    */     }
/*  61:181 */     return null;
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.VMBridge
 * JD-Core Version:    0.7.0.1
 */