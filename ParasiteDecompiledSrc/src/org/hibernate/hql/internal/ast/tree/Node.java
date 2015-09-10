/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.CommonAST;
/*   4:    */ import antlr.Token;
/*   5:    */ import antlr.collections.AST;
/*   6:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   7:    */ import org.hibernate.internal.util.StringHelper;
/*   8:    */ 
/*   9:    */ public class Node
/*  10:    */   extends CommonAST
/*  11:    */ {
/*  12:    */   private String filename;
/*  13:    */   private int line;
/*  14:    */   private int column;
/*  15:    */   private int textLength;
/*  16:    */   
/*  17:    */   public Node() {}
/*  18:    */   
/*  19:    */   public Node(Token tok)
/*  20:    */   {
/*  21: 50 */     super(tok);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String getRenderText(SessionFactoryImplementor sessionFactory)
/*  25:    */   {
/*  26: 61 */     return getText();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void initialize(Token tok)
/*  30:    */   {
/*  31: 66 */     super.initialize(tok);
/*  32: 67 */     this.filename = tok.getFilename();
/*  33: 68 */     this.line = tok.getLine();
/*  34: 69 */     this.column = tok.getColumn();
/*  35: 70 */     String text = tok.getText();
/*  36: 71 */     this.textLength = (StringHelper.isEmpty(text) ? 0 : text.length());
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void initialize(AST t)
/*  40:    */   {
/*  41: 76 */     super.initialize(t);
/*  42: 77 */     if ((t instanceof Node))
/*  43:    */     {
/*  44: 78 */       Node n = (Node)t;
/*  45: 79 */       this.filename = n.filename;
/*  46: 80 */       this.line = n.line;
/*  47: 81 */       this.column = n.column;
/*  48: 82 */       this.textLength = n.textLength;
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getFilename()
/*  53:    */   {
/*  54: 87 */     return this.filename;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getLine()
/*  58:    */   {
/*  59: 92 */     return this.line;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getColumn()
/*  63:    */   {
/*  64: 97 */     return this.column;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getTextLength()
/*  68:    */   {
/*  69:101 */     return this.textLength;
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.Node
 * JD-Core Version:    0.7.0.1
 */