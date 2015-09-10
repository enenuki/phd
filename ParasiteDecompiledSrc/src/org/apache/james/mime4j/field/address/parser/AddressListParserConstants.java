/*  1:   */ package org.apache.james.mime4j.field.address.parser;
/*  2:   */ 
/*  3:   */ public abstract interface AddressListParserConstants
/*  4:   */ {
/*  5:   */   public static final int EOF = 0;
/*  6:   */   public static final int WS = 10;
/*  7:   */   public static final int ALPHA = 11;
/*  8:   */   public static final int DIGIT = 12;
/*  9:   */   public static final int ATEXT = 13;
/* 10:   */   public static final int DOTATOM = 14;
/* 11:   */   public static final int DOMAINLITERAL = 18;
/* 12:   */   public static final int COMMENT = 20;
/* 13:   */   public static final int QUOTEDSTRING = 31;
/* 14:   */   public static final int QUOTEDPAIR = 32;
/* 15:   */   public static final int ANY = 33;
/* 16:   */   public static final int DEFAULT = 0;
/* 17:   */   public static final int INDOMAINLITERAL = 1;
/* 18:   */   public static final int INCOMMENT = 2;
/* 19:   */   public static final int NESTED_COMMENT = 3;
/* 20:   */   public static final int INQUOTEDSTRING = 4;
/* 21:42 */   public static final String[] tokenImage = { "<EOF>", "\"\\r\"", "\"\\n\"", "\",\"", "\":\"", "\";\"", "\"<\"", "\">\"", "\"@\"", "\".\"", "<WS>", "<ALPHA>", "<DIGIT>", "<ATEXT>", "<DOTATOM>", "\"[\"", "<token of kind 16>", "<token of kind 17>", "\"]\"", "\"(\"", "\")\"", "<token of kind 21>", "\"(\"", "<token of kind 23>", "<token of kind 24>", "\"(\"", "\")\"", "<token of kind 27>", "\"\\\"\"", "<token of kind 29>", "<token of kind 30>", "\"\\\"\"", "<QUOTEDPAIR>", "<ANY>" };
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.parser.AddressListParserConstants
 * JD-Core Version:    0.7.0.1
 */