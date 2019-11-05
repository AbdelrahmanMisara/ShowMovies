package com.moetaz.popularmoviesapp.utilities

import java.util.*

class Utile {

    companion object Factory {
        fun getLangiage(code : String)  : String {
            var loc = Locale(code)
            return loc.getDisplayLanguage(Locale.US)
        }
    }
}