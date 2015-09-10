/*   1:    */ package javassist.compiler.ast;
/*   2:    */ 
/*   3:    */ import javassist.compiler.CompileError;
/*   4:    */ import javassist.compiler.TokenId;
/*   5:    */ 
/*   6:    */ public class Declarator
/*   7:    */   extends ASTList
/*   8:    */   implements TokenId
/*   9:    */ {
/*  10:    */   protected int varType;
/*  11:    */   protected int arrayDim;
/*  12:    */   protected int localVar;
/*  13:    */   protected String qualifiedClass;
/*  14:    */   
/*  15:    */   public Declarator(int type, int dim)
/*  16:    */   {
/*  17: 31 */     super(null);
/*  18: 32 */     this.varType = type;
/*  19: 33 */     this.arrayDim = dim;
/*  20: 34 */     this.localVar = -1;
/*  21: 35 */     this.qualifiedClass = null;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Declarator(ASTList className, int dim)
/*  25:    */   {
/*  26: 39 */     super(null);
/*  27: 40 */     this.varType = 307;
/*  28: 41 */     this.arrayDim = dim;
/*  29: 42 */     this.localVar = -1;
/*  30: 43 */     this.qualifiedClass = astToClassName(className, '/');
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Declarator(int type, String jvmClassName, int dim, int var, Symbol sym)
/*  34:    */   {
/*  35: 50 */     super(null);
/*  36: 51 */     this.varType = type;
/*  37: 52 */     this.arrayDim = dim;
/*  38: 53 */     this.localVar = var;
/*  39: 54 */     this.qualifiedClass = jvmClassName;
/*  40: 55 */     setLeft(sym);
/*  41: 56 */     append(this, null);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Declarator make(Symbol sym, int dim, ASTree init)
/*  45:    */   {
/*  46: 60 */     Declarator d = new Declarator(this.varType, this.arrayDim + dim);
/*  47: 61 */     d.qualifiedClass = this.qualifiedClass;
/*  48: 62 */     d.setLeft(sym);
/*  49: 63 */     append(d, init);
/*  50: 64 */     return d;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getType()
/*  54:    */   {
/*  55: 70 */     return this.varType;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getArrayDim()
/*  59:    */   {
/*  60: 72 */     return this.arrayDim;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void addArrayDim(int d)
/*  64:    */   {
/*  65: 74 */     this.arrayDim += d;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getClassName()
/*  69:    */   {
/*  70: 76 */     return this.qualifiedClass;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setClassName(String s)
/*  74:    */   {
/*  75: 78 */     this.qualifiedClass = s;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Symbol getVariable()
/*  79:    */   {
/*  80: 80 */     return (Symbol)getLeft();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setVariable(Symbol sym)
/*  84:    */   {
/*  85: 82 */     setLeft(sym);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public ASTree getInitializer()
/*  89:    */   {
/*  90: 85 */     ASTList t = tail();
/*  91: 86 */     if (t != null) {
/*  92: 87 */       return t.head();
/*  93:    */     }
/*  94: 89 */     return null;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setLocalVar(int n)
/*  98:    */   {
/*  99: 92 */     this.localVar = n;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getLocalVar()
/* 103:    */   {
/* 104: 94 */     return this.localVar;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getTag()
/* 108:    */   {
/* 109: 96 */     return "decl";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void accept(Visitor v)
/* 113:    */     throws CompileError
/* 114:    */   {
/* 115: 99 */     v.atDeclarator(this);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static String astToClassName(ASTList name, char sep)
/* 119:    */   {
/* 120:103 */     if (name == null) {
/* 121:104 */       return null;
/* 122:    */     }
/* 123:106 */     StringBuffer sbuf = new StringBuffer();
/* 124:107 */     astToClassName(sbuf, name, sep);
/* 125:108 */     return sbuf.toString();
/* 126:    */   }
/* 127:    */   
/* 128:    */   private static void astToClassName(StringBuffer sbuf, ASTList name, char sep)
/* 129:    */   {
/* 130:    */     for (;;)
/* 131:    */     {
/* 132:114 */       ASTree h = name.head();
/* 133:115 */       if ((h instanceof Symbol)) {
/* 134:116 */         sbuf.append(((Symbol)h).get());
/* 135:117 */       } else if ((h instanceof ASTList)) {
/* 136:118 */         astToClassName(sbuf, (ASTList)h, sep);
/* 137:    */       }
/* 138:120 */       name = name.tail();
/* 139:121 */       if (name == null) {
/* 140:    */         break;
/* 141:    */       }
/* 142:124 */       sbuf.append(sep);
/* 143:    */     }
/* 144:    */   }
/* 145:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.ast.Declarator
 * JD-Core Version:    0.7.0.1
 */