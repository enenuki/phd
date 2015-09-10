/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import java.io.Reader;
/*  4:   */ import java.io.StringReader;
/*  5:   */ import org.hibernate.type.descriptor.CharacterStream;
/*  6:   */ 
/*  7:   */ public class CharacterStreamImpl
/*  8:   */   implements CharacterStream
/*  9:   */ {
/* 10:   */   private final StringReader reader;
/* 11:   */   private final int length;
/* 12:   */   
/* 13:   */   public CharacterStreamImpl(String chars)
/* 14:   */   {
/* 15:41 */     this.reader = new StringReader(chars);
/* 16:42 */     this.length = chars.length();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public Reader getReader()
/* 20:   */   {
/* 21:46 */     return this.reader;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getLength()
/* 25:   */   {
/* 26:50 */     return this.length;
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.CharacterStreamImpl
 * JD-Core Version:    0.7.0.1
 */