/*  1:   */ package org.apache.james.mime4j.storage;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import org.apache.james.mime4j.codec.CodecUtil;
/*  6:   */ 
/*  7:   */ public abstract class AbstractStorageProvider
/*  8:   */   implements StorageProvider
/*  9:   */ {
/* 10:   */   public final Storage store(InputStream in)
/* 11:   */     throws IOException
/* 12:   */   {
/* 13:56 */     StorageOutputStream out = createStorageOutputStream();
/* 14:57 */     CodecUtil.copy(in, out);
/* 15:58 */     return out.toStorage();
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.storage.AbstractStorageProvider
 * JD-Core Version:    0.7.0.1
 */