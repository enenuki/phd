/*   1:    */ package javassist.tools.rmi;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.InvalidClassException;
/*   7:    */ import java.io.NotSerializableException;
/*   8:    */ import java.io.ObjectInputStream;
/*   9:    */ import java.io.ObjectOutputStream;
/*  10:    */ import java.io.OutputStream;
/*  11:    */ import java.lang.reflect.Method;
/*  12:    */ import java.util.Hashtable;
/*  13:    */ import java.util.Vector;
/*  14:    */ import javassist.CannotCompileException;
/*  15:    */ import javassist.ClassPool;
/*  16:    */ import javassist.NotFoundException;
/*  17:    */ import javassist.tools.web.BadHttpRequest;
/*  18:    */ import javassist.tools.web.Webserver;
/*  19:    */ 
/*  20:    */ public class AppletServer
/*  21:    */   extends Webserver
/*  22:    */ {
/*  23:    */   private StubGenerator stubGen;
/*  24:    */   private Hashtable exportedNames;
/*  25:    */   private Vector exportedObjects;
/*  26: 42 */   private static final byte[] okHeader = "HTTP/1.0 200 OK\r\n\r\n".getBytes();
/*  27:    */   
/*  28:    */   public AppletServer(String port)
/*  29:    */     throws IOException, NotFoundException, CannotCompileException
/*  30:    */   {
/*  31: 53 */     this(Integer.parseInt(port));
/*  32:    */   }
/*  33:    */   
/*  34:    */   public AppletServer(int port)
/*  35:    */     throws IOException, NotFoundException, CannotCompileException
/*  36:    */   {
/*  37: 64 */     this(ClassPool.getDefault(), new StubGenerator(), port);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public AppletServer(int port, ClassPool src)
/*  41:    */     throws IOException, NotFoundException, CannotCompileException
/*  42:    */   {
/*  43: 76 */     this(new ClassPool(src), new StubGenerator(), port);
/*  44:    */   }
/*  45:    */   
/*  46:    */   private AppletServer(ClassPool loader, StubGenerator gen, int port)
/*  47:    */     throws IOException, NotFoundException, CannotCompileException
/*  48:    */   {
/*  49: 82 */     super(port);
/*  50: 83 */     this.exportedNames = new Hashtable();
/*  51: 84 */     this.exportedObjects = new Vector();
/*  52: 85 */     this.stubGen = gen;
/*  53: 86 */     addTranslator(loader, gen);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void run()
/*  57:    */   {
/*  58: 93 */     super.run();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public synchronized int exportObject(String name, Object obj)
/*  62:    */     throws CannotCompileException
/*  63:    */   {
/*  64:111 */     Class clazz = obj.getClass();
/*  65:112 */     ExportedObject eo = new ExportedObject();
/*  66:113 */     eo.object = obj;
/*  67:114 */     eo.methods = clazz.getMethods();
/*  68:115 */     this.exportedObjects.addElement(eo);
/*  69:116 */     eo.identifier = (this.exportedObjects.size() - 1);
/*  70:117 */     if (name != null) {
/*  71:118 */       this.exportedNames.put(name, eo);
/*  72:    */     }
/*  73:    */     try
/*  74:    */     {
/*  75:121 */       this.stubGen.makeProxyClass(clazz);
/*  76:    */     }
/*  77:    */     catch (NotFoundException e)
/*  78:    */     {
/*  79:124 */       throw new CannotCompileException(e);
/*  80:    */     }
/*  81:127 */     return eo.identifier;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void doReply(InputStream in, OutputStream out, String cmd)
/*  85:    */     throws IOException, BadHttpRequest
/*  86:    */   {
/*  87:136 */     if (cmd.startsWith("POST /rmi ")) {
/*  88:137 */       processRMI(in, out);
/*  89:138 */     } else if (cmd.startsWith("POST /lookup ")) {
/*  90:139 */       lookupName(cmd, in, out);
/*  91:    */     } else {
/*  92:141 */       super.doReply(in, out, cmd);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   private void processRMI(InputStream ins, OutputStream outs)
/*  97:    */     throws IOException
/*  98:    */   {
/*  99:147 */     ObjectInputStream in = new ObjectInputStream(ins);
/* 100:    */     
/* 101:149 */     int objectId = in.readInt();
/* 102:150 */     int methodId = in.readInt();
/* 103:151 */     Exception err = null;
/* 104:152 */     Object rvalue = null;
/* 105:    */     try
/* 106:    */     {
/* 107:154 */       ExportedObject eo = (ExportedObject)this.exportedObjects.elementAt(objectId);
/* 108:    */       
/* 109:156 */       Object[] args = readParameters(in);
/* 110:157 */       rvalue = convertRvalue(eo.methods[methodId].invoke(eo.object, args));
/* 111:    */     }
/* 112:    */     catch (Exception e)
/* 113:    */     {
/* 114:161 */       err = e;
/* 115:162 */       logging2(e.toString());
/* 116:    */     }
/* 117:165 */     outs.write(okHeader);
/* 118:166 */     ObjectOutputStream out = new ObjectOutputStream(outs);
/* 119:167 */     if (err != null)
/* 120:    */     {
/* 121:168 */       out.writeBoolean(false);
/* 122:169 */       out.writeUTF(err.toString());
/* 123:    */     }
/* 124:    */     else
/* 125:    */     {
/* 126:    */       try
/* 127:    */       {
/* 128:173 */         out.writeBoolean(true);
/* 129:174 */         out.writeObject(rvalue);
/* 130:    */       }
/* 131:    */       catch (NotSerializableException e)
/* 132:    */       {
/* 133:177 */         logging2(e.toString());
/* 134:    */       }
/* 135:    */       catch (InvalidClassException e)
/* 136:    */       {
/* 137:180 */         logging2(e.toString());
/* 138:    */       }
/* 139:    */     }
/* 140:183 */     out.flush();
/* 141:184 */     out.close();
/* 142:185 */     in.close();
/* 143:    */   }
/* 144:    */   
/* 145:    */   private Object[] readParameters(ObjectInputStream in)
/* 146:    */     throws IOException, ClassNotFoundException
/* 147:    */   {
/* 148:191 */     int n = in.readInt();
/* 149:192 */     Object[] args = new Object[n];
/* 150:193 */     for (int i = 0; i < n; i++)
/* 151:    */     {
/* 152:194 */       Object a = in.readObject();
/* 153:195 */       if ((a instanceof RemoteRef))
/* 154:    */       {
/* 155:196 */         RemoteRef ref = (RemoteRef)a;
/* 156:197 */         ExportedObject eo = (ExportedObject)this.exportedObjects.elementAt(ref.oid);
/* 157:    */         
/* 158:199 */         a = eo.object;
/* 159:    */       }
/* 160:202 */       args[i] = a;
/* 161:    */     }
/* 162:205 */     return args;
/* 163:    */   }
/* 164:    */   
/* 165:    */   private Object convertRvalue(Object rvalue)
/* 166:    */     throws CannotCompileException
/* 167:    */   {
/* 168:211 */     if (rvalue == null) {
/* 169:212 */       return null;
/* 170:    */     }
/* 171:214 */     String classname = rvalue.getClass().getName();
/* 172:215 */     if (this.stubGen.isProxyClass(classname)) {
/* 173:216 */       return new RemoteRef(exportObject(null, rvalue), classname);
/* 174:    */     }
/* 175:218 */     return rvalue;
/* 176:    */   }
/* 177:    */   
/* 178:    */   private void lookupName(String cmd, InputStream ins, OutputStream outs)
/* 179:    */     throws IOException
/* 180:    */   {
/* 181:224 */     ObjectInputStream in = new ObjectInputStream(ins);
/* 182:225 */     String name = DataInputStream.readUTF(in);
/* 183:226 */     ExportedObject found = (ExportedObject)this.exportedNames.get(name);
/* 184:227 */     outs.write(okHeader);
/* 185:228 */     ObjectOutputStream out = new ObjectOutputStream(outs);
/* 186:229 */     if (found == null)
/* 187:    */     {
/* 188:230 */       logging2(name + "not found.");
/* 189:231 */       out.writeInt(-1);
/* 190:232 */       out.writeUTF("error");
/* 191:    */     }
/* 192:    */     else
/* 193:    */     {
/* 194:235 */       logging2(name);
/* 195:236 */       out.writeInt(found.identifier);
/* 196:237 */       out.writeUTF(found.object.getClass().getName());
/* 197:    */     }
/* 198:240 */     out.flush();
/* 199:241 */     out.close();
/* 200:242 */     in.close();
/* 201:    */   }
/* 202:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.rmi.AppletServer
 * JD-Core Version:    0.7.0.1
 */