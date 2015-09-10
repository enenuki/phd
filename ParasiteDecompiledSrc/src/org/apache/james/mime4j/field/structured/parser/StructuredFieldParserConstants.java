/*  1:   */ package org.apache.james.mime4j.field.structured.parser;
/*  2:   */ 
/*  3:   */ public abstract interface StructuredFieldParserConstants
/*  4:   */ {
/*  5:   */   public static final int EOF = 0;
/*  6:   */   public static final int STRING_CONTENT = 11;
/*  7:   */   public static final int FOLD = 12;
/*  8:   */   public static final int QUOTEDSTRING = 13;
/*  9:   */   public static final int WS = 14;
/* 10:   */   public static final int CONTENT = 15;
/* 11:   */   public static final int QUOTEDPAIR = 16;
/* 12:   */   public static final int ANY = 17;
/* 13:   */   public static final int DEFAULT = 0;
/* 14:   */   public static final int INCOMMENT = 1;
/* 15:   */   public static final int NESTED_COMMENT = 2;
/* 16:   */   public static final int INQUOTEDSTRING = 3;
/* 17:38 */   public static final String[] tokenImage = { "<EOF>", "\"(\"", "\")\"", "\"(\"", "<token of kind 4>", "\"(\"", "\")\"", "<token of kind 7>", "<token of kind 8>", "\"\\\"\"", "<token of kind 10>", "<STRING_CONTENT>", "<FOLD>", "\"\\\"\"", "<WS>", "<CONTENT>", "<QUOTEDPAIR>", "<ANY>" };
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.structured.parser.StructuredFieldParserConstants
 * JD-Core Version:    0.7.0.1
 */