package com.example.shopp.Utils

import java.util.regex.Pattern

//
const val PREF_NAME = "PREF_SHOP"
//////////////////////////////////////////// Partie Login
const val IDUSER = "ID"
const val NAMEUSER = "NAME"
const val EMAILUSER = "EMAIL"
const val AGEUSER = "AGE"
const val AVATARUSER = "AVATAR"
const val TOKENUSER = "TOKEN"
const val RememberEmail="RememberEmail"
const val RememberPassword="RememberPassword"
/////////////////////////////////////////////
const val EMAILFORGET = "EMAILFORGET"
const val CODEFORGET  = "CODEFORGET"
const val IS_DARK_MODE = "is_dark_mode"
const val IS_FRENSH = "is_frensh"

























//
val emailRegex = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )



