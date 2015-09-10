/*   1:    */ package org.apache.xalan.xsltc.trax;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.security.AccessController;
/*   8:    */ import java.security.PrivilegedAction;
/*   9:    */ import java.util.Properties;
/*  10:    */ import javax.xml.transform.Templates;
/*  11:    */ import javax.xml.transform.Transformer;
/*  12:    */ import javax.xml.transform.TransformerConfigurationException;
/*  13:    */ import javax.xml.transform.URIResolver;
/*  14:    */ import org.apache.xalan.xsltc.DOM;
/*  15:    */ import org.apache.xalan.xsltc.Translet;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  17:    */ import org.apache.xalan.xsltc.runtime.AbstractTranslet;
/*  18:    */ import org.apache.xalan.xsltc.runtime.Hashtable;
/*  19:    */ 
/*  20:    */ public final class TemplatesImpl
/*  21:    */   implements Templates, Serializable
/*  22:    */ {
/*  23:    */   static final long serialVersionUID = 673094361519270707L;
/*  24: 57 */   private static String ABSTRACT_TRANSLET = "org.apache.xalan.xsltc.runtime.AbstractTranslet";
/*  25: 63 */   private String _name = null;
/*  26: 69 */   private byte[][] _bytecodes = null;
/*  27: 75 */   private Class[] _class = null;
/*  28: 81 */   private int _transletIndex = -1;
/*  29: 86 */   private Hashtable _auxClasses = null;
/*  30:    */   private Properties _outputProperties;
/*  31:    */   private int _indentNumber;
/*  32:102 */   private transient URIResolver _uriResolver = null;
/*  33:111 */   private transient ThreadLocal _sdom = new ThreadLocal();
/*  34:117 */   private transient TransformerFactoryImpl _tfactory = null;
/*  35:    */   
/*  36:    */   static final class TransletClassLoader
/*  37:    */     extends ClassLoader
/*  38:    */   {
/*  39:    */     TransletClassLoader(ClassLoader parent)
/*  40:    */     {
/*  41:121 */       super();
/*  42:    */     }
/*  43:    */     
/*  44:    */     Class defineClass(byte[] b)
/*  45:    */     {
/*  46:128 */       return defineClass(null, b, 0, b.length);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected TemplatesImpl(byte[][] bytecodes, String transletName, Properties outputProperties, int indentNumber, TransformerFactoryImpl tfactory)
/*  51:    */   {
/*  52:142 */     this._bytecodes = bytecodes;
/*  53:143 */     this._name = transletName;
/*  54:144 */     this._outputProperties = outputProperties;
/*  55:145 */     this._indentNumber = indentNumber;
/*  56:146 */     this._tfactory = tfactory;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected TemplatesImpl(Class[] transletClasses, String transletName, Properties outputProperties, int indentNumber, TransformerFactoryImpl tfactory)
/*  60:    */   {
/*  61:156 */     this._class = transletClasses;
/*  62:157 */     this._name = transletName;
/*  63:158 */     this._transletIndex = 0;
/*  64:159 */     this._outputProperties = outputProperties;
/*  65:160 */     this._indentNumber = indentNumber;
/*  66:161 */     this._tfactory = tfactory;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public TemplatesImpl() {}
/*  70:    */   
/*  71:    */   private void readObject(ObjectInputStream is)
/*  72:    */     throws IOException, ClassNotFoundException
/*  73:    */   {
/*  74:182 */     is.defaultReadObject();
/*  75:183 */     if (is.readBoolean()) {
/*  76:184 */       this._uriResolver = ((URIResolver)is.readObject());
/*  77:    */     }
/*  78:187 */     this._tfactory = new TransformerFactoryImpl();
/*  79:    */   }
/*  80:    */   
/*  81:    */   private void writeObject(ObjectOutputStream os)
/*  82:    */     throws IOException, ClassNotFoundException
/*  83:    */   {
/*  84:198 */     os.defaultWriteObject();
/*  85:199 */     if ((this._uriResolver instanceof Serializable))
/*  86:    */     {
/*  87:200 */       os.writeBoolean(true);
/*  88:201 */       os.writeObject((Serializable)this._uriResolver);
/*  89:    */     }
/*  90:    */     else
/*  91:    */     {
/*  92:204 */       os.writeBoolean(false);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public synchronized void setURIResolver(URIResolver resolver)
/*  97:    */   {
/*  98:213 */     this._uriResolver = resolver;
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected synchronized void setTransletBytecodes(byte[][] bytecodes)
/* 102:    */   {
/* 103:221 */     this._bytecodes = bytecodes;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public synchronized byte[][] getTransletBytecodes()
/* 107:    */   {
/* 108:228 */     return this._bytecodes;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public synchronized Class[] getTransletClasses()
/* 112:    */   {
/* 113:    */     try
/* 114:    */     {
/* 115:236 */       if (this._class == null) {
/* 116:236 */         defineTransletClasses();
/* 117:    */       }
/* 118:    */     }
/* 119:    */     catch (TransformerConfigurationException e) {}
/* 120:241 */     return this._class;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public synchronized int getTransletIndex()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:249 */       if (this._class == null) {
/* 128:249 */         defineTransletClasses();
/* 129:    */       }
/* 130:    */     }
/* 131:    */     catch (TransformerConfigurationException e) {}
/* 132:254 */     return this._transletIndex;
/* 133:    */   }
/* 134:    */   
/* 135:    */   protected synchronized void setTransletName(String name)
/* 136:    */   {
/* 137:261 */     this._name = name;
/* 138:    */   }
/* 139:    */   
/* 140:    */   protected synchronized String getTransletName()
/* 141:    */   {
/* 142:268 */     return this._name;
/* 143:    */   }
/* 144:    */   
/* 145:    */   private void defineTransletClasses()
/* 146:    */     throws TransformerConfigurationException
/* 147:    */   {
/* 148:278 */     if (this._bytecodes == null)
/* 149:    */     {
/* 150:279 */       ErrorMsg err = new ErrorMsg("NO_TRANSLET_CLASS_ERR");
/* 151:280 */       throw new TransformerConfigurationException(err.toString());
/* 152:    */     }
/* 153:283 */     TransletClassLoader loader = (TransletClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/* 154:    */     {
/* 155:    */       public Object run()
/* 156:    */       {
/* 157:286 */         return new TemplatesImpl.TransletClassLoader(ObjectFactory.findClassLoader());
/* 158:    */       }
/* 159:    */     });
/* 160:    */     try
/* 161:    */     {
/* 162:291 */       int classCount = this._bytecodes.length;
/* 163:292 */       this._class = new Class[classCount];
/* 164:294 */       if (classCount > 1) {
/* 165:295 */         this._auxClasses = new Hashtable();
/* 166:    */       }
/* 167:298 */       for (int i = 0; i < classCount; i++)
/* 168:    */       {
/* 169:299 */         this._class[i] = loader.defineClass(this._bytecodes[i]);
/* 170:300 */         Class superClass = this._class[i].getSuperclass();
/* 171:303 */         if (superClass.getName().equals(ABSTRACT_TRANSLET)) {
/* 172:304 */           this._transletIndex = i;
/* 173:    */         } else {
/* 174:307 */           this._auxClasses.put(this._class[i].getName(), this._class[i]);
/* 175:    */         }
/* 176:    */       }
/* 177:311 */       if (this._transletIndex < 0)
/* 178:    */       {
/* 179:312 */         ErrorMsg err = new ErrorMsg("NO_MAIN_TRANSLET_ERR", this._name);
/* 180:313 */         throw new TransformerConfigurationException(err.toString());
/* 181:    */       }
/* 182:    */     }
/* 183:    */     catch (ClassFormatError e)
/* 184:    */     {
/* 185:317 */       ErrorMsg err = new ErrorMsg("TRANSLET_CLASS_ERR", this._name);
/* 186:318 */       throw new TransformerConfigurationException(err.toString());
/* 187:    */     }
/* 188:    */     catch (LinkageError e)
/* 189:    */     {
/* 190:321 */       ErrorMsg err = new ErrorMsg("TRANSLET_OBJECT_ERR", this._name);
/* 191:322 */       throw new TransformerConfigurationException(err.toString());
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   private Translet getTransletInstance()
/* 196:    */     throws TransformerConfigurationException
/* 197:    */   {
/* 198:    */     try
/* 199:    */     {
/* 200:334 */       if (this._name == null) {
/* 201:334 */         return null;
/* 202:    */       }
/* 203:336 */       if (this._class == null) {
/* 204:336 */         defineTransletClasses();
/* 205:    */       }
/* 206:340 */       AbstractTranslet translet = (AbstractTranslet)this._class[this._transletIndex].newInstance();
/* 207:341 */       translet.postInitialization();
/* 208:342 */       translet.setTemplates(this);
/* 209:343 */       if (this._auxClasses != null) {
/* 210:344 */         translet.setAuxiliaryClasses(this._auxClasses);
/* 211:    */       }
/* 212:347 */       return translet;
/* 213:    */     }
/* 214:    */     catch (InstantiationException e)
/* 215:    */     {
/* 216:350 */       ErrorMsg err = new ErrorMsg("TRANSLET_OBJECT_ERR", this._name);
/* 217:351 */       throw new TransformerConfigurationException(err.toString());
/* 218:    */     }
/* 219:    */     catch (IllegalAccessException e)
/* 220:    */     {
/* 221:354 */       ErrorMsg err = new ErrorMsg("TRANSLET_OBJECT_ERR", this._name);
/* 222:355 */       throw new TransformerConfigurationException(err.toString());
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   public synchronized Transformer newTransformer()
/* 227:    */     throws TransformerConfigurationException
/* 228:    */   {
/* 229:369 */     TransformerImpl transformer = new TransformerImpl(getTransletInstance(), this._outputProperties, this._indentNumber, this._tfactory);
/* 230:372 */     if (this._uriResolver != null) {
/* 231:373 */       transformer.setURIResolver(this._uriResolver);
/* 232:    */     }
/* 233:376 */     if (this._tfactory.getFeature("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 234:377 */       transformer.setSecureProcessing(true);
/* 235:    */     }
/* 236:379 */     return transformer;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public synchronized Properties getOutputProperties()
/* 240:    */   {
/* 241:    */     try
/* 242:    */     {
/* 243:390 */       return newTransformer().getOutputProperties();
/* 244:    */     }
/* 245:    */     catch (TransformerConfigurationException e) {}
/* 246:393 */     return null;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public DOM getStylesheetDOM()
/* 250:    */   {
/* 251:401 */     return (DOM)this._sdom.get();
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setStylesheetDOM(DOM sdom)
/* 255:    */   {
/* 256:408 */     this._sdom.set(sdom);
/* 257:    */   }
/* 258:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.trax.TemplatesImpl
 * JD-Core Version:    0.7.0.1
 */