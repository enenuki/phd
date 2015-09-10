/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.dtm.DTMIterator;
/*   5:    */ import org.apache.xml.utils.PrefixResolver;
/*   6:    */ import org.apache.xpath.VariableStack;
/*   7:    */ import org.apache.xpath.XPathContext;
/*   8:    */ import org.apache.xpath.compiler.Compiler;
/*   9:    */ import org.apache.xpath.compiler.OpMap;
/*  10:    */ import org.apache.xpath.patterns.NodeTest;
/*  11:    */ 
/*  12:    */ public abstract class BasicTestIterator
/*  13:    */   extends LocPathIterator
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = 3505378079378096623L;
/*  16:    */   
/*  17:    */   protected BasicTestIterator() {}
/*  18:    */   
/*  19:    */   protected BasicTestIterator(PrefixResolver nscontext)
/*  20:    */   {
/*  21: 58 */     super(nscontext);
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected BasicTestIterator(Compiler compiler, int opPos, int analysis)
/*  25:    */     throws TransformerException
/*  26:    */   {
/*  27: 76 */     super(compiler, opPos, analysis, false);
/*  28:    */     
/*  29: 78 */     int firstStepPos = OpMap.getFirstChildPos(opPos);
/*  30: 79 */     int whatToShow = compiler.getWhatToShow(firstStepPos);
/*  31: 81 */     if ((0 == (whatToShow & 0x1043)) || (whatToShow == -1)) {
/*  32: 87 */       initNodeTest(whatToShow);
/*  33:    */     } else {
/*  34: 90 */       initNodeTest(whatToShow, compiler.getStepNS(firstStepPos), compiler.getStepLocalName(firstStepPos));
/*  35:    */     }
/*  36: 93 */     initPredicateInfo(compiler, firstStepPos);
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected BasicTestIterator(Compiler compiler, int opPos, int analysis, boolean shouldLoadWalkers)
/*  40:    */     throws TransformerException
/*  41:    */   {
/*  42:115 */     super(compiler, opPos, analysis, shouldLoadWalkers);
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected abstract int getNextNode();
/*  46:    */   
/*  47:    */   public int nextNode()
/*  48:    */   {
/*  49:135 */     if (this.m_foundLast)
/*  50:    */     {
/*  51:137 */       this.m_lastFetched = -1;
/*  52:138 */       return -1;
/*  53:    */     }
/*  54:141 */     if (-1 == this.m_lastFetched) {
/*  55:143 */       resetProximityPositions();
/*  56:    */     }
/*  57:    */     VariableStack vars;
/*  58:    */     int savedStart;
/*  59:150 */     if (-1 != this.m_stackFrame)
/*  60:    */     {
/*  61:152 */       vars = this.m_execContext.getVarStack();
/*  62:    */       
/*  63:    */ 
/*  64:155 */       savedStart = vars.getStackFrame();
/*  65:    */       
/*  66:157 */       vars.setStackFrame(this.m_stackFrame);
/*  67:    */     }
/*  68:    */     else
/*  69:    */     {
/*  70:162 */       vars = null;
/*  71:163 */       savedStart = 0;
/*  72:    */     }
/*  73:    */     try
/*  74:    */     {
/*  75:    */       int next;
/*  76:    */       do
/*  77:    */       {
/*  78:170 */         next = getNextNode();
/*  79:172 */       } while ((-1 != next) && 
/*  80:    */       
/*  81:174 */         (1 != acceptNode(next)) && 
/*  82:    */         
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:182 */         (next != -1));
/*  90:    */       int i;
/*  91:184 */       if (-1 != next)
/*  92:    */       {
/*  93:186 */         this.m_pos += 1;
/*  94:187 */         return next;
/*  95:    */       }
/*  96:191 */       this.m_foundLast = true;
/*  97:    */       
/*  98:193 */       return -1;
/*  99:    */     }
/* 100:    */     finally
/* 101:    */     {
/* 102:198 */       if (-1 != this.m_stackFrame) {
/* 103:201 */         vars.setStackFrame(savedStart);
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public DTMIterator cloneWithReset()
/* 109:    */     throws CloneNotSupportedException
/* 110:    */   {
/* 111:217 */     ChildTestIterator clone = (ChildTestIterator)super.cloneWithReset();
/* 112:    */     
/* 113:219 */     clone.resetProximityPositions();
/* 114:    */     
/* 115:221 */     return clone;
/* 116:    */   }
/* 117:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.BasicTestIterator
 * JD-Core Version:    0.7.0.1
 */