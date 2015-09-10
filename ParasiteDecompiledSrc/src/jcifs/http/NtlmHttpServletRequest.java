/*  1:   */ package jcifs.http;
/*  2:   */ 
/*  3:   */ import java.security.Principal;
/*  4:   */ import javax.servlet.http.HttpServletRequest;
/*  5:   */ import javax.servlet.http.HttpServletRequestWrapper;
/*  6:   */ 
/*  7:   */ class NtlmHttpServletRequest
/*  8:   */   extends HttpServletRequestWrapper
/*  9:   */ {
/* 10:   */   Principal principal;
/* 11:   */   
/* 12:   */   NtlmHttpServletRequest(HttpServletRequest req, Principal principal)
/* 13:   */   {
/* 14:31 */     super(req);
/* 15:32 */     this.principal = principal;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getRemoteUser()
/* 19:   */   {
/* 20:35 */     return this.principal.getName();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Principal getUserPrincipal()
/* 24:   */   {
/* 25:38 */     return this.principal;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getAuthType()
/* 29:   */   {
/* 30:41 */     return "NTLM";
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.http.NtlmHttpServletRequest
 * JD-Core Version:    0.7.0.1
 */