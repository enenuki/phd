/*   1:    */ package org.apache.commons.io;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.InputStreamReader;
/*   7:    */ import java.io.OutputStream;
/*   8:    */ import java.io.OutputStreamWriter;
/*   9:    */ import java.io.Reader;
/*  10:    */ import java.io.StringReader;
/*  11:    */ import java.io.Writer;
/*  12:    */ 
/*  13:    */ @Deprecated
/*  14:    */ public class CopyUtils
/*  15:    */ {
/*  16:    */   private static final int DEFAULT_BUFFER_SIZE = 4096;
/*  17:    */   
/*  18:    */   public static void copy(byte[] input, OutputStream output)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21:139 */     output.write(input);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static void copy(byte[] input, Writer output)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27:156 */     ByteArrayInputStream in = new ByteArrayInputStream(input);
/*  28:157 */     copy(in, output);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static void copy(byte[] input, Writer output, String encoding)
/*  32:    */     throws IOException
/*  33:    */   {
/*  34:176 */     ByteArrayInputStream in = new ByteArrayInputStream(input);
/*  35:177 */     copy(in, output, encoding);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static int copy(InputStream input, OutputStream output)
/*  39:    */     throws IOException
/*  40:    */   {
/*  41:197 */     byte[] buffer = new byte[4096];
/*  42:198 */     int count = 0;
/*  43:199 */     int n = 0;
/*  44:200 */     while (-1 != (n = input.read(buffer)))
/*  45:    */     {
/*  46:201 */       output.write(buffer, 0, n);
/*  47:202 */       count += n;
/*  48:    */     }
/*  49:204 */     return count;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static int copy(Reader input, Writer output)
/*  53:    */     throws IOException
/*  54:    */   {
/*  55:222 */     char[] buffer = new char[4096];
/*  56:223 */     int count = 0;
/*  57:224 */     int n = 0;
/*  58:225 */     while (-1 != (n = input.read(buffer)))
/*  59:    */     {
/*  60:226 */       output.write(buffer, 0, n);
/*  61:227 */       count += n;
/*  62:    */     }
/*  63:229 */     return count;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static void copy(InputStream input, Writer output)
/*  67:    */     throws IOException
/*  68:    */   {
/*  69:248 */     InputStreamReader in = new InputStreamReader(input);
/*  70:249 */     copy(in, output);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static void copy(InputStream input, Writer output, String encoding)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:267 */     InputStreamReader in = new InputStreamReader(input, encoding);
/*  77:268 */     copy(in, output);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static void copy(Reader input, OutputStream output)
/*  81:    */     throws IOException
/*  82:    */   {
/*  83:287 */     OutputStreamWriter out = new OutputStreamWriter(output);
/*  84:288 */     copy(input, out);
/*  85:    */     
/*  86:    */ 
/*  87:291 */     out.flush();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static void copy(String input, OutputStream output)
/*  91:    */     throws IOException
/*  92:    */   {
/*  93:310 */     StringReader in = new StringReader(input);
/*  94:311 */     OutputStreamWriter out = new OutputStreamWriter(output);
/*  95:312 */     copy(in, out);
/*  96:    */     
/*  97:    */ 
/*  98:315 */     out.flush();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void copy(String input, Writer output)
/* 102:    */     throws IOException
/* 103:    */   {
/* 104:330 */     output.write(input);
/* 105:    */   }
/* 106:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.CopyUtils
 * JD-Core Version:    0.7.0.1
 */