/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*  2:   */ 
/*  3:   */ public class EmptyExpression
/*  4:   */   extends AstNode
/*  5:   */ {
/*  6:   */   public EmptyExpression()
/*  7:   */   {
/*  8:51 */     this.type = 128;
/*  9:   */   }
/* 10:   */   
/* 11:   */   public EmptyExpression(int pos)
/* 12:   */   {
/* 13:58 */     super(pos);this.type = 128;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public EmptyExpression(int pos, int len)
/* 17:   */   {
/* 18:62 */     super(pos, len);this.type = 128;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String toSource(int depth)
/* 22:   */   {
/* 23:67 */     return makeIndent(depth);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void visit(NodeVisitor v)
/* 27:   */   {
/* 28:75 */     v.visit(this);
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.EmptyExpression
 * JD-Core Version:    0.7.0.1
 */