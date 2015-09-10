/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public final class FormEncodingType
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:29 */   public static final FormEncodingType URL_ENCODED = new FormEncodingType("application/x-www-form-urlencoded");
/*  9:32 */   public static final FormEncodingType MULTIPART = new FormEncodingType("multipart/form-data");
/* 10:   */   private final String name_;
/* 11:   */   
/* 12:   */   private FormEncodingType(String name)
/* 13:   */   {
/* 14:37 */     this.name_ = name;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getName()
/* 18:   */   {
/* 19:46 */     return this.name_;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static FormEncodingType getInstance(String name)
/* 23:   */   {
/* 24:56 */     String lowerCaseName = name.toLowerCase();
/* 25:58 */     if (MULTIPART.getName().equals(lowerCaseName)) {
/* 26:59 */       return MULTIPART;
/* 27:   */     }
/* 28:62 */     return URL_ENCODED;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String toString()
/* 32:   */   {
/* 33:71 */     return "EncodingType[name=" + getName() + "]";
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.FormEncodingType
 * JD-Core Version:    0.7.0.1
 */