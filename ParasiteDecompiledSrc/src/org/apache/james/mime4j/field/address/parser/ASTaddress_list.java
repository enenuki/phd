/*  1:   */ package org.apache.james.mime4j.field.address.parser;
/*  2:   */ 
/*  3:   */ public class ASTaddress_list
/*  4:   */   extends SimpleNode
/*  5:   */ {
/*  6:   */   public ASTaddress_list(int id)
/*  7:   */   {
/*  8: 7 */     super(id);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public ASTaddress_list(AddressListParser p, int id)
/* 12:   */   {
/* 13:11 */     super(p, id);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Object jjtAccept(AddressListParserVisitor visitor, Object data)
/* 17:   */   {
/* 18:17 */     return visitor.visit(this, data);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.parser.ASTaddress_list
 * JD-Core Version:    0.7.0.1
 */