/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xml.utils.PrefixResolver;
/*   6:    */ import org.apache.xpath.Expression;
/*   7:    */ import org.apache.xpath.ExpressionOwner;
/*   8:    */ import org.apache.xpath.VariableStack;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.apache.xpath.XPathVisitor;
/*  11:    */ import org.apache.xpath.compiler.Compiler;
/*  12:    */ import org.apache.xpath.compiler.OpMap;
/*  13:    */ 
/*  14:    */ public class WalkingIterator
/*  15:    */   extends LocPathIterator
/*  16:    */   implements ExpressionOwner
/*  17:    */ {
/*  18:    */   static final long serialVersionUID = 9110225941815665906L;
/*  19:    */   protected AxesWalker m_lastUsedWalker;
/*  20:    */   protected AxesWalker m_firstWalker;
/*  21:    */   
/*  22:    */   WalkingIterator(Compiler compiler, int opPos, int analysis, boolean shouldLoadWalkers)
/*  23:    */     throws TransformerException
/*  24:    */   {
/*  25: 58 */     super(compiler, opPos, analysis, shouldLoadWalkers);
/*  26:    */     
/*  27: 60 */     int firstStepPos = OpMap.getFirstChildPos(opPos);
/*  28: 62 */     if (shouldLoadWalkers)
/*  29:    */     {
/*  30: 64 */       this.m_firstWalker = WalkerFactory.loadWalkers(this, compiler, firstStepPos, 0);
/*  31: 65 */       this.m_lastUsedWalker = this.m_firstWalker;
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public WalkingIterator(PrefixResolver nscontext)
/*  36:    */   {
/*  37: 78 */     super(nscontext);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getAnalysisBits()
/*  41:    */   {
/*  42: 88 */     int bits = 0;
/*  43: 89 */     if (null != this.m_firstWalker)
/*  44:    */     {
/*  45: 91 */       AxesWalker walker = this.m_firstWalker;
/*  46: 93 */       while (null != walker)
/*  47:    */       {
/*  48: 95 */         int bit = walker.getAnalysisBits();
/*  49: 96 */         bits |= bit;
/*  50: 97 */         walker = walker.getNextWalker();
/*  51:    */       }
/*  52:    */     }
/*  53:100 */     return bits;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Object clone()
/*  57:    */     throws CloneNotSupportedException
/*  58:    */   {
/*  59:114 */     WalkingIterator clone = (WalkingIterator)super.clone();
/*  60:118 */     if (null != this.m_firstWalker) {
/*  61:120 */       clone.m_firstWalker = this.m_firstWalker.cloneDeep(clone, null);
/*  62:    */     }
/*  63:123 */     return clone;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void reset()
/*  67:    */   {
/*  68:132 */     super.reset();
/*  69:134 */     if (null != this.m_firstWalker)
/*  70:    */     {
/*  71:136 */       this.m_lastUsedWalker = this.m_firstWalker;
/*  72:    */       
/*  73:138 */       this.m_firstWalker.setRoot(this.m_context);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setRoot(int context, Object environment)
/*  78:    */   {
/*  79:153 */     super.setRoot(context, environment);
/*  80:155 */     if (null != this.m_firstWalker)
/*  81:    */     {
/*  82:157 */       this.m_firstWalker.setRoot(context);
/*  83:158 */       this.m_lastUsedWalker = this.m_firstWalker;
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int nextNode()
/*  88:    */   {
/*  89:171 */     if (this.m_foundLast) {
/*  90:172 */       return -1;
/*  91:    */     }
/*  92:182 */     if (-1 == this.m_stackFrame) {
/*  93:184 */       return returnNextNode(this.m_firstWalker.nextNode());
/*  94:    */     }
/*  95:188 */     VariableStack vars = this.m_execContext.getVarStack();
/*  96:    */     
/*  97:    */ 
/*  98:191 */     int savedStart = vars.getStackFrame();
/*  99:    */     
/* 100:193 */     vars.setStackFrame(this.m_stackFrame);
/* 101:    */     
/* 102:195 */     int n = returnNextNode(this.m_firstWalker.nextNode());
/* 103:    */     
/* 104:    */ 
/* 105:198 */     vars.setStackFrame(savedStart);
/* 106:    */     
/* 107:200 */     return n;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public final AxesWalker getFirstWalker()
/* 111:    */   {
/* 112:214 */     return this.m_firstWalker;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public final void setFirstWalker(AxesWalker walker)
/* 116:    */   {
/* 117:225 */     this.m_firstWalker = walker;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public final void setLastUsedWalker(AxesWalker walker)
/* 121:    */   {
/* 122:237 */     this.m_lastUsedWalker = walker;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public final AxesWalker getLastUsedWalker()
/* 126:    */   {
/* 127:248 */     return this.m_lastUsedWalker;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void detach()
/* 131:    */   {
/* 132:260 */     if (this.m_allowDetach)
/* 133:    */     {
/* 134:262 */       AxesWalker walker = this.m_firstWalker;
/* 135:263 */       while (null != walker)
/* 136:    */       {
/* 137:265 */         walker.detach();
/* 138:266 */         walker = walker.getNextWalker();
/* 139:    */       }
/* 140:269 */       this.m_lastUsedWalker = null;
/* 141:    */       
/* 142:    */ 
/* 143:272 */       super.detach();
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void fixupVariables(Vector vars, int globalsSize)
/* 148:    */   {
/* 149:288 */     this.m_predicateIndex = -1;
/* 150:    */     
/* 151:290 */     AxesWalker walker = this.m_firstWalker;
/* 152:292 */     while (null != walker)
/* 153:    */     {
/* 154:294 */       walker.fixupVariables(vars, globalsSize);
/* 155:295 */       walker = walker.getNextWalker();
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/* 160:    */   {
/* 161:304 */     if (visitor.visitLocationPath(owner, this)) {
/* 162:306 */       if (null != this.m_firstWalker) {
/* 163:308 */         this.m_firstWalker.callVisitors(this, visitor);
/* 164:    */       }
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Expression getExpression()
/* 169:    */   {
/* 170:327 */     return this.m_firstWalker;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setExpression(Expression exp)
/* 174:    */   {
/* 175:335 */     exp.exprSetParent(this);
/* 176:336 */     this.m_firstWalker = ((AxesWalker)exp);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean deepEquals(Expression expr)
/* 180:    */   {
/* 181:344 */     if (!super.deepEquals(expr)) {
/* 182:345 */       return false;
/* 183:    */     }
/* 184:347 */     AxesWalker walker1 = this.m_firstWalker;
/* 185:348 */     AxesWalker walker2 = ((WalkingIterator)expr).m_firstWalker;
/* 186:349 */     while ((null != walker1) && (null != walker2))
/* 187:    */     {
/* 188:351 */       if (!walker1.deepEquals(walker2)) {
/* 189:352 */         return false;
/* 190:    */       }
/* 191:353 */       walker1 = walker1.getNextWalker();
/* 192:354 */       walker2 = walker2.getNextWalker();
/* 193:    */     }
/* 194:357 */     if ((null != walker1) || (null != walker2)) {
/* 195:358 */       return false;
/* 196:    */     }
/* 197:360 */     return true;
/* 198:    */   }
/* 199:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.WalkingIterator
 * JD-Core Version:    0.7.0.1
 */