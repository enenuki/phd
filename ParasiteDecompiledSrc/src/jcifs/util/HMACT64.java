/*   1:    */ package jcifs.util;
/*   2:    */ 
/*   3:    */ import java.security.MessageDigest;
/*   4:    */ 
/*   5:    */ public class HMACT64
/*   6:    */   extends MessageDigest
/*   7:    */   implements Cloneable
/*   8:    */ {
/*   9:    */   private static final int BLOCK_LENGTH = 64;
/*  10:    */   private static final byte IPAD = 54;
/*  11:    */   private static final byte OPAD = 92;
/*  12:    */   private MessageDigest md5;
/*  13: 39 */   private byte[] ipad = new byte[64];
/*  14: 41 */   private byte[] opad = new byte[64];
/*  15:    */   
/*  16:    */   public HMACT64(byte[] key)
/*  17:    */   {
/*  18: 49 */     super("HMACT64");
/*  19: 50 */     int length = Math.min(key.length, 64);
/*  20: 51 */     for (int i = 0; i < length; i++)
/*  21:    */     {
/*  22: 52 */       this.ipad[i] = ((byte)(key[i] ^ 0x36));
/*  23: 53 */       this.opad[i] = ((byte)(key[i] ^ 0x5C));
/*  24:    */     }
/*  25: 55 */     for (int i = length; i < 64; i++)
/*  26:    */     {
/*  27: 56 */       this.ipad[i] = 54;
/*  28: 57 */       this.opad[i] = 92;
/*  29:    */     }
/*  30:    */     try
/*  31:    */     {
/*  32: 60 */       this.md5 = MessageDigest.getInstance("MD5");
/*  33:    */     }
/*  34:    */     catch (Exception ex)
/*  35:    */     {
/*  36: 62 */       throw new IllegalStateException(ex.getMessage());
/*  37:    */     }
/*  38: 64 */     engineReset();
/*  39:    */   }
/*  40:    */   
/*  41:    */   private HMACT64(HMACT64 hmac)
/*  42:    */     throws CloneNotSupportedException
/*  43:    */   {
/*  44: 68 */     super("HMACT64");
/*  45: 69 */     this.ipad = hmac.ipad;
/*  46: 70 */     this.opad = hmac.opad;
/*  47: 71 */     this.md5 = ((MessageDigest)hmac.md5.clone());
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Object clone()
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 76 */       return new HMACT64(this);
/*  55:    */     }
/*  56:    */     catch (CloneNotSupportedException ex)
/*  57:    */     {
/*  58: 78 */       throw new IllegalStateException(ex.getMessage());
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected byte[] engineDigest()
/*  63:    */   {
/*  64: 83 */     byte[] digest = this.md5.digest();
/*  65: 84 */     this.md5.update(this.opad);
/*  66: 85 */     return this.md5.digest(digest);
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected int engineDigest(byte[] buf, int offset, int len)
/*  70:    */   {
/*  71: 89 */     byte[] digest = this.md5.digest();
/*  72: 90 */     this.md5.update(this.opad);
/*  73: 91 */     this.md5.update(digest);
/*  74:    */     try
/*  75:    */     {
/*  76: 93 */       return this.md5.digest(buf, offset, len);
/*  77:    */     }
/*  78:    */     catch (Exception ex)
/*  79:    */     {
/*  80: 95 */       throw new IllegalStateException();
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected int engineGetDigestLength()
/*  85:    */   {
/*  86:100 */     return this.md5.getDigestLength();
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected void engineReset()
/*  90:    */   {
/*  91:104 */     this.md5.reset();
/*  92:105 */     this.md5.update(this.ipad);
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected void engineUpdate(byte b)
/*  96:    */   {
/*  97:109 */     this.md5.update(b);
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected void engineUpdate(byte[] input, int offset, int len)
/* 101:    */   {
/* 102:113 */     this.md5.update(input, offset, len);
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.util.HMACT64
 * JD-Core Version:    0.7.0.1
 */