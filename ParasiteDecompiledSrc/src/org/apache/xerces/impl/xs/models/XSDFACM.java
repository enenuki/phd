package org.apache.xerces.impl.xs.models;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Vector;
import org.apache.xerces.impl.dtd.models.CMNode;
import org.apache.xerces.impl.dtd.models.CMStateSet;
import org.apache.xerces.impl.xs.SubstitutionGroupHandler;
import org.apache.xerces.impl.xs.XMLSchemaException;
import org.apache.xerces.impl.xs.XSConstraints;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.XSWildcardDecl;
import org.apache.xerces.xni.QName;

public class XSDFACM
  implements XSCMValidator
{
  private static final boolean DEBUG = false;
  private static final boolean DEBUG_VALIDATE_CONTENT = false;
  private Object[] fElemMap = null;
  private int[] fElemMapType = null;
  private int[] fElemMapId = null;
  private int fElemMapSize = 0;
  private boolean[] fFinalStateFlags = null;
  private CMStateSet[] fFollowList = null;
  private CMNode fHeadNode = null;
  private int fLeafCount = 0;
  private XSCMLeaf[] fLeafList = null;
  private int[] fLeafListType = null;
  private int[][] fTransTable = null;
  private Occurence[] fCountingStates = null;
  private int fTransTableSize = 0;
  private boolean fIsCompactedForUPA;
  private static long time = 0L;
  
  public XSDFACM(CMNode paramCMNode, int paramInt)
  {
    this.fLeafCount = paramInt;
    this.fIsCompactedForUPA = paramCMNode.isCompactedForUPA();
    buildDFA(paramCMNode);
  }
  
  public boolean isFinalState(int paramInt)
  {
    return paramInt < 0 ? 0 : this.fFinalStateFlags[paramInt];
  }
  
  public Object oneTransition(QName paramQName, int[] paramArrayOfInt, SubstitutionGroupHandler paramSubstitutionGroupHandler)
  {
    int i = paramArrayOfInt[0];
    if ((i == -1) || (i == -2))
    {
      if (i == -1) {
        paramArrayOfInt[0] = -2;
      }
      return findMatchingDecl(paramQName, paramSubstitutionGroupHandler);
    }
    int j = 0;
    int k = 0;
    Object localObject = null;
    while (k < this.fElemMapSize)
    {
      j = this.fTransTable[i][k];
      if (j != -1)
      {
        int m = this.fElemMapType[k];
        if (m == 1)
        {
          localObject = paramSubstitutionGroupHandler.getMatchingElemDecl(paramQName, (XSElementDecl)this.fElemMap[k]);
          if (localObject != null) {
            break;
          }
        }
        else if ((m == 2) && (((XSWildcardDecl)this.fElemMap[k]).allowNamespace(paramQName.uri)))
        {
          localObject = this.fElemMap[k];
          break;
        }
      }
      k++;
    }
    if (k == this.fElemMapSize)
    {
      paramArrayOfInt[1] = paramArrayOfInt[0];
      paramArrayOfInt[0] = -1;
      return findMatchingDecl(paramQName, paramSubstitutionGroupHandler);
    }
    if (this.fCountingStates != null)
    {
      Occurence localOccurence = this.fCountingStates[i];
      if (localOccurence != null)
      {
        if (i == j)
        {
          if ((paramArrayOfInt[2] += 1 > localOccurence.maxOccurs) && (localOccurence.maxOccurs != -1)) {
            return findMatchingDecl(paramQName, paramArrayOfInt, paramSubstitutionGroupHandler, k);
          }
        }
        else
        {
          if (paramArrayOfInt[2] < localOccurence.minOccurs)
          {
            paramArrayOfInt[1] = paramArrayOfInt[0];
            paramArrayOfInt[0] = -1;
            return findMatchingDecl(paramQName, paramSubstitutionGroupHandler);
          }
          localOccurence = this.fCountingStates[j];
          if (localOccurence != null) {
            paramArrayOfInt[2] = (k == localOccurence.elemIndex ? 1 : 0);
          }
        }
      }
      else
      {
        localOccurence = this.fCountingStates[j];
        if (localOccurence != null) {
          paramArrayOfInt[2] = (k == localOccurence.elemIndex ? 1 : 0);
        }
      }
    }
    paramArrayOfInt[0] = j;
    return localObject;
  }
  
  Object findMatchingDecl(QName paramQName, SubstitutionGroupHandler paramSubstitutionGroupHandler)
  {
    XSElementDecl localXSElementDecl = null;
    for (int i = 0; i < this.fElemMapSize; i++)
    {
      int j = this.fElemMapType[i];
      if (j == 1)
      {
        localXSElementDecl = paramSubstitutionGroupHandler.getMatchingElemDecl(paramQName, (XSElementDecl)this.fElemMap[i]);
        if (localXSElementDecl != null) {
          return localXSElementDecl;
        }
      }
      else if ((j == 2) && (((XSWildcardDecl)this.fElemMap[i]).allowNamespace(paramQName.uri)))
      {
        return this.fElemMap[i];
      }
    }
    return null;
  }
  
  Object findMatchingDecl(QName paramQName, int[] paramArrayOfInt, SubstitutionGroupHandler paramSubstitutionGroupHandler, int paramInt)
  {
    int i = paramArrayOfInt[0];
    int j = 0;
    Object localObject = null;
    do
    {
      j = this.fTransTable[i][paramInt];
      if (j != -1)
      {
        int k = this.fElemMapType[paramInt];
        if (k == 1)
        {
          localObject = paramSubstitutionGroupHandler.getMatchingElemDecl(paramQName, (XSElementDecl)this.fElemMap[paramInt]);
          if (localObject != null) {
            break;
          }
        }
        else if ((k == 2) && (((XSWildcardDecl)this.fElemMap[paramInt]).allowNamespace(paramQName.uri)))
        {
          localObject = this.fElemMap[paramInt];
          break;
        }
      }
      paramInt++;
    } while (paramInt < this.fElemMapSize);
    if (paramInt == this.fElemMapSize)
    {
      paramArrayOfInt[1] = paramArrayOfInt[0];
      paramArrayOfInt[0] = -1;
      return findMatchingDecl(paramQName, paramSubstitutionGroupHandler);
    }
    paramArrayOfInt[0] = j;
    Occurence localOccurence = this.fCountingStates[j];
    if (localOccurence != null) {
      paramArrayOfInt[2] = (paramInt == localOccurence.elemIndex ? 1 : 0);
    }
    return localObject;
  }
  
  public int[] startContentModel()
  {
    return new int[3];
  }
  
  public boolean endContentModel(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    if (this.fFinalStateFlags[i] != 0)
    {
      if (this.fCountingStates != null)
      {
        Occurence localOccurence = this.fCountingStates[i];
        if ((localOccurence != null) && (paramArrayOfInt[2] < localOccurence.minOccurs)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  private void buildDFA(CMNode paramCMNode)
  {
    int i = this.fLeafCount;
    XSCMLeaf localXSCMLeaf1 = new XSCMLeaf(1, null, -1, this.fLeafCount++);
    this.fHeadNode = new XSCMBinOp(102, paramCMNode, localXSCMLeaf1);
    this.fLeafList = new XSCMLeaf[this.fLeafCount];
    this.fLeafListType = new int[this.fLeafCount];
    postTreeBuildInit(this.fHeadNode);
    this.fFollowList = new CMStateSet[this.fLeafCount];
    for (int j = 0; j < this.fLeafCount; j++) {
      this.fFollowList[j] = new CMStateSet(this.fLeafCount);
    }
    calcFollowList(this.fHeadNode);
    this.fElemMap = new Object[this.fLeafCount];
    this.fElemMapType = new int[this.fLeafCount];
    this.fElemMapId = new int[this.fLeafCount];
    this.fElemMapSize = 0;
    Occurence[] arrayOfOccurence = null;
    for (int k = 0; k < this.fLeafCount; k++)
    {
      this.fElemMap[k] = null;
      int m = 0;
      n = this.fLeafList[k].getParticleId();
      while (m < this.fElemMapSize)
      {
        if (n == this.fElemMapId[m]) {
          break;
        }
        m++;
      }
      if (m == this.fElemMapSize)
      {
        XSCMLeaf localXSCMLeaf2 = this.fLeafList[k];
        this.fElemMap[this.fElemMapSize] = localXSCMLeaf2.getLeaf();
        if ((localXSCMLeaf2 instanceof XSCMRepeatingLeaf))
        {
          if (arrayOfOccurence == null) {
            arrayOfOccurence = new Occurence[this.fLeafCount];
          }
          arrayOfOccurence[this.fElemMapSize] = new Occurence((XSCMRepeatingLeaf)localXSCMLeaf2, this.fElemMapSize);
        }
        this.fElemMapType[this.fElemMapSize] = this.fLeafListType[k];
        this.fElemMapId[this.fElemMapSize] = n;
        this.fElemMapSize += 1;
      }
    }
    this.fElemMapSize -= 1;
    int[] arrayOfInt1 = new int[this.fLeafCount + this.fElemMapSize];
    int n = 0;
    for (int i1 = 0; i1 < this.fElemMapSize; i1++)
    {
      i2 = this.fElemMapId[i1];
      for (int i3 = 0; i3 < this.fLeafCount; i3++) {
        if (i2 == this.fLeafList[i3].getParticleId()) {
          arrayOfInt1[(n++)] = i3;
        }
      }
      arrayOfInt1[(n++)] = -1;
    }
    int i2 = this.fLeafCount * 4;
    Object localObject1 = new CMStateSet[i2];
    this.fFinalStateFlags = new boolean[i2];
    this.fTransTable = new int[i2][];
    CMStateSet localCMStateSet = this.fHeadNode.firstPos();
    int i4 = 0;
    int i5 = 0;
    this.fTransTable[i5] = makeDefStateList();
    localObject1[i5] = localCMStateSet;
    i5++;
    HashMap localHashMap = new HashMap();
    Object localObject2;
    int i7;
    while (i4 < i5)
    {
      localCMStateSet = localObject1[i4];
      int[] arrayOfInt2 = this.fTransTable[i4];
      this.fFinalStateFlags[i4] = localCMStateSet.getBit(i);
      i4++;
      localObject2 = null;
      i7 = 0;
      for (int i8 = 0; i8 < this.fElemMapSize; i8++)
      {
        if (localObject2 == null) {
          localObject2 = new CMStateSet(this.fLeafCount);
        } else {
          ((CMStateSet)localObject2).zeroBits();
        }
        for (int i9 = arrayOfInt1[(i7++)]; i9 != -1; i9 = arrayOfInt1[(i7++)]) {
          if (localCMStateSet.getBit(i9)) {
            ((CMStateSet)localObject2).union(this.fFollowList[i9]);
          }
        }
        if (!((CMStateSet)localObject2).isEmpty())
        {
          Integer localInteger = (Integer)localHashMap.get(localObject2);
          int i10 = localInteger == null ? i5 : localInteger.intValue();
          if (i10 == i5)
          {
            localObject1[i5] = localObject2;
            this.fTransTable[i5] = makeDefStateList();
            localHashMap.put(localObject2, new Integer(i5));
            i5++;
            localObject2 = null;
          }
          arrayOfInt2[i8] = i10;
          if (i5 == i2)
          {
            int i11 = (int)(i2 * 1.5D);
            CMStateSet[] arrayOfCMStateSet = new CMStateSet[i11];
            boolean[] arrayOfBoolean = new boolean[i11];
            int[][] arrayOfInt = new int[i11][];
            System.arraycopy(localObject1, 0, arrayOfCMStateSet, 0, i2);
            System.arraycopy(this.fFinalStateFlags, 0, arrayOfBoolean, 0, i2);
            System.arraycopy(this.fTransTable, 0, arrayOfInt, 0, i2);
            i2 = i11;
            localObject1 = arrayOfCMStateSet;
            this.fFinalStateFlags = arrayOfBoolean;
            this.fTransTable = arrayOfInt;
          }
        }
      }
    }
    if (arrayOfOccurence != null)
    {
      this.fCountingStates = new Occurence[i5];
      for (int i6 = 0; i6 < i5; i6++)
      {
        localObject2 = this.fTransTable[i6];
        for (i7 = 0; i7 < localObject2.length; i7++) {
          if (i6 == localObject2[i7])
          {
            this.fCountingStates[i6] = arrayOfOccurence[i7];
            break;
          }
        }
      }
    }
    this.fHeadNode = null;
    this.fLeafList = null;
    this.fFollowList = null;
    this.fLeafListType = null;
    this.fElemMapId = null;
  }
  
  private void calcFollowList(CMNode paramCMNode)
  {
    if (paramCMNode.type() == 101)
    {
      calcFollowList(((XSCMBinOp)paramCMNode).getLeft());
      calcFollowList(((XSCMBinOp)paramCMNode).getRight());
    }
    else
    {
      CMStateSet localCMStateSet1;
      CMStateSet localCMStateSet2;
      int i;
      if (paramCMNode.type() == 102)
      {
        calcFollowList(((XSCMBinOp)paramCMNode).getLeft());
        calcFollowList(((XSCMBinOp)paramCMNode).getRight());
        localCMStateSet1 = ((XSCMBinOp)paramCMNode).getLeft().lastPos();
        localCMStateSet2 = ((XSCMBinOp)paramCMNode).getRight().firstPos();
        for (i = 0; i < this.fLeafCount; i++) {
          if (localCMStateSet1.getBit(i)) {
            this.fFollowList[i].union(localCMStateSet2);
          }
        }
      }
      else if ((paramCMNode.type() == 4) || (paramCMNode.type() == 6))
      {
        calcFollowList(((XSCMUniOp)paramCMNode).getChild());
        localCMStateSet1 = paramCMNode.firstPos();
        localCMStateSet2 = paramCMNode.lastPos();
        for (i = 0; i < this.fLeafCount; i++) {
          if (localCMStateSet2.getBit(i)) {
            this.fFollowList[i].union(localCMStateSet1);
          }
        }
      }
      else if (paramCMNode.type() == 5)
      {
        calcFollowList(((XSCMUniOp)paramCMNode).getChild());
      }
    }
  }
  
  private void dumpTree(CMNode paramCMNode, int paramInt)
  {
    for (int i = 0; i < paramInt; i++) {
      System.out.print("   ");
    }
    int j = paramCMNode.type();
    switch (j)
    {
    case 101: 
    case 102: 
      if (j == 101) {
        System.out.print("Choice Node ");
      } else {
        System.out.print("Seq Node ");
      }
      if (paramCMNode.isNullable()) {
        System.out.print("Nullable ");
      }
      System.out.print("firstPos=");
      System.out.print(paramCMNode.firstPos().toString());
      System.out.print(" lastPos=");
      System.out.println(paramCMNode.lastPos().toString());
      dumpTree(((XSCMBinOp)paramCMNode).getLeft(), paramInt + 1);
      dumpTree(((XSCMBinOp)paramCMNode).getRight(), paramInt + 1);
      break;
    case 4: 
    case 5: 
    case 6: 
      System.out.print("Rep Node ");
      if (paramCMNode.isNullable()) {
        System.out.print("Nullable ");
      }
      System.out.print("firstPos=");
      System.out.print(paramCMNode.firstPos().toString());
      System.out.print(" lastPos=");
      System.out.println(paramCMNode.lastPos().toString());
      dumpTree(((XSCMUniOp)paramCMNode).getChild(), paramInt + 1);
      break;
    case 1: 
      System.out.print("Leaf: (pos=" + ((XSCMLeaf)paramCMNode).getPosition() + "), " + "(elemIndex=" + ((XSCMLeaf)paramCMNode).getLeaf() + ") ");
      if (paramCMNode.isNullable()) {
        System.out.print(" Nullable ");
      }
      System.out.print("firstPos=");
      System.out.print(paramCMNode.firstPos().toString());
      System.out.print(" lastPos=");
      System.out.println(paramCMNode.lastPos().toString());
      break;
    case 2: 
      System.out.print("Any Node: ");
      System.out.print("firstPos=");
      System.out.print(paramCMNode.firstPos().toString());
      System.out.print(" lastPos=");
      System.out.println(paramCMNode.lastPos().toString());
      break;
    default: 
      throw new RuntimeException("ImplementationMessages.VAL_NIICM");
    }
  }
  
  private int[] makeDefStateList()
  {
    int[] arrayOfInt = new int[this.fElemMapSize];
    for (int i = 0; i < this.fElemMapSize; i++) {
      arrayOfInt[i] = -1;
    }
    return arrayOfInt;
  }
  
  private void postTreeBuildInit(CMNode paramCMNode)
    throws RuntimeException
  {
    paramCMNode.setMaxStates(this.fLeafCount);
    XSCMLeaf localXSCMLeaf = null;
    int i = 0;
    if (paramCMNode.type() == 2)
    {
      localXSCMLeaf = (XSCMLeaf)paramCMNode;
      i = localXSCMLeaf.getPosition();
      this.fLeafList[i] = localXSCMLeaf;
      this.fLeafListType[i] = 2;
    }
    else if ((paramCMNode.type() == 101) || (paramCMNode.type() == 102))
    {
      postTreeBuildInit(((XSCMBinOp)paramCMNode).getLeft());
      postTreeBuildInit(((XSCMBinOp)paramCMNode).getRight());
    }
    else if ((paramCMNode.type() == 4) || (paramCMNode.type() == 6) || (paramCMNode.type() == 5))
    {
      postTreeBuildInit(((XSCMUniOp)paramCMNode).getChild());
    }
    else if (paramCMNode.type() == 1)
    {
      localXSCMLeaf = (XSCMLeaf)paramCMNode;
      i = localXSCMLeaf.getPosition();
      this.fLeafList[i] = localXSCMLeaf;
      this.fLeafListType[i] = 1;
    }
    else
    {
      throw new RuntimeException("ImplementationMessages.VAL_NIICM");
    }
  }
  
  public boolean checkUniqueParticleAttribution(SubstitutionGroupHandler paramSubstitutionGroupHandler)
    throws XMLSchemaException
  {
    byte[][] arrayOfByte = new byte[this.fElemMapSize][this.fElemMapSize];
    Object localObject;
    for (int i = 0; (i < this.fTransTable.length) && (this.fTransTable[i] != null); i++) {
      for (j = 0; j < this.fElemMapSize; j++) {
        for (k = j + 1; k < this.fElemMapSize; k++) {
          if ((this.fTransTable[i][j] != -1) && (this.fTransTable[i][k] != -1) && (arrayOfByte[j][k] == 0)) {
            if (XSConstraints.overlapUPA(this.fElemMap[j], this.fElemMap[k], paramSubstitutionGroupHandler))
            {
              if (this.fCountingStates != null)
              {
                localObject = this.fCountingStates[i];
                if (localObject != null) {
                  if ((((this.fTransTable[i][j] == i ? 1 : 0) ^ (this.fTransTable[i][k] == i ? 1 : 0)) != 0) && (((Occurence)localObject).minOccurs == ((Occurence)localObject).maxOccurs))
                  {
                    arrayOfByte[j][k] = -1;
                    continue;
                  }
                }
              }
              arrayOfByte[j][k] = 1;
            }
            else
            {
              arrayOfByte[j][k] = -1;
            }
          }
        }
      }
    }
    for (int j = 0; j < this.fElemMapSize; j++) {
      for (k = 0; k < this.fElemMapSize; k++) {
        if (arrayOfByte[j][k] == 1) {
          throw new XMLSchemaException("cos-nonambig", new Object[] { this.fElemMap[j].toString(), this.fElemMap[k].toString() });
        }
      }
    }
    for (int k = 0; k < this.fElemMapSize; k++) {
      if (this.fElemMapType[k] == 2)
      {
        localObject = (XSWildcardDecl)this.fElemMap[k];
        if ((((XSWildcardDecl)localObject).fType == 3) || (((XSWildcardDecl)localObject).fType == 2)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public Vector whatCanGoHere(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    if (i < 0) {
      i = paramArrayOfInt[1];
    }
    Object localObject = this.fCountingStates != null ? this.fCountingStates[i] : null;
    int j = paramArrayOfInt[2];
    Vector localVector = new Vector();
    for (int k = 0; k < this.fElemMapSize; k++)
    {
      int m = this.fTransTable[i][k];
      if ((m != -1) && ((localObject == null) || (i == m ? (j < localObject.maxOccurs) && (localObject.maxOccurs == -1) : j >= localObject.minOccurs))) {
        localVector.addElement(this.fElemMap[k]);
      }
    }
    return localVector;
  }
  
  public boolean isCompactedForUPA()
  {
    return this.fIsCompactedForUPA;
  }
  
  static final class Occurence
  {
    final int minOccurs;
    final int maxOccurs;
    final int elemIndex;
    
    public Occurence(XSCMRepeatingLeaf paramXSCMRepeatingLeaf, int paramInt)
    {
      this.minOccurs = paramXSCMRepeatingLeaf.getMinOccurs();
      this.maxOccurs = paramXSCMRepeatingLeaf.getMaxOccurs();
      this.elemIndex = paramInt;
    }
    
    public String toString()
    {
      return "minOccurs=" + this.minOccurs + ";maxOccurs=" + (this.maxOccurs != -1 ? Integer.toString(this.maxOccurs) : "unbounded");
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.models.XSDFACM
 * JD-Core Version:    0.7.0.1
 */