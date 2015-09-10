/*  1:   */ package org.hibernate.lob;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.Reader;
/*  5:   */ 
/*  6:   */ /**
/*  7:   */  * @deprecated
/*  8:   */  */
/*  9:   */ public class ReaderInputStream
/* 10:   */   extends org.hibernate.engine.jdbc.ReaderInputStream
/* 11:   */ {
/* 12:   */   public ReaderInputStream(Reader reader)
/* 13:   */   {
/* 14:37 */     super(reader);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int read()
/* 18:   */     throws IOException
/* 19:   */   {
/* 20:41 */     return super.read();
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.lob.ReaderInputStream
 * JD-Core Version:    0.7.0.1
 */