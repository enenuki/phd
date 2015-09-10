/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*   4:    */ 
/*   5:    */ public class Jump
/*   6:    */   extends AstNode
/*   7:    */ {
/*   8:    */   public Node target;
/*   9:    */   private Node target2;
/*  10:    */   private Jump jumpNode;
/*  11:    */   
/*  12:    */   public Jump()
/*  13:    */   {
/*  14: 62 */     this.type = -1;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public Jump(int nodeType)
/*  18:    */   {
/*  19: 66 */     this.type = nodeType;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Jump(int type, int lineno)
/*  23:    */   {
/*  24: 70 */     this(type);
/*  25: 71 */     setLineno(lineno);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Jump(int type, Node child)
/*  29:    */   {
/*  30: 75 */     this(type);
/*  31: 76 */     addChildToBack(child);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Jump(int type, Node child, int lineno)
/*  35:    */   {
/*  36: 80 */     this(type, child);
/*  37: 81 */     setLineno(lineno);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Jump getJumpStatement()
/*  41:    */   {
/*  42: 86 */     if ((this.type != 120) && (this.type != 121)) {
/*  43: 86 */       codeBug();
/*  44:    */     }
/*  45: 87 */     return this.jumpNode;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setJumpStatement(Jump jumpStatement)
/*  49:    */   {
/*  50: 92 */     if ((this.type != 120) && (this.type != 121)) {
/*  51: 92 */       codeBug();
/*  52:    */     }
/*  53: 93 */     if (jumpStatement == null) {
/*  54: 93 */       codeBug();
/*  55:    */     }
/*  56: 94 */     if (this.jumpNode != null) {
/*  57: 94 */       codeBug();
/*  58:    */     }
/*  59: 95 */     this.jumpNode = jumpStatement;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Node getDefault()
/*  63:    */   {
/*  64:100 */     if (this.type != 114) {
/*  65:100 */       codeBug();
/*  66:    */     }
/*  67:101 */     return this.target2;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setDefault(Node defaultTarget)
/*  71:    */   {
/*  72:106 */     if (this.type != 114) {
/*  73:106 */       codeBug();
/*  74:    */     }
/*  75:107 */     if (defaultTarget.getType() != 131) {
/*  76:107 */       codeBug();
/*  77:    */     }
/*  78:108 */     if (this.target2 != null) {
/*  79:108 */       codeBug();
/*  80:    */     }
/*  81:109 */     this.target2 = defaultTarget;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Node getFinally()
/*  85:    */   {
/*  86:114 */     if (this.type != 81) {
/*  87:114 */       codeBug();
/*  88:    */     }
/*  89:115 */     return this.target2;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setFinally(Node finallyTarget)
/*  93:    */   {
/*  94:120 */     if (this.type != 81) {
/*  95:120 */       codeBug();
/*  96:    */     }
/*  97:121 */     if (finallyTarget.getType() != 131) {
/*  98:121 */       codeBug();
/*  99:    */     }
/* 100:122 */     if (this.target2 != null) {
/* 101:122 */       codeBug();
/* 102:    */     }
/* 103:123 */     this.target2 = finallyTarget;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Jump getLoop()
/* 107:    */   {
/* 108:128 */     if (this.type != 130) {
/* 109:128 */       codeBug();
/* 110:    */     }
/* 111:129 */     return this.jumpNode;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setLoop(Jump loop)
/* 115:    */   {
/* 116:134 */     if (this.type != 130) {
/* 117:134 */       codeBug();
/* 118:    */     }
/* 119:135 */     if (loop == null) {
/* 120:135 */       codeBug();
/* 121:    */     }
/* 122:136 */     if (this.jumpNode != null) {
/* 123:136 */       codeBug();
/* 124:    */     }
/* 125:137 */     this.jumpNode = loop;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Node getContinue()
/* 129:    */   {
/* 130:142 */     if (this.type != 132) {
/* 131:142 */       codeBug();
/* 132:    */     }
/* 133:143 */     return this.target2;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setContinue(Node continueTarget)
/* 137:    */   {
/* 138:148 */     if (this.type != 132) {
/* 139:148 */       codeBug();
/* 140:    */     }
/* 141:149 */     if (continueTarget.getType() != 131) {
/* 142:149 */       codeBug();
/* 143:    */     }
/* 144:150 */     if (this.target2 != null) {
/* 145:150 */       codeBug();
/* 146:    */     }
/* 147:151 */     this.target2 = continueTarget;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void visit(NodeVisitor visitor)
/* 151:    */   {
/* 152:161 */     throw new UnsupportedOperationException(toString());
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String toSource(int depth)
/* 156:    */   {
/* 157:166 */     throw new UnsupportedOperationException(toString());
/* 158:    */   }
/* 159:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.Jump
 * JD-Core Version:    0.7.0.1
 */