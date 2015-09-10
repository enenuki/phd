/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ 
/*  5:   */ public class ArrayInit
/*  6:   */   extends ASTList
/*  7:   */ {
/*  8:   */   public ArrayInit(ASTree firstElement)
/*  9:   */   {
/* 10:25 */     super(firstElement);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public void accept(Visitor v)
/* 14:   */     throws CompileError
/* 15:   */   {
/* 16:28 */     v.atArrayInit(this);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getTag()
/* 20:   */   {
/* 21:30 */     return "array";
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.ArrayInit
 * JD-Core Version:    0.7.0.1
 */