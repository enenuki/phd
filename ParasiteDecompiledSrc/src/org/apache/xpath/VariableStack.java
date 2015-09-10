/*   1:    */ package org.apache.xpath;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*   5:    */ import org.apache.xalan.templates.ElemVariable;
/*   6:    */ import org.apache.xalan.templates.Stylesheet;
/*   7:    */ import org.apache.xalan.templates.StylesheetRoot;
/*   8:    */ import org.apache.xml.utils.PrefixResolver;
/*   9:    */ import org.apache.xml.utils.QName;
/*  10:    */ import org.apache.xpath.objects.XObject;
/*  11:    */ import org.apache.xpath.res.XPATHMessages;
/*  12:    */ 
/*  13:    */ public class VariableStack
/*  14:    */   implements Cloneable
/*  15:    */ {
/*  16:    */   public static final int CLEARLIMITATION = 1024;
/*  17:    */   XObject[] _stackFrames;
/*  18:    */   int _frameTop;
/*  19:    */   private int _currentFrameBottom;
/*  20:    */   int[] _links;
/*  21:    */   int _linksTop;
/*  22:    */   
/*  23:    */   public VariableStack()
/*  24:    */   {
/*  25: 49 */     reset();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public VariableStack(int initStackSize)
/*  29:    */   {
/*  30: 60 */     reset(initStackSize, initStackSize * 2);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public synchronized Object clone()
/*  34:    */     throws CloneNotSupportedException
/*  35:    */   {
/*  36: 73 */     VariableStack vs = (VariableStack)super.clone();
/*  37:    */     
/*  38:    */ 
/*  39: 76 */     vs._stackFrames = ((XObject[])this._stackFrames.clone());
/*  40: 77 */     vs._links = ((int[])this._links.clone());
/*  41:    */     
/*  42: 79 */     return vs;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public XObject elementAt(int i)
/*  46:    */   {
/*  47:122 */     return this._stackFrames[i];
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int size()
/*  51:    */   {
/*  52:132 */     return this._frameTop;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void reset()
/*  56:    */   {
/*  57:143 */     int linksSize = this._links == null ? 4096 : this._links.length;
/*  58:    */     
/*  59:145 */     int varArraySize = this._stackFrames == null ? 8192 : this._stackFrames.length;
/*  60:    */     
/*  61:147 */     reset(linksSize, varArraySize);
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void reset(int linksSize, int varArraySize)
/*  65:    */   {
/*  66:156 */     this._frameTop = 0;
/*  67:157 */     this._linksTop = 0;
/*  68:160 */     if (this._links == null) {
/*  69:161 */       this._links = new int[linksSize];
/*  70:    */     }
/*  71:167 */     this._links[(this._linksTop++)] = 0;
/*  72:    */     
/*  73:    */ 
/*  74:170 */     this._stackFrames = new XObject[varArraySize];
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setStackFrame(int sf)
/*  78:    */   {
/*  79:180 */     this._currentFrameBottom = sf;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getStackFrame()
/*  83:    */   {
/*  84:192 */     return this._currentFrameBottom;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int link(int size)
/*  88:    */   {
/*  89:213 */     this._currentFrameBottom = this._frameTop;
/*  90:214 */     this._frameTop += size;
/*  91:216 */     if (this._frameTop >= this._stackFrames.length)
/*  92:    */     {
/*  93:218 */       XObject[] newsf = new XObject[this._stackFrames.length + 4096 + size];
/*  94:    */       
/*  95:220 */       System.arraycopy(this._stackFrames, 0, newsf, 0, this._stackFrames.length);
/*  96:    */       
/*  97:222 */       this._stackFrames = newsf;
/*  98:    */     }
/*  99:225 */     if (this._linksTop + 1 >= this._links.length)
/* 100:    */     {
/* 101:227 */       int[] newlinks = new int[this._links.length + 2048];
/* 102:    */       
/* 103:229 */       System.arraycopy(this._links, 0, newlinks, 0, this._links.length);
/* 104:    */       
/* 105:231 */       this._links = newlinks;
/* 106:    */     }
/* 107:234 */     this._links[(this._linksTop++)] = this._currentFrameBottom;
/* 108:    */     
/* 109:236 */     return this._currentFrameBottom;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void unlink()
/* 113:    */   {
/* 114:245 */     this._frameTop = this._links[(--this._linksTop)];
/* 115:246 */     this._currentFrameBottom = this._links[(this._linksTop - 1)];
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void unlink(int currentFrame)
/* 119:    */   {
/* 120:257 */     this._frameTop = this._links[(--this._linksTop)];
/* 121:258 */     this._currentFrameBottom = currentFrame;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setLocalVariable(int index, XObject val)
/* 125:    */   {
/* 126:272 */     this._stackFrames[(index + this._currentFrameBottom)] = val;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setLocalVariable(int index, XObject val, int stackFrame)
/* 130:    */   {
/* 131:287 */     this._stackFrames[(index + stackFrame)] = val;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public XObject getLocalVariable(XPathContext xctxt, int index)
/* 135:    */     throws TransformerException
/* 136:    */   {
/* 137:308 */     index += this._currentFrameBottom;
/* 138:    */     
/* 139:310 */     XObject val = this._stackFrames[index];
/* 140:312 */     if (null == val) {
/* 141:313 */       throw new TransformerException(XPATHMessages.createXPATHMessage("ER_VARIABLE_ACCESSED_BEFORE_BIND", null), xctxt.getSAXLocator());
/* 142:    */     }
/* 143:318 */     if (val.getType() == 600) {
/* 144:319 */       return this._stackFrames[index] =  = val.execute(xctxt);
/* 145:    */     }
/* 146:321 */     return val;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public XObject getLocalVariable(int index, int frame)
/* 150:    */     throws TransformerException
/* 151:    */   {
/* 152:340 */     index += frame;
/* 153:    */     
/* 154:342 */     XObject val = this._stackFrames[index];
/* 155:    */     
/* 156:344 */     return val;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public XObject getLocalVariable(XPathContext xctxt, int index, boolean destructiveOK)
/* 160:    */     throws TransformerException
/* 161:    */   {
/* 162:365 */     index += this._currentFrameBottom;
/* 163:    */     
/* 164:367 */     XObject val = this._stackFrames[index];
/* 165:369 */     if (null == val) {
/* 166:370 */       throw new TransformerException(XPATHMessages.createXPATHMessage("ER_VARIABLE_ACCESSED_BEFORE_BIND", null), xctxt.getSAXLocator());
/* 167:    */     }
/* 168:375 */     if (val.getType() == 600) {
/* 169:376 */       return this._stackFrames[index] =  = val.execute(xctxt);
/* 170:    */     }
/* 171:378 */     return destructiveOK ? val : val.getFresh();
/* 172:    */   }
/* 173:    */   
/* 174:    */   public boolean isLocalSet(int index)
/* 175:    */     throws TransformerException
/* 176:    */   {
/* 177:393 */     return this._stackFrames[(index + this._currentFrameBottom)] != null;
/* 178:    */   }
/* 179:    */   
/* 180:397 */   private static XObject[] m_nulls = new XObject[1024];
/* 181:    */   
/* 182:    */   public void clearLocalSlots(int start, int len)
/* 183:    */   {
/* 184:411 */     start += this._currentFrameBottom;
/* 185:    */     
/* 186:413 */     System.arraycopy(m_nulls, 0, this._stackFrames, start, len);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setGlobalVariable(int index, XObject val)
/* 190:    */   {
/* 191:427 */     this._stackFrames[index] = val;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public XObject getGlobalVariable(XPathContext xctxt, int index)
/* 195:    */     throws TransformerException
/* 196:    */   {
/* 197:448 */     XObject val = this._stackFrames[index];
/* 198:451 */     if (val.getType() == 600) {
/* 199:452 */       return this._stackFrames[index] =  = val.execute(xctxt);
/* 200:    */     }
/* 201:454 */     return val;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public XObject getGlobalVariable(XPathContext xctxt, int index, boolean destructiveOK)
/* 205:    */     throws TransformerException
/* 206:    */   {
/* 207:475 */     XObject val = this._stackFrames[index];
/* 208:478 */     if (val.getType() == 600) {
/* 209:479 */       return this._stackFrames[index] =  = val.execute(xctxt);
/* 210:    */     }
/* 211:481 */     return destructiveOK ? val : val.getFresh();
/* 212:    */   }
/* 213:    */   
/* 214:    */   public XObject getVariableOrParam(XPathContext xctxt, QName qname)
/* 215:    */     throws TransformerException
/* 216:    */   {
/* 217:502 */     PrefixResolver prefixResolver = xctxt.getNamespaceContext();
/* 218:511 */     if ((prefixResolver instanceof ElemTemplateElement))
/* 219:    */     {
/* 220:516 */       ElemTemplateElement prev = (ElemTemplateElement)prefixResolver;
/* 221:519 */       if (!(prev instanceof Stylesheet)) {
/* 222:521 */         while (!(prev.getParentNode() instanceof Stylesheet))
/* 223:    */         {
/* 224:523 */           ElemTemplateElement savedprev = prev;
/* 225:525 */           while (null != (prev = prev.getPreviousSiblingElem())) {
/* 226:527 */             if ((prev instanceof ElemVariable))
/* 227:    */             {
/* 228:529 */               vvar = (ElemVariable)prev;
/* 229:531 */               if (vvar.getName().equals(qname)) {
/* 230:532 */                 return getLocalVariable(xctxt, vvar.getIndex());
/* 231:    */               }
/* 232:    */             }
/* 233:    */           }
/* 234:535 */           prev = savedprev.getParentElem();
/* 235:    */         }
/* 236:    */       }
/* 237:539 */       ElemVariable vvar = prev.getStylesheetRoot().getVariableOrParamComposed(qname);
/* 238:540 */       if (null != vvar) {
/* 239:541 */         return getGlobalVariable(xctxt, vvar.getIndex());
/* 240:    */       }
/* 241:    */     }
/* 242:544 */     throw new TransformerException(XPATHMessages.createXPATHMessage("ER_VAR_NOT_RESOLVABLE", new Object[] { qname.toString() }));
/* 243:    */   }
/* 244:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.VariableStack
 * JD-Core Version:    0.7.0.1
 */