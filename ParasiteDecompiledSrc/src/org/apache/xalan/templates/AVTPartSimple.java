/*  1:   */ package org.apache.xalan.templates;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.xml.utils.FastStringBuffer;
/*  5:   */ import org.apache.xml.utils.PrefixResolver;
/*  6:   */ import org.apache.xpath.XPathContext;
/*  7:   */ 
/*  8:   */ public class AVTPartSimple
/*  9:   */   extends AVTPart
/* 10:   */ {
/* 11:   */   static final long serialVersionUID = -3744957690598727913L;
/* 12:   */   private String m_val;
/* 13:   */   
/* 14:   */   public AVTPartSimple(String val)
/* 15:   */   {
/* 16:46 */     this.m_val = val;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getSimpleString()
/* 20:   */   {
/* 21:56 */     return this.m_val;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void fixupVariables(Vector vars, int globalsSize) {}
/* 25:   */   
/* 26:   */   public void evaluate(XPathContext xctxt, FastStringBuffer buf, int context, PrefixResolver nsNode)
/* 27:   */   {
/* 28:90 */     buf.append(this.m_val);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void callVisitors(XSLTVisitor visitor) {}
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.AVTPartSimple
 * JD-Core Version:    0.7.0.1
 */