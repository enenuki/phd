/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ import antlr.CharStreamException;
/*  4:   */ import antlr.InputBuffer;
/*  5:   */ import java.util.Vector;
/*  6:   */ 
/*  7:   */ public class DebuggingInputBuffer
/*  8:   */   extends InputBuffer
/*  9:   */ {
/* 10:   */   private InputBuffer buffer;
/* 11:   */   private InputBufferEventSupport inputBufferEventSupport;
/* 12:11 */   private boolean debugMode = true;
/* 13:   */   
/* 14:   */   public DebuggingInputBuffer(InputBuffer paramInputBuffer)
/* 15:   */   {
/* 16:15 */     this.buffer = paramInputBuffer;
/* 17:16 */     this.inputBufferEventSupport = new InputBufferEventSupport(this);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void addInputBufferListener(InputBufferListener paramInputBufferListener)
/* 21:   */   {
/* 22:19 */     this.inputBufferEventSupport.addInputBufferListener(paramInputBufferListener);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void consume()
/* 26:   */   {
/* 27:22 */     char c = ' ';
/* 28:   */     try
/* 29:   */     {
/* 30:23 */       c = this.buffer.LA(1);
/* 31:   */     }
/* 32:   */     catch (CharStreamException localCharStreamException) {}
/* 33:25 */     this.buffer.consume();
/* 34:26 */     if (this.debugMode) {
/* 35:27 */       this.inputBufferEventSupport.fireConsume(c);
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void fill(int paramInt)
/* 40:   */     throws CharStreamException
/* 41:   */   {
/* 42:30 */     this.buffer.fill(paramInt);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Vector getInputBufferListeners()
/* 46:   */   {
/* 47:33 */     return this.inputBufferEventSupport.getInputBufferListeners();
/* 48:   */   }
/* 49:   */   
/* 50:   */   public boolean isDebugMode()
/* 51:   */   {
/* 52:36 */     return this.debugMode;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean isMarked()
/* 56:   */   {
/* 57:39 */     return this.buffer.isMarked();
/* 58:   */   }
/* 59:   */   
/* 60:   */   public char LA(int paramInt)
/* 61:   */     throws CharStreamException
/* 62:   */   {
/* 63:42 */     char c = this.buffer.LA(paramInt);
/* 64:43 */     if (this.debugMode) {
/* 65:44 */       this.inputBufferEventSupport.fireLA(c, paramInt);
/* 66:   */     }
/* 67:45 */     return c;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public int mark()
/* 71:   */   {
/* 72:48 */     int i = this.buffer.mark();
/* 73:49 */     this.inputBufferEventSupport.fireMark(i);
/* 74:50 */     return i;
/* 75:   */   }
/* 76:   */   
/* 77:   */   public void removeInputBufferListener(InputBufferListener paramInputBufferListener)
/* 78:   */   {
/* 79:53 */     if (this.inputBufferEventSupport != null) {
/* 80:54 */       this.inputBufferEventSupport.removeInputBufferListener(paramInputBufferListener);
/* 81:   */     }
/* 82:   */   }
/* 83:   */   
/* 84:   */   public void rewind(int paramInt)
/* 85:   */   {
/* 86:57 */     this.buffer.rewind(paramInt);
/* 87:58 */     this.inputBufferEventSupport.fireRewind(paramInt);
/* 88:   */   }
/* 89:   */   
/* 90:   */   public void setDebugMode(boolean paramBoolean)
/* 91:   */   {
/* 92:61 */     this.debugMode = paramBoolean;
/* 93:   */   }
/* 94:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.DebuggingInputBuffer
 * JD-Core Version:    0.7.0.1
 */