package com.example.dubizzletask.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EndPointUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EndPointPort