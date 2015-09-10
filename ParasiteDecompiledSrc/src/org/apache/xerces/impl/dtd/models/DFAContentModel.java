package org.apache.xerces.impl.dtd.models;

import java.io.PrintStream;
import java.util.HashMap;
import org.apache.xerces.xni.QName;

public class DFAContentModel
  implements ContentModelValidator
{
  private static String fEpsilonString = fEpsilonString.intern();
  private static String fEOCString = fEOCString.intern();
  private static final boolean DEBUG_VALIDATE_CONTENT = false;
  private QName[] fElemMap = null;
  private int[] fElemMapType = null;
  private int fElemMapSize = 0;
  private boolean fMixed;
  private int fEOCPos = 0;
  private boolean[] fFinalStateFlags = null;
  private CMStateSet[] fFollowList = null;
  private CMNode fHeadNode = null;
  private int fLeafCount = 0;
  private CMLeaf[] fLeafList = null;
  private int[] fLeafListType = null;
  private int[][] fTransTable = null;
  private int fTransTableSize = 0;
  private boolean fEmptyContentIsValid = false;
  private final QName fQName = new QName();
  
  public DFAContentModel(CMNode paramCMNode, int paramInt, boolean paramBoolean)
  {
    this.fLeafCount = paramInt;
    this.fMixed = paramBoolean;
    buildDFA(paramCMNode);
  }
  
  public int validate(QName[] paramArrayOfQName, int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0) {
      return this.fEmptyContentIsValid ? -1 : 0;
    }
    int i = 0;
    for (int j = 0; j < paramInt2; j++)
    {
      QName localQName = paramArrayOfQName[(paramInt1 + j)];
      if ((!this.fMixed) || (localQName.localpart != null))
      {
        for (int k = 0; k < this.fElemMapSize; k++)
        {
          int m = this.fElemMapType[k] & 0xF;
          if (m == 0)
          {
            if (this.fElemMap[k].rawname == localQName.rawname) {
              break;
            }
          }
          else if (m == 6)
          {
            String str = this.fElemMap[k].uri;
            if (str == null) {
              break;
            }
            if (str == localQName.uri) {
              break;
            }
          }
          else
          {
            if (m == 8 ? localQName.uri != null : (m == 7) && (this.fElemMap[k].uri != localQName.uri)) {
              break;
            }
          }
        }
        if (k == this.fElemMapSize) {
          return j;
        }
        i = this.fTransTable[i][k];
        if (i == -1) {
          return j;
        }
      }
    }
    if (this.fFinalStateFlags[i] == 0) {
      return paramInt2;
    }
    return -1;
  }
  
  private void buildDFA(CMNode paramCMNode)
  {
    this.fQName.setValues(null, fEOCString, fEOCString, null);
    CMLeaf localCMLeaf = new CMLeaf(this.fQName);
    this.fHeadNode = new CMBinOp(5, paramCMNode, localCMLeaf);
    this.fEOCPos = this.fLeafCount;
    localCMLeaf.setPosition(this.fLeafCount++);
    this.fLeafList = new CMLeaf[this.fLeafCount];
    this.fLeafListType = new int[this.fLeafCount];
    postTreeBuildInit(this.fHeadNode, 0);
    this.fFollowList = new CMStateSet[this.fLeafCount];
    for (int i = 0; i < this.fLeafCount; i++) {
      this.fFollowList[i] = new CMStateSet(this.fLeafCount);
    }
    calcFollowList(this.fHeadNode);
    this.fElemMap = new QName[this.fLeafCount];
    this.fElemMapType = new int[this.fLeafCount];
    this.fElemMapSize = 0;
    for (int j = 0; j < this.fLeafCount; j++)
    {
      this.fElemMap[j] = new QName();
      localObject1 = this.fLeafList[j].getElement();
      for (k = 0; k < this.fElemMapSize; k++) {
        if (this.fElemMap[k].rawname == ((QName)localObject1).rawname) {
          break;
        }
      }
      if (k == this.fElemMapSize)
      {
        this.fElemMap[this.fElemMapSize].setValues((QName)localObject1);
        this.fElemMapType[this.fElemMapSize] = this.fLeafListType[j];
        this.fElemMapSize += 1;
      }
    }
    Object localObject1 = new int[this.fLeafCount + this.fElemMapSize];
    int k = 0;
    for (int m = 0; m < this.fElemMapSize; m++)
    {
      for (n = 0; n < this.fLeafCount; n++)
      {
        localObject2 = this.fLeafList[n].getElement();
        localObject3 = this.fElemMap[m];
        if (((QName)localObject2).rawname == ((QName)localObject3).rawname) {
          localObject1[(k++)] = n;
        }
      }
      localObject1[(k++)] = -1;
    }
    int n = this.fLeafCount * 4;
    Object localObject2 = new CMStateSet[n];
    this.fFinalStateFlags = new boolean[n];
    this.fTransTable = new int[n][];
    Object localObject3 = this.fHeadNode.firstPos();
    int i1 = 0;
    int i2 = 0;
    this.fTransTable[i2] = makeDefStateList();
    localObject2[i2] = localObject3;
    i2++;
    HashMap localHashMap = new HashMap();
    while (i1 < i2)
    {
      localObject3 = localObject2[i1];
      int[] arrayOfInt = this.fTransTable[i1];
      this.fFinalStateFlags[i1] = ((CMStateSet)localObject3).getBit(this.fEOCPos);
      i1++;
      CMStateSet localCMStateSet = null;
      int i3 = 0;
      for (int i4 = 0; i4 < this.fElemMapSize; i4++)
      {
        if (localCMStateSet == null) {
          localCMStateSet = new CMStateSet(this.fLeafCount);
        } else {
          localCMStateSet.zeroBits();
        }
        for (int i5 = localObject1[(i3++)]; i5 != -1; i5 = localObject1[(i3++)]) {
          if (((CMStateSet)localObject3).getBit(i5)) {
            localCMStateSet.union(this.fFollowList[i5]);
          }
        }
        if (!localCMStateSet.isEmpty())
        {
          Integer localInteger = (Integer)localHashMap.get(localCMStateSet);
          int i6 = localInteger == null ? i2 : localInteger.intValue();
          if (i6 == i2)
          {
            localObject2[i2] = localCMStateSet;
            this.fTransTable[i2] = makeDefStateList();
            localHashMap.put(localCMStateSet, new Integer(i2));
            i2++;
            localCMStateSet = null;
          }
          arrayOfInt[i4] = i6;
          if (i2 == n)
          {
            int i7 = (int)(n * 1.5D);
            CMStateSet[] arrayOfCMStateSet = new CMStateSet[i7];
            boolean[] arrayOfBoolean = new boolean[i7];
            int[][] arrayOfInt1 = new int[i7][];
            System.arraycopy(localObject2, 0, arrayOfCMStateSet, 0, n);
            System.arraycopy(this.fFinalStateFlags, 0, arrayOfBoolean, 0, n);
            System.arraycopy(this.fTransTable, 0, arrayOfInt1, 0, n);
            n = i7;
            localObject2 = arrayOfCMStateSet;
            this.fFinalStateFlags = arrayOfBoolean;
            this.fTransTable = arrayOfInt1;
          }
        }
      }
    }
    this.fEmptyContentIsValid = ((CMBinOp)this.fHeadNode).getLeft().isNullable();
    this.fHeadNode = null;
    this.fLeafList = null;
    this.fFollowList = null;
  }
  
  private void calcFollowList(CMNode paramCMNode)
  {
    if (paramCMNode.type() == 4)
    {
      calcFollowList(((CMBinOp)paramCMNode).getLeft());
      calcFollowList(((CMBinOp)paramCMNode).getRight());
    }
    else
    {
      CMStateSet localCMStateSet1;
      CMStateSet localCMStateSet2;
      int i;
      if (paramCMNode.type() == 5)
      {
        calcFollowList(((CMBinOp)paramCMNode).getLeft());
        calcFollowList(((CMBinOp)paramCMNode).getRight());
        localCMStateSet1 = ((CMBinOp)paramCMNode).getLeft().lastPos();
        localCMStateSet2 = ((CMBinOp)paramCMNode).getRight().firstPos();
        for (i = 0; i < this.fLeafCount; i++) {
          if (localCMStateSet1.getBit(i)) {
            this.fFollowList[i].union(localCMStateSet2);
          }
        }
      }
      else if ((paramCMNode.type() == 2) || (paramCMNode.type() == 3))
      {
        calcFollowList(((CMUniOp)paramCMNode).getChild());
        localCMStateSet1 = paramCMNode.firstPos();
        localCMStateSet2 = paramCMNode.lastPos();
        for (i = 0; i < this.fLeafCount; i++) {
          if (localCMStateSet2.getBit(i)) {
            this.fFollowList[i].union(localCMStateSet1);
          }
        }
      }
      else if (paramCMNode.type() == 1)
      {
        calcFollowList(((CMUniOp)paramCMNode).getChild());
      }
    }
  }
  
  private void dumpTree(CMNode paramCMNode, int paramInt)
  {
    for (int i = 0; i < paramInt; i++) {
      System.out.print("   ");
    }
    int j = paramCMNode.type();
    if ((j == 4) || (j == 5))
    {
      if (j == 4) {
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
      dumpTree(((CMBinOp)paramCMNode).getLeft(), paramInt + 1);
      dumpTree(((CMBinOp)paramCMNode).getRight(), paramInt + 1);
    }
    else if (paramCMNode.type() == 2)
    {
      System.out.print("Rep Node ");
      if (paramCMNode.isNullable()) {
        System.out.print("Nullable ");
      }
      System.out.print("firstPos=");
      System.out.print(paramCMNode.firstPos().toString());
      System.out.print(" lastPos=");
      System.out.println(paramCMNode.lastPos().toString());
      dumpTree(((CMUniOp)paramCMNode).getChild(), paramInt + 1);
    }
    else if (paramCMNode.type() == 0)
    {
      System.out.print("Leaf: (pos=" + ((CMLeaf)paramCMNode).getPosition() + "), " + ((CMLeaf)paramCMNode).getElement() + "(elemIndex=" + ((CMLeaf)paramCMNode).getElement() + ") ");
      if (paramCMNode.isNullable()) {
        System.out.print(" Nullable ");
      }
      System.out.print("firstPos=");
      System.out.print(paramCMNode.firstPos().toString());
      System.out.print(" lastPos=");
      System.out.println(paramCMNode.lastPos().toString());
    }
    else
    {
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
  
  private int postTreeBuildInit(CMNode paramCMNode, int paramInt)
  {
    paramCMNode.setMaxStates(this.fLeafCount);
    QName localQName;
    if (((paramCMNode.type() & 0xF) == 6) || ((paramCMNode.type() & 0xF) == 8) || ((paramCMNode.type() & 0xF) == 7))
    {
      localQName = new QName(null, null, null, ((CMAny)paramCMNode).getURI());
      this.fLeafList[paramInt] = new CMLeaf(localQName, ((CMAny)paramCMNode).getPosition());
      this.fLeafListType[paramInt] = paramCMNode.type();
      paramInt++;
    }
    else if ((paramCMNode.type() == 4) || (paramCMNode.type() == 5))
    {
      paramInt = postTreeBuildInit(((CMBinOp)paramCMNode).getLeft(), paramInt);
      paramInt = postTreeBuildInit(((CMBinOp)paramCMNode).getRight(), paramInt);
    }
    else if ((paramCMNode.type() == 2) || (paramCMNode.type() == 3) || (paramCMNode.type() == 1))
    {
      paramInt = postTreeBuildInit(((CMUniOp)paramCMNode).getChild(), paramInt);
    }
    else if (paramCMNode.type() == 0)
    {
      localQName = ((CMLeaf)paramCMNode).getElement();
      if (localQName.localpart != fEpsilonString)
      {
        this.fLeafList[paramInt] = ((CMLeaf)paramCMNode);
        this.fLeafListType[paramInt] = 0;
        paramInt++;
      }
    }
    else
    {
      throw new RuntimeException("ImplementationMessages.VAL_NIICM: type=" + paramCMNode.type());
    }
    return paramInt;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dtd.models.DFAContentModel
 * JD-Core Version:    0.7.0.1
 */