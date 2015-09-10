/*   1:    */ package org.apache.http.protocol;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.ProtocolException;
/*   5:    */ import org.apache.http.HttpClientConnection;
/*   6:    */ import org.apache.http.HttpEntityEnclosingRequest;
/*   7:    */ import org.apache.http.HttpException;
/*   8:    */ import org.apache.http.HttpRequest;
/*   9:    */ import org.apache.http.HttpResponse;
/*  10:    */ import org.apache.http.HttpVersion;
/*  11:    */ import org.apache.http.ProtocolVersion;
/*  12:    */ import org.apache.http.RequestLine;
/*  13:    */ import org.apache.http.StatusLine;
/*  14:    */ import org.apache.http.params.HttpParams;
/*  15:    */ 
/*  16:    */ public class HttpRequestExecutor
/*  17:    */ {
/*  18:    */   protected boolean canResponseHaveBody(HttpRequest request, HttpResponse response)
/*  19:    */   {
/*  20: 85 */     if ("HEAD".equalsIgnoreCase(request.getRequestLine().getMethod())) {
/*  21: 86 */       return false;
/*  22:    */     }
/*  23: 88 */     int status = response.getStatusLine().getStatusCode();
/*  24: 89 */     return (status >= 200) && (status != 204) && (status != 304) && (status != 205);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public HttpResponse execute(HttpRequest request, HttpClientConnection conn, HttpContext context)
/*  28:    */     throws IOException, HttpException
/*  29:    */   {
/*  30:112 */     if (request == null) {
/*  31:113 */       throw new IllegalArgumentException("HTTP request may not be null");
/*  32:    */     }
/*  33:115 */     if (conn == null) {
/*  34:116 */       throw new IllegalArgumentException("Client connection may not be null");
/*  35:    */     }
/*  36:118 */     if (context == null) {
/*  37:119 */       throw new IllegalArgumentException("HTTP context may not be null");
/*  38:    */     }
/*  39:    */     try
/*  40:    */     {
/*  41:123 */       HttpResponse response = doSendRequest(request, conn, context);
/*  42:124 */       if (response == null) {}
/*  43:125 */       return doReceiveResponse(request, conn, context);
/*  44:    */     }
/*  45:    */     catch (IOException ex)
/*  46:    */     {
/*  47:129 */       closeConnection(conn);
/*  48:130 */       throw ex;
/*  49:    */     }
/*  50:    */     catch (HttpException ex)
/*  51:    */     {
/*  52:132 */       closeConnection(conn);
/*  53:133 */       throw ex;
/*  54:    */     }
/*  55:    */     catch (RuntimeException ex)
/*  56:    */     {
/*  57:135 */       closeConnection(conn);
/*  58:136 */       throw ex;
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   private static final void closeConnection(HttpClientConnection conn)
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66:142 */       conn.close();
/*  67:    */     }
/*  68:    */     catch (IOException ignore) {}
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void preProcess(HttpRequest request, HttpProcessor processor, HttpContext context)
/*  72:    */     throws HttpException, IOException
/*  73:    */   {
/*  74:164 */     if (request == null) {
/*  75:165 */       throw new IllegalArgumentException("HTTP request may not be null");
/*  76:    */     }
/*  77:167 */     if (processor == null) {
/*  78:168 */       throw new IllegalArgumentException("HTTP processor may not be null");
/*  79:    */     }
/*  80:170 */     if (context == null) {
/*  81:171 */       throw new IllegalArgumentException("HTTP context may not be null");
/*  82:    */     }
/*  83:173 */     context.setAttribute("http.request", request);
/*  84:174 */     processor.process(request, context);
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected HttpResponse doSendRequest(HttpRequest request, HttpClientConnection conn, HttpContext context)
/*  88:    */     throws IOException, HttpException
/*  89:    */   {
/*  90:204 */     if (request == null) {
/*  91:205 */       throw new IllegalArgumentException("HTTP request may not be null");
/*  92:    */     }
/*  93:207 */     if (conn == null) {
/*  94:208 */       throw new IllegalArgumentException("HTTP connection may not be null");
/*  95:    */     }
/*  96:210 */     if (context == null) {
/*  97:211 */       throw new IllegalArgumentException("HTTP context may not be null");
/*  98:    */     }
/*  99:214 */     HttpResponse response = null;
/* 100:    */     
/* 101:216 */     context.setAttribute("http.connection", conn);
/* 102:217 */     context.setAttribute("http.request_sent", Boolean.FALSE);
/* 103:    */     
/* 104:219 */     conn.sendRequestHeader(request);
/* 105:220 */     if ((request instanceof HttpEntityEnclosingRequest))
/* 106:    */     {
/* 107:224 */       boolean sendentity = true;
/* 108:225 */       ProtocolVersion ver = request.getRequestLine().getProtocolVersion();
/* 109:227 */       if ((((HttpEntityEnclosingRequest)request).expectContinue()) && (!ver.lessEquals(HttpVersion.HTTP_1_0)))
/* 110:    */       {
/* 111:230 */         conn.flush();
/* 112:    */         
/* 113:    */ 
/* 114:233 */         int tms = request.getParams().getIntParameter("http.protocol.wait-for-continue", 2000);
/* 115:236 */         if (conn.isResponseAvailable(tms))
/* 116:    */         {
/* 117:237 */           response = conn.receiveResponseHeader();
/* 118:238 */           if (canResponseHaveBody(request, response)) {
/* 119:239 */             conn.receiveResponseEntity(response);
/* 120:    */           }
/* 121:241 */           int status = response.getStatusLine().getStatusCode();
/* 122:242 */           if (status < 200)
/* 123:    */           {
/* 124:243 */             if (status != 100) {
/* 125:244 */               throw new ProtocolException("Unexpected response: " + response.getStatusLine());
/* 126:    */             }
/* 127:248 */             response = null;
/* 128:    */           }
/* 129:    */           else
/* 130:    */           {
/* 131:250 */             sendentity = false;
/* 132:    */           }
/* 133:    */         }
/* 134:    */       }
/* 135:254 */       if (sendentity) {
/* 136:255 */         conn.sendRequestEntity((HttpEntityEnclosingRequest)request);
/* 137:    */       }
/* 138:    */     }
/* 139:258 */     conn.flush();
/* 140:259 */     context.setAttribute("http.request_sent", Boolean.TRUE);
/* 141:260 */     return response;
/* 142:    */   }
/* 143:    */   
/* 144:    */   protected HttpResponse doReceiveResponse(HttpRequest request, HttpClientConnection conn, HttpContext context)
/* 145:    */     throws HttpException, IOException
/* 146:    */   {
/* 147:283 */     if (request == null) {
/* 148:284 */       throw new IllegalArgumentException("HTTP request may not be null");
/* 149:    */     }
/* 150:286 */     if (conn == null) {
/* 151:287 */       throw new IllegalArgumentException("HTTP connection may not be null");
/* 152:    */     }
/* 153:289 */     if (context == null) {
/* 154:290 */       throw new IllegalArgumentException("HTTP context may not be null");
/* 155:    */     }
/* 156:293 */     HttpResponse response = null;
/* 157:294 */     int statuscode = 0;
/* 158:296 */     while ((response == null) || (statuscode < 200))
/* 159:    */     {
/* 160:298 */       response = conn.receiveResponseHeader();
/* 161:299 */       if (canResponseHaveBody(request, response)) {
/* 162:300 */         conn.receiveResponseEntity(response);
/* 163:    */       }
/* 164:302 */       statuscode = response.getStatusLine().getStatusCode();
/* 165:    */     }
/* 166:306 */     return response;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void postProcess(HttpResponse response, HttpProcessor processor, HttpContext context)
/* 170:    */     throws HttpException, IOException
/* 171:    */   {
/* 172:332 */     if (response == null) {
/* 173:333 */       throw new IllegalArgumentException("HTTP response may not be null");
/* 174:    */     }
/* 175:335 */     if (processor == null) {
/* 176:336 */       throw new IllegalArgumentException("HTTP processor may not be null");
/* 177:    */     }
/* 178:338 */     if (context == null) {
/* 179:339 */       throw new IllegalArgumentException("HTTP context may not be null");
/* 180:    */     }
/* 181:341 */     context.setAttribute("http.response", response);
/* 182:342 */     processor.process(response, context);
/* 183:    */   }
/* 184:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.HttpRequestExecutor
 * JD-Core Version:    0.7.0.1
 */