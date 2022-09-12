package hu.ait.aittimeshower

class Car(var type: String) {

    init {

    }

    constructor(type: String, speed: Int) : this(type){

    }

    fun getName() : String {
        return type
    }

}