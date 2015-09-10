/*  1:   */ package org.apache.james.mime4j.message;
/*  2:   */ 
/*  3:   */ public class BodyCopier
/*  4:   */ {
/*  5:   */   public static Body copy(Body body)
/*  6:   */   {
/*  7:53 */     if (body == null) {
/*  8:54 */       throw new IllegalArgumentException("Body is null");
/*  9:   */     }
/* 10:56 */     if ((body instanceof Message)) {
/* 11:57 */       return new Message((Message)body);
/* 12:   */     }
/* 13:59 */     if ((body instanceof Multipart)) {
/* 14:60 */       return new Multipart((Multipart)body);
/* 15:   */     }
/* 16:62 */     if ((body instanceof SingleBody)) {
/* 17:63 */       return ((SingleBody)body).copy();
/* 18:   */     }
/* 19:65 */     throw new IllegalArgumentException("Unsupported body class");
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.BodyCopier
 * JD-Core Version:    0.7.0.1
 */