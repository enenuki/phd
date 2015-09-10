/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ 
/*   5:    */ public class RandomStringUtils
/*   6:    */ {
/*   7: 46 */   private static final Random RANDOM = new Random();
/*   8:    */   
/*   9:    */   public static String random(int count)
/*  10:    */   {
/*  11: 72 */     return random(count, false, false);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public static String randomAscii(int count)
/*  15:    */   {
/*  16: 86 */     return random(count, 32, 127, false, false);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static String randomAlphabetic(int count)
/*  20:    */   {
/*  21:100 */     return random(count, true, false);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static String randomAlphanumeric(int count)
/*  25:    */   {
/*  26:114 */     return random(count, true, true);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static String randomNumeric(int count)
/*  30:    */   {
/*  31:128 */     return random(count, false, true);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static String random(int count, boolean letters, boolean numbers)
/*  35:    */   {
/*  36:146 */     return random(count, 0, 0, letters, numbers);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static String random(int count, int start, int end, boolean letters, boolean numbers)
/*  40:    */   {
/*  41:166 */     return random(count, start, end, letters, numbers, null, RANDOM);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars)
/*  45:    */   {
/*  46:190 */     return random(count, start, end, letters, numbers, chars, RANDOM);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars, Random random)
/*  50:    */   {
/*  51:228 */     if (count == 0) {
/*  52:229 */       return "";
/*  53:    */     }
/*  54:230 */     if (count < 0) {
/*  55:231 */       throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
/*  56:    */     }
/*  57:233 */     if ((start == 0) && (end == 0))
/*  58:    */     {
/*  59:234 */       end = 123;
/*  60:235 */       start = 32;
/*  61:236 */       if ((!letters) && (!numbers))
/*  62:    */       {
/*  63:237 */         start = 0;
/*  64:238 */         end = 2147483647;
/*  65:    */       }
/*  66:    */     }
/*  67:242 */     char[] buffer = new char[count];
/*  68:243 */     int gap = end - start;
/*  69:245 */     while (count-- != 0)
/*  70:    */     {
/*  71:    */       char ch;
/*  72:    */       char ch;
/*  73:247 */       if (chars == null) {
/*  74:248 */         ch = (char)(random.nextInt(gap) + start);
/*  75:    */       } else {
/*  76:250 */         ch = chars[(random.nextInt(gap) + start)];
/*  77:    */       }
/*  78:252 */       if (((letters) && (Character.isLetter(ch))) || ((numbers) && (Character.isDigit(ch))) || ((!letters) && (!numbers)))
/*  79:    */       {
/*  80:256 */         if ((ch >= 56320) && (ch <= 57343))
/*  81:    */         {
/*  82:257 */           if (count == 0)
/*  83:    */           {
/*  84:258 */             count++;
/*  85:    */           }
/*  86:    */           else
/*  87:    */           {
/*  88:261 */             buffer[count] = ch;
/*  89:262 */             count--;
/*  90:263 */             buffer[count] = ((char)(55296 + random.nextInt(128)));
/*  91:    */           }
/*  92:    */         }
/*  93:265 */         else if ((ch >= 55296) && (ch <= 56191))
/*  94:    */         {
/*  95:266 */           if (count == 0)
/*  96:    */           {
/*  97:267 */             count++;
/*  98:    */           }
/*  99:    */           else
/* 100:    */           {
/* 101:270 */             buffer[count] = ((char)(56320 + random.nextInt(128)));
/* 102:271 */             count--;
/* 103:272 */             buffer[count] = ch;
/* 104:    */           }
/* 105:    */         }
/* 106:274 */         else if ((ch >= 56192) && (ch <= 56319)) {
/* 107:276 */           count++;
/* 108:    */         } else {
/* 109:278 */           buffer[count] = ch;
/* 110:    */         }
/* 111:    */       }
/* 112:    */       else {
/* 113:281 */         count++;
/* 114:    */       }
/* 115:    */     }
/* 116:284 */     return new String(buffer);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static String random(int count, String chars)
/* 120:    */   {
/* 121:301 */     if (chars == null) {
/* 122:302 */       return random(count, 0, 0, false, false, null, RANDOM);
/* 123:    */     }
/* 124:304 */     return random(count, chars.toCharArray());
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static String random(int count, char[] chars)
/* 128:    */   {
/* 129:320 */     if (chars == null) {
/* 130:321 */       return random(count, 0, 0, false, false, null, RANDOM);
/* 131:    */     }
/* 132:323 */     return random(count, 0, chars.length, false, false, chars, RANDOM);
/* 133:    */   }
/* 134:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.RandomStringUtils
 * JD-Core Version:    0.7.0.1
 */