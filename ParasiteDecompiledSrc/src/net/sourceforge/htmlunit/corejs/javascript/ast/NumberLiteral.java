/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class NumberLiteral
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private String value;
/*   7:    */   private double number;
/*   8:    */   
/*   9:    */   public NumberLiteral()
/*  10:    */   {
/*  11: 52 */     this.type = 40;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public NumberLiteral(int pos)
/*  15:    */   {
/*  16: 59 */     super(pos);this.type = 40;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public NumberLiteral(int pos, int len)
/*  20:    */   {
/*  21: 63 */     super(pos, len);this.type = 40;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public NumberLiteral(int pos, String value)
/*  25:    */   {
/*  26: 70 */     super(pos);this.type = 40;
/*  27: 71 */     setValue(value);
/*  28: 72 */     setLength(value.length());
/*  29:    */   }
/*  30:    */   
/*  31:    */   public NumberLiteral(int pos, String value, double number)
/*  32:    */   {
/*  33: 79 */     this(pos, value);
/*  34: 80 */     setDouble(number);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public NumberLiteral(double number)
/*  38:    */   {
/*  39: 52 */     this.type = 40;
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
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71: 84 */     setDouble(number);
/*  72: 85 */     setValue(Double.toString(number));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getValue()
/*  76:    */   {
/*  77: 92 */     return this.value;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setValue(String value)
/*  81:    */   {
/*  82:100 */     assertNotNull(value);
/*  83:101 */     this.value = value;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public double getNumber()
/*  87:    */   {
/*  88:108 */     return this.number;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setNumber(double value)
/*  92:    */   {
/*  93:115 */     this.number = value;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String toSource(int depth)
/*  97:    */   {
/*  98:120 */     return makeIndent(depth) + (this.value == null ? "<null>" : this.value);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void visit(NodeVisitor v)
/* 102:    */   {
/* 103:128 */     v.visit(this);
/* 104:    */   }
/* 105:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.NumberLiteral
 * JD-Core Version:    0.7.0.1
 */