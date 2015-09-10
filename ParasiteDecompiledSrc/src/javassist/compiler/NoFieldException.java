/*  1:   */ package javassist.compiler;
/*  2:   */ 
/*  3:   */ import javassist.compiler.ast.ASTree;
/*  4:   */ 
/*  5:   */ public class NoFieldException
/*  6:   */   extends CompileError
/*  7:   */ {
/*  8:   */   private String fieldName;
/*  9:   */   private ASTree expr;
/* 10:   */   
/* 11:   */   public NoFieldException(String name, ASTree e)
/* 12:   */   {
/* 13:27 */     super("no such field: " + name);
/* 14:28 */     this.fieldName = name;
/* 15:29 */     this.expr = e;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getField()
/* 19:   */   {
/* 20:34 */     return this.fieldName;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public ASTree getExpr()
/* 24:   */   {
/* 25:38 */     return this.expr;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.NoFieldException
 * JD-Core Version:    0.7.0.1
 */