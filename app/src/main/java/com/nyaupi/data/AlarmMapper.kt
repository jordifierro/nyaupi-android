package com.nyaupi.data


data class AlarmMapper(val alarmActive: Boolean, val doorOpen: Boolean) {

    fun toDomain() = Alarm(active = alarmActive)
}