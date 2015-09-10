/*  1:   */ package org.apache.james.mime4j.parser;
/*  2:   */ 
/*  3:   */ import java.io.InputStream;
/*  4:   */ import org.apache.james.mime4j.descriptor.BodyDescriptor;
/*  5:   */ 
/*  6:   */ public class RawEntity
/*  7:   */   implements EntityStateMachine
/*  8:   */ {
/*  9:   */   private final InputStream stream;
/* 10:   */   private int state;
/* 11:   */   
/* 12:   */   RawEntity(InputStream stream)
/* 13:   */   {
/* 14:38 */     this.stream = stream;
/* 15:39 */     this.state = 2;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getState()
/* 19:   */   {
/* 20:43 */     return this.state;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setRecursionMode(int recursionMode) {}
/* 24:   */   
/* 25:   */   public EntityStateMachine advance()
/* 26:   */   {
/* 27:53 */     this.state = -1;
/* 28:54 */     return null;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public InputStream getContentStream()
/* 32:   */   {
/* 33:61 */     return this.stream;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public BodyDescriptor getBodyDescriptor()
/* 37:   */   {
/* 38:68 */     return null;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Field getField()
/* 42:   */   {
/* 43:75 */     return null;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String getFieldName()
/* 47:   */   {
/* 48:82 */     return null;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String getFieldValue()
/* 52:   */   {
/* 53:89 */     return null;
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.parser.RawEntity
 * JD-Core Version:    0.7.0.1
 */