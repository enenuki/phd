/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.BufferedWriter;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileReader;
/*   7:    */ import java.io.FileWriter;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.Reader;
/*  10:    */ import java.io.Writer;
/*  11:    */ 
/*  12:    */ public class PreservingFileWriter
/*  13:    */   extends FileWriter
/*  14:    */ {
/*  15:    */   protected File target_file;
/*  16:    */   protected File tmp_file;
/*  17:    */   
/*  18:    */   public PreservingFileWriter(String paramString)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 24 */     super(paramString + ".antlr.tmp");
/*  22:    */     
/*  23:    */ 
/*  24: 27 */     this.target_file = new File(paramString);
/*  25:    */     
/*  26: 29 */     String str = this.target_file.getParent();
/*  27: 30 */     if (str != null)
/*  28:    */     {
/*  29: 32 */       File localFile = new File(str);
/*  30: 34 */       if (!localFile.exists()) {
/*  31: 35 */         throw new IOException("destination directory of '" + paramString + "' doesn't exist");
/*  32:    */       }
/*  33: 36 */       if (!localFile.canWrite()) {
/*  34: 37 */         throw new IOException("destination directory of '" + paramString + "' isn't writeable");
/*  35:    */       }
/*  36:    */     }
/*  37: 39 */     if ((this.target_file.exists()) && (!this.target_file.canWrite())) {
/*  38: 40 */       throw new IOException("cannot write to '" + paramString + "'");
/*  39:    */     }
/*  40: 43 */     this.tmp_file = new File(paramString + ".antlr.tmp");
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void close()
/*  44:    */     throws IOException
/*  45:    */   {
/*  46: 56 */     BufferedReader localBufferedReader1 = null;
/*  47: 57 */     BufferedWriter localBufferedWriter = null;
/*  48:    */     try
/*  49:    */     {
/*  50: 61 */       super.close();
/*  51:    */       
/*  52: 63 */       char[] arrayOfChar1 = new char[1024];
/*  53: 67 */       if (this.target_file.length() == this.tmp_file.length())
/*  54:    */       {
/*  55: 71 */         char[] arrayOfChar2 = new char[1024];
/*  56:    */         
/*  57: 73 */         localBufferedReader1 = new BufferedReader(new FileReader(this.tmp_file));
/*  58: 74 */         BufferedReader localBufferedReader2 = new BufferedReader(new FileReader(this.target_file));
/*  59:    */         
/*  60: 76 */         int m = 1;
/*  61:    */         label164:
/*  62: 78 */         while (m != 0)
/*  63:    */         {
/*  64: 80 */           int j = localBufferedReader1.read(arrayOfChar1, 0, 1024);
/*  65: 81 */           int k = localBufferedReader2.read(arrayOfChar2, 0, 1024);
/*  66: 82 */           if (j != k) {
/*  67: 84 */             m = 0;
/*  68: 87 */           } else if (j != -1) {
/*  69: 89 */             for (int n = 0;; n++)
/*  70:    */             {
/*  71: 89 */               if (n >= j) {
/*  72:    */                 break label164;
/*  73:    */               }
/*  74: 91 */               if (arrayOfChar1[n] != arrayOfChar2[n])
/*  75:    */               {
/*  76: 93 */                 m = 0;
/*  77: 94 */                 break;
/*  78:    */               }
/*  79:    */             }
/*  80:    */           }
/*  81:    */         }
/*  82: 99 */         localBufferedReader1.close();
/*  83:100 */         localBufferedReader2.close();
/*  84:    */         
/*  85:102 */         localBufferedReader1 = localBufferedReader2 = null;
/*  86:104 */         if (m != 0) {
/*  87:105 */           return;
/*  88:    */         }
/*  89:    */       }
/*  90:108 */       localBufferedReader1 = new BufferedReader(new FileReader(this.tmp_file));
/*  91:109 */       localBufferedWriter = new BufferedWriter(new FileWriter(this.target_file));
/*  92:    */       for (;;)
/*  93:    */       {
/*  94:113 */         int i = localBufferedReader1.read(arrayOfChar1, 0, 1024);
/*  95:114 */         if (i == -1) {
/*  96:    */           break;
/*  97:    */         }
/*  98:116 */         localBufferedWriter.write(arrayOfChar1, 0, i);
/*  99:    */       }
/* 100:    */     }
/* 101:    */     finally
/* 102:    */     {
/* 103:120 */       if (localBufferedReader1 != null) {
/* 104:    */         try
/* 105:    */         {
/* 106:122 */           localBufferedReader1.close();
/* 107:    */         }
/* 108:    */         catch (IOException localIOException1) {}
/* 109:    */       }
/* 110:125 */       if (localBufferedWriter != null) {
/* 111:    */         try
/* 112:    */         {
/* 113:127 */           localBufferedWriter.close();
/* 114:    */         }
/* 115:    */         catch (IOException localIOException2) {}
/* 116:    */       }
/* 117:131 */       if ((this.tmp_file != null) && (this.tmp_file.exists()))
/* 118:    */       {
/* 119:133 */         this.tmp_file.delete();
/* 120:134 */         this.tmp_file = null;
/* 121:    */       }
/* 122:    */     }
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.PreservingFileWriter
 * JD-Core Version:    0.7.0.1
 */