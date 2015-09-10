/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import javassist.compiler.CompileError;
/*  5:   */ 
/*  6:   */ public abstract class ASTree
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   public ASTree getLeft()
/* 10:   */   {
/* 11:27 */     return null;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public ASTree getRight()
/* 15:   */   {
/* 16:29 */     return null;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setLeft(ASTree _left) {}
/* 20:   */   
/* 21:   */   public void setRight(ASTree _right) {}
/* 22:   */   
/* 23:   */   public abstract void accept(Visitor paramVisitor)
/* 24:   */     throws CompileError;
/* 25:   */   
/* 26:   */   public String toString()
/* 27:   */   {
/* 28:43 */     StringBuffer sbuf = new StringBuffer();
/* 29:44 */     sbuf.append('<');
/* 30:45 */     sbuf.append(getTag());
/* 31:46 */     sbuf.append('>');
/* 32:47 */     return sbuf.toString();
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected String getTag()
/* 36:   */   {
/* 37:55 */     String name = getClass().getName();
/* 38:56 */     return name.substring(name.lastIndexOf('.') + 1);
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.ASTree
 * JD-Core Version:    0.7.0.1
 */