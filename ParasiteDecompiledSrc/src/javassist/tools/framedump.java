/*  1:   */ package javassist.tools;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import javassist.ClassPool;
/*  5:   */ import javassist.CtClass;
/*  6:   */ import javassist.bytecode.analysis.FramePrinter;
/*  7:   */ 
/*  8:   */ public class framedump
/*  9:   */ {
/* 10:   */   public static void main(String[] args)
/* 11:   */     throws Exception
/* 12:   */   {
/* 13:37 */     if (args.length != 1)
/* 14:   */     {
/* 15:38 */       System.err.println("Usage: java javassist.tools.framedump <class file name>");
/* 16:39 */       return;
/* 17:   */     }
/* 18:42 */     ClassPool pool = ClassPool.getDefault();
/* 19:43 */     CtClass clazz = pool.get(args[0]);
/* 20:44 */     System.out.println("Frame Dump of " + clazz.getName() + ":");
/* 21:45 */     FramePrinter.print(clazz, System.out);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.framedump
 * JD-Core Version:    0.7.0.1
 */