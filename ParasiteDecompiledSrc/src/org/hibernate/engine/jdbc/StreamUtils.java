/*  1:   */ package org.hibernate.engine.jdbc;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.io.OutputStream;
/*  6:   */ import java.io.Reader;
/*  7:   */ import java.io.Writer;
/*  8:   */ 
/*  9:   */ public class StreamUtils
/* 10:   */ {
/* 11:   */   public static final int DEFAULT_CHUNK_SIZE = 1024;
/* 12:   */   
/* 13:   */   public static long copy(InputStream inputStream, OutputStream outputStream)
/* 14:   */     throws IOException
/* 15:   */   {
/* 16:40 */     return copy(inputStream, outputStream, 1024);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static long copy(InputStream inputStream, OutputStream outputStream, int bufferSize)
/* 20:   */     throws IOException
/* 21:   */   {
/* 22:44 */     byte[] buffer = new byte[bufferSize];
/* 23:45 */     long count = 0L;
/* 24:   */     int n;
/* 25:47 */     while (-1 != (n = inputStream.read(buffer)))
/* 26:   */     {
/* 27:48 */       outputStream.write(buffer, 0, n);
/* 28:49 */       count += n;
/* 29:   */     }
/* 30:51 */     return count;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static long copy(Reader reader, Writer writer)
/* 34:   */     throws IOException
/* 35:   */   {
/* 36:56 */     return copy(reader, writer, 1024);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static long copy(Reader reader, Writer writer, int bufferSize)
/* 40:   */     throws IOException
/* 41:   */   {
/* 42:60 */     char[] buffer = new char[bufferSize];
/* 43:61 */     long count = 0L;
/* 44:   */     int n;
/* 45:63 */     while (-1 != (n = reader.read(buffer)))
/* 46:   */     {
/* 47:64 */       writer.write(buffer, 0, n);
/* 48:65 */       count += n;
/* 49:   */     }
/* 50:67 */     return count;
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.StreamUtils
 * JD-Core Version:    0.7.0.1
 */