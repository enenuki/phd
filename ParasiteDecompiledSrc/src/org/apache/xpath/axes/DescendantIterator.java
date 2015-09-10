/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.dtm.DTM;
/*   5:    */ import org.apache.xml.dtm.DTMAxisTraverser;
/*   6:    */ import org.apache.xml.dtm.DTMIterator;
/*   7:    */ import org.apache.xpath.Expression;
/*   8:    */ import org.apache.xpath.VariableStack;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.apache.xpath.compiler.Compiler;
/*  11:    */ import org.apache.xpath.compiler.OpMap;
/*  12:    */ import org.apache.xpath.patterns.NodeTest;
/*  13:    */ 
/*  14:    */ public class DescendantIterator
/*  15:    */   extends LocPathIterator
/*  16:    */ {
/*  17:    */   static final long serialVersionUID = -1190338607743976938L;
/*  18:    */   protected transient DTMAxisTraverser m_traverser;
/*  19:    */   protected int m_axis;
/*  20:    */   protected int m_extendedTypeID;
/*  21:    */   
/*  22:    */   DescendantIterator(Compiler compiler, int opPos, int analysis)
/*  23:    */     throws TransformerException
/*  24:    */   {
/*  25: 57 */     super(compiler, opPos, analysis, false);
/*  26:    */     
/*  27: 59 */     int firstStepPos = OpMap.getFirstChildPos(opPos);
/*  28: 60 */     int stepType = compiler.getOp(firstStepPos);
/*  29:    */     
/*  30: 62 */     boolean orSelf = 42 == stepType;
/*  31: 63 */     boolean fromRoot = false;
/*  32: 64 */     if (48 == stepType)
/*  33:    */     {
/*  34: 66 */       orSelf = true;
/*  35:    */     }
/*  36: 69 */     else if (50 == stepType)
/*  37:    */     {
/*  38: 71 */       fromRoot = true;
/*  39:    */       
/*  40: 73 */       int nextStepPos = compiler.getNextStepPos(firstStepPos);
/*  41: 74 */       if (compiler.getOp(nextStepPos) == 42) {
/*  42: 75 */         orSelf = true;
/*  43:    */       }
/*  44:    */     }
/*  45: 80 */     int nextStepPos = firstStepPos;
/*  46:    */     for (;;)
/*  47:    */     {
/*  48: 83 */       nextStepPos = compiler.getNextStepPos(nextStepPos);
/*  49: 84 */       if (nextStepPos <= 0) {
/*  50:    */         break;
/*  51:    */       }
/*  52: 86 */       int stepOp = compiler.getOp(nextStepPos);
/*  53: 87 */       if (-1 == stepOp) {
/*  54:    */         break;
/*  55:    */       }
/*  56: 88 */       firstStepPos = nextStepPos;
/*  57:    */     }
/*  58: 98 */     if ((analysis & 0x10000) != 0) {
/*  59: 99 */       orSelf = false;
/*  60:    */     }
/*  61:101 */     if (fromRoot)
/*  62:    */     {
/*  63:103 */       if (orSelf) {
/*  64:104 */         this.m_axis = 18;
/*  65:    */       } else {
/*  66:106 */         this.m_axis = 17;
/*  67:    */       }
/*  68:    */     }
/*  69:108 */     else if (orSelf) {
/*  70:109 */       this.m_axis = 5;
/*  71:    */     } else {
/*  72:111 */       this.m_axis = 4;
/*  73:    */     }
/*  74:113 */     int whatToShow = compiler.getWhatToShow(firstStepPos);
/*  75:115 */     if ((0 == (whatToShow & 0x43)) || (whatToShow == -1)) {
/*  76:119 */       initNodeTest(whatToShow);
/*  77:    */     } else {
/*  78:122 */       initNodeTest(whatToShow, compiler.getStepNS(firstStepPos), compiler.getStepLocalName(firstStepPos));
/*  79:    */     }
/*  80:125 */     initPredicateInfo(compiler, firstStepPos);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public DescendantIterator()
/*  84:    */   {
/*  85:134 */     super(null);
/*  86:135 */     this.m_axis = 18;
/*  87:136 */     int whatToShow = -1;
/*  88:137 */     initNodeTest(whatToShow);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public DTMIterator cloneWithReset()
/*  92:    */     throws CloneNotSupportedException
/*  93:    */   {
/*  94:152 */     DescendantIterator clone = (DescendantIterator)super.cloneWithReset();
/*  95:153 */     clone.m_traverser = this.m_traverser;
/*  96:    */     
/*  97:155 */     clone.resetProximityPositions();
/*  98:    */     
/*  99:157 */     return clone;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int nextNode()
/* 103:    */   {
/* 104:174 */     if (this.m_foundLast) {
/* 105:175 */       return -1;
/* 106:    */     }
/* 107:177 */     if (-1 == this.m_lastFetched) {
/* 108:179 */       resetProximityPositions();
/* 109:    */     }
/* 110:    */     VariableStack vars;
/* 111:    */     int savedStart;
/* 112:186 */     if (-1 != this.m_stackFrame)
/* 113:    */     {
/* 114:188 */       vars = this.m_execContext.getVarStack();
/* 115:    */       
/* 116:    */ 
/* 117:191 */       savedStart = vars.getStackFrame();
/* 118:    */       
/* 119:193 */       vars.setStackFrame(this.m_stackFrame);
/* 120:    */     }
/* 121:    */     else
/* 122:    */     {
/* 123:198 */       vars = null;
/* 124:199 */       savedStart = 0;
/* 125:    */     }
/* 126:    */     try
/* 127:    */     {
/* 128:    */       int next;
/* 129:    */       do
/* 130:    */       {
/* 131:206 */         if (0 == this.m_extendedTypeID) {
/* 132:208 */           next = this.m_lastFetched = -1 == this.m_lastFetched ? this.m_traverser.first(this.m_context) : this.m_traverser.next(this.m_context, this.m_lastFetched);
/* 133:    */         } else {
/* 134:214 */           next = this.m_lastFetched = -1 == this.m_lastFetched ? this.m_traverser.first(this.m_context, this.m_extendedTypeID) : this.m_traverser.next(this.m_context, this.m_lastFetched, this.m_extendedTypeID);
/* 135:    */         }
/* 136:220 */       } while ((-1 != next) && 
/* 137:    */       
/* 138:222 */         (1 != acceptNode(next)) && 
/* 139:    */         
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:230 */         (next != -1));
/* 147:    */       int i;
/* 148:232 */       if (-1 != next)
/* 149:    */       {
/* 150:234 */         this.m_pos += 1;
/* 151:235 */         return next;
/* 152:    */       }
/* 153:239 */       this.m_foundLast = true;
/* 154:    */       
/* 155:241 */       return -1;
/* 156:    */     }
/* 157:    */     finally
/* 158:    */     {
/* 159:246 */       if (-1 != this.m_stackFrame) {
/* 160:249 */         vars.setStackFrame(savedStart);
/* 161:    */       }
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setRoot(int context, Object environment)
/* 166:    */   {
/* 167:263 */     super.setRoot(context, environment);
/* 168:264 */     this.m_traverser = this.m_cdtm.getAxisTraverser(this.m_axis);
/* 169:    */     
/* 170:266 */     String localName = getLocalName();
/* 171:267 */     String namespace = getNamespace();
/* 172:268 */     int what = this.m_whatToShow;
/* 173:271 */     if ((-1 == what) || ("*".equals(localName)) || ("*".equals(namespace)))
/* 174:    */     {
/* 175:275 */       this.m_extendedTypeID = 0;
/* 176:    */     }
/* 177:    */     else
/* 178:    */     {
/* 179:279 */       int type = NodeTest.getNodeTypeTest(what);
/* 180:280 */       this.m_extendedTypeID = this.m_cdtm.getExpandedTypeID(namespace, localName, type);
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public int asNode(XPathContext xctxt)
/* 185:    */     throws TransformerException
/* 186:    */   {
/* 187:296 */     if (getPredicateCount() > 0) {
/* 188:297 */       return super.asNode(xctxt);
/* 189:    */     }
/* 190:299 */     int current = xctxt.getCurrentNode();
/* 191:    */     
/* 192:301 */     DTM dtm = xctxt.getDTM(current);
/* 193:302 */     DTMAxisTraverser traverser = dtm.getAxisTraverser(this.m_axis);
/* 194:    */     
/* 195:304 */     String localName = getLocalName();
/* 196:305 */     String namespace = getNamespace();
/* 197:306 */     int what = this.m_whatToShow;
/* 198:312 */     if ((-1 == what) || (localName == "*") || (namespace == "*")) {
/* 199:316 */       return traverser.first(current);
/* 200:    */     }
/* 201:320 */     int type = NodeTest.getNodeTypeTest(what);
/* 202:321 */     int extendedType = dtm.getExpandedTypeID(namespace, localName, type);
/* 203:322 */     return traverser.first(current, extendedType);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void detach()
/* 207:    */   {
/* 208:335 */     if (this.m_allowDetach)
/* 209:    */     {
/* 210:336 */       this.m_traverser = null;
/* 211:337 */       this.m_extendedTypeID = 0;
/* 212:    */       
/* 213:    */ 
/* 214:340 */       super.detach();
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public int getAxis()
/* 219:    */   {
/* 220:352 */     return this.m_axis;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public boolean deepEquals(Expression expr)
/* 224:    */   {
/* 225:370 */     if (!super.deepEquals(expr)) {
/* 226:371 */       return false;
/* 227:    */     }
/* 228:373 */     if (this.m_axis != ((DescendantIterator)expr).m_axis) {
/* 229:374 */       return false;
/* 230:    */     }
/* 231:376 */     return true;
/* 232:    */   }
/* 233:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.DescendantIterator
 * JD-Core Version:    0.7.0.1
 */