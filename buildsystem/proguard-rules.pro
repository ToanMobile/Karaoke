-dontobfuscate
-dontoptimize
-ignorewarnings
-keepattributes SourceFile,LineNumberTable,Exceptions
-keepnames class * extends java.lang.Throwable


-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# ----------------------------------------
# Retrolambda
# ----------------------------------------
-dontwarn java.lang.invoke.*


# ----------------------------------------
# Parceler rules
# ----------------------------------------
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep class org.parceler.Parceler$$Parcels


# ----------------------------------------
# Realm
# ----------------------------------------
-keep class com.vicpin.krealmextensions.**
-keepnames public class * extends io.realm.RealmObject
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class *
-dontwarn io.realm.**

# ----------------------------------------
# Glide
# ----------------------------------------
-dontwarn jp.co.cyberagent.android.gpuimage.**
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# ----------------------------------------
# GSON
# ----------------------------------------
-keepattributes Signature
-dontwarn org.androidannotations.api.rest.**
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.FieldNamingStrategy { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer


# ----------------------------------------
# Eventbus
# ----------------------------------------
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# ----------------------------------------
# Libs
# ----------------------------------------
-keep class org.joda.time.** { *; }
-keep class okio.** { *; }
-keep class com.google.gson.**{ *; }
-keep class com.github.moduth.blockcanary.**{ *; }
-dontwarn com.github.moduth.blockcanary.**
-keep class com.google.zxing.**{ *; }
-keep class okio.**{ *; }
-dontwarn okio.**
-keep class rx.**{ *; }
-dontwarn rx.**
#Stetho
-keep class com.facebook.stetho.** { *; }
-keep class com.uphyca.** { *; }
-dontwarn com.facebook.stetho.**

#ReactiveNetwork
-dontwarn com.github.pwittchen.reactivenetwork.library.ReactiveNetwork
-dontwarn io.reactivex.functions.Function
-dontwarn rx.internal.util.**
-dontwarn sun.misc.Unsafe

# Dagger ProGuard rules.
-dontwarn com.google.errorprone.annotations.**

# Utils Core
-keep class com.blankj.utilcode.** { *; }
-keepclassmembers class com.blankj.utilcode.** { *; }
-dontwarn com.blankj.utilcode.**
