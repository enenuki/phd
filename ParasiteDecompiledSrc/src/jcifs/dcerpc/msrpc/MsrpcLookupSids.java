/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ import jcifs.smb.SID;
/*  4:   */ 
/*  5:   */ public class MsrpcLookupSids
/*  6:   */   extends lsarpc.LsarLookupSids
/*  7:   */ {
/*  8:   */   SID[] sids;
/*  9:   */   
/* 10:   */   public MsrpcLookupSids(LsaPolicyHandle policyHandle, SID[] sids)
/* 11:   */   {
/* 12:31 */     super(policyHandle, new LsarSidArrayX(sids), new lsarpc.LsarRefDomainList(), new lsarpc.LsarTransNameArray(), (short)1, sids.length);
/* 13:   */     
/* 14:   */ 
/* 15:   */ 
/* 16:   */ 
/* 17:   */ 
/* 18:37 */     this.sids = sids;
/* 19:38 */     this.ptype = 0;
/* 20:39 */     this.flags = 3;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.MsrpcLookupSids
 * JD-Core Version:    0.7.0.1
 */