/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ 
/*   6:    */ public class LabeledStatement
/*   7:    */   extends AstNode
/*   8:    */ {
/*   9: 55 */   private List<Label> labels = new ArrayList();
/*  10:    */   private AstNode statement;
/*  11:    */   
/*  12:    */   public LabeledStatement()
/*  13:    */   {
/*  14: 59 */     this.type = 133;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public LabeledStatement(int pos)
/*  18:    */   {
/*  19: 66 */     super(pos);this.type = 133;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public LabeledStatement(int pos, int len)
/*  23:    */   {
/*  24: 70 */     super(pos, len);this.type = 133;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public List<Label> getLabels()
/*  28:    */   {
/*  29: 77 */     return this.labels;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setLabels(List<Label> labels)
/*  33:    */   {
/*  34: 86 */     assertNotNull(labels);
/*  35: 87 */     if (this.labels != null) {
/*  36: 88 */       this.labels.clear();
/*  37:    */     }
/*  38: 89 */     for (Label l : labels) {
/*  39: 90 */       addLabel(l);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void addLabel(Label label)
/*  44:    */   {
/*  45: 99 */     assertNotNull(label);
/*  46:100 */     this.labels.add(label);
/*  47:101 */     label.setParent(this);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public AstNode getStatement()
/*  51:    */   {
/*  52:108 */     return this.statement;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Label getLabelByName(String name)
/*  56:    */   {
/*  57:117 */     for (Label label : this.labels) {
/*  58:118 */       if (name.equals(label.getName())) {
/*  59:119 */         return label;
/*  60:    */       }
/*  61:    */     }
/*  62:122 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setStatement(AstNode statement)
/*  66:    */   {
/*  67:130 */     assertNotNull(statement);
/*  68:131 */     this.statement = statement;
/*  69:132 */     statement.setParent(this);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Label getFirstLabel()
/*  73:    */   {
/*  74:136 */     return (Label)this.labels.get(0);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toSource(int depth)
/*  78:    */   {
/*  79:141 */     StringBuilder sb = new StringBuilder();
/*  80:142 */     for (Label label : this.labels) {
/*  81:143 */       sb.append(label.toSource(depth));
/*  82:    */     }
/*  83:145 */     sb.append(this.statement.toSource(depth + 1));
/*  84:146 */     return sb.toString();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void visit(NodeVisitor v)
/*  88:    */   {
/*  89:155 */     if (v.visit(this))
/*  90:    */     {
/*  91:156 */       for (AstNode label : this.labels) {
/*  92:157 */         label.visit(v);
/*  93:    */       }
/*  94:159 */       this.statement.visit(v);
/*  95:    */     }
/*  96:    */   }
/*  97:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.LabeledStatement
 * JD-Core Version:    0.7.0.1
 */