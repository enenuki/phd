/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.io.ByteArrayInputStream;
/*  4:   */ import java.io.File;
/*  5:   */ import java.io.FileInputStream;
/*  6:   */ import java.io.FileNotFoundException;
/*  7:   */ import java.io.IOException;
/*  8:   */ import java.io.InputStream;
/*  9:   */ import java.io.Serializable;
/* 10:   */ import org.apache.commons.lang.ArrayUtils;
/* 11:   */ 
/* 12:   */ public abstract interface DownloadedContent
/* 13:   */   extends Serializable
/* 14:   */ {
/* 15:   */   public abstract InputStream getInputStream()
/* 16:   */     throws IOException;
/* 17:   */   
/* 18:   */   public static class InMemory
/* 19:   */     implements DownloadedContent
/* 20:   */   {
/* 21:   */     private final byte[] bytes_;
/* 22:   */     
/* 23:   */     public InMemory(byte[] byteArray)
/* 24:   */     {
/* 25:40 */       if (byteArray == null) {
/* 26:41 */         this.bytes_ = ArrayUtils.EMPTY_BYTE_ARRAY;
/* 27:   */       } else {
/* 28:44 */         this.bytes_ = byteArray;
/* 29:   */       }
/* 30:   */     }
/* 31:   */     
/* 32:   */     public InputStream getInputStream()
/* 33:   */     {
/* 34:49 */       return new ByteArrayInputStream(this.bytes_);
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static class OnFile
/* 39:   */     implements DownloadedContent
/* 40:   */   {
/* 41:   */     private final File file_;
/* 42:   */     
/* 43:   */     public OnFile(File file)
/* 44:   */     {
/* 45:59 */       this.file_ = file;
/* 46:   */     }
/* 47:   */     
/* 48:   */     public InputStream getInputStream()
/* 49:   */       throws FileNotFoundException
/* 50:   */     {
/* 51:63 */       return new FileInputStream(this.file_);
/* 52:   */     }
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.DownloadedContent
 * JD-Core Version:    0.7.0.1
 */