/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class XmlPropRef
/*   4:    */   extends XmlRef
/*   5:    */ {
/*   6:    */   private Name propName;
/*   7:    */   
/*   8:    */   public XmlPropRef()
/*   9:    */   {
/*  10: 65 */     this.type = 79;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public XmlPropRef(int pos)
/*  14:    */   {
/*  15: 72 */     super(pos);this.type = 79;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public XmlPropRef(int pos, int len)
/*  19:    */   {
/*  20: 76 */     super(pos, len);this.type = 79;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Name getPropName()
/*  24:    */   {
/*  25: 83 */     return this.propName;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setPropName(Name propName)
/*  29:    */   {
/*  30: 91 */     assertNotNull(propName);
/*  31: 92 */     this.propName = propName;
/*  32: 93 */     propName.setParent(this);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String toSource(int depth)
/*  36:    */   {
/*  37: 98 */     StringBuilder sb = new StringBuilder();
/*  38: 99 */     sb.append(makeIndent(depth));
/*  39:100 */     if (isAttributeAccess()) {
/*  40:101 */       sb.append("@");
/*  41:    */     }
/*  42:103 */     if (this.namespace != null)
/*  43:    */     {
/*  44:104 */       sb.append(this.namespace.toSource(0));
/*  45:105 */       sb.append("::");
/*  46:    */     }
/*  47:107 */     sb.append(this.propName.toSource(0));
/*  48:108 */     return sb.toString();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void visit(NodeVisitor v)
/*  52:    */   {
/*  53:116 */     if (v.visit(this))
/*  54:    */     {
/*  55:117 */       if (this.namespace != null) {
/*  56:118 */         this.namespace.visit(v);
/*  57:    */       }
/*  58:120 */       this.propName.visit(v);
/*  59:    */     }
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.XmlPropRef
 * JD-Core Version:    0.7.0.1
 */