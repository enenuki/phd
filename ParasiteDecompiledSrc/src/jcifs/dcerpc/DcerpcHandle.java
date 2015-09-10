/*   1:    */ package jcifs.dcerpc;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.MalformedURLException;
/*   5:    */ import java.net.UnknownHostException;
/*   6:    */ import java.security.Principal;
/*   7:    */ import jcifs.dcerpc.ndr.NdrBuffer;
/*   8:    */ import jcifs.smb.BufferCache;
/*   9:    */ import jcifs.smb.NtlmPasswordAuthentication;
/*  10:    */ import jcifs.smb.SmbNamedPipe;
/*  11:    */ 
/*  12:    */ public abstract class DcerpcHandle
/*  13:    */   implements DcerpcConstants
/*  14:    */ {
/*  15:    */   protected DcerpcBinding binding;
/*  16:    */   
/*  17:    */   protected static DcerpcBinding parseBinding(String str)
/*  18:    */     throws DcerpcException
/*  19:    */   {
/*  20: 48 */     char[] arr = str.toCharArray();
/*  21: 49 */     String proto = null;String key = null;
/*  22: 50 */     DcerpcBinding binding = null;
/*  23:    */     int si;
/*  24:    */     int mark;
/*  25: 52 */     int state = mark = si = 0;
/*  26:    */     do
/*  27:    */     {
/*  28: 54 */       char ch = arr[si];
/*  29: 56 */       switch (state)
/*  30:    */       {
/*  31:    */       case 0: 
/*  32: 58 */         if (ch == ':')
/*  33:    */         {
/*  34: 59 */           proto = str.substring(mark, si);
/*  35: 60 */           mark = si + 1;
/*  36: 61 */           state = 1;
/*  37:    */         }
/*  38:    */         break;
/*  39:    */       case 1: 
/*  40: 65 */         if (ch == '\\') {
/*  41: 66 */           mark = si + 1;
/*  42:    */         } else {
/*  43: 69 */           state = 2;
/*  44:    */         }
/*  45:    */         break;
/*  46:    */       case 2: 
/*  47: 71 */         if (ch == '[')
/*  48:    */         {
/*  49: 72 */           String server = str.substring(mark, si).trim();
/*  50: 73 */           if (server.length() == 0) {
/*  51: 74 */             server = "127.0.0.1";
/*  52:    */           }
/*  53: 75 */           binding = new DcerpcBinding(proto, str.substring(mark, si));
/*  54: 76 */           mark = si + 1;
/*  55: 77 */           state = 5;
/*  56:    */         }
/*  57:    */         break;
/*  58:    */       case 5: 
/*  59: 81 */         if (ch == '=')
/*  60:    */         {
/*  61: 82 */           key = str.substring(mark, si).trim();
/*  62: 83 */           mark = si + 1;
/*  63:    */         }
/*  64: 84 */         else if ((ch == ',') || (ch == ']'))
/*  65:    */         {
/*  66: 85 */           String val = str.substring(mark, si).trim();
/*  67: 86 */           if (key == null) {
/*  68: 87 */             key = "endpoint";
/*  69:    */           }
/*  70: 88 */           binding.setOption(key, val);
/*  71: 89 */           key = null;
/*  72:    */         }
/*  73:    */         break;
/*  74:    */       case 3: 
/*  75:    */       case 4: 
/*  76:    */       default: 
/*  77: 93 */         si = arr.length;
/*  78:    */       }
/*  79: 96 */       si++;
/*  80: 97 */     } while (si < arr.length);
/*  81: 99 */     if ((binding == null) || (binding.endpoint == null)) {
/*  82:100 */       throw new DcerpcException("Invalid binding URL: " + str);
/*  83:    */     }
/*  84:102 */     return binding;
/*  85:    */   }
/*  86:    */   
/*  87:106 */   protected int max_xmit = 4280;
/*  88:107 */   protected int max_recv = this.max_xmit;
/*  89:108 */   protected int state = 0;
/*  90:109 */   protected DcerpcSecurityProvider securityProvider = null;
/*  91:110 */   private static int call_id = 1;
/*  92:    */   
/*  93:    */   public static DcerpcHandle getHandle(String url, NtlmPasswordAuthentication auth)
/*  94:    */     throws UnknownHostException, MalformedURLException, DcerpcException
/*  95:    */   {
/*  96:115 */     if (url.startsWith("ncacn_np:")) {
/*  97:116 */       return new DcerpcPipeHandle(url, auth);
/*  98:    */     }
/*  99:118 */     throw new DcerpcException("DCERPC transport not supported: " + url);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void bind()
/* 103:    */     throws DcerpcException, IOException
/* 104:    */   {
/* 105:122 */     synchronized (this)
/* 106:    */     {
/* 107:    */       try
/* 108:    */       {
/* 109:124 */         this.state = 1;
/* 110:125 */         DcerpcMessage bind = new DcerpcBind(this.binding, this);
/* 111:126 */         sendrecv(bind);
/* 112:    */       }
/* 113:    */       catch (IOException ioe)
/* 114:    */       {
/* 115:128 */         this.state = 0;
/* 116:129 */         throw ioe;
/* 117:    */       }
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void sendrecv(DcerpcMessage msg)
/* 122:    */     throws DcerpcException, IOException
/* 123:    */   {
/* 124:139 */     if (this.state == 0) {
/* 125:140 */       bind();
/* 126:    */     }
/* 127:143 */     boolean isDirect = true;
/* 128:    */     
/* 129:145 */     byte[] stub = BufferCache.getBuffer();
/* 130:    */     try
/* 131:    */     {
/* 132:149 */       NdrBuffer buf = new NdrBuffer(stub, 0);
/* 133:    */       
/* 134:151 */       msg.flags = 3;
/* 135:152 */       msg.call_id = (call_id++);
/* 136:    */       
/* 137:154 */       msg.encode(buf);
/* 138:156 */       if (this.securityProvider != null)
/* 139:    */       {
/* 140:157 */         buf.setIndex(0);
/* 141:158 */         this.securityProvider.wrap(buf);
/* 142:    */       }
/* 143:161 */       int tot = buf.getLength() - 24;
/* 144:162 */       int off = 0;
/* 145:164 */       while (off < tot)
/* 146:    */       {
/* 147:165 */         int n = tot - off;
/* 148:167 */         if (24 + n > this.max_xmit)
/* 149:    */         {
/* 150:168 */           msg.flags &= 0xFFFFFFFD;
/* 151:169 */           n = this.max_xmit - 24;
/* 152:    */         }
/* 153:    */         else
/* 154:    */         {
/* 155:171 */           msg.flags |= 0x2;
/* 156:172 */           isDirect = false;
/* 157:173 */           msg.alloc_hint = n;
/* 158:    */         }
/* 159:176 */         msg.length = (24 + n);
/* 160:178 */         if (off > 0) {
/* 161:179 */           msg.flags &= 0xFFFFFFFE;
/* 162:    */         }
/* 163:181 */         if ((msg.flags & 0x3) != 3)
/* 164:    */         {
/* 165:182 */           buf.start = off;
/* 166:183 */           buf.reset();
/* 167:184 */           msg.encode_header(buf);
/* 168:185 */           buf.enc_ndr_long(msg.alloc_hint);
/* 169:186 */           buf.enc_ndr_short(0);
/* 170:187 */           buf.enc_ndr_short(msg.getOpnum());
/* 171:    */         }
/* 172:190 */         doSendFragment(stub, off, msg.length, isDirect);
/* 173:191 */         off += n;
/* 174:    */       }
/* 175:194 */       doReceiveFragment(stub, isDirect);
/* 176:195 */       buf.reset();
/* 177:196 */       buf.setIndex(8);
/* 178:197 */       buf.setLength(buf.dec_ndr_short());
/* 179:199 */       if (this.securityProvider != null) {
/* 180:200 */         this.securityProvider.unwrap(buf);
/* 181:    */       }
/* 182:202 */       buf.setIndex(0);
/* 183:    */       
/* 184:204 */       msg.decode_header(buf);
/* 185:    */       
/* 186:206 */       off = 24;
/* 187:207 */       if ((msg.ptype == 2) && (!msg.isFlagSet(2))) {
/* 188:208 */         off = msg.length;
/* 189:    */       }
/* 190:210 */       byte[] frag = null;
/* 191:211 */       NdrBuffer fbuf = null;
/* 192:212 */       while (!msg.isFlagSet(2))
/* 193:    */       {
/* 194:215 */         if (frag == null)
/* 195:    */         {
/* 196:216 */           frag = new byte[this.max_recv];
/* 197:217 */           fbuf = new NdrBuffer(frag, 0);
/* 198:    */         }
/* 199:220 */         doReceiveFragment(frag, isDirect);
/* 200:221 */         fbuf.reset();
/* 201:222 */         fbuf.setIndex(8);
/* 202:223 */         fbuf.setLength(fbuf.dec_ndr_short());
/* 203:225 */         if (this.securityProvider != null) {
/* 204:226 */           this.securityProvider.unwrap(fbuf);
/* 205:    */         }
/* 206:228 */         fbuf.reset();
/* 207:229 */         msg.decode_header(fbuf);
/* 208:230 */         int stub_frag_len = msg.length - 24;
/* 209:232 */         if (off + stub_frag_len > stub.length)
/* 210:    */         {
/* 211:234 */           byte[] tmp = new byte[off + stub_frag_len];
/* 212:235 */           System.arraycopy(stub, 0, tmp, 0, off);
/* 213:236 */           stub = tmp;
/* 214:    */         }
/* 215:239 */         System.arraycopy(frag, 24, stub, off, stub_frag_len);
/* 216:240 */         off += stub_frag_len;
/* 217:    */       }
/* 218:243 */       buf = new NdrBuffer(stub, 0);
/* 219:244 */       msg.decode(buf);
/* 220:    */     }
/* 221:    */     finally
/* 222:    */     {
/* 223:246 */       BufferCache.releaseBuffer(stub);
/* 224:    */     }
/* 225:    */     NdrBuffer fbuf;
/* 226:    */     NdrBuffer buf;
/* 227:    */     byte[] frag;
/* 228:    */     DcerpcException de;
/* 229:249 */     if ((de = msg.getResult()) != null) {
/* 230:250 */       throw de;
/* 231:    */     }
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void setDcerpcSecurityProvider(DcerpcSecurityProvider securityProvider)
/* 235:    */   {
/* 236:255 */     this.securityProvider = securityProvider;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public String getServer()
/* 240:    */   {
/* 241:258 */     if ((this instanceof DcerpcPipeHandle)) {
/* 242:259 */       return ((DcerpcPipeHandle)this).pipe.getServer();
/* 243:    */     }
/* 244:260 */     return null;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public Principal getPrincipal()
/* 248:    */   {
/* 249:263 */     if ((this instanceof DcerpcPipeHandle)) {
/* 250:264 */       return ((DcerpcPipeHandle)this).pipe.getPrincipal();
/* 251:    */     }
/* 252:265 */     return null;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public String toString()
/* 256:    */   {
/* 257:268 */     return this.binding.toString();
/* 258:    */   }
/* 259:    */   
/* 260:    */   protected abstract void doSendFragment(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
/* 261:    */     throws IOException;
/* 262:    */   
/* 263:    */   protected abstract void doReceiveFragment(byte[] paramArrayOfByte, boolean paramBoolean)
/* 264:    */     throws IOException;
/* 265:    */   
/* 266:    */   public abstract void close()
/* 267:    */     throws IOException;
/* 268:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.DcerpcHandle
 * JD-Core Version:    0.7.0.1
 */