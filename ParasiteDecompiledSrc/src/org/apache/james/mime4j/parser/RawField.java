/*  1:   */ package org.apache.james.mime4j.parser;
/*  2:   */ 
/*  3:   */ import org.apache.james.mime4j.util.ByteSequence;
/*  4:   */ import org.apache.james.mime4j.util.ContentUtil;
/*  5:   */ 
/*  6:   */ class RawField
/*  7:   */   implements Field
/*  8:   */ {
/*  9:   */   private final ByteSequence raw;
/* 10:   */   private int colonIdx;
/* 11:   */   private String name;
/* 12:   */   private String body;
/* 13:   */   
/* 14:   */   public RawField(ByteSequence raw, int colonIdx)
/* 15:   */   {
/* 16:37 */     this.raw = raw;
/* 17:38 */     this.colonIdx = colonIdx;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getName()
/* 21:   */   {
/* 22:42 */     if (this.name == null) {
/* 23:43 */       this.name = parseName();
/* 24:   */     }
/* 25:46 */     return this.name;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getBody()
/* 29:   */   {
/* 30:50 */     if (this.body == null) {
/* 31:51 */       this.body = parseBody();
/* 32:   */     }
/* 33:54 */     return this.body;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public ByteSequence getRaw()
/* 37:   */   {
/* 38:58 */     return this.raw;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String toString()
/* 42:   */   {
/* 43:63 */     return getName() + ':' + getBody();
/* 44:   */   }
/* 45:   */   
/* 46:   */   private String parseName()
/* 47:   */   {
/* 48:67 */     return ContentUtil.decode(this.raw, 0, this.colonIdx);
/* 49:   */   }
/* 50:   */   
/* 51:   */   private String parseBody()
/* 52:   */   {
/* 53:71 */     int offset = this.colonIdx + 1;
/* 54:72 */     int length = this.raw.length() - offset;
/* 55:73 */     return ContentUtil.decode(this.raw, offset, length);
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.parser.RawField
 * JD-Core Version:    0.7.0.1
 */