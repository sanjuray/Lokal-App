package com.practice.lokaljobs.utils

import android.content.Context
import android.view.Gravity
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu

fun popMenu(context: Context, menuResourceId: Int,imageButton: ImageButton, callback: (id: Int) -> Unit){
    try {
        val popupMenu = PopupMenu(context, imageButton)
        popupMenu.inflate(menuResourceId)
        popupMenu.setOnMenuItemClickListener {
            callback(it.itemId)
            true
        }
        popupMenu.gravity = Gravity.START

        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true

        val menu = popup.get(popupMenu)
        menu.javaClass
            .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)
        popupMenu.show()
    }catch (e: Exception){
        e.errorLogs("popMenu@UiUtilities")
    }
}

fun showToast(context: Context, msg: String){
    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
}