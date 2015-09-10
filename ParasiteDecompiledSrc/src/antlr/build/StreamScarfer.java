/*  1:   */ package antlr.build;
/*  2:   */ 
/*  3:   */ import java.io.BufferedReader;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.InputStream;
/*  6:   */ import java.io.InputStreamReader;
/*  7:   */ 
/*  8:   */ class StreamScarfer
/*  9:   */   extends Thread
/* 10:   */ {
/* 11:   */   InputStream is;
/* 12:   */   String type;
/* 13:   */   Tool tool;
/* 14:   */   
/* 15:   */   StreamScarfer(InputStream paramInputStream, String paramString, Tool paramTool)
/* 16:   */   {
/* 17:14 */     this.is = paramInputStream;
/* 18:15 */     this.type = paramString;
/* 19:16 */     this.tool = paramTool;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void run()
/* 23:   */   {
/* 24:   */     try
/* 25:   */     {
/* 26:21 */       InputStreamReader localInputStreamReader = new InputStreamReader(this.is);
/* 27:22 */       BufferedReader localBufferedReader = new BufferedReader(localInputStreamReader);
/* 28:23 */       String str = null;
/* 29:24 */       while ((str = localBufferedReader.readLine()) != null) {
/* 30:25 */         if ((this.type == null) || (this.type.equals("stdout"))) {
/* 31:26 */           this.tool.stdout(str);
/* 32:   */         } else {
/* 33:29 */           this.tool.stderr(str);
/* 34:   */         }
/* 35:   */       }
/* 36:   */     }
/* 37:   */     catch (IOException localIOException)
/* 38:   */     {
/* 39:34 */       localIOException.printStackTrace();
/* 40:   */     }
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.build.StreamScarfer
 * JD-Core Version:    0.7.0.1
 */