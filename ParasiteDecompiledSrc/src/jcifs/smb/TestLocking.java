/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ 
/*   7:    */ public class TestLocking
/*   8:    */   implements Runnable
/*   9:    */ {
/*  10:  9 */   int numThreads = 1;
/*  11: 10 */   int numIter = 1;
/*  12: 11 */   long delay = 100L;
/*  13: 12 */   String url = null;
/*  14: 13 */   int numComplete = 0;
/*  15: 14 */   long ltime = 0L;
/*  16:    */   
/*  17:    */   public void run()
/*  18:    */   {
/*  19:    */     try
/*  20:    */     {
/*  21: 19 */       SmbFile f = new SmbFile(this.url);
/*  22: 20 */       SmbFile d = new SmbFile(f.getParent());
/*  23: 21 */       byte[] buf = new byte[1024];
/*  24: 23 */       for (int ii = 0; ii < this.numIter; ii++)
/*  25:    */       {
/*  26: 25 */         synchronized (this)
/*  27:    */         {
/*  28: 26 */           this.ltime = System.currentTimeMillis();
/*  29: 27 */           wait();
/*  30:    */         }
/*  31:    */         try
/*  32:    */         {
/*  33: 31 */           double r = Math.random();
/*  34: 32 */           if (r < 0.333D)
/*  35:    */           {
/*  36: 33 */             f.exists();
/*  37:    */           }
/*  38: 35 */           else if (r < 0.667D)
/*  39:    */           {
/*  40: 36 */             d.listFiles();
/*  41:    */           }
/*  42: 38 */           else if (r < 1.0D)
/*  43:    */           {
/*  44: 39 */             InputStream in = f.getInputStream();
/*  45: 40 */             while (in.read(buf) > 0) {}
/*  46: 43 */             in.close();
/*  47:    */           }
/*  48:    */         }
/*  49:    */         catch (IOException ioe)
/*  50:    */         {
/*  51: 46 */           System.err.println(ioe.getMessage());
/*  52:    */         }
/*  53:    */       }
/*  54:    */     }
/*  55:    */     catch (Exception e)
/*  56:    */     {
/*  57: 52 */       e.printStackTrace();
/*  58:    */     }
/*  59:    */     finally
/*  60:    */     {
/*  61: 54 */       this.numComplete += 1;
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static void main(String[] args)
/*  66:    */     throws Exception
/*  67:    */   {
/*  68: 60 */     if (args.length < 1)
/*  69:    */     {
/*  70: 61 */       System.err.println("usage: TestLocking [-t <numThreads>] [-i <numIter>] [-d <delay>] url");
/*  71: 62 */       System.exit(1);
/*  72:    */     }
/*  73: 65 */     TestLocking t = new TestLocking();
/*  74: 66 */     t.ltime = System.currentTimeMillis();
/*  75: 68 */     for (int ai = 0; ai < args.length; ai++) {
/*  76: 69 */       if (args[ai].equals("-t"))
/*  77:    */       {
/*  78: 70 */         ai++;
/*  79: 71 */         t.numThreads = Integer.parseInt(args[ai]);
/*  80:    */       }
/*  81: 72 */       else if (args[ai].equals("-i"))
/*  82:    */       {
/*  83: 73 */         ai++;
/*  84: 74 */         t.numIter = Integer.parseInt(args[ai]);
/*  85:    */       }
/*  86: 75 */       else if (args[ai].equals("-d"))
/*  87:    */       {
/*  88: 76 */         ai++;
/*  89: 77 */         t.delay = Long.parseLong(args[ai]);
/*  90:    */       }
/*  91:    */       else
/*  92:    */       {
/*  93: 79 */         t.url = args[ai];
/*  94:    */       }
/*  95:    */     }
/*  96: 83 */     Thread[] threads = new Thread[t.numThreads];
/*  97: 86 */     for (int ti = 0; ti < t.numThreads; ti++)
/*  98:    */     {
/*  99: 87 */       threads[ti] = new Thread(t);
/* 100: 88 */       System.out.print(threads[ti].getName());
/* 101: 89 */       threads[ti].start();
/* 102:    */     }
/* 103: 92 */     while (t.numComplete < t.numThreads)
/* 104:    */     {
/* 105:    */       long delay;
/* 106:    */       do
/* 107:    */       {
/* 108: 96 */         delay = 2L;
/* 109: 98 */         synchronized (t)
/* 110:    */         {
/* 111: 99 */           long expire = t.ltime + t.delay;
/* 112:100 */           long ctime = System.currentTimeMillis();
/* 113:102 */           if (expire > ctime) {
/* 114:103 */             delay = expire - ctime;
/* 115:    */           }
/* 116:    */         }
/* 117:106 */         if (delay > 2L) {
/* 118:107 */           System.out.println("delay=" + delay);
/* 119:    */         }
/* 120:108 */         Thread.sleep(delay);
/* 121:109 */       } while (delay > 2L);
/* 122:111 */       synchronized (t)
/* 123:    */       {
/* 124:112 */         t.notifyAll();
/* 125:    */       }
/* 126:    */     }
/* 127:117 */     for (ti = 0; ti < t.numThreads; ti++)
/* 128:    */     {
/* 129:118 */       threads[ti].join();
/* 130:119 */       System.out.print(threads[ti].getName());
/* 131:    */     }
/* 132:122 */     System.out.println();
/* 133:    */   }
/* 134:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.TestLocking
 * JD-Core Version:    0.7.0.1
 */