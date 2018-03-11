package com.example.msi.realmmemo

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by msi on 2017-12-20.
 */
@RealmClass
open class Memo(
        @PrimaryKey
        var id: Int = 0,
        var title: String = "",
        var text: String = "",
        var date: String = "") : RealmModel