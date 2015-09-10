/*   1:    */ package javassist.util;
/*   2:    */ 
/*   3:    */ import com.sun.jdi.Bootstrap;
/*   4:    */ import com.sun.jdi.ReferenceType;
/*   5:    */ import com.sun.jdi.VirtualMachine;
/*   6:    */ import com.sun.jdi.VirtualMachineManager;
/*   7:    */ import com.sun.jdi.connect.AttachingConnector;
/*   8:    */ import com.sun.jdi.connect.Connector;
/*   9:    */ import com.sun.jdi.connect.Connector.Argument;
/*  10:    */ import com.sun.jdi.connect.IllegalConnectorArgumentsException;
/*  11:    */ import com.sun.jdi.event.Event;
/*  12:    */ import com.sun.jdi.event.EventIterator;
/*  13:    */ import com.sun.jdi.event.EventQueue;
/*  14:    */ import com.sun.jdi.event.EventSet;
/*  15:    */ import com.sun.jdi.event.MethodEntryEvent;
/*  16:    */ import com.sun.jdi.request.EventRequestManager;
/*  17:    */ import com.sun.jdi.request.MethodEntryRequest;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.io.PrintStream;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.Iterator;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import java.util.Map.Entry;
/*  25:    */ import java.util.Set;
/*  26:    */ 
/*  27:    */ public class HotSwapper
/*  28:    */ {
/*  29:    */   private VirtualMachine jvm;
/*  30:    */   private MethodEntryRequest request;
/*  31:    */   private Map newClassFiles;
/*  32:    */   private Trigger trigger;
/*  33:    */   private static final String HOST_NAME = "localhost";
/*  34: 84 */   private static final String TRIGGER_NAME = Trigger.class.getName();
/*  35:    */   
/*  36:    */   public HotSwapper(int port)
/*  37:    */     throws IOException, IllegalConnectorArgumentsException
/*  38:    */   {
/*  39: 94 */     this(Integer.toString(port));
/*  40:    */   }
/*  41:    */   
/*  42:    */   public HotSwapper(String port)
/*  43:    */     throws IOException, IllegalConnectorArgumentsException
/*  44:    */   {
/*  45:105 */     this.jvm = null;
/*  46:106 */     this.request = null;
/*  47:107 */     this.newClassFiles = null;
/*  48:108 */     this.trigger = new Trigger();
/*  49:109 */     AttachingConnector connector = (AttachingConnector)findConnector("com.sun.jdi.SocketAttach");
/*  50:    */     
/*  51:    */ 
/*  52:112 */     Map arguments = connector.defaultArguments();
/*  53:113 */     ((Connector.Argument)arguments.get("hostname")).setValue("localhost");
/*  54:114 */     ((Connector.Argument)arguments.get("port")).setValue(port);
/*  55:115 */     this.jvm = connector.attach(arguments);
/*  56:116 */     EventRequestManager manager = this.jvm.eventRequestManager();
/*  57:117 */     this.request = methodEntryRequests(manager, TRIGGER_NAME);
/*  58:    */   }
/*  59:    */   
/*  60:    */   private Connector findConnector(String connector)
/*  61:    */     throws IOException
/*  62:    */   {
/*  63:121 */     List connectors = Bootstrap.virtualMachineManager().allConnectors();
/*  64:122 */     Iterator iter = connectors.iterator();
/*  65:123 */     while (iter.hasNext())
/*  66:    */     {
/*  67:124 */       Connector con = (Connector)iter.next();
/*  68:125 */       if (con.name().equals(connector)) {
/*  69:126 */         return con;
/*  70:    */       }
/*  71:    */     }
/*  72:130 */     throw new IOException("Not found: " + connector);
/*  73:    */   }
/*  74:    */   
/*  75:    */   private static MethodEntryRequest methodEntryRequests(EventRequestManager manager, String classpattern)
/*  76:    */   {
/*  77:136 */     MethodEntryRequest mereq = manager.createMethodEntryRequest();
/*  78:137 */     mereq.addClassFilter(classpattern);
/*  79:138 */     mereq.setSuspendPolicy(1);
/*  80:139 */     return mereq;
/*  81:    */   }
/*  82:    */   
/*  83:    */   private void deleteEventRequest(EventRequestManager manager, MethodEntryRequest request)
/*  84:    */   {
/*  85:146 */     manager.deleteEventRequest(request);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void reload(String className, byte[] classFile)
/*  89:    */   {
/*  90:156 */     ReferenceType classtype = toRefType(className);
/*  91:157 */     Map map = new HashMap();
/*  92:158 */     map.put(classtype, classFile);
/*  93:159 */     reload2(map, className);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void reload(Map classFiles)
/*  97:    */   {
/*  98:171 */     Set set = classFiles.entrySet();
/*  99:172 */     Iterator it = set.iterator();
/* 100:173 */     Map map = new HashMap();
/* 101:174 */     String className = null;
/* 102:175 */     while (it.hasNext())
/* 103:    */     {
/* 104:176 */       Map.Entry e = (Map.Entry)it.next();
/* 105:177 */       className = (String)e.getKey();
/* 106:178 */       map.put(toRefType(className), e.getValue());
/* 107:    */     }
/* 108:181 */     if (className != null) {
/* 109:182 */       reload2(map, className + " etc.");
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   private ReferenceType toRefType(String className)
/* 114:    */   {
/* 115:186 */     List list = this.jvm.classesByName(className);
/* 116:187 */     if ((list == null) || (list.isEmpty())) {
/* 117:188 */       throw new RuntimeException("no such a class: " + className);
/* 118:    */     }
/* 119:190 */     return (ReferenceType)list.get(0);
/* 120:    */   }
/* 121:    */   
/* 122:    */   private void reload2(Map map, String msg)
/* 123:    */   {
/* 124:194 */     synchronized (this.trigger)
/* 125:    */     {
/* 126:195 */       startDaemon();
/* 127:196 */       this.newClassFiles = map;
/* 128:197 */       this.request.enable();
/* 129:198 */       this.trigger.doSwap();
/* 130:199 */       this.request.disable();
/* 131:200 */       Map ncf = this.newClassFiles;
/* 132:201 */       if (ncf != null)
/* 133:    */       {
/* 134:202 */         this.newClassFiles = null;
/* 135:203 */         throw new RuntimeException("failed to reload: " + msg);
/* 136:    */       }
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   private void startDaemon()
/* 141:    */   {
/* 142:209 */     new Thread()
/* 143:    */     {
/* 144:    */       private void errorMsg(Throwable e)
/* 145:    */       {
/* 146:211 */         System.err.print("Exception in thread \"HotSwap\" ");
/* 147:212 */         e.printStackTrace(System.err);
/* 148:    */       }
/* 149:    */       
/* 150:    */       public void run()
/* 151:    */       {
/* 152:216 */         EventSet events = null;
/* 153:    */         try
/* 154:    */         {
/* 155:218 */           events = HotSwapper.this.waitEvent();
/* 156:219 */           EventIterator iter = events.eventIterator();
/* 157:220 */           while (iter.hasNext())
/* 158:    */           {
/* 159:221 */             Event event = iter.nextEvent();
/* 160:222 */             if ((event instanceof MethodEntryEvent))
/* 161:    */             {
/* 162:223 */               HotSwapper.this.hotswap();
/* 163:224 */               break;
/* 164:    */             }
/* 165:    */           }
/* 166:    */         }
/* 167:    */         catch (Throwable e)
/* 168:    */         {
/* 169:229 */           errorMsg(e);
/* 170:    */         }
/* 171:    */         try
/* 172:    */         {
/* 173:232 */           if (events != null) {
/* 174:233 */             events.resume();
/* 175:    */           }
/* 176:    */         }
/* 177:    */         catch (Throwable e)
/* 178:    */         {
/* 179:236 */           errorMsg(e);
/* 180:    */         }
/* 181:    */       }
/* 182:    */     }.start();
/* 183:    */   }
/* 184:    */   
/* 185:    */   EventSet waitEvent()
/* 186:    */     throws InterruptedException
/* 187:    */   {
/* 188:243 */     EventQueue queue = this.jvm.eventQueue();
/* 189:244 */     return queue.remove();
/* 190:    */   }
/* 191:    */   
/* 192:    */   void hotswap()
/* 193:    */   {
/* 194:248 */     Map map = this.newClassFiles;
/* 195:249 */     this.jvm.redefineClasses(map);
/* 196:250 */     this.newClassFiles = null;
/* 197:    */   }
/* 198:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.util.HotSwapper
 * JD-Core Version:    0.7.0.1
 */