/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ public class TokenBuffer
/*   4:    */ {
/*   5:    */   protected TokenStream input;
/*   6: 33 */   int nMarkers = 0;
/*   7: 36 */   int markerOffset = 0;
/*   8: 39 */   int numToConsume = 0;
/*   9:    */   TokenQueue queue;
/*  10:    */   
/*  11:    */   public TokenBuffer(TokenStream paramTokenStream)
/*  12:    */   {
/*  13: 46 */     this.input = paramTokenStream;
/*  14: 47 */     this.queue = new TokenQueue(1);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public final void reset()
/*  18:    */   {
/*  19: 52 */     this.nMarkers = 0;
/*  20: 53 */     this.markerOffset = 0;
/*  21: 54 */     this.numToConsume = 0;
/*  22: 55 */     this.queue.reset();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public final void consume()
/*  26:    */   {
/*  27: 60 */     this.numToConsume += 1;
/*  28:    */   }
/*  29:    */   
/*  30:    */   private final void fill(int paramInt)
/*  31:    */     throws TokenStreamException
/*  32:    */   {
/*  33: 65 */     syncConsume();
/*  34: 67 */     while (this.queue.nbrEntries < paramInt + this.markerOffset) {
/*  35: 69 */       this.queue.append(this.input.nextToken());
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public TokenStream getInput()
/*  40:    */   {
/*  41: 75 */     return this.input;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public final int LA(int paramInt)
/*  45:    */     throws TokenStreamException
/*  46:    */   {
/*  47: 80 */     fill(paramInt);
/*  48: 81 */     return this.queue.elementAt(this.markerOffset + paramInt - 1).getType();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public final Token LT(int paramInt)
/*  52:    */     throws TokenStreamException
/*  53:    */   {
/*  54: 86 */     fill(paramInt);
/*  55: 87 */     return this.queue.elementAt(this.markerOffset + paramInt - 1);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public final int mark()
/*  59:    */   {
/*  60: 94 */     syncConsume();
/*  61:    */     
/*  62:    */ 
/*  63: 97 */     this.nMarkers += 1;
/*  64: 98 */     return this.markerOffset;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public final void rewind(int paramInt)
/*  68:    */   {
/*  69:105 */     syncConsume();
/*  70:106 */     this.markerOffset = paramInt;
/*  71:107 */     this.nMarkers -= 1;
/*  72:    */   }
/*  73:    */   
/*  74:    */   private final void syncConsume()
/*  75:    */   {
/*  76:114 */     while (this.numToConsume > 0)
/*  77:    */     {
/*  78:115 */       if (this.nMarkers > 0) {
/*  79:117 */         this.markerOffset += 1;
/*  80:    */       } else {
/*  81:121 */         this.queue.removeFirst();
/*  82:    */       }
/*  83:123 */       this.numToConsume -= 1;
/*  84:    */     }
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TokenBuffer
 * JD-Core Version:    0.7.0.1
 */