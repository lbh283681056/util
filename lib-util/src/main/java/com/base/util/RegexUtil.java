/*

 * Created on 2005-4-15

 *

 * Summary of regular-expression constructs   正则表达式结构简介：

 * Construct Matches

 *   Characters   字符：

 *    x The character x   x 字符 x

 *    \\ The backslash character  \\ 反斜杠

 *    \0n The character with octal value 0n (0 <= n <= 7)     \0n 十进制数 (0 <= n <= 7)

 *    \0nn The character with octal value 0nn (0 <= n <= 7)   \0nn 十进制数 0nn (0 <= n <= 7)

 *    \0mnn The character with octal value 0mnn (0 <= m <= 3, 0 <= n <= 7)    \0mnn 十进制数 0mnn (0 <= m <= 3, 0 <= n <= 7)

 *    \xhh The character with hexadecimal value 0xhh  \xhh 十六进制数 0xhh

 *    \\uhhhh The character with hexadecimal value 0xhhhh     \\uhhhh 十六进制数 0xhhhh

*    \t The tab character ('\u0009')     \t 制表符 ('\u0009')

*    \n The newline (line feed) character ('\u000A')     \n 换行符 ('\u000A')

*    \r The carriage-return character ('\u000D')     \r 回车符 ('\u000D')

*    \f The form-feed character ('\u000C')   \f The form-feed character ('\u000C')

*    \a The alert (bell) character ('\u0007')    \a The alert (bell) character ('\u0007')

*    \e The escape character ('\u001B')  \e esc符号 ('\u001B')

*    \cx The control character corresponding to x    \cx x 对应的控制符

*

*    Character classes   字符类

*    [abc] a, b, or c (simple class)     [abc] a, b, 或 c (简单字符串)

*    [^abc] Any character except a, b, or c (negation)   [^abc] 除了 a, b, 或 c 之外的任意字符(否定)

*    [a-zA-Z] a through z or A through Z, inclusive (range)  [a-zA-Z] 从a 到 z 或 从A 到 Z（包括a,z,A,Z）(范围)

*    [a-d[m-p]] a through d, or m through p: [a-dm-p] (union)    [a-d[m-p]] 从a 到 d, 或 从m 到 p: [a-dm-p] (并集)

*    [a-z&&[def]] d, e, or f (intersection)  [a-z&&[def]] d, e, 或 f (交集)

*    [a-z&&[^bc]] a through z, except for b and c: [ad-z] (subtraction)  [a-z&&[^bc]] 从a 到 z, 但 b 和 c 除外: [ad-z] (子集)

*    [a-z&&[^m-p]] a through z, and not m through p: [a-lq-z](subtraction)   [a-z&&[^m-p]] 从a 到 z, 不包括从 m 到 p: [a-lq-z](子集)

*

* Predefined character classes   预定义字符序列

*   . Any character (may or may not match line terminators)  . 任意字符 (也可能不包括行结束符)

*   \d A digit: [0-9]    \d 数字: [0-9]

*   \D A non-digit: [^0-9]   \D 非数字: [^0-9]

*   \s A whitespace character: [ \t\n\x0B\f\r]   \s 空字符: [ \t\n\x0B\f\r]

*   \S A non-whitespace character: [^\s]     \S 非空字符: [^\s]

*   \w A word character: [a-zA-Z_0-9]    \w 单字字符: [a-zA-Z_0-9]

*   \W A non-word character: [^\w]   \W 非单字字符: [^\w]

*

*   POSIX character classes (US-ASCII only)  POSIX 字符类 (US-ASCII only)

*   \p{Lower} A lower-case alphabetic character: [a-z]   \p{Lower} 小写字母字符: [a-z]

*   \p{Upper} An upper-case alphabetic character:[A-Z]   \p{Upper} 大写字母字符:[A-Z]

*   \p{ASCII} All ASCII:[\x00-\x7F]  \p{ASCII} 所有 ASCII:[\x00-\x7F]

*   \p{Alpha} An alphabetic character:[\p{Lower}\p{Upper}]   \p{Alpha} 单个字母字符:[\p{Lower}\p{Upper}]

*   \p{Digit} A decimal digit: [0-9]     \p{Digit} 十进制数: [0-9]

*   \p{Alnum} An alphanumeric character:[\p{Alpha}\p{Digit}]     \p{Alnum} 单个字符:[\p{Alpha}\p{Digit}]

*   \p{Punct} Punctuation: One of !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~   \p{Punct} 标点符号: 包括 !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~

*   \p{Graph} A visible character: [\p{Alnum}\p{Punct}]  \p{Graph} 可视字符: [\p{Alnum}\p{Punct}]

*   \p{Print} A printable character: [\p{Graph}]     \p{Print} 可打印字符: [\p{Graph}]

*   \p{Blank} A space or a tab: [ \t]    \p{Blank} 空格或制表符: [ \t]

*   \p{Cntrl} A control character: [\x00-\x1F\x7F]   \p{Cntrl} 控制字符: [\x00-\x1F\x7F]

*   \p{XDigit} A hexadecimal digit: [0-9a-fA-F]  \p{XDigit} 十六进制数: [0-9a-fA-F]

*   \p{Space} A whitespace character: [ \t\n\x0B\f\r]    \p{Space} 空字符: [ \t\n\x0B\f\r]

*

*   Classes for Unicode blocks and categories    Unicode 字符类

*   \p{InGreek} A character in the Greek block (simple block)    \p{InGreek} 希腊语种的字符 (simple block)

*   \p{Lu} An uppercase letter (simple category)     \p{Lu} 大写字母 (simple category)

*   \p{Sc} A currency symbol     \p{Sc} 货币符号

*   \P{InGreek} Any character except one in the Greek block (negation)   \P{InGreek} 除希腊语种字符外的任意字符 (negation)

*   [\p{L}&&[^\p{Lu}]] Any letter except an uppercase letter (subtraction)   [\p{L}&&[^\p{Lu}]] 除大写字母外的任意字符 (subtraction)

*

*   Boundary matchers    边界匹配器

*   ^ The beginning of a line    ^ 一行的开始

*   $ The end of a line  $ 一行的结束

*   \b A word boundary   \b 单词边界

*   \B A non-word boundary   \B 非单词边界

*   \A The beginning of the input    \A 输入的开始

*   \G The end of the previous match     \G 当前匹配的结束

*   \Z The end of the input but for the final terminator, if any     \Z The end of the input but for the final terminator, if any

*   \z The end of the input  \z 输入的结束

*

*   Greedy quantifiers   Greedy quantifiers 贪婪匹配量词（Greedy quantifiers ）（不知道翻译的对不对）

*   X? X, once or not at all     X? X不出现或出现一次 (特殊字符"?"与{0,1}是相等的)

*   X* X, zero or more times     X* X不出现或出现多次 (特殊字符"*"与{0,}是相等的)

*   X+ X, one or more times  X+ X至少出现一次 (特殊字符"+"与 {1,}是相等的)

*   X{n} X, exactly n times  X{n} X出现n次

*   X{n,} X, at least n times    X{n,} X至少出现n次

*   X{n,m} X, at least n but not more than m times   X{n,m} X至少出现n次，但不会超过m次

*

*   Reluctant quantifiers    Reluctant quantifiers

*   X?? X, once or not at all    X?? X, 不出现或出现一次

*   X*? X, zero or more times    X*? X, 不出现或出现多次

*   X+? X, one or more times     X+? X, 至少出现一次

*   X{n}? X, exactly n times     X{n}? X, 出现n次

*   X{n,}? X, at least n times   X{n,}? X, 至少出现n次

*   X{n,m}? X, at least n but not more than m times  X{n,m}? X, 至少出现n次，但不会超过m次

*

*   Possessive quantifiers   Possessive quantifiers

*   X?+ X, once or not at all    X?+ X, 不出现或出现一次

*   X*+ X, zero or more times    X*+ X, 不出现或出现多次

*   X++ X, one or more times     X++ X, 至少出现一次

*   X{n}+ X, exactly n times     X{n}+ X, 出现n次

*   X{n,}+ X, at least n times   X{n,}+ X, 至少出现n次

*   X{n,m}+ X, at least n but not more than m times  X{n,m}+ X, 至少出现n次，但不会超过m次

*

* Logical operators 逻辑运算符

*   XY X followed by Y XY Y跟在X后面

*   X|Y Either X or Y X|Y X 或 Y

*   (X) X, as a capturing group (X) X, as a capturing group

*

*   Back references 反向引用

*   \n Whatever the nth capturing group matched \n Whatever the nth capturing group matched

*

*   Quotation Quotation

*   \ Nothing, but quotes the following character \ 引用后面的字符

*   \Q Nothing, but quotes all characters until \E \Q 引用所有的字符直到 \E 出现

*   \E Nothing, but ends quoting started by \Q \E 结束以 \Q 开始的引用

*

*   Special constructs (non-capturing) Special constructs (non-capturing)

*   (?:X) X, as a non-capturing group (?:X) X, as a non-capturing group

*   (?idmsux-idmsux) Nothing, but turns match flags on - off (?idmsux-idmsux) 匹配标志开关

*   (?idmsux-idmsux:X) X, as a non-capturing group with the given flags on - off (?idmsux-idmsux:X) X, as a non-capturing group with the given flags on

*   (?=X) X, via zero-width positive lookahead  - off

*   (?!X) X, via zero-width negative lookahead (?=X) X, via zero-width positive lookahead

*   (?<=X) X, via zero-width positive lookbehind (?!X) X, via zero-width negative lookahead

*   (?<!X) X, via zero-width negative lookbehind (?<=X) X, via zero-width positive lookbehind

*   (?>X) X, as an independent, non-capturing group (?<!X) X, via zero-width negative lookbehind

*   (?>X) X, as an independent, non-capturing group

*

*   Backslashes, escapes, and quoting

*    反斜杠字符('\')用来转义，就像上面的表中定义的那样，如果不这样做的话可能会产生

*    歧义。因此，表达式\\匹配

*    单个反斜杠，表达式\{匹配单个左花括号。

*    如果把反斜杠放在没有定义转移构造的任何字母符号前面都会发生错误，这些将被保留

*    到以后的正则表达式中扩展。反斜杠可以放在任何

*    非字母符号前面，即使它没有定义转义构造也不会发生错误。

*    在java语言规范中指出，在java代码中自符串中的反斜杠是必要的，不管用于Unicode转

*    义，还是用于普通的字符转义。因此，

*    为了保持正则表达式的完整性，在java字符串中要写两个反斜杠。例如，在正则表达式

*    中字符'\b'代表退格，'\\b'则代表单词边界。'\(hello\)'是无效的，并且会产生编译

*    时错误，你必须用

*    '\\(hello\\)'来匹配(hello)。

*

*   Character Classes

*

*    字符类可以出现在其他字符类内部，并且可以由并操作符和与操作符(&&)组成。并集操

*    作结果是，其中的任意字符，肯定在至少其中操作数中至少出现过一次。

*    交集的结果包括各个操作数中同时出现的任意字符。

*

*    字符类操作符的优先级如下：（从高到低）

*    1 文字转义 \x

*    2 集合 [...]

*    3 范围 a-z

*    4 并集 [a-e][i-u]

*    5 交集 [a-z&&[aeiou]]

*

*    请注意各个字符类的有效字符集。例如，在字符类中，正则表达式.失去了它的特别含义

*    ，而-变成了元字符的范围指示。

*

*    Line terminators

*

*    行结束符是一个或两个字符序列，用来标识输入字符序列的一行的结束。下列都被认为

*    是行结束符：

*

*    换行符 ('\n'),

*    回车换行符 ("\r\n"),

*    回车符 ('\r'),

*    下一行 ('\u0085'),

*    行分隔符 ('\u2028'), 或

*    段分隔符 ('\u2029).

*

*    如果激活了 UNIX_LINES 模式，唯一的行结束符就是换行符。

*    除非你指定了 DOTALL 标志，否则正则表达式.匹配任何字符，只有行结束符除外。

*    确省情况时，在整个输入队列中，正则表达式^和$忽略行结束符，只匹配开始和结束。

*    如果激活了 MULTILINE 模式，则^匹配输入的开始和所有行结束符之后，除了整个输入

*    的结束。

*    在MULTILINE 模式下，$匹配所有行结束符之前，和整个输入的结束。

*

*    Groups and capturing

*

*    分组捕获通过从左到右的顺序，根据括号的数量类排序。例如，在表达式((A)(B(C)))中

*    ，有四个组：

*    1 ((A)(B(C)))

*    2 (A)

*    3 (B(C))

*    4 (C)

*

*    0组代表整个表达式。

*    分组捕获之所以如此命名，是因为在匹配过程中，输入序列的每一个与分组匹配的子序

*    列都会被保存起来。通过向后引用，被捕获的子序列可以在后面的表达式中被再次使用

*    。

*    而且，在匹配操作结束以后还可以通过匹配器重新找到。

*    与一个分组关联的被捕获到的输入通常是被保存的最近与这个分组相匹配的队列的子队

*    列。如果一个分组被第二次求值，即使失败，它的上一次被捕获的值也会被保存起来。

*    例如，

*    表达式(a(b)?)+匹配"aba"，"b"设为子分组。在开始匹配的时候，以前被捕获的输入都

*    将被清除。

*    以(?开始的分组是完全的，无需捕获的分组不会捕获任何文本，也不会计算分组总数。

*

*    Unicode support

*

*    Unicode Technical Report #18: Unicode Regular Expression Guidelines通过轻微的语法改变实现了更深层次的支持。

*    在java代码中，像\u2014 这样的转义序列，java语言规范中？3.3提供了处理方法

*    。

*    为了便于使用从文件或键盘读取的unicode转义字符，正则表达式解析器也直接实现了这

*    种转移。因此，字符串"\u2014"与"\\u2014"虽然不相等，但是编译进同一种模式，可以

*    匹配

*    十六进制数0x2014。

*

*    在Perl中，unicode块和分类被写入\p,\P。如果输入有prop属性，\p{prop}将会匹配，

*    而\P{prop}将不会匹配。块通过前缀In指定，作为在nMongolian之中。

*    分类通过任意的前缀Is指定： \p{L} 和 \p{IsL} 都引用 Unicode 字母。块和分类可以

*    被使用在字符类的内部或外部。

*

*    The Unicode Standard, Version 3.0指出了支持的块和分类。块的名字在第14章和 Unicode Character

*    Database中的 Blocks-3.txt 文件定义，

*    但空格被剔除了。例如Basic Latin"变成了 "BasicLatin"。分类的名字被定义在88页

*    ，表4-5。

*

*/

package com.base.util;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类简介: 使用正则表达式验证数据或提取数据,类中的方法全为静态的<br/>
 * <p>
 * 在这里，我将这个类定义成了一个单例，
 * <p>
 * （本来只有static 方法是不new去对象的，也就用不到单例了）<br/>
 * <p>
 * 是因为有一个 regexpHash(HashMap实例)，为了可以动态
 * <p>
 * 添加正规表达式（不知道是不是想的太多了:) ）。
 * <p>
 * <pre>
 *
 * 主要方法:1. isHardRegexpValidate(String source, String regexp)
 *
 * 区分大小写敏感的正规表达式批配
 *
 * 2. isSoftRegexpValidate(String source, String regexp)
 *
 * 不区分大小写的正规表达式批配
 *
 * 3. getHardRegexpMatchResult(String source, String regexp)
 *
 * 返回许要的批配结果集(大小写敏感的正规表达式批配)
 *
 * 4. getSoftRegexpMatchResult(String source, String regexp)
 *
 * 返回许要的批配结果集(不区分大小写的正规表达式批配)
 *
 * 5 getHardRegexpArray(String source, String regexp)
 *
 * 返回许要的批配结果集(大小写敏感的正规表达式批配)
 *
 * 6. getSoftRegexpMatchResult(String source, String regexp)
 *
 * 返回许要的批配结果集(不区分大小写的正规表达式批配)
 *
 * 7. getBetweenSeparatorStr(final String originStr,final char leftSeparator,final char rightSeparator)
 *
 * 得到指定分隔符中间的字符串的集合
 *
 * 正规表达式目前共 25种
 *
 * 1.匹配图象
 *
 * icon_regexp;
 *
 * 2 匹配email地址
 *
 * email_regexp;
 *
 * 3 匹配匹配并提取url
 *
 * url_regexp;
 *
 * 4 匹配并提取http
 *
 * http_regexp;
 *
 * 5.匹配日期
 *
 * date_regexp;
 *
 * 6 匹配电话
 *
 * phone_regexp;
 *
 * 7 匹配身份证
 *
 * ID_card_regexp;
 *
 * 8 匹配邮编代码
 *
 * ZIP_regexp
 *
 * 9. 不包括特殊字符的匹配 (字符串中不包括符号 数学次方号^ 单引号' 双引号" 分号; 逗号, 帽号:
 *
 * 数学减号- 右尖括号> 左尖括号< 反斜杠\ 即空格,制表符,回车符等
 *
 * non_special_char_regexp;
 *
 * 10 匹配非负整数（正整数 + 0)
 *
 * non_negative_integers_regexp;
 *
 * 11 匹配不包括零的非负整数（正整数 > 0)
 *
 * non_zero_negative_integers_regexp;
 *
 * 12 匹配正整数
 *
 * positive_integer_regexp;
 *
 * 13 匹配非正整数（负整数 + 0）
 *
 * non_positive_integers_regexp;
 *
 * 14 匹配负整数
 *
 * negative_integers_regexp;
 *
 * 15. 匹配整数
 *
 * integer_regexp;
 *
 * 16 匹配非负浮点数（正浮点数 + 0）
 *
 * non_negative_rational_numbers_regexp
 *
 * 17. 匹配正浮点数
 *
 * positive_rational_numbers_regexp
 *
 * 18 匹配非正浮点数（负浮点数 + 0）
 *
 * non_positive_rational_numbers_regexp;
 *
 * 19 匹配负浮点数
 *
 * negative_rational_numbers_regexp;
 *
 * 20 .匹配浮点数
 *
 * rational_numbers_regexp;
 *
 * 21. 匹配由26个英文字母组成的字符串
 *
 * letter_regexp;
 *
 * 22. 匹配由26个英文字母的大写组成的字符串
 *
 * upward_letter_regexp;
 *
 * 23 匹配由26个英文字母的小写组成的字符串
 *
 * lower_letter_regexp
 *
 * 24 匹配由数字和26个英文字母组成的字符串
 *
 * letter_number_regexp;
 *
 * 25 匹配由数字、26个英文字母或者下划线组成的字符串
 *
 * letter_number_underline_regexp;
 *
 * 26 匹配%,*$\<>的字符串
 *
 * regeEx;
 *
 * </pre>
 *
 * 作者 ygj
 * @mail wuzhi2000@hotmail.com
 */

public final class RegexUtil

{

    /**
     * 保放有四组对应分隔符
     */

    static final Set<String> SEPARATOR_SET = new TreeSet<String>();

    {

        SEPARATOR_SET.add("(");

        SEPARATOR_SET.add(")");

        SEPARATOR_SET.add("[");

        SEPARATOR_SET.add("]");

        SEPARATOR_SET.add("{");

        SEPARATOR_SET.add("}");

        SEPARATOR_SET.add("<");

        SEPARATOR_SET.add(">");

    }

    /**
     * 存放各种正规表达式(以key->value的形式)
     */

    public static HashMap<String, String> regexpHash = new HashMap<String, String>();

    private RegexUtil()

    {

    }

    /**
     * 返回 Regexp 实例
     *
     * @return
     */

    public static RegexUtil getInstance()

    {

        return new RegexUtil();

    }

    /**
     * 匹配图象 <br>
     * <p>
     * <p>
     * <p>
     * 格式: /相对路径/文件名.后缀 (后缀为gif,dmp,png)
     * <p>
     * <p>
     * <p>
     * 匹配 : /forum/head_icon/admini2005111_ff.gif 或 admini2005111.dmp<br>
     * <p>
     * <p>
     * <p>
     * 不匹配: c:/admins4512.gif
     */

    public static final String icon_regexp = "^(/{0,1}\\w){1,}\\.(gif|dmp|png|jpg)$|^\\w{1,}\\.(gif|dmp|png|jpg)$";

    /**
     * 匹配email地址 <br>
     * <p>
     * <p>
     * <p>
     * 格式: XXX@XXX.XXX.XX
     * <p>
     * <p>
     * <p>
     * 匹配 : foo@bar.com 或 foobar@foobar.com.au <br>
     * <p>
     * <p>
     * <p>
     * 不匹配: foo@bar 或 $$$@bar.com
     */

    public static final String email_regexp = "(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)";

    /**
     * 匹配匹配并提取url <br>
     * <p>
     * <p>
     * <p>
     * 格式: XXXX://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX
     * <p>
     * <p>
     * <p>
     * 匹配 : http://www.suncer.com 或news://www<br>
     * <p>
     * <p>
     * <p>
     * 提取(MatchResult matchResult=matcher.getMatch()):
     * <p>
     * matchResult.group(0)= http://www.suncer.com:8080/index.html?login=true
     * <p>
     * matchResult.group(1) = http
     * <p>
     * matchResult.group(2) = www.suncer.com
     * <p>
     * matchResult.group(3) = :8080
     * <p>
     * matchResult.group(4) = /index.html?login=true
     * <p>
     * <p>
     * <p>
     * 不匹配: c:\window
     */

    public static final String url_regexp = "(\\w+)://([^/:]+)(:\\d*)?([^#\\s]*)";

    /**
     * 匹配并提取http <br>
     * <p>
     * <p>
     * <p>
     * 格式: http://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX 或 ftp://XXX.XXX.XXX 或
     * https://XXX
     * <p>
     * <p>
     * <p>
     * 匹配 : http://www.suncer.com:8080/index.html?login=true<br>
     * <p>
     * <p>
     * <p>
     * 提取(MatchResult matchResult=matcher.getMatch()):
     * <p>
     * matchResult.group(0)= http://www.suncer.com:8080/index.html?login=true
     * <p>
     * matchResult.group(1) = http
     * <p>
     * matchResult.group(2) = www.suncer.com
     * <p>
     * matchResult.group(3) = :8080
     * <p>
     * matchResult.group(4) = /index.html?login=true
     * <p>
     * <p>
     * <p>
     * 不匹配: news://www
     */

    public static final String http_regexp = "(http|https|ftp)://([^/:]+)(:\\d*)?([^#\\s]*)";

    /**
     * 匹配日期 <br>
     * <p>
     * <p>
     * <p>
     * 格式(首位不为0): XXXX-XX-XX 或 XXXX XX XX 或 XXXX-X-X <br>
     * <p>
     * <p>
     * <p>
     * 范围:1900--2099 <br>
     * <p>
     * <p>
     * <p>
     * 匹配 : 2005-04-04 <br>
     * <p>
     * <p>
     * <p>
     * 不匹配: 01-01-01
     */

    public static final String date_regexp = "^((((19){1}|(20){1})d{2})|d{2})[-\\s]{1}[01]{1}d{1}[-\\s]{1}[0-3]{1}d{1}$";// 匹配日期

    /**
     * 匹配电话 <br>
     * <p>
     * <p>
     * <p>
     * 格式为: 0XXX-XXXXXX(10-13位首位必须为0) 或0XXX XXXXXXX(10-13位首位必须为0) 或 <br>
     * <p>
     * (0XXX)XXXXXXXX(11-14位首位必须为0) 或 XXXXXXXX(6-8位首位不为0) 或
     * <p>
     * XXXXXXXXXXX(11位首位不为0) <br>
     * <p>
     * <p>
     * <p>
     * 匹配 : 0371-123456 或 (0371)1234567 或 (0371)12345678 或 010-123456 或
     * <p>
     * 010-12345678 或 12345678912 <br>
     * <p>
     * <p>
     * <p>
     * 不匹配: 1111-134355 或 0123456789
     */

    public static final String phone_regexp = "^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";

    /**
     * 匹配身份证 <br>
     * <p>
     * <p>
     * <p>
     * 格式为: XXXXXXXXXX(10位) 或 XXXXXXXXXXXXX(13位) 或 XXXXXXXXXXXXXXX(15位) 或
     * <p>
     * XXXXXXXXXXXXXXXXXX(18位) <br>
     * <p>
     * <p>
     * <p>
     * 匹配 : 0123456789123 <br>
     * <p>
     * <p>
     * <p>
     * 不匹配: 0123456
     */

    public static final String ID_card_regexp = "^\\d{10}|\\d{13}|\\d{15}|\\d{18}$";

    /**
     * 匹配邮编代码 <br>
     * <p>
     * <p>
     * <p>
     * 格式为: XXXXXX(6位) <br>
     * <p>
     * <p>
     * <p>
     * 匹配 : 012345 <br>
     * <p>
     * <p>
     * <p>
     * 不匹配: 0123456
     */

    public static final String ZIP_regexp = "^[0-9]{6}$";// 匹配邮编代码

    /**
     * 不包括特殊字符的匹配 (字符串中不包括符号 数学次方号^ 单引号' 双引号" 分号; 逗号, 帽号: 数学减号- 右尖括号> 左尖括号< 反斜杠\
     * 即空格,制表符,回车符等 )<br>
     * <p>
     * <p>
     * <p>
     * 格式为: x 或 一个一上的字符 <br>
     * <p>
     * <p>
     * <p>
     * 匹配 : 012345 <br>
     * <p>
     * <p>
     * <p>
     * 不匹配: 0123456
     */

    public static final String non_special_char_regexp = "^[^'\"\\;,:-<>\\s].+$";// 匹配邮编代码

    /**
     * 匹配非负整数（正整数 + 0)
     */

    public static final String non_negative_integers_regexp = "^\\d+$";

    /**
     * 匹配不包括零的非负整数（正整数 > 0)
     */

    public static final String non_zero_negative_integers_regexp = "^[1-9]+\\d*$";

    /**
     * 匹配正整数
     */

    public static final String positive_integer_regexp = "^[0-9]*[1-9][0-9]*$";

    /**
     * 匹配非正整数（负整数 + 0）
     */

    public static final String non_positive_integers_regexp = "^((-\\d+)|(0+))$";

    /**
     * 匹配负整数
     */

    public static final String negative_integers_regexp = "^-[0-9]*[1-9][0-9]*$";

    /**
     * 匹配整数
     */

    public static final String integer_regexp = "^-?\\d+$";

    /**
     * 匹配非负浮点数（正浮点数 + 0）
     */

    public static final String non_negative_rational_numbers_regexp = "^\\d+(\\.\\d+)?$";

    /**
     * 匹配正浮点数
     */

    public static final String positive_rational_numbers_regexp = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";

    /**
     * 匹配非正浮点数（负浮点数 + 0）
     */

    public static final String non_positive_rational_numbers_regexp = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";

    /**
     * 匹配负浮点数
     */

    public static final String negative_rational_numbers_regexp = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";

    /**
     * 匹配浮点数
     */

    public static final String rational_numbers_regexp = "^(-?\\d+)(\\.\\d+)?$";

    /**
     * 匹配由26个英文字母组成的字符串
     */

    public static final String letter_regexp = "^[A-Za-z]+$";

    /**
     * 匹配由26个英文字母的大写组成的字符串
     */

    public static final String upward_letter_regexp = "^[A-Z]+$";

    /**
     * 匹配由26个英文字母的小写组成的字符串
     */

    public static final String lower_letter_regexp = "^[a-z]+$";

    /**
     * 匹配由数字和26个英文字母组成的字符串
     */

    public static final String letter_number_regexp = "^[A-Za-z0-9]+$";

    /**
     * 匹配由数字、26个英文字母或者下划线组成的字符串
     */

    public static final String letter_number_underline_regexp = "^[a-zA-Z0-9_]+$";

    /**
     * 匹配%,*$\<>的字符串
     */

    public static final String regeEx = "[\\\\%,*$<>]";

    /**
     * 匹配中文的字符串
     */

    public static final String rege_Chinese = "[u4e00-u9fa5]";

    /**
     * 匹配由汉字英文字母数字组成的字符串
     */

    public static final String letter_number_china_regexp = "^[\u4E00-\u9fa5_a-zA-Z0-9]+$";


    /**
     * @Fields letter_number_china_regexp : 验证手机号是否正确
     */
    public static final String letter_phone__regexp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * @Fields letter_pwd__regexp : 密码合法性校验 大于等于6位小于等于12位 ,支持大小写字母、特殊字符和数字
     */
    public static final String letter_pwd__regexp = "^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,12}$";


    /**
     * 添加正规表达式 (以key->value的形式存储)
     *
     * 属性 regexpName 该正规表达式名称 `
     * 属性 regexp     该正规表达式内容
     */

    public void putRegexpHash(String regexpName, String regexp) {

        regexpHash.put(regexpName, regexp);

    }

    /**
     * 得到正规表达式内容 (通过key名提取出value[正规表达式内容])
     *
     * 属性 regexpName 正规表达式名称
     * @return 正规表达式内容
     */

    public String getRegexpHash(String regexpName)

    {

        if (regexpHash.get(regexpName) != null)

        {

            return (regexpHash.get(regexpName));

        } else

        {

            System.out.println("在regexpHash中没有此正规表达式");

            return "";

        }

    }

    /**
     * 清除正规表达式存放单元
     */

    public void clearRegexpHash()

    {

        regexpHash.clear();

        return;

    }

    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

//    /**
//     * <pre>
//     *
//     * 得到指定分隔符中间的字符串的集合,
//     *
//     * 说明: 1.分隔符为 ()，[]，{}，<> 中的一组
//     *
//     * 2.得到的集合以 ASCII 的升序排列
//     *
//     * 如 String originStr="((([a]+[b])/[c])-24)+[d]";
//     *
//     * 则 getStrBetweenSeparator(originStr,"[","]"));
//     *
//     * 返回结果集合有四个元素 [a, b, c, d],
//     *
//     * 以 ASCII 的升序排列
//     *
//     * </pre>
//     *
//     * 属性 originStr      要提取的源字符串
//     * 属性 leftSeparator  左边的分隔符
//     * 属性 rightSeparator 右边的分隔符
//     * @return 指定分隔符中间的字符串的集合
//     */
//
//    public static Set<String> getBetweenSeparatorStr(final String originStr, final char leftSeparator, final char rightSeparator) {
//
//        Set<String> variableSet = new TreeSet<String>();
//
//        if (originStr == null || originStr.equals(""))
//
//        {
//
//            return variableSet;
//
//        }
//
//        String sTempArray[] = originStr.split("(\\" + leftSeparator + ")");
//
//        for (int i = 1; i < sTempArray.length; i++)
//
//        {
//
//            int endPosition = sTempArray[i].indexOf(rightSeparator);
//
//            String sTempVariable = sTempArray[i].substring(0, endPosition);
//
//            variableSet.add(sTempVariable);
//
//        }
//
//        return variableSet;
//
//    }

}