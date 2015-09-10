/*  1:   */ package javassist.compiler;
/*  2:   */ 
/*  3:   */ public abstract interface TokenId
/*  4:   */ {
/*  5:   */   public static final int ABSTRACT = 300;
/*  6:   */   public static final int BOOLEAN = 301;
/*  7:   */   public static final int BREAK = 302;
/*  8:   */   public static final int BYTE = 303;
/*  9:   */   public static final int CASE = 304;
/* 10:   */   public static final int CATCH = 305;
/* 11:   */   public static final int CHAR = 306;
/* 12:   */   public static final int CLASS = 307;
/* 13:   */   public static final int CONST = 308;
/* 14:   */   public static final int CONTINUE = 309;
/* 15:   */   public static final int DEFAULT = 310;
/* 16:   */   public static final int DO = 311;
/* 17:   */   public static final int DOUBLE = 312;
/* 18:   */   public static final int ELSE = 313;
/* 19:   */   public static final int EXTENDS = 314;
/* 20:   */   public static final int FINAL = 315;
/* 21:   */   public static final int FINALLY = 316;
/* 22:   */   public static final int FLOAT = 317;
/* 23:   */   public static final int FOR = 318;
/* 24:   */   public static final int GOTO = 319;
/* 25:   */   public static final int IF = 320;
/* 26:   */   public static final int IMPLEMENTS = 321;
/* 27:   */   public static final int IMPORT = 322;
/* 28:   */   public static final int INSTANCEOF = 323;
/* 29:   */   public static final int INT = 324;
/* 30:   */   public static final int INTERFACE = 325;
/* 31:   */   public static final int LONG = 326;
/* 32:   */   public static final int NATIVE = 327;
/* 33:   */   public static final int NEW = 328;
/* 34:   */   public static final int PACKAGE = 329;
/* 35:   */   public static final int PRIVATE = 330;
/* 36:   */   public static final int PROTECTED = 331;
/* 37:   */   public static final int PUBLIC = 332;
/* 38:   */   public static final int RETURN = 333;
/* 39:   */   public static final int SHORT = 334;
/* 40:   */   public static final int STATIC = 335;
/* 41:   */   public static final int SUPER = 336;
/* 42:   */   public static final int SWITCH = 337;
/* 43:   */   public static final int SYNCHRONIZED = 338;
/* 44:   */   public static final int THIS = 339;
/* 45:   */   public static final int THROW = 340;
/* 46:   */   public static final int THROWS = 341;
/* 47:   */   public static final int TRANSIENT = 342;
/* 48:   */   public static final int TRY = 343;
/* 49:   */   public static final int VOID = 344;
/* 50:   */   public static final int VOLATILE = 345;
/* 51:   */   public static final int WHILE = 346;
/* 52:   */   public static final int STRICT = 347;
/* 53:   */   public static final int NEQ = 350;
/* 54:   */   public static final int MOD_E = 351;
/* 55:   */   public static final int AND_E = 352;
/* 56:   */   public static final int MUL_E = 353;
/* 57:   */   public static final int PLUS_E = 354;
/* 58:   */   public static final int MINUS_E = 355;
/* 59:   */   public static final int DIV_E = 356;
/* 60:   */   public static final int LE = 357;
/* 61:   */   public static final int EQ = 358;
/* 62:   */   public static final int GE = 359;
/* 63:   */   public static final int EXOR_E = 360;
/* 64:   */   public static final int OR_E = 361;
/* 65:   */   public static final int PLUSPLUS = 362;
/* 66:   */   public static final int MINUSMINUS = 363;
/* 67:   */   public static final int LSHIFT = 364;
/* 68:   */   public static final int LSHIFT_E = 365;
/* 69:   */   public static final int RSHIFT = 366;
/* 70:   */   public static final int RSHIFT_E = 367;
/* 71:   */   public static final int OROR = 368;
/* 72:   */   public static final int ANDAND = 369;
/* 73:   */   public static final int ARSHIFT = 370;
/* 74:   */   public static final int ARSHIFT_E = 371;
/* 75:92 */   public static final String[] opNames = { "!=", "%=", "&=", "*=", "+=", "-=", "/=", "<=", "==", ">=", "^=", "|=", "++", "--", "<<", "<<=", ">>", ">>=", "||", "&&", ">>>", ">>>=" };
/* 76:98 */   public static final int[] assignOps = { 37, 38, 42, 43, 45, 47, 0, 0, 0, 94, 124, 0, 0, 0, 364, 0, 366, 0, 0, 0, 370 };
/* 77:   */   public static final int Identifier = 400;
/* 78:   */   public static final int CharConstant = 401;
/* 79:   */   public static final int IntConstant = 402;
/* 80:   */   public static final int LongConstant = 403;
/* 81:   */   public static final int FloatConstant = 404;
/* 82:   */   public static final int DoubleConstant = 405;
/* 83:   */   public static final int StringL = 406;
/* 84:   */   public static final int TRUE = 410;
/* 85:   */   public static final int FALSE = 411;
/* 86:   */   public static final int NULL = 412;
/* 87:   */   public static final int CALL = 67;
/* 88:   */   public static final int ARRAY = 65;
/* 89:   */   public static final int MEMBER = 35;
/* 90:   */   public static final int EXPR = 69;
/* 91:   */   public static final int LABEL = 76;
/* 92:   */   public static final int BLOCK = 66;
/* 93:   */   public static final int DECL = 68;
/* 94:   */   public static final int BadToken = 500;
/* 95:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.TokenId
 * JD-Core Version:    0.7.0.1
 */