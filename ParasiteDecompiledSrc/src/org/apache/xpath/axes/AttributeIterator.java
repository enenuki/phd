/*  1:   */ package org.apache.xpath.axes;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.dtm.DTM;
/*  5:   */ import org.apache.xpath.compiler.Compiler;
/*  6:   */ 
/*  7:   */ public class AttributeIterator
/*  8:   */   extends ChildTestIterator
/*  9:   */ {
/* 10:   */   static final long serialVersionUID = -8417986700712229686L;
/* 11:   */   
/* 12:   */   AttributeIterator(Compiler compiler, int opPos, int analysis)
/* 13:   */     throws TransformerException
/* 14:   */   {
/* 15:48 */     super(compiler, opPos, analysis);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected int getNextNode()
/* 19:   */   {
/* 20:56 */     this.m_lastFetched = (-1 == this.m_lastFetched ? this.m_cdtm.getFirstAttribute(this.m_context) : this.m_cdtm.getNextAttribute(this.m_lastFetched));
/* 21:   */     
/* 22:   */ 
/* 23:59 */     return this.m_lastFetched;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getAxis()
/* 27:   */   {
/* 28:70 */     return 2;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.AttributeIterator
 * JD-Core Version:    0.7.0.1
 */