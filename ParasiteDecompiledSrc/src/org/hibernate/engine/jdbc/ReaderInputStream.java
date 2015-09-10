/*  1:   */ package org.hibernate.engine.jdbc;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.io.Reader;
/*  6:   */ 
/*  7:   */ public class ReaderInputStream
/*  8:   */   extends InputStream
/*  9:   */ {
/* 10:   */   private Reader reader;
/* 11:   */   
/* 12:   */   public ReaderInputStream(Reader reader)
/* 13:   */   {
/* 14:39 */     this.reader = reader;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int read()
/* 18:   */     throws IOException
/* 19:   */   {
/* 20:43 */     return this.reader.read();
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.ReaderInputStream
 * JD-Core Version:    0.7.0.1
 */