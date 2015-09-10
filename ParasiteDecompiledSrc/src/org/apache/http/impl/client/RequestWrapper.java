/*   1:    */ package org.apache.http.impl.client;
/*   2:    */ 
/*   3:    */ import java.net.URI;
/*   4:    */ import java.net.URISyntaxException;
/*   5:    */ import org.apache.http.HttpRequest;
/*   6:    */ import org.apache.http.ProtocolException;
/*   7:    */ import org.apache.http.ProtocolVersion;
/*   8:    */ import org.apache.http.RequestLine;
/*   9:    */ import org.apache.http.annotation.NotThreadSafe;
/*  10:    */ import org.apache.http.client.methods.HttpUriRequest;
/*  11:    */ import org.apache.http.message.AbstractHttpMessage;
/*  12:    */ import org.apache.http.message.BasicRequestLine;
/*  13:    */ import org.apache.http.message.HeaderGroup;
/*  14:    */ import org.apache.http.params.HttpProtocolParams;
/*  15:    */ 
/*  16:    */ @NotThreadSafe
/*  17:    */ public class RequestWrapper
/*  18:    */   extends AbstractHttpMessage
/*  19:    */   implements HttpUriRequest
/*  20:    */ {
/*  21:    */   private final HttpRequest original;
/*  22:    */   private URI uri;
/*  23:    */   private String method;
/*  24:    */   private ProtocolVersion version;
/*  25:    */   private int execCount;
/*  26:    */   
/*  27:    */   public RequestWrapper(HttpRequest request)
/*  28:    */     throws ProtocolException
/*  29:    */   {
/*  30: 67 */     if (request == null) {
/*  31: 68 */       throw new IllegalArgumentException("HTTP request may not be null");
/*  32:    */     }
/*  33: 70 */     this.original = request;
/*  34: 71 */     setParams(request.getParams());
/*  35: 72 */     setHeaders(request.getAllHeaders());
/*  36: 74 */     if ((request instanceof HttpUriRequest))
/*  37:    */     {
/*  38: 75 */       this.uri = ((HttpUriRequest)request).getURI();
/*  39: 76 */       this.method = ((HttpUriRequest)request).getMethod();
/*  40: 77 */       this.version = null;
/*  41:    */     }
/*  42:    */     else
/*  43:    */     {
/*  44: 79 */       RequestLine requestLine = request.getRequestLine();
/*  45:    */       try
/*  46:    */       {
/*  47: 81 */         this.uri = new URI(requestLine.getUri());
/*  48:    */       }
/*  49:    */       catch (URISyntaxException ex)
/*  50:    */       {
/*  51: 83 */         throw new ProtocolException("Invalid request URI: " + requestLine.getUri(), ex);
/*  52:    */       }
/*  53: 86 */       this.method = requestLine.getMethod();
/*  54: 87 */       this.version = request.getProtocolVersion();
/*  55:    */     }
/*  56: 89 */     this.execCount = 0;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void resetHeaders()
/*  60:    */   {
/*  61: 94 */     this.headergroup.clear();
/*  62: 95 */     setHeaders(this.original.getAllHeaders());
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getMethod()
/*  66:    */   {
/*  67: 99 */     return this.method;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setMethod(String method)
/*  71:    */   {
/*  72:103 */     if (method == null) {
/*  73:104 */       throw new IllegalArgumentException("Method name may not be null");
/*  74:    */     }
/*  75:106 */     this.method = method;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public ProtocolVersion getProtocolVersion()
/*  79:    */   {
/*  80:110 */     if (this.version == null) {
/*  81:111 */       this.version = HttpProtocolParams.getVersion(getParams());
/*  82:    */     }
/*  83:113 */     return this.version;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setProtocolVersion(ProtocolVersion version)
/*  87:    */   {
/*  88:117 */     this.version = version;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public URI getURI()
/*  92:    */   {
/*  93:122 */     return this.uri;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setURI(URI uri)
/*  97:    */   {
/*  98:126 */     this.uri = uri;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public RequestLine getRequestLine()
/* 102:    */   {
/* 103:130 */     String method = getMethod();
/* 104:131 */     ProtocolVersion ver = getProtocolVersion();
/* 105:132 */     String uritext = null;
/* 106:133 */     if (this.uri != null) {
/* 107:134 */       uritext = this.uri.toASCIIString();
/* 108:    */     }
/* 109:136 */     if ((uritext == null) || (uritext.length() == 0)) {
/* 110:137 */       uritext = "/";
/* 111:    */     }
/* 112:139 */     return new BasicRequestLine(method, uritext, ver);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void abort()
/* 116:    */     throws UnsupportedOperationException
/* 117:    */   {
/* 118:143 */     throw new UnsupportedOperationException();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isAborted()
/* 122:    */   {
/* 123:147 */     return false;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public HttpRequest getOriginal()
/* 127:    */   {
/* 128:151 */     return this.original;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean isRepeatable()
/* 132:    */   {
/* 133:155 */     return true;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getExecCount()
/* 137:    */   {
/* 138:159 */     return this.execCount;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void incrementExecCount()
/* 142:    */   {
/* 143:163 */     this.execCount += 1;
/* 144:    */   }
/* 145:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.RequestWrapper
 * JD-Core Version:    0.7.0.1
 */