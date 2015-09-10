/*    1:     */ package org.apache.bcel.verifier.statics;
/*    2:     */ 
/*    3:     */ import java.util.HashMap;
/*    4:     */ import java.util.HashSet;
/*    5:     */ import org.apache.bcel.Constants;
/*    6:     */ import org.apache.bcel.Repository;
/*    7:     */ import org.apache.bcel.classfile.AccessFlags;
/*    8:     */ import org.apache.bcel.classfile.Attribute;
/*    9:     */ import org.apache.bcel.classfile.Code;
/*   10:     */ import org.apache.bcel.classfile.CodeException;
/*   11:     */ import org.apache.bcel.classfile.Constant;
/*   12:     */ import org.apache.bcel.classfile.ConstantCP;
/*   13:     */ import org.apache.bcel.classfile.ConstantClass;
/*   14:     */ import org.apache.bcel.classfile.ConstantDouble;
/*   15:     */ import org.apache.bcel.classfile.ConstantFieldref;
/*   16:     */ import org.apache.bcel.classfile.ConstantFloat;
/*   17:     */ import org.apache.bcel.classfile.ConstantInteger;
/*   18:     */ import org.apache.bcel.classfile.ConstantInterfaceMethodref;
/*   19:     */ import org.apache.bcel.classfile.ConstantLong;
/*   20:     */ import org.apache.bcel.classfile.ConstantMethodref;
/*   21:     */ import org.apache.bcel.classfile.ConstantNameAndType;
/*   22:     */ import org.apache.bcel.classfile.ConstantPool;
/*   23:     */ import org.apache.bcel.classfile.ConstantString;
/*   24:     */ import org.apache.bcel.classfile.ConstantUtf8;
/*   25:     */ import org.apache.bcel.classfile.ConstantValue;
/*   26:     */ import org.apache.bcel.classfile.Deprecated;
/*   27:     */ import org.apache.bcel.classfile.DescendingVisitor;
/*   28:     */ import org.apache.bcel.classfile.EmptyVisitor;
/*   29:     */ import org.apache.bcel.classfile.ExceptionTable;
/*   30:     */ import org.apache.bcel.classfile.Field;
/*   31:     */ import org.apache.bcel.classfile.FieldOrMethod;
/*   32:     */ import org.apache.bcel.classfile.InnerClass;
/*   33:     */ import org.apache.bcel.classfile.InnerClasses;
/*   34:     */ import org.apache.bcel.classfile.JavaClass;
/*   35:     */ import org.apache.bcel.classfile.LineNumber;
/*   36:     */ import org.apache.bcel.classfile.LineNumberTable;
/*   37:     */ import org.apache.bcel.classfile.LocalVariable;
/*   38:     */ import org.apache.bcel.classfile.LocalVariableTable;
/*   39:     */ import org.apache.bcel.classfile.Method;
/*   40:     */ import org.apache.bcel.classfile.Node;
/*   41:     */ import org.apache.bcel.classfile.SourceFile;
/*   42:     */ import org.apache.bcel.classfile.Synthetic;
/*   43:     */ import org.apache.bcel.classfile.Unknown;
/*   44:     */ import org.apache.bcel.classfile.Visitor;
/*   45:     */ import org.apache.bcel.generic.ArrayType;
/*   46:     */ import org.apache.bcel.generic.ConstantPoolGen;
/*   47:     */ import org.apache.bcel.generic.ObjectType;
/*   48:     */ import org.apache.bcel.generic.Type;
/*   49:     */ import org.apache.bcel.verifier.PassVerifier;
/*   50:     */ import org.apache.bcel.verifier.VerificationResult;
/*   51:     */ import org.apache.bcel.verifier.Verifier;
/*   52:     */ import org.apache.bcel.verifier.VerifierFactory;
/*   53:     */ import org.apache.bcel.verifier.exc.AssertionViolatedException;
/*   54:     */ import org.apache.bcel.verifier.exc.ClassConstraintException;
/*   55:     */ import org.apache.bcel.verifier.exc.LocalVariableInfoInconsistentException;
/*   56:     */ import org.apache.bcel.verifier.exc.VerifierConstraintViolatedException;
/*   57:     */ 
/*   58:     */ public final class Pass2Verifier
/*   59:     */   extends PassVerifier
/*   60:     */   implements Constants
/*   61:     */ {
/*   62:     */   private LocalVariablesInfo[] localVariablesInfos;
/*   63:     */   private Verifier myOwner;
/*   64:     */   
/*   65:     */   public Pass2Verifier(Verifier owner)
/*   66:     */   {
/*   67:  99 */     this.myOwner = owner;
/*   68:     */   }
/*   69:     */   
/*   70:     */   public LocalVariablesInfo getLocalVariablesInfo(int method_nr)
/*   71:     */   {
/*   72: 112 */     if (verify() != VerificationResult.VR_OK) {
/*   73: 112 */       return null;
/*   74:     */     }
/*   75: 113 */     if ((method_nr < 0) || (method_nr >= this.localVariablesInfos.length)) {
/*   76: 114 */       throw new AssertionViolatedException("Method number out of range.");
/*   77:     */     }
/*   78: 116 */     return this.localVariablesInfos[method_nr];
/*   79:     */   }
/*   80:     */   
/*   81:     */   public VerificationResult do_verify()
/*   82:     */   {
/*   83: 142 */     VerificationResult vr1 = this.myOwner.doPass1();
/*   84: 143 */     if (vr1.equals(VerificationResult.VR_OK))
/*   85:     */     {
/*   86: 147 */       this.localVariablesInfos = new LocalVariablesInfo[Repository.lookupClass(this.myOwner.getClassName()).getMethods().length];
/*   87:     */       
/*   88: 149 */       VerificationResult vr = VerificationResult.VR_OK;
/*   89:     */       try
/*   90:     */       {
/*   91: 151 */         constant_pool_entries_satisfy_static_constraints();
/*   92: 152 */         field_and_method_refs_are_valid();
/*   93: 153 */         every_class_has_an_accessible_superclass();
/*   94: 154 */         final_methods_are_not_overridden();
/*   95:     */       }
/*   96:     */       catch (ClassConstraintException cce)
/*   97:     */       {
/*   98: 157 */         vr = new VerificationResult(2, cce.getMessage());
/*   99:     */       }
/*  100: 159 */       return vr;
/*  101:     */     }
/*  102: 162 */     return VerificationResult.VR_NOTYET;
/*  103:     */   }
/*  104:     */   
/*  105:     */   private void every_class_has_an_accessible_superclass()
/*  106:     */   {
/*  107: 179 */     HashSet hs = new HashSet();
/*  108: 180 */     JavaClass jc = Repository.lookupClass(this.myOwner.getClassName());
/*  109: 181 */     int supidx = -1;
/*  110: 183 */     while (supidx != 0)
/*  111:     */     {
/*  112: 184 */       supidx = jc.getSuperclassNameIndex();
/*  113: 186 */       if (supidx == 0)
/*  114:     */       {
/*  115: 187 */         if (jc != Repository.lookupClass(Type.OBJECT.getClassName())) {
/*  116: 188 */           throw new ClassConstraintException("Superclass of '" + jc.getClassName() + "' missing but not " + Type.OBJECT.getClassName() + " itself!");
/*  117:     */         }
/*  118:     */       }
/*  119:     */       else
/*  120:     */       {
/*  121: 192 */         String supername = jc.getSuperclassName();
/*  122: 193 */         if (!hs.add(supername)) {
/*  123: 194 */           throw new ClassConstraintException("Circular superclass hierarchy detected.");
/*  124:     */         }
/*  125: 196 */         Verifier v = VerifierFactory.getVerifier(supername);
/*  126: 197 */         VerificationResult vr = v.doPass1();
/*  127: 199 */         if (vr != VerificationResult.VR_OK) {
/*  128: 200 */           throw new ClassConstraintException("Could not load in ancestor class '" + supername + "'.");
/*  129:     */         }
/*  130: 202 */         jc = Repository.lookupClass(supername);
/*  131: 204 */         if (jc.isFinal()) {
/*  132: 205 */           throw new ClassConstraintException("Ancestor class '" + supername + "' has the FINAL access modifier and must therefore not be subclassed.");
/*  133:     */         }
/*  134:     */       }
/*  135:     */     }
/*  136:     */   }
/*  137:     */   
/*  138:     */   private void final_methods_are_not_overridden()
/*  139:     */   {
/*  140: 223 */     HashMap hashmap = new HashMap();
/*  141: 224 */     JavaClass jc = Repository.lookupClass(this.myOwner.getClassName());
/*  142:     */     
/*  143: 226 */     int supidx = -1;
/*  144: 227 */     while (supidx != 0)
/*  145:     */     {
/*  146: 228 */       supidx = jc.getSuperclassNameIndex();
/*  147:     */       
/*  148: 230 */       ConstantPoolGen cpg = new ConstantPoolGen(jc.getConstantPool());
/*  149: 231 */       Method[] methods = jc.getMethods();
/*  150: 232 */       for (int i = 0; i < methods.length; i++)
/*  151:     */       {
/*  152: 233 */         String name_and_sig = methods[i].getName() + methods[i].getSignature();
/*  153: 235 */         if (hashmap.containsKey(name_and_sig))
/*  154:     */         {
/*  155: 236 */           if (methods[i].isFinal()) {
/*  156: 237 */             throw new ClassConstraintException("Method '" + name_and_sig + "' in class '" + hashmap.get(name_and_sig) + "' overrides the final (not-overridable) definition in class '" + jc.getClassName() + "'.");
/*  157:     */           }
/*  158: 240 */           if (!methods[i].isStatic()) {
/*  159: 241 */             hashmap.put(name_and_sig, jc.getClassName());
/*  160:     */           }
/*  161:     */         }
/*  162: 246 */         else if (!methods[i].isStatic())
/*  163:     */         {
/*  164: 247 */           hashmap.put(name_and_sig, jc.getClassName());
/*  165:     */         }
/*  166:     */       }
/*  167: 252 */       jc = Repository.lookupClass(jc.getSuperclassName());
/*  168:     */     }
/*  169:     */   }
/*  170:     */   
/*  171:     */   private void constant_pool_entries_satisfy_static_constraints()
/*  172:     */   {
/*  173: 267 */     JavaClass jc = Repository.lookupClass(this.myOwner.getClassName());
/*  174: 268 */     new CPESSC_Visitor(jc, null);
/*  175:     */   }
/*  176:     */   
/*  177:     */   private class CPESSC_Visitor
/*  178:     */     extends EmptyVisitor
/*  179:     */     implements Visitor
/*  180:     */   {
/*  181:     */     private Class CONST_Class;
/*  182:     */     private Class CONST_Fieldref;
/*  183:     */     private Class CONST_Methodref;
/*  184:     */     private Class CONST_InterfaceMethodref;
/*  185:     */     private Class CONST_String;
/*  186:     */     private Class CONST_Integer;
/*  187:     */     private Class CONST_Float;
/*  188:     */     private Class CONST_Long;
/*  189:     */     private Class CONST_Double;
/*  190:     */     private Class CONST_NameAndType;
/*  191:     */     private Class CONST_Utf8;
/*  192:     */     private final JavaClass jc;
/*  193:     */     private final ConstantPool cp;
/*  194:     */     private final int cplen;
/*  195:     */     private DescendingVisitor carrier;
/*  196:     */     
/*  197:     */     CPESSC_Visitor(JavaClass x1, Pass2Verifier.1 x2)
/*  198:     */     {
/*  199: 278 */       this(x1);
/*  200:     */     }
/*  201:     */     
/*  202: 296 */     private HashSet field_names = new HashSet();
/*  203: 297 */     private HashSet field_names_and_desc = new HashSet();
/*  204: 298 */     private HashSet method_names_and_desc = new HashSet();
/*  205:     */     
/*  206:     */     private CPESSC_Visitor(JavaClass _jc)
/*  207:     */     {
/*  208: 301 */       this.jc = _jc;
/*  209: 302 */       this.cp = _jc.getConstantPool();
/*  210: 303 */       this.cplen = this.cp.getLength();
/*  211:     */       
/*  212: 305 */       this.CONST_Class = ConstantClass.class;
/*  213: 306 */       this.CONST_Fieldref = ConstantFieldref.class;
/*  214: 307 */       this.CONST_Methodref = ConstantMethodref.class;
/*  215: 308 */       this.CONST_InterfaceMethodref = ConstantInterfaceMethodref.class;
/*  216: 309 */       this.CONST_String = ConstantString.class;
/*  217: 310 */       this.CONST_Integer = ConstantInteger.class;
/*  218: 311 */       this.CONST_Float = ConstantFloat.class;
/*  219: 312 */       this.CONST_Long = ConstantLong.class;
/*  220: 313 */       this.CONST_Double = ConstantDouble.class;
/*  221: 314 */       this.CONST_NameAndType = ConstantNameAndType.class;
/*  222: 315 */       this.CONST_Utf8 = ConstantUtf8.class;
/*  223:     */       
/*  224: 317 */       this.carrier = new DescendingVisitor(_jc, this);
/*  225: 318 */       this.carrier.visit();
/*  226:     */     }
/*  227:     */     
/*  228:     */     private void checkIndex(Node referrer, int index, Class shouldbe)
/*  229:     */     {
/*  230: 322 */       if ((index < 0) || (index >= this.cplen)) {
/*  231: 323 */         throw new ClassConstraintException("Invalid index '" + index + "' used by '" + Pass2Verifier.tostring(referrer) + "'.");
/*  232:     */       }
/*  233: 325 */       Constant c = this.cp.getConstant(index);
/*  234: 326 */       if (!shouldbe.isInstance(c))
/*  235:     */       {
/*  236: 327 */         String isnot = shouldbe.toString().substring(shouldbe.toString().lastIndexOf(".") + 1);
/*  237: 328 */         throw new ClassCastException("Illegal constant '" + Pass2Verifier.tostring(c) + "' at index '" + index + "'. '" + Pass2Verifier.tostring(referrer) + "' expects a '" + shouldbe + "'.");
/*  238:     */       }
/*  239:     */     }
/*  240:     */     
/*  241:     */     public void visitJavaClass(JavaClass obj)
/*  242:     */     {
/*  243: 335 */       Attribute[] atts = obj.getAttributes();
/*  244: 336 */       boolean foundSourceFile = false;
/*  245: 337 */       boolean foundInnerClasses = false;
/*  246:     */       
/*  247:     */ 
/*  248:     */ 
/*  249: 341 */       boolean hasInnerClass = new Pass2Verifier.InnerClassDetector(Pass2Verifier.this, this.jc).innerClassReferenced();
/*  250: 343 */       for (int i = 0; i < atts.length; i++)
/*  251:     */       {
/*  252: 344 */         if ((!(atts[i] instanceof SourceFile)) && (!(atts[i] instanceof Deprecated)) && (!(atts[i] instanceof InnerClasses)) && (!(atts[i] instanceof Synthetic))) {
/*  253: 348 */           Pass2Verifier.this.addMessage("Attribute '" + Pass2Verifier.tostring(atts[i]) + "' as an attribute of the ClassFile structure '" + Pass2Verifier.tostring(obj) + "' is unknown and will therefore be ignored.");
/*  254:     */         }
/*  255: 351 */         if ((atts[i] instanceof SourceFile)) {
/*  256: 352 */           if (!foundSourceFile) {
/*  257: 352 */             foundSourceFile = true;
/*  258:     */           } else {
/*  259: 353 */             throw new ClassConstraintException("A ClassFile structure (like '" + Pass2Verifier.tostring(obj) + "') may have no more than one SourceFile attribute.");
/*  260:     */           }
/*  261:     */         }
/*  262: 356 */         if ((atts[i] instanceof InnerClasses))
/*  263:     */         {
/*  264: 357 */           if (!foundInnerClasses) {
/*  265: 357 */             foundInnerClasses = true;
/*  266: 359 */           } else if (hasInnerClass) {
/*  267: 360 */             throw new ClassConstraintException("A Classfile structure (like '" + Pass2Verifier.tostring(obj) + "') must have exactly one InnerClasses attribute if at least one Inner Class is referenced (which is the case). More than one InnerClasses attribute was found.");
/*  268:     */           }
/*  269: 363 */           if (!hasInnerClass) {
/*  270: 364 */             Pass2Verifier.this.addMessage("No referenced Inner Class found, but InnerClasses attribute '" + Pass2Verifier.tostring(atts[i]) + "' found. Strongly suggest removal of that attribute.");
/*  271:     */           }
/*  272:     */         }
/*  273:     */       }
/*  274: 369 */       if ((hasInnerClass) && (!foundInnerClasses)) {
/*  275: 374 */         Pass2Verifier.this.addMessage("A Classfile structure (like '" + Pass2Verifier.tostring(obj) + "') must have exactly one InnerClasses attribute if at least one Inner Class is referenced (which is the case). No InnerClasses attribute was found.");
/*  276:     */       }
/*  277:     */     }
/*  278:     */     
/*  279:     */     public void visitConstantClass(ConstantClass obj)
/*  280:     */     {
/*  281: 381 */       if (obj.getTag() != 7) {
/*  282: 382 */         throw new ClassConstraintException("Wrong constant tag in '" + Pass2Verifier.tostring(obj) + "'.");
/*  283:     */       }
/*  284: 384 */       checkIndex(obj, obj.getNameIndex(), this.CONST_Utf8);
/*  285:     */     }
/*  286:     */     
/*  287:     */     public void visitConstantFieldref(ConstantFieldref obj)
/*  288:     */     {
/*  289: 388 */       if (obj.getTag() != 9) {
/*  290: 389 */         throw new ClassConstraintException("Wrong constant tag in '" + Pass2Verifier.tostring(obj) + "'.");
/*  291:     */       }
/*  292: 391 */       checkIndex(obj, obj.getClassIndex(), this.CONST_Class);
/*  293: 392 */       checkIndex(obj, obj.getNameAndTypeIndex(), this.CONST_NameAndType);
/*  294:     */     }
/*  295:     */     
/*  296:     */     public void visitConstantMethodref(ConstantMethodref obj)
/*  297:     */     {
/*  298: 395 */       if (obj.getTag() != 10) {
/*  299: 396 */         throw new ClassConstraintException("Wrong constant tag in '" + Pass2Verifier.tostring(obj) + "'.");
/*  300:     */       }
/*  301: 398 */       checkIndex(obj, obj.getClassIndex(), this.CONST_Class);
/*  302: 399 */       checkIndex(obj, obj.getNameAndTypeIndex(), this.CONST_NameAndType);
/*  303:     */     }
/*  304:     */     
/*  305:     */     public void visitConstantInterfaceMethodref(ConstantInterfaceMethodref obj)
/*  306:     */     {
/*  307: 402 */       if (obj.getTag() != 11) {
/*  308: 403 */         throw new ClassConstraintException("Wrong constant tag in '" + Pass2Verifier.tostring(obj) + "'.");
/*  309:     */       }
/*  310: 405 */       checkIndex(obj, obj.getClassIndex(), this.CONST_Class);
/*  311: 406 */       checkIndex(obj, obj.getNameAndTypeIndex(), this.CONST_NameAndType);
/*  312:     */     }
/*  313:     */     
/*  314:     */     public void visitConstantString(ConstantString obj)
/*  315:     */     {
/*  316: 409 */       if (obj.getTag() != 8) {
/*  317: 410 */         throw new ClassConstraintException("Wrong constant tag in '" + Pass2Verifier.tostring(obj) + "'.");
/*  318:     */       }
/*  319: 412 */       checkIndex(obj, obj.getStringIndex(), this.CONST_Utf8);
/*  320:     */     }
/*  321:     */     
/*  322:     */     public void visitConstantInteger(ConstantInteger obj)
/*  323:     */     {
/*  324: 415 */       if (obj.getTag() != 3) {
/*  325: 416 */         throw new ClassConstraintException("Wrong constant tag in '" + Pass2Verifier.tostring(obj) + "'.");
/*  326:     */       }
/*  327:     */     }
/*  328:     */     
/*  329:     */     public void visitConstantFloat(ConstantFloat obj)
/*  330:     */     {
/*  331: 421 */       if (obj.getTag() != 4) {
/*  332: 422 */         throw new ClassConstraintException("Wrong constant tag in '" + Pass2Verifier.tostring(obj) + "'.");
/*  333:     */       }
/*  334:     */     }
/*  335:     */     
/*  336:     */     public void visitConstantLong(ConstantLong obj)
/*  337:     */     {
/*  338: 427 */       if (obj.getTag() != 5) {
/*  339: 428 */         throw new ClassConstraintException("Wrong constant tag in '" + Pass2Verifier.tostring(obj) + "'.");
/*  340:     */       }
/*  341:     */     }
/*  342:     */     
/*  343:     */     public void visitConstantDouble(ConstantDouble obj)
/*  344:     */     {
/*  345: 433 */       if (obj.getTag() != 6) {
/*  346: 434 */         throw new ClassConstraintException("Wrong constant tag in '" + Pass2Verifier.tostring(obj) + "'.");
/*  347:     */       }
/*  348:     */     }
/*  349:     */     
/*  350:     */     public void visitConstantNameAndType(ConstantNameAndType obj)
/*  351:     */     {
/*  352: 439 */       if (obj.getTag() != 12) {
/*  353: 440 */         throw new ClassConstraintException("Wrong constant tag in '" + Pass2Verifier.tostring(obj) + "'.");
/*  354:     */       }
/*  355: 442 */       checkIndex(obj, obj.getNameIndex(), this.CONST_Utf8);
/*  356:     */       
/*  357: 444 */       checkIndex(obj, obj.getSignatureIndex(), this.CONST_Utf8);
/*  358:     */     }
/*  359:     */     
/*  360:     */     public void visitConstantUtf8(ConstantUtf8 obj)
/*  361:     */     {
/*  362: 447 */       if (obj.getTag() != 1) {
/*  363: 448 */         throw new ClassConstraintException("Wrong constant tag in '" + Pass2Verifier.tostring(obj) + "'.");
/*  364:     */       }
/*  365:     */     }
/*  366:     */     
/*  367:     */     public void visitField(Field obj)
/*  368:     */     {
/*  369: 457 */       if (this.jc.isClass())
/*  370:     */       {
/*  371: 458 */         int maxone = 0;
/*  372: 459 */         if (obj.isPrivate()) {
/*  373: 459 */           maxone++;
/*  374:     */         }
/*  375: 460 */         if (obj.isProtected()) {
/*  376: 460 */           maxone++;
/*  377:     */         }
/*  378: 461 */         if (obj.isPublic()) {
/*  379: 461 */           maxone++;
/*  380:     */         }
/*  381: 462 */         if (maxone > 1) {
/*  382: 463 */           throw new ClassConstraintException("Field '" + Pass2Verifier.tostring(obj) + "' must only have at most one of its ACC_PRIVATE, ACC_PROTECTED, ACC_PUBLIC modifiers set.");
/*  383:     */         }
/*  384: 466 */         if ((obj.isFinal()) && (obj.isVolatile())) {
/*  385: 467 */           throw new ClassConstraintException("Field '" + Pass2Verifier.tostring(obj) + "' must only have at most one of its ACC_FINAL, ACC_VOLATILE modifiers set.");
/*  386:     */         }
/*  387:     */       }
/*  388:     */       else
/*  389:     */       {
/*  390: 471 */         if (!obj.isPublic()) {
/*  391: 472 */           throw new ClassConstraintException("Interface field '" + Pass2Verifier.tostring(obj) + "' must have the ACC_PUBLIC modifier set but hasn't!");
/*  392:     */         }
/*  393: 474 */         if (!obj.isStatic()) {
/*  394: 475 */           throw new ClassConstraintException("Interface field '" + Pass2Verifier.tostring(obj) + "' must have the ACC_STATIC modifier set but hasn't!");
/*  395:     */         }
/*  396: 477 */         if (!obj.isFinal()) {
/*  397: 478 */           throw new ClassConstraintException("Interface field '" + Pass2Verifier.tostring(obj) + "' must have the ACC_FINAL modifier set but hasn't!");
/*  398:     */         }
/*  399:     */       }
/*  400: 482 */       if ((obj.getAccessFlags() & 0xFFFFFF20) > 0) {
/*  401: 483 */         Pass2Verifier.this.addMessage("Field '" + Pass2Verifier.tostring(obj) + "' has access flag(s) other than ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED, ACC_STATIC, ACC_FINAL, ACC_VOLATILE, ACC_TRANSIENT set (ignored).");
/*  402:     */       }
/*  403: 486 */       checkIndex(obj, obj.getNameIndex(), this.CONST_Utf8);
/*  404:     */       
/*  405: 488 */       String name = obj.getName();
/*  406: 489 */       if (!Pass2Verifier.validFieldName(name)) {
/*  407: 490 */         throw new ClassConstraintException("Field '" + Pass2Verifier.tostring(obj) + "' has illegal name '" + obj.getName() + "'.");
/*  408:     */       }
/*  409: 494 */       checkIndex(obj, obj.getSignatureIndex(), this.CONST_Utf8);
/*  410:     */       
/*  411: 496 */       String sig = ((ConstantUtf8)this.cp.getConstant(obj.getSignatureIndex())).getBytes();
/*  412:     */       try
/*  413:     */       {
/*  414: 499 */         t = Type.getType(sig);
/*  415:     */       }
/*  416:     */       catch (ClassFormatError cfe)
/*  417:     */       {
/*  418:     */         Type t;
/*  419: 502 */         throw new ClassConstraintException("Illegal descriptor (==signature) '" + sig + "' used by '" + Pass2Verifier.tostring(obj) + "'.");
/*  420:     */       }
/*  421: 505 */       String nameanddesc = name + sig;
/*  422: 506 */       if (this.field_names_and_desc.contains(nameanddesc)) {
/*  423: 507 */         throw new ClassConstraintException("No two fields (like '" + Pass2Verifier.tostring(obj) + "') are allowed have same names and descriptors!");
/*  424:     */       }
/*  425: 509 */       if (this.field_names.contains(name)) {
/*  426: 510 */         Pass2Verifier.this.addMessage("More than one field of name '" + name + "' detected (but with different type descriptors). This is very unusual.");
/*  427:     */       }
/*  428: 512 */       this.field_names_and_desc.add(nameanddesc);
/*  429: 513 */       this.field_names.add(name);
/*  430:     */       
/*  431: 515 */       Attribute[] atts = obj.getAttributes();
/*  432: 516 */       for (int i = 0; i < atts.length; i++)
/*  433:     */       {
/*  434: 517 */         if ((!(atts[i] instanceof ConstantValue)) && (!(atts[i] instanceof Synthetic)) && (!(atts[i] instanceof Deprecated))) {
/*  435: 520 */           Pass2Verifier.this.addMessage("Attribute '" + Pass2Verifier.tostring(atts[i]) + "' as an attribute of Field '" + Pass2Verifier.tostring(obj) + "' is unknown and will therefore be ignored.");
/*  436:     */         }
/*  437: 522 */         if (!(atts[i] instanceof ConstantValue)) {
/*  438: 523 */           Pass2Verifier.this.addMessage("Attribute '" + Pass2Verifier.tostring(atts[i]) + "' as an attribute of Field '" + Pass2Verifier.tostring(obj) + "' is not a ConstantValue and is therefore only of use for debuggers and such.");
/*  439:     */         }
/*  440:     */       }
/*  441:     */     }
/*  442:     */     
/*  443:     */     public void visitMethod(Method obj)
/*  444:     */     {
/*  445: 532 */       checkIndex(obj, obj.getNameIndex(), this.CONST_Utf8);
/*  446:     */       
/*  447: 534 */       String name = obj.getName();
/*  448: 535 */       if (!Pass2Verifier.validMethodName(name, true)) {
/*  449: 536 */         throw new ClassConstraintException("Method '" + Pass2Verifier.tostring(obj) + "' has illegal name '" + name + "'.");
/*  450:     */       }
/*  451: 540 */       checkIndex(obj, obj.getSignatureIndex(), this.CONST_Utf8);
/*  452:     */       
/*  453: 542 */       String sig = ((ConstantUtf8)this.cp.getConstant(obj.getSignatureIndex())).getBytes();
/*  454:     */       Type t;
/*  455:     */       Type[] ts;
/*  456:     */       try
/*  457:     */       {
/*  458: 547 */         t = Type.getReturnType(sig);
/*  459: 548 */         ts = Type.getArgumentTypes(sig);
/*  460:     */       }
/*  461:     */       catch (ClassFormatError cfe)
/*  462:     */       {
/*  463: 552 */         throw new ClassConstraintException("Illegal descriptor (==signature) '" + sig + "' used by Method '" + Pass2Verifier.tostring(obj) + "'.");
/*  464:     */       }
/*  465: 556 */       Type act = t;
/*  466: 557 */       if ((act instanceof ArrayType)) {
/*  467: 557 */         act = ((ArrayType)act).getBasicType();
/*  468:     */       }
/*  469: 558 */       if ((act instanceof ObjectType))
/*  470:     */       {
/*  471: 559 */         Verifier v = VerifierFactory.getVerifier(((ObjectType)act).getClassName());
/*  472: 560 */         VerificationResult vr = v.doPass1();
/*  473: 561 */         if (vr != VerificationResult.VR_OK) {
/*  474: 562 */           throw new ClassConstraintException("Method '" + Pass2Verifier.tostring(obj) + "' has a return type that does not pass verification pass 1: '" + vr + "'.");
/*  475:     */         }
/*  476:     */       }
/*  477: 566 */       for (int i = 0; i < ts.length; i++)
/*  478:     */       {
/*  479: 567 */         act = ts[i];
/*  480: 568 */         if ((act instanceof ArrayType)) {
/*  481: 568 */           act = ((ArrayType)act).getBasicType();
/*  482:     */         }
/*  483: 569 */         if ((act instanceof ObjectType))
/*  484:     */         {
/*  485: 570 */           Verifier v = VerifierFactory.getVerifier(((ObjectType)act).getClassName());
/*  486: 571 */           VerificationResult vr = v.doPass1();
/*  487: 572 */           if (vr != VerificationResult.VR_OK) {
/*  488: 573 */             throw new ClassConstraintException("Method '" + Pass2Verifier.tostring(obj) + "' has an argument type that does not pass verification pass 1: '" + vr + "'.");
/*  489:     */           }
/*  490:     */         }
/*  491:     */       }
/*  492: 579 */       if ((name.equals("<clinit>")) && (ts.length != 0)) {
/*  493: 580 */         throw new ClassConstraintException("Method '" + Pass2Verifier.tostring(obj) + "' has illegal name '" + name + "'. It's name resembles the class or interface initialization method which it isn't because of its arguments (==descriptor).");
/*  494:     */       }
/*  495: 583 */       if (this.jc.isClass())
/*  496:     */       {
/*  497: 584 */         int maxone = 0;
/*  498: 585 */         if (obj.isPrivate()) {
/*  499: 585 */           maxone++;
/*  500:     */         }
/*  501: 586 */         if (obj.isProtected()) {
/*  502: 586 */           maxone++;
/*  503:     */         }
/*  504: 587 */         if (obj.isPublic()) {
/*  505: 587 */           maxone++;
/*  506:     */         }
/*  507: 588 */         if (maxone > 1) {
/*  508: 589 */           throw new ClassConstraintException("Method '" + Pass2Verifier.tostring(obj) + "' must only have at most one of its ACC_PRIVATE, ACC_PROTECTED, ACC_PUBLIC modifiers set.");
/*  509:     */         }
/*  510: 592 */         if (obj.isAbstract())
/*  511:     */         {
/*  512: 593 */           if (obj.isFinal()) {
/*  513: 593 */             throw new ClassConstraintException("Abstract method '" + Pass2Verifier.tostring(obj) + "' must not have the ACC_FINAL modifier set.");
/*  514:     */           }
/*  515: 594 */           if (obj.isNative()) {
/*  516: 594 */             throw new ClassConstraintException("Abstract method '" + Pass2Verifier.tostring(obj) + "' must not have the ACC_NATIVE modifier set.");
/*  517:     */           }
/*  518: 595 */           if (obj.isPrivate()) {
/*  519: 595 */             throw new ClassConstraintException("Abstract method '" + Pass2Verifier.tostring(obj) + "' must not have the ACC_PRIVATE modifier set.");
/*  520:     */           }
/*  521: 596 */           if (obj.isStatic()) {
/*  522: 596 */             throw new ClassConstraintException("Abstract method '" + Pass2Verifier.tostring(obj) + "' must not have the ACC_STATIC modifier set.");
/*  523:     */           }
/*  524: 597 */           if (obj.isStrictfp()) {
/*  525: 597 */             throw new ClassConstraintException("Abstract method '" + Pass2Verifier.tostring(obj) + "' must not have the ACC_STRICT modifier set.");
/*  526:     */           }
/*  527: 598 */           if (obj.isSynchronized()) {
/*  528: 598 */             throw new ClassConstraintException("Abstract method '" + Pass2Verifier.tostring(obj) + "' must not have the ACC_SYNCHRONIZED modifier set.");
/*  529:     */           }
/*  530:     */         }
/*  531:     */       }
/*  532: 602 */       else if (!name.equals("<clinit>"))
/*  533:     */       {
/*  534: 603 */         if (!obj.isPublic()) {
/*  535: 604 */           throw new ClassConstraintException("Interface method '" + Pass2Verifier.tostring(obj) + "' must have the ACC_PUBLIC modifier set but hasn't!");
/*  536:     */         }
/*  537: 606 */         if (!obj.isAbstract()) {
/*  538: 607 */           throw new ClassConstraintException("Interface method '" + Pass2Verifier.tostring(obj) + "' must have the ACC_STATIC modifier set but hasn't!");
/*  539:     */         }
/*  540: 609 */         if ((obj.isPrivate()) || (obj.isProtected()) || (obj.isStatic()) || (obj.isFinal()) || (obj.isSynchronized()) || (obj.isNative()) || (obj.isStrictfp())) {
/*  541: 616 */           throw new ClassConstraintException("Interface method '" + Pass2Verifier.tostring(obj) + "' must not have any of the ACC_PRIVATE, ACC_PROTECTED, ACC_STATIC, ACC_FINAL, ACC_SYNCHRONIZED, ACC_NATIVE, ACC_ABSTRACT, ACC_STRICT modifiers set.");
/*  542:     */         }
/*  543:     */       }
/*  544: 622 */       if (name.equals("<init>")) {
/*  545: 625 */         if ((obj.isStatic()) || (obj.isFinal()) || (obj.isSynchronized()) || (obj.isNative()) || (obj.isAbstract())) {
/*  546: 630 */           throw new ClassConstraintException("Instance initialization method '" + Pass2Verifier.tostring(obj) + "' must not have any of the ACC_STATIC, ACC_FINAL, ACC_SYNCHRONIZED, ACC_NATIVE, ACC_ABSTRACT modifiers set.");
/*  547:     */         }
/*  548:     */       }
/*  549: 635 */       if (name.equals("<clinit>"))
/*  550:     */       {
/*  551: 636 */         if ((obj.getAccessFlags() & 0xFFFFF7FF) > 0) {
/*  552: 637 */           Pass2Verifier.this.addMessage("Class or interface initialization method '" + Pass2Verifier.tostring(obj) + "' has superfluous access modifier(s) set: everything but ACC_STRICT is ignored.");
/*  553:     */         }
/*  554: 639 */         if (obj.isAbstract()) {
/*  555: 640 */           throw new ClassConstraintException("Class or interface initialization method '" + Pass2Verifier.tostring(obj) + "' must not be abstract. This contradicts the Java Language Specification, Second Edition (which omits this constraint) but is common practice of existing verifiers.");
/*  556:     */         }
/*  557:     */       }
/*  558: 644 */       if ((obj.getAccessFlags() & 0xFFFFF2C0) > 0) {
/*  559: 645 */         Pass2Verifier.this.addMessage("Method '" + Pass2Verifier.tostring(obj) + "' has access flag(s) other than ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED, ACC_STATIC, ACC_FINAL, ACC_SYNCHRONIZED, ACC_NATIVE, ACC_ABSTRACT, ACC_STRICT set (ignored).");
/*  560:     */       }
/*  561: 648 */       String nameanddesc = name + sig;
/*  562: 649 */       if (this.method_names_and_desc.contains(nameanddesc)) {
/*  563: 650 */         throw new ClassConstraintException("No two methods (like '" + Pass2Verifier.tostring(obj) + "') are allowed have same names and desciptors!");
/*  564:     */       }
/*  565: 652 */       this.method_names_and_desc.add(nameanddesc);
/*  566:     */       
/*  567: 654 */       Attribute[] atts = obj.getAttributes();
/*  568: 655 */       int num_code_atts = 0;
/*  569: 656 */       for (int i = 0; i < atts.length; i++)
/*  570:     */       {
/*  571: 657 */         if ((!(atts[i] instanceof Code)) && (!(atts[i] instanceof ExceptionTable)) && (!(atts[i] instanceof Synthetic)) && (!(atts[i] instanceof Deprecated))) {
/*  572: 661 */           Pass2Verifier.this.addMessage("Attribute '" + Pass2Verifier.tostring(atts[i]) + "' as an attribute of Method '" + Pass2Verifier.tostring(obj) + "' is unknown and will therefore be ignored.");
/*  573:     */         }
/*  574: 663 */         if ((!(atts[i] instanceof Code)) && (!(atts[i] instanceof ExceptionTable))) {
/*  575: 665 */           Pass2Verifier.this.addMessage("Attribute '" + Pass2Verifier.tostring(atts[i]) + "' as an attribute of Method '" + Pass2Verifier.tostring(obj) + "' is neither Code nor Exceptions and is therefore only of use for debuggers and such.");
/*  576:     */         }
/*  577: 667 */         if (((atts[i] instanceof Code)) && ((obj.isNative()) || (obj.isAbstract()))) {
/*  578: 668 */           throw new ClassConstraintException("Native or abstract methods like '" + Pass2Verifier.tostring(obj) + "' must not have a Code attribute like '" + Pass2Verifier.tostring(atts[i]) + "'.");
/*  579:     */         }
/*  580: 670 */         if ((atts[i] instanceof Code)) {
/*  581: 670 */           num_code_atts++;
/*  582:     */         }
/*  583:     */       }
/*  584: 672 */       if ((!obj.isNative()) && (!obj.isAbstract()) && (num_code_atts != 1)) {
/*  585: 673 */         throw new ClassConstraintException("Non-native, non-abstract methods like '" + Pass2Verifier.tostring(obj) + "' must have exactly one Code attribute (found: " + num_code_atts + ").");
/*  586:     */       }
/*  587:     */     }
/*  588:     */     
/*  589:     */     public void visitSourceFile(SourceFile obj)
/*  590:     */     {
/*  591: 683 */       checkIndex(obj, obj.getNameIndex(), this.CONST_Utf8);
/*  592:     */       
/*  593: 685 */       String name = ((ConstantUtf8)this.cp.getConstant(obj.getNameIndex())).getBytes();
/*  594: 686 */       if (!name.equals("SourceFile")) {
/*  595: 687 */         throw new ClassConstraintException("The SourceFile attribute '" + Pass2Verifier.tostring(obj) + "' is not correctly named 'SourceFile' but '" + name + "'.");
/*  596:     */       }
/*  597: 690 */       checkIndex(obj, obj.getSourceFileIndex(), this.CONST_Utf8);
/*  598:     */       
/*  599: 692 */       String sourcefilename = ((ConstantUtf8)this.cp.getConstant(obj.getSourceFileIndex())).getBytes();
/*  600: 693 */       String sourcefilenamelc = sourcefilename.toLowerCase();
/*  601: 695 */       if ((sourcefilename.indexOf('/') != -1) || (sourcefilename.indexOf('\\') != -1) || (sourcefilename.indexOf(':') != -1) || (sourcefilenamelc.lastIndexOf(".java") == -1)) {
/*  602: 699 */         Pass2Verifier.this.addMessage("SourceFile attribute '" + Pass2Verifier.tostring(obj) + "' has a funny name: remember not to confuse certain parsers working on javap's output. Also, this name ('" + sourcefilename + "') is considered an unqualified (simple) file name only.");
/*  603:     */       }
/*  604:     */     }
/*  605:     */     
/*  606:     */     public void visitDeprecated(Deprecated obj)
/*  607:     */     {
/*  608: 703 */       checkIndex(obj, obj.getNameIndex(), this.CONST_Utf8);
/*  609:     */       
/*  610: 705 */       String name = ((ConstantUtf8)this.cp.getConstant(obj.getNameIndex())).getBytes();
/*  611: 706 */       if (!name.equals("Deprecated")) {
/*  612: 707 */         throw new ClassConstraintException("The Deprecated attribute '" + Pass2Verifier.tostring(obj) + "' is not correctly named 'Deprecated' but '" + name + "'.");
/*  613:     */       }
/*  614:     */     }
/*  615:     */     
/*  616:     */     public void visitSynthetic(Synthetic obj)
/*  617:     */     {
/*  618: 711 */       checkIndex(obj, obj.getNameIndex(), this.CONST_Utf8);
/*  619: 712 */       String name = ((ConstantUtf8)this.cp.getConstant(obj.getNameIndex())).getBytes();
/*  620: 713 */       if (!name.equals("Synthetic")) {
/*  621: 714 */         throw new ClassConstraintException("The Synthetic attribute '" + Pass2Verifier.tostring(obj) + "' is not correctly named 'Synthetic' but '" + name + "'.");
/*  622:     */       }
/*  623:     */     }
/*  624:     */     
/*  625:     */     public void visitInnerClasses(InnerClasses obj)
/*  626:     */     {
/*  627: 721 */       checkIndex(obj, obj.getNameIndex(), this.CONST_Utf8);
/*  628:     */       
/*  629: 723 */       String name = ((ConstantUtf8)this.cp.getConstant(obj.getNameIndex())).getBytes();
/*  630: 724 */       if (!name.equals("InnerClasses")) {
/*  631: 725 */         throw new ClassConstraintException("The InnerClasses attribute '" + Pass2Verifier.tostring(obj) + "' is not correctly named 'InnerClasses' but '" + name + "'.");
/*  632:     */       }
/*  633: 728 */       InnerClass[] ics = obj.getInnerClasses();
/*  634: 730 */       for (int i = 0; i < ics.length; i++)
/*  635:     */       {
/*  636: 731 */         checkIndex(obj, ics[i].getInnerClassIndex(), this.CONST_Class);
/*  637: 732 */         int outer_idx = ics[i].getOuterClassIndex();
/*  638: 733 */         if (outer_idx != 0) {
/*  639: 734 */           checkIndex(obj, outer_idx, this.CONST_Class);
/*  640:     */         }
/*  641: 736 */         int innername_idx = ics[i].getInnerNameIndex();
/*  642: 737 */         if (innername_idx != 0) {
/*  643: 738 */           checkIndex(obj, innername_idx, this.CONST_Utf8);
/*  644:     */         }
/*  645: 740 */         int acc = ics[i].getInnerAccessFlags();
/*  646: 741 */         acc &= 0xFFFFF9E0;
/*  647: 742 */         if (acc != 0) {
/*  648: 743 */           Pass2Verifier.this.addMessage("Unknown access flag for inner class '" + Pass2Verifier.tostring(ics[i]) + "' set (InnerClasses attribute '" + Pass2Verifier.tostring(obj) + "').");
/*  649:     */         }
/*  650:     */       }
/*  651:     */     }
/*  652:     */     
/*  653:     */     public void visitConstantValue(ConstantValue obj)
/*  654:     */     {
/*  655: 755 */       checkIndex(obj, obj.getNameIndex(), this.CONST_Utf8);
/*  656:     */       
/*  657: 757 */       String name = ((ConstantUtf8)this.cp.getConstant(obj.getNameIndex())).getBytes();
/*  658: 758 */       if (!name.equals("ConstantValue")) {
/*  659: 759 */         throw new ClassConstraintException("The ConstantValue attribute '" + Pass2Verifier.tostring(obj) + "' is not correctly named 'ConstantValue' but '" + name + "'.");
/*  660:     */       }
/*  661: 762 */       Object pred = this.carrier.predecessor();
/*  662: 763 */       if ((pred instanceof Field))
/*  663:     */       {
/*  664: 764 */         Field f = (Field)pred;
/*  665:     */         
/*  666: 766 */         Type field_type = Type.getType(((ConstantUtf8)this.cp.getConstant(f.getSignatureIndex())).getBytes());
/*  667:     */         
/*  668: 768 */         int index = obj.getConstantValueIndex();
/*  669: 769 */         if ((index < 0) || (index >= this.cplen)) {
/*  670: 770 */           throw new ClassConstraintException("Invalid index '" + index + "' used by '" + Pass2Verifier.tostring(obj) + "'.");
/*  671:     */         }
/*  672: 772 */         Constant c = this.cp.getConstant(index);
/*  673: 774 */         if ((this.CONST_Long.isInstance(c)) && (field_type.equals(Type.LONG))) {
/*  674: 775 */           return;
/*  675:     */         }
/*  676: 777 */         if ((this.CONST_Float.isInstance(c)) && (field_type.equals(Type.FLOAT))) {
/*  677: 778 */           return;
/*  678:     */         }
/*  679: 780 */         if ((this.CONST_Double.isInstance(c)) && (field_type.equals(Type.DOUBLE))) {
/*  680: 781 */           return;
/*  681:     */         }
/*  682: 783 */         if ((this.CONST_Integer.isInstance(c)) && ((field_type.equals(Type.INT)) || (field_type.equals(Type.SHORT)) || (field_type.equals(Type.CHAR)) || (field_type.equals(Type.BYTE)) || (field_type.equals(Type.BOOLEAN)))) {
/*  683: 784 */           return;
/*  684:     */         }
/*  685: 786 */         if ((this.CONST_String.isInstance(c)) && (field_type.equals(Type.STRING))) {
/*  686: 787 */           return;
/*  687:     */         }
/*  688: 790 */         throw new ClassConstraintException("Illegal type of ConstantValue '" + obj + "' embedding Constant '" + c + "'. It is referenced by field '" + Pass2Verifier.tostring(f) + "' expecting a different type: '" + field_type + "'.");
/*  689:     */       }
/*  690:     */     }
/*  691:     */     
/*  692:     */     public void visitCode(Code obj)
/*  693:     */     {
/*  694: 802 */       checkIndex(obj, obj.getNameIndex(), this.CONST_Utf8);
/*  695:     */       
/*  696: 804 */       String name = ((ConstantUtf8)this.cp.getConstant(obj.getNameIndex())).getBytes();
/*  697: 805 */       if (!name.equals("Code")) {
/*  698: 806 */         throw new ClassConstraintException("The Code attribute '" + Pass2Verifier.tostring(obj) + "' is not correctly named 'Code' but '" + name + "'.");
/*  699:     */       }
/*  700: 809 */       Method m = null;
/*  701: 810 */       if (!(this.carrier.predecessor() instanceof Method))
/*  702:     */       {
/*  703: 811 */         Pass2Verifier.this.addMessage("Code attribute '" + Pass2Verifier.tostring(obj) + "' is not declared in a method_info structure but in '" + this.carrier.predecessor() + "'. Ignored.");
/*  704: 812 */         return;
/*  705:     */       }
/*  706: 815 */       m = (Method)this.carrier.predecessor();
/*  707: 819 */       if (obj.getCode().length == 0) {
/*  708: 820 */         throw new ClassConstraintException("Code array of Code attribute '" + Pass2Verifier.tostring(obj) + "' (method '" + m + "') must not be empty.");
/*  709:     */       }
/*  710: 824 */       CodeException[] exc_table = obj.getExceptionTable();
/*  711: 825 */       for (int i = 0; i < exc_table.length; i++)
/*  712:     */       {
/*  713: 826 */         int exc_index = exc_table[i].getCatchType();
/*  714: 827 */         if (exc_index != 0)
/*  715:     */         {
/*  716: 828 */           checkIndex(obj, exc_index, this.CONST_Class);
/*  717: 829 */           ConstantClass cc = (ConstantClass)this.cp.getConstant(exc_index);
/*  718: 830 */           checkIndex(cc, cc.getNameIndex(), this.CONST_Utf8);
/*  719: 831 */           String cname = ((ConstantUtf8)this.cp.getConstant(cc.getNameIndex())).getBytes().replace('/', '.');
/*  720:     */           
/*  721: 833 */           Verifier v = VerifierFactory.getVerifier(cname);
/*  722: 834 */           VerificationResult vr = v.doPass1();
/*  723: 836 */           if (vr != VerificationResult.VR_OK) {
/*  724: 837 */             throw new ClassConstraintException("Code attribute '" + Pass2Verifier.tostring(obj) + "' (method '" + m + "') has an exception_table entry '" + Pass2Verifier.tostring(exc_table[i]) + "' that references '" + cname + "' as an Exception but it does not pass verification pass 1: " + vr);
/*  725:     */           }
/*  726: 842 */           JavaClass e = Repository.lookupClass(cname);
/*  727: 843 */           JavaClass t = Repository.lookupClass(Type.THROWABLE.getClassName());
/*  728: 844 */           JavaClass o = Repository.lookupClass(Type.OBJECT.getClassName());
/*  729: 845 */           while (e != o)
/*  730:     */           {
/*  731: 846 */             if (e == t) {
/*  732:     */               break;
/*  733:     */             }
/*  734: 848 */             v = VerifierFactory.getVerifier(e.getSuperclassName());
/*  735: 849 */             vr = v.doPass1();
/*  736: 850 */             if (vr != VerificationResult.VR_OK) {
/*  737: 851 */               throw new ClassConstraintException("Code attribute '" + Pass2Verifier.tostring(obj) + "' (method '" + m + "') has an exception_table entry '" + Pass2Verifier.tostring(exc_table[i]) + "' that references '" + cname + "' as an Exception but '" + e.getSuperclassName() + "' in the ancestor hierachy does not pass verification pass 1: " + vr);
/*  738:     */             }
/*  739: 854 */             e = Repository.lookupClass(e.getSuperclassName());
/*  740:     */           }
/*  741: 857 */           if (e != t) {
/*  742: 857 */             throw new ClassConstraintException("Code attribute '" + Pass2Verifier.tostring(obj) + "' (method '" + m + "') has an exception_table entry '" + Pass2Verifier.tostring(exc_table[i]) + "' that references '" + cname + "' as an Exception but it is not a subclass of '" + t.getClassName() + "'.");
/*  743:     */           }
/*  744:     */         }
/*  745:     */       }
/*  746: 865 */       int method_number = -1;
/*  747: 866 */       Method[] ms = Repository.lookupClass(Pass2Verifier.this.myOwner.getClassName()).getMethods();
/*  748: 867 */       for (int mn = 0; mn < ms.length; mn++) {
/*  749: 868 */         if (m == ms[mn])
/*  750:     */         {
/*  751: 869 */           method_number = mn;
/*  752: 870 */           break;
/*  753:     */         }
/*  754:     */       }
/*  755: 873 */       if (method_number < 0) {
/*  756: 874 */         throw new AssertionViolatedException("Could not find a known BCEL Method object in the corresponding BCEL JavaClass object.");
/*  757:     */       }
/*  758: 876 */       Pass2Verifier.this.localVariablesInfos[method_number] = new LocalVariablesInfo(obj.getMaxLocals());
/*  759:     */       
/*  760: 878 */       int num_of_lvt_attribs = 0;
/*  761:     */       
/*  762: 880 */       Attribute[] atts = obj.getAttributes();
/*  763: 881 */       for (int a = 0; a < atts.length; a++)
/*  764:     */       {
/*  765: 882 */         if ((!(atts[a] instanceof LineNumberTable)) && (!(atts[a] instanceof LocalVariableTable))) {
/*  766: 884 */           Pass2Verifier.this.addMessage("Attribute '" + Pass2Verifier.tostring(atts[a]) + "' as an attribute of Code attribute '" + Pass2Verifier.tostring(obj) + "' (method '" + m + "') is unknown and will therefore be ignored.");
/*  767:     */         } else {
/*  768: 887 */           Pass2Verifier.this.addMessage("Attribute '" + Pass2Verifier.tostring(atts[a]) + "' as an attribute of Code attribute '" + Pass2Verifier.tostring(obj) + "' (method '" + m + "') will effectively be ignored and is only useful for debuggers and such.");
/*  769:     */         }
/*  770: 894 */         if ((atts[a] instanceof LocalVariableTable))
/*  771:     */         {
/*  772: 896 */           LocalVariableTable lvt = (LocalVariableTable)atts[a];
/*  773:     */           
/*  774: 898 */           checkIndex(lvt, lvt.getNameIndex(), this.CONST_Utf8);
/*  775:     */           
/*  776: 900 */           String lvtname = ((ConstantUtf8)this.cp.getConstant(lvt.getNameIndex())).getBytes();
/*  777: 901 */           if (!lvtname.equals("LocalVariableTable")) {
/*  778: 902 */             throw new ClassConstraintException("The LocalVariableTable attribute '" + Pass2Verifier.tostring(lvt) + "' is not correctly named 'LocalVariableTable' but '" + lvtname + "'.");
/*  779:     */           }
/*  780: 905 */           Code code = obj;
/*  781: 906 */           int max_locals = code.getMaxLocals();
/*  782:     */           
/*  783:     */ 
/*  784: 909 */           LocalVariable[] localvariables = lvt.getLocalVariableTable();
/*  785: 911 */           for (int i = 0; i < localvariables.length; i++)
/*  786:     */           {
/*  787: 912 */             checkIndex(lvt, localvariables[i].getNameIndex(), this.CONST_Utf8);
/*  788: 913 */             String localname = ((ConstantUtf8)this.cp.getConstant(localvariables[i].getNameIndex())).getBytes();
/*  789: 914 */             if (!Pass2Verifier.validJavaIdentifier(localname)) {
/*  790: 915 */               throw new ClassConstraintException("LocalVariableTable '" + Pass2Verifier.tostring(lvt) + "' references a local variable by the name '" + localname + "' which is not a legal Java simple name.");
/*  791:     */             }
/*  792: 918 */             checkIndex(lvt, localvariables[i].getSignatureIndex(), this.CONST_Utf8);
/*  793: 919 */             String localsig = ((ConstantUtf8)this.cp.getConstant(localvariables[i].getSignatureIndex())).getBytes();
/*  794:     */             Type t;
/*  795:     */             try
/*  796:     */             {
/*  797: 922 */               t = Type.getType(localsig);
/*  798:     */             }
/*  799:     */             catch (ClassFormatError cfe)
/*  800:     */             {
/*  801: 925 */               throw new ClassConstraintException("Illegal descriptor (==signature) '" + localsig + "' used by LocalVariable '" + Pass2Verifier.tostring(localvariables[i]) + "' referenced by '" + Pass2Verifier.tostring(lvt) + "'.");
/*  802:     */             }
/*  803: 927 */             int localindex = localvariables[i].getIndex();
/*  804: 928 */             if (((t == Type.LONG) || (t == Type.DOUBLE) ? localindex + 1 : localindex) >= code.getMaxLocals()) {
/*  805: 929 */               throw new ClassConstraintException("LocalVariableTable attribute '" + Pass2Verifier.tostring(lvt) + "' references a LocalVariable '" + Pass2Verifier.tostring(localvariables[i]) + "' with an index that exceeds the surrounding Code attribute's max_locals value of '" + code.getMaxLocals() + "'.");
/*  806:     */             }
/*  807:     */             try
/*  808:     */             {
/*  809: 933 */               Pass2Verifier.this.localVariablesInfos[method_number].add(localindex, localname, localvariables[i].getStartPC(), localvariables[i].getLength(), t);
/*  810:     */             }
/*  811:     */             catch (LocalVariableInfoInconsistentException lviie)
/*  812:     */             {
/*  813: 936 */               throw new ClassConstraintException("Conflicting information in LocalVariableTable '" + Pass2Verifier.tostring(lvt) + "' found in Code attribute '" + Pass2Verifier.tostring(obj) + "' (method '" + Pass2Verifier.tostring(m) + "'). " + lviie.getMessage());
/*  814:     */             }
/*  815:     */           }
/*  816: 940 */           num_of_lvt_attribs++;
/*  817: 941 */           if (num_of_lvt_attribs > obj.getMaxLocals()) {
/*  818: 942 */             throw new ClassConstraintException("Number of LocalVariableTable attributes of Code attribute '" + Pass2Verifier.tostring(obj) + "' (method '" + Pass2Verifier.tostring(m) + "') exceeds number of local variable slots '" + obj.getMaxLocals() + "' ('There may be no more than one LocalVariableTable attribute per local variable in the Code attribute.').");
/*  819:     */           }
/*  820:     */         }
/*  821:     */       }
/*  822:     */     }
/*  823:     */     
/*  824:     */     public void visitExceptionTable(ExceptionTable obj)
/*  825:     */     {
/*  826: 950 */       checkIndex(obj, obj.getNameIndex(), this.CONST_Utf8);
/*  827:     */       
/*  828: 952 */       String name = ((ConstantUtf8)this.cp.getConstant(obj.getNameIndex())).getBytes();
/*  829: 953 */       if (!name.equals("Exceptions")) {
/*  830: 954 */         throw new ClassConstraintException("The Exceptions attribute '" + Pass2Verifier.tostring(obj) + "' is not correctly named 'Exceptions' but '" + name + "'.");
/*  831:     */       }
/*  832: 957 */       int[] exc_indices = obj.getExceptionIndexTable();
/*  833: 959 */       for (int i = 0; i < exc_indices.length; i++)
/*  834:     */       {
/*  835: 960 */         checkIndex(obj, exc_indices[i], this.CONST_Class);
/*  836:     */         
/*  837: 962 */         ConstantClass cc = (ConstantClass)this.cp.getConstant(exc_indices[i]);
/*  838: 963 */         checkIndex(cc, cc.getNameIndex(), this.CONST_Utf8);
/*  839: 964 */         String cname = ((ConstantUtf8)this.cp.getConstant(cc.getNameIndex())).getBytes().replace('/', '.');
/*  840:     */         
/*  841: 966 */         Verifier v = VerifierFactory.getVerifier(cname);
/*  842: 967 */         VerificationResult vr = v.doPass1();
/*  843: 969 */         if (vr != VerificationResult.VR_OK) {
/*  844: 970 */           throw new ClassConstraintException("Exceptions attribute '" + Pass2Verifier.tostring(obj) + "' references '" + cname + "' as an Exception but it does not pass verification pass 1: " + vr);
/*  845:     */         }
/*  846: 975 */         JavaClass e = Repository.lookupClass(cname);
/*  847: 976 */         JavaClass t = Repository.lookupClass(Type.THROWABLE.getClassName());
/*  848: 977 */         JavaClass o = Repository.lookupClass(Type.OBJECT.getClassName());
/*  849: 978 */         while (e != o)
/*  850:     */         {
/*  851: 979 */           if (e == t) {
/*  852:     */             break;
/*  853:     */           }
/*  854: 981 */           v = VerifierFactory.getVerifier(e.getSuperclassName());
/*  855: 982 */           vr = v.doPass1();
/*  856: 983 */           if (vr != VerificationResult.VR_OK) {
/*  857: 984 */             throw new ClassConstraintException("Exceptions attribute '" + Pass2Verifier.tostring(obj) + "' references '" + cname + "' as an Exception but '" + e.getSuperclassName() + "' in the ancestor hierachy does not pass verification pass 1: " + vr);
/*  858:     */           }
/*  859: 987 */           e = Repository.lookupClass(e.getSuperclassName());
/*  860:     */         }
/*  861: 990 */         if (e != t) {
/*  862: 990 */           throw new ClassConstraintException("Exceptions attribute '" + Pass2Verifier.tostring(obj) + "' references '" + cname + "' as an Exception but it is not a subclass of '" + t.getClassName() + "'.");
/*  863:     */         }
/*  864:     */       }
/*  865:     */     }
/*  866:     */     
/*  867:     */     public void visitLineNumberTable(LineNumberTable obj)
/*  868:     */     {
/*  869:1000 */       checkIndex(obj, obj.getNameIndex(), this.CONST_Utf8);
/*  870:     */       
/*  871:1002 */       String name = ((ConstantUtf8)this.cp.getConstant(obj.getNameIndex())).getBytes();
/*  872:1003 */       if (!name.equals("LineNumberTable")) {
/*  873:1004 */         throw new ClassConstraintException("The LineNumberTable attribute '" + Pass2Verifier.tostring(obj) + "' is not correctly named 'LineNumberTable' but '" + name + "'.");
/*  874:     */       }
/*  875:     */     }
/*  876:     */     
/*  877:     */     public void visitUnknown(Unknown obj)
/*  878:     */     {
/*  879:1021 */       checkIndex(obj, obj.getNameIndex(), this.CONST_Utf8);
/*  880:     */       
/*  881:     */ 
/*  882:1024 */       Pass2Verifier.this.addMessage("Unknown attribute '" + Pass2Verifier.tostring(obj) + "'. This attribute is not known in any context!");
/*  883:     */     }
/*  884:     */     
/*  885:     */     public void visitLocalVariableTable(LocalVariableTable obj) {}
/*  886:     */     
/*  887:     */     public void visitLocalVariable(LocalVariable obj) {}
/*  888:     */     
/*  889:     */     public void visitCodeException(CodeException obj) {}
/*  890:     */     
/*  891:     */     public void visitConstantPool(ConstantPool obj) {}
/*  892:     */     
/*  893:     */     public void visitInnerClass(InnerClass obj) {}
/*  894:     */     
/*  895:     */     public void visitLineNumber(LineNumber obj) {}
/*  896:     */   }
/*  897:     */   
/*  898:     */   private void field_and_method_refs_are_valid()
/*  899:     */   {
/*  900:1074 */     JavaClass jc = Repository.lookupClass(this.myOwner.getClassName());
/*  901:1075 */     DescendingVisitor v = new DescendingVisitor(jc, new FAMRAV_Visitor(jc, null));
/*  902:1076 */     v.visit();
/*  903:     */   }
/*  904:     */   
/*  905:     */   private class FAMRAV_Visitor
/*  906:     */     extends EmptyVisitor
/*  907:     */     implements Visitor
/*  908:     */   {
/*  909:     */     private final JavaClass jc;
/*  910:     */     private final ConstantPool cp;
/*  911:     */     
/*  912:     */     FAMRAV_Visitor(JavaClass x1, Pass2Verifier.1 x2)
/*  913:     */     {
/*  914:1088 */       this(x1);
/*  915:     */     }
/*  916:     */     
/*  917:     */     private FAMRAV_Visitor(JavaClass _jc)
/*  918:     */     {
/*  919:1092 */       this.jc = _jc;
/*  920:1093 */       this.cp = _jc.getConstantPool();
/*  921:     */     }
/*  922:     */     
/*  923:     */     public void visitConstantFieldref(ConstantFieldref obj)
/*  924:     */     {
/*  925:1097 */       if (obj.getTag() != 9) {
/*  926:1098 */         throw new ClassConstraintException("ConstantFieldref '" + Pass2Verifier.tostring(obj) + "' has wrong tag!");
/*  927:     */       }
/*  928:1100 */       int name_and_type_index = obj.getNameAndTypeIndex();
/*  929:1101 */       ConstantNameAndType cnat = (ConstantNameAndType)this.cp.getConstant(name_and_type_index);
/*  930:1102 */       String name = ((ConstantUtf8)this.cp.getConstant(cnat.getNameIndex())).getBytes();
/*  931:1103 */       if (!Pass2Verifier.validFieldName(name)) {
/*  932:1104 */         throw new ClassConstraintException("Invalid field name '" + name + "' referenced by '" + Pass2Verifier.tostring(obj) + "'.");
/*  933:     */       }
/*  934:1107 */       int class_index = obj.getClassIndex();
/*  935:1108 */       ConstantClass cc = (ConstantClass)this.cp.getConstant(class_index);
/*  936:1109 */       String className = ((ConstantUtf8)this.cp.getConstant(cc.getNameIndex())).getBytes();
/*  937:1110 */       if (!Pass2Verifier.validClassName(className)) {
/*  938:1111 */         throw new ClassConstraintException("Illegal class name '" + className + "' used by '" + Pass2Verifier.tostring(obj) + "'.");
/*  939:     */       }
/*  940:1114 */       String sig = ((ConstantUtf8)this.cp.getConstant(cnat.getSignatureIndex())).getBytes();
/*  941:     */       try
/*  942:     */       {
/*  943:1117 */         t = Type.getType(sig);
/*  944:     */       }
/*  945:     */       catch (ClassFormatError cfe)
/*  946:     */       {
/*  947:     */         Type t;
/*  948:1121 */         throw new ClassConstraintException("Illegal descriptor (==signature) '" + sig + "' used by '" + Pass2Verifier.tostring(obj) + "'.");
/*  949:     */       }
/*  950:     */     }
/*  951:     */     
/*  952:     */     public void visitConstantMethodref(ConstantMethodref obj)
/*  953:     */     {
/*  954:1126 */       if (obj.getTag() != 10) {
/*  955:1127 */         throw new ClassConstraintException("ConstantMethodref '" + Pass2Verifier.tostring(obj) + "' has wrong tag!");
/*  956:     */       }
/*  957:1129 */       int name_and_type_index = obj.getNameAndTypeIndex();
/*  958:1130 */       ConstantNameAndType cnat = (ConstantNameAndType)this.cp.getConstant(name_and_type_index);
/*  959:1131 */       String name = ((ConstantUtf8)this.cp.getConstant(cnat.getNameIndex())).getBytes();
/*  960:1132 */       if (!Pass2Verifier.validClassMethodName(name)) {
/*  961:1133 */         throw new ClassConstraintException("Invalid (non-interface) method name '" + name + "' referenced by '" + Pass2Verifier.tostring(obj) + "'.");
/*  962:     */       }
/*  963:1136 */       int class_index = obj.getClassIndex();
/*  964:1137 */       ConstantClass cc = (ConstantClass)this.cp.getConstant(class_index);
/*  965:1138 */       String className = ((ConstantUtf8)this.cp.getConstant(cc.getNameIndex())).getBytes();
/*  966:1139 */       if (!Pass2Verifier.validClassName(className)) {
/*  967:1140 */         throw new ClassConstraintException("Illegal class name '" + className + "' used by '" + Pass2Verifier.tostring(obj) + "'.");
/*  968:     */       }
/*  969:1143 */       String sig = ((ConstantUtf8)this.cp.getConstant(cnat.getSignatureIndex())).getBytes();
/*  970:     */       try
/*  971:     */       {
/*  972:1146 */         Type t = Type.getReturnType(sig);
/*  973:1147 */         Type[] ts = Type.getArgumentTypes(sig);
/*  974:1148 */         if ((name.equals("<init>")) && (t != Type.VOID)) {
/*  975:1149 */           throw new ClassConstraintException("Instance initialization method must have VOID return type.");
/*  976:     */         }
/*  977:     */       }
/*  978:     */       catch (ClassFormatError cfe)
/*  979:     */       {
/*  980:1154 */         throw new ClassConstraintException("Illegal descriptor (==signature) '" + sig + "' used by '" + Pass2Verifier.tostring(obj) + "'.");
/*  981:     */       }
/*  982:     */     }
/*  983:     */     
/*  984:     */     public void visitConstantInterfaceMethodref(ConstantInterfaceMethodref obj)
/*  985:     */     {
/*  986:1159 */       if (obj.getTag() != 11) {
/*  987:1160 */         throw new ClassConstraintException("ConstantInterfaceMethodref '" + Pass2Verifier.tostring(obj) + "' has wrong tag!");
/*  988:     */       }
/*  989:1162 */       int name_and_type_index = obj.getNameAndTypeIndex();
/*  990:1163 */       ConstantNameAndType cnat = (ConstantNameAndType)this.cp.getConstant(name_and_type_index);
/*  991:1164 */       String name = ((ConstantUtf8)this.cp.getConstant(cnat.getNameIndex())).getBytes();
/*  992:1165 */       if (!Pass2Verifier.validInterfaceMethodName(name)) {
/*  993:1166 */         throw new ClassConstraintException("Invalid (interface) method name '" + name + "' referenced by '" + Pass2Verifier.tostring(obj) + "'.");
/*  994:     */       }
/*  995:1169 */       int class_index = obj.getClassIndex();
/*  996:1170 */       ConstantClass cc = (ConstantClass)this.cp.getConstant(class_index);
/*  997:1171 */       String className = ((ConstantUtf8)this.cp.getConstant(cc.getNameIndex())).getBytes();
/*  998:1172 */       if (!Pass2Verifier.validClassName(className)) {
/*  999:1173 */         throw new ClassConstraintException("Illegal class name '" + className + "' used by '" + Pass2Verifier.tostring(obj) + "'.");
/* 1000:     */       }
/* 1001:1176 */       String sig = ((ConstantUtf8)this.cp.getConstant(cnat.getSignatureIndex())).getBytes();
/* 1002:     */       try
/* 1003:     */       {
/* 1004:1179 */         Type t = Type.getReturnType(sig);
/* 1005:1180 */         Type[] ts = Type.getArgumentTypes(sig);
/* 1006:1181 */         if ((name.equals("<clinit>")) && (t != Type.VOID)) {
/* 1007:1182 */           Pass2Verifier.this.addMessage("Class or interface initialization method '<clinit>' usually has VOID return type instead of '" + t + "'. Note this is really not a requirement of The Java Virtual Machine Specification, Second Edition.");
/* 1008:     */         }
/* 1009:     */       }
/* 1010:     */       catch (ClassFormatError cfe)
/* 1011:     */       {
/* 1012:1187 */         throw new ClassConstraintException("Illegal descriptor (==signature) '" + sig + "' used by '" + Pass2Verifier.tostring(obj) + "'.");
/* 1013:     */       }
/* 1014:     */     }
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   private static final boolean validClassName(String name)
/* 1018:     */   {
/* 1019:1200 */     return true;
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   private static boolean validMethodName(String name, boolean allowStaticInit)
/* 1023:     */   {
/* 1024:1211 */     if (validJavaLangMethodName(name)) {
/* 1025:1211 */       return true;
/* 1026:     */     }
/* 1027:1213 */     if (allowStaticInit) {
/* 1028:1214 */       return (name.equals("<init>")) || (name.equals("<clinit>"));
/* 1029:     */     }
/* 1030:1217 */     return name.equals("<init>");
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   private static boolean validClassMethodName(String name)
/* 1034:     */   {
/* 1035:1227 */     return validMethodName(name, false);
/* 1036:     */   }
/* 1037:     */   
/* 1038:     */   private static boolean validJavaLangMethodName(String name)
/* 1039:     */   {
/* 1040:1237 */     if (!Character.isJavaIdentifierStart(name.charAt(0))) {
/* 1041:1237 */       return false;
/* 1042:     */     }
/* 1043:1239 */     for (int i = 1; i < name.length(); i++) {
/* 1044:1240 */       if (!Character.isJavaIdentifierPart(name.charAt(i))) {
/* 1045:1240 */         return false;
/* 1046:     */       }
/* 1047:     */     }
/* 1048:1242 */     return true;
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   private static boolean validInterfaceMethodName(String name)
/* 1052:     */   {
/* 1053:1252 */     if (name.startsWith("<")) {
/* 1054:1252 */       return false;
/* 1055:     */     }
/* 1056:1253 */     return validJavaLangMethodName(name);
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   private static boolean validJavaIdentifier(String name)
/* 1060:     */   {
/* 1061:1262 */     if (!Character.isJavaIdentifierStart(name.charAt(0))) {
/* 1062:1262 */       return false;
/* 1063:     */     }
/* 1064:1264 */     for (int i = 1; i < name.length(); i++) {
/* 1065:1265 */       if (!Character.isJavaIdentifierPart(name.charAt(i))) {
/* 1066:1265 */         return false;
/* 1067:     */       }
/* 1068:     */     }
/* 1069:1267 */     return true;
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   private static boolean validFieldName(String name)
/* 1073:     */   {
/* 1074:1276 */     return validJavaIdentifier(name);
/* 1075:     */   }
/* 1076:     */   
/* 1077:     */   private class InnerClassDetector
/* 1078:     */     extends EmptyVisitor
/* 1079:     */   {
/* 1080:1299 */     private boolean hasInnerClass = false;
/* 1081:     */     private JavaClass jc;
/* 1082:     */     private ConstantPool cp;
/* 1083:     */     
/* 1084:     */     private InnerClassDetector() {}
/* 1085:     */     
/* 1086:     */     public InnerClassDetector(JavaClass _jc)
/* 1087:     */     {
/* 1088:1305 */       this.jc = _jc;
/* 1089:1306 */       this.cp = this.jc.getConstantPool();
/* 1090:1307 */       new DescendingVisitor(this.jc, this).visit();
/* 1091:     */     }
/* 1092:     */     
/* 1093:     */     public boolean innerClassReferenced()
/* 1094:     */     {
/* 1095:1314 */       return this.hasInnerClass;
/* 1096:     */     }
/* 1097:     */     
/* 1098:     */     public void visitConstantClass(ConstantClass obj)
/* 1099:     */     {
/* 1100:1318 */       Constant c = this.cp.getConstant(obj.getNameIndex());
/* 1101:1319 */       if ((c instanceof ConstantUtf8))
/* 1102:     */       {
/* 1103:1320 */         String classname = ((ConstantUtf8)c).getBytes();
/* 1104:1321 */         if (classname.startsWith(this.jc.getClassName().replace('.', '/') + "$")) {
/* 1105:1322 */           this.hasInnerClass = true;
/* 1106:     */         }
/* 1107:     */       }
/* 1108:     */     }
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   private static String tostring(Node n)
/* 1112:     */   {
/* 1113:1332 */     return new StringRepresentation(n).toString();
/* 1114:     */   }
/* 1115:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.statics.Pass2Verifier
 * JD-Core Version:    0.7.0.1
 */