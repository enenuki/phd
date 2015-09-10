/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ 
/*   6:    */ public class ArrayComprehension
/*   7:    */   extends Scope
/*   8:    */ {
/*   9:    */   private AstNode result;
/*  10: 53 */   private List<ArrayComprehensionLoop> loops = new ArrayList();
/*  11:    */   private AstNode filter;
/*  12: 56 */   private int ifPosition = -1;
/*  13: 57 */   private int lp = -1;
/*  14: 58 */   private int rp = -1;
/*  15:    */   
/*  16:    */   public ArrayComprehension()
/*  17:    */   {
/*  18: 61 */     this.type = 157;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public ArrayComprehension(int pos)
/*  22:    */   {
/*  23: 68 */     super(pos);this.type = 157;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public ArrayComprehension(int pos, int len)
/*  27:    */   {
/*  28: 72 */     super(pos, len);this.type = 157;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public AstNode getResult()
/*  32:    */   {
/*  33: 79 */     return this.result;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setResult(AstNode result)
/*  37:    */   {
/*  38: 87 */     assertNotNull(result);
/*  39: 88 */     this.result = result;
/*  40: 89 */     result.setParent(this);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public List<ArrayComprehensionLoop> getLoops()
/*  44:    */   {
/*  45: 96 */     return this.loops;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setLoops(List<ArrayComprehensionLoop> loops)
/*  49:    */   {
/*  50:104 */     assertNotNull(loops);
/*  51:105 */     this.loops.clear();
/*  52:106 */     for (ArrayComprehensionLoop acl : loops) {
/*  53:107 */       addLoop(acl);
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void addLoop(ArrayComprehensionLoop acl)
/*  58:    */   {
/*  59:116 */     assertNotNull(acl);
/*  60:117 */     this.loops.add(acl);
/*  61:118 */     acl.setParent(this);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public AstNode getFilter()
/*  65:    */   {
/*  66:125 */     return this.filter;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setFilter(AstNode filter)
/*  70:    */   {
/*  71:133 */     this.filter = filter;
/*  72:134 */     if (filter != null) {
/*  73:135 */       filter.setParent(this);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIfPosition()
/*  78:    */   {
/*  79:142 */     return this.ifPosition;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIfPosition(int ifPosition)
/*  83:    */   {
/*  84:149 */     this.ifPosition = ifPosition;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getFilterLp()
/*  88:    */   {
/*  89:156 */     return this.lp;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setFilterLp(int lp)
/*  93:    */   {
/*  94:163 */     this.lp = lp;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getFilterRp()
/*  98:    */   {
/*  99:170 */     return this.rp;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setFilterRp(int rp)
/* 103:    */   {
/* 104:177 */     this.rp = rp;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String toSource(int depth)
/* 108:    */   {
/* 109:182 */     StringBuilder sb = new StringBuilder(250);
/* 110:183 */     sb.append("[");
/* 111:184 */     sb.append(this.result.toSource(0));
/* 112:185 */     for (ArrayComprehensionLoop loop : this.loops) {
/* 113:186 */       sb.append(loop.toSource(0));
/* 114:    */     }
/* 115:188 */     if (this.filter != null)
/* 116:    */     {
/* 117:189 */       sb.append(" if (");
/* 118:190 */       sb.append(this.filter.toSource(0));
/* 119:191 */       sb.append(")");
/* 120:    */     }
/* 121:193 */     sb.append("]");
/* 122:194 */     return sb.toString();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void visit(NodeVisitor v)
/* 126:    */   {
/* 127:203 */     if (!v.visit(this)) {
/* 128:204 */       return;
/* 129:    */     }
/* 130:206 */     this.result.visit(v);
/* 131:207 */     for (ArrayComprehensionLoop loop : this.loops) {
/* 132:208 */       loop.visit(v);
/* 133:    */     }
/* 134:210 */     if (this.filter != null) {
/* 135:211 */       this.filter.visit(v);
/* 136:    */     }
/* 137:    */   }
/* 138:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ArrayComprehension
 * JD-Core Version:    0.7.0.1
 */