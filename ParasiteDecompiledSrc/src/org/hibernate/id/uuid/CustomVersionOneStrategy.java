/*   1:    */ package org.hibernate.id.uuid;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.UUID;
/*   5:    */ import org.hibernate.engine.spi.SessionImplementor;
/*   6:    */ import org.hibernate.id.UUIDGenerationStrategy;
/*   7:    */ import org.hibernate.internal.util.BytesHelper;
/*   8:    */ 
/*   9:    */ public class CustomVersionOneStrategy
/*  10:    */   implements UUIDGenerationStrategy
/*  11:    */ {
/*  12:    */   private final long mostSignificantBits;
/*  13:    */   
/*  14:    */   public int getGeneratedVersion()
/*  15:    */   {
/*  16: 43 */     return 1;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public CustomVersionOneStrategy()
/*  20:    */   {
/*  21: 50 */     byte[] hiBits = new byte[8];
/*  22:    */     
/*  23: 52 */     System.arraycopy(Helper.getAddressBytes(), 0, hiBits, 0, 4);
/*  24:    */     
/*  25: 54 */     System.arraycopy(Helper.getJvmIdentifierBytes(), 0, hiBits, 4, 4); byte[] 
/*  26:    */     
/*  27: 56 */       tmp32_29 = hiBits;tmp32_29[6] = ((byte)(tmp32_29[6] & 0xF)); byte[] 
/*  28: 57 */       tmp42_39 = hiBits;tmp42_39[6] = ((byte)(tmp42_39[6] | 0x10));
/*  29:    */     
/*  30: 59 */     this.mostSignificantBits = BytesHelper.asLong(hiBits);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public UUID generateUUID(SessionImplementor session)
/*  34:    */   {
/*  35: 63 */     long leastSignificantBits = generateLeastSignificantBits(System.currentTimeMillis());
/*  36: 64 */     return new UUID(this.mostSignificantBits, leastSignificantBits);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public long getMostSignificantBits()
/*  40:    */   {
/*  41: 68 */     return this.mostSignificantBits;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static long generateLeastSignificantBits(long seed)
/*  45:    */   {
/*  46: 72 */     byte[] loBits = new byte[8];
/*  47:    */     
/*  48: 74 */     short hiTime = (short)(int)(seed >>> 32);
/*  49: 75 */     int loTime = (int)seed;
/*  50: 76 */     System.arraycopy(BytesHelper.fromShort(hiTime), 0, loBits, 0, 2);
/*  51: 77 */     System.arraycopy(BytesHelper.fromInt(loTime), 0, loBits, 2, 4);
/*  52: 78 */     System.arraycopy(Helper.getCountBytes(), 0, loBits, 6, 2); int 
/*  53: 79 */       tmp52_51 = 0; byte[] tmp52_50 = loBits;tmp52_50[tmp52_51] = ((byte)(tmp52_50[tmp52_51] & 0x3F)); int 
/*  54: 80 */       tmp61_60 = 0; byte[] tmp61_59 = loBits;tmp61_59[tmp61_60] = ((byte)(tmp61_59[tmp61_60] | 0x80));
/*  55:    */     
/*  56: 82 */     return BytesHelper.asLong(loBits);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static void main(String[] args)
/*  60:    */   {
/*  61: 86 */     CustomVersionOneStrategy strategy = new CustomVersionOneStrategy();
/*  62: 88 */     for (int i = 0; i < 1000; i++)
/*  63:    */     {
/*  64: 89 */       System.out.println("Generation # " + i + " ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
/*  65: 90 */       byte[] loBits = new byte[8];
/*  66:    */       
/*  67: 92 */       long sysTime = System.currentTimeMillis();
/*  68: 93 */       short hiTime = (short)(int)(System.currentTimeMillis() >>> 32);
/*  69: 94 */       int loTime = (int)sysTime;
/*  70: 95 */       System.arraycopy(BytesHelper.fromShort(hiTime), 0, loBits, 0, 2);
/*  71: 96 */       System.arraycopy(BytesHelper.fromInt(loTime), 0, loBits, 2, 4);
/*  72: 97 */       System.arraycopy(Helper.getCountBytes(), 0, loBits, 6, 2);
/*  73:    */       
/*  74: 99 */       System.out.println("    before bit setting ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
/*  75:100 */       System.out.println("       loBits[0] : " + BytesHelper.toBinaryString(loBits[0]));
/*  76:101 */       System.out.println("             lsb : " + BytesHelper.toBinaryString(BytesHelper.asLong(loBits)));
/*  77:102 */       System.out.println("    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"); int 
/*  78:    */       
/*  79:104 */         tmp186_185 = 0; byte[] tmp186_184 = loBits;tmp186_184[tmp186_185] = ((byte)(tmp186_184[tmp186_185] & 0x3F)); int 
/*  80:105 */         tmp195_194 = 0; byte[] tmp195_193 = loBits;tmp195_193[tmp195_194] = ((byte)(tmp195_193[tmp195_194] | 0x80));
/*  81:    */       
/*  82:107 */       System.out.println("    after bit setting ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
/*  83:108 */       System.out.println("       loBits[0] : " + BytesHelper.toBinaryString(loBits[0]));
/*  84:109 */       long leastSignificantBits = BytesHelper.asLong(loBits);
/*  85:110 */       System.out.println("             lsb : " + BytesHelper.toBinaryString(leastSignificantBits));
/*  86:111 */       System.out.println("    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
/*  87:    */       
/*  88:    */ 
/*  89:114 */       UUID uuid = new UUID(strategy.mostSignificantBits, leastSignificantBits);
/*  90:115 */       System.out.println("  uuid : " + uuid.toString());
/*  91:116 */       System.out.println("  variant : " + uuid.variant());
/*  92:117 */       System.out.println("  version : " + uuid.version());
/*  93:118 */       if (uuid.variant() != 2) {
/*  94:119 */         throw new RuntimeException("bad variant");
/*  95:    */       }
/*  96:121 */       System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
/*  97:    */     }
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.uuid.CustomVersionOneStrategy
 * JD-Core Version:    0.7.0.1
 */