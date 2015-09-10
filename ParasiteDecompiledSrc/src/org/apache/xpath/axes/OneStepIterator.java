/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.dtm.DTM;
/*   5:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   6:    */ import org.apache.xml.dtm.DTMIterator;
/*   7:    */ import org.apache.xpath.Expression;
/*   8:    */ import org.apache.xpath.XPathContext;
/*   9:    */ import org.apache.xpath.compiler.Compiler;
/*  10:    */ import org.apache.xpath.compiler.OpMap;
/*  11:    */ import org.apache.xpath.patterns.NodeTest;
/*  12:    */ 
/*  13:    */ public class OneStepIterator
/*  14:    */   extends ChildTestIterator
/*  15:    */ {
/*  16:    */   static final long serialVersionUID = 4623710779664998283L;
/*  17: 42 */   protected int m_axis = -1;
/*  18:    */   protected DTMAxisIterator m_iterator;
/*  19:    */   
/*  20:    */   OneStepIterator(Compiler compiler, int opPos, int analysis)
/*  21:    */     throws TransformerException
/*  22:    */   {
/*  23: 59 */     super(compiler, opPos, analysis);
/*  24: 60 */     int firstStepPos = OpMap.getFirstChildPos(opPos);
/*  25:    */     
/*  26: 62 */     this.m_axis = WalkerFactory.getAxisFromStep(compiler, firstStepPos);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public OneStepIterator(DTMAxisIterator iterator, int axis)
/*  30:    */     throws TransformerException
/*  31:    */   {
/*  32: 78 */     super(null);
/*  33:    */     
/*  34: 80 */     this.m_iterator = iterator;
/*  35: 81 */     this.m_axis = axis;
/*  36: 82 */     int whatToShow = -1;
/*  37: 83 */     initNodeTest(whatToShow);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setRoot(int context, Object environment)
/*  41:    */   {
/*  42: 95 */     super.setRoot(context, environment);
/*  43: 96 */     if (this.m_axis > -1) {
/*  44: 97 */       this.m_iterator = this.m_cdtm.getAxisIterator(this.m_axis);
/*  45:    */     }
/*  46: 98 */     this.m_iterator.setStartNode(this.m_context);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void detach()
/*  50:    */   {
/*  51:110 */     if (this.m_allowDetach)
/*  52:    */     {
/*  53:112 */       if (this.m_axis > -1) {
/*  54:113 */         this.m_iterator = null;
/*  55:    */       }
/*  56:116 */       super.detach();
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected int getNextNode()
/*  61:    */   {
/*  62:125 */     return this.m_lastFetched = this.m_iterator.next();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Object clone()
/*  66:    */     throws CloneNotSupportedException
/*  67:    */   {
/*  68:139 */     OneStepIterator clone = (OneStepIterator)super.clone();
/*  69:141 */     if (this.m_iterator != null) {
/*  70:143 */       clone.m_iterator = this.m_iterator.cloneIterator();
/*  71:    */     }
/*  72:145 */     return clone;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public DTMIterator cloneWithReset()
/*  76:    */     throws CloneNotSupportedException
/*  77:    */   {
/*  78:159 */     OneStepIterator clone = (OneStepIterator)super.cloneWithReset();
/*  79:160 */     clone.m_iterator = this.m_iterator;
/*  80:    */     
/*  81:162 */     return clone;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean isReverseAxes()
/*  85:    */   {
/*  86:174 */     return this.m_iterator.isReverse();
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected int getProximityPosition(int predicateIndex)
/*  90:    */   {
/*  91:190 */     if (!isReverseAxes()) {
/*  92:191 */       return super.getProximityPosition(predicateIndex);
/*  93:    */     }
/*  94:196 */     if (predicateIndex < 0) {
/*  95:197 */       return -1;
/*  96:    */     }
/*  97:199 */     if (this.m_proximityPositions[predicateIndex] <= 0)
/*  98:    */     {
/*  99:201 */       XPathContext xctxt = getXPathContext();
/* 100:    */       try
/* 101:    */       {
/* 102:204 */         OneStepIterator clone = (OneStepIterator)clone();
/* 103:    */         
/* 104:206 */         int root = getRoot();
/* 105:207 */         xctxt.pushCurrentNode(root);
/* 106:208 */         clone.setRoot(root, xctxt);
/* 107:    */         
/* 108:    */ 
/* 109:211 */         clone.m_predCount = predicateIndex;
/* 110:    */         
/* 111:    */ 
/* 112:214 */         int count = 1;
/* 113:    */         int next;
/* 114:217 */         while (-1 != (next = clone.nextNode())) {
/* 115:219 */           count++;
/* 116:    */         }
/* 117:222 */         this.m_proximityPositions[predicateIndex] += count;
/* 118:    */       }
/* 119:    */       catch (CloneNotSupportedException cnse) {}finally
/* 120:    */       {
/* 121:231 */         xctxt.popCurrentNode();
/* 122:    */       }
/* 123:    */     }
/* 124:235 */     return this.m_proximityPositions[predicateIndex];
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getLength()
/* 128:    */   {
/* 129:246 */     if (!isReverseAxes()) {
/* 130:247 */       return super.getLength();
/* 131:    */     }
/* 132:250 */     boolean isPredicateTest = this == this.m_execContext.getSubContextList();
/* 133:    */     
/* 134:    */ 
/* 135:253 */     int predCount = getPredicateCount();
/* 136:258 */     if ((-1 != this.m_length) && (isPredicateTest) && (this.m_predicateIndex < 1)) {
/* 137:259 */       return this.m_length;
/* 138:    */     }
/* 139:261 */     int count = 0;
/* 140:    */     
/* 141:263 */     XPathContext xctxt = getXPathContext();
/* 142:    */     try
/* 143:    */     {
/* 144:266 */       OneStepIterator clone = (OneStepIterator)cloneWithReset();
/* 145:    */       
/* 146:268 */       int root = getRoot();
/* 147:269 */       xctxt.pushCurrentNode(root);
/* 148:270 */       clone.setRoot(root, xctxt);
/* 149:    */       
/* 150:272 */       clone.m_predCount = this.m_predicateIndex;
/* 151:    */       int next;
/* 152:276 */       while (-1 != (next = clone.nextNode())) {
/* 153:278 */         count++;
/* 154:    */       }
/* 155:    */     }
/* 156:    */     catch (CloneNotSupportedException cnse) {}finally
/* 157:    */     {
/* 158:287 */       xctxt.popCurrentNode();
/* 159:    */     }
/* 160:289 */     if ((isPredicateTest) && (this.m_predicateIndex < 1)) {
/* 161:290 */       this.m_length = count;
/* 162:    */     }
/* 163:292 */     return count;
/* 164:    */   }
/* 165:    */   
/* 166:    */   protected void countProximityPosition(int i)
/* 167:    */   {
/* 168:302 */     if (!isReverseAxes()) {
/* 169:303 */       super.countProximityPosition(i);
/* 170:304 */     } else if (i < this.m_proximityPositions.length) {
/* 171:305 */       this.m_proximityPositions[i] -= 1;
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void reset()
/* 176:    */   {
/* 177:314 */     super.reset();
/* 178:315 */     if (null != this.m_iterator) {
/* 179:316 */       this.m_iterator.reset();
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public int getAxis()
/* 184:    */   {
/* 185:327 */     return this.m_axis;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public boolean deepEquals(Expression expr)
/* 189:    */   {
/* 190:335 */     if (!super.deepEquals(expr)) {
/* 191:336 */       return false;
/* 192:    */     }
/* 193:338 */     if (this.m_axis != ((OneStepIterator)expr).m_axis) {
/* 194:339 */       return false;
/* 195:    */     }
/* 196:341 */     return true;
/* 197:    */   }
/* 198:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.OneStepIterator
 * JD-Core Version:    0.7.0.1
 */