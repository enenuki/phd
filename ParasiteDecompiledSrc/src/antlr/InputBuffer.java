/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ public abstract class InputBuffer
/*   4:    */ {
/*   5: 32 */   protected int nMarkers = 0;
/*   6: 35 */   protected int markerOffset = 0;
/*   7: 38 */   protected int numToConsume = 0;
/*   8:    */   protected CharQueue queue;
/*   9:    */   
/*  10:    */   public InputBuffer()
/*  11:    */   {
/*  12: 45 */     this.queue = new CharQueue(1);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public void commit()
/*  16:    */   {
/*  17: 55 */     this.nMarkers -= 1;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void consume()
/*  21:    */   {
/*  22: 60 */     this.numToConsume += 1;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public abstract void fill(int paramInt)
/*  26:    */     throws CharStreamException;
/*  27:    */   
/*  28:    */   public String getLAChars()
/*  29:    */   {
/*  30: 67 */     StringBuffer localStringBuffer = new StringBuffer();
/*  31: 68 */     for (int i = this.markerOffset; i < this.queue.nbrEntries; i++) {
/*  32: 69 */       localStringBuffer.append(this.queue.elementAt(i));
/*  33:    */     }
/*  34: 70 */     return localStringBuffer.toString();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getMarkedChars()
/*  38:    */   {
/*  39: 74 */     StringBuffer localStringBuffer = new StringBuffer();
/*  40: 75 */     for (int i = 0; i < this.markerOffset; i++) {
/*  41: 76 */       localStringBuffer.append(this.queue.elementAt(i));
/*  42:    */     }
/*  43: 77 */     return localStringBuffer.toString();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isMarked()
/*  47:    */   {
/*  48: 81 */     return this.nMarkers != 0;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public char LA(int paramInt)
/*  52:    */     throws CharStreamException
/*  53:    */   {
/*  54: 86 */     fill(paramInt);
/*  55: 87 */     return this.queue.elementAt(this.markerOffset + paramInt - 1);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int mark()
/*  59:    */   {
/*  60: 94 */     syncConsume();
/*  61: 95 */     this.nMarkers += 1;
/*  62: 96 */     return this.markerOffset;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void rewind(int paramInt)
/*  66:    */   {
/*  67:103 */     syncConsume();
/*  68:104 */     this.markerOffset = paramInt;
/*  69:105 */     this.nMarkers -= 1;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void reset()
/*  73:    */   {
/*  74:111 */     this.nMarkers = 0;
/*  75:112 */     this.markerOffset = 0;
/*  76:113 */     this.numToConsume = 0;
/*  77:114 */     this.queue.reset();
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected void syncConsume()
/*  81:    */   {
/*  82:119 */     while (this.numToConsume > 0)
/*  83:    */     {
/*  84:120 */       if (this.nMarkers > 0) {
/*  85:122 */         this.markerOffset += 1;
/*  86:    */       } else {
/*  87:126 */         this.queue.removeFirst();
/*  88:    */       }
/*  89:128 */       this.numToConsume -= 1;
/*  90:    */     }
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.InputBuffer
 * JD-Core Version:    0.7.0.1
 */