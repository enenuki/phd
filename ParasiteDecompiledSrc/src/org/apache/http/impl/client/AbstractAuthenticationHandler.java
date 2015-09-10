/*   1:    */ package org.apache.http.impl.client;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Locale;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.apache.commons.logging.Log;
/*  11:    */ import org.apache.commons.logging.LogFactory;
/*  12:    */ import org.apache.http.FormattedHeader;
/*  13:    */ import org.apache.http.Header;
/*  14:    */ import org.apache.http.HttpResponse;
/*  15:    */ import org.apache.http.annotation.Immutable;
/*  16:    */ import org.apache.http.auth.AuthScheme;
/*  17:    */ import org.apache.http.auth.AuthSchemeRegistry;
/*  18:    */ import org.apache.http.auth.AuthenticationException;
/*  19:    */ import org.apache.http.auth.MalformedChallengeException;
/*  20:    */ import org.apache.http.client.AuthenticationHandler;
/*  21:    */ import org.apache.http.protocol.HTTP;
/*  22:    */ import org.apache.http.protocol.HttpContext;
/*  23:    */ import org.apache.http.util.CharArrayBuffer;
/*  24:    */ 
/*  25:    */ @Immutable
/*  26:    */ public abstract class AbstractAuthenticationHandler
/*  27:    */   implements AuthenticationHandler
/*  28:    */ {
/*  29: 63 */   private final Log log = LogFactory.getLog(getClass());
/*  30: 65 */   private static final List<String> DEFAULT_SCHEME_PRIORITY = Collections.unmodifiableList(Arrays.asList(new String[] { "negotiate", "NTLM", "Digest", "Basic" }));
/*  31:    */   
/*  32:    */   protected Map<String, Header> parseChallenges(Header[] headers)
/*  33:    */     throws MalformedChallengeException
/*  34:    */   {
/*  35: 80 */     Map<String, Header> map = new HashMap(headers.length);
/*  36: 81 */     for (Header header : headers)
/*  37:    */     {
/*  38:    */       int pos;
/*  39:    */       CharArrayBuffer buffer;
/*  40:    */       int pos;
/*  41: 84 */       if ((header instanceof FormattedHeader))
/*  42:    */       {
/*  43: 85 */         CharArrayBuffer buffer = ((FormattedHeader)header).getBuffer();
/*  44: 86 */         pos = ((FormattedHeader)header).getValuePos();
/*  45:    */       }
/*  46:    */       else
/*  47:    */       {
/*  48: 88 */         String s = header.getValue();
/*  49: 89 */         if (s == null) {
/*  50: 90 */           throw new MalformedChallengeException("Header value is null");
/*  51:    */         }
/*  52: 92 */         buffer = new CharArrayBuffer(s.length());
/*  53: 93 */         buffer.append(s);
/*  54: 94 */         pos = 0;
/*  55:    */       }
/*  56: 96 */       while ((pos < buffer.length()) && (HTTP.isWhitespace(buffer.charAt(pos)))) {
/*  57: 97 */         pos++;
/*  58:    */       }
/*  59: 99 */       int beginIndex = pos;
/*  60:100 */       while ((pos < buffer.length()) && (!HTTP.isWhitespace(buffer.charAt(pos)))) {
/*  61:101 */         pos++;
/*  62:    */       }
/*  63:103 */       int endIndex = pos;
/*  64:104 */       String s = buffer.substring(beginIndex, endIndex);
/*  65:105 */       map.put(s.toLowerCase(Locale.ENGLISH), header);
/*  66:    */     }
/*  67:107 */     return map;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected List<String> getAuthPreferences()
/*  71:    */   {
/*  72:116 */     return DEFAULT_SCHEME_PRIORITY;
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected List<String> getAuthPreferences(HttpResponse response, HttpContext context)
/*  76:    */   {
/*  77:131 */     return getAuthPreferences();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public AuthScheme selectScheme(Map<String, Header> challenges, HttpResponse response, HttpContext context)
/*  81:    */     throws AuthenticationException
/*  82:    */   {
/*  83:139 */     AuthSchemeRegistry registry = (AuthSchemeRegistry)context.getAttribute("http.authscheme-registry");
/*  84:141 */     if (registry == null) {
/*  85:142 */       throw new IllegalStateException("AuthScheme registry not set in HTTP context");
/*  86:    */     }
/*  87:145 */     Collection<String> authPrefs = getAuthPreferences(response, context);
/*  88:146 */     if (authPrefs == null) {
/*  89:147 */       authPrefs = DEFAULT_SCHEME_PRIORITY;
/*  90:    */     }
/*  91:150 */     if (this.log.isDebugEnabled()) {
/*  92:151 */       this.log.debug("Authentication schemes in the order of preference: " + authPrefs);
/*  93:    */     }
/*  94:155 */     AuthScheme authScheme = null;
/*  95:156 */     for (String id : authPrefs)
/*  96:    */     {
/*  97:157 */       Header challenge = (Header)challenges.get(id.toLowerCase(Locale.ENGLISH));
/*  98:159 */       if (challenge != null)
/*  99:    */       {
/* 100:160 */         if (this.log.isDebugEnabled()) {
/* 101:161 */           this.log.debug(id + " authentication scheme selected");
/* 102:    */         }
/* 103:    */         try
/* 104:    */         {
/* 105:164 */           authScheme = registry.getAuthScheme(id, response.getParams());
/* 106:    */         }
/* 107:    */         catch (IllegalStateException e)
/* 108:    */         {
/* 109:167 */           if (this.log.isWarnEnabled()) {
/* 110:168 */             this.log.warn("Authentication scheme " + id + " not supported");
/* 111:    */           }
/* 112:    */           break label301;
/* 113:    */         }
/* 114:    */       }
/* 115:173 */       else if (this.log.isDebugEnabled())
/* 116:    */       {
/* 117:174 */         this.log.debug("Challenge for " + id + " authentication scheme not available");
/* 118:    */       }
/* 119:    */     }
/* 120:    */     label301:
/* 121:179 */     if (authScheme == null) {
/* 122:181 */       throw new AuthenticationException("Unable to respond to any of these challenges: " + challenges);
/* 123:    */     }
/* 124:185 */     return authScheme;
/* 125:    */   }
/* 126:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.client.AbstractAuthenticationHandler
 * JD-Core Version:    0.7.0.1
 */