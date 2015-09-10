/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*  2:   */ 
/*  3:   */ public class Assignment
/*  4:   */   extends InfixExpression
/*  5:   */ {
/*  6:   */   public Assignment() {}
/*  7:   */   
/*  8:   */   public Assignment(int pos)
/*  9:   */   {
/* 10:51 */     super(pos);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public Assignment(int pos, int len)
/* 14:   */   {
/* 15:55 */     super(pos, len);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Assignment(int pos, int len, AstNode left, AstNode right)
/* 19:   */   {
/* 20:59 */     super(pos, len, left, right);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Assignment(AstNode left, AstNode right)
/* 24:   */   {
/* 25:63 */     super(left, right);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Assignment(int operator, AstNode left, AstNode right, int operatorPos)
/* 29:   */   {
/* 30:68 */     super(operator, left, right, operatorPos);
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.Assignment
 * JD-Core Version:    0.7.0.1
 */