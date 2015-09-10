/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   5:    */ 
/*   6:    */ public class DOMException
/*   7:    */   extends SimpleScriptable
/*   8:    */ {
/*   9:    */   public static final short DOMSTRING_SIZE_ERR = 2;
/*  10:    */   public static final short HIERARCHY_REQUEST_ERR = 3;
/*  11:    */   public static final short INDEX_SIZE_ERR = 1;
/*  12:    */   public static final short INUSE_ATTRIBUTE_ERR = 10;
/*  13:    */   public static final short INVALID_ACCESS_ERR = 15;
/*  14:    */   public static final short INVALID_CHARACTER_ERR = 5;
/*  15:    */   public static final short INVALID_MODIFICATION_ERR = 13;
/*  16:    */   public static final short INVALID_STATE_ERR = 11;
/*  17:    */   public static final short NAMESPACE_ERR = 14;
/*  18:    */   public static final short NO_DATA_ALLOWED_ERR = 6;
/*  19:    */   public static final short NO_MODIFICATION_ALLOWED_ERR = 7;
/*  20:    */   public static final short NOT_FOUND_ERR = 8;
/*  21:    */   public static final short NOT_SUPPORTED_ERR = 9;
/*  22:    */   public static final short SYNTAX_ERR = 12;
/*  23:    */   public static final short WRONG_DOCUMENT_ERR = 4;
/*  24:    */   private final short code_;
/*  25:    */   private final String message_;
/*  26:    */   private int lineNumber_;
/*  27:    */   private String fileName_;
/*  28:    */   
/*  29:    */   public DOMException()
/*  30:    */   {
/*  31: 70 */     this.code_ = -1;
/*  32: 71 */     this.message_ = null;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public DOMException(String message, short errorCode)
/*  36:    */   {
/*  37: 80 */     this.code_ = errorCode;
/*  38: 81 */     this.message_ = message;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Object jsxGet_code()
/*  42:    */   {
/*  43: 89 */     if (this.code_ == -1) {
/*  44: 90 */       return Context.getUndefinedValue();
/*  45:    */     }
/*  46: 92 */     return Short.valueOf(this.code_);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Object jsxGet_message()
/*  50:    */   {
/*  51:100 */     if (this.message_ == null) {
/*  52:101 */       return Context.getUndefinedValue();
/*  53:    */     }
/*  54:103 */     return this.message_;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object jsxGet_lineNumber()
/*  58:    */   {
/*  59:111 */     if (this.lineNumber_ == -1) {
/*  60:112 */       return Context.getUndefinedValue();
/*  61:    */     }
/*  62:114 */     return Integer.valueOf(this.lineNumber_);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Object jsxGet_filename()
/*  66:    */   {
/*  67:122 */     if (this.fileName_ == null) {
/*  68:123 */       return Context.getUndefinedValue();
/*  69:    */     }
/*  70:125 */     return this.fileName_;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setLocation(String fileName, int lineNumber)
/*  74:    */   {
/*  75:134 */     this.fileName_ = fileName;
/*  76:135 */     this.lineNumber_ = lineNumber;
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.DOMException
 * JD-Core Version:    0.7.0.1
 */