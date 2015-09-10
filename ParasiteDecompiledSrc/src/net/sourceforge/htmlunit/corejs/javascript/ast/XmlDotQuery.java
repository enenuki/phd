/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class XmlDotQuery
/*   4:    */   extends InfixExpression
/*   5:    */ {
/*   6: 57 */   private int rp = -1;
/*   7:    */   
/*   8:    */   public XmlDotQuery()
/*   9:    */   {
/*  10: 60 */     this.type = 146;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public XmlDotQuery(int pos)
/*  14:    */   {
/*  15: 67 */     super(pos);this.type = 146;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public XmlDotQuery(int pos, int len)
/*  19:    */   {
/*  20: 71 */     super(pos, len);this.type = 146;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public int getRp()
/*  24:    */   {
/*  25: 82 */     return this.rp;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setRp(int rp)
/*  29:    */   {
/*  30: 89 */     this.rp = rp;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String toSource(int depth)
/*  34:    */   {
/*  35: 94 */     StringBuilder sb = new StringBuilder();
/*  36: 95 */     sb.append(makeIndent(depth));
/*  37: 96 */     sb.append(getLeft().toSource(0));
/*  38: 97 */     sb.append(".(");
/*  39: 98 */     sb.append(getRight().toSource(0));
/*  40: 99 */     sb.append(")");
/*  41:100 */     return sb.toString();
/*  42:    */   }
/*  43:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.XmlDotQuery
 * JD-Core Version:    0.7.0.1
 */