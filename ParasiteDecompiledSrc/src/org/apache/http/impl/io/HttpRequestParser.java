/*  1:   */ package org.apache.http.impl.io;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.ConnectionClosedException;
/*  5:   */ import org.apache.http.HttpException;
/*  6:   */ import org.apache.http.HttpMessage;
/*  7:   */ import org.apache.http.HttpRequestFactory;
/*  8:   */ import org.apache.http.ParseException;
/*  9:   */ import org.apache.http.RequestLine;
/* 10:   */ import org.apache.http.io.SessionInputBuffer;
/* 11:   */ import org.apache.http.message.LineParser;
/* 12:   */ import org.apache.http.message.ParserCursor;
/* 13:   */ import org.apache.http.params.HttpParams;
/* 14:   */ import org.apache.http.util.CharArrayBuffer;
/* 15:   */ 
/* 16:   */ public class HttpRequestParser
/* 17:   */   extends AbstractMessageParser
/* 18:   */ {
/* 19:   */   private final HttpRequestFactory requestFactory;
/* 20:   */   private final CharArrayBuffer lineBuf;
/* 21:   */   
/* 22:   */   public HttpRequestParser(SessionInputBuffer buffer, LineParser parser, HttpRequestFactory requestFactory, HttpParams params)
/* 23:   */   {
/* 24:77 */     super(buffer, parser, params);
/* 25:78 */     if (requestFactory == null) {
/* 26:79 */       throw new IllegalArgumentException("Request factory may not be null");
/* 27:   */     }
/* 28:81 */     this.requestFactory = requestFactory;
/* 29:82 */     this.lineBuf = new CharArrayBuffer(128);
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected HttpMessage parseHead(SessionInputBuffer sessionBuffer)
/* 33:   */     throws IOException, HttpException, ParseException
/* 34:   */   {
/* 35:89 */     this.lineBuf.clear();
/* 36:90 */     int i = sessionBuffer.readLine(this.lineBuf);
/* 37:91 */     if (i == -1) {
/* 38:92 */       throw new ConnectionClosedException("Client closed connection");
/* 39:   */     }
/* 40:94 */     ParserCursor cursor = new ParserCursor(0, this.lineBuf.length());
/* 41:95 */     RequestLine requestline = this.lineParser.parseRequestLine(this.lineBuf, cursor);
/* 42:96 */     return this.requestFactory.newHttpRequest(requestline);
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.HttpRequestParser
 * JD-Core Version:    0.7.0.1
 */