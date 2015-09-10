/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.AST;
/*   4:    */ import antlr.collections.ASTEnumeration;
/*   5:    */ 
/*   6:    */ public class ASTNULLType
/*   7:    */   implements AST
/*   8:    */ {
/*   9:    */   public void addChild(AST paramAST) {}
/*  10:    */   
/*  11:    */   public boolean equals(AST paramAST)
/*  12:    */   {
/*  13: 20 */     return false;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public boolean equalsList(AST paramAST)
/*  17:    */   {
/*  18: 24 */     return false;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public boolean equalsListPartial(AST paramAST)
/*  22:    */   {
/*  23: 28 */     return false;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean equalsTree(AST paramAST)
/*  27:    */   {
/*  28: 32 */     return false;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean equalsTreePartial(AST paramAST)
/*  32:    */   {
/*  33: 36 */     return false;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public ASTEnumeration findAll(AST paramAST)
/*  37:    */   {
/*  38: 40 */     return null;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public ASTEnumeration findAllPartial(AST paramAST)
/*  42:    */   {
/*  43: 44 */     return null;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public AST getFirstChild()
/*  47:    */   {
/*  48: 48 */     return this;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public AST getNextSibling()
/*  52:    */   {
/*  53: 52 */     return this;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getText()
/*  57:    */   {
/*  58: 56 */     return "<ASTNULL>";
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getType()
/*  62:    */   {
/*  63: 60 */     return 3;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getLine()
/*  67:    */   {
/*  68: 64 */     return 0;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getColumn()
/*  72:    */   {
/*  73: 68 */     return 0;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getNumberOfChildren()
/*  77:    */   {
/*  78: 72 */     return 0;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void initialize(int paramInt, String paramString) {}
/*  82:    */   
/*  83:    */   public void initialize(AST paramAST) {}
/*  84:    */   
/*  85:    */   public void initialize(Token paramToken) {}
/*  86:    */   
/*  87:    */   public void setFirstChild(AST paramAST) {}
/*  88:    */   
/*  89:    */   public void setNextSibling(AST paramAST) {}
/*  90:    */   
/*  91:    */   public void setText(String paramString) {}
/*  92:    */   
/*  93:    */   public void setType(int paramInt) {}
/*  94:    */   
/*  95:    */   public String toString()
/*  96:    */   {
/*  97: 97 */     return getText();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String toStringList()
/* 101:    */   {
/* 102:101 */     return getText();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String toStringTree()
/* 106:    */   {
/* 107:105 */     return getText();
/* 108:    */   }
/* 109:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ASTNULLType
 * JD-Core Version:    0.7.0.1
 */