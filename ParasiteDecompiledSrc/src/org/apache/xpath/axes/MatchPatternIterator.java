/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.dtm.DTM;
/*   5:    */ import org.apache.xml.dtm.DTMAxisTraverser;
/*   6:    */ import org.apache.xpath.VariableStack;
/*   7:    */ import org.apache.xpath.XPathContext;
/*   8:    */ import org.apache.xpath.compiler.Compiler;
/*   9:    */ import org.apache.xpath.compiler.OpMap;
/*  10:    */ import org.apache.xpath.objects.XObject;
/*  11:    */ import org.apache.xpath.patterns.NodeTest;
/*  12:    */ import org.apache.xpath.patterns.StepPattern;
/*  13:    */ 
/*  14:    */ public class MatchPatternIterator
/*  15:    */   extends LocPathIterator
/*  16:    */ {
/*  17:    */   static final long serialVersionUID = -5201153767396296474L;
/*  18:    */   protected StepPattern m_pattern;
/*  19: 50 */   protected int m_superAxis = -1;
/*  20:    */   protected DTMAxisTraverser m_traverser;
/*  21:    */   private static final boolean DEBUG = false;
/*  22:    */   
/*  23:    */   MatchPatternIterator(Compiler compiler, int opPos, int analysis)
/*  24:    */     throws TransformerException
/*  25:    */   {
/*  26: 78 */     super(compiler, opPos, analysis, false);
/*  27:    */     
/*  28: 80 */     int firstStepPos = OpMap.getFirstChildPos(opPos);
/*  29:    */     
/*  30: 82 */     this.m_pattern = WalkerFactory.loadSteps(this, compiler, firstStepPos, 0);
/*  31:    */     
/*  32: 84 */     boolean fromRoot = false;
/*  33: 85 */     boolean walkBack = false;
/*  34: 86 */     boolean walkDescendants = false;
/*  35: 87 */     boolean walkAttributes = false;
/*  36: 89 */     if (0 != (analysis & 0x28000000)) {
/*  37: 91 */       fromRoot = true;
/*  38:    */     }
/*  39: 93 */     if (0 != (analysis & 0x5D86000)) {
/*  40:101 */       walkBack = true;
/*  41:    */     }
/*  42:103 */     if (0 != (analysis & 0x70000)) {
/*  43:107 */       walkDescendants = true;
/*  44:    */     }
/*  45:109 */     if (0 != (analysis & 0x208000)) {
/*  46:111 */       walkAttributes = true;
/*  47:    */     }
/*  48:119 */     if ((fromRoot) || (walkBack))
/*  49:    */     {
/*  50:121 */       if (walkAttributes) {
/*  51:123 */         this.m_superAxis = 16;
/*  52:    */       } else {
/*  53:127 */         this.m_superAxis = 17;
/*  54:    */       }
/*  55:    */     }
/*  56:130 */     else if (walkDescendants)
/*  57:    */     {
/*  58:132 */       if (walkAttributes) {
/*  59:134 */         this.m_superAxis = 14;
/*  60:    */       } else {
/*  61:138 */         this.m_superAxis = 5;
/*  62:    */       }
/*  63:    */     }
/*  64:    */     else {
/*  65:143 */       this.m_superAxis = 16;
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setRoot(int context, Object environment)
/*  70:    */   {
/*  71:162 */     super.setRoot(context, environment);
/*  72:163 */     this.m_traverser = this.m_cdtm.getAxisTraverser(this.m_superAxis);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void detach()
/*  76:    */   {
/*  77:175 */     if (this.m_allowDetach)
/*  78:    */     {
/*  79:177 */       this.m_traverser = null;
/*  80:    */       
/*  81:    */ 
/*  82:180 */       super.detach();
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected int getNextNode()
/*  87:    */   {
/*  88:190 */     this.m_lastFetched = (-1 == this.m_lastFetched ? this.m_traverser.first(this.m_context) : this.m_traverser.next(this.m_context, this.m_lastFetched));
/*  89:    */     
/*  90:    */ 
/*  91:193 */     return this.m_lastFetched;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int nextNode()
/*  95:    */   {
/*  96:205 */     if (this.m_foundLast) {
/*  97:206 */       return -1;
/*  98:    */     }
/*  99:    */     VariableStack vars;
/* 100:    */     int savedStart;
/* 101:212 */     if (-1 != this.m_stackFrame)
/* 102:    */     {
/* 103:214 */       vars = this.m_execContext.getVarStack();
/* 104:    */       
/* 105:    */ 
/* 106:217 */       savedStart = vars.getStackFrame();
/* 107:    */       
/* 108:219 */       vars.setStackFrame(this.m_stackFrame);
/* 109:    */     }
/* 110:    */     else
/* 111:    */     {
/* 112:224 */       vars = null;
/* 113:225 */       savedStart = 0;
/* 114:    */     }
/* 115:    */     try
/* 116:    */     {
/* 117:    */       int next;
/* 118:    */       do
/* 119:    */       {
/* 120:235 */         next = getNextNode();
/* 121:237 */       } while ((-1 != next) && 
/* 122:    */       
/* 123:239 */         (1 != acceptNode(next, this.m_execContext)) && 
/* 124:    */         
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:247 */         (next != -1));
/* 132:    */       int i;
/* 133:249 */       if (-1 != next)
/* 134:    */       {
/* 135:256 */         incrementCurrentPos();
/* 136:    */         
/* 137:258 */         return next;
/* 138:    */       }
/* 139:262 */       this.m_foundLast = true;
/* 140:    */       
/* 141:264 */       return -1;
/* 142:    */     }
/* 143:    */     finally
/* 144:    */     {
/* 145:269 */       if (-1 != this.m_stackFrame) {
/* 146:272 */         vars.setStackFrame(savedStart);
/* 147:    */       }
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public short acceptNode(int n, XPathContext xctxt)
/* 152:    */   {
/* 153:    */     try
/* 154:    */     {
/* 155:292 */       xctxt.pushCurrentNode(n);
/* 156:293 */       xctxt.pushIteratorRoot(this.m_context);
/* 157:    */       
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:304 */       XObject score = this.m_pattern.execute(xctxt);
/* 168:    */       
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:314 */       return score == NodeTest.SCORE_NONE ? 3 : 1;
/* 178:    */     }
/* 179:    */     catch (TransformerException se)
/* 180:    */     {
/* 181:321 */       throw new RuntimeException(se.getMessage());
/* 182:    */     }
/* 183:    */     finally
/* 184:    */     {
/* 185:325 */       xctxt.popCurrentNode();
/* 186:326 */       xctxt.popIteratorRoot();
/* 187:    */     }
/* 188:    */   }
/* 189:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.MatchPatternIterator
 * JD-Core Version:    0.7.0.1
 */