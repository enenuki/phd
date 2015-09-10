/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ import java.io.PrintWriter;
/*  4:   */ 
/*  5:   */ public class CSharpNameSpace
/*  6:   */   extends NameSpace
/*  7:   */ {
/*  8:   */   public CSharpNameSpace(String paramString)
/*  9:   */   {
/* 10:39 */     super(paramString);
/* 11:   */   }
/* 12:   */   
/* 13:   */   void emitDeclarations(PrintWriter paramPrintWriter)
/* 14:   */   {
/* 15:46 */     paramPrintWriter.println("namespace " + getName());
/* 16:47 */     paramPrintWriter.println("{");
/* 17:   */   }
/* 18:   */   
/* 19:   */   void emitClosures(PrintWriter paramPrintWriter)
/* 20:   */   {
/* 21:54 */     paramPrintWriter.println("}");
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CSharpNameSpace
 * JD-Core Version:    0.7.0.1
 */