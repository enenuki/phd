/*  1:   */ package com.steadystate.css.parser;
/*  2:   */ 
/*  3:   */ public abstract interface SACParserCSS1Constants
/*  4:   */ {
/*  5:   */   public static final int EOF = 0;
/*  6:   */   public static final int S = 1;
/*  7:   */   public static final int IDENT = 3;
/*  8:   */   public static final int LINK_PSCLASS = 4;
/*  9:   */   public static final int VISITED_PSCLASS = 5;
/* 10:   */   public static final int ACTIVE_PSCLASS = 6;
/* 11:   */   public static final int FIRST_LINE = 7;
/* 12:   */   public static final int FIRST_LETTER = 8;
/* 13:   */   public static final int HASH = 9;
/* 14:   */   public static final int LBRACE = 10;
/* 15:   */   public static final int RBRACE = 11;
/* 16:   */   public static final int COMMA = 12;
/* 17:   */   public static final int DOT = 13;
/* 18:   */   public static final int SEMICOLON = 14;
/* 19:   */   public static final int COLON = 15;
/* 20:   */   public static final int ASTERISK = 16;
/* 21:   */   public static final int SLASH = 17;
/* 22:   */   public static final int PLUS = 18;
/* 23:   */   public static final int MINUS = 19;
/* 24:   */   public static final int EQUALS = 20;
/* 25:   */   public static final int GT = 21;
/* 26:   */   public static final int LSQUARE = 22;
/* 27:   */   public static final int RSQUARE = 23;
/* 28:   */   public static final int STRING = 24;
/* 29:   */   public static final int RROUND = 25;
/* 30:   */   public static final int URL = 26;
/* 31:   */   public static final int CDO = 27;
/* 32:   */   public static final int CDC = 28;
/* 33:   */   public static final int IMPORT_SYM = 29;
/* 34:   */   public static final int IMPORTANT_SYM = 30;
/* 35:   */   public static final int ATKEYWORD = 31;
/* 36:   */   public static final int EMS = 32;
/* 37:   */   public static final int EXS = 33;
/* 38:   */   public static final int LENGTH_PX = 34;
/* 39:   */   public static final int LENGTH_CM = 35;
/* 40:   */   public static final int LENGTH_MM = 36;
/* 41:   */   public static final int LENGTH_IN = 37;
/* 42:   */   public static final int LENGTH_PT = 38;
/* 43:   */   public static final int LENGTH_PC = 39;
/* 44:   */   public static final int PERCENTAGE = 40;
/* 45:   */   public static final int NUMBER = 41;
/* 46:   */   public static final int RGB = 42;
/* 47:   */   public static final int NAME = 43;
/* 48:   */   public static final int D = 44;
/* 49:   */   public static final int NUM = 45;
/* 50:   */   public static final int UNICODERANGE = 46;
/* 51:   */   public static final int RANGE = 47;
/* 52:   */   public static final int Q16 = 48;
/* 53:   */   public static final int Q15 = 49;
/* 54:   */   public static final int Q14 = 50;
/* 55:   */   public static final int Q13 = 51;
/* 56:   */   public static final int Q12 = 52;
/* 57:   */   public static final int Q11 = 53;
/* 58:   */   public static final int NMSTART = 54;
/* 59:   */   public static final int NMCHAR = 55;
/* 60:   */   public static final int STRING1 = 56;
/* 61:   */   public static final int STRING2 = 57;
/* 62:   */   public static final int NONASCII = 58;
/* 63:   */   public static final int ESCAPE = 59;
/* 64:   */   public static final int NL = 60;
/* 65:   */   public static final int UNICODE = 61;
/* 66:   */   public static final int HNUM = 62;
/* 67:   */   public static final int H = 63;
/* 68:   */   public static final int UNKNOWN = 66;
/* 69:   */   public static final int DEFAULT = 0;
/* 70:   */   public static final int COMMENT = 1;
/* 71:74 */   public static final String[] tokenImage = { "<EOF>", "<S>", "\"/*\"", "<IDENT>", "\":link\"", "\":visited\"", "\":active\"", "\":first-line\"", "\":first-letter\"", "<HASH>", "\"{\"", "\"}\"", "\",\"", "\".\"", "\";\"", "\":\"", "\"*\"", "\"/\"", "\"+\"", "\"-\"", "\"=\"", "\">\"", "\"[\"", "\"]\"", "<STRING>", "\")\"", "<URL>", "\"<!--\"", "\"-->\"", "\"@import\"", "<IMPORTANT_SYM>", "<ATKEYWORD>", "<EMS>", "<EXS>", "<LENGTH_PX>", "<LENGTH_CM>", "<LENGTH_MM>", "<LENGTH_IN>", "<LENGTH_PT>", "<LENGTH_PC>", "<PERCENTAGE>", "<NUMBER>", "\"rgb(\"", "<NAME>", "<D>", "<NUM>", "<UNICODERANGE>", "<RANGE>", "<Q16>", "<Q15>", "<Q14>", "<Q13>", "<Q12>", "\"?\"", "<NMSTART>", "<NMCHAR>", "<STRING1>", "<STRING2>", "<NONASCII>", "<ESCAPE>", "<NL>", "<UNICODE>", "<HNUM>", "<H>", "\"*/\"", "<token of kind 65>", "<UNKNOWN>" };
/* 72:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.SACParserCSS1Constants
 * JD-Core Version:    0.7.0.1
 */