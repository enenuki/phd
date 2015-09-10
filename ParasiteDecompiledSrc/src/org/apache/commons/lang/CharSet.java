/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ 
/*  11:    */ public class CharSet
/*  12:    */   implements Serializable
/*  13:    */ {
/*  14:    */   private static final long serialVersionUID = 5947847346149275958L;
/*  15: 53 */   public static final CharSet EMPTY = new CharSet((String)null);
/*  16: 59 */   public static final CharSet ASCII_ALPHA = new CharSet("a-zA-Z");
/*  17: 65 */   public static final CharSet ASCII_ALPHA_LOWER = new CharSet("a-z");
/*  18: 71 */   public static final CharSet ASCII_ALPHA_UPPER = new CharSet("A-Z");
/*  19: 77 */   public static final CharSet ASCII_NUMERIC = new CharSet("0-9");
/*  20: 84 */   protected static final Map COMMON = Collections.synchronizedMap(new HashMap());
/*  21:    */   
/*  22:    */   static
/*  23:    */   {
/*  24: 87 */     COMMON.put(null, EMPTY);
/*  25: 88 */     COMMON.put("", EMPTY);
/*  26: 89 */     COMMON.put("a-zA-Z", ASCII_ALPHA);
/*  27: 90 */     COMMON.put("A-Za-z", ASCII_ALPHA);
/*  28: 91 */     COMMON.put("a-z", ASCII_ALPHA_LOWER);
/*  29: 92 */     COMMON.put("A-Z", ASCII_ALPHA_UPPER);
/*  30: 93 */     COMMON.put("0-9", ASCII_NUMERIC);
/*  31:    */   }
/*  32:    */   
/*  33: 97 */   private final Set set = Collections.synchronizedSet(new HashSet());
/*  34:    */   
/*  35:    */   public static CharSet getInstance(String setStr)
/*  36:    */   {
/*  37:144 */     Object set = COMMON.get(setStr);
/*  38:145 */     if (set != null) {
/*  39:146 */       return (CharSet)set;
/*  40:    */     }
/*  41:148 */     return new CharSet(setStr);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static CharSet getInstance(String[] setStrs)
/*  45:    */   {
/*  46:160 */     if (setStrs == null) {
/*  47:161 */       return null;
/*  48:    */     }
/*  49:163 */     return new CharSet(setStrs);
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected CharSet(String setStr)
/*  53:    */   {
/*  54:175 */     add(setStr);
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected CharSet(String[] set)
/*  58:    */   {
/*  59:187 */     int sz = set.length;
/*  60:188 */     for (int i = 0; i < sz; i++) {
/*  61:189 */       add(set[i]);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void add(String str)
/*  66:    */   {
/*  67:200 */     if (str == null) {
/*  68:201 */       return;
/*  69:    */     }
/*  70:204 */     int len = str.length();
/*  71:205 */     int pos = 0;
/*  72:206 */     while (pos < len)
/*  73:    */     {
/*  74:207 */       int remainder = len - pos;
/*  75:208 */       if ((remainder >= 4) && (str.charAt(pos) == '^') && (str.charAt(pos + 2) == '-'))
/*  76:    */       {
/*  77:210 */         this.set.add(CharRange.isNotIn(str.charAt(pos + 1), str.charAt(pos + 3)));
/*  78:211 */         pos += 4;
/*  79:    */       }
/*  80:212 */       else if ((remainder >= 3) && (str.charAt(pos + 1) == '-'))
/*  81:    */       {
/*  82:214 */         this.set.add(CharRange.isIn(str.charAt(pos), str.charAt(pos + 2)));
/*  83:215 */         pos += 3;
/*  84:    */       }
/*  85:216 */       else if ((remainder >= 2) && (str.charAt(pos) == '^'))
/*  86:    */       {
/*  87:218 */         this.set.add(CharRange.isNot(str.charAt(pos + 1)));
/*  88:219 */         pos += 2;
/*  89:    */       }
/*  90:    */       else
/*  91:    */       {
/*  92:222 */         this.set.add(CharRange.is(str.charAt(pos)));
/*  93:223 */         pos++;
/*  94:    */       }
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public CharRange[] getCharRanges()
/*  99:    */   {
/* 100:236 */     return (CharRange[])this.set.toArray(new CharRange[this.set.size()]);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean contains(char ch)
/* 104:    */   {
/* 105:248 */     for (Iterator it = this.set.iterator(); it.hasNext();)
/* 106:    */     {
/* 107:249 */       CharRange range = (CharRange)it.next();
/* 108:250 */       if (range.contains(ch)) {
/* 109:251 */         return true;
/* 110:    */       }
/* 111:    */     }
/* 112:254 */     return false;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean equals(Object obj)
/* 116:    */   {
/* 117:271 */     if (obj == this) {
/* 118:272 */       return true;
/* 119:    */     }
/* 120:274 */     if (!(obj instanceof CharSet)) {
/* 121:275 */       return false;
/* 122:    */     }
/* 123:277 */     CharSet other = (CharSet)obj;
/* 124:278 */     return this.set.equals(other.set);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int hashCode()
/* 128:    */   {
/* 129:288 */     return 89 + this.set.hashCode();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String toString()
/* 133:    */   {
/* 134:297 */     return this.set.toString();
/* 135:    */   }
/* 136:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.CharSet
 * JD-Core Version:    0.7.0.1
 */