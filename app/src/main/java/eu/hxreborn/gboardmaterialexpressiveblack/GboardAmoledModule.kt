package eu.hxreborn.gboardmaterialexpressiveblack

import android.content.res.TypedArray
import android.inputmethodservice.InputMethodService
import io.github.libxposed.api.XposedInterface
import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam
import io.github.libxposed.api.XposedModuleInterface.PackageLoadedParam

internal lateinit var module: GboardAmoledModule

class GboardAmoledModule(
    base: XposedInterface,
    param: ModuleLoadedParam,
) : XposedModule(base, param) {
    init {
        module = this
        module.log("$TAG Module initialized")
    }

    override fun onPackageLoaded(param: PackageLoadedParam) {
        if (param.packageName != TARGET_PACKAGE || !param.isFirstPackage) {
            return
        }

        module.log("$TAG Loaded package: ${param.packageName}")

        // --- Existing color hook ---
        val colorMethod =
            runCatching {
                TypedArray::class.java.getDeclaredMethod(
                    "getColor",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType,
                )
            }.getOrElse {
                module.log("$TAG Error resolving TypedArray.getColor: ${it.message}")
                return
            }

        runCatching {
            module.hook(colorMethod, TypedArrayColorHooker::class.java)
            module.log("$TAG Successfully hooked TypedArray.getColor")
        }.onFailure {
            module.log("$TAG Color hook failed: ${it.message}")
        }

        // --- Blur behind hook ---
        // Hook InputMethodService.onCreateWindow — this is where the IME window
        // is created and we can intercept it to apply blur
        val imeWindowMethod =
            runCatching {
                InputMethodService::class.java.getDeclaredMethod("onCreateWindow")
            }.getOrElse {
                module.log("$TAG Error resolving InputMethodService.onCreateWindow: ${it.message}")
                // Not fatal — color hook still works
                return
            }

        runCatching {
            module.hook(imeWindowMethod, BlurBehindHooker::class.java)
            module.log("$TAG Successfully hooked InputMethodService.onCreateWindow for blur")
        }.onFailure {
            module.log("$TAG Blur hook failed: ${it.message}")
        }
    }

    companion object {
        const val TAG = "GboardAmoledModule"
        private const val TARGET_PACKAGE = "com.google.android.inputmethod.latin"
    }
}
