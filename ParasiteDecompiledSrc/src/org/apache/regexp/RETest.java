/*   1:    */ package org.apache.regexp;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileReader;
/*   6:    */ import java.io.InputStreamReader;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.io.PrintWriter;
/*   9:    */ 
/*  10:    */ public class RETest
/*  11:    */ {
/*  12: 73 */   RE r = new RE();
/*  13: 74 */   REDebugCompiler compiler = new REDebugCompiler();
/*  14:    */   static final boolean showSuccesses = false;
/*  15:    */   
/*  16:    */   public static void main(String[] paramArrayOfString)
/*  17:    */   {
/*  18:    */     try
/*  19:    */     {
/*  20: 90 */       test();
/*  21:    */     }
/*  22:    */     catch (Exception localException)
/*  23:    */     {
/*  24: 94 */       localException.printStackTrace();
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static boolean test()
/*  29:    */     throws Exception
/*  30:    */   {
/*  31:105 */     RETest localRETest = new RETest();
/*  32:106 */     localRETest.runAutomatedTests("docs/RETest.txt");
/*  33:107 */     return localRETest.failures == 0;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public RETest(String[] paramArrayOfString)
/*  37:    */   {
/*  38:    */     try
/*  39:    */     {
/*  40:126 */       if (paramArrayOfString.length == 2) {
/*  41:128 */         runInteractiveTests(paramArrayOfString[1]);
/*  42:130 */       } else if (paramArrayOfString.length == 1) {
/*  43:133 */         runAutomatedTests(paramArrayOfString[0]);
/*  44:    */       } else {
/*  45:137 */         System.out.println("Usage: RETest ([-i] [regex]) ([/path/to/testfile.txt])");
/*  46:    */       }
/*  47:    */     }
/*  48:    */     catch (Exception localException)
/*  49:    */     {
/*  50:142 */       localException.printStackTrace();
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   void runInteractiveTests(String paramString)
/*  55:    */   {
/*  56:    */     try
/*  57:    */     {
/*  58:155 */       this.r.setProgram(this.compiler.compile(paramString));
/*  59:    */       
/*  60:    */ 
/*  61:158 */       say("\n" + paramString + "\n");
/*  62:    */       
/*  63:    */ 
/*  64:161 */       this.compiler.dumpProgram(new PrintWriter(System.out));
/*  65:    */       for (;;)
/*  66:    */       {
/*  67:167 */         BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(System.in));
/*  68:168 */         System.out.print("> ");
/*  69:169 */         System.out.flush();
/*  70:170 */         String str = localBufferedReader.readLine();
/*  71:173 */         if (this.r.match(str)) {
/*  72:175 */           say("Match successful.");
/*  73:    */         } else {
/*  74:179 */           say("Match failed.");
/*  75:    */         }
/*  76:183 */         showParens(this.r);
/*  77:    */       }
/*  78:    */     }
/*  79:    */     catch (Exception localException)
/*  80:    */     {
/*  81:188 */       say("Error: " + localException.toString());
/*  82:189 */       localException.printStackTrace();
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   void die(String paramString)
/*  87:    */   {
/*  88:199 */     say("FATAL ERROR: " + paramString);
/*  89:200 */     System.exit(0);
/*  90:    */   }
/*  91:    */   
/*  92:    */   void fail(String paramString)
/*  93:    */   {
/*  94:209 */     this.failures += 1;
/*  95:210 */     say("\n");
/*  96:211 */     say("*******************************************************");
/*  97:212 */     say("*********************  FAILURE!  **********************");
/*  98:213 */     say("*******************************************************");
/*  99:214 */     say("\n");
/* 100:215 */     say(paramString);
/* 101:216 */     say("");
/* 102:217 */     this.compiler.dumpProgram(new PrintWriter(System.out));
/* 103:218 */     say("\n");
/* 104:    */   }
/* 105:    */   
/* 106:    */   void say(String paramString)
/* 107:    */   {
/* 108:240 */     System.out.println(paramString);
/* 109:    */   }
/* 110:    */   
/* 111:    */   void show()
/* 112:    */   {
/* 113:248 */     say("\n-----------------------\n");
/* 114:249 */     say("Expression #" + this.n + " \"" + this.expr + "\" ");
/* 115:    */   }
/* 116:    */   
/* 117:    */   void showParens(RE paramRE)
/* 118:    */   {
/* 119:259 */     for (int i = 0; i < paramRE.getParenCount(); i++) {
/* 120:262 */       say("$" + i + " = " + paramRE.getParen(i));
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:268 */   char[] re1Instructions = {
/* 125:269 */     '|', '\000', '\032', '|', '\000', '\r', 'A', 
/* 126:270 */     '\001', '\004', 'a', '|', '\000', '\003', 'G', '\000', 
/* 127:271 */     65526, '|', '\000', '\003', 'N', '\000', 
/* 128:272 */     '\003', 'A', '\001', '\004', 'b', 'E' };
/* 129:276 */   REProgram re1 = new REProgram(this.re1Instructions);
/* 130:    */   String expr;
/* 131:282 */   int n = 0;
/* 132:287 */   int failures = 0;
/* 133:    */   
/* 134:    */   void runAutomatedTests(String paramString)
/* 135:    */     throws Exception
/* 136:    */   {
/* 137:295 */     long l = System.currentTimeMillis();
/* 138:    */     
/* 139:    */ 
/* 140:298 */     RE localRE = new RE(this.re1);
/* 141:299 */     say("a*b");
/* 142:300 */     say("aaaab = " + localRE.match("aaab"));
/* 143:301 */     showParens(localRE);
/* 144:302 */     say("b = " + localRE.match("b"));
/* 145:303 */     showParens(localRE);
/* 146:304 */     say("c = " + localRE.match("c"));
/* 147:305 */     showParens(localRE);
/* 148:306 */     say("ccccaaaaab = " + localRE.match("ccccaaaaab"));
/* 149:307 */     showParens(localRE);
/* 150:    */     
/* 151:309 */     localRE = new RE("a*b");
/* 152:310 */     String[] arrayOfString = localRE.split("xxxxaabxxxxbyyyyaaabzzz");
/* 153:311 */     localRE = new RE("x+");
/* 154:312 */     arrayOfString = localRE.grep(arrayOfString);
/* 155:313 */     for (int i = 0; i < arrayOfString.length; i++) {
/* 156:315 */       System.out.println("s[" + i + "] = " + arrayOfString[i]);
/* 157:    */     }
/* 158:318 */     localRE = new RE("a*b");
/* 159:319 */     String str1 = localRE.subst("aaaabfooaaabgarplyaaabwackyb", "-");
/* 160:320 */     System.out.println("s = " + str1);
/* 161:    */     
/* 162:    */ 
/* 163:323 */     File localFile = new File(paramString);
/* 164:324 */     if (!localFile.exists()) {
/* 165:325 */       throw new Exception("Could not find: " + paramString);
/* 166:    */     }
/* 167:326 */     BufferedReader localBufferedReader = new BufferedReader(new FileReader(localFile));
/* 168:    */     try
/* 169:    */     {
/* 170:330 */       while (localBufferedReader.ready())
/* 171:    */       {
/* 172:333 */         String str2 = "";
/* 173:335 */         while (localBufferedReader.ready())
/* 174:    */         {
/* 175:337 */           str2 = localBufferedReader.readLine();
/* 176:338 */           if (str2 == null) {
/* 177:    */             break;
/* 178:    */           }
/* 179:342 */           str2 = str2.trim();
/* 180:343 */           if (str2.startsWith("#")) {
/* 181:    */             break;
/* 182:    */           }
/* 183:347 */           if (!str2.equals(""))
/* 184:    */           {
/* 185:349 */             System.out.println("Script error.  Line = " + str2);
/* 186:350 */             System.exit(0);
/* 187:    */           }
/* 188:    */         }
/* 189:355 */         if (!localBufferedReader.ready()) {
/* 190:    */           break;
/* 191:    */         }
/* 192:361 */         this.expr = localBufferedReader.readLine();
/* 193:362 */         this.n += 1;
/* 194:363 */         say("");
/* 195:364 */         say(this.n + ". " + this.expr);
/* 196:365 */         say("");
/* 197:    */         String str3;
/* 198:    */         try
/* 199:    */         {
/* 200:370 */           localRE.setProgram(this.compiler.compile(this.expr));
/* 201:    */         }
/* 202:    */         catch (Exception localException1)
/* 203:    */         {
/* 204:377 */           str3 = localBufferedReader.readLine().trim();
/* 205:380 */           if (str3.equals("ERR"))
/* 206:    */           {
/* 207:382 */             say("   Match: ERR");
/* 208:383 */             success("Produces an error (" + localException1.toString() + "), as expected.");
/* 209:384 */             continue;
/* 210:    */           }
/* 211:388 */           fail("Produces the unexpected error \"" + localException1.getMessage() + "\"");
/* 212:    */         }
/* 213:    */         catch (Error localError1)
/* 214:    */         {
/* 215:393 */           fail("Compiler threw fatal error \"" + localError1.getMessage() + "\"");
/* 216:394 */           localError1.printStackTrace();
/* 217:    */         }
/* 218:398 */         String str4 = localBufferedReader.readLine().trim();
/* 219:399 */         say("   Match against: '" + str4 + "'");
/* 220:402 */         if (str4.equals("ERR")) {
/* 221:404 */           fail("Was expected to be an error, but wasn't.");
/* 222:    */         } else {
/* 223:    */           try
/* 224:    */           {
/* 225:412 */             boolean bool = localRE.match(str4);
/* 226:    */             
/* 227:    */ 
/* 228:415 */             str3 = localBufferedReader.readLine().trim();
/* 229:418 */             if (bool)
/* 230:    */             {
/* 231:421 */               say("   Match: YES");
/* 232:424 */               if (str3.equals("NO"))
/* 233:    */               {
/* 234:426 */                 fail("Matched \"" + str4 + "\", when not expected to.");
/* 235:    */               }
/* 236:429 */               else if (str3.equals("YES"))
/* 237:    */               {
/* 238:432 */                 success("Matched \"" + str4 + "\", as expected:");
/* 239:    */                 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:440 */                 say("   Paren count: " + localRE.getParenCount());
/* 247:443 */                 for (int j = 0; j < localRE.getParenCount(); j++)
/* 248:    */                 {
/* 249:446 */                   String str5 = localBufferedReader.readLine().trim();
/* 250:447 */                   say("   Paren " + j + " : " + localRE.getParen(j));
/* 251:450 */                   if (!str5.equals(localRE.getParen(j))) {
/* 252:453 */                     fail("Register " + j + " should be = \"" + str5 + "\", but is \"" + localRE.getParen(j) + "\" instead.");
/* 253:    */                   }
/* 254:    */                 }
/* 255:    */               }
/* 256:    */               else
/* 257:    */               {
/* 258:460 */                 die("Test script error!");
/* 259:    */               }
/* 260:    */             }
/* 261:    */             else
/* 262:    */             {
/* 263:466 */               say("   Match: NO");
/* 264:469 */               if (str3.equals("YES")) {
/* 265:472 */                 fail("Did not match \"" + str4 + "\", when expected to.");
/* 266:475 */               } else if (str3.equals("NO")) {
/* 267:478 */                 success("Did not match \"" + str4 + "\", as expected.");
/* 268:    */               } else {
/* 269:483 */                 die("Test script error!");
/* 270:    */               }
/* 271:    */             }
/* 272:    */           }
/* 273:    */           catch (Exception localException2)
/* 274:    */           {
/* 275:491 */             fail("Matcher threw exception: " + localException2.toString());
/* 276:492 */             localException2.printStackTrace();
/* 277:    */           }
/* 278:    */           catch (Error localError2)
/* 279:    */           {
/* 280:498 */             fail("Matcher threw fatal error \"" + localError2.getMessage() + "\"");
/* 281:499 */             localError2.printStackTrace();
/* 282:    */           }
/* 283:    */         }
/* 284:    */       }
/* 285:    */     }
/* 286:    */     finally
/* 287:    */     {
/* 288:505 */       localBufferedReader.close();
/* 289:    */     }
/* 290:509 */     System.out.println("\n\nMatch time = " + (System.currentTimeMillis() - l) + " ms.");
/* 291:    */     
/* 292:    */ 
/* 293:512 */     System.out.println("\nTests complete.  " + this.n + " tests, " + this.failures + " failure(s).");
/* 294:    */   }
/* 295:    */   
/* 296:    */   public RETest() {}
/* 297:    */   
/* 298:    */   void success(String paramString) {}
/* 299:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.regexp.RETest
 * JD-Core Version:    0.7.0.1
 */