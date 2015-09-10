/*  1:   */ package org.apache.http.message;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.http.ProtocolVersion;
/*  5:   */ import org.apache.http.RequestLine;
/*  6:   */ import org.apache.http.util.CharArrayBuffer;
/*  7:   */ 
/*  8:   */ public class BasicRequestLine
/*  9:   */   implements RequestLine, Cloneable, Serializable
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = 2810581718468737193L;
/* 12:   */   private final ProtocolVersion protoversion;
/* 13:   */   private final String method;
/* 14:   */   private final String uri;
/* 15:   */   
/* 16:   */   public BasicRequestLine(String method, String uri, ProtocolVersion version)
/* 17:   */   {
/* 18:52 */     if (method == null) {
/* 19:53 */       throw new IllegalArgumentException("Method must not be null.");
/* 20:   */     }
/* 21:56 */     if (uri == null) {
/* 22:57 */       throw new IllegalArgumentException("URI must not be null.");
/* 23:   */     }
/* 24:60 */     if (version == null) {
/* 25:61 */       throw new IllegalArgumentException("Protocol version must not be null.");
/* 26:   */     }
/* 27:64 */     this.method = method;
/* 28:65 */     this.uri = uri;
/* 29:66 */     this.protoversion = version;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String getMethod()
/* 33:   */   {
/* 34:70 */     return this.method;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public ProtocolVersion getProtocolVersion()
/* 38:   */   {
/* 39:74 */     return this.protoversion;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String getUri()
/* 43:   */   {
/* 44:78 */     return this.uri;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String toString()
/* 48:   */   {
/* 49:83 */     return BasicLineFormatter.DEFAULT.formatRequestLine(null, this).toString();
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Object clone()
/* 53:   */     throws CloneNotSupportedException
/* 54:   */   {
/* 55:88 */     return super.clone();
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicRequestLine
 * JD-Core Version:    0.7.0.1
 */