/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class Name
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private String identifier;
/*   7:    */   private Scope scope;
/*   8:    */   
/*   9:    */   public Name()
/*  10:    */   {
/*  11: 58 */     this.type = 39;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public Name(int pos)
/*  15:    */   {
/*  16: 65 */     super(pos);this.type = 39;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Name(int pos, int len)
/*  20:    */   {
/*  21: 69 */     super(pos, len);this.type = 39;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Name(int pos, int len, String name)
/*  25:    */   {
/*  26: 79 */     super(pos, len);this.type = 39;
/*  27: 80 */     setIdentifier(name);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Name(int pos, String name)
/*  31:    */   {
/*  32: 84 */     super(pos);this.type = 39;
/*  33: 85 */     setIdentifier(name);
/*  34: 86 */     setLength(name.length());
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getIdentifier()
/*  38:    */   {
/*  39: 93 */     return this.identifier;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setIdentifier(String identifier)
/*  43:    */   {
/*  44:101 */     assertNotNull(identifier);
/*  45:102 */     this.identifier = identifier;
/*  46:103 */     setLength(identifier.length());
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setScope(Scope s)
/*  50:    */   {
/*  51:117 */     this.scope = s;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Scope getScope()
/*  55:    */   {
/*  56:128 */     return this.scope;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Scope getDefiningScope()
/*  60:    */   {
/*  61:137 */     Scope enclosing = getEnclosingScope();
/*  62:138 */     String name = getIdentifier();
/*  63:139 */     return enclosing == null ? null : enclosing.getDefiningScope(name);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean isLocalName()
/*  67:    */   {
/*  68:156 */     Scope scope = getDefiningScope();
/*  69:157 */     return (scope != null) && (scope.getParentScope() != null);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int length()
/*  73:    */   {
/*  74:167 */     return this.identifier == null ? 0 : this.identifier.length();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toSource(int depth)
/*  78:    */   {
/*  79:172 */     return makeIndent(depth) + (this.identifier == null ? "<null>" : this.identifier);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void visit(NodeVisitor v)
/*  83:    */   {
/*  84:180 */     v.visit(this);
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.Name
 * JD-Core Version:    0.7.0.1
 */