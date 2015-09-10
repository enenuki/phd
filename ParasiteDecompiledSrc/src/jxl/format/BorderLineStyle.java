/*   1:    */ package jxl.format;
/*   2:    */ 
/*   3:    */ public class BorderLineStyle
/*   4:    */ {
/*   5:    */   private int value;
/*   6:    */   private String string;
/*   7: 40 */   private static BorderLineStyle[] styles = new BorderLineStyle[0];
/*   8:    */   
/*   9:    */   protected BorderLineStyle(int val, String s)
/*  10:    */   {
/*  11: 48 */     this.value = val;
/*  12: 49 */     this.string = s;
/*  13:    */     
/*  14: 51 */     BorderLineStyle[] oldstyles = styles;
/*  15: 52 */     styles = new BorderLineStyle[oldstyles.length + 1];
/*  16: 53 */     System.arraycopy(oldstyles, 0, styles, 0, oldstyles.length);
/*  17: 54 */     styles[oldstyles.length] = this;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public int getValue()
/*  21:    */   {
/*  22: 64 */     return this.value;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String getDescription()
/*  26:    */   {
/*  27: 72 */     return this.string;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static BorderLineStyle getStyle(int val)
/*  31:    */   {
/*  32: 83 */     for (int i = 0; i < styles.length; i++) {
/*  33: 85 */       if (styles[i].getValue() == val) {
/*  34: 87 */         return styles[i];
/*  35:    */       }
/*  36:    */     }
/*  37: 91 */     return NONE;
/*  38:    */   }
/*  39:    */   
/*  40: 94 */   public static final BorderLineStyle NONE = new BorderLineStyle(0, "none");
/*  41: 96 */   public static final BorderLineStyle THIN = new BorderLineStyle(1, "thin");
/*  42: 98 */   public static final BorderLineStyle MEDIUM = new BorderLineStyle(2, "medium");
/*  43:100 */   public static final BorderLineStyle DASHED = new BorderLineStyle(3, "dashed");
/*  44:102 */   public static final BorderLineStyle DOTTED = new BorderLineStyle(4, "dotted");
/*  45:104 */   public static final BorderLineStyle THICK = new BorderLineStyle(5, "thick");
/*  46:106 */   public static final BorderLineStyle DOUBLE = new BorderLineStyle(6, "double");
/*  47:108 */   public static final BorderLineStyle HAIR = new BorderLineStyle(7, "hair");
/*  48:110 */   public static final BorderLineStyle MEDIUM_DASHED = new BorderLineStyle(8, "medium dashed");
/*  49:112 */   public static final BorderLineStyle DASH_DOT = new BorderLineStyle(9, "dash dot");
/*  50:114 */   public static final BorderLineStyle MEDIUM_DASH_DOT = new BorderLineStyle(10, "medium dash dot");
/*  51:116 */   public static final BorderLineStyle DASH_DOT_DOT = new BorderLineStyle(11, "Dash dot dot");
/*  52:118 */   public static final BorderLineStyle MEDIUM_DASH_DOT_DOT = new BorderLineStyle(12, "Medium dash dot dot");
/*  53:120 */   public static final BorderLineStyle SLANTED_DASH_DOT = new BorderLineStyle(13, "Slanted dash dot");
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.format.BorderLineStyle
 * JD-Core Version:    0.7.0.1
 */