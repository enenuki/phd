/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.AST;
/*   4:    */ import antlr.collections.impl.ASTArray;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.lang.reflect.Constructor;
/*   7:    */ import java.util.Hashtable;
/*   8:    */ 
/*   9:    */ public class ASTFactory
/*  10:    */ {
/*  11: 32 */   protected String theASTNodeType = null;
/*  12: 33 */   protected Class theASTNodeTypeClass = null;
/*  13: 51 */   protected Hashtable tokenTypeToASTClassMap = null;
/*  14:    */   
/*  15:    */   public ASTFactory() {}
/*  16:    */   
/*  17:    */   public ASTFactory(Hashtable paramHashtable)
/*  18:    */   {
/*  19: 61 */     setTokenTypeToASTClassMap(paramHashtable);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void setTokenTypeASTNodeType(int paramInt, String paramString)
/*  23:    */     throws IllegalArgumentException
/*  24:    */   {
/*  25: 83 */     if (this.tokenTypeToASTClassMap == null) {
/*  26: 84 */       this.tokenTypeToASTClassMap = new Hashtable();
/*  27:    */     }
/*  28: 86 */     if (paramString == null)
/*  29:    */     {
/*  30: 87 */       this.tokenTypeToASTClassMap.remove(new Integer(paramInt));
/*  31: 88 */       return;
/*  32:    */     }
/*  33: 90 */     Class localClass = null;
/*  34:    */     try
/*  35:    */     {
/*  36: 92 */       localClass = Utils.loadClass(paramString);
/*  37: 93 */       this.tokenTypeToASTClassMap.put(new Integer(paramInt), localClass);
/*  38:    */     }
/*  39:    */     catch (Exception localException)
/*  40:    */     {
/*  41: 96 */       throw new IllegalArgumentException("Invalid class, " + paramString);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Class getASTNodeType(int paramInt)
/*  46:    */   {
/*  47:106 */     if (this.tokenTypeToASTClassMap != null)
/*  48:    */     {
/*  49:107 */       Class localClass = (Class)this.tokenTypeToASTClassMap.get(new Integer(paramInt));
/*  50:108 */       if (localClass != null) {
/*  51:109 */         return localClass;
/*  52:    */       }
/*  53:    */     }
/*  54:114 */     if (this.theASTNodeTypeClass != null) {
/*  55:115 */       return this.theASTNodeTypeClass;
/*  56:    */     }
/*  57:119 */     return CommonAST.class;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void addASTChild(ASTPair paramASTPair, AST paramAST)
/*  61:    */   {
/*  62:124 */     if (paramAST != null)
/*  63:    */     {
/*  64:125 */       if (paramASTPair.root == null) {
/*  65:127 */         paramASTPair.root = paramAST;
/*  66:130 */       } else if (paramASTPair.child == null) {
/*  67:132 */         paramASTPair.root.setFirstChild(paramAST);
/*  68:    */       } else {
/*  69:135 */         paramASTPair.child.setNextSibling(paramAST);
/*  70:    */       }
/*  71:139 */       paramASTPair.child = paramAST;
/*  72:140 */       paramASTPair.advanceChildToEnd();
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public AST create()
/*  77:    */   {
/*  78:148 */     return create(0);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public AST create(int paramInt)
/*  82:    */   {
/*  83:152 */     Class localClass = getASTNodeType(paramInt);
/*  84:153 */     AST localAST = create(localClass);
/*  85:154 */     if (localAST != null) {
/*  86:155 */       localAST.initialize(paramInt, "");
/*  87:    */     }
/*  88:157 */     return localAST;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public AST create(int paramInt, String paramString)
/*  92:    */   {
/*  93:161 */     AST localAST = create(paramInt);
/*  94:162 */     if (localAST != null) {
/*  95:163 */       localAST.initialize(paramInt, paramString);
/*  96:    */     }
/*  97:165 */     return localAST;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public AST create(int paramInt, String paramString1, String paramString2)
/* 101:    */   {
/* 102:174 */     AST localAST = create(paramString2);
/* 103:175 */     if (localAST != null) {
/* 104:176 */       localAST.initialize(paramInt, paramString1);
/* 105:    */     }
/* 106:178 */     return localAST;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public AST create(AST paramAST)
/* 110:    */   {
/* 111:185 */     if (paramAST == null) {
/* 112:185 */       return null;
/* 113:    */     }
/* 114:186 */     AST localAST = create(paramAST.getType());
/* 115:187 */     if (localAST != null) {
/* 116:188 */       localAST.initialize(paramAST);
/* 117:    */     }
/* 118:190 */     return localAST;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public AST create(Token paramToken)
/* 122:    */   {
/* 123:194 */     AST localAST = create(paramToken.getType());
/* 124:195 */     if (localAST != null) {
/* 125:196 */       localAST.initialize(paramToken);
/* 126:    */     }
/* 127:198 */     return localAST;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public AST create(Token paramToken, String paramString)
/* 131:    */   {
/* 132:210 */     AST localAST = createUsingCtor(paramToken, paramString);
/* 133:211 */     return localAST;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public AST create(String paramString)
/* 137:    */   {
/* 138:218 */     Class localClass = null;
/* 139:    */     try
/* 140:    */     {
/* 141:220 */       localClass = Utils.loadClass(paramString);
/* 142:    */     }
/* 143:    */     catch (Exception localException)
/* 144:    */     {
/* 145:223 */       throw new IllegalArgumentException("Invalid class, " + paramString);
/* 146:    */     }
/* 147:225 */     return create(localClass);
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected AST createUsingCtor(Token paramToken, String paramString)
/* 151:    */   {
/* 152:232 */     Class localClass = null;
/* 153:233 */     AST localAST = null;
/* 154:    */     try
/* 155:    */     {
/* 156:235 */       localClass = Utils.loadClass(paramString);
/* 157:236 */       Class[] arrayOfClass = { Token.class };
/* 158:    */       try
/* 159:    */       {
/* 160:238 */         Constructor localConstructor = localClass.getConstructor(arrayOfClass);
/* 161:239 */         localAST = (AST)localConstructor.newInstance(new Object[] { paramToken });
/* 162:    */       }
/* 163:    */       catch (NoSuchMethodException localNoSuchMethodException)
/* 164:    */       {
/* 165:244 */         localAST = create(localClass);
/* 166:245 */         if (localAST != null) {
/* 167:246 */           localAST.initialize(paramToken);
/* 168:    */         }
/* 169:    */       }
/* 170:    */     }
/* 171:    */     catch (Exception localException)
/* 172:    */     {
/* 173:251 */       throw new IllegalArgumentException("Invalid class or can't make instance, " + paramString);
/* 174:    */     }
/* 175:253 */     return localAST;
/* 176:    */   }
/* 177:    */   
/* 178:    */   protected AST create(Class paramClass)
/* 179:    */   {
/* 180:260 */     AST localAST = null;
/* 181:    */     try
/* 182:    */     {
/* 183:262 */       localAST = (AST)paramClass.newInstance();
/* 184:    */     }
/* 185:    */     catch (Exception localException)
/* 186:    */     {
/* 187:265 */       error("Can't create AST Node " + paramClass.getName());
/* 188:266 */       return null;
/* 189:    */     }
/* 190:268 */     return localAST;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public AST dup(AST paramAST)
/* 194:    */   {
/* 195:280 */     if (paramAST == null) {
/* 196:281 */       return null;
/* 197:    */     }
/* 198:283 */     AST localAST = create(paramAST.getClass());
/* 199:284 */     localAST.initialize(paramAST);
/* 200:285 */     return localAST;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public AST dupList(AST paramAST)
/* 204:    */   {
/* 205:290 */     AST localAST1 = dupTree(paramAST);
/* 206:291 */     AST localAST2 = localAST1;
/* 207:292 */     while (paramAST != null)
/* 208:    */     {
/* 209:293 */       paramAST = paramAST.getNextSibling();
/* 210:294 */       localAST2.setNextSibling(dupTree(paramAST));
/* 211:295 */       localAST2 = localAST2.getNextSibling();
/* 212:    */     }
/* 213:297 */     return localAST1;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public AST dupTree(AST paramAST)
/* 217:    */   {
/* 218:304 */     AST localAST = dup(paramAST);
/* 219:306 */     if (paramAST != null) {
/* 220:307 */       localAST.setFirstChild(dupList(paramAST.getFirstChild()));
/* 221:    */     }
/* 222:309 */     return localAST;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public AST make(AST[] paramArrayOfAST)
/* 226:    */   {
/* 227:319 */     if ((paramArrayOfAST == null) || (paramArrayOfAST.length == 0)) {
/* 228:319 */       return null;
/* 229:    */     }
/* 230:320 */     AST localAST1 = paramArrayOfAST[0];
/* 231:321 */     AST localAST2 = null;
/* 232:322 */     if (localAST1 != null) {
/* 233:323 */       localAST1.setFirstChild(null);
/* 234:    */     }
/* 235:326 */     for (int i = 1; i < paramArrayOfAST.length; i++) {
/* 236:327 */       if (paramArrayOfAST[i] != null)
/* 237:    */       {
/* 238:328 */         if (localAST1 == null)
/* 239:    */         {
/* 240:330 */           localAST1 = localAST2 = paramArrayOfAST[i];
/* 241:    */         }
/* 242:332 */         else if (localAST2 == null)
/* 243:    */         {
/* 244:333 */           localAST1.setFirstChild(paramArrayOfAST[i]);
/* 245:334 */           localAST2 = localAST1.getFirstChild();
/* 246:    */         }
/* 247:    */         else
/* 248:    */         {
/* 249:337 */           localAST2.setNextSibling(paramArrayOfAST[i]);
/* 250:338 */           localAST2 = localAST2.getNextSibling();
/* 251:    */         }
/* 252:341 */         while (localAST2.getNextSibling() != null) {
/* 253:342 */           localAST2 = localAST2.getNextSibling();
/* 254:    */         }
/* 255:    */       }
/* 256:    */     }
/* 257:345 */     return localAST1;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public AST make(ASTArray paramASTArray)
/* 261:    */   {
/* 262:352 */     return make(paramASTArray.array);
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void makeASTRoot(ASTPair paramASTPair, AST paramAST)
/* 266:    */   {
/* 267:357 */     if (paramAST != null)
/* 268:    */     {
/* 269:359 */       paramAST.addChild(paramASTPair.root);
/* 270:    */       
/* 271:361 */       paramASTPair.child = paramASTPair.root;
/* 272:362 */       paramASTPair.advanceChildToEnd();
/* 273:    */       
/* 274:364 */       paramASTPair.root = paramAST;
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void setASTNodeClass(Class paramClass)
/* 279:    */   {
/* 280:369 */     if (paramClass != null)
/* 281:    */     {
/* 282:370 */       this.theASTNodeTypeClass = paramClass;
/* 283:371 */       this.theASTNodeType = paramClass.getName();
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setASTNodeClass(String paramString)
/* 288:    */   {
/* 289:376 */     this.theASTNodeType = paramString;
/* 290:    */     try
/* 291:    */     {
/* 292:378 */       this.theASTNodeTypeClass = Utils.loadClass(paramString);
/* 293:    */     }
/* 294:    */     catch (Exception localException)
/* 295:    */     {
/* 296:384 */       error("Can't find/access AST Node type" + paramString);
/* 297:    */     }
/* 298:    */   }
/* 299:    */   
/* 300:    */   /**
/* 301:    */    * @deprecated
/* 302:    */    */
/* 303:    */   public void setASTNodeType(String paramString)
/* 304:    */   {
/* 305:392 */     setASTNodeClass(paramString);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public Hashtable getTokenTypeToASTClassMap()
/* 309:    */   {
/* 310:396 */     return this.tokenTypeToASTClassMap;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setTokenTypeToASTClassMap(Hashtable paramHashtable)
/* 314:    */   {
/* 315:400 */     this.tokenTypeToASTClassMap = paramHashtable;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void error(String paramString)
/* 319:    */   {
/* 320:408 */     System.err.println(paramString);
/* 321:    */   }
/* 322:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ASTFactory
 * JD-Core Version:    0.7.0.1
 */