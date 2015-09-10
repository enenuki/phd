/*  1:   */ package jcifs.dcerpc.ndr;
/*  2:   */ 
/*  3:   */ public class NdrShort
/*  4:   */   extends NdrObject
/*  5:   */ {
/*  6:   */   public int value;
/*  7:   */   
/*  8:   */   public NdrShort(int value)
/*  9:   */   {
/* 10:27 */     this.value = (value & 0xFF);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public void encode(NdrBuffer dst)
/* 14:   */     throws NdrException
/* 15:   */   {
/* 16:31 */     dst.enc_ndr_short(this.value);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void decode(NdrBuffer src)
/* 20:   */     throws NdrException
/* 21:   */   {
/* 22:34 */     this.value = src.dec_ndr_short();
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.ndr.NdrShort
 * JD-Core Version:    0.7.0.1
 */