#!/usr/bin/env python
# -*- coding: utf-8 -*-

#====================================================================
# @step 1: 处理一个文件的数据
# @step 2: 读取一个文件夹下符合正则表达式的所有文件
# @step 3: 完善异常处理
# @step 4: 处理dict中key值一样的数据，去平均值
# @step 5: 处理所有的测试结果文件
# @step 6: 函数返回值
# @setp 7: 处理路径中的中文情况("str".decode('utf-8'))
# setp 8: 测试并总结
#====================================================================

#====================================================================
# Author:			Triangle23
# Email:			triangle23.lzl@gmail.com
# Website:			http://my.oschina.net/liuzeli
# FileName:			dataProcessing.py
# Description:		deal exprimental data of my paper
# Version:			3
# LastChange: 		2014-04-14 15:09:18
# History:
#====================================================================

import re,string,os

filePath = "/Users/liuzeli/Dropbox/毕业/毕业论文/测试结果/"
#dataPattern = re.compile(r"""\d+ # the integral part
#						 \.? # the decimal point
#						 \d+ # some fractional digits""",re.X)
dataPattern = re.compile(r"\d+\.?\d*")
filePattern = re.compile(r"result_BT\w+\.txt")

dealedData = {}

def getAllFiles(rootDir): 
	dealFiles = []
	list_dirs = os.walk(rootDir)
	for root, dirs, files in list_dirs:
		for f in files:
			if filePattern.search(f):
				dealFiles.append(os.path.join(root,f))
	return dealFiles

def getAvgOfTwoNum(x,y):
	return (x + y) / 2

def dealOneFileData(filePath):
	originData = None
	try:
		originData=open(filePath, "r", 0)
		for line in originData:
			if line.find("Test") >= 0:
				continue
			elif line.find("Scale") >= 0:
				scale = dataPattern.findall(line)
				scale = string.atoi(scale[0])
			else:
				data = dataPattern.findall(line)
				avg = 0.0
				for i in range(3):
					avg += string.atof(data[0 - i - 1])
				avg /= 3.0
				for i in range(7):
					data[i] = string.atoi(data[i])
				for i in range(3):
					data.pop(-1)
				data.append(avg)
				if None !=dealedData.get(scale):
					tempData = data
					data = map(getAvgOfTwoNum,data,dealedData[scale])
				dealedData[scale]=data
	except IOError:
		print "file not exist"
	except:
		print "run error"
	finally:
		if originData:
			originData.close()

dealFiles = getAllFiles(filePath)
for oneFile in dealFiles:
	dealOneFileData(oneFile)
print len(dealedData)
for data in dealedData:
	print data,": ", dealedData[data]