/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import java.text.NumberFormat;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.NoSuchElementException;
/*   6:    */ import org.w3c.dom.Element;
/*   7:    */ 
/*   8:    */ class NumeratorFormatter
/*   9:    */ {
/*  10:    */   protected Element m_xslNumberElement;
/*  11:    */   NumberFormatStringTokenizer m_formatTokenizer;
/*  12:    */   Locale m_locale;
/*  13:    */   NumberFormat m_formatter;
/*  14:    */   TransformerImpl m_processor;
/*  15: 55 */   private static final DecimalToRoman[] m_romanConvertTable = { new DecimalToRoman(1000L, "M", 900L, "CM"), new DecimalToRoman(500L, "D", 400L, "CD"), new DecimalToRoman(100L, "C", 90L, "XC"), new DecimalToRoman(50L, "L", 40L, "XL"), new DecimalToRoman(10L, "X", 9L, "IX"), new DecimalToRoman(5L, "V", 4L, "IV"), new DecimalToRoman(1L, "I", 1L, "I") };
/*  16: 68 */   private static final char[] m_alphaCountTable = { 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y' };
/*  17:    */   
/*  18:    */   NumeratorFormatter(Element xslNumberElement, TransformerImpl processor)
/*  19:    */   {
/*  20: 86 */     this.m_xslNumberElement = xslNumberElement;
/*  21: 87 */     this.m_processor = processor;
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected String int2alphaCount(int val, char[] table)
/*  25:    */   {
/*  26:105 */     int radix = table.length;
/*  27:    */     
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:110 */     char[] buf = new char[100];
/*  32:    */     
/*  33:    */ 
/*  34:113 */     int charPos = buf.length - 1;
/*  35:    */     
/*  36:    */ 
/*  37:116 */     int lookupIndex = 1;
/*  38:    */     
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:142 */     int correction = 0;
/*  64:    */     do
/*  65:    */     {
/*  66:151 */       correction = (lookupIndex == 0) || ((correction != 0) && (lookupIndex == radix - 1)) ? radix - 1 : 0;
/*  67:    */       
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:156 */       lookupIndex = (val + correction) % radix;
/*  72:    */       
/*  73:    */ 
/*  74:159 */       val /= radix;
/*  75:162 */       if ((lookupIndex == 0) && (val == 0)) {
/*  76:    */         break;
/*  77:    */       }
/*  78:166 */       buf[(charPos--)] = table[lookupIndex];
/*  79:168 */     } while (val > 0);
/*  80:170 */     return new String(buf, charPos + 1, buf.length - charPos - 1);
/*  81:    */   }
/*  82:    */   
/*  83:    */   String long2roman(long val, boolean prefixesAreOK)
/*  84:    */   {
/*  85:185 */     if (val <= 0L) {
/*  86:187 */       return "#E(" + val + ")";
/*  87:    */     }
/*  88:190 */     String roman = "";
/*  89:191 */     int place = 0;
/*  90:193 */     if (val <= 3999L) {
/*  91:    */       do
/*  92:    */       {
/*  93:197 */         while (val >= m_romanConvertTable[place].m_postValue)
/*  94:    */         {
/*  95:199 */           roman = roman + m_romanConvertTable[place].m_postLetter;
/*  96:200 */           val -= m_romanConvertTable[place].m_postValue;
/*  97:    */         }
/*  98:203 */         if (prefixesAreOK) {
/*  99:205 */           if (val >= m_romanConvertTable[place].m_preValue)
/* 100:    */           {
/* 101:207 */             roman = roman + m_romanConvertTable[place].m_preLetter;
/* 102:208 */             val -= m_romanConvertTable[place].m_preValue;
/* 103:    */           }
/* 104:    */         }
/* 105:212 */         place++;
/* 106:214 */       } while (val > 0L);
/* 107:    */     } else {
/* 108:218 */       roman = "#error";
/* 109:    */     }
/* 110:221 */     return roman;
/* 111:    */   }
/* 112:    */   
/* 113:    */   class NumberFormatStringTokenizer
/* 114:    */   {
/* 115:    */     private int currentPosition;
/* 116:    */     private int maxPosition;
/* 117:    */     private String str;
/* 118:    */     
/* 119:    */     NumberFormatStringTokenizer(String str)
/* 120:    */     {
/* 121:247 */       this.str = str;
/* 122:248 */       this.maxPosition = str.length();
/* 123:    */     }
/* 124:    */     
/* 125:    */     void reset()
/* 126:    */     {
/* 127:257 */       this.currentPosition = 0;
/* 128:    */     }
/* 129:    */     
/* 130:    */     String nextToken()
/* 131:    */     {
/* 132:270 */       if (this.currentPosition >= this.maxPosition) {
/* 133:272 */         throw new NoSuchElementException();
/* 134:    */       }
/* 135:275 */       int start = this.currentPosition;
/* 136:278 */       while ((this.currentPosition < this.maxPosition) && (Character.isLetterOrDigit(this.str.charAt(this.currentPosition)))) {
/* 137:280 */         this.currentPosition += 1;
/* 138:    */       }
/* 139:283 */       if ((start == this.currentPosition) && (!Character.isLetterOrDigit(this.str.charAt(this.currentPosition)))) {
/* 140:286 */         this.currentPosition += 1;
/* 141:    */       }
/* 142:289 */       return this.str.substring(start, this.currentPosition);
/* 143:    */     }
/* 144:    */     
/* 145:    */     boolean hasMoreTokens()
/* 146:    */     {
/* 147:299 */       return this.currentPosition < this.maxPosition;
/* 148:    */     }
/* 149:    */     
/* 150:    */     int countTokens()
/* 151:    */     {
/* 152:314 */       int count = 0;
/* 153:315 */       int currpos = this.currentPosition;
/* 154:317 */       while (currpos < this.maxPosition)
/* 155:    */       {
/* 156:319 */         int start = currpos;
/* 157:322 */         while ((currpos < this.maxPosition) && (Character.isLetterOrDigit(this.str.charAt(currpos)))) {
/* 158:324 */           currpos++;
/* 159:    */         }
/* 160:327 */         if ((start == currpos) && (!Character.isLetterOrDigit(this.str.charAt(currpos)))) {
/* 161:330 */           currpos++;
/* 162:    */         }
/* 163:333 */         count++;
/* 164:    */       }
/* 165:336 */       return count;
/* 166:    */     }
/* 167:    */   }
/* 168:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.NumeratorFormatter
 * JD-Core Version:    0.7.0.1
 */