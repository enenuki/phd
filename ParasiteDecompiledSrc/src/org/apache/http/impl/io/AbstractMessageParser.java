/*   1:    */ package org.apache.http.impl.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.http.Header;
/*   7:    */ import org.apache.http.HttpException;
/*   8:    */ import org.apache.http.HttpMessage;
/*   9:    */ import org.apache.http.ParseException;
/*  10:    */ import org.apache.http.ProtocolException;
/*  11:    */ import org.apache.http.io.HttpMessageParser;
/*  12:    */ import org.apache.http.io.SessionInputBuffer;
/*  13:    */ import org.apache.http.message.BasicLineParser;
/*  14:    */ import org.apache.http.message.LineParser;
/*  15:    */ import org.apache.http.params.HttpParams;
/*  16:    */ import org.apache.http.util.CharArrayBuffer;
/*  17:    */ 
/*  18:    */ public abstract class AbstractMessageParser
/*  19:    */   implements HttpMessageParser
/*  20:    */ {
/*  21:    */   private static final int HEAD_LINE = 0;
/*  22:    */   private static final int HEADERS = 1;
/*  23:    */   private final SessionInputBuffer sessionBuffer;
/*  24:    */   private final int maxHeaderCount;
/*  25:    */   private final int maxLineLen;
/*  26:    */   private final List headerLines;
/*  27:    */   protected final LineParser lineParser;
/*  28:    */   private int state;
/*  29:    */   private HttpMessage message;
/*  30:    */   
/*  31:    */   public AbstractMessageParser(SessionInputBuffer buffer, LineParser parser, HttpParams params)
/*  32:    */   {
/*  33: 86 */     if (buffer == null) {
/*  34: 87 */       throw new IllegalArgumentException("Session input buffer may not be null");
/*  35:    */     }
/*  36: 89 */     if (params == null) {
/*  37: 90 */       throw new IllegalArgumentException("HTTP parameters may not be null");
/*  38:    */     }
/*  39: 92 */     this.sessionBuffer = buffer;
/*  40: 93 */     this.maxHeaderCount = params.getIntParameter("http.connection.max-header-count", -1);
/*  41:    */     
/*  42: 95 */     this.maxLineLen = params.getIntParameter("http.connection.max-line-length", -1);
/*  43:    */     
/*  44: 97 */     this.lineParser = (parser != null ? parser : BasicLineParser.DEFAULT);
/*  45: 98 */     this.headerLines = new ArrayList();
/*  46: 99 */     this.state = 0;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static Header[] parseHeaders(SessionInputBuffer inbuffer, int maxHeaderCount, int maxLineLen, LineParser parser)
/*  50:    */     throws HttpException, IOException
/*  51:    */   {
/*  52:127 */     if (parser == null) {
/*  53:128 */       parser = BasicLineParser.DEFAULT;
/*  54:    */     }
/*  55:130 */     List headerLines = new ArrayList();
/*  56:131 */     return parseHeaders(inbuffer, maxHeaderCount, maxLineLen, parser, headerLines);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static Header[] parseHeaders(SessionInputBuffer inbuffer, int maxHeaderCount, int maxLineLen, LineParser parser, List headerLines)
/*  60:    */     throws HttpException, IOException
/*  61:    */   {
/*  62:166 */     if (inbuffer == null) {
/*  63:167 */       throw new IllegalArgumentException("Session input buffer may not be null");
/*  64:    */     }
/*  65:169 */     if (parser == null) {
/*  66:170 */       throw new IllegalArgumentException("Line parser may not be null");
/*  67:    */     }
/*  68:172 */     if (headerLines == null) {
/*  69:173 */       throw new IllegalArgumentException("Header line list may not be null");
/*  70:    */     }
/*  71:176 */     CharArrayBuffer current = null;
/*  72:177 */     CharArrayBuffer previous = null;
/*  73:    */     for (;;)
/*  74:    */     {
/*  75:179 */       if (current == null) {
/*  76:180 */         current = new CharArrayBuffer(64);
/*  77:    */       } else {
/*  78:182 */         current.clear();
/*  79:    */       }
/*  80:184 */       int l = inbuffer.readLine(current);
/*  81:185 */       if ((l == -1) || (current.length() < 1)) {
/*  82:    */         break;
/*  83:    */       }
/*  84:192 */       if (((current.charAt(0) == ' ') || (current.charAt(0) == '\t')) && (previous != null))
/*  85:    */       {
/*  86:195 */         int i = 0;
/*  87:196 */         while (i < current.length())
/*  88:    */         {
/*  89:197 */           char ch = current.charAt(i);
/*  90:198 */           if ((ch != ' ') && (ch != '\t')) {
/*  91:    */             break;
/*  92:    */           }
/*  93:201 */           i++;
/*  94:    */         }
/*  95:203 */         if ((maxLineLen > 0) && (previous.length() + 1 + current.length() - i > maxLineLen)) {
/*  96:205 */           throw new IOException("Maximum line length limit exceeded");
/*  97:    */         }
/*  98:207 */         previous.append(' ');
/*  99:208 */         previous.append(current, i, current.length() - i);
/* 100:    */       }
/* 101:    */       else
/* 102:    */       {
/* 103:210 */         headerLines.add(current);
/* 104:211 */         previous = current;
/* 105:212 */         current = null;
/* 106:    */       }
/* 107:214 */       if ((maxHeaderCount > 0) && (headerLines.size() >= maxHeaderCount)) {
/* 108:215 */         throw new IOException("Maximum header count exceeded");
/* 109:    */       }
/* 110:    */     }
/* 111:218 */     Header[] headers = new Header[headerLines.size()];
/* 112:219 */     for (int i = 0; i < headerLines.size(); i++)
/* 113:    */     {
/* 114:220 */       CharArrayBuffer buffer = (CharArrayBuffer)headerLines.get(i);
/* 115:    */       try
/* 116:    */       {
/* 117:222 */         headers[i] = parser.parseHeader(buffer);
/* 118:    */       }
/* 119:    */       catch (ParseException ex)
/* 120:    */       {
/* 121:224 */         throw new ProtocolException(ex.getMessage());
/* 122:    */       }
/* 123:    */     }
/* 124:227 */     return headers;
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected abstract HttpMessage parseHead(SessionInputBuffer paramSessionInputBuffer)
/* 128:    */     throws IOException, HttpException, ParseException;
/* 129:    */   
/* 130:    */   public HttpMessage parse()
/* 131:    */     throws IOException, HttpException
/* 132:    */   {
/* 133:248 */     int st = this.state;
/* 134:249 */     switch (st)
/* 135:    */     {
/* 136:    */     case 0: 
/* 137:    */       try
/* 138:    */       {
/* 139:252 */         this.message = parseHead(this.sessionBuffer);
/* 140:    */       }
/* 141:    */       catch (ParseException px)
/* 142:    */       {
/* 143:254 */         throw new ProtocolException(px.getMessage(), px);
/* 144:    */       }
/* 145:256 */       this.state = 1;
/* 146:    */     case 1: 
/* 147:259 */       Header[] headers = parseHeaders(this.sessionBuffer, this.maxHeaderCount, this.maxLineLen, this.lineParser, this.headerLines);
/* 148:    */       
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:265 */       this.message.setHeaders(headers);
/* 154:266 */       HttpMessage result = this.message;
/* 155:267 */       this.message = null;
/* 156:268 */       this.headerLines.clear();
/* 157:269 */       this.state = 0;
/* 158:270 */       return result;
/* 159:    */     }
/* 160:272 */     throw new IllegalStateException("Inconsistent parser state");
/* 161:    */   }
/* 162:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.io.AbstractMessageParser
 * JD-Core Version:    0.7.0.1
 */