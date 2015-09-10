/*   1:    */ package org.apache.http.impl.conn;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.commons.logging.Log;
/*   5:    */ import org.apache.commons.logging.LogFactory;
/*   6:    */ import org.apache.http.HttpException;
/*   7:    */ import org.apache.http.HttpMessage;
/*   8:    */ import org.apache.http.HttpResponseFactory;
/*   9:    */ import org.apache.http.NoHttpResponseException;
/*  10:    */ import org.apache.http.ProtocolException;
/*  11:    */ import org.apache.http.StatusLine;
/*  12:    */ import org.apache.http.annotation.ThreadSafe;
/*  13:    */ import org.apache.http.impl.io.AbstractMessageParser;
/*  14:    */ import org.apache.http.io.SessionInputBuffer;
/*  15:    */ import org.apache.http.message.LineParser;
/*  16:    */ import org.apache.http.message.ParserCursor;
/*  17:    */ import org.apache.http.params.HttpParams;
/*  18:    */ import org.apache.http.util.CharArrayBuffer;
/*  19:    */ 
/*  20:    */ @ThreadSafe
/*  21:    */ public class DefaultResponseParser
/*  22:    */   extends AbstractMessageParser
/*  23:    */ {
/*  24: 66 */   private final Log log = LogFactory.getLog(getClass());
/*  25:    */   private final HttpResponseFactory responseFactory;
/*  26:    */   private final CharArrayBuffer lineBuf;
/*  27:    */   private final int maxGarbageLines;
/*  28:    */   
/*  29:    */   public DefaultResponseParser(SessionInputBuffer buffer, LineParser parser, HttpResponseFactory responseFactory, HttpParams params)
/*  30:    */   {
/*  31: 77 */     super(buffer, parser, params);
/*  32: 78 */     if (responseFactory == null) {
/*  33: 79 */       throw new IllegalArgumentException("Response factory may not be null");
/*  34:    */     }
/*  35: 82 */     this.responseFactory = responseFactory;
/*  36: 83 */     this.lineBuf = new CharArrayBuffer(128);
/*  37: 84 */     this.maxGarbageLines = params.getIntParameter("http.connection.max-status-line-garbage", 2147483647);
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected HttpMessage parseHead(SessionInputBuffer sessionBuffer)
/*  41:    */     throws IOException, HttpException
/*  42:    */   {
/*  43: 93 */     int count = 0;
/*  44: 94 */     ParserCursor cursor = null;
/*  45:    */     for (;;)
/*  46:    */     {
/*  47: 97 */       this.lineBuf.clear();
/*  48: 98 */       int i = sessionBuffer.readLine(this.lineBuf);
/*  49: 99 */       if ((i == -1) && (count == 0)) {
/*  50:101 */         throw new NoHttpResponseException("The target server failed to respond");
/*  51:    */       }
/*  52:103 */       cursor = new ParserCursor(0, this.lineBuf.length());
/*  53:104 */       if (this.lineParser.hasProtocolVersion(this.lineBuf, cursor)) {
/*  54:    */         break;
/*  55:    */       }
/*  56:107 */       if ((i == -1) || (count >= this.maxGarbageLines)) {
/*  57:109 */         throw new ProtocolException("The server failed to respond with a valid HTTP response");
/*  58:    */       }
/*  59:112 */       if (this.log.isDebugEnabled()) {
/*  60:113 */         this.log.debug("Garbage in response: " + this.lineBuf.toString());
/*  61:    */       }
/*  62:115 */       count++;
/*  63:    */     }
/*  64:118 */     StatusLine statusline = this.lineParser.parseStatusLine(this.lineBuf, cursor);
/*  65:119 */     return this.responseFactory.newHttpResponse(statusline, null);
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.conn.DefaultResponseParser
 * JD-Core Version:    0.7.0.1
 */