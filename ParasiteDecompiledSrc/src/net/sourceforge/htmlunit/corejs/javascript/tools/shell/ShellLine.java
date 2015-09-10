/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.shell;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.lang.reflect.Proxy;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  10:    */ 
/*  11:    */ public class ShellLine
/*  12:    */ {
/*  13:    */   public static InputStream getStream(Scriptable scope)
/*  14:    */   {
/*  15: 64 */     ClassLoader classLoader = ShellLine.class.getClassLoader();
/*  16: 65 */     if (classLoader == null) {
/*  17: 68 */       classLoader = ClassLoader.getSystemClassLoader();
/*  18:    */     }
/*  19: 70 */     if (classLoader == null) {
/*  20: 73 */       return null;
/*  21:    */     }
/*  22: 75 */     Class<?> readerClass = Kit.classOrNull(classLoader, "jline.ConsoleReader");
/*  23: 76 */     if (readerClass == null) {
/*  24: 77 */       return null;
/*  25:    */     }
/*  26:    */     try
/*  27:    */     {
/*  28: 80 */       Constructor<?> c = readerClass.getConstructor(new Class[0]);
/*  29: 81 */       Object reader = c.newInstance(new Object[0]);
/*  30:    */       
/*  31:    */ 
/*  32: 84 */       Method m = readerClass.getMethod("setBellEnabled", new Class[] { Boolean.TYPE });
/*  33: 85 */       m.invoke(reader, new Object[] { Boolean.FALSE });
/*  34:    */       
/*  35:    */ 
/*  36: 88 */       Class<?> completorClass = Kit.classOrNull(classLoader, "jline.Completor");
/*  37:    */       
/*  38: 90 */       m = readerClass.getMethod("addCompletor", new Class[] { completorClass });
/*  39: 91 */       Object completor = Proxy.newProxyInstance(classLoader, new Class[] { completorClass }, new FlexibleCompletor(completorClass, scope));
/*  40:    */       
/*  41:    */ 
/*  42: 94 */       m.invoke(reader, new Object[] { completor });
/*  43:    */       
/*  44:    */ 
/*  45: 97 */       Class<?> inputStreamClass = Kit.classOrNull(classLoader, "jline.ConsoleReaderInputStream");
/*  46:    */       
/*  47: 99 */       c = inputStreamClass.getConstructor(new Class[] { readerClass });
/*  48:100 */       return (InputStream)c.newInstance(new Object[] { reader });
/*  49:    */     }
/*  50:    */     catch (NoSuchMethodException e) {}catch (InstantiationException e) {}catch (IllegalAccessException e) {}catch (InvocationTargetException e) {}
/*  51:106 */     return null;
/*  52:    */   }
/*  53:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.ShellLine
 * JD-Core Version:    0.7.0.1
 */