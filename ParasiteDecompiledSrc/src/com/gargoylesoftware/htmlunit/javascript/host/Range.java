/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.DomDocumentFragment;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HTMLParser;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.impl.SimpleRange;
/*   8:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   9:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCollection;
/*  10:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDocument;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  14:    */ import org.apache.commons.collections.ListUtils;
/*  15:    */ import org.apache.commons.logging.Log;
/*  16:    */ import org.apache.commons.logging.LogFactory;
/*  17:    */ 
/*  18:    */ public class Range
/*  19:    */   extends SimpleScriptable
/*  20:    */ {
/*  21:    */   private Node startContainer_;
/*  22:    */   private Node endContainer_;
/*  23:    */   private int startOffset_;
/*  24:    */   private int endOffset_;
/*  25:    */   public static final short START_TO_START = 0;
/*  26:    */   public static final short START_TO_END = 1;
/*  27:    */   public static final short END_TO_END = 2;
/*  28:    */   public static final short END_TO_START = 3;
/*  29:    */   
/*  30:    */   public Range() {}
/*  31:    */   
/*  32:    */   public Range(HTMLDocument document)
/*  33:    */   {
/*  34: 72 */     this.startContainer_ = document;
/*  35: 73 */     this.endContainer_ = document;
/*  36:    */   }
/*  37:    */   
/*  38:    */   Range(org.w3c.dom.ranges.Range w3cRange)
/*  39:    */   {
/*  40: 77 */     DomNode domNodeStartContainer = (DomNode)w3cRange.getStartContainer();
/*  41: 78 */     this.startContainer_ = ((Node)domNodeStartContainer.getScriptObject());
/*  42: 79 */     this.startOffset_ = w3cRange.getStartOffset();
/*  43:    */     
/*  44: 81 */     DomNode domNodeEndContainer = (DomNode)w3cRange.getEndContainer();
/*  45: 82 */     this.endContainer_ = ((Node)domNodeEndContainer.getScriptObject());
/*  46: 83 */     this.endOffset_ = w3cRange.getEndOffset();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Object getDefaultValue(Class<?> hint)
/*  50:    */   {
/*  51: 91 */     return toW3C().toString();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object jsxGet_startContainer()
/*  55:    */   {
/*  56: 99 */     if (this.startContainer_ == null) {
/*  57:100 */       return Context.getUndefinedValue();
/*  58:    */     }
/*  59:102 */     return this.startContainer_;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Object jsxGet_endContainer()
/*  63:    */   {
/*  64:110 */     if (this.endContainer_ == null) {
/*  65:111 */       return Context.getUndefinedValue();
/*  66:    */     }
/*  67:113 */     return this.endContainer_;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int jsxGet_startOffset()
/*  71:    */   {
/*  72:121 */     return this.startOffset_;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int jsxGet_endOffset()
/*  76:    */   {
/*  77:129 */     return this.endOffset_;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void jsxFunction_setStart(Node refNode, int offset)
/*  81:    */   {
/*  82:138 */     if (refNode == null) {
/*  83:139 */       throw Context.reportRuntimeError("It is illegal to call Range.setStart() with a null node.");
/*  84:    */     }
/*  85:141 */     this.startContainer_ = refNode;
/*  86:142 */     this.startOffset_ = offset;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void jsxFunction_setStartAfter(Node refNode)
/*  90:    */   {
/*  91:150 */     if (refNode == null) {
/*  92:151 */       throw Context.reportRuntimeError("It is illegal to call Range.setStartAfter() with a null node.");
/*  93:    */     }
/*  94:153 */     this.startContainer_ = refNode.getParent();
/*  95:154 */     this.startOffset_ = (getPositionInContainer(refNode) + 1);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void jsxFunction_setStartBefore(Node refNode)
/*  99:    */   {
/* 100:162 */     if (refNode == null) {
/* 101:163 */       throw Context.reportRuntimeError("It is illegal to call Range.setStartBefore() with a null node.");
/* 102:    */     }
/* 103:165 */     this.startContainer_ = refNode.getParent();
/* 104:166 */     this.startOffset_ = getPositionInContainer(refNode);
/* 105:    */   }
/* 106:    */   
/* 107:    */   private int getPositionInContainer(Node refNode)
/* 108:    */   {
/* 109:170 */     int i = 0;
/* 110:171 */     Node node = refNode;
/* 111:172 */     while (node.jsxGet_previousSibling() != null)
/* 112:    */     {
/* 113:173 */       node = node.jsxGet_previousSibling();
/* 114:174 */       i++;
/* 115:    */     }
/* 116:176 */     return i;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean jsxGet_collapsed()
/* 120:    */   {
/* 121:184 */     return (this.startContainer_ == this.endContainer_) && (this.startOffset_ == this.endOffset_);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void jsxFunction_setEnd(Node refNode, int offset)
/* 125:    */   {
/* 126:193 */     if (refNode == null) {
/* 127:194 */       throw Context.reportRuntimeError("It is illegal to call Range.setEnd() with a null node.");
/* 128:    */     }
/* 129:196 */     this.endContainer_ = refNode;
/* 130:197 */     this.endOffset_ = offset;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void jsxFunction_setEndAfter(Node refNode)
/* 134:    */   {
/* 135:205 */     if (refNode == null) {
/* 136:206 */       throw Context.reportRuntimeError("It is illegal to call Range.setEndAfter() with a null node.");
/* 137:    */     }
/* 138:208 */     this.endContainer_ = refNode.getParent();
/* 139:209 */     this.endOffset_ = (getPositionInContainer(refNode) + 1);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void jsxFunction_setEndBefore(Node refNode)
/* 143:    */   {
/* 144:217 */     if (refNode == null) {
/* 145:218 */       throw Context.reportRuntimeError("It is illegal to call Range.setEndBefore() with a null node.");
/* 146:    */     }
/* 147:220 */     this.startContainer_ = refNode.getParent();
/* 148:221 */     this.startOffset_ = getPositionInContainer(refNode);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void jsxFunction_selectNodeContents(Node refNode)
/* 152:    */   {
/* 153:229 */     this.startContainer_ = refNode;
/* 154:230 */     this.startOffset_ = 0;
/* 155:231 */     this.endContainer_ = refNode;
/* 156:232 */     this.endOffset_ = refNode.jsxGet_childNodes().jsxGet_length();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void jsxFunction_selectNode(Node refNode)
/* 160:    */   {
/* 161:240 */     jsxFunction_setStartBefore(refNode);
/* 162:241 */     jsxFunction_setEndAfter(refNode);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void jsxFunction_collapse(boolean toStart)
/* 166:    */   {
/* 167:249 */     if (toStart)
/* 168:    */     {
/* 169:250 */       this.endContainer_ = this.startContainer_;
/* 170:251 */       this.endOffset_ = this.startOffset_;
/* 171:    */     }
/* 172:    */     else
/* 173:    */     {
/* 174:254 */       this.startContainer_ = this.endContainer_;
/* 175:255 */       this.startOffset_ = this.endOffset_;
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Object jsxGet_commonAncestorContainer()
/* 180:    */   {
/* 181:264 */     Node ancestor = getCommonAncestor();
/* 182:265 */     if (ancestor == null) {
/* 183:266 */       return Context.getUndefinedValue();
/* 184:    */     }
/* 185:268 */     return ancestor;
/* 186:    */   }
/* 187:    */   
/* 188:    */   private Node getCommonAncestor()
/* 189:    */   {
/* 190:277 */     List<Node> startAncestors = getAncestorsAndSelf(this.startContainer_);
/* 191:278 */     List<Node> endAncestors = getAncestorsAndSelf(this.endContainer_);
/* 192:279 */     List<Node> commonAncestors = ListUtils.intersection(startAncestors, endAncestors);
/* 193:280 */     if (commonAncestors.isEmpty()) {
/* 194:281 */       return null;
/* 195:    */     }
/* 196:283 */     return (Node)commonAncestors.get(commonAncestors.size() - 1);
/* 197:    */   }
/* 198:    */   
/* 199:    */   private List<Node> getAncestorsAndSelf(Node node)
/* 200:    */   {
/* 201:292 */     List<Node> ancestors = new ArrayList();
/* 202:293 */     Node ancestor = node;
/* 203:294 */     while (ancestor != null)
/* 204:    */     {
/* 205:295 */       ancestors.add(0, ancestor);
/* 206:296 */       ancestor = ancestor.getParent();
/* 207:    */     }
/* 208:298 */     return ancestors;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public Object jsxFunction_createContextualFragment(String valueAsString)
/* 212:    */   {
/* 213:308 */     SgmlPage page = this.startContainer_.getDomNodeOrDie().getPage();
/* 214:309 */     DomDocumentFragment fragment = new DomDocumentFragment(page);
/* 215:    */     try
/* 216:    */     {
/* 217:311 */       HTMLParser.parseFragment(fragment, this.startContainer_.getDomNodeOrDie(), valueAsString);
/* 218:    */     }
/* 219:    */     catch (Exception e)
/* 220:    */     {
/* 221:314 */       LogFactory.getLog(Range.class).error("Unexpected exception occurred in createContextualFragment", e);
/* 222:315 */       throw Context.reportRuntimeError("Unexpected exception occurred in createContextualFragment: " + e.getMessage());
/* 223:    */     }
/* 224:319 */     return fragment.getScriptObject();
/* 225:    */   }
/* 226:    */   
/* 227:    */   public Object jsxFunction_extractContents()
/* 228:    */   {
/* 229:327 */     return toW3C().extractContents().getScriptObject();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public SimpleRange toW3C()
/* 233:    */   {
/* 234:335 */     return new SimpleRange(this.startContainer_.getDomNodeOrNull(), this.startOffset_, this.endContainer_.getDomNodeOrDie(), this.endOffset_);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Object jsxFunction_compareBoundaryPoints(int how, Range sourceRange)
/* 238:    */   {
/* 239:    */     int containingMoficator;
/* 240:    */     Node nodeForThis;
/* 241:    */     int offsetForThis;
/* 242:    */     int containingMoficator;
/* 243:350 */     if ((0 == how) || (3 == how))
/* 244:    */     {
/* 245:351 */       Node nodeForThis = this.startContainer_;
/* 246:352 */       int offsetForThis = this.startOffset_;
/* 247:353 */       containingMoficator = 1;
/* 248:    */     }
/* 249:    */     else
/* 250:    */     {
/* 251:356 */       nodeForThis = this.endContainer_;
/* 252:357 */       offsetForThis = this.endOffset_;
/* 253:358 */       containingMoficator = -1;
/* 254:    */     }
/* 255:    */     int offsetForOther;
/* 256:    */     Node nodeForOther;
/* 257:    */     int offsetForOther;
/* 258:363 */     if ((1 == how) || (0 == how))
/* 259:    */     {
/* 260:364 */       Node nodeForOther = sourceRange.startContainer_;
/* 261:365 */       offsetForOther = sourceRange.startOffset_;
/* 262:    */     }
/* 263:    */     else
/* 264:    */     {
/* 265:368 */       nodeForOther = sourceRange.endContainer_;
/* 266:369 */       offsetForOther = sourceRange.endOffset_;
/* 267:    */     }
/* 268:372 */     if (nodeForThis == nodeForOther)
/* 269:    */     {
/* 270:373 */       if (offsetForThis < offsetForOther) {
/* 271:374 */         return Integer.valueOf(-1);
/* 272:    */       }
/* 273:376 */       if (offsetForThis < offsetForOther) {
/* 274:377 */         return Integer.valueOf(1);
/* 275:    */       }
/* 276:379 */       return Integer.valueOf(0);
/* 277:    */     }
/* 278:382 */     byte nodeComparision = (byte)nodeForThis.jsxFunction_compareDocumentPosition(nodeForOther);
/* 279:383 */     if ((nodeComparision & 0x10) != 0) {
/* 280:384 */       return Integer.valueOf(-1 * containingMoficator);
/* 281:    */     }
/* 282:386 */     if ((nodeComparision & 0x2) != 0) {
/* 283:387 */       return Integer.valueOf(-1);
/* 284:    */     }
/* 285:390 */     return Integer.valueOf(1);
/* 286:    */   }
/* 287:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Range
 * JD-Core Version:    0.7.0.1
 */