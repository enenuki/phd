/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.InterruptedIOException;
/*   6:    */ import java.net.MalformedURLException;
/*   7:    */ import java.net.UnknownHostException;
/*   8:    */ import jcifs.util.LogStream;
/*   9:    */ import jcifs.util.transport.TransportException;
/*  10:    */ 
/*  11:    */ public class SmbFileInputStream
/*  12:    */   extends InputStream
/*  13:    */ {
/*  14:    */   private long fp;
/*  15:    */   private int readSize;
/*  16:    */   private int openFlags;
/*  17:    */   private int access;
/*  18: 38 */   private byte[] tmp = new byte[1];
/*  19:    */   SmbFile file;
/*  20:    */   
/*  21:    */   public SmbFileInputStream(String url)
/*  22:    */     throws SmbException, MalformedURLException, UnknownHostException
/*  23:    */   {
/*  24: 52 */     this(new SmbFile(url));
/*  25:    */   }
/*  26:    */   
/*  27:    */   public SmbFileInputStream(SmbFile file)
/*  28:    */     throws SmbException, MalformedURLException, UnknownHostException
/*  29:    */   {
/*  30: 65 */     this(file, 1);
/*  31:    */   }
/*  32:    */   
/*  33:    */   SmbFileInputStream(SmbFile file, int openFlags)
/*  34:    */     throws SmbException, MalformedURLException, UnknownHostException
/*  35:    */   {
/*  36: 69 */     this.file = file;
/*  37: 70 */     this.openFlags = (openFlags & 0xFFFF);
/*  38: 71 */     this.access = (openFlags >>> 16 & 0xFFFF);
/*  39: 72 */     if (file.type != 16)
/*  40:    */     {
/*  41: 73 */       file.open(openFlags, this.access, 128, 0);
/*  42: 74 */       this.openFlags &= 0xFFFFFFAF;
/*  43:    */     }
/*  44:    */     else
/*  45:    */     {
/*  46: 76 */       file.connect0();
/*  47:    */     }
/*  48: 78 */     this.readSize = Math.min(file.tree.session.transport.rcv_buf_size - 70, file.tree.session.transport.server.maxBufferSize - 70);
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected IOException seToIoe(SmbException se)
/*  52:    */   {
/*  53: 83 */     IOException ioe = se;
/*  54: 84 */     Throwable root = se.getRootCause();
/*  55: 85 */     if ((root instanceof TransportException))
/*  56:    */     {
/*  57: 86 */       ioe = (TransportException)root;
/*  58: 87 */       root = ((TransportException)ioe).getRootCause();
/*  59:    */     }
/*  60: 89 */     if ((root instanceof InterruptedException))
/*  61:    */     {
/*  62: 90 */       ioe = new InterruptedIOException(root.getMessage());
/*  63: 91 */       ioe.initCause(root);
/*  64:    */     }
/*  65: 93 */     return ioe;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void close()
/*  69:    */     throws IOException
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:104 */       this.file.close();
/*  74:105 */       this.tmp = null;
/*  75:    */     }
/*  76:    */     catch (SmbException se)
/*  77:    */     {
/*  78:107 */       throw seToIoe(se);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int read()
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:119 */     if (read(this.tmp, 0, 1) == -1) {
/*  86:120 */       return -1;
/*  87:    */     }
/*  88:122 */     return this.tmp[0] & 0xFF;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int read(byte[] b)
/*  92:    */     throws IOException
/*  93:    */   {
/*  94:132 */     return read(b, 0, b.length);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int read(byte[] b, int off, int len)
/*  98:    */     throws IOException
/*  99:    */   {
/* 100:142 */     return readDirect(b, off, len);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int readDirect(byte[] b, int off, int len)
/* 104:    */     throws IOException
/* 105:    */   {
/* 106:145 */     if (len <= 0) {
/* 107:146 */       return 0;
/* 108:    */     }
/* 109:148 */     long start = this.fp;
/* 110:150 */     if (this.tmp == null) {
/* 111:151 */       throw new IOException("Bad file descriptor");
/* 112:    */     }
/* 113:154 */     this.file.open(this.openFlags, this.access, 128, 0);
/* 114:160 */     if (LogStream.level >= 4) {
/* 115:161 */       SmbFile.log.println("read: fid=" + this.file.fid + ",off=" + off + ",len=" + len);
/* 116:    */     }
/* 117:163 */     SmbComReadAndXResponse response = new SmbComReadAndXResponse(b, off);
/* 118:165 */     if (this.file.type == 16) {
/* 119:166 */       response.responseTimeout = 0L;
/* 120:    */     }
/* 121:    */     int r;
/* 122:    */     int n;
/* 123:    */     do
/* 124:    */     {
/* 125:171 */       r = len > this.readSize ? this.readSize : len;
/* 126:173 */       if (LogStream.level >= 4) {
/* 127:174 */         SmbFile.log.println("read: len=" + len + ",r=" + r + ",fp=" + this.fp);
/* 128:    */       }
/* 129:    */       try
/* 130:    */       {
/* 131:177 */         SmbComReadAndX request = new SmbComReadAndX(this.file.fid, this.fp, r, null);
/* 132:178 */         if (this.file.type == 16) {
/* 133:179 */           request.minCount = (request.maxCount = request.remaining = 1024);
/* 134:    */         }
/* 135:181 */         this.file.send(request, response);
/* 136:    */       }
/* 137:    */       catch (SmbException se)
/* 138:    */       {
/* 139:183 */         if ((this.file.type == 16) && (se.getNtStatus() == -1073741493)) {
/* 140:185 */           return -1;
/* 141:    */         }
/* 142:187 */         throw seToIoe(se);
/* 143:    */       }
/* 144:189 */       if ((n = response.dataLength) <= 0) {
/* 145:190 */         return (int)(this.fp - start > 0L ? this.fp - start : -1L);
/* 146:    */       }
/* 147:192 */       this.fp += n;
/* 148:193 */       len -= n;
/* 149:194 */       response.off += n;
/* 150:195 */     } while ((len > 0) && (n == r));
/* 151:197 */     return (int)(this.fp - start);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public int available()
/* 155:    */     throws IOException
/* 156:    */   {
/* 157:211 */     if (this.file.type != 16) {
/* 158:212 */       return 0;
/* 159:    */     }
/* 160:    */     try
/* 161:    */     {
/* 162:216 */       SmbNamedPipe pipe = (SmbNamedPipe)this.file;
/* 163:217 */       this.file.open(32, pipe.pipeType & 0xFF0000, 128, 0);
/* 164:    */       
/* 165:219 */       TransPeekNamedPipe req = new TransPeekNamedPipe(this.file.unc, this.file.fid);
/* 166:220 */       TransPeekNamedPipeResponse resp = new TransPeekNamedPipeResponse(pipe);
/* 167:    */       
/* 168:222 */       pipe.send(req, resp);
/* 169:223 */       if ((resp.status == 1) || (resp.status == 4))
/* 170:    */       {
/* 171:225 */         this.file.opened = false;
/* 172:226 */         return 0;
/* 173:    */       }
/* 174:228 */       return resp.available;
/* 175:    */     }
/* 176:    */     catch (SmbException se)
/* 177:    */     {
/* 178:230 */       throw seToIoe(se);
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public long skip(long n)
/* 183:    */     throws IOException
/* 184:    */   {
/* 185:240 */     if (n > 0L)
/* 186:    */     {
/* 187:241 */       this.fp += n;
/* 188:242 */       return n;
/* 189:    */     }
/* 190:244 */     return 0L;
/* 191:    */   }
/* 192:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbFileInputStream
 * JD-Core Version:    0.7.0.1
 */