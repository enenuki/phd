/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class ParserReporter
/*  6:   */   extends Tracer
/*  7:   */   implements ParserListener
/*  8:   */ {
/*  9:   */   public void parserConsume(ParserTokenEvent paramParserTokenEvent)
/* 10:   */   {
/* 11: 7 */     System.out.println(this.indent + paramParserTokenEvent);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void parserLA(ParserTokenEvent paramParserTokenEvent)
/* 15:   */   {
/* 16:10 */     System.out.println(this.indent + paramParserTokenEvent);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void parserMatch(ParserMatchEvent paramParserMatchEvent)
/* 20:   */   {
/* 21:13 */     System.out.println(this.indent + paramParserMatchEvent);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void parserMatchNot(ParserMatchEvent paramParserMatchEvent)
/* 25:   */   {
/* 26:16 */     System.out.println(this.indent + paramParserMatchEvent);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void parserMismatch(ParserMatchEvent paramParserMatchEvent)
/* 30:   */   {
/* 31:19 */     System.out.println(this.indent + paramParserMatchEvent);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void parserMismatchNot(ParserMatchEvent paramParserMatchEvent)
/* 35:   */   {
/* 36:22 */     System.out.println(this.indent + paramParserMatchEvent);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void reportError(MessageEvent paramMessageEvent)
/* 40:   */   {
/* 41:25 */     System.out.println(this.indent + paramMessageEvent);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void reportWarning(MessageEvent paramMessageEvent)
/* 45:   */   {
/* 46:28 */     System.out.println(this.indent + paramMessageEvent);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void semanticPredicateEvaluated(SemanticPredicateEvent paramSemanticPredicateEvent)
/* 50:   */   {
/* 51:31 */     System.out.println(this.indent + paramSemanticPredicateEvent);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void syntacticPredicateFailed(SyntacticPredicateEvent paramSyntacticPredicateEvent)
/* 55:   */   {
/* 56:34 */     System.out.println(this.indent + paramSyntacticPredicateEvent);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void syntacticPredicateStarted(SyntacticPredicateEvent paramSyntacticPredicateEvent)
/* 60:   */   {
/* 61:37 */     System.out.println(this.indent + paramSyntacticPredicateEvent);
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void syntacticPredicateSucceeded(SyntacticPredicateEvent paramSyntacticPredicateEvent)
/* 65:   */   {
/* 66:40 */     System.out.println(this.indent + paramSyntacticPredicateEvent);
/* 67:   */   }
/* 68:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.ParserReporter
 * JD-Core Version:    0.7.0.1
 */