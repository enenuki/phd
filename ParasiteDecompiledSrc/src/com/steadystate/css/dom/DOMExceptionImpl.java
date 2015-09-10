/*  1:   */ package com.steadystate.css.dom;
/*  2:   */ 
/*  3:   */ import java.util.Locale;
/*  4:   */ import java.util.ResourceBundle;
/*  5:   */ import org.w3c.dom.DOMException;
/*  6:   */ 
/*  7:   */ public class DOMExceptionImpl
/*  8:   */   extends DOMException
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 7365733663951145145L;
/* 11:   */   public static final int SYNTAX_ERROR = 0;
/* 12:   */   public static final int ARRAY_OUT_OF_BOUNDS = 1;
/* 13:   */   public static final int READ_ONLY_STYLE_SHEET = 2;
/* 14:   */   public static final int EXPECTING_UNKNOWN_RULE = 3;
/* 15:   */   public static final int EXPECTING_STYLE_RULE = 4;
/* 16:   */   public static final int EXPECTING_CHARSET_RULE = 5;
/* 17:   */   public static final int EXPECTING_IMPORT_RULE = 6;
/* 18:   */   public static final int EXPECTING_MEDIA_RULE = 7;
/* 19:   */   public static final int EXPECTING_FONT_FACE_RULE = 8;
/* 20:   */   public static final int EXPECTING_PAGE_RULE = 9;
/* 21:   */   public static final int FLOAT_ERROR = 10;
/* 22:   */   public static final int STRING_ERROR = 11;
/* 23:   */   public static final int COUNTER_ERROR = 12;
/* 24:   */   public static final int RECT_ERROR = 13;
/* 25:   */   public static final int RGBCOLOR_ERROR = 14;
/* 26:   */   public static final int CHARSET_NOT_FIRST = 15;
/* 27:   */   public static final int CHARSET_NOT_UNIQUE = 16;
/* 28:   */   public static final int IMPORT_NOT_FIRST = 17;
/* 29:   */   public static final int NOT_FOUND = 18;
/* 30:   */   public static final int NOT_IMPLEMENTED = 19;
/* 31:66 */   private static ResourceBundle _exceptionResource = ResourceBundle.getBundle("com.steadystate.css.parser.ExceptionResource", Locale.getDefault());
/* 32:   */   
/* 33:   */   public DOMExceptionImpl(short code, int messageKey)
/* 34:   */   {
/* 35:72 */     super(code, _exceptionResource.getString(keyString(messageKey)));
/* 36:   */   }
/* 37:   */   
/* 38:   */   public DOMExceptionImpl(int code, int messageKey)
/* 39:   */   {
/* 40:76 */     super((short)code, _exceptionResource.getString(keyString(messageKey)));
/* 41:   */   }
/* 42:   */   
/* 43:   */   public DOMExceptionImpl(short code, int messageKey, String info)
/* 44:   */   {
/* 45:80 */     super(code, _exceptionResource.getString(keyString(messageKey)));
/* 46:   */   }
/* 47:   */   
/* 48:   */   private static String keyString(int key)
/* 49:   */   {
/* 50:84 */     return "s" + String.valueOf(key);
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.DOMExceptionImpl
 * JD-Core Version:    0.7.0.1
 */