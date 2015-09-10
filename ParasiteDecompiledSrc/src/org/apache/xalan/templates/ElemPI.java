/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.trace.TraceManager;
/*   6:    */ import org.apache.xalan.transformer.MsgMgr;
/*   7:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   8:    */ import org.apache.xml.utils.XML11Char;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.xml.sax.ContentHandler;
/*  11:    */ import org.xml.sax.SAXException;
/*  12:    */ 
/*  13:    */ public class ElemPI
/*  14:    */   extends ElemTemplateElement
/*  15:    */ {
/*  16:    */   static final long serialVersionUID = 5621976448020889825L;
/*  17: 53 */   private AVT m_name_atv = null;
/*  18:    */   
/*  19:    */   public void setName(AVT v)
/*  20:    */   {
/*  21: 63 */     this.m_name_atv = v;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public AVT getName()
/*  25:    */   {
/*  26: 74 */     return this.m_name_atv;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void compose(StylesheetRoot sroot)
/*  30:    */     throws TransformerException
/*  31:    */   {
/*  32: 85 */     super.compose(sroot);
/*  33: 86 */     Vector vnames = sroot.getComposeState().getVariableNames();
/*  34: 87 */     if (null != this.m_name_atv) {
/*  35: 88 */       this.m_name_atv.fixupVariables(vnames, sroot.getComposeState().getGlobalsSize());
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getXSLToken()
/*  40:    */   {
/*  41:101 */     return 58;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getNodeName()
/*  45:    */   {
/*  46:111 */     return "processing-instruction";
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void execute(TransformerImpl transformer)
/*  50:    */     throws TransformerException
/*  51:    */   {
/*  52:129 */     if (transformer.getDebug()) {
/*  53:130 */       transformer.getTraceManager().fireTraceEvent(this);
/*  54:    */     }
/*  55:132 */     XPathContext xctxt = transformer.getXPathContext();
/*  56:133 */     int sourceNode = xctxt.getCurrentNode();
/*  57:    */     
/*  58:135 */     String piName = this.m_name_atv == null ? null : this.m_name_atv.evaluate(xctxt, sourceNode, this);
/*  59:138 */     if (piName == null) {
/*  60:138 */       return;
/*  61:    */     }
/*  62:140 */     if (piName.equalsIgnoreCase("xml"))
/*  63:    */     {
/*  64:142 */       transformer.getMsgMgr().warn(this, "WG_PROCESSINGINSTRUCTION_NAME_CANT_BE_XML", new Object[] { "name", piName });
/*  65:    */       
/*  66:    */ 
/*  67:145 */       return;
/*  68:    */     }
/*  69:150 */     if ((!this.m_name_atv.isSimple()) && (!XML11Char.isXML11ValidNCName(piName)))
/*  70:    */     {
/*  71:152 */       transformer.getMsgMgr().warn(this, "WG_PROCESSINGINSTRUCTION_NOTVALID_NCNAME", new Object[] { "name", piName });
/*  72:    */       
/*  73:    */ 
/*  74:155 */       return;
/*  75:    */     }
/*  76:166 */     String data = transformer.transformToString(this);
/*  77:    */     try
/*  78:    */     {
/*  79:170 */       transformer.getResultTreeHandler().processingInstruction(piName, data);
/*  80:    */     }
/*  81:    */     catch (SAXException se)
/*  82:    */     {
/*  83:174 */       throw new TransformerException(se);
/*  84:    */     }
/*  85:177 */     if (transformer.getDebug()) {
/*  86:178 */       transformer.getTraceManager().fireTraceEndEvent(this);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public ElemTemplateElement appendChild(ElemTemplateElement newChild)
/*  91:    */   {
/*  92:193 */     int type = newChild.getXSLToken();
/*  93:195 */     switch (type)
/*  94:    */     {
/*  95:    */     case 9: 
/*  96:    */     case 17: 
/*  97:    */     case 28: 
/*  98:    */     case 30: 
/*  99:    */     case 35: 
/* 100:    */     case 36: 
/* 101:    */     case 37: 
/* 102:    */     case 42: 
/* 103:    */     case 50: 
/* 104:    */     case 72: 
/* 105:    */     case 73: 
/* 106:    */     case 74: 
/* 107:    */     case 75: 
/* 108:    */     case 78: 
/* 109:    */       break;
/* 110:    */     default: 
/* 111:221 */       error("ER_CANNOT_ADD", new Object[] { newChild.getNodeName(), getNodeName() });
/* 112:    */     }
/* 113:228 */     return super.appendChild(newChild);
/* 114:    */   }
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemPI
 * JD-Core Version:    0.7.0.1
 */