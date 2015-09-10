/*   1:    */ package org.apache.http.impl.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.http.annotation.Immutable;
/*   5:    */ import org.apache.http.io.EofSensor;
/*   6:    */ import org.apache.http.io.HttpTransportMetrics;
/*   7:    */ import org.apache.http.io.SessionInputBuffer;
/*   8:    */ import org.apache.http.util.CharArrayBuffer;
/*   9:    */ 
/*  10:    */ @Immutable
/*  11:    */ public class LoggingSessionInputBuffer
/*  12:    */   implements SessionInputBuffer, EofSensor
/*  13:    */ {
/*  14:    */   private final SessionInputBuffer in;
/*  15:    */   private final EofSensor eofSensor;
/*  16:    */   private final Wire wire;
/*  17:    */   private final String charset;
/*  18:    */   
/*  19:    */   public LoggingSessionInputBuffer(SessionInputBuffer in, Wire wire, String charset)
/*  20:    */   {
/*  21: 67 */     this.in = in;
/*  22: 68 */     this.eofSensor = ((in instanceof EofSensor) ? (EofSensor)in : null);
/*  23: 69 */     this.wire = wire;
/*  24: 70 */     this.charset = (charset != null ? charset : "ASCII");
/*  25:    */   }
/*  26:    */   
/*  27:    */   public LoggingSessionInputBuffer(SessionInputBuffer in, Wire wire)
/*  28:    */   {
/*  29: 74 */     this(in, wire, null);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean isDataAvailable(int timeout)
/*  33:    */     throws IOException
/*  34:    */   {
/*  35: 78 */     return this.in.isDataAvailable(timeout);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int read(byte[] b, int off, int len)
/*  39:    */     throws IOException
/*  40:    */   {
/*  41: 82 */     int l = this.in.read(b, off, len);
/*  42: 83 */     if ((this.wire.enabled()) && (l > 0)) {
/*  43: 84 */       this.wire.input(b, off, l);
/*  44:    */     }
/*  45: 86 */     return l;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int read()
/*  49:    */     throws IOException
/*  50:    */   {
/*  51: 90 */     int l = this.in.read();
/*  52: 91 */     if ((this.wire.enabled()) && (l != -1)) {
/*  53: 92 */       this.wire.input(l);
/*  54:    */     }
/*  55: 94 */     return l;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int read(byte[] b)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61: 98 */     int l = this.in.read(b);
/*  62: 99 */     if ((this.wire.enabled()) && (l > 0)) {
/*  63:100 */       this.wire.input(b, 0, l);
/*  64:    */     }
/*  65:102 */     return l;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String readLine()
/*  69:    */     throws IOException
/*  70:    */   {
/*  71:106 */     String s = this.in.readLine();
/*  72:107 */     if ((this.wire.enabled()) && (s != null))
/*  73:    */     {
/*  74:108 */       String tmp = s + "\r\n";
/*  75:109 */       this.wire.input(tmp.getBytes(this.charset));
/*  76:    */     }
/*  77:111 */     return s;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int readLine(CharArrayBuffer buffer)
/*  81:    */     throws IOException
/*  82:    */   {
/*  83:115 */     int l = this.in.readLine(buffer);
/*  84:116 */     if ((this.wire.enabled()) && (l >= 0))
/*  85:    */     {
/*  86:117 */       int pos = buffer.length() - l;
/*  87:118 */       String s = new String(buffer.buffer(), pos, l);
/*  88:119 */       String tmp = s + "\r\n";
/*  89:120 */       this.wire.input(tmp.getBytes(this.charset));
/*  90:    */     }
/*  91:122 */     return l;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public HttpTransportMetrics getMetrics()
/*  95:    */   {
/*  96:126 */     return this.in.getMetrics();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isEof()
/* 100:    */   {
/* 101:130 */     if (this.eofSensor != null) {
/* 102:131 */       return this.eofSensor.isEof();
/* 103:    */     }
/* 104:133 */     return false;
/* 105:    */   }
/* 106:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.LoggingSessionInputBuffer
 * JD-Core Version:    0.7.0.1
 */