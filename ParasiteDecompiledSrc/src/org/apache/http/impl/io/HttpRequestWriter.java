/*  1:   */ package org.apache.http.impl.io;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.HttpMessage;
/*  5:   */ import org.apache.http.HttpRequest;
/*  6:   */ import org.apache.http.io.SessionOutputBuffer;
/*  7:   */ import org.apache.http.message.LineFormatter;
/*  8:   */ import org.apache.http.params.HttpParams;
/*  9:   */ 
/* 10:   */ public class HttpRequestWriter
/* 11:   */   extends AbstractMessageWriter
/* 12:   */ {
/* 13:   */   public HttpRequestWriter(SessionOutputBuffer buffer, LineFormatter formatter, HttpParams params)
/* 14:   */   {
/* 15:49 */     super(buffer, formatter, params);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected void writeHeadLine(HttpMessage message)
/* 19:   */     throws IOException
/* 20:   */   {
/* 21:55 */     this.lineFormatter.formatRequestLine(this.lineBuf, ((HttpRequest)message).getRequestLine());
/* 22:   */     
/* 23:57 */     this.sessionBuffer.writeLine(this.lineBuf);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.HttpRequestWriter
 * JD-Core Version:    0.7.0.1
 */