/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.trace.TraceManager;
/*   6:    */ import org.apache.xalan.transformer.MsgMgr;
/*   7:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   8:    */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   9:    */ import org.apache.xml.serializer.SerializationHandler;
/*  10:    */ import org.apache.xml.utils.QName;
/*  11:    */ import org.apache.xml.utils.XML11Char;
/*  12:    */ import org.apache.xpath.XPathContext;
/*  13:    */ import org.xml.sax.ContentHandler;
/*  14:    */ import org.xml.sax.SAXException;
/*  15:    */ 
/*  16:    */ public class ElemElement
/*  17:    */   extends ElemUse
/*  18:    */ {
/*  19:    */   static final long serialVersionUID = -324619535592435183L;
/*  20: 57 */   protected AVT m_name_avt = null;
/*  21:    */   
/*  22:    */   public void setName(AVT v)
/*  23:    */   {
/*  24: 69 */     this.m_name_avt = v;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public AVT getName()
/*  28:    */   {
/*  29: 82 */     return this.m_name_avt;
/*  30:    */   }
/*  31:    */   
/*  32: 92 */   protected AVT m_namespace_avt = null;
/*  33:    */   
/*  34:    */   public void setNamespace(AVT v)
/*  35:    */   {
/*  36:105 */     this.m_namespace_avt = v;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public AVT getNamespace()
/*  40:    */   {
/*  41:119 */     return this.m_namespace_avt;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void compose(StylesheetRoot sroot)
/*  45:    */     throws TransformerException
/*  46:    */   {
/*  47:130 */     super.compose(sroot);
/*  48:    */     
/*  49:132 */     StylesheetRoot.ComposeState cstate = sroot.getComposeState();
/*  50:133 */     Vector vnames = cstate.getVariableNames();
/*  51:134 */     if (null != this.m_name_avt) {
/*  52:135 */       this.m_name_avt.fixupVariables(vnames, cstate.getGlobalsSize());
/*  53:    */     }
/*  54:136 */     if (null != this.m_namespace_avt) {
/*  55:137 */       this.m_namespace_avt.fixupVariables(vnames, cstate.getGlobalsSize());
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getXSLToken()
/*  60:    */   {
/*  61:149 */     return 46;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getNodeName()
/*  65:    */   {
/*  66:159 */     return "element";
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected String resolvePrefix(SerializationHandler rhandler, String prefix, String nodeNamespace)
/*  70:    */     throws TransformerException
/*  71:    */   {
/*  72:185 */     return prefix;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void execute(TransformerImpl transformer)
/*  76:    */     throws TransformerException
/*  77:    */   {
/*  78:205 */     if (transformer.getDebug()) {
/*  79:206 */       transformer.getTraceManager().fireTraceEvent(this);
/*  80:    */     }
/*  81:208 */     SerializationHandler rhandler = transformer.getSerializationHandler();
/*  82:209 */     XPathContext xctxt = transformer.getXPathContext();
/*  83:210 */     int sourceNode = xctxt.getCurrentNode();
/*  84:    */     
/*  85:    */ 
/*  86:213 */     String nodeName = this.m_name_avt == null ? null : this.m_name_avt.evaluate(xctxt, sourceNode, this);
/*  87:    */     
/*  88:215 */     String prefix = null;
/*  89:216 */     String nodeNamespace = "";
/*  90:219 */     if ((nodeName != null) && (!this.m_name_avt.isSimple()) && (!XML11Char.isXML11ValidQName(nodeName)))
/*  91:    */     {
/*  92:221 */       transformer.getMsgMgr().warn(this, "WG_ILLEGAL_ATTRIBUTE_VALUE", new Object[] { "name", nodeName });
/*  93:    */       
/*  94:    */ 
/*  95:    */ 
/*  96:225 */       nodeName = null;
/*  97:    */     }
/*  98:228 */     else if (nodeName != null)
/*  99:    */     {
/* 100:230 */       prefix = QName.getPrefixPart(nodeName);
/* 101:232 */       if (null != this.m_namespace_avt)
/* 102:    */       {
/* 103:234 */         nodeNamespace = this.m_namespace_avt.evaluate(xctxt, sourceNode, this);
/* 104:235 */         if ((null == nodeNamespace) || ((prefix != null) && (prefix.length() > 0) && (nodeNamespace.length() == 0)))
/* 105:    */         {
/* 106:237 */           transformer.getMsgMgr().error(this, "ER_NULL_URI_NAMESPACE");
/* 107:    */         }
/* 108:    */         else
/* 109:    */         {
/* 110:243 */           prefix = resolvePrefix(rhandler, prefix, nodeNamespace);
/* 111:244 */           if (null == prefix) {
/* 112:245 */             prefix = "";
/* 113:    */           }
/* 114:247 */           if (prefix.length() > 0) {
/* 115:248 */             nodeName = prefix + ":" + QName.getLocalPart(nodeName);
/* 116:    */           } else {
/* 117:250 */             nodeName = QName.getLocalPart(nodeName);
/* 118:    */           }
/* 119:    */         }
/* 120:    */       }
/* 121:    */       else
/* 122:    */       {
/* 123:    */         try
/* 124:    */         {
/* 125:261 */           nodeNamespace = getNamespaceForPrefix(prefix);
/* 126:267 */           if ((null == nodeNamespace) && (prefix.length() == 0))
/* 127:    */           {
/* 128:268 */             nodeNamespace = "";
/* 129:    */           }
/* 130:269 */           else if (null == nodeNamespace)
/* 131:    */           {
/* 132:271 */             transformer.getMsgMgr().warn(this, "WG_COULD_NOT_RESOLVE_PREFIX", new Object[] { prefix });
/* 133:    */             
/* 134:    */ 
/* 135:    */ 
/* 136:275 */             nodeName = null;
/* 137:    */           }
/* 138:    */         }
/* 139:    */         catch (Exception ex)
/* 140:    */         {
/* 141:281 */           transformer.getMsgMgr().warn(this, "WG_COULD_NOT_RESOLVE_PREFIX", new Object[] { prefix });
/* 142:    */           
/* 143:    */ 
/* 144:    */ 
/* 145:285 */           nodeName = null;
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:290 */     constructNode(nodeName, prefix, nodeNamespace, transformer);
/* 150:292 */     if (transformer.getDebug()) {
/* 151:293 */       transformer.getTraceManager().fireTraceEndEvent(this);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   void constructNode(String nodeName, String prefix, String nodeNamespace, TransformerImpl transformer)
/* 156:    */     throws TransformerException
/* 157:    */   {
/* 158:    */     try
/* 159:    */     {
/* 160:320 */       SerializationHandler rhandler = transformer.getResultTreeHandler();
/* 161:    */       boolean shouldAddAttrs;
/* 162:322 */       if (null == nodeName)
/* 163:    */       {
/* 164:324 */         shouldAddAttrs = false;
/* 165:    */       }
/* 166:    */       else
/* 167:    */       {
/* 168:328 */         if (null != prefix) {
/* 169:330 */           rhandler.startPrefixMapping(prefix, nodeNamespace, true);
/* 170:    */         }
/* 171:333 */         rhandler.startElement(nodeNamespace, QName.getLocalPart(nodeName), nodeName);
/* 172:    */         
/* 173:    */ 
/* 174:336 */         super.execute(transformer);
/* 175:    */         
/* 176:338 */         shouldAddAttrs = true;
/* 177:    */       }
/* 178:341 */       transformer.executeChildTemplates(this, shouldAddAttrs);
/* 179:344 */       if (null != nodeName)
/* 180:    */       {
/* 181:346 */         rhandler.endElement(nodeNamespace, QName.getLocalPart(nodeName), nodeName);
/* 182:348 */         if (null != prefix) {
/* 183:350 */           rhandler.endPrefixMapping(prefix);
/* 184:    */         }
/* 185:    */       }
/* 186:    */     }
/* 187:    */     catch (SAXException se)
/* 188:    */     {
/* 189:356 */       throw new TransformerException(se);
/* 190:    */     }
/* 191:    */   }
/* 192:    */   
/* 193:    */   protected void callChildVisitors(XSLTVisitor visitor, boolean callAttrs)
/* 194:    */   {
/* 195:366 */     if (callAttrs)
/* 196:    */     {
/* 197:368 */       if (null != this.m_name_avt) {
/* 198:369 */         this.m_name_avt.callVisitors(visitor);
/* 199:    */       }
/* 200:371 */       if (null != this.m_namespace_avt) {
/* 201:372 */         this.m_namespace_avt.callVisitors(visitor);
/* 202:    */       }
/* 203:    */     }
/* 204:375 */     super.callChildVisitors(visitor, callAttrs);
/* 205:    */   }
/* 206:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemElement
 * JD-Core Version:    0.7.0.1
 */