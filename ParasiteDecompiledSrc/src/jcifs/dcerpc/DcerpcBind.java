/*  1:   */ package jcifs.dcerpc;
/*  2:   */ 
/*  3:   */ import jcifs.dcerpc.ndr.NdrBuffer;
/*  4:   */ import jcifs.dcerpc.ndr.NdrException;
/*  5:   */ import jcifs.util.Hexdump;
/*  6:   */ 
/*  7:   */ public class DcerpcBind
/*  8:   */   extends DcerpcMessage
/*  9:   */ {
/* 10:26 */   static final String[] result_message = { "0", "DCERPC_BIND_ERR_ABSTRACT_SYNTAX_NOT_SUPPORTED", "DCERPC_BIND_ERR_PROPOSED_TRANSFER_SYNTAXES_NOT_SUPPORTED", "DCERPC_BIND_ERR_LOCAL_LIMIT_EXCEEDED" };
/* 11:   */   DcerpcBinding binding;
/* 12:   */   int max_xmit;
/* 13:   */   int max_recv;
/* 14:   */   
/* 15:   */   static String getResultMessage(int result)
/* 16:   */   {
/* 17:34 */     return "0x" + Hexdump.toHexString(result, 4);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public DcerpcException getResult()
/* 21:   */   {
/* 22:39 */     if (this.result != 0) {
/* 23:40 */       return new DcerpcException(getResultMessage(this.result));
/* 24:   */     }
/* 25:41 */     return null;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public DcerpcBind() {}
/* 29:   */   
/* 30:   */   DcerpcBind(DcerpcBinding binding, DcerpcHandle handle)
/* 31:   */   {
/* 32:50 */     this.binding = binding;
/* 33:51 */     this.max_xmit = handle.max_xmit;
/* 34:52 */     this.max_recv = handle.max_recv;
/* 35:53 */     this.ptype = 11;
/* 36:54 */     this.flags = 3;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public int getOpnum()
/* 40:   */   {
/* 41:58 */     return 0;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void encode_in(NdrBuffer buf)
/* 45:   */     throws NdrException
/* 46:   */   {
/* 47:61 */     buf.enc_ndr_short(this.max_xmit);
/* 48:62 */     buf.enc_ndr_short(this.max_recv);
/* 49:63 */     buf.enc_ndr_long(0);
/* 50:64 */     buf.enc_ndr_small(1);
/* 51:65 */     buf.enc_ndr_small(0);
/* 52:66 */     buf.enc_ndr_short(0);
/* 53:67 */     buf.enc_ndr_short(0);
/* 54:68 */     buf.enc_ndr_small(1);
/* 55:69 */     buf.enc_ndr_small(0);
/* 56:70 */     this.binding.uuid.encode(buf);
/* 57:71 */     buf.enc_ndr_short(this.binding.major);
/* 58:72 */     buf.enc_ndr_short(this.binding.minor);
/* 59:73 */     DcerpcConstants.DCERPC_UUID_SYNTAX_NDR.encode(buf);
/* 60:74 */     buf.enc_ndr_long(2);
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void decode_out(NdrBuffer buf)
/* 64:   */     throws NdrException
/* 65:   */   {
/* 66:77 */     buf.dec_ndr_short();
/* 67:78 */     buf.dec_ndr_short();
/* 68:79 */     buf.dec_ndr_long();
/* 69:80 */     int n = buf.dec_ndr_short();
/* 70:81 */     buf.advance(n);
/* 71:82 */     buf.align(4);
/* 72:83 */     buf.dec_ndr_small();
/* 73:84 */     buf.align(4);
/* 74:85 */     this.result = buf.dec_ndr_short();
/* 75:86 */     buf.dec_ndr_short();
/* 76:87 */     buf.advance(20);
/* 77:   */   }
/* 78:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.DcerpcBind
 * JD-Core Version:    0.7.0.1
 */