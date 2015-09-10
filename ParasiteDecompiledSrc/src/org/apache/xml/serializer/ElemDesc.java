/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import org.apache.xml.serializer.utils.StringToIntTable;
/*   4:    */ 
/*   5:    */ public final class ElemDesc
/*   6:    */ {
/*   7:    */   private int m_flags;
/*   8: 42 */   private StringToIntTable m_attrs = null;
/*   9:    */   static final int EMPTY = 2;
/*  10:    */   private static final int FLOW = 4;
/*  11:    */   static final int BLOCK = 8;
/*  12:    */   static final int BLOCKFORM = 16;
/*  13:    */   static final int BLOCKFORMFIELDSET = 32;
/*  14:    */   private static final int CDATA = 64;
/*  15:    */   private static final int PCDATA = 128;
/*  16:    */   static final int RAW = 256;
/*  17:    */   private static final int INLINE = 512;
/*  18:    */   private static final int INLINEA = 1024;
/*  19:    */   static final int INLINELABEL = 2048;
/*  20:    */   static final int FONTSTYLE = 4096;
/*  21:    */   static final int PHRASE = 8192;
/*  22:    */   static final int FORMCTRL = 16384;
/*  23:    */   static final int SPECIAL = 32768;
/*  24:    */   static final int ASPECIAL = 65536;
/*  25:    */   static final int HEADMISC = 131072;
/*  26:    */   static final int HEAD = 262144;
/*  27:    */   static final int LIST = 524288;
/*  28:    */   static final int PREFORMATTED = 1048576;
/*  29:    */   static final int WHITESPACESENSITIVE = 2097152;
/*  30:    */   static final int HEADELEM = 4194304;
/*  31:    */   static final int HTMLELEM = 8388608;
/*  32:    */   public static final int ATTRURL = 2;
/*  33:    */   public static final int ATTREMPTY = 4;
/*  34:    */   
/*  35:    */   ElemDesc(int flags)
/*  36:    */   {
/*  37:127 */     this.m_flags = flags;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private boolean is(int flags)
/*  41:    */   {
/*  42:142 */     return (this.m_flags & flags) != 0;
/*  43:    */   }
/*  44:    */   
/*  45:    */   int getFlags()
/*  46:    */   {
/*  47:146 */     return this.m_flags;
/*  48:    */   }
/*  49:    */   
/*  50:    */   void setAttr(String name, int flags)
/*  51:    */   {
/*  52:159 */     if (null == this.m_attrs) {
/*  53:160 */       this.m_attrs = new StringToIntTable();
/*  54:    */     }
/*  55:162 */     this.m_attrs.put(name, flags);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean isAttrFlagSet(String name, int flags)
/*  59:    */   {
/*  60:175 */     return (this.m_attrs.getIgnoreCase(name) & flags) != 0;
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.ElemDesc
 * JD-Core Version:    0.7.0.1
 */