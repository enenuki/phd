/*    1:     */ package org.apache.commons.collections;
/*    2:     */ 
/*    3:     */ import java.util.Collection;
/*    4:     */ import java.util.ConcurrentModificationException;
/*    5:     */ import java.util.Iterator;
/*    6:     */ import java.util.List;
/*    7:     */ import java.util.ListIterator;
/*    8:     */ 
/*    9:     */ /**
/*   10:     */  * @deprecated
/*   11:     */  */
/*   12:     */ class CursorableSubList
/*   13:     */   extends CursorableLinkedList
/*   14:     */   implements List
/*   15:     */ {
/*   16:     */   CursorableSubList(CursorableLinkedList list, int from, int to)
/*   17:     */   {
/*   18:1207 */     if ((0 > from) || (list.size() < to)) {
/*   19:1208 */       throw new IndexOutOfBoundsException();
/*   20:     */     }
/*   21:1209 */     if (from > to) {
/*   22:1210 */       throw new IllegalArgumentException();
/*   23:     */     }
/*   24:1212 */     this._list = list;
/*   25:1213 */     if (from < list.size())
/*   26:     */     {
/*   27:1214 */       this._head.setNext(this._list.getListableAt(from));
/*   28:1215 */       this._pre = (null == this._head.next() ? null : this._head.next().prev());
/*   29:     */     }
/*   30:     */     else
/*   31:     */     {
/*   32:1217 */       this._pre = this._list.getListableAt(from - 1);
/*   33:     */     }
/*   34:1219 */     if (from == to)
/*   35:     */     {
/*   36:1220 */       this._head.setNext(null);
/*   37:1221 */       this._head.setPrev(null);
/*   38:1222 */       if (to < list.size()) {
/*   39:1223 */         this._post = this._list.getListableAt(to);
/*   40:     */       } else {
/*   41:1225 */         this._post = null;
/*   42:     */       }
/*   43:     */     }
/*   44:     */     else
/*   45:     */     {
/*   46:1228 */       this._head.setPrev(this._list.getListableAt(to - 1));
/*   47:1229 */       this._post = this._head.prev().next();
/*   48:     */     }
/*   49:1231 */     this._size = (to - from);
/*   50:1232 */     this._modCount = this._list._modCount;
/*   51:     */   }
/*   52:     */   
/*   53:     */   public void clear()
/*   54:     */   {
/*   55:1238 */     checkForComod();
/*   56:1239 */     Iterator it = iterator();
/*   57:1240 */     while (it.hasNext())
/*   58:     */     {
/*   59:1241 */       it.next();
/*   60:1242 */       it.remove();
/*   61:     */     }
/*   62:     */   }
/*   63:     */   
/*   64:     */   public Iterator iterator()
/*   65:     */   {
/*   66:1247 */     checkForComod();
/*   67:1248 */     return super.iterator();
/*   68:     */   }
/*   69:     */   
/*   70:     */   public int size()
/*   71:     */   {
/*   72:1252 */     checkForComod();
/*   73:1253 */     return super.size();
/*   74:     */   }
/*   75:     */   
/*   76:     */   public boolean isEmpty()
/*   77:     */   {
/*   78:1257 */     checkForComod();
/*   79:1258 */     return super.isEmpty();
/*   80:     */   }
/*   81:     */   
/*   82:     */   public Object[] toArray()
/*   83:     */   {
/*   84:1262 */     checkForComod();
/*   85:1263 */     return super.toArray();
/*   86:     */   }
/*   87:     */   
/*   88:     */   public Object[] toArray(Object[] a)
/*   89:     */   {
/*   90:1267 */     checkForComod();
/*   91:1268 */     return super.toArray(a);
/*   92:     */   }
/*   93:     */   
/*   94:     */   public boolean contains(Object o)
/*   95:     */   {
/*   96:1272 */     checkForComod();
/*   97:1273 */     return super.contains(o);
/*   98:     */   }
/*   99:     */   
/*  100:     */   public boolean remove(Object o)
/*  101:     */   {
/*  102:1277 */     checkForComod();
/*  103:1278 */     return super.remove(o);
/*  104:     */   }
/*  105:     */   
/*  106:     */   public Object removeFirst()
/*  107:     */   {
/*  108:1282 */     checkForComod();
/*  109:1283 */     return super.removeFirst();
/*  110:     */   }
/*  111:     */   
/*  112:     */   public Object removeLast()
/*  113:     */   {
/*  114:1287 */     checkForComod();
/*  115:1288 */     return super.removeLast();
/*  116:     */   }
/*  117:     */   
/*  118:     */   public boolean addAll(Collection c)
/*  119:     */   {
/*  120:1292 */     checkForComod();
/*  121:1293 */     return super.addAll(c);
/*  122:     */   }
/*  123:     */   
/*  124:     */   public boolean add(Object o)
/*  125:     */   {
/*  126:1297 */     checkForComod();
/*  127:1298 */     return super.add(o);
/*  128:     */   }
/*  129:     */   
/*  130:     */   public boolean addFirst(Object o)
/*  131:     */   {
/*  132:1302 */     checkForComod();
/*  133:1303 */     return super.addFirst(o);
/*  134:     */   }
/*  135:     */   
/*  136:     */   public boolean addLast(Object o)
/*  137:     */   {
/*  138:1307 */     checkForComod();
/*  139:1308 */     return super.addLast(o);
/*  140:     */   }
/*  141:     */   
/*  142:     */   public boolean removeAll(Collection c)
/*  143:     */   {
/*  144:1312 */     checkForComod();
/*  145:1313 */     return super.removeAll(c);
/*  146:     */   }
/*  147:     */   
/*  148:     */   public boolean containsAll(Collection c)
/*  149:     */   {
/*  150:1317 */     checkForComod();
/*  151:1318 */     return super.containsAll(c);
/*  152:     */   }
/*  153:     */   
/*  154:     */   public boolean addAll(int index, Collection c)
/*  155:     */   {
/*  156:1322 */     checkForComod();
/*  157:1323 */     return super.addAll(index, c);
/*  158:     */   }
/*  159:     */   
/*  160:     */   public int hashCode()
/*  161:     */   {
/*  162:1327 */     checkForComod();
/*  163:1328 */     return super.hashCode();
/*  164:     */   }
/*  165:     */   
/*  166:     */   public boolean retainAll(Collection c)
/*  167:     */   {
/*  168:1332 */     checkForComod();
/*  169:1333 */     return super.retainAll(c);
/*  170:     */   }
/*  171:     */   
/*  172:     */   public Object set(int index, Object element)
/*  173:     */   {
/*  174:1337 */     checkForComod();
/*  175:1338 */     return super.set(index, element);
/*  176:     */   }
/*  177:     */   
/*  178:     */   public boolean equals(Object o)
/*  179:     */   {
/*  180:1342 */     checkForComod();
/*  181:1343 */     return super.equals(o);
/*  182:     */   }
/*  183:     */   
/*  184:     */   public Object get(int index)
/*  185:     */   {
/*  186:1347 */     checkForComod();
/*  187:1348 */     return super.get(index);
/*  188:     */   }
/*  189:     */   
/*  190:     */   public Object getFirst()
/*  191:     */   {
/*  192:1352 */     checkForComod();
/*  193:1353 */     return super.getFirst();
/*  194:     */   }
/*  195:     */   
/*  196:     */   public Object getLast()
/*  197:     */   {
/*  198:1357 */     checkForComod();
/*  199:1358 */     return super.getLast();
/*  200:     */   }
/*  201:     */   
/*  202:     */   public void add(int index, Object element)
/*  203:     */   {
/*  204:1362 */     checkForComod();
/*  205:1363 */     super.add(index, element);
/*  206:     */   }
/*  207:     */   
/*  208:     */   public ListIterator listIterator(int index)
/*  209:     */   {
/*  210:1367 */     checkForComod();
/*  211:1368 */     return super.listIterator(index);
/*  212:     */   }
/*  213:     */   
/*  214:     */   public Object remove(int index)
/*  215:     */   {
/*  216:1372 */     checkForComod();
/*  217:1373 */     return super.remove(index);
/*  218:     */   }
/*  219:     */   
/*  220:     */   public int indexOf(Object o)
/*  221:     */   {
/*  222:1377 */     checkForComod();
/*  223:1378 */     return super.indexOf(o);
/*  224:     */   }
/*  225:     */   
/*  226:     */   public int lastIndexOf(Object o)
/*  227:     */   {
/*  228:1382 */     checkForComod();
/*  229:1383 */     return super.lastIndexOf(o);
/*  230:     */   }
/*  231:     */   
/*  232:     */   public ListIterator listIterator()
/*  233:     */   {
/*  234:1387 */     checkForComod();
/*  235:1388 */     return super.listIterator();
/*  236:     */   }
/*  237:     */   
/*  238:     */   public List subList(int fromIndex, int toIndex)
/*  239:     */   {
/*  240:1392 */     checkForComod();
/*  241:1393 */     return super.subList(fromIndex, toIndex);
/*  242:     */   }
/*  243:     */   
/*  244:     */   protected CursorableLinkedList.Listable insertListable(CursorableLinkedList.Listable before, CursorableLinkedList.Listable after, Object value)
/*  245:     */   {
/*  246:1406 */     this._modCount += 1;
/*  247:1407 */     this._size += 1;
/*  248:1408 */     CursorableLinkedList.Listable elt = this._list.insertListable(null == before ? this._pre : before, null == after ? this._post : after, value);
/*  249:1409 */     if (null == this._head.next())
/*  250:     */     {
/*  251:1410 */       this._head.setNext(elt);
/*  252:1411 */       this._head.setPrev(elt);
/*  253:     */     }
/*  254:1413 */     if (before == this._head.prev()) {
/*  255:1414 */       this._head.setPrev(elt);
/*  256:     */     }
/*  257:1416 */     if (after == this._head.next()) {
/*  258:1417 */       this._head.setNext(elt);
/*  259:     */     }
/*  260:1419 */     broadcastListableInserted(elt);
/*  261:1420 */     return elt;
/*  262:     */   }
/*  263:     */   
/*  264:     */   protected void removeListable(CursorableLinkedList.Listable elt)
/*  265:     */   {
/*  266:1427 */     this._modCount += 1;
/*  267:1428 */     this._size -= 1;
/*  268:1429 */     if ((this._head.next() == elt) && (this._head.prev() == elt))
/*  269:     */     {
/*  270:1430 */       this._head.setNext(null);
/*  271:1431 */       this._head.setPrev(null);
/*  272:     */     }
/*  273:1433 */     if (this._head.next() == elt) {
/*  274:1434 */       this._head.setNext(elt.next());
/*  275:     */     }
/*  276:1436 */     if (this._head.prev() == elt) {
/*  277:1437 */       this._head.setPrev(elt.prev());
/*  278:     */     }
/*  279:1439 */     this._list.removeListable(elt);
/*  280:1440 */     broadcastListableRemoved(elt);
/*  281:     */   }
/*  282:     */   
/*  283:     */   protected void checkForComod()
/*  284:     */     throws ConcurrentModificationException
/*  285:     */   {
/*  286:1452 */     if (this._modCount != this._list._modCount) {
/*  287:1453 */       throw new ConcurrentModificationException();
/*  288:     */     }
/*  289:     */   }
/*  290:     */   
/*  291:1460 */   protected CursorableLinkedList _list = null;
/*  292:1463 */   protected CursorableLinkedList.Listable _pre = null;
/*  293:1466 */   protected CursorableLinkedList.Listable _post = null;
/*  294:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.CursorableSubList
 * JD-Core Version:    0.7.0.1
 */