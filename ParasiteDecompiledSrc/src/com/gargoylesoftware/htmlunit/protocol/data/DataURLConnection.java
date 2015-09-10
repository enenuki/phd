/*  1:   */ package com.gargoylesoftware.htmlunit.protocol.data;
/*  2:   */ 
/*  3:   */ import java.io.ByteArrayInputStream;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.io.UnsupportedEncodingException;
/*  6:   */ import java.net.URL;
/*  7:   */ import java.net.URLConnection;
/*  8:   */ import org.apache.commons.codec.DecoderException;
/*  9:   */ import org.apache.commons.logging.Log;
/* 10:   */ import org.apache.commons.logging.LogFactory;
/* 11:   */ 
/* 12:   */ public class DataURLConnection
/* 13:   */   extends URLConnection
/* 14:   */ {
/* 15:36 */   private static final Log LOG = LogFactory.getLog(DataURLConnection.class);
/* 16:   */   public static final String DATA_PREFIX = "data:";
/* 17:   */   private final byte[] content_;
/* 18:   */   
/* 19:   */   public DataURLConnection(URL url)
/* 20:   */   {
/* 21:49 */     super(url);
/* 22:   */     
/* 23:51 */     byte[] data = null;
/* 24:   */     try
/* 25:   */     {
/* 26:53 */       data = DataUrlDecoder.decode(url).getBytes();
/* 27:   */     }
/* 28:   */     catch (UnsupportedEncodingException e)
/* 29:   */     {
/* 30:56 */       LOG.error("Exception decoding " + url, e);
/* 31:   */     }
/* 32:   */     catch (DecoderException e)
/* 33:   */     {
/* 34:59 */       LOG.error("Exception decoding " + url, e);
/* 35:   */     }
/* 36:61 */     this.content_ = data;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void connect() {}
/* 40:   */   
/* 41:   */   public InputStream getInputStream()
/* 42:   */   {
/* 43:78 */     return new ByteArrayInputStream(this.content_);
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.protocol.data.DataURLConnection
 * JD-Core Version:    0.7.0.1
 */