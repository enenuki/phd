/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import jcifs.dcerpc.DcerpcHandle;
/*  5:   */ import jcifs.dcerpc.rpc.policy_handle;
/*  6:   */ import jcifs.smb.SmbException;
/*  7:   */ 
/*  8:   */ public class LsaPolicyHandle
/*  9:   */   extends rpc.policy_handle
/* 10:   */ {
/* 11:   */   public LsaPolicyHandle(DcerpcHandle handle, String server, int access)
/* 12:   */     throws IOException
/* 13:   */   {
/* 14:31 */     if (server == null) {
/* 15:32 */       server = "\\\\";
/* 16:   */     }
/* 17:33 */     MsrpcLsarOpenPolicy2 rpc = new MsrpcLsarOpenPolicy2(server, access, this);
/* 18:34 */     handle.sendrecv(rpc);
/* 19:35 */     if (rpc.retval != 0) {
/* 20:36 */       throw new SmbException(rpc.retval, false);
/* 21:   */     }
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void close()
/* 25:   */     throws IOException
/* 26:   */   {}
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.LsaPolicyHandle
 * JD-Core Version:    0.7.0.1
 */