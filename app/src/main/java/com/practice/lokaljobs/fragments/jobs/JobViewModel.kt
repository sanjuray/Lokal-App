package com.practice.lokaljobs.fragments.jobs

import com.practice.lokaljobs.base.BaseViewModel
import com.practice.lokaljobs.model.Result
import com.practice.lokaljobs.utils.CommonUtils.getNAIfEmpty
import com.practice.lokaljobs.utils.CommonUtils.getDateFormatted

class JobViewModel: BaseViewModel<Any>() {

    private lateinit var result: Result

    fun init(data: Result){
        result = data
    }

    fun getResult() = result

    fun getTitle() = getNAIfEmpty(result.title)

    fun getSalary() = getNAIfEmpty(result.primary_details.Salary)

    fun getCompanyName() = getNAIfEmpty(result.company_name)

    fun getCompanyLocation() = getNAIfEmpty(result.primary_details.Place)

    fun getJobTags() = result.job_tags

    fun getJobId() = result.id

    fun isJobBookmarked() = result.is_bookmarked

    fun getExperience() = getNAIfEmpty(result.primary_details.Experience)

    fun getQualification() = getNAIfEmpty(result.primary_details.Qualification)

    fun getGender() = getNAIfEmpty(result.contentV3.V3[1].field_value)

    fun getShiftTimings() = getNAIfEmpty(result.contentV3.V3[2].field_value)

    fun getJobDescription() = getNAIfEmpty(result.other_details)

    fun getPostedDate() = getDateFormatted(result.created_on)

    fun getViews() = result.views

    fun getPhoneNo() = result.custom_link

    fun getWhatsappLink() = result.contact_preference.whatsapp_link

}