/*   1:    */ package org.apache.commons.io.output;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.io.Writer;
/*   6:    */ import java.nio.ByteBuffer;
/*   7:    */ import java.nio.CharBuffer;
/*   8:    */ import java.nio.charset.Charset;
/*   9:    */ import java.nio.charset.CharsetDecoder;
/*  10:    */ import java.nio.charset.CoderResult;
/*  11:    */ import java.nio.charset.CodingErrorAction;
/*  12:    */ 
/*  13:    */ public class WriterOutputStream
/*  14:    */   extends OutputStream
/*  15:    */ {
/*  16:    */   private static final int DEFAULT_BUFFER_SIZE = 1024;
/*  17:    */   private final Writer writer;
/*  18:    */   private final CharsetDecoder decoder;
/*  19:    */   private final boolean writeImmediately;
/*  20: 86 */   private final ByteBuffer decoderIn = ByteBuffer.allocate(128);
/*  21:    */   private final CharBuffer decoderOut;
/*  22:    */   
/*  23:    */   public WriterOutputStream(Writer writer, Charset charset, int bufferSize, boolean writeImmediately)
/*  24:    */   {
/*  25:108 */     this.writer = writer;
/*  26:109 */     this.decoder = charset.newDecoder();
/*  27:110 */     this.decoder.onMalformedInput(CodingErrorAction.REPLACE);
/*  28:111 */     this.decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
/*  29:112 */     this.decoder.replaceWith("?");
/*  30:113 */     this.writeImmediately = writeImmediately;
/*  31:114 */     this.decoderOut = CharBuffer.allocate(bufferSize);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public WriterOutputStream(Writer writer, Charset charset)
/*  35:    */   {
/*  36:126 */     this(writer, charset, 1024, false);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public WriterOutputStream(Writer writer, String charsetName, int bufferSize, boolean writeImmediately)
/*  40:    */   {
/*  41:142 */     this(writer, Charset.forName(charsetName), bufferSize, writeImmediately);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public WriterOutputStream(Writer writer, String charsetName)
/*  45:    */   {
/*  46:154 */     this(writer, charsetName, 1024, false);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public WriterOutputStream(Writer writer)
/*  50:    */   {
/*  51:165 */     this(writer, Charset.defaultCharset(), 1024, false);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void write(byte[] b, int off, int len)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57:178 */     while (len > 0)
/*  58:    */     {
/*  59:179 */       int c = Math.min(len, this.decoderIn.remaining());
/*  60:180 */       this.decoderIn.put(b, off, c);
/*  61:181 */       processInput(false);
/*  62:182 */       len -= c;
/*  63:183 */       off += c;
/*  64:    */     }
/*  65:185 */     if (this.writeImmediately) {
/*  66:186 */       flushOutput();
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void write(byte[] b)
/*  71:    */     throws IOException
/*  72:    */   {
/*  73:198 */     write(b, 0, b.length);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void write(int b)
/*  77:    */     throws IOException
/*  78:    */   {
/*  79:209 */     write(new byte[] { (byte)b }, 0, 1);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void flush()
/*  83:    */     throws IOException
/*  84:    */   {
/*  85:220 */     flushOutput();
/*  86:221 */     this.writer.flush();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void close()
/*  90:    */     throws IOException
/*  91:    */   {
/*  92:232 */     processInput(true);
/*  93:233 */     flushOutput();
/*  94:234 */     this.writer.close();
/*  95:    */   }
/*  96:    */   
/*  97:    */   private void processInput(boolean endOfInput)
/*  98:    */     throws IOException
/*  99:    */   {
/* 100:245 */     this.decoderIn.flip();
/* 101:    */     CoderResult coderResult;
/* 102:    */     for (;;)
/* 103:    */     {
/* 104:248 */       coderResult = this.decoder.decode(this.decoderIn, this.decoderOut, endOfInput);
/* 105:249 */       if (!coderResult.isOverflow()) {
/* 106:    */         break;
/* 107:    */       }
/* 108:250 */       flushOutput();
/* 109:    */     }
/* 110:251 */     if (!coderResult.isUnderflow()) {
/* 111:256 */       throw new IOException("Unexpected coder result");
/* 112:    */     }
/* 113:260 */     this.decoderIn.compact();
/* 114:    */   }
/* 115:    */   
/* 116:    */   private void flushOutput()
/* 117:    */     throws IOException
/* 118:    */   {
/* 119:269 */     if (this.decoderOut.position() > 0)
/* 120:    */     {
/* 121:270 */       this.writer.write(this.decoderOut.array(), 0, this.decoderOut.position());
/* 122:271 */       this.decoderOut.rewind();
/* 123:    */     }
/* 124:    */   }
/* 125:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.WriterOutputStream
 * JD-Core Version:    0.7.0.1
 */