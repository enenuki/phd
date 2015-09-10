/*   1:    */ package hr.nukic.parasite.core;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.BufferedWriter;
/*   5:    */ import java.io.DataInputStream;
/*   6:    */ import java.io.File;
/*   7:    */ import java.io.FileInputStream;
/*   8:    */ import java.io.FileNotFoundException;
/*   9:    */ import java.io.FileWriter;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.io.InputStreamReader;
/*  12:    */ import java.text.ParseException;
/*  13:    */ import java.text.SimpleDateFormat;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.List;
/*  18:    */ import nukic.parasite.utils.MainLogger;
/*  19:    */ 
/*  20:    */ public class StockHistoryData
/*  21:    */ {
/*  22:    */   public static final int REFRESH_DAYS = 7;
/*  23:    */   String ticker;
/*  24: 24 */   public List<StockDaySummary> daySummaries = new ArrayList();
/*  25: 25 */   public StockDaySummary averageDayValues = null;
/*  26:    */   public String historiesFolder;
/*  27: 27 */   public Date historyCreated = new Date(0L);
/*  28:    */   
/*  29:    */   public StockHistoryData(String ticker)
/*  30:    */   {
/*  31: 32 */     this.ticker = ticker;
/*  32: 33 */     this.historiesFolder = ParasiteManager.getProperty("HISTORY_FOLDER");
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean readDaySummariesFromFile()
/*  36:    */   {
/*  37:    */     try
/*  38:    */     {
/*  39: 40 */       fstream = new FileInputStream(this.historiesFolder + "\\" + this.ticker + ".daysums");
/*  40:    */     }
/*  41:    */     catch (FileNotFoundException e)
/*  42:    */     {
/*  43:    */       FileInputStream fstream;
/*  44: 42 */       MainLogger.info("Could not find day sumaries file. ");
/*  45: 43 */       MainLogger.info(e.toString());
/*  46: 44 */       return false;
/*  47:    */     }
/*  48:    */     FileInputStream fstream;
/*  49: 46 */     DataInputStream in = new DataInputStream(fstream);
/*  50: 47 */     BufferedReader br = new BufferedReader(new InputStreamReader(in));
/*  51:    */     
/*  52:    */ 
/*  53: 50 */     int lineNum = 0;
/*  54:    */     try
/*  55:    */     {
/*  56:    */       String strLine;
/*  57: 54 */       while ((strLine = br.readLine()) != null)
/*  58:    */       {
/*  59:    */         String strLine;
/*  60: 56 */         if (lineNum == 0)
/*  61:    */         {
/*  62: 57 */           SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
/*  63: 58 */           String[] toks = strLine.split(":");
/*  64: 59 */           this.historyCreated = f.parse(toks[1]);
/*  65: 60 */           Date now = new Date();
/*  66: 61 */           if (now.getTime() - this.historyCreated.getTime() > 604800000L) {
/*  67: 63 */             return false;
/*  68:    */           }
/*  69:    */         }
/*  70:    */         else
/*  71:    */         {
/*  72: 67 */           this.daySummaries.add(new StockDaySummary(strLine));
/*  73:    */         }
/*  74: 69 */         lineNum++;
/*  75:    */       }
/*  76:    */     }
/*  77:    */     catch (IOException e)
/*  78:    */     {
/*  79: 72 */       MainLogger.error(e.toString());
/*  80: 73 */       return false;
/*  81:    */     }
/*  82:    */     catch (ParseException e)
/*  83:    */     {
/*  84: 75 */       MainLogger.error("Error while parsing...");
/*  85: 76 */       MainLogger.error(e.toString());
/*  86: 77 */       return false;
/*  87:    */     }
/*  88:    */     try
/*  89:    */     {
/*  90:    */       String strLine;
/*  91: 81 */       in.close();
/*  92:    */     }
/*  93:    */     catch (IOException e)
/*  94:    */     {
/*  95: 83 */       MainLogger.error("Could not close the day sumaries document...");
/*  96: 84 */       return true;
/*  97:    */     }
/*  98: 87 */     return true;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void writeDaySummariesToFile()
/* 102:    */   {
/* 103: 92 */     String str = toString();
/* 104:    */     try
/* 105:    */     {
/* 106: 94 */       String outDirPath = this.historiesFolder + "\\";
/* 107: 95 */       File outFolder = new File(outDirPath);
/* 108: 96 */       if (!outFolder.exists())
/* 109:    */       {
/* 110: 97 */         boolean success = outFolder.mkdir();
/* 111: 98 */         if (!success) {
/* 112: 99 */           MainLogger.error("ERROR while creating directory: " + outDirPath);
/* 113:    */         }
/* 114:    */       }
/* 115:102 */       FileWriter fstream = new FileWriter(outDirPath + this.ticker + ".daysums", false);
/* 116:103 */       BufferedWriter out = new BufferedWriter(fstream);
/* 117:    */       
/* 118:105 */       out.write(str);
/* 119:106 */       out.close();
/* 120:    */     }
/* 121:    */     catch (Exception e)
/* 122:    */     {
/* 123:108 */       MainLogger.error(e);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void calculateAverageDayValues()
/* 128:    */   {
/* 129:113 */     Iterator<StockDaySummary> i = this.daySummaries.iterator();
/* 130:114 */     this.averageDayValues = new StockDaySummary();
/* 131:115 */     while (i.hasNext())
/* 132:    */     {
/* 133:116 */       StockDaySummary summary = (StockDaySummary)i.next();
/* 134:117 */       this.averageDayValues.amount += summary.amount;
/* 135:118 */       this.averageDayValues.average += summary.average;
/* 136:119 */       this.averageDayValues.change += summary.change;
/* 137:120 */       this.averageDayValues.first += summary.first;
/* 138:121 */       this.averageDayValues.high += summary.high;
/* 139:122 */       this.averageDayValues.last += summary.last;
/* 140:123 */       this.averageDayValues.low += summary.low;
/* 141:124 */       this.averageDayValues.transactionNum += summary.transactionNum;
/* 142:125 */       this.averageDayValues.turnover += summary.turnover;
/* 143:    */     }
/* 144:128 */     int size = this.daySummaries.size();
/* 145:129 */     if (size != 0)
/* 146:    */     {
/* 147:130 */       this.averageDayValues.amount /= size;
/* 148:131 */       this.averageDayValues.average /= size;
/* 149:132 */       this.averageDayValues.change /= size;
/* 150:133 */       this.averageDayValues.first /= size;
/* 151:134 */       this.averageDayValues.high /= size;
/* 152:135 */       this.averageDayValues.last /= size;
/* 153:136 */       this.averageDayValues.low /= size;
/* 154:137 */       this.averageDayValues.transactionNum /= size;
/* 155:138 */       this.averageDayValues.turnover /= size;
/* 156:    */     }
/* 157:    */     else
/* 158:    */     {
/* 159:140 */       MainLogger.error("No history day summaries! Cannot calculate average day values!");
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void calculateAverageValuesForLast(int days)
/* 164:    */   {
/* 165:145 */     Iterator<StockDaySummary> i = this.daySummaries.iterator();
/* 166:146 */     this.averageDayValues = new StockDaySummary();
/* 167:147 */     Date now = new Date();
/* 168:148 */     Date since = new Date(now.getTime() - days * 24 * 60 * 60 * 1000);
/* 169:149 */     int c = 1;
/* 170:151 */     while (i.hasNext())
/* 171:    */     {
/* 172:152 */       StockDaySummary summary = (StockDaySummary)i.next();
/* 173:153 */       if (summary.date.getTime() > since.getTime())
/* 174:    */       {
/* 175:154 */         this.averageDayValues.amount += summary.amount;
/* 176:155 */         this.averageDayValues.average += summary.average;
/* 177:156 */         this.averageDayValues.change += summary.change;
/* 178:157 */         this.averageDayValues.first += summary.first;
/* 179:158 */         this.averageDayValues.high += summary.high;
/* 180:159 */         this.averageDayValues.last += summary.last;
/* 181:160 */         this.averageDayValues.low += summary.low;
/* 182:161 */         this.averageDayValues.transactionNum += summary.transactionNum;
/* 183:162 */         this.averageDayValues.turnover += summary.turnover;
/* 184:163 */         c++;
/* 185:    */       }
/* 186:    */     }
/* 187:167 */     this.averageDayValues.amount /= c;
/* 188:168 */     this.averageDayValues.average /= c;
/* 189:169 */     this.averageDayValues.change /= c;
/* 190:170 */     this.averageDayValues.first /= c;
/* 191:171 */     this.averageDayValues.high /= c;
/* 192:172 */     this.averageDayValues.last /= c;
/* 193:173 */     this.averageDayValues.low /= c;
/* 194:174 */     this.averageDayValues.transactionNum /= c;
/* 195:175 */     this.averageDayValues.turnover /= c;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String toString()
/* 199:    */   {
/* 200:179 */     String str = "";
/* 201:180 */     Date now = new Date();
/* 202:181 */     SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
/* 203:182 */     str = str + "CREATED:" + f.format(now) + "\n";
/* 204:184 */     for (StockDaySummary summary : this.daySummaries) {
/* 205:185 */       str = str + summary.toString() + "\n";
/* 206:    */     }
/* 207:188 */     return str;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public int hashCode()
/* 211:    */   {
/* 212:193 */     int prime = 31;
/* 213:194 */     int result = 1;
/* 214:195 */     result = 31 * result + (this.daySummaries == null ? 0 : this.daySummaries.hashCode());
/* 215:196 */     result = 31 * result + (this.ticker == null ? 0 : this.ticker.hashCode());
/* 216:197 */     return result;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public boolean equals(Object obj)
/* 220:    */   {
/* 221:202 */     if (this == obj) {
/* 222:203 */       return true;
/* 223:    */     }
/* 224:204 */     if (obj == null) {
/* 225:205 */       return false;
/* 226:    */     }
/* 227:206 */     if (getClass() != obj.getClass()) {
/* 228:207 */       return false;
/* 229:    */     }
/* 230:208 */     StockHistoryData other = (StockHistoryData)obj;
/* 231:210 */     if (this.daySummaries == null)
/* 232:    */     {
/* 233:211 */       if (other.daySummaries != null) {
/* 234:212 */         return false;
/* 235:    */       }
/* 236:    */     }
/* 237:    */     else
/* 238:    */     {
/* 239:213 */       if (this.daySummaries.size() != other.daySummaries.size()) {
/* 240:214 */         return false;
/* 241:    */       }
/* 242:216 */       for (int i = 0; i < this.daySummaries.size(); i++) {
/* 243:217 */         if (!((StockDaySummary)this.daySummaries.get(i)).equals(other.daySummaries.get(i))) {
/* 244:218 */           return false;
/* 245:    */         }
/* 246:    */       }
/* 247:    */     }
/* 248:222 */     if (this.ticker == null)
/* 249:    */     {
/* 250:223 */       if (other.ticker != null) {
/* 251:224 */         return false;
/* 252:    */       }
/* 253:    */     }
/* 254:225 */     else if (!this.ticker.equals(other.ticker)) {
/* 255:226 */       return false;
/* 256:    */     }
/* 257:228 */     return true;
/* 258:    */   }
/* 259:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.core.StockHistoryData
 * JD-Core Version:    0.7.0.1
 */