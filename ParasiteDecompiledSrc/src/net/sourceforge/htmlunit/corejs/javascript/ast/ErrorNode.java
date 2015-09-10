/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*  2:   */ 
/*  3:   */ public class ErrorNode
/*  4:   */   extends AstNode
/*  5:   */ {
/*  6:   */   private String message;
/*  7:   */   
/*  8:   */   public ErrorNode()
/*  9:   */   {
/* 10:52 */     this.type = -1;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public ErrorNode(int pos)
/* 14:   */   {
/* 15:59 */     super(pos);this.type = -1;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public ErrorNode(int pos, int len)
/* 19:   */   {
/* 20:63 */     super(pos, len);this.type = -1;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getMessage()
/* 24:   */   {
/* 25:70 */     return this.message;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setMessage(String message)
/* 29:   */   {
/* 30:77 */     this.message = message;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String toSource(int depth)
/* 34:   */   {
/* 35:82 */     return "";
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void visit(NodeVisitor v)
/* 39:   */   {
/* 40:91 */     v.visit(this);
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ErrorNode
 * JD-Core Version:    0.7.0.1
 */