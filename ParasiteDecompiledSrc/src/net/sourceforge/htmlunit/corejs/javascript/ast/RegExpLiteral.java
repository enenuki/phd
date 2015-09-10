/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class RegExpLiteral
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private String value;
/*   7:    */   private String flags;
/*   8:    */   
/*   9:    */   public RegExpLiteral()
/*  10:    */   {
/*  11: 53 */     this.type = 48;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public RegExpLiteral(int pos)
/*  15:    */   {
/*  16: 60 */     super(pos);this.type = 48;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public RegExpLiteral(int pos, int len)
/*  20:    */   {
/*  21: 64 */     super(pos, len);this.type = 48;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String getValue()
/*  25:    */   {
/*  26: 71 */     return this.value;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setValue(String value)
/*  30:    */   {
/*  31: 79 */     assertNotNull(value);
/*  32: 80 */     this.value = value;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getFlags()
/*  36:    */   {
/*  37: 87 */     return this.flags;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setFlags(String flags)
/*  41:    */   {
/*  42: 94 */     this.flags = flags;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String toSource(int depth)
/*  46:    */   {
/*  47: 99 */     return makeIndent(depth) + "/" + this.value + "/" + (this.flags == null ? "" : this.flags);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void visit(NodeVisitor v)
/*  51:    */   {
/*  52:108 */     v.visit(this);
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.RegExpLiteral
 * JD-Core Version:    0.7.0.1
 */