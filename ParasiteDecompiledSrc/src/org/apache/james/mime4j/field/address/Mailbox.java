/*   1:    */ package org.apache.james.mime4j.field.address;
/*   2:    */ 
/*   3:    */ import java.io.StringReader;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Locale;
/*   7:    */ import org.apache.james.mime4j.codec.EncoderUtil;
/*   8:    */ import org.apache.james.mime4j.field.address.parser.AddressListParser;
/*   9:    */ import org.apache.james.mime4j.field.address.parser.ParseException;
/*  10:    */ 
/*  11:    */ public class Mailbox
/*  12:    */   extends Address
/*  13:    */ {
/*  14:    */   private static final long serialVersionUID = 1L;
/*  15: 38 */   private static final DomainList EMPTY_ROUTE_LIST = new DomainList(Collections.emptyList(), true);
/*  16:    */   private final String name;
/*  17:    */   private final DomainList route;
/*  18:    */   private final String localPart;
/*  19:    */   private final String domain;
/*  20:    */   
/*  21:    */   public Mailbox(String localPart, String domain)
/*  22:    */   {
/*  23: 55 */     this(null, null, localPart, domain);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Mailbox(DomainList route, String localPart, String domain)
/*  27:    */   {
/*  28: 70 */     this(null, route, localPart, domain);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Mailbox(String name, String localPart, String domain)
/*  32:    */   {
/*  33: 84 */     this(name, null, localPart, domain);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Mailbox(String name, DomainList route, String localPart, String domain)
/*  37:    */   {
/*  38:102 */     if ((localPart == null) || (localPart.length() == 0)) {
/*  39:103 */       throw new IllegalArgumentException();
/*  40:    */     }
/*  41:105 */     this.name = ((name == null) || (name.length() == 0) ? null : name);
/*  42:106 */     this.route = (route == null ? EMPTY_ROUTE_LIST : route);
/*  43:107 */     this.localPart = localPart;
/*  44:108 */     this.domain = ((domain == null) || (domain.length() == 0) ? null : domain);
/*  45:    */   }
/*  46:    */   
/*  47:    */   Mailbox(String name, Mailbox baseMailbox)
/*  48:    */   {
/*  49:116 */     this(name, baseMailbox.getRoute(), baseMailbox.getLocalPart(), baseMailbox.getDomain());
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static Mailbox parse(String rawMailboxString)
/*  53:    */   {
/*  54:131 */     AddressListParser parser = new AddressListParser(new StringReader(rawMailboxString));
/*  55:    */     try
/*  56:    */     {
/*  57:134 */       return Builder.getInstance().buildMailbox(parser.parseMailbox());
/*  58:    */     }
/*  59:    */     catch (ParseException e)
/*  60:    */     {
/*  61:136 */       throw new IllegalArgumentException(e);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getName()
/*  66:    */   {
/*  67:145 */     return this.name;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public DomainList getRoute()
/*  71:    */   {
/*  72:153 */     return this.route;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getLocalPart()
/*  76:    */   {
/*  77:160 */     return this.localPart;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getDomain()
/*  81:    */   {
/*  82:167 */     return this.domain;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getAddress()
/*  86:    */   {
/*  87:176 */     if (this.domain == null) {
/*  88:177 */       return this.localPart;
/*  89:    */     }
/*  90:179 */     return this.localPart + '@' + this.domain;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getDisplayString(boolean includeRoute)
/*  94:    */   {
/*  95:185 */     includeRoute &= this.route != null;
/*  96:186 */     boolean includeAngleBrackets = (this.name != null) || (includeRoute);
/*  97:    */     
/*  98:188 */     StringBuilder sb = new StringBuilder();
/*  99:190 */     if (this.name != null)
/* 100:    */     {
/* 101:191 */       sb.append(this.name);
/* 102:192 */       sb.append(' ');
/* 103:    */     }
/* 104:195 */     if (includeAngleBrackets) {
/* 105:196 */       sb.append('<');
/* 106:    */     }
/* 107:199 */     if (includeRoute)
/* 108:    */     {
/* 109:200 */       sb.append(this.route.toRouteString());
/* 110:201 */       sb.append(':');
/* 111:    */     }
/* 112:204 */     sb.append(this.localPart);
/* 113:206 */     if (this.domain != null)
/* 114:    */     {
/* 115:207 */       sb.append('@');
/* 116:208 */       sb.append(this.domain);
/* 117:    */     }
/* 118:211 */     if (includeAngleBrackets) {
/* 119:212 */       sb.append('>');
/* 120:    */     }
/* 121:215 */     return sb.toString();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getEncodedString()
/* 125:    */   {
/* 126:220 */     StringBuilder sb = new StringBuilder();
/* 127:222 */     if (this.name != null)
/* 128:    */     {
/* 129:223 */       sb.append(EncoderUtil.encodeAddressDisplayName(this.name));
/* 130:224 */       sb.append(" <");
/* 131:    */     }
/* 132:227 */     sb.append(EncoderUtil.encodeAddressLocalPart(this.localPart));
/* 133:232 */     if (this.domain != null)
/* 134:    */     {
/* 135:233 */       sb.append('@');
/* 136:234 */       sb.append(this.domain);
/* 137:    */     }
/* 138:237 */     if (this.name != null) {
/* 139:238 */       sb.append('>');
/* 140:    */     }
/* 141:241 */     return sb.toString();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int hashCode()
/* 145:    */   {
/* 146:246 */     return getCanonicalizedAddress().hashCode();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean equals(Object obj)
/* 150:    */   {
/* 151:265 */     if (obj == this) {
/* 152:266 */       return true;
/* 153:    */     }
/* 154:267 */     if (!(obj instanceof Mailbox)) {
/* 155:268 */       return false;
/* 156:    */     }
/* 157:270 */     Mailbox other = (Mailbox)obj;
/* 158:271 */     return getCanonicalizedAddress().equals(other.getCanonicalizedAddress());
/* 159:    */   }
/* 160:    */   
/* 161:    */   protected final void doAddMailboxesTo(List<Mailbox> results)
/* 162:    */   {
/* 163:277 */     results.add(this);
/* 164:    */   }
/* 165:    */   
/* 166:    */   private Object getCanonicalizedAddress()
/* 167:    */   {
/* 168:281 */     if (this.domain == null) {
/* 169:282 */       return this.localPart;
/* 170:    */     }
/* 171:284 */     return this.localPart + '@' + this.domain.toLowerCase(Locale.US);
/* 172:    */   }
/* 173:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.Mailbox
 * JD-Core Version:    0.7.0.1
 */