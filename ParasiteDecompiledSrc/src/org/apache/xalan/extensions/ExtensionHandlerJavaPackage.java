/*   1:    */ package org.apache.xalan.extensions;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.lang.reflect.Modifier;
/*   8:    */ import java.util.Vector;
/*   9:    */ import javax.xml.transform.TransformerException;
/*  10:    */ import org.apache.xalan.res.XSLMessages;
/*  11:    */ import org.apache.xalan.templates.ElemExtensionCall;
/*  12:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*  13:    */ import org.apache.xalan.templates.Stylesheet;
/*  14:    */ import org.apache.xalan.trace.ExtensionEvent;
/*  15:    */ import org.apache.xalan.trace.TraceManager;
/*  16:    */ import org.apache.xalan.transformer.TransformerImpl;
/*  17:    */ import org.apache.xpath.XPathContext;
/*  18:    */ import org.apache.xpath.functions.FuncExtFunction;
/*  19:    */ import org.apache.xpath.objects.XObject;
/*  20:    */ 
/*  21:    */ public class ExtensionHandlerJavaPackage
/*  22:    */   extends ExtensionHandlerJava
/*  23:    */ {
/*  24:    */   public ExtensionHandlerJavaPackage(String namespaceUri, String scriptLang, String className)
/*  25:    */   {
/*  26: 79 */     super(namespaceUri, scriptLang, className);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean isFunctionAvailable(String function)
/*  30:    */   {
/*  31:    */     try
/*  32:    */     {
/*  33: 99 */       String fullName = this.m_className + function;
/*  34:100 */       int lastDot = fullName.lastIndexOf(".");
/*  35:101 */       if (lastDot >= 0)
/*  36:    */       {
/*  37:103 */         Class myClass = ExtensionHandler.getClassForName(fullName.substring(0, lastDot));
/*  38:104 */         Method[] methods = myClass.getMethods();
/*  39:105 */         int nMethods = methods.length;
/*  40:106 */         function = fullName.substring(lastDot + 1);
/*  41:107 */         for (int i = 0; i < nMethods; i++) {
/*  42:109 */           if (methods[i].getName().equals(function)) {
/*  43:110 */             return true;
/*  44:    */           }
/*  45:    */         }
/*  46:    */       }
/*  47:    */     }
/*  48:    */     catch (ClassNotFoundException cnfe) {}
/*  49:116 */     return false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean isElementAvailable(String element)
/*  53:    */   {
/*  54:    */     try
/*  55:    */     {
/*  56:132 */       String fullName = this.m_className + element;
/*  57:133 */       int lastDot = fullName.lastIndexOf(".");
/*  58:134 */       if (lastDot >= 0)
/*  59:    */       {
/*  60:136 */         Class myClass = ExtensionHandler.getClassForName(fullName.substring(0, lastDot));
/*  61:137 */         Method[] methods = myClass.getMethods();
/*  62:138 */         int nMethods = methods.length;
/*  63:139 */         element = fullName.substring(lastDot + 1);
/*  64:140 */         for (int i = 0; i < nMethods; i++) {
/*  65:142 */           if (methods[i].getName().equals(element))
/*  66:    */           {
/*  67:144 */             Class[] paramTypes = methods[i].getParameterTypes();
/*  68:145 */             if ((paramTypes.length == 2) && (paramTypes[0].isAssignableFrom(XSLProcessorContext.class)) && (paramTypes[1].isAssignableFrom(ElemExtensionCall.class))) {
/*  69:151 */               return true;
/*  70:    */             }
/*  71:    */           }
/*  72:    */         }
/*  73:    */       }
/*  74:    */     }
/*  75:    */     catch (ClassNotFoundException cnfe) {}
/*  76:159 */     return false;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Object callFunction(String funcName, Vector args, Object methodKey, ExpressionContext exprContext)
/*  80:    */     throws TransformerException
/*  81:    */   {
/*  82:214 */     int lastDot = funcName.lastIndexOf(".");
/*  83:    */     try
/*  84:    */     {
/*  85:221 */       TransformerImpl trans = exprContext != null ? (TransformerImpl)exprContext.getXPathContext().getOwnerObject() : null;
/*  86:    */       Class[] paramTypes;
/*  87:    */       String className;
/*  88:223 */       if (funcName.endsWith(".new"))
/*  89:    */       {
/*  90:225 */         methodArgs = new Object[args.size()];
/*  91:226 */         convertedArgs = new Object[1][];
/*  92:227 */         for (int i = 0; i < methodArgs.length; i++) {
/*  93:229 */           methodArgs[i] = args.get(i);
/*  94:    */         }
/*  95:232 */         Constructor c = methodKey != null ? (Constructor)getFromCache(methodKey, null, methodArgs) : null;
/*  96:235 */         if (c != null) {
/*  97:    */           try
/*  98:    */           {
/*  99:239 */             paramTypes = c.getParameterTypes();
/* 100:240 */             MethodResolver.convertParams(methodArgs, convertedArgs, paramTypes, exprContext);
/* 101:241 */             return c.newInstance(convertedArgs[0]);
/* 102:    */           }
/* 103:    */           catch (InvocationTargetException ite)
/* 104:    */           {
/* 105:245 */             throw ite;
/* 106:    */           }
/* 107:    */           catch (Exception e) {}
/* 108:    */         }
/* 109:252 */         className = this.m_className + funcName.substring(0, lastDot);
/* 110:    */         try
/* 111:    */         {
/* 112:255 */           classObj = ExtensionHandler.getClassForName(className);
/* 113:    */         }
/* 114:    */         catch (ClassNotFoundException e)
/* 115:    */         {
/* 116:259 */           throw new TransformerException(e);
/* 117:    */         }
/* 118:261 */         c = MethodResolver.getConstructor(classObj, methodArgs, convertedArgs, exprContext);
/* 119:265 */         if (methodKey != null) {
/* 120:266 */           putToCache(methodKey, null, methodArgs, c);
/* 121:    */         }
/* 122:268 */         if ((trans != null) && (trans.getDebug()))
/* 123:    */         {
/* 124:269 */           trans.getTraceManager().fireExtensionEvent(new ExtensionEvent(trans, c, convertedArgs[0]));
/* 125:    */           Object result;
/* 126:    */           try
/* 127:    */           {
/* 128:272 */             result = c.newInstance(convertedArgs[0]);
/* 129:    */           }
/* 130:    */           catch (Exception e)
/* 131:    */           {
/* 132:274 */             throw e;
/* 133:    */           }
/* 134:    */           finally
/* 135:    */           {
/* 136:276 */             trans.getTraceManager().fireExtensionEndEvent(new ExtensionEvent(trans, c, convertedArgs[0]));
/* 137:    */           }
/* 138:278 */           return result;
/* 139:    */         }
/* 140:280 */         return c.newInstance(convertedArgs[0]);
/* 141:    */       }
/* 142:283 */       if (-1 != lastDot)
/* 143:    */       {
/* 144:285 */         methodArgs = new Object[args.size()];
/* 145:286 */         convertedArgs = new Object[1][];
/* 146:287 */         for (int i = 0; i < methodArgs.length; i++) {
/* 147:289 */           methodArgs[i] = args.get(i);
/* 148:    */         }
/* 149:291 */         Method m = methodKey != null ? (Method)getFromCache(methodKey, null, methodArgs) : null;
/* 150:294 */         if ((m != null) && (!trans.getDebug())) {
/* 151:    */           try
/* 152:    */           {
/* 153:298 */             paramTypes = m.getParameterTypes();
/* 154:299 */             MethodResolver.convertParams(methodArgs, convertedArgs, paramTypes, exprContext);
/* 155:300 */             return m.invoke(null, convertedArgs[0]);
/* 156:    */           }
/* 157:    */           catch (InvocationTargetException ite)
/* 158:    */           {
/* 159:304 */             throw ite;
/* 160:    */           }
/* 161:    */           catch (Exception e) {}
/* 162:    */         }
/* 163:311 */         className = this.m_className + funcName.substring(0, lastDot);
/* 164:312 */         String methodName = funcName.substring(lastDot + 1);
/* 165:    */         try
/* 166:    */         {
/* 167:315 */           classObj = ExtensionHandler.getClassForName(className);
/* 168:    */         }
/* 169:    */         catch (ClassNotFoundException e)
/* 170:    */         {
/* 171:319 */           throw new TransformerException(e);
/* 172:    */         }
/* 173:321 */         m = MethodResolver.getMethod(classObj, methodName, methodArgs, convertedArgs, exprContext, 1);
/* 174:327 */         if (methodKey != null) {
/* 175:328 */           putToCache(methodKey, null, methodArgs, m);
/* 176:    */         }
/* 177:330 */         if ((trans != null) && (trans.getDebug()))
/* 178:    */         {
/* 179:331 */           trans.getTraceManager().fireExtensionEvent(m, null, convertedArgs[0]);
/* 180:    */           Object result;
/* 181:    */           try
/* 182:    */           {
/* 183:334 */             result = m.invoke(null, convertedArgs[0]);
/* 184:    */           }
/* 185:    */           catch (Exception e)
/* 186:    */           {
/* 187:336 */             throw e;
/* 188:    */           }
/* 189:    */           finally
/* 190:    */           {
/* 191:338 */             trans.getTraceManager().fireExtensionEndEvent(m, null, convertedArgs[0]);
/* 192:    */           }
/* 193:340 */           return result;
/* 194:    */         }
/* 195:343 */         return m.invoke(null, convertedArgs[0]);
/* 196:    */       }
/* 197:348 */       if (args.size() < 1) {
/* 198:350 */         throw new TransformerException(XSLMessages.createMessage("ER_INSTANCE_MTHD_CALL_REQUIRES", new Object[] { funcName }));
/* 199:    */       }
/* 200:353 */       Object targetObject = args.get(0);
/* 201:354 */       if ((targetObject instanceof XObject)) {
/* 202:355 */         targetObject = ((XObject)targetObject).object();
/* 203:    */       }
/* 204:356 */       Object[] methodArgs = new Object[args.size() - 1];
/* 205:357 */       Object[][] convertedArgs = new Object[1][];
/* 206:358 */       for (int i = 0; i < methodArgs.length; i++) {
/* 207:360 */         methodArgs[i] = args.get(i + 1);
/* 208:    */       }
/* 209:362 */       Method m = methodKey != null ? (Method)getFromCache(methodKey, targetObject, methodArgs) : null;
/* 210:365 */       if (m != null) {
/* 211:    */         try
/* 212:    */         {
/* 213:369 */           paramTypes = m.getParameterTypes();
/* 214:370 */           MethodResolver.convertParams(methodArgs, convertedArgs, paramTypes, exprContext);
/* 215:371 */           return m.invoke(targetObject, convertedArgs[0]);
/* 216:    */         }
/* 217:    */         catch (InvocationTargetException ite)
/* 218:    */         {
/* 219:375 */           throw ite;
/* 220:    */         }
/* 221:    */         catch (Exception e) {}
/* 222:    */       }
/* 223:382 */       Class classObj = targetObject.getClass();
/* 224:383 */       m = MethodResolver.getMethod(classObj, funcName, methodArgs, convertedArgs, exprContext, 2);
/* 225:389 */       if (methodKey != null) {
/* 226:390 */         putToCache(methodKey, targetObject, methodArgs, m);
/* 227:    */       }
/* 228:392 */       if ((trans != null) && (trans.getDebug()))
/* 229:    */       {
/* 230:393 */         trans.getTraceManager().fireExtensionEvent(m, targetObject, convertedArgs[0]);
/* 231:    */         Object result;
/* 232:    */         try
/* 233:    */         {
/* 234:396 */           result = m.invoke(targetObject, convertedArgs[0]);
/* 235:    */         }
/* 236:    */         catch (Exception e)
/* 237:    */         {
/* 238:398 */           throw e;
/* 239:    */         }
/* 240:    */         finally
/* 241:    */         {
/* 242:400 */           trans.getTraceManager().fireExtensionEndEvent(m, targetObject, convertedArgs[0]);
/* 243:    */         }
/* 244:402 */         return result;
/* 245:    */       }
/* 246:404 */       return m.invoke(targetObject, convertedArgs[0]);
/* 247:    */     }
/* 248:    */     catch (InvocationTargetException ite)
/* 249:    */     {
/* 250:409 */       Throwable resultException = ite;
/* 251:410 */       Throwable targetException = ite.getTargetException();
/* 252:412 */       if ((targetException instanceof TransformerException)) {
/* 253:413 */         throw ((TransformerException)targetException);
/* 254:    */       }
/* 255:414 */       if (targetException != null) {
/* 256:415 */         resultException = targetException;
/* 257:    */       }
/* 258:417 */       throw new TransformerException(resultException);
/* 259:    */     }
/* 260:    */     catch (Exception e)
/* 261:    */     {
/* 262:422 */       throw new TransformerException(e);
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   public Object callFunction(FuncExtFunction extFunction, Vector args, ExpressionContext exprContext)
/* 267:    */     throws TransformerException
/* 268:    */   {
/* 269:440 */     return callFunction(extFunction.getFunctionName(), args, extFunction.getMethodKey(), exprContext);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void processElement(String localPart, ElemTemplateElement element, TransformerImpl transformer, Stylesheet stylesheetTree, Object methodKey)
/* 273:    */     throws TransformerException, IOException
/* 274:    */   {
/* 275:465 */     Object result = null;
/* 276:    */     
/* 277:    */ 
/* 278:468 */     Method m = (Method)getFromCache(methodKey, null, null);
/* 279:469 */     if (null == m)
/* 280:    */     {
/* 281:    */       try
/* 282:    */       {
/* 283:473 */         String fullName = this.m_className + localPart;
/* 284:474 */         int lastDot = fullName.lastIndexOf(".");
/* 285:475 */         if (lastDot < 0) {
/* 286:476 */           throw new TransformerException(XSLMessages.createMessage("ER_INVALID_ELEMENT_NAME", new Object[] { fullName }));
/* 287:    */         }
/* 288:    */         Class classObj;
/* 289:    */         try
/* 290:    */         {
/* 291:479 */           classObj = ExtensionHandler.getClassForName(fullName.substring(0, lastDot));
/* 292:    */         }
/* 293:    */         catch (ClassNotFoundException e)
/* 294:    */         {
/* 295:483 */           throw new TransformerException(e);
/* 296:    */         }
/* 297:485 */         localPart = fullName.substring(lastDot + 1);
/* 298:486 */         m = MethodResolver.getElementMethod(classObj, localPart);
/* 299:487 */         if (!Modifier.isStatic(m.getModifiers())) {
/* 300:488 */           throw new TransformerException(XSLMessages.createMessage("ER_ELEMENT_NAME_METHOD_STATIC", new Object[] { fullName }));
/* 301:    */         }
/* 302:    */       }
/* 303:    */       catch (Exception e)
/* 304:    */       {
/* 305:493 */         throw new TransformerException(e);
/* 306:    */       }
/* 307:495 */       putToCache(methodKey, null, null, m);
/* 308:    */     }
/* 309:498 */     XSLProcessorContext xpc = new XSLProcessorContext(transformer, stylesheetTree);
/* 310:    */     try
/* 311:    */     {
/* 312:503 */       if (transformer.getDebug())
/* 313:    */       {
/* 314:504 */         transformer.getTraceManager().fireExtensionEvent(m, null, new Object[] { xpc, element });
/* 315:    */         try
/* 316:    */         {
/* 317:506 */           result = m.invoke(null, new Object[] { xpc, element });
/* 318:    */         }
/* 319:    */         catch (Exception e)
/* 320:    */         {
/* 321:508 */           throw e;
/* 322:    */         }
/* 323:    */         finally
/* 324:    */         {
/* 325:510 */           transformer.getTraceManager().fireExtensionEndEvent(m, null, new Object[] { xpc, element });
/* 326:    */         }
/* 327:    */       }
/* 328:    */       else
/* 329:    */       {
/* 330:513 */         result = m.invoke(null, new Object[] { xpc, element });
/* 331:    */       }
/* 332:    */     }
/* 333:    */     catch (InvocationTargetException ite)
/* 334:    */     {
/* 335:517 */       Throwable resultException = ite;
/* 336:518 */       Throwable targetException = ite.getTargetException();
/* 337:520 */       if ((targetException instanceof TransformerException)) {
/* 338:521 */         throw ((TransformerException)targetException);
/* 339:    */       }
/* 340:522 */       if (targetException != null) {
/* 341:523 */         resultException = targetException;
/* 342:    */       }
/* 343:525 */       throw new TransformerException(resultException);
/* 344:    */     }
/* 345:    */     catch (Exception e)
/* 346:    */     {
/* 347:530 */       throw new TransformerException(e);
/* 348:    */     }
/* 349:533 */     if (result != null) {
/* 350:535 */       xpc.outputToResultTree(stylesheetTree, result);
/* 351:    */     }
/* 352:    */   }
/* 353:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.ExtensionHandlerJavaPackage
 * JD-Core Version:    0.7.0.1
 */