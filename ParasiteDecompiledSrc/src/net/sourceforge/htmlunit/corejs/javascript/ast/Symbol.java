/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Token;
/*   5:    */ 
/*   6:    */ public class Symbol
/*   7:    */ {
/*   8:    */   private int declType;
/*   9: 55 */   private int index = -1;
/*  10:    */   private String name;
/*  11:    */   private Node node;
/*  12:    */   private Scope containingTable;
/*  13:    */   
/*  14:    */   public Symbol() {}
/*  15:    */   
/*  16:    */   public Symbol(int declType, String name)
/*  17:    */   {
/*  18: 69 */     setName(name);
/*  19: 70 */     setDeclType(declType);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int getDeclType()
/*  23:    */   {
/*  24: 77 */     return this.declType;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setDeclType(int declType)
/*  28:    */   {
/*  29: 84 */     if ((declType != 109) && (declType != 87) && (declType != 122) && (declType != 153) && (declType != 154)) {
/*  30: 89 */       throw new IllegalArgumentException("Invalid declType: " + declType);
/*  31:    */     }
/*  32: 90 */     this.declType = declType;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getName()
/*  36:    */   {
/*  37: 97 */     return this.name;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setName(String name)
/*  41:    */   {
/*  42:104 */     this.name = name;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Node getNode()
/*  46:    */   {
/*  47:111 */     return this.node;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getIndex()
/*  51:    */   {
/*  52:118 */     return this.index;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setIndex(int index)
/*  56:    */   {
/*  57:125 */     this.index = index;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setNode(Node node)
/*  61:    */   {
/*  62:132 */     this.node = node;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Scope getContainingTable()
/*  66:    */   {
/*  67:139 */     return this.containingTable;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setContainingTable(Scope containingTable)
/*  71:    */   {
/*  72:146 */     this.containingTable = containingTable;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getDeclTypeName()
/*  76:    */   {
/*  77:150 */     return Token.typeToName(this.declType);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String toString()
/*  81:    */   {
/*  82:155 */     StringBuilder result = new StringBuilder();
/*  83:156 */     result.append("Symbol (");
/*  84:157 */     result.append(getDeclTypeName());
/*  85:158 */     result.append(") name=");
/*  86:159 */     result.append(this.name);
/*  87:160 */     if (this.node != null)
/*  88:    */     {
/*  89:161 */       result.append(" line=");
/*  90:162 */       result.append(this.node.getLineno());
/*  91:    */     }
/*  92:164 */     return result.toString();
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.Symbol
 * JD-Core Version:    0.7.0.1
 */