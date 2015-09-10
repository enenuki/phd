/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ public class TryStatement
/*   8:    */   extends AstNode
/*   9:    */ {
/*  10: 61 */   private static final List<CatchClause> NO_CATCHES = Collections.unmodifiableList(new ArrayList());
/*  11:    */   private AstNode tryBlock;
/*  12:    */   private List<CatchClause> catchClauses;
/*  13:    */   private AstNode finallyBlock;
/*  14: 67 */   private int finallyPosition = -1;
/*  15:    */   
/*  16:    */   public TryStatement()
/*  17:    */   {
/*  18: 70 */     this.type = 81;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public TryStatement(int pos)
/*  22:    */   {
/*  23: 77 */     super(pos);this.type = 81;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public TryStatement(int pos, int len)
/*  27:    */   {
/*  28: 81 */     super(pos, len);this.type = 81;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public AstNode getTryBlock()
/*  32:    */   {
/*  33: 85 */     return this.tryBlock;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setTryBlock(AstNode tryBlock)
/*  37:    */   {
/*  38: 93 */     assertNotNull(tryBlock);
/*  39: 94 */     this.tryBlock = tryBlock;
/*  40: 95 */     tryBlock.setParent(this);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<CatchClause> getCatchClauses()
/*  44:    */   {
/*  45:103 */     return this.catchClauses != null ? this.catchClauses : NO_CATCHES;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setCatchClauses(List<CatchClause> catchClauses)
/*  49:    */   {
/*  50:112 */     if (catchClauses == null)
/*  51:    */     {
/*  52:113 */       this.catchClauses = null;
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56:115 */       if (this.catchClauses != null) {
/*  57:116 */         this.catchClauses.clear();
/*  58:    */       }
/*  59:117 */       for (CatchClause cc : catchClauses) {
/*  60:118 */         addCatchClause(cc);
/*  61:    */       }
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void addCatchClause(CatchClause clause)
/*  66:    */   {
/*  67:129 */     assertNotNull(clause);
/*  68:130 */     if (this.catchClauses == null) {
/*  69:131 */       this.catchClauses = new ArrayList();
/*  70:    */     }
/*  71:133 */     this.catchClauses.add(clause);
/*  72:134 */     clause.setParent(this);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public AstNode getFinallyBlock()
/*  76:    */   {
/*  77:141 */     return this.finallyBlock;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setFinallyBlock(AstNode finallyBlock)
/*  81:    */   {
/*  82:149 */     this.finallyBlock = finallyBlock;
/*  83:150 */     if (finallyBlock != null) {
/*  84:151 */       finallyBlock.setParent(this);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getFinallyPosition()
/*  89:    */   {
/*  90:158 */     return this.finallyPosition;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setFinallyPosition(int finallyPosition)
/*  94:    */   {
/*  95:165 */     this.finallyPosition = finallyPosition;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String toSource(int depth)
/*  99:    */   {
/* 100:170 */     StringBuilder sb = new StringBuilder(250);
/* 101:171 */     sb.append(makeIndent(depth));
/* 102:172 */     sb.append("try ");
/* 103:173 */     sb.append(this.tryBlock.toSource(depth).trim());
/* 104:174 */     for (CatchClause cc : getCatchClauses()) {
/* 105:175 */       sb.append(cc.toSource(depth));
/* 106:    */     }
/* 107:177 */     if (this.finallyBlock != null)
/* 108:    */     {
/* 109:178 */       sb.append(" finally ");
/* 110:179 */       sb.append(this.finallyBlock.toSource(depth));
/* 111:    */     }
/* 112:181 */     return sb.toString();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void visit(NodeVisitor v)
/* 116:    */   {
/* 117:190 */     if (v.visit(this))
/* 118:    */     {
/* 119:191 */       this.tryBlock.visit(v);
/* 120:192 */       for (CatchClause cc : getCatchClauses()) {
/* 121:193 */         cc.visit(v);
/* 122:    */       }
/* 123:195 */       if (this.finallyBlock != null) {
/* 124:196 */         this.finallyBlock.visit(v);
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.TryStatement
 * JD-Core Version:    0.7.0.1
 */