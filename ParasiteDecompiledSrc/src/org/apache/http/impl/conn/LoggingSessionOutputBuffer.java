/*   1:    */ package org.apache.http.impl.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.http.annotation.Immutable;
/*   5:    */ import org.apache.http.io.HttpTransportMetrics;
/*   6:    */ import org.apache.http.io.SessionOutputBuffer;
/*   7:    */ import org.apache.http.util.CharArrayBuffer;
/*   8:    */ 
/*   9:    */ @Immutable
/*  10:    */ public class LoggingSessionOutputBuffer
/*  11:    */   implements SessionOutputBuffer
/*  12:    */ {
/*  13:    */   private final SessionOutputBuffer out;
/*  14:    */   private final Wire wire;
/*  15:    */   private final String charset;
/*  16:    */   
/*  17:    */   public LoggingSessionOutputBuffer(SessionOutputBuffer out, Wire wire, String charset)
/*  18:    */   {
/*  19: 64 */     this.out = out;
/*  20: 65 */     this.wire = wire;
/*  21: 66 */     this.charset = (charset != null ? charset : "ASCII");
/*  22:    */   }
/*  23:    */   
/*  24:    */   public LoggingSessionOutputBuffer(SessionOutputBuffer out, Wire wire)
/*  25:    */   {
/*  26: 70 */     this(out, wire, null);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void write(byte[] b, int off, int len)
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 74 */     this.out.write(b, off, len);
/*  33: 75 */     if (this.wire.enabled()) {
/*  34: 76 */       this.wire.output(b, off, len);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void write(int b)
/*  39:    */     throws IOException
/*  40:    */   {
/*  41: 81 */     this.out.write(b);
/*  42: 82 */     if (this.wire.enabled()) {
/*  43: 83 */       this.wire.output(b);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void write(byte[] b)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50: 88 */     this.out.write(b);
/*  51: 89 */     if (this.wire.enabled()) {
/*  52: 90 */       this.wire.output(b);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void flush()
/*  57:    */     throws IOException
/*  58:    */   {
/*  59: 95 */     this.out.flush();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void writeLine(CharArrayBuffer buffer)
/*  63:    */     throws IOException
/*  64:    */   {
/*  65: 99 */     this.out.writeLine(buffer);
/*  66:100 */     if (this.wire.enabled())
/*  67:    */     {
/*  68:101 */       String s = new String(buffer.buffer(), 0, buffer.length());
/*  69:102 */       String tmp = s + "\r\n";
/*  70:103 */       this.wire.output(tmp.getBytes(this.charset));
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void writeLine(String s)
/*  75:    */     throws IOException
/*  76:    */   {
/*  77:108 */     this.out.writeLine(s);
/*  78:109 */     if (this.wire.enabled())
/*  79:    */     {
/*  80:110 */       String tmp = s + "\r\n";
/*  81:111 */       this.wire.output(tmp.getBytes(this.charset));
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public HttpTransportMetrics getMetrics()
/*  86:    */   {
/*  87:116 */     return this.out.getMetrics();
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.LoggingSessionOutputBuffer
 * JD-Core Version:    0.7.0.1
 */