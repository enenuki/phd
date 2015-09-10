/*   1:    */ package org.apache.http.client.methods;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.URI;
/*   5:    */ import java.util.concurrent.locks.Lock;
/*   6:    */ import java.util.concurrent.locks.ReentrantLock;
/*   7:    */ import org.apache.http.ProtocolVersion;
/*   8:    */ import org.apache.http.RequestLine;
/*   9:    */ import org.apache.http.annotation.NotThreadSafe;
/*  10:    */ import org.apache.http.client.utils.CloneUtils;
/*  11:    */ import org.apache.http.conn.ClientConnectionRequest;
/*  12:    */ import org.apache.http.conn.ConnectionReleaseTrigger;
/*  13:    */ import org.apache.http.message.AbstractHttpMessage;
/*  14:    */ import org.apache.http.message.BasicRequestLine;
/*  15:    */ import org.apache.http.message.HeaderGroup;
/*  16:    */ import org.apache.http.params.HttpParams;
/*  17:    */ import org.apache.http.params.HttpProtocolParams;
/*  18:    */ 
/*  19:    */ @NotThreadSafe
/*  20:    */ public abstract class HttpRequestBase
/*  21:    */   extends AbstractHttpMessage
/*  22:    */   implements HttpUriRequest, AbortableHttpRequest, Cloneable
/*  23:    */ {
/*  24:    */   private Lock abortLock;
/*  25:    */   private boolean aborted;
/*  26:    */   private URI uri;
/*  27:    */   private ClientConnectionRequest connRequest;
/*  28:    */   private ConnectionReleaseTrigger releaseTrigger;
/*  29:    */   
/*  30:    */   public HttpRequestBase()
/*  31:    */   {
/*  32: 68 */     this.abortLock = new ReentrantLock();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public abstract String getMethod();
/*  36:    */   
/*  37:    */   public ProtocolVersion getProtocolVersion()
/*  38:    */   {
/*  39: 74 */     return HttpProtocolParams.getVersion(getParams());
/*  40:    */   }
/*  41:    */   
/*  42:    */   public URI getURI()
/*  43:    */   {
/*  44: 84 */     return this.uri;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public RequestLine getRequestLine()
/*  48:    */   {
/*  49: 88 */     String method = getMethod();
/*  50: 89 */     ProtocolVersion ver = getProtocolVersion();
/*  51: 90 */     URI uri = getURI();
/*  52: 91 */     String uritext = null;
/*  53: 92 */     if (uri != null) {
/*  54: 93 */       uritext = uri.toASCIIString();
/*  55:    */     }
/*  56: 95 */     if ((uritext == null) || (uritext.length() == 0)) {
/*  57: 96 */       uritext = "/";
/*  58:    */     }
/*  59: 98 */     return new BasicRequestLine(method, uritext, ver);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setURI(URI uri)
/*  63:    */   {
/*  64:102 */     this.uri = uri;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setConnectionRequest(ClientConnectionRequest connRequest)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:107 */     this.abortLock.lock();
/*  71:    */     try
/*  72:    */     {
/*  73:109 */       if (this.aborted) {
/*  74:110 */         throw new IOException("Request already aborted");
/*  75:    */       }
/*  76:113 */       this.releaseTrigger = null;
/*  77:114 */       this.connRequest = connRequest;
/*  78:    */     }
/*  79:    */     finally
/*  80:    */     {
/*  81:116 */       this.abortLock.unlock();
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setReleaseTrigger(ConnectionReleaseTrigger releaseTrigger)
/*  86:    */     throws IOException
/*  87:    */   {
/*  88:122 */     this.abortLock.lock();
/*  89:    */     try
/*  90:    */     {
/*  91:124 */       if (this.aborted) {
/*  92:125 */         throw new IOException("Request already aborted");
/*  93:    */       }
/*  94:128 */       this.connRequest = null;
/*  95:129 */       this.releaseTrigger = releaseTrigger;
/*  96:    */     }
/*  97:    */     finally
/*  98:    */     {
/*  99:131 */       this.abortLock.unlock();
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void abort()
/* 104:    */   {
/* 105:139 */     this.abortLock.lock();
/* 106:    */     ClientConnectionRequest localRequest;
/* 107:    */     ConnectionReleaseTrigger localTrigger;
/* 108:    */     try
/* 109:    */     {
/* 110:141 */       if (this.aborted) {
/* 111:    */         return;
/* 112:    */       }
/* 113:144 */       this.aborted = true;
/* 114:    */       
/* 115:146 */       localRequest = this.connRequest;
/* 116:147 */       localTrigger = this.releaseTrigger;
/* 117:    */     }
/* 118:    */     finally
/* 119:    */     {
/* 120:149 */       this.abortLock.unlock();
/* 121:    */     }
/* 122:156 */     if (localRequest != null) {
/* 123:157 */       localRequest.abortRequest();
/* 124:    */     }
/* 125:159 */     if (localTrigger != null) {
/* 126:    */       try
/* 127:    */       {
/* 128:161 */         localTrigger.abortConnection();
/* 129:    */       }
/* 130:    */       catch (IOException ex) {}
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean isAborted()
/* 135:    */   {
/* 136:169 */     return this.aborted;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Object clone()
/* 140:    */     throws CloneNotSupportedException
/* 141:    */   {
/* 142:174 */     HttpRequestBase clone = (HttpRequestBase)super.clone();
/* 143:175 */     clone.abortLock = new ReentrantLock();
/* 144:176 */     clone.aborted = false;
/* 145:177 */     clone.releaseTrigger = null;
/* 146:178 */     clone.connRequest = null;
/* 147:179 */     clone.headergroup = ((HeaderGroup)CloneUtils.clone(this.headergroup));
/* 148:180 */     clone.params = ((HttpParams)CloneUtils.clone(this.params));
/* 149:181 */     return clone;
/* 150:    */   }
/* 151:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.methods.HttpRequestBase
 * JD-Core Version:    0.7.0.1
 */