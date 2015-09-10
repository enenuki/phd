/*  1:   */ package org.apache.http.impl.io;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.HttpException;
/*  5:   */ import org.apache.http.HttpMessage;
/*  6:   */ import org.apache.http.HttpResponseFactory;
/*  7:   */ import org.apache.http.NoHttpResponseException;
/*  8:   */ import org.apache.http.ParseException;
/*  9:   */ import org.apache.http.StatusLine;
/* 10:   */ import org.apache.http.io.SessionInputBuffer;
/* 11:   */ import org.apache.http.message.LineParser;
/* 12:   */ import org.apache.http.message.ParserCursor;
/* 13:   */ import org.apache.http.params.HttpParams;
/* 14:   */ import org.apache.http.util.CharArrayBuffer;
/* 15:   */ 
/* 16:   */ public class HttpResponseParser
/* 17:   */   extends AbstractMessageParser
/* 18:   */ {
/* 19:   */   private final HttpResponseFactory responseFactory;
/* 20:   */   private final CharArrayBuffer lineBuf;
/* 21:   */   
/* 22:   */   public HttpResponseParser(SessionInputBuffer buffer, LineParser parser, HttpResponseFactory responseFactory, HttpParams params)
/* 23:   */   {
/* 24:77 */     super(buffer, parser, params);
/* 25:78 */     if (responseFactory == null) {
/* 26:79 */       throw new IllegalArgumentException("Response factory may not be null");
/* 27:   */     }
/* 28:81 */     this.responseFactory = responseFactory;
/* 29:82 */     this.lineBuf = new CharArrayBuffer(128);
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected HttpMessage parseHead(SessionInputBuffer sessionBuffer)
/* 33:   */     throws IOException, HttpException, ParseException
/* 34:   */   {
/* 35:89 */     this.lineBuf.clear();
/* 36:90 */     int i = sessionBuffer.readLine(this.lineBuf);
/* 37:91 */     if (i == -1) {
/* 38:92 */       throw new NoHttpResponseException("The target server failed to respond");
/* 39:   */     }
/* 40:95 */     ParserCursor cursor = new ParserCursor(0, this.lineBuf.length());
/* 41:96 */     StatusLine statusline = this.lineParser.parseStatusLine(this.lineBuf, cursor);
/* 42:97 */     return this.responseFactory.newHttpResponse(statusline, null);
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.HttpResponseParser
 * JD-Core Version:    0.7.0.1
 */