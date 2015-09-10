/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ public class MsrpcSamrOpenAlias
/*  4:   */   extends samr.SamrOpenAlias
/*  5:   */ {
/*  6:   */   public MsrpcSamrOpenAlias(SamrDomainHandle handle, int access, int rid, SamrAliasHandle aliasHandle)
/*  7:   */   {
/*  8:29 */     super(handle, access, rid, aliasHandle);
/*  9:30 */     this.ptype = 0;
/* 10:31 */     this.flags = 3;
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.MsrpcSamrOpenAlias
 * JD-Core Version:    0.7.0.1
 */