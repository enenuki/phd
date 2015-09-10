/*  1:   */ package org.apache.log4j.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.log4j.Category;
/*  5:   */ import org.apache.log4j.DefaultThrowableRenderer;
/*  6:   */ 
/*  7:   */ public class ThrowableInformation
/*  8:   */   implements Serializable
/*  9:   */ {
/* 10:   */   static final long serialVersionUID = -4748765566864322735L;
/* 11:   */   private transient Throwable throwable;
/* 12:   */   private transient Category category;
/* 13:   */   private String[] rep;
/* 14:   */   
/* 15:   */   public ThrowableInformation(Throwable throwable)
/* 16:   */   {
/* 17:46 */     this.throwable = throwable;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public ThrowableInformation(Throwable throwable, Category category)
/* 21:   */   {
/* 22:56 */     this.throwable = throwable;
/* 23:57 */     this.category = category;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public ThrowableInformation(String[] r)
/* 27:   */   {
/* 28:66 */     if (r != null) {
/* 29:67 */       this.rep = ((String[])r.clone());
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Throwable getThrowable()
/* 34:   */   {
/* 35:74 */     return this.throwable;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public synchronized String[] getThrowableStrRep()
/* 39:   */   {
/* 40:78 */     if (this.rep == null)
/* 41:   */     {
/* 42:79 */       ThrowableRenderer renderer = null;
/* 43:80 */       if (this.category != null)
/* 44:   */       {
/* 45:81 */         LoggerRepository repo = this.category.getLoggerRepository();
/* 46:82 */         if ((repo instanceof ThrowableRendererSupport)) {
/* 47:83 */           renderer = ((ThrowableRendererSupport)repo).getThrowableRenderer();
/* 48:   */         }
/* 49:   */       }
/* 50:86 */       if (renderer == null) {
/* 51:87 */         this.rep = DefaultThrowableRenderer.render(this.throwable);
/* 52:   */       } else {
/* 53:89 */         this.rep = renderer.doRender(this.throwable);
/* 54:   */       }
/* 55:   */     }
/* 56:92 */     return (String[])this.rep.clone();
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.spi.ThrowableInformation
 * JD-Core Version:    0.7.0.1
 */