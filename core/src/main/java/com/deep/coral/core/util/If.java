package com.deep.coral.core.util;



import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * <h2>if</h2>
 * 支持嵌套，形如：<br>
 * <pre>
 *     if(){
 *        if(){}
 *     }
 * </pre>
 * 将第二层的if作为一个全新的If。
 * 待补充：else-if
 *
 * @author Create by liuwenhao on 2022/3/27 15:18
 */
@SuppressWarnings("all")
public class If {

    /**
     * 判断语句，默认结果为true
     */
    private BooleanSupplier test;

    /**
     * 头，第一个If
     */
    private If head;

    /**
     * 嵌套if的上一级，没有则为null
     */
    private If parent;

    /**
     * 嵌套if的下一级，没有则为null
     */
    private If children;

    /**
     * 当前If 和children的连接方式，true：and（默认）。false：or
     */
    private Boolean join = Boolean.TRUE;

    // =================================构造=================================

    private If(BooleanSupplier test) {
        this.test = checkTrue(test);
    }

    private If(Boolean test) {
        this.test = () -> checkTrue(test);
    }

    public If child(BooleanSupplier test) {
        return child(test, true);
    }

    public If child(Boolean test) {
        return child(test, true);
    }

    public If child(BooleanSupplier test, Boolean join) {
        this.children = new If(test);
        this.join = join;
        this.children.parent = this;
        this.children.head = this.head;
        return children;
    }

    public If child(Boolean test, Boolean join) {
        this.children = new If(test);
        this.join = join;
        this.children.parent = this;
        this.children.head = this.head;
        return children;
    }

    // =================================逻辑=================================

    /**
     * <h2>和当前谓词语句进行 && 操作</h2>
     * 如果为null 则默认按照true处理
     *
     * @param supplier && 的谓词语句
     * @return com.example.airplane.timer.util.If
     * @author liuwenhao
     * @date 2022/3/27 15:51
     */
    public If and(BooleanSupplier supplier) {
        boolean other = checkTrue(supplier).getAsBoolean();
        boolean self = test.getAsBoolean();
        test = () -> other && self;
        return this;
    }

    /**
     * <h2>和当前谓词语句进行 && 操作</h2>
     * 如果为null 则默认按照true处理
     *
     * @param b && 的判断条件
     * @return com.example.airplane.timer.util.If
     * @author liuwenhao
     * @date 2022/3/27 15:51
     */
    public If and(Boolean b) {
        boolean self = test.getAsBoolean();
        test = () -> checkTrue(b) && self;
        return this;
    }

    /**
     * <h2>和当前谓词语句进行 & 操作</h2>
     * 如果为null 则默认按照true处理
     *
     * @param supplier & 的谓词语句
     * @return com.example.airplane.timer.util.If
     * @author liuwenhao
     * @date 2022/3/27 15:51
     */
    public If andLogic(BooleanSupplier supplier) {
        boolean other = checkTrue(supplier).getAsBoolean();
        boolean self = test.getAsBoolean();
        test = () -> other & self;
        return this;
    }

    /**
     * <h2>和当前谓词语句进行 && 操作</h2>
     * 如果为null 则默认按照true处理
     *
     * @param b && 的判断条件
     * @return com.example.airplane.timer.util.If
     * @author liuwenhao
     * @date 2022/3/27 15:51
     */
    public If andLogic(Boolean b) {
        boolean self = test.getAsBoolean();
        test = () -> checkTrue(b) & self;
        return this;
    }

    /**
     * <h2>和当前谓词语句进行 || 操作</h2>
     * 如果为null 则默认按照false处理
     *
     * @param supplier || 的谓词语句
     * @return com.example.airplane.timer.util.If
     * @author liuwenhao
     * @date 2022/3/27 15:51
     */
    public If or(BooleanSupplier supplier) {
        boolean other = checkFalse(supplier).getAsBoolean();
        boolean self = test.getAsBoolean();
        test = () -> other || self;
        return this;
    }

    /**
     * <h2>和当前谓词语句进行 || 操作</h2>
     * 如果为null 则默认按照false处理
     *
     * @param b || 的判断条件
     * @return com.example.airplane.timer.util.If
     * @author liuwenhao
     * @date 2022/3/27 15:51
     */
    public If or(Boolean b) {
        boolean self = test.getAsBoolean();
        test = () -> checkFalse(b) || self;
        return this;
    }

    /**
     * <h2>和当前谓词语句进行 | 操作</h2>
     * 如果为null 则默认按照false处理
     *
     * @param supplier | 的谓词语句
     * @return com.example.airplane.timer.util.If
     * @author liuwenhao
     * @date 2022/3/27 15:51
     */
    public If orLogic(BooleanSupplier supplier) {
        boolean other = checkFalse(supplier).getAsBoolean();
        boolean self = test.getAsBoolean();
        test = () -> other | self;
        return this;
    }

    /**
     * <h2>和当前谓词语句进行 | 操作</h2>
     * 如果为null 则默认按照false处理
     *
     * @param b | 的判断条件
     * @return com.example.airplane.timer.util.If
     * @author liuwenhao
     * @date 2022/3/27 15:51
     */
    public If orLogic(Boolean b) {
        boolean self = test.getAsBoolean();
        test = () -> checkFalse(b) | self;
        return this;
    }

    // =================================结果=================================

    /**
     * <h2>通过构建的条件执行任务</h2>
     * 该操作存在返回结果
     *
     * @param t1 结果为true时执行的语句
     * @param t2 结果为false时执行的语句
     * @return Optional<T>
     * @author liuwenhao
     * @date 2022/3/27 15:21
     */
    public <T> Optional<T> branch(Supplier<T> t1, Supplier<T> t2) {
        return Optional.of(exec() ? t1.get() : t2.get());
    }

    /**
     * <h2>通过构建的条件执行任务</h2>
     * 该操作存在返回结果
     *
     * @param t1 结果为true时执行的语句
     * @param t2 结果为false时执行的语句
     * @return T
     * @author liuwenhao
     * @date 2022/3/27 15:21
     */
    public <T> T branchBare(Supplier<T> t1, Supplier<T> t2) {
        return exec() ? t1.get() : t2.get();
    }


    /**
     * <h2>通过构建的条件执行任务</h2>
     * 如果结果为true，则执行<br>
     * 该操作存在返回结果
     *
     * @param t 执行的语句
     * @return Optional<T>
     * @author liuwenhao
     * @date 2022/3/27 15:21
     */
    public <T> Optional<T> isTure(Supplier<T> t) {
        boolean b = exec();
        if (b) {
            return Optional.of(t.get());
        }
        return Optional.empty();
    }

    /**
     * <h2>通过构建的条件执行任务</h2>
     * 如果结果为false，则执行<br>
     * 该操作存在返回结果
     *
     * @param t 执行的语句
     * @return Optional<T>
     * @author liuwenhao
     * @date 2022/3/27 15:21
     */
    public <T> Optional<T> isFalse(Supplier<T> t) {
        boolean b = exec();
        if (!b) {
            return Optional.of(t.get());
        }
        return Optional.empty();
    }


    /**
     * <h2>通过构建的条件执行任务</h2>
     *
     * @param r1 结果为true时执行的语句
     * @param r2 结果为false时执行的语句
     * @author liuwenhao
     * @date 2022/3/27 15:21
     */
    public void branch(Runnable r1, Runnable r2) {
        boolean b = exec();
        if (b) {
            r1.run();
        } else {
            r2.run();
        }
    }

    /**
     * <h2>通过构建的条件执行任务</h2>
     * 如果结果为true，则执行
     *
     * @param r 执行的语句
     * @author liuwenhao
     * @date 2022/3/27 15:21
     */
    public void isTrue(Runnable r) {
        boolean b = exec();
        if (b) {
            r.run();
        }
    }

    /**
     * <h2>通过构建的条件执行任务</h2>
     * 如果结果为false，则执行
     *
     * @param r 执行的语句
     * @author liuwenhao
     * @date 2022/3/27 15:21
     */
    public void isFalse(Runnable r) {
        boolean b = exec();
        if (!b) {
            r.run();
        }
    }

    // =================================嵌套=================================

    /**
     * <h2>进入Child</h2>
     * 如果child为null，则通过默认值新建一个child
     *
     * @return com.example.airplane.timer.util.If
     * @author liuwenhao
     * @date 2022/3/27 17:21
     */
    public If toChild() {
        if (Objects.isNull(children)) {
            children = new If(true);
            return children;
        }
        return children;
    }

    /**
     * <h2>进入Head</h2>
     * 如果Head为null，则不任何变化
     *
     * @return com.example.airplane.timer.util.If
     * @author liuwenhao
     * @date 2022/3/27 17:21
     */
    public If toHead() {
        return Objects.nonNull(head) ? head : this;
    }

    /**
     * <h2>进入Parent</h2>
     * 如果Parent为null，则不任何变化
     *
     * @return com.example.airplane.timer.util.If
     * @author liuwenhao
     * @date 2022/3/27 17:21
     */
    public If toParent() {
        return Objects.nonNull(parent) ? parent : this;
    }

    /**
     * <h2>执行全部的逻辑</h2>
     *
     * @return java.lang.Boolean
     * @author liuwenhao
     * @date 2022/3/27 17:37
     */
    private Boolean exec() {
        return toHead().execSelf();
    }

    /**
     * <h2>执行自己和child的逻辑</h2>
     *
     * @return java.lang.Boolean
     * @author liuwenhao
     * @date 2022/3/27 17:37
     */
    private Boolean execSelf() {
        boolean b = test.getAsBoolean();
        if (Objects.isNull(children)) {
            return b;
        }
        return Boolean.TRUE.equals(join) ? b && children.execSelf() : b || children.execSelf();
    }

    // =================================检查=================================

    private BooleanSupplier checkTrue(BooleanSupplier test) {
        return Objects.isNull(test) ? () -> true : test;
    }

    private Boolean checkTrue(Boolean test) {
        return Objects.isNull(test) || test;
    }

    private BooleanSupplier checkFalse(BooleanSupplier test) {
        return Objects.isNull(test) ? () -> false : test;
    }

    private Boolean checkFalse(Boolean test) {
        return !Objects.isNull(test) && test;
    }

    // =================================create=================================

    public static If of(BooleanSupplier test) {
        return new If(test);
    }

    public static If of(Boolean test) {
        return new If(test);
    }

    public static If of() {
        return new If(true);
    }

}