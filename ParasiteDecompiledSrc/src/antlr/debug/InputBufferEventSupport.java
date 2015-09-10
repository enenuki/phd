/*  1:   */ package antlr.debug;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ 
/*  5:   */ public class InputBufferEventSupport
/*  6:   */ {
/*  7:   */   private Object source;
/*  8:   */   private Vector inputBufferListeners;
/*  9:   */   private InputBufferEvent inputBufferEvent;
/* 10:   */   protected static final int CONSUME = 0;
/* 11:   */   protected static final int LA = 1;
/* 12:   */   protected static final int MARK = 2;
/* 13:   */   protected static final int REWIND = 3;
/* 14:   */   
/* 15:   */   public InputBufferEventSupport(Object paramObject)
/* 16:   */   {
/* 17:18 */     this.inputBufferEvent = new InputBufferEvent(paramObject);
/* 18:19 */     this.source = paramObject;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void addInputBufferListener(InputBufferListener paramInputBufferListener)
/* 22:   */   {
/* 23:22 */     if (this.inputBufferListeners == null) {
/* 24:22 */       this.inputBufferListeners = new Vector();
/* 25:   */     }
/* 26:23 */     this.inputBufferListeners.addElement(paramInputBufferListener);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void fireConsume(char paramChar)
/* 30:   */   {
/* 31:26 */     this.inputBufferEvent.setValues(0, paramChar, 0);
/* 32:27 */     fireEvents(0, this.inputBufferListeners);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void fireEvent(int paramInt, ListenerBase paramListenerBase)
/* 36:   */   {
/* 37:30 */     switch (paramInt)
/* 38:   */     {
/* 39:   */     case 0: 
/* 40:31 */       ((InputBufferListener)paramListenerBase).inputBufferConsume(this.inputBufferEvent); break;
/* 41:   */     case 1: 
/* 42:32 */       ((InputBufferListener)paramListenerBase).inputBufferLA(this.inputBufferEvent); break;
/* 43:   */     case 2: 
/* 44:33 */       ((InputBufferListener)paramListenerBase).inputBufferMark(this.inputBufferEvent); break;
/* 45:   */     case 3: 
/* 46:34 */       ((InputBufferListener)paramListenerBase).inputBufferRewind(this.inputBufferEvent); break;
/* 47:   */     default: 
/* 48:36 */       throw new IllegalArgumentException("bad type " + paramInt + " for fireEvent()");
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void fireEvents(int paramInt, Vector paramVector)
/* 53:   */   {
/* 54:40 */     Vector localVector = null;
/* 55:41 */     ListenerBase localListenerBase = null;
/* 56:43 */     synchronized (this)
/* 57:   */     {
/* 58:44 */       if (paramVector == null) {
/* 59:44 */         return;
/* 60:   */       }
/* 61:45 */       localVector = (Vector)paramVector.clone();
/* 62:   */     }
/* 63:48 */     if (localVector != null) {
/* 64:49 */       for (int i = 0; i < localVector.size(); i++)
/* 65:   */       {
/* 66:50 */         localListenerBase = (ListenerBase)localVector.elementAt(i);
/* 67:51 */         fireEvent(paramInt, localListenerBase);
/* 68:   */       }
/* 69:   */     }
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void fireLA(char paramChar, int paramInt)
/* 73:   */   {
/* 74:55 */     this.inputBufferEvent.setValues(1, paramChar, paramInt);
/* 75:56 */     fireEvents(1, this.inputBufferListeners);
/* 76:   */   }
/* 77:   */   
/* 78:   */   public void fireMark(int paramInt)
/* 79:   */   {
/* 80:59 */     this.inputBufferEvent.setValues(2, ' ', paramInt);
/* 81:60 */     fireEvents(2, this.inputBufferListeners);
/* 82:   */   }
/* 83:   */   
/* 84:   */   public void fireRewind(int paramInt)
/* 85:   */   {
/* 86:63 */     this.inputBufferEvent.setValues(3, ' ', paramInt);
/* 87:64 */     fireEvents(3, this.inputBufferListeners);
/* 88:   */   }
/* 89:   */   
/* 90:   */   public Vector getInputBufferListeners()
/* 91:   */   {
/* 92:67 */     return this.inputBufferListeners;
/* 93:   */   }
/* 94:   */   
/* 95:   */   protected void refresh(Vector paramVector)
/* 96:   */   {
/* 97:   */     Vector localVector;
/* 98:71 */     synchronized (paramVector)
/* 99:   */     {
/* :0:72 */       localVector = (Vector)paramVector.clone();
/* :1:   */     }
/* :2:74 */     if (localVector != null) {
/* :3:75 */       for (int i = 0; i < localVector.size(); i++) {
/* :4:76 */         ((ListenerBase)localVector.elementAt(i)).refresh();
/* :5:   */       }
/* :6:   */     }
/* :7:   */   }
/* :8:   */   
/* :9:   */   public void refreshListeners()
/* ;0:   */   {
/* ;1:79 */     refresh(this.inputBufferListeners);
/* ;2:   */   }
/* ;3:   */   
/* ;4:   */   public void removeInputBufferListener(InputBufferListener paramInputBufferListener)
/* ;5:   */   {
/* ;6:82 */     if (this.inputBufferListeners != null) {
/* ;7:83 */       this.inputBufferListeners.removeElement(paramInputBufferListener);
/* ;8:   */     }
/* ;9:   */   }
/* <0:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.InputBufferEventSupport
 * JD-Core Version:    0.7.0.1
 */