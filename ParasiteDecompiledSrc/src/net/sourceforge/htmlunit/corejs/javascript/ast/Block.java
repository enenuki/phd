/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*  2:   */ 
/*  3:   */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*  4:   */ 
/*  5:   */ public class Block
/*  6:   */   extends AstNode
/*  7:   */ {
/*  8:   */   public Block()
/*  9:   */   {
/* 10:56 */     this.type = 129;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public Block(int pos)
/* 14:   */   {
/* 15:63 */     super(pos);this.type = 129;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Block(int pos, int len)
/* 19:   */   {
/* 20:67 */     super(pos, len);this.type = 129;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void addStatement(AstNode statement)
/* 24:   */   {
/* 25:74 */     addChild(statement);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String toSource(int depth)
/* 29:   */   {
/* 30:79 */     StringBuilder sb = new StringBuilder();
/* 31:80 */     sb.append(makeIndent(depth));
/* 32:81 */     sb.append("{\n");
/* 33:82 */     for (Node kid : this) {
/* 34:83 */       sb.append(((AstNode)kid).toSource(depth + 1));
/* 35:   */     }
/* 36:85 */     sb.append(makeIndent(depth));
/* 37:86 */     sb.append("}\n");
/* 38:87 */     return sb.toString();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void visit(NodeVisitor v)
/* 42:   */   {
/* 43:92 */     if (v.visit(this)) {
/* 44:93 */       for (Node kid : this) {
/* 45:94 */         ((AstNode)kid).visit(v);
/* 46:   */       }
/* 47:   */     }
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.Block
 * JD-Core Version:    0.7.0.1
 */