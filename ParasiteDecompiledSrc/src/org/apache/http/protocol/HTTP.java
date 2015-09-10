/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ public final class HTTP
/*  4:   */ {
/*  5:   */   public static final int CR = 13;
/*  6:   */   public static final int LF = 10;
/*  7:   */   public static final int SP = 32;
/*  8:   */   public static final int HT = 9;
/*  9:   */   public static final String TRANSFER_ENCODING = "Transfer-Encoding";
/* 10:   */   public static final String CONTENT_LEN = "Content-Length";
/* 11:   */   public static final String CONTENT_TYPE = "Content-Type";
/* 12:   */   public static final String CONTENT_ENCODING = "Content-Encoding";
/* 13:   */   public static final String EXPECT_DIRECTIVE = "Expect";
/* 14:   */   public static final String CONN_DIRECTIVE = "Connection";
/* 15:   */   public static final String TARGET_HOST = "Host";
/* 16:   */   public static final String USER_AGENT = "User-Agent";
/* 17:   */   public static final String DATE_HEADER = "Date";
/* 18:   */   public static final String SERVER_HEADER = "Server";
/* 19:   */   public static final String EXPECT_CONTINUE = "100-continue";
/* 20:   */   public static final String CONN_CLOSE = "Close";
/* 21:   */   public static final String CONN_KEEP_ALIVE = "Keep-Alive";
/* 22:   */   public static final String CHUNK_CODING = "chunked";
/* 23:   */   public static final String IDENTITY_CODING = "identity";
/* 24:   */   public static final String UTF_8 = "UTF-8";
/* 25:   */   public static final String UTF_16 = "UTF-16";
/* 26:   */   public static final String US_ASCII = "US-ASCII";
/* 27:   */   public static final String ASCII = "ASCII";
/* 28:   */   public static final String ISO_8859_1 = "ISO-8859-1";
/* 29:   */   public static final String DEFAULT_CONTENT_CHARSET = "ISO-8859-1";
/* 30:   */   public static final String DEFAULT_PROTOCOL_CHARSET = "US-ASCII";
/* 31:   */   public static final String OCTET_STREAM_TYPE = "application/octet-stream";
/* 32:   */   public static final String PLAIN_TEXT_TYPE = "text/plain";
/* 33:   */   public static final String CHARSET_PARAM = "; charset=";
/* 34:   */   public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
/* 35:   */   
/* 36:   */   public static boolean isWhitespace(char ch)
/* 37:   */   {
/* 38:85 */     return (ch == ' ') || (ch == '\t') || (ch == '\r') || (ch == '\n');
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.HTTP
 * JD-Core Version:    0.7.0.1
 */