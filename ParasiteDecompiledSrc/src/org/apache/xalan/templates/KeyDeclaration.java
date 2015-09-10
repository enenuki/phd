/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xml.utils.QName;
/*   6:    */ import org.apache.xpath.XPath;
/*   7:    */ 
/*   8:    */ public class KeyDeclaration
/*   9:    */   extends ElemTemplateElement
/*  10:    */ {
/*  11:    */   static final long serialVersionUID = 7724030248631137918L;
/*  12:    */   private QName m_name;
/*  13:    */   
/*  14:    */   public KeyDeclaration(Stylesheet parentNode, int docOrderNumber)
/*  15:    */   {
/*  16: 47 */     this.m_parentNode = parentNode;
/*  17: 48 */     setUid(docOrderNumber);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void setName(QName name)
/*  21:    */   {
/*  22: 67 */     this.m_name = name;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public QName getName()
/*  26:    */   {
/*  27: 80 */     return this.m_name;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getNodeName()
/*  31:    */   {
/*  32: 90 */     return "key";
/*  33:    */   }
/*  34:    */   
/*  35: 98 */   private XPath m_matchPattern = null;
/*  36:    */   private XPath m_use;
/*  37:    */   
/*  38:    */   public void setMatch(XPath v)
/*  39:    */   {
/*  40:111 */     this.m_matchPattern = v;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public XPath getMatch()
/*  44:    */   {
/*  45:125 */     return this.m_matchPattern;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setUse(XPath v)
/*  49:    */   {
/*  50:144 */     this.m_use = v;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public XPath getUse()
/*  54:    */   {
/*  55:157 */     return this.m_use;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getXSLToken()
/*  59:    */   {
/*  60:168 */     return 31;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void compose(StylesheetRoot sroot)
/*  64:    */     throws TransformerException
/*  65:    */   {
/*  66:180 */     super.compose(sroot);
/*  67:181 */     Vector vnames = sroot.getComposeState().getVariableNames();
/*  68:182 */     if (null != this.m_matchPattern) {
/*  69:183 */       this.m_matchPattern.fixupVariables(vnames, sroot.getComposeState().getGlobalsSize());
/*  70:    */     }
/*  71:184 */     if (null != this.m_use) {
/*  72:185 */       this.m_use.fixupVariables(vnames, sroot.getComposeState().getGlobalsSize());
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void recompose(StylesheetRoot root)
/*  77:    */   {
/*  78:195 */     root.recomposeKeys(this);
/*  79:    */   }
/*  80:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.KeyDeclaration
 * JD-Core Version:    0.7.0.1
 */