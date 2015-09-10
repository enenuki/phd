/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.SourceLocator;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.res.XSLMessages;
/*   6:    */ import org.apache.xalan.trace.TraceManager;
/*   7:    */ import org.apache.xalan.transformer.MsgMgr;
/*   8:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   9:    */ import org.apache.xml.utils.QName;
/*  10:    */ import org.apache.xpath.VariableStack;
/*  11:    */ import org.apache.xpath.XPathContext;
/*  12:    */ import org.apache.xpath.objects.XObject;
/*  13:    */ 
/*  14:    */ public class ElemCallTemplate
/*  15:    */   extends ElemForEach
/*  16:    */ {
/*  17:    */   static final long serialVersionUID = 5009634612916030591L;
/*  18: 54 */   public QName m_templateName = null;
/*  19:    */   
/*  20:    */   public void setName(QName name)
/*  21:    */   {
/*  22: 65 */     this.m_templateName = name;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public QName getName()
/*  26:    */   {
/*  27: 77 */     return this.m_templateName;
/*  28:    */   }
/*  29:    */   
/*  30: 84 */   private ElemTemplate m_template = null;
/*  31:    */   
/*  32:    */   public int getXSLToken()
/*  33:    */   {
/*  34: 94 */     return 17;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getNodeName()
/*  38:    */   {
/*  39:104 */     return "call-template";
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void compose(StylesheetRoot sroot)
/*  43:    */     throws TransformerException
/*  44:    */   {
/*  45:115 */     super.compose(sroot);
/*  46:    */     
/*  47:    */ 
/*  48:    */ 
/*  49:119 */     int length = getParamElemCount();
/*  50:120 */     for (int i = 0; i < length; i++)
/*  51:    */     {
/*  52:122 */       ElemWithParam ewp = getParamElem(i);
/*  53:123 */       ewp.compose(sroot);
/*  54:    */     }
/*  55:126 */     if ((null != this.m_templateName) && (null == this.m_template))
/*  56:    */     {
/*  57:127 */       this.m_template = getStylesheetRoot().getTemplateComposed(this.m_templateName);
/*  58:130 */       if (null == this.m_template)
/*  59:    */       {
/*  60:131 */         String themsg = XSLMessages.createMessage("ER_ELEMTEMPLATEELEM_ERR", new Object[] { this.m_templateName });
/*  61:    */         
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:136 */         throw new TransformerException(themsg, this);
/*  66:    */       }
/*  67:140 */       length = getParamElemCount();
/*  68:141 */       for (int i = 0; i < length; i++)
/*  69:    */       {
/*  70:143 */         ElemWithParam ewp = getParamElem(i);
/*  71:144 */         ewp.m_index = -1;
/*  72:    */         
/*  73:    */ 
/*  74:147 */         int etePos = 0;
/*  75:148 */         for (ElemTemplateElement ete = this.m_template.getFirstChildElem(); null != ete; ete = ete.getNextSiblingElem())
/*  76:    */         {
/*  77:151 */           if (ete.getXSLToken() != 41) {
/*  78:    */             break;
/*  79:    */           }
/*  80:153 */           ElemParam ep = (ElemParam)ete;
/*  81:154 */           if (ep.getName().equals(ewp.getName())) {
/*  82:156 */             ewp.m_index = etePos;
/*  83:    */           }
/*  84:161 */           etePos++;
/*  85:    */         }
/*  86:    */       }
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void endCompose(StylesheetRoot sroot)
/*  91:    */     throws TransformerException
/*  92:    */   {
/*  93:173 */     int length = getParamElemCount();
/*  94:174 */     for (int i = 0; i < length; i++)
/*  95:    */     {
/*  96:176 */       ElemWithParam ewp = getParamElem(i);
/*  97:177 */       ewp.endCompose(sroot);
/*  98:    */     }
/*  99:180 */     super.endCompose(sroot);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void execute(TransformerImpl transformer)
/* 103:    */     throws TransformerException
/* 104:    */   {
/* 105:196 */     if (transformer.getDebug()) {
/* 106:197 */       transformer.getTraceManager().fireTraceEvent(this);
/* 107:    */     }
/* 108:199 */     if (null != this.m_template)
/* 109:    */     {
/* 110:201 */       XPathContext xctxt = transformer.getXPathContext();
/* 111:202 */       VariableStack vars = xctxt.getVarStack();
/* 112:    */       
/* 113:204 */       int thisframe = vars.getStackFrame();
/* 114:205 */       int nextFrame = vars.link(this.m_template.m_frameSize);
/* 115:209 */       if (this.m_template.m_inArgsSize > 0)
/* 116:    */       {
/* 117:211 */         vars.clearLocalSlots(0, this.m_template.m_inArgsSize);
/* 118:213 */         if (null != this.m_paramElems)
/* 119:    */         {
/* 120:215 */           int currentNode = xctxt.getCurrentNode();
/* 121:216 */           vars.setStackFrame(thisframe);
/* 122:217 */           int size = this.m_paramElems.length;
/* 123:219 */           for (int i = 0; i < size; i++)
/* 124:    */           {
/* 125:221 */             ElemWithParam ewp = this.m_paramElems[i];
/* 126:222 */             if (ewp.m_index >= 0)
/* 127:    */             {
/* 128:224 */               if (transformer.getDebug()) {
/* 129:225 */                 transformer.getTraceManager().fireTraceEvent(ewp);
/* 130:    */               }
/* 131:226 */               XObject obj = ewp.getValue(transformer, currentNode);
/* 132:227 */               if (transformer.getDebug()) {
/* 133:228 */                 transformer.getTraceManager().fireTraceEndEvent(ewp);
/* 134:    */               }
/* 135:233 */               vars.setLocalVariable(ewp.m_index, obj, nextFrame);
/* 136:    */             }
/* 137:    */           }
/* 138:236 */           vars.setStackFrame(nextFrame);
/* 139:    */         }
/* 140:    */       }
/* 141:240 */       SourceLocator savedLocator = xctxt.getSAXLocator();
/* 142:    */       try
/* 143:    */       {
/* 144:244 */         xctxt.setSAXLocator(this.m_template);
/* 145:    */         
/* 146:    */ 
/* 147:247 */         transformer.pushElemTemplateElement(this.m_template);
/* 148:248 */         this.m_template.execute(transformer);
/* 149:    */       }
/* 150:    */       finally
/* 151:    */       {
/* 152:252 */         transformer.popElemTemplateElement();
/* 153:253 */         xctxt.setSAXLocator(savedLocator);
/* 154:    */         
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:263 */         vars.unlink(thisframe);
/* 164:    */       }
/* 165:    */     }
/* 166:    */     else
/* 167:    */     {
/* 168:268 */       transformer.getMsgMgr().error(this, "ER_TEMPLATE_NOT_FOUND", new Object[] { this.m_templateName });
/* 169:    */     }
/* 170:272 */     if (transformer.getDebug()) {
/* 171:273 */       transformer.getTraceManager().fireTraceEndEvent(this);
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:279 */   protected ElemWithParam[] m_paramElems = null;
/* 176:    */   
/* 177:    */   public int getParamElemCount()
/* 178:    */   {
/* 179:287 */     return this.m_paramElems == null ? 0 : this.m_paramElems.length;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public ElemWithParam getParamElem(int i)
/* 183:    */   {
/* 184:299 */     return this.m_paramElems[i];
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setParamElem(ElemWithParam ParamElem)
/* 188:    */   {
/* 189:309 */     if (null == this.m_paramElems)
/* 190:    */     {
/* 191:311 */       this.m_paramElems = new ElemWithParam[1];
/* 192:312 */       this.m_paramElems[0] = ParamElem;
/* 193:    */     }
/* 194:    */     else
/* 195:    */     {
/* 196:318 */       int length = this.m_paramElems.length;
/* 197:319 */       ElemWithParam[] ewp = new ElemWithParam[length + 1];
/* 198:320 */       System.arraycopy(this.m_paramElems, 0, ewp, 0, length);
/* 199:321 */       this.m_paramElems = ewp;
/* 200:322 */       ewp[length] = ParamElem;
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   public ElemTemplateElement appendChild(ElemTemplateElement newChild)
/* 205:    */   {
/* 206:343 */     int type = newChild.getXSLToken();
/* 207:345 */     if (2 == type) {
/* 208:347 */       setParamElem((ElemWithParam)newChild);
/* 209:    */     }
/* 210:352 */     return super.appendChild(newChild);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void callChildVisitors(XSLTVisitor visitor, boolean callAttrs)
/* 214:    */   {
/* 215:372 */     super.callChildVisitors(visitor, callAttrs);
/* 216:    */   }
/* 217:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemCallTemplate
 * JD-Core Version:    0.7.0.1
 */