/*   1:    */ package org.apache.commons.collections.list;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.lang.ref.WeakReference;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.ConcurrentModificationException;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.ListIterator;
/*  14:    */ 
/*  15:    */ public class CursorableLinkedList
/*  16:    */   extends AbstractLinkedList
/*  17:    */   implements Serializable
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 8836393098519411393L;
/*  20: 69 */   protected transient List cursors = new ArrayList();
/*  21:    */   
/*  22:    */   public CursorableLinkedList()
/*  23:    */   {
/*  24: 77 */     init();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public CursorableLinkedList(Collection coll)
/*  28:    */   {
/*  29: 86 */     super(coll);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void init()
/*  33:    */   {
/*  34: 94 */     super.init();
/*  35: 95 */     this.cursors = new ArrayList();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Iterator iterator()
/*  39:    */   {
/*  40:109 */     return super.listIterator(0);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public ListIterator listIterator()
/*  44:    */   {
/*  45:128 */     return cursor(0);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ListIterator listIterator(int fromIndex)
/*  49:    */   {
/*  50:148 */     return cursor(fromIndex);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Cursor cursor()
/*  54:    */   {
/*  55:175 */     return cursor(0);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Cursor cursor(int fromIndex)
/*  59:    */   {
/*  60:206 */     Cursor cursor = new Cursor(this, fromIndex);
/*  61:207 */     registerCursor(cursor);
/*  62:208 */     return cursor;
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void updateNode(AbstractLinkedList.Node node, Object value)
/*  66:    */   {
/*  67:221 */     super.updateNode(node, value);
/*  68:222 */     broadcastNodeChanged(node);
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected void addNode(AbstractLinkedList.Node nodeToInsert, AbstractLinkedList.Node insertBeforeNode)
/*  72:    */   {
/*  73:233 */     super.addNode(nodeToInsert, insertBeforeNode);
/*  74:234 */     broadcastNodeInserted(nodeToInsert);
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected void removeNode(AbstractLinkedList.Node node)
/*  78:    */   {
/*  79:244 */     super.removeNode(node);
/*  80:245 */     broadcastNodeRemoved(node);
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected void removeAllNodes()
/*  84:    */   {
/*  85:252 */     if (size() > 0)
/*  86:    */     {
/*  87:254 */       Iterator it = iterator();
/*  88:255 */       while (it.hasNext())
/*  89:    */       {
/*  90:256 */         it.next();
/*  91:257 */         it.remove();
/*  92:    */       }
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected void registerCursor(Cursor cursor)
/*  97:    */   {
/*  98:271 */     for (Iterator it = this.cursors.iterator(); it.hasNext();)
/*  99:    */     {
/* 100:272 */       WeakReference ref = (WeakReference)it.next();
/* 101:273 */       if (ref.get() == null) {
/* 102:274 */         it.remove();
/* 103:    */       }
/* 104:    */     }
/* 105:277 */     this.cursors.add(new WeakReference(cursor));
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected void unregisterCursor(Cursor cursor)
/* 109:    */   {
/* 110:286 */     for (Iterator it = this.cursors.iterator(); it.hasNext();)
/* 111:    */     {
/* 112:287 */       WeakReference ref = (WeakReference)it.next();
/* 113:288 */       Cursor cur = (Cursor)ref.get();
/* 114:289 */       if (cur == null)
/* 115:    */       {
/* 116:293 */         it.remove();
/* 117:    */       }
/* 118:295 */       else if (cur == cursor)
/* 119:    */       {
/* 120:296 */         ref.clear();
/* 121:297 */         it.remove();
/* 122:298 */         break;
/* 123:    */       }
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected void broadcastNodeChanged(AbstractLinkedList.Node node)
/* 128:    */   {
/* 129:311 */     Iterator it = this.cursors.iterator();
/* 130:312 */     while (it.hasNext())
/* 131:    */     {
/* 132:313 */       WeakReference ref = (WeakReference)it.next();
/* 133:314 */       Cursor cursor = (Cursor)ref.get();
/* 134:315 */       if (cursor == null) {
/* 135:316 */         it.remove();
/* 136:    */       } else {
/* 137:318 */         cursor.nodeChanged(node);
/* 138:    */       }
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected void broadcastNodeRemoved(AbstractLinkedList.Node node)
/* 143:    */   {
/* 144:330 */     Iterator it = this.cursors.iterator();
/* 145:331 */     while (it.hasNext())
/* 146:    */     {
/* 147:332 */       WeakReference ref = (WeakReference)it.next();
/* 148:333 */       Cursor cursor = (Cursor)ref.get();
/* 149:334 */       if (cursor == null) {
/* 150:335 */         it.remove();
/* 151:    */       } else {
/* 152:337 */         cursor.nodeRemoved(node);
/* 153:    */       }
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected void broadcastNodeInserted(AbstractLinkedList.Node node)
/* 158:    */   {
/* 159:349 */     Iterator it = this.cursors.iterator();
/* 160:350 */     while (it.hasNext())
/* 161:    */     {
/* 162:351 */       WeakReference ref = (WeakReference)it.next();
/* 163:352 */       Cursor cursor = (Cursor)ref.get();
/* 164:353 */       if (cursor == null) {
/* 165:354 */         it.remove();
/* 166:    */       } else {
/* 167:356 */         cursor.nodeInserted(node);
/* 168:    */       }
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   private void writeObject(ObjectOutputStream out)
/* 173:    */     throws IOException
/* 174:    */   {
/* 175:366 */     out.defaultWriteObject();
/* 176:367 */     doWriteObject(out);
/* 177:    */   }
/* 178:    */   
/* 179:    */   private void readObject(ObjectInputStream in)
/* 180:    */     throws IOException, ClassNotFoundException
/* 181:    */   {
/* 182:374 */     in.defaultReadObject();
/* 183:375 */     doReadObject(in);
/* 184:    */   }
/* 185:    */   
/* 186:    */   protected ListIterator createSubListListIterator(AbstractLinkedList.LinkedSubList subList, int fromIndex)
/* 187:    */   {
/* 188:386 */     SubCursor cursor = new SubCursor(subList, fromIndex);
/* 189:387 */     registerCursor(cursor);
/* 190:388 */     return cursor;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static class Cursor
/* 194:    */     extends AbstractLinkedList.LinkedListIterator
/* 195:    */   {
/* 196:398 */     boolean valid = true;
/* 197:400 */     boolean nextIndexValid = true;
/* 198:402 */     boolean currentRemovedByAnother = false;
/* 199:    */     
/* 200:    */     protected Cursor(CursorableLinkedList parent, int index)
/* 201:    */     {
/* 202:410 */       super(index);
/* 203:411 */       this.valid = true;
/* 204:    */     }
/* 205:    */     
/* 206:    */     public void remove()
/* 207:    */     {
/* 208:428 */       if ((this.current != null) || (!this.currentRemovedByAnother))
/* 209:    */       {
/* 210:434 */         checkModCount();
/* 211:435 */         this.parent.removeNode(getLastNodeReturned());
/* 212:    */       }
/* 213:437 */       this.currentRemovedByAnother = false;
/* 214:    */     }
/* 215:    */     
/* 216:    */     public void add(Object obj)
/* 217:    */     {
/* 218:448 */       super.add(obj);
/* 219:    */       
/* 220:    */ 
/* 221:451 */       this.next = this.next.next;
/* 222:    */     }
/* 223:    */     
/* 224:    */     public int nextIndex()
/* 225:    */     {
/* 226:465 */       if (!this.nextIndexValid)
/* 227:    */       {
/* 228:466 */         if (this.next == this.parent.header)
/* 229:    */         {
/* 230:467 */           this.nextIndex = this.parent.size();
/* 231:    */         }
/* 232:    */         else
/* 233:    */         {
/* 234:469 */           int pos = 0;
/* 235:470 */           AbstractLinkedList.Node temp = this.parent.header.next;
/* 236:471 */           while (temp != this.next)
/* 237:    */           {
/* 238:472 */             pos++;
/* 239:473 */             temp = temp.next;
/* 240:    */           }
/* 241:475 */           this.nextIndex = pos;
/* 242:    */         }
/* 243:477 */         this.nextIndexValid = true;
/* 244:    */       }
/* 245:479 */       return this.nextIndex;
/* 246:    */     }
/* 247:    */     
/* 248:    */     protected void nodeChanged(AbstractLinkedList.Node node) {}
/* 249:    */     
/* 250:    */     protected void nodeRemoved(AbstractLinkedList.Node node)
/* 251:    */     {
/* 252:497 */       if ((node == this.next) && (node == this.current))
/* 253:    */       {
/* 254:499 */         this.next = node.next;
/* 255:500 */         this.current = null;
/* 256:501 */         this.currentRemovedByAnother = true;
/* 257:    */       }
/* 258:502 */       else if (node == this.next)
/* 259:    */       {
/* 260:505 */         this.next = node.next;
/* 261:506 */         this.currentRemovedByAnother = false;
/* 262:    */       }
/* 263:507 */       else if (node == this.current)
/* 264:    */       {
/* 265:510 */         this.current = null;
/* 266:511 */         this.currentRemovedByAnother = true;
/* 267:512 */         this.nextIndex -= 1;
/* 268:    */       }
/* 269:    */       else
/* 270:    */       {
/* 271:514 */         this.nextIndexValid = false;
/* 272:515 */         this.currentRemovedByAnother = false;
/* 273:    */       }
/* 274:    */     }
/* 275:    */     
/* 276:    */     protected void nodeInserted(AbstractLinkedList.Node node)
/* 277:    */     {
/* 278:525 */       if (node.previous == this.current) {
/* 279:526 */         this.next = node;
/* 280:527 */       } else if (this.next.previous == node) {
/* 281:528 */         this.next = node;
/* 282:    */       } else {
/* 283:530 */         this.nextIndexValid = false;
/* 284:    */       }
/* 285:    */     }
/* 286:    */     
/* 287:    */     protected void checkModCount()
/* 288:    */     {
/* 289:538 */       if (!this.valid) {
/* 290:539 */         throw new ConcurrentModificationException("Cursor closed");
/* 291:    */       }
/* 292:    */     }
/* 293:    */     
/* 294:    */     public void close()
/* 295:    */     {
/* 296:552 */       if (this.valid)
/* 297:    */       {
/* 298:553 */         ((CursorableLinkedList)this.parent).unregisterCursor(this);
/* 299:554 */         this.valid = false;
/* 300:    */       }
/* 301:    */     }
/* 302:    */   }
/* 303:    */   
/* 304:    */   protected static class SubCursor
/* 305:    */     extends CursorableLinkedList.Cursor
/* 306:    */   {
/* 307:    */     protected final AbstractLinkedList.LinkedSubList sub;
/* 308:    */     
/* 309:    */     protected SubCursor(AbstractLinkedList.LinkedSubList sub, int index)
/* 310:    */     {
/* 311:576 */       super(index + sub.offset);
/* 312:577 */       this.sub = sub;
/* 313:    */     }
/* 314:    */     
/* 315:    */     public boolean hasNext()
/* 316:    */     {
/* 317:581 */       return nextIndex() < this.sub.size;
/* 318:    */     }
/* 319:    */     
/* 320:    */     public boolean hasPrevious()
/* 321:    */     {
/* 322:585 */       return previousIndex() >= 0;
/* 323:    */     }
/* 324:    */     
/* 325:    */     public int nextIndex()
/* 326:    */     {
/* 327:589 */       return super.nextIndex() - this.sub.offset;
/* 328:    */     }
/* 329:    */     
/* 330:    */     public void add(Object obj)
/* 331:    */     {
/* 332:593 */       super.add(obj);
/* 333:594 */       this.sub.expectedModCount = this.parent.modCount;
/* 334:595 */       this.sub.size += 1;
/* 335:    */     }
/* 336:    */     
/* 337:    */     public void remove()
/* 338:    */     {
/* 339:599 */       super.remove();
/* 340:600 */       this.sub.expectedModCount = this.parent.modCount;
/* 341:601 */       this.sub.size -= 1;
/* 342:    */     }
/* 343:    */   }
/* 344:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.CursorableLinkedList
 * JD-Core Version:    0.7.0.1
 */