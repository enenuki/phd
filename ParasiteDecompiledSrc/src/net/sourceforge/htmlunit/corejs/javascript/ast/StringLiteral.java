/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*   4:    */ 
/*   5:    */ public class StringLiteral
/*   6:    */   extends AstNode
/*   7:    */ {
/*   8:    */   private String value;
/*   9:    */   private char quoteChar;
/*  10:    */   
/*  11:    */   public StringLiteral()
/*  12:    */   {
/*  13: 54 */     this.type = 41;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public StringLiteral(int pos)
/*  17:    */   {
/*  18: 61 */     super(pos);this.type = 41;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public StringLiteral(int pos, int len)
/*  22:    */   {
/*  23: 69 */     super(pos, len);this.type = 41;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getValue()
/*  27:    */   {
/*  28: 78 */     return this.value;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getValue(boolean includeQuotes)
/*  32:    */   {
/*  33: 85 */     if (!includeQuotes) {
/*  34: 86 */       return this.value;
/*  35:    */     }
/*  36: 87 */     return this.quoteChar + this.value + this.quoteChar;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setValue(String value)
/*  40:    */   {
/*  41: 96 */     assertNotNull(value);
/*  42: 97 */     this.value = value;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public char getQuoteCharacter()
/*  46:    */   {
/*  47:104 */     return this.quoteChar;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setQuoteCharacter(char c)
/*  51:    */   {
/*  52:108 */     this.quoteChar = c;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String toSource(int depth)
/*  56:    */   {
/*  57:113 */     return makeIndent(depth) + this.quoteChar + ScriptRuntime.escapeString(this.value, this.quoteChar) + this.quoteChar;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void visit(NodeVisitor v)
/*  61:    */   {
/*  62:125 */     v.visit(this);
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.StringLiteral
 * JD-Core Version:    0.7.0.1
 */