/*  1:   */ package org.apache.james.mime4j.field.datetime.parser;
/*  2:   */ 
/*  3:   */ public abstract interface DateTimeParserConstants
/*  4:   */ {
/*  5:   */   public static final int EOF = 0;
/*  6:   */   public static final int OFFSETDIR = 24;
/*  7:   */   public static final int MILITARY_ZONE = 35;
/*  8:   */   public static final int WS = 36;
/*  9:   */   public static final int COMMENT = 38;
/* 10:   */   public static final int DIGITS = 46;
/* 11:   */   public static final int QUOTEDPAIR = 47;
/* 12:   */   public static final int ANY = 48;
/* 13:   */   public static final int DEFAULT = 0;
/* 14:   */   public static final int INCOMMENT = 1;
/* 15:   */   public static final int NESTED_COMMENT = 2;
/* 16:37 */   public static final String[] tokenImage = { "<EOF>", "\"\\r\"", "\"\\n\"", "\",\"", "\"Mon\"", "\"Tue\"", "\"Wed\"", "\"Thu\"", "\"Fri\"", "\"Sat\"", "\"Sun\"", "\"Jan\"", "\"Feb\"", "\"Mar\"", "\"Apr\"", "\"May\"", "\"Jun\"", "\"Jul\"", "\"Aug\"", "\"Sep\"", "\"Oct\"", "\"Nov\"", "\"Dec\"", "\":\"", "<OFFSETDIR>", "\"UT\"", "\"GMT\"", "\"EST\"", "\"EDT\"", "\"CST\"", "\"CDT\"", "\"MST\"", "\"MDT\"", "\"PST\"", "\"PDT\"", "<MILITARY_ZONE>", "<WS>", "\"(\"", "\")\"", "<token of kind 39>", "\"(\"", "<token of kind 41>", "<token of kind 42>", "\"(\"", "\")\"", "<token of kind 45>", "<DIGITS>", "<QUOTEDPAIR>", "<ANY>" };
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.datetime.parser.DateTimeParserConstants
 * JD-Core Version:    0.7.0.1
 */