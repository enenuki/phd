/*   1:    */ package jcifs.dcerpc;
/*   2:    */ 
/*   3:    */ import jcifs.dcerpc.ndr.NdrBuffer;
/*   4:    */ import jcifs.dcerpc.ndr.NdrException;
/*   5:    */ import jcifs.dcerpc.ndr.NdrObject;
/*   6:    */ 
/*   7:    */ public class rpc
/*   8:    */ {
/*   9:    */   public static class uuid_t
/*  10:    */     extends NdrObject
/*  11:    */   {
/*  12:    */     public int time_low;
/*  13:    */     public short time_mid;
/*  14:    */     public short time_hi_and_version;
/*  15:    */     public byte clock_seq_hi_and_reserved;
/*  16:    */     public byte clock_seq_low;
/*  17:    */     public byte[] node;
/*  18:    */     
/*  19:    */     public void encode(NdrBuffer _dst)
/*  20:    */       throws NdrException
/*  21:    */     {
/*  22: 18 */       _dst.align(4);
/*  23: 19 */       _dst.enc_ndr_long(this.time_low);
/*  24: 20 */       _dst.enc_ndr_short(this.time_mid);
/*  25: 21 */       _dst.enc_ndr_short(this.time_hi_and_version);
/*  26: 22 */       _dst.enc_ndr_small(this.clock_seq_hi_and_reserved);
/*  27: 23 */       _dst.enc_ndr_small(this.clock_seq_low);
/*  28: 24 */       int _nodes = 6;
/*  29: 25 */       int _nodei = _dst.index;
/*  30: 26 */       _dst.advance(1 * _nodes);
/*  31:    */       
/*  32: 28 */       _dst = _dst.derive(_nodei);
/*  33: 29 */       for (int _i = 0; _i < _nodes; _i++) {
/*  34: 30 */         _dst.enc_ndr_small(this.node[_i]);
/*  35:    */       }
/*  36:    */     }
/*  37:    */     
/*  38:    */     public void decode(NdrBuffer _src)
/*  39:    */       throws NdrException
/*  40:    */     {
/*  41: 34 */       _src.align(4);
/*  42: 35 */       this.time_low = _src.dec_ndr_long();
/*  43: 36 */       this.time_mid = ((short)_src.dec_ndr_short());
/*  44: 37 */       this.time_hi_and_version = ((short)_src.dec_ndr_short());
/*  45: 38 */       this.clock_seq_hi_and_reserved = ((byte)_src.dec_ndr_small());
/*  46: 39 */       this.clock_seq_low = ((byte)_src.dec_ndr_small());
/*  47: 40 */       int _nodes = 6;
/*  48: 41 */       int _nodei = _src.index;
/*  49: 42 */       _src.advance(1 * _nodes);
/*  50: 44 */       if (this.node == null)
/*  51:    */       {
/*  52: 45 */         if ((_nodes < 0) || (_nodes > 65535)) {
/*  53: 45 */           throw new NdrException("invalid array conformance");
/*  54:    */         }
/*  55: 46 */         this.node = new byte[_nodes];
/*  56:    */       }
/*  57: 48 */       _src = _src.derive(_nodei);
/*  58: 49 */       for (int _i = 0; _i < _nodes; _i++) {
/*  59: 50 */         this.node[_i] = ((byte)_src.dec_ndr_small());
/*  60:    */       }
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static class policy_handle
/*  65:    */     extends NdrObject
/*  66:    */   {
/*  67:    */     public int type;
/*  68:    */     public rpc.uuid_t uuid;
/*  69:    */     
/*  70:    */     public void encode(NdrBuffer _dst)
/*  71:    */       throws NdrException
/*  72:    */     {
/*  73: 60 */       _dst.align(4);
/*  74: 61 */       _dst.enc_ndr_long(this.type);
/*  75: 62 */       _dst.enc_ndr_long(this.uuid.time_low);
/*  76: 63 */       _dst.enc_ndr_short(this.uuid.time_mid);
/*  77: 64 */       _dst.enc_ndr_short(this.uuid.time_hi_and_version);
/*  78: 65 */       _dst.enc_ndr_small(this.uuid.clock_seq_hi_and_reserved);
/*  79: 66 */       _dst.enc_ndr_small(this.uuid.clock_seq_low);
/*  80: 67 */       int _uuid_nodes = 6;
/*  81: 68 */       int _uuid_nodei = _dst.index;
/*  82: 69 */       _dst.advance(1 * _uuid_nodes);
/*  83:    */       
/*  84: 71 */       _dst = _dst.derive(_uuid_nodei);
/*  85: 72 */       for (int _i = 0; _i < _uuid_nodes; _i++) {
/*  86: 73 */         _dst.enc_ndr_small(this.uuid.node[_i]);
/*  87:    */       }
/*  88:    */     }
/*  89:    */     
/*  90:    */     public void decode(NdrBuffer _src)
/*  91:    */       throws NdrException
/*  92:    */     {
/*  93: 77 */       _src.align(4);
/*  94: 78 */       this.type = _src.dec_ndr_long();
/*  95: 79 */       _src.align(4);
/*  96: 80 */       if (this.uuid == null) {
/*  97: 81 */         this.uuid = new rpc.uuid_t();
/*  98:    */       }
/*  99: 83 */       this.uuid.time_low = _src.dec_ndr_long();
/* 100: 84 */       this.uuid.time_mid = ((short)_src.dec_ndr_short());
/* 101: 85 */       this.uuid.time_hi_and_version = ((short)_src.dec_ndr_short());
/* 102: 86 */       this.uuid.clock_seq_hi_and_reserved = ((byte)_src.dec_ndr_small());
/* 103: 87 */       this.uuid.clock_seq_low = ((byte)_src.dec_ndr_small());
/* 104: 88 */       int _uuid_nodes = 6;
/* 105: 89 */       int _uuid_nodei = _src.index;
/* 106: 90 */       _src.advance(1 * _uuid_nodes);
/* 107: 92 */       if (this.uuid.node == null)
/* 108:    */       {
/* 109: 93 */         if ((_uuid_nodes < 0) || (_uuid_nodes > 65535)) {
/* 110: 93 */           throw new NdrException("invalid array conformance");
/* 111:    */         }
/* 112: 94 */         this.uuid.node = new byte[_uuid_nodes];
/* 113:    */       }
/* 114: 96 */       _src = _src.derive(_uuid_nodei);
/* 115: 97 */       for (int _i = 0; _i < _uuid_nodes; _i++) {
/* 116: 98 */         this.uuid.node[_i] = ((byte)_src.dec_ndr_small());
/* 117:    */       }
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static class unicode_string
/* 122:    */     extends NdrObject
/* 123:    */   {
/* 124:    */     public short length;
/* 125:    */     public short maximum_length;
/* 126:    */     public short[] buffer;
/* 127:    */     
/* 128:    */     public void encode(NdrBuffer _dst)
/* 129:    */       throws NdrException
/* 130:    */     {
/* 131:109 */       _dst.align(4);
/* 132:110 */       _dst.enc_ndr_short(this.length);
/* 133:111 */       _dst.enc_ndr_short(this.maximum_length);
/* 134:112 */       _dst.enc_ndr_referent(this.buffer, 1);
/* 135:114 */       if (this.buffer != null)
/* 136:    */       {
/* 137:115 */         _dst = _dst.deferred;
/* 138:116 */         int _bufferl = this.length / 2;
/* 139:117 */         int _buffers = this.maximum_length / 2;
/* 140:118 */         _dst.enc_ndr_long(_buffers);
/* 141:119 */         _dst.enc_ndr_long(0);
/* 142:120 */         _dst.enc_ndr_long(_bufferl);
/* 143:121 */         int _bufferi = _dst.index;
/* 144:122 */         _dst.advance(2 * _bufferl);
/* 145:    */         
/* 146:124 */         _dst = _dst.derive(_bufferi);
/* 147:125 */         for (int _i = 0; _i < _bufferl; _i++) {
/* 148:126 */           _dst.enc_ndr_short(this.buffer[_i]);
/* 149:    */         }
/* 150:    */       }
/* 151:    */     }
/* 152:    */     
/* 153:    */     public void decode(NdrBuffer _src)
/* 154:    */       throws NdrException
/* 155:    */     {
/* 156:131 */       _src.align(4);
/* 157:132 */       this.length = ((short)_src.dec_ndr_short());
/* 158:133 */       this.maximum_length = ((short)_src.dec_ndr_short());
/* 159:134 */       int _bufferp = _src.dec_ndr_long();
/* 160:136 */       if (_bufferp != 0)
/* 161:    */       {
/* 162:137 */         _src = _src.deferred;
/* 163:138 */         int _buffers = _src.dec_ndr_long();
/* 164:139 */         _src.dec_ndr_long();
/* 165:140 */         int _bufferl = _src.dec_ndr_long();
/* 166:141 */         int _bufferi = _src.index;
/* 167:142 */         _src.advance(2 * _bufferl);
/* 168:144 */         if (this.buffer == null)
/* 169:    */         {
/* 170:145 */           if ((_buffers < 0) || (_buffers > 65535)) {
/* 171:145 */             throw new NdrException("invalid array conformance");
/* 172:    */           }
/* 173:146 */           this.buffer = new short[_buffers];
/* 174:    */         }
/* 175:148 */         _src = _src.derive(_bufferi);
/* 176:149 */         for (int _i = 0; _i < _bufferl; _i++) {
/* 177:150 */           this.buffer[_i] = ((short)_src.dec_ndr_short());
/* 178:    */         }
/* 179:    */       }
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public static class sid_t
/* 184:    */     extends NdrObject
/* 185:    */   {
/* 186:    */     public byte revision;
/* 187:    */     public byte sub_authority_count;
/* 188:    */     public byte[] identifier_authority;
/* 189:    */     public int[] sub_authority;
/* 190:    */     
/* 191:    */     public void encode(NdrBuffer _dst)
/* 192:    */       throws NdrException
/* 193:    */     {
/* 194:163 */       _dst.align(4);
/* 195:164 */       int _sub_authoritys = this.sub_authority_count;
/* 196:165 */       _dst.enc_ndr_long(_sub_authoritys);
/* 197:166 */       _dst.enc_ndr_small(this.revision);
/* 198:167 */       _dst.enc_ndr_small(this.sub_authority_count);
/* 199:168 */       int _identifier_authoritys = 6;
/* 200:169 */       int _identifier_authorityi = _dst.index;
/* 201:170 */       _dst.advance(1 * _identifier_authoritys);
/* 202:171 */       int _sub_authorityi = _dst.index;
/* 203:172 */       _dst.advance(4 * _sub_authoritys);
/* 204:    */       
/* 205:174 */       _dst = _dst.derive(_identifier_authorityi);
/* 206:175 */       for (int _i = 0; _i < _identifier_authoritys; _i++) {
/* 207:176 */         _dst.enc_ndr_small(this.identifier_authority[_i]);
/* 208:    */       }
/* 209:178 */       _dst = _dst.derive(_sub_authorityi);
/* 210:179 */       for (int _i = 0; _i < _sub_authoritys; _i++) {
/* 211:180 */         _dst.enc_ndr_long(this.sub_authority[_i]);
/* 212:    */       }
/* 213:    */     }
/* 214:    */     
/* 215:    */     public void decode(NdrBuffer _src)
/* 216:    */       throws NdrException
/* 217:    */     {
/* 218:184 */       _src.align(4);
/* 219:185 */       int _sub_authoritys = _src.dec_ndr_long();
/* 220:186 */       this.revision = ((byte)_src.dec_ndr_small());
/* 221:187 */       this.sub_authority_count = ((byte)_src.dec_ndr_small());
/* 222:188 */       int _identifier_authoritys = 6;
/* 223:189 */       int _identifier_authorityi = _src.index;
/* 224:190 */       _src.advance(1 * _identifier_authoritys);
/* 225:191 */       int _sub_authorityi = _src.index;
/* 226:192 */       _src.advance(4 * _sub_authoritys);
/* 227:194 */       if (this.identifier_authority == null)
/* 228:    */       {
/* 229:195 */         if ((_identifier_authoritys < 0) || (_identifier_authoritys > 65535)) {
/* 230:195 */           throw new NdrException("invalid array conformance");
/* 231:    */         }
/* 232:196 */         this.identifier_authority = new byte[_identifier_authoritys];
/* 233:    */       }
/* 234:198 */       _src = _src.derive(_identifier_authorityi);
/* 235:199 */       for (int _i = 0; _i < _identifier_authoritys; _i++) {
/* 236:200 */         this.identifier_authority[_i] = ((byte)_src.dec_ndr_small());
/* 237:    */       }
/* 238:202 */       if (this.sub_authority == null)
/* 239:    */       {
/* 240:203 */         if ((_sub_authoritys < 0) || (_sub_authoritys > 65535)) {
/* 241:203 */           throw new NdrException("invalid array conformance");
/* 242:    */         }
/* 243:204 */         this.sub_authority = new int[_sub_authoritys];
/* 244:    */       }
/* 245:206 */       _src = _src.derive(_sub_authorityi);
/* 246:207 */       for (int _i = 0; _i < _sub_authoritys; _i++) {
/* 247:208 */         this.sub_authority[_i] = _src.dec_ndr_long();
/* 248:    */       }
/* 249:    */     }
/* 250:    */   }
/* 251:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.rpc
 * JD-Core Version:    0.7.0.1
 */