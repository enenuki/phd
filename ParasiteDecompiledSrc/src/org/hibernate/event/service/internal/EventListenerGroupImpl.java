/*   1:    */ package org.hibernate.event.service.internal;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.LinkedHashSet;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.ListIterator;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.event.service.spi.DuplicationStrategy;
/*  10:    */ import org.hibernate.event.service.spi.DuplicationStrategy.Action;
/*  11:    */ import org.hibernate.event.service.spi.EventListenerGroup;
/*  12:    */ import org.hibernate.event.service.spi.EventListenerRegistrationException;
/*  13:    */ import org.hibernate.event.spi.EventType;
/*  14:    */ 
/*  15:    */ public class EventListenerGroupImpl<T>
/*  16:    */   implements EventListenerGroup<T>
/*  17:    */ {
/*  18:    */   private EventType<T> eventType;
/*  19: 44 */   private final Set<DuplicationStrategy> duplicationStrategies = new LinkedHashSet();
/*  20:    */   private List<T> listeners;
/*  21:    */   
/*  22:    */   public EventListenerGroupImpl(EventType<T> eventType)
/*  23:    */   {
/*  24: 48 */     this.eventType = eventType;
/*  25: 49 */     this.duplicationStrategies.add(new DuplicationStrategy()
/*  26:    */     {
/*  27:    */       public boolean areMatch(Object listener, Object original)
/*  28:    */       {
/*  29: 54 */         return listener.getClass().equals(original.getClass());
/*  30:    */       }
/*  31:    */       
/*  32:    */       public DuplicationStrategy.Action getAction()
/*  33:    */       {
/*  34: 59 */         return DuplicationStrategy.Action.ERROR;
/*  35:    */       }
/*  36:    */     });
/*  37:    */   }
/*  38:    */   
/*  39:    */   public EventType<T> getEventType()
/*  40:    */   {
/*  41: 67 */     return this.eventType;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isEmpty()
/*  45:    */   {
/*  46: 72 */     return count() <= 0;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int count()
/*  50:    */   {
/*  51: 77 */     return this.listeners == null ? 0 : this.listeners.size();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void clear()
/*  55:    */   {
/*  56: 82 */     if (this.duplicationStrategies != null) {
/*  57: 83 */       this.duplicationStrategies.clear();
/*  58:    */     }
/*  59: 85 */     if (this.listeners != null) {
/*  60: 86 */       this.listeners.clear();
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void addDuplicationStrategy(DuplicationStrategy strategy)
/*  65:    */   {
/*  66: 92 */     this.duplicationStrategies.add(strategy);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Iterable<T> listeners()
/*  70:    */   {
/*  71: 96 */     return this.listeners == null ? Collections.emptyList() : this.listeners;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void appendListeners(T... listeners)
/*  75:    */   {
/*  76:101 */     for (T listener : listeners) {
/*  77:102 */       appendListener(listener);
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void appendListener(T listener)
/*  82:    */   {
/*  83:108 */     if (listenerShouldGetAdded(listener)) {
/*  84:109 */       internalAppend(listener);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void prependListeners(T... listeners)
/*  89:    */   {
/*  90:115 */     for (T listener : listeners) {
/*  91:116 */       prependListener(listener);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void prependListener(T listener)
/*  96:    */   {
/*  97:122 */     if (listenerShouldGetAdded(listener)) {
/*  98:123 */       internalPrepend(listener);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   private boolean listenerShouldGetAdded(T listener)
/* 103:    */   {
/* 104:128 */     if (this.listeners == null)
/* 105:    */     {
/* 106:129 */       this.listeners = new ArrayList();
/* 107:130 */       return true;
/* 108:    */     }
/* 109:134 */     boolean doAdd = true;
/* 110:135 */     for (DuplicationStrategy strategy : this.duplicationStrategies)
/* 111:    */     {
/* 112:136 */       ListIterator<T> itr = this.listeners.listIterator();
/* 113:137 */       while (itr.hasNext())
/* 114:    */       {
/* 115:138 */         T existingListener = itr.next();
/* 116:139 */         if (strategy.areMatch(listener, existingListener)) {
/* 117:140 */           switch (2.$SwitchMap$org$hibernate$event$service$spi$DuplicationStrategy$Action[strategy.getAction().ordinal()])
/* 118:    */           {
/* 119:    */           case 1: 
/* 120:143 */             throw new EventListenerRegistrationException("Duplicate event listener found");
/* 121:    */           case 2: 
/* 122:146 */             doAdd = false;
/* 123:147 */             break;
/* 124:    */           case 3: 
/* 125:150 */             itr.set(listener);
/* 126:151 */             doAdd = false;
/* 127:152 */             break;
/* 128:    */           }
/* 129:    */         }
/* 130:    */       }
/* 131:    */     }
/* 132:158 */     return doAdd;
/* 133:    */   }
/* 134:    */   
/* 135:    */   private void internalPrepend(T listener)
/* 136:    */   {
/* 137:162 */     checkAgainstBaseInterface(listener);
/* 138:163 */     this.listeners.add(0, listener);
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void checkAgainstBaseInterface(T listener)
/* 142:    */   {
/* 143:167 */     if (!this.eventType.baseListenerInterface().isInstance(listener)) {
/* 144:168 */       throw new EventListenerRegistrationException("Listener did not implement expected interface [" + this.eventType.baseListenerInterface().getName() + "]");
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   private void internalAppend(T listener)
/* 149:    */   {
/* 150:175 */     checkAgainstBaseInterface(listener);
/* 151:176 */     this.listeners.add(listener);
/* 152:    */   }
/* 153:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.service.internal.EventListenerGroupImpl
 * JD-Core Version:    0.7.0.1
 */