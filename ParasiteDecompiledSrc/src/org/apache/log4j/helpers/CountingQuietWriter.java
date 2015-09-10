/*  1:   */ package org.apache.log4j.helpers;
/*  2:   */ 
/*  3:   */ import java.io.FilterWriter;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.Writer;
/*  6:   */ import org.apache.log4j.spi.ErrorHandler;
/*  7:   */ 
/*  8:   */ public class CountingQuietWriter
/*  9:   */   extends QuietWriter
/* 10:   */ {
/* 11:   */   protected long count;
/* 12:   */   
/* 13:   */   public CountingQuietWriter(Writer writer, ErrorHandler eh)
/* 14:   */   {
/* 15:39 */     super(writer, eh);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void write(String string)
/* 19:   */   {
/* 20:   */     try
/* 21:   */     {
/* 22:45 */       this.out.write(string);
/* 23:46 */       this.count += string.length();
/* 24:   */     }
/* 25:   */     catch (IOException e)
/* 26:   */     {
/* 27:49 */       this.errorHandler.error("Write failure.", e, 1);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public long getCount()
/* 32:   */   {
/* 33:55 */     return this.count;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setCount(long count)
/* 37:   */   {
/* 38:60 */     this.count = count;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.CountingQuietWriter
 * JD-Core Version:    0.7.0.1
 */