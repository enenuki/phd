/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Date;
/*  5:   */ import org.apache.http.annotation.NotThreadSafe;
/*  6:   */ import org.apache.http.cookie.SetCookie2;
/*  7:   */ 
/*  8:   */ @NotThreadSafe
/*  9:   */ public class BasicClientCookie2
/* 10:   */   extends BasicClientCookie
/* 11:   */   implements SetCookie2, Serializable
/* 12:   */ {
/* 13:   */   private static final long serialVersionUID = -7744598295706617057L;
/* 14:   */   private String commentURL;
/* 15:   */   private int[] ports;
/* 16:   */   private boolean discard;
/* 17:   */   
/* 18:   */   public BasicClientCookie2(String name, String value)
/* 19:   */   {
/* 20:58 */     super(name, value);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int[] getPorts()
/* 24:   */   {
/* 25:63 */     return this.ports;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setPorts(int[] ports)
/* 29:   */   {
/* 30:67 */     this.ports = ports;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getCommentURL()
/* 34:   */   {
/* 35:72 */     return this.commentURL;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setCommentURL(String commentURL)
/* 39:   */   {
/* 40:76 */     this.commentURL = commentURL;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void setDiscard(boolean discard)
/* 44:   */   {
/* 45:80 */     this.discard = discard;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public boolean isPersistent()
/* 49:   */   {
/* 50:85 */     return (!this.discard) && (super.isPersistent());
/* 51:   */   }
/* 52:   */   
/* 53:   */   public boolean isExpired(Date date)
/* 54:   */   {
/* 55:90 */     return (this.discard) || (super.isExpired(date));
/* 56:   */   }
/* 57:   */   
/* 58:   */   public Object clone()
/* 59:   */     throws CloneNotSupportedException
/* 60:   */   {
/* 61:95 */     BasicClientCookie2 clone = (BasicClientCookie2)super.clone();
/* 62:96 */     if (this.ports != null) {
/* 63:97 */       clone.ports = ((int[])this.ports.clone());
/* 64:   */     }
/* 65:99 */     return clone;
/* 66:   */   }
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.BasicClientCookie2
 * JD-Core Version:    0.7.0.1
 */