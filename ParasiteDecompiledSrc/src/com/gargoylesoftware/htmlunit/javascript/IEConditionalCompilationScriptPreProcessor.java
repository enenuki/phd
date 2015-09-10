/*   1:    */ package com.gargoylesoftware.htmlunit.javascript;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.ScriptPreProcessor;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   8:    */ import java.util.HashSet;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.regex.Matcher;
/*  11:    */ import java.util.regex.Pattern;
/*  12:    */ import org.apache.commons.lang.ArrayUtils;
/*  13:    */ 
/*  14:    */ public class IEConditionalCompilationScriptPreProcessor
/*  15:    */   implements ScriptPreProcessor
/*  16:    */ {
/*  17: 47 */   private static final Pattern CC_VARIABLE_PATTERN = Pattern.compile("@\\w+|'[^']*'|\"[^\"]*\"");
/*  18: 48 */   private static final Pattern SET_PATTERN = Pattern.compile("@set\\s+(@\\w+)(\\s*=\\s*[\\d\\.]+)");
/*  19: 49 */   private static final Pattern C_VARIABLE_PATTERN = Pattern.compile("(@_\\w+)|'[^']*'|\"[^\"]*\"");
/*  20: 50 */   private static final Pattern CC_PROCESS_PATTERN = Pattern.compile("/\\*@end");
/*  21: 52 */   private static final Pattern IF1_PATTERN = Pattern.compile("@if\\s*\\(([^\\)]+)\\)");
/*  22: 53 */   private static final Pattern IF2_PATTERN = Pattern.compile("@elif\\s*\\(([^\\)]+)\\)");
/*  23: 54 */   private static final Pattern IF3_PATTERN = Pattern.compile("@else");
/*  24: 55 */   private static final Pattern IF4_PATTERN = Pattern.compile("(/\\*)?@end");
/*  25:    */   private static final String CC_VARIABLE_PREFIX = "htmlunit_cc_variable_";
/*  26:    */   
/*  27:    */   private static enum PARSING_STATUS
/*  28:    */   {
/*  29: 58 */     NORMAL,  IN_MULTI_LINE_COMMENT,  IN_SINGLE_LINE_COMMENT,  IN_STRING,  IN_REG_EXP;
/*  30:    */     
/*  31:    */     private PARSING_STATUS() {}
/*  32:    */   }
/*  33:    */   
/*  34: 60 */   private final Set<String> setVariables_ = new HashSet();
/*  35:    */   
/*  36:    */   public String preProcess(HtmlPage htmlPage, String sourceCode, String sourceName, int lineNumber, HtmlElement htmlElement)
/*  37:    */   {
/*  38: 68 */     int startPos = indexOf(sourceCode, "/*@cc_on", 0);
/*  39: 69 */     if (startPos == -1) {
/*  40: 70 */       return sourceCode;
/*  41:    */     }
/*  42: 72 */     int endPos = indexOf(sourceCode, "@*/", startPos);
/*  43: 73 */     if (endPos == -1) {
/*  44: 74 */       return sourceCode;
/*  45:    */     }
/*  46: 77 */     StringBuilder sb = new StringBuilder();
/*  47: 78 */     if (startPos > 0) {
/*  48: 79 */       sb.append(sourceCode.substring(0, startPos));
/*  49:    */     }
/*  50: 81 */     BrowserVersion browserVersion = htmlPage.getWebClient().getBrowserVersion();
/*  51: 82 */     String body = sourceCode.substring(startPos + 8, endPos);
/*  52: 83 */     sb.append(processConditionalCompilation(body, browserVersion));
/*  53: 84 */     if (endPos < sourceCode.length() - 3)
/*  54:    */     {
/*  55: 85 */       String remaining = sourceCode.substring(endPos + 3);
/*  56: 86 */       int nextStart = remaining.indexOf("/*@");
/*  57: 87 */       int nextEnd = remaining.indexOf("@*/", nextStart + 3);
/*  58: 89 */       while ((nextStart >= 0) && (nextEnd > 0))
/*  59:    */       {
/*  60: 90 */         sb.append(remaining.substring(0, nextStart));
/*  61: 91 */         String nextBody = remaining.substring(nextStart + 3, nextEnd);
/*  62: 92 */         sb.append(processConditionalCompilation(nextBody, browserVersion));
/*  63: 93 */         remaining = remaining.substring(nextEnd + 3);
/*  64: 94 */         nextStart = remaining.indexOf("/*@");
/*  65: 95 */         nextEnd = remaining.indexOf("@*/", nextStart + 3);
/*  66:    */       }
/*  67: 97 */       sb.append(remaining);
/*  68:    */     }
/*  69: 99 */     return sb.toString();
/*  70:    */   }
/*  71:    */   
/*  72:    */   private String processConditionalCompilation(String precompilationBody, BrowserVersion browserVersion)
/*  73:    */   {
/*  74:104 */     String body = precompilationBody;
/*  75:105 */     if (body.startsWith("cc_on")) {
/*  76:106 */       body = body.substring(5);
/*  77:    */     }
/*  78:108 */     body = "@" + body;
/*  79:    */     
/*  80:110 */     body = processIfs(body);
/*  81:111 */     if (body.startsWith("@")) {
/*  82:112 */       body = body.substring(1);
/*  83:    */     }
/*  84:114 */     body = CC_PROCESS_PATTERN.matcher(body).replaceAll("");
/*  85:115 */     body = replaceCompilationVariables(body, browserVersion);
/*  86:116 */     body = processSet(body);
/*  87:117 */     body = replaceCustomCompilationVariables(body);
/*  88:118 */     return body;
/*  89:    */   }
/*  90:    */   
/*  91:    */   private String replaceCustomCompilationVariables(String body)
/*  92:    */   {
/*  93:122 */     Matcher m = CC_VARIABLE_PATTERN.matcher(body);
/*  94:123 */     StringBuffer sb = new StringBuffer();
/*  95:124 */     while (m.find())
/*  96:    */     {
/*  97:125 */       String match = m.group();
/*  98:126 */       if (match.startsWith("@")) {
/*  99:127 */         m.appendReplacement(sb, replaceOneCustomCompilationVariable(match));
/* 100:    */       } else {
/* 101:130 */         m.appendReplacement(sb, match);
/* 102:    */       }
/* 103:    */     }
/* 104:133 */     m.appendTail(sb);
/* 105:134 */     return sb.toString();
/* 106:    */   }
/* 107:    */   
/* 108:    */   private String replaceOneCustomCompilationVariable(String variable)
/* 109:    */   {
/* 110:138 */     if (this.setVariables_.contains(variable)) {
/* 111:139 */       return "htmlunit_cc_variable_" + variable.substring(1);
/* 112:    */     }
/* 113:141 */     return "NaN";
/* 114:    */   }
/* 115:    */   
/* 116:    */   private String processSet(String body)
/* 117:    */   {
/* 118:145 */     Matcher m = SET_PATTERN.matcher(body);
/* 119:146 */     StringBuffer sb = new StringBuffer();
/* 120:147 */     while (m.find())
/* 121:    */     {
/* 122:148 */       this.setVariables_.add(m.group(1));
/* 123:149 */       m.appendReplacement(sb, "htmlunit_cc_variable_" + m.group(1).substring(1) + m.group(2) + ";");
/* 124:    */     }
/* 125:151 */     m.appendTail(sb);
/* 126:152 */     return sb.toString();
/* 127:    */   }
/* 128:    */   
/* 129:    */   private static String processIfs(String code)
/* 130:    */   {
/* 131:157 */     code = IF1_PATTERN.matcher(code).replaceAll("if ($1) {");
/* 132:158 */     code = IF2_PATTERN.matcher(code).replaceAll("} else if ($1) {");
/* 133:159 */     code = IF3_PATTERN.matcher(code).replaceAll("} else {");
/* 134:160 */     code = IF4_PATTERN.matcher(code).replaceAll("}");
/* 135:161 */     return code;
/* 136:    */   }
/* 137:    */   
/* 138:    */   String replaceCompilationVariables(String source, BrowserVersion browserVersion)
/* 139:    */   {
/* 140:165 */     Matcher m = C_VARIABLE_PATTERN.matcher(source);
/* 141:166 */     StringBuffer sb = new StringBuffer();
/* 142:167 */     while (m.find())
/* 143:    */     {
/* 144:168 */       String match = m.group();
/* 145:169 */       if (match.startsWith("@")) {
/* 146:170 */         m.appendReplacement(sb, replaceOneVariable(match, browserVersion));
/* 147:    */       } else {
/* 148:173 */         m.appendReplacement(sb, match);
/* 149:    */       }
/* 150:    */     }
/* 151:176 */     m.appendTail(sb);
/* 152:177 */     return sb.toString();
/* 153:    */   }
/* 154:    */   
/* 155:    */   private static String replaceOneVariable(String variable, BrowserVersion browserVersion)
/* 156:    */   {
/* 157:186 */     String[] varNaN = { "@_win16", "@_mac", "@_alpha", "@_mc680x0", "@_PowerPC", "@_debug", "@_fast" };
/* 158:187 */     String[] varTrue = { "@_win32", "@_x86", "@_jscript" };
/* 159:189 */     if (ArrayUtils.contains(varTrue, variable)) {
/* 160:190 */       return "true";
/* 161:    */     }
/* 162:192 */     if ("@_jscript_version".equals(variable))
/* 163:    */     {
/* 164:193 */       if (browserVersion.getBrowserVersionNumeric() <= 6.0F) {
/* 165:194 */         return "5.6";
/* 166:    */       }
/* 167:196 */       if (browserVersion.getBrowserVersionNumeric() == 7.0F) {
/* 168:197 */         return "5.7";
/* 169:    */       }
/* 170:199 */       return "5.8";
/* 171:    */     }
/* 172:201 */     if ("@_jscript_build".equals(variable))
/* 173:    */     {
/* 174:202 */       if (browserVersion.getBrowserVersionNumeric() <= 6.0F) {
/* 175:203 */         return "6626";
/* 176:    */       }
/* 177:205 */       if (browserVersion.getBrowserVersionNumeric() == 7.0F) {
/* 178:206 */         return "5730";
/* 179:    */       }
/* 180:208 */       return "18702";
/* 181:    */     }
/* 182:210 */     if (ArrayUtils.contains(varNaN, variable)) {
/* 183:211 */       return "NaN";
/* 184:    */     }
/* 185:213 */     return variable;
/* 186:    */   }
/* 187:    */   
/* 188:    */   private static int indexOf(String sourceCode, String str, int fromIndex)
/* 189:    */   {
/* 190:225 */     PARSING_STATUS parsingStatus = PARSING_STATUS.NORMAL;
/* 191:226 */     char stringChar = '\000';
/* 192:227 */     for (int i = 0; i < sourceCode.length(); i++)
/* 193:    */     {
/* 194:228 */       if (((parsingStatus == PARSING_STATUS.NORMAL) || (parsingStatus == PARSING_STATUS.IN_MULTI_LINE_COMMENT)) && (i >= fromIndex) && (i + str.length() <= sourceCode.length()) && (sourceCode.substring(i, i + str.length()).equals(str))) {
/* 195:231 */         return i;
/* 196:    */       }
/* 197:233 */       char ch = sourceCode.charAt(i);
/* 198:234 */       switch (ch)
/* 199:    */       {
/* 200:    */       case '/': 
/* 201:236 */         if ((parsingStatus == PARSING_STATUS.NORMAL) && (i + 1 < sourceCode.length()))
/* 202:    */         {
/* 203:237 */           char nextCh = sourceCode.charAt(i + 1);
/* 204:238 */           if (nextCh == '/')
/* 205:    */           {
/* 206:239 */             parsingStatus = PARSING_STATUS.IN_SINGLE_LINE_COMMENT;
/* 207:    */           }
/* 208:241 */           else if (nextCh == '*')
/* 209:    */           {
/* 210:242 */             parsingStatus = PARSING_STATUS.IN_MULTI_LINE_COMMENT;
/* 211:    */           }
/* 212:    */           else
/* 213:    */           {
/* 214:245 */             stringChar = ch;
/* 215:246 */             parsingStatus = PARSING_STATUS.IN_REG_EXP;
/* 216:    */           }
/* 217:    */         }
/* 218:249 */         else if ((parsingStatus == PARSING_STATUS.IN_REG_EXP) && (ch == stringChar))
/* 219:    */         {
/* 220:250 */           stringChar = '\000';
/* 221:251 */           parsingStatus = PARSING_STATUS.NORMAL;
/* 222:    */         }
/* 223:    */         break;
/* 224:    */       case '*': 
/* 225:256 */         if ((parsingStatus == PARSING_STATUS.IN_MULTI_LINE_COMMENT) && (i + 1 < sourceCode.length()))
/* 226:    */         {
/* 227:257 */           char nextCh = sourceCode.charAt(i + 1);
/* 228:258 */           if (nextCh == '/') {
/* 229:259 */             parsingStatus = PARSING_STATUS.NORMAL;
/* 230:    */           }
/* 231:    */         }
/* 232:261 */         break;
/* 233:    */       case '\n': 
/* 234:265 */         if (parsingStatus == PARSING_STATUS.IN_SINGLE_LINE_COMMENT) {
/* 235:266 */           parsingStatus = PARSING_STATUS.NORMAL;
/* 236:    */         }
/* 237:    */         break;
/* 238:    */       case '"': 
/* 239:    */       case '\'': 
/* 240:272 */         if (parsingStatus == PARSING_STATUS.NORMAL)
/* 241:    */         {
/* 242:273 */           stringChar = ch;
/* 243:274 */           parsingStatus = PARSING_STATUS.IN_STRING;
/* 244:    */         }
/* 245:276 */         else if ((parsingStatus == PARSING_STATUS.IN_STRING) && (ch == stringChar))
/* 246:    */         {
/* 247:277 */           stringChar = '\000';
/* 248:278 */           parsingStatus = PARSING_STATUS.NORMAL;
/* 249:    */         }
/* 250:    */         break;
/* 251:    */       case '\\': 
/* 252:283 */         if (parsingStatus == PARSING_STATUS.IN_STRING) {
/* 253:284 */           if ((i + 3 < sourceCode.length()) && (sourceCode.charAt(i + 1) == 'x'))
/* 254:    */           {
/* 255:285 */             char ch1 = Character.toUpperCase(sourceCode.charAt(i + 2));
/* 256:286 */             char ch2 = Character.toUpperCase(sourceCode.charAt(i + 3));
/* 257:287 */             if (((ch1 >= '0') && (ch1 <= '9')) || ((ch1 >= 'A') && (ch1 <= 'F') && (((ch2 >= '0') && (ch2 <= '9')) || ((ch2 >= 'A') && (ch2 <= 'F')))))
/* 258:    */             {
/* 259:289 */               char character = (char)Integer.parseInt(sourceCode.substring(i + 2, i + 4), 16);
/* 260:290 */               if (character >= ' ')
/* 261:    */               {
/* 262:291 */                 i += 3;
/* 263:292 */                 continue;
/* 264:    */               }
/* 265:    */             }
/* 266:    */           }
/* 267:296 */           else if (i + 1 < sourceCode.length())
/* 268:    */           {
/* 269:297 */             i++;
/* 270:    */           }
/* 271:    */         }
/* 272:    */         break;
/* 273:    */       }
/* 274:    */     }
/* 275:305 */     return -1;
/* 276:    */   }
/* 277:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.IEConditionalCompilationScriptPreProcessor
 * JD-Core Version:    0.7.0.1
 */