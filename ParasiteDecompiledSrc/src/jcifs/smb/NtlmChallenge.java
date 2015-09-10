/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import jcifs.UniAddress;
/*  5:   */ import jcifs.util.Hexdump;
/*  6:   */ 
/*  7:   */ public final class NtlmChallenge
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   public byte[] challenge;
/* 11:   */   public UniAddress dc;
/* 12:   */   
/* 13:   */   NtlmChallenge(byte[] challenge, UniAddress dc)
/* 14:   */   {
/* 15:31 */     this.challenge = challenge;
/* 16:32 */     this.dc = dc;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String toString()
/* 20:   */   {
/* 21:36 */     return "NtlmChallenge[challenge=0x" + Hexdump.toHexString(this.challenge, 0, this.challenge.length * 2) + ",dc=" + this.dc.toString() + "]";
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.NtlmChallenge
 * JD-Core Version:    0.7.0.1
 */