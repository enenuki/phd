/*   1:    */ package org.apache.xalan.xsltc.compiler.util;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.StringTokenizer;
/*   5:    */ import org.apache.bcel.generic.Type;
/*   6:    */ import org.apache.xml.utils.XML11Char;
/*   7:    */ 
/*   8:    */ public final class Util
/*   9:    */ {
/*  10:    */   private static char filesep;
/*  11:    */   
/*  12:    */   static
/*  13:    */   {
/*  14: 38 */     String temp = System.getProperty("file.separator", "/");
/*  15: 39 */     filesep = temp.charAt(0);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static String noExtName(String name)
/*  19:    */   {
/*  20: 43 */     int index = name.lastIndexOf('.');
/*  21: 44 */     return name.substring(0, index >= 0 ? index : name.length());
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static String baseName(String name)
/*  25:    */   {
/*  26: 52 */     int index = name.lastIndexOf('\\');
/*  27: 53 */     if (index < 0) {
/*  28: 54 */       index = name.lastIndexOf('/');
/*  29:    */     }
/*  30: 57 */     if (index >= 0) {
/*  31: 58 */       return name.substring(index + 1);
/*  32:    */     }
/*  33: 60 */     int lastColonIndex = name.lastIndexOf(':');
/*  34: 61 */     if (lastColonIndex > 0) {
/*  35: 62 */       return name.substring(lastColonIndex + 1);
/*  36:    */     }
/*  37: 64 */     return name;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static String pathName(String name)
/*  41:    */   {
/*  42: 73 */     int index = name.lastIndexOf('/');
/*  43: 74 */     if (index < 0) {
/*  44: 75 */       index = name.lastIndexOf('\\');
/*  45:    */     }
/*  46: 77 */     return name.substring(0, index + 1);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static String toJavaName(String name)
/*  50:    */   {
/*  51: 84 */     if (name.length() > 0)
/*  52:    */     {
/*  53: 85 */       StringBuffer result = new StringBuffer();
/*  54:    */       
/*  55: 87 */       char ch = name.charAt(0);
/*  56: 88 */       result.append(Character.isJavaIdentifierStart(ch) ? ch : '_');
/*  57:    */       
/*  58: 90 */       int n = name.length();
/*  59: 91 */       for (int i = 1; i < n; i++)
/*  60:    */       {
/*  61: 92 */         ch = name.charAt(i);
/*  62: 93 */         result.append(Character.isJavaIdentifierPart(ch) ? ch : '_');
/*  63:    */       }
/*  64: 95 */       return result.toString();
/*  65:    */     }
/*  66: 97 */     return name;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static Type getJCRefType(String signature)
/*  70:    */   {
/*  71:101 */     return Type.getType(signature);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static String internalName(String cname)
/*  75:    */   {
/*  76:105 */     return cname.replace('.', filesep);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static void println(String s)
/*  80:    */   {
/*  81:109 */     System.out.println(s);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static void println(char ch)
/*  85:    */   {
/*  86:113 */     System.out.println(ch);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static void TRACE1()
/*  90:    */   {
/*  91:117 */     System.out.println("TRACE1");
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static void TRACE2()
/*  95:    */   {
/*  96:121 */     System.out.println("TRACE2");
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static void TRACE3()
/* 100:    */   {
/* 101:125 */     System.out.println("TRACE3");
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static String replace(String base, char ch, String str)
/* 105:    */   {
/* 106:132 */     return base.indexOf(ch) < 0 ? base : replace(base, String.valueOf(ch), new String[] { str });
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static String replace(String base, String delim, String[] str)
/* 110:    */   {
/* 111:137 */     int len = base.length();
/* 112:138 */     StringBuffer result = new StringBuffer();
/* 113:140 */     for (int i = 0; i < len; i++)
/* 114:    */     {
/* 115:141 */       char ch = base.charAt(i);
/* 116:142 */       int k = delim.indexOf(ch);
/* 117:144 */       if (k >= 0) {
/* 118:145 */         result.append(str[k]);
/* 119:    */       } else {
/* 120:148 */         result.append(ch);
/* 121:    */       }
/* 122:    */     }
/* 123:151 */     return result.toString();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static String escape(String input)
/* 127:    */   {
/* 128:158 */     return replace(input, ".-/:", new String[] { "$dot$", "$dash$", "$slash$", "$colon$" });
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static String getLocalName(String qname)
/* 132:    */   {
/* 133:163 */     int index = qname.lastIndexOf(":");
/* 134:164 */     return index > 0 ? qname.substring(index + 1) : qname;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static String getPrefix(String qname)
/* 138:    */   {
/* 139:168 */     int index = qname.lastIndexOf(":");
/* 140:169 */     return index > 0 ? qname.substring(0, index) : "";
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static boolean isLiteral(String str)
/* 144:    */   {
/* 145:177 */     int length = str.length();
/* 146:178 */     for (int i = 0; i < length - 1; i++) {
/* 147:179 */       if ((str.charAt(i) == '{') && (str.charAt(i + 1) != '{')) {
/* 148:180 */         return false;
/* 149:    */       }
/* 150:    */     }
/* 151:183 */     return true;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static boolean isValidQNames(String str)
/* 155:    */   {
/* 156:190 */     if ((str != null) && (!str.equals("")))
/* 157:    */     {
/* 158:191 */       StringTokenizer tokens = new StringTokenizer(str);
/* 159:192 */       while (tokens.hasMoreTokens()) {
/* 160:193 */         if (!XML11Char.isXML11ValidQName(tokens.nextToken())) {
/* 161:194 */           return false;
/* 162:    */         }
/* 163:    */       }
/* 164:    */     }
/* 165:198 */     return true;
/* 166:    */   }
/* 167:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.util.Util
 * JD-Core Version:    0.7.0.1
 */