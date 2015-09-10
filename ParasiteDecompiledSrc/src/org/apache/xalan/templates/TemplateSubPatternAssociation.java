/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xml.utils.QName;
/*   6:    */ import org.apache.xpath.XPathContext;
/*   7:    */ import org.apache.xpath.patterns.StepPattern;
/*   8:    */ 
/*   9:    */ class TemplateSubPatternAssociation
/*  10:    */   implements Serializable, Cloneable
/*  11:    */ {
/*  12:    */   static final long serialVersionUID = -8902606755229903350L;
/*  13:    */   StepPattern m_stepPattern;
/*  14:    */   private String m_pattern;
/*  15:    */   private ElemTemplate m_template;
/*  16: 50 */   private TemplateSubPatternAssociation m_next = null;
/*  17:    */   private boolean m_wild;
/*  18:    */   private String m_targetString;
/*  19:    */   
/*  20:    */   TemplateSubPatternAssociation(ElemTemplate template, StepPattern pattern, String pat)
/*  21:    */   {
/*  22: 67 */     this.m_pattern = pat;
/*  23: 68 */     this.m_template = template;
/*  24: 69 */     this.m_stepPattern = pattern;
/*  25: 70 */     this.m_targetString = this.m_stepPattern.getTargetString();
/*  26: 71 */     this.m_wild = this.m_targetString.equals("*");
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Object clone()
/*  30:    */     throws CloneNotSupportedException
/*  31:    */   {
/*  32: 84 */     TemplateSubPatternAssociation tspa = (TemplateSubPatternAssociation)super.clone();
/*  33:    */     
/*  34:    */ 
/*  35: 87 */     tspa.m_next = null;
/*  36:    */     
/*  37: 89 */     return tspa;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final String getTargetString()
/*  41:    */   {
/*  42:100 */     return this.m_targetString;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setTargetString(String key)
/*  46:    */   {
/*  47:111 */     this.m_targetString = key;
/*  48:    */   }
/*  49:    */   
/*  50:    */   boolean matchMode(QName m1)
/*  51:    */   {
/*  52:123 */     return matchModes(m1, this.m_template.getMode());
/*  53:    */   }
/*  54:    */   
/*  55:    */   private boolean matchModes(QName m1, QName m2)
/*  56:    */   {
/*  57:136 */     return ((null == m1) && (null == m2)) || ((null != m1) && (null != m2) && (m1.equals(m2)));
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean matches(XPathContext xctxt, int targetNode, QName mode)
/*  61:    */     throws TransformerException
/*  62:    */   {
/*  63:155 */     double score = this.m_stepPattern.getMatchScore(xctxt, targetNode);
/*  64:    */     
/*  65:157 */     return ((-1.0D / 0.0D) != score) && (matchModes(mode, this.m_template.getMode()));
/*  66:    */   }
/*  67:    */   
/*  68:    */   public final boolean isWild()
/*  69:    */   {
/*  70:168 */     return this.m_wild;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public final StepPattern getStepPattern()
/*  74:    */   {
/*  75:179 */     return this.m_stepPattern;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public final String getPattern()
/*  79:    */   {
/*  80:190 */     return this.m_pattern;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getDocOrderPos()
/*  84:    */   {
/*  85:201 */     return this.m_template.getUid();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public final int getImportLevel()
/*  89:    */   {
/*  90:212 */     return this.m_template.getStylesheetComposed().getImportCountComposed();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public final ElemTemplate getTemplate()
/*  94:    */   {
/*  95:223 */     return this.m_template;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public final TemplateSubPatternAssociation getNext()
/*  99:    */   {
/* 100:233 */     return this.m_next;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setNext(TemplateSubPatternAssociation mp)
/* 104:    */   {
/* 105:247 */     this.m_next = mp;
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.TemplateSubPatternAssociation
 * JD-Core Version:    0.7.0.1
 */