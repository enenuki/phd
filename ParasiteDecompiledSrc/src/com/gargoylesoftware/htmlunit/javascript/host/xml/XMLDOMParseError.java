/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.xml;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   4:    */ 
/*   5:    */ public class XMLDOMParseError
/*   6:    */   extends SimpleScriptable
/*   7:    */ {
/*   8:    */   private int errorCode_;
/*   9:    */   private int filepos_;
/*  10:    */   private int line_;
/*  11:    */   private int linepos_;
/*  12: 32 */   private String reason_ = "";
/*  13: 33 */   private String srcText_ = "";
/*  14: 34 */   private String url_ = "";
/*  15:    */   
/*  16:    */   public int jsxGet_errorCode()
/*  17:    */   {
/*  18: 41 */     return this.errorCode_;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public int jsxGet_filepos()
/*  22:    */   {
/*  23: 49 */     return this.filepos_;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int jsxGet_line()
/*  27:    */   {
/*  28: 57 */     return this.line_;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int jsxGet_linepos()
/*  32:    */   {
/*  33: 65 */     return this.linepos_;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String jsxGet_reason()
/*  37:    */   {
/*  38: 73 */     return this.reason_;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String jsxGet_srcText()
/*  42:    */   {
/*  43: 81 */     return this.srcText_;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String jsxGet_url()
/*  47:    */   {
/*  48: 89 */     return this.url_;
/*  49:    */   }
/*  50:    */   
/*  51:    */   void setErrorCode(int errorCode)
/*  52:    */   {
/*  53: 93 */     this.errorCode_ = errorCode;
/*  54:    */   }
/*  55:    */   
/*  56:    */   void setFilepos(int filepos)
/*  57:    */   {
/*  58: 97 */     this.filepos_ = filepos;
/*  59:    */   }
/*  60:    */   
/*  61:    */   void setLine(int line)
/*  62:    */   {
/*  63:101 */     this.line_ = line;
/*  64:    */   }
/*  65:    */   
/*  66:    */   void setLinepos(int linepos)
/*  67:    */   {
/*  68:105 */     this.linepos_ = linepos;
/*  69:    */   }
/*  70:    */   
/*  71:    */   void setReason(String reason)
/*  72:    */   {
/*  73:109 */     this.reason_ = reason;
/*  74:    */   }
/*  75:    */   
/*  76:    */   void setSrcText(String srcText)
/*  77:    */   {
/*  78:113 */     this.srcText_ = srcText;
/*  79:    */   }
/*  80:    */   
/*  81:    */   void setUrl(String url)
/*  82:    */   {
/*  83:117 */     this.url_ = url;
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.xml.XMLDOMParseError
 * JD-Core Version:    0.7.0.1
 */