/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ 
/*  5:   */ public class Pair
/*  6:   */   extends ASTree
/*  7:   */ {
/*  8:   */   protected ASTree left;
/*  9:   */   protected ASTree right;
/* 10:   */   
/* 11:   */   public Pair(ASTree _left, ASTree _right)
/* 12:   */   {
/* 13:28 */     this.left = _left;
/* 14:29 */     this.right = _right;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void accept(Visitor v)
/* 18:   */     throws CompileError
/* 19:   */   {
/* 20:32 */     v.atPair(this);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String toString()
/* 24:   */   {
/* 25:35 */     StringBuffer sbuf = new StringBuffer();
/* 26:36 */     sbuf.append("(<Pair> ");
/* 27:37 */     sbuf.append(this.left == null ? "<null>" : this.left.toString());
/* 28:38 */     sbuf.append(" . ");
/* 29:39 */     sbuf.append(this.right == null ? "<null>" : this.right.toString());
/* 30:40 */     sbuf.append(')');
/* 31:41 */     return sbuf.toString();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public ASTree getLeft()
/* 35:   */   {
/* 36:44 */     return this.left;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public ASTree getRight()
/* 40:   */   {
/* 41:46 */     return this.right;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setLeft(ASTree _left)
/* 45:   */   {
/* 46:48 */     this.left = _left;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void setRight(ASTree _right)
/* 50:   */   {
/* 51:50 */     this.right = _right;
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.Pair
 * JD-Core Version:    0.7.0.1
 */