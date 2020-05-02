package dev.bmcreations.shredder.core.extensions

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import dev.bmcreations.shredder.models.BuildConfig


val isDebugging = BuildConfig.DEBUG

@Throws(PackageManager.NameNotFoundException::class)
fun Context.packageInfo(): PackageInfo  {
    return packageManager.getPackageInfo(packageName, 0)
}
val Context.versionCode: Int
    get() {
        return try {
            packageInfo().versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            0
        }
    }

val Context.versionName: String?
    get() {
        return try {
            packageInfo().versionName
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
