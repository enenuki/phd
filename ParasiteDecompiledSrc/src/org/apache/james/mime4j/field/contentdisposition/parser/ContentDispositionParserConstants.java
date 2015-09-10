/*  1:   */ package org.apache.james.mime4j.field.contentdisposition.parser;
/*  2:   */ 
/*  3:   */ public abstract interface ContentDispositionParserConstants
/*  4:   */ {
/*  5:   */   public static final int EOF = 0;
/*  6:   */   public static final int WS = 5;
/*  7:   */   public static final int COMMENT = 7;
/*  8:   */   public static final int QUOTEDSTRING = 18;
/*  9:   */   public static final int DIGITS = 19;
/* 10:   */   public static final int ATOKEN = 20;
/* 11:   */   public static final int QUOTEDPAIR = 21;
/* 12:   */   public static final int ANY = 22;
/* 13:   */   public static final int DEFAULT = 0;
/* 14:   */   public static final int INCOMMENT = 1;
/* 15:   */   public static final int NESTED_COMMENT = 2;
/* 16:   */   public static final int INQUOTEDSTRING = 3;
/* 17:38 */   public static final String[] tokenImage = { "<EOF>", "\"\\r\"", "\"\\n\"", "\";\"", "\"=\"", "<WS>", "\"(\"", "\")\"", "<token of kind 8>", "\"(\"", "<token of kind 10>", "<token of kind 11>", "\"(\"", "\")\"", "<token of kind 14>", "\"\\\"\"", "<token of kind 16>", "<token of kind 17>", "\"\\\"\"", "<DIGITS>", "<ATOKEN>", "<QUOTEDPAIR>", "<ANY>" };
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.contentdisposition.parser.ContentDispositionParserConstants
 * JD-Core Version:    0.7.0.1
 */