/*  1:   */ package javassist.compiler;
/*  2:   */ 
/*  3:   */ public class SyntaxError
/*  4:   */   extends CompileError
/*  5:   */ {
/*  6:   */   public SyntaxError(Lex lexer)
/*  7:   */   {
/*  8:20 */     super("syntax error near \"" + lexer.getTextAround() + "\"", lexer);
/*  9:   */   }
/* 10:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.SyntaxError
 * JD-Core Version:    0.7.0.1
 */