/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.Token;
/*   7:    */ 
/*   8:    */ public class VariableDeclaration
/*   9:    */   extends AstNode
/*  10:    */ {
/*  11: 61 */   private List<VariableInitializer> variables = new ArrayList();
/*  12:    */   
/*  13:    */   public VariableDeclaration()
/*  14:    */   {
/*  15: 65 */     this.type = 122;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public VariableDeclaration(int pos)
/*  19:    */   {
/*  20: 72 */     super(pos);this.type = 122;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public VariableDeclaration(int pos, int len)
/*  24:    */   {
/*  25: 76 */     super(pos, len);this.type = 122;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public List<VariableInitializer> getVariables()
/*  29:    */   {
/*  30: 83 */     return this.variables;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setVariables(List<VariableInitializer> variables)
/*  34:    */   {
/*  35: 91 */     assertNotNull(variables);
/*  36: 92 */     this.variables.clear();
/*  37: 93 */     for (VariableInitializer vi : variables) {
/*  38: 94 */       addVariable(vi);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void addVariable(VariableInitializer v)
/*  43:    */   {
/*  44:104 */     assertNotNull(v);
/*  45:105 */     this.variables.add(v);
/*  46:106 */     v.setParent(this);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Node setType(int type)
/*  50:    */   {
/*  51:115 */     if ((type != 122) && (type != 154) && (type != 153)) {
/*  52:118 */       throw new IllegalArgumentException("invalid decl type: " + type);
/*  53:    */     }
/*  54:119 */     return super.setType(type);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean isVar()
/*  58:    */   {
/*  59:128 */     return this.type == 122;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean isConst()
/*  63:    */   {
/*  64:135 */     return this.type == 154;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean isLet()
/*  68:    */   {
/*  69:142 */     return this.type == 153;
/*  70:    */   }
/*  71:    */   
/*  72:    */   private String declTypeName()
/*  73:    */   {
/*  74:146 */     return Token.typeToName(this.type).toLowerCase();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toSource(int depth)
/*  78:    */   {
/*  79:151 */     StringBuilder sb = new StringBuilder();
/*  80:152 */     sb.append(makeIndent(depth));
/*  81:153 */     sb.append(declTypeName());
/*  82:154 */     sb.append(" ");
/*  83:155 */     printList(this.variables, sb);
/*  84:156 */     if (!(getParent() instanceof Loop)) {
/*  85:157 */       sb.append(";\n");
/*  86:    */     }
/*  87:159 */     return sb.toString();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void visit(NodeVisitor v)
/*  91:    */   {
/*  92:167 */     if (v.visit(this)) {
/*  93:168 */       for (AstNode var : this.variables) {
/*  94:169 */         var.visit(v);
/*  95:    */       }
/*  96:    */     }
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.VariableDeclaration
 * JD-Core Version:    0.7.0.1
 */