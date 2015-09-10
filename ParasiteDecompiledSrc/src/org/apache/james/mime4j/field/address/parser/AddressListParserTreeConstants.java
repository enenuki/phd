/*  1:   */ package org.apache.james.mime4j.field.address.parser;
/*  2:   */ 
/*  3:   */ public abstract interface AddressListParserTreeConstants
/*  4:   */ {
/*  5:   */   public static final int JJTVOID = 0;
/*  6:   */   public static final int JJTADDRESS_LIST = 1;
/*  7:   */   public static final int JJTADDRESS = 2;
/*  8:   */   public static final int JJTMAILBOX = 3;
/*  9:   */   public static final int JJTNAME_ADDR = 4;
/* 10:   */   public static final int JJTGROUP_BODY = 5;
/* 11:   */   public static final int JJTANGLE_ADDR = 6;
/* 12:   */   public static final int JJTROUTE = 7;
/* 13:   */   public static final int JJTPHRASE = 8;
/* 14:   */   public static final int JJTADDR_SPEC = 9;
/* 15:   */   public static final int JJTLOCAL_PART = 10;
/* 16:   */   public static final int JJTDOMAIN = 11;
/* 17:21 */   public static final String[] jjtNodeName = { "void", "address_list", "address", "mailbox", "name_addr", "group_body", "angle_addr", "route", "phrase", "addr_spec", "local_part", "domain" };
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.parser.AddressListParserTreeConstants
 * JD-Core Version:    0.7.0.1
 */