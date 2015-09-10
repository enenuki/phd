/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ public class ArrayLiteral
/*   8:    */   extends AstNode
/*   9:    */   implements DestructuringForm
/*  10:    */ {
/*  11: 67 */   private static final List<AstNode> NO_ELEMS = Collections.unmodifiableList(new ArrayList());
/*  12:    */   private List<AstNode> elements;
/*  13:    */   private int destructuringLength;
/*  14:    */   private int skipCount;
/*  15:    */   private boolean isDestructuring;
/*  16:    */   
/*  17:    */   public ArrayLiteral()
/*  18:    */   {
/*  19: 76 */     this.type = 65;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ArrayLiteral(int pos)
/*  23:    */   {
/*  24: 83 */     super(pos);this.type = 65;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ArrayLiteral(int pos, int len)
/*  28:    */   {
/*  29: 87 */     super(pos, len);this.type = 65;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public List<AstNode> getElements()
/*  33:    */   {
/*  34: 97 */     return this.elements != null ? this.elements : NO_ELEMS;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setElements(List<AstNode> elements)
/*  38:    */   {
/*  39:105 */     if (elements == null)
/*  40:    */     {
/*  41:106 */       this.elements = null;
/*  42:    */     }
/*  43:    */     else
/*  44:    */     {
/*  45:108 */       if (this.elements != null) {
/*  46:109 */         this.elements.clear();
/*  47:    */       }
/*  48:110 */       for (AstNode e : elements) {
/*  49:111 */         addElement(e);
/*  50:    */       }
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void addElement(AstNode element)
/*  55:    */   {
/*  56:122 */     assertNotNull(element);
/*  57:123 */     if (this.elements == null) {
/*  58:124 */       this.elements = new ArrayList();
/*  59:    */     }
/*  60:125 */     this.elements.add(element);
/*  61:126 */     element.setParent(this);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getSize()
/*  65:    */   {
/*  66:134 */     return this.elements == null ? 0 : this.elements.size();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public AstNode getElement(int index)
/*  70:    */   {
/*  71:144 */     if (this.elements == null) {
/*  72:145 */       throw new IndexOutOfBoundsException("no elements");
/*  73:    */     }
/*  74:146 */     return (AstNode)this.elements.get(index);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getDestructuringLength()
/*  78:    */   {
/*  79:153 */     return this.destructuringLength;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setDestructuringLength(int destructuringLength)
/*  83:    */   {
/*  84:164 */     this.destructuringLength = destructuringLength;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getSkipCount()
/*  88:    */   {
/*  89:172 */     return this.skipCount;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setSkipCount(int count)
/*  93:    */   {
/*  94:180 */     this.skipCount = count;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIsDestructuring(boolean destructuring)
/*  98:    */   {
/*  99:189 */     this.isDestructuring = destructuring;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean isDestructuring()
/* 103:    */   {
/* 104:198 */     return this.isDestructuring;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String toSource(int depth)
/* 108:    */   {
/* 109:203 */     StringBuilder sb = new StringBuilder();
/* 110:204 */     sb.append(makeIndent(depth));
/* 111:205 */     sb.append("[");
/* 112:206 */     if (this.elements != null) {
/* 113:207 */       printList(this.elements, sb);
/* 114:    */     }
/* 115:209 */     sb.append("]");
/* 116:210 */     return sb.toString();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void visit(NodeVisitor v)
/* 120:    */   {
/* 121:220 */     if (v.visit(this)) {
/* 122:221 */       for (AstNode e : getElements()) {
/* 123:222 */         e.visit(v);
/* 124:    */       }
/* 125:    */     }
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ArrayLiteral
 * JD-Core Version:    0.7.0.1
 */