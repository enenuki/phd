/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import jxl.biff.FormatRecord;
/*   4:    */ import jxl.common.Logger;
/*   5:    */ 
/*   6:    */ public class NumberFormatRecord
/*   7:    */   extends FormatRecord
/*   8:    */ {
/*   9: 34 */   private static Logger logger = Logger.getLogger(NumberFormatRecord.class);
/*  10:    */   
/*  11:    */   protected NumberFormatRecord(String fmt)
/*  12:    */   {
/*  13: 51 */     String fs = fmt;
/*  14:    */     
/*  15: 53 */     fs = replace(fs, "E0", "E+0");
/*  16:    */     
/*  17: 55 */     fs = trimInvalidChars(fs);
/*  18:    */     
/*  19: 57 */     setFormatString(fs);
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected NumberFormatRecord(String fmt, NonValidatingFormat dummy)
/*  23:    */   {
/*  24: 71 */     String fs = fmt;
/*  25:    */     
/*  26: 73 */     fs = replace(fs, "E0", "E+0");
/*  27:    */     
/*  28: 75 */     setFormatString(fs);
/*  29:    */   }
/*  30:    */   
/*  31:    */   private String trimInvalidChars(String fs)
/*  32:    */   {
/*  33: 87 */     int firstHash = fs.indexOf('#');
/*  34: 88 */     int firstZero = fs.indexOf('0');
/*  35: 89 */     int firstValidChar = 0;
/*  36: 91 */     if ((firstHash == -1) && (firstZero == -1)) {
/*  37: 94 */       return "#.###";
/*  38:    */     }
/*  39: 97 */     if ((firstHash != 0) && (firstZero != 0) && (firstHash != 1) && (firstZero != 1))
/*  40:    */     {
/*  41:101 */       firstHash = firstHash == -1 ? (firstHash = 2147483647) : firstHash;
/*  42:102 */       firstZero = firstZero == -1 ? (firstZero = 2147483647) : firstZero;
/*  43:103 */       firstValidChar = Math.min(firstHash, firstZero);
/*  44:    */       
/*  45:105 */       StringBuffer tmp = new StringBuffer();
/*  46:106 */       tmp.append(fs.charAt(0));
/*  47:107 */       tmp.append(fs.substring(firstValidChar));
/*  48:108 */       fs = tmp.toString();
/*  49:    */     }
/*  50:112 */     int lastHash = fs.lastIndexOf('#');
/*  51:113 */     int lastZero = fs.lastIndexOf('0');
/*  52:115 */     if ((lastHash == fs.length()) || (lastZero == fs.length())) {
/*  53:118 */       return fs;
/*  54:    */     }
/*  55:122 */     int lastValidChar = Math.max(lastHash, lastZero);
/*  56:125 */     while ((fs.length() > lastValidChar + 1) && ((fs.charAt(lastValidChar + 1) == ')') || (fs.charAt(lastValidChar + 1) == '%'))) {
/*  57:129 */       lastValidChar++;
/*  58:    */     }
/*  59:132 */     return fs.substring(0, lastValidChar + 1);
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected static class NonValidatingFormat {}
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.NumberFormatRecord
 * JD-Core Version:    0.7.0.1
 */