/*   1:    */ package org.apache.http.impl;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import org.apache.http.HttpConnectionMetrics;
/*   5:    */ import org.apache.http.io.HttpTransportMetrics;
/*   6:    */ 
/*   7:    */ public class HttpConnectionMetricsImpl
/*   8:    */   implements HttpConnectionMetrics
/*   9:    */ {
/*  10:    */   public static final String REQUEST_COUNT = "http.request-count";
/*  11:    */   public static final String RESPONSE_COUNT = "http.response-count";
/*  12:    */   public static final String SENT_BYTES_COUNT = "http.sent-bytes-count";
/*  13:    */   public static final String RECEIVED_BYTES_COUNT = "http.received-bytes-count";
/*  14:    */   private final HttpTransportMetrics inTransportMetric;
/*  15:    */   private final HttpTransportMetrics outTransportMetric;
/*  16: 48 */   private long requestCount = 0L;
/*  17: 49 */   private long responseCount = 0L;
/*  18:    */   private HashMap metricsCache;
/*  19:    */   
/*  20:    */   public HttpConnectionMetricsImpl(HttpTransportMetrics inTransportMetric, HttpTransportMetrics outTransportMetric)
/*  21:    */   {
/*  22: 60 */     this.inTransportMetric = inTransportMetric;
/*  23: 61 */     this.outTransportMetric = outTransportMetric;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public long getReceivedBytesCount()
/*  27:    */   {
/*  28: 67 */     if (this.inTransportMetric != null) {
/*  29: 68 */       return this.inTransportMetric.getBytesTransferred();
/*  30:    */     }
/*  31: 70 */     return -1L;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public long getSentBytesCount()
/*  35:    */   {
/*  36: 75 */     if (this.outTransportMetric != null) {
/*  37: 76 */       return this.outTransportMetric.getBytesTransferred();
/*  38:    */     }
/*  39: 78 */     return -1L;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public long getRequestCount()
/*  43:    */   {
/*  44: 83 */     return this.requestCount;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void incrementRequestCount()
/*  48:    */   {
/*  49: 87 */     this.requestCount += 1L;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public long getResponseCount()
/*  53:    */   {
/*  54: 91 */     return this.responseCount;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void incrementResponseCount()
/*  58:    */   {
/*  59: 95 */     this.responseCount += 1L;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Object getMetric(String metricName)
/*  63:    */   {
/*  64: 99 */     Object value = null;
/*  65:100 */     if (this.metricsCache != null) {
/*  66:101 */       value = this.metricsCache.get(metricName);
/*  67:    */     }
/*  68:103 */     if (value == null) {
/*  69:104 */       if ("http.request-count".equals(metricName))
/*  70:    */       {
/*  71:105 */         value = new Long(this.requestCount);
/*  72:    */       }
/*  73:106 */       else if ("http.response-count".equals(metricName))
/*  74:    */       {
/*  75:107 */         value = new Long(this.responseCount);
/*  76:    */       }
/*  77:    */       else
/*  78:    */       {
/*  79:108 */         if ("http.received-bytes-count".equals(metricName))
/*  80:    */         {
/*  81:109 */           if (this.inTransportMetric != null) {
/*  82:110 */             return new Long(this.inTransportMetric.getBytesTransferred());
/*  83:    */           }
/*  84:112 */           return null;
/*  85:    */         }
/*  86:114 */         if ("http.sent-bytes-count".equals(metricName))
/*  87:    */         {
/*  88:115 */           if (this.outTransportMetric != null) {
/*  89:116 */             return new Long(this.outTransportMetric.getBytesTransferred());
/*  90:    */           }
/*  91:118 */           return null;
/*  92:    */         }
/*  93:    */       }
/*  94:    */     }
/*  95:122 */     return value;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setMetric(String metricName, Object obj)
/*  99:    */   {
/* 100:126 */     if (this.metricsCache == null) {
/* 101:127 */       this.metricsCache = new HashMap();
/* 102:    */     }
/* 103:129 */     this.metricsCache.put(metricName, obj);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void reset()
/* 107:    */   {
/* 108:133 */     if (this.outTransportMetric != null) {
/* 109:134 */       this.outTransportMetric.reset();
/* 110:    */     }
/* 111:136 */     if (this.inTransportMetric != null) {
/* 112:137 */       this.inTransportMetric.reset();
/* 113:    */     }
/* 114:139 */     this.requestCount = 0L;
/* 115:140 */     this.responseCount = 0L;
/* 116:141 */     this.metricsCache = null;
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.HttpConnectionMetricsImpl
 * JD-Core Version:    0.7.0.1
 */