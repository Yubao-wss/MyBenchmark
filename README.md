<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://stackedit.io/style.css" />
</head>

<body class="stackedit">
  <div class="stackedit__html"><h1><a id="MyBenchmark_0"></a>MyBenchmark</h1>
<h2><a id="_2"></a>性能测试框架</h2>
<p>软件环境：jdk1.8.0/ Apache Maven 3.3.9<br>
开发工具：intellij IDEA<br>
项目描述：该项目的灵感来源于 OpenJDK的JMH测试框架，主要用于测试算法在特定的情况下，响应时间和稳定性的表现情况。<br>
例如：<br>
1、测试字符串String类的相加操作与StringBuffer追加操作性能的优略。<br>
2、比较常见的几种排序算法的性能优略。</p>
<h2><a id="_11"></a>技术栈：</h2>
<p>1、利用反射机制自动发现并加载待测类。<br>
2、使用注解的方式对待测类进行相关配置。</p>
</div>
</body>

</html>
