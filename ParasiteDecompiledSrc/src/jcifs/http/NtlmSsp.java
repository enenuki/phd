/*   1:    */ package jcifs.http;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import javax.servlet.ServletException;
/*   5:    */ import javax.servlet.http.HttpServletRequest;
/*   6:    */ import javax.servlet.http.HttpServletResponse;
/*   7:    */ import jcifs.ntlmssp.NtlmFlags;
/*   8:    */ import jcifs.ntlmssp.Type1Message;
/*   9:    */ import jcifs.ntlmssp.Type2Message;
/*  10:    */ import jcifs.ntlmssp.Type3Message;
/*  11:    */ import jcifs.smb.NtlmPasswordAuthentication;
/*  12:    */ import jcifs.util.Base64;
/*  13:    */ 
/*  14:    */ public class NtlmSsp
/*  15:    */   implements NtlmFlags
/*  16:    */ {
/*  17:    */   public NtlmPasswordAuthentication doAuthentication(HttpServletRequest req, HttpServletResponse resp, byte[] challenge)
/*  18:    */     throws IOException, ServletException
/*  19:    */   {
/*  20: 69 */     return authenticate(req, resp, challenge);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static NtlmPasswordAuthentication authenticate(HttpServletRequest req, HttpServletResponse resp, byte[] challenge)
/*  24:    */     throws IOException, ServletException
/*  25:    */   {
/*  26: 84 */     String msg = req.getHeader("Authorization");
/*  27: 85 */     if ((msg != null) && (msg.startsWith("NTLM ")))
/*  28:    */     {
/*  29: 86 */       byte[] src = Base64.decode(msg.substring(5));
/*  30: 87 */       if (src[8] == 1)
/*  31:    */       {
/*  32: 88 */         Type1Message type1 = new Type1Message(src);
/*  33: 89 */         Type2Message type2 = new Type2Message(type1, challenge, null);
/*  34: 90 */         msg = Base64.encode(type2.toByteArray());
/*  35: 91 */         resp.setHeader("WWW-Authenticate", "NTLM " + msg);
/*  36:    */       }
/*  37: 92 */       else if (src[8] == 3)
/*  38:    */       {
/*  39: 93 */         Type3Message type3 = new Type3Message(src);
/*  40: 94 */         byte[] lmResponse = type3.getLMResponse();
/*  41: 95 */         if (lmResponse == null) {
/*  42: 95 */           lmResponse = new byte[0];
/*  43:    */         }
/*  44: 96 */         byte[] ntResponse = type3.getNTResponse();
/*  45: 97 */         if (ntResponse == null) {
/*  46: 97 */           ntResponse = new byte[0];
/*  47:    */         }
/*  48: 98 */         return new NtlmPasswordAuthentication(type3.getDomain(), type3.getUser(), challenge, lmResponse, ntResponse);
/*  49:    */       }
/*  50:    */     }
/*  51:    */     else
/*  52:    */     {
/*  53:102 */       resp.setHeader("WWW-Authenticate", "NTLM");
/*  54:    */     }
/*  55:104 */     resp.setStatus(401);
/*  56:105 */     resp.setContentLength(0);
/*  57:106 */     resp.flushBuffer();
/*  58:107 */     return null;
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.http.NtlmSsp
 * JD-Core Version:    0.7.0.1
 */