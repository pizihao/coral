### 介绍：

对java提供的定时任务实现ScheduledExecutorService进行扩展，在schedule和scheduleAtFixedRate和基础上扩展到以下四种模式：

- Rate模式
- Cron模式
- Holiday模式
- Calendar模式

同时提供对定时任务可控制的管理，包括动态注入定时任务，停止正在运行的定时任务，停止整个任务管理模块 - 停止改管理者下所有的定时任务等操作。

支持使用注解配置定时任务，支持声明式定时任务。

### 引入jar：

使用时导入以下依赖：

~~~xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <version>version</version>
</dependency>
<dependency>
    <groupId>com.deep.coral</groupId>
    <artifactId>coral</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
~~~

### 使用：

使用时需在启动类添加定时任务所在的包路径:

```java
@ScheduleScan(packages = "com.deep.coral.web.task")
```

可以直接在需要定制定时任务的方法上使用以下注解执行：

> @ScheduleRate
>
> Rate模式，直接对应ScheduledExecutorService的scheduleAtFixedRate模式，通过一个间隔是时间控制任务的执行

>@ScheduleCron
>
>Cron模式，通过cron表达式控制任务，注意cron表达式仅能支持到秒

>@ScheduleHoliday
>
>Holiday模式，通过预先配置好的日历枚举进行控制，相较于cron表达式，Holiday模式可以支持农历

> @Schedule
>
> Calendar模式，日历模式，完全精确到年月日时分秒的执行，使用绝对时间进行对接自然时间，如：每天的12时执行，每分钟的30秒执行等

### Rate模式：

较为广泛的定时任务模式，如每10秒同步数据，每24小时清空数据等。是业务模块定时任务的主要模式。

例如:2秒后，每5秒打印当时的时间

~~~java
@ScheduleRate(name = TestConstant.STRING, timeUnit = TimeUnit.SECONDS, initialDelay = 2, interval = 5)
public void refresh() {
    System.out.println(LocalDateTime.now());
}
~~~

使用注解配置较为方便，也可使用声明式定时任务：

~~~java
public void refresh() {
    TaskManager builder = TaskHelper.builder();
    BasicTask basicTask = TaskHelper.rateBuilder(TimeUnit.SECONDS, 2, 5);
    TaskGuardian guardian = TaskHelper.builder(executor, TestConstant.STRING)
        .runnable(() -> System.out.println(LocalDateTime.now()))
        .task(basicTask);
    builder.addTask(guardian).begin();
}
~~~

### Cron模式：

同样是较为普遍的任务模式，cron表达式几乎可以定制任意时间规则的任务执行。

可以通过以下网址了解：

https://www.cnblogs.com/junrong624/p/4239517.html

网上同样存在诸多cron表达式生成规则：

http://cron.ciding.cc/

cron模式一如既往的继承该表达式的简洁直观。

例如：通过cron模式实现每5秒执行一次：

~~~java
@ScheduleCron(name = TestConstant.STRING, cron = "0/5 * * * * ? ")
public void refresh() {
    System.out.println(LocalDateTime.now());
}
~~~

也可以使用声明式写法：

~~~java
public void refresh() {
    TaskManager builder = TaskHelper.builder();
    BasicTask basicTask = TaskHelper.cronBuilder("0/5 * * * * ? ");
    TaskGuardian guardian = TaskHelper.builder(executor, TestConstant.STRING)
        .runnable(() -> System.out.println(LocalDateTime.now()))
        .task(basicTask);
    builder.addTask(guardian).begin();
}
~~~

### Holiday模式：

节日模式，较为罕见的模式，如每年的国庆节执行任务，当然该类型可直接使用cron表达式，但是存在历制冲突时cron表达式就不能够兼顾了，比如每年的清明节执行，因为每年的清明节对应的公历日期不一致，所以cron表达式无法直接使用。

以下为测试使用的测试数据：每5秒执行一次

~~~java
@ScheduleHoliday(name = TestConstant.STRING, holiday = HolidayEnum.FIVE_SECONDS, isOnce = true)
public void refresh() {
    System.out.println(LocalDateTime.now());
}
~~~

其中，HolidayEnum：

```java
public enum HolidayEnum {


    /**
     * 测试
     */
    FIVE_SECONDS(1, "测试", 0/5 * * * * ? ");

    /**
     * 历制 0: 农历，1：公历
     *
    private final int code;

    /**
     * 节日或节气名称
     */
    private final String value;

    /**
     * cron表达式,仅适用于公历，通过code进行同等时间的转化
     */
    private final String cron;

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getCron() {
        return cron;
    }

    HolidayEnum(int code, String value, String cron) {
        this.code = code;
        this.value = value;
        this.cron = cron;
    }
}
```

也可以使用声明式写法：

~~~java
public void refresh() {
    TaskManager builder = TaskHelper.builder();
    BasicTask basicTask = TaskHelper.holidayBuilder(HolidayEnum.FIVE_SECONDS, true);
    TaskGuardian guardian = TaskHelper.builder(executor, TestConstant.STRING)
        .runnable(() -> System.out.println(LocalDateTime.now()))
        .task(basicTask);
    builder.addTask(guardian).begin();
}
~~~

### Calendar模式：

和cron表达式相辅相成的模式，鉴于cron表达式晦涩难懂，故设计了该模式，旨在使用更多的配置项让任务的执行更直观，通过绝对时间进行循环和执行任务。

如使用此模式实现每分钟的第5秒执行一次:

~~~java
@Schedule(name = TestConstant.STRING, second = 5)
public void refresh() {
    System.out.println(LocalDateTime.now());
}
~~~

声明式方法：

~~~java
TaskManager builder = TaskHelper.builder();
BasicTask basicTask = TaskHelper.calendarBuilder(CalendarEnum.GREGORIAN, 5);
TaskGuardian guardian = TaskHelper.builder(executor, TestConstant.STRING)
    .runnable(() -> System.out.println(LocalDateTime.now()))
    .task(basicTask);
builder.addTask(guardian).begin();
~~~

同时和历制进行了对接。

### 停止任务：

为了使得任务可控，仿照FutureTask实现了GuardianTask，通过GuardianTask可以直接停止任务的执行，不过任务的执行存在延迟性，此处停止的是下一次执行。

```java
public void refresh() {
    AtomicInteger num = new AtomicInteger();

    TaskManager builder = TaskHelper.builder();
    BasicTask basicTask = TaskHelper.calendarBuilder(CalendarEnum.GREGORIAN, 5);
    TaskGuardian guardian = TaskHelper.builder(executor, TestConstant.STRING)
        .runnable(num::incrementAndGet)
        .task(basicTask);
    builder.addTask(guardian).begin();

    if (num.get() > 3) {
        TaskGuardian task = builder.getTask(TestConstant.STRING);
        task.stop();
    }

}
```

如实例，上述任务会在执行4次后停止。

除此之外，还提供了全部任务停止的方法：

~~~java
public void refresh(String s) {
    AtomicInteger num = new AtomicInteger();

    TaskManager builder = TaskHelper.builder();
    BasicTask basicTask = TaskHelper.calendarBuilder(CalendarEnum.GREGORIAN, 5);
    TaskGuardian guardian = TaskHelper.builder(executor, TestConstant.STRING)
        .runnable(num::incrementAndGet)
        .task(basicTask);
    builder.addTask(guardian).begin();

    if (num.get() > 3) {
        builder.close();
    }

}
~~~



