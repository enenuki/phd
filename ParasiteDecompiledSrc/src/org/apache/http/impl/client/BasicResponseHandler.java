/*  1:   */ package org.apache.http.impl.client;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.apache.http.HttpEntity;
/*  5:   */ import org.apache.http.HttpResponse;
/*  6:   */ import org.apache.http.StatusLine;
/*  7:   */ import org.apache.http.annotation.Immutable;
/*  8:   */ import org.apache.http.client.HttpResponseException;
/*  9:   */ import org.apache.http.client.ResponseHandler;
/* 10:   */ import org.apache.http.util.EntityUtils;
/* 11:   */ 
/* 12:   */ @Immutable
/* 13:   */ public class BasicResponseHandler
/* 14:   */   implements ResponseHandler<String>
/* 15:   */ {
/* 16:   */   public String handleResponse(HttpResponse response)
/* 17:   */     throws HttpResponseException, IOException
/* 18:   */   {
/* 19:65 */     StatusLine statusLine = response.getStatusLine();
/* 20:66 */     if (statusLine.getStatusCode() >= 300) {
/* 21:67 */       throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
/* 22:   */     }
/* 23:71 */     HttpEntity entity = response.getEntity();
/* 24:72 */     return entity == null ? null : EntityUtils.toString(entity);
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.BasicResponseHandler
 * JD-Core Version:    0.7.0.1
 */