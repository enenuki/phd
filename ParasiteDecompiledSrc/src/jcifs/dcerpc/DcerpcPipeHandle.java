/*   1:    */ package jcifs.dcerpc;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.MalformedURLException;
/*   5:    */ import java.net.UnknownHostException;
/*   6:    */ import jcifs.smb.NtlmPasswordAuthentication;
/*   7:    */ import jcifs.smb.SmbFileInputStream;
/*   8:    */ import jcifs.smb.SmbFileOutputStream;
/*   9:    */ import jcifs.smb.SmbNamedPipe;
/*  10:    */ import jcifs.util.Encdec;
/*  11:    */ 
/*  12:    */ public class DcerpcPipeHandle
/*  13:    */   extends DcerpcHandle
/*  14:    */ {
/*  15:    */   SmbNamedPipe pipe;
/*  16: 32 */   SmbFileInputStream in = null;
/*  17: 33 */   SmbFileOutputStream out = null;
/*  18: 34 */   boolean isStart = true;
/*  19:    */   
/*  20:    */   public DcerpcPipeHandle(String url, NtlmPasswordAuthentication auth)
/*  21:    */     throws UnknownHostException, MalformedURLException, DcerpcException
/*  22:    */   {
/*  23: 39 */     this.binding = DcerpcHandle.parseBinding(url);
/*  24: 40 */     url = "smb://" + this.binding.server + "/IPC$/" + this.binding.endpoint.substring(6);
/*  25:    */     
/*  26: 42 */     String params = "";
/*  27: 43 */     String server = (String)this.binding.getOption("server");
/*  28: 44 */     if (server != null) {
/*  29: 45 */       params = params + "&server=" + server;
/*  30:    */     }
/*  31: 46 */     String address = (String)this.binding.getOption("address");
/*  32: 47 */     if (server != null) {
/*  33: 48 */       params = params + "&address=" + address;
/*  34:    */     }
/*  35: 49 */     if (params.length() > 0) {
/*  36: 50 */       url = url + "?" + params.substring(1);
/*  37:    */     }
/*  38: 52 */     this.pipe = new SmbNamedPipe(url, 27198979, auth);
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected void doSendFragment(byte[] buf, int off, int length, boolean isDirect)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44: 62 */     if ((this.out != null) && (!this.out.isOpen())) {
/*  45: 63 */       throw new IOException("DCERPC pipe is no longer open");
/*  46:    */     }
/*  47: 65 */     if (this.in == null) {
/*  48: 66 */       this.in = ((SmbFileInputStream)this.pipe.getNamedPipeInputStream());
/*  49:    */     }
/*  50: 67 */     if (this.out == null) {
/*  51: 68 */       this.out = ((SmbFileOutputStream)this.pipe.getNamedPipeOutputStream());
/*  52:    */     }
/*  53: 69 */     if (isDirect)
/*  54:    */     {
/*  55: 70 */       this.out.writeDirect(buf, off, length, 1);
/*  56: 71 */       return;
/*  57:    */     }
/*  58: 73 */     this.out.write(buf, off, length);
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected void doReceiveFragment(byte[] buf, boolean isDirect)
/*  62:    */     throws IOException
/*  63:    */   {
/*  64: 78 */     if (buf.length < this.max_recv) {
/*  65: 79 */       throw new IllegalArgumentException("buffer too small");
/*  66:    */     }
/*  67:    */     int off;
/*  68:    */     int off;
/*  69: 81 */     if ((this.isStart) && (!isDirect)) {
/*  70: 82 */       off = this.in.read(buf, 0, 1024);
/*  71:    */     } else {
/*  72: 84 */       off = this.in.readDirect(buf, 0, buf.length);
/*  73:    */     }
/*  74: 87 */     if ((buf[0] != 5) && (buf[1] != 0)) {
/*  75: 88 */       throw new IOException("Unexpected DCERPC PDU header");
/*  76:    */     }
/*  77: 90 */     int flags = buf[3] & 0xFF;
/*  78:    */     
/*  79: 92 */     this.isStart = ((flags & 0x2) == 2);
/*  80:    */     
/*  81: 94 */     int length = Encdec.dec_uint16le(buf, 8);
/*  82: 95 */     if (length > this.max_recv) {
/*  83: 96 */       throw new IOException("Unexpected fragment length: " + length);
/*  84:    */     }
/*  85: 98 */     while (off < length) {
/*  86: 99 */       off += this.in.readDirect(buf, off, length - off);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void close()
/*  91:    */     throws IOException
/*  92:    */   {
/*  93:103 */     this.state = 0;
/*  94:104 */     if (this.out != null) {
/*  95:105 */       this.out.close();
/*  96:    */     }
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.DcerpcPipeHandle
 * JD-Core Version:    0.7.0.1
 */