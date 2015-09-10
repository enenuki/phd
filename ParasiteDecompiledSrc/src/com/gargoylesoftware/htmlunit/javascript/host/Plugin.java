/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ public class Plugin
/*  4:   */   extends SimpleArray
/*  5:   */ {
/*  6:   */   private String description_;
/*  7:   */   private String filename_;
/*  8:   */   private String name_;
/*  9:   */   
/* 10:   */   public Plugin() {}
/* 11:   */   
/* 12:   */   public Plugin(String name, String description, String filename)
/* 13:   */   {
/* 14:43 */     this.name_ = name;
/* 15:44 */     this.description_ = description;
/* 16:45 */     this.filename_ = filename;
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected String getItemName(Object element)
/* 20:   */   {
/* 21:55 */     return ((MimeType)element).jsxGet_type();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String jsxGet_description()
/* 25:   */   {
/* 26:63 */     return this.description_;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String jsxGet_filename()
/* 30:   */   {
/* 31:71 */     return this.filename_;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String jsxGet_name()
/* 35:   */   {
/* 36:79 */     return this.name_;
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Plugin
 * JD-Core Version:    0.7.0.1
 */