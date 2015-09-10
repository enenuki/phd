/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import jcifs.util.LogStream;
/*   4:    */ 
/*   5:    */ class SmbTree
/*   6:    */ {
/*   7:    */   private static int tree_conn_counter;
/*   8:    */   int connectionState;
/*   9:    */   int tid;
/*  10:    */   String share;
/*  11: 40 */   String service = "?????";
/*  12:    */   String service0;
/*  13:    */   SmbSession session;
/*  14:    */   boolean inDfs;
/*  15:    */   boolean inDomainDfs;
/*  16:    */   int tree_num;
/*  17:    */   
/*  18:    */   SmbTree(SmbSession session, String share, String service)
/*  19:    */   {
/*  20: 47 */     this.session = session;
/*  21: 48 */     this.share = share.toUpperCase();
/*  22: 49 */     if ((service != null) && (!service.startsWith("??"))) {
/*  23: 50 */       this.service = service;
/*  24:    */     }
/*  25: 52 */     this.service0 = this.service;
/*  26: 53 */     this.connectionState = 0;
/*  27:    */   }
/*  28:    */   
/*  29:    */   boolean matches(String share, String service)
/*  30:    */   {
/*  31: 57 */     return (this.share.equalsIgnoreCase(share)) && ((service == null) || (service.startsWith("??")) || (this.service.equalsIgnoreCase(service)));
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean equals(Object obj)
/*  35:    */   {
/*  36: 62 */     if ((obj instanceof SmbTree))
/*  37:    */     {
/*  38: 63 */       SmbTree tree = (SmbTree)obj;
/*  39: 64 */       return matches(tree.share, tree.service);
/*  40:    */     }
/*  41: 66 */     return false;
/*  42:    */   }
/*  43:    */   
/*  44:    */   void send(ServerMessageBlock request, ServerMessageBlock response)
/*  45:    */     throws SmbException
/*  46:    */   {
/*  47: 70 */     synchronized (this.session.transport())
/*  48:    */     {
/*  49: 71 */       if (response != null) {
/*  50: 72 */         response.received = false;
/*  51:    */       }
/*  52: 74 */       treeConnect(request, response);
/*  53: 75 */       if ((request == null) || ((response != null) && (response.received))) {
/*  54: 76 */         return;
/*  55:    */       }
/*  56: 78 */       if (!this.service.equals("A:")) {
/*  57: 79 */         switch (request.command)
/*  58:    */         {
/*  59:    */         case -94: 
/*  60:    */         case 4: 
/*  61:    */         case 45: 
/*  62:    */         case 46: 
/*  63:    */         case 47: 
/*  64:    */         case 113: 
/*  65:    */           break;
/*  66:    */         case 37: 
/*  67:    */         case 50: 
/*  68: 89 */           switch (((SmbComTransaction)request).subCommand & 0xFF)
/*  69:    */           {
/*  70:    */           case 0: 
/*  71:    */           case 16: 
/*  72:    */           case 35: 
/*  73:    */           case 38: 
/*  74:    */           case 83: 
/*  75:    */           case 84: 
/*  76:    */           case 104: 
/*  77:    */           case 215: 
/*  78:    */             break;
/*  79:    */           default: 
/*  80:100 */             throw new SmbException("Invalid operation for " + this.service + " service");
/*  81:    */           }
/*  82:    */           break;
/*  83:    */         default: 
/*  84:104 */           throw new SmbException("Invalid operation for " + this.service + " service" + request);
/*  85:    */         }
/*  86:    */       }
/*  87:107 */       request.tid = this.tid;
/*  88:108 */       if ((this.inDfs) && (!this.service.equals("IPC")) && (request.path != null) && (request.path.length() > 0))
/*  89:    */       {
/*  90:115 */         request.flags2 = 4096;
/*  91:116 */         request.path = ('\\' + this.session.transport().tconHostName + '\\' + this.share + request.path);
/*  92:    */       }
/*  93:    */       try
/*  94:    */       {
/*  95:119 */         this.session.send(request, response);
/*  96:    */       }
/*  97:    */       catch (SmbException se)
/*  98:    */       {
/*  99:121 */         if (se.getNtStatus() == -1073741623) {
/* 100:127 */           treeDisconnect(true);
/* 101:    */         }
/* 102:129 */         throw se;
/* 103:    */       }
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   void treeConnect(ServerMessageBlock andx, ServerMessageBlock andxResponse)
/* 108:    */     throws SmbException
/* 109:    */   {
/* 110:136 */     synchronized (this.session.transport())
/* 111:    */     {
/* 112:139 */       while (this.connectionState != 0)
/* 113:    */       {
/* 114:140 */         if ((this.connectionState == 2) || (this.connectionState == 3)) {
/* 115:141 */           return;
/* 116:    */         }
/* 117:    */         try
/* 118:    */         {
/* 119:143 */           this.session.transport.wait();
/* 120:    */         }
/* 121:    */         catch (InterruptedException ie)
/* 122:    */         {
/* 123:145 */           throw new SmbException(ie.getMessage(), ie);
/* 124:    */         }
/* 125:    */       }
/* 126:148 */       this.connectionState = 1;
/* 127:    */       try
/* 128:    */       {
/* 129:156 */         this.session.transport.connect();
/* 130:    */         
/* 131:158 */         String unc = "\\\\" + this.session.transport.tconHostName + '\\' + this.share;
/* 132:    */         
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:163 */         this.service = this.service0;
/* 137:169 */         if (LogStream.level >= 4) {
/* 138:170 */           SmbTransport.log.println("treeConnect: unc=" + unc + ",service=" + this.service);
/* 139:    */         }
/* 140:172 */         SmbComTreeConnectAndXResponse response = new SmbComTreeConnectAndXResponse(andxResponse);
/* 141:    */         
/* 142:174 */         SmbComTreeConnectAndX request = new SmbComTreeConnectAndX(this.session, unc, this.service, andx);
/* 143:    */         
/* 144:176 */         this.session.send(request, response);
/* 145:    */         
/* 146:178 */         this.tid = response.tid;
/* 147:179 */         this.service = response.service;
/* 148:180 */         this.inDfs = response.shareIsInDfs;
/* 149:181 */         this.tree_num = (tree_conn_counter++);
/* 150:    */         
/* 151:183 */         this.connectionState = 2;
/* 152:    */       }
/* 153:    */       catch (SmbException se)
/* 154:    */       {
/* 155:185 */         treeDisconnect(true);
/* 156:186 */         this.connectionState = 0;
/* 157:187 */         throw se;
/* 158:    */       }
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   void treeDisconnect(boolean inError)
/* 163:    */   {
/* 164:192 */     synchronized (this.session.transport())
/* 165:    */     {
/* 166:194 */       if (this.connectionState != 2) {
/* 167:195 */         return;
/* 168:    */       }
/* 169:196 */       this.connectionState = 3;
/* 170:198 */       if ((!inError) && (this.tid != 0)) {
/* 171:    */         try
/* 172:    */         {
/* 173:200 */           send(new SmbComTreeDisconnect(), null);
/* 174:    */         }
/* 175:    */         catch (SmbException se)
/* 176:    */         {
/* 177:202 */           if (LogStream.level > 1) {
/* 178:203 */             se.printStackTrace(SmbTransport.log);
/* 179:    */           }
/* 180:    */         }
/* 181:    */       }
/* 182:207 */       this.inDfs = false;
/* 183:208 */       this.inDomainDfs = false;
/* 184:    */       
/* 185:210 */       this.connectionState = 0;
/* 186:    */       
/* 187:212 */       this.session.transport.notifyAll();
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String toString()
/* 192:    */   {
/* 193:217 */     return "SmbTree[share=" + this.share + ",service=" + this.service + ",tid=" + this.tid + ",inDfs=" + this.inDfs + ",inDomainDfs=" + this.inDomainDfs + ",connectionState=" + this.connectionState + "]";
/* 194:    */   }
/* 195:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbTree
 * JD-Core Version:    0.7.0.1
 */