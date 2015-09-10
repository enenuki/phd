/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class ContinueStatement
/*   4:    */   extends Jump
/*   5:    */ {
/*   6:    */   private Name label;
/*   7:    */   private Loop target;
/*   8:    */   
/*   9:    */   public ContinueStatement()
/*  10:    */   {
/*  11: 56 */     this.type = 121;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public ContinueStatement(int pos)
/*  15:    */   {
/*  16: 63 */     this(pos, -1);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public ContinueStatement(int pos, int len)
/*  20:    */   {
/*  21: 56 */     this.type = 121;
/*  22:    */     
/*  23:    */ 
/*  24:    */ 
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33: 68 */     this.position = pos;
/*  34: 69 */     this.length = len;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public ContinueStatement(Name label)
/*  38:    */   {
/*  39: 56 */     this.type = 121;
/*  40:    */     
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56: 73 */     setLabel(label);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public ContinueStatement(int pos, Name label)
/*  60:    */   {
/*  61: 77 */     this(pos);
/*  62: 78 */     setLabel(label);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public ContinueStatement(int pos, int len, Name label)
/*  66:    */   {
/*  67: 82 */     this(pos, len);
/*  68: 83 */     setLabel(label);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Loop getTarget()
/*  72:    */   {
/*  73: 90 */     return this.target;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setTarget(Loop target)
/*  77:    */   {
/*  78:100 */     assertNotNull(target);
/*  79:101 */     this.target = target;
/*  80:102 */     setJumpStatement(target);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Name getLabel()
/*  84:    */   {
/*  85:111 */     return this.label;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setLabel(Name label)
/*  89:    */   {
/*  90:120 */     this.label = label;
/*  91:121 */     if (label != null) {
/*  92:122 */       label.setParent(this);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String toSource(int depth)
/*  97:    */   {
/*  98:127 */     StringBuilder sb = new StringBuilder();
/*  99:128 */     sb.append(makeIndent(depth));
/* 100:129 */     sb.append("continue");
/* 101:130 */     if (this.label != null)
/* 102:    */     {
/* 103:131 */       sb.append(" ");
/* 104:132 */       sb.append(this.label.toSource(0));
/* 105:    */     }
/* 106:134 */     sb.append(";\n");
/* 107:135 */     return sb.toString();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void visit(NodeVisitor v)
/* 111:    */   {
/* 112:143 */     if ((v.visit(this)) && (this.label != null)) {
/* 113:144 */       this.label.visit(v);
/* 114:    */     }
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ContinueStatement
 * JD-Core Version:    0.7.0.1
 */