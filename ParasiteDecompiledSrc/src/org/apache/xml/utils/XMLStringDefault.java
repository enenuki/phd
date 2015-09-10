/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.util.Locale;
/*   4:    */ import org.xml.sax.ContentHandler;
/*   5:    */ import org.xml.sax.SAXException;
/*   6:    */ import org.xml.sax.ext.LexicalHandler;
/*   7:    */ 
/*   8:    */ public class XMLStringDefault
/*   9:    */   implements XMLString
/*  10:    */ {
/*  11:    */   private String m_str;
/*  12:    */   
/*  13:    */   public XMLStringDefault(String str)
/*  14:    */   {
/*  15: 39 */     this.m_str = str;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void dispatchCharactersEvents(ContentHandler ch)
/*  19:    */     throws SAXException
/*  20:    */   {}
/*  21:    */   
/*  22:    */   public void dispatchAsComment(LexicalHandler lh)
/*  23:    */     throws SAXException
/*  24:    */   {}
/*  25:    */   
/*  26:    */   public XMLString fixWhiteSpace(boolean trimHead, boolean trimTail, boolean doublePunctuationSpaces)
/*  27:    */   {
/*  28: 90 */     return new XMLStringDefault(this.m_str.trim());
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int length()
/*  32:    */   {
/*  33:101 */     return this.m_str.length();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public char charAt(int index)
/*  37:    */   {
/*  38:119 */     return this.m_str.charAt(index);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
/*  42:    */   {
/*  43:146 */     int destIndex = dstBegin;
/*  44:147 */     for (int i = srcBegin; i < srcEnd; i++) {
/*  45:149 */       dst[(destIndex++)] = this.m_str.charAt(i);
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean equals(String obj2)
/*  50:    */   {
/*  51:166 */     return this.m_str.equals(obj2);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean equals(XMLString anObject)
/*  55:    */   {
/*  56:184 */     return this.m_str.equals(anObject.toString());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean equals(Object anObject)
/*  60:    */   {
/*  61:203 */     return this.m_str.equals(anObject);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean equalsIgnoreCase(String anotherString)
/*  65:    */   {
/*  66:223 */     return this.m_str.equalsIgnoreCase(anotherString);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int compareTo(XMLString anotherString)
/*  70:    */   {
/*  71:240 */     return this.m_str.compareTo(anotherString.toString());
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int compareToIgnoreCase(XMLString str)
/*  75:    */   {
/*  76:263 */     return this.m_str.compareToIgnoreCase(str.toString());
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean startsWith(String prefix, int toffset)
/*  80:    */   {
/*  81:287 */     return this.m_str.startsWith(prefix, toffset);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean startsWith(XMLString prefix, int toffset)
/*  85:    */   {
/*  86:311 */     return this.m_str.startsWith(prefix.toString(), toffset);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean startsWith(String prefix)
/*  90:    */   {
/*  91:331 */     return this.m_str.startsWith(prefix);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean startsWith(XMLString prefix)
/*  95:    */   {
/*  96:351 */     return this.m_str.startsWith(prefix.toString());
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean endsWith(String suffix)
/* 100:    */   {
/* 101:369 */     return this.m_str.endsWith(suffix);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int hashCode()
/* 105:    */   {
/* 106:387 */     return this.m_str.hashCode();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int indexOf(int ch)
/* 110:    */   {
/* 111:409 */     return this.m_str.indexOf(ch);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int indexOf(int ch, int fromIndex)
/* 115:    */   {
/* 116:442 */     return this.m_str.indexOf(ch, fromIndex);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public int lastIndexOf(int ch)
/* 120:    */   {
/* 121:462 */     return this.m_str.lastIndexOf(ch);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int lastIndexOf(int ch, int fromIndex)
/* 125:    */   {
/* 126:490 */     return this.m_str.lastIndexOf(ch, fromIndex);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int indexOf(String str)
/* 130:    */   {
/* 131:512 */     return this.m_str.indexOf(str);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int indexOf(XMLString str)
/* 135:    */   {
/* 136:534 */     return this.m_str.indexOf(str.toString());
/* 137:    */   }
/* 138:    */   
/* 139:    */   public int indexOf(String str, int fromIndex)
/* 140:    */   {
/* 141:565 */     return this.m_str.indexOf(str, fromIndex);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int lastIndexOf(String str)
/* 145:    */   {
/* 146:588 */     return this.m_str.lastIndexOf(str);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public int lastIndexOf(String str, int fromIndex)
/* 150:    */   {
/* 151:613 */     return this.m_str.lastIndexOf(str, fromIndex);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public XMLString substring(int beginIndex)
/* 155:    */   {
/* 156:635 */     return new XMLStringDefault(this.m_str.substring(beginIndex));
/* 157:    */   }
/* 158:    */   
/* 159:    */   public XMLString substring(int beginIndex, int endIndex)
/* 160:    */   {
/* 161:656 */     return new XMLStringDefault(this.m_str.substring(beginIndex, endIndex));
/* 162:    */   }
/* 163:    */   
/* 164:    */   public XMLString concat(String str)
/* 165:    */   {
/* 166:671 */     return new XMLStringDefault(this.m_str.concat(str));
/* 167:    */   }
/* 168:    */   
/* 169:    */   public XMLString toLowerCase(Locale locale)
/* 170:    */   {
/* 171:685 */     return new XMLStringDefault(this.m_str.toLowerCase(locale));
/* 172:    */   }
/* 173:    */   
/* 174:    */   public XMLString toLowerCase()
/* 175:    */   {
/* 176:700 */     return new XMLStringDefault(this.m_str.toLowerCase());
/* 177:    */   }
/* 178:    */   
/* 179:    */   public XMLString toUpperCase(Locale locale)
/* 180:    */   {
/* 181:713 */     return new XMLStringDefault(this.m_str.toUpperCase(locale));
/* 182:    */   }
/* 183:    */   
/* 184:    */   public XMLString toUpperCase()
/* 185:    */   {
/* 186:744 */     return new XMLStringDefault(this.m_str.toUpperCase());
/* 187:    */   }
/* 188:    */   
/* 189:    */   public XMLString trim()
/* 190:    */   {
/* 191:778 */     return new XMLStringDefault(this.m_str.trim());
/* 192:    */   }
/* 193:    */   
/* 194:    */   public String toString()
/* 195:    */   {
/* 196:788 */     return this.m_str;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean hasString()
/* 200:    */   {
/* 201:798 */     return true;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public double toDouble()
/* 205:    */   {
/* 206:    */     try
/* 207:    */     {
/* 208:811 */       return Double.valueOf(this.m_str).doubleValue();
/* 209:    */     }
/* 210:    */     catch (NumberFormatException nfe) {}
/* 211:815 */     return (0.0D / 0.0D);
/* 212:    */   }
/* 213:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.XMLStringDefault
 * JD-Core Version:    0.7.0.1
 */