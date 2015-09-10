/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ import jcifs.smb.SID;
/*  4:   */ 
/*  5:   */ class LsarSidArrayX
/*  6:   */   extends lsarpc.LsarSidArray
/*  7:   */ {
/*  8:   */   LsarSidArrayX(SID[] sids)
/*  9:   */   {
/* 10: 8 */     this.num_sids = sids.length;
/* 11: 9 */     this.sids = new lsarpc.LsarSidPtr[sids.length];
/* 12:10 */     for (int si = 0; si < sids.length; si++)
/* 13:   */     {
/* 14:11 */       this.sids[si] = new lsarpc.LsarSidPtr();
/* 15:12 */       this.sids[si].sid = sids[si];
/* 16:   */     }
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.LsarSidArrayX
 * JD-Core Version:    0.7.0.1
 */