/*  1:   */ package org.apache.http.client.entity;
/*  2:   */ 
/*  3:   */ import java.io.UnsupportedEncodingException;
/*  4:   */ import java.util.List;
/*  5:   */ import org.apache.http.NameValuePair;
/*  6:   */ import org.apache.http.annotation.NotThreadSafe;
/*  7:   */ import org.apache.http.client.utils.URLEncodedUtils;
/*  8:   */ import org.apache.http.entity.StringEntity;
/*  9:   */ 
/* 10:   */ @NotThreadSafe
/* 11:   */ public class UrlEncodedFormEntity
/* 12:   */   extends StringEntity
/* 13:   */ {
/* 14:   */   public UrlEncodedFormEntity(List<? extends NameValuePair> parameters, String encoding)
/* 15:   */     throws UnsupportedEncodingException
/* 16:   */   {
/* 17:59 */     super(URLEncodedUtils.format(parameters, encoding), encoding);
/* 18:60 */     setContentType("application/x-www-form-urlencoded; charset=" + (encoding != null ? encoding : "ISO-8859-1"));
/* 19:   */   }
/* 20:   */   
/* 21:   */   public UrlEncodedFormEntity(List<? extends NameValuePair> parameters)
/* 22:   */     throws UnsupportedEncodingException
/* 23:   */   {
/* 24:73 */     this(parameters, "ISO-8859-1");
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.entity.UrlEncodedFormEntity
 * JD-Core Version:    0.7.0.1
 */