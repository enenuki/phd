/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class ForInLoop
/*   4:    */   extends Loop
/*   5:    */ {
/*   6:    */   protected AstNode iterator;
/*   7:    */   protected AstNode iteratedObject;
/*   8: 53 */   protected int inPosition = -1;
/*   9: 54 */   protected int eachPosition = -1;
/*  10:    */   protected boolean isForEach;
/*  11:    */   
/*  12:    */   public ForInLoop()
/*  13:    */   {
/*  14: 58 */     this.type = 119;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public ForInLoop(int pos)
/*  18:    */   {
/*  19: 65 */     super(pos);this.type = 119;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ForInLoop(int pos, int len)
/*  23:    */   {
/*  24: 69 */     super(pos, len);this.type = 119;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public AstNode getIterator()
/*  28:    */   {
/*  29: 76 */     return this.iterator;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setIterator(AstNode iterator)
/*  33:    */   {
/*  34: 85 */     assertNotNull(iterator);
/*  35: 86 */     this.iterator = iterator;
/*  36: 87 */     iterator.setParent(this);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public AstNode getIteratedObject()
/*  40:    */   {
/*  41: 94 */     return this.iteratedObject;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setIteratedObject(AstNode object)
/*  45:    */   {
/*  46:102 */     assertNotNull(object);
/*  47:103 */     this.iteratedObject = object;
/*  48:104 */     object.setParent(this);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean isForEach()
/*  52:    */   {
/*  53:111 */     return this.isForEach;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIsForEach(boolean isForEach)
/*  57:    */   {
/*  58:118 */     this.isForEach = isForEach;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getInPosition()
/*  62:    */   {
/*  63:125 */     return this.inPosition;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setInPosition(int inPosition)
/*  67:    */   {
/*  68:134 */     this.inPosition = inPosition;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getEachPosition()
/*  72:    */   {
/*  73:141 */     return this.eachPosition;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setEachPosition(int eachPosition)
/*  77:    */   {
/*  78:150 */     this.eachPosition = eachPosition;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String toSource(int depth)
/*  82:    */   {
/*  83:155 */     StringBuilder sb = new StringBuilder();
/*  84:156 */     sb.append(makeIndent(depth));
/*  85:157 */     sb.append("for ");
/*  86:158 */     if (isForEach()) {
/*  87:159 */       sb.append("each ");
/*  88:    */     }
/*  89:161 */     sb.append("(");
/*  90:162 */     sb.append(this.iterator.toSource(0));
/*  91:163 */     sb.append(" in ");
/*  92:164 */     sb.append(this.iteratedObject.toSource(0));
/*  93:165 */     sb.append(") ");
/*  94:166 */     if ((this.body instanceof Block)) {
/*  95:167 */       sb.append(this.body.toSource(depth).trim()).append("\n");
/*  96:    */     } else {
/*  97:169 */       sb.append("\n").append(this.body.toSource(depth + 1));
/*  98:    */     }
/*  99:171 */     return sb.toString();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void visit(NodeVisitor v)
/* 103:    */   {
/* 104:179 */     if (v.visit(this))
/* 105:    */     {
/* 106:180 */       this.iterator.visit(v);
/* 107:181 */       this.iteratedObject.visit(v);
/* 108:182 */       this.body.visit(v);
/* 109:    */     }
/* 110:    */   }
/* 111:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ForInLoop
 * JD-Core Version:    0.7.0.1
 */