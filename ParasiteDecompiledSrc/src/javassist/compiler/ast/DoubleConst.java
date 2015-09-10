/*  1:   */ package javassist.compiler.ast;
/*  2:   */ 
/*  3:   */ import javassist.compiler.CompileError;
/*  4:   */ 
/*  5:   */ public class DoubleConst
/*  6:   */   extends ASTree
/*  7:   */ {
/*  8:   */   protected double value;
/*  9:   */   protected int type;
/* 10:   */   
/* 11:   */   public DoubleConst(double v, int tokenId)
/* 12:   */   {
/* 13:28 */     this.value = v;this.type = tokenId;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public double get()
/* 17:   */   {
/* 18:30 */     return this.value;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void set(double v)
/* 22:   */   {
/* 23:32 */     this.value = v;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getType()
/* 27:   */   {
/* 28:36 */     return this.type;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String toString()
/* 32:   */   {
/* 33:38 */     return Double.toString(this.value);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void accept(Visitor v)
/* 37:   */     throws CompileError
/* 38:   */   {
/* 39:41 */     v.atDoubleConst(this);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public ASTree compute(int op, ASTree right)
/* 43:   */   {
/* 44:45 */     if ((right instanceof IntConst)) {
/* 45:46 */       return compute0(op, (IntConst)right);
/* 46:   */     }
/* 47:47 */     if ((right instanceof DoubleConst)) {
/* 48:48 */       return compute0(op, (DoubleConst)right);
/* 49:   */     }
/* 50:50 */     return null;
/* 51:   */   }
/* 52:   */   
/* 53:   */   private DoubleConst compute0(int op, DoubleConst right)
/* 54:   */   {
/* 55:   */     int newType;
/* 56:   */     int newType;
/* 57:55 */     if ((this.type == 405) || (right.type == 405)) {
/* 58:57 */       newType = 405;
/* 59:   */     } else {
/* 60:59 */       newType = 404;
/* 61:   */     }
/* 62:61 */     return compute(op, this.value, right.value, newType);
/* 63:   */   }
/* 64:   */   
/* 65:   */   private DoubleConst compute0(int op, IntConst right)
/* 66:   */   {
/* 67:65 */     return compute(op, this.value, right.value, this.type);
/* 68:   */   }
/* 69:   */   
/* 70:   */   private static DoubleConst compute(int op, double value1, double value2, int newType)
/* 71:   */   {
/* 72:   */     double newValue;
/* 73:72 */     switch (op)
/* 74:   */     {
/* 75:   */     case 43: 
/* 76:74 */       newValue = value1 + value2;
/* 77:75 */       break;
/* 78:   */     case 45: 
/* 79:77 */       newValue = value1 - value2;
/* 80:78 */       break;
/* 81:   */     case 42: 
/* 82:80 */       newValue = value1 * value2;
/* 83:81 */       break;
/* 84:   */     case 47: 
/* 85:83 */       newValue = value1 / value2;
/* 86:84 */       break;
/* 87:   */     case 37: 
/* 88:86 */       newValue = value1 % value2;
/* 89:87 */       break;
/* 90:   */     case 38: 
/* 91:   */     case 39: 
/* 92:   */     case 40: 
/* 93:   */     case 41: 
/* 94:   */     case 44: 
/* 95:   */     case 46: 
/* 96:   */     default: 
/* 97:89 */       return null;
/* 98:   */     }
/* 99:92 */     return new DoubleConst(newValue, newType);
/* :0:   */   }
/* :1:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.DoubleConst
 * JD-Core Version:    0.7.0.1
 */