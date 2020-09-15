/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 16/08/2020 12:29:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (1);

-- ----------------------------
-- Table structure for t_blog
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog`  (
  `id` bigint(20) NOT NULL,
  `appreciation` bit(1) NOT NULL,
  `comment_tabled` bit(1) NOT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `first_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `flag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `published` bit(1) NOT NULL,
  `recommend` bit(1) NOT NULL,
  `share_statement` bit(1) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `views` int(11) NULL DEFAULT NULL,
  `type_id` bigint(20) NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sticky` bit(1) NOT NULL,
  `hide_content` bit(1) NOT NULL,
  `hide_contents` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK292449gwg5yf7ocdlmswv9w4j`(`type_id`) USING BTREE,
  INDEX `FK8ky5rrsxh01nkhctmo7d48p82`(`user_id`) USING BTREE,
  CONSTRAINT `FK292449gwg5yf7ocdlmswv9w4j` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK8ky5rrsxh01nkhctmo7d48p82` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_blog
-- ----------------------------
INSERT INTO `t_blog` VALUES (180, b'1', b'1', '# day01【Object类、常用API】\r\n\r\n## 主要内容\r\n\r\n* Object类\r\n* Date类\r\n* DateFormat类\r\n* Calendar类\r\n* System类\r\n* StringBuilder类\r\n* 包装类\r\n\r\n## 教学目标\r\n\r\n-[ ] 能够说出Object类的特点\r\n-[ ] 能够重写Object类的toString方法\r\n-[ ] 能够重写Object类的equals方法\r\n-[ ] 能够使用日期类输出当前日期\r\n-[ ] 能够使用将日期格式化为字符串的方法\r\n-[ ] 能够使用将字符串转换成日期的方法\r\n-[ ] 能够使用System类的数组复制方法\r\n-[ ] 能够使用System类获取当前毫秒时刻值\r\n-[ ] 能够说出使用StringBuilder类可以解决的问题\r\n-[ ] 能够使用StringBuilder进行字符串拼接操作\r\n-[ ] 能够说出8种基本类型对应的包装类名称\r\n-[ ] 能够说出自动装箱、自动拆箱的概念\r\n-[ ] 能够将字符串转换为对应的基本类型\r\n-[ ] 能够将基本类型转换为对应的字符串\r\n\r\n# 第一章 Object类\r\n\r\n## 1.1 概述\r\n\r\n`java.lang.Object`类是Java语言中的根类，即所有类的父类。它中描述的所有方法子类都可以使用。在对象实例化的时候，最终找的父类就是Object。\r\n\r\n如果一个类没有特别指定父类，	那么默认则继承自Object类。例如：\r\n\r\n```java\r\npublic class MyClass /*extends Object*/ {\r\n  	// ...\r\n}\r\n```\r\n\r\n根据JDK源代码及Object类的API文档，Object类当中包含的方法有11个。今天我们主要学习其中的2个：\r\n\r\n* `public String toString()`：返回该对象的字符串表示。\r\n* `public boolean equals(Object obj)`：指示其他某个对象是否与此对象“相等”。\r\n\r\n## 1.2 toString方法\r\n\r\n### 方法摘要\r\n\r\n* `public String toString()`：返回该对象的字符串表示。\r\n\r\ntoString方法返回该对象的字符串表示，其实该字符串内容就是对象的类型+@+内存地址值。\r\n\r\n由于toString方法返回的结果是内存地址，而在开发中，经常需要按照对象的属性得到相应的字符串表现形式，因此也需要重写它。\r\n\r\n### 覆盖重写\r\n\r\n如果不希望使用toString方法的默认行为，则可以对它进行覆盖重写。例如自定义的Person类：\r\n\r\n```java\r\npublic class Person {  \r\n    private String name;\r\n    private int age;\r\n\r\n    @Override\r\n    public String toString() {\r\n        return \"Person{\" + \"name=\'\" + name + \'\\\'\' + \", age=\" + age + \'}\';\r\n    }\r\n\r\n    // 省略构造器与Getter Setter\r\n}\r\n```\r\n\r\n在IntelliJ IDEA中，可以点击`Code`菜单中的`Generate...`，也可以使用快捷键`alt+insert`，点击`toString()`选项。选择需要包含的成员变量并确定。如下图所示：\r\n\r\n![toString方法的自动重写](img\\toString方法的自动重写.bmp)\r\n\r\n> 小贴士： 在我们直接使用输出语句输出对象名的时候,其实通过该对象调用了其toString()方法。\r\n\r\n## 1.3 equals方法\r\n\r\n### 方法摘要\r\n\r\n* `public boolean equals(Object obj)`：指示其他某个对象是否与此对象“相等”。\r\n\r\n调用成员方法equals并指定参数为另一个对象，则可以判断这两个对象是否是相同的。这里的“相同”有默认和自定义两种方式。\r\n\r\n### 默认地址比较\r\n\r\n如果没有覆盖重写equals方法，那么Object类中默认进行`==`运算符的对象地址比较，只要不是同一个对象，结果必然为false。\r\n\r\n### 对象内容比较\r\n\r\n如果希望进行对象的内容比较，即所有或指定的部分成员变量相同就判定两个对象相同，则可以覆盖重写equals方法。例如：\r\n\r\n```java\r\nimport java.util.Objects;\r\n\r\npublic class Person {	\r\n	private String name;\r\n	private int age;\r\n	\r\n    @Override\r\n    public boolean equals(Object o) {\r\n        // 如果对象地址一样，则认为相同\r\n        if (this == o)\r\n            return true;\r\n        // 如果参数为空，或者类型信息不一样，则认为不同\r\n        if (o == null || getClass() != o.getClass())\r\n            return false;\r\n        // 转换为当前类型\r\n        Person person = (Person) o;\r\n        // 要求基本类型相等，并且将引用类型交给java.util.Objects类的equals静态方法取用结果\r\n        return age == person.age && Objects.equals(name, person.name);\r\n    }\r\n}\r\n```\r\n\r\n这段代码充分考虑了对象为空、类型一致等问题，但方法内容并不唯一。大多数IDE都可以自动生成equals方法的代码内容。在IntelliJ IDEA中，可以使用`Code`菜单中的`Generate…`选项，也可以使用快捷键`alt+insert`，并选择`equals() and hashCode()`进行自动代码生成。如下图所示：\r\n\r\n![](img\\equals方法1.png)\r\n\r\n![](img\\equals方法2.png)\r\n\r\n![](img\\equals方法3.png)\r\n\r\n> tips：Object类当中的hashCode等其他方法，今后学习。\r\n\r\n## 1.4 Objects类\r\n\r\n在刚才IDEA自动重写equals代码中，使用到了`java.util.Objects`类，那么这个类是什么呢？\r\n\r\n在**JDK7**添加了一个Objects工具类，它提供了一些方法来操作对象，它由一些静态的实用方法组成，这些方法是null-save（空指针安全的）或null-tolerant（容忍空指针的），用于计算对象的hashcode、返回对象的字符串表示形式、比较两个对象。\r\n\r\n在比较两个对象的时候，Object的equals方法容易抛出空指针异常，而Objects类中的equals方法就优化了这个问题。方法如下：\r\n\r\n* `public static boolean equals(Object a, Object b)`:判断两个对象是否相等。\r\n\r\n我们可以查看一下源码，学习一下：\r\n\r\n~~~java\r\npublic static boolean equals(Object a, Object b) {  \r\n    return (a == b) || (a != null && a.equals(b));  \r\n}\r\n~~~\r\n\r\n# 第二章 日期时间类\r\n\r\n## 2.1 Date类\r\n\r\n### 概述\r\n\r\n` java.util.Date`类 表示特定的瞬间，精确到毫秒。\r\n\r\n继续查阅Date类的描述，发现Date拥有多个构造函数，只是部分已经过时，但是其中有未过时的构造函数可以把毫秒值转成日期对象。\r\n\r\n- `public Date()`：分配Date对象并初始化此对象，以表示分配它的时间（精确到毫秒）。\r\n- `public Date(long date)`：分配Date对象并初始化此对象，以表示自从标准基准时间（称为“历元（epoch）”，即1970年1月1日00:00:00 GMT）以来的指定毫秒数。\r\n\r\n> tips: 由于我们处于东八区，所以我们的基准时间为1970年1月1日8时0分0秒。\r\n\r\n简单来说：使用无参构造，可以自动设置当前系统时间的毫秒时刻；指定long类型的构造参数，可以自定义毫秒时刻。例如：\r\n\r\n```java\r\nimport java.util.Date;\r\n\r\npublic class Demo01Date {\r\n    public static void main(String[] args) {\r\n        // 创建日期对象，把当前的时间\r\n        System.out.println(new Date()); // Tue Jan 16 14:37:35 CST 2018\r\n        // 创建日期对象，把当前的毫秒值转成日期对象\r\n        System.out.println(new Date(0L)); // Thu Jan 01 08:00:00 CST 1970\r\n    }\r\n}\r\n```\r\n\r\n> tips:在使用println方法时，会自动调用Date类中的toString方法。Date类对Object类中的toString方法进行了覆盖重写，所以结果为指定格式的字符串。\r\n\r\n### 常用方法\r\n\r\nDate类中的多数方法已经过时，常用的方法有：\r\n\r\n* `public long getTime()` 把日期对象转换成对应的时间毫秒值。\r\n\r\n## 2.2 DateFormat类\r\n\r\n`java.text.DateFormat` 是日期/时间格式化子类的抽象类，我们通过这个类可以帮我们完成日期和文本之间的转换,也就是可以在Date对象与String对象之间进行来回转换。\r\n\r\n* **格式化**：按照指定的格式，从Date对象转换为String对象。\r\n* **解析**：按照指定的格式，从String对象转换为Date对象。\r\n\r\n### 构造方法\r\n\r\n由于DateFormat为抽象类，不能直接使用，所以需要常用的子类`java.text.SimpleDateFormat`。这个类需要一个模式（格式）来指定格式化或解析的标准。构造方法为：\r\n\r\n* `public SimpleDateFormat(String pattern)`：用给定的模式和默认语言环境的日期格式符号构造SimpleDateFormat。\r\n\r\n参数pattern是一个字符串，代表日期时间的自定义格式。\r\n\r\n### 格式规则\r\n\r\n常用的格式规则为：\r\n\r\n| 标识字母（区分大小写） | 含义 |\r\n| ---------------------- | ---- |\r\n| y                      | 年   |\r\n| M                      | 月   |\r\n| d                      | 日   |\r\n| H                      | 时   |\r\n| m                      | 分   |\r\n| s                      | 秒   |\r\n\r\n> 备注：更详细的格式规则，可以参考SimpleDateFormat类的API文档0。\r\n\r\n创建SimpleDateFormat对象的代码如：\r\n\r\n```java\r\nimport java.text.DateFormat;\r\nimport java.text.SimpleDateFormat;\r\n\r\npublic class Demo02SimpleDateFormat {\r\n    public static void main(String[] args) {\r\n        // 对应的日期格式如：2018-01-16 15:06:38\r\n        DateFormat format = new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\");\r\n    }    \r\n}\r\n```\r\n\r\n### 常用方法\r\n\r\nDateFormat类的常用方法有：\r\n\r\n- `public String format(Date date)`：将Date对象格式化为字符串。\r\n- `public Date parse(String source)`：将字符串解析为Date对象。\r\n\r\n#### format方法\r\n\r\n使用format方法的代码为：\r\n\r\n```java\r\nimport java.text.DateFormat;\r\nimport java.text.SimpleDateFormat;\r\nimport java.util.Date;\r\n/*\r\n 把Date对象转换成String\r\n*/\r\npublic class Demo03DateFormatMethod {\r\n    public static void main(String[] args) {\r\n        Date date = new Date();\r\n        // 创建日期格式化对象,在获取格式化对象时可以指定风格\r\n        DateFormat df = new SimpleDateFormat(\"yyyy年MM月dd日\");\r\n        String str = df.format(date);\r\n        System.out.println(str); // 2008年1月23日\r\n    }\r\n}\r\n```\r\n\r\n#### parse方法\r\n\r\n使用parse方法的代码为：\r\n\r\n```java\r\nimport java.text.DateFormat;\r\nimport java.text.ParseException;\r\nimport java.text.SimpleDateFormat;\r\nimport java.util.Date;\r\n/*\r\n 把String转换成Date对象\r\n*/\r\npublic class Demo04DateFormatMethod {\r\n    public static void main(String[] args) throws ParseException {\r\n        DateFormat df = new SimpleDateFormat(\"yyyy年MM月dd日\");\r\n        String str = \"2018年12月11日\";\r\n        Date date = df.parse(str);\r\n        System.out.println(date); // Tue Dec 11 00:00:00 CST 2018\r\n    }\r\n}\r\n```\r\n\r\n## 2.3 练习\r\n\r\n请使用日期时间相关的API，计算出一个人已经出生了多少天。\r\n\r\n**思路：**\r\n\r\n1.获取当前时间对应的毫秒值\r\n\r\n2.获取自己出生日期对应的毫秒值\r\n\r\n3.两个时间相减（当前时间– 出生日期）\r\n\r\n**代码实现：**\r\n\r\n```java\r\npublic static void function() throws Exception {\r\n	System.out.println(\"请输入出生日期 格式 YYYY-MM-dd\");\r\n	// 获取出生日期,键盘输入\r\n	String birthdayString = new Scanner(System.in).next();\r\n	// 将字符串日期,转成Date对象\r\n	// 创建SimpleDateFormat对象,写日期模式\r\n	SimpleDateFormat sdf = new SimpleDateFormat(\"yyyy-MM-dd\");\r\n	// 调用方法parse,字符串转成日期对象\r\n	Date birthdayDate = sdf.parse(birthdayString);	\r\n	// 获取今天的日期对象\r\n	Date todayDate = new Date();	\r\n	// 将两个日期转成毫秒值,Date类的方法getTime\r\n	long birthdaySecond = birthdayDate.getTime();\r\n	long todaySecond = todayDate.getTime();\r\n	long secone = todaySecond-birthdaySecond;	\r\n	if (secone < 0){\r\n		System.out.println(\"还没出生呢\");\r\n	} else {\r\n		System.out.println(secone/1000/60/60/24);\r\n	}\r\n}\r\n```\r\n\r\n## 2.4 Calendar类\r\n\r\n###  概念\r\n\r\n日历我们都见过\r\n\r\n![](img\\日历.jpg)\r\n\r\n`java.util.Calendar`是日历类，在Date后出现，替换掉了许多Date的方法。该类将所有可能用到的时间信息封装为静态成员变量，方便获取。日历类就是方便获取各个时间属性的。\r\n\r\n### 获取方式\r\n\r\nCalendar为抽象类，由于语言敏感性，Calendar类在创建对象时并非直接创建，而是通过静态方法创建，返回子类对象，如下：\r\n\r\nCalendar静态方法\r\n\r\n* `public static Calendar getInstance()`：使用默认时区和语言环境获得一个日历\r\n\r\n例如：\r\n\r\n```java\r\nimport java.util.Calendar;\r\n\r\npublic class Demo06CalendarInit {\r\n    public static void main(String[] args) {\r\n        Calendar cal = Calendar.getInstance();\r\n    }    \r\n}\r\n```\r\n\r\n### 常用方法\r\n\r\n根据Calendar类的API文档，常用方法有：\r\n\r\n- `public int get(int field)`：返回给定日历字段的值。\r\n- `public void set(int field, int value)`：将给定的日历字段设置为给定值。\r\n- `public abstract void add(int field, int amount)`：根据日历的规则，为给定的日历字段添加或减去指定的时间量。\r\n- `public Date getTime()`：返回一个表示此Calendar时间值（从历元到现在的毫秒偏移量）的Date对象。\r\n\r\nCalendar类中提供很多成员常量，代表给定的日历字段：\r\n\r\n| 字段值       | 含义                                  |\r\n| ------------ | ------------------------------------- |\r\n| YEAR         | 年                                    |\r\n| MONTH        | 月（从0开始，可以+1使用）             |\r\n| DAY_OF_MONTH | 月中的天（几号）                      |\r\n| HOUR         | 时（12小时制）                        |\r\n| HOUR_OF_DAY  | 时（24小时制）                        |\r\n| MINUTE       | 分                                    |\r\n| SECOND       | 秒                                    |\r\n| DAY_OF_WEEK  | 周中的天（周几，周日为1，可以-1使用） |\r\n\r\n#### get/set方法\r\n\r\nget方法用来获取指定字段的值，set方法用来设置指定字段的值，代码使用演示：\r\n\r\n```java\r\nimport java.util.Calendar;\r\n\r\npublic class CalendarUtil {\r\n    public static void main(String[] args) {\r\n        // 创建Calendar对象\r\n        Calendar cal = Calendar.getInstance();\r\n        // 设置年 \r\n        int year = cal.get(Calendar.YEAR);\r\n        // 设置月\r\n        int month = cal.get(Calendar.MONTH) + 1;\r\n        // 设置日\r\n        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);\r\n        System.out.print(year + \"年\" + month + \"月\" + dayOfMonth + \"日\");\r\n    }    \r\n}\r\n```\r\n\r\n```java\r\nimport java.util.Calendar;\r\n\r\npublic class Demo07CalendarMethod {\r\n    public static void main(String[] args) {\r\n        Calendar cal = Calendar.getInstance();\r\n        cal.set(Calendar.YEAR, 2020);\r\n        System.out.print(year + \"年\" + month + \"月\" + dayOfMonth + \"日\"); // 2020年1月17日\r\n    }\r\n}\r\n```\r\n\r\n#### add方法\r\n\r\nadd方法可以对指定日历字段的值进行加减操作，如果第二个参数为正数则加上偏移量，如果为负数则减去偏移量。代码如：\r\n\r\n```java\r\nimport java.util.Calendar;\r\n\r\npublic class Demo08CalendarMethod {\r\n    public static void main(String[] args) {\r\n        Calendar cal = Calendar.getInstance();\r\n        System.out.print(year + \"年\" + month + \"月\" + dayOfMonth + \"日\"); // 2018年1月17日\r\n        // 使用add方法\r\n        cal.add(Calendar.DAY_OF_MONTH, 2); // 加2天\r\n        cal.add(Calendar.YEAR, -3); // 减3年\r\n        System.out.print(year + \"年\" + month + \"月\" + dayOfMonth + \"日\"); // 2015年1月18日; \r\n    }\r\n}\r\n```\r\n\r\n#### getTime方法\r\n\r\nCalendar中的getTime方法并不是获取毫秒时刻，而是拿到对应的Date对象。\r\n\r\n```java\r\nimport java.util.Calendar;\r\nimport java.util.Date;\r\n\r\npublic class Demo09CalendarMethod {\r\n    public static void main(String[] args) {\r\n        Calendar cal = Calendar.getInstance();\r\n        Date date = cal.getTime();\r\n        System.out.println(date); // Tue Jan 16 16:03:09 CST 2018\r\n    }\r\n}\r\n```\r\n\r\n> 小贴士：\r\n>\r\n> ​     西方星期的开始为周日，中国为周一。\r\n>\r\n> ​     在Calendar类中，月份的表示是以0-11代表1-12月。\r\n>\r\n> ​     日期是有大小关系的，时间靠后，时间越大。\r\n\r\n# 第三章 System类\r\n\r\n`java.lang.System`类中提供了大量的静态方法，可以获取与系统相关的信息或系统级操作，在System类的API文档中，常用的方法有：\r\n\r\n- `public static long currentTimeMillis()`：返回以毫秒为单位的当前时间。\r\n- `public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)`：将数组中指定的数据拷贝到另一个数组中。\r\n\r\n## 3.1 currentTimeMillis方法\r\n\r\n实际上，currentTimeMillis方法就是 获取当前系统时间与1970年01月01日00:00点之间的毫秒差值\r\n\r\n```java\r\nimport java.util.Date;\r\n\r\npublic class SystemDemo {\r\n    public static void main(String[] args) {\r\n       	//获取当前时间毫秒值\r\n        System.out.println(System.currentTimeMillis()); // 1516090531144\r\n    }\r\n}\r\n```\r\n\r\n### 练习\r\n\r\n验证for循环打印数字1-9999所需要使用的时间（毫秒）\r\n\r\n~~~java\r\npublic class SystemTest1 {\r\n    public static void main(String[] args) {\r\n        long start = System.currentTimeMillis();\r\n        for (int i = 0; i < 10000; i++) {\r\n            System.out.println(i);\r\n        }\r\n        long end = System.currentTimeMillis();\r\n        System.out.println(\"共耗时毫秒：\" + (end - start));\r\n    }\r\n}\r\n~~~\r\n\r\n## 3.2 arraycopy方法\r\n\r\n* `public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)`：将数组中指定的数据拷贝到另一个数组中。\r\n\r\n数组的拷贝动作是系统级的，性能很高。System.arraycopy方法具有5个参数，含义分别为：\r\n\r\n| 参数序号 | 参数名称 | 参数类型 | 参数含义             |\r\n| -------- | -------- | -------- | -------------------- |\r\n| 1        | src      | Object   | 源数组               |\r\n| 2        | srcPos   | int      | 源数组索引起始位置   |\r\n| 3        | dest     | Object   | 目标数组             |\r\n| 4        | destPos  | int      | 目标数组索引起始位置 |\r\n| 5        | length   | int      | 复制元素个数         |\r\n\r\n### 练习\r\n\r\n将src数组中前3个元素，复制到dest数组的前3个位置上复制元素前：src数组元素[1,2,3,4,5]，dest数组元素[6,7,8,9,10]复制元素后：src数组元素[1,2,3,4,5]，dest数组元素[1,2,3,9,10]\r\n\r\n```java\r\nimport java.util.Arrays;\r\n\r\npublic class Demo11SystemArrayCopy {\r\n    public static void main(String[] args) {\r\n        int[] src = new int[]{1,2,3,4,5};\r\n        int[] dest = new int[]{6,7,8,9,10};\r\n        System.arraycopy( src, 0, dest, 0, 3);\r\n        /*代码运行后：两个数组中的元素发生了变化\r\n         src数组元素[1,2,3,4,5]\r\n         dest数组元素[1,2,3,9,10]\r\n        */\r\n    }\r\n}\r\n```\r\n\r\n# 第四章 StringBuilder类\r\n\r\n## 4.1 字符串拼接问题\r\n\r\n由于String类的对象内容不可改变，所以每当进行字符串拼接时，总是会在内存中创建一个新的对象。例如：\r\n\r\n~~~java\r\npublic class StringDemo {\r\n    public static void main(String[] args) {\r\n        String s = \"Hello\";\r\n        s += \"World\";\r\n        System.out.println(s);\r\n    }\r\n}\r\n~~~\r\n\r\n在API中对String类有这样的描述：字符串是常量，它们的值在创建后不能被更改。\r\n\r\n根据这句话分析我们的代码，其实总共产生了三个字符串，即`\"Hello\"`、`\"World\"`和`\"HelloWorld\"`。引用变量s首先指向`Hello`对象，最终指向拼接出来的新字符串对象，即`HelloWord` 。\r\n\r\n![](img\\String拼接问题.bmp)\r\n\r\n由此可知，如果对字符串进行拼接操作，每次拼接，都会构建一个新的String对象，既耗时，又浪费空间。为了解决这一问题，可以使用`java.lang.StringBuilder`类。\r\n\r\n## 4.2 StringBuilder概述\r\n\r\n查阅`java.lang.StringBuilder`的API，StringBuilder又称为可变字符序列，它是一个类似于 String 的字符串缓冲区，通过某些方法调用可以改变该序列的长度和内容。\r\n\r\n原来StringBuilder是个字符串的缓冲区，即它是一个容器，容器中可以装很多字符串。并且能够对其中的字符串进行各种操作。\r\n\r\n它的内部拥有一个数组用来存放字符串内容，进行字符串拼接时，直接在数组中加入新内容。StringBuilder会自动维护数组的扩容。原理如下图所示：(默认16字符空间，超过自动扩充)\r\n\r\n![06-StringBuilder的原理](img\\06-StringBuilder的原理.png)\r\n\r\n## 4.3 构造方法\r\n\r\n根据StringBuilder的API文档，常用构造方法有2个：\r\n\r\n- `public StringBuilder()`：构造一个空的StringBuilder容器。\r\n- `public StringBuilder(String str)`：构造一个StringBuilder容器，并将字符串添加进去。\r\n\r\n```java\r\npublic class StringBuilderDemo {\r\n    public static void main(String[] args) {\r\n        StringBuilder sb1 = new StringBuilder();\r\n        System.out.println(sb1); // (空白)\r\n        // 使用带参构造\r\n        StringBuilder sb2 = new StringBuilder(\"itcast\");\r\n        System.out.println(sb2); // itcast\r\n    }\r\n}\r\n```\r\n\r\n## 4.4 常用方法\r\n\r\nStringBuilder常用的方法有2个：\r\n\r\n- `public StringBuilder append(...)`：添加任意类型数据的字符串形式，并返回当前对象自身。\r\n- `public String toString()`：将当前StringBuilder对象转换为String对象。\r\n\r\n### append方法\r\n\r\nappend方法具有多种重载形式，可以接收任意类型的参数。任何数据作为参数都会将对应的字符串内容添加到StringBuilder中。例如：\r\n\r\n```java\r\npublic class Demo02StringBuilder {\r\n	public static void main(String[] args) {\r\n		//创建对象\r\n		StringBuilder builder = new StringBuilder();\r\n		//public StringBuilder append(任意类型)\r\n		StringBuilder builder2 = builder.append(\"hello\");\r\n		//对比一下\r\n		System.out.println(\"builder:\"+builder);\r\n		System.out.println(\"builder2:\"+builder2);\r\n		System.out.println(builder == builder2); //true\r\n	    // 可以添加 任何类型\r\n		builder.append(\"hello\");\r\n		builder.append(\"world\");\r\n		builder.append(true);\r\n		builder.append(100);\r\n		// 在我们开发中，会遇到调用一个方法后，返回一个对象的情况。然后使用返回的对象继续调用方法。\r\n        // 这种时候，我们就可以把代码现在一起，如append方法一样，代码如下\r\n		//链式编程\r\n		builder.append(\"hello\").append(\"world\").append(true).append(100);\r\n		System.out.println(\"builder:\"+builder);\r\n	}\r\n}\r\n```\r\n\r\n> 备注：StringBuilder已经覆盖重写了Object当中的toString方法。\r\n\r\n### toString方法\r\n\r\n通过toString方法，StringBuilder对象将会转换为不可变的String对象。如：\r\n\r\n```java\r\npublic class Demo16StringBuilder {\r\n    public static void main(String[] args) {\r\n        // 链式创建\r\n        StringBuilder sb = new StringBuilder(\"Hello\").append(\"World\").append(\"Java\");\r\n        // 调用方法\r\n        String str = sb.toString();\r\n        System.out.println(str); // HelloWorldJava\r\n    }\r\n}\r\n```\r\n\r\n# 第五章 包装类\r\n\r\n## 5.1 概述\r\n\r\nJava提供了两个类型系统，基本类型与引用类型，使用基本类型在于效率，然而很多情况，会创建对象使用，因为对象可以做更多的功能，如果想要我们的基本类型像对象一样操作，就可以使用基本类型对应的包装类，如下：\r\n\r\n| 基本类型 | 对应的包装类（位于java.lang包中） |\r\n| -------- | --------------------------------- |\r\n| byte     | Byte                              |\r\n| short    | Short                             |\r\n| int      | **Integer**                       |\r\n| long     | Long                              |\r\n| float    | Float                             |\r\n| double   | Double                            |\r\n| char     | **Character**                     |\r\n| boolean  | Boolean                           |\r\n\r\n## 5.2 装箱与拆箱\r\n\r\n基本类型与对应的包装类对象之间，来回转换的过程称为”装箱“与”拆箱“：\r\n\r\n* **装箱**：从基本类型转换为对应的包装类对象。\r\n\r\n* **拆箱**：从包装类对象转换为对应的基本类型。\r\n\r\n用Integer与 int为例：（看懂代码即可）\r\n\r\n基本数值---->包装对象\r\n\r\n~~~java\r\nInteger i = new Integer(4);//使用构造函数函数\r\nInteger iii = Integer.valueOf(4);//使用包装类中的valueOf方法\r\n~~~\r\n\r\n包装对象---->基本数值\r\n\r\n~~~java\r\nint num = i.intValue();\r\n~~~\r\n\r\n## 5.3自动装箱与自动拆箱\r\n\r\n由于我们经常要做基本类型与包装类之间的转换，从Java 5（JDK 1.5）开始，基本类型与包装类的装箱、拆箱动作可以自动完成。例如：\r\n\r\n```java\r\nInteger i = 4;//自动装箱。相当于Integer i = Integer.valueOf(4);\r\ni = i + 5;//等号右边：将i对象转成基本数值(自动拆箱) i.intValue() + 5;\r\n//加法运算完成后，再次装箱，把基本数值转成对象。\r\n```\r\n\r\n## 5.3 基本类型与字符串之间的转换\r\n\r\n### 基本类型转换为String\r\n\r\n   基本类型转换String总共有三种方式，查看课后资料可以得知，这里只讲最简单的一种方式： \r\n\r\n~~~\r\n基本类型直接与””相连接即可；如：34+\"\"\r\n~~~\r\n\r\nString转换成对应的基本类型 \r\n\r\n除了Character类之外，其他所有包装类都具有parseXxx静态方法可以将字符串参数转换为对应的基本类型：\r\n\r\n- `public static byte parseByte(String s)`：将字符串参数转换为对应的byte基本类型。\r\n- `public static short parseShort(String s)`：将字符串参数转换为对应的short基本类型。\r\n- `public static int parseInt(String s)`：将字符串参数转换为对应的int基本类型。\r\n- `public static long parseLong(String s)`：将字符串参数转换为对应的long基本类型。\r\n- `public static float parseFloat(String s)`：将字符串参数转换为对应的float基本类型。\r\n- `public static double parseDouble(String s)`：将字符串参数转换为对应的double基本类型。\r\n- `public static boolean parseBoolean(String s)`：将字符串参数转换为对应的boolean基本类型。\r\n\r\n代码使用（仅以Integer类的静态方法parseXxx为例）如：\r\n\r\n```java\r\npublic class Demo18WrapperParse {\r\n    public static void main(String[] args) {\r\n        int num = Integer.parseInt(\"100\");\r\n    }\r\n}\r\n```\r\n\r\n> 注意:如果字符串参数的内容无法正确转换为对应的基本类型，则会抛出`java.lang.NumberFormatException`异常。\r\n\r\n', '2020-06-09 10:26:42', 'https://i.picsum.photos/id/450/800/450.jpg', '', b'1', b'1', b'1', 'java常用API', '2020-06-09 10:48:21', 2, 179, 1, '介绍了基本的常用javaAPI', b'0', b'1', '链接：https://pan.baidu.com/s/1w5PhQQFxlsse65O20ZS53A \r\n提取码：685q \r\n复制这段内容后打开百度网盘手机App，操作更方便哦');

-- ----------------------------
-- Table structure for t_blog_tags
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_tags`;
CREATE TABLE `t_blog_tags`  (
  `blogs_id` bigint(20) NOT NULL,
  `tags_id` bigint(20) NOT NULL,
  INDEX `FK5feau0gb4lq47fdb03uboswm8`(`tags_id`) USING BTREE,
  INDEX `FKh4pacwjwofrugxa9hpwaxg6mr`(`blogs_id`) USING BTREE,
  CONSTRAINT `FK5feau0gb4lq47fdb03uboswm8` FOREIGN KEY (`tags_id`) REFERENCES `t_tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKh4pacwjwofrugxa9hpwaxg6mr` FOREIGN KEY (`blogs_id`) REFERENCES `t_blog` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_blog_tags
-- ----------------------------
INSERT INTO `t_blog_tags` VALUES (180, 176);

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` bigint(20) NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `blog_id` bigint(20) NULL DEFAULT NULL,
  `parent_comment_id` bigint(20) NULL DEFAULT NULL,
  `is_comment` bit(1) NOT NULL,
  `admin_comment` bit(1) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKke3uogd04j4jx316m1p51e05u`(`blog_id`) USING BTREE,
  INDEX `FK4jj284r3pb7japogvo6h72q95`(`parent_comment_id`) USING BTREE,
  CONSTRAINT `FK4jj284r3pb7japogvo6h72q95` FOREIGN KEY (`parent_comment_id`) REFERENCES `t_comment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKke3uogd04j4jx316m1p51e05u` FOREIGN KEY (`blog_id`) REFERENCES `t_blog` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES (176, 'java');

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES (177, 'spring');
INSERT INTO `t_type` VALUES (178, '大数据');
INSERT INTO `t_type` VALUES (179, 'java基础');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '/images/blogImgAvatar.jpg', '2020-05-21 13:43:47', '1013084647@qq.com', '汤姆猫', '97d90ef8906bdbf2a01b5372a5699328', NULL, '2020-05-21 13:44:35', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
