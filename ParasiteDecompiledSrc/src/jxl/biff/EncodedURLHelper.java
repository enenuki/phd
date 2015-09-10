/*   1:    */ package jxl.biff;
/*   2:    */ 
/*   3:    */ import jxl.WorkbookSettings;
/*   4:    */ import jxl.common.Logger;
/*   5:    */ 
/*   6:    */ public class EncodedURLHelper
/*   7:    */ {
/*   8: 34 */   private static Logger logger = Logger.getLogger(EncodedURLHelper.class);
/*   9: 37 */   private static byte msDosDriveLetter = 1;
/*  10: 38 */   private static byte sameDrive = 2;
/*  11: 39 */   private static byte endOfSubdirectory = 3;
/*  12: 40 */   private static byte parentDirectory = 4;
/*  13: 41 */   private static byte unencodedUrl = 5;
/*  14:    */   
/*  15:    */   public static byte[] getEncodedURL(String s, WorkbookSettings ws)
/*  16:    */   {
/*  17: 45 */     if (s.startsWith("http:")) {
/*  18: 47 */       return getURL(s, ws);
/*  19:    */     }
/*  20: 51 */     return getFile(s, ws);
/*  21:    */   }
/*  22:    */   
/*  23:    */   private static byte[] getFile(String s, WorkbookSettings ws)
/*  24:    */   {
/*  25: 57 */     ByteArray byteArray = new ByteArray();
/*  26:    */     
/*  27: 59 */     int pos = 0;
/*  28: 60 */     if (s.charAt(1) == ':')
/*  29:    */     {
/*  30: 63 */       byteArray.add(msDosDriveLetter);
/*  31: 64 */       byteArray.add((byte)s.charAt(0));
/*  32: 65 */       pos = 2;
/*  33:    */     }
/*  34: 67 */     else if ((s.charAt(pos) == '\\') || (s.charAt(pos) == '/'))
/*  35:    */     {
/*  36: 70 */       byteArray.add(sameDrive);
/*  37:    */     }
/*  38: 74 */     while ((s.charAt(pos) == '\\') || (s.charAt(pos) == '/')) {
/*  39: 76 */       pos++;
/*  40:    */     }
/*  41: 79 */     while (pos < s.length())
/*  42:    */     {
/*  43: 81 */       int nextSepIndex1 = s.indexOf('/', pos);
/*  44: 82 */       int nextSepIndex2 = s.indexOf('\\', pos);
/*  45: 83 */       int nextSepIndex = 0;
/*  46: 84 */       String nextFileNameComponent = null;
/*  47: 86 */       if ((nextSepIndex1 != -1) && (nextSepIndex2 != -1)) {
/*  48: 89 */         nextSepIndex = Math.min(nextSepIndex1, nextSepIndex2);
/*  49: 91 */       } else if ((nextSepIndex1 == -1) || (nextSepIndex2 == -1)) {
/*  50: 94 */         nextSepIndex = Math.max(nextSepIndex1, nextSepIndex2);
/*  51:    */       }
/*  52: 97 */       if (nextSepIndex == -1)
/*  53:    */       {
/*  54:100 */         nextFileNameComponent = s.substring(pos);
/*  55:101 */         pos = s.length();
/*  56:    */       }
/*  57:    */       else
/*  58:    */       {
/*  59:105 */         nextFileNameComponent = s.substring(pos, nextSepIndex);
/*  60:106 */         pos = nextSepIndex + 1;
/*  61:    */       }
/*  62:109 */       if (!nextFileNameComponent.equals(".")) {
/*  63:113 */         if (nextFileNameComponent.equals("..")) {
/*  64:116 */           byteArray.add(parentDirectory);
/*  65:    */         } else {
/*  66:121 */           byteArray.add(StringHelper.getBytes(nextFileNameComponent, ws));
/*  67:    */         }
/*  68:    */       }
/*  69:125 */       if (pos < s.length()) {
/*  70:127 */         byteArray.add(endOfSubdirectory);
/*  71:    */       }
/*  72:    */     }
/*  73:131 */     return byteArray.getBytes();
/*  74:    */   }
/*  75:    */   
/*  76:    */   private static byte[] getURL(String s, WorkbookSettings ws)
/*  77:    */   {
/*  78:136 */     ByteArray byteArray = new ByteArray();
/*  79:137 */     byteArray.add(unencodedUrl);
/*  80:138 */     byteArray.add((byte)s.length());
/*  81:139 */     byteArray.add(StringHelper.getBytes(s, ws));
/*  82:140 */     return byteArray.getBytes();
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.EncodedURLHelper
 * JD-Core Version:    0.7.0.1
 */