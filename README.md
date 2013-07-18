DNTShareCalculation
===================

DNT share calculation for desktop and android 


A small utility to convert the values processed from Hive queries of DNT to JSON

The tool needs the location of the data file and the year of calculation to be passed as  parameters


Sample Input Data - dataInput
=============================

2013-04-01 3 4

2013-04-02 3 4

2013-05-01 2 3

2013-05-08 8 1


Arguments to be passed [for example]
=====================================
args[0]=/home/user/dataInput
args[1]=2013


SampleOuput
===============
DNTSHARECALCULATION
######DESKTOP JSON####### {
  "GLOBAL": [
    {
      "date": "year-05-01",
      "percentage": 0.3225806451612903
    },
    {
      "date": "year-04-01",
      "percentage": 0.2
    }
  ]
}
######FENNEC JSON#######{
  "GLOBAL": [
    {
      "date": "year-05-01",
      "percentage": 0.12903225806451613
    },
    {
      "date": "year-04-01",
      "percentage": 0.26666666666666666
    }
  ]
}


