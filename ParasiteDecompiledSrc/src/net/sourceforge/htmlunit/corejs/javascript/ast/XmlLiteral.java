/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ 
/*   6:    */ public class XmlLiteral
/*   7:    */   extends AstNode
/*   8:    */ {
/*   9: 55 */   private List<XmlFragment> fragments = new ArrayList();
/*  10:    */   
/*  11:    */   public XmlLiteral()
/*  12:    */   {
/*  13: 58 */     this.type = 145;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public XmlLiteral(int pos)
/*  17:    */   {
/*  18: 65 */     super(pos);this.type = 145;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public XmlLiteral(int pos, int len)
/*  22:    */   {
/*  23: 69 */     super(pos, len);this.type = 145;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public List<XmlFragment> getFragments()
/*  27:    */   {
/*  28: 76 */     return this.fragments;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setFragments(List<XmlFragment> fragments)
/*  32:    */   {
/*  33: 86 */     assertNotNull(fragments);
/*  34: 87 */     this.fragments.clear();
/*  35: 88 */     for (XmlFragment fragment : fragments) {
/*  36: 89 */       addFragment(fragment);
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void addFragment(XmlFragment fragment)
/*  41:    */   {
/*  42: 97 */     assertNotNull(fragment);
/*  43: 98 */     this.fragments.add(fragment);
/*  44: 99 */     fragment.setParent(this);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String toSource(int depth)
/*  48:    */   {
/*  49:104 */     StringBuilder sb = new StringBuilder(250);
/*  50:105 */     for (XmlFragment frag : this.fragments) {
/*  51:106 */       sb.append(frag.toSource(0));
/*  52:    */     }
/*  53:108 */     return sb.toString();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void visit(NodeVisitor v)
/*  57:    */   {
/*  58:116 */     if (v.visit(this)) {
/*  59:117 */       for (XmlFragment frag : this.fragments) {
/*  60:118 */         frag.visit(v);
/*  61:    */       }
/*  62:    */     }
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.XmlLiteral
 * JD-Core Version:    0.7.0.1
 */