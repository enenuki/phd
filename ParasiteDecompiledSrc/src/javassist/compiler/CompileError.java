/*  1:   */ package javassist.compiler;
/*  2:   */ 
/*  3:   */ import javassist.CannotCompileException;
/*  4:   */ import javassist.NotFoundException;
/*  5:   */ 
/*  6:   */ public class CompileError
/*  7:   */   extends Exception
/*  8:   */ {
/*  9:   */   private Lex lex;
/* 10:   */   private String reason;
/* 11:   */   
/* 12:   */   public CompileError(String s, Lex l)
/* 13:   */   {
/* 14:26 */     this.reason = s;
/* 15:27 */     this.lex = l;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public CompileError(String s)
/* 19:   */   {
/* 20:31 */     this.reason = s;
/* 21:32 */     this.lex = null;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public CompileError(CannotCompileException e)
/* 25:   */   {
/* 26:36 */     this(e.getReason());
/* 27:   */   }
/* 28:   */   
/* 29:   */   public CompileError(NotFoundException e)
/* 30:   */   {
/* 31:40 */     this("cannot find " + e.getMessage());
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Lex getLex()
/* 35:   */   {
/* 36:43 */     return this.lex;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getMessage()
/* 40:   */   {
/* 41:46 */     return this.reason;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String toString()
/* 45:   */   {
/* 46:50 */     return "compile error: " + this.reason;
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.CompileError
 * JD-Core Version:    0.7.0.1
 */