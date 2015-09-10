/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class InputBufferReporter
/*  6:   */   implements InputBufferListener
/*  7:   */ {
/*  8:   */   public void doneParsing(TraceEvent paramTraceEvent) {}
/*  9:   */   
/* 10:   */   public void inputBufferChanged(InputBufferEvent paramInputBufferEvent)
/* 11:   */   {
/* 12:12 */     System.out.println(paramInputBufferEvent);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void inputBufferConsume(InputBufferEvent paramInputBufferEvent)
/* 16:   */   {
/* 17:18 */     System.out.println(paramInputBufferEvent);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void inputBufferLA(InputBufferEvent paramInputBufferEvent)
/* 21:   */   {
/* 22:24 */     System.out.println(paramInputBufferEvent);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void inputBufferMark(InputBufferEvent paramInputBufferEvent)
/* 26:   */   {
/* 27:27 */     System.out.println(paramInputBufferEvent);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void inputBufferRewind(InputBufferEvent paramInputBufferEvent)
/* 31:   */   {
/* 32:30 */     System.out.println(paramInputBufferEvent);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void refresh() {}
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.InputBufferReporter
 * JD-Core Version:    0.7.0.1
 */