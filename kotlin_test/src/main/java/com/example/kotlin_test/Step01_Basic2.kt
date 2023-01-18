package com.example.kotlin_test

fun main(){
    var myName="김구라"
    var yourName="해골"
    // 연결 연산자 +
    var result = "내 이름:"+myName
    var result2 = "너의 이름:"+yourName

    // javascript backtick을 이용해서 문자열 만들 때 사용했던 ${} 표현식 가능
    var result3 = "내 이름:${myName}"
    var result4 = "너의 이름:${yourName}"

    var msg:MutableList<String> = mutableListOf()
    msg.add("hello")
    msg.add("test")
    msg.add("bye")

    msg.forEach {
        println(it)
    }


    // 읽기 전용 배역
    val names= listOf<String>("kim", "lee","park")
    println(names[0])
    println(names[1])
    println(names.get(2))
    // names[0]="xxx" //수정불가

    println("------")

    // 반복문
    for(i in names.indices){
        println("${i} 번째 item:${names[i]}")
    }

    println("------")

    names.forEach{
        println(it)
    }
}