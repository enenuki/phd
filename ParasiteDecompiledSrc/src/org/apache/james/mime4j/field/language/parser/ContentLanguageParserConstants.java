/*  1:   */ package org.apache.james.mime4j.field.language.parser;
/*  2:   */ 
/*  3:   */ public abstract interface ContentLanguageParserConstants
/*  4:   */ {
/*  5:   */   public static final int EOF = 0;
/*  6:   */   public static final int WS = 3;
/*  7:   */   public static final int COMMENT = 5;
/*  8:   */   public static final int QUOTEDSTRING = 16;
/*  9:   */   public static final int DIGITS = 17;
/* 10:   */   public static final int ALPHA = 18;
/* 11:   */   public static final int ALPHANUM = 19;
/* 12:   */   public static final int DOT = 20;
/* 13:   */   public static final int QUOTEDPAIR = 21;
/* 14:   */   public static final int ANY = 22;
/* 15:   */   public static final int DEFAULT = 0;
/* 16:   */   public static final int INCOMMENT = 1;
/* 17:   */   public static final int NESTED_COMMENT = 2;
/* 18:   */   public static final int INQUOTEDSTRING = 3;
/* 19:40 */   public static final String[] tokenImage = { "<EOF>", "\",\"", "\"-\"", "<WS>", "\"(\"", "\")\"", "<token of kind 6>", "\"(\"", "<token of kind 8>", "<token of kind 9>", "\"(\"", "\")\"", "<token of kind 12>", "\"\\\"\"", "<token of kind 14>", "<token of kind 15>", "\"\\\"\"", "<DIGITS>", "<ALPHA>", "<ALPHANUM>", "\".\"", "<QUOTEDPAIR>", "<ANY>" };
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.language.parser.ContentLanguageParserConstants
 * JD-Core Version:    0.7.0.1
 */