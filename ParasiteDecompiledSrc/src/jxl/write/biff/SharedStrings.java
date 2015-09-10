/*   1:    */ package jxl.write.biff;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ 
/*   8:    */ class SharedStrings
/*   9:    */ {
/*  10:    */   private HashMap strings;
/*  11:    */   private ArrayList stringList;
/*  12:    */   private int totalOccurrences;
/*  13:    */   
/*  14:    */   public SharedStrings()
/*  15:    */   {
/*  16: 53 */     this.strings = new HashMap(100);
/*  17: 54 */     this.stringList = new ArrayList(100);
/*  18: 55 */     this.totalOccurrences = 0;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public int getIndex(String s)
/*  22:    */   {
/*  23: 68 */     Integer i = (Integer)this.strings.get(s);
/*  24: 70 */     if (i == null)
/*  25:    */     {
/*  26: 72 */       i = new Integer(this.strings.size());
/*  27: 73 */       this.strings.put(s, i);
/*  28: 74 */       this.stringList.add(s);
/*  29:    */     }
/*  30: 77 */     this.totalOccurrences += 1;
/*  31:    */     
/*  32: 79 */     return i.intValue();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String get(int i)
/*  36:    */   {
/*  37: 90 */     return (String)this.stringList.get(i);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void write(File outputFile)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43:103 */     int charsLeft = 0;
/*  44:104 */     String curString = null;
/*  45:105 */     SSTRecord sst = new SSTRecord(this.totalOccurrences, this.stringList.size());
/*  46:106 */     ExtendedSSTRecord extsst = new ExtendedSSTRecord(this.stringList.size());
/*  47:107 */     int bucketSize = extsst.getNumberOfStringsPerBucket();
/*  48:    */     
/*  49:109 */     Iterator i = this.stringList.iterator();
/*  50:110 */     int stringIndex = 0;
/*  51:111 */     while ((i.hasNext()) && (charsLeft == 0))
/*  52:    */     {
/*  53:113 */       curString = (String)i.next();
/*  54:    */       
/*  55:115 */       int relativePosition = sst.getOffset() + 4;
/*  56:116 */       charsLeft = sst.add(curString);
/*  57:117 */       if (stringIndex % bucketSize == 0) {
/*  58:118 */         extsst.addString(outputFile.getPos(), relativePosition);
/*  59:    */       }
/*  60:120 */       stringIndex++;
/*  61:    */     }
/*  62:122 */     outputFile.write(sst);
/*  63:124 */     if ((charsLeft != 0) || (i.hasNext()))
/*  64:    */     {
/*  65:127 */       SSTContinueRecord cont = createContinueRecord(curString, charsLeft, outputFile);
/*  66:132 */       while (i.hasNext())
/*  67:    */       {
/*  68:134 */         curString = (String)i.next();
/*  69:135 */         int relativePosition = cont.getOffset() + 4;
/*  70:136 */         charsLeft = cont.add(curString);
/*  71:137 */         if (stringIndex % bucketSize == 0) {
/*  72:138 */           extsst.addString(outputFile.getPos(), relativePosition);
/*  73:    */         }
/*  74:140 */         stringIndex++;
/*  75:142 */         if (charsLeft != 0)
/*  76:    */         {
/*  77:144 */           outputFile.write(cont);
/*  78:145 */           cont = createContinueRecord(curString, charsLeft, outputFile);
/*  79:    */         }
/*  80:    */       }
/*  81:149 */       outputFile.write(cont);
/*  82:    */     }
/*  83:152 */     outputFile.write(extsst);
/*  84:    */   }
/*  85:    */   
/*  86:    */   private SSTContinueRecord createContinueRecord(String curString, int charsLeft, File outputFile)
/*  87:    */     throws IOException
/*  88:    */   {
/*  89:163 */     SSTContinueRecord cont = null;
/*  90:164 */     while (charsLeft != 0)
/*  91:    */     {
/*  92:166 */       cont = new SSTContinueRecord();
/*  93:168 */       if ((charsLeft == curString.length()) || (curString.length() == 0)) {
/*  94:170 */         charsLeft = cont.setFirstString(curString, true);
/*  95:    */       } else {
/*  96:174 */         charsLeft = cont.setFirstString(curString.substring(curString.length() - charsLeft), false);
/*  97:    */       }
/*  98:178 */       if (charsLeft != 0)
/*  99:    */       {
/* 100:180 */         outputFile.write(cont);
/* 101:181 */         cont = new SSTContinueRecord();
/* 102:    */       }
/* 103:    */     }
/* 104:185 */     return cont;
/* 105:    */   }
/* 106:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.SharedStrings
 * JD-Core Version:    0.7.0.1
 */