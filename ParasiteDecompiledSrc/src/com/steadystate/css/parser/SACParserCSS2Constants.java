/*  1:   */ package com.steadystate.css.parser;
/*  2:   */ 
/*  3:   */ public abstract interface SACParserCSS2Constants
/*  4:   */ {
/*  5:   */   public static final int EOF = 0;
/*  6:   */   public static final int S = 1;
/*  7:   */   public static final int LBRACE = 5;
/*  8:   */   public static final int RBRACE = 6;
/*  9:   */   public static final int COMMA = 7;
/* 10:   */   public static final int DOT = 8;
/* 11:   */   public static final int SEMICOLON = 9;
/* 12:   */   public static final int COLON = 10;
/* 13:   */   public static final int ASTERISK = 11;
/* 14:   */   public static final int SLASH = 12;
/* 15:   */   public static final int PLUS = 13;
/* 16:   */   public static final int MINUS = 14;
/* 17:   */   public static final int EQUALS = 15;
/* 18:   */   public static final int GT = 16;
/* 19:   */   public static final int LSQUARE = 17;
/* 20:   */   public static final int RSQUARE = 18;
/* 21:   */   public static final int HASH = 19;
/* 22:   */   public static final int STRING = 20;
/* 23:   */   public static final int RROUND = 21;
/* 24:   */   public static final int URL = 22;
/* 25:   */   public static final int URI = 23;
/* 26:   */   public static final int CDO = 24;
/* 27:   */   public static final int CDC = 25;
/* 28:   */   public static final int INCLUDES = 26;
/* 29:   */   public static final int DASHMATCH = 27;
/* 30:   */   public static final int IMPORT_SYM = 28;
/* 31:   */   public static final int PAGE_SYM = 29;
/* 32:   */   public static final int MEDIA_SYM = 30;
/* 33:   */   public static final int FONT_FACE_SYM = 31;
/* 34:   */   public static final int CHARSET_SYM = 32;
/* 35:   */   public static final int ATKEYWORD = 33;
/* 36:   */   public static final int IMPORTANT_SYM = 34;
/* 37:   */   public static final int INHERIT = 35;
/* 38:   */   public static final int EMS = 36;
/* 39:   */   public static final int EXS = 37;
/* 40:   */   public static final int LENGTH_PX = 38;
/* 41:   */   public static final int LENGTH_CM = 39;
/* 42:   */   public static final int LENGTH_MM = 40;
/* 43:   */   public static final int LENGTH_IN = 41;
/* 44:   */   public static final int LENGTH_PT = 42;
/* 45:   */   public static final int LENGTH_PC = 43;
/* 46:   */   public static final int ANGLE_DEG = 44;
/* 47:   */   public static final int ANGLE_RAD = 45;
/* 48:   */   public static final int ANGLE_GRAD = 46;
/* 49:   */   public static final int TIME_MS = 47;
/* 50:   */   public static final int TIME_S = 48;
/* 51:   */   public static final int FREQ_HZ = 49;
/* 52:   */   public static final int FREQ_KHZ = 50;
/* 53:   */   public static final int DIMEN = 51;
/* 54:   */   public static final int PERCENTAGE = 52;
/* 55:   */   public static final int NUMBER = 53;
/* 56:   */   public static final int RGB = 54;
/* 57:   */   public static final int FUNCTION = 55;
/* 58:   */   public static final int IDENT = 56;
/* 59:   */   public static final int NAME = 57;
/* 60:   */   public static final int NUM = 58;
/* 61:   */   public static final int UNICODERANGE = 59;
/* 62:   */   public static final int RANGE = 60;
/* 63:   */   public static final int Q16 = 61;
/* 64:   */   public static final int Q15 = 62;
/* 65:   */   public static final int Q14 = 63;
/* 66:   */   public static final int Q13 = 64;
/* 67:   */   public static final int Q12 = 65;
/* 68:   */   public static final int Q11 = 66;
/* 69:   */   public static final int NMSTART = 67;
/* 70:   */   public static final int NMCHAR = 68;
/* 71:   */   public static final int STRING1 = 69;
/* 72:   */   public static final int STRING2 = 70;
/* 73:   */   public static final int NONASCII = 71;
/* 74:   */   public static final int ESCAPE = 72;
/* 75:   */   public static final int NL = 73;
/* 76:   */   public static final int UNICODE = 74;
/* 77:   */   public static final int HNUM = 75;
/* 78:   */   public static final int H = 76;
/* 79:   */   public static final int UNKNOWN = 77;
/* 80:   */   public static final int DEFAULT = 0;
/* 81:   */   public static final int COMMENT = 1;
/* 82:85 */   public static final String[] tokenImage = { "<EOF>", "<S>", "\"/*\"", "\"*/\"", "<token of kind 4>", "\"{\"", "\"}\"", "\",\"", "\".\"", "\";\"", "\":\"", "\"*\"", "\"/\"", "\"+\"", "\"-\"", "\"=\"", "\">\"", "\"[\"", "\"]\"", "<HASH>", "<STRING>", "\")\"", "<URL>", "<URI>", "\"<!--\"", "\"-->\"", "\"~=\"", "\"|=\"", "\"@import\"", "\"@page\"", "\"@media\"", "\"@font-face\"", "\"@charset\"", "<ATKEYWORD>", "<IMPORTANT_SYM>", "\"inherit\"", "<EMS>", "<EXS>", "<LENGTH_PX>", "<LENGTH_CM>", "<LENGTH_MM>", "<LENGTH_IN>", "<LENGTH_PT>", "<LENGTH_PC>", "<ANGLE_DEG>", "<ANGLE_RAD>", "<ANGLE_GRAD>", "<TIME_MS>", "<TIME_S>", "<FREQ_HZ>", "<FREQ_KHZ>", "<DIMEN>", "<PERCENTAGE>", "<NUMBER>", "\"rgb(\"", "<FUNCTION>", "<IDENT>", "<NAME>", "<NUM>", "<UNICODERANGE>", "<RANGE>", "<Q16>", "<Q15>", "<Q14>", "<Q13>", "<Q12>", "\"?\"", "<NMSTART>", "<NMCHAR>", "<STRING1>", "<STRING2>", "<NONASCII>", "<ESCAPE>", "<NL>", "<UNICODE>", "<HNUM>", "<H>", "<UNKNOWN>" };
/* 83:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.SACParserCSS2Constants
 * JD-Core Version:    0.7.0.1
 */