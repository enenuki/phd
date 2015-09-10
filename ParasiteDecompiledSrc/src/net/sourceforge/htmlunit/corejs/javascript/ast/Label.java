/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class Label
/*   4:    */   extends Jump
/*   5:    */ {
/*   6:    */   private String name;
/*   7:    */   
/*   8:    */   public Label()
/*   9:    */   {
/*  10: 53 */     this.type = 130;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public Label(int pos)
/*  14:    */   {
/*  15: 60 */     this(pos, -1);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public Label(int pos, int len)
/*  19:    */   {
/*  20: 53 */     this.type = 130;
/*  21:    */     
/*  22:    */ 
/*  23:    */ 
/*  24:    */ 
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32: 65 */     this.position = pos;
/*  33: 66 */     this.length = len;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Label(int pos, int len, String name)
/*  37:    */   {
/*  38: 70 */     this(pos, len);
/*  39: 71 */     setName(name);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getName()
/*  43:    */   {
/*  44: 78 */     return this.name;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setName(String name)
/*  48:    */   {
/*  49: 87 */     name = name == null ? null : name.trim();
/*  50: 88 */     if ((name == null) || ("".equals(name))) {
/*  51: 89 */       throw new IllegalArgumentException("invalid label name");
/*  52:    */     }
/*  53: 90 */     this.name = name;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String toSource(int depth)
/*  57:    */   {
/*  58: 95 */     StringBuilder sb = new StringBuilder();
/*  59: 96 */     sb.append(makeIndent(depth));
/*  60: 97 */     sb.append(this.name);
/*  61: 98 */     sb.append(":\n");
/*  62: 99 */     return sb.toString();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void visit(NodeVisitor v)
/*  66:    */   {
/*  67:107 */     v.visit(this);
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.Label
 * JD-Core Version:    0.7.0.1
 */