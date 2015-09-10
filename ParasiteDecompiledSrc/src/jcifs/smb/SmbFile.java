/*    1:     */ package jcifs.smb;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.InputStream;
/*    5:     */ import java.io.OutputStream;
/*    6:     */ import java.net.InetAddress;
/*    7:     */ import java.net.MalformedURLException;
/*    8:     */ import java.net.URL;
/*    9:     */ import java.net.URLConnection;
/*   10:     */ import java.net.UnknownHostException;
/*   11:     */ import java.security.Principal;
/*   12:     */ import java.util.ArrayList;
/*   13:     */ import java.util.HashMap;
/*   14:     */ import java.util.Iterator;
/*   15:     */ import java.util.Map;
/*   16:     */ import java.util.Set;
/*   17:     */ import jcifs.Config;
/*   18:     */ import jcifs.UniAddress;
/*   19:     */ import jcifs.dcerpc.DcerpcHandle;
/*   20:     */ import jcifs.dcerpc.msrpc.MsrpcDfsRootEnum;
/*   21:     */ import jcifs.dcerpc.msrpc.MsrpcShareEnum;
/*   22:     */ import jcifs.dcerpc.msrpc.MsrpcShareGetInfo;
/*   23:     */ import jcifs.netbios.NbtAddress;
/*   24:     */ import jcifs.util.LogStream;
/*   25:     */ 
/*   26:     */ public class SmbFile
/*   27:     */   extends URLConnection
/*   28:     */   implements SmbConstants
/*   29:     */ {
/*   30: 356 */   static final int HASH_DOT = ".".hashCode();
/*   31: 357 */   static final int HASH_DOT_DOT = "..".hashCode();
/*   32: 359 */   static LogStream log = LogStream.getInstance();
/*   33:     */   
/*   34:     */   static
/*   35:     */   {
/*   36:     */     try
/*   37:     */     {
/*   38: 365 */       Class.forName("jcifs.Config");
/*   39:     */     }
/*   40:     */     catch (ClassNotFoundException cnfe)
/*   41:     */     {
/*   42: 367 */       cnfe.printStackTrace();
/*   43:     */     }
/*   44:     */   }
/*   45:     */   
/*   46: 369 */   static long attrExpirationPeriod = Config.getLong("jcifs.smb.client.attrExpirationPeriod", 5000L);
/*   47: 370 */   protected static Dfs dfs = new Dfs();
/*   48: 419 */   private int shareAccess = 7;
/*   49: 420 */   private SmbComBlankResponse blank_resp = null;
/*   50: 421 */   private DfsReferral dfsReferral = null;
/*   51: 426 */   SmbTree tree = null;
/*   52:     */   static final int O_RDONLY = 1;
/*   53:     */   static final int O_WRONLY = 2;
/*   54:     */   static final int O_RDWR = 3;
/*   55:     */   static final int O_APPEND = 4;
/*   56:     */   static final int O_CREAT = 16;
/*   57:     */   static final int O_EXCL = 32;
/*   58:     */   static final int O_TRUNC = 64;
/*   59:     */   public static final int FILE_NO_SHARE = 0;
/*   60:     */   public static final int FILE_SHARE_READ = 1;
/*   61:     */   public static final int FILE_SHARE_WRITE = 2;
/*   62:     */   public static final int FILE_SHARE_DELETE = 4;
/*   63:     */   public static final int ATTR_READONLY = 1;
/*   64:     */   public static final int ATTR_HIDDEN = 2;
/*   65:     */   public static final int ATTR_SYSTEM = 4;
/*   66:     */   public static final int ATTR_VOLUME = 8;
/*   67:     */   public static final int ATTR_DIRECTORY = 16;
/*   68:     */   public static final int ATTR_ARCHIVE = 32;
/*   69:     */   static final int ATTR_COMPRESSED = 2048;
/*   70:     */   static final int ATTR_NORMAL = 128;
/*   71:     */   static final int ATTR_TEMPORARY = 256;
/*   72:     */   static final int ATTR_GET_MASK = 32767;
/*   73:     */   static final int ATTR_SET_MASK = 12455;
/*   74:     */   static final int DEFAULT_ATTR_EXPIRATION_PERIOD = 5000;
/*   75:     */   public static final int TYPE_FILESYSTEM = 1;
/*   76:     */   public static final int TYPE_WORKGROUP = 2;
/*   77:     */   public static final int TYPE_SERVER = 4;
/*   78:     */   public static final int TYPE_SHARE = 8;
/*   79:     */   public static final int TYPE_NAMED_PIPE = 16;
/*   80:     */   public static final int TYPE_PRINTER = 32;
/*   81:     */   public static final int TYPE_COMM = 64;
/*   82:     */   private String canon;
/*   83:     */   private String share;
/*   84:     */   private long createTime;
/*   85:     */   private long lastModified;
/*   86:     */   private int attributes;
/*   87:     */   private long attrExpiration;
/*   88:     */   private long size;
/*   89:     */   private long sizeExpiration;
/*   90:     */   private boolean isExists;
/*   91:     */   NtlmPasswordAuthentication auth;
/*   92:     */   String unc;
/*   93:     */   int fid;
/*   94:     */   int type;
/*   95:     */   boolean opened;
/*   96:     */   int tree_num;
/*   97:     */   UniAddress[] addresses;
/*   98:     */   int addressIndex;
/*   99:     */   
/*  100:     */   public SmbFile(String url)
/*  101:     */     throws MalformedURLException
/*  102:     */   {
/*  103: 444 */     this(new URL(null, url, Handler.SMB_HANDLER));
/*  104:     */   }
/*  105:     */   
/*  106:     */   public SmbFile(SmbFile context, String name)
/*  107:     */     throws MalformedURLException, UnknownHostException
/*  108:     */   {
/*  109: 464 */     this(context.isWorkgroup0() ? new URL(null, "smb://" + name, Handler.SMB_HANDLER) : new URL(context.url, name, Handler.SMB_HANDLER), context.auth);
/*  110:     */   }
/*  111:     */   
/*  112:     */   public SmbFile(String context, String name)
/*  113:     */     throws MalformedURLException
/*  114:     */   {
/*  115: 483 */     this(new URL(new URL(null, context, Handler.SMB_HANDLER), name, Handler.SMB_HANDLER));
/*  116:     */   }
/*  117:     */   
/*  118:     */   public SmbFile(String url, NtlmPasswordAuthentication auth)
/*  119:     */     throws MalformedURLException
/*  120:     */   {
/*  121: 498 */     this(new URL(null, url, Handler.SMB_HANDLER), auth);
/*  122:     */   }
/*  123:     */   
/*  124:     */   public SmbFile(String url, NtlmPasswordAuthentication auth, int shareAccess)
/*  125:     */     throws MalformedURLException
/*  126:     */   {
/*  127: 516 */     this(new URL(null, url, Handler.SMB_HANDLER), auth);
/*  128: 517 */     if ((shareAccess & 0xFFFFFFF8) != 0) {
/*  129: 518 */       throw new RuntimeException("Illegal shareAccess parameter");
/*  130:     */     }
/*  131: 520 */     this.shareAccess = shareAccess;
/*  132:     */   }
/*  133:     */   
/*  134:     */   public SmbFile(String context, String name, NtlmPasswordAuthentication auth)
/*  135:     */     throws MalformedURLException
/*  136:     */   {
/*  137: 537 */     this(new URL(new URL(null, context, Handler.SMB_HANDLER), name, Handler.SMB_HANDLER), auth);
/*  138:     */   }
/*  139:     */   
/*  140:     */   public SmbFile(String context, String name, NtlmPasswordAuthentication auth, int shareAccess)
/*  141:     */     throws MalformedURLException
/*  142:     */   {
/*  143: 560 */     this(new URL(new URL(null, context, Handler.SMB_HANDLER), name, Handler.SMB_HANDLER), auth);
/*  144: 561 */     if ((shareAccess & 0xFFFFFFF8) != 0) {
/*  145: 562 */       throw new RuntimeException("Illegal shareAccess parameter");
/*  146:     */     }
/*  147: 564 */     this.shareAccess = shareAccess;
/*  148:     */   }
/*  149:     */   
/*  150:     */   public SmbFile(SmbFile context, String name, int shareAccess)
/*  151:     */     throws MalformedURLException, UnknownHostException
/*  152:     */   {
/*  153: 586 */     this(context.isWorkgroup0() ? new URL(null, "smb://" + name, Handler.SMB_HANDLER) : new URL(context.url, name, Handler.SMB_HANDLER), context.auth);
/*  154: 589 */     if ((shareAccess & 0xFFFFFFF8) != 0) {
/*  155: 590 */       throw new RuntimeException("Illegal shareAccess parameter");
/*  156:     */     }
/*  157: 592 */     this.shareAccess = shareAccess;
/*  158:     */   }
/*  159:     */   
/*  160:     */   public SmbFile(URL url)
/*  161:     */   {
/*  162: 601 */     this(url, new NtlmPasswordAuthentication(url.getUserInfo()));
/*  163:     */   }
/*  164:     */   
/*  165:     */   public SmbFile(URL url, NtlmPasswordAuthentication auth)
/*  166:     */   {
/*  167: 612 */     super(url);
/*  168: 613 */     this.auth = (auth == null ? new NtlmPasswordAuthentication(url.getUserInfo()) : auth);
/*  169:     */     
/*  170: 615 */     getUncPath0();
/*  171:     */   }
/*  172:     */   
/*  173:     */   SmbFile(SmbFile context, String name, int type, int attributes, long createTime, long lastModified, long size)
/*  174:     */     throws MalformedURLException, UnknownHostException
/*  175:     */   {
/*  176: 620 */     this(context.isWorkgroup0() ? new URL(null, "smb://" + name + "/", Handler.SMB_HANDLER) : new URL(context.url, name + ((attributes & 0x10) > 0 ? "/" : "")));
/*  177:     */     
/*  178:     */ 
/*  179:     */ 
/*  180:     */ 
/*  181: 625 */     this.auth = context.auth;
/*  182: 628 */     if (context.share != null)
/*  183:     */     {
/*  184: 629 */       this.tree = context.tree;
/*  185: 630 */       this.dfsReferral = context.dfsReferral;
/*  186:     */     }
/*  187: 632 */     int last = name.length() - 1;
/*  188: 633 */     if (name.charAt(last) == '/') {
/*  189: 634 */       name = name.substring(0, last);
/*  190:     */     }
/*  191: 636 */     if (context.share == null) {
/*  192: 637 */       this.unc = "\\";
/*  193: 638 */     } else if (context.unc.equals("\\")) {
/*  194: 639 */       this.unc = ('\\' + name);
/*  195:     */     } else {
/*  196: 641 */       this.unc = (context.unc + '\\' + name);
/*  197:     */     }
/*  198: 646 */     this.type = type;
/*  199: 647 */     this.attributes = attributes;
/*  200: 648 */     this.createTime = createTime;
/*  201: 649 */     this.lastModified = lastModified;
/*  202: 650 */     this.size = size;
/*  203: 651 */     this.isExists = true;
/*  204:     */     
/*  205: 653 */     this.attrExpiration = (this.sizeExpiration = System.currentTimeMillis() + attrExpirationPeriod);
/*  206:     */   }
/*  207:     */   
/*  208:     */   private SmbComBlankResponse blank_resp()
/*  209:     */   {
/*  210: 658 */     if (this.blank_resp == null) {
/*  211: 659 */       this.blank_resp = new SmbComBlankResponse();
/*  212:     */     }
/*  213: 661 */     return this.blank_resp;
/*  214:     */   }
/*  215:     */   
/*  216:     */   void resolveDfs(ServerMessageBlock request)
/*  217:     */     throws SmbException
/*  218:     */   {
/*  219: 664 */     connect0();
/*  220:     */     
/*  221: 666 */     DfsReferral dr = dfs.resolve(this.tree.session.transport.tconHostName, this.tree.share, this.unc, this.auth);
/*  222: 671 */     if (dr != null)
/*  223:     */     {
/*  224: 672 */       String service = null;
/*  225: 674 */       if (request != null) {
/*  226: 675 */         switch (request.command)
/*  227:     */         {
/*  228:     */         case 37: 
/*  229:     */         case 50: 
/*  230: 678 */           switch (((SmbComTransaction)request).subCommand & 0xFF)
/*  231:     */           {
/*  232:     */           case 16: 
/*  233:     */             break;
/*  234:     */           default: 
/*  235: 682 */             service = "A:";
/*  236:     */           }
/*  237: 684 */           break;
/*  238:     */         default: 
/*  239: 686 */           service = "A:";
/*  240:     */         }
/*  241:     */       }
/*  242: 690 */       DfsReferral start = dr;
/*  243: 691 */       SmbException se = null;
/*  244:     */       do
/*  245:     */       {
/*  246:     */         try
/*  247:     */         {
/*  248: 695 */           if (LogStream.level >= 2) {
/*  249: 696 */             log.println("DFS redirect: " + dr);
/*  250:     */           }
/*  251: 698 */           UniAddress addr = UniAddress.getByName(dr.server);
/*  252: 699 */           SmbTransport trans = SmbTransport.getSmbTransport(addr, this.url.getPort());
/*  253:     */           
/*  254:     */ 
/*  255:     */ 
/*  256:     */ 
/*  257:     */ 
/*  258: 705 */           trans.connect();
/*  259: 706 */           this.tree = trans.getSmbSession(this.auth).getSmbTree(dr.share, service);
/*  260: 708 */           if ((dr != start) && (dr.key != null)) {
/*  261: 709 */             dr.map.put(dr.key, dr);
/*  262:     */           }
/*  263: 712 */           se = null;
/*  264:     */         }
/*  265:     */         catch (IOException ioe)
/*  266:     */         {
/*  267: 716 */           if ((ioe instanceof SmbException)) {
/*  268: 717 */             se = (SmbException)ioe;
/*  269:     */           } else {
/*  270: 719 */             se = new SmbException(dr.server, ioe);
/*  271:     */           }
/*  272: 723 */           dr = dr.next;
/*  273:     */         }
/*  274: 724 */       } while (dr != start);
/*  275: 726 */       if (se != null) {
/*  276: 727 */         throw se;
/*  277:     */       }
/*  278: 729 */       if (LogStream.level >= 3) {
/*  279: 730 */         log.println(dr);
/*  280:     */       }
/*  281: 732 */       this.dfsReferral = dr;
/*  282: 733 */       if (dr.pathConsumed < 0) {
/*  283: 734 */         dr.pathConsumed = 0;
/*  284: 735 */       } else if (dr.pathConsumed > this.unc.length()) {
/*  285: 736 */         dr.pathConsumed = this.unc.length();
/*  286:     */       }
/*  287: 738 */       String dunc = this.unc.substring(dr.pathConsumed);
/*  288: 739 */       if (dunc.equals("")) {
/*  289: 740 */         dunc = "\\";
/*  290:     */       }
/*  291: 741 */       if (!dr.path.equals("")) {
/*  292: 742 */         dunc = "\\" + dr.path + dunc;
/*  293:     */       }
/*  294: 744 */       this.unc = dunc;
/*  295: 745 */       if ((request != null) && (request.path != null) && (request.path.endsWith("\\")) && (!dunc.endsWith("\\"))) {
/*  296: 749 */         dunc = dunc + "\\";
/*  297:     */       }
/*  298: 751 */       if (request != null)
/*  299:     */       {
/*  300: 752 */         request.path = dunc;
/*  301: 753 */         request.flags2 |= 0x1000;
/*  302:     */       }
/*  303:     */     }
/*  304:     */     else
/*  305:     */     {
/*  306: 755 */       if ((this.tree.inDomainDfs) && (!(request instanceof NtTransQuerySecurityDesc)) && (!(request instanceof SmbComClose)) && (!(request instanceof SmbComFindClose2))) {
/*  307: 759 */         throw new SmbException(-1073741275, false);
/*  308:     */       }
/*  309: 761 */       if (request != null) {
/*  310: 762 */         request.flags2 &= 0xFFFFEFFF;
/*  311:     */       }
/*  312:     */     }
/*  313:     */   }
/*  314:     */   
/*  315:     */   void send(ServerMessageBlock request, ServerMessageBlock response)
/*  316:     */     throws SmbException
/*  317:     */   {
/*  318:     */     for (;;)
/*  319:     */     {
/*  320: 768 */       resolveDfs(request);
/*  321:     */       try
/*  322:     */       {
/*  323: 770 */         this.tree.send(request, response);
/*  324:     */       }
/*  325:     */       catch (DfsReferral dre)
/*  326:     */       {
/*  327: 773 */         if (dre.resolveHashes) {
/*  328: 774 */           throw dre;
/*  329:     */         }
/*  330: 776 */         request.reset();
/*  331:     */       }
/*  332:     */     }
/*  333:     */   }
/*  334:     */   
/*  335:     */   static String queryLookup(String query, String param)
/*  336:     */   {
/*  337: 782 */     char[] in = query.toCharArray();
/*  338:     */     int eq;
/*  339: 785 */     int st = eq = 0;
/*  340: 786 */     for (int i = 0; i < in.length; i++)
/*  341:     */     {
/*  342: 787 */       int ch = in[i];
/*  343: 788 */       if (ch == 38)
/*  344:     */       {
/*  345: 789 */         if (eq > st)
/*  346:     */         {
/*  347: 790 */           String p = new String(in, st, eq - st);
/*  348: 791 */           if (p.equalsIgnoreCase(param))
/*  349:     */           {
/*  350: 792 */             eq++;
/*  351: 793 */             return new String(in, eq, i - eq);
/*  352:     */           }
/*  353:     */         }
/*  354: 796 */         st = i + 1;
/*  355:     */       }
/*  356: 797 */       else if (ch == 61)
/*  357:     */       {
/*  358: 798 */         eq = i;
/*  359:     */       }
/*  360:     */     }
/*  361: 801 */     if (eq > st)
/*  362:     */     {
/*  363: 802 */       String p = new String(in, st, eq - st);
/*  364: 803 */       if (p.equalsIgnoreCase(param))
/*  365:     */       {
/*  366: 804 */         eq++;
/*  367: 805 */         return new String(in, eq, in.length - eq);
/*  368:     */       }
/*  369:     */     }
/*  370: 809 */     return null;
/*  371:     */   }
/*  372:     */   
/*  373:     */   UniAddress getAddress()
/*  374:     */     throws UnknownHostException
/*  375:     */   {
/*  376: 816 */     if (this.addressIndex == 0) {
/*  377: 817 */       return getFirstAddress();
/*  378:     */     }
/*  379: 818 */     return this.addresses[(this.addressIndex - 1)];
/*  380:     */   }
/*  381:     */   
/*  382:     */   UniAddress getFirstAddress()
/*  383:     */     throws UnknownHostException
/*  384:     */   {
/*  385: 821 */     this.addressIndex = 0;
/*  386:     */     
/*  387: 823 */     String host = this.url.getHost();
/*  388: 824 */     String path = this.url.getPath();
/*  389: 825 */     String query = this.url.getQuery();
/*  390: 827 */     if (query != null)
/*  391:     */     {
/*  392: 828 */       String server = queryLookup(query, "server");
/*  393: 829 */       if ((server != null) && (server.length() > 0))
/*  394:     */       {
/*  395: 830 */         this.addresses = new UniAddress[1];
/*  396: 831 */         this.addresses[0] = UniAddress.getByName(server);
/*  397: 832 */         return getNextAddress();
/*  398:     */       }
/*  399: 834 */       String address = queryLookup(query, "address");
/*  400: 835 */       if ((address != null) && (address.length() > 0))
/*  401:     */       {
/*  402: 836 */         byte[] ip = InetAddress.getByName(address).getAddress();
/*  403: 837 */         this.addresses = new UniAddress[1];
/*  404: 838 */         this.addresses[0] = new UniAddress(InetAddress.getByAddress(host, ip));
/*  405: 839 */         return getNextAddress();
/*  406:     */       }
/*  407:     */     }
/*  408: 843 */     if (host.length() == 0) {
/*  409:     */       try
/*  410:     */       {
/*  411: 845 */         NbtAddress addr = NbtAddress.getByName("\001\002__MSBROWSE__\002", 1, null);
/*  412:     */         
/*  413: 847 */         this.addresses = new UniAddress[1];
/*  414: 848 */         this.addresses[0] = UniAddress.getByName(addr.getHostAddress());
/*  415:     */       }
/*  416:     */       catch (UnknownHostException uhe)
/*  417:     */       {
/*  418: 850 */         NtlmPasswordAuthentication.initDefaults();
/*  419: 851 */         if (NtlmPasswordAuthentication.DEFAULT_DOMAIN.equals("?")) {
/*  420: 852 */           throw uhe;
/*  421:     */         }
/*  422: 854 */         this.addresses = UniAddress.getAllByName(NtlmPasswordAuthentication.DEFAULT_DOMAIN, true);
/*  423:     */       }
/*  424: 856 */     } else if ((path.length() == 0) || (path.equals("/"))) {
/*  425: 857 */       this.addresses = UniAddress.getAllByName(host, true);
/*  426:     */     } else {
/*  427: 859 */       this.addresses = UniAddress.getAllByName(host, false);
/*  428:     */     }
/*  429: 862 */     return getNextAddress();
/*  430:     */   }
/*  431:     */   
/*  432:     */   UniAddress getNextAddress()
/*  433:     */   {
/*  434: 865 */     UniAddress addr = null;
/*  435: 866 */     if (this.addressIndex < this.addresses.length) {
/*  436: 867 */       addr = this.addresses[(this.addressIndex++)];
/*  437:     */     }
/*  438: 868 */     return addr;
/*  439:     */   }
/*  440:     */   
/*  441:     */   boolean hasNextAddress()
/*  442:     */   {
/*  443: 871 */     return this.addressIndex < this.addresses.length;
/*  444:     */   }
/*  445:     */   
/*  446:     */   void connect0()
/*  447:     */     throws SmbException
/*  448:     */   {
/*  449:     */     try
/*  450:     */     {
/*  451: 875 */       connect();
/*  452:     */     }
/*  453:     */     catch (UnknownHostException uhe)
/*  454:     */     {
/*  455: 877 */       throw new SmbException("Failed to connect to server", uhe);
/*  456:     */     }
/*  457:     */     catch (SmbException se)
/*  458:     */     {
/*  459: 879 */       throw se;
/*  460:     */     }
/*  461:     */     catch (IOException ioe)
/*  462:     */     {
/*  463: 881 */       throw new SmbException("Failed to connect to server", ioe);
/*  464:     */     }
/*  465:     */   }
/*  466:     */   
/*  467:     */   void doConnect()
/*  468:     */     throws IOException
/*  469:     */   {
/*  470: 888 */     UniAddress addr = getAddress();
/*  471:     */     SmbTransport trans;
/*  472:     */     SmbTransport trans;
/*  473: 889 */     if (this.tree != null)
/*  474:     */     {
/*  475: 890 */       trans = this.tree.session.transport;
/*  476:     */     }
/*  477:     */     else
/*  478:     */     {
/*  479: 892 */       trans = SmbTransport.getSmbTransport(addr, this.url.getPort());
/*  480: 893 */       this.tree = trans.getSmbSession(this.auth).getSmbTree(this.share, null);
/*  481:     */     }
/*  482: 896 */     String hostName = getServerWithDfs();
/*  483: 897 */     this.tree.inDomainDfs = (dfs.resolve(hostName, this.tree.share, null, this.auth) != null);
/*  484: 898 */     if (this.tree.inDomainDfs) {
/*  485: 899 */       this.tree.connectionState = 2;
/*  486:     */     }
/*  487:     */     try
/*  488:     */     {
/*  489: 903 */       if (LogStream.level >= 3) {
/*  490: 904 */         log.println("doConnect: " + addr);
/*  491:     */       }
/*  492: 906 */       this.tree.treeConnect(null, null);
/*  493:     */     }
/*  494:     */     catch (SmbAuthException sae)
/*  495:     */     {
/*  496: 911 */       if (this.share == null)
/*  497:     */       {
/*  498: 912 */         SmbSession ssn = trans.getSmbSession(NtlmPasswordAuthentication.NULL);
/*  499: 913 */         this.tree = ssn.getSmbTree(null, null);
/*  500: 914 */         this.tree.treeConnect(null, null);
/*  501:     */       }
/*  502:     */       else
/*  503:     */       {
/*  504:     */         NtlmPasswordAuthentication a;
/*  505: 915 */         if ((a = NtlmAuthenticator.requestNtlmPasswordAuthentication(this.url.toString(), sae)) != null)
/*  506:     */         {
/*  507: 917 */           this.auth = a;
/*  508: 918 */           SmbSession ssn = trans.getSmbSession(this.auth);
/*  509: 919 */           this.tree = ssn.getSmbTree(this.share, null);
/*  510: 920 */           this.tree.inDomainDfs = (dfs.resolve(hostName, this.tree.share, null, this.auth) != null);
/*  511: 921 */           if (this.tree.inDomainDfs) {
/*  512: 922 */             this.tree.connectionState = 2;
/*  513:     */           }
/*  514: 924 */           this.tree.treeConnect(null, null);
/*  515:     */         }
/*  516:     */         else
/*  517:     */         {
/*  518: 926 */           if ((LogStream.level >= 1) && (hasNextAddress())) {
/*  519: 927 */             sae.printStackTrace(log);
/*  520:     */           }
/*  521: 928 */           throw sae;
/*  522:     */         }
/*  523:     */       }
/*  524:     */     }
/*  525:     */   }
/*  526:     */   
/*  527:     */   public void connect()
/*  528:     */     throws IOException
/*  529:     */   {
/*  530: 941 */     if (isConnected()) {
/*  531: 942 */       return;
/*  532:     */     }
/*  533: 945 */     getUncPath0();
/*  534: 946 */     getFirstAddress();
/*  535:     */     for (;;)
/*  536:     */     {
/*  537:     */       try
/*  538:     */       {
/*  539: 949 */         doConnect();
/*  540: 950 */         return;
/*  541:     */       }
/*  542:     */       catch (SmbException se)
/*  543:     */       {
/*  544: 952 */         if (getNextAddress() == null) {
/*  545: 953 */           throw se;
/*  546:     */         }
/*  547: 954 */         if (LogStream.level >= 3) {
/*  548: 955 */           se.printStackTrace(log);
/*  549:     */         }
/*  550:     */       }
/*  551:     */     }
/*  552:     */   }
/*  553:     */   
/*  554:     */   boolean isConnected()
/*  555:     */   {
/*  556: 960 */     return (this.tree != null) && (this.tree.connectionState == 2);
/*  557:     */   }
/*  558:     */   
/*  559:     */   int open0(int flags, int access, int attrs, int options)
/*  560:     */     throws SmbException
/*  561:     */   {
/*  562: 965 */     connect0();
/*  563: 967 */     if (LogStream.level >= 3) {
/*  564: 968 */       log.println("open0: " + this.unc);
/*  565:     */     }
/*  566:     */     int f;
/*  567: 974 */     if (this.tree.session.transport.hasCapability(16))
/*  568:     */     {
/*  569: 975 */       SmbComNTCreateAndXResponse response = new SmbComNTCreateAndXResponse();
/*  570: 976 */       SmbComNTCreateAndX request = new SmbComNTCreateAndX(this.unc, flags, access, this.shareAccess, attrs, options, null);
/*  571: 977 */       if ((this instanceof SmbNamedPipe))
/*  572:     */       {
/*  573: 978 */         request.flags0 |= 0x16;
/*  574: 979 */         request.desiredAccess |= 0x20000;
/*  575: 980 */         response.isExtended = true;
/*  576:     */       }
/*  577: 982 */       send(request, response);
/*  578: 983 */       int f = response.fid;
/*  579: 984 */       this.attributes = (response.extFileAttributes & 0x7FFF);
/*  580: 985 */       this.attrExpiration = (System.currentTimeMillis() + attrExpirationPeriod);
/*  581: 986 */       this.isExists = true;
/*  582:     */     }
/*  583:     */     else
/*  584:     */     {
/*  585: 988 */       SmbComOpenAndXResponse response = new SmbComOpenAndXResponse();
/*  586: 989 */       send(new SmbComOpenAndX(this.unc, access, flags, null), response);
/*  587: 990 */       f = response.fid;
/*  588:     */     }
/*  589: 993 */     return f;
/*  590:     */   }
/*  591:     */   
/*  592:     */   void open(int flags, int access, int attrs, int options)
/*  593:     */     throws SmbException
/*  594:     */   {
/*  595: 996 */     if (isOpen()) {
/*  596: 997 */       return;
/*  597:     */     }
/*  598: 999 */     this.fid = open0(flags, access, attrs, options);
/*  599:1000 */     this.opened = true;
/*  600:1001 */     this.tree_num = this.tree.tree_num;
/*  601:     */   }
/*  602:     */   
/*  603:     */   boolean isOpen()
/*  604:     */   {
/*  605:1004 */     boolean ans = (this.opened) && (isConnected()) && (this.tree_num == this.tree.tree_num);
/*  606:1005 */     return ans;
/*  607:     */   }
/*  608:     */   
/*  609:     */   void close(int f, long lastWriteTime)
/*  610:     */     throws SmbException
/*  611:     */   {
/*  612:1009 */     if (LogStream.level >= 3) {
/*  613:1010 */       log.println("close: " + f);
/*  614:     */     }
/*  615:1016 */     send(new SmbComClose(f, lastWriteTime), blank_resp());
/*  616:     */   }
/*  617:     */   
/*  618:     */   void close(long lastWriteTime)
/*  619:     */     throws SmbException
/*  620:     */   {
/*  621:1019 */     if (!isOpen()) {
/*  622:1020 */       return;
/*  623:     */     }
/*  624:1022 */     close(this.fid, lastWriteTime);
/*  625:1023 */     this.opened = false;
/*  626:     */   }
/*  627:     */   
/*  628:     */   void close()
/*  629:     */     throws SmbException
/*  630:     */   {
/*  631:1026 */     close(0L);
/*  632:     */   }
/*  633:     */   
/*  634:     */   public Principal getPrincipal()
/*  635:     */   {
/*  636:1042 */     return this.auth;
/*  637:     */   }
/*  638:     */   
/*  639:     */   public String getName()
/*  640:     */   {
/*  641:1061 */     getUncPath0();
/*  642:1062 */     if (this.canon.length() > 1)
/*  643:     */     {
/*  644:1063 */       int i = this.canon.length() - 2;
/*  645:1064 */       while (this.canon.charAt(i) != '/') {
/*  646:1065 */         i--;
/*  647:     */       }
/*  648:1067 */       return this.canon.substring(i + 1);
/*  649:     */     }
/*  650:1068 */     if (this.share != null) {
/*  651:1069 */       return this.share + '/';
/*  652:     */     }
/*  653:1070 */     if (this.url.getHost().length() > 0) {
/*  654:1071 */       return this.url.getHost() + '/';
/*  655:     */     }
/*  656:1073 */     return "smb://";
/*  657:     */   }
/*  658:     */   
/*  659:     */   public String getParent()
/*  660:     */   {
/*  661:1088 */     String str = this.url.getAuthority();
/*  662:1090 */     if (str.length() > 0)
/*  663:     */     {
/*  664:1091 */       StringBuffer sb = new StringBuffer("smb://");
/*  665:     */       
/*  666:1093 */       sb.append(str);
/*  667:     */       
/*  668:1095 */       getUncPath0();
/*  669:1096 */       if (this.canon.length() > 1) {
/*  670:1097 */         sb.append(this.canon);
/*  671:     */       } else {
/*  672:1099 */         sb.append('/');
/*  673:     */       }
/*  674:1102 */       str = sb.toString();
/*  675:     */       
/*  676:1104 */       int i = str.length() - 2;
/*  677:1105 */       while (str.charAt(i) != '/') {
/*  678:1106 */         i--;
/*  679:     */       }
/*  680:1109 */       return str.substring(0, i + 1);
/*  681:     */     }
/*  682:1112 */     return "smb://";
/*  683:     */   }
/*  684:     */   
/*  685:     */   public String getPath()
/*  686:     */   {
/*  687:1124 */     return this.url.toString();
/*  688:     */   }
/*  689:     */   
/*  690:     */   String getUncPath0()
/*  691:     */   {
/*  692:1128 */     if (this.unc == null)
/*  693:     */     {
/*  694:1129 */       char[] in = this.url.getPath().toCharArray();
/*  695:1130 */       char[] out = new char[in.length];
/*  696:1131 */       int length = in.length;
/*  697:     */       
/*  698:     */ 
/*  699:     */ 
/*  700:1135 */       int state = 0;
/*  701:1136 */       int o = 0;
/*  702:1137 */       for (int i = 0; i < length; i++) {
/*  703:1138 */         switch (state)
/*  704:     */         {
/*  705:     */         case 0: 
/*  706:1140 */           if (in[i] != '/') {
/*  707:1141 */             return null;
/*  708:     */           }
/*  709:1143 */           out[(o++)] = in[i];
/*  710:1144 */           state = 1;
/*  711:1145 */           break;
/*  712:     */         case 1: 
/*  713:1147 */           if (in[i] != '/') {
/*  714:1149 */             if ((in[i] == '.') && ((i + 1 >= length) || (in[(i + 1)] == '/')))
/*  715:     */             {
/*  716:1151 */               i++;
/*  717:     */             }
/*  718:1153 */             else if ((i + 1 < length) && (in[i] == '.') && (in[(i + 1)] == '.') && ((i + 2 >= length) || (in[(i + 2)] == '/')))
/*  719:     */             {
/*  720:1157 */               i += 2;
/*  721:1158 */               if (o != 1) {
/*  722:     */                 do
/*  723:     */                 {
/*  724:1160 */                   o--;
/*  725:1161 */                   if (o <= 1) {
/*  726:     */                     break;
/*  727:     */                   }
/*  728:1161 */                 } while (out[(o - 1)] != '/');
/*  729:     */               }
/*  730:     */             }
/*  731:     */             else
/*  732:     */             {
/*  733:1164 */               state = 2;
/*  734:     */             }
/*  735:     */           }
/*  736:     */           break;
/*  737:     */         case 2: 
/*  738:1166 */           if (in[i] == '/') {
/*  739:1167 */             state = 1;
/*  740:     */           }
/*  741:1169 */           out[(o++)] = in[i];
/*  742:     */         }
/*  743:     */       }
/*  744:1174 */       this.canon = new String(out, 0, o);
/*  745:1176 */       if (o > 1)
/*  746:     */       {
/*  747:1177 */         o--;
/*  748:1178 */         i = this.canon.indexOf('/', 1);
/*  749:1179 */         if (i < 0)
/*  750:     */         {
/*  751:1180 */           this.share = this.canon.substring(1);
/*  752:1181 */           this.unc = "\\";
/*  753:     */         }
/*  754:1182 */         else if (i == o)
/*  755:     */         {
/*  756:1183 */           this.share = this.canon.substring(1, i);
/*  757:1184 */           this.unc = "\\";
/*  758:     */         }
/*  759:     */         else
/*  760:     */         {
/*  761:1186 */           this.share = this.canon.substring(1, i);
/*  762:1187 */           this.unc = this.canon.substring(i, out[o] == '/' ? o : o + 1);
/*  763:1188 */           this.unc = this.unc.replace('/', '\\');
/*  764:     */         }
/*  765:     */       }
/*  766:     */       else
/*  767:     */       {
/*  768:1191 */         this.share = null;
/*  769:1192 */         this.unc = "\\";
/*  770:     */       }
/*  771:     */     }
/*  772:1195 */     return this.unc;
/*  773:     */   }
/*  774:     */   
/*  775:     */   public String getUncPath()
/*  776:     */   {
/*  777:1203 */     getUncPath0();
/*  778:1204 */     if (this.share == null) {
/*  779:1205 */       return "\\\\" + this.url.getHost();
/*  780:     */     }
/*  781:1207 */     return "\\\\" + this.url.getHost() + this.canon.replace('/', '\\');
/*  782:     */   }
/*  783:     */   
/*  784:     */   public String getCanonicalPath()
/*  785:     */   {
/*  786:1219 */     String str = this.url.getAuthority();
/*  787:1220 */     getUncPath0();
/*  788:1221 */     if (str.length() > 0) {
/*  789:1222 */       return "smb://" + this.url.getAuthority() + this.canon;
/*  790:     */     }
/*  791:1224 */     return "smb://";
/*  792:     */   }
/*  793:     */   
/*  794:     */   public String getShare()
/*  795:     */   {
/*  796:1237 */     return this.share;
/*  797:     */   }
/*  798:     */   
/*  799:     */   String getServerWithDfs()
/*  800:     */   {
/*  801:1241 */     if (this.dfsReferral != null) {
/*  802:1242 */       return this.dfsReferral.server;
/*  803:     */     }
/*  804:1244 */     return getServer();
/*  805:     */   }
/*  806:     */   
/*  807:     */   public String getServer()
/*  808:     */   {
/*  809:1257 */     String str = this.url.getHost();
/*  810:1258 */     if (str.length() == 0) {
/*  811:1259 */       return null;
/*  812:     */     }
/*  813:1261 */     return str;
/*  814:     */   }
/*  815:     */   
/*  816:     */   public int getType()
/*  817:     */     throws SmbException
/*  818:     */   {
/*  819:1270 */     if (this.type == 0) {
/*  820:1271 */       if (getUncPath0().length() > 1)
/*  821:     */       {
/*  822:1272 */         this.type = 1;
/*  823:     */       }
/*  824:1273 */       else if (this.share != null)
/*  825:     */       {
/*  826:1275 */         connect0();
/*  827:1276 */         if (this.share.equals("IPC$")) {
/*  828:1277 */           this.type = 16;
/*  829:1278 */         } else if (this.tree.service.equals("LPT1:")) {
/*  830:1279 */           this.type = 32;
/*  831:1280 */         } else if (this.tree.service.equals("COMM")) {
/*  832:1281 */           this.type = 64;
/*  833:     */         } else {
/*  834:1283 */           this.type = 8;
/*  835:     */         }
/*  836:     */       }
/*  837:1285 */       else if ((this.url.getAuthority() == null) || (this.url.getAuthority().length() == 0))
/*  838:     */       {
/*  839:1286 */         this.type = 2;
/*  840:     */       }
/*  841:     */       else
/*  842:     */       {
/*  843:     */         try
/*  844:     */         {
/*  845:1290 */           addr = getAddress();
/*  846:     */         }
/*  847:     */         catch (UnknownHostException uhe)
/*  848:     */         {
/*  849:     */           UniAddress addr;
/*  850:1292 */           throw new SmbException(this.url.toString(), uhe);
/*  851:     */         }
/*  852:     */         UniAddress addr;
/*  853:1294 */         if ((addr.getAddress() instanceof NbtAddress))
/*  854:     */         {
/*  855:1295 */           int code = ((NbtAddress)addr.getAddress()).getNameType();
/*  856:1296 */           if ((code == 29) || (code == 27))
/*  857:     */           {
/*  858:1297 */             this.type = 2;
/*  859:1298 */             return this.type;
/*  860:     */           }
/*  861:     */         }
/*  862:1301 */         this.type = 4;
/*  863:     */       }
/*  864:     */     }
/*  865:1304 */     return this.type;
/*  866:     */   }
/*  867:     */   
/*  868:     */   boolean isWorkgroup0()
/*  869:     */     throws UnknownHostException
/*  870:     */   {
/*  871:1307 */     if ((this.type == 2) || (this.url.getHost().length() == 0))
/*  872:     */     {
/*  873:1308 */       this.type = 2;
/*  874:1309 */       return true;
/*  875:     */     }
/*  876:1311 */     getUncPath0();
/*  877:1312 */     if (this.share == null)
/*  878:     */     {
/*  879:1313 */       UniAddress addr = getAddress();
/*  880:1314 */       if ((addr.getAddress() instanceof NbtAddress))
/*  881:     */       {
/*  882:1315 */         int code = ((NbtAddress)addr.getAddress()).getNameType();
/*  883:1316 */         if ((code == 29) || (code == 27))
/*  884:     */         {
/*  885:1317 */           this.type = 2;
/*  886:1318 */           return true;
/*  887:     */         }
/*  888:     */       }
/*  889:1321 */       this.type = 4;
/*  890:     */     }
/*  891:1324 */     return false;
/*  892:     */   }
/*  893:     */   
/*  894:     */   Info queryPath(String path, int infoLevel)
/*  895:     */     throws SmbException
/*  896:     */   {
/*  897:1328 */     connect0();
/*  898:1330 */     if (LogStream.level >= 3) {
/*  899:1331 */       log.println("queryPath: " + path);
/*  900:     */     }
/*  901:1348 */     if (this.tree.session.transport.hasCapability(16))
/*  902:     */     {
/*  903:1354 */       Trans2QueryPathInformationResponse response = new Trans2QueryPathInformationResponse(infoLevel);
/*  904:     */       
/*  905:1356 */       send(new Trans2QueryPathInformation(path, infoLevel), response);
/*  906:     */       
/*  907:1358 */       return response.info;
/*  908:     */     }
/*  909:1365 */     SmbComQueryInformationResponse response = new SmbComQueryInformationResponse(this.tree.session.transport.server.serverTimeZone * 1000 * 60L);
/*  910:     */     
/*  911:     */ 
/*  912:1368 */     send(new SmbComQueryInformation(path), response);
/*  913:1369 */     return response;
/*  914:     */   }
/*  915:     */   
/*  916:     */   public boolean exists()
/*  917:     */     throws SmbException
/*  918:     */   {
/*  919:1389 */     if (this.attrExpiration > System.currentTimeMillis()) {
/*  920:1390 */       return this.isExists;
/*  921:     */     }
/*  922:1393 */     this.attributes = 17;
/*  923:1394 */     this.createTime = 0L;
/*  924:1395 */     this.lastModified = 0L;
/*  925:1396 */     this.isExists = false;
/*  926:     */     try
/*  927:     */     {
/*  928:1399 */       if (this.url.getHost().length() != 0) {
/*  929:1400 */         if (this.share == null)
/*  930:     */         {
/*  931:1401 */           if (getType() == 2) {
/*  932:1402 */             UniAddress.getByName(this.url.getHost(), true);
/*  933:     */           } else {
/*  934:1404 */             UniAddress.getByName(this.url.getHost()).getHostName();
/*  935:     */           }
/*  936:     */         }
/*  937:1406 */         else if ((getUncPath0().length() == 1) || (this.share.equalsIgnoreCase("IPC$")))
/*  938:     */         {
/*  939:1408 */           connect0();
/*  940:     */         }
/*  941:     */         else
/*  942:     */         {
/*  943:1410 */           Info info = queryPath(getUncPath0(), 257);
/*  944:     */           
/*  945:1412 */           this.attributes = info.getAttributes();
/*  946:1413 */           this.createTime = info.getCreateTime();
/*  947:1414 */           this.lastModified = info.getLastWriteTime();
/*  948:     */         }
/*  949:     */       }
/*  950:1420 */       this.isExists = true;
/*  951:     */     }
/*  952:     */     catch (UnknownHostException uhe) {}catch (SmbException se)
/*  953:     */     {
/*  954:1424 */       switch (se.getNtStatus())
/*  955:     */       {
/*  956:     */       }
/*  957:     */     }
/*  958:1431 */     throw se;
/*  959:     */     
/*  960:     */ 
/*  961:     */ 
/*  962:1435 */     this.attrExpiration = (System.currentTimeMillis() + attrExpirationPeriod);
/*  963:     */     
/*  964:1437 */     return this.isExists;
/*  965:     */   }
/*  966:     */   
/*  967:     */   public boolean canRead()
/*  968:     */     throws SmbException
/*  969:     */   {
/*  970:1449 */     if (getType() == 16) {
/*  971:1450 */       return true;
/*  972:     */     }
/*  973:1452 */     return exists();
/*  974:     */   }
/*  975:     */   
/*  976:     */   public boolean canWrite()
/*  977:     */     throws SmbException
/*  978:     */   {
/*  979:1467 */     if (getType() == 16) {
/*  980:1468 */       return true;
/*  981:     */     }
/*  982:1470 */     return (exists()) && ((this.attributes & 0x1) == 0);
/*  983:     */   }
/*  984:     */   
/*  985:     */   public boolean isDirectory()
/*  986:     */     throws SmbException
/*  987:     */   {
/*  988:1480 */     if (getUncPath0().length() == 1) {
/*  989:1481 */       return true;
/*  990:     */     }
/*  991:1483 */     if (!exists()) {
/*  992:1483 */       return false;
/*  993:     */     }
/*  994:1484 */     return (this.attributes & 0x10) == 16;
/*  995:     */   }
/*  996:     */   
/*  997:     */   public boolean isFile()
/*  998:     */     throws SmbException
/*  999:     */   {
/* 1000:1494 */     if (getUncPath0().length() == 1) {
/* 1001:1495 */       return false;
/* 1002:     */     }
/* 1003:1497 */     exists();
/* 1004:1498 */     return (this.attributes & 0x10) == 0;
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   public boolean isHidden()
/* 1008:     */     throws SmbException
/* 1009:     */   {
/* 1010:1510 */     if (this.share == null) {
/* 1011:1511 */       return false;
/* 1012:     */     }
/* 1013:1512 */     if (getUncPath0().length() == 1)
/* 1014:     */     {
/* 1015:1513 */       if (this.share.endsWith("$")) {
/* 1016:1514 */         return true;
/* 1017:     */       }
/* 1018:1516 */       return false;
/* 1019:     */     }
/* 1020:1518 */     exists();
/* 1021:1519 */     return (this.attributes & 0x2) == 2;
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public String getDfsPath()
/* 1025:     */     throws SmbException
/* 1026:     */   {
/* 1027:1529 */     resolveDfs(null);
/* 1028:1530 */     if (this.dfsReferral == null) {
/* 1029:1531 */       return null;
/* 1030:     */     }
/* 1031:1533 */     String path = "smb:/" + this.dfsReferral.server + "/" + this.dfsReferral.share + this.unc;
/* 1032:1534 */     path = path.replace('\\', '/');
/* 1033:1535 */     if (isDirectory()) {
/* 1034:1536 */       path = path + '/';
/* 1035:     */     }
/* 1036:1538 */     return path;
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   public long createTime()
/* 1040:     */     throws SmbException
/* 1041:     */   {
/* 1042:1554 */     if (getUncPath0().length() > 1)
/* 1043:     */     {
/* 1044:1555 */       exists();
/* 1045:1556 */       return this.createTime;
/* 1046:     */     }
/* 1047:1558 */     return 0L;
/* 1048:     */   }
/* 1049:     */   
/* 1050:     */   public long lastModified()
/* 1051:     */     throws SmbException
/* 1052:     */   {
/* 1053:1571 */     if (getUncPath0().length() > 1)
/* 1054:     */     {
/* 1055:1572 */       exists();
/* 1056:1573 */       return this.lastModified;
/* 1057:     */     }
/* 1058:1575 */     return 0L;
/* 1059:     */   }
/* 1060:     */   
/* 1061:     */   public String[] list()
/* 1062:     */     throws SmbException
/* 1063:     */   {
/* 1064:1599 */     return list("*", 22, null, null);
/* 1065:     */   }
/* 1066:     */   
/* 1067:     */   public String[] list(SmbFilenameFilter filter)
/* 1068:     */     throws SmbException
/* 1069:     */   {
/* 1070:1612 */     return list("*", 22, filter, null);
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   public SmbFile[] listFiles()
/* 1074:     */     throws SmbException
/* 1075:     */   {
/* 1076:1641 */     return listFiles("*", 22, null, null);
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   public SmbFile[] listFiles(String wildcard)
/* 1080:     */     throws SmbException
/* 1081:     */   {
/* 1082:1674 */     return listFiles(wildcard, 22, null, null);
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public SmbFile[] listFiles(SmbFilenameFilter filter)
/* 1086:     */     throws SmbException
/* 1087:     */   {
/* 1088:1686 */     return listFiles("*", 22, filter, null);
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   public SmbFile[] listFiles(SmbFileFilter filter)
/* 1092:     */     throws SmbException
/* 1093:     */   {
/* 1094:1697 */     return listFiles("*", 22, null, filter);
/* 1095:     */   }
/* 1096:     */   
/* 1097:     */   String[] list(String wildcard, int searchAttributes, SmbFilenameFilter fnf, SmbFileFilter ff)
/* 1098:     */     throws SmbException
/* 1099:     */   {
/* 1100:1701 */     ArrayList list = new ArrayList();
/* 1101:1702 */     doEnum(list, false, wildcard, searchAttributes, fnf, ff);
/* 1102:1703 */     return (String[])list.toArray(new String[list.size()]);
/* 1103:     */   }
/* 1104:     */   
/* 1105:     */   SmbFile[] listFiles(String wildcard, int searchAttributes, SmbFilenameFilter fnf, SmbFileFilter ff)
/* 1106:     */     throws SmbException
/* 1107:     */   {
/* 1108:1707 */     ArrayList list = new ArrayList();
/* 1109:1708 */     doEnum(list, true, wildcard, searchAttributes, fnf, ff);
/* 1110:1709 */     return (SmbFile[])list.toArray(new SmbFile[list.size()]);
/* 1111:     */   }
/* 1112:     */   
/* 1113:     */   void doEnum(ArrayList list, boolean files, String wildcard, int searchAttributes, SmbFilenameFilter fnf, SmbFileFilter ff)
/* 1114:     */     throws SmbException
/* 1115:     */   {
/* 1116:1717 */     if ((ff != null) && ((ff instanceof DosFileFilter)))
/* 1117:     */     {
/* 1118:1718 */       DosFileFilter dff = (DosFileFilter)ff;
/* 1119:1719 */       if (dff.wildcard != null) {
/* 1120:1720 */         wildcard = dff.wildcard;
/* 1121:     */       }
/* 1122:1721 */       searchAttributes = dff.attributes;
/* 1123:     */     }
/* 1124:     */     try
/* 1125:     */     {
/* 1126:1725 */       int hostlen = this.url.getHost().length();
/* 1127:1726 */       if ((hostlen == 0) || (getType() == 2)) {
/* 1128:1727 */         doNetServerEnum(list, files, wildcard, searchAttributes, fnf, ff);
/* 1129:1728 */       } else if (this.share == null) {
/* 1130:1729 */         doShareEnum(list, files, wildcard, searchAttributes, fnf, ff);
/* 1131:     */       } else {
/* 1132:1731 */         doFindFirstNext(list, files, wildcard, searchAttributes, fnf, ff);
/* 1133:     */       }
/* 1134:     */     }
/* 1135:     */     catch (UnknownHostException uhe)
/* 1136:     */     {
/* 1137:1734 */       throw new SmbException(this.url.toString(), uhe);
/* 1138:     */     }
/* 1139:     */     catch (MalformedURLException mue)
/* 1140:     */     {
/* 1141:1736 */       throw new SmbException(this.url.toString(), mue);
/* 1142:     */     }
/* 1143:     */   }
/* 1144:     */   
/* 1145:     */   void doShareEnum(ArrayList list, boolean files, String wildcard, int searchAttributes, SmbFilenameFilter fnf, SmbFileFilter ff)
/* 1146:     */     throws SmbException, UnknownHostException, MalformedURLException
/* 1147:     */   {
/* 1148:1747 */     String p = this.url.getPath();
/* 1149:1748 */     IOException last = null;
/* 1150:1754 */     if (p.lastIndexOf('/') != p.length() - 1) {
/* 1151:1755 */       throw new SmbException(this.url.toString() + " directory must end with '/'");
/* 1152:     */     }
/* 1153:1756 */     if (getType() != 4) {
/* 1154:1757 */       throw new SmbException("The requested list operations is invalid: " + this.url.toString());
/* 1155:     */     }
/* 1156:1759 */     HashMap map = new HashMap();
/* 1157:1761 */     if (dfs.isTrustedDomain(getServer(), this.auth)) {
/* 1158:     */       try
/* 1159:     */       {
/* 1160:1766 */         FileEntry[] entries = doDfsRootEnum();
/* 1161:1767 */         for (int ei = 0; ei < entries.length; ei++)
/* 1162:     */         {
/* 1163:1768 */           FileEntry e = entries[ei];
/* 1164:1769 */           if (!map.containsKey(e)) {
/* 1165:1770 */             map.put(e, e);
/* 1166:     */           }
/* 1167:     */         }
/* 1168:     */       }
/* 1169:     */       catch (IOException ioe)
/* 1170:     */       {
/* 1171:1773 */         if (LogStream.level >= 4) {
/* 1172:1774 */           ioe.printStackTrace(log);
/* 1173:     */         }
/* 1174:     */       }
/* 1175:     */     }
/* 1176:1778 */     UniAddress addr = getFirstAddress();
/* 1177:1779 */     while (addr != null)
/* 1178:     */     {
/* 1179:     */       try
/* 1180:     */       {
/* 1181:1781 */         doConnect();
/* 1182:     */         FileEntry[] entries;
/* 1183:     */         try
/* 1184:     */         {
/* 1185:1783 */           entries = doMsrpcShareEnum();
/* 1186:     */         }
/* 1187:     */         catch (IOException ioe)
/* 1188:     */         {
/* 1189:     */           FileEntry[] entries;
/* 1190:1785 */           if (LogStream.level >= 3) {
/* 1191:1786 */             ioe.printStackTrace(log);
/* 1192:     */           }
/* 1193:1787 */           entries = doNetShareEnum();
/* 1194:     */         }
/* 1195:1789 */         for (int ei = 0; ei < entries.length; ei++)
/* 1196:     */         {
/* 1197:1790 */           FileEntry e = entries[ei];
/* 1198:1791 */           if (!map.containsKey(e)) {
/* 1199:1792 */             map.put(e, e);
/* 1200:     */           }
/* 1201:     */         }
/* 1202:     */       }
/* 1203:     */       catch (IOException ioe)
/* 1204:     */       {
/* 1205:1796 */         if (LogStream.level >= 3) {
/* 1206:1797 */           ioe.printStackTrace(log);
/* 1207:     */         }
/* 1208:1798 */         last = ioe;
/* 1209:     */       }
/* 1210:1800 */       addr = getNextAddress();
/* 1211:     */     }
/* 1212:1803 */     if ((last != null) && (map.isEmpty()))
/* 1213:     */     {
/* 1214:1804 */       if (!(last instanceof SmbException)) {
/* 1215:1805 */         throw new SmbException(this.url.toString(), last);
/* 1216:     */       }
/* 1217:1806 */       throw ((SmbException)last);
/* 1218:     */     }
/* 1219:1809 */     Iterator iter = map.keySet().iterator();
/* 1220:1810 */     while (iter.hasNext())
/* 1221:     */     {
/* 1222:1811 */       FileEntry e = (FileEntry)iter.next();
/* 1223:1812 */       String name = e.getName();
/* 1224:1813 */       if ((fnf == null) || (fnf.accept(this, name))) {
/* 1225:1815 */         if (name.length() > 0)
/* 1226:     */         {
/* 1227:1817 */           SmbFile f = new SmbFile(this, name, e.getType(), 17, 0L, 0L, 0L);
/* 1228:1819 */           if ((ff == null) || (ff.accept(f))) {
/* 1229:1821 */             if (files) {
/* 1230:1822 */               list.add(f);
/* 1231:     */             } else {
/* 1232:1824 */               list.add(name);
/* 1233:     */             }
/* 1234:     */           }
/* 1235:     */         }
/* 1236:     */       }
/* 1237:     */     }
/* 1238:     */   }
/* 1239:     */   
/* 1240:     */   FileEntry[] doDfsRootEnum()
/* 1241:     */     throws IOException
/* 1242:     */   {
/* 1243:1831 */     DcerpcHandle handle = null;
/* 1244:     */     
/* 1245:     */ 
/* 1246:1834 */     handle = DcerpcHandle.getHandle("ncacn_np:" + getAddress().getHostAddress() + "[\\PIPE\\netdfs]", this.auth);
/* 1247:     */     try
/* 1248:     */     {
/* 1249:1838 */       MsrpcDfsRootEnum rpc = new MsrpcDfsRootEnum(getServer());
/* 1250:1839 */       handle.sendrecv(rpc);
/* 1251:1840 */       if (rpc.retval != 0) {
/* 1252:1841 */         throw new SmbException(rpc.retval, true);
/* 1253:     */       }
/* 1254:1842 */       return rpc.getEntries();
/* 1255:     */     }
/* 1256:     */     finally
/* 1257:     */     {
/* 1258:     */       try
/* 1259:     */       {
/* 1260:1845 */         handle.close();
/* 1261:     */       }
/* 1262:     */       catch (IOException ioe)
/* 1263:     */       {
/* 1264:1847 */         if (LogStream.level >= 4) {
/* 1265:1848 */           ioe.printStackTrace(log);
/* 1266:     */         }
/* 1267:     */       }
/* 1268:     */     }
/* 1269:     */   }
/* 1270:     */   
/* 1271:     */   FileEntry[] doMsrpcShareEnum()
/* 1272:     */     throws IOException
/* 1273:     */   {
/* 1274:1856 */     MsrpcShareEnum rpc = new MsrpcShareEnum(this.url.getHost());
/* 1275:     */     
/* 1276:     */ 
/* 1277:     */ 
/* 1278:     */ 
/* 1279:     */ 
/* 1280:     */ 
/* 1281:     */ 
/* 1282:     */ 
/* 1283:1865 */     DcerpcHandle handle = DcerpcHandle.getHandle("ncacn_np:" + getAddress().getHostAddress() + "[\\PIPE\\srvsvc]", this.auth);
/* 1284:     */     try
/* 1285:     */     {
/* 1286:1870 */       handle.sendrecv(rpc);
/* 1287:1871 */       if (rpc.retval != 0) {
/* 1288:1872 */         throw new SmbException(rpc.retval, true);
/* 1289:     */       }
/* 1290:1873 */       return rpc.getEntries();
/* 1291:     */     }
/* 1292:     */     finally
/* 1293:     */     {
/* 1294:     */       try
/* 1295:     */       {
/* 1296:1876 */         handle.close();
/* 1297:     */       }
/* 1298:     */       catch (IOException ioe)
/* 1299:     */       {
/* 1300:1878 */         if (LogStream.level >= 4) {
/* 1301:1879 */           ioe.printStackTrace(log);
/* 1302:     */         }
/* 1303:     */       }
/* 1304:     */     }
/* 1305:     */   }
/* 1306:     */   
/* 1307:     */   FileEntry[] doNetShareEnum()
/* 1308:     */     throws SmbException
/* 1309:     */   {
/* 1310:1884 */     SmbComTransaction req = new NetShareEnum();
/* 1311:1885 */     SmbComTransactionResponse resp = new NetShareEnumResponse();
/* 1312:     */     
/* 1313:1887 */     send(req, resp);
/* 1314:1889 */     if (resp.status != 0) {
/* 1315:1890 */       throw new SmbException(resp.status, true);
/* 1316:     */     }
/* 1317:1892 */     return resp.results;
/* 1318:     */   }
/* 1319:     */   
/* 1320:     */   void doNetServerEnum(ArrayList list, boolean files, String wildcard, int searchAttributes, SmbFilenameFilter fnf, SmbFileFilter ff)
/* 1321:     */     throws SmbException, UnknownHostException, MalformedURLException
/* 1322:     */   {
/* 1323:1902 */     int listType = this.url.getHost().length() == 0 ? 0 : getType();
/* 1324:     */     SmbComTransactionResponse resp;
/* 1325:     */     SmbComTransaction req;
/* 1326:     */     SmbComTransactionResponse resp;
/* 1327:1906 */     if (listType == 0)
/* 1328:     */     {
/* 1329:1907 */       connect0();
/* 1330:1908 */       SmbComTransaction req = new NetServerEnum2(this.tree.session.transport.server.oemDomainName, -2147483648);
/* 1331:     */       
/* 1332:1910 */       resp = new NetServerEnum2Response();
/* 1333:     */     }
/* 1334:1911 */     else if (listType == 2)
/* 1335:     */     {
/* 1336:1912 */       req = new NetServerEnum2(this.url.getHost(), -1);
/* 1337:1913 */       resp = new NetServerEnum2Response();
/* 1338:     */     }
/* 1339:     */     else
/* 1340:     */     {
/* 1341:1915 */       throw new SmbException("The requested list operations is invalid: " + this.url.toString());
/* 1342:     */     }
/* 1343:     */     boolean more;
/* 1344:     */     do
/* 1345:     */     {
/* 1346:1922 */       send(req, resp);
/* 1347:1924 */       if ((resp.status != 0) && (resp.status != 234)) {
/* 1348:1926 */         throw new SmbException(resp.status, true);
/* 1349:     */       }
/* 1350:1928 */       more = resp.status == 234;
/* 1351:     */       
/* 1352:1930 */       int n = more ? resp.numEntries - 1 : resp.numEntries;
/* 1353:1931 */       for (int i = 0; i < n; i++)
/* 1354:     */       {
/* 1355:1932 */         FileEntry e = resp.results[i];
/* 1356:1933 */         String name = e.getName();
/* 1357:1934 */         if ((fnf == null) || (fnf.accept(this, name))) {
/* 1358:1936 */           if (name.length() > 0)
/* 1359:     */           {
/* 1360:1938 */             SmbFile f = new SmbFile(this, name, e.getType(), 17, 0L, 0L, 0L);
/* 1361:1940 */             if ((ff == null) || (ff.accept(f))) {
/* 1362:1942 */               if (files) {
/* 1363:1943 */                 list.add(f);
/* 1364:     */               } else {
/* 1365:1945 */                 list.add(name);
/* 1366:     */               }
/* 1367:     */             }
/* 1368:     */           }
/* 1369:     */         }
/* 1370:     */       }
/* 1371:1949 */       if (getType() != 2) {
/* 1372:     */         break;
/* 1373:     */       }
/* 1374:1952 */       req.subCommand = -41;
/* 1375:1953 */       req.reset(0, ((NetServerEnum2Response)resp).lastName);
/* 1376:1954 */       resp.reset();
/* 1377:1955 */     } while (more);
/* 1378:     */   }
/* 1379:     */   
/* 1380:     */   void doFindFirstNext(ArrayList list, boolean files, String wildcard, int searchAttributes, SmbFilenameFilter fnf, SmbFileFilter ff)
/* 1381:     */     throws SmbException, UnknownHostException, MalformedURLException
/* 1382:     */   {
/* 1383:1966 */     String path = getUncPath0();
/* 1384:1967 */     String p = this.url.getPath();
/* 1385:1969 */     if (p.lastIndexOf('/') != p.length() - 1) {
/* 1386:1970 */       throw new SmbException(this.url.toString() + " directory must end with '/'");
/* 1387:     */     }
/* 1388:1973 */     SmbComTransaction req = new Trans2FindFirst2(path, wildcard, searchAttributes);
/* 1389:1974 */     Trans2FindFirst2Response resp = new Trans2FindFirst2Response();
/* 1390:1976 */     if (LogStream.level >= 3) {
/* 1391:1977 */       log.println("doFindFirstNext: " + req.path);
/* 1392:     */     }
/* 1393:1979 */     send(req, resp);
/* 1394:     */     
/* 1395:1981 */     int sid = resp.sid;
/* 1396:1982 */     req = new Trans2FindNext2(sid, resp.resumeKey, resp.lastName);
/* 1397:     */     
/* 1398:     */ 
/* 1399:     */ 
/* 1400:     */ 
/* 1401:1987 */     resp.subCommand = 2;
/* 1402:     */     for (;;)
/* 1403:     */     {
/* 1404:1990 */       for (int i = 0; i < resp.numEntries; i++)
/* 1405:     */       {
/* 1406:1991 */         FileEntry e = resp.results[i];
/* 1407:1992 */         String name = e.getName();
/* 1408:1993 */         if (name.length() < 3)
/* 1409:     */         {
/* 1410:1994 */           int h = name.hashCode();
/* 1411:1995 */           if (((h == HASH_DOT) || (h == HASH_DOT_DOT)) && (
/* 1412:1996 */             (name.equals(".")) || (name.equals("..")))) {}
/* 1413:     */         }
/* 1414:2000 */         else if ((fnf == null) || (fnf.accept(this, name)))
/* 1415:     */         {
/* 1416:2003 */           if (name.length() > 0)
/* 1417:     */           {
/* 1418:2004 */             SmbFile f = new SmbFile(this, name, 1, e.getAttributes(), e.createTime(), e.lastModified(), e.length());
/* 1419:2006 */             if ((ff == null) || (ff.accept(f))) {
/* 1420:2009 */               if (files) {
/* 1421:2010 */                 list.add(f);
/* 1422:     */               } else {
/* 1423:2012 */                 list.add(name);
/* 1424:     */               }
/* 1425:     */             }
/* 1426:     */           }
/* 1427:     */         }
/* 1428:     */       }
/* 1429:2017 */       if ((resp.isEndOfSearch) || (resp.numEntries == 0)) {
/* 1430:     */         break;
/* 1431:     */       }
/* 1432:2021 */       req.reset(resp.resumeKey, resp.lastName);
/* 1433:2022 */       resp.reset();
/* 1434:2023 */       send(req, resp);
/* 1435:     */     }
/* 1436:     */     try
/* 1437:     */     {
/* 1438:2027 */       send(new SmbComFindClose2(sid), blank_resp());
/* 1439:     */     }
/* 1440:     */     catch (SmbException se)
/* 1441:     */     {
/* 1442:2029 */       if (LogStream.level >= 4) {
/* 1443:2030 */         se.printStackTrace(log);
/* 1444:     */       }
/* 1445:     */     }
/* 1446:     */   }
/* 1447:     */   
/* 1448:     */   public void renameTo(SmbFile dest)
/* 1449:     */     throws SmbException
/* 1450:     */   {
/* 1451:2048 */     if ((getUncPath0().length() == 1) || (dest.getUncPath0().length() == 1)) {
/* 1452:2049 */       throw new SmbException("Invalid operation for workgroups, servers, or shares");
/* 1453:     */     }
/* 1454:2052 */     resolveDfs(null);
/* 1455:2053 */     dest.resolveDfs(null);
/* 1456:2055 */     if (!this.tree.equals(dest.tree)) {
/* 1457:2056 */       throw new SmbException("Invalid operation for workgroups, servers, or shares");
/* 1458:     */     }
/* 1459:2059 */     if (LogStream.level >= 3) {
/* 1460:2060 */       log.println("renameTo: " + this.unc + " -> " + dest.unc);
/* 1461:     */     }
/* 1462:2062 */     this.attrExpiration = (this.sizeExpiration = 0L);
/* 1463:2063 */     dest.attrExpiration = 0L;
/* 1464:     */     
/* 1465:     */ 
/* 1466:     */ 
/* 1467:     */ 
/* 1468:     */ 
/* 1469:2069 */     send(new SmbComRename(this.unc, dest.unc), blank_resp());
/* 1470:     */   }
/* 1471:     */   
/* 1472:     */   class WriterThread
/* 1473:     */     extends Thread
/* 1474:     */   {
/* 1475:     */     byte[] b;
/* 1476:     */     int n;
/* 1477:     */     long off;
/* 1478:     */     boolean ready;
/* 1479:     */     SmbFile dest;
/* 1480:2078 */     SmbException e = null;
/* 1481:     */     boolean useNTSmbs;
/* 1482:     */     SmbComWriteAndX reqx;
/* 1483:     */     SmbComWrite req;
/* 1484:     */     ServerMessageBlock resp;
/* 1485:     */     
/* 1486:     */     WriterThread()
/* 1487:     */       throws SmbException
/* 1488:     */     {
/* 1489:2085 */       super();
/* 1490:2086 */       this.useNTSmbs = SmbFile.this.tree.session.transport.hasCapability(16);
/* 1491:2087 */       if (this.useNTSmbs)
/* 1492:     */       {
/* 1493:2088 */         this.reqx = new SmbComWriteAndX();
/* 1494:2089 */         this.resp = new SmbComWriteAndXResponse();
/* 1495:     */       }
/* 1496:     */       else
/* 1497:     */       {
/* 1498:2091 */         this.req = new SmbComWrite();
/* 1499:2092 */         this.resp = new SmbComWriteResponse();
/* 1500:     */       }
/* 1501:2094 */       this.ready = false;
/* 1502:     */     }
/* 1503:     */     
/* 1504:     */     synchronized void write(byte[] b, int n, SmbFile dest, long off)
/* 1505:     */     {
/* 1506:2098 */       this.b = b;
/* 1507:2099 */       this.n = n;
/* 1508:2100 */       this.dest = dest;
/* 1509:2101 */       this.off = off;
/* 1510:2102 */       this.ready = false;
/* 1511:2103 */       notify();
/* 1512:     */     }
/* 1513:     */     
/* 1514:     */     public void run()
/* 1515:     */     {
/* 1516:2107 */       synchronized (this)
/* 1517:     */       {
/* 1518:     */         try
/* 1519:     */         {
/* 1520:     */           for (;;)
/* 1521:     */           {
/* 1522:2110 */             notify();
/* 1523:2111 */             this.ready = true;
/* 1524:2112 */             while (this.ready) {
/* 1525:2113 */               wait();
/* 1526:     */             }
/* 1527:2115 */             if (this.n == -1) {
/* 1528:2116 */               return;
/* 1529:     */             }
/* 1530:2118 */             if (this.useNTSmbs)
/* 1531:     */             {
/* 1532:2119 */               this.reqx.setParam(this.dest.fid, this.off, this.n, this.b, 0, this.n);
/* 1533:2120 */               this.dest.send(this.reqx, this.resp);
/* 1534:     */             }
/* 1535:     */             else
/* 1536:     */             {
/* 1537:2122 */               this.req.setParam(this.dest.fid, this.off, this.n, this.b, 0, this.n);
/* 1538:2123 */               this.dest.send(this.req, this.resp);
/* 1539:     */             }
/* 1540:     */           }
/* 1541:2131 */           notify();
/* 1542:     */         }
/* 1543:     */         catch (SmbException e)
/* 1544:     */         {
/* 1545:2127 */           this.e = e;
/* 1546:     */         }
/* 1547:     */         catch (Exception x)
/* 1548:     */         {
/* 1549:2129 */           this.e = new SmbException("WriterThread", x);
/* 1550:     */         }
/* 1551:     */       }
/* 1552:     */     }
/* 1553:     */   }
/* 1554:     */   
/* 1555:     */   void copyTo0(SmbFile dest, byte[][] b, int bsize, WriterThread w, SmbComReadAndX req, SmbComReadAndXResponse resp)
/* 1556:     */     throws SmbException
/* 1557:     */   {
/* 1558:2139 */     if (this.attrExpiration < System.currentTimeMillis())
/* 1559:     */     {
/* 1560:2140 */       this.attributes = 17;
/* 1561:2141 */       this.createTime = 0L;
/* 1562:2142 */       this.lastModified = 0L;
/* 1563:2143 */       this.isExists = false;
/* 1564:     */       
/* 1565:2145 */       Info info = queryPath(getUncPath0(), 257);
/* 1566:     */       
/* 1567:2147 */       this.attributes = info.getAttributes();
/* 1568:2148 */       this.createTime = info.getCreateTime();
/* 1569:2149 */       this.lastModified = info.getLastWriteTime();
/* 1570:     */       
/* 1571:     */ 
/* 1572:     */ 
/* 1573:     */ 
/* 1574:2154 */       this.isExists = true;
/* 1575:2155 */       this.attrExpiration = (System.currentTimeMillis() + attrExpirationPeriod);
/* 1576:     */     }
/* 1577:     */     int i;
/* 1578:2158 */     if (isDirectory())
/* 1579:     */     {
/* 1580:2162 */       String path = dest.getUncPath0();
/* 1581:2163 */       if (path.length() > 1) {
/* 1582:     */         try
/* 1583:     */         {
/* 1584:2165 */           dest.mkdir();
/* 1585:2166 */           dest.setPathInformation(this.attributes, this.createTime, this.lastModified);
/* 1586:     */         }
/* 1587:     */         catch (SmbException se)
/* 1588:     */         {
/* 1589:2168 */           if ((se.getNtStatus() != -1073741790) && (se.getNtStatus() != -1073741771)) {
/* 1590:2170 */             throw se;
/* 1591:     */           }
/* 1592:     */         }
/* 1593:     */       }
/* 1594:2175 */       SmbFile[] files = listFiles("*", 22, null, null);
/* 1595:     */       try
/* 1596:     */       {
/* 1597:2177 */         for (int i = 0; i < files.length; i++)
/* 1598:     */         {
/* 1599:2178 */           SmbFile ndest = new SmbFile(dest, files[i].getName(), files[i].type, files[i].attributes, files[i].createTime, files[i].lastModified, files[i].size);
/* 1600:     */           
/* 1601:     */ 
/* 1602:     */ 
/* 1603:     */ 
/* 1604:     */ 
/* 1605:     */ 
/* 1606:2185 */           files[i].copyTo0(ndest, b, bsize, w, req, resp);
/* 1607:     */         }
/* 1608:     */       }
/* 1609:     */       catch (UnknownHostException uhe)
/* 1610:     */       {
/* 1611:2188 */         throw new SmbException(this.url.toString(), uhe);
/* 1612:     */       }
/* 1613:     */       catch (MalformedURLException mue)
/* 1614:     */       {
/* 1615:2190 */         throw new SmbException(this.url.toString(), mue);
/* 1616:     */       }
/* 1617:     */     }
/* 1618:     */     else
/* 1619:     */     {
/* 1620:     */       try
/* 1621:     */       {
/* 1622:2196 */         open(1, 0, 128, 0);
/* 1623:     */         try
/* 1624:     */         {
/* 1625:2198 */           dest.open(82, 258, this.attributes, 0);
/* 1626:     */         }
/* 1627:     */         catch (SmbAuthException sae)
/* 1628:     */         {
/* 1629:2202 */           if ((dest.attributes & 0x1) != 0)
/* 1630:     */           {
/* 1631:2205 */             dest.setPathInformation(dest.attributes & 0xFFFFFFFE, 0L, 0L);
/* 1632:2206 */             dest.open(82, 258, this.attributes, 0);
/* 1633:     */           }
/* 1634:     */           else
/* 1635:     */           {
/* 1636:2210 */             throw sae;
/* 1637:     */           }
/* 1638:     */         }
/* 1639:2214 */         int i = 0;
/* 1640:2215 */         long off = 0L;
/* 1641:     */         for (;;)
/* 1642:     */         {
/* 1643:2217 */           req.setParam(this.fid, off, bsize);
/* 1644:2218 */           resp.setParam(b[i], 0);
/* 1645:2219 */           send(req, resp);
/* 1646:2221 */           synchronized (w)
/* 1647:     */           {
/* 1648:2222 */             if (w.e != null) {
/* 1649:2223 */               throw w.e;
/* 1650:     */             }
/* 1651:2225 */             if (!w.ready) {
/* 1652:     */               try {}catch (InterruptedException ie)
/* 1653:     */               {
/* 1654:2229 */                 throw new SmbException(dest.url.toString(), ie);
/* 1655:     */               }
/* 1656:     */             }
/* 1657:2232 */             if (w.e != null) {
/* 1658:2233 */               throw w.e;
/* 1659:     */             }
/* 1660:2235 */             if (resp.dataLength <= 0) {
/* 1661:     */               break;
/* 1662:     */             }
/* 1663:2238 */             w.write(b[i], resp.dataLength, dest, off);
/* 1664:     */           }
/* 1665:2241 */           i = i == 1 ? 0 : 1;
/* 1666:2242 */           off += resp.dataLength;
/* 1667:     */         }
/* 1668:2245 */         dest.send(new Trans2SetFileInformation(dest.fid, this.attributes, this.createTime, this.lastModified), new Trans2SetFileInformationResponse());
/* 1669:     */         
/* 1670:     */ 
/* 1671:2248 */         dest.close(0L);
/* 1672:     */       }
/* 1673:     */       catch (Exception ex)
/* 1674:     */       {
/* 1675:2250 */         if (LogStream.level > 1) {
/* 1676:2251 */           ex.printStackTrace(log);
/* 1677:     */         }
/* 1678:     */       }
/* 1679:     */       finally
/* 1680:     */       {
/* 1681:2253 */         close();
/* 1682:     */       }
/* 1683:     */     }
/* 1684:     */   }
/* 1685:     */   
/* 1686:     */   public void copyTo(SmbFile dest)
/* 1687:     */     throws SmbException
/* 1688:     */   {
/* 1689:2282 */     if ((this.share == null) || (dest.share == null)) {
/* 1690:2283 */       throw new SmbException("Invalid operation for workgroups or servers");
/* 1691:     */     }
/* 1692:2286 */     SmbComReadAndX req = new SmbComReadAndX();
/* 1693:2287 */     SmbComReadAndXResponse resp = new SmbComReadAndXResponse();
/* 1694:     */     
/* 1695:2289 */     connect0();
/* 1696:2290 */     dest.connect0();
/* 1697:     */     
/* 1698:     */ 
/* 1699:     */ 
/* 1700:     */ 
/* 1701:     */ 
/* 1702:     */ 
/* 1703:     */ 
/* 1704:     */ 
/* 1705:     */ 
/* 1706:     */ 
/* 1707:2301 */     resolveDfs(null);
/* 1708:     */     try
/* 1709:     */     {
/* 1710:2307 */       if ((getAddress().equals(dest.getAddress())) && (this.canon.regionMatches(true, 0, dest.canon, 0, Math.min(this.canon.length(), dest.canon.length())))) {
/* 1711:2310 */         throw new SmbException("Source and destination paths overlap.");
/* 1712:     */       }
/* 1713:     */     }
/* 1714:     */     catch (UnknownHostException uhe) {}
/* 1715:2315 */     WriterThread w = new WriterThread();
/* 1716:2316 */     w.setDaemon(true);
/* 1717:2317 */     w.start();
/* 1718:     */     
/* 1719:     */ 
/* 1720:     */ 
/* 1721:     */ 
/* 1722:     */ 
/* 1723:2323 */     SmbTransport t1 = this.tree.session.transport;
/* 1724:2324 */     SmbTransport t2 = dest.tree.session.transport;
/* 1725:2326 */     if (t1.snd_buf_size < t2.snd_buf_size) {
/* 1726:2327 */       t2.snd_buf_size = t1.snd_buf_size;
/* 1727:     */     } else {
/* 1728:2329 */       t1.snd_buf_size = t2.snd_buf_size;
/* 1729:     */     }
/* 1730:2332 */     int bsize = Math.min(t1.rcv_buf_size - 70, t1.snd_buf_size - 70);
/* 1731:2333 */     byte[][] b = new byte[2][bsize];
/* 1732:     */     try
/* 1733:     */     {
/* 1734:2336 */       copyTo0(dest, b, bsize, w, req, resp);
/* 1735:     */     }
/* 1736:     */     finally
/* 1737:     */     {
/* 1738:2338 */       w.write(null, -1, null, 0L);
/* 1739:     */     }
/* 1740:     */   }
/* 1741:     */   
/* 1742:     */   public void delete()
/* 1743:     */     throws SmbException
/* 1744:     */   {
/* 1745:2352 */     exists();
/* 1746:2353 */     getUncPath0();
/* 1747:2354 */     delete(this.unc);
/* 1748:     */   }
/* 1749:     */   
/* 1750:     */   void delete(String fileName)
/* 1751:     */     throws SmbException
/* 1752:     */   {
/* 1753:2357 */     if (getUncPath0().length() == 1) {
/* 1754:2358 */       throw new SmbException("Invalid operation for workgroups, servers, or shares");
/* 1755:     */     }
/* 1756:2361 */     if (System.currentTimeMillis() > this.attrExpiration)
/* 1757:     */     {
/* 1758:2362 */       this.attributes = 17;
/* 1759:2363 */       this.createTime = 0L;
/* 1760:2364 */       this.lastModified = 0L;
/* 1761:2365 */       this.isExists = false;
/* 1762:     */       
/* 1763:2367 */       Info info = queryPath(getUncPath0(), 257);
/* 1764:     */       
/* 1765:2369 */       this.attributes = info.getAttributes();
/* 1766:2370 */       this.createTime = info.getCreateTime();
/* 1767:2371 */       this.lastModified = info.getLastWriteTime();
/* 1768:     */       
/* 1769:2373 */       this.attrExpiration = (System.currentTimeMillis() + attrExpirationPeriod);
/* 1770:2374 */       this.isExists = true;
/* 1771:     */     }
/* 1772:2377 */     if ((this.attributes & 0x1) != 0) {
/* 1773:2378 */       setReadWrite();
/* 1774:     */     }
/* 1775:2385 */     if (LogStream.level >= 3) {
/* 1776:2386 */       log.println("delete: " + fileName);
/* 1777:     */     }
/* 1778:2388 */     if ((this.attributes & 0x10) != 0)
/* 1779:     */     {
/* 1780:     */       try
/* 1781:     */       {
/* 1782:2394 */         SmbFile[] l = listFiles("*", 22, null, null);
/* 1783:2395 */         for (int i = 0; i < l.length; i++) {
/* 1784:2396 */           l[i].delete();
/* 1785:     */         }
/* 1786:     */       }
/* 1787:     */       catch (SmbException se)
/* 1788:     */       {
/* 1789:2403 */         if (se.getNtStatus() != -1073741809) {
/* 1790:2404 */           throw se;
/* 1791:     */         }
/* 1792:     */       }
/* 1793:2408 */       send(new SmbComDeleteDirectory(fileName), blank_resp());
/* 1794:     */     }
/* 1795:     */     else
/* 1796:     */     {
/* 1797:2410 */       send(new SmbComDelete(fileName), blank_resp());
/* 1798:     */     }
/* 1799:2413 */     this.attrExpiration = (this.sizeExpiration = 0L);
/* 1800:     */   }
/* 1801:     */   
/* 1802:     */   public long length()
/* 1803:     */     throws SmbException
/* 1804:     */   {
/* 1805:2428 */     if (this.sizeExpiration > System.currentTimeMillis()) {
/* 1806:2429 */       return this.size;
/* 1807:     */     }
/* 1808:2432 */     if (getType() == 8)
/* 1809:     */     {
/* 1810:2434 */       int level = 1;
/* 1811:     */       
/* 1812:2436 */       Trans2QueryFSInformationResponse response = new Trans2QueryFSInformationResponse(level);
/* 1813:2437 */       send(new Trans2QueryFSInformation(level), response);
/* 1814:     */       
/* 1815:2439 */       this.size = response.info.getCapacity();
/* 1816:     */     }
/* 1817:2440 */     else if ((getUncPath0().length() > 1) && (this.type != 16))
/* 1818:     */     {
/* 1819:2441 */       Info info = queryPath(getUncPath0(), 258);
/* 1820:     */       
/* 1821:2443 */       this.size = info.getSize();
/* 1822:     */     }
/* 1823:     */     else
/* 1824:     */     {
/* 1825:2445 */       this.size = 0L;
/* 1826:     */     }
/* 1827:2447 */     this.sizeExpiration = (System.currentTimeMillis() + attrExpirationPeriod);
/* 1828:2448 */     return this.size;
/* 1829:     */   }
/* 1830:     */   
/* 1831:     */   public long getDiskFreeSpace()
/* 1832:     */     throws SmbException
/* 1833:     */   {
/* 1834:2461 */     if ((getType() == 8) || (this.type == 1))
/* 1835:     */     {
/* 1836:2462 */       int level = 1007;
/* 1837:     */       try
/* 1838:     */       {
/* 1839:2464 */         return queryFSInformation(level);
/* 1840:     */       }
/* 1841:     */       catch (SmbException ex)
/* 1842:     */       {
/* 1843:2466 */         switch (ex.getNtStatus())
/* 1844:     */         {
/* 1845:     */         case -1073741823: 
/* 1846:     */         case -1073741821: 
/* 1847:2470 */           level = 1;
/* 1848:2471 */           return queryFSInformation(level);
/* 1849:     */         }
/* 1850:     */       }
/* 1851:2473 */       throw ex;
/* 1852:     */     }
/* 1853:2476 */     return 0L;
/* 1854:     */   }
/* 1855:     */   
/* 1856:     */   private long queryFSInformation(int level)
/* 1857:     */     throws SmbException
/* 1858:     */   {
/* 1859:2482 */     Trans2QueryFSInformationResponse response = new Trans2QueryFSInformationResponse(level);
/* 1860:2483 */     send(new Trans2QueryFSInformation(level), response);
/* 1861:2485 */     if (this.type == 8)
/* 1862:     */     {
/* 1863:2486 */       this.size = response.info.getCapacity();
/* 1864:2487 */       this.sizeExpiration = (System.currentTimeMillis() + attrExpirationPeriod);
/* 1865:     */     }
/* 1866:2490 */     return response.info.getFree();
/* 1867:     */   }
/* 1868:     */   
/* 1869:     */   public void mkdir()
/* 1870:     */     throws SmbException
/* 1871:     */   {
/* 1872:2505 */     String path = getUncPath0();
/* 1873:2507 */     if (path.length() == 1) {
/* 1874:2508 */       throw new SmbException("Invalid operation for workgroups, servers, or shares");
/* 1875:     */     }
/* 1876:2515 */     if (LogStream.level >= 3) {
/* 1877:2516 */       log.println("mkdir: " + path);
/* 1878:     */     }
/* 1879:2518 */     send(new SmbComCreateDirectory(path), blank_resp());
/* 1880:     */     
/* 1881:2520 */     this.attrExpiration = (this.sizeExpiration = 0L);
/* 1882:     */   }
/* 1883:     */   
/* 1884:     */   public void mkdirs()
/* 1885:     */     throws SmbException
/* 1886:     */   {
/* 1887:     */     try
/* 1888:     */     {
/* 1889:2537 */       parent = new SmbFile(getParent(), this.auth);
/* 1890:     */     }
/* 1891:     */     catch (IOException ioe)
/* 1892:     */     {
/* 1893:     */       SmbFile parent;
/* 1894:     */       return;
/* 1895:     */     }
/* 1896:     */     SmbFile parent;
/* 1897:2541 */     if (!parent.exists()) {
/* 1898:2542 */       parent.mkdirs();
/* 1899:     */     }
/* 1900:2544 */     mkdir();
/* 1901:     */   }
/* 1902:     */   
/* 1903:     */   public void createNewFile()
/* 1904:     */     throws SmbException
/* 1905:     */   {
/* 1906:2553 */     if (getUncPath0().length() == 1) {
/* 1907:2554 */       throw new SmbException("Invalid operation for workgroups, servers, or shares");
/* 1908:     */     }
/* 1909:2556 */     close(open0(51, 0, 128, 0), 0L);
/* 1910:     */   }
/* 1911:     */   
/* 1912:     */   void setPathInformation(int attrs, long ctime, long mtime)
/* 1913:     */     throws SmbException
/* 1914:     */   {
/* 1915:2562 */     exists();
/* 1916:2563 */     int dir = this.attributes & 0x10;
/* 1917:     */     
/* 1918:2565 */     int f = open0(1, 256, dir, dir != 0 ? 1 : 64);
/* 1919:     */     
/* 1920:2567 */     send(new Trans2SetFileInformation(f, attrs | dir, ctime, mtime), new Trans2SetFileInformationResponse());
/* 1921:     */     
/* 1922:2569 */     close(f, 0L);
/* 1923:     */     
/* 1924:2571 */     this.attrExpiration = 0L;
/* 1925:     */   }
/* 1926:     */   
/* 1927:     */   public void setCreateTime(long time)
/* 1928:     */     throws SmbException
/* 1929:     */   {
/* 1930:2584 */     if (getUncPath0().length() == 1) {
/* 1931:2585 */       throw new SmbException("Invalid operation for workgroups, servers, or shares");
/* 1932:     */     }
/* 1933:2588 */     setPathInformation(0, time, 0L);
/* 1934:     */   }
/* 1935:     */   
/* 1936:     */   public void setLastModified(long time)
/* 1937:     */     throws SmbException
/* 1938:     */   {
/* 1939:2600 */     if (getUncPath0().length() == 1) {
/* 1940:2601 */       throw new SmbException("Invalid operation for workgroups, servers, or shares");
/* 1941:     */     }
/* 1942:2604 */     setPathInformation(0, 0L, time);
/* 1943:     */   }
/* 1944:     */   
/* 1945:     */   public int getAttributes()
/* 1946:     */     throws SmbException
/* 1947:     */   {
/* 1948:2617 */     if (getUncPath0().length() == 1) {
/* 1949:2618 */       return 0;
/* 1950:     */     }
/* 1951:2620 */     exists();
/* 1952:2621 */     return this.attributes & 0x7FFF;
/* 1953:     */   }
/* 1954:     */   
/* 1955:     */   public void setAttributes(int attrs)
/* 1956:     */     throws SmbException
/* 1957:     */   {
/* 1958:2632 */     if (getUncPath0().length() == 1) {
/* 1959:2633 */       throw new SmbException("Invalid operation for workgroups, servers, or shares");
/* 1960:     */     }
/* 1961:2635 */     setPathInformation(attrs & 0x30A7, 0L, 0L);
/* 1962:     */   }
/* 1963:     */   
/* 1964:     */   public void setReadOnly()
/* 1965:     */     throws SmbException
/* 1966:     */   {
/* 1967:2645 */     setAttributes(getAttributes() | 0x1);
/* 1968:     */   }
/* 1969:     */   
/* 1970:     */   public void setReadWrite()
/* 1971:     */     throws SmbException
/* 1972:     */   {
/* 1973:2655 */     setAttributes(getAttributes() & 0xFFFFFFFE);
/* 1974:     */   }
/* 1975:     */   
/* 1976:     */   /**
/* 1977:     */    * @deprecated
/* 1978:     */    */
/* 1979:     */   public URL toURL()
/* 1980:     */     throws MalformedURLException
/* 1981:     */   {
/* 1982:2669 */     return this.url;
/* 1983:     */   }
/* 1984:     */   
/* 1985:     */   public int hashCode()
/* 1986:     */   {
/* 1987:     */     int hash;
/* 1988:     */     try
/* 1989:     */     {
/* 1990:2688 */       hash = getAddress().hashCode();
/* 1991:     */     }
/* 1992:     */     catch (UnknownHostException uhe)
/* 1993:     */     {
/* 1994:     */       int hash;
/* 1995:2690 */       hash = getServer().toUpperCase().hashCode();
/* 1996:     */     }
/* 1997:2692 */     getUncPath0();
/* 1998:2693 */     return hash + this.canon.toUpperCase().hashCode();
/* 1999:     */   }
/* 2000:     */   
/* 2001:     */   protected boolean pathNamesPossiblyEqual(String path1, String path2)
/* 2002:     */   {
/* 2003:2701 */     int p1 = path1.lastIndexOf('/');
/* 2004:2702 */     int p2 = path2.lastIndexOf('/');
/* 2005:2703 */     int l1 = path1.length() - p1;
/* 2006:2704 */     int l2 = path2.length() - p2;
/* 2007:2707 */     if ((l1 > 1) && (path1.charAt(p1 + 1) == '.')) {
/* 2008:2708 */       return true;
/* 2009:     */     }
/* 2010:2709 */     if ((l2 > 1) && (path2.charAt(p2 + 1) == '.')) {
/* 2011:2710 */       return true;
/* 2012:     */     }
/* 2013:2712 */     return (l1 == l2) && (path1.regionMatches(true, p1, path2, p2, l1));
/* 2014:     */   }
/* 2015:     */   
/* 2016:     */   public boolean equals(Object obj)
/* 2017:     */   {
/* 2018:2738 */     if ((obj instanceof SmbFile))
/* 2019:     */     {
/* 2020:2739 */       SmbFile f = (SmbFile)obj;
/* 2021:2742 */       if (this == f) {
/* 2022:2743 */         return true;
/* 2023:     */       }
/* 2024:2748 */       if (pathNamesPossiblyEqual(this.url.getPath(), f.url.getPath()))
/* 2025:     */       {
/* 2026:2750 */         getUncPath0();
/* 2027:2751 */         f.getUncPath0();
/* 2028:2753 */         if (this.canon.equalsIgnoreCase(f.canon))
/* 2029:     */         {
/* 2030:     */           boolean ret;
/* 2031:     */           try
/* 2032:     */           {
/* 2033:2755 */             ret = getAddress().equals(f.getAddress());
/* 2034:     */           }
/* 2035:     */           catch (UnknownHostException uhe)
/* 2036:     */           {
/* 2037:     */             boolean ret;
/* 2038:2757 */             ret = getServer().equalsIgnoreCase(f.getServer());
/* 2039:     */           }
/* 2040:2759 */           return ret;
/* 2041:     */         }
/* 2042:     */       }
/* 2043:     */     }
/* 2044:2764 */     return false;
/* 2045:     */   }
/* 2046:     */   
/* 2047:     */   public String toString()
/* 2048:     */   {
/* 2049:2783 */     return this.url.toString();
/* 2050:     */   }
/* 2051:     */   
/* 2052:     */   public int getContentLength()
/* 2053:     */   {
/* 2054:     */     try
/* 2055:     */     {
/* 2056:2795 */       return (int)(length() & 0xFFFFFFFF);
/* 2057:     */     }
/* 2058:     */     catch (SmbException se) {}
/* 2059:2798 */     return 0;
/* 2060:     */   }
/* 2061:     */   
/* 2062:     */   public long getDate()
/* 2063:     */   {
/* 2064:     */     try
/* 2065:     */     {
/* 2066:2808 */       return lastModified();
/* 2067:     */     }
/* 2068:     */     catch (SmbException se) {}
/* 2069:2811 */     return 0L;
/* 2070:     */   }
/* 2071:     */   
/* 2072:     */   public long getLastModified()
/* 2073:     */   {
/* 2074:     */     try
/* 2075:     */     {
/* 2076:2821 */       return lastModified();
/* 2077:     */     }
/* 2078:     */     catch (SmbException se) {}
/* 2079:2824 */     return 0L;
/* 2080:     */   }
/* 2081:     */   
/* 2082:     */   public InputStream getInputStream()
/* 2083:     */     throws IOException
/* 2084:     */   {
/* 2085:2833 */     return new SmbFileInputStream(this);
/* 2086:     */   }
/* 2087:     */   
/* 2088:     */   public OutputStream getOutputStream()
/* 2089:     */     throws IOException
/* 2090:     */   {
/* 2091:2842 */     return new SmbFileOutputStream(this);
/* 2092:     */   }
/* 2093:     */   
/* 2094:     */   private void processAces(ACE[] aces, boolean resolveSids)
/* 2095:     */     throws IOException
/* 2096:     */   {
/* 2097:2846 */     String server = getServerWithDfs();
/* 2098:2849 */     if (resolveSids)
/* 2099:     */     {
/* 2100:2850 */       SID[] sids = new SID[aces.length];
/* 2101:2851 */       String[] names = null;
/* 2102:2853 */       for (int ai = 0; ai < aces.length; ai++) {
/* 2103:2854 */         sids[ai] = aces[ai].sid;
/* 2104:     */       }
/* 2105:2857 */       for (int off = 0; off < sids.length; off += 10)
/* 2106:     */       {
/* 2107:2858 */         int len = sids.length - off;
/* 2108:2859 */         if (len > 64) {
/* 2109:2860 */           len = 64;
/* 2110:     */         }
/* 2111:2861 */         SID.resolveSids(server, this.auth, sids, off, len);
/* 2112:     */       }
/* 2113:     */     }
/* 2114:     */     else
/* 2115:     */     {
/* 2116:2864 */       for (int ai = 0; ai < aces.length; ai++)
/* 2117:     */       {
/* 2118:2865 */         aces[ai].sid.origin_server = server;
/* 2119:2866 */         aces[ai].sid.origin_auth = this.auth;
/* 2120:     */       }
/* 2121:     */     }
/* 2122:     */   }
/* 2123:     */   
/* 2124:     */   public ACE[] getSecurity(boolean resolveSids)
/* 2125:     */     throws IOException
/* 2126:     */   {
/* 2127:2881 */     int f = open0(1, 131072, 0, isDirectory() ? 1 : 0);
/* 2128:     */     
/* 2129:     */ 
/* 2130:     */ 
/* 2131:     */ 
/* 2132:     */ 
/* 2133:2887 */     NtTransQuerySecurityDesc request = new NtTransQuerySecurityDesc(f, 4);
/* 2134:2888 */     NtTransQuerySecurityDescResponse response = new NtTransQuerySecurityDescResponse();
/* 2135:     */     try
/* 2136:     */     {
/* 2137:2891 */       send(request, response);
/* 2138:     */     }
/* 2139:     */     finally
/* 2140:     */     {
/* 2141:2893 */       close(f, 0L);
/* 2142:     */     }
/* 2143:2896 */     ACE[] aces = response.securityDescriptor.aces;
/* 2144:2897 */     if (aces != null) {
/* 2145:2898 */       processAces(aces, resolveSids);
/* 2146:     */     }
/* 2147:2900 */     return aces;
/* 2148:     */   }
/* 2149:     */   
/* 2150:     */   public ACE[] getShareSecurity(boolean resolveSids)
/* 2151:     */     throws IOException
/* 2152:     */   {
/* 2153:2920 */     String p = this.url.getPath();
/* 2154:     */     
/* 2155:     */ 
/* 2156:     */ 
/* 2157:     */ 
/* 2158:2925 */     resolveDfs(null);
/* 2159:2926 */     String server = getServerWithDfs();
/* 2160:     */     
/* 2161:2928 */     MsrpcShareGetInfo rpc = new MsrpcShareGetInfo(server, this.tree.share);
/* 2162:2929 */     DcerpcHandle handle = DcerpcHandle.getHandle("ncacn_np:" + server + "[\\PIPE\\srvsvc]", this.auth);
/* 2163:     */     try
/* 2164:     */     {
/* 2165:2932 */       handle.sendrecv(rpc);
/* 2166:2933 */       if (rpc.retval != 0) {
/* 2167:2934 */         throw new SmbException(rpc.retval, true);
/* 2168:     */       }
/* 2169:2935 */       ACE[] aces = rpc.getSecurity();
/* 2170:2936 */       if (aces != null) {
/* 2171:2937 */         processAces(aces, resolveSids);
/* 2172:     */       }
/* 2173:     */     }
/* 2174:     */     finally
/* 2175:     */     {
/* 2176:     */       try
/* 2177:     */       {
/* 2178:2940 */         handle.close();
/* 2179:     */       }
/* 2180:     */       catch (IOException ioe)
/* 2181:     */       {
/* 2182:2942 */         if (LogStream.level >= 1) {
/* 2183:2943 */           ioe.printStackTrace(log);
/* 2184:     */         }
/* 2185:     */       }
/* 2186:     */     }
/* 2187:     */     ACE[] aces;
/* 2188:2947 */     return aces;
/* 2189:     */   }
/* 2190:     */   
/* 2191:     */   public ACE[] getSecurity()
/* 2192:     */     throws IOException
/* 2193:     */   {
/* 2194:2964 */     return getSecurity(false);
/* 2195:     */   }
/* 2196:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbFile
 * JD-Core Version:    0.7.0.1
 */