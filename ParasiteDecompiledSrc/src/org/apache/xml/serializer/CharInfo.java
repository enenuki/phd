/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.io.UnsupportedEncodingException;
/*   7:    */ import java.net.URL;
/*   8:    */ import java.security.AccessController;
/*   9:    */ import java.security.PrivilegedAction;
/*  10:    */ import java.util.Enumeration;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.Hashtable;
/*  13:    */ import java.util.ResourceBundle;
/*  14:    */ import javax.xml.transform.TransformerException;
/*  15:    */ import org.apache.xml.serializer.utils.Messages;
/*  16:    */ import org.apache.xml.serializer.utils.SystemIDResolver;
/*  17:    */ import org.apache.xml.serializer.utils.Utils;
/*  18:    */ import org.apache.xml.serializer.utils.WrappedRuntimeException;
/*  19:    */ 
/*  20:    */ final class CharInfo
/*  21:    */ {
/*  22:    */   private HashMap m_charToString;
/*  23:    */   
/*  24:    */   CharInfo(String x0, String x1, boolean x2, 1 x3)
/*  25:    */   {
/*  26: 53 */     this(x0, x1, x2);
/*  27:    */   }
/*  28:    */   
/*  29: 62 */   public static final String HTML_ENTITIES_RESOURCE = SerializerBase.PKG_NAME + ".HTMLEntities";
/*  30: 69 */   public static final String XML_ENTITIES_RESOURCE = SerializerBase.PKG_NAME + ".XMLEntities";
/*  31:    */   static final char S_HORIZONAL_TAB = '\t';
/*  32:    */   static final char S_LINEFEED = '\n';
/*  33:    */   static final char S_CARRIAGERETURN = '\r';
/*  34:    */   static final char S_SPACE = ' ';
/*  35:    */   static final char S_QUOTE = '"';
/*  36:    */   static final char S_LT = '<';
/*  37:    */   static final char S_GT = '>';
/*  38:    */   static final char S_NEL = '';
/*  39:    */   static final char S_LINE_SEPARATOR = ' ';
/*  40:    */   boolean onlyQuotAmpLtGt;
/*  41:    */   static final int ASCII_MAX = 128;
/*  42:    */   private final boolean[] shouldMapAttrChar_ASCII;
/*  43:    */   private final boolean[] shouldMapTextChar_ASCII;
/*  44:    */   private final int[] array_of_bits;
/*  45:    */   private static final int SHIFT_PER_WORD = 5;
/*  46:    */   private static final int LOW_ORDER_BITMASK = 31;
/*  47:    */   private int firstWordNotUsed;
/*  48:    */   private final CharKey m_charKey;
/*  49:    */   
/*  50:    */   private CharInfo()
/*  51:    */   {
/*  52:158 */     this.array_of_bits = createEmptySetOfIntegers(65535);
/*  53:159 */     this.firstWordNotUsed = 0;
/*  54:160 */     this.shouldMapAttrChar_ASCII = new boolean[''];
/*  55:161 */     this.shouldMapTextChar_ASCII = new boolean[''];
/*  56:162 */     this.m_charKey = new CharKey();
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:167 */     this.onlyQuotAmpLtGt = true;
/*  62:    */   }
/*  63:    */   
/*  64:    */   private CharInfo(String entitiesResource, String method, boolean internal)
/*  65:    */   {
/*  66:176 */     this();
/*  67:177 */     this.m_charToString = new HashMap();
/*  68:    */     
/*  69:179 */     ResourceBundle entities = null;
/*  70:180 */     boolean noExtraEntities = true;
/*  71:190 */     if (internal) {
/*  72:    */       try
/*  73:    */       {
/*  74:194 */         entities = ResourceBundle.getBundle(entitiesResource);
/*  75:    */       }
/*  76:    */       catch (Exception e) {}
/*  77:    */     }
/*  78:198 */     if (entities != null)
/*  79:    */     {
/*  80:199 */       Enumeration keys = entities.getKeys();
/*  81:200 */       while (keys.hasMoreElements())
/*  82:    */       {
/*  83:201 */         String name = (String)keys.nextElement();
/*  84:202 */         String value = entities.getString(name);
/*  85:203 */         int code = Integer.parseInt(value);
/*  86:204 */         boolean extra = defineEntity(name, (char)code);
/*  87:205 */         if (extra) {
/*  88:206 */           noExtraEntities = false;
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94:209 */       InputStream is = null;
/*  95:    */       try
/*  96:    */       {
/*  97:214 */         if (internal)
/*  98:    */         {
/*  99:215 */           is = CharInfo.class.getResourceAsStream(entitiesResource);
/* 100:    */         }
/* 101:    */         else
/* 102:    */         {
/* 103:217 */           ClassLoader cl = ObjectFactory.findClassLoader();
/* 104:218 */           if (cl == null) {
/* 105:219 */             is = ClassLoader.getSystemResourceAsStream(entitiesResource);
/* 106:    */           } else {
/* 107:221 */             is = cl.getResourceAsStream(entitiesResource);
/* 108:    */           }
/* 109:224 */           if (is == null) {
/* 110:    */             try
/* 111:    */             {
/* 112:226 */               URL url = new URL(entitiesResource);
/* 113:227 */               is = url.openStream();
/* 114:    */             }
/* 115:    */             catch (Exception e) {}
/* 116:    */           }
/* 117:    */         }
/* 118:232 */         if (is == null) {
/* 119:233 */           throw new RuntimeException(Utils.messages.createMessage("ER_RESOURCE_COULD_NOT_FIND", new Object[] { entitiesResource, entitiesResource }));
/* 120:    */         }
/* 121:    */         BufferedReader reader;
/* 122:    */         try
/* 123:    */         {
/* 124:260 */           reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/* 125:    */         }
/* 126:    */         catch (UnsupportedEncodingException e)
/* 127:    */         {
/* 128:262 */           reader = new BufferedReader(new InputStreamReader(is));
/* 129:    */         }
/* 130:265 */         String line = reader.readLine();
/* 131:267 */         while (line != null) {
/* 132:268 */           if ((line.length() == 0) || (line.charAt(0) == '#'))
/* 133:    */           {
/* 134:269 */             line = reader.readLine();
/* 135:    */           }
/* 136:    */           else
/* 137:    */           {
/* 138:274 */             int index = line.indexOf(' ');
/* 139:276 */             if (index > 1)
/* 140:    */             {
/* 141:277 */               String name = line.substring(0, index);
/* 142:    */               
/* 143:279 */               index++;
/* 144:281 */               if (index < line.length())
/* 145:    */               {
/* 146:282 */                 String value = line.substring(index);
/* 147:283 */                 index = value.indexOf(' ');
/* 148:285 */                 if (index > 0) {
/* 149:286 */                   value = value.substring(0, index);
/* 150:    */                 }
/* 151:289 */                 int code = Integer.parseInt(value);
/* 152:    */                 
/* 153:291 */                 boolean extra = defineEntity(name, (char)code);
/* 154:292 */                 if (extra) {
/* 155:293 */                   noExtraEntities = false;
/* 156:    */                 }
/* 157:    */               }
/* 158:    */             }
/* 159:297 */             line = reader.readLine();
/* 160:    */           }
/* 161:    */         }
/* 162:300 */         is.close();
/* 163:    */       }
/* 164:    */       catch (Exception e)
/* 165:    */       {
/* 166:302 */         throw new RuntimeException(Utils.messages.createMessage("ER_RESOURCE_COULD_NOT_LOAD", new Object[] { entitiesResource, e.toString(), entitiesResource, e.toString() }));
/* 167:    */       }
/* 168:    */       finally
/* 169:    */       {
/* 170:310 */         if (is != null) {
/* 171:    */           try
/* 172:    */           {
/* 173:312 */             is.close();
/* 174:    */           }
/* 175:    */           catch (Exception except) {}
/* 176:    */         }
/* 177:    */       }
/* 178:    */     }
/* 179:318 */     this.onlyQuotAmpLtGt = noExtraEntities;
/* 180:329 */     if ("xml".equals(method)) {
/* 181:332 */       this.shouldMapTextChar_ASCII[34] = false;
/* 182:    */     }
/* 183:335 */     if ("html".equals(method))
/* 184:    */     {
/* 185:339 */       this.shouldMapAttrChar_ASCII[60] = false;
/* 186:    */       
/* 187:    */ 
/* 188:342 */       this.shouldMapTextChar_ASCII[34] = false;
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   private boolean defineEntity(String name, char value)
/* 193:    */   {
/* 194:366 */     StringBuffer sb = new StringBuffer("&");
/* 195:367 */     sb.append(name);
/* 196:368 */     sb.append(';');
/* 197:369 */     String entityString = sb.toString();
/* 198:    */     
/* 199:371 */     boolean extra = defineChar2StringMapping(entityString, value);
/* 200:372 */     return extra;
/* 201:    */   }
/* 202:    */   
/* 203:    */   String getOutputStringForChar(char value)
/* 204:    */   {
/* 205:408 */     this.m_charKey.setChar(value);
/* 206:409 */     return (String)this.m_charToString.get(this.m_charKey);
/* 207:    */   }
/* 208:    */   
/* 209:    */   final boolean shouldMapAttrChar(int value)
/* 210:    */   {
/* 211:426 */     if (value < 128) {
/* 212:427 */       return this.shouldMapAttrChar_ASCII[value];
/* 213:    */     }
/* 214:431 */     return get(value);
/* 215:    */   }
/* 216:    */   
/* 217:    */   final boolean shouldMapTextChar(int value)
/* 218:    */   {
/* 219:449 */     if (value < 128) {
/* 220:450 */       return this.shouldMapTextChar_ASCII[value];
/* 221:    */     }
/* 222:454 */     return get(value);
/* 223:    */   }
/* 224:    */   
/* 225:    */   private static CharInfo getCharInfoBasedOnPrivilege(String entitiesFileName, String method, boolean internal)
/* 226:    */   {
/* 227:462 */     (CharInfo)AccessController.doPrivileged(new PrivilegedAction()
/* 228:    */     {
/* 229:    */       private final String val$entitiesFileName;
/* 230:    */       private final String val$method;
/* 231:    */       private final boolean val$internal;
/* 232:    */       
/* 233:    */       public Object run()
/* 234:    */       {
/* 235:465 */         return new CharInfo(this.val$entitiesFileName, this.val$method, this.val$internal, null);
/* 236:    */       }
/* 237:    */     });
/* 238:    */   }
/* 239:    */   
/* 240:    */   static CharInfo getCharInfo(String entitiesFileName, String method)
/* 241:    */   {
/* 242:491 */     CharInfo charInfo = (CharInfo)m_getCharInfoCache.get(entitiesFileName);
/* 243:492 */     if (charInfo != null) {
/* 244:493 */       return mutableCopyOf(charInfo);
/* 245:    */     }
/* 246:    */     try
/* 247:    */     {
/* 248:498 */       charInfo = getCharInfoBasedOnPrivilege(entitiesFileName, method, true);
/* 249:    */       
/* 250:    */ 
/* 251:    */ 
/* 252:502 */       m_getCharInfoCache.put(entitiesFileName, charInfo);
/* 253:503 */       return mutableCopyOf(charInfo);
/* 254:    */     }
/* 255:    */     catch (Exception e)
/* 256:    */     {
/* 257:    */       try
/* 258:    */       {
/* 259:508 */         return getCharInfoBasedOnPrivilege(entitiesFileName, method, false);
/* 260:    */       }
/* 261:    */       catch (Exception e)
/* 262:    */       {
/* 263:    */         String absoluteEntitiesFileName;
/* 264:514 */         if (entitiesFileName.indexOf(':') < 0) {
/* 265:515 */           absoluteEntitiesFileName = SystemIDResolver.getAbsoluteURIFromRelative(entitiesFileName);
/* 266:    */         } else {
/* 267:    */           try
/* 268:    */           {
/* 269:519 */             absoluteEntitiesFileName = SystemIDResolver.getAbsoluteURI(entitiesFileName, null);
/* 270:    */           }
/* 271:    */           catch (TransformerException te)
/* 272:    */           {
/* 273:522 */             throw new WrappedRuntimeException(te);
/* 274:    */           }
/* 275:    */         }
/* 276:    */       }
/* 277:    */     }
/* 278:526 */     return getCharInfoBasedOnPrivilege(entitiesFileName, method, false);
/* 279:    */   }
/* 280:    */   
/* 281:    */   private static CharInfo mutableCopyOf(CharInfo charInfo)
/* 282:    */   {
/* 283:536 */     CharInfo copy = new CharInfo();
/* 284:    */     
/* 285:538 */     int max = charInfo.array_of_bits.length;
/* 286:539 */     System.arraycopy(charInfo.array_of_bits, 0, copy.array_of_bits, 0, max);
/* 287:    */     
/* 288:541 */     copy.firstWordNotUsed = charInfo.firstWordNotUsed;
/* 289:    */     
/* 290:543 */     max = charInfo.shouldMapAttrChar_ASCII.length;
/* 291:544 */     System.arraycopy(charInfo.shouldMapAttrChar_ASCII, 0, copy.shouldMapAttrChar_ASCII, 0, max);
/* 292:    */     
/* 293:546 */     max = charInfo.shouldMapTextChar_ASCII.length;
/* 294:547 */     System.arraycopy(charInfo.shouldMapTextChar_ASCII, 0, copy.shouldMapTextChar_ASCII, 0, max);
/* 295:    */     
/* 296:    */ 
/* 297:    */ 
/* 298:551 */     copy.m_charToString = ((HashMap)charInfo.m_charToString.clone());
/* 299:    */     
/* 300:553 */     copy.onlyQuotAmpLtGt = charInfo.onlyQuotAmpLtGt;
/* 301:    */     
/* 302:555 */     return copy;
/* 303:    */   }
/* 304:    */   
/* 305:565 */   private static Hashtable m_getCharInfoCache = new Hashtable();
/* 306:    */   
/* 307:    */   private static int arrayIndex(int i)
/* 308:    */   {
/* 309:574 */     return i >> 5;
/* 310:    */   }
/* 311:    */   
/* 312:    */   private static int bit(int i)
/* 313:    */   {
/* 314:583 */     int ret = 1 << (i & 0x1F);
/* 315:584 */     return ret;
/* 316:    */   }
/* 317:    */   
/* 318:    */   private int[] createEmptySetOfIntegers(int max)
/* 319:    */   {
/* 320:592 */     this.firstWordNotUsed = 0;
/* 321:    */     
/* 322:594 */     int[] arr = new int[arrayIndex(max - 1) + 1];
/* 323:595 */     return arr;
/* 324:    */   }
/* 325:    */   
/* 326:    */   private final void set(int i)
/* 327:    */   {
/* 328:606 */     setASCIItextDirty(i);
/* 329:607 */     setASCIIattrDirty(i);
/* 330:    */     
/* 331:609 */     int j = i >> 5;
/* 332:610 */     int k = j + 1;
/* 333:612 */     if (this.firstWordNotUsed < k) {
/* 334:613 */       this.firstWordNotUsed = k;
/* 335:    */     }
/* 336:615 */     this.array_of_bits[j] |= 1 << (i & 0x1F);
/* 337:    */   }
/* 338:    */   
/* 339:    */   private final boolean get(int i)
/* 340:    */   {
/* 341:631 */     boolean in_the_set = false;
/* 342:632 */     int j = i >> 5;
/* 343:635 */     if (j < this.firstWordNotUsed) {
/* 344:636 */       in_the_set = (this.array_of_bits[j] & 1 << (i & 0x1F)) != 0;
/* 345:    */     }
/* 346:639 */     return in_the_set;
/* 347:    */   }
/* 348:    */   
/* 349:    */   private boolean extraEntity(String outputString, int charToMap)
/* 350:    */   {
/* 351:659 */     boolean extra = false;
/* 352:660 */     if (charToMap < 128) {
/* 353:662 */       switch (charToMap)
/* 354:    */       {
/* 355:    */       case 34: 
/* 356:665 */         if (!outputString.equals("&quot;")) {
/* 357:666 */           extra = true;
/* 358:    */         }
/* 359:    */         break;
/* 360:    */       case 38: 
/* 361:669 */         if (!outputString.equals("&amp;")) {
/* 362:670 */           extra = true;
/* 363:    */         }
/* 364:    */         break;
/* 365:    */       case 60: 
/* 366:673 */         if (!outputString.equals("&lt;")) {
/* 367:674 */           extra = true;
/* 368:    */         }
/* 369:    */         break;
/* 370:    */       case 62: 
/* 371:677 */         if (!outputString.equals("&gt;")) {
/* 372:678 */           extra = true;
/* 373:    */         }
/* 374:    */         break;
/* 375:    */       default: 
/* 376:681 */         extra = true;
/* 377:    */       }
/* 378:    */     }
/* 379:684 */     return extra;
/* 380:    */   }
/* 381:    */   
/* 382:    */   private void setASCIItextDirty(int j)
/* 383:    */   {
/* 384:695 */     if ((0 <= j) && (j < 128)) {
/* 385:697 */       this.shouldMapTextChar_ASCII[j] = true;
/* 386:    */     }
/* 387:    */   }
/* 388:    */   
/* 389:    */   private void setASCIIattrDirty(int j)
/* 390:    */   {
/* 391:709 */     if ((0 <= j) && (j < 128)) {
/* 392:711 */       this.shouldMapAttrChar_ASCII[j] = true;
/* 393:    */     }
/* 394:    */   }
/* 395:    */   
/* 396:    */   boolean defineChar2StringMapping(String outputString, char inputChar)
/* 397:    */   {
/* 398:731 */     CharKey character = new CharKey(inputChar);
/* 399:732 */     this.m_charToString.put(character, outputString);
/* 400:733 */     set(inputChar);
/* 401:    */     
/* 402:735 */     boolean extraMapping = extraEntity(outputString, inputChar);
/* 403:736 */     return extraMapping;
/* 404:    */   }
/* 405:    */   
/* 406:    */   private static class CharKey
/* 407:    */   {
/* 408:    */     private char m_char;
/* 409:    */     
/* 410:    */     public CharKey(char key)
/* 411:    */     {
/* 412:759 */       this.m_char = key;
/* 413:    */     }
/* 414:    */     
/* 415:    */     public CharKey() {}
/* 416:    */     
/* 417:    */     public final void setChar(char c)
/* 418:    */     {
/* 419:778 */       this.m_char = c;
/* 420:    */     }
/* 421:    */     
/* 422:    */     public final int hashCode()
/* 423:    */     {
/* 424:790 */       return this.m_char;
/* 425:    */     }
/* 426:    */     
/* 427:    */     public final boolean equals(Object obj)
/* 428:    */     {
/* 429:802 */       return ((CharKey)obj).m_char == this.m_char;
/* 430:    */     }
/* 431:    */   }
/* 432:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.CharInfo
 * JD-Core Version:    0.7.0.1
 */