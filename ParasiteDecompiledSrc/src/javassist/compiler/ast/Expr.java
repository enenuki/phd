/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ import javassist.compiler.TokenId;
/*  5:   */ 
/*  6:   */ public class Expr
/*  7:   */   extends ASTList
/*  8:   */   implements TokenId
/*  9:   */ {
/* 10:   */   protected int operatorId;
/* 11:   */   
/* 12:   */   Expr(int op, ASTree _head, ASTList _tail)
/* 13:   */   {
/* 14:34 */     super(_head, _tail);
/* 15:35 */     this.operatorId = op;
/* 16:   */   }
/* 17:   */   
/* 18:   */   Expr(int op, ASTree _head)
/* 19:   */   {
/* 20:39 */     super(_head);
/* 21:40 */     this.operatorId = op;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static Expr make(int op, ASTree oprand1, ASTree oprand2)
/* 25:   */   {
/* 26:44 */     return new Expr(op, oprand1, new ASTList(oprand2));
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static Expr make(int op, ASTree oprand1)
/* 30:   */   {
/* 31:48 */     return new Expr(op, oprand1);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int getOperator()
/* 35:   */   {
/* 36:51 */     return this.operatorId;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setOperator(int op)
/* 40:   */   {
/* 41:53 */     this.operatorId = op;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public ASTree oprand1()
/* 45:   */   {
/* 46:55 */     return getLeft();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void setOprand1(ASTree expr)
/* 50:   */   {
/* 51:58 */     setLeft(expr);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public ASTree oprand2()
/* 55:   */   {
/* 56:61 */     return getRight().getLeft();
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void setOprand2(ASTree expr)
/* 60:   */   {
/* 61:64 */     getRight().setLeft(expr);
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void accept(Visitor v)
/* 65:   */     throws CompileError
/* 66:   */   {
/* 67:67 */     v.atExpr(this);
/* 68:   */   }
/* 69:   */   
/* 70:   */   public String getName()
/* 71:   */   {
/* 72:70 */     int id = this.operatorId;
/* 73:71 */     if (id < 128) {
/* 74:72 */       return String.valueOf((char)id);
/* 75:   */     }
/* 76:73 */     if ((350 <= id) && (id <= 371)) {
/* 77:74 */       return opNames[(id - 350)];
/* 78:   */     }
/* 79:75 */     if (id == 323) {
/* 80:76 */       return "instanceof";
/* 81:   */     }
/* 82:78 */     return String.valueOf(id);
/* 83:   */   }
/* 84:   */   
/* 85:   */   protected String getTag()
/* 86:   */   {
/* 87:82 */     return "op:" + getName();
/* 88:   */   }
/* 89:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.Expr
 * JD-Core Version:    0.7.0.1
 */