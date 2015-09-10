/*  1:   */ package org.jboss.jandex;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ class Result
/*  7:   */ {
/*  8:   */   private int annotations;
/*  9:   */   private int instances;
/* 10:   */   private int classes;
/* 11:   */   private int bytes;
/* 12:   */   private String name;
/* 13:   */   
/* 14:   */   Result(Index index, String name, int bytes)
/* 15:   */   {
/* 16:37 */     this.annotations = index.annotations.size();
/* 17:38 */     this.instances = countInstances(index);
/* 18:39 */     this.classes = index.classes.size();
/* 19:40 */     this.bytes = bytes;
/* 20:41 */     this.name = name;
/* 21:   */   }
/* 22:   */   
/* 23:   */   private int countInstances(Index index)
/* 24:   */   {
/* 25:45 */     int c = 0;
/* 26:46 */     for (List<AnnotationInstance> list : index.annotations.values()) {
/* 27:47 */       c += list.size();
/* 28:   */     }
/* 29:49 */     return c;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int getAnnotations()
/* 33:   */   {
/* 34:53 */     return this.annotations;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void setAnnotations(int annotations)
/* 38:   */   {
/* 39:57 */     this.annotations = annotations;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public int getBytes()
/* 43:   */   {
/* 44:61 */     return this.bytes;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void setBytes(int bytes)
/* 48:   */   {
/* 49:65 */     this.bytes = bytes;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int getClasses()
/* 53:   */   {
/* 54:69 */     return this.classes;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void setClasses(int classes)
/* 58:   */   {
/* 59:73 */     this.classes = classes;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public int getInstances()
/* 63:   */   {
/* 64:77 */     return this.instances;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void setInstances(int instances)
/* 68:   */   {
/* 69:81 */     this.instances = instances;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public String getName()
/* 73:   */   {
/* 74:85 */     return this.name;
/* 75:   */   }
/* 76:   */   
/* 77:   */   public void setName(String name)
/* 78:   */   {
/* 79:89 */     this.name = name;
/* 80:   */   }
/* 81:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.Result
 * JD-Core Version:    0.7.0.1
 */