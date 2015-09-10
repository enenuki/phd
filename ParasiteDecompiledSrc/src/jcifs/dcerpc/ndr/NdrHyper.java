/*  1:   */ package jcifs.dcerpc.ndr;
/*  2:   */ 
/*  3:   */ public class NdrHyper
/*  4:   */   extends NdrObject
/*  5:   */ {
/*  6:   */   public long value;
/*  7:   */   
/*  8:   */   public NdrHyper(long value)
/*  9:   */   {
/* 10:27 */     this.value = value;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public void encode(NdrBuffer dst)
/* 14:   */     throws NdrException
/* 15:   */   {
/* 16:31 */     dst.enc_ndr_hyper(this.value);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void decode(NdrBuffer src)
/* 20:   */     throws NdrException
/* 21:   */   {
/* 22:34 */     this.value = src.dec_ndr_hyper();
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.ndr.NdrHyper
 * JD-Core Version:    0.7.0.1
 */