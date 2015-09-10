/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xpath.XPath;
/*   6:    */ import org.w3c.dom.DOMException;
/*   7:    */ import org.w3c.dom.Node;
/*   8:    */ 
/*   9:    */ public class ElemSort
/*  10:    */   extends ElemTemplateElement
/*  11:    */ {
/*  12:    */   static final long serialVersionUID = -4991510257335851938L;
/*  13: 54 */   private XPath m_selectExpression = null;
/*  14:    */   
/*  15:    */   public void setSelect(XPath v)
/*  16:    */   {
/*  17: 73 */     if (v.getPatternString().indexOf("{") < 0) {
/*  18: 74 */       this.m_selectExpression = v;
/*  19:    */     } else {
/*  20: 76 */       error("ER_NO_CURLYBRACE", null);
/*  21:    */     }
/*  22:    */   }
/*  23:    */   
/*  24:    */   public XPath getSelect()
/*  25:    */   {
/*  26: 95 */     return this.m_selectExpression;
/*  27:    */   }
/*  28:    */   
/*  29:102 */   private AVT m_lang_avt = null;
/*  30:    */   
/*  31:    */   public void setLang(AVT v)
/*  32:    */   {
/*  33:114 */     this.m_lang_avt = v;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public AVT getLang()
/*  37:    */   {
/*  38:127 */     return this.m_lang_avt;
/*  39:    */   }
/*  40:    */   
/*  41:135 */   private AVT m_dataType_avt = null;
/*  42:    */   
/*  43:    */   public void setDataType(AVT v)
/*  44:    */   {
/*  45:171 */     this.m_dataType_avt = v;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public AVT getDataType()
/*  49:    */   {
/*  50:208 */     return this.m_dataType_avt;
/*  51:    */   }
/*  52:    */   
/*  53:216 */   private AVT m_order_avt = null;
/*  54:    */   
/*  55:    */   public void setOrder(AVT v)
/*  56:    */   {
/*  57:228 */     this.m_order_avt = v;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public AVT getOrder()
/*  61:    */   {
/*  62:241 */     return this.m_order_avt;
/*  63:    */   }
/*  64:    */   
/*  65:249 */   private AVT m_caseorder_avt = null;
/*  66:    */   
/*  67:    */   public void setCaseOrder(AVT v)
/*  68:    */   {
/*  69:266 */     this.m_caseorder_avt = v;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public AVT getCaseOrder()
/*  73:    */   {
/*  74:282 */     return this.m_caseorder_avt;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getXSLToken()
/*  78:    */   {
/*  79:293 */     return 64;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getNodeName()
/*  83:    */   {
/*  84:303 */     return "sort";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Node appendChild(Node newChild)
/*  88:    */     throws DOMException
/*  89:    */   {
/*  90:318 */     error("ER_CANNOT_ADD", new Object[] { newChild.getNodeName(), getNodeName() });
/*  91:    */     
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:323 */     return null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void compose(StylesheetRoot sroot)
/*  99:    */     throws TransformerException
/* 100:    */   {
/* 101:335 */     super.compose(sroot);
/* 102:336 */     StylesheetRoot.ComposeState cstate = sroot.getComposeState();
/* 103:337 */     Vector vnames = cstate.getVariableNames();
/* 104:338 */     if (null != this.m_caseorder_avt) {
/* 105:339 */       this.m_caseorder_avt.fixupVariables(vnames, cstate.getGlobalsSize());
/* 106:    */     }
/* 107:340 */     if (null != this.m_dataType_avt) {
/* 108:341 */       this.m_dataType_avt.fixupVariables(vnames, cstate.getGlobalsSize());
/* 109:    */     }
/* 110:342 */     if (null != this.m_lang_avt) {
/* 111:343 */       this.m_lang_avt.fixupVariables(vnames, cstate.getGlobalsSize());
/* 112:    */     }
/* 113:344 */     if (null != this.m_order_avt) {
/* 114:345 */       this.m_order_avt.fixupVariables(vnames, cstate.getGlobalsSize());
/* 115:    */     }
/* 116:346 */     if (null != this.m_selectExpression) {
/* 117:347 */       this.m_selectExpression.fixupVariables(vnames, cstate.getGlobalsSize());
/* 118:    */     }
/* 119:    */   }
/* 120:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemSort
 * JD-Core Version:    0.7.0.1
 */