package com.example.kotlin_test

// java에서 dto처럼 여러개의 값을 저장할 객체를 생성할 클래스를 정의할 때는 data class를 만들면 됨.
data class Member(var num:Int, var name:String, var addr:String)

data class Text(var num:Int, var title:String, var content:String)
fun main(){
    val m1 = Member(1, "김구라", "노량진")

    // data 클래스로 생성한 객체의 참조값을 출력해보면 객체에 저장된 내용을 보여준다.
    println(m1)
    // ListOf는 수정x, mutableListOf는 수정o
    // 수정 가능한 List를 얻어내서 참조값을 members라는 상수에 담기
    val members = mutableListOf<Member>()
    members.add(m1)
    members.add(Member(2,"해골","행신동"))

    println("--------------------------------------")

    members.forEach{
        println(it)
    }
}