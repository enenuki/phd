/*  1:   */ package javassist.tools;
/*  2:   */ 
/*  3:   */ import java.io.DataInputStream;
/*  4:   */ import java.io.FileInputStream;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ import java.io.PrintWriter;
/*  7:   */ import javassist.bytecode.ClassFile;
/*  8:   */ import javassist.bytecode.ClassFilePrinter;
/*  9:   */ import javassist.bytecode.ConstPool;
/* 10:   */ 
/* 11:   */ public class Dump
/* 12:   */ {
/* 13:   */   public static void main(String[] args)
/* 14:   */     throws Exception
/* 15:   */   {
/* 16:42 */     if (args.length != 1)
/* 17:   */     {
/* 18:43 */       System.err.println("Usage: java Dump <class file name>");
/* 19:44 */       return;
/* 20:   */     }
/* 21:47 */     DataInputStream in = new DataInputStream(new FileInputStream(args[0]));
/* 22:   */     
/* 23:49 */     ClassFile w = new ClassFile(in);
/* 24:50 */     PrintWriter out = new PrintWriter(System.out, true);
/* 25:51 */     out.println("*** constant pool ***");
/* 26:52 */     w.getConstPool().print(out);
/* 27:53 */     out.println();
/* 28:54 */     out.println("*** members ***");
/* 29:55 */     ClassFilePrinter.print(w, out);
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.Dump
 * JD-Core Version:    0.7.0.1
 */