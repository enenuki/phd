/*   1:    */ package javassist.tools.reflect;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import javassist.ClassPool;
/*   5:    */ import javassist.CtClass;
/*   6:    */ 
/*   7:    */ public class Compiler
/*   8:    */ {
/*   9:    */   public static void main(String[] args)
/*  10:    */     throws Exception
/*  11:    */   {
/*  12: 73 */     if (args.length == 0)
/*  13:    */     {
/*  14: 74 */       help(System.err);
/*  15: 75 */       return;
/*  16:    */     }
/*  17: 78 */     CompiledClass[] entries = new CompiledClass[args.length];
/*  18: 79 */     int n = parse(args, entries);
/*  19: 81 */     if (n < 1)
/*  20:    */     {
/*  21: 82 */       System.err.println("bad parameter.");
/*  22: 83 */       return;
/*  23:    */     }
/*  24: 86 */     processClasses(entries, n);
/*  25:    */   }
/*  26:    */   
/*  27:    */   private static void processClasses(CompiledClass[] entries, int n)
/*  28:    */     throws Exception
/*  29:    */   {
/*  30: 92 */     Reflection implementor = new Reflection();
/*  31: 93 */     ClassPool pool = ClassPool.getDefault();
/*  32: 94 */     implementor.start(pool);
/*  33: 96 */     for (int i = 0; i < n; i++)
/*  34:    */     {
/*  35: 97 */       CtClass c = pool.get(entries[i].classname);
/*  36: 98 */       if ((entries[i].metaobject != null) || (entries[i].classobject != null))
/*  37:    */       {
/*  38:    */         String metaobj;
/*  39:    */         String metaobj;
/*  40:102 */         if (entries[i].metaobject == null) {
/*  41:103 */           metaobj = "javassist.tools.reflect.Metaobject";
/*  42:    */         } else {
/*  43:105 */           metaobj = entries[i].metaobject;
/*  44:    */         }
/*  45:    */         String classobj;
/*  46:    */         String classobj;
/*  47:107 */         if (entries[i].classobject == null) {
/*  48:108 */           classobj = "javassist.tools.reflect.ClassMetaobject";
/*  49:    */         } else {
/*  50:110 */           classobj = entries[i].classobject;
/*  51:    */         }
/*  52:112 */         if (!implementor.makeReflective(c, pool.get(metaobj), pool.get(classobj))) {
/*  53:114 */           System.err.println("Warning: " + c.getName() + " is reflective.  It was not changed.");
/*  54:    */         }
/*  55:117 */         System.err.println(c.getName() + ": " + metaobj + ", " + classobj);
/*  56:    */       }
/*  57:    */       else
/*  58:    */       {
/*  59:121 */         System.err.println(c.getName() + ": not reflective");
/*  60:    */       }
/*  61:    */     }
/*  62:124 */     for (int i = 0; i < n; i++)
/*  63:    */     {
/*  64:125 */       implementor.onLoad(pool, entries[i].classname);
/*  65:126 */       pool.get(entries[i].classname).writeFile();
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   private static int parse(String[] args, CompiledClass[] result)
/*  70:    */   {
/*  71:131 */     int n = -1;
/*  72:132 */     for (int i = 0; i < args.length; i++)
/*  73:    */     {
/*  74:133 */       String a = args[i];
/*  75:134 */       if (a.equals("-m"))
/*  76:    */       {
/*  77:135 */         if ((n < 0) || (i + 1 > args.length)) {
/*  78:136 */           return -1;
/*  79:    */         }
/*  80:138 */         result[n].metaobject = args[(++i)];
/*  81:    */       }
/*  82:139 */       else if (a.equals("-c"))
/*  83:    */       {
/*  84:140 */         if ((n < 0) || (i + 1 > args.length)) {
/*  85:141 */           return -1;
/*  86:    */         }
/*  87:143 */         result[n].classobject = args[(++i)];
/*  88:    */       }
/*  89:    */       else
/*  90:    */       {
/*  91:144 */         if (a.charAt(0) == '-') {
/*  92:145 */           return -1;
/*  93:    */         }
/*  94:147 */         CompiledClass cc = new CompiledClass();
/*  95:148 */         cc.classname = a;
/*  96:149 */         cc.metaobject = null;
/*  97:150 */         cc.classobject = null;
/*  98:151 */         result[(++n)] = cc;
/*  99:    */       }
/* 100:    */     }
/* 101:155 */     return n + 1;
/* 102:    */   }
/* 103:    */   
/* 104:    */   private static void help(PrintStream out)
/* 105:    */   {
/* 106:159 */     out.println("Usage: java javassist.tools.reflect.Compiler");
/* 107:160 */     out.println("            (<class> [-m <metaobject>] [-c <class metaobject>])+");
/* 108:    */   }
/* 109:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.reflect.Compiler
 * JD-Core Version:    0.7.0.1
 */