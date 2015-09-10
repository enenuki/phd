/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class KeywordLiteral
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   public KeywordLiteral() {}
/*   7:    */   
/*   8:    */   public KeywordLiteral(int pos)
/*   9:    */   {
/*  10: 59 */     super(pos);
/*  11:    */   }
/*  12:    */   
/*  13:    */   public KeywordLiteral(int pos, int len)
/*  14:    */   {
/*  15: 63 */     super(pos, len);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public KeywordLiteral(int pos, int len, int nodeType)
/*  19:    */   {
/*  20: 71 */     super(pos, len);
/*  21: 72 */     setType(nodeType);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public KeywordLiteral setType(int nodeType)
/*  25:    */   {
/*  26: 81 */     if ((nodeType != 43) && (nodeType != 42) && (nodeType != 45) && (nodeType != 44) && (nodeType != 160)) {
/*  27: 86 */       throw new IllegalArgumentException("Invalid node type: " + nodeType);
/*  28:    */     }
/*  29: 88 */     this.type = nodeType;
/*  30: 89 */     return this;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean isBooleanLiteral()
/*  34:    */   {
/*  35: 97 */     return (this.type == 45) || (this.type == 44);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String toSource(int depth)
/*  39:    */   {
/*  40:102 */     StringBuilder sb = new StringBuilder();
/*  41:103 */     sb.append(makeIndent(depth));
/*  42:104 */     switch (getType())
/*  43:    */     {
/*  44:    */     case 43: 
/*  45:106 */       sb.append("this");
/*  46:107 */       break;
/*  47:    */     case 42: 
/*  48:109 */       sb.append("null");
/*  49:110 */       break;
/*  50:    */     case 45: 
/*  51:112 */       sb.append("true");
/*  52:113 */       break;
/*  53:    */     case 44: 
/*  54:115 */       sb.append("false");
/*  55:116 */       break;
/*  56:    */     case 160: 
/*  57:118 */       sb.append("debugger");
/*  58:119 */       break;
/*  59:    */     default: 
/*  60:121 */       throw new IllegalStateException("Invalid keyword literal type: " + getType());
/*  61:    */     }
/*  62:124 */     return sb.toString();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void visit(NodeVisitor v)
/*  66:    */   {
/*  67:132 */     v.visit(this);
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.KeywordLiteral
 * JD-Core Version:    0.7.0.1
 */