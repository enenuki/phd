/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.SourceLocator;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.apache.xalan.trace.TraceManager;
/*   7:    */ import org.apache.xalan.transformer.StackGuard;
/*   8:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   9:    */ import org.apache.xml.utils.QName;
/*  10:    */ import org.apache.xpath.XPath;
/*  11:    */ import org.apache.xpath.XPathContext;
/*  12:    */ 
/*  13:    */ public class ElemTemplate
/*  14:    */   extends ElemTemplateElement
/*  15:    */ {
/*  16:    */   static final long serialVersionUID = -5283056789965384058L;
/*  17:    */   private String m_publicId;
/*  18:    */   private String m_systemId;
/*  19:    */   private Stylesheet m_stylesheet;
/*  20:    */   
/*  21:    */   public String getPublicId()
/*  22:    */   {
/*  23: 72 */     return this.m_publicId;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getSystemId()
/*  27:    */   {
/*  28: 87 */     return this.m_systemId;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setLocaterInfo(SourceLocator locator)
/*  32:    */   {
/*  33: 98 */     this.m_publicId = locator.getPublicId();
/*  34: 99 */     this.m_systemId = locator.getSystemId();
/*  35:    */     
/*  36:101 */     super.setLocaterInfo(locator);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public StylesheetComposed getStylesheetComposed()
/*  40:    */   {
/*  41:120 */     return this.m_stylesheet.getStylesheetComposed();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Stylesheet getStylesheet()
/*  45:    */   {
/*  46:130 */     return this.m_stylesheet;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setStylesheet(Stylesheet sheet)
/*  50:    */   {
/*  51:140 */     this.m_stylesheet = sheet;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public StylesheetRoot getStylesheetRoot()
/*  55:    */   {
/*  56:150 */     return this.m_stylesheet.getStylesheetRoot();
/*  57:    */   }
/*  58:    */   
/*  59:158 */   private XPath m_matchPattern = null;
/*  60:    */   
/*  61:    */   public void setMatch(XPath v)
/*  62:    */   {
/*  63:173 */     this.m_matchPattern = v;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public XPath getMatch()
/*  67:    */   {
/*  68:189 */     return this.m_matchPattern;
/*  69:    */   }
/*  70:    */   
/*  71:196 */   private QName m_name = null;
/*  72:    */   private QName m_mode;
/*  73:    */   
/*  74:    */   public void setName(QName v)
/*  75:    */   {
/*  76:209 */     this.m_name = v;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public QName getName()
/*  80:    */   {
/*  81:223 */     return this.m_name;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setMode(QName v)
/*  85:    */   {
/*  86:244 */     this.m_mode = v;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public QName getMode()
/*  90:    */   {
/*  91:258 */     return this.m_mode;
/*  92:    */   }
/*  93:    */   
/*  94:266 */   private double m_priority = (-1.0D / 0.0D);
/*  95:    */   public int m_frameSize;
/*  96:    */   int m_inArgsSize;
/*  97:    */   private int[] m_argsQNameIDs;
/*  98:    */   
/*  99:    */   public void setPriority(double v)
/* 100:    */   {
/* 101:280 */     this.m_priority = v;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public double getPriority()
/* 105:    */   {
/* 106:295 */     return this.m_priority;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getXSLToken()
/* 110:    */   {
/* 111:306 */     return 19;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getNodeName()
/* 115:    */   {
/* 116:316 */     return "template";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void compose(StylesheetRoot sroot)
/* 120:    */     throws TransformerException
/* 121:    */   {
/* 122:347 */     super.compose(sroot);
/* 123:348 */     StylesheetRoot.ComposeState cstate = sroot.getComposeState();
/* 124:349 */     Vector vnames = cstate.getVariableNames();
/* 125:350 */     if (null != this.m_matchPattern) {
/* 126:351 */       this.m_matchPattern.fixupVariables(vnames, sroot.getComposeState().getGlobalsSize());
/* 127:    */     }
/* 128:353 */     cstate.resetStackFrameSize();
/* 129:354 */     this.m_inArgsSize = 0;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void endCompose(StylesheetRoot sroot)
/* 133:    */     throws TransformerException
/* 134:    */   {
/* 135:362 */     StylesheetRoot.ComposeState cstate = sroot.getComposeState();
/* 136:363 */     super.endCompose(sroot);
/* 137:364 */     this.m_frameSize = cstate.getFrameSize();
/* 138:    */     
/* 139:366 */     cstate.resetStackFrameSize();
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void execute(TransformerImpl transformer)
/* 143:    */     throws TransformerException
/* 144:    */   {
/* 145:382 */     XPathContext xctxt = transformer.getXPathContext();
/* 146:    */     
/* 147:384 */     transformer.getStackGuard().checkForInfinateLoop();
/* 148:    */     
/* 149:386 */     xctxt.pushRTFContext();
/* 150:388 */     if (transformer.getDebug()) {
/* 151:389 */       transformer.getTraceManager().fireTraceEvent(this);
/* 152:    */     }
/* 153:394 */     transformer.executeChildTemplates(this, true);
/* 154:405 */     if (transformer.getDebug()) {
/* 155:406 */       transformer.getTraceManager().fireTraceEndEvent(this);
/* 156:    */     }
/* 157:408 */     xctxt.popRTFContext();
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void recompose(StylesheetRoot root)
/* 161:    */   {
/* 162:418 */     root.recomposeTemplates(this);
/* 163:    */   }
/* 164:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemTemplate
 * JD-Core Version:    0.7.0.1
 */