/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Token.CommentType;
/*   4:    */ 
/*   5:    */ public class Comment
/*   6:    */   extends AstNode
/*   7:    */ {
/*   8:    */   private String value;
/*   9:    */   private Token.CommentType commentType;
/*  10:    */   
/*  11:    */   public Comment(int pos, int len, Token.CommentType type, String value)
/*  12:    */   {
/*  13: 90 */     super(pos, len);this.type = 161;
/*  14: 91 */     this.commentType = type;
/*  15: 92 */     this.value = value;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public Token.CommentType getCommentType()
/*  19:    */   {
/*  20: 99 */     return this.commentType;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void setCommentType(Token.CommentType type)
/*  24:    */   {
/*  25:108 */     this.commentType = type;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getValue()
/*  29:    */   {
/*  30:115 */     return this.value;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String toSource(int depth)
/*  34:    */   {
/*  35:120 */     StringBuilder sb = new StringBuilder(getLength() + 10);
/*  36:121 */     sb.append(makeIndent(depth));
/*  37:122 */     sb.append(this.value);
/*  38:123 */     return sb.toString();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void visit(NodeVisitor v)
/*  42:    */   {
/*  43:132 */     v.visit(this);
/*  44:    */   }
/*  45:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.Comment
 * JD-Core Version:    0.7.0.1
 */