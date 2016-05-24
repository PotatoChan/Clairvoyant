package cn.walink.clairvoyant.annotation;

/**
 * 作用：
 * 创建者：陈佳润
 * 创建日期：16/5/11
 * 更新历史：
 */
public enum LookType {

    Mobile(Mobile.class), NotNull(NotNull.class), Pattern(Pattern.class);

    private Class cls;

    LookType(Class cls) {
        this.cls = cls;
    }

    public Class getCls() {

        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }
}
