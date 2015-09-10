/*   1:    */ package nukic.parasite.utils;
/*   2:    */ 
/*   3:    */ import java.io.BufferedWriter;
/*   4:    */ import java.io.FileWriter;
/*   5:    */ import java.text.NumberFormat;
/*   6:    */ import java.text.ParseException;
/*   7:    */ import java.text.SimpleDateFormat;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.Locale;
/*  10:    */ 
/*  11:    */ public class ParasiteUtils
/*  12:    */ {
/*  13: 14 */   public static String outputPath = "C:\\ParasiteTrade\\out\\";
/*  14:    */   
/*  15:    */   public static String getMonthName(int nowMonth)
/*  16:    */   {
/*  17:    */     String monthName;
/*  18:    */     String monthName;
/*  19:    */     String monthName;
/*  20:    */     String monthName;
/*  21:    */     String monthName;
/*  22:    */     String monthName;
/*  23:    */     String monthName;
/*  24:    */     String monthName;
/*  25:    */     String monthName;
/*  26:    */     String monthName;
/*  27:    */     String monthName;
/*  28:    */     String monthName;
/*  29:    */     String monthName;
/*  30: 25 */     switch (nowMonth)
/*  31:    */     {
/*  32:    */     case 1: 
/*  33: 27 */       monthName = "Sijecanj";
/*  34: 28 */       break;
/*  35:    */     case 2: 
/*  36: 30 */       monthName = "Veljaca";
/*  37: 31 */       break;
/*  38:    */     case 3: 
/*  39: 33 */       monthName = "Ozujak";
/*  40: 34 */       break;
/*  41:    */     case 4: 
/*  42: 36 */       monthName = "Travanj";
/*  43: 37 */       break;
/*  44:    */     case 5: 
/*  45: 39 */       monthName = "Svibanj";
/*  46: 40 */       break;
/*  47:    */     case 6: 
/*  48: 42 */       monthName = "Lipanj";
/*  49: 43 */       break;
/*  50:    */     case 7: 
/*  51: 45 */       monthName = "Srpanj";
/*  52: 46 */       break;
/*  53:    */     case 8: 
/*  54: 48 */       monthName = "Kolovoz";
/*  55: 49 */       break;
/*  56:    */     case 9: 
/*  57: 51 */       monthName = "Rujan";
/*  58: 52 */       break;
/*  59:    */     case 10: 
/*  60: 54 */       monthName = "Listopad";
/*  61: 55 */       break;
/*  62:    */     case 11: 
/*  63: 57 */       monthName = "Studeni";
/*  64: 58 */       break;
/*  65:    */     case 12: 
/*  66: 60 */       monthName = "Prosinac";
/*  67: 61 */       break;
/*  68:    */     default: 
/*  69: 63 */       monthName = "Invalid month";
/*  70:    */     }
/*  71: 67 */     return monthName;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static void writeToFile(String str, String path)
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78: 72 */       FileWriter fstream = new FileWriter(path);
/*  79: 73 */       BufferedWriter out = new BufferedWriter(fstream);
/*  80: 74 */       out.write(str);
/*  81: 75 */       out.close();
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85: 77 */       MainLogger.error(e);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static float getSignedFloatFromPercentageSpacedString(String percentStr)
/*  90:    */   {
/*  91: 89 */     float percent = 0.0F;
/*  92: 90 */     String[] tokens = percentStr.split(" ");
/*  93:    */     try
/*  94:    */     {
/*  95: 94 */       NumberFormat nfGerman = NumberFormat.getInstance(Locale.GERMAN);
/*  96: 97 */       if (tokens.length == 2) {
/*  97: 98 */         percent = nfGerman.parse(tokens[0]).floatValue();
/*  98:101 */       } else if (tokens.length == 3) {
/*  99:102 */         percent = 0.0F - nfGerman.parse(tokens[1]).floatValue();
/* 100:    */       } else {
/* 101:106 */         MainLogger.error("Error while parsing signed percentage spaced string " + percentStr);
/* 102:    */       }
/* 103:    */     }
/* 104:    */     catch (ParseException e)
/* 105:    */     {
/* 106:110 */       e.printStackTrace();
/* 107:    */     }
/* 108:112 */     return percent;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static int getFirstNanTokenIndexAfter(int tIndex, String[] tokens)
/* 112:    */   {
/* 113:116 */     for (int i = tIndex + 1; i < tokens.length; i++)
/* 114:    */     {
/* 115:118 */       if (tokens[i].length() == 0) {
/* 116:119 */         return i;
/* 117:    */       }
/* 118:122 */       if (!Character.isDigit(tokens[i].toCharArray()[0])) {
/* 119:123 */         return i;
/* 120:    */       }
/* 121:    */     }
/* 122:126 */     return -1;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static int getFirstIndexOfToken(String string, String[] tokens)
/* 126:    */   {
/* 127:130 */     for (int i = 0; i < tokens.length; i++) {
/* 128:131 */       if (tokens[i].equals(string)) {
/* 129:132 */         return i;
/* 130:    */       }
/* 131:    */     }
/* 132:135 */     return -1;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static float round(float valueToRound, int numberOfDecimalPlaces)
/* 136:    */   {
/* 137:141 */     double multipicationFactor = Math.pow(10.0D, numberOfDecimalPlaces);
/* 138:142 */     double interestedInZeroDPs = valueToRound * multipicationFactor;
/* 139:143 */     return (float)(Math.round(interestedInZeroDPs) / multipicationFactor);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static Date combineDateAndTimeIntoOneDate(Date time, Date day)
/* 143:    */   {
/* 144:147 */     Date fullDateAndTime = new Date(0L);
/* 145:148 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 146:149 */     String dateString = dateFormat.format(day);
/* 147:    */     
/* 148:151 */     SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
/* 149:152 */     String timeString = timeFormat.format(time);
/* 150:    */     
/* 151:154 */     SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
/* 152:155 */     String fullDateAndTimeString = dateString + "_" + timeString;
/* 153:    */     try
/* 154:    */     {
/* 155:158 */       fullDateAndTime = fullFormat.parse(fullDateAndTimeString);
/* 156:    */     }
/* 157:    */     catch (ParseException e)
/* 158:    */     {
/* 159:160 */       MainLogger.error(e);
/* 160:    */     }
/* 161:164 */     return fullDateAndTime;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static void printMemoryStatistics()
/* 165:    */   {
/* 166:170 */     int megaBytes = 1048576;
/* 167:    */     
/* 168:    */ 
/* 169:173 */     Runtime runtime = Runtime.getRuntime();
/* 170:    */     
/* 171:175 */     MainLogger.info(" ------| JVM Memory Utilization Statistics (in MB) |-------");
/* 172:    */     
/* 173:    */ 
/* 174:178 */     MainLogger.info("Used Memory: " + (runtime.totalMemory() - runtime.freeMemory()) / megaBytes);
/* 175:    */     
/* 176:    */ 
/* 177:181 */     MainLogger.info("Free Memory: " + runtime.freeMemory() / megaBytes);
/* 178:    */     
/* 179:    */ 
/* 180:184 */     MainLogger.info("Total Memory: " + runtime.totalMemory() / megaBytes);
/* 181:    */     
/* 182:    */ 
/* 183:187 */     MainLogger.info("Max Memory: " + runtime.maxMemory() / megaBytes);
/* 184:188 */     MainLogger.info(" ----------------------------------------------------------");
/* 185:    */   }
/* 186:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     nukic.parasite.utils.ParasiteUtils
 * JD-Core Version:    0.7.0.1
 */