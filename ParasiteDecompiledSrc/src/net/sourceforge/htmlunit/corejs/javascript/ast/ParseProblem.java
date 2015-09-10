/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class ParseProblem
/*   4:    */ {
/*   5:    */   private Type type;
/*   6:    */   private String message;
/*   7:    */   private String sourceName;
/*   8:    */   private int offset;
/*   9:    */   private int length;
/*  10:    */   
/*  11:    */   public static enum Type
/*  12:    */   {
/*  13: 46 */     Error,  Warning;
/*  14:    */     
/*  15:    */     private Type() {}
/*  16:    */   }
/*  17:    */   
/*  18:    */   public ParseProblem(Type type, String message, String sourceName, int offset, int length)
/*  19:    */   {
/*  20: 59 */     setType(type);
/*  21: 60 */     setMessage(message);
/*  22: 61 */     setSourceName(sourceName);
/*  23: 62 */     setFileOffset(offset);
/*  24: 63 */     setLength(length);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Type getType()
/*  28:    */   {
/*  29: 67 */     return this.type;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setType(Type type)
/*  33:    */   {
/*  34: 71 */     this.type = type;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getMessage()
/*  38:    */   {
/*  39: 75 */     return this.message;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setMessage(String msg)
/*  43:    */   {
/*  44: 79 */     this.message = msg;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getSourceName()
/*  48:    */   {
/*  49: 83 */     return this.sourceName;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setSourceName(String name)
/*  53:    */   {
/*  54: 87 */     this.sourceName = name;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getFileOffset()
/*  58:    */   {
/*  59: 91 */     return this.offset;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setFileOffset(int offset)
/*  63:    */   {
/*  64: 95 */     this.offset = offset;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getLength()
/*  68:    */   {
/*  69: 99 */     return this.length;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setLength(int length)
/*  73:    */   {
/*  74:103 */     this.length = length;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toString()
/*  78:    */   {
/*  79:108 */     StringBuilder sb = new StringBuilder(200);
/*  80:109 */     sb.append(this.sourceName).append(":");
/*  81:110 */     sb.append("offset=").append(this.offset).append(",");
/*  82:111 */     sb.append("length=").append(this.length).append(",");
/*  83:112 */     sb.append(this.type == Type.Error ? "error: " : "warning: ");
/*  84:113 */     sb.append(this.message);
/*  85:114 */     return sb.toString();
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ParseProblem
 * JD-Core Version:    0.7.0.1
 */