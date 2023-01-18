package com.example.kotlin_test

fun main(){
    // 수정 불가능한 Map
    val mem = mapOf<String, Any>("num" to 1, "name" to "김구라", "isMain" to true)
    // Map에 저장된 데이터 참조하는 방법1
    val num = mem.get("num")
    val name = mem.get("name")
    val addr = mem.get("addr")

    // Map에 저장된 데이터 참조하는 방법2
    val num2 = mem["num"]
    val name2 = mem["name"]
    val addr2 = mem["addr"]

    // 수정 가능한 Map
    // 빈 Map에 데이터 넣기 방법1
    val mem2 = mutableMapOf<String, Any>()
    mem2.put("num",2)
    mem2.put("name", "해골")
    mem2.put("isMan", false)

    // 빈 Map에 데이터 넣기 방법2
    val mem3:MutableMap<String, Any> = mutableMapOf()
    mem3["num"]=3
    mem3["name"]="원숭이"
    mem3["isMan"]=true
}