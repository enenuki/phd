/*  1:   */ package org.apache.xalan.templates;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Vector;
/*  5:   */ import javax.xml.transform.TransformerException;
/*  6:   */ import org.apache.xml.utils.FastStringBuffer;
/*  7:   */ import org.apache.xml.utils.PrefixResolver;
/*  8:   */ import org.apache.xpath.XPathContext;
/*  9:   */ 
/* 10:   */ public abstract class AVTPart
/* 11:   */   implements Serializable, XSLTVisitable
/* 12:   */ {
/* 13:   */   static final long serialVersionUID = -1747749903613916025L;
/* 14:   */   
/* 15:   */   public abstract String getSimpleString();
/* 16:   */   
/* 17:   */   public abstract void evaluate(XPathContext paramXPathContext, FastStringBuffer paramFastStringBuffer, int paramInt, PrefixResolver paramPrefixResolver)
/* 18:   */     throws TransformerException;
/* 19:   */   
/* 20:   */   public void setXPathSupport(XPathContext support) {}
/* 21:   */   
/* 22:   */   public boolean canTraverseOutsideSubtree()
/* 23:   */   {
/* 24:78 */     return false;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public abstract void fixupVariables(Vector paramVector, int paramInt);
/* 28:   */   
/* 29:   */   public abstract void callVisitors(XSLTVisitor paramXSLTVisitor);
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.AVTPart
 * JD-Core Version:    0.7.0.1
 */