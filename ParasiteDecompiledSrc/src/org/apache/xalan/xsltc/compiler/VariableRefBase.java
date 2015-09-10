/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*   4:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*   5:    */ 
/*   6:    */ class VariableRefBase
/*   7:    */   extends Expression
/*   8:    */ {
/*   9:    */   protected VariableBase _variable;
/*  10: 42 */   protected Closure _closure = null;
/*  11:    */   
/*  12:    */   public VariableRefBase(VariableBase variable)
/*  13:    */   {
/*  14: 45 */     this._variable = variable;
/*  15: 46 */     variable.addReference(this);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public VariableRefBase()
/*  19:    */   {
/*  20: 50 */     this._variable = null;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public VariableBase getVariable()
/*  24:    */   {
/*  25: 57 */     return this._variable;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void addParentDependency()
/*  29:    */   {
/*  30: 73 */     SyntaxTreeNode node = this;
/*  31: 74 */     while ((node != null) && (!(node instanceof TopLevelElement))) {
/*  32: 75 */       node = node.getParent();
/*  33:    */     }
/*  34: 78 */     TopLevelElement parent = (TopLevelElement)node;
/*  35: 79 */     if (parent != null)
/*  36:    */     {
/*  37: 80 */       VariableBase var = this._variable;
/*  38: 81 */       if (this._variable._ignore) {
/*  39: 82 */         if ((this._variable instanceof Variable)) {
/*  40: 83 */           var = parent.getSymbolTable().lookupVariable(this._variable._name);
/*  41: 85 */         } else if ((this._variable instanceof Param)) {
/*  42: 86 */           var = parent.getSymbolTable().lookupParam(this._variable._name);
/*  43:    */         }
/*  44:    */       }
/*  45: 90 */       parent.addDependency(var);
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean equals(Object obj)
/*  50:    */   {
/*  51:    */     try
/*  52:    */     {
/*  53:100 */       return this._variable == ((VariableRefBase)obj)._variable;
/*  54:    */     }
/*  55:    */     catch (ClassCastException e) {}
/*  56:103 */     return false;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String toString()
/*  60:    */   {
/*  61:113 */     return "variable-ref(" + this._variable.getName() + '/' + this._variable.getType() + ')';
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Type typeCheck(SymbolTable stable)
/*  65:    */     throws TypeCheckError
/*  66:    */   {
/*  67:120 */     if (this._type != null) {
/*  68:120 */       return this._type;
/*  69:    */     }
/*  70:123 */     if (this._variable.isLocal())
/*  71:    */     {
/*  72:124 */       SyntaxTreeNode node = getParent();
/*  73:    */       do
/*  74:    */       {
/*  75:126 */         if ((node instanceof Closure))
/*  76:    */         {
/*  77:127 */           this._closure = ((Closure)node);
/*  78:128 */           break;
/*  79:    */         }
/*  80:130 */         if ((node instanceof TopLevelElement)) {
/*  81:    */           break;
/*  82:    */         }
/*  83:133 */         node = node.getParent();
/*  84:134 */       } while (node != null);
/*  85:136 */       if (this._closure != null) {
/*  86:137 */         this._closure.addVariable(this);
/*  87:    */       }
/*  88:    */     }
/*  89:142 */     this._type = this._variable.getType();
/*  90:146 */     if (this._type == null)
/*  91:    */     {
/*  92:147 */       this._variable.typeCheck(stable);
/*  93:148 */       this._type = this._variable.getType();
/*  94:    */     }
/*  95:152 */     addParentDependency();
/*  96:    */     
/*  97:    */ 
/*  98:155 */     return this._type;
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.VariableRefBase
 * JD-Core Version:    0.7.0.1
 */