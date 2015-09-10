/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import jcifs.dcerpc.DcerpcHandle;
/*  5:   */ import jcifs.dcerpc.rpc.policy_handle;
/*  6:   */ import jcifs.dcerpc.rpc.sid_t;
/*  7:   */ import jcifs.smb.SmbException;
/*  8:   */ 
/*  9:   */ public class SamrDomainHandle
/* 10:   */   extends rpc.policy_handle
/* 11:   */ {
/* 12:   */   public SamrDomainHandle(DcerpcHandle handle, SamrPolicyHandle policyHandle, int access, rpc.sid_t sid)
/* 13:   */     throws IOException
/* 14:   */   {
/* 15:33 */     MsrpcSamrOpenDomain rpc = new MsrpcSamrOpenDomain(policyHandle, access, sid, this);
/* 16:34 */     handle.sendrecv(rpc);
/* 17:35 */     if (rpc.retval != 0) {
/* 18:36 */       throw new SmbException(rpc.retval, false);
/* 19:   */     }
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void close()
/* 23:   */     throws IOException
/* 24:   */   {}
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.SamrDomainHandle
 * JD-Core Version:    0.7.0.1
 */