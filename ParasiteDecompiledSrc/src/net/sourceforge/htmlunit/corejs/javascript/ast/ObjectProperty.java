/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class ObjectProperty
/*   4:    */   extends InfixExpression
/*   5:    */ {
/*   6:    */   public ObjectProperty()
/*   7:    */   {
/*   8: 68 */     this.type = 103;
/*   9:    */   }
/*  10:    */   
/*  11:    */   public void setNodeType(int nodeType)
/*  12:    */   {
/*  13: 77 */     if ((nodeType != 103) && (nodeType != 151) && (nodeType != 152)) {
/*  14: 80 */       throw new IllegalArgumentException("invalid node type: " + nodeType);
/*  15:    */     }
/*  16: 82 */     setType(nodeType);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public ObjectProperty(int pos)
/*  20:    */   {
/*  21: 89 */     super(pos);this.type = 103;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ObjectProperty(int pos, int len)
/*  25:    */   {
/*  26: 93 */     super(pos, len);this.type = 103;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setIsGetter()
/*  30:    */   {
/*  31:100 */     this.type = 151;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean isGetter()
/*  35:    */   {
/*  36:107 */     return this.type == 151;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setIsSetter()
/*  40:    */   {
/*  41:114 */     this.type = 152;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isSetter()
/*  45:    */   {
/*  46:121 */     return this.type == 152;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String toSource(int depth)
/*  50:    */   {
/*  51:126 */     StringBuilder sb = new StringBuilder();
/*  52:127 */     sb.append(makeIndent(depth));
/*  53:128 */     if (isGetter()) {
/*  54:129 */       sb.append("get ");
/*  55:130 */     } else if (isSetter()) {
/*  56:131 */       sb.append("set ");
/*  57:    */     }
/*  58:133 */     sb.append(this.left.toSource(0));
/*  59:134 */     if (this.type == 103) {
/*  60:135 */       sb.append(": ");
/*  61:    */     }
/*  62:137 */     sb.append(this.right.toSource(0));
/*  63:138 */     return sb.toString();
/*  64:    */   }
/*  65:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ObjectProperty
 * JD-Core Version:    0.7.0.1
 */