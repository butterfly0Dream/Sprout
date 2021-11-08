package com.jsx.applib

/**
 * Author: JackPan
 * Date: 2021-11-08
 * Time: 11:52
 * Description:
 */
class TestGrammar {

    fun add1(a: Int, b: Int): Int {
        return a + b
    }

    fun count0(add: (Int, Int) -> Int): Int {
        // 这里将1,1两个值传递给到add函数，在外部调用词函数的地方，可以直接使用a,b的值
        return add(1, 1)
    }


    fun count(a: Int = 1, b: Int = 1, add: (Int, Int) -> Int): Int {
        // 这里将a,b两个变量传递给到add函数，在外部调用词函数的地方，可以直接使用a,b的值
        return add(a, b)
    }

    // 函数字面量方式的fun定义
    // 用来实现链式调用的样式的代码
    fun count1(a: Int = 1, b: Int = 1, add: (Int.(Int) -> Int)): Int {
        // 调用时用此写法
        return a.add(b)
        // 可以实现链式调用
//        return a.add(b).add(1).add(2)
    }

    val sum = fun Int.(other: Int): Int = this + other
    val sum2: Int.(Int) -> Int = { other -> plus(other) }

    class HTML {
        fun body() { }
    }
    fun html(init: HTML.(Int) -> Unit): HTML {
        val html = HTML()  // 创建接收者对象
        html.init(1)        // 将该接收者对象传给该 lambda
        return html
    }

    fun mainTest() {
        count0(fun(a: Int, b: Int): Int { return 1 + 1 })

        count(1, 2, fun(a: Int, b: Int): Int { return 1 + 1 })

        //此种调用方式不行，必须用下面的 lambda 表达式的方式
//        count(fun(a: Int, b: Int): Int { return 1 + 1 })

        // 这里的i，j会由count函数内部传递过来
        count{ i: Int, j: Int ->
            i + j
        }

        // 函数字面量方式的函数(Int.(Any))，仅能使用 this 获取到.前面的变量，其他的变量获取不到
        count1{
            1 + this
        }

        // 函数字面量的链式调用
        1.sum(2).sum(3).sum(4)


        html {
            this.body()// 带接收者的 lambda 由此开始
            body()   // 调用该接收者对象的一个方法
        }
    }
}