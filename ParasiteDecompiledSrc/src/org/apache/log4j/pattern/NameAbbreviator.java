/*   1:    */ package org.apache.log4j.pattern;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ 
/*   6:    */ public abstract class NameAbbreviator
/*   7:    */ {
/*   8: 32 */   private static final NameAbbreviator DEFAULT = new NOPAbbreviator();
/*   9:    */   
/*  10:    */   public static NameAbbreviator getAbbreviator(String pattern)
/*  11:    */   {
/*  12: 47 */     if (pattern.length() > 0)
/*  13:    */     {
/*  14: 50 */       String trimmed = pattern.trim();
/*  15: 52 */       if (trimmed.length() == 0) {
/*  16: 53 */         return DEFAULT;
/*  17:    */       }
/*  18: 56 */       int i = 0;
/*  19: 57 */       if (trimmed.length() > 0)
/*  20:    */       {
/*  21: 58 */         if (trimmed.charAt(0) == '-') {
/*  22: 59 */           i++;
/*  23:    */         }
/*  24: 63 */         while ((i < trimmed.length()) && (trimmed.charAt(i) >= '0') && (trimmed.charAt(i) <= '9')) {
/*  25: 65 */           i++;
/*  26:    */         }
/*  27:    */       }
/*  28: 73 */       if (i == trimmed.length())
/*  29:    */       {
/*  30: 74 */         int elements = Integer.parseInt(trimmed);
/*  31: 75 */         if (elements >= 0) {
/*  32: 76 */           return new MaxElementAbbreviator(elements);
/*  33:    */         }
/*  34: 78 */         return new DropElementAbbreviator(-elements);
/*  35:    */       }
/*  36: 82 */       ArrayList fragments = new ArrayList(5);
/*  37:    */       
/*  38:    */ 
/*  39: 85 */       int pos = 0;
/*  40: 87 */       while ((pos < trimmed.length()) && (pos >= 0))
/*  41:    */       {
/*  42: 88 */         int ellipsisPos = pos;
/*  43:    */         int charCount;
/*  44: 90 */         if (trimmed.charAt(pos) == '*')
/*  45:    */         {
/*  46: 91 */           int charCount = 2147483647;
/*  47: 92 */           ellipsisPos++;
/*  48:    */         }
/*  49: 94 */         else if ((trimmed.charAt(pos) >= '0') && (trimmed.charAt(pos) <= '9'))
/*  50:    */         {
/*  51: 95 */           int charCount = trimmed.charAt(pos) - '0';
/*  52: 96 */           ellipsisPos++;
/*  53:    */         }
/*  54:    */         else
/*  55:    */         {
/*  56: 98 */           charCount = 0;
/*  57:    */         }
/*  58:102 */         char ellipsis = '\000';
/*  59:104 */         if (ellipsisPos < trimmed.length())
/*  60:    */         {
/*  61:105 */           ellipsis = trimmed.charAt(ellipsisPos);
/*  62:107 */           if (ellipsis == '.') {
/*  63:108 */             ellipsis = '\000';
/*  64:    */           }
/*  65:    */         }
/*  66:112 */         fragments.add(new PatternAbbreviatorFragment(charCount, ellipsis));
/*  67:113 */         pos = trimmed.indexOf(".", pos);
/*  68:115 */         if (pos == -1) {
/*  69:    */           break;
/*  70:    */         }
/*  71:119 */         pos++;
/*  72:    */       }
/*  73:122 */       return new PatternAbbreviator(fragments);
/*  74:    */     }
/*  75:128 */     return DEFAULT;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static NameAbbreviator getDefaultAbbreviator()
/*  79:    */   {
/*  80:137 */     return DEFAULT;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public abstract void abbreviate(int paramInt, StringBuffer paramStringBuffer);
/*  84:    */   
/*  85:    */   private static class NOPAbbreviator
/*  86:    */     extends NameAbbreviator
/*  87:    */   {
/*  88:    */     public void abbreviate(int nameStart, StringBuffer buf) {}
/*  89:    */   }
/*  90:    */   
/*  91:    */   private static class MaxElementAbbreviator
/*  92:    */     extends NameAbbreviator
/*  93:    */   {
/*  94:    */     private final int count;
/*  95:    */     
/*  96:    */     public MaxElementAbbreviator(int count)
/*  97:    */     {
/*  98:179 */       this.count = count;
/*  99:    */     }
/* 100:    */     
/* 101:    */     public void abbreviate(int nameStart, StringBuffer buf)
/* 102:    */     {
/* 103:191 */       int end = buf.length() - 1;
/* 104:    */       
/* 105:193 */       String bufString = buf.toString();
/* 106:194 */       for (int i = this.count; i > 0; i--)
/* 107:    */       {
/* 108:195 */         end = bufString.lastIndexOf(".", end - 1);
/* 109:197 */         if ((end == -1) || (end < nameStart)) {
/* 110:198 */           return;
/* 111:    */         }
/* 112:    */       }
/* 113:202 */       buf.delete(nameStart, end + 1);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   private static class DropElementAbbreviator
/* 118:    */     extends NameAbbreviator
/* 119:    */   {
/* 120:    */     private final int count;
/* 121:    */     
/* 122:    */     public DropElementAbbreviator(int count)
/* 123:    */     {
/* 124:220 */       this.count = count;
/* 125:    */     }
/* 126:    */     
/* 127:    */     public void abbreviate(int nameStart, StringBuffer buf)
/* 128:    */     {
/* 129:229 */       int i = this.count;
/* 130:230 */       for (int pos = buf.indexOf(".", nameStart); pos != -1; pos = buf.indexOf(".", pos + 1))
/* 131:    */       {
/* 132:233 */         i--;
/* 133:233 */         if (i == 0)
/* 134:    */         {
/* 135:234 */           buf.delete(nameStart, pos + 1);
/* 136:235 */           break;
/* 137:    */         }
/* 138:    */       }
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   private static class PatternAbbreviatorFragment
/* 143:    */   {
/* 144:    */     private final int charCount;
/* 145:    */     private final char ellipsis;
/* 146:    */     
/* 147:    */     public PatternAbbreviatorFragment(int charCount, char ellipsis)
/* 148:    */     {
/* 149:266 */       this.charCount = charCount;
/* 150:267 */       this.ellipsis = ellipsis;
/* 151:    */     }
/* 152:    */     
/* 153:    */     public int abbreviate(StringBuffer buf, int startPos)
/* 154:    */     {
/* 155:277 */       int nextDot = buf.toString().indexOf(".", startPos);
/* 156:279 */       if (nextDot != -1)
/* 157:    */       {
/* 158:280 */         if (nextDot - startPos > this.charCount)
/* 159:    */         {
/* 160:281 */           buf.delete(startPos + this.charCount, nextDot);
/* 161:282 */           nextDot = startPos + this.charCount;
/* 162:284 */           if (this.ellipsis != 0)
/* 163:    */           {
/* 164:285 */             buf.insert(nextDot, this.ellipsis);
/* 165:286 */             nextDot++;
/* 166:    */           }
/* 167:    */         }
/* 168:290 */         nextDot++;
/* 169:    */       }
/* 170:293 */       return nextDot;
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   private static class PatternAbbreviator
/* 175:    */     extends NameAbbreviator
/* 176:    */   {
/* 177:    */     private final NameAbbreviator.PatternAbbreviatorFragment[] fragments;
/* 178:    */     
/* 179:    */     public PatternAbbreviator(List fragments)
/* 180:    */     {
/* 181:314 */       if (fragments.size() == 0) {
/* 182:315 */         throw new IllegalArgumentException("fragments must have at least one element");
/* 183:    */       }
/* 184:319 */       this.fragments = new NameAbbreviator.PatternAbbreviatorFragment[fragments.size()];
/* 185:320 */       fragments.toArray(this.fragments);
/* 186:    */     }
/* 187:    */     
/* 188:    */     public void abbreviate(int nameStart, StringBuffer buf)
/* 189:    */     {
/* 190:332 */       int pos = nameStart;
/* 191:334 */       for (int i = 0; (i < this.fragments.length - 1) && (pos < buf.length()); i++) {
/* 192:336 */         pos = this.fragments[i].abbreviate(buf, pos);
/* 193:    */       }
/* 194:342 */       NameAbbreviator.PatternAbbreviatorFragment terminalFragment = this.fragments[(this.fragments.length - 1)];
/* 195:345 */       while ((pos < buf.length()) && (pos >= 0)) {
/* 196:346 */         pos = terminalFragment.abbreviate(buf, pos);
/* 197:    */       }
/* 198:    */     }
/* 199:    */   }
/* 200:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.NameAbbreviator
 * JD-Core Version:    0.7.0.1
 */