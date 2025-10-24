package kz.market.updater.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.market.updater.data.config.UpdateDefaults
import kz.market.updater.domain.model.UpdateMetaData
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import javax.inject.Inject

class GitHubUpdateDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun fetchUpdateMetaData(): UpdateMetaData = withContext(Dispatchers.IO) {
        if (!isNetworkAvailable()) return@withContext UpdateMetaData.EMPTY

        try {
            val request = Request.Builder()
                .header("Accept", "application/vnd.github.v3+json")
                .header("X-GitHub-Api-Version", "2022-11-28")
                .url("https://api.github.com/repos/${UpdateDefaults.REPOSITORY_OWNER}/${UpdateDefaults.REPOSITORY_NAME}/releases/latest")
                .build()

            val response = OkHttpClient().newCall(request).execute()

            if (!response.isSuccessful) {
                Log.e("GitHubUpdateDataSource", "Error getting update metadata: ${response.code}")
                return@withContext UpdateMetaData.EMPTY
            }

            val body = response.body.string()

            val json = JSONObject(body)
            val remoteVersionTag = json.getString("tag_name").removePrefix("v")
            val assets = json.getJSONArray("assets")
            val asset = (0 until assets.length())
                .asSequence()
                .map { assets.getJSONObject(it) }
                .firstOrNull { it.getString("name").endsWith(".apk") }
                ?: return@withContext UpdateMetaData.EMPTY
            val apkUrl = asset.getString("browser_download_url")
            val digest = asset.optString("digest", "")
            val changelog = json.optString("body", "")

            return@withContext UpdateMetaData(
                remoteVersion = remoteVersionTag,
                currentVersion = getCurrentVersionTag(),
                apkUrl = apkUrl,
                apkDigest = digest,
            )
        } catch (e: Exception) {
            Log.e("GitHubUpdateDataSource", "Error getting update metadata", e)
            return@withContext UpdateMetaData.EMPTY
        }
    }

    private fun getCurrentVersionTag(): String =
        context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "0.0.0"

    private fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(ConnectivityManager::class.java)
        val activeNetwork = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}