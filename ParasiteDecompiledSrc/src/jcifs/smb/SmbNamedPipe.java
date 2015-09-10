/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.net.MalformedURLException;
/*   7:    */ import java.net.URL;
/*   8:    */ import java.net.UnknownHostException;
/*   9:    */ 
/*  10:    */ public class SmbNamedPipe
/*  11:    */   extends SmbFile
/*  12:    */ {
/*  13:    */   public static final int PIPE_TYPE_RDONLY = 1;
/*  14:    */   public static final int PIPE_TYPE_WRONLY = 2;
/*  15:    */   public static final int PIPE_TYPE_RDWR = 3;
/*  16:    */   public static final int PIPE_TYPE_CALL = 256;
/*  17:    */   public static final int PIPE_TYPE_TRANSACT = 512;
/*  18:    */   public static final int PIPE_TYPE_DCE_TRANSACT = 1536;
/*  19:    */   InputStream pipeIn;
/*  20:    */   OutputStream pipeOut;
/*  21:    */   int pipeType;
/*  22:    */   
/*  23:    */   public SmbNamedPipe(String url, int pipeType)
/*  24:    */     throws MalformedURLException, UnknownHostException
/*  25:    */   {
/*  26:134 */     super(url);
/*  27:135 */     this.pipeType = pipeType;
/*  28:136 */     this.type = 16;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public SmbNamedPipe(String url, int pipeType, NtlmPasswordAuthentication auth)
/*  32:    */     throws MalformedURLException, UnknownHostException
/*  33:    */   {
/*  34:140 */     super(url, auth);
/*  35:141 */     this.pipeType = pipeType;
/*  36:142 */     this.type = 16;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public SmbNamedPipe(URL url, int pipeType, NtlmPasswordAuthentication auth)
/*  40:    */     throws MalformedURLException, UnknownHostException
/*  41:    */   {
/*  42:146 */     super(url, auth);
/*  43:147 */     this.pipeType = pipeType;
/*  44:148 */     this.type = 16;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public InputStream getNamedPipeInputStream()
/*  48:    */     throws IOException
/*  49:    */   {
/*  50:163 */     if (this.pipeIn == null) {
/*  51:164 */       if (((this.pipeType & 0x100) == 256) || ((this.pipeType & 0x200) == 512)) {
/*  52:166 */         this.pipeIn = new TransactNamedPipeInputStream(this);
/*  53:    */       } else {
/*  54:168 */         this.pipeIn = new SmbFileInputStream(this, this.pipeType & 0xFFFF00FF | 0x20);
/*  55:    */       }
/*  56:    */     }
/*  57:172 */     return this.pipeIn;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public OutputStream getNamedPipeOutputStream()
/*  61:    */     throws IOException
/*  62:    */   {
/*  63:184 */     if (this.pipeOut == null) {
/*  64:185 */       if (((this.pipeType & 0x100) == 256) || ((this.pipeType & 0x200) == 512)) {
/*  65:187 */         this.pipeOut = new TransactNamedPipeOutputStream(this);
/*  66:    */       } else {
/*  67:189 */         this.pipeOut = new SmbFileOutputStream(this, false, this.pipeType & 0xFFFF00FF | 0x20);
/*  68:    */       }
/*  69:    */     }
/*  70:193 */     return this.pipeOut;
/*  71:    */   }
/*  72:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbNamedPipe
 * JD-Core Version:    0.7.0.1
 */