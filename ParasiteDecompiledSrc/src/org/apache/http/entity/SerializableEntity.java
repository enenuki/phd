/*   1:    */ package org.apache.http.entity;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.io.ObjectOutputStream;
/*   8:    */ import java.io.OutputStream;
/*   9:    */ import java.io.Serializable;
/*  10:    */ 
/*  11:    */ public class SerializableEntity
/*  12:    */   extends AbstractHttpEntity
/*  13:    */ {
/*  14:    */   private byte[] objSer;
/*  15:    */   private Serializable objRef;
/*  16:    */   
/*  17:    */   public SerializableEntity(Serializable ser, boolean bufferize)
/*  18:    */     throws IOException
/*  19:    */   {
/*  20: 62 */     if (ser == null) {
/*  21: 63 */       throw new IllegalArgumentException("Source object may not be null");
/*  22:    */     }
/*  23: 66 */     if (bufferize) {
/*  24: 67 */       createBytes(ser);
/*  25:    */     } else {
/*  26: 69 */       this.objRef = ser;
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   private void createBytes(Serializable ser)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 74 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  34: 75 */     ObjectOutputStream out = new ObjectOutputStream(baos);
/*  35: 76 */     out.writeObject(ser);
/*  36: 77 */     out.flush();
/*  37: 78 */     this.objSer = baos.toByteArray();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public InputStream getContent()
/*  41:    */     throws IOException, IllegalStateException
/*  42:    */   {
/*  43: 82 */     if (this.objSer == null) {
/*  44: 83 */       createBytes(this.objRef);
/*  45:    */     }
/*  46: 85 */     return new ByteArrayInputStream(this.objSer);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public long getContentLength()
/*  50:    */   {
/*  51: 89 */     if (this.objSer == null) {
/*  52: 90 */       return -1L;
/*  53:    */     }
/*  54: 92 */     return this.objSer.length;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean isRepeatable()
/*  58:    */   {
/*  59: 97 */     return true;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean isStreaming()
/*  63:    */   {
/*  64:101 */     return this.objSer == null;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void writeTo(OutputStream outstream)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:105 */     if (outstream == null) {
/*  71:106 */       throw new IllegalArgumentException("Output stream may not be null");
/*  72:    */     }
/*  73:109 */     if (this.objSer == null)
/*  74:    */     {
/*  75:110 */       ObjectOutputStream out = new ObjectOutputStream(outstream);
/*  76:111 */       out.writeObject(this.objRef);
/*  77:112 */       out.flush();
/*  78:    */     }
/*  79:    */     else
/*  80:    */     {
/*  81:114 */       outstream.write(this.objSer);
/*  82:115 */       outstream.flush();
/*  83:    */     }
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.SerializableEntity
 * JD-Core Version:    0.7.0.1
 */