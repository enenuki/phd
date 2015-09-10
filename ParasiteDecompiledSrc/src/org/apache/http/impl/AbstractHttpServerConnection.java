/*   1:    */ package org.apache.http.impl;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.http.HttpConnectionMetrics;
/*   5:    */ import org.apache.http.HttpEntity;
/*   6:    */ import org.apache.http.HttpEntityEnclosingRequest;
/*   7:    */ import org.apache.http.HttpException;
/*   8:    */ import org.apache.http.HttpRequest;
/*   9:    */ import org.apache.http.HttpRequestFactory;
/*  10:    */ import org.apache.http.HttpResponse;
/*  11:    */ import org.apache.http.HttpServerConnection;
/*  12:    */ import org.apache.http.StatusLine;
/*  13:    */ import org.apache.http.impl.entity.EntityDeserializer;
/*  14:    */ import org.apache.http.impl.entity.EntitySerializer;
/*  15:    */ import org.apache.http.impl.entity.LaxContentLengthStrategy;
/*  16:    */ import org.apache.http.impl.entity.StrictContentLengthStrategy;
/*  17:    */ import org.apache.http.impl.io.HttpRequestParser;
/*  18:    */ import org.apache.http.impl.io.HttpResponseWriter;
/*  19:    */ import org.apache.http.io.EofSensor;
/*  20:    */ import org.apache.http.io.HttpMessageParser;
/*  21:    */ import org.apache.http.io.HttpMessageWriter;
/*  22:    */ import org.apache.http.io.HttpTransportMetrics;
/*  23:    */ import org.apache.http.io.SessionInputBuffer;
/*  24:    */ import org.apache.http.io.SessionOutputBuffer;
/*  25:    */ import org.apache.http.params.HttpParams;
/*  26:    */ 
/*  27:    */ public abstract class AbstractHttpServerConnection
/*  28:    */   implements HttpServerConnection
/*  29:    */ {
/*  30:    */   private final EntitySerializer entityserializer;
/*  31:    */   private final EntityDeserializer entitydeserializer;
/*  32: 76 */   private SessionInputBuffer inbuffer = null;
/*  33: 77 */   private SessionOutputBuffer outbuffer = null;
/*  34: 78 */   private EofSensor eofSensor = null;
/*  35: 79 */   private HttpMessageParser requestParser = null;
/*  36: 80 */   private HttpMessageWriter responseWriter = null;
/*  37: 81 */   private HttpConnectionMetricsImpl metrics = null;
/*  38:    */   
/*  39:    */   public AbstractHttpServerConnection()
/*  40:    */   {
/*  41: 93 */     this.entityserializer = createEntitySerializer();
/*  42: 94 */     this.entitydeserializer = createEntityDeserializer();
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected abstract void assertOpen()
/*  46:    */     throws IllegalStateException;
/*  47:    */   
/*  48:    */   protected EntityDeserializer createEntityDeserializer()
/*  49:    */   {
/*  50:116 */     return new EntityDeserializer(new LaxContentLengthStrategy());
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected EntitySerializer createEntitySerializer()
/*  54:    */   {
/*  55:131 */     return new EntitySerializer(new StrictContentLengthStrategy());
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected HttpRequestFactory createHttpRequestFactory()
/*  59:    */   {
/*  60:145 */     return new DefaultHttpRequestFactory();
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected HttpMessageParser createRequestParser(SessionInputBuffer buffer, HttpRequestFactory requestFactory, HttpParams params)
/*  64:    */   {
/*  65:166 */     return new HttpRequestParser(buffer, null, requestFactory, params);
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected HttpMessageWriter createResponseWriter(SessionOutputBuffer buffer, HttpParams params)
/*  69:    */   {
/*  70:185 */     return new HttpResponseWriter(buffer, null, params);
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected HttpConnectionMetricsImpl createConnectionMetrics(HttpTransportMetrics inTransportMetric, HttpTransportMetrics outTransportMetric)
/*  74:    */   {
/*  75:194 */     return new HttpConnectionMetricsImpl(inTransportMetric, outTransportMetric);
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void init(SessionInputBuffer inbuffer, SessionOutputBuffer outbuffer, HttpParams params)
/*  79:    */   {
/*  80:217 */     if (inbuffer == null) {
/*  81:218 */       throw new IllegalArgumentException("Input session buffer may not be null");
/*  82:    */     }
/*  83:220 */     if (outbuffer == null) {
/*  84:221 */       throw new IllegalArgumentException("Output session buffer may not be null");
/*  85:    */     }
/*  86:223 */     this.inbuffer = inbuffer;
/*  87:224 */     this.outbuffer = outbuffer;
/*  88:225 */     if ((inbuffer instanceof EofSensor)) {
/*  89:226 */       this.eofSensor = ((EofSensor)inbuffer);
/*  90:    */     }
/*  91:228 */     this.requestParser = createRequestParser(inbuffer, createHttpRequestFactory(), params);
/*  92:    */     
/*  93:    */ 
/*  94:    */ 
/*  95:232 */     this.responseWriter = createResponseWriter(outbuffer, params);
/*  96:    */     
/*  97:234 */     this.metrics = createConnectionMetrics(inbuffer.getMetrics(), outbuffer.getMetrics());
/*  98:    */   }
/*  99:    */   
/* 100:    */   public HttpRequest receiveRequestHeader()
/* 101:    */     throws HttpException, IOException
/* 102:    */   {
/* 103:241 */     assertOpen();
/* 104:242 */     HttpRequest request = (HttpRequest)this.requestParser.parse();
/* 105:243 */     this.metrics.incrementRequestCount();
/* 106:244 */     return request;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void receiveRequestEntity(HttpEntityEnclosingRequest request)
/* 110:    */     throws HttpException, IOException
/* 111:    */   {
/* 112:249 */     if (request == null) {
/* 113:250 */       throw new IllegalArgumentException("HTTP request may not be null");
/* 114:    */     }
/* 115:252 */     assertOpen();
/* 116:253 */     HttpEntity entity = this.entitydeserializer.deserialize(this.inbuffer, request);
/* 117:254 */     request.setEntity(entity);
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected void doFlush()
/* 121:    */     throws IOException
/* 122:    */   {
/* 123:258 */     this.outbuffer.flush();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void flush()
/* 127:    */     throws IOException
/* 128:    */   {
/* 129:262 */     assertOpen();
/* 130:263 */     doFlush();
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void sendResponseHeader(HttpResponse response)
/* 134:    */     throws HttpException, IOException
/* 135:    */   {
/* 136:268 */     if (response == null) {
/* 137:269 */       throw new IllegalArgumentException("HTTP response may not be null");
/* 138:    */     }
/* 139:271 */     assertOpen();
/* 140:272 */     this.responseWriter.write(response);
/* 141:273 */     if (response.getStatusLine().getStatusCode() >= 200) {
/* 142:274 */       this.metrics.incrementResponseCount();
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void sendResponseEntity(HttpResponse response)
/* 147:    */     throws HttpException, IOException
/* 148:    */   {
/* 149:280 */     if (response.getEntity() == null) {
/* 150:281 */       return;
/* 151:    */     }
/* 152:283 */     this.entityserializer.serialize(this.outbuffer, response, response.getEntity());
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected boolean isEof()
/* 156:    */   {
/* 157:290 */     return (this.eofSensor != null) && (this.eofSensor.isEof());
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean isStale()
/* 161:    */   {
/* 162:294 */     if (!isOpen()) {
/* 163:295 */       return true;
/* 164:    */     }
/* 165:297 */     if (isEof()) {
/* 166:298 */       return true;
/* 167:    */     }
/* 168:    */     try
/* 169:    */     {
/* 170:301 */       this.inbuffer.isDataAvailable(1);
/* 171:302 */       return isEof();
/* 172:    */     }
/* 173:    */     catch (IOException ex) {}
/* 174:304 */     return true;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public HttpConnectionMetrics getMetrics()
/* 178:    */   {
/* 179:309 */     return this.metrics;
/* 180:    */   }
/* 181:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.AbstractHttpServerConnection
 * JD-Core Version:    0.7.0.1
 */