/*   1:    */ package org.apache.http;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.http.util.CharArrayBuffer;
/*   5:    */ 
/*   6:    */ public class ProtocolVersion
/*   7:    */   implements Serializable, Cloneable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 8950662842175091068L;
/*  10:    */   protected final String protocol;
/*  11:    */   protected final int major;
/*  12:    */   protected final int minor;
/*  13:    */   
/*  14:    */   public ProtocolVersion(String protocol, int major, int minor)
/*  15:    */   {
/*  16: 68 */     if (protocol == null) {
/*  17: 69 */       throw new IllegalArgumentException("Protocol name must not be null.");
/*  18:    */     }
/*  19: 72 */     if (major < 0) {
/*  20: 73 */       throw new IllegalArgumentException("Protocol major version number must not be negative.");
/*  21:    */     }
/*  22: 76 */     if (minor < 0) {
/*  23: 77 */       throw new IllegalArgumentException("Protocol minor version number may not be negative");
/*  24:    */     }
/*  25: 80 */     this.protocol = protocol;
/*  26: 81 */     this.major = major;
/*  27: 82 */     this.minor = minor;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public final String getProtocol()
/*  31:    */   {
/*  32: 91 */     return this.protocol;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final int getMajor()
/*  36:    */   {
/*  37:100 */     return this.major;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final int getMinor()
/*  41:    */   {
/*  42:109 */     return this.minor;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ProtocolVersion forVersion(int major, int minor)
/*  46:    */   {
/*  47:130 */     if ((major == this.major) && (minor == this.minor)) {
/*  48:131 */       return this;
/*  49:    */     }
/*  50:135 */     return new ProtocolVersion(this.protocol, major, minor);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final int hashCode()
/*  54:    */   {
/*  55:145 */     return this.protocol.hashCode() ^ this.major * 100000 ^ this.minor;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public final boolean equals(Object obj)
/*  59:    */   {
/*  60:163 */     if (this == obj) {
/*  61:164 */       return true;
/*  62:    */     }
/*  63:166 */     if (!(obj instanceof ProtocolVersion)) {
/*  64:167 */       return false;
/*  65:    */     }
/*  66:169 */     ProtocolVersion that = (ProtocolVersion)obj;
/*  67:    */     
/*  68:171 */     return (this.protocol.equals(that.protocol)) && (this.major == that.major) && (this.minor == that.minor);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isComparable(ProtocolVersion that)
/*  72:    */   {
/*  73:188 */     return (that != null) && (this.protocol.equals(that.protocol));
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int compareToVersion(ProtocolVersion that)
/*  77:    */   {
/*  78:209 */     if (that == null) {
/*  79:210 */       throw new IllegalArgumentException("Protocol version must not be null.");
/*  80:    */     }
/*  81:213 */     if (!this.protocol.equals(that.protocol)) {
/*  82:214 */       throw new IllegalArgumentException("Versions for different protocols cannot be compared. " + this + " " + that);
/*  83:    */     }
/*  84:219 */     int delta = getMajor() - that.getMajor();
/*  85:220 */     if (delta == 0) {
/*  86:221 */       delta = getMinor() - that.getMinor();
/*  87:    */     }
/*  88:223 */     return delta;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public final boolean greaterEquals(ProtocolVersion version)
/*  92:    */   {
/*  93:238 */     return (isComparable(version)) && (compareToVersion(version) >= 0);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public final boolean lessEquals(ProtocolVersion version)
/*  97:    */   {
/*  98:253 */     return (isComparable(version)) && (compareToVersion(version) <= 0);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String toString()
/* 102:    */   {
/* 103:263 */     CharArrayBuffer buffer = new CharArrayBuffer(16);
/* 104:264 */     buffer.append(this.protocol);
/* 105:265 */     buffer.append('/');
/* 106:266 */     buffer.append(Integer.toString(this.major));
/* 107:267 */     buffer.append('.');
/* 108:268 */     buffer.append(Integer.toString(this.minor));
/* 109:269 */     return buffer.toString();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Object clone()
/* 113:    */     throws CloneNotSupportedException
/* 114:    */   {
/* 115:273 */     return super.clone();
/* 116:    */   }
/* 117:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.ProtocolVersion
 * JD-Core Version:    0.7.0.1
 */