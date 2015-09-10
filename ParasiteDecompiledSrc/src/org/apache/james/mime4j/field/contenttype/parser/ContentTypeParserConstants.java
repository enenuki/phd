/*  1:   */ package org.apache.james.mime4j.field.contenttype.parser;
/*  2:   */ 
/*  3:   */ public abstract interface ContentTypeParserConstants
/*  4:   */ {
/*  5:   */   public static final int EOF = 0;
/*  6:   */   public static final int WS = 6;
/*  7:   */   public static final int COMMENT = 8;
/*  8:   */   public static final int QUOTEDSTRING = 19;
/*  9:   */   public static final int DIGITS = 20;
/* 10:   */   public static final int ATOKEN = 21;
/* 11:   */   public static final int QUOTEDPAIR = 22;
/* 12:   */   public static final int ANY = 23;
/* 13:   */   public static final int DEFAULT = 0;
/* 14:   */   public static final int INCOMMENT = 1;
/* 15:   */   public static final int NESTED_COMMENT = 2;
/* 16:   */   public static final int INQUOTEDSTRING = 3;
/* 17:38 */   public static final String[] tokenImage = { "<EOF>", "\"\\r\"", "\"\\n\"", "\"/\"", "\";\"", "\"=\"", "<WS>", "\"(\"", "\")\"", "<token of kind 9>", "\"(\"", "<token of kind 11>", "<token of kind 12>", "\"(\"", "\")\"", "<token of kind 15>", "\"\\\"\"", "<token of kind 17>", "<token of kind 18>", "\"\\\"\"", "<DIGITS>", "<ATOKEN>", "<QUOTEDPAIR>", "<ANY>" };
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.contenttype.parser.ContentTypeParserConstants
 * JD-Core Version:    0.7.0.1
 */