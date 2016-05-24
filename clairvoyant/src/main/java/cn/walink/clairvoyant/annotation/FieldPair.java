package cn.walink.clairvoyant.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 作用：
 * 创建者：陈佳润
 * 创建日期：16/5/11
 * 更新历史：
 */
public class FieldPair implements Comparable<FieldPair> {

    private Field field;

    private LookType lookType;

    private Integer seq;

    private Class cls;

    private String input;

    private Annotation annotation;


    public FieldPair(Field field, LookType lookType, Integer seq, Class cls, String input, Annotation annotation) {
        this.field = field;
        this.lookType = lookType;
        this.seq = seq;
        this.cls = cls;
        this.input = input;
        this.annotation = annotation;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }


    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Field getField() {

        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public LookType getLookType() {
        return lookType;
    }

    public void setLookType(LookType lookType) {
        this.lookType = lookType;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    @Override
    public int compareTo(FieldPair another) {
        return this.getSeq().compareTo(another.getSeq());
    }
}
