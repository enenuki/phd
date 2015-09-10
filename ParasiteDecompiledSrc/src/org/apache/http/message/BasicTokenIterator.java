/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import java.util.NoSuchElementException;
/*   4:    */ import org.apache.http.Header;
/*   5:    */ import org.apache.http.HeaderIterator;
/*   6:    */ import org.apache.http.ParseException;
/*   7:    */ import org.apache.http.TokenIterator;
/*   8:    */ 
/*   9:    */ public class BasicTokenIterator
/*  10:    */   implements TokenIterator
/*  11:    */ {
/*  12:    */   public static final String HTTP_SEPARATORS = " ,;=()<>@:\\\"/[]?{}\t";
/*  13:    */   protected final HeaderIterator headerIt;
/*  14:    */   protected String currentHeader;
/*  15:    */   protected String currentToken;
/*  16:    */   protected int searchPos;
/*  17:    */   
/*  18:    */   public BasicTokenIterator(HeaderIterator headerIterator)
/*  19:    */   {
/*  20: 81 */     if (headerIterator == null) {
/*  21: 82 */       throw new IllegalArgumentException("Header iterator must not be null.");
/*  22:    */     }
/*  23: 86 */     this.headerIt = headerIterator;
/*  24: 87 */     this.searchPos = findNext(-1);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean hasNext()
/*  28:    */   {
/*  29: 93 */     return this.currentToken != null;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String nextToken()
/*  33:    */     throws NoSuchElementException, ParseException
/*  34:    */   {
/*  35:108 */     if (this.currentToken == null) {
/*  36:109 */       throw new NoSuchElementException("Iteration already finished.");
/*  37:    */     }
/*  38:112 */     String result = this.currentToken;
/*  39:    */     
/*  40:114 */     this.searchPos = findNext(this.searchPos);
/*  41:    */     
/*  42:116 */     return result;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public final Object next()
/*  46:    */     throws NoSuchElementException, ParseException
/*  47:    */   {
/*  48:131 */     return nextToken();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public final void remove()
/*  52:    */     throws UnsupportedOperationException
/*  53:    */   {
/*  54:143 */     throw new UnsupportedOperationException("Removing tokens is not supported.");
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected int findNext(int from)
/*  58:    */     throws ParseException
/*  59:    */   {
/*  60:167 */     if (from < 0)
/*  61:    */     {
/*  62:169 */       if (!this.headerIt.hasNext()) {
/*  63:170 */         return -1;
/*  64:    */       }
/*  65:172 */       this.currentHeader = this.headerIt.nextHeader().getValue();
/*  66:173 */       from = 0;
/*  67:    */     }
/*  68:    */     else
/*  69:    */     {
/*  70:176 */       from = findTokenSeparator(from);
/*  71:    */     }
/*  72:179 */     int start = findTokenStart(from);
/*  73:180 */     if (start < 0)
/*  74:    */     {
/*  75:181 */       this.currentToken = null;
/*  76:182 */       return -1;
/*  77:    */     }
/*  78:185 */     int end = findTokenEnd(start);
/*  79:186 */     this.currentToken = createToken(this.currentHeader, start, end);
/*  80:187 */     return end;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected String createToken(String value, int start, int end)
/*  84:    */   {
/*  85:212 */     return value.substring(start, end);
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected int findTokenStart(int from)
/*  89:    */   {
/*  90:227 */     if (from < 0) {
/*  91:228 */       throw new IllegalArgumentException("Search position must not be negative: " + from);
/*  92:    */     }
/*  93:232 */     boolean found = false;
/*  94:233 */     while ((!found) && (this.currentHeader != null))
/*  95:    */     {
/*  96:235 */       int to = this.currentHeader.length();
/*  97:236 */       while ((!found) && (from < to))
/*  98:    */       {
/*  99:238 */         char ch = this.currentHeader.charAt(from);
/* 100:239 */         if ((isTokenSeparator(ch)) || (isWhitespace(ch))) {
/* 101:241 */           from++;
/* 102:242 */         } else if (isTokenChar(this.currentHeader.charAt(from))) {
/* 103:244 */           found = true;
/* 104:    */         } else {
/* 105:246 */           throw new ParseException("Invalid character before token (pos " + from + "): " + this.currentHeader);
/* 106:    */         }
/* 107:    */       }
/* 108:251 */       if (!found) {
/* 109:252 */         if (this.headerIt.hasNext())
/* 110:    */         {
/* 111:253 */           this.currentHeader = this.headerIt.nextHeader().getValue();
/* 112:254 */           from = 0;
/* 113:    */         }
/* 114:    */         else
/* 115:    */         {
/* 116:256 */           this.currentHeader = null;
/* 117:    */         }
/* 118:    */       }
/* 119:    */     }
/* 120:261 */     return found ? from : -1;
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected int findTokenSeparator(int from)
/* 124:    */   {
/* 125:283 */     if (from < 0) {
/* 126:284 */       throw new IllegalArgumentException("Search position must not be negative: " + from);
/* 127:    */     }
/* 128:288 */     boolean found = false;
/* 129:289 */     int to = this.currentHeader.length();
/* 130:290 */     while ((!found) && (from < to))
/* 131:    */     {
/* 132:291 */       char ch = this.currentHeader.charAt(from);
/* 133:292 */       if (isTokenSeparator(ch))
/* 134:    */       {
/* 135:293 */         found = true;
/* 136:    */       }
/* 137:294 */       else if (isWhitespace(ch))
/* 138:    */       {
/* 139:295 */         from++;
/* 140:    */       }
/* 141:    */       else
/* 142:    */       {
/* 143:296 */         if (isTokenChar(ch)) {
/* 144:297 */           throw new ParseException("Tokens without separator (pos " + from + "): " + this.currentHeader);
/* 145:    */         }
/* 146:301 */         throw new ParseException("Invalid character after token (pos " + from + "): " + this.currentHeader);
/* 147:    */       }
/* 148:    */     }
/* 149:307 */     return from;
/* 150:    */   }
/* 151:    */   
/* 152:    */   protected int findTokenEnd(int from)
/* 153:    */   {
/* 154:323 */     if (from < 0) {
/* 155:324 */       throw new IllegalArgumentException("Token start position must not be negative: " + from);
/* 156:    */     }
/* 157:328 */     int to = this.currentHeader.length();
/* 158:329 */     int end = from + 1;
/* 159:330 */     while ((end < to) && (isTokenChar(this.currentHeader.charAt(end)))) {
/* 160:331 */       end++;
/* 161:    */     }
/* 162:334 */     return end;
/* 163:    */   }
/* 164:    */   
/* 165:    */   protected boolean isTokenSeparator(char ch)
/* 166:    */   {
/* 167:350 */     return ch == ',';
/* 168:    */   }
/* 169:    */   
/* 170:    */   protected boolean isWhitespace(char ch)
/* 171:    */   {
/* 172:369 */     return (ch == '\t') || (Character.isSpaceChar(ch));
/* 173:    */   }
/* 174:    */   
/* 175:    */   protected boolean isTokenChar(char ch)
/* 176:    */   {
/* 177:388 */     if (Character.isLetterOrDigit(ch)) {
/* 178:389 */       return true;
/* 179:    */     }
/* 180:392 */     if (Character.isISOControl(ch)) {
/* 181:393 */       return false;
/* 182:    */     }
/* 183:396 */     if (isHttpSeparator(ch)) {
/* 184:397 */       return false;
/* 185:    */     }
/* 186:405 */     return true;
/* 187:    */   }
/* 188:    */   
/* 189:    */   protected boolean isHttpSeparator(char ch)
/* 190:    */   {
/* 191:420 */     return " ,;=()<>@:\\\"/[]?{}\t".indexOf(ch) >= 0;
/* 192:    */   }
/* 193:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicTokenIterator
 * JD-Core Version:    0.7.0.1
 */