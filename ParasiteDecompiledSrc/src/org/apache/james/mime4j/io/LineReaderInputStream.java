/*  1:   */ package org.apache.james.mime4j.io;
/*  2:   */ 
/*  3:   */ import java.io.FilterInputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.InputStream;
/*  6:   */ import org.apache.james.mime4j.util.ByteArrayBuffer;
/*  7:   */ 
/*  8:   */ public abstract class LineReaderInputStream
/*  9:   */   extends FilterInputStream
/* 10:   */ {
/* 11:   */   protected LineReaderInputStream(InputStream in)
/* 12:   */   {
/* 13:34 */     super(in);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public abstract int readLine(ByteArrayBuffer paramByteArrayBuffer)
/* 17:   */     throws IOException;
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.io.LineReaderInputStream
 * JD-Core Version:    0.7.0.1
 */