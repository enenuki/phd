/*   1:    */ package org.apache.commons.lang.text;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ 
/*   5:    */ public abstract class StrMatcher
/*   6:    */ {
/*   7: 37 */   private static final StrMatcher COMMA_MATCHER = new CharMatcher(',');
/*   8: 41 */   private static final StrMatcher TAB_MATCHER = new CharMatcher('\t');
/*   9: 45 */   private static final StrMatcher SPACE_MATCHER = new CharMatcher(' ');
/*  10: 50 */   private static final StrMatcher SPLIT_MATCHER = new CharSetMatcher(" \t\n\r\f".toCharArray());
/*  11: 54 */   private static final StrMatcher TRIM_MATCHER = new TrimMatcher();
/*  12: 58 */   private static final StrMatcher SINGLE_QUOTE_MATCHER = new CharMatcher('\'');
/*  13: 62 */   private static final StrMatcher DOUBLE_QUOTE_MATCHER = new CharMatcher('"');
/*  14: 66 */   private static final StrMatcher QUOTE_MATCHER = new CharSetMatcher("'\"".toCharArray());
/*  15: 70 */   private static final StrMatcher NONE_MATCHER = new NoMatcher();
/*  16:    */   
/*  17:    */   public static StrMatcher commaMatcher()
/*  18:    */   {
/*  19: 80 */     return COMMA_MATCHER;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static StrMatcher tabMatcher()
/*  23:    */   {
/*  24: 89 */     return TAB_MATCHER;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static StrMatcher spaceMatcher()
/*  28:    */   {
/*  29: 98 */     return SPACE_MATCHER;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static StrMatcher splitMatcher()
/*  33:    */   {
/*  34:108 */     return SPLIT_MATCHER;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static StrMatcher trimMatcher()
/*  38:    */   {
/*  39:117 */     return TRIM_MATCHER;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static StrMatcher singleQuoteMatcher()
/*  43:    */   {
/*  44:126 */     return SINGLE_QUOTE_MATCHER;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static StrMatcher doubleQuoteMatcher()
/*  48:    */   {
/*  49:135 */     return DOUBLE_QUOTE_MATCHER;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static StrMatcher quoteMatcher()
/*  53:    */   {
/*  54:144 */     return QUOTE_MATCHER;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static StrMatcher noneMatcher()
/*  58:    */   {
/*  59:153 */     return NONE_MATCHER;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static StrMatcher charMatcher(char ch)
/*  63:    */   {
/*  64:163 */     return new CharMatcher(ch);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static StrMatcher charSetMatcher(char[] chars)
/*  68:    */   {
/*  69:173 */     if ((chars == null) || (chars.length == 0)) {
/*  70:174 */       return NONE_MATCHER;
/*  71:    */     }
/*  72:176 */     if (chars.length == 1) {
/*  73:177 */       return new CharMatcher(chars[0]);
/*  74:    */     }
/*  75:179 */     return new CharSetMatcher(chars);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static StrMatcher charSetMatcher(String chars)
/*  79:    */   {
/*  80:189 */     if ((chars == null) || (chars.length() == 0)) {
/*  81:190 */       return NONE_MATCHER;
/*  82:    */     }
/*  83:192 */     if (chars.length() == 1) {
/*  84:193 */       return new CharMatcher(chars.charAt(0));
/*  85:    */     }
/*  86:195 */     return new CharSetMatcher(chars.toCharArray());
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static StrMatcher stringMatcher(String str)
/*  90:    */   {
/*  91:205 */     if ((str == null) || (str.length() == 0)) {
/*  92:206 */       return NONE_MATCHER;
/*  93:    */     }
/*  94:208 */     return new StringMatcher(str);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public abstract int isMatch(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3);
/*  98:    */   
/*  99:    */   public int isMatch(char[] buffer, int pos)
/* 100:    */   {
/* 101:267 */     return isMatch(buffer, pos, 0, buffer.length);
/* 102:    */   }
/* 103:    */   
/* 104:    */   static final class CharSetMatcher
/* 105:    */     extends StrMatcher
/* 106:    */   {
/* 107:    */     private final char[] chars;
/* 108:    */     
/* 109:    */     CharSetMatcher(char[] chars)
/* 110:    */     {
/* 111:285 */       this.chars = ((char[])chars.clone());
/* 112:286 */       Arrays.sort(this.chars);
/* 113:    */     }
/* 114:    */     
/* 115:    */     public int isMatch(char[] buffer, int pos, int bufferStart, int bufferEnd)
/* 116:    */     {
/* 117:299 */       return Arrays.binarySearch(this.chars, buffer[pos]) >= 0 ? 1 : 0;
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   static final class CharMatcher
/* 122:    */     extends StrMatcher
/* 123:    */   {
/* 124:    */     private final char ch;
/* 125:    */     
/* 126:    */     CharMatcher(char ch)
/* 127:    */     {
/* 128:318 */       this.ch = ch;
/* 129:    */     }
/* 130:    */     
/* 131:    */     public int isMatch(char[] buffer, int pos, int bufferStart, int bufferEnd)
/* 132:    */     {
/* 133:331 */       return this.ch == buffer[pos] ? 1 : 0;
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   static final class StringMatcher
/* 138:    */     extends StrMatcher
/* 139:    */   {
/* 140:    */     private final char[] chars;
/* 141:    */     
/* 142:    */     StringMatcher(String str)
/* 143:    */     {
/* 144:350 */       this.chars = str.toCharArray();
/* 145:    */     }
/* 146:    */     
/* 147:    */     public int isMatch(char[] buffer, int pos, int bufferStart, int bufferEnd)
/* 148:    */     {
/* 149:363 */       int len = this.chars.length;
/* 150:364 */       if (pos + len > bufferEnd) {
/* 151:365 */         return 0;
/* 152:    */       }
/* 153:367 */       for (int i = 0; i < this.chars.length; pos++)
/* 154:    */       {
/* 155:368 */         if (this.chars[i] != buffer[pos]) {
/* 156:369 */           return 0;
/* 157:    */         }
/* 158:367 */         i++;
/* 159:    */       }
/* 160:372 */       return len;
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   static final class NoMatcher
/* 165:    */     extends StrMatcher
/* 166:    */   {
/* 167:    */     public int isMatch(char[] buffer, int pos, int bufferStart, int bufferEnd)
/* 168:    */     {
/* 169:399 */       return 0;
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   static final class TrimMatcher
/* 174:    */     extends StrMatcher
/* 175:    */   {
/* 176:    */     public int isMatch(char[] buffer, int pos, int bufferStart, int bufferEnd)
/* 177:    */     {
/* 178:426 */       return buffer[pos] <= ' ' ? 1 : 0;
/* 179:    */     }
/* 180:    */   }
/* 181:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.text.StrMatcher
 * JD-Core Version:    0.7.0.1
 */