/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ 
/*   5:    */ class ElemDesc
/*   6:    */ {
/*   7: 34 */   Hashtable m_attrs = null;
/*   8:    */   int m_flags;
/*   9:    */   static final int EMPTY = 2;
/*  10:    */   static final int FLOW = 4;
/*  11:    */   static final int BLOCK = 8;
/*  12:    */   static final int BLOCKFORM = 16;
/*  13:    */   static final int BLOCKFORMFIELDSET = 32;
/*  14:    */   static final int CDATA = 64;
/*  15:    */   static final int PCDATA = 128;
/*  16:    */   static final int RAW = 256;
/*  17:    */   static final int INLINE = 512;
/*  18:    */   static final int INLINEA = 1024;
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
/*  30:    */   static final int ATTRURL = 2;
/*  31:    */   static final int ATTREMPTY = 4;
/*  32:    */   
/*  33:    */   ElemDesc(int flags)
/*  34:    */   {
/*  35:121 */     this.m_flags = flags;
/*  36:    */   }
/*  37:    */   
/*  38:    */   boolean is(int flags)
/*  39:    */   {
/*  40:144 */     return (this.m_flags & flags) != 0;
/*  41:    */   }
/*  42:    */   
/*  43:    */   void setAttr(String name, int flags)
/*  44:    */   {
/*  45:157 */     if (null == this.m_attrs) {
/*  46:158 */       this.m_attrs = new Hashtable();
/*  47:    */     }
/*  48:160 */     this.m_attrs.put(name, new Integer(flags));
/*  49:    */   }
/*  50:    */   
/*  51:    */   boolean isAttrFlagSet(String name, int flags)
/*  52:    */   {
/*  53:177 */     if (null != this.m_attrs)
/*  54:    */     {
/*  55:179 */       Integer _flags = (Integer)this.m_attrs.get(name);
/*  56:181 */       if (null != _flags) {
/*  57:183 */         return (_flags.intValue() & flags) != 0;
/*  58:    */       }
/*  59:    */     }
/*  60:187 */     return false;
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.ElemDesc
 * JD-Core Version:    0.7.0.1
 */