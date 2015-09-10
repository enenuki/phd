/*   1:    */ package org.apache.http.message;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.http.ProtocolVersion;
/*   5:    */ import org.apache.http.StatusLine;
/*   6:    */ import org.apache.http.util.CharArrayBuffer;
/*   7:    */ 
/*   8:    */ public class BasicStatusLine
/*   9:    */   implements StatusLine, Cloneable, Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = -2443303766890459269L;
/*  12:    */   private final ProtocolVersion protoVersion;
/*  13:    */   private final int statusCode;
/*  14:    */   private final String reasonPhrase;
/*  15:    */   
/*  16:    */   public BasicStatusLine(ProtocolVersion version, int statusCode, String reasonPhrase)
/*  17:    */   {
/*  18: 69 */     if (version == null) {
/*  19: 70 */       throw new IllegalArgumentException("Protocol version may not be null.");
/*  20:    */     }
/*  21: 73 */     if (statusCode < 0) {
/*  22: 74 */       throw new IllegalArgumentException("Status code may not be negative.");
/*  23:    */     }
/*  24: 77 */     this.protoVersion = version;
/*  25: 78 */     this.statusCode = statusCode;
/*  26: 79 */     this.reasonPhrase = reasonPhrase;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int getStatusCode()
/*  30:    */   {
/*  31: 85 */     return this.statusCode;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public ProtocolVersion getProtocolVersion()
/*  35:    */   {
/*  36: 89 */     return this.protoVersion;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getReasonPhrase()
/*  40:    */   {
/*  41: 93 */     return this.reasonPhrase;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String toString()
/*  45:    */   {
/*  46: 98 */     return BasicLineFormatter.DEFAULT.formatStatusLine(null, this).toString();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Object clone()
/*  50:    */     throws CloneNotSupportedException
/*  51:    */   {
/*  52:103 */     return super.clone();
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.BasicStatusLine
 * JD-Core Version:    0.7.0.1
 */