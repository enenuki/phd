/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class ElementGet
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private AstNode target;
/*   7:    */   private AstNode element;
/*   8: 58 */   private int lb = -1;
/*   9: 59 */   private int rb = -1;
/*  10:    */   
/*  11:    */   public ElementGet()
/*  12:    */   {
/*  13: 62 */     this.type = 36;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public ElementGet(int pos)
/*  17:    */   {
/*  18: 69 */     super(pos);this.type = 36;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public ElementGet(int pos, int len)
/*  22:    */   {
/*  23: 73 */     super(pos, len);this.type = 36;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public ElementGet(AstNode target, AstNode element)
/*  27:    */   {
/*  28: 62 */     this.type = 36;
/*  29:    */     
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43: 77 */     setTarget(target);
/*  44: 78 */     setElement(element);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public AstNode getTarget()
/*  48:    */   {
/*  49: 85 */     return this.target;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setTarget(AstNode target)
/*  53:    */   {
/*  54: 95 */     assertNotNull(target);
/*  55: 96 */     this.target = target;
/*  56: 97 */     target.setParent(this);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public AstNode getElement()
/*  60:    */   {
/*  61:104 */     return this.element;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setElement(AstNode element)
/*  65:    */   {
/*  66:112 */     assertNotNull(element);
/*  67:113 */     this.element = element;
/*  68:114 */     element.setParent(this);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getLb()
/*  72:    */   {
/*  73:121 */     return this.lb;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setLb(int lb)
/*  77:    */   {
/*  78:128 */     this.lb = lb;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getRb()
/*  82:    */   {
/*  83:135 */     return this.rb;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setRb(int rb)
/*  87:    */   {
/*  88:142 */     this.rb = rb;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setParens(int lb, int rb)
/*  92:    */   {
/*  93:146 */     this.lb = lb;
/*  94:147 */     this.rb = rb;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String toSource(int depth)
/*  98:    */   {
/*  99:152 */     StringBuilder sb = new StringBuilder();
/* 100:153 */     sb.append(makeIndent(depth));
/* 101:154 */     sb.append(this.target.toSource(0));
/* 102:155 */     sb.append("[");
/* 103:156 */     sb.append(this.element.toSource(0));
/* 104:157 */     sb.append("]");
/* 105:158 */     return sb.toString();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void visit(NodeVisitor v)
/* 109:    */   {
/* 110:166 */     if (v.visit(this))
/* 111:    */     {
/* 112:167 */       this.target.visit(v);
/* 113:168 */       this.element.visit(v);
/* 114:    */     }
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ElementGet
 * JD-Core Version:    0.7.0.1
 */