/*   1:    */ package org.apache.http.protocol;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.http.ConnectionReuseStrategy;
/*   5:    */ import org.apache.http.HttpEntity;
/*   6:    */ import org.apache.http.HttpEntityEnclosingRequest;
/*   7:    */ import org.apache.http.HttpException;
/*   8:    */ import org.apache.http.HttpRequest;
/*   9:    */ import org.apache.http.HttpResponse;
/*  10:    */ import org.apache.http.HttpResponseFactory;
/*  11:    */ import org.apache.http.HttpServerConnection;
/*  12:    */ import org.apache.http.HttpVersion;
/*  13:    */ import org.apache.http.MethodNotSupportedException;
/*  14:    */ import org.apache.http.ProtocolException;
/*  15:    */ import org.apache.http.ProtocolVersion;
/*  16:    */ import org.apache.http.RequestLine;
/*  17:    */ import org.apache.http.StatusLine;
/*  18:    */ import org.apache.http.UnsupportedHttpVersionException;
/*  19:    */ import org.apache.http.entity.ByteArrayEntity;
/*  20:    */ import org.apache.http.params.DefaultedHttpParams;
/*  21:    */ import org.apache.http.params.HttpParams;
/*  22:    */ import org.apache.http.util.EncodingUtils;
/*  23:    */ import org.apache.http.util.EntityUtils;
/*  24:    */ 
/*  25:    */ public class HttpService
/*  26:    */ {
/*  27: 76 */   private volatile HttpParams params = null;
/*  28: 77 */   private volatile HttpProcessor processor = null;
/*  29: 78 */   private volatile HttpRequestHandlerResolver handlerResolver = null;
/*  30: 79 */   private volatile ConnectionReuseStrategy connStrategy = null;
/*  31: 80 */   private volatile HttpResponseFactory responseFactory = null;
/*  32: 81 */   private volatile HttpExpectationVerifier expectationVerifier = null;
/*  33:    */   
/*  34:    */   public HttpService(HttpProcessor processor, ConnectionReuseStrategy connStrategy, HttpResponseFactory responseFactory, HttpRequestHandlerResolver handlerResolver, HttpExpectationVerifier expectationVerifier, HttpParams params)
/*  35:    */   {
/*  36:103 */     if (processor == null) {
/*  37:104 */       throw new IllegalArgumentException("HTTP processor may not be null");
/*  38:    */     }
/*  39:106 */     if (connStrategy == null) {
/*  40:107 */       throw new IllegalArgumentException("Connection reuse strategy may not be null");
/*  41:    */     }
/*  42:109 */     if (responseFactory == null) {
/*  43:110 */       throw new IllegalArgumentException("Response factory may not be null");
/*  44:    */     }
/*  45:112 */     if (params == null) {
/*  46:113 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  47:    */     }
/*  48:115 */     this.processor = processor;
/*  49:116 */     this.connStrategy = connStrategy;
/*  50:117 */     this.responseFactory = responseFactory;
/*  51:118 */     this.handlerResolver = handlerResolver;
/*  52:119 */     this.expectationVerifier = expectationVerifier;
/*  53:120 */     this.params = params;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public HttpService(HttpProcessor processor, ConnectionReuseStrategy connStrategy, HttpResponseFactory responseFactory, HttpRequestHandlerResolver handlerResolver, HttpParams params)
/*  57:    */   {
/*  58:140 */     this(processor, connStrategy, responseFactory, handlerResolver, null, params);
/*  59:    */   }
/*  60:    */   
/*  61:    */   /**
/*  62:    */    * @deprecated
/*  63:    */    */
/*  64:    */   public HttpService(HttpProcessor proc, ConnectionReuseStrategy connStrategy, HttpResponseFactory responseFactory)
/*  65:    */   {
/*  66:158 */     setHttpProcessor(proc);
/*  67:159 */     setConnReuseStrategy(connStrategy);
/*  68:160 */     setResponseFactory(responseFactory);
/*  69:    */   }
/*  70:    */   
/*  71:    */   /**
/*  72:    */    * @deprecated
/*  73:    */    */
/*  74:    */   public void setHttpProcessor(HttpProcessor processor)
/*  75:    */   {
/*  76:167 */     if (processor == null) {
/*  77:168 */       throw new IllegalArgumentException("HTTP processor may not be null");
/*  78:    */     }
/*  79:170 */     this.processor = processor;
/*  80:    */   }
/*  81:    */   
/*  82:    */   /**
/*  83:    */    * @deprecated
/*  84:    */    */
/*  85:    */   public void setConnReuseStrategy(ConnectionReuseStrategy connStrategy)
/*  86:    */   {
/*  87:177 */     if (connStrategy == null) {
/*  88:178 */       throw new IllegalArgumentException("Connection reuse strategy may not be null");
/*  89:    */     }
/*  90:180 */     this.connStrategy = connStrategy;
/*  91:    */   }
/*  92:    */   
/*  93:    */   /**
/*  94:    */    * @deprecated
/*  95:    */    */
/*  96:    */   public void setResponseFactory(HttpResponseFactory responseFactory)
/*  97:    */   {
/*  98:187 */     if (responseFactory == null) {
/*  99:188 */       throw new IllegalArgumentException("Response factory may not be null");
/* 100:    */     }
/* 101:190 */     this.responseFactory = responseFactory;
/* 102:    */   }
/* 103:    */   
/* 104:    */   /**
/* 105:    */    * @deprecated
/* 106:    */    */
/* 107:    */   public void setParams(HttpParams params)
/* 108:    */   {
/* 109:197 */     this.params = params;
/* 110:    */   }
/* 111:    */   
/* 112:    */   /**
/* 113:    */    * @deprecated
/* 114:    */    */
/* 115:    */   public void setHandlerResolver(HttpRequestHandlerResolver handlerResolver)
/* 116:    */   {
/* 117:204 */     this.handlerResolver = handlerResolver;
/* 118:    */   }
/* 119:    */   
/* 120:    */   /**
/* 121:    */    * @deprecated
/* 122:    */    */
/* 123:    */   public void setExpectationVerifier(HttpExpectationVerifier expectationVerifier)
/* 124:    */   {
/* 125:211 */     this.expectationVerifier = expectationVerifier;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public HttpParams getParams()
/* 129:    */   {
/* 130:215 */     return this.params;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void handleRequest(HttpServerConnection conn, HttpContext context)
/* 134:    */     throws IOException, HttpException
/* 135:    */   {
/* 136:232 */     context.setAttribute("http.connection", conn);
/* 137:    */     
/* 138:234 */     HttpResponse response = null;
/* 139:    */     try
/* 140:    */     {
/* 141:238 */       HttpRequest request = conn.receiveRequestHeader();
/* 142:239 */       request.setParams(new DefaultedHttpParams(request.getParams(), this.params));
/* 143:    */       
/* 144:    */ 
/* 145:242 */       ProtocolVersion ver = request.getRequestLine().getProtocolVersion();
/* 146:244 */       if (!ver.lessEquals(HttpVersion.HTTP_1_1)) {
/* 147:246 */         ver = HttpVersion.HTTP_1_1;
/* 148:    */       }
/* 149:249 */       if ((request instanceof HttpEntityEnclosingRequest)) {
/* 150:251 */         if (((HttpEntityEnclosingRequest)request).expectContinue())
/* 151:    */         {
/* 152:252 */           response = this.responseFactory.newHttpResponse(ver, 100, context);
/* 153:    */           
/* 154:254 */           response.setParams(new DefaultedHttpParams(response.getParams(), this.params));
/* 155:257 */           if (this.expectationVerifier != null) {
/* 156:    */             try
/* 157:    */             {
/* 158:259 */               this.expectationVerifier.verify(request, response, context);
/* 159:    */             }
/* 160:    */             catch (HttpException ex)
/* 161:    */             {
/* 162:261 */               response = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, context);
/* 163:    */               
/* 164:263 */               response.setParams(new DefaultedHttpParams(response.getParams(), this.params));
/* 165:    */               
/* 166:265 */               handleException(ex, response);
/* 167:    */             }
/* 168:    */           }
/* 169:268 */           if (response.getStatusLine().getStatusCode() < 200)
/* 170:    */           {
/* 171:271 */             conn.sendResponseHeader(response);
/* 172:272 */             conn.flush();
/* 173:273 */             response = null;
/* 174:274 */             conn.receiveRequestEntity((HttpEntityEnclosingRequest)request);
/* 175:    */           }
/* 176:    */         }
/* 177:    */         else
/* 178:    */         {
/* 179:277 */           conn.receiveRequestEntity((HttpEntityEnclosingRequest)request);
/* 180:    */         }
/* 181:    */       }
/* 182:281 */       if (response == null)
/* 183:    */       {
/* 184:282 */         response = this.responseFactory.newHttpResponse(ver, 200, context);
/* 185:283 */         response.setParams(new DefaultedHttpParams(response.getParams(), this.params));
/* 186:    */         
/* 187:    */ 
/* 188:286 */         context.setAttribute("http.request", request);
/* 189:287 */         context.setAttribute("http.response", response);
/* 190:    */         
/* 191:289 */         this.processor.process(request, context);
/* 192:290 */         doService(request, response, context);
/* 193:    */       }
/* 194:294 */       if ((request instanceof HttpEntityEnclosingRequest))
/* 195:    */       {
/* 196:295 */         HttpEntity entity = ((HttpEntityEnclosingRequest)request).getEntity();
/* 197:296 */         EntityUtils.consume(entity);
/* 198:    */       }
/* 199:    */     }
/* 200:    */     catch (HttpException ex)
/* 201:    */     {
/* 202:300 */       response = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, context);
/* 203:    */       
/* 204:    */ 
/* 205:303 */       response.setParams(new DefaultedHttpParams(response.getParams(), this.params));
/* 206:    */       
/* 207:305 */       handleException(ex, response);
/* 208:    */     }
/* 209:308 */     this.processor.process(response, context);
/* 210:309 */     conn.sendResponseHeader(response);
/* 211:310 */     conn.sendResponseEntity(response);
/* 212:311 */     conn.flush();
/* 213:313 */     if (!this.connStrategy.keepAlive(response, context)) {
/* 214:314 */       conn.close();
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   protected void handleException(HttpException ex, HttpResponse response)
/* 219:    */   {
/* 220:327 */     if ((ex instanceof MethodNotSupportedException)) {
/* 221:328 */       response.setStatusCode(501);
/* 222:329 */     } else if ((ex instanceof UnsupportedHttpVersionException)) {
/* 223:330 */       response.setStatusCode(505);
/* 224:331 */     } else if ((ex instanceof ProtocolException)) {
/* 225:332 */       response.setStatusCode(400);
/* 226:    */     } else {
/* 227:334 */       response.setStatusCode(500);
/* 228:    */     }
/* 229:336 */     byte[] msg = EncodingUtils.getAsciiBytes(ex.getMessage());
/* 230:337 */     ByteArrayEntity entity = new ByteArrayEntity(msg);
/* 231:338 */     entity.setContentType("text/plain; charset=US-ASCII");
/* 232:339 */     response.setEntity(entity);
/* 233:    */   }
/* 234:    */   
/* 235:    */   protected void doService(HttpRequest request, HttpResponse response, HttpContext context)
/* 236:    */     throws HttpException, IOException
/* 237:    */   {
/* 238:363 */     HttpRequestHandler handler = null;
/* 239:364 */     if (this.handlerResolver != null)
/* 240:    */     {
/* 241:365 */       String requestURI = request.getRequestLine().getUri();
/* 242:366 */       handler = this.handlerResolver.lookup(requestURI);
/* 243:    */     }
/* 244:368 */     if (handler != null) {
/* 245:369 */       handler.handle(request, response, context);
/* 246:    */     } else {
/* 247:371 */       response.setStatusCode(501);
/* 248:    */     }
/* 249:    */   }
/* 250:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.HttpService
 * JD-Core Version:    0.7.0.1
 */