/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class Tracer
/*  6:   */   extends TraceAdapter
/*  7:   */   implements TraceListener
/*  8:   */ {
/*  9: 4 */   String indent = "";
/* 10:   */   
/* 11:   */   protected void dedent()
/* 12:   */   {
/* 13: 8 */     if (this.indent.length() < 2) {
/* 14: 9 */       this.indent = "";
/* 15:   */     } else {
/* 16:11 */       this.indent = this.indent.substring(2);
/* 17:   */     }
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void enterRule(TraceEvent paramTraceEvent)
/* 21:   */   {
/* 22:14 */     System.out.println(this.indent + paramTraceEvent);
/* 23:15 */     indent();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void exitRule(TraceEvent paramTraceEvent)
/* 27:   */   {
/* 28:18 */     dedent();
/* 29:19 */     System.out.println(this.indent + paramTraceEvent);
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected void indent()
/* 33:   */   {
/* 34:22 */     this.indent += "  ";
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.Tracer
 * JD-Core Version:    0.7.0.1
 */