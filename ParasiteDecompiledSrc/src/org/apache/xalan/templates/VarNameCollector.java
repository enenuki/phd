/*  1:   */ package org.apache.xalan.templates;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.xml.utils.QName;
/*  5:   */ import org.apache.xpath.ExpressionOwner;
/*  6:   */ import org.apache.xpath.XPathVisitor;
/*  7:   */ import org.apache.xpath.operations.Variable;
/*  8:   */ 
/*  9:   */ public class VarNameCollector
/* 10:   */   extends XPathVisitor
/* 11:   */ {
/* 12:35 */   Vector m_refs = new Vector();
/* 13:   */   
/* 14:   */   public void reset()
/* 15:   */   {
/* 16:42 */     this.m_refs.removeAllElements();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public int getVarCount()
/* 20:   */   {
/* 21:51 */     return this.m_refs.size();
/* 22:   */   }
/* 23:   */   
/* 24:   */   boolean doesOccur(QName refName)
/* 25:   */   {
/* 26:63 */     return this.m_refs.contains(refName);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean visitVariableRef(ExpressionOwner owner, Variable var)
/* 30:   */   {
/* 31:75 */     this.m_refs.addElement(var.getQName());
/* 32:76 */     return true;
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.VarNameCollector
 * JD-Core Version:    0.7.0.1
 */