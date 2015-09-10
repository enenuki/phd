/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ public class MsrpcGetMembersInAlias
/*  4:   */   extends samr.SamrGetMembersInAlias
/*  5:   */ {
/*  6:   */   public MsrpcGetMembersInAlias(SamrAliasHandle aliasHandle, lsarpc.LsarSidArray sids)
/*  7:   */   {
/*  8:27 */     super(aliasHandle, sids);
/*  9:28 */     this.sids = sids;
/* 10:29 */     this.ptype = 0;
/* 11:30 */     this.flags = 3;
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.MsrpcGetMembersInAlias
 * JD-Core Version:    0.7.0.1
 */