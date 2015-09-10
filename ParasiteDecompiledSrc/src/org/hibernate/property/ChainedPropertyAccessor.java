/*  1:   */ package org.hibernate.property;
/*  2:   */ 
/*  3:   */ import org.hibernate.PropertyNotFoundException;
/*  4:   */ 
/*  5:   */ public class ChainedPropertyAccessor
/*  6:   */   implements PropertyAccessor
/*  7:   */ {
/*  8:   */   final PropertyAccessor[] chain;
/*  9:   */   
/* 10:   */   public ChainedPropertyAccessor(PropertyAccessor[] chain)
/* 11:   */   {
/* 12:36 */     this.chain = chain;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Getter getGetter(Class theClass, String propertyName)
/* 16:   */     throws PropertyNotFoundException
/* 17:   */   {
/* 18:41 */     Getter result = null;
/* 19:42 */     for (int i = 0; i < this.chain.length; i++)
/* 20:   */     {
/* 21:43 */       PropertyAccessor candidate = this.chain[i];
/* 22:   */       try
/* 23:   */       {
/* 24:45 */         return candidate.getGetter(theClass, propertyName);
/* 25:   */       }
/* 26:   */       catch (PropertyNotFoundException pnfe) {}
/* 27:   */     }
/* 28:51 */     throw new PropertyNotFoundException("Could not find getter for " + propertyName + " on " + theClass);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Setter getSetter(Class theClass, String propertyName)
/* 32:   */     throws PropertyNotFoundException
/* 33:   */   {
/* 34:56 */     Setter result = null;
/* 35:57 */     for (int i = 0; i < this.chain.length; i++)
/* 36:   */     {
/* 37:58 */       PropertyAccessor candidate = this.chain[i];
/* 38:   */       try
/* 39:   */       {
/* 40:60 */         return candidate.getSetter(theClass, propertyName);
/* 41:   */       }
/* 42:   */       catch (PropertyNotFoundException pnfe) {}
/* 43:   */     }
/* 44:66 */     throw new PropertyNotFoundException("Could not find setter for " + propertyName + " on " + theClass);
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.property.ChainedPropertyAccessor
 * JD-Core Version:    0.7.0.1
 */