/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.xml;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.javascript.host.Attr;
/*  4:   */ import com.gargoylesoftware.htmlunit.util.StringUtils;
/*  5:   */ 
/*  6:   */ public class XMLAttr
/*  7:   */   extends Attr
/*  8:   */ {
/*  9:   */   public String jsxGet_text()
/* 10:   */   {
/* 11:40 */     return jsxGet_value();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void jsxSet_text(String value)
/* 15:   */   {
/* 16:48 */     jsxSet_value(value);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String jsxGet_xml()
/* 20:   */   {
/* 21:56 */     StringBuilder sb = new StringBuilder();
/* 22:57 */     sb.append(jsxGet_name());
/* 23:58 */     sb.append('=');
/* 24:59 */     sb.append('"');
/* 25:60 */     sb.append(StringUtils.escapeXmlAttributeValue(jsxGet_value()));
/* 26:61 */     sb.append('"');
/* 27:62 */     return sb.toString();
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.xml.XMLAttr
 * JD-Core Version:    0.7.0.1
 */