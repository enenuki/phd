/*  1:   */ package org.apache.log4j.helpers;
/*  2:   */ 
/*  3:   */ import java.io.FilterWriter;
/*  4:   */ import java.io.Writer;
/*  5:   */ import org.apache.log4j.spi.ErrorHandler;
/*  6:   */ 
/*  7:   */ public class QuietWriter
/*  8:   */   extends FilterWriter
/*  9:   */ {
/* 10:   */   protected ErrorHandler errorHandler;
/* 11:   */   
/* 12:   */   public QuietWriter(Writer writer, ErrorHandler errorHandler)
/* 13:   */   {
/* 14:40 */     super(writer);
/* 15:41 */     setErrorHandler(errorHandler);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void write(String string)
/* 19:   */   {
/* 20:46 */     if (string != null) {
/* 21:   */       try
/* 22:   */       {
/* 23:48 */         this.out.write(string);
/* 24:   */       }
/* 25:   */       catch (Exception e)
/* 26:   */       {
/* 27:50 */         this.errorHandler.error("Failed to write [" + string + "].", e, 1);
/* 28:   */       }
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void flush()
/* 33:   */   {
/* 34:   */     try
/* 35:   */     {
/* 36:59 */       this.out.flush();
/* 37:   */     }
/* 38:   */     catch (Exception e)
/* 39:   */     {
/* 40:61 */       this.errorHandler.error("Failed to flush writer,", e, 2);
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void setErrorHandler(ErrorHandler eh)
/* 45:   */   {
/* 46:69 */     if (eh == null) {
/* 47:71 */       throw new IllegalArgumentException("Attempted to set null ErrorHandler.");
/* 48:   */     }
/* 49:73 */     this.errorHandler = eh;
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.QuietWriter
 * JD-Core Version:    0.7.0.1
 */