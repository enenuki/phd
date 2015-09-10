/*   1:    */ package org.apache.http.impl;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.http.HttpClientConnection;
/*   5:    */ import org.apache.http.HttpConnectionMetrics;
/*   6:    */ import org.apache.http.HttpEntity;
/*   7:    */ import org.apache.http.HttpEntityEnclosingRequest;
/*   8:    */ import org.apache.http.HttpException;
/*   9:    */ import org.apache.http.HttpRequest;
/*  10:    */ import org.apache.http.HttpResponse;
/*  11:    */ import org.apache.http.HttpResponseFactory;
/*  12:    */ import org.apache.http.StatusLine;
/*  13:    */ import org.apache.http.impl.entity.EntityDeserializer;
/*  14:    */ import org.apache.http.impl.entity.EntitySerializer;
/*  15:    */ import org.apache.http.impl.entity.LaxContentLengthStrategy;
/*  16:    */ import org.apache.http.impl.entity.StrictContentLengthStrategy;
/*  17:    */ import org.apache.http.impl.io.HttpRequestWriter;
/*  18:    */ import org.apache.http.impl.io.HttpResponseParser;
/*  19:    */ import org.apache.http.io.EofSensor;
/*  20:    */ import org.apache.http.io.HttpMessageParser;
/*  21:    */ import org.apache.http.io.HttpMessageWriter;
/*  22:    */ import org.apache.http.io.HttpTransportMetrics;
/*  23:    */ import org.apache.http.io.SessionInputBuffer;
/*  24:    */ import org.apache.http.io.SessionOutputBuffer;
/*  25:    */ import org.apache.http.params.HttpParams;
/*  26:    */ 
/*  27:    */ public abstract class AbstractHttpClientConnection
/*  28:    */   implements HttpClientConnection
/*  29:    */ {
/*  30:    */   private final EntitySerializer entityserializer;
/*  31:    */   private final EntityDeserializer entitydeserializer;
/*  32: 76 */   private SessionInputBuffer inbuffer = null;
/*  33: 77 */   private SessionOutputBuffer outbuffer = null;
/*  34: 78 */   private EofSensor eofSensor = null;
/*  35: 79 */   private HttpMessageParser responseParser = null;
/*  36: 80 */   private HttpMessageWriter requestWriter = null;
/*  37: 81 */   private HttpConnectionMetricsImpl metrics = null;
/*  38:    */   
/*  39:    */   public AbstractHttpClientConnection()
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
/*  58:    */   protected HttpResponseFactory createHttpResponseFactory()
/*  59:    */   {
/*  60:145 */     return new DefaultHttpResponseFactory();
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected HttpMessageParser createResponseParser(SessionInputBuffer buffer, HttpResponseFactory responseFactory, HttpParams params)
/*  64:    */   {
/*  65:166 */     return new HttpResponseParser(buffer, null, responseFactory, params);
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected HttpMessageWriter createRequestWriter(SessionOutputBuffer buffer, HttpParams params)
/*  69:    */   {
/*  70:185 */     return new HttpRequestWriter(buffer, null, params);
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
/*  91:228 */     this.responseParser = createResponseParser(inbuffer, createHttpResponseFactory(), params);
/*  92:    */     
/*  93:    */ 
/*  94:    */ 
/*  95:232 */     this.requestWriter = createRequestWriter(outbuffer, params);
/*  96:    */     
/*  97:234 */     this.metrics = createConnectionMetrics(inbuffer.getMetrics(), outbuffer.getMetrics());
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean isResponseAvailable(int timeout)
/* 101:    */     throws IOException
/* 102:    */   {
/* 103:240 */     assertOpen();
/* 104:241 */     return this.inbuffer.isDataAvailable(timeout);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void sendRequestHeader(HttpRequest request)
/* 108:    */     throws HttpException, IOException
/* 109:    */   {
/* 110:246 */     if (request == null) {
/* 111:247 */       throw new IllegalArgumentException("HTTP request may not be null");
/* 112:    */     }
/* 113:249 */     assertOpen();
/* 114:250 */     this.requestWriter.write(request);
/* 115:251 */     this.metrics.incrementRequestCount();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void sendRequestEntity(HttpEntityEnclosingRequest request)
/* 119:    */     throws HttpException, IOException
/* 120:    */   {
/* 121:256 */     if (request == null) {
/* 122:257 */       throw new IllegalArgumentException("HTTP request may not be null");
/* 123:    */     }
/* 124:259 */     assertOpen();
/* 125:260 */     if (request.getEntity() == null) {
/* 126:261 */       return;
/* 127:    */     }
/* 128:263 */     this.entityserializer.serialize(this.outbuffer, request, request.getEntity());
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected void doFlush()
/* 132:    */     throws IOException
/* 133:    */   {
/* 134:270 */     this.outbuffer.flush();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void flush()
/* 138:    */     throws IOException
/* 139:    */   {
/* 140:274 */     assertOpen();
/* 141:275 */     doFlush();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public HttpResponse receiveResponseHeader()
/* 145:    */     throws HttpException, IOException
/* 146:    */   {
/* 147:280 */     assertOpen();
/* 148:281 */     HttpResponse response = (HttpResponse)this.responseParser.parse();
/* 149:282 */     if (response.getStatusLine().getStatusCode() >= 200) {
/* 150:283 */       this.metrics.incrementResponseCount();
/* 151:    */     }
/* 152:285 */     return response;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void receiveResponseEntity(HttpResponse response)
/* 156:    */     throws HttpException, IOException
/* 157:    */   {
/* 158:290 */     if (response == null) {
/* 159:291 */       throw new IllegalArgumentException("HTTP response may not be null");
/* 160:    */     }
/* 161:293 */     assertOpen();
/* 162:294 */     HttpEntity entity = this.entitydeserializer.deserialize(this.inbuffer, response);
/* 163:295 */     response.setEntity(entity);
/* 164:    */   }
/* 165:    */   
/* 166:    */   protected boolean isEof()
/* 167:    */   {
/* 168:299 */     return (this.eofSensor != null) && (this.eofSensor.isEof());
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean isStale()
/* 172:    */   {
/* 173:303 */     if (!isOpen()) {
/* 174:304 */       return true;
/* 175:    */     }
/* 176:306 */     if (isEof()) {
/* 177:307 */       return true;
/* 178:    */     }
/* 179:    */     try
/* 180:    */     {
/* 181:310 */       this.inbuffer.isDataAvailable(1);
/* 182:311 */       return isEof();
/* 183:    */     }
/* 184:    */     catch (IOException ex) {}
/* 185:313 */     return true;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public HttpConnectionMetrics getMetrics()
/* 189:    */   {
/* 190:318 */     return this.metrics;
/* 191:    */   }
/* 192:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.AbstractHttpClientConnection
 * JD-Core Version:    0.7.0.1
 */