/*   1:    */ package org.apache.james.mime4j.message;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.nio.charset.Charset;
/*   6:    */ import org.apache.commons.logging.Log;
/*   7:    */ import org.apache.commons.logging.LogFactory;
/*   8:    */ import org.apache.james.mime4j.storage.DefaultStorageProvider;
/*   9:    */ import org.apache.james.mime4j.storage.MultiReferenceStorage;
/*  10:    */ import org.apache.james.mime4j.storage.Storage;
/*  11:    */ import org.apache.james.mime4j.storage.StorageProvider;
/*  12:    */ import org.apache.james.mime4j.util.CharsetUtil;
/*  13:    */ 
/*  14:    */ public class BodyFactory
/*  15:    */ {
/*  16: 39 */   private static Log log = LogFactory.getLog(BodyFactory.class);
/*  17: 41 */   private static final Charset FALLBACK_CHARSET = CharsetUtil.DEFAULT_CHARSET;
/*  18:    */   private StorageProvider storageProvider;
/*  19:    */   
/*  20:    */   public BodyFactory()
/*  21:    */   {
/*  22: 50 */     this.storageProvider = DefaultStorageProvider.getInstance();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public BodyFactory(StorageProvider storageProvider)
/*  26:    */   {
/*  27: 62 */     if (storageProvider == null) {
/*  28: 63 */       storageProvider = DefaultStorageProvider.getInstance();
/*  29:    */     }
/*  30: 65 */     this.storageProvider = storageProvider;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public StorageProvider getStorageProvider()
/*  34:    */   {
/*  35: 75 */     return this.storageProvider;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public BinaryBody binaryBody(InputStream is)
/*  39:    */     throws IOException
/*  40:    */   {
/*  41: 89 */     if (is == null) {
/*  42: 90 */       throw new IllegalArgumentException();
/*  43:    */     }
/*  44: 92 */     Storage storage = this.storageProvider.store(is);
/*  45: 93 */     return new StorageBinaryBody(new MultiReferenceStorage(storage));
/*  46:    */   }
/*  47:    */   
/*  48:    */   public BinaryBody binaryBody(Storage storage)
/*  49:    */     throws IOException
/*  50:    */   {
/*  51:113 */     if (storage == null) {
/*  52:114 */       throw new IllegalArgumentException();
/*  53:    */     }
/*  54:116 */     return new StorageBinaryBody(new MultiReferenceStorage(storage));
/*  55:    */   }
/*  56:    */   
/*  57:    */   public TextBody textBody(InputStream is)
/*  58:    */     throws IOException
/*  59:    */   {
/*  60:134 */     if (is == null) {
/*  61:135 */       throw new IllegalArgumentException();
/*  62:    */     }
/*  63:137 */     Storage storage = this.storageProvider.store(is);
/*  64:138 */     return new StorageTextBody(new MultiReferenceStorage(storage), CharsetUtil.DEFAULT_CHARSET);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public TextBody textBody(InputStream is, String mimeCharset)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:162 */     if (is == null) {
/*  71:163 */       throw new IllegalArgumentException();
/*  72:    */     }
/*  73:164 */     if (mimeCharset == null) {
/*  74:165 */       throw new IllegalArgumentException();
/*  75:    */     }
/*  76:167 */     Storage storage = this.storageProvider.store(is);
/*  77:168 */     Charset charset = toJavaCharset(mimeCharset, false);
/*  78:169 */     return new StorageTextBody(new MultiReferenceStorage(storage), charset);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public TextBody textBody(Storage storage)
/*  82:    */     throws IOException
/*  83:    */   {
/*  84:193 */     if (storage == null) {
/*  85:194 */       throw new IllegalArgumentException();
/*  86:    */     }
/*  87:196 */     return new StorageTextBody(new MultiReferenceStorage(storage), CharsetUtil.DEFAULT_CHARSET);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public TextBody textBody(Storage storage, String mimeCharset)
/*  91:    */     throws IOException
/*  92:    */   {
/*  93:227 */     if (storage == null) {
/*  94:228 */       throw new IllegalArgumentException();
/*  95:    */     }
/*  96:229 */     if (mimeCharset == null) {
/*  97:230 */       throw new IllegalArgumentException();
/*  98:    */     }
/*  99:232 */     Charset charset = toJavaCharset(mimeCharset, false);
/* 100:233 */     return new StorageTextBody(new MultiReferenceStorage(storage), charset);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public TextBody textBody(String text)
/* 104:    */   {
/* 105:249 */     if (text == null) {
/* 106:250 */       throw new IllegalArgumentException();
/* 107:    */     }
/* 108:252 */     return new StringTextBody(text, CharsetUtil.DEFAULT_CHARSET);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public TextBody textBody(String text, String mimeCharset)
/* 112:    */   {
/* 113:272 */     if (text == null) {
/* 114:273 */       throw new IllegalArgumentException();
/* 115:    */     }
/* 116:274 */     if (mimeCharset == null) {
/* 117:275 */       throw new IllegalArgumentException();
/* 118:    */     }
/* 119:277 */     Charset charset = toJavaCharset(mimeCharset, true);
/* 120:278 */     return new StringTextBody(text, charset);
/* 121:    */   }
/* 122:    */   
/* 123:    */   private static Charset toJavaCharset(String mimeCharset, boolean forEncoding)
/* 124:    */   {
/* 125:282 */     String charset = CharsetUtil.toJavaCharset(mimeCharset);
/* 126:283 */     if (charset == null)
/* 127:    */     {
/* 128:284 */       if (log.isWarnEnabled()) {
/* 129:285 */         log.warn("MIME charset '" + mimeCharset + "' has no " + "corresponding Java charset. Using " + FALLBACK_CHARSET + " instead.");
/* 130:    */       }
/* 131:288 */       return FALLBACK_CHARSET;
/* 132:    */     }
/* 133:291 */     if ((forEncoding) && (!CharsetUtil.isEncodingSupported(charset)))
/* 134:    */     {
/* 135:292 */       if (log.isWarnEnabled()) {
/* 136:293 */         log.warn("MIME charset '" + mimeCharset + "' does not support encoding. Using " + FALLBACK_CHARSET + " instead.");
/* 137:    */       }
/* 138:296 */       return FALLBACK_CHARSET;
/* 139:    */     }
/* 140:299 */     if ((!forEncoding) && (!CharsetUtil.isDecodingSupported(charset)))
/* 141:    */     {
/* 142:300 */       if (log.isWarnEnabled()) {
/* 143:301 */         log.warn("MIME charset '" + mimeCharset + "' does not support decoding. Using " + FALLBACK_CHARSET + " instead.");
/* 144:    */       }
/* 145:304 */       return FALLBACK_CHARSET;
/* 146:    */     }
/* 147:307 */     return Charset.forName(charset);
/* 148:    */   }
/* 149:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.BodyFactory
 * JD-Core Version:    0.7.0.1
 */