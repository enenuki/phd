/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import java.io.OutputStream;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import java.io.Writer;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collections;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ 
/*  13:    */ public class PrintWriterWithSMAP
/*  14:    */   extends PrintWriter
/*  15:    */ {
/*  16: 18 */   private int currentOutputLine = 1;
/*  17: 19 */   private int currentSourceLine = 0;
/*  18: 20 */   private Map sourceMap = new HashMap();
/*  19: 22 */   private boolean lastPrintCharacterWasCR = false;
/*  20: 23 */   private boolean mapLines = false;
/*  21: 24 */   private boolean mapSingleSourceLine = false;
/*  22: 25 */   private boolean anythingWrittenSinceMapping = false;
/*  23:    */   
/*  24:    */   public PrintWriterWithSMAP(OutputStream paramOutputStream)
/*  25:    */   {
/*  26: 28 */     super(paramOutputStream);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public PrintWriterWithSMAP(OutputStream paramOutputStream, boolean paramBoolean)
/*  30:    */   {
/*  31: 31 */     super(paramOutputStream, paramBoolean);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public PrintWriterWithSMAP(Writer paramWriter)
/*  35:    */   {
/*  36: 34 */     super(paramWriter);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public PrintWriterWithSMAP(Writer paramWriter, boolean paramBoolean)
/*  40:    */   {
/*  41: 37 */     super(paramWriter, paramBoolean);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void startMapping(int paramInt)
/*  45:    */   {
/*  46: 41 */     this.mapLines = true;
/*  47: 42 */     if (paramInt != -888) {
/*  48: 43 */       this.currentSourceLine = paramInt;
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void startSingleSourceLineMapping(int paramInt)
/*  53:    */   {
/*  54: 47 */     this.mapSingleSourceLine = true;
/*  55: 48 */     this.mapLines = true;
/*  56: 49 */     if (paramInt != -888) {
/*  57: 50 */       this.currentSourceLine = paramInt;
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void endMapping()
/*  62:    */   {
/*  63: 54 */     mapLine(false);
/*  64: 55 */     this.mapLines = false;
/*  65: 56 */     this.mapSingleSourceLine = false;
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected void mapLine(boolean paramBoolean)
/*  69:    */   {
/*  70: 60 */     if ((this.mapLines) && (this.anythingWrittenSinceMapping))
/*  71:    */     {
/*  72: 61 */       Integer localInteger1 = new Integer(this.currentSourceLine);
/*  73: 62 */       Integer localInteger2 = new Integer(this.currentOutputLine);
/*  74: 63 */       Object localObject = (List)this.sourceMap.get(localInteger1);
/*  75: 64 */       if (localObject == null)
/*  76:    */       {
/*  77: 65 */         localObject = new ArrayList();
/*  78: 66 */         this.sourceMap.put(localInteger1, localObject);
/*  79:    */       }
/*  80: 68 */       if (!((List)localObject).contains(localInteger2)) {
/*  81: 69 */         ((List)localObject).add(localInteger2);
/*  82:    */       }
/*  83:    */     }
/*  84: 71 */     if (paramBoolean) {
/*  85: 72 */       this.currentOutputLine += 1;
/*  86:    */     }
/*  87: 73 */     if (!this.mapSingleSourceLine) {
/*  88: 74 */       this.currentSourceLine += 1;
/*  89:    */     }
/*  90: 75 */     this.anythingWrittenSinceMapping = false;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void dump(PrintWriter paramPrintWriter, String paramString1, String paramString2)
/*  94:    */   {
/*  95: 79 */     paramPrintWriter.println("SMAP");
/*  96: 80 */     paramPrintWriter.println(paramString1 + ".java");
/*  97: 81 */     paramPrintWriter.println("G");
/*  98: 82 */     paramPrintWriter.println("*S G");
/*  99: 83 */     paramPrintWriter.println("*F");
/* 100: 84 */     paramPrintWriter.println("+ 0 " + paramString2);
/* 101: 85 */     paramPrintWriter.println(paramString2);
/* 102: 86 */     paramPrintWriter.println("*L");
/* 103: 87 */     ArrayList localArrayList = new ArrayList(this.sourceMap.keySet());
/* 104: 88 */     Collections.sort(localArrayList);
/* 105: 89 */     for (Iterator localIterator1 = localArrayList.iterator(); localIterator1.hasNext();)
/* 106:    */     {
/* 107: 90 */       localInteger1 = (Integer)localIterator1.next();
/* 108: 91 */       List localList = (List)this.sourceMap.get(localInteger1);
/* 109: 92 */       for (localIterator2 = localList.iterator(); localIterator2.hasNext();)
/* 110:    */       {
/* 111: 93 */         Integer localInteger2 = (Integer)localIterator2.next();
/* 112: 94 */         paramPrintWriter.println(localInteger1 + ":" + localInteger2);
/* 113:    */       }
/* 114:    */     }
/* 115:    */     Integer localInteger1;
/* 116:    */     Iterator localIterator2;
/* 117: 97 */     paramPrintWriter.println("*E");
/* 118: 98 */     paramPrintWriter.close();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/* 122:    */   {
/* 123:102 */     int i = paramInt1 + paramInt2;
/* 124:103 */     for (int j = paramInt1; j < i; j++) {
/* 125:104 */       checkChar(paramArrayOfChar[j]);
/* 126:    */     }
/* 127:106 */     super.write(paramArrayOfChar, paramInt1, paramInt2);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void checkChar(int paramInt)
/* 131:    */   {
/* 132:111 */     if ((this.lastPrintCharacterWasCR) && (paramInt != 10)) {
/* 133:112 */       mapLine(true);
/* 134:114 */     } else if (paramInt == 10) {
/* 135:115 */       mapLine(true);
/* 136:117 */     } else if (!Character.isWhitespace((char)paramInt)) {
/* 137:118 */       this.anythingWrittenSinceMapping = true;
/* 138:    */     }
/* 139:120 */     this.lastPrintCharacterWasCR = (paramInt == 13);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void write(int paramInt)
/* 143:    */   {
/* 144:123 */     checkChar(paramInt);
/* 145:124 */     super.write(paramInt);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void write(String paramString, int paramInt1, int paramInt2)
/* 149:    */   {
/* 150:127 */     int i = paramInt1 + paramInt2;
/* 151:128 */     for (int j = paramInt1; j < i; j++) {
/* 152:129 */       checkChar(paramString.charAt(j));
/* 153:    */     }
/* 154:131 */     super.write(paramString, paramInt1, paramInt2);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void println()
/* 158:    */   {
/* 159:141 */     mapLine(true);
/* 160:142 */     super.println();
/* 161:143 */     this.lastPrintCharacterWasCR = false;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public Map getSourceMap()
/* 165:    */   {
/* 166:146 */     return this.sourceMap;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public int getCurrentOutputLine()
/* 170:    */   {
/* 171:150 */     return this.currentOutputLine;
/* 172:    */   }
/* 173:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.PrintWriterWithSMAP
 * JD-Core Version:    0.7.0.1
 */