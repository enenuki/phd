/*   1:    */ package org.apache.xalan.extensions;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.lang.reflect.Modifier;
/*   8:    */ import java.util.Vector;
/*   9:    */ import javax.xml.transform.TransformerException;
/*  10:    */ import org.apache.xalan.templates.ElemExtensionCall;
/*  11:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*  12:    */ import org.apache.xalan.templates.Stylesheet;
/*  13:    */ import org.apache.xalan.trace.ExtensionEvent;
/*  14:    */ import org.apache.xalan.trace.TraceManager;
/*  15:    */ import org.apache.xalan.transformer.TransformerImpl;
/*  16:    */ import org.apache.xpath.XPathContext;
/*  17:    */ import org.apache.xpath.functions.FuncExtFunction;
/*  18:    */ import org.apache.xpath.objects.XObject;
/*  19:    */ 
/*  20:    */ public class ExtensionHandlerJavaClass
/*  21:    */   extends ExtensionHandlerJava
/*  22:    */ {
/*  23: 60 */   private Class m_classObj = null;
/*  24: 67 */   private Object m_defaultInstance = null;
/*  25:    */   
/*  26:    */   public ExtensionHandlerJavaClass(String namespaceUri, String scriptLang, String className)
/*  27:    */   {
/*  28: 81 */     super(namespaceUri, scriptLang, className);
/*  29:    */     try
/*  30:    */     {
/*  31: 84 */       this.m_classObj = ExtensionHandler.getClassForName(className);
/*  32:    */     }
/*  33:    */     catch (ClassNotFoundException e) {}
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean isFunctionAvailable(String function)
/*  37:    */   {
/*  38:105 */     Method[] methods = this.m_classObj.getMethods();
/*  39:106 */     int nMethods = methods.length;
/*  40:107 */     for (int i = 0; i < nMethods; i++) {
/*  41:109 */       if (methods[i].getName().equals(function)) {
/*  42:110 */         return true;
/*  43:    */       }
/*  44:    */     }
/*  45:112 */     return false;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean isElementAvailable(String element)
/*  49:    */   {
/*  50:126 */     Method[] methods = this.m_classObj.getMethods();
/*  51:127 */     int nMethods = methods.length;
/*  52:128 */     for (int i = 0; i < nMethods; i++) {
/*  53:130 */       if (methods[i].getName().equals(element))
/*  54:    */       {
/*  55:132 */         Class[] paramTypes = methods[i].getParameterTypes();
/*  56:133 */         if ((paramTypes.length == 2) && (paramTypes[0].isAssignableFrom(XSLProcessorContext.class)) && (paramTypes[1].isAssignableFrom(ElemExtensionCall.class))) {
/*  57:139 */           return true;
/*  58:    */         }
/*  59:    */       }
/*  60:    */     }
/*  61:143 */     return false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Object callFunction(String funcName, Vector args, Object methodKey, ExpressionContext exprContext)
/*  65:    */     throws TransformerException
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69:196 */       TransformerImpl trans = exprContext != null ? (TransformerImpl)exprContext.getXPathContext().getOwnerObject() : null;
/*  70:    */       Class[] paramTypes;
/*  71:198 */       if (funcName.equals("new"))
/*  72:    */       {
/*  73:200 */         methodArgs = new Object[args.size()];
/*  74:201 */         convertedArgs = new Object[1][];
/*  75:202 */         for (int i = 0; i < methodArgs.length; i++) {
/*  76:204 */           methodArgs[i] = args.get(i);
/*  77:    */         }
/*  78:206 */         Constructor c = null;
/*  79:207 */         if (methodKey != null) {
/*  80:208 */           c = (Constructor)getFromCache(methodKey, null, methodArgs);
/*  81:    */         }
/*  82:210 */         if ((c != null) && (!trans.getDebug())) {
/*  83:    */           try
/*  84:    */           {
/*  85:214 */             paramTypes = c.getParameterTypes();
/*  86:215 */             MethodResolver.convertParams(methodArgs, convertedArgs, paramTypes, exprContext);
/*  87:    */             
/*  88:217 */             return c.newInstance(convertedArgs[0]);
/*  89:    */           }
/*  90:    */           catch (InvocationTargetException ite)
/*  91:    */           {
/*  92:221 */             throw ite;
/*  93:    */           }
/*  94:    */           catch (Exception e) {}
/*  95:    */         }
/*  96:228 */         c = MethodResolver.getConstructor(this.m_classObj, methodArgs, convertedArgs, exprContext);
/*  97:232 */         if (methodKey != null) {
/*  98:233 */           putToCache(methodKey, null, methodArgs, c);
/*  99:    */         }
/* 100:235 */         if ((trans != null) && (trans.getDebug()))
/* 101:    */         {
/* 102:236 */           trans.getTraceManager().fireExtensionEvent(new ExtensionEvent(trans, c, convertedArgs[0]));
/* 103:    */           Object result;
/* 104:    */           try
/* 105:    */           {
/* 106:240 */             result = c.newInstance(convertedArgs[0]);
/* 107:    */           }
/* 108:    */           catch (Exception e)
/* 109:    */           {
/* 110:242 */             throw e;
/* 111:    */           }
/* 112:    */           finally
/* 113:    */           {
/* 114:244 */             trans.getTraceManager().fireExtensionEndEvent(new ExtensionEvent(trans, c, convertedArgs[0]));
/* 115:    */           }
/* 116:247 */           return result;
/* 117:    */         }
/* 118:249 */         return c.newInstance(convertedArgs[0]);
/* 119:    */       }
/* 120:256 */       Object targetObject = null;
/* 121:257 */       Object[] methodArgs = new Object[args.size()];
/* 122:258 */       Object[][] convertedArgs = new Object[1][];
/* 123:259 */       for (int i = 0; i < methodArgs.length; i++) {
/* 124:261 */         methodArgs[i] = args.get(i);
/* 125:    */       }
/* 126:263 */       Method m = null;
/* 127:264 */       if (methodKey != null) {
/* 128:265 */         m = (Method)getFromCache(methodKey, null, methodArgs);
/* 129:    */       }
/* 130:267 */       if ((m != null) && (!trans.getDebug())) {
/* 131:    */         try
/* 132:    */         {
/* 133:271 */           paramTypes = m.getParameterTypes();
/* 134:272 */           MethodResolver.convertParams(methodArgs, convertedArgs, paramTypes, exprContext);
/* 135:274 */           if (Modifier.isStatic(m.getModifiers())) {
/* 136:275 */             return m.invoke(null, convertedArgs[0]);
/* 137:    */           }
/* 138:280 */           int nTargetArgs = convertedArgs[0].length;
/* 139:281 */           if (ExpressionContext.class.isAssignableFrom(paramTypes[0])) {
/* 140:282 */             nTargetArgs--;
/* 141:    */           }
/* 142:283 */           if (methodArgs.length <= nTargetArgs) {
/* 143:284 */             return m.invoke(this.m_defaultInstance, convertedArgs[0]);
/* 144:    */           }
/* 145:287 */           targetObject = methodArgs[0];
/* 146:289 */           if ((targetObject instanceof XObject)) {
/* 147:290 */             targetObject = ((XObject)targetObject).object();
/* 148:    */           }
/* 149:292 */           return m.invoke(targetObject, convertedArgs[0]);
/* 150:    */         }
/* 151:    */         catch (InvocationTargetException ite)
/* 152:    */         {
/* 153:298 */           throw ite;
/* 154:    */         }
/* 155:    */         catch (Exception e) {}
/* 156:    */       }
/* 157:    */       int resolveType;
/* 158:306 */       if (args.size() > 0)
/* 159:    */       {
/* 160:308 */         targetObject = methodArgs[0];
/* 161:310 */         if ((targetObject instanceof XObject)) {
/* 162:311 */           targetObject = ((XObject)targetObject).object();
/* 163:    */         }
/* 164:313 */         if (this.m_classObj.isAssignableFrom(targetObject.getClass())) {
/* 165:314 */           resolveType = 4;
/* 166:    */         } else {
/* 167:316 */           resolveType = 3;
/* 168:    */         }
/* 169:    */       }
/* 170:    */       else
/* 171:    */       {
/* 172:320 */         targetObject = null;
/* 173:321 */         resolveType = 3;
/* 174:    */       }
/* 175:324 */       m = MethodResolver.getMethod(this.m_classObj, funcName, methodArgs, convertedArgs, exprContext, resolveType);
/* 176:330 */       if (methodKey != null) {
/* 177:331 */         putToCache(methodKey, null, methodArgs, m);
/* 178:    */       }
/* 179:333 */       if (4 == resolveType)
/* 180:    */       {
/* 181:334 */         if ((trans != null) && (trans.getDebug()))
/* 182:    */         {
/* 183:335 */           trans.getTraceManager().fireExtensionEvent(m, targetObject, convertedArgs[0]);
/* 184:    */           Object result;
/* 185:    */           try
/* 186:    */           {
/* 187:339 */             result = m.invoke(targetObject, convertedArgs[0]);
/* 188:    */           }
/* 189:    */           catch (Exception e)
/* 190:    */           {
/* 191:341 */             throw e;
/* 192:    */           }
/* 193:    */           finally
/* 194:    */           {
/* 195:343 */             trans.getTraceManager().fireExtensionEndEvent(m, targetObject, convertedArgs[0]);
/* 196:    */           }
/* 197:346 */           return result;
/* 198:    */         }
/* 199:348 */         return m.invoke(targetObject, convertedArgs[0]);
/* 200:    */       }
/* 201:352 */       if (Modifier.isStatic(m.getModifiers()))
/* 202:    */       {
/* 203:353 */         if ((trans != null) && (trans.getDebug()))
/* 204:    */         {
/* 205:354 */           trans.getTraceManager().fireExtensionEvent(m, null, convertedArgs[0]);
/* 206:    */           Object result;
/* 207:    */           try
/* 208:    */           {
/* 209:358 */             result = m.invoke(null, convertedArgs[0]);
/* 210:    */           }
/* 211:    */           catch (Exception e)
/* 212:    */           {
/* 213:360 */             throw e;
/* 214:    */           }
/* 215:    */           finally
/* 216:    */           {
/* 217:362 */             trans.getTraceManager().fireExtensionEndEvent(m, null, convertedArgs[0]);
/* 218:    */           }
/* 219:365 */           return result;
/* 220:    */         }
/* 221:367 */         return m.invoke(null, convertedArgs[0]);
/* 222:    */       }
/* 223:371 */       if (null == this.m_defaultInstance) {
/* 224:373 */         if ((trans != null) && (trans.getDebug()))
/* 225:    */         {
/* 226:374 */           trans.getTraceManager().fireExtensionEvent(new ExtensionEvent(trans, this.m_classObj));
/* 227:    */           try
/* 228:    */           {
/* 229:377 */             this.m_defaultInstance = this.m_classObj.newInstance();
/* 230:    */           }
/* 231:    */           catch (Exception e)
/* 232:    */           {
/* 233:379 */             throw e;
/* 234:    */           }
/* 235:    */           finally
/* 236:    */           {
/* 237:381 */             trans.getTraceManager().fireExtensionEndEvent(new ExtensionEvent(trans, this.m_classObj));
/* 238:    */           }
/* 239:    */         }
/* 240:    */         else
/* 241:    */         {
/* 242:385 */           this.m_defaultInstance = this.m_classObj.newInstance();
/* 243:    */         }
/* 244:    */       }
/* 245:387 */       if ((trans != null) && (trans.getDebug()))
/* 246:    */       {
/* 247:388 */         trans.getTraceManager().fireExtensionEvent(m, this.m_defaultInstance, convertedArgs[0]);
/* 248:    */         Object result;
/* 249:    */         try
/* 250:    */         {
/* 251:392 */           result = m.invoke(this.m_defaultInstance, convertedArgs[0]);
/* 252:    */         }
/* 253:    */         catch (Exception e)
/* 254:    */         {
/* 255:394 */           throw e;
/* 256:    */         }
/* 257:    */         finally
/* 258:    */         {
/* 259:396 */           trans.getTraceManager().fireExtensionEndEvent(m, this.m_defaultInstance, convertedArgs[0]);
/* 260:    */         }
/* 261:399 */         return result;
/* 262:    */       }
/* 263:401 */       return m.invoke(this.m_defaultInstance, convertedArgs[0]);
/* 264:    */     }
/* 265:    */     catch (InvocationTargetException ite)
/* 266:    */     {
/* 267:409 */       Throwable resultException = ite;
/* 268:410 */       Throwable targetException = ite.getTargetException();
/* 269:412 */       if ((targetException instanceof TransformerException)) {
/* 270:413 */         throw ((TransformerException)targetException);
/* 271:    */       }
/* 272:414 */       if (targetException != null) {
/* 273:415 */         resultException = targetException;
/* 274:    */       }
/* 275:417 */       throw new TransformerException(resultException);
/* 276:    */     }
/* 277:    */     catch (Exception e)
/* 278:    */     {
/* 279:422 */       throw new TransformerException(e);
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   public Object callFunction(FuncExtFunction extFunction, Vector args, ExpressionContext exprContext)
/* 284:    */     throws TransformerException
/* 285:    */   {
/* 286:440 */     return callFunction(extFunction.getFunctionName(), args, extFunction.getMethodKey(), exprContext);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void processElement(String localPart, ElemTemplateElement element, TransformerImpl transformer, Stylesheet stylesheetTree, Object methodKey)
/* 290:    */     throws TransformerException, IOException
/* 291:    */   {
/* 292:467 */     Object result = null;
/* 293:    */     
/* 294:469 */     Method m = (Method)getFromCache(methodKey, null, null);
/* 295:470 */     if (null == m)
/* 296:    */     {
/* 297:    */       try
/* 298:    */       {
/* 299:474 */         m = MethodResolver.getElementMethod(this.m_classObj, localPart);
/* 300:475 */         if ((null == this.m_defaultInstance) && (!Modifier.isStatic(m.getModifiers()))) {
/* 301:477 */           if (transformer.getDebug())
/* 302:    */           {
/* 303:478 */             transformer.getTraceManager().fireExtensionEvent(new ExtensionEvent(transformer, this.m_classObj));
/* 304:    */             try
/* 305:    */             {
/* 306:481 */               this.m_defaultInstance = this.m_classObj.newInstance();
/* 307:    */             }
/* 308:    */             catch (Exception e)
/* 309:    */             {
/* 310:483 */               throw e;
/* 311:    */             }
/* 312:    */             finally
/* 313:    */             {
/* 314:485 */               transformer.getTraceManager().fireExtensionEndEvent(new ExtensionEvent(transformer, this.m_classObj));
/* 315:    */             }
/* 316:    */           }
/* 317:    */           else
/* 318:    */           {
/* 319:489 */             this.m_defaultInstance = this.m_classObj.newInstance();
/* 320:    */           }
/* 321:    */         }
/* 322:    */       }
/* 323:    */       catch (Exception e)
/* 324:    */       {
/* 325:495 */         throw new TransformerException(e.getMessage(), e);
/* 326:    */       }
/* 327:497 */       putToCache(methodKey, null, null, m);
/* 328:    */     }
/* 329:500 */     XSLProcessorContext xpc = new XSLProcessorContext(transformer, stylesheetTree);
/* 330:    */     try
/* 331:    */     {
/* 332:505 */       if (transformer.getDebug())
/* 333:    */       {
/* 334:506 */         transformer.getTraceManager().fireExtensionEvent(m, this.m_defaultInstance, new Object[] { xpc, element });
/* 335:    */         try
/* 336:    */         {
/* 337:509 */           result = m.invoke(this.m_defaultInstance, new Object[] { xpc, element });
/* 338:    */         }
/* 339:    */         catch (Exception e)
/* 340:    */         {
/* 341:511 */           throw e;
/* 342:    */         }
/* 343:    */         finally
/* 344:    */         {
/* 345:513 */           transformer.getTraceManager().fireExtensionEndEvent(m, this.m_defaultInstance, new Object[] { xpc, element });
/* 346:    */         }
/* 347:    */       }
/* 348:    */       else
/* 349:    */       {
/* 350:517 */         result = m.invoke(this.m_defaultInstance, new Object[] { xpc, element });
/* 351:    */       }
/* 352:    */     }
/* 353:    */     catch (InvocationTargetException e)
/* 354:    */     {
/* 355:521 */       Throwable targetException = e.getTargetException();
/* 356:523 */       if ((targetException instanceof TransformerException)) {
/* 357:524 */         throw ((TransformerException)targetException);
/* 358:    */       }
/* 359:525 */       if (targetException != null) {
/* 360:526 */         throw new TransformerException(targetException.getMessage(), targetException);
/* 361:    */       }
/* 362:529 */       throw new TransformerException(e.getMessage(), e);
/* 363:    */     }
/* 364:    */     catch (Exception e)
/* 365:    */     {
/* 366:534 */       throw new TransformerException(e.getMessage(), e);
/* 367:    */     }
/* 368:537 */     if (result != null) {
/* 369:539 */       xpc.outputToResultTree(stylesheetTree, result);
/* 370:    */     }
/* 371:    */   }
/* 372:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.ExtensionHandlerJavaClass
 * JD-Core Version:    0.7.0.1
 */