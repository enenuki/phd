/*   1:    */ package org.junit.experimental.max;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.ObjectOutputStream;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import java.util.Comparator;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.Map;
/*  11:    */ import org.junit.runner.Description;
/*  12:    */ import org.junit.runner.Result;
/*  13:    */ import org.junit.runner.notification.Failure;
/*  14:    */ import org.junit.runner.notification.RunListener;
/*  15:    */ 
/*  16:    */ public class MaxHistory
/*  17:    */   implements Serializable
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 1L;
/*  20:    */   
/*  21:    */   public static MaxHistory forFolder(File file)
/*  22:    */   {
/*  23: 34 */     if (file.exists()) {
/*  24:    */       try
/*  25:    */       {
/*  26: 36 */         return readHistory(file);
/*  27:    */       }
/*  28:    */       catch (CouldNotReadCoreException e)
/*  29:    */       {
/*  30: 38 */         e.printStackTrace();
/*  31: 39 */         file.delete();
/*  32:    */       }
/*  33:    */     }
/*  34: 41 */     return new MaxHistory(file);
/*  35:    */   }
/*  36:    */   
/*  37: 63 */   private final Map<String, Long> fDurations = new HashMap();
/*  38: 65 */   private final Map<String, Long> fFailureTimestamps = new HashMap();
/*  39:    */   private final File fHistoryStore;
/*  40:    */   
/*  41:    */   /* Error */
/*  42:    */   private static MaxHistory readHistory(File storedResults)
/*  43:    */     throws CouldNotReadCoreException
/*  44:    */   {
/*  45:    */     // Byte code:
/*  46:    */     //   0: new 9	java/io/FileInputStream
/*  47:    */     //   3: dup
/*  48:    */     //   4: aload_0
/*  49:    */     //   5: invokespecial 10	java/io/FileInputStream:<init>	(Ljava/io/File;)V
/*  50:    */     //   8: astore_1
/*  51:    */     //   9: new 11	java/io/ObjectInputStream
/*  52:    */     //   12: dup
/*  53:    */     //   13: aload_1
/*  54:    */     //   14: invokespecial 12	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
/*  55:    */     //   17: astore_2
/*  56:    */     //   18: aload_2
/*  57:    */     //   19: invokevirtual 13	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
/*  58:    */     //   22: checkcast 7	org/junit/experimental/max/MaxHistory
/*  59:    */     //   25: astore_3
/*  60:    */     //   26: aload_2
/*  61:    */     //   27: invokevirtual 14	java/io/ObjectInputStream:close	()V
/*  62:    */     //   30: aload_1
/*  63:    */     //   31: invokevirtual 15	java/io/FileInputStream:close	()V
/*  64:    */     //   34: aload_3
/*  65:    */     //   35: areturn
/*  66:    */     //   36: astore 4
/*  67:    */     //   38: aload_2
/*  68:    */     //   39: invokevirtual 14	java/io/ObjectInputStream:close	()V
/*  69:    */     //   42: aload 4
/*  70:    */     //   44: athrow
/*  71:    */     //   45: astore 5
/*  72:    */     //   47: aload_1
/*  73:    */     //   48: invokevirtual 15	java/io/FileInputStream:close	()V
/*  74:    */     //   51: aload 5
/*  75:    */     //   53: athrow
/*  76:    */     //   54: astore_1
/*  77:    */     //   55: new 4	org/junit/experimental/max/CouldNotReadCoreException
/*  78:    */     //   58: dup
/*  79:    */     //   59: aload_1
/*  80:    */     //   60: invokespecial 17	org/junit/experimental/max/CouldNotReadCoreException:<init>	(Ljava/lang/Throwable;)V
/*  81:    */     //   63: athrow
/*  82:    */     // Line number table:
/*  83:    */     //   Java source line #47	-> byte code offset #0
/*  84:    */     //   Java source line #49	-> byte code offset #9
/*  85:    */     //   Java source line #51	-> byte code offset #18
/*  86:    */     //   Java source line #53	-> byte code offset #26
/*  87:    */     //   Java source line #56	-> byte code offset #30
/*  88:    */     //   Java source line #53	-> byte code offset #36
/*  89:    */     //   Java source line #56	-> byte code offset #45
/*  90:    */     //   Java source line #58	-> byte code offset #54
/*  91:    */     //   Java source line #59	-> byte code offset #55
/*  92:    */     // Local variable table:
/*  93:    */     //   start	length	slot	name	signature
/*  94:    */     //   0	64	0	storedResults	File
/*  95:    */     //   8	40	1	file	java.io.FileInputStream
/*  96:    */     //   54	6	1	e	Exception
/*  97:    */     //   17	22	2	stream	java.io.ObjectInputStream
/*  98:    */     //   36	7	4	localObject1	Object
/*  99:    */     //   45	7	5	localObject2	Object
/* 100:    */     // Exception table:
/* 101:    */     //   from	to	target	type
/* 102:    */     //   18	26	36	finally
/* 103:    */     //   36	38	36	finally
/* 104:    */     //   9	30	45	finally
/* 105:    */     //   36	47	45	finally
/* 106:    */     //   0	34	54	java/lang/Exception
/* 107:    */     //   36	54	54	java/lang/Exception
/* 108:    */   }
/* 109:    */   
/* 110:    */   private MaxHistory(File storedResults)
/* 111:    */   {
/* 112: 70 */     this.fHistoryStore = storedResults;
/* 113:    */   }
/* 114:    */   
/* 115:    */   private void save()
/* 116:    */     throws IOException
/* 117:    */   {
/* 118: 74 */     ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(this.fHistoryStore));
/* 119:    */     
/* 120: 76 */     stream.writeObject(this);
/* 121: 77 */     stream.close();
/* 122:    */   }
/* 123:    */   
/* 124:    */   Long getFailureTimestamp(Description key)
/* 125:    */   {
/* 126: 81 */     return (Long)this.fFailureTimestamps.get(key.toString());
/* 127:    */   }
/* 128:    */   
/* 129:    */   void putTestFailureTimestamp(Description key, long end)
/* 130:    */   {
/* 131: 85 */     this.fFailureTimestamps.put(key.toString(), Long.valueOf(end));
/* 132:    */   }
/* 133:    */   
/* 134:    */   boolean isNewTest(Description key)
/* 135:    */   {
/* 136: 89 */     return !this.fDurations.containsKey(key.toString());
/* 137:    */   }
/* 138:    */   
/* 139:    */   Long getTestDuration(Description key)
/* 140:    */   {
/* 141: 93 */     return (Long)this.fDurations.get(key.toString());
/* 142:    */   }
/* 143:    */   
/* 144:    */   void putTestDuration(Description description, long duration)
/* 145:    */   {
/* 146: 97 */     this.fDurations.put(description.toString(), Long.valueOf(duration));
/* 147:    */   }
/* 148:    */   
/* 149:    */   private final class RememberingListener
/* 150:    */     extends RunListener
/* 151:    */   {
/* 152:101 */     private long overallStart = System.currentTimeMillis();
/* 153:103 */     private Map<Description, Long> starts = new HashMap();
/* 154:    */     
/* 155:    */     private RememberingListener() {}
/* 156:    */     
/* 157:    */     public void testStarted(Description description)
/* 158:    */       throws Exception
/* 159:    */     {
/* 160:107 */       this.starts.put(description, Long.valueOf(System.nanoTime()));
/* 161:    */     }
/* 162:    */     
/* 163:    */     public void testFinished(Description description)
/* 164:    */       throws Exception
/* 165:    */     {
/* 166:113 */       long end = System.nanoTime();
/* 167:114 */       long start = ((Long)this.starts.get(description)).longValue();
/* 168:115 */       MaxHistory.this.putTestDuration(description, end - start);
/* 169:    */     }
/* 170:    */     
/* 171:    */     public void testFailure(Failure failure)
/* 172:    */       throws Exception
/* 173:    */     {
/* 174:120 */       MaxHistory.this.putTestFailureTimestamp(failure.getDescription(), this.overallStart);
/* 175:    */     }
/* 176:    */     
/* 177:    */     public void testRunFinished(Result result)
/* 178:    */       throws Exception
/* 179:    */     {
/* 180:125 */       MaxHistory.this.save();
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   private class TestComparator
/* 185:    */     implements Comparator<Description>
/* 186:    */   {
/* 187:    */     private TestComparator() {}
/* 188:    */     
/* 189:    */     public int compare(Description o1, Description o2)
/* 190:    */     {
/* 191:132 */       if (MaxHistory.this.isNewTest(o1)) {
/* 192:133 */         return -1;
/* 193:    */       }
/* 194:134 */       if (MaxHistory.this.isNewTest(o2)) {
/* 195:135 */         return 1;
/* 196:    */       }
/* 197:137 */       int result = getFailure(o2).compareTo(getFailure(o1));
/* 198:138 */       return result != 0 ? result : MaxHistory.this.getTestDuration(o1).compareTo(MaxHistory.this.getTestDuration(o2));
/* 199:    */     }
/* 200:    */     
/* 201:    */     private Long getFailure(Description key)
/* 202:    */     {
/* 203:144 */       Long result = MaxHistory.this.getFailureTimestamp(key);
/* 204:145 */       if (result == null) {
/* 205:146 */         return Long.valueOf(0L);
/* 206:    */       }
/* 207:147 */       return result;
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   public RunListener listener()
/* 212:    */   {
/* 213:156 */     return new RememberingListener(null);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public Comparator<Description> testComparator()
/* 217:    */   {
/* 218:164 */     return new TestComparator(null);
/* 219:    */   }
/* 220:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.max.MaxHistory
 * JD-Core Version:    0.7.0.1
 */