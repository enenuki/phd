/*   1:    */ package org.apache.commons.io.output;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileNotFoundException;
/*   5:    */ import java.io.FileOutputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.OutputStream;
/*   8:    */ import java.io.OutputStreamWriter;
/*   9:    */ import java.io.StringWriter;
/*  10:    */ import java.io.Writer;
/*  11:    */ import java.util.regex.Matcher;
/*  12:    */ import java.util.regex.Pattern;
/*  13:    */ import org.apache.commons.io.input.XmlStreamReader;
/*  14:    */ 
/*  15:    */ public class XmlStreamWriter
/*  16:    */   extends Writer
/*  17:    */ {
/*  18:    */   private static final int BUFFER_SIZE = 4096;
/*  19:    */   private final OutputStream out;
/*  20:    */   private final String defaultEncoding;
/*  21: 48 */   private StringWriter xmlPrologWriter = new StringWriter(4096);
/*  22:    */   private Writer writer;
/*  23:    */   private String encoding;
/*  24:    */   
/*  25:    */   public XmlStreamWriter(OutputStream out)
/*  26:    */   {
/*  27: 61 */     this(out, null);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public XmlStreamWriter(OutputStream out, String defaultEncoding)
/*  31:    */   {
/*  32: 72 */     this.out = out;
/*  33: 73 */     this.defaultEncoding = (defaultEncoding != null ? defaultEncoding : "UTF-8");
/*  34:    */   }
/*  35:    */   
/*  36:    */   public XmlStreamWriter(File file)
/*  37:    */     throws FileNotFoundException
/*  38:    */   {
/*  39: 85 */     this(file, null);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public XmlStreamWriter(File file, String defaultEncoding)
/*  43:    */     throws FileNotFoundException
/*  44:    */   {
/*  45: 98 */     this(new FileOutputStream(file), defaultEncoding);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getEncoding()
/*  49:    */   {
/*  50:107 */     return this.encoding;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getDefaultEncoding()
/*  54:    */   {
/*  55:116 */     return this.defaultEncoding;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void close()
/*  59:    */     throws IOException
/*  60:    */   {
/*  61:126 */     if (this.writer == null)
/*  62:    */     {
/*  63:127 */       this.encoding = this.defaultEncoding;
/*  64:128 */       this.writer = new OutputStreamWriter(this.out, this.encoding);
/*  65:129 */       this.writer.write(this.xmlPrologWriter.toString());
/*  66:    */     }
/*  67:131 */     this.writer.close();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void flush()
/*  71:    */     throws IOException
/*  72:    */   {
/*  73:141 */     if (this.writer != null) {
/*  74:142 */       this.writer.flush();
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   private void detectEncoding(char[] cbuf, int off, int len)
/*  79:    */     throws IOException
/*  80:    */   {
/*  81:156 */     int size = len;
/*  82:157 */     StringBuffer xmlProlog = this.xmlPrologWriter.getBuffer();
/*  83:158 */     if (xmlProlog.length() + len > 4096) {
/*  84:159 */       size = 4096 - xmlProlog.length();
/*  85:    */     }
/*  86:161 */     this.xmlPrologWriter.write(cbuf, off, size);
/*  87:164 */     if (xmlProlog.length() >= 5)
/*  88:    */     {
/*  89:165 */       if (xmlProlog.substring(0, 5).equals("<?xml"))
/*  90:    */       {
/*  91:167 */         int xmlPrologEnd = xmlProlog.indexOf("?>");
/*  92:168 */         if (xmlPrologEnd > 0)
/*  93:    */         {
/*  94:170 */           Matcher m = ENCODING_PATTERN.matcher(xmlProlog.substring(0, xmlPrologEnd));
/*  95:172 */           if (m.find())
/*  96:    */           {
/*  97:173 */             this.encoding = m.group(1).toUpperCase();
/*  98:174 */             this.encoding = this.encoding.substring(1, this.encoding.length() - 1);
/*  99:    */           }
/* 100:    */           else
/* 101:    */           {
/* 102:178 */             this.encoding = this.defaultEncoding;
/* 103:    */           }
/* 104:    */         }
/* 105:181 */         else if (xmlProlog.length() >= 4096)
/* 106:    */         {
/* 107:184 */           this.encoding = this.defaultEncoding;
/* 108:    */         }
/* 109:    */       }
/* 110:    */       else
/* 111:    */       {
/* 112:189 */         this.encoding = this.defaultEncoding;
/* 113:    */       }
/* 114:191 */       if (this.encoding != null)
/* 115:    */       {
/* 116:193 */         this.xmlPrologWriter = null;
/* 117:194 */         this.writer = new OutputStreamWriter(this.out, this.encoding);
/* 118:195 */         this.writer.write(xmlProlog.toString());
/* 119:196 */         if (len > size) {
/* 120:197 */           this.writer.write(cbuf, off + size, len - size);
/* 121:    */         }
/* 122:    */       }
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void write(char[] cbuf, int off, int len)
/* 127:    */     throws IOException
/* 128:    */   {
/* 129:213 */     if (this.xmlPrologWriter != null) {
/* 130:214 */       detectEncoding(cbuf, off, len);
/* 131:    */     } else {
/* 132:216 */       this.writer.write(cbuf, off, len);
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:220 */   static final Pattern ENCODING_PATTERN = XmlStreamReader.ENCODING_PATTERN;
/* 137:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.XmlStreamWriter
 * JD-Core Version:    0.7.0.1
 */