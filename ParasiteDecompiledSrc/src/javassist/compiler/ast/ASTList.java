/*   1:    */ package javassist.compiler.ast;
/*   2:    */ 
/*   3:    */ import javassist.compiler.CompileError;
/*   4:    */ 
/*   5:    */ public class ASTList
/*   6:    */   extends ASTree
/*   7:    */ {
/*   8:    */   private ASTree left;
/*   9:    */   private ASTList right;
/*  10:    */   
/*  11:    */   public ASTList(ASTree _head, ASTList _tail)
/*  12:    */   {
/*  13: 29 */     this.left = _head;
/*  14: 30 */     this.right = _tail;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public ASTList(ASTree _head)
/*  18:    */   {
/*  19: 34 */     this.left = _head;
/*  20: 35 */     this.right = null;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static ASTList make(ASTree e1, ASTree e2, ASTree e3)
/*  24:    */   {
/*  25: 39 */     return new ASTList(e1, new ASTList(e2, new ASTList(e3)));
/*  26:    */   }
/*  27:    */   
/*  28:    */   public ASTree getLeft()
/*  29:    */   {
/*  30: 42 */     return this.left;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ASTree getRight()
/*  34:    */   {
/*  35: 44 */     return this.right;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setLeft(ASTree _left)
/*  39:    */   {
/*  40: 46 */     this.left = _left;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setRight(ASTree _right)
/*  44:    */   {
/*  45: 49 */     this.right = ((ASTList)_right);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ASTree head()
/*  49:    */   {
/*  50: 55 */     return this.left;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setHead(ASTree _head)
/*  54:    */   {
/*  55: 58 */     this.left = _head;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public ASTList tail()
/*  59:    */   {
/*  60: 64 */     return this.right;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setTail(ASTList _tail)
/*  64:    */   {
/*  65: 67 */     this.right = _tail;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void accept(Visitor v)
/*  69:    */     throws CompileError
/*  70:    */   {
/*  71: 70 */     v.atASTList(this);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String toString()
/*  75:    */   {
/*  76: 73 */     StringBuffer sbuf = new StringBuffer();
/*  77: 74 */     sbuf.append("(<");
/*  78: 75 */     sbuf.append(getTag());
/*  79: 76 */     sbuf.append('>');
/*  80: 77 */     ASTList list = this;
/*  81: 78 */     while (list != null)
/*  82:    */     {
/*  83: 79 */       sbuf.append(' ');
/*  84: 80 */       ASTree a = list.left;
/*  85: 81 */       sbuf.append(a == null ? "<null>" : a.toString());
/*  86: 82 */       list = list.right;
/*  87:    */     }
/*  88: 85 */     sbuf.append(')');
/*  89: 86 */     return sbuf.toString();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int length()
/*  93:    */   {
/*  94: 93 */     return length(this);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static int length(ASTList list)
/*  98:    */   {
/*  99: 97 */     if (list == null) {
/* 100: 98 */       return 0;
/* 101:    */     }
/* 102:100 */     int n = 0;
/* 103:101 */     while (list != null)
/* 104:    */     {
/* 105:102 */       list = list.right;
/* 106:103 */       n++;
/* 107:    */     }
/* 108:106 */     return n;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public ASTList sublist(int nth)
/* 112:    */   {
/* 113:116 */     ASTList list = this;
/* 114:117 */     while (nth-- > 0) {
/* 115:118 */       list = list.right;
/* 116:    */     }
/* 117:120 */     return list;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean subst(ASTree newObj, ASTree oldObj)
/* 121:    */   {
/* 122:128 */     for (ASTList list = this; list != null; list = list.right) {
/* 123:129 */       if (list.left == oldObj)
/* 124:    */       {
/* 125:130 */         list.left = newObj;
/* 126:131 */         return true;
/* 127:    */       }
/* 128:    */     }
/* 129:134 */     return false;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public static ASTList append(ASTList a, ASTree b)
/* 133:    */   {
/* 134:141 */     return concat(a, new ASTList(b));
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static ASTList concat(ASTList a, ASTList b)
/* 138:    */   {
/* 139:148 */     if (a == null) {
/* 140:149 */       return b;
/* 141:    */     }
/* 142:151 */     ASTList list = a;
/* 143:152 */     while (list.right != null) {
/* 144:153 */       list = list.right;
/* 145:    */     }
/* 146:155 */     list.right = b;
/* 147:156 */     return a;
/* 148:    */   }
/* 149:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.ASTList
 * JD-Core Version:    0.7.0.1
 */