/*  1:   */ package org.apache.http.message;
/*  2:   */ 
/*  3:   */ import org.apache.http.util.CharArrayBuffer;
/*  4:   */ 
/*  5:   */ public class ParserCursor
/*  6:   */ {
/*  7:   */   private final int lowerBound;
/*  8:   */   private final int upperBound;
/*  9:   */   private int pos;
/* 10:   */   
/* 11:   */   public ParserCursor(int lowerBound, int upperBound)
/* 12:   */   {
/* 13:49 */     if (lowerBound < 0) {
/* 14:50 */       throw new IndexOutOfBoundsException("Lower bound cannot be negative");
/* 15:   */     }
/* 16:52 */     if (lowerBound > upperBound) {
/* 17:53 */       throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
/* 18:   */     }
/* 19:55 */     this.lowerBound = lowerBound;
/* 20:56 */     this.upperBound = upperBound;
/* 21:57 */     this.pos = lowerBound;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getLowerBound()
/* 25:   */   {
/* 26:61 */     return this.lowerBound;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int getUpperBound()
/* 30:   */   {
/* 31:65 */     return this.upperBound;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int getPos()
/* 35:   */   {
/* 36:69 */     return this.pos;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void updatePos(int pos)
/* 40:   */   {
/* 41:73 */     if (pos < this.lowerBound) {
/* 42:74 */       throw new IndexOutOfBoundsException("pos: " + pos + " < lowerBound: " + this.lowerBound);
/* 43:   */     }
/* 44:76 */     if (pos > this.upperBound) {
/* 45:77 */       throw new IndexOutOfBoundsException("pos: " + pos + " > upperBound: " + this.upperBound);
/* 46:   */     }
/* 47:79 */     this.pos = pos;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public boolean atEnd()
/* 51:   */   {
/* 52:83 */     return this.pos >= this.upperBound;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String toString()
/* 56:   */   {
/* 57:87 */     CharArrayBuffer buffer = new CharArrayBuffer(16);
/* 58:88 */     buffer.append('[');
/* 59:89 */     buffer.append(Integer.toString(this.lowerBound));
/* 60:90 */     buffer.append('>');
/* 61:91 */     buffer.append(Integer.toString(this.pos));
/* 62:92 */     buffer.append('>');
/* 63:93 */     buffer.append(Integer.toString(this.upperBound));
/* 64:94 */     buffer.append(']');
/* 65:95 */     return buffer.toString();
/* 66:   */   }
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.message.ParserCursor
 * JD-Core Version:    0.7.0.1
 */