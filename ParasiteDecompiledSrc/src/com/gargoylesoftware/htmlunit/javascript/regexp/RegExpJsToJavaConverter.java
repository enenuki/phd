/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.regexp;
/*   2:    */ 
/*   3:    */ public class RegExpJsToJavaConverter
/*   4:    */ {
/*   5:    */   private static final String DIGITS = "0123456789";
/*   6:    */   private Tape tape_;
/*   7:    */   private boolean insideCharClass_;
/*   8:    */   private boolean insideRepetition_;
/*   9:    */   private int noOfSubexpressions_;
/*  10:    */   
/*  11:    */   private static class Tape
/*  12:    */   {
/*  13:    */     private StringBuilder tape_;
/*  14:    */     private int currentPos_;
/*  15:    */     
/*  16:    */     public Tape(String input)
/*  17:    */     {
/*  18: 47 */       this.currentPos_ = 0;
/*  19: 48 */       this.tape_ = new StringBuilder(input);
/*  20:    */     }
/*  21:    */     
/*  22:    */     public void move(int offset)
/*  23:    */     {
/*  24: 56 */       this.currentPos_ += offset;
/*  25:    */     }
/*  26:    */     
/*  27:    */     public int read()
/*  28:    */     {
/*  29: 66 */       if (this.currentPos_ < 0) {
/*  30: 67 */         return -1;
/*  31:    */       }
/*  32: 69 */       if (this.currentPos_ >= this.tape_.length()) {
/*  33: 70 */         return -1;
/*  34:    */       }
/*  35: 73 */       return this.tape_.charAt(this.currentPos_++);
/*  36:    */     }
/*  37:    */     
/*  38:    */     public void insert(String token, int offset)
/*  39:    */     {
/*  40: 82 */       this.tape_.insert(this.currentPos_ + offset, token);
/*  41: 83 */       this.currentPos_ += token.length();
/*  42:    */     }
/*  43:    */     
/*  44:    */     public void replace(String token)
/*  45:    */     {
/*  46: 91 */       this.tape_.replace(this.currentPos_, this.currentPos_ + 1, token);
/*  47:    */     }
/*  48:    */     
/*  49:    */     public String toString()
/*  50:    */     {
/*  51:100 */       return this.tape_.toString();
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String convert(String input)
/*  56:    */   {
/*  57:124 */     this.tape_ = new Tape(input);
/*  58:125 */     this.insideCharClass_ = false;
/*  59:126 */     this.insideRepetition_ = false;
/*  60:127 */     this.noOfSubexpressions_ = 0;
/*  61:    */     
/*  62:129 */     int current = this.tape_.read();
/*  63:130 */     while (current > -1)
/*  64:    */     {
/*  65:131 */       if (92 == current) {
/*  66:132 */         processEscapeSequence();
/*  67:134 */       } else if (91 == current) {
/*  68:135 */         processCharClassStart();
/*  69:137 */       } else if (93 == current) {
/*  70:138 */         processCharClassEnd();
/*  71:140 */       } else if (123 == current) {
/*  72:141 */         processRepetitionStart();
/*  73:143 */       } else if (125 == current) {
/*  74:144 */         processRepetitionEnd();
/*  75:146 */       } else if (40 == current) {
/*  76:147 */         processSubExpressionStart();
/*  77:    */       }
/*  78:151 */       current = this.tape_.read();
/*  79:    */     }
/*  80:154 */     return this.tape_.toString();
/*  81:    */   }
/*  82:    */   
/*  83:    */   private void processCharClassStart()
/*  84:    */   {
/*  85:158 */     if (this.insideCharClass_)
/*  86:    */     {
/*  87:159 */       this.tape_.insert("\\", -1);
/*  88:    */     }
/*  89:    */     else
/*  90:    */     {
/*  91:162 */       this.insideCharClass_ = true;
/*  92:    */       
/*  93:164 */       int next = this.tape_.read();
/*  94:165 */       if (next < 0)
/*  95:    */       {
/*  96:166 */         this.tape_.insert("\\", -1);
/*  97:167 */         return;
/*  98:    */       }
/*  99:169 */       if (94 == next)
/* 100:    */       {
/* 101:171 */         next = this.tape_.read();
/* 102:172 */         if (next < 0)
/* 103:    */         {
/* 104:173 */           this.tape_.insert("\\", -2);
/* 105:174 */           return;
/* 106:    */         }
/* 107:176 */         if (92 == next)
/* 108:    */         {
/* 109:177 */           next = this.tape_.read();
/* 110:178 */           if ("0123456789".indexOf(next) < 0)
/* 111:    */           {
/* 112:179 */             this.tape_.move(-2);
/* 113:180 */             return;
/* 114:    */           }
/* 115:183 */           if (handleBackReferenceOrOctal(next))
/* 116:    */           {
/* 117:184 */             next = this.tape_.read();
/* 118:185 */             if (93 == next)
/* 119:    */             {
/* 120:186 */               this.tape_.move(-3);
/* 121:187 */               this.tape_.replace("");
/* 122:188 */               this.tape_.replace("");
/* 123:189 */               this.tape_.replace(".");
/* 124:190 */               this.insideCharClass_ = false;
/* 125:    */             }
/* 126:    */           }
/* 127:    */         }
/* 128:    */         else
/* 129:    */         {
/* 130:195 */           this.tape_.move(-1);
/* 131:    */         }
/* 132:    */       }
/* 133:    */       else
/* 134:    */       {
/* 135:199 */         this.tape_.move(-1);
/* 136:    */       }
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   private void processCharClassEnd()
/* 141:    */   {
/* 142:205 */     this.insideCharClass_ = false;
/* 143:    */   }
/* 144:    */   
/* 145:    */   private void processRepetitionStart()
/* 146:    */   {
/* 147:209 */     int next = this.tape_.read();
/* 148:210 */     if (next < 0)
/* 149:    */     {
/* 150:211 */       this.tape_.insert("\\", -1);
/* 151:212 */       return;
/* 152:    */     }
/* 153:215 */     if ("0123456789".indexOf(next) > -1)
/* 154:    */     {
/* 155:216 */       this.insideRepetition_ = true;
/* 156:    */     }
/* 157:218 */     else if (125 == next)
/* 158:    */     {
/* 159:219 */       this.tape_.insert("\\", -2);
/* 160:220 */       this.tape_.insert("\\", -1);
/* 161:    */     }
/* 162:    */     else
/* 163:    */     {
/* 164:223 */       this.tape_.insert("\\", -2);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   private void processRepetitionEnd()
/* 169:    */   {
/* 170:228 */     if (this.insideRepetition_)
/* 171:    */     {
/* 172:229 */       this.insideRepetition_ = false;
/* 173:230 */       return;
/* 174:    */     }
/* 175:233 */     this.tape_.insert("\\", -1);
/* 176:    */   }
/* 177:    */   
/* 178:    */   private void processSubExpressionStart()
/* 179:    */   {
/* 180:237 */     int next = this.tape_.read();
/* 181:238 */     if (next < 0) {
/* 182:239 */       return;
/* 183:    */     }
/* 184:242 */     if (63 != next)
/* 185:    */     {
/* 186:243 */       this.noOfSubexpressions_ += 1;
/* 187:244 */       this.tape_.move(-1);
/* 188:245 */       return;
/* 189:    */     }
/* 190:248 */     next = this.tape_.read();
/* 191:249 */     if (next < 0) {
/* 192:250 */       return;
/* 193:    */     }
/* 194:252 */     if (58 != next)
/* 195:    */     {
/* 196:253 */       this.noOfSubexpressions_ += 1;
/* 197:254 */       this.tape_.move(-1);
/* 198:255 */       return;
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   private void processEscapeSequence()
/* 203:    */   {
/* 204:260 */     int escapeSequence = this.tape_.read();
/* 205:261 */     if (escapeSequence < 0) {
/* 206:262 */       return;
/* 207:    */     }
/* 208:265 */     if (120 == escapeSequence)
/* 209:    */     {
/* 210:268 */       this.tape_.move(2);
/* 211:269 */       return;
/* 212:    */     }
/* 213:272 */     if (117 == escapeSequence)
/* 214:    */     {
/* 215:275 */       this.tape_.move(4);
/* 216:276 */       return;
/* 217:    */     }
/* 218:279 */     if ("ACEFGHIJKLMNOPQRTUVXYZaeghijklmpqyz".indexOf(escapeSequence) > -1)
/* 219:    */     {
/* 220:281 */       this.tape_.move(-2);
/* 221:282 */       this.tape_.replace("");
/* 222:283 */       this.tape_.move(1);
/* 223:284 */       return;
/* 224:    */     }
/* 225:287 */     if ((this.insideCharClass_) && (98 == escapeSequence))
/* 226:    */     {
/* 227:289 */       this.tape_.move(-1);
/* 228:290 */       this.tape_.replace("cH");
/* 229:291 */       this.tape_.move(2);
/* 230:292 */       return;
/* 231:    */     }
/* 232:295 */     if ("0123456789".indexOf(escapeSequence) > -1) {
/* 233:296 */       handleBackReferenceOrOctal(escapeSequence);
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   private boolean handleBackReferenceOrOctal(int aFirstChar)
/* 238:    */   {
/* 239:302 */     StringBuilder tmpNo = new StringBuilder(Character.toString((char)aFirstChar));
/* 240:303 */     int tmpInsertPos = -1;
/* 241:304 */     int next = this.tape_.read();
/* 242:305 */     if (next > -1) {
/* 243:306 */       if ("0123456789".indexOf(next) > -1)
/* 244:    */       {
/* 245:307 */         tmpNo.append(next);
/* 246:308 */         tmpInsertPos--;
/* 247:309 */         next = this.tape_.read();
/* 248:310 */         if (next > -1) {
/* 249:311 */           if ("0123456789".indexOf(next) > -1)
/* 250:    */           {
/* 251:312 */             tmpNo.append(next);
/* 252:313 */             tmpInsertPos--;
/* 253:    */           }
/* 254:    */           else
/* 255:    */           {
/* 256:316 */             this.tape_.move(-1);
/* 257:    */           }
/* 258:    */         }
/* 259:    */       }
/* 260:    */       else
/* 261:    */       {
/* 262:321 */         this.tape_.move(-1);
/* 263:    */       }
/* 264:    */     }
/* 265:325 */     int value = Integer.parseInt(tmpNo.toString());
/* 266:326 */     if (value > this.noOfSubexpressions_)
/* 267:    */     {
/* 268:328 */       this.tape_.insert("0", tmpInsertPos);
/* 269:329 */       return false;
/* 270:    */     }
/* 271:332 */     if (this.insideCharClass_) {
/* 272:334 */       for (int i = tmpInsertPos; i <= 0; i++)
/* 273:    */       {
/* 274:335 */         this.tape_.move(-1);
/* 275:336 */         this.tape_.replace("");
/* 276:    */       }
/* 277:    */     }
/* 278:339 */     return true;
/* 279:    */   }
/* 280:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.regexp.RegExpJsToJavaConverter
 * JD-Core Version:    0.7.0.1
 */