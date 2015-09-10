/*  1:   */ package org.apache.james.mime4j.parser;
/*  2:   */ 
/*  3:   */ public final class Event
/*  4:   */ {
/*  5:28 */   public static final Event MIME_BODY_PREMATURE_END = new Event("Body part ended prematurely. Boundary detected in header or EOF reached.");
/*  6:32 */   public static final Event HEADERS_PREMATURE_END = new Event("Unexpected end of headers detected. Higher level boundary detected or EOF reached.");
/*  7:36 */   public static final Event INALID_HEADER = new Event("Invalid header encountered");
/*  8:   */   private final String code;
/*  9:   */   
/* 10:   */   public Event(String code)
/* 11:   */   {
/* 12:43 */     if (code == null) {
/* 13:44 */       throw new IllegalArgumentException("Code may not be null");
/* 14:   */     }
/* 15:46 */     this.code = code;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int hashCode()
/* 19:   */   {
/* 20:51 */     return this.code.hashCode();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean equals(Object obj)
/* 24:   */   {
/* 25:56 */     if (obj == null) {
/* 26:56 */       return false;
/* 27:   */     }
/* 28:57 */     if (this == obj) {
/* 29:57 */       return true;
/* 30:   */     }
/* 31:58 */     if ((obj instanceof Event))
/* 32:   */     {
/* 33:59 */       Event that = (Event)obj;
/* 34:60 */       return this.code.equals(that.code);
/* 35:   */     }
/* 36:62 */     return false;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String toString()
/* 40:   */   {
/* 41:68 */     return this.code;
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.parser.Event
 * JD-Core Version:    0.7.0.1
 */