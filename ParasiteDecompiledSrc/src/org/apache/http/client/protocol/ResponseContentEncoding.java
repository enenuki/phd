/*  1:   */ package org.apache.http.client.protocol;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.util.Locale;
/*  5:   */ import org.apache.http.Header;
/*  6:   */ import org.apache.http.HeaderElement;
/*  7:   */ import org.apache.http.HttpEntity;
/*  8:   */ import org.apache.http.HttpException;
/*  9:   */ import org.apache.http.HttpResponse;
/* 10:   */ import org.apache.http.HttpResponseInterceptor;
/* 11:   */ import org.apache.http.annotation.Immutable;
/* 12:   */ import org.apache.http.client.entity.DeflateDecompressingEntity;
/* 13:   */ import org.apache.http.client.entity.GzipDecompressingEntity;
/* 14:   */ import org.apache.http.protocol.HttpContext;
/* 15:   */ 
/* 16:   */ @Immutable
/* 17:   */ public class ResponseContentEncoding
/* 18:   */   implements HttpResponseInterceptor
/* 19:   */ {
/* 20:   */   public void process(HttpResponse response, HttpContext context)
/* 21:   */     throws HttpException, IOException
/* 22:   */   {
/* 23:72 */     HttpEntity entity = response.getEntity();
/* 24:75 */     if (entity != null)
/* 25:   */     {
/* 26:76 */       Header ceheader = entity.getContentEncoding();
/* 27:77 */       if (ceheader != null)
/* 28:   */       {
/* 29:78 */         HeaderElement[] codecs = ceheader.getElements();
/* 30:79 */         HeaderElement[] arr$ = codecs;int len$ = arr$.length;int i$ = 0;
/* 31:79 */         if (i$ < len$)
/* 32:   */         {
/* 33:79 */           HeaderElement codec = arr$[i$];
/* 34:80 */           String codecname = codec.getName().toLowerCase(Locale.US);
/* 35:81 */           if (("gzip".equals(codecname)) || ("x-gzip".equals(codecname)))
/* 36:   */           {
/* 37:82 */             response.setEntity(new GzipDecompressingEntity(response.getEntity()));
/* 38:83 */             return;
/* 39:   */           }
/* 40:84 */           if ("deflate".equals(codecname))
/* 41:   */           {
/* 42:85 */             response.setEntity(new DeflateDecompressingEntity(response.getEntity()));
/* 43:86 */             return;
/* 44:   */           }
/* 45:87 */           if ("identity".equals(codecname)) {
/* 46:90 */             return;
/* 47:   */           }
/* 48:92 */           throw new HttpException("Unsupported Content-Coding: " + codec.getName());
/* 49:   */         }
/* 50:   */       }
/* 51:   */     }
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.protocol.ResponseContentEncoding
 * JD-Core Version:    0.7.0.1
 */