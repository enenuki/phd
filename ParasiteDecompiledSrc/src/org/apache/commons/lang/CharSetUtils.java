/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import org.apache.commons.lang.text.StrBuilder;
/*   4:    */ 
/*   5:    */ public class CharSetUtils
/*   6:    */ {
/*   7:    */   /**
/*   8:    */    * @deprecated
/*   9:    */    */
/*  10:    */   public static CharSet evaluateSet(String[] set)
/*  11:    */   {
/*  12: 73 */     if (set == null) {
/*  13: 74 */       return null;
/*  14:    */     }
/*  15: 76 */     return new CharSet(set);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static String squeeze(String str, String set)
/*  19:    */   {
/*  20:100 */     if ((StringUtils.isEmpty(str)) || (StringUtils.isEmpty(set))) {
/*  21:101 */       return str;
/*  22:    */     }
/*  23:103 */     String[] strs = new String[1];
/*  24:104 */     strs[0] = set;
/*  25:105 */     return squeeze(str, strs);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static String squeeze(String str, String[] set)
/*  29:    */   {
/*  30:123 */     if ((StringUtils.isEmpty(str)) || (ArrayUtils.isEmpty(set))) {
/*  31:124 */       return str;
/*  32:    */     }
/*  33:126 */     CharSet chars = CharSet.getInstance(set);
/*  34:127 */     StrBuilder buffer = new StrBuilder(str.length());
/*  35:128 */     char[] chrs = str.toCharArray();
/*  36:129 */     int sz = chrs.length;
/*  37:130 */     char lastChar = ' ';
/*  38:131 */     char ch = ' ';
/*  39:132 */     for (int i = 0; i < sz; i++)
/*  40:    */     {
/*  41:133 */       ch = chrs[i];
/*  42:134 */       if ((!chars.contains(ch)) || 
/*  43:135 */         (ch != lastChar) || (i == 0))
/*  44:    */       {
/*  45:139 */         buffer.append(ch);
/*  46:140 */         lastChar = ch;
/*  47:    */       }
/*  48:    */     }
/*  49:142 */     return buffer.toString();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static int count(String str, String set)
/*  53:    */   {
/*  54:166 */     if ((StringUtils.isEmpty(str)) || (StringUtils.isEmpty(set))) {
/*  55:167 */       return 0;
/*  56:    */     }
/*  57:169 */     String[] strs = new String[1];
/*  58:170 */     strs[0] = set;
/*  59:171 */     return count(str, strs);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static int count(String str, String[] set)
/*  63:    */   {
/*  64:189 */     if ((StringUtils.isEmpty(str)) || (ArrayUtils.isEmpty(set))) {
/*  65:190 */       return 0;
/*  66:    */     }
/*  67:192 */     CharSet chars = CharSet.getInstance(set);
/*  68:193 */     int count = 0;
/*  69:194 */     char[] chrs = str.toCharArray();
/*  70:195 */     int sz = chrs.length;
/*  71:196 */     for (int i = 0; i < sz; i++) {
/*  72:197 */       if (chars.contains(chrs[i])) {
/*  73:198 */         count++;
/*  74:    */       }
/*  75:    */     }
/*  76:201 */     return count;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static String keep(String str, String set)
/*  80:    */   {
/*  81:226 */     if (str == null) {
/*  82:227 */       return null;
/*  83:    */     }
/*  84:229 */     if ((str.length() == 0) || (StringUtils.isEmpty(set))) {
/*  85:230 */       return "";
/*  86:    */     }
/*  87:232 */     String[] strs = new String[1];
/*  88:233 */     strs[0] = set;
/*  89:234 */     return keep(str, strs);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static String keep(String str, String[] set)
/*  93:    */   {
/*  94:254 */     if (str == null) {
/*  95:255 */       return null;
/*  96:    */     }
/*  97:257 */     if ((str.length() == 0) || (ArrayUtils.isEmpty(set))) {
/*  98:258 */       return "";
/*  99:    */     }
/* 100:260 */     return modify(str, set, true);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static String delete(String str, String set)
/* 104:    */   {
/* 105:284 */     if ((StringUtils.isEmpty(str)) || (StringUtils.isEmpty(set))) {
/* 106:285 */       return str;
/* 107:    */     }
/* 108:287 */     String[] strs = new String[1];
/* 109:288 */     strs[0] = set;
/* 110:289 */     return delete(str, strs);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static String delete(String str, String[] set)
/* 114:    */   {
/* 115:308 */     if ((StringUtils.isEmpty(str)) || (ArrayUtils.isEmpty(set))) {
/* 116:309 */       return str;
/* 117:    */     }
/* 118:311 */     return modify(str, set, false);
/* 119:    */   }
/* 120:    */   
/* 121:    */   private static String modify(String str, String[] set, boolean expect)
/* 122:    */   {
/* 123:324 */     CharSet chars = CharSet.getInstance(set);
/* 124:325 */     StrBuilder buffer = new StrBuilder(str.length());
/* 125:326 */     char[] chrs = str.toCharArray();
/* 126:327 */     int sz = chrs.length;
/* 127:328 */     for (int i = 0; i < sz; i++) {
/* 128:329 */       if (chars.contains(chrs[i]) == expect) {
/* 129:330 */         buffer.append(chrs[i]);
/* 130:    */       }
/* 131:    */     }
/* 132:333 */     return buffer.toString();
/* 133:    */   }
/* 134:    */   
/* 135:    */   /**
/* 136:    */    * @deprecated
/* 137:    */    */
/* 138:    */   public static String translate(String str, String searchChars, String replaceChars)
/* 139:    */   {
/* 140:371 */     if (StringUtils.isEmpty(str)) {
/* 141:372 */       return str;
/* 142:    */     }
/* 143:374 */     StrBuilder buffer = new StrBuilder(str.length());
/* 144:375 */     char[] chrs = str.toCharArray();
/* 145:376 */     char[] withChrs = replaceChars.toCharArray();
/* 146:377 */     int sz = chrs.length;
/* 147:378 */     int withMax = replaceChars.length() - 1;
/* 148:379 */     for (int i = 0; i < sz; i++)
/* 149:    */     {
/* 150:380 */       int idx = searchChars.indexOf(chrs[i]);
/* 151:381 */       if (idx != -1)
/* 152:    */       {
/* 153:382 */         if (idx > withMax) {
/* 154:383 */           idx = withMax;
/* 155:    */         }
/* 156:385 */         buffer.append(withChrs[idx]);
/* 157:    */       }
/* 158:    */       else
/* 159:    */       {
/* 160:387 */         buffer.append(chrs[i]);
/* 161:    */       }
/* 162:    */     }
/* 163:390 */     return buffer.toString();
/* 164:    */   }
/* 165:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.CharSetUtils
 * JD-Core Version:    0.7.0.1
 */