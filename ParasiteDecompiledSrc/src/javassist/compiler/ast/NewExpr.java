/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ import javassist.compiler.TokenId;
/*  5:   */ 
/*  6:   */ public class NewExpr
/*  7:   */   extends ASTList
/*  8:   */   implements TokenId
/*  9:   */ {
/* 10:   */   protected boolean newArray;
/* 11:   */   protected int arrayType;
/* 12:   */   
/* 13:   */   public NewExpr(ASTList className, ASTList args)
/* 14:   */   {
/* 15:29 */     super(className, new ASTList(args));
/* 16:30 */     this.newArray = false;
/* 17:31 */     this.arrayType = 307;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public NewExpr(int type, ASTList arraySize, ArrayInit init)
/* 21:   */   {
/* 22:35 */     super(null, new ASTList(arraySize));
/* 23:36 */     this.newArray = true;
/* 24:37 */     this.arrayType = type;
/* 25:38 */     if (init != null) {
/* 26:39 */       append(this, init);
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static NewExpr makeObjectArray(ASTList className, ASTList arraySize, ArrayInit init)
/* 31:   */   {
/* 32:44 */     NewExpr e = new NewExpr(className, arraySize);
/* 33:45 */     e.newArray = true;
/* 34:46 */     if (init != null) {
/* 35:47 */       append(e, init);
/* 36:   */     }
/* 37:49 */     return e;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean isArray()
/* 41:   */   {
/* 42:52 */     return this.newArray;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public int getArrayType()
/* 46:   */   {
/* 47:56 */     return this.arrayType;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public ASTList getClassName()
/* 51:   */   {
/* 52:58 */     return (ASTList)getLeft();
/* 53:   */   }
/* 54:   */   
/* 55:   */   public ASTList getArguments()
/* 56:   */   {
/* 57:60 */     return (ASTList)getRight().getLeft();
/* 58:   */   }
/* 59:   */   
/* 60:   */   public ASTList getArraySize()
/* 61:   */   {
/* 62:62 */     return getArguments();
/* 63:   */   }
/* 64:   */   
/* 65:   */   public ArrayInit getInitializer()
/* 66:   */   {
/* 67:65 */     ASTree t = getRight().getRight();
/* 68:66 */     if (t == null) {
/* 69:67 */       return null;
/* 70:   */     }
/* 71:69 */     return (ArrayInit)t.getLeft();
/* 72:   */   }
/* 73:   */   
/* 74:   */   public void accept(Visitor v)
/* 75:   */     throws CompileError
/* 76:   */   {
/* 77:72 */     v.atNewExpr(this);
/* 78:   */   }
/* 79:   */   
/* 80:   */   protected String getTag()
/* 81:   */   {
/* 82:75 */     return this.newArray ? "new[]" : "new";
/* 83:   */   }
/* 84:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.NewExpr
 * JD-Core Version:    0.7.0.1
 */