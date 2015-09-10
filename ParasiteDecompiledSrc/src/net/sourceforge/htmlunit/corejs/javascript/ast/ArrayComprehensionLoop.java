/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*  2:   */ 
/*  3:   */ public class ArrayComprehensionLoop
/*  4:   */   extends ForInLoop
/*  5:   */ {
/*  6:   */   public ArrayComprehensionLoop() {}
/*  7:   */   
/*  8:   */   public ArrayComprehensionLoop(int pos)
/*  9:   */   {
/* 10:55 */     super(pos);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public ArrayComprehensionLoop(int pos, int len)
/* 14:   */   {
/* 15:59 */     super(pos, len);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public AstNode getBody()
/* 19:   */   {
/* 20:67 */     return null;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setBody(AstNode body)
/* 24:   */   {
/* 25:76 */     throw new UnsupportedOperationException("this node type has no body");
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String toSource(int depth)
/* 29:   */   {
/* 30:81 */     return makeIndent(depth) + " for (" + this.iterator.toSource(0) + " in " + this.iteratedObject.toSource(0) + ")";
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void visit(NodeVisitor v)
/* 34:   */   {
/* 35:95 */     if (v.visit(this))
/* 36:   */     {
/* 37:96 */       this.iterator.visit(v);
/* 38:97 */       this.iteratedObject.visit(v);
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ArrayComprehensionLoop
 * JD-Core Version:    0.7.0.1
 */