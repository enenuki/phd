/*   1:    */ package org.apache.xalan.lib;
/*   2:    */ 
/*   3:    */ import org.apache.xpath.NodeSet;
/*   4:    */ import org.w3c.dom.Node;
/*   5:    */ import org.w3c.dom.NodeList;
/*   6:    */ 
/*   7:    */ public class ExsltMath
/*   8:    */   extends ExsltBase
/*   9:    */ {
/*  10: 45 */   private static String PI = "3.1415926535897932384626433832795028841971693993751";
/*  11: 46 */   private static String E = "2.71828182845904523536028747135266249775724709369996";
/*  12: 47 */   private static String SQRRT2 = "1.41421356237309504880168872420969807856967187537694";
/*  13: 48 */   private static String LN2 = "0.69314718055994530941723212145817656807550013436025";
/*  14: 49 */   private static String LN10 = "2.302585092994046";
/*  15: 50 */   private static String LOG2E = "1.4426950408889633";
/*  16: 51 */   private static String SQRT1_2 = "0.7071067811865476";
/*  17:    */   
/*  18:    */   public static double max(NodeList nl)
/*  19:    */   {
/*  20: 71 */     if ((nl == null) || (nl.getLength() == 0)) {
/*  21: 72 */       return (0.0D / 0.0D);
/*  22:    */     }
/*  23: 74 */     double m = -1.797693134862316E+308D;
/*  24: 75 */     for (int i = 0; i < nl.getLength(); i++)
/*  25:    */     {
/*  26: 77 */       Node n = nl.item(i);
/*  27: 78 */       double d = ExsltBase.toNumber(n);
/*  28: 79 */       if (Double.isNaN(d)) {
/*  29: 80 */         return (0.0D / 0.0D);
/*  30:    */       }
/*  31: 81 */       if (d > m) {
/*  32: 82 */         m = d;
/*  33:    */       }
/*  34:    */     }
/*  35: 85 */     return m;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static double min(NodeList nl)
/*  39:    */   {
/*  40:106 */     if ((nl == null) || (nl.getLength() == 0)) {
/*  41:107 */       return (0.0D / 0.0D);
/*  42:    */     }
/*  43:109 */     double m = 1.7976931348623157E+308D;
/*  44:110 */     for (int i = 0; i < nl.getLength(); i++)
/*  45:    */     {
/*  46:112 */       Node n = nl.item(i);
/*  47:113 */       double d = ExsltBase.toNumber(n);
/*  48:114 */       if (Double.isNaN(d)) {
/*  49:115 */         return (0.0D / 0.0D);
/*  50:    */       }
/*  51:116 */       if (d < m) {
/*  52:117 */         m = d;
/*  53:    */       }
/*  54:    */     }
/*  55:120 */     return m;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static NodeList highest(NodeList nl)
/*  59:    */   {
/*  60:142 */     double maxValue = max(nl);
/*  61:    */     
/*  62:144 */     NodeSet highNodes = new NodeSet();
/*  63:145 */     highNodes.setShouldCacheNodes(true);
/*  64:147 */     if (Double.isNaN(maxValue)) {
/*  65:148 */       return highNodes;
/*  66:    */     }
/*  67:150 */     for (int i = 0; i < nl.getLength(); i++)
/*  68:    */     {
/*  69:152 */       Node n = nl.item(i);
/*  70:153 */       double d = ExsltBase.toNumber(n);
/*  71:154 */       if (d == maxValue) {
/*  72:155 */         highNodes.addElement(n);
/*  73:    */       }
/*  74:    */     }
/*  75:157 */     return highNodes;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static NodeList lowest(NodeList nl)
/*  79:    */   {
/*  80:179 */     double minValue = min(nl);
/*  81:    */     
/*  82:181 */     NodeSet lowNodes = new NodeSet();
/*  83:182 */     lowNodes.setShouldCacheNodes(true);
/*  84:184 */     if (Double.isNaN(minValue)) {
/*  85:185 */       return lowNodes;
/*  86:    */     }
/*  87:187 */     for (int i = 0; i < nl.getLength(); i++)
/*  88:    */     {
/*  89:189 */       Node n = nl.item(i);
/*  90:190 */       double d = ExsltBase.toNumber(n);
/*  91:191 */       if (d == minValue) {
/*  92:192 */         lowNodes.addElement(n);
/*  93:    */       }
/*  94:    */     }
/*  95:194 */     return lowNodes;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static double abs(double num)
/*  99:    */   {
/* 100:205 */     return Math.abs(num);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static double acos(double num)
/* 104:    */   {
/* 105:216 */     return Math.acos(num);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static double asin(double num)
/* 109:    */   {
/* 110:227 */     return Math.asin(num);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static double atan(double num)
/* 114:    */   {
/* 115:238 */     return Math.atan(num);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static double atan2(double num1, double num2)
/* 119:    */   {
/* 120:250 */     return Math.atan2(num1, num2);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static double cos(double num)
/* 124:    */   {
/* 125:261 */     return Math.cos(num);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static double exp(double num)
/* 129:    */   {
/* 130:272 */     return Math.exp(num);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static double log(double num)
/* 134:    */   {
/* 135:283 */     return Math.log(num);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static double power(double num1, double num2)
/* 139:    */   {
/* 140:295 */     return Math.pow(num1, num2);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static double random()
/* 144:    */   {
/* 145:305 */     return Math.random();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static double sin(double num)
/* 149:    */   {
/* 150:316 */     return Math.sin(num);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static double sqrt(double num)
/* 154:    */   {
/* 155:327 */     return Math.sqrt(num);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static double tan(double num)
/* 159:    */   {
/* 160:338 */     return Math.tan(num);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static double constant(String name, double precision)
/* 164:    */   {
/* 165:359 */     String value = null;
/* 166:360 */     if (name.equals("PI")) {
/* 167:361 */       value = PI;
/* 168:362 */     } else if (name.equals("E")) {
/* 169:363 */       value = E;
/* 170:364 */     } else if (name.equals("SQRRT2")) {
/* 171:365 */       value = SQRRT2;
/* 172:366 */     } else if (name.equals("LN2")) {
/* 173:367 */       value = LN2;
/* 174:368 */     } else if (name.equals("LN10")) {
/* 175:369 */       value = LN10;
/* 176:370 */     } else if (name.equals("LOG2E")) {
/* 177:371 */       value = LOG2E;
/* 178:372 */     } else if (name.equals("SQRT1_2")) {
/* 179:373 */       value = SQRT1_2;
/* 180:    */     }
/* 181:375 */     if (value != null)
/* 182:    */     {
/* 183:377 */       int bits = new Double(precision).intValue();
/* 184:379 */       if (bits <= value.length()) {
/* 185:380 */         value = value.substring(0, bits);
/* 186:    */       }
/* 187:382 */       return new Double(value).doubleValue();
/* 188:    */     }
/* 189:385 */     return (0.0D / 0.0D);
/* 190:    */   }
/* 191:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.ExsltMath
 * JD-Core Version:    0.7.0.1
 */