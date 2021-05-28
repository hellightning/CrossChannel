package com.example.crossChannel.anim

import android.animation.Animator
import android.animation.ObjectAnimator
import android.util.Log
import android.view.View
import android.view.animation.OvershootInterpolator

// anim组件部分的代码学习自网络
object FabTransform {
    fun showView(view: View, to: Float) {
        val anim = ObjectAnimator.ofFloat(view, "TranslationY", 0f, to)
        anim.duration = 500
        anim.interpolator = OvershootInterpolator()
        anim.start()
        Log.d("hltn", "visisble now")
        view.visibility = View.VISIBLE
    }
    fun hideView(view: View, from: Float) {
        val anim = ObjectAnimator.ofFloat(view, "TranslationY", from, 0f)
        anim.duration = 500
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                view.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        anim.start()
        ObjectAnimator.ofFloat(view, "rotation", 0f, (360 * 30).toFloat()).setDuration(1000).start()
    }
    fun activateFabAnim(fab : View, h: Int){
        when(fab.visibility){
            View.VISIBLE->{
                FabTransform.hideView(fab, -h*0.6f)
                FabTransform.hideView(fab, -h*1.2f)
                FabTransform.hideView(fab, -h*2.4f)
            }
            else->{
                FabTransform.showView(fab, -h*0.6f)
                FabTransform.showView(fab, -h*1.2f)
                FabTransform.showView(fab, -h*2.4f)
            }
        }
    }
}