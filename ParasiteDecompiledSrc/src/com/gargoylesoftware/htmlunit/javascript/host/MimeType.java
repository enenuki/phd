/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  4:   */ 
/*  5:   */ public final class MimeType
/*  6:   */   extends SimpleScriptable
/*  7:   */ {
/*  8:   */   private String description_;
/*  9:   */   private String suffixes_;
/* 10:   */   private String type_;
/* 11:   */   private Plugin enabledPlugin_;
/* 12:   */   
/* 13:   */   public MimeType() {}
/* 14:   */   
/* 15:   */   public MimeType(String type, String description, String suffixes, Plugin plugin)
/* 16:   */   {
/* 17:48 */     this.type_ = type;
/* 18:49 */     this.description_ = description;
/* 19:50 */     this.suffixes_ = suffixes;
/* 20:51 */     this.enabledPlugin_ = plugin;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String jsxGet_description()
/* 24:   */   {
/* 25:59 */     return this.description_;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String jsxGet_suffixes()
/* 29:   */   {
/* 30:67 */     return this.suffixes_;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String jsxGet_type()
/* 34:   */   {
/* 35:75 */     return this.type_;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Object jsxGet_enabledPlugin()
/* 39:   */   {
/* 40:83 */     return this.enabledPlugin_;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.MimeType
 * JD-Core Version:    0.7.0.1
 */