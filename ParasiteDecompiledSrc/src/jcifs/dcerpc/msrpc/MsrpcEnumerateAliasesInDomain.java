/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ public class MsrpcEnumerateAliasesInDomain
/*  4:   */   extends samr.SamrEnumerateAliasesInDomain
/*  5:   */ {
/*  6:   */   public MsrpcEnumerateAliasesInDomain(SamrDomainHandle domainHandle, int acct_flags, samr.SamrSamArray sam)
/*  7:   */   {
/*  8:29 */     super(domainHandle, 0, acct_flags, null, 0);
/*  9:30 */     this.sam = sam;
/* 10:31 */     this.ptype = 0;
/* 11:32 */     this.flags = 3;
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.MsrpcEnumerateAliasesInDomain
 * JD-Core Version:    0.7.0.1
 */