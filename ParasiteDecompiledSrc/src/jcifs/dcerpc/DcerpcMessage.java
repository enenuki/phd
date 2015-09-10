/*   1:    */ package jcifs.dcerpc;
/*   2:    */ 
/*   3:    */ import jcifs.dcerpc.ndr.NdrBuffer;
/*   4:    */ import jcifs.dcerpc.ndr.NdrException;
/*   5:    */ import jcifs.dcerpc.ndr.NdrObject;
/*   6:    */ 
/*   7:    */ public abstract class DcerpcMessage
/*   8:    */   extends NdrObject
/*   9:    */   implements DcerpcConstants
/*  10:    */ {
/*  11: 27 */   protected int ptype = -1;
/*  12: 28 */   protected int flags = 0;
/*  13: 29 */   protected int length = 0;
/*  14: 30 */   protected int call_id = 0;
/*  15: 31 */   protected int alloc_hint = 0;
/*  16: 32 */   protected int result = 0;
/*  17:    */   
/*  18:    */   public boolean isFlagSet(int flag)
/*  19:    */   {
/*  20: 35 */     return (this.flags & flag) == flag;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void unsetFlag(int flag)
/*  24:    */   {
/*  25: 38 */     this.flags &= (flag ^ 0xFFFFFFFF);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setFlag(int flag)
/*  29:    */   {
/*  30: 41 */     this.flags |= flag;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public DcerpcException getResult()
/*  34:    */   {
/*  35: 44 */     if (this.result != 0) {
/*  36: 45 */       return new DcerpcException(this.result);
/*  37:    */     }
/*  38: 46 */     return null;
/*  39:    */   }
/*  40:    */   
/*  41:    */   void encode_header(NdrBuffer buf)
/*  42:    */   {
/*  43: 50 */     buf.enc_ndr_small(5);
/*  44: 51 */     buf.enc_ndr_small(0);
/*  45: 52 */     buf.enc_ndr_small(this.ptype);
/*  46: 53 */     buf.enc_ndr_small(this.flags);
/*  47: 54 */     buf.enc_ndr_long(16);
/*  48: 55 */     buf.enc_ndr_short(this.length);
/*  49: 56 */     buf.enc_ndr_short(0);
/*  50: 57 */     buf.enc_ndr_long(this.call_id);
/*  51:    */   }
/*  52:    */   
/*  53:    */   void decode_header(NdrBuffer buf)
/*  54:    */     throws NdrException
/*  55:    */   {
/*  56: 61 */     if ((buf.dec_ndr_small() != 5) || (buf.dec_ndr_small() != 0)) {
/*  57: 62 */       throw new NdrException("DCERPC version not supported");
/*  58:    */     }
/*  59: 63 */     this.ptype = buf.dec_ndr_small();
/*  60: 64 */     this.flags = buf.dec_ndr_small();
/*  61: 65 */     if (buf.dec_ndr_long() != 16) {
/*  62: 66 */       throw new NdrException("Data representation not supported");
/*  63:    */     }
/*  64: 67 */     this.length = buf.dec_ndr_short();
/*  65: 68 */     if (buf.dec_ndr_short() != 0) {
/*  66: 69 */       throw new NdrException("DCERPC authentication not supported");
/*  67:    */     }
/*  68: 70 */     this.call_id = buf.dec_ndr_long();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void encode(NdrBuffer buf)
/*  72:    */     throws NdrException
/*  73:    */   {
/*  74: 73 */     int start = buf.getIndex();
/*  75: 74 */     int alloc_hint_index = 0;
/*  76:    */     
/*  77: 76 */     buf.advance(16);
/*  78: 77 */     if (this.ptype == 0)
/*  79:    */     {
/*  80: 78 */       alloc_hint_index = buf.getIndex();
/*  81: 79 */       buf.enc_ndr_long(0);
/*  82: 80 */       buf.enc_ndr_short(0);
/*  83: 81 */       buf.enc_ndr_short(getOpnum());
/*  84:    */     }
/*  85: 84 */     encode_in(buf);
/*  86: 85 */     this.length = (buf.getIndex() - start);
/*  87: 87 */     if (this.ptype == 0)
/*  88:    */     {
/*  89: 88 */       buf.setIndex(alloc_hint_index);
/*  90: 89 */       this.alloc_hint = (this.length - alloc_hint_index);
/*  91: 90 */       buf.enc_ndr_long(this.alloc_hint);
/*  92:    */     }
/*  93: 93 */     buf.setIndex(start);
/*  94: 94 */     encode_header(buf);
/*  95: 95 */     buf.setIndex(start + this.length);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void decode(NdrBuffer buf)
/*  99:    */     throws NdrException
/* 100:    */   {
/* 101: 98 */     decode_header(buf);
/* 102:100 */     if ((this.ptype != 12) && (this.ptype != 2) && (this.ptype != 3) && (this.ptype != 13)) {
/* 103:101 */       throw new NdrException("Unexpected ptype: " + this.ptype);
/* 104:    */     }
/* 105:103 */     if ((this.ptype == 2) || (this.ptype == 3))
/* 106:    */     {
/* 107:104 */       this.alloc_hint = buf.dec_ndr_long();
/* 108:105 */       buf.dec_ndr_short();
/* 109:106 */       buf.dec_ndr_short();
/* 110:    */     }
/* 111:108 */     if ((this.ptype == 3) || (this.ptype == 13)) {
/* 112:109 */       this.result = buf.dec_ndr_long();
/* 113:    */     } else {
/* 114:111 */       decode_out(buf);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public abstract int getOpnum();
/* 119:    */   
/* 120:    */   public abstract void encode_in(NdrBuffer paramNdrBuffer)
/* 121:    */     throws NdrException;
/* 122:    */   
/* 123:    */   public abstract void decode_out(NdrBuffer paramNdrBuffer)
/* 124:    */     throws NdrException;
/* 125:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.DcerpcMessage
 * JD-Core Version:    0.7.0.1
 */