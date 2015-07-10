# AgentRunnerAutoTest
agent runner test. include auto generate script in batch,and check agent runner working end,and statistics the results

@in details
这里主要包括三个部分的工作。
（1）批生成脚本，每个脚本的id不一样
（2）启动线程，判断agent runer是否工作完毕
          具体策略：定时检测result目录下的文件个数是否增加，后期agent runner暴露接口，以判断时候工作完毕。
（3）分析生成结果，并生产report
          结果主要包括三种情况：
          1）正常的结果
          2）同一job，多个result
          3）空的result、错误的result 格式(xml格式)
