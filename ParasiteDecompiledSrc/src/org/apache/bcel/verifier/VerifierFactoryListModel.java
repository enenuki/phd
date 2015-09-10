/*   1:    */ package org.apache.bcel.verifier;
/*   2:    */ 
/*   3:    */ import java.util.AbstractCollection;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.TreeSet;
/*   6:    */ import javax.swing.ListModel;
/*   7:    */ import javax.swing.event.ListDataEvent;
/*   8:    */ import javax.swing.event.ListDataListener;
/*   9:    */ 
/*  10:    */ public class VerifierFactoryListModel
/*  11:    */   implements VerifierFactoryObserver, ListModel
/*  12:    */ {
/*  13: 67 */   private ArrayList listeners = new ArrayList();
/*  14: 69 */   private TreeSet cache = new TreeSet();
/*  15:    */   
/*  16:    */   public VerifierFactoryListModel()
/*  17:    */   {
/*  18: 72 */     VerifierFactory.attach(this);
/*  19: 73 */     update(null);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public synchronized void update(String s)
/*  23:    */   {
/*  24: 77 */     int size = this.listeners.size();
/*  25:    */     
/*  26: 79 */     Verifier[] verifiers = VerifierFactory.getVerifiers();
/*  27: 80 */     int num_of_verifiers = verifiers.length;
/*  28: 81 */     this.cache.clear();
/*  29: 82 */     for (int i = 0; i < num_of_verifiers; i++) {
/*  30: 83 */       this.cache.add(verifiers[i].getClassName());
/*  31:    */     }
/*  32: 86 */     for (int i = 0; i < size; i++)
/*  33:    */     {
/*  34: 87 */       ListDataEvent e = new ListDataEvent(this, 0, 0, num_of_verifiers - 1);
/*  35: 88 */       ((ListDataListener)this.listeners.get(i)).contentsChanged(e);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public synchronized void addListDataListener(ListDataListener l)
/*  40:    */   {
/*  41: 93 */     this.listeners.add(l);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public synchronized void removeListDataListener(ListDataListener l)
/*  45:    */   {
/*  46: 97 */     this.listeners.remove(l);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public synchronized int getSize()
/*  50:    */   {
/*  51:101 */     return this.cache.size();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public synchronized Object getElementAt(int index)
/*  55:    */   {
/*  56:105 */     return this.cache.toArray()[index];
/*  57:    */   }
/*  58:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.VerifierFactoryListModel
 * JD-Core Version:    0.7.0.1
 */