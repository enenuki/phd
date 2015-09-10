/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import java.net.InetAddress;
/*   4:    */ import java.util.LinkedList;
/*   5:    */ import java.util.TimeZone;
/*   6:    */ import jcifs.Config;
/*   7:    */ 
/*   8:    */ abstract interface SmbConstants
/*   9:    */ {
/*  10:    */   public static final int DEFAULT_PORT = 445;
/*  11:    */   public static final int DEFAULT_MAX_MPX_COUNT = 10;
/*  12:    */   public static final int DEFAULT_RESPONSE_TIMEOUT = 30000;
/*  13:    */   public static final int DEFAULT_SO_TIMEOUT = 35000;
/*  14:    */   public static final int DEFAULT_RCV_BUF_SIZE = 60416;
/*  15:    */   public static final int DEFAULT_SND_BUF_SIZE = 16644;
/*  16:    */   public static final int DEFAULT_SSN_LIMIT = 250;
/*  17: 19 */   public static final InetAddress LADDR = ;
/*  18: 20 */   public static final int LPORT = Config.getInt("jcifs.smb.client.lport", 0);
/*  19: 21 */   public static final int MAX_MPX_COUNT = Config.getInt("jcifs.smb.client.maxMpxCount", 10);
/*  20: 22 */   public static final int SND_BUF_SIZE = Config.getInt("jcifs.smb.client.snd_buf_size", 16644);
/*  21: 23 */   public static final int RCV_BUF_SIZE = Config.getInt("jcifs.smb.client.rcv_buf_size", 60416);
/*  22: 24 */   public static final boolean USE_UNICODE = Config.getBoolean("jcifs.smb.client.useUnicode", true);
/*  23: 25 */   public static final boolean FORCE_UNICODE = Config.getBoolean("jcifs.smb.client.useUnicode", false);
/*  24: 26 */   public static final boolean USE_NTSTATUS = Config.getBoolean("jcifs.smb.client.useNtStatus", true);
/*  25: 27 */   public static final boolean SIGNPREF = Config.getBoolean("jcifs.smb.client.signingPreferred", false);
/*  26: 28 */   public static final boolean USE_NTSMBS = Config.getBoolean("jcifs.smb.client.useNTSmbs", true);
/*  27: 29 */   public static final boolean USE_EXTSEC = Config.getBoolean("jcifs.smb.client.useExtendedSecurity", true);
/*  28: 31 */   public static final String NETBIOS_HOSTNAME = Config.getProperty("jcifs.netbios.hostname", null);
/*  29: 32 */   public static final int LM_COMPATIBILITY = Config.getInt("jcifs.smb.lmCompatibility", 3);
/*  30:    */   public static final int FLAGS_NONE = 0;
/*  31:    */   public static final int FLAGS_LOCK_AND_READ_WRITE_AND_UNLOCK = 1;
/*  32:    */   public static final int FLAGS_RECEIVE_BUFFER_POSTED = 2;
/*  33:    */   public static final int FLAGS_PATH_NAMES_CASELESS = 8;
/*  34:    */   public static final int FLAGS_PATH_NAMES_CANONICALIZED = 16;
/*  35:    */   public static final int FLAGS_OPLOCK_REQUESTED_OR_GRANTED = 32;
/*  36:    */   public static final int FLAGS_NOTIFY_OF_MODIFY_ACTION = 64;
/*  37:    */   public static final int FLAGS_RESPONSE = 128;
/*  38:    */   public static final int FLAGS2_NONE = 0;
/*  39:    */   public static final int FLAGS2_LONG_FILENAMES = 1;
/*  40:    */   public static final int FLAGS2_EXTENDED_ATTRIBUTES = 2;
/*  41:    */   public static final int FLAGS2_SECURITY_SIGNATURES = 4;
/*  42:    */   public static final int FLAGS2_EXTENDED_SECURITY_NEGOTIATION = 2048;
/*  43:    */   public static final int FLAGS2_RESOLVE_PATHS_IN_DFS = 4096;
/*  44:    */   public static final int FLAGS2_PERMIT_READ_IF_EXECUTE_PERM = 8192;
/*  45:    */   public static final int FLAGS2_STATUS32 = 16384;
/*  46:    */   public static final int FLAGS2_UNICODE = 32768;
/*  47:    */   public static final int CAP_NONE = 0;
/*  48:    */   public static final int CAP_RAW_MODE = 1;
/*  49:    */   public static final int CAP_MPX_MODE = 2;
/*  50:    */   public static final int CAP_UNICODE = 4;
/*  51:    */   public static final int CAP_LARGE_FILES = 8;
/*  52:    */   public static final int CAP_NT_SMBS = 16;
/*  53:    */   public static final int CAP_RPC_REMOTE_APIS = 32;
/*  54:    */   public static final int CAP_STATUS32 = 64;
/*  55:    */   public static final int CAP_LEVEL_II_OPLOCKS = 128;
/*  56:    */   public static final int CAP_LOCK_AND_READ = 256;
/*  57:    */   public static final int CAP_NT_FIND = 512;
/*  58:    */   public static final int CAP_DFS = 4096;
/*  59:    */   public static final int CAP_EXTENDED_SECURITY = -2147483648;
/*  60:    */   public static final int ATTR_READONLY = 1;
/*  61:    */   public static final int ATTR_HIDDEN = 2;
/*  62:    */   public static final int ATTR_SYSTEM = 4;
/*  63:    */   public static final int ATTR_VOLUME = 8;
/*  64:    */   public static final int ATTR_DIRECTORY = 16;
/*  65:    */   public static final int ATTR_ARCHIVE = 32;
/*  66:    */   public static final int ATTR_COMPRESSED = 2048;
/*  67:    */   public static final int ATTR_NORMAL = 128;
/*  68:    */   public static final int ATTR_TEMPORARY = 256;
/*  69:    */   public static final int FILE_READ_DATA = 1;
/*  70:    */   public static final int FILE_WRITE_DATA = 2;
/*  71:    */   public static final int FILE_APPEND_DATA = 4;
/*  72:    */   public static final int FILE_READ_EA = 8;
/*  73:    */   public static final int FILE_WRITE_EA = 16;
/*  74:    */   public static final int FILE_EXECUTE = 32;
/*  75:    */   public static final int FILE_DELETE = 64;
/*  76:    */   public static final int FILE_READ_ATTRIBUTES = 128;
/*  77:    */   public static final int FILE_WRITE_ATTRIBUTES = 256;
/*  78:    */   public static final int DELETE = 65536;
/*  79:    */   public static final int READ_CONTROL = 131072;
/*  80:    */   public static final int WRITE_DAC = 262144;
/*  81:    */   public static final int WRITE_OWNER = 524288;
/*  82:    */   public static final int SYNCHRONIZE = 1048576;
/*  83:    */   public static final int GENERIC_ALL = 268435456;
/*  84:    */   public static final int GENERIC_EXECUTE = 536870912;
/*  85:    */   public static final int GENERIC_WRITE = 1073741824;
/*  86:    */   public static final int GENERIC_READ = -2147483648;
/*  87:    */   public static final int FLAGS_TARGET_MUST_BE_FILE = 1;
/*  88:    */   public static final int FLAGS_TARGET_MUST_BE_DIRECTORY = 2;
/*  89:    */   public static final int FLAGS_COPY_TARGET_MODE_ASCII = 4;
/*  90:    */   public static final int FLAGS_COPY_SOURCE_MODE_ASCII = 8;
/*  91:    */   public static final int FLAGS_VERIFY_ALL_WRITES = 16;
/*  92:    */   public static final int FLAGS_TREE_COPY = 32;
/*  93:    */   public static final int OPEN_FUNCTION_FAIL_IF_EXISTS = 0;
/*  94:    */   public static final int OPEN_FUNCTION_OVERWRITE_IF_EXISTS = 32;
/*  95:113 */   public static final int PID = (int)(Math.random() * 65536.0D);
/*  96:    */   public static final int SECURITY_SHARE = 0;
/*  97:    */   public static final int SECURITY_USER = 1;
/*  98:    */   public static final int CMD_OFFSET = 4;
/*  99:    */   public static final int ERROR_CODE_OFFSET = 5;
/* 100:    */   public static final int FLAGS_OFFSET = 9;
/* 101:    */   public static final int SIGNATURE_OFFSET = 14;
/* 102:    */   public static final int TID_OFFSET = 24;
/* 103:    */   public static final int HEADER_LENGTH = 32;
/* 104:    */   public static final long MILLISECONDS_BETWEEN_1970_AND_1601 = 11644473600000L;
/* 105:126 */   public static final TimeZone TZ = TimeZone.getDefault();
/* 106:128 */   public static final boolean USE_BATCHING = Config.getBoolean("jcifs.smb.client.useBatching", true);
/* 107:129 */   public static final String OEM_ENCODING = Config.getProperty("jcifs.encoding", Config.DEFAULT_OEM_ENCODING);
/* 108:    */   public static final String UNI_ENCODING = "UTF-16LE";
/* 109:131 */   public static final int DEFAULT_FLAGS2 = 0x3 | (USE_EXTSEC ? 2048 : 0) | (SIGNPREF ? 4 : 0) | (USE_NTSTATUS ? 16384 : 0) | (USE_UNICODE ? 32768 : 0);
/* 110:138 */   public static final int DEFAULT_CAPABILITIES = (USE_NTSMBS ? 16 : 0) | (USE_NTSTATUS ? 64 : 0) | (USE_UNICODE ? 4 : 0) | 0x1000;
/* 111:143 */   public static final int FLAGS2 = Config.getInt("jcifs.smb.client.flags2", DEFAULT_FLAGS2);
/* 112:144 */   public static final int CAPABILITIES = Config.getInt("jcifs.smb.client.capabilities", DEFAULT_CAPABILITIES);
/* 113:145 */   public static final boolean TCP_NODELAY = Config.getBoolean("jcifs.smb.client.tcpNoDelay", false);
/* 114:146 */   public static final int RESPONSE_TIMEOUT = Config.getInt("jcifs.smb.client.responseTimeout", 30000);
/* 115:149 */   public static final LinkedList CONNECTIONS = new LinkedList();
/* 116:151 */   public static final int SSN_LIMIT = Config.getInt("jcifs.smb.client.ssnLimit", 250);
/* 117:153 */   public static final int SO_TIMEOUT = Config.getInt("jcifs.smb.client.soTimeout", 35000);
/* 118:155 */   public static final String NATIVE_OS = Config.getProperty("jcifs.smb.client.nativeOs", System.getProperty("os.name"));
/* 119:157 */   public static final String NATIVE_LANMAN = Config.getProperty("jcifs.smb.client.nativeLanMan", "jCIFS");
/* 120:    */   public static final int VC_NUMBER = 1;
/* 121:160 */   public static final SmbTransport NULL_TRANSPORT = new SmbTransport(null, 0, null, 0);
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbConstants
 * JD-Core Version:    0.7.0.1
 */