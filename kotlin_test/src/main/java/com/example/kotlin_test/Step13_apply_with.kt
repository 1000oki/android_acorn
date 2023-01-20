package com.example.kotlin_test

import javax.swing.BoxLayout

class MachineGun{
    // 총알을 발사하는 메소드
    fun fire(): MachineGun {
        println("짱")
        return this
    }
}

class MyGun(){
    fun fire(){
        println("빵야!")
    }
}

class YourWeapon(){
    var name:String?=null
    fun prepare(){
        println("무기 작동을 준비해요")
    }
    fun move(){
        println("무기를 움직여요")
    }
    fun use(){
        println("무기를 사용해요")
    }
}

fun main(){
    val g1 = MachineGun()
    g1.fire().fire().fire()

    val g2 = MyGun()
    // 특정 참족밧을 블러 안에서 여러번 사용할 수 있다.
    with(g2){
        fire()
        fire()
        fire()
    }

    val w1 = YourWeapon()
    with(w1){
        name="잠수함"
        prepare()
        move()
        use()
    }

    val w2 = YourWeapon().apply {
        name="전투기"
        prepare()
        move()
    }

    w2.use()
}