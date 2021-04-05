package ru.myacademyhomework.myfilms

sealed class LoadState
class Loading: LoadState()
class Ready: LoadState()

