/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.CtField;
/*  4:   */ import javassist.compiler.CompileError;
/*  5:   */ 
/*  6:   */ public class Member
/*  7:   */   extends Symbol
/*  8:   */ {
/*  9:   */   private CtField field;
/* 10:   */   
/* 11:   */   public Member(String name)
/* 12:   */   {
/* 13:30 */     super(name);
/* 14:31 */     this.field = null;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setField(CtField f)
/* 18:   */   {
/* 19:34 */     this.field = f;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public CtField getField()
/* 23:   */   {
/* 24:36 */     return this.field;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void accept(Visitor v)
/* 28:   */     throws CompileError
/* 29:   */   {
/* 30:38 */     v.atMember(this);
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.Member
 * JD-Core Version:    0.7.0.1
 */