package vn.gomisellers.apps;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import io.agora.rtc.RtcEngine;
import vn.gomisellers.apps.data.source.local.prefs.AppPreferences;
import vn.gomisellers.apps.event.AgoraEventHandler;
import vn.gomisellers.apps.event.EngineConfig;
import vn.gomisellers.apps.event.EventHandler;
import vn.gomisellers.apps.main.live.main.stats.StatsManager;
import vn.gomisellers.apps.utils.Constants;
import vn.gomisellers.apps.utils.FileUtil;
import vn.gomisellers.apps.utils.LogUtils;
import vn.gomisellers.apps.utils.PrefManager;

public class EappsApplication extends Application {
    private static EappsApplication instance;

    private static AppPreferences appPreferences;

    private RtcEngine mRtcEngine;
    private EngineConfig mGlobalConfig = new EngineConfig();
    private AgoraEventHandler mHandler = new AgoraEventHandler();
    private StatsManager mStatsManager = new StatsManager();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        try {
            mRtcEngine = RtcEngine.create(getApplicationContext(), getString(R.string.private_app_id), mHandler);
            mRtcEngine.setChannelProfile(io.agora.rtc.Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
            mRtcEngine.enableVideo();
            mRtcEngine.setLogFile(FileUtil.initializeLogFile(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

        initConfig();
        if (BuildConfig.DEBUG) {
            LogUtils.setLogLevel(LogUtils.LogLevel.LOG_LEVEL_VERBOSE);
        } else {
            LogUtils.setLogLevel(LogUtils.LogLevel.LOG_LEVEL_NONE);
        }
    }

    private void initConfig() {
        SharedPreferences pref = PrefManager.getPreferences(getApplicationContext());
        mGlobalConfig.setVideoDimenIndex(pref.getInt(
                Constants.PREF_RESOLUTION_IDX, Constants.DEFAULT_PROFILE_IDX));

        boolean showStats = pref.getBoolean(Constants.PREF_ENABLE_STATS, false);
        mGlobalConfig.setIfShowVideoStats(showStats);
        mStatsManager.enableStats(showStats);

        mGlobalConfig.setMirrorLocalIndex(pref.getInt(Constants.PREF_MIRROR_LOCAL, 0));
        mGlobalConfig.setMirrorRemoteIndex(pref.getInt(Constants.PREF_MIRROR_REMOTE, 0));
        mGlobalConfig.setMirrorEncodeIndex(pref.getInt(Constants.PREF_MIRROR_ENCODE, 0));
    }

    public EngineConfig engineConfig() {
        return mGlobalConfig;
    }

    public RtcEngine rtcEngine() {
        return mRtcEngine;
    }

    public StatsManager statsManager() {
        return mStatsManager;
    }

    public void registerEventHandler(EventHandler handler) {
        mHandler.addHandler(handler);
    }

    public void removeEventHandler(EventHandler handler) {
        mHandler.removeHandler(handler);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        RtcEngine.destroy();
    }

    public static synchronized EappsApplication getInstance() {
        return instance;
    }

    public Context getAppContext() {
        return instance.getApplicationContext();
    }

    public static AppPreferences getPreferences() {
        if (appPreferences == null)
            appPreferences = new AppPreferences(instance.getAppContext());

        return appPreferences;
    }
}
