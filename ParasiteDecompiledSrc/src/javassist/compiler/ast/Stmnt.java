/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ import javassist.compiler.TokenId;
/*  5:   */ 
/*  6:   */ public class Stmnt
/*  7:   */   extends ASTList
/*  8:   */   implements TokenId
/*  9:   */ {
/* 10:   */   protected int operatorId;
/* 11:   */   
/* 12:   */   public Stmnt(int op, ASTree _head, ASTList _tail)
/* 13:   */   {
/* 14:28 */     super(_head, _tail);
/* 15:29 */     this.operatorId = op;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Stmnt(int op, ASTree _head)
/* 19:   */   {
/* 20:33 */     super(_head);
/* 21:34 */     this.operatorId = op;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Stmnt(int op)
/* 25:   */   {
/* 26:38 */     this(op, null);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static Stmnt make(int op, ASTree oprand1, ASTree oprand2)
/* 30:   */   {
/* 31:42 */     return new Stmnt(op, oprand1, new ASTList(oprand2));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static Stmnt make(int op, ASTree op1, ASTree op2, ASTree op3)
/* 35:   */   {
/* 36:46 */     return new Stmnt(op, op1, new ASTList(op2, new ASTList(op3)));
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void accept(Visitor v)
/* 40:   */     throws CompileError
/* 41:   */   {
/* 42:49 */     v.atStmnt(this);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public int getOperator()
/* 46:   */   {
/* 47:51 */     return this.operatorId;
/* 48:   */   }
/* 49:   */   
/* 50:   */   protected String getTag()
/* 51:   */   {
/* 52:54 */     if (this.operatorId < 128) {
/* 53:55 */       return "stmnt:" + (char)this.operatorId;
/* 54:   */     }
/* 55:57 */     return "stmnt:" + this.operatorId;
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.Stmnt
 * JD-Core Version:    0.7.0.1
 */