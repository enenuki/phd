/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.NoSuchElementException;
/*   6:    */ import org.apache.commons.lang.text.StrBuilder;
/*   7:    */ 
/*   8:    */ public final class CharRange
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 8270183163158333422L;
/*  12:    */   private final char start;
/*  13:    */   private final char end;
/*  14:    */   private final boolean negated;
/*  15:    */   private transient String iToString;
/*  16:    */   
/*  17:    */   public static CharRange is(char ch)
/*  18:    */   {
/*  19: 67 */     return new CharRange(ch, ch, false);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static CharRange isNot(char ch)
/*  23:    */   {
/*  24: 79 */     return new CharRange(ch, ch, true);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static CharRange isIn(char start, char end)
/*  28:    */   {
/*  29: 92 */     return new CharRange(start, end, false);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static CharRange isNotIn(char start, char end)
/*  33:    */   {
/*  34:105 */     return new CharRange(start, end, true);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public CharRange(char ch)
/*  38:    */   {
/*  39:115 */     this(ch, ch, false);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public CharRange(char ch, boolean negated)
/*  43:    */   {
/*  44:128 */     this(ch, ch, negated);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public CharRange(char start, char end)
/*  48:    */   {
/*  49:138 */     this(start, end, false);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public CharRange(char start, char end, boolean negated)
/*  53:    */   {
/*  54:157 */     if (start > end)
/*  55:    */     {
/*  56:158 */       char temp = start;
/*  57:159 */       start = end;
/*  58:160 */       end = temp;
/*  59:    */     }
/*  60:163 */     this.start = start;
/*  61:164 */     this.end = end;
/*  62:165 */     this.negated = negated;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public char getStart()
/*  66:    */   {
/*  67:176 */     return this.start;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public char getEnd()
/*  71:    */   {
/*  72:185 */     return this.end;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean isNegated()
/*  76:    */   {
/*  77:197 */     return this.negated;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean contains(char ch)
/*  81:    */   {
/*  82:209 */     return ((ch >= this.start) && (ch <= this.end)) != this.negated;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean contains(CharRange range)
/*  86:    */   {
/*  87:221 */     if (range == null) {
/*  88:222 */       throw new IllegalArgumentException("The Range must not be null");
/*  89:    */     }
/*  90:224 */     if (this.negated)
/*  91:    */     {
/*  92:225 */       if (range.negated) {
/*  93:226 */         return (this.start >= range.start) && (this.end <= range.end);
/*  94:    */       }
/*  95:228 */       return (range.end < this.start) || (range.start > this.end);
/*  96:    */     }
/*  97:230 */     if (range.negated) {
/*  98:231 */       return (this.start == 0) && (this.end == 65535);
/*  99:    */     }
/* 100:233 */     return (this.start <= range.start) && (this.end >= range.end);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean equals(Object obj)
/* 104:    */   {
/* 105:246 */     if (obj == this) {
/* 106:247 */       return true;
/* 107:    */     }
/* 108:249 */     if (!(obj instanceof CharRange)) {
/* 109:250 */       return false;
/* 110:    */     }
/* 111:252 */     CharRange other = (CharRange)obj;
/* 112:253 */     return (this.start == other.start) && (this.end == other.end) && (this.negated == other.negated);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int hashCode()
/* 116:    */   {
/* 117:262 */     return 'S' + this.start + '\007' * this.end + (this.negated ? 1 : 0);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String toString()
/* 121:    */   {
/* 122:271 */     if (this.iToString == null)
/* 123:    */     {
/* 124:272 */       StrBuilder buf = new StrBuilder(4);
/* 125:273 */       if (isNegated()) {
/* 126:274 */         buf.append('^');
/* 127:    */       }
/* 128:276 */       buf.append(this.start);
/* 129:277 */       if (this.start != this.end)
/* 130:    */       {
/* 131:278 */         buf.append('-');
/* 132:279 */         buf.append(this.end);
/* 133:    */       }
/* 134:281 */       this.iToString = buf.toString();
/* 135:    */     }
/* 136:283 */     return this.iToString;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Iterator iterator()
/* 140:    */   {
/* 141:296 */     return new CharacterIterator(this, null);
/* 142:    */   }
/* 143:    */   
/* 144:    */   private static class CharacterIterator
/* 145:    */     implements Iterator
/* 146:    */   {
/* 147:    */     private char current;
/* 148:    */     private final CharRange range;
/* 149:    */     private boolean hasNext;
/* 150:    */     
/* 151:    */     CharacterIterator(CharRange x0, CharRange.1 x1)
/* 152:    */     {
/* 153:303 */       this(x0);
/* 154:    */     }
/* 155:    */     
/* 156:    */     private CharacterIterator(CharRange r)
/* 157:    */     {
/* 158:316 */       this.range = r;
/* 159:317 */       this.hasNext = true;
/* 160:319 */       if (this.range.negated)
/* 161:    */       {
/* 162:320 */         if (this.range.start == 0)
/* 163:    */         {
/* 164:321 */           if (this.range.end == 65535) {
/* 165:323 */             this.hasNext = false;
/* 166:    */           } else {
/* 167:325 */             this.current = ((char)(this.range.end + '\001'));
/* 168:    */           }
/* 169:    */         }
/* 170:    */         else {
/* 171:328 */           this.current = '\000';
/* 172:    */         }
/* 173:    */       }
/* 174:    */       else {
/* 175:331 */         this.current = this.range.start;
/* 176:    */       }
/* 177:    */     }
/* 178:    */     
/* 179:    */     private void prepareNext()
/* 180:    */     {
/* 181:339 */       if (this.range.negated)
/* 182:    */       {
/* 183:340 */         if (this.current == 65535) {
/* 184:341 */           this.hasNext = false;
/* 185:342 */         } else if (this.current + '\001' == this.range.start)
/* 186:    */         {
/* 187:343 */           if (this.range.end == 65535) {
/* 188:344 */             this.hasNext = false;
/* 189:    */           } else {
/* 190:346 */             this.current = ((char)(this.range.end + '\001'));
/* 191:    */           }
/* 192:    */         }
/* 193:    */         else {
/* 194:349 */           this.current = ((char)(this.current + '\001'));
/* 195:    */         }
/* 196:    */       }
/* 197:351 */       else if (this.current < this.range.end) {
/* 198:352 */         this.current = ((char)(this.current + '\001'));
/* 199:    */       } else {
/* 200:354 */         this.hasNext = false;
/* 201:    */       }
/* 202:    */     }
/* 203:    */     
/* 204:    */     public boolean hasNext()
/* 205:    */     {
/* 206:364 */       return this.hasNext;
/* 207:    */     }
/* 208:    */     
/* 209:    */     public Object next()
/* 210:    */     {
/* 211:373 */       if (!this.hasNext) {
/* 212:374 */         throw new NoSuchElementException();
/* 213:    */       }
/* 214:376 */       char cur = this.current;
/* 215:377 */       prepareNext();
/* 216:378 */       return new Character(cur);
/* 217:    */     }
/* 218:    */     
/* 219:    */     public void remove()
/* 220:    */     {
/* 221:388 */       throw new UnsupportedOperationException();
/* 222:    */     }
/* 223:    */   }
/* 224:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.CharRange
 * JD-Core Version:    0.7.0.1
 */