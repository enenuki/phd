/*  1:   */ package org.apache.http.impl.io;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import org.apache.http.Header;
/*  6:   */ import org.apache.http.HttpException;
/*  7:   */ import org.apache.http.HttpMessage;
/*  8:   */ import org.apache.http.io.HttpMessageWriter;
/*  9:   */ import org.apache.http.io.SessionOutputBuffer;
/* 10:   */ import org.apache.http.message.BasicLineFormatter;
/* 11:   */ import org.apache.http.message.LineFormatter;
/* 12:   */ import org.apache.http.params.HttpParams;
/* 13:   */ import org.apache.http.util.CharArrayBuffer;
/* 14:   */ 
/* 15:   */ public abstract class AbstractMessageWriter
/* 16:   */   implements HttpMessageWriter
/* 17:   */ {
/* 18:   */   protected final SessionOutputBuffer sessionBuffer;
/* 19:   */   protected final CharArrayBuffer lineBuf;
/* 20:   */   protected final LineFormatter lineFormatter;
/* 21:   */   
/* 22:   */   public AbstractMessageWriter(SessionOutputBuffer buffer, LineFormatter formatter, HttpParams params)
/* 23:   */   {
/* 24:66 */     if (buffer == null) {
/* 25:67 */       throw new IllegalArgumentException("Session input buffer may not be null");
/* 26:   */     }
/* 27:69 */     this.sessionBuffer = buffer;
/* 28:70 */     this.lineBuf = new CharArrayBuffer(128);
/* 29:71 */     this.lineFormatter = (formatter != null ? formatter : BasicLineFormatter.DEFAULT);
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected abstract void writeHeadLine(HttpMessage paramHttpMessage)
/* 33:   */     throws IOException;
/* 34:   */   
/* 35:   */   public void write(HttpMessage message)
/* 36:   */     throws IOException, HttpException
/* 37:   */   {
/* 38:86 */     if (message == null) {
/* 39:87 */       throw new IllegalArgumentException("HTTP message may not be null");
/* 40:   */     }
/* 41:89 */     writeHeadLine(message);
/* 42:90 */     for (Iterator it = message.headerIterator(); it.hasNext();)
/* 43:   */     {
/* 44:91 */       Header header = (Header)it.next();
/* 45:92 */       this.sessionBuffer.writeLine(this.lineFormatter.formatHeader(this.lineBuf, header));
/* 46:   */     }
/* 47:95 */     this.lineBuf.clear();
/* 48:96 */     this.sessionBuffer.writeLine(this.lineBuf);
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.AbstractMessageWriter
 * JD-Core Version:    0.7.0.1
 */