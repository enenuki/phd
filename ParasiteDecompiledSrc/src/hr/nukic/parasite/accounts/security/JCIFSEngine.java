/*  1:   */ package hr.nukic.parasite.accounts.security;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import jcifs.ntlmssp.Type1Message;
/*  5:   */ import jcifs.ntlmssp.Type2Message;
/*  6:   */ import jcifs.ntlmssp.Type3Message;
/*  7:   */ import jcifs.util.Base64;
/*  8:   */ import org.apache.http.impl.auth.NTLMEngine;
/*  9:   */ import org.apache.http.impl.auth.NTLMEngineException;
/* 10:   */ 
/* 11:   */ public class JCIFSEngine
/* 12:   */   implements NTLMEngine
/* 13:   */ {
/* 14:   */   public String generateType1Msg(String domain, String workstation)
/* 15:   */     throws NTLMEngineException
/* 16:   */   {
/* 17:18 */     Type1Message t1m = new Type1Message(Type1Message.getDefaultFlags(), domain, workstation);
/* 18:19 */     return Base64.encode(t1m.toByteArray());
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String generateType3Msg(String username, String password, String domain, String workstation, String challenge)
/* 22:   */     throws NTLMEngineException
/* 23:   */   {
/* 24:   */     try
/* 25:   */     {
/* 26:30 */       t2m = new Type2Message(Base64.decode(challenge));
/* 27:   */     }
/* 28:   */     catch (IOException ex)
/* 29:   */     {
/* 30:   */       Type2Message t2m;
/* 31:32 */       throw new NTLMEngineException("Invalid Type2 message", ex);
/* 32:   */     }
/* 33:   */     Type2Message t2m;
/* 34:34 */     Type3Message t3m = new Type3Message(t2m, password, domain, username, workstation, 0);
/* 35:35 */     return Base64.encode(t3m.toByteArray());
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hr.nukic.parasite.accounts.security.JCIFSEngine
 * JD-Core Version:    0.7.0.1
 */