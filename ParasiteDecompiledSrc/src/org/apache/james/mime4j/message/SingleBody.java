/*  1:   */ package org.apache.james.mime4j.message;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.OutputStream;
/*  5:   */ 
/*  6:   */ public abstract class SingleBody
/*  7:   */   implements Body
/*  8:   */ {
/*  9:32 */   private Entity parent = null;
/* 10:   */   
/* 11:   */   public Entity getParent()
/* 12:   */   {
/* 13:44 */     return this.parent;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setParent(Entity parent)
/* 17:   */   {
/* 18:51 */     this.parent = parent;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public abstract void writeTo(OutputStream paramOutputStream)
/* 22:   */     throws IOException;
/* 23:   */   
/* 24:   */   public SingleBody copy()
/* 25:   */   {
/* 26:89 */     throw new UnsupportedOperationException();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void dispose() {}
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.SingleBody
 * JD-Core Version:    0.7.0.1
 */