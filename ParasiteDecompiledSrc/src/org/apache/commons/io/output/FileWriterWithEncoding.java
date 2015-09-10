/*   1:    */ package org.apache.commons.io.output;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.OutputStream;
/*   7:    */ import java.io.OutputStreamWriter;
/*   8:    */ import java.io.Writer;
/*   9:    */ import java.nio.charset.Charset;
/*  10:    */ import java.nio.charset.CharsetEncoder;
/*  11:    */ import org.apache.commons.io.FileUtils;
/*  12:    */ import org.apache.commons.io.IOUtils;
/*  13:    */ 
/*  14:    */ public class FileWriterWithEncoding
/*  15:    */   extends Writer
/*  16:    */ {
/*  17:    */   private final Writer out;
/*  18:    */   
/*  19:    */   public FileWriterWithEncoding(String filename, String encoding)
/*  20:    */     throws IOException
/*  21:    */   {
/*  22: 66 */     this(new File(filename), encoding, false);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public FileWriterWithEncoding(String filename, String encoding, boolean append)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28: 79 */     this(new File(filename), encoding, append);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public FileWriterWithEncoding(String filename, Charset encoding)
/*  32:    */     throws IOException
/*  33:    */   {
/*  34: 91 */     this(new File(filename), encoding, false);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public FileWriterWithEncoding(String filename, Charset encoding, boolean append)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40:104 */     this(new File(filename), encoding, append);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public FileWriterWithEncoding(String filename, CharsetEncoder encoding)
/*  44:    */     throws IOException
/*  45:    */   {
/*  46:116 */     this(new File(filename), encoding, false);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public FileWriterWithEncoding(String filename, CharsetEncoder encoding, boolean append)
/*  50:    */     throws IOException
/*  51:    */   {
/*  52:129 */     this(new File(filename), encoding, append);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public FileWriterWithEncoding(File file, String encoding)
/*  56:    */     throws IOException
/*  57:    */   {
/*  58:141 */     this(file, encoding, false);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public FileWriterWithEncoding(File file, String encoding, boolean append)
/*  62:    */     throws IOException
/*  63:    */   {
/*  64:155 */     this.out = initWriter(file, encoding, append);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public FileWriterWithEncoding(File file, Charset encoding)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:167 */     this(file, encoding, false);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public FileWriterWithEncoding(File file, Charset encoding, boolean append)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:181 */     this.out = initWriter(file, encoding, append);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public FileWriterWithEncoding(File file, CharsetEncoder encoding)
/*  80:    */     throws IOException
/*  81:    */   {
/*  82:193 */     this(file, encoding, false);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public FileWriterWithEncoding(File file, CharsetEncoder encoding, boolean append)
/*  86:    */     throws IOException
/*  87:    */   {
/*  88:207 */     this.out = initWriter(file, encoding, append);
/*  89:    */   }
/*  90:    */   
/*  91:    */   private static Writer initWriter(File file, Object encoding, boolean append)
/*  92:    */     throws IOException
/*  93:    */   {
/*  94:223 */     if (file == null) {
/*  95:224 */       throw new NullPointerException("File is missing");
/*  96:    */     }
/*  97:226 */     if (encoding == null) {
/*  98:227 */       throw new NullPointerException("Encoding is missing");
/*  99:    */     }
/* 100:229 */     boolean fileExistedAlready = file.exists();
/* 101:230 */     OutputStream stream = null;
/* 102:231 */     Writer writer = null;
/* 103:    */     try
/* 104:    */     {
/* 105:233 */       stream = new FileOutputStream(file, append);
/* 106:234 */       if ((encoding instanceof Charset)) {
/* 107:235 */         writer = new OutputStreamWriter(stream, (Charset)encoding);
/* 108:236 */       } else if ((encoding instanceof CharsetEncoder)) {
/* 109:237 */         writer = new OutputStreamWriter(stream, (CharsetEncoder)encoding);
/* 110:    */       } else {
/* 111:239 */         writer = new OutputStreamWriter(stream, (String)encoding);
/* 112:    */       }
/* 113:    */     }
/* 114:    */     catch (IOException ex)
/* 115:    */     {
/* 116:242 */       IOUtils.closeQuietly(writer);
/* 117:243 */       IOUtils.closeQuietly(stream);
/* 118:244 */       if (!fileExistedAlready) {
/* 119:245 */         FileUtils.deleteQuietly(file);
/* 120:    */       }
/* 121:247 */       throw ex;
/* 122:    */     }
/* 123:    */     catch (RuntimeException ex)
/* 124:    */     {
/* 125:249 */       IOUtils.closeQuietly(writer);
/* 126:250 */       IOUtils.closeQuietly(stream);
/* 127:251 */       if (!fileExistedAlready) {
/* 128:252 */         FileUtils.deleteQuietly(file);
/* 129:    */       }
/* 130:254 */       throw ex;
/* 131:    */     }
/* 132:256 */     return writer;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void write(int idx)
/* 136:    */     throws IOException
/* 137:    */   {
/* 138:267 */     this.out.write(idx);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void write(char[] chr)
/* 142:    */     throws IOException
/* 143:    */   {
/* 144:277 */     this.out.write(chr);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void write(char[] chr, int st, int end)
/* 148:    */     throws IOException
/* 149:    */   {
/* 150:289 */     this.out.write(chr, st, end);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void write(String str)
/* 154:    */     throws IOException
/* 155:    */   {
/* 156:299 */     this.out.write(str);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void write(String str, int st, int end)
/* 160:    */     throws IOException
/* 161:    */   {
/* 162:311 */     this.out.write(str, st, end);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void flush()
/* 166:    */     throws IOException
/* 167:    */   {
/* 168:320 */     this.out.flush();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void close()
/* 172:    */     throws IOException
/* 173:    */   {
/* 174:329 */     this.out.close();
/* 175:    */   }
/* 176:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.FileWriterWithEncoding
 * JD-Core Version:    0.7.0.1
 */