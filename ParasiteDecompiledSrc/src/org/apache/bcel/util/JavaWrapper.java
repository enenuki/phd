/*   1:    */ package org.apache.bcel.util;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.lang.reflect.Modifier;
/*   6:    */ 
/*   7:    */ public class JavaWrapper
/*   8:    */ {
/*   9:    */   private ClassLoader loader;
/*  10:    */   
/*  11:    */   private static ClassLoader getClassLoader()
/*  12:    */   {
/*  13: 79 */     String s = System.getProperty("bcel.classloader");
/*  14: 81 */     if ((s == null) || ("".equals(s))) {
/*  15: 82 */       s = "org.apache.bcel.util.ClassLoader";
/*  16:    */     }
/*  17:    */     try
/*  18:    */     {
/*  19: 85 */       return (ClassLoader)Class.forName(s).newInstance();
/*  20:    */     }
/*  21:    */     catch (Exception e)
/*  22:    */     {
/*  23: 87 */       throw new RuntimeException(e.toString());
/*  24:    */     }
/*  25:    */   }
/*  26:    */   
/*  27:    */   public JavaWrapper(ClassLoader loader)
/*  28:    */   {
/*  29: 92 */     this.loader = loader;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public JavaWrapper()
/*  33:    */   {
/*  34: 96 */     this(getClassLoader());
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void runMain(String class_name, String[] argv)
/*  38:    */     throws ClassNotFoundException
/*  39:    */   {
/*  40:106 */     Class cl = this.loader.loadClass(class_name);
/*  41:107 */     Method method = null;
/*  42:    */     try
/*  43:    */     {
/*  44:110 */       method = cl.getMethod("main", new Class[] { argv.getClass() });
/*  45:    */       
/*  46:    */ 
/*  47:    */ 
/*  48:114 */       int m = method.getModifiers();
/*  49:115 */       Class r = method.getReturnType();
/*  50:117 */       if ((!Modifier.isPublic(m)) || (!Modifier.isStatic(m)) || (Modifier.isAbstract(m)) || (r != Void.TYPE)) {
/*  51:119 */         throw new NoSuchMethodException();
/*  52:    */       }
/*  53:    */     }
/*  54:    */     catch (NoSuchMethodException no)
/*  55:    */     {
/*  56:121 */       System.out.println("In class " + class_name + ": public static void main(String[] argv) is not defined");
/*  57:    */       
/*  58:123 */       return;
/*  59:    */     }
/*  60:    */     try
/*  61:    */     {
/*  62:127 */       method.invoke(null, new Object[] { argv });
/*  63:    */     }
/*  64:    */     catch (Exception ex)
/*  65:    */     {
/*  66:129 */       ex.printStackTrace();
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static void main(String[] argv)
/*  71:    */     throws Exception
/*  72:    */   {
/*  73:139 */     if (argv.length == 0)
/*  74:    */     {
/*  75:140 */       System.out.println("Missing class name.");
/*  76:141 */       return;
/*  77:    */     }
/*  78:144 */     String class_name = argv[0];
/*  79:145 */     String[] new_argv = new String[argv.length - 1];
/*  80:146 */     System.arraycopy(argv, 1, new_argv, 0, new_argv.length);
/*  81:    */     
/*  82:148 */     JavaWrapper wrapper = new JavaWrapper();
/*  83:149 */     wrapper.runMain(class_name, new_argv);
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.JavaWrapper
 * JD-Core Version:    0.7.0.1
 */