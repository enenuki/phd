/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*  2:   */ 
/*  3:   */ public class XmlString
/*  4:   */   extends XmlFragment
/*  5:   */ {
/*  6:   */   private String xml;
/*  7:   */   
/*  8:   */   public XmlString() {}
/*  9:   */   
/* 10:   */   public XmlString(int pos)
/* 11:   */   {
/* 12:54 */     super(pos);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public XmlString(int pos, String s)
/* 16:   */   {
/* 17:58 */     super(pos);
/* 18:59 */     setXml(s);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setXml(String s)
/* 22:   */   {
/* 23:69 */     assertNotNull(s);
/* 24:70 */     this.xml = s;
/* 25:71 */     setLength(s.length());
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getXml()
/* 29:   */   {
/* 30:79 */     return this.xml;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String toSource(int depth)
/* 34:   */   {
/* 35:84 */     return makeIndent(depth) + this.xml;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void visit(NodeVisitor v)
/* 39:   */   {
/* 40:92 */     v.visit(this);
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.XmlString
 * JD-Core Version:    0.7.0.1
 */