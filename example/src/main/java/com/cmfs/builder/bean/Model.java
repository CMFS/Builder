package com.cmfs.builder.bean;

import com.cmfs.builder.InnerModel;
import com.cmfs.builder.annotations.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author merlin
 */

@Builder
public class Model {

    private String str;
    String str2 = "";
    String str3 = null;
    String str4 = "A";

    int i;
    int i2 = 0;
    int i3 = -1;
    int[] is;
    int[] is2 = new int[2];

    long l;
    long l2 = 0L;
    long l3 = 0;
    long l4 = -1L;
    long l5 = 1L;

    char c;
    char c2 = 'a';

    InnerModel model;

    final InnerModel innerModel = new InnerModel();

    InnerModel[] innerModels;

    InnerModel[] innerModels2 = new InnerModel[1];

    List<InnerModel> innerModelList;
    List<? extends InnerModel> innerModelList4;
    List<InnerModel> innerModelList2 = new ArrayList<>();
    List<? extends InnerModel> innerModelList3 = new ArrayList<>();


    double d;

    double d2 = 1;

    float f;

    float f2 = 1.0F;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getStr3() {
        return str3;
    }

    public void setStr3(String str3) {
        this.str3 = str3;
    }

    public String getStr4() {
        return str4;
    }

    public void setStr4(String str4) {
        this.str4 = str4;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getI2() {
        return i2;
    }

    public void setI2(int i2) {
        this.i2 = i2;
    }

    public int getI3() {
        return i3;
    }

    public void setI3(int i3) {
        this.i3 = i3;
    }

    public int[] getIs() {
        return is;
    }

    public void setIs(int[] is) {
        this.is = is;
    }

    public int[] getIs2() {
        return is2;
    }

    public void setIs2(int[] is2) {
        this.is2 = is2;
    }

    public long getL() {
        return l;
    }

    public void setL(long l) {
        this.l = l;
    }

    public long getL2() {
        return l2;
    }

    public void setL2(long l2) {
        this.l2 = l2;
    }

    public long getL3() {
        return l3;
    }

    public void setL3(long l3) {
        this.l3 = l3;
    }

    public long getL4() {
        return l4;
    }

    public void setL4(long l4) {
        this.l4 = l4;
    }

    public long getL5() {
        return l5;
    }

    public void setL5(long l5) {
        this.l5 = l5;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public char getC2() {
        return c2;
    }

    public void setC2(char c2) {
        this.c2 = c2;
    }

    public InnerModel getModel() {
        return model;
    }

    public void setModel(InnerModel model) {
        this.model = model;
    }

    public InnerModel getInnerModel() {
        return innerModel;
    }

    public InnerModel[] getInnerModels() {
        return innerModels;
    }

    public void setInnerModels(InnerModel[] innerModels) {
        this.innerModels = innerModels;
    }

    public InnerModel[] getInnerModels2() {
        return innerModels2;
    }

    public void setInnerModels2(InnerModel[] innerModels2) {
        this.innerModels2 = innerModels2;
    }

    public List<InnerModel> getInnerModelList() {
        return innerModelList;
    }

    public void setInnerModelList(List<InnerModel> innerModelList) {
        this.innerModelList = innerModelList;
    }

    public List<? extends InnerModel> getInnerModelList4() {
        return innerModelList4;
    }

    public void setInnerModelList4(List<? extends InnerModel> innerModelList4) {
        this.innerModelList4 = innerModelList4;
    }

    public List<InnerModel> getInnerModelList2() {
        return innerModelList2;
    }

    public void setInnerModelList2(List<InnerModel> innerModelList2) {
        this.innerModelList2 = innerModelList2;
    }

    public List<? extends InnerModel> getInnerModelList3() {
        return innerModelList3;
    }

    public void setInnerModelList3(List<? extends InnerModel> innerModelList3) {
        this.innerModelList3 = innerModelList3;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getD2() {
        return d2;
    }

    public void setD2(double d2) {
        this.d2 = d2;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public float getF2() {
        return f2;
    }

    public void setF2(float f2) {
        this.f2 = f2;
    }
}
