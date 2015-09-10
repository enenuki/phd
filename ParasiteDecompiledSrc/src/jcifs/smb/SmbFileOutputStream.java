/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.net.MalformedURLException;
/*   6:    */ import java.net.UnknownHostException;
/*   7:    */ import jcifs.util.LogStream;
/*   8:    */ 
/*   9:    */ public class SmbFileOutputStream
/*  10:    */   extends OutputStream
/*  11:    */ {
/*  12:    */   private SmbFile file;
/*  13:    */   private boolean append;
/*  14:    */   private boolean useNTSmbs;
/*  15:    */   private int openFlags;
/*  16:    */   private int access;
/*  17:    */   private int writeSize;
/*  18:    */   private long fp;
/*  19: 38 */   private byte[] tmp = new byte[1];
/*  20:    */   private SmbComWriteAndX reqx;
/*  21:    */   private SmbComWriteAndXResponse rspx;
/*  22:    */   private SmbComWrite req;
/*  23:    */   private SmbComWriteResponse rsp;
/*  24:    */   
/*  25:    */   public SmbFileOutputStream(String url)
/*  26:    */     throws SmbException, MalformedURLException, UnknownHostException
/*  27:    */   {
/*  28: 54 */     this(url, false);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public SmbFileOutputStream(SmbFile file)
/*  32:    */     throws SmbException, MalformedURLException, UnknownHostException
/*  33:    */   {
/*  34: 67 */     this(file, false);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public SmbFileOutputStream(String url, boolean append)
/*  38:    */     throws SmbException, MalformedURLException, UnknownHostException
/*  39:    */   {
/*  40: 82 */     this(new SmbFile(url), append);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public SmbFileOutputStream(SmbFile file, boolean append)
/*  44:    */     throws SmbException, MalformedURLException, UnknownHostException
/*  45:    */   {
/*  46: 97 */     this(file, append, append ? 22 : 82);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public SmbFileOutputStream(String url, int shareAccess)
/*  50:    */     throws SmbException, MalformedURLException, UnknownHostException
/*  51:    */   {
/*  52:120 */     this(new SmbFile(url, "", null, shareAccess), false);
/*  53:    */   }
/*  54:    */   
/*  55:    */   SmbFileOutputStream(SmbFile file, boolean append, int openFlags)
/*  56:    */     throws SmbException, MalformedURLException, UnknownHostException
/*  57:    */   {
/*  58:124 */     this.file = file;
/*  59:125 */     this.append = append;
/*  60:126 */     this.openFlags = openFlags;
/*  61:127 */     this.access = (openFlags >>> 16 & 0xFFFF);
/*  62:128 */     if (append) {
/*  63:    */       try
/*  64:    */       {
/*  65:130 */         this.fp = file.length();
/*  66:    */       }
/*  67:    */       catch (SmbAuthException sae)
/*  68:    */       {
/*  69:132 */         throw sae;
/*  70:    */       }
/*  71:    */       catch (SmbException se)
/*  72:    */       {
/*  73:134 */         this.fp = 0L;
/*  74:    */       }
/*  75:    */     }
/*  76:137 */     if (((file instanceof SmbNamedPipe)) && (file.unc.startsWith("\\pipe\\")))
/*  77:    */     {
/*  78:138 */       file.unc = file.unc.substring(5);
/*  79:139 */       file.send(new TransWaitNamedPipe("\\pipe" + file.unc), new TransWaitNamedPipeResponse());
/*  80:    */     }
/*  81:142 */     file.open(openFlags, this.access | 0x2, 128, 0);
/*  82:143 */     this.openFlags &= 0xFFFFFFAF;
/*  83:144 */     this.writeSize = (file.tree.session.transport.snd_buf_size - 70);
/*  84:    */     
/*  85:146 */     this.useNTSmbs = file.tree.session.transport.hasCapability(16);
/*  86:147 */     if (this.useNTSmbs)
/*  87:    */     {
/*  88:148 */       this.reqx = new SmbComWriteAndX();
/*  89:149 */       this.rspx = new SmbComWriteAndXResponse();
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:151 */       this.req = new SmbComWrite();
/*  94:152 */       this.rsp = new SmbComWriteResponse();
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void close()
/*  99:    */     throws IOException
/* 100:    */   {
/* 101:164 */     this.file.close();
/* 102:165 */     this.tmp = null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void write(int b)
/* 106:    */     throws IOException
/* 107:    */   {
/* 108:175 */     this.tmp[0] = ((byte)b);
/* 109:176 */     write(this.tmp, 0, 1);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void write(byte[] b)
/* 113:    */     throws IOException
/* 114:    */   {
/* 115:187 */     write(b, 0, b.length);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean isOpen()
/* 119:    */   {
/* 120:192 */     return this.file.isOpen();
/* 121:    */   }
/* 122:    */   
/* 123:    */   void ensureOpen()
/* 124:    */     throws IOException
/* 125:    */   {
/* 126:196 */     if (!this.file.isOpen())
/* 127:    */     {
/* 128:197 */       this.file.open(this.openFlags, this.access | 0x2, 128, 0);
/* 129:198 */       if (this.append) {
/* 130:199 */         this.fp = this.file.length();
/* 131:    */       }
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void write(byte[] b, int off, int len)
/* 136:    */     throws IOException
/* 137:    */   {
/* 138:212 */     if ((!this.file.isOpen()) && ((this.file instanceof SmbNamedPipe))) {
/* 139:213 */       this.file.send(new TransWaitNamedPipe("\\pipe" + this.file.unc), new TransWaitNamedPipeResponse());
/* 140:    */     }
/* 141:216 */     writeDirect(b, off, len, 0);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void writeDirect(byte[] b, int off, int len, int flags)
/* 145:    */     throws IOException
/* 146:    */   {
/* 147:222 */     if (len <= 0) {
/* 148:223 */       return;
/* 149:    */     }
/* 150:226 */     if (this.tmp == null) {
/* 151:227 */       throw new IOException("Bad file descriptor");
/* 152:    */     }
/* 153:229 */     ensureOpen();
/* 154:231 */     if (LogStream.level >= 4) {
/* 155:232 */       SmbFile.log.println("write: fid=" + this.file.fid + ",off=" + off + ",len=" + len);
/* 156:    */     }
/* 157:    */     do
/* 158:    */     {
/* 159:236 */       int w = len > this.writeSize ? this.writeSize : len;
/* 160:237 */       if (this.useNTSmbs)
/* 161:    */       {
/* 162:238 */         this.reqx.setParam(this.file.fid, this.fp, len - w, b, off, w);
/* 163:239 */         if ((flags & 0x1) != 0)
/* 164:    */         {
/* 165:240 */           this.reqx.setParam(this.file.fid, this.fp, len, b, off, w);
/* 166:241 */           this.reqx.writeMode = 8;
/* 167:    */         }
/* 168:    */         else
/* 169:    */         {
/* 170:243 */           this.reqx.writeMode = 0;
/* 171:    */         }
/* 172:245 */         this.file.send(this.reqx, this.rspx);
/* 173:246 */         this.fp += this.rspx.count;
/* 174:247 */         len = (int)(len - this.rspx.count);
/* 175:248 */         off = (int)(off + this.rspx.count);
/* 176:    */       }
/* 177:    */       else
/* 178:    */       {
/* 179:250 */         this.req.setParam(this.file.fid, this.fp, len - w, b, off, w);
/* 180:251 */         this.fp += this.rsp.count;
/* 181:252 */         len = (int)(len - this.rsp.count);
/* 182:253 */         off = (int)(off + this.rsp.count);
/* 183:254 */         this.file.send(this.req, this.rsp);
/* 184:    */       }
/* 185:256 */     } while (len > 0);
/* 186:    */   }
/* 187:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbFileOutputStream
 * JD-Core Version:    0.7.0.1
 */