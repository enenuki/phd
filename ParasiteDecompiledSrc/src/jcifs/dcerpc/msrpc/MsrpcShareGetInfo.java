/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import jcifs.smb.ACE;
/*  5:   */ import jcifs.smb.SecurityDescriptor;
/*  6:   */ 
/*  7:   */ public class MsrpcShareGetInfo
/*  8:   */   extends srvsvc.ShareGetInfo
/*  9:   */ {
/* 10:   */   public MsrpcShareGetInfo(String server, String sharename)
/* 11:   */   {
/* 12:30 */     super(server, sharename, 502, new srvsvc.ShareInfo502());
/* 13:31 */     this.ptype = 0;
/* 14:32 */     this.flags = 3;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public ACE[] getSecurity()
/* 18:   */     throws IOException
/* 19:   */   {
/* 20:36 */     srvsvc.ShareInfo502 info502 = (srvsvc.ShareInfo502)this.info;
/* 21:37 */     if (info502.security_descriptor != null)
/* 22:   */     {
/* 23:39 */       SecurityDescriptor sd = new SecurityDescriptor(info502.security_descriptor, 0, info502.sd_size);
/* 24:40 */       return sd.aces;
/* 25:   */     }
/* 26:42 */     return null;
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.MsrpcShareGetInfo
 * JD-Core Version:    0.7.0.1
 */