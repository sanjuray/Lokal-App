package com.practice.lokaljobs.utils

import retrofit2.Response

object NetworkUtils {

    fun makeNetworkCall(
        response: Response<Any>,
        onErrorCallback: (String) -> Unit,
        postProcessingCallback: (Any) -> Unit
    ){
        /**
         * 0 -> error in request and request parameters are a problem(parsing issue)
         * 1 -> error in api-end-point
         */
        try {
            CommonUtils.networkLogs(response.code(),response.message())
            if(response.isSuccessful){
                if(response.code() == 401){
                    onErrorCallback("error in request and request parameters are a problem(parsing issue)")
                }else if(response.code() == 400){
                    onErrorCallback("error in api-end-point")
                }else{
                    postProcessingCallback(response.body()!!)
                }
            }else{
                onErrorCallback("Request not succeeded.")
            }
        }catch (e: Exception){
            e.errorLogs()
        }
    }

}