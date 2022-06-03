package org.quevedo.quevedovirtualclassrooms.data.sources.remote

import org.quevedo.quevedovirtualclassrooms.BuildConfig


object DataConsts {
    const val IO_DISPATCHER = "IO_DISPATCHER"
    //PROD ENV
    const val BASE_URL = BuildConfig.BASE_URL;

    const val REQUEST_INTERCEPTOR = "AuthRequestInterceptor"
    const val RESPONSE_INTERCEPTOR = "AuthResponseInterceptor"
}