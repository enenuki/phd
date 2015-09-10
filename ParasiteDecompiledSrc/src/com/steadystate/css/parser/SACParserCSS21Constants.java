/*  1:   */ package com.steadystate.css.parser;
/*  2:   */ 
/*  3:   */ public abstract interface SACParserCSS21Constants
/*  4:   */ {
/*  5:   */   public static final int EOF = 0;
/*  6:   */   public static final int S = 1;
/*  7:   */   public static final int W = 2;
/*  8:   */   public static final int LBRACE = 6;
/*  9:   */   public static final int RBRACE = 7;
/* 10:   */   public static final int COMMA = 8;
/* 11:   */   public static final int DOT = 9;
/* 12:   */   public static final int SEMICOLON = 10;
/* 13:   */   public static final int COLON = 11;
/* 14:   */   public static final int ASTERISK = 12;
/* 15:   */   public static final int SLASH = 13;
/* 16:   */   public static final int PLUS = 14;
/* 17:   */   public static final int MINUS = 15;
/* 18:   */   public static final int EQUALS = 16;
/* 19:   */   public static final int LSQUARE = 17;
/* 20:   */   public static final int RSQUARE = 18;
/* 21:   */   public static final int GREATER = 19;
/* 22:   */   public static final int HASH = 20;
/* 23:   */   public static final int STRING = 21;
/* 24:   */   public static final int RROUND = 22;
/* 25:   */   public static final int URL = 23;
/* 26:   */   public static final int URI = 24;
/* 27:   */   public static final int CDO = 25;
/* 28:   */   public static final int CDC = 26;
/* 29:   */   public static final int INCLUDES = 27;
/* 30:   */   public static final int DASHMATCH = 28;
/* 31:   */   public static final int IMPORT_SYM = 29;
/* 32:   */   public static final int PAGE_SYM = 30;
/* 33:   */   public static final int MEDIA_SYM = 31;
/* 34:   */   public static final int CHARSET_SYM = 32;
/* 35:   */   public static final int ATKEYWORD = 33;
/* 36:   */   public static final int IMPORTANT_KEYWORD = 34;
/* 37:   */   public static final int IMPORTANT_SYM = 35;
/* 38:   */   public static final int INHERIT = 36;
/* 39:   */   public static final int EMS = 37;
/* 40:   */   public static final int EXS = 38;
/* 41:   */   public static final int LENGTH_PX = 39;
/* 42:   */   public static final int LENGTH_CM = 40;
/* 43:   */   public static final int LENGTH_MM = 41;
/* 44:   */   public static final int LENGTH_IN = 42;
/* 45:   */   public static final int LENGTH_PT = 43;
/* 46:   */   public static final int LENGTH_PC = 44;
/* 47:   */   public static final int ANGLE_DEG = 45;
/* 48:   */   public static final int ANGLE_RAD = 46;
/* 49:   */   public static final int ANGLE_GRAD = 47;
/* 50:   */   public static final int TIME_MS = 48;
/* 51:   */   public static final int TIME_S = 49;
/* 52:   */   public static final int FREQ_HZ = 50;
/* 53:   */   public static final int FREQ_KHZ = 51;
/* 54:   */   public static final int DIMENSION = 52;
/* 55:   */   public static final int PERCENTAGE = 53;
/* 56:   */   public static final int NUMBER = 54;
/* 57:   */   public static final int FUNCTION = 55;
/* 58:   */   public static final int IDENT = 56;
/* 59:   */   public static final int NAME = 57;
/* 60:   */   public static final int NUM = 58;
/* 61:   */   public static final int Q16 = 59;
/* 62:   */   public static final int Q15 = 60;
/* 63:   */   public static final int Q14 = 61;
/* 64:   */   public static final int Q13 = 62;
/* 65:   */   public static final int Q12 = 63;
/* 66:   */   public static final int Q11 = 64;
/* 67:   */   public static final int NMSTART = 65;
/* 68:   */   public static final int NMCHAR = 66;
/* 69:   */   public static final int STRING1 = 67;
/* 70:   */   public static final int STRING2 = 68;
/* 71:   */   public static final int NONASCII = 69;
/* 72:   */   public static final int ESCAPE = 70;
/* 73:   */   public static final int NL = 71;
/* 74:   */   public static final int A_LETTER = 72;
/* 75:   */   public static final int C_LETTER = 73;
/* 76:   */   public static final int D_LETTER = 74;
/* 77:   */   public static final int E_LETTER = 75;
/* 78:   */   public static final int G_LETTER = 76;
/* 79:   */   public static final int H_LETTER = 77;
/* 80:   */   public static final int I_LETTER = 78;
/* 81:   */   public static final int K_LETTER = 79;
/* 82:   */   public static final int M_LETTER = 80;
/* 83:   */   public static final int N_LETTER = 81;
/* 84:   */   public static final int P_LETTER = 82;
/* 85:   */   public static final int R_LETTER = 83;
/* 86:   */   public static final int S_LETTER = 84;
/* 87:   */   public static final int T_LETTER = 85;
/* 88:   */   public static final int X_LETTER = 86;
/* 89:   */   public static final int Z_LETTER = 87;
/* 90:   */   public static final int UNICODE = 88;
/* 91:   */   public static final int HNUM = 89;
/* 92:   */   public static final int H = 90;
/* 93:   */   public static final int UNKNOWN = 91;
/* 94:   */   public static final int DEFAULT = 0;
/* 95:   */   public static final int COMMENT = 1;
/* 96:99 */   public static final String[] tokenImage = { "<EOF>", "<S>", "<W>", "\"/*\"", "\"*/\"", "<token of kind 5>", "<LBRACE>", "\"}\"", "<COMMA>", "\".\"", "\";\"", "\":\"", "\"*\"", "\"/\"", "<PLUS>", "\"-\"", "\"=\"", "\"[\"", "\"]\"", "<GREATER>", "<HASH>", "<STRING>", "\")\"", "<URL>", "<URI>", "\"<!--\"", "\"-->\"", "\"~=\"", "\"|=\"", "\"@import\"", "\"@page\"", "\"@media\"", "\"@charset \"", "<ATKEYWORD>", "\"important\"", "\"!\"", "\"inherit\"", "<EMS>", "<EXS>", "<LENGTH_PX>", "<LENGTH_CM>", "<LENGTH_MM>", "<LENGTH_IN>", "<LENGTH_PT>", "<LENGTH_PC>", "<ANGLE_DEG>", "<ANGLE_RAD>", "<ANGLE_GRAD>", "<TIME_MS>", "<TIME_S>", "<FREQ_HZ>", "<FREQ_KHZ>", "<DIMENSION>", "<PERCENTAGE>", "<NUMBER>", "<FUNCTION>", "<IDENT>", "<NAME>", "<NUM>", "<Q16>", "<Q15>", "<Q14>", "<Q13>", "<Q12>", "\"?\"", "<NMSTART>", "<NMCHAR>", "<STRING1>", "<STRING2>", "<NONASCII>", "<ESCAPE>", "<NL>", "<A_LETTER>", "<C_LETTER>", "<D_LETTER>", "<E_LETTER>", "<G_LETTER>", "<H_LETTER>", "<I_LETTER>", "<K_LETTER>", "<M_LETTER>", "<N_LETTER>", "<P_LETTER>", "<R_LETTER>", "<S_LETTER>", "<T_LETTER>", "<X_LETTER>", "<Z_LETTER>", "<UNICODE>", "<HNUM>", "<H>", "<UNKNOWN>" };
/* 97:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.SACParserCSS21Constants
 * JD-Core Version:    0.7.0.1
 */