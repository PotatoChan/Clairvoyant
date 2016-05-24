package cn.walink.clairvoyant.trigger;

import android.content.Context;
import android.widget.EditText;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.walink.clairvoyant.annotation.FieldPair;
import cn.walink.clairvoyant.annotation.LookType;
import cn.walink.clairvoyant.annotation.Mobile;
import cn.walink.clairvoyant.annotation.NotNull;
import cn.walink.clairvoyant.annotation.Pattern;
import cn.walink.clairvoyant.exception.ClairvoyantException;
import cn.walink.clairvoyant.listener.ClairvoyantListener;


/**
 * 作用：
 * 创建者：陈佳润
 * 创建日期：16/5/11
 * 更新历史：
 */
public class Clairvoyant {

    private Context context;
    private ClairvoyantListener listener;

    public Clairvoyant(Context context, ClairvoyantListener listener) {
        this.context = context;
        this.listener = listener;
    }

    Class anno[] = {Mobile.class, NotNull.class};


    public synchronized void look() throws ClairvoyantException {
        look(0);
    }

    public synchronized void look(int group) throws ClairvoyantException {

        Class clazz = context.getClass();
        Field[] fields = clazz.getDeclaredFields();

        //对field进行装载
        List<FieldPair> fieldPairList = loadFieldPair(fields, group);

        //对 field 进行排序
        sort(fieldPairList);

        //进行检测
        look(fieldPairList);

    }

    private void look(List<FieldPair> fieldPairList) throws ClairvoyantException {

        //对 filed 进行处理
        for (FieldPair fieldPair : fieldPairList) {


            switch (fieldPair.getLookType()) {
                case Mobile:
                    lookMobile(fieldPair);
                    break;

                case NotNull:

                    lookNotNull(fieldPair);
                    break;

                case Pattern:
                    lookPattern(fieldPair);
                    break;
            }
        }
    }


    /**
     * 装载字段对象
     *
     * @param fields
     * @return
     */
    private List<FieldPair> loadFieldPair(Field[] fields, Integer group) {
        List<FieldPair> fieldPairList = new ArrayList<>();

        //对field进行装载
        for (Field field : fields) {

            for (LookType type : LookType.values()) {

                try {
                    if (field.isAnnotationPresent(type.getCls())) {

                        field.setAccessible(true);

                        Annotation annotation = field.getAnnotation(type.getCls());

                        //判断是否指定分组
                        Method methodGroup = type.getCls().getMethod("group");
                        int[] resultGroup = (int[]) methodGroup.invoke((type.getCls().cast(annotation)));

                        boolean flag = false;

                        for (int i : resultGroup) {

                            if (i == group) {
                                flag = true;
                                break;
                            }
                        }

                        if (!flag) {
                            continue;
                        }

                        //获取组件的顺序
                        Method method = type.getCls().getMethod("seq");
                        Integer seq = (Integer) method.invoke((type.getCls().cast(annotation)));

                        // 获取组件的输入
                        String input = getInput(field);

                        //添加字段对象到数组中
                        FieldPair fieldPair = new FieldPair(field, type, seq, type.getCls(), input, annotation);

                        fieldPairList.add(fieldPair);

                        field.setAccessible(false);

                        continue;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return fieldPairList;
    }


    /**
     * 进行排序
     *
     * @param fieldPairList
     */
    private void sort(List<FieldPair> fieldPairList) {
        Collections.sort(fieldPairList);
    }

    /**
     * 对电话号码进行检测
     *
     * @param fieldPair
     * @throws Exception
     */
    private void lookMobile(FieldPair fieldPair) throws ClairvoyantException {

        Mobile mobile = (Mobile) fieldPair.getAnnotation();
        String input = fieldPair.getInput();

        if (input == null || "".equals(input)) {
            listener.inputError("请填写您的手机号码");
            throw new ClairvoyantException("请填写您的手机号码");
        }

        if (input.length() != 11) {
            listener.inputError("手机号码必须为11位");
            throw new ClairvoyantException("手机号码必须为11位");
        }
    }

    /**
     * 校验非空输入
     *
     * @param fieldPair
     * @throws Exception
     */
    private void lookNotNull(FieldPair fieldPair) throws ClairvoyantException {
        NotNull notNull = (NotNull) fieldPair.getAnnotation();
        String input = fieldPair.getInput();

        if (input == null || "".equals(input)) {
            listener.inputError(notNull.msg());
            throw new ClairvoyantException(notNull.msg());
        }
    }

    /**
     * 正则表达式校验输入
     *
     * @param fieldPair
     * @throws Exception
     */
    private void lookPattern(FieldPair fieldPair) throws ClairvoyantException {

        Pattern pattern = (Pattern) fieldPair.getAnnotation();
        String input = fieldPair.getInput();

        if (!input.matches(pattern.pattern())) {
            listener.inputError(pattern.msg());
            throw new ClairvoyantException(pattern.msg());
        }

    }


    /**
     * 获取输入内容
     *
     * @param field
     * @return
     * @throws Exception
     */
    private String getInput(Field field) throws Exception {
        EditText editText = (EditText) field.get(context);
        return editText.getText().toString();
    }


}
