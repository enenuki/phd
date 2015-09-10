/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ public class ObjectLiteral
/*   8:    */   extends AstNode
/*   9:    */   implements DestructuringForm
/*  10:    */ {
/*  11: 67 */   private static final List<ObjectProperty> NO_ELEMS = Collections.unmodifiableList(new ArrayList());
/*  12:    */   private List<ObjectProperty> elements;
/*  13:    */   boolean isDestructuring;
/*  14:    */   
/*  15:    */   public ObjectLiteral()
/*  16:    */   {
/*  17: 74 */     this.type = 66;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public ObjectLiteral(int pos)
/*  21:    */   {
/*  22: 81 */     super(pos);this.type = 66;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public ObjectLiteral(int pos, int len)
/*  26:    */   {
/*  27: 85 */     super(pos, len);this.type = 66;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public List<ObjectProperty> getElements()
/*  31:    */   {
/*  32: 93 */     return this.elements != null ? this.elements : NO_ELEMS;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setElements(List<ObjectProperty> elements)
/*  36:    */   {
/*  37:102 */     if (elements == null)
/*  38:    */     {
/*  39:103 */       this.elements = null;
/*  40:    */     }
/*  41:    */     else
/*  42:    */     {
/*  43:105 */       if (this.elements != null) {
/*  44:106 */         this.elements.clear();
/*  45:    */       }
/*  46:107 */       for (ObjectProperty o : elements) {
/*  47:108 */         addElement(o);
/*  48:    */       }
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void addElement(ObjectProperty element)
/*  53:    */   {
/*  54:118 */     assertNotNull(element);
/*  55:119 */     if (this.elements == null) {
/*  56:120 */       this.elements = new ArrayList();
/*  57:    */     }
/*  58:122 */     this.elements.add(element);
/*  59:123 */     element.setParent(this);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setIsDestructuring(boolean destructuring)
/*  63:    */   {
/*  64:132 */     this.isDestructuring = destructuring;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean isDestructuring()
/*  68:    */   {
/*  69:141 */     return this.isDestructuring;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String toSource(int depth)
/*  73:    */   {
/*  74:146 */     StringBuilder sb = new StringBuilder();
/*  75:147 */     sb.append(makeIndent(depth));
/*  76:148 */     sb.append("{");
/*  77:149 */     if (this.elements != null) {
/*  78:150 */       printList(this.elements, sb);
/*  79:    */     }
/*  80:152 */     sb.append("}");
/*  81:153 */     return sb.toString();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void visit(NodeVisitor v)
/*  85:    */   {
/*  86:162 */     if (v.visit(this)) {
/*  87:163 */       for (ObjectProperty prop : getElements()) {
/*  88:164 */         prop.visit(v);
/*  89:    */       }
/*  90:    */     }
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ObjectLiteral
 * JD-Core Version:    0.7.0.1
 */