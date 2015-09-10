/*   1:    */ package org.apache.xpath.compiler;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.utils.ObjectVector;
/*   5:    */ import org.apache.xpath.res.XPATHMessages;
/*   6:    */ 
/*   7:    */ public class OpMap
/*   8:    */ {
/*   9:    */   protected String m_currentPattern;
/*  10:    */   static final int MAXTOKENQUEUESIZE = 500;
/*  11:    */   static final int BLOCKTOKENQUEUESIZE = 500;
/*  12:    */   
/*  13:    */   public String toString()
/*  14:    */   {
/*  15: 47 */     return this.m_currentPattern;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public String getPatternString()
/*  19:    */   {
/*  20: 57 */     return this.m_currentPattern;
/*  21:    */   }
/*  22:    */   
/*  23: 75 */   ObjectVector m_tokenQueue = new ObjectVector(500, 500);
/*  24:    */   
/*  25:    */   public ObjectVector getTokenQueue()
/*  26:    */   {
/*  27: 84 */     return this.m_tokenQueue;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Object getToken(int pos)
/*  31:    */   {
/*  32: 96 */     return this.m_tokenQueue.elementAt(pos);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getTokenQueueSize()
/*  36:    */   {
/*  37:111 */     return this.m_tokenQueue.size();
/*  38:    */   }
/*  39:    */   
/*  40:121 */   OpMapVector m_opMap = null;
/*  41:    */   public static final int MAPINDEX_LENGTH = 1;
/*  42:    */   
/*  43:    */   public OpMapVector getOpMap()
/*  44:    */   {
/*  45:133 */     return this.m_opMap;
/*  46:    */   }
/*  47:    */   
/*  48:    */   void shrink()
/*  49:    */   {
/*  50:152 */     int n = this.m_opMap.elementAt(1);
/*  51:153 */     this.m_opMap.setToSize(n + 4);
/*  52:    */     
/*  53:155 */     this.m_opMap.setElementAt(0, n);
/*  54:156 */     this.m_opMap.setElementAt(0, n + 1);
/*  55:157 */     this.m_opMap.setElementAt(0, n + 2);
/*  56:    */     
/*  57:    */ 
/*  58:160 */     n = this.m_tokenQueue.size();
/*  59:161 */     this.m_tokenQueue.setToSize(n + 4);
/*  60:    */     
/*  61:163 */     this.m_tokenQueue.setElementAt(null, n);
/*  62:164 */     this.m_tokenQueue.setElementAt(null, n + 1);
/*  63:165 */     this.m_tokenQueue.setElementAt(null, n + 2);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getOp(int opPos)
/*  67:    */   {
/*  68:176 */     return this.m_opMap.elementAt(opPos);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setOp(int opPos, int value)
/*  72:    */   {
/*  73:187 */     this.m_opMap.setElementAt(value, opPos);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getNextOpPos(int opPos)
/*  77:    */   {
/*  78:200 */     return opPos + this.m_opMap.elementAt(opPos + 1);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getNextStepPos(int opPos)
/*  82:    */   {
/*  83:213 */     int stepType = getOp(opPos);
/*  84:215 */     if ((stepType >= 37) && (stepType <= 53)) {
/*  85:218 */       return getNextOpPos(opPos);
/*  86:    */     }
/*  87:220 */     if ((stepType >= 22) && (stepType <= 25))
/*  88:    */     {
/*  89:223 */       int newOpPos = getNextOpPos(opPos);
/*  90:225 */       while (29 == getOp(newOpPos)) {
/*  91:227 */         newOpPos = getNextOpPos(newOpPos);
/*  92:    */       }
/*  93:230 */       stepType = getOp(newOpPos);
/*  94:232 */       if ((stepType < 37) || (stepType > 53)) {
/*  95:235 */         return -1;
/*  96:    */       }
/*  97:238 */       return newOpPos;
/*  98:    */     }
/*  99:242 */     throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_UNKNOWN_STEP", new Object[] { String.valueOf(stepType) }));
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static int getNextOpPos(int[] opMap, int opPos)
/* 103:    */   {
/* 104:258 */     return opPos + opMap[(opPos + 1)];
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getFirstPredicateOpPos(int opPos)
/* 108:    */     throws TransformerException
/* 109:    */   {
/* 110:277 */     int stepType = this.m_opMap.elementAt(opPos);
/* 111:279 */     if ((stepType >= 37) && (stepType <= 53)) {
/* 112:282 */       return opPos + this.m_opMap.elementAt(opPos + 2);
/* 113:    */     }
/* 114:284 */     if ((stepType >= 22) && (stepType <= 25)) {
/* 115:287 */       return opPos + this.m_opMap.elementAt(opPos + 1);
/* 116:    */     }
/* 117:289 */     if (-2 == stepType) {
/* 118:291 */       return -2;
/* 119:    */     }
/* 120:295 */     error("ER_UNKNOWN_OPCODE", new Object[] { String.valueOf(stepType) });
/* 121:    */     
/* 122:297 */     return -1;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void error(String msg, Object[] args)
/* 126:    */     throws TransformerException
/* 127:    */   {
/* 128:317 */     String fmsg = XPATHMessages.createXPATHMessage(msg, args);
/* 129:    */     
/* 130:    */ 
/* 131:320 */     throw new TransformerException(fmsg);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static int getFirstChildPos(int opPos)
/* 135:    */   {
/* 136:333 */     return opPos + 2;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public int getArgLength(int opPos)
/* 140:    */   {
/* 141:345 */     return this.m_opMap.elementAt(opPos + 1);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int getArgLengthOfStep(int opPos)
/* 145:    */   {
/* 146:357 */     return this.m_opMap.elementAt(opPos + 1 + 1) - 3;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static int getFirstChildPosOfStep(int opPos)
/* 150:    */   {
/* 151:369 */     return opPos + 3;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public int getStepTestType(int opPosOfStep)
/* 155:    */   {
/* 156:381 */     return this.m_opMap.elementAt(opPosOfStep + 3);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String getStepNS(int opPosOfStep)
/* 160:    */   {
/* 161:394 */     int argLenOfStep = getArgLengthOfStep(opPosOfStep);
/* 162:397 */     if (argLenOfStep == 3)
/* 163:    */     {
/* 164:399 */       int index = this.m_opMap.elementAt(opPosOfStep + 4);
/* 165:401 */       if (index >= 0) {
/* 166:402 */         return (String)this.m_tokenQueue.elementAt(index);
/* 167:    */       }
/* 168:403 */       if (-3 == index) {
/* 169:404 */         return "*";
/* 170:    */       }
/* 171:406 */       return null;
/* 172:    */     }
/* 173:409 */     return null;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getStepLocalName(int opPosOfStep)
/* 177:    */   {
/* 178:421 */     int argLenOfStep = getArgLengthOfStep(opPosOfStep);
/* 179:    */     int index;
/* 180:426 */     switch (argLenOfStep)
/* 181:    */     {
/* 182:    */     case 0: 
/* 183:429 */       index = -2;
/* 184:430 */       break;
/* 185:    */     case 1: 
/* 186:432 */       index = -3;
/* 187:433 */       break;
/* 188:    */     case 2: 
/* 189:435 */       index = this.m_opMap.elementAt(opPosOfStep + 4);
/* 190:436 */       break;
/* 191:    */     case 3: 
/* 192:438 */       index = this.m_opMap.elementAt(opPosOfStep + 5);
/* 193:439 */       break;
/* 194:    */     default: 
/* 195:441 */       index = -2;
/* 196:    */     }
/* 197:447 */     if (index >= 0) {
/* 198:448 */       return this.m_tokenQueue.elementAt(index).toString();
/* 199:    */     }
/* 200:449 */     if (-3 == index) {
/* 201:450 */       return "*";
/* 202:    */     }
/* 203:452 */     return null;
/* 204:    */   }
/* 205:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.compiler.OpMap
 * JD-Core Version:    0.7.0.1
 */