/*   1:    */ package org.apache.james.mime4j.field.address;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.james.mime4j.codec.DecoderUtil;
/*   7:    */ import org.apache.james.mime4j.field.address.parser.ASTaddr_spec;
/*   8:    */ import org.apache.james.mime4j.field.address.parser.ASTaddress;
/*   9:    */ import org.apache.james.mime4j.field.address.parser.ASTaddress_list;
/*  10:    */ import org.apache.james.mime4j.field.address.parser.ASTangle_addr;
/*  11:    */ import org.apache.james.mime4j.field.address.parser.ASTdomain;
/*  12:    */ import org.apache.james.mime4j.field.address.parser.ASTgroup_body;
/*  13:    */ import org.apache.james.mime4j.field.address.parser.ASTlocal_part;
/*  14:    */ import org.apache.james.mime4j.field.address.parser.ASTmailbox;
/*  15:    */ import org.apache.james.mime4j.field.address.parser.ASTname_addr;
/*  16:    */ import org.apache.james.mime4j.field.address.parser.ASTphrase;
/*  17:    */ import org.apache.james.mime4j.field.address.parser.ASTroute;
/*  18:    */ import org.apache.james.mime4j.field.address.parser.Node;
/*  19:    */ import org.apache.james.mime4j.field.address.parser.SimpleNode;
/*  20:    */ import org.apache.james.mime4j.field.address.parser.Token;
/*  21:    */ 
/*  22:    */ class Builder
/*  23:    */ {
/*  24: 48 */   private static Builder singleton = new Builder();
/*  25:    */   
/*  26:    */   public static Builder getInstance()
/*  27:    */   {
/*  28: 51 */     return singleton;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public AddressList buildAddressList(ASTaddress_list node)
/*  32:    */   {
/*  33: 55 */     List<Address> list = new ArrayList();
/*  34: 56 */     for (int i = 0; i < node.jjtGetNumChildren(); i++)
/*  35:    */     {
/*  36: 57 */       ASTaddress childNode = (ASTaddress)node.jjtGetChild(i);
/*  37: 58 */       Address address = buildAddress(childNode);
/*  38: 59 */       list.add(address);
/*  39:    */     }
/*  40: 61 */     return new AddressList(list, true);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Address buildAddress(ASTaddress node)
/*  44:    */   {
/*  45: 65 */     ChildNodeIterator it = new ChildNodeIterator(node);
/*  46: 66 */     Node n = it.next();
/*  47: 67 */     if ((n instanceof ASTaddr_spec)) {
/*  48: 68 */       return buildAddrSpec((ASTaddr_spec)n);
/*  49:    */     }
/*  50: 69 */     if ((n instanceof ASTangle_addr)) {
/*  51: 70 */       return buildAngleAddr((ASTangle_addr)n);
/*  52:    */     }
/*  53: 71 */     if ((n instanceof ASTphrase))
/*  54:    */     {
/*  55: 72 */       String name = buildString((ASTphrase)n, false);
/*  56: 73 */       Node n2 = it.next();
/*  57: 74 */       if ((n2 instanceof ASTgroup_body)) {
/*  58: 75 */         return new Group(name, buildGroupBody((ASTgroup_body)n2));
/*  59:    */       }
/*  60: 76 */       if ((n2 instanceof ASTangle_addr))
/*  61:    */       {
/*  62: 77 */         name = DecoderUtil.decodeEncodedWords(name);
/*  63: 78 */         return new Mailbox(name, buildAngleAddr((ASTangle_addr)n2));
/*  64:    */       }
/*  65: 80 */       throw new IllegalStateException();
/*  66:    */     }
/*  67: 83 */     throw new IllegalStateException();
/*  68:    */   }
/*  69:    */   
/*  70:    */   private MailboxList buildGroupBody(ASTgroup_body node)
/*  71:    */   {
/*  72: 88 */     List<Mailbox> results = new ArrayList();
/*  73: 89 */     ChildNodeIterator it = new ChildNodeIterator(node);
/*  74: 90 */     while (it.hasNext())
/*  75:    */     {
/*  76: 91 */       Node n = it.next();
/*  77: 92 */       if ((n instanceof ASTmailbox)) {
/*  78: 93 */         results.add(buildMailbox((ASTmailbox)n));
/*  79:    */       } else {
/*  80: 95 */         throw new IllegalStateException();
/*  81:    */       }
/*  82:    */     }
/*  83: 97 */     return new MailboxList(results, true);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Mailbox buildMailbox(ASTmailbox node)
/*  87:    */   {
/*  88:101 */     ChildNodeIterator it = new ChildNodeIterator(node);
/*  89:102 */     Node n = it.next();
/*  90:103 */     if ((n instanceof ASTaddr_spec)) {
/*  91:104 */       return buildAddrSpec((ASTaddr_spec)n);
/*  92:    */     }
/*  93:105 */     if ((n instanceof ASTangle_addr)) {
/*  94:106 */       return buildAngleAddr((ASTangle_addr)n);
/*  95:    */     }
/*  96:107 */     if ((n instanceof ASTname_addr)) {
/*  97:108 */       return buildNameAddr((ASTname_addr)n);
/*  98:    */     }
/*  99:110 */     throw new IllegalStateException();
/* 100:    */   }
/* 101:    */   
/* 102:    */   private Mailbox buildNameAddr(ASTname_addr node)
/* 103:    */   {
/* 104:115 */     ChildNodeIterator it = new ChildNodeIterator(node);
/* 105:116 */     Node n = it.next();
/* 106:    */     String name;
/* 107:118 */     if ((n instanceof ASTphrase)) {
/* 108:119 */       name = buildString((ASTphrase)n, false);
/* 109:    */     } else {
/* 110:121 */       throw new IllegalStateException();
/* 111:    */     }
/* 112:    */     String name;
/* 113:124 */     n = it.next();
/* 114:125 */     if ((n instanceof ASTangle_addr))
/* 115:    */     {
/* 116:126 */       name = DecoderUtil.decodeEncodedWords(name);
/* 117:127 */       return new Mailbox(name, buildAngleAddr((ASTangle_addr)n));
/* 118:    */     }
/* 119:129 */     throw new IllegalStateException();
/* 120:    */   }
/* 121:    */   
/* 122:    */   private Mailbox buildAngleAddr(ASTangle_addr node)
/* 123:    */   {
/* 124:134 */     ChildNodeIterator it = new ChildNodeIterator(node);
/* 125:135 */     DomainList route = null;
/* 126:136 */     Node n = it.next();
/* 127:137 */     if ((n instanceof ASTroute))
/* 128:    */     {
/* 129:138 */       route = buildRoute((ASTroute)n);
/* 130:139 */       n = it.next();
/* 131:    */     }
/* 132:140 */     else if (!(n instanceof ASTaddr_spec))
/* 133:    */     {
/* 134:144 */       throw new IllegalStateException();
/* 135:    */     }
/* 136:146 */     if ((n instanceof ASTaddr_spec)) {
/* 137:147 */       return buildAddrSpec(route, (ASTaddr_spec)n);
/* 138:    */     }
/* 139:149 */     throw new IllegalStateException();
/* 140:    */   }
/* 141:    */   
/* 142:    */   private DomainList buildRoute(ASTroute node)
/* 143:    */   {
/* 144:153 */     List<String> results = new ArrayList(node.jjtGetNumChildren());
/* 145:154 */     ChildNodeIterator it = new ChildNodeIterator(node);
/* 146:155 */     while (it.hasNext())
/* 147:    */     {
/* 148:156 */       Node n = it.next();
/* 149:157 */       if ((n instanceof ASTdomain)) {
/* 150:158 */         results.add(buildString((ASTdomain)n, true));
/* 151:    */       } else {
/* 152:160 */         throw new IllegalStateException();
/* 153:    */       }
/* 154:    */     }
/* 155:162 */     return new DomainList(results, true);
/* 156:    */   }
/* 157:    */   
/* 158:    */   private Mailbox buildAddrSpec(ASTaddr_spec node)
/* 159:    */   {
/* 160:166 */     return buildAddrSpec(null, node);
/* 161:    */   }
/* 162:    */   
/* 163:    */   private Mailbox buildAddrSpec(DomainList route, ASTaddr_spec node)
/* 164:    */   {
/* 165:170 */     ChildNodeIterator it = new ChildNodeIterator(node);
/* 166:171 */     String localPart = buildString((ASTlocal_part)it.next(), true);
/* 167:172 */     String domain = buildString((ASTdomain)it.next(), true);
/* 168:173 */     return new Mailbox(route, localPart, domain);
/* 169:    */   }
/* 170:    */   
/* 171:    */   private String buildString(SimpleNode node, boolean stripSpaces)
/* 172:    */   {
/* 173:177 */     Token head = node.firstToken;
/* 174:178 */     Token tail = node.lastToken;
/* 175:179 */     StringBuilder out = new StringBuilder();
/* 176:181 */     while (head != tail)
/* 177:    */     {
/* 178:182 */       out.append(head.image);
/* 179:183 */       head = head.next;
/* 180:184 */       if (!stripSpaces) {
/* 181:185 */         addSpecials(out, head.specialToken);
/* 182:    */       }
/* 183:    */     }
/* 184:187 */     out.append(tail.image);
/* 185:    */     
/* 186:189 */     return out.toString();
/* 187:    */   }
/* 188:    */   
/* 189:    */   private void addSpecials(StringBuilder out, Token specialToken)
/* 190:    */   {
/* 191:193 */     if (specialToken != null)
/* 192:    */     {
/* 193:194 */       addSpecials(out, specialToken.specialToken);
/* 194:195 */       out.append(specialToken.image);
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   private static class ChildNodeIterator
/* 199:    */     implements Iterator<Node>
/* 200:    */   {
/* 201:    */     private SimpleNode simpleNode;
/* 202:    */     private int index;
/* 203:    */     private int len;
/* 204:    */     
/* 205:    */     public ChildNodeIterator(SimpleNode simpleNode)
/* 206:    */     {
/* 207:206 */       this.simpleNode = simpleNode;
/* 208:207 */       this.len = simpleNode.jjtGetNumChildren();
/* 209:208 */       this.index = 0;
/* 210:    */     }
/* 211:    */     
/* 212:    */     public void remove()
/* 213:    */     {
/* 214:212 */       throw new UnsupportedOperationException();
/* 215:    */     }
/* 216:    */     
/* 217:    */     public boolean hasNext()
/* 218:    */     {
/* 219:216 */       return this.index < this.len;
/* 220:    */     }
/* 221:    */     
/* 222:    */     public Node next()
/* 223:    */     {
/* 224:220 */       return this.simpleNode.jjtGetChild(this.index++);
/* 225:    */     }
/* 226:    */   }
/* 227:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.Builder
 * JD-Core Version:    0.7.0.1
 */