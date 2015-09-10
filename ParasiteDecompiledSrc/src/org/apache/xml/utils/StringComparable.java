/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.text.CollationElementIterator;
/*   4:    */ import java.text.Collator;
/*   5:    */ import java.text.RuleBasedCollator;
/*   6:    */ import java.util.Locale;
/*   7:    */ 
/*   8:    */ public class StringComparable
/*   9:    */   implements Comparable
/*  10:    */ {
/*  11:    */   public static final int UNKNOWN_CASE = -1;
/*  12:    */   public static final int UPPER_CASE = 1;
/*  13:    */   public static final int LOWER_CASE = 2;
/*  14:    */   private String m_text;
/*  15:    */   private Locale m_locale;
/*  16:    */   private RuleBasedCollator m_collator;
/*  17:    */   private String m_caseOrder;
/*  18: 46 */   private int m_mask = -1;
/*  19:    */   
/*  20:    */   public StringComparable(String text, Locale locale, Collator collator, String caseOrder)
/*  21:    */   {
/*  22: 49 */     this.m_text = text;
/*  23: 50 */     this.m_locale = locale;
/*  24: 51 */     this.m_collator = ((RuleBasedCollator)collator);
/*  25: 52 */     this.m_caseOrder = caseOrder;
/*  26: 53 */     this.m_mask = getMask(this.m_collator.getStrength());
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static final Comparable getComparator(String text, Locale locale, Collator collator, String caseOrder)
/*  30:    */   {
/*  31: 57 */     if ((caseOrder == null) || (caseOrder.length() == 0)) {
/*  32: 58 */       return ((RuleBasedCollator)collator).getCollationKey(text);
/*  33:    */     }
/*  34: 60 */     return new StringComparable(text, locale, collator, caseOrder);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public final String toString()
/*  38:    */   {
/*  39: 64 */     return this.m_text;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int compareTo(Object o)
/*  43:    */   {
/*  44: 67 */     String pattern = ((StringComparable)o).toString();
/*  45: 68 */     if (this.m_text.equals(pattern)) {
/*  46: 69 */       return 0;
/*  47:    */     }
/*  48: 71 */     int savedStrength = this.m_collator.getStrength();
/*  49: 72 */     int comp = 0;
/*  50: 74 */     if ((savedStrength == 0) || (savedStrength == 1))
/*  51:    */     {
/*  52: 75 */       comp = this.m_collator.compare(this.m_text, pattern);
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56: 77 */       this.m_collator.setStrength(1);
/*  57: 78 */       comp = this.m_collator.compare(this.m_text, pattern);
/*  58: 79 */       this.m_collator.setStrength(savedStrength);
/*  59:    */     }
/*  60: 81 */     if (comp != 0) {
/*  61: 82 */       return comp;
/*  62:    */     }
/*  63: 87 */     comp = getCaseDiff(this.m_text, pattern);
/*  64: 88 */     if (comp != 0) {
/*  65: 89 */       return comp;
/*  66:    */     }
/*  67: 91 */     return this.m_collator.compare(this.m_text, pattern);
/*  68:    */   }
/*  69:    */   
/*  70:    */   private final int getCaseDiff(String text, String pattern)
/*  71:    */   {
/*  72: 97 */     int savedStrength = this.m_collator.getStrength();
/*  73: 98 */     int savedDecomposition = this.m_collator.getDecomposition();
/*  74: 99 */     this.m_collator.setStrength(2);
/*  75:100 */     this.m_collator.setDecomposition(1);
/*  76:    */     
/*  77:102 */     int[] diff = getFirstCaseDiff(text, pattern, this.m_locale);
/*  78:103 */     this.m_collator.setStrength(savedStrength);
/*  79:104 */     this.m_collator.setDecomposition(savedDecomposition);
/*  80:105 */     if (diff != null)
/*  81:    */     {
/*  82:106 */       if (this.m_caseOrder.equals("upper-first"))
/*  83:    */       {
/*  84:107 */         if (diff[0] == 1) {
/*  85:108 */           return -1;
/*  86:    */         }
/*  87:110 */         return 1;
/*  88:    */       }
/*  89:113 */       if (diff[0] == 2) {
/*  90:114 */         return -1;
/*  91:    */       }
/*  92:116 */       return 1;
/*  93:    */     }
/*  94:120 */     return 0;
/*  95:    */   }
/*  96:    */   
/*  97:    */   private final int[] getFirstCaseDiff(String text, String pattern, Locale locale)
/*  98:    */   {
/*  99:129 */     CollationElementIterator targIter = this.m_collator.getCollationElementIterator(text);
/* 100:130 */     CollationElementIterator patIter = this.m_collator.getCollationElementIterator(pattern);
/* 101:131 */     int startTarg = -1;
/* 102:132 */     int endTarg = -1;
/* 103:133 */     int startPatt = -1;
/* 104:134 */     int endPatt = -1;
/* 105:135 */     int done = getElement(-1);
/* 106:136 */     int patternElement = 0;int targetElement = 0;
/* 107:137 */     boolean getPattern = true;boolean getTarget = true;
/* 108:    */     int[] diff;
/* 109:    */     do
/* 110:    */     {
/* 111:    */       String subText;
/* 112:    */       String subPatt;
/* 113:    */       String subTextUp;
/* 114:    */       String subPattUp;
/* 115:    */       do
/* 116:    */       {
/* 117:    */         do
/* 118:    */         {
/* 119:    */           for (;;)
/* 120:    */           {
/* 121:140 */             if (getPattern)
/* 122:    */             {
/* 123:141 */               startPatt = patIter.getOffset();
/* 124:142 */               patternElement = getElement(patIter.next());
/* 125:143 */               endPatt = patIter.getOffset();
/* 126:    */             }
/* 127:145 */             if (getTarget)
/* 128:    */             {
/* 129:146 */               startTarg = targIter.getOffset();
/* 130:147 */               targetElement = getElement(targIter.next());
/* 131:148 */               endTarg = targIter.getOffset();
/* 132:    */             }
/* 133:150 */             getTarget = getPattern = 1;
/* 134:151 */             if ((patternElement == done) || (targetElement == done)) {
/* 135:152 */               return null;
/* 136:    */             }
/* 137:153 */             if (targetElement == 0)
/* 138:    */             {
/* 139:154 */               getPattern = false;
/* 140:    */             }
/* 141:    */             else
/* 142:    */             {
/* 143:155 */               if (patternElement != 0) {
/* 144:    */                 break;
/* 145:    */               }
/* 146:156 */               getTarget = false;
/* 147:    */             }
/* 148:    */           }
/* 149:157 */         } while ((targetElement == patternElement) || 
/* 150:158 */           (startPatt >= endPatt) || (startTarg >= endTarg));
/* 151:159 */         subText = text.substring(startTarg, endTarg);
/* 152:160 */         subPatt = pattern.substring(startPatt, endPatt);
/* 153:161 */         subTextUp = subText.toUpperCase(locale);
/* 154:162 */         subPattUp = subPatt.toUpperCase(locale);
/* 155:163 */       } while (this.m_collator.compare(subTextUp, subPattUp) != 0);
/* 156:167 */       diff = new int[] { -1, -1 };
/* 157:168 */       if (this.m_collator.compare(subText, subTextUp) == 0) {
/* 158:169 */         diff[0] = 1;
/* 159:170 */       } else if (this.m_collator.compare(subText, subText.toLowerCase(locale)) == 0) {
/* 160:171 */         diff[0] = 2;
/* 161:    */       }
/* 162:173 */       if (this.m_collator.compare(subPatt, subPattUp) == 0) {
/* 163:174 */         diff[1] = 1;
/* 164:175 */       } else if (this.m_collator.compare(subPatt, subPatt.toLowerCase(locale)) == 0) {
/* 165:176 */         diff[1] = 2;
/* 166:    */       }
/* 167:179 */     } while (((diff[0] != 1) || (diff[1] != 2)) && ((diff[0] != 2) || (diff[1] != 1)));
/* 168:181 */     return diff;
/* 169:    */   }
/* 170:    */   
/* 171:    */   private static final int getMask(int strength)
/* 172:    */   {
/* 173:197 */     switch (strength)
/* 174:    */     {
/* 175:    */     case 0: 
/* 176:199 */       return -65536;
/* 177:    */     case 1: 
/* 178:201 */       return -256;
/* 179:    */     }
/* 180:203 */     return -1;
/* 181:    */   }
/* 182:    */   
/* 183:    */   private final int getElement(int maxStrengthElement)
/* 184:    */   {
/* 185:210 */     return maxStrengthElement & this.m_mask;
/* 186:    */   }
/* 187:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.StringComparable
 * JD-Core Version:    0.7.0.1
 */