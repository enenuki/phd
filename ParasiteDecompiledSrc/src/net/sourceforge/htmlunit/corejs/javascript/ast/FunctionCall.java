/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ public class FunctionCall
/*   8:    */   extends AstNode
/*   9:    */ {
/*  10: 52 */   protected static final List<AstNode> NO_ARGS = Collections.unmodifiableList(new ArrayList());
/*  11:    */   protected AstNode target;
/*  12:    */   protected List<AstNode> arguments;
/*  13: 57 */   protected int lp = -1;
/*  14: 58 */   protected int rp = -1;
/*  15:    */   
/*  16:    */   public FunctionCall()
/*  17:    */   {
/*  18: 61 */     this.type = 38;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public FunctionCall(int pos)
/*  22:    */   {
/*  23: 68 */     super(pos);this.type = 38;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public FunctionCall(int pos, int len)
/*  27:    */   {
/*  28: 72 */     super(pos, len);this.type = 38;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public AstNode getTarget()
/*  32:    */   {
/*  33: 79 */     return this.target;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setTarget(AstNode target)
/*  37:    */   {
/*  38: 89 */     assertNotNull(target);
/*  39: 90 */     this.target = target;
/*  40: 91 */     target.setParent(this);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<AstNode> getArguments()
/*  44:    */   {
/*  45:100 */     return this.arguments != null ? this.arguments : NO_ARGS;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setArguments(List<AstNode> arguments)
/*  49:    */   {
/*  50:109 */     if (arguments == null)
/*  51:    */     {
/*  52:110 */       this.arguments = null;
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56:112 */       if (this.arguments != null) {
/*  57:113 */         this.arguments.clear();
/*  58:    */       }
/*  59:114 */       for (AstNode arg : arguments) {
/*  60:115 */         addArgument(arg);
/*  61:    */       }
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void addArgument(AstNode arg)
/*  66:    */   {
/*  67:126 */     assertNotNull(arg);
/*  68:127 */     if (this.arguments == null) {
/*  69:128 */       this.arguments = new ArrayList();
/*  70:    */     }
/*  71:130 */     this.arguments.add(arg);
/*  72:131 */     arg.setParent(this);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getLp()
/*  76:    */   {
/*  77:138 */     return this.lp;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setLp(int lp)
/*  81:    */   {
/*  82:146 */     this.lp = lp;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getRp()
/*  86:    */   {
/*  87:153 */     return this.rp;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setRp(int rp)
/*  91:    */   {
/*  92:160 */     this.rp = rp;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setParens(int lp, int rp)
/*  96:    */   {
/*  97:167 */     this.lp = lp;
/*  98:168 */     this.rp = rp;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String toSource(int depth)
/* 102:    */   {
/* 103:173 */     StringBuilder sb = new StringBuilder();
/* 104:174 */     sb.append(makeIndent(depth));
/* 105:175 */     sb.append(this.target.toSource(0));
/* 106:176 */     sb.append("(");
/* 107:177 */     if (this.arguments != null) {
/* 108:178 */       printList(this.arguments, sb);
/* 109:    */     }
/* 110:180 */     sb.append(")");
/* 111:181 */     return sb.toString();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void visit(NodeVisitor v)
/* 115:    */   {
/* 116:189 */     if (v.visit(this))
/* 117:    */     {
/* 118:190 */       this.target.visit(v);
/* 119:191 */       for (AstNode arg : getArguments()) {
/* 120:192 */         arg.visit(v);
/* 121:    */       }
/* 122:    */     }
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.FunctionCall
 * JD-Core Version:    0.7.0.1
 */