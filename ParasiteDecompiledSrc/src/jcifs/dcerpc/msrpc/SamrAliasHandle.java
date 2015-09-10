/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import jcifs.dcerpc.DcerpcHandle;
/*  5:   */ import jcifs.dcerpc.rpc.policy_handle;
/*  6:   */ import jcifs.smb.SmbException;
/*  7:   */ 
/*  8:   */ public class SamrAliasHandle
/*  9:   */   extends rpc.policy_handle
/* 10:   */ {
/* 11:   */   public SamrAliasHandle(DcerpcHandle handle, SamrDomainHandle domainHandle, int access, int rid)
/* 12:   */     throws IOException
/* 13:   */   {
/* 14:33 */     MsrpcSamrOpenAlias rpc = new MsrpcSamrOpenAlias(domainHandle, access, rid, this);
/* 15:34 */     handle.sendrecv(rpc);
/* 16:35 */     if (rpc.retval != 0) {
/* 17:36 */       throw new SmbException(rpc.retval, false);
/* 18:   */     }
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void close()
/* 22:   */     throws IOException
/* 23:   */   {}
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.SamrAliasHandle
 * JD-Core Version:    0.7.0.1
 */