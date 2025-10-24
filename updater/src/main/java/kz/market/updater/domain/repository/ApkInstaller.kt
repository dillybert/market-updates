package kz.market.updater.domain.repository

import java.io.File

interface ApkInstaller {
    fun installApk(apkFile: File, digest: String): Result<Unit>
}