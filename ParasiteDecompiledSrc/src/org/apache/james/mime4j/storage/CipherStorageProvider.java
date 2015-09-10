/*   1:    */ package org.apache.james.mime4j.storage;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.security.GeneralSecurityException;
/*   6:    */ import java.security.NoSuchAlgorithmException;
/*   7:    */ import javax.crypto.Cipher;
/*   8:    */ import javax.crypto.CipherInputStream;
/*   9:    */ import javax.crypto.CipherOutputStream;
/*  10:    */ import javax.crypto.KeyGenerator;
/*  11:    */ import javax.crypto.SecretKey;
/*  12:    */ import javax.crypto.spec.SecretKeySpec;
/*  13:    */ 
/*  14:    */ public class CipherStorageProvider
/*  15:    */   extends AbstractStorageProvider
/*  16:    */ {
/*  17:    */   private final StorageProvider backend;
/*  18:    */   private final String algorithm;
/*  19:    */   private final KeyGenerator keygen;
/*  20:    */   
/*  21:    */   public CipherStorageProvider(StorageProvider backend)
/*  22:    */   {
/*  23: 61 */     this(backend, "Blowfish");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public CipherStorageProvider(StorageProvider backend, String algorithm)
/*  27:    */   {
/*  28: 75 */     if (backend == null) {
/*  29: 76 */       throw new IllegalArgumentException();
/*  30:    */     }
/*  31:    */     try
/*  32:    */     {
/*  33: 79 */       this.backend = backend;
/*  34: 80 */       this.algorithm = algorithm;
/*  35: 81 */       this.keygen = KeyGenerator.getInstance(algorithm);
/*  36:    */     }
/*  37:    */     catch (NoSuchAlgorithmException e)
/*  38:    */     {
/*  39: 83 */       throw new IllegalArgumentException(e);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public StorageOutputStream createStorageOutputStream()
/*  44:    */     throws IOException
/*  45:    */   {
/*  46: 88 */     SecretKeySpec skeySpec = getSecretKeySpec();
/*  47:    */     
/*  48: 90 */     return new CipherStorageOutputStream(this.backend.createStorageOutputStream(), this.algorithm, skeySpec);
/*  49:    */   }
/*  50:    */   
/*  51:    */   private SecretKeySpec getSecretKeySpec()
/*  52:    */   {
/*  53: 95 */     byte[] raw = this.keygen.generateKey().getEncoded();
/*  54: 96 */     return new SecretKeySpec(raw, this.algorithm);
/*  55:    */   }
/*  56:    */   
/*  57:    */   private static final class CipherStorageOutputStream
/*  58:    */     extends StorageOutputStream
/*  59:    */   {
/*  60:    */     private final StorageOutputStream storageOut;
/*  61:    */     private final String algorithm;
/*  62:    */     private final SecretKeySpec skeySpec;
/*  63:    */     private final CipherOutputStream cipherOut;
/*  64:    */     
/*  65:    */     public CipherStorageOutputStream(StorageOutputStream out, String algorithm, SecretKeySpec skeySpec)
/*  66:    */       throws IOException
/*  67:    */     {
/*  68:    */       try
/*  69:    */       {
/*  70:109 */         this.storageOut = out;
/*  71:110 */         this.algorithm = algorithm;
/*  72:111 */         this.skeySpec = skeySpec;
/*  73:    */         
/*  74:113 */         Cipher cipher = Cipher.getInstance(algorithm);
/*  75:114 */         cipher.init(1, skeySpec);
/*  76:    */         
/*  77:116 */         this.cipherOut = new CipherOutputStream(out, cipher);
/*  78:    */       }
/*  79:    */       catch (GeneralSecurityException e)
/*  80:    */       {
/*  81:118 */         throw ((IOException)new IOException().initCause(e));
/*  82:    */       }
/*  83:    */     }
/*  84:    */     
/*  85:    */     public void close()
/*  86:    */       throws IOException
/*  87:    */     {
/*  88:124 */       super.close();
/*  89:125 */       this.cipherOut.close();
/*  90:    */     }
/*  91:    */     
/*  92:    */     protected void write0(byte[] buffer, int offset, int length)
/*  93:    */       throws IOException
/*  94:    */     {
/*  95:131 */       this.cipherOut.write(buffer, offset, length);
/*  96:    */     }
/*  97:    */     
/*  98:    */     protected Storage toStorage0()
/*  99:    */       throws IOException
/* 100:    */     {
/* 101:137 */       Storage encrypted = this.storageOut.toStorage();
/* 102:138 */       return new CipherStorageProvider.CipherStorage(encrypted, this.algorithm, this.skeySpec);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   private static final class CipherStorage
/* 107:    */     implements Storage
/* 108:    */   {
/* 109:    */     private Storage encrypted;
/* 110:    */     private final String algorithm;
/* 111:    */     private final SecretKeySpec skeySpec;
/* 112:    */     
/* 113:    */     public CipherStorage(Storage encrypted, String algorithm, SecretKeySpec skeySpec)
/* 114:    */     {
/* 115:149 */       this.encrypted = encrypted;
/* 116:150 */       this.algorithm = algorithm;
/* 117:151 */       this.skeySpec = skeySpec;
/* 118:    */     }
/* 119:    */     
/* 120:    */     public void delete()
/* 121:    */     {
/* 122:155 */       if (this.encrypted != null)
/* 123:    */       {
/* 124:156 */         this.encrypted.delete();
/* 125:157 */         this.encrypted = null;
/* 126:    */       }
/* 127:    */     }
/* 128:    */     
/* 129:    */     public InputStream getInputStream()
/* 130:    */       throws IOException
/* 131:    */     {
/* 132:162 */       if (this.encrypted == null) {
/* 133:163 */         throw new IllegalStateException("storage has been deleted");
/* 134:    */       }
/* 135:    */       try
/* 136:    */       {
/* 137:166 */         Cipher cipher = Cipher.getInstance(this.algorithm);
/* 138:167 */         cipher.init(2, this.skeySpec);
/* 139:    */         
/* 140:169 */         InputStream in = this.encrypted.getInputStream();
/* 141:170 */         return new CipherInputStream(in, cipher);
/* 142:    */       }
/* 143:    */       catch (GeneralSecurityException e)
/* 144:    */       {
/* 145:172 */         throw ((IOException)new IOException().initCause(e));
/* 146:    */       }
/* 147:    */     }
/* 148:    */   }
/* 149:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.storage.CipherStorageProvider
 * JD-Core Version:    0.7.0.1
 */