package eu.hxreborn.gboardmaterialexpressiveblack

import android.graphics.RenderEffect
import android.graphics.BlurMaskFilter
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import io.github.libxposed.api.XposedInterface
import io.github.libxposed.api.XposedInterface.AfterHookCallback
import io.github.libxposed.api.annotations.AfterInvocation
import io.github.libxposed.api.annotations.XposedHooker

@XposedHooker
class BlurBehindHooker : XposedInterface.Hooker {
    companion object {
        // Blur radius in pixels — tweak this to taste
        private const val BLUR_RADIUS = 20.0f

        @JvmStatic
        @AfterInvocation
        fun afterOnCreate(callback: AfterHookCallback) {
            val window = callback.thisObject as? Window ?: return

            runCatching {
                // Apply blur behind on the window itself (API 31+)
                // This is the same mechanism Android uses on lockscreen
                window.setBlurBehindRadius(BLUR_RADIUS.toInt())

                // Also need to set the flag so the system actually renders it
                window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)

                module.log("${GboardAmoledModule.TAG} BlurBehind applied, radius=$BLUR_RADIUS")
            }.onFailure {
                module.log("${GboardAmoledModule.TAG} BlurBehind failed: ${it.message}")
                // Fallback: try applying BlurEffect directly to the decor view
                applyBlurToDecorView(window)
            }
        }

        /**
         * Fallback for devices where setBlurBehindRadius doesn't work on IME windows.
         * Walks the view hierarchy and applies a RenderEffect blur to the
         * background/surface views instead.
         */
        private fun applyBlurToDecorView(window: Window) {
            runCatching {
                val decorView = window.decorView as? ViewGroup ?: return

                // The IME layout is typically the first child — find the
                // background surface view and apply blur render effect to it
                decorView.postDelayed({
                    val backgroundView = findBackgroundView(decorView) ?: return@postDelayed

                    // Apply a GraphicsLayer blur render effect
                    // This blurs the view's own content but we want blur BEHIND,
                    // so we actually need to set the view semi-transparent
                    // and rely on the window-level blur above
                    backgroundView.alpha = 0.85f

                    module.log("${GboardAmoledModule.TAG} Fallback: set background view alpha")
                }, 100)
            }.onFailure {
                module.log("${GboardAmoledModule.TAG} BlurBehind fallback failed: ${it.message}")
            }
        }

        /**
         * Finds the topmost background/surface view in the IME layout.
         * Gboard's layout typically has a FrameLayout or custom view as the
         * root surface that holds the keyboard content.
         */
        private fun findBackgroundView(root: ViewGroup): View? {
            // First child of decor is usually the IME's main layout container
            if (root.childCount == 0) return null

            val firstChild = root.getChildAt(0)

            // If it's a ViewGroup, go one level deeper — the surface is usually
            // the first child of the main layout
            if (firstChild is ViewGroup && firstChild.childCount > 0) {
                return firstChild.getChildAt(0)
            }

            return firstChild
        }
    }
}
